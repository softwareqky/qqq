package project.edge.mobile.controller.project;

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
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.SessionAttributes;

import garage.origin.common.constant.Constants;
import garage.origin.common.constant.FieldValueType;
import garage.origin.common.constant.JsonStatus;
import garage.origin.common.util.DateUtils;
import garage.origin.domain.business.OrderByDto;
import garage.origin.domain.business.PageCtrlDto;
import garage.origin.domain.filter.CommonFilter;
import garage.origin.domain.view.MobileJsonResultBean;
import garage.origin.domain.view.SessionUserBean;
import project.edge.common.constant.FlowStatusEnum;
import project.edge.domain.entity.Project;
import project.edge.domain.entity.ProjectPerson;
import project.edge.domain.mobile.converter.ProjectApproveItemBeanConverter;
import project.edge.domain.mobile.converter.ProjectDetailBeanConverter;
import project.edge.domain.mobile.converter.ProjectListItemBeanConverter;
import project.edge.domain.mobile.converter.ProjectPersonBeanConverter;
import project.edge.domain.mobile.view.FieldListWrapperBean;
import project.edge.domain.mobile.view.PagedResultBean;
import project.edge.domain.mobile.view.ProjectListItemBean;
import project.edge.domain.mobile.view.RequestParamBean;
import project.edge.service.project.ProjectPersonService;
import project.edge.service.project.ProjectService;
import project.edge.service.system.DataOptionService;

/**
 * @author angel_000
 *         项目。
 */
@Controller
@SessionAttributes(Constants.SESSION_USER)
public class ProjectController {

    private static final Logger logger = LoggerFactory.getLogger(ProjectController.class);

    @Resource
    private MessageSource messageSource;

    @Resource
    private ProjectPersonService projectPersonService;

    @Resource
    private ProjectService projectService;

    @Resource
    private DataOptionService dataOptionService;
    
