/**
 * 
 */
package project.edge.domain.business;

import org.springframework.web.multipart.MultipartFile;

import project.edge.domain.view.KnowledgeBaseBean;

/**
 * 上传的知识库包装类。
 *
 */
public class MultipartWrapper2 {

    private MultipartFile file;
    private KnowledgeBaseBean knowledgeBaseBean;

    public MultipartWrapper2(KnowledgeBaseBean knowledgeBaseBean, MultipartFile file) {
        this.knowledgeBaseBean = knowledgeBaseBean;
        this.file = file;
    }

    public MultipartFile getFile() {
        return this.file;
    }

    public void setFile(MultipartFile file) {
        this.file = file;
    }

    
    public KnowledgeBaseBean getKnowledgeBaseBean() {
        return this.knowledgeBaseBean;
    }

    
    public void setKnowledgeBaseBean(KnowledgeBaseBean knowledgeBaseBean) {
        this.knowledgeBaseBean = knowledgeBaseBean;
    }

}
