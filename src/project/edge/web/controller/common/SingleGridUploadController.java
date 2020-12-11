package project.edge.web.controller.common;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.fasterxml.jackson.databind.ObjectMapper;

import garage.origin.common.constant.Constants;
import garage.origin.common.constant.JsonStatus;
import garage.origin.common.constant.OnOffEnum;
import garage.origin.domain.filter.CommonFilter;
import garage.origin.domain.view.JsonResultBean;
import garage.origin.domain.view.SessionUserBean;
import garage.origin.domain.view.ViewBean;
import project.edge.common.constant.FolderPath;
import project.edge.common.constant.SystemConfigKeys;
import project.edge.domain.business.MultipartWrapper;
import project.edge.domain.entity.Archive;
import project.edge.domain.view.ArchiveBean;
import project.edge.service.archive.ArchiveService;
import project.edge.service.system.SystemConfigService;

/**
 * 共通的Controller，只包含单个datagrid及filter，支持文件上传。
 * 
 * @param <T> 数据层实体
 * @param <V> 表现层实体
 */
public abstract class SingleGridUploadController<T, V extends ViewBean>
        extends SingleGridController<T, V> {

    @Resource
    protected SystemConfigService systemConfigService;

    @Resource
    protected ArchiveService archiveService;

    @Override
    protected boolean useFile() {
        return true;
    }

    /**
     * [t_system_config]中，根目录文件夹全路径的key。
     * 
     * @return
     */
    protected String getRootFolderPathKey() {
        return SystemConfigKeys.ARCHIVE_ROOT_FOLDER_PATH;
    }

    /**
     * 新建，支持上传文件，返回Json格式。上传的文件的存放位置，在相应的BeanConverter内设置。可以参考ProjectGroupController的做法。
     * 
     * 【约定】见修改方法的注释。
     * 
     * @param request
     * @param response
     * @param bean 表现层对象
     * @param paramMap
     * @param fieldNameArchiveListMap
     *            表单中，可以有多个字段支持文件上传，每个文件字段在此map中对应一组键值对。map中的每个key是该字段对应的[t_data_field].field_name，value是该字段在bean中对应的List<ArchiveBean>。
     * @param userBean
     * @param locale
     * @return
     */
    public void createWithUpload(HttpServletRequest request, HttpServletResponse response,
            @ModelAttribute V bean, Map<String, Object> paramMap,
            Map<String, List<ArchiveBean>> fieldNameArchiveListMap,
            @ModelAttribute(Constants.SESSION_USER) SessionUserBean userBean, Locale locale) {

        if (paramMap == null) {
            paramMap = new HashMap<>();
        }

        JsonResultBean jsonResult =
                this.parseMultipart(request, response, paramMap, fieldNameArchiveListMap);

        if (jsonResult.getStatus() != JsonStatus.ERROR) {
            jsonResult = super.create(bean, paramMap, userBean, locale);
        }

        try {
            String result = new ObjectMapper().writeValueAsString(jsonResult);
            response.getWriter().write("<textarea>" + result + "</textarea>");
        } catch (Exception e) {
            this.getLogger().error("Exception converting object to json string.", e);
        }

    }

    /**
     * 解析请求中的文件参数，转换至表现层bean，并构建paramMap，供postXxxxx使用。
     * 
     * @param request
     * @param response
     * @param paramMap
     * @param fieldNameArchiveListMap
     * @return
     */
    protected JsonResultBean parseMultipart(HttpServletRequest request,
            HttpServletResponse response, Map<String, Object> paramMap,
            Map<String, List<ArchiveBean>> fieldNameArchiveListMap) {

        // 当浏览器是IE时，如果返回application/json，IE会提示用户保存upload.json文件
        response.setContentType("text/html");

        JsonResultBean jsonResult = new JsonResultBean();
        try {

            // 将上传的文件转换成ArchiveBean
            if (request instanceof MultipartHttpServletRequest) {

                // 上传的文件信息列表，用来传入到postXxxxx方法中，然后通过postXxxxx方法将其保存到本地
                List<MultipartWrapper> multipartList = new ArrayList<>();
                paramMap.put(KEY_MULTIPART_LIST, multipartList);

                for (String fieldName : fieldNameArchiveListMap.keySet()) {

                    List<ArchiveBean> beanList = fieldNameArchiveListMap.get(fieldName);

                    List<MultipartFile> files =
                            ((MultipartHttpServletRequest) request).getFiles(fieldName);
                    for (MultipartFile f : files) {

                        String originName = f.getOriginalFilename();

                        // 将文件做初步处理，然后存入表现层bean，到converter中再做进一步转换
                        // ArchiveBean分两步处理，主要是因为entity的id在converter中才确定，relativePath依赖于id
                        ArchiveBean ab = new ArchiveBean();
                        ab.setArchiveName(originName);
                        ab.setIsDir(OnOffEnum.OFF.value());
                        ab.setFileSize((int) f.getSize());
                        beanList.add(ab);

                        // Archive:
                        // id/level/pid/(id)path/relativePath由converter各自设置，
                        // archiveName/isDir/fileSize在Controller中统一设置，
                        // fileDigest暂不使用

                        multipartList.add(new MultipartWrapper(ab, f));
                    }
                }
            }
        } catch (Exception e) {
            this.getLogger().error("Exception parsing uploaded files.", e);
            jsonResult.setStatus(JsonStatus.ERROR);
        }
        return jsonResult;
    }

    /**
     * 所有要保存的文件list
     */
    protected static final String KEY_MULTIPART_LIST = "multipartList";

    /**
     * 处理上传文件的本地保存。
     * 
     * @throws IOException
     */
    @Override
    protected void postCreate(T entity, V bean, Map<String, Object> paramMap, Locale locale)
            throws IOException {
        this.saveMultipartToFile(paramMap);
    }

    /**
     * 将文件保存到本地磁盘。
     * 
     * @param paramMap
     * @throws IOException
     */
    protected void saveMultipartToFile(Map<String, Object> paramMap) throws IOException {
        if (paramMap == null || !paramMap.containsKey(KEY_MULTIPART_LIST)) {
            return;
        }

        @SuppressWarnings("unchecked")
        List<MultipartWrapper> multipartList =
                (List<MultipartWrapper>) paramMap.get(KEY_MULTIPART_LIST);

        String rootFolderPath =
                this.systemConfigService.getStringConfig(this.getRootFolderPathKey());

        for (MultipartWrapper wp : multipartList) {

            ArchiveBean ab = wp.getArchiveBean();
            String baseFolderPath = FilenameUtils.getFullPathNoEndSeparator(ab.getRelativePath());

            File targetDir = new File(rootFolderPath, baseFolderPath);
            if (!targetDir.exists()) {
                FileUtils.forceMkdir(targetDir);
            }

            File targetFile = new File(rootFolderPath, ab.getRelativePath());
            FileUtils.writeByteArrayToFile(targetFile, wp.getFile().getBytes());
        }
    }

    /**
     * 修改，支持上传文件，返回Json格式。上传的文件的存放位置，在相应的BeanConverter内设置。可以参考ProjectGroupController的做法。
     * 
     * 【约定】：entity和表现层bean中的文件上传字段名，要与[t_data_field].field_name和[t_data_field].field_name_view一致。
     * 例如，entity中的archives字段，其[t_data_field].field_name应该是archives_，[t_data_field].field_name_view应该是archivesText。
     * 表现层bean中，无需创建archives_或archivesText字段，因为文件上传字段需要特别处理，无法借助Spring直接映射到bean。
     * 但是bean中应创建List<ArchiveBean>类型的字段，用来做特别处理的中间转换，字段名是[t_data_field].field_name去掉末尾的下划线"_"，然后加上"List"，即archivesList。
     * bean中还需要创建List<String>类型的字段，用来保存从前端传回的修改后保留的文件id列表，字段名是[t_data_field].field_name去掉末尾的下划线"_"，然后加上"ReservedList"，即archivesReservedList。
     * 
     * @param request
     * @param response
     * @param bean 表现层对象
     * @param fieldNameArchiveListMap
     *            表单中，可以有多个字段支持文件上传，每个文件字段在此map中对应一组键值对。map中的每个key是该字段对应的[t_data_field].field_name，value是该字段在bean中对应的List<ArchiveBean>。
     * @param userBean
     * @param locale
     * @return
     */
    public void updateWithUpload(HttpServletRequest request, HttpServletResponse response,
            @ModelAttribute V bean, Map<String, List<ArchiveBean>> fieldNameArchiveListMap,
            @ModelAttribute(Constants.SESSION_USER) SessionUserBean userBean, Locale locale) {

        Map<String, Object> paramMap = new HashMap<>();
        JsonResultBean jsonResult =
                this.parseMultipart(request, response, paramMap, fieldNameArchiveListMap);

        if (jsonResult.getStatus() != JsonStatus.ERROR) {
            jsonResult = super.update(bean, paramMap, userBean, locale);
        }

        try {
            String result = new ObjectMapper().writeValueAsString(jsonResult);
            response.getWriter().write("<textarea>" + result + "</textarea>");
        } catch (Exception e) {
            this.getLogger().error("Exception converting object to json string.", e);
        }

    }

    /**
     * 处理上传文件的本地保存。
     * 
     * @throws IOException
     */
    @Override
    protected void postUpdate(T entity, T oldEntity, V bean, java.util.Map<String, Object> paramMap)
            throws IOException {

        this.saveMultipartToFile(paramMap);

    }

    /**
     * 删除档案文件。
     * 
     * @param list
     * @throws IOException
     */
    protected void deleteArchiveFiles(List<Archive> list) throws IOException {

        String rootFolderPath =
                this.systemConfigService.getStringConfig(this.getRootFolderPathKey());

        String cacheFolderPath = this.context.getRealPath(FolderPath.PREVIEW_CACHE);

        for (Archive a : list) {

            this.deleteFile(a, rootFolderPath, cacheFolderPath);
        }
    }

    /**
     * 待删除Archive的id，英文半角逗号分隔
     */
    protected static final String KEY_ARCHIVE_IDS_TO_DELETE = "archiveIdsToDelete";

    /**
     * 是否要删除记录本身
     */
    protected static final String KEY_NEED_DELETE_SELF_RECORD = "needDeleteSelfRecord";

    /**
     * 删除数据库记录前，先删除文件。
     */
    @Override
    protected void beforeDelete(Map<String, Object> paramMap, String modifier, Date mDatetime) {

        if (paramMap == null || !paramMap.containsKey(KEY_ARCHIVE_IDS_TO_DELETE)) {
            return;
        }

        String idsToDelete = paramMap.get(KEY_ARCHIVE_IDS_TO_DELETE).toString();
        CommonFilter f = new CommonFilter().addWithin("id",
                StringUtils.commaDelimitedListToStringArray(idsToDelete));

        String rootFolderPath =
                this.systemConfigService.getStringConfig(this.getRootFolderPathKey());

        String cacheFolderPath = this.context.getRealPath(FolderPath.PREVIEW_CACHE);

        List<Archive> list = this.archiveService.list(f, null);
        for (Archive a : list) {

            if (OnOffEnum.ON.value() == a.getIsDeleted()) {
                continue;
            }

            this.deleteFile(a, rootFolderPath, cacheFolderPath);
        }
    }

    /**
     * 删除文件，并删除对应的预览文件。
     * 
     * @param a
     * @param rootFolderPath
     * @param cacheFolderPath
     */
    protected void deleteFile(Archive a, String rootFolderPath, String cacheFolderPath) {
        FileUtils.deleteQuietly(new File(rootFolderPath, a.getRelativePath()));
        FileUtils.deleteQuietly(new File(cacheFolderPath, a.getRelativePath()));
        FileUtils.deleteQuietly(new File(cacheFolderPath, a.getRelativePath() + ".pdf"));
        FileUtils.deleteQuietly(new File(cacheFolderPath, a.getRelativePath() + ".html"));
    }

    /**
     * 删除数据库记录后，级联删除下级的文件(夹)。
     */
    @Override
    protected void postDelete(Map<String, Object> paramMap, String modifier, Date mDatetime) {

        if (paramMap == null || !paramMap.containsKey(KEY_ARCHIVE_IDS_TO_DELETE)) {
            return;
        }

        String idsToDelete = paramMap.get(KEY_ARCHIVE_IDS_TO_DELETE).toString();
        String[] pidArray = StringUtils.commaDelimitedListToStringArray(idsToDelete);

        List<String> pidList = Arrays.asList(pidArray);
        this.archiveService.deleteSub(pidList); // 数据库有外键级联删除后，此调用可以删去

        if (paramMap.containsKey(KEY_NEED_DELETE_SELF_RECORD)
                && (boolean) paramMap.get(KEY_NEED_DELETE_SELF_RECORD)) {
            this.archiveService.delete(pidList);
        }
    }

}
