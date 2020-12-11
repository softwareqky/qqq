package project.edge.domain.business;

import org.springframework.web.multipart.MultipartFile;

import project.edge.domain.view.ArchiveBean;

/**
 * 上传的文件包装类。
 *
 */
public class MultipartWrapper {

    private MultipartFile file;
    private ArchiveBean archiveBean;

    public MultipartWrapper(ArchiveBean archiveBean, MultipartFile file) {
        this.archiveBean = archiveBean;
        this.file = file;
    }

    public MultipartFile getFile() {
        return this.file;
    }

    public void setFile(MultipartFile file) {
        this.file = file;
    }

    public ArchiveBean getArchiveBean() {
        return this.archiveBean;
    }

    public void setArchiveBean(ArchiveBean archiveBean) {
        this.archiveBean = archiveBean;
    }

}
