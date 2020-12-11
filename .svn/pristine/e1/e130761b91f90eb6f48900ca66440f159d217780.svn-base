package project.edge.common.util;

import org.springframework.util.FileCopyUtils;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URLConnection;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;

/**
 * Web下载工具类。配合downloadViaIframe.jsp和javascript函数MainUtils.downloadFile一起使用。
 *
 */
public class ControllerDownloadUtils {

    /**
     * 将待预览的服务端文件输出到response。
     * 
     * @param serverFile
     * @param fileName
     * @param response
     * @param locale
     * @throws IOException
     */
    public static void previewFile(File serverFile, String fileName, HttpServletResponse response)
            throws IOException {

        String mimeType = URLConnection.guessContentTypeFromName(fileName);
        if (mimeType == null) {
            mimeType = "application/octet-stream";
        }
        response.setContentType(mimeType);

        // 解决中文文件名显示问题
        fileName = new String(fileName.getBytes("GB2312"), "ISO8859-1");
        response.setHeader("Content-Disposition", "inline; filename=" + fileName);

        // response.setContentLength(downloadFile..getLabelFileContent().length);

        try (InputStream inputStream = new BufferedInputStream(new FileInputStream(serverFile));
                ServletOutputStream outputStream = response.getOutputStream()) {

            // Copy bytes from source to destination, closes both streams.
            FileCopyUtils.copy(inputStream, outputStream);
        }
    }

    /**
     * 将待下载的服务端文件输出到response。
     * 
     * @param serverFile
     * @param response
     * @param locale
     * @throws IOException
     */
    public static void downloadFile(File serverFile, HttpServletResponse response)
            throws IOException {

        String fileName = serverFile.getName();

        String mimeType = URLConnection.guessContentTypeFromName(fileName);
        if (mimeType == null) {
            mimeType = "application/octet-stream";
        }
        response.setContentType(mimeType);

        // 解决中文文件名显示问题
        fileName = new String(fileName.getBytes("GB2312"), "ISO8859-1");
        response.setHeader("Content-Disposition", "attachment; filename=" + fileName);

        // response.setContentLength(downloadFile..getLabelFileContent().length);

        try (InputStream inputStream = new BufferedInputStream(new FileInputStream(serverFile));
                ServletOutputStream outputStream = response.getOutputStream()) {

            // Copy bytes from source to destination, closes both streams.
            FileCopyUtils.copy(inputStream, outputStream);
        }
    }
}
