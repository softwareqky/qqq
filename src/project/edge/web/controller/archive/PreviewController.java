package project.edge.web.controller.archive;

import java.io.File;
import java.util.Arrays;
import java.util.Locale;

import javax.annotation.Resource;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.SessionAttributes;

import garage.origin.common.constant.Constants;
import garage.origin.common.constant.FileEncodingTypeEnum;
import garage.origin.common.util.DateUtils;
import garage.origin.common.util.ExcelConverterUtils;
import garage.origin.common.util.WordConverterUtils;
import garage.origin.domain.view.SessionUserBean;
import project.edge.common.constant.ControllerModelKeys;
import project.edge.common.constant.FolderPath;
import project.edge.common.constant.SystemConfigKeys;
import project.edge.common.util.ControllerDownloadUtils;
import project.edge.domain.entity.Archive;
import project.edge.service.archive.ArchiveService;
import project.edge.service.system.SystemConfigService;

/**
 * 预览功能。
 *
 */
@Controller
@SessionAttributes(Constants.SESSION_USER)
@RequestMapping("/archive/preview")
public class PreviewController {

    private static final Logger logger = LoggerFactory.getLogger(PreviewController.class);

    private static final String MODLE_KEY_HTML_URL = "htmlUrl";

    @Autowired
    protected ServletContext context;

    @Resource
    protected ArchiveService archiveService;

    @Resource
    protected SystemConfigService systemConfigService;

    /**
     * 画面Open的入口方法，为前端提供一个唯一的入口。根据不同的文件类型，一共有4种不同的情况。
     * 
     * 1. jpeg/jpg/bmp/png/gif/txt/xml，通过response返回，直接打开
     * 2. pdf/doc/docx，跳转到PDF.js的viewer.html(pdfViewer.jsp)，需要提供名为file的查询字符串，然后会请求this.pdf()方法
     * 3. xls/xlsx，跳转到转换后的HTML
     * 4. 其他，通过response返回，下载
     * 
     * @param id
     * @param response
     * @param model
     * @param userBean
     * @param locale
     * @return
     */
    @RequestMapping("/{id}/main")
    public String main(@PathVariable String id, HttpServletResponse response, Model model,
            @ModelAttribute(Constants.SESSION_USER) SessionUserBean userBean, Locale locale) {

        Archive a = this.archiveService.find(id);
        if (a == null) {
            logger.warn("No archive found with id {}.", id);
            return "common/page/noSuchFile";
        }

        String name = a.getArchiveName();
        String ext = FilenameUtils.getExtension(name).toLowerCase();

        model.addAttribute(ControllerModelKeys.PAGE_TITLE, name); // 标题

        if (Arrays.asList("jpeg", "jpg", "bmp", "png", "gif", "txt", "xml").contains(ext)) {

            try {

                String rootFolderPath = this.systemConfigService
                        .getStringConfig(SystemConfigKeys.ARCHIVE_ROOT_FOLDER_PATH);
                String path = rootFolderPath + a.getRelativePath();
                File file = new File(path);

                ControllerDownloadUtils.previewFile(file, name, response);

            } catch (Exception e) {
                logger.error("Exception previewing text or image archive file.", e);

                response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
                return "common/page/error";
            }

        } else if (Arrays.asList("pdf", "doc", "docx").contains(ext)) {

            return "archive/preview/pdfViewer"; // 包装了PDF.js的viewer.html，之后PDF.js会访问PreviewController.pdf()方法

        } else if (Arrays.asList("xls", "xlsx").contains(ext)) {

            // 将Excel转换成HTML
            try {

                this.excelToHtml(a);

            } catch (Exception e) {
                logger.error("Exception preview Excel archive file.", e);
                return "common/page/error";
            }

            String contextPath = this.context.getContextPath();
            model.addAttribute(MODLE_KEY_HTML_URL, contextPath + FolderPath.PREVIEW_CACHE
                    + a.getRelativePath().replace(Constants.BACK_SLASH, Constants.SLASH) + ".html"); // HTML的URL

            return "archive/preview/htmlViewer"; // 包装了转换生成的HTML

        }

        return null;

    }

