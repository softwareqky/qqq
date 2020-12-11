package project.edge.domain.converter;

import java.io.File;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Locale;
import java.util.Map;
import java.util.Set;

import org.springframework.context.MessageSource;
import org.springframework.util.StringUtils;

import garage.origin.common.constant.Constants;
import garage.origin.common.util.DateUtils;
import garage.origin.domain.view.AbstractSessionUserBean;
import garage.origin.domain.view.converter.ViewConverter;
import project.edge.common.constant.FolderPath;
import project.edge.domain.entity.Archive;
import project.edge.domain.entity.Dept;
import project.edge.domain.entity.ExposureSettings;
import project.edge.domain.entity.ExposureSettingsAttachment;
import project.edge.domain.entity.Project;
import project.edge.domain.view.ArchiveBean;
import project.edge.domain.view.ExposureSettingsBean;

public class ExposureSettingsBeanConverter
        implements ViewConverter<ExposureSettings, ExposureSettingsBean> {

    @Override
    public ExposureSettingsBean fromEntity(ExposureSettings entity, MessageSource messageSource,
            Locale locale) {
        ExposureSettingsBean bean = new ExposureSettingsBean();
        bean.setId(entity.getId());

        Project project = entity.getProject();
        if (project != null) {
            bean.setProject_(project.getId());
            bean.setProjectText(project.getProjectName());
        }

        Dept dept = entity.getDept();
        if (dept != null) {
            bean.setDept_(dept.getId());
            bean.setDeptText(dept.getDeptName());
        }

        if (entity.getExposureTime() != null) {
            bean.setExposureTime(
                    DateUtils.date2String(entity.getExposureTime(), Constants.DATE_FORMAT));
        }
        bean.setExposureBasis(entity.getExposureBasis());
        // 附件处理
        ArchiveBeanConverter abConverter = new ArchiveBeanConverter();
        Set<ExposureSettingsAttachment> archives = entity.getArchives();

        for (ExposureSettingsAttachment archive : archives) {
            bean.getArchivesList().add(abConverter.fromEntity(archive.getArchive(), messageSource, locale));
        }
        return bean;
    }

    @Override
    public ExposureSettings toEntity(ExposureSettingsBean bean, ExposureSettings oldEntity,
            AbstractSessionUserBean user, Date now) {
        ExposureSettings entity = oldEntity;
        //Set<Archive> archives = new HashSet<>();
        if (entity == null) { // 表示新建
            entity = new ExposureSettings();

            entity.setCreator(user.getSessionUserId());
            entity.setcDatetime(now);
        } else { // 表示修改
                 // 在Controller.postUpdate中，需要比较新老entity的关联的档案文件，所以必须clone
                 // clone之后，清空外键关联字段的值
            entity = oldEntity.clone();
            entity.setArchives(new HashSet<>());

            entity.setModifier(user.getSessionUserId());
            // 修改后，仍然保留的档案文件
            entity.setArchives(new HashSet<>());
            
            Map<String, String> map = new HashMap<>();
            for (String aid : bean.getArchivesReservedList()) {
                map.put(aid, aid);
            }
            for (String aid : bean.getArchivesReservedList()) {
                map.put(aid, aid);
            }

            // 找出需要删除的Archive
            for (ExposureSettingsAttachment dbArchive : oldEntity.getArchives()) {
                if (!map.containsKey(dbArchive.getId())) {
                    entity.getArchivesToDelete().add(dbArchive);
                }
            }
        }
        bean.setId(entity.getId()); // ID必须赋值
        entity.setmDatetime(now);

        if (!StringUtils.isEmpty(bean.getProject_())) {
            Project project = new Project();
            project.setId(bean.getProject_());
            project.setProjectName(bean.getProjectText());
            entity.setProject(project);
        }

        if (!StringUtils.isEmpty(bean.getDept_())) {
            Dept dept = new Dept();
            dept.setId(bean.getDept_());
            entity.setDept(dept);
        }
        entity.setExposureBasis(bean.getExposureBasis());
        if (!StringUtils.isEmpty(bean.getExposureTime())) {
            try {
                entity.setExposureTime(
                        DateUtils.string2Date(bean.getExposureTime(), Constants.DATE_FORMAT));
            } catch (ParseException e) {
                // do nothing
            }
        }
        // 附件处理，此处只需处理新增加的ProjectAttachment
        Set<ExposureSettingsAttachment> exposureSettingsAttachments = new HashSet<>();

        ArchiveBeanConverter abConverter = new ArchiveBeanConverter();

        for (ArchiveBean ab : bean.getArchivesList()) {
        	exposureSettingsAttachments.add(this.toAttachmentEntity(ab, abConverter, entity,user, now));
        }
        entity.setArchives(exposureSettingsAttachments);
        
        
        
        return entity;
    }
    

    /**
     * 把ArchiveBean转换成Archive，同时创建对应的ProjectAttachment并返回。
     * 
     * @param ab
     * @param abConverter
     * @param entity
     * @param attachmentType
     * @param user
     * @param now
     * @return
     */
    private ExposureSettingsAttachment toAttachmentEntity(ArchiveBean ab, ArchiveBeanConverter abConverter,
    		ExposureSettings entity, AbstractSessionUserBean user, Date now) {

    	ExposureSettingsAttachment attachment = new ExposureSettingsAttachment();
        attachment.setExposureSettings(entity);
//changed start by huang 
//        /**
//         * 第零层：根
//         * 第一层：FolderPath.PROJECT(DIR)
//         * 第二层：Project.id(DIR)
//         * 第三层：Archive文件
//         */
//        ab.setLevel(3);
//        ab.setPid(entity.getId());
//        ab.setPath(Constants.SLASH + Constants.COMMA + FolderPath.BUDGET_TEMPLATE + Constants.COMMA
//                + entity.getId());
//        Archive aentity = abConverter.toEntity(ab, null, user, now);
//
//        // "\project\guid\xxx.doc"
//        // String extension = FilenameUtils.getExtension(ab.getArchiveName()).toLowerCase();
//        // String relativePath = File.separator + FolderPath.PROJECT + File.separator
//        // + entity.getId() + File.separator + aentity.getId() + Constants.DOT + extension;
//        String relativePath = File.separator + FolderPath.BUDGET_TEMPLATE + File.separator + entity.getId()
//                + File.separator + aentity.getId();
     
        /**
         * 第零层：根
         * 第一层：年份
         * 第二层：项目名称+项目编号 FolderPath.PROJECT(DIR)
         * 第三层: 系统归档
         * 第四层：文件夹名
         * 第五层：Archive文件
         */
        ab.setLevel(5);

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy");
        Date date = new Date();
        String year = sdf.format(date);

        ab.setPid(entity.getProject().getId() + "_" + FolderPath.Exposure);

        ab.setPath(Constants.SLASH + Constants.COMMA + year + Constants.COMMA
                + entity.getProject().getId() + Constants.COMMA
                + entity.getProject().getId() + "_" + FolderPath.SYSTEM_PLACE_ON_FILE + Constants.COMMA
                + entity.getProject().getId() + "_" + FolderPath.Exposure);
        
        Archive aentity = abConverter.toEntity(ab, null, user, now);
        
        String relativePath = File.separator + year + File.separator + entity.getProject().getId() + File.separator + entity.getProject().getId() + "_"
                + FolderPath.SYSTEM_PLACE_ON_FILE + File.separator + entity.getProject().getId() + "_"
                + FolderPath.Exposure + File.separator + aentity.getId();
//changed end by huang 
        ab.setRelativePath(relativePath);
        aentity.setRelativePath(relativePath);

        // Archive:
        // id/level/pid/(id)path/relativePath由converter各自设置，
        // archiveName/isDir/fileSize在Controller中统一设置，
        // fileDigest暂不使用

        attachment.setArchive(aentity);
        return attachment;
    }

}