    /**
     * 待我审批项目列表。
     * 
     * @param locale
     * @return
     */
    @RequestMapping(value = "/project-approve", method = RequestMethod.POST)
    @ResponseBody
    public MobileJsonResultBean getProjectApproveList(
            @RequestParam(required = false, defaultValue = "") String projectName,
            @RequestParam(required = true, defaultValue = "1") int page,
            @RequestParam(required = true, defaultValue = "999999999") int rows, Model model,
            @ModelAttribute(Constants.SESSION_USER) SessionUserBean userBean, Locale locale) {

        MobileJsonResultBean jsonResult = new MobileJsonResultBean();
        try {

            String userid = userBean.getSessionUserId();

            // 过滤
            CommonFilter filter = null;
            filter = new CommonFilter().addExact("person.user.id", userid)
                    .addExact("project.flowStatusProject", FlowStatusEnum.REVIEWING.value());

            if (!StringUtils.isEmpty(projectName)) {
                filter.addExact("project.projectName", projectName);
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

            List<ProjectPerson> projectApproveList =
                    this.projectPersonService.list(filter, orders, pageCtrl);
            long total = pageCtrl.getRecordAmount();
            int count = projectApproveList.size();

            PagedResultBean<ProjectListItemBean> paged = new PagedResultBean<>();
            paged.setTotal(total);
            paged.setCount(count);
            jsonResult.setResult(paged);

            if (count > 0) {

                ProjectApproveItemBeanConverter converter = new ProjectApproveItemBeanConverter();
                for (ProjectPerson p : projectApproveList) {
                    paged.getRows()
                            .add(converter.fromEntity(p.getProject(), this.messageSource, locale));
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
     * 项目列表。
     * 
     * @param locale
     * @return
     */
    @RequestMapping(value = "/project-list",method = RequestMethod.POST)
    @ResponseBody
    public MobileJsonResultBean getProjectList(
            @RequestParam(required = false, defaultValue = "") String projectName,
            @RequestParam(required = false, defaultValue = "") String year,
            @RequestParam(required = false, defaultValue = "0") int sortType,
            @RequestBody RequestParamBean requestParamBean,
            @RequestParam(required = true, defaultValue = "1") int page,
            @RequestParam(required = true, defaultValue = "999999999") int rows, Model model,
            @ModelAttribute(Constants.SESSION_USER) SessionUserBean userBean, Locale locale) {

        MobileJsonResultBean jsonResult = new MobileJsonResultBean();
        try {

            String userid = userBean.getSessionUserId();

            // 过滤
            CommonFilter filter = new CommonFilter();

            filter.addExact("person.user.id", userid);

            if (!StringUtils.isEmpty(projectName)) {
                filter.addExact("project.projectName", projectName);
            }
            //分类
            if (requestParamBean != null && requestParamBean.getProjectKind() != null && requestParamBean.getProjectKind().size() > 0) {
                filter.addWithin("project.projectKind.id", requestParamBean.getProjectKind());
            }
            if (requestParamBean != null && requestParamBean.getProjectStatus() != null && requestParamBean.getProjectStatus().size() > 0) {
                filter.addWithin("project.projectStatus.id", requestParamBean.getProjectStatus());
            }
            if (requestParamBean != null && requestParamBean.getFlowStatusProject() != null && requestParamBean.getFlowStatusProject().size() > 0) {
                filter.addWithin("project.flowStatusProject", requestParamBean.getFlowStatusProject());
            }
            if (!StringUtils.isEmpty(year)) {
                 String fromText = String.format("%1$s-01-01 00:00:00",year);
                 String toText = String.format("%1$s-12-31 23:59:59", year);
                 filter.addRange("project.projectStartDate", FieldValueType.DATE,
                         DateUtils.string2Date(fromText, Constants.SIMPLE_DATE_TIME_FORMAT),
                         DateUtils.string2Date(toText, Constants.SIMPLE_DATE_TIME_FORMAT));
            }
       
//            filter.addRange(fName, vType, from, to)
//            if (year.size() > 0) {
//                filter.addWithin("year", year);
//            }
//            if (projectKind.size() > 0) {
//                filter.addWithin("projectKind", projectKind);
//            }
            // 设置分页信息
            PageCtrlDto pageCtrl = new PageCtrlDto();
            if (page > 0 && rows > 0) {
                pageCtrl.setCurrentPage(page);
                pageCtrl.setPageSize(rows);
            }
            
            // 设置排序信息
            List<OrderByDto> orders = new ArrayList<>();
            
            switch(sortType) {
            	case 1:
            		orders.add(new OrderByDto("cDatetime", true));
            		break;
            	case 2:
            		orders.add(new OrderByDto("cDatetime"));
            		break;
            	case 3:
            		orders.add(new OrderByDto("mDatetime", true));
            		break;
            	case 4:
            		orders.add(new OrderByDto("mDatetime"));
            		break;
            	default:
            		orders.add(new OrderByDto("cDatetime"));
            		break;
            }
            
            List<ProjectPerson> projectPersonList =
                    this.projectPersonService.list(filter, orders, pageCtrl);
            long total = pageCtrl.getRecordAmount();
            int count = projectPersonList.size();

            PagedResultBean<ProjectListItemBean> paged = new PagedResultBean<>();
            paged.setTotal(total);
            paged.setCount(count);
            jsonResult.setResult(paged);

            if (count > 0) {

                ProjectListItemBeanConverter converter = new ProjectListItemBeanConverter();
                for (ProjectPerson p : projectPersonList) {
                    paged.getRows().add(converter.fromEntity(p, this.messageSource, locale));
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
     * 项目详情。
     * 
     * @param locale
     * @return
     */
    @RequestMapping(value = "/project-detail", method = RequestMethod.POST)
    @ResponseBody
    public MobileJsonResultBean getProjectDetail(
            @RequestParam(required = true, defaultValue = "") String projectId, Model model,
            @ModelAttribute(Constants.SESSION_USER) SessionUserBean userBean, Locale locale) {

        MobileJsonResultBean jsonResult = new MobileJsonResultBean();

        try {

            // 参数检查
            if (StringUtils.isEmpty(projectId)) {
                return jsonResult.setErrorMessage("message.error.param", this.messageSource,
                        locale);
            }

            Project p = this.projectService.find(projectId);

            if (p != null) {

                ProjectDetailBeanConverter converter = new ProjectDetailBeanConverter();
                FieldListWrapperBean result = converter.fromEntity(p, this.messageSource, locale);

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


    /**
     * 项目成员。
     * 
     * @param locale
     * @return
     */
    @RequestMapping(value = "/project-persons", method = RequestMethod.POST)
    @ResponseBody
    public MobileJsonResultBean getProjectPersons(
            @RequestParam(required = true, defaultValue = "") String projectId,
            @RequestParam(required = false, defaultValue = "") String personName,
            @RequestParam(required = true, defaultValue = "1") int page,
            @RequestParam(required = true, defaultValue = "999999999") int rows, Model model,
            @ModelAttribute(Constants.SESSION_USER) SessionUserBean userBean, Locale locale) {

        MobileJsonResultBean jsonResult = new MobileJsonResultBean();

        try {

            // 参数检查
            if (StringUtils.isEmpty(projectId)) {
                return jsonResult.setErrorMessage("message.error.param", this.messageSource,
                        locale);
            }

            CommonFilter filter = null;

            filter = new CommonFilter().addExact("project.id", projectId);


            if (!StringUtils.isEmpty(personName)) {
                filter.addExact("person.personName", personName);
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

            List<ProjectPerson> projectPersonList =
                    this.projectPersonService.list(filter, orders, pageCtrl);
            long total = pageCtrl.getRecordAmount();
            int count = projectPersonList.size();

            PagedResultBean<FieldListWrapperBean> paged = new PagedResultBean<>();
            paged.setTotal(total);
            paged.setCount(count);
            jsonResult.setResult(paged);

            if (count > 0) {

                ProjectPersonBeanConverter converter = new ProjectPersonBeanConverter();
                for (ProjectPerson p : projectPersonList) {
                    paged.getRows().add(converter.fromEntity(p, this.messageSource, locale));
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

}
