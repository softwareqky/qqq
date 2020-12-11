package project.edge.mobile.controller.quality;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.SessionAttributes;

import garage.origin.common.constant.Constants;
import garage.origin.common.constant.JsonStatus;
import garage.origin.domain.business.OrderByDto;
import garage.origin.domain.business.PageCtrlDto;
import garage.origin.domain.filter.CommonFilter;
import garage.origin.domain.view.MobileJsonResultBean;
import garage.origin.domain.view.SessionUserBean;
import project.edge.domain.entity.Issue;
import project.edge.domain.entity.ProjectPerson;
import project.edge.domain.mobile.converter.IssueInfoBeanConverter;
import project.edge.domain.mobile.converter.IssueListItemBeanConverter;
import project.edge.domain.mobile.converter.QualityIssueListItemBeanConverter;
import project.edge.domain.mobile.view.FieldListWrapperBean;
import project.edge.domain.mobile.view.IssueListItemBean;
import project.edge.domain.mobile.view.PagedResultBean;
import project.edge.domain.mobile.view.QualityIssueListItemBean;
import project.edge.service.project.ProjectPersonService;
import project.edge.service.quality.IssueService;

/**
 * @author angel_000
 *         问题风险。
 */
@Controller
@SessionAttributes(Constants.SESSION_USER)
public class QualityIssueController {

    private static final Logger logger = LoggerFactory.getLogger(QualityIssueController.class);

    @Resource
    private MessageSource messageSource;

    @Resource
    private IssueService issueService;

    @Resource
    private ProjectPersonService projectPersonService;

    /**
     * 问题风险项目列表。
     * 
     * @param locale
     * @return
     */
    @RequestMapping(value = "/quality/project-list", method = RequestMethod.POST)
    @ResponseBody
    public MobileJsonResultBean getQualityIssueList(
            @RequestParam(required = false, defaultValue = "") String projectName,
            @RequestParam(required = true, defaultValue = "1") int page,
            @RequestParam(required = true, defaultValue = "999999999") int rows, Model model,
            @ModelAttribute(Constants.SESSION_USER) SessionUserBean userBean, Locale locale) {

        MobileJsonResultBean jsonResult = new MobileJsonResultBean();
        try {

            String userid = userBean.getSessionUserId();

            // 过滤
            CommonFilter filter = null;
            filter = new CommonFilter().addExact("person.user.id", userid);

            if (!StringUtils.isEmpty(projectName)) {
                filter.addExact("project.projectName", projectName);
            }

            List<ProjectPerson> projectPersonList = this.projectPersonService.list(filter, null);

            List<String> uniqueKeys = new ArrayList<>();

            if (projectPersonList != null) {
                for (ProjectPerson p : projectPersonList) {
                    uniqueKeys.add(p.getProject().getId());
                }
            }

            filter = new CommonFilter().addWithin("pj.id", uniqueKeys);

            // 设置分页信息
            PageCtrlDto pageCtrl = new PageCtrlDto();
            if (page > 0 && rows > 0) {
                pageCtrl.setCurrentPage(page);
                pageCtrl.setPageSize(rows);
            }

            // 设置排序信息
            List<OrderByDto> orders = new ArrayList<>();
            orders.add(new OrderByDto("cDatetime"));

            List<Issue> issueList = this.issueService.list(filter, orders, pageCtrl);
            long total = pageCtrl.getRecordAmount();
            int count = issueList.size();

            PagedResultBean<QualityIssueListItemBean> paged = new PagedResultBean<>();
            paged.setTotal(total);
            paged.setCount(count);
            jsonResult.setResult(paged);

            if (count > 0) {

                QualityIssueListItemBeanConverter converter =
                        new QualityIssueListItemBeanConverter();
                for (Issue issue : issueList) {
                    paged.getRows().add(converter.fromEntity(issue, this.messageSource, locale));
                }
            }

            // 准备JSON结果
            jsonResult.setStatus(JsonStatus.OK);

        } catch (Exception e) {
            logger.error("Exception while getting the settings.", e);

            // 在JSON对象内设定服务器处理结果状态
            jsonResult.setErrorMessage("message.error.remote.server", this.messageSource, locale);
        }

        return jsonResult;

    }

    /**
     * 验收信息项目验收列表。
     * 
     * @param locale
     * @return
     */
    @RequestMapping(value = "/quality/issue-list", method = RequestMethod.POST)
    @ResponseBody
    public MobileJsonResultBean getIssueList(
            @RequestParam(required = true, defaultValue = "") String projectId,
            @RequestParam(required = true, defaultValue = "1") int page,
            @RequestParam(required = true, defaultValue = "999999999") int rows, Model model,
            @ModelAttribute(Constants.SESSION_USER) SessionUserBean userBean, Locale locale) {

        MobileJsonResultBean jsonResult = new MobileJsonResultBean();
        try {

            // 过滤
            CommonFilter filter = null;
            filter = new CommonFilter();

            if (!StringUtils.isEmpty(projectId)) {
                filter.addExact("pj.id", projectId);
            }

            // 设置分页信息
            PageCtrlDto pageCtrl = new PageCtrlDto();
            if (page > 0 && rows > 0) {
                pageCtrl.setCurrentPage(page);
                pageCtrl.setPageSize(rows);
            }

            // 设置排序信息
            List<OrderByDto> orders = new ArrayList<>();
            orders.add(new OrderByDto("cDatetime"));

            List<Issue> issueList = this.issueService.list(filter, orders, pageCtrl);
            long total = pageCtrl.getRecordAmount();
            int count = issueList.size();

            PagedResultBean<IssueListItemBean> paged = new PagedResultBean<>();
            paged.setTotal(total);
            paged.setCount(count);
            jsonResult.setResult(paged);

            if (count > 0) {

                IssueListItemBeanConverter converter = new IssueListItemBeanConverter();
                for (Issue issue : issueList) {
                    paged.getRows().add(converter.fromEntity(issue, this.messageSource, locale));
                }
            }

            // 准备JSON结果
            jsonResult.setStatus(JsonStatus.OK);

        } catch (Exception e) {
            logger.error("Exception while getting the settings.", e);

            // 在JSON对象内设定服务器处理结果状态
            jsonResult.setErrorMessage("message.error.remote.server", this.messageSource, locale);
        }

        return jsonResult;

    }

    /**
     * 问题风险详情。
     * 
     * @param locale
     * @return
     */
    @RequestMapping(value = "/quality/issue-info", method = RequestMethod.POST)
    @ResponseBody
    public MobileJsonResultBean getAcceptanceInfo(
            @RequestParam(required = true, defaultValue = "") String issueId, Model model,
            @ModelAttribute(Constants.SESSION_USER) SessionUserBean userBean, Locale locale) {

        MobileJsonResultBean jsonResult = new MobileJsonResultBean();
        try {

            // 过滤
            // 参数检查
            if (StringUtils.isEmpty(issueId)) {
                return jsonResult.setErrorMessage("message.error.param", this.messageSource,
                        locale);
            }

            Issue issue = this.issueService.find(issueId);

            if (issue != null) {

                IssueInfoBeanConverter converter = new IssueInfoBeanConverter();
                FieldListWrapperBean result =
                        converter.fromEntity(issue, this.messageSource, locale);

                jsonResult.setResult(result);
            }

            // 准备JSON结果
            jsonResult.setStatus(JsonStatus.OK);

        } catch (Exception e) {
            logger.error("Exception while getting the settings.", e);

            // 在JSON对象内设定服务器处理结果状态
            jsonResult.setErrorMessage("message.error.remote.server", this.messageSource, locale);
        }

        return jsonResult;

    }

}
