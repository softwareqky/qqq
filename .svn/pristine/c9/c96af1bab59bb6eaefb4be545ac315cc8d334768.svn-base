/**
 * 
 */
package project.edge.web.controller.archive;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.SessionAttributes;

import java.util.Map;

import javax.annotation.Resource;

import garage.origin.common.constant.Constants;
import garage.origin.domain.view.converter.ViewConverter;
import garage.origin.service.Service;
import project.edge.common.constant.DataTypeEnum;
import project.edge.domain.entity.KnowledgeBaseAuthority;
import project.edge.domain.view.KnowledgeBaseAuthorityBean;
import project.edge.service.archive.KnowledgeBaseAuthorityService;
import project.edge.web.controller.common.TreeGridController;


/**
 * @author angel_000
 *         知识库权限画面。
 */
@Controller
@SessionAttributes(Constants.SESSION_USER)
@RequestMapping("/archive/knowledge-base-auth")
public class KnowledgeBaseAuthorityController
        extends TreeGridController<KnowledgeBaseAuthority, KnowledgeBaseAuthorityBean> {

    private static final Logger logger =
            LoggerFactory.getLogger(KnowledgeBaseAuthorityController.class);

    @Resource
    private KnowledgeBaseAuthorityService knowledgeBaseAuthorityService;

    @Override
    protected Logger getLogger() {
        return logger;
    }

    @Override
    public String getDataType() {
        return DataTypeEnum.KNOWLEDGE_BASE_AUTHORITY.value();
    }

    @Override
    protected Service<KnowledgeBaseAuthority, String> getDataService() {
        return this.knowledgeBaseAuthorityService;
    }

    @Override
    protected String getPageId() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    protected ViewConverter<KnowledgeBaseAuthority, KnowledgeBaseAuthorityBean> getViewConverter() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    protected String getMainView(Map<String, String> paramMap) {
        return null;
    }

    @Override
    protected String getJavascriptJspPath() {
        return "/WEB-INF/jsp/archive/knowledgeBaseJs.jsp";
    }

    @Override
    protected String getHiddenContentJspPath() {
        return "/WEB-INF/jsp/archive/knowledgeBaseHidden.jsp";
    }

}