    /**
     * 将Excel转换成HTML，并返回HTML文件的路径。
     * 
     * @param a
     * @return
     * @throws Exception
     */
    private String excelToHtml(Archive a) throws Exception {

        String rootFolderPath =
                this.systemConfigService.getStringConfig(SystemConfigKeys.ARCHIVE_ROOT_FOLDER_PATH);

        String cacheFolderPath = this.context.getRealPath(FolderPath.PREVIEW_CACHE);

        String path = rootFolderPath + a.getRelativePath();
        String htmlPath = cacheFolderPath + a.getRelativePath() + ".html";
        String attachedDir = cacheFolderPath + a.getRelativePath();

        String parent = FilenameUtils.getFullPathNoEndSeparator(a.getRelativePath());
        if (File.separator.equals(parent)) {
            parent = ""; // 确保路径末尾不加斜杠
        }
        String baseDir = cacheFolderPath + parent;

        File html = new File(htmlPath);

        // 预览的版本已过期或不存在
        if (a.getPreviewVersion() == null
                || DateUtils.compareDate(a.getPreviewVersion(), a.getmDatetime()) < 0
                || !html.exists()) {

            File attachedDirFile = new File(attachedDir);
            if (!attachedDirFile.exists()) {
                attachedDirFile.mkdirs();
            }

            ExcelConverterUtils.toHtml(path, htmlPath, attachedDir, a.getArchiveName());

            // 修改入口html中的链接的前缀
            // String encoding = garage.origin.common.util.FileUtils.getFilecharset(htmlPath);
            String content = FileUtils.readFileToString(html, FileEncodingTypeEnum.UTF8);
            content = content.replace(baseDir + File.separator, "");
            FileUtils.writeStringToFile(html, content, FileEncodingTypeEnum.UTF8);

            // 更新预览版本
            a.setPreviewVersion(a.getmDatetime());
            this.archiveService.updatePreviewVersion(a);
        }

        return htmlPath;
    }

    /**
     * 处理doc/docx/pdf类型的 文件，如果是doc和docx，则先转换成pdf，然后再将pdf输出response，必须配合前端的PDF.js使用。
     * 
     * @param id
     * @param response
     * @param userBean
     * @param locale
     * @return
     */
    @RequestMapping("/{id}/pdf")
    public void pdf(@PathVariable String id, HttpServletResponse response,
            @ModelAttribute(Constants.SESSION_USER) SessionUserBean userBean, Locale locale) {

        try {

            Archive a = this.archiveService.find(id);
            if (a == null) {
                logger.warn("No archive found with id {}.", id);
                return;
            }

            String rootFolderPath = this.systemConfigService
                    .getStringConfig(SystemConfigKeys.ARCHIVE_ROOT_FOLDER_PATH);
            String path = rootFolderPath + a.getRelativePath();
            File pdf = new File(path);

            // doc和docx的情况下，先转换成pdf
            String name = a.getArchiveName();
            String ext = FilenameUtils.getExtension(name).toLowerCase();
            if (!"pdf".equals(ext)) {

                String cacheFolderPath = this.context.getRealPath(FolderPath.PREVIEW_CACHE);
                String pdfPath = cacheFolderPath + a.getRelativePath() + ".pdf";

                pdf = new File(pdfPath);

                // 预览的版本已过期或不存在
                if (a.getPreviewVersion() == null
                        || DateUtils.compareDate(a.getPreviewVersion(), a.getmDatetime()) < 0
                        || !pdf.exists()) {

                    String baseFolderPath =
                            FilenameUtils.getFullPathNoEndSeparator(a.getRelativePath());

                    File targetDir = new File(cacheFolderPath, baseFolderPath);
                    if (!targetDir.exists()) {
                        FileUtils.forceMkdir(targetDir);
                    }

                    WordConverterUtils.toPdf(path, pdfPath);

                    // 更新预览版本
                    a.setPreviewVersion(a.getmDatetime());
                    this.archiveService.updatePreviewVersion(a);
                }
            }

            ControllerDownloadUtils.downloadFile(pdf, response);

        } catch (Exception e) {
            logger.error("Exception preview Word or PDF archive file.", e);
        }

    }
}
