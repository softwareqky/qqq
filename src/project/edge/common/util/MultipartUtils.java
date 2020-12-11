package project.edge.common.util;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import garage.origin.common.constant.OnOffEnum;
import project.edge.domain.business.MultipartWrapper;
import project.edge.domain.view.ArchiveBean;

/**
 * 文件上传的工具类
 * @author lynx
 *
 */
public class MultipartUtils {
	
	/**
	 * 保存上传文件到临时文件目录
	 * @param multipartList
	 * @param rootDir
	 * @return
	 * @throws IOException
	 */
	public static void saveMultipartToLocal(List<MultipartWrapper> multipartList, String rootDir) throws IOException {
		
		for (MultipartWrapper wp : multipartList) {

            ArchiveBean ab = wp.getArchiveBean();
            String baseFolderPath = FilenameUtils.getFullPathNoEndSeparator(ab.getRelativePath());

            File targetDir = new File(rootDir, baseFolderPath);
            if (!targetDir.exists()) {
                FileUtils.forceMkdir(targetDir);
            }

            File targetFile = new File(rootDir, ab.getRelativePath());
            FileUtils.writeByteArrayToFile(targetFile, wp.getFile().getBytes());
        }
	}
	
	
	/**
	 * 解析Multipart文件数组倒MultipartWrapper中
	 * @param files
	 * @return
	 */
	public static List<MultipartWrapper> parseMultipart(MultipartFile[] files) {
		
		List<MultipartWrapper> multipartList = new ArrayList<>();
		
		for (MultipartFile file : files) {
			
			ArchiveBean archive = new ArchiveBean();
			archive.setArchiveName(file.getOriginalFilename());
			archive.setIsDir(OnOffEnum.OFF.value());
			archive.setFileSize((int) file.getSize());
			
			multipartList.add(new MultipartWrapper(archive, file));
		}
		
		return multipartList;
	}
	
	
	/**
	 * 解析Multipart文件请求到MultipartWrapper中
	 * @param request
	 * @return
	 */
	public static List<MultipartWrapper> parseMultipart(MultipartHttpServletRequest request) {
		
		List<MultipartWrapper> multipartList = new ArrayList<>();
		
		Map<String, MultipartFile> fileMap = request.getFileMap();
		for (Map.Entry<String, MultipartFile> entity: fileMap.entrySet()) {

			// 取得文件和文件名
			MultipartFile uploadFile = entity.getValue();
			String fileName = uploadFile.getOriginalFilename();
			
			ArchiveBean archive = new ArchiveBean();
			archive.setArchiveName(fileName);
			archive.setIsDir(OnOffEnum.OFF.value());
			archive.setFileSize((int) uploadFile.getSize());
			
			multipartList.add(new MultipartWrapper(archive, uploadFile));
		}
		
		return multipartList;
	}
}
