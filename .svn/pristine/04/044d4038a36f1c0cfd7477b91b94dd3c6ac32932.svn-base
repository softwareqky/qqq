package project.edge.web.controller.bidding;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.SessionAttributes;

import garage.origin.common.constant.Constants;
import garage.origin.common.constant.JsonStatus;
import garage.origin.common.constant.OnOffEnum;
import garage.origin.common.util.DateUtils;
import garage.origin.domain.filter.CommonFilter;
import garage.origin.domain.view.ComboboxOptionBean;
import garage.origin.domain.view.JsonResultBean;
import garage.origin.domain.view.SessionUserBean;
import garage.origin.domain.view.converter.ViewConverter;
import garage.origin.service.Service;
import project.edge.common.SysLogAnnotation;
import project.edge.common.constant.DataTypeEnum;
import project.edge.common.constant.OAAuditApiType;
import project.edge.domain.converter.PrequalificationBeanConverter;
import project.edge.domain.entity.DataOption;
import project.edge.domain.entity.Prequalification;
import project.edge.domain.view.PrequalificationBean;
import project.edge.integration.oa.CreateWorkFlowManager;
import project.edge.service.bidding.PrequalificationService;
import project.edge.service.system.DataOptionService;
import project.edge.web.controller.common.TreeGridUploadController;

/**
 * 项目预审画面
 *
 */
@Controller
@SessionAttributes(Constants.SESSION_USER)
@RequestMapping("/prequalification")
public class PrequalificationController
        extends TreeGridUploadController<Prequalification, PrequalificationBean> {

    private static final Logger logger = LoggerFactory.getLogger(PrequalificationController.class);

    private static final String PID = "P303010";
    @Resource
	CreateWorkFlowManager createWorkFlowManager;
	@Autowired
	HttpServletRequest request;
    @Resource
    private PrequalificationService prequalificationService;

    @Resource
    private DataOptionService dataOptionService;
    
    @Override
    protected Logger getLogger() {
        return logger;
    }

    @Override
    public String getDataType() {
        return DataTypeEnum.PREQUALIFICATION.value();
    }

    @Override
    protected Service<Prequalification, String> getDataService() {
        return this.prequalificationService;
    }

    @Override
    protected String getPageId() {
        return PID;
    }
    
    @Override
    protected boolean useFile() {
        return false;
    }

    @Override
    protected ViewConverter<Prequalification, PrequalificationBean> getViewConverter() {
        return new PrequalificationBeanConverter();
    }

    /**
     * 获取画面的各个下拉列表项，用于新增/修改/检索/批量修改画面。有下拉列表的子类必须重写此方法。
     * 
     * @param locale
     * @return key:[v_data_option].option_source，value:[v_data_option]
     */
    @Override
    protected Map<String, List<ComboboxOptionBean>> prepareOptionMap(Locale locale) {

        Map<String, List<ComboboxOptionBean>> optionMap = new HashMap<>();

        List<String> dataTypeList = new ArrayList<>();
        dataTypeList.add(DataTypeEnum.PROJECT_KIND.value()); // 项目类别

        CommonFilter f = new CommonFilter().addWithin("dataType", dataTypeList);

        // 项目类别
        List<ComboboxOptionBean> projectKindOptions = new ArrayList<>();

        List<DataOption> list = this.dataOptionService.list(f, null);

        for (DataOption o : list) {
            if (o.getDataType().equals(DataTypeEnum.PROJECT_KIND.value())) {
                // 屏蔽复合类项目
                if (!o.getOptionName().equals("复合类项目")) {
                    projectKindOptions.add(new ComboboxOptionBean(o.getId(), o.getOptionName()));
                }

            }
        }

        optionMap.put("ProjectKindOptions", projectKindOptions);

        return optionMap;

    }
    
    /**
     * 画面Open的入口方法，用于生成JSP。
     * 
     * @param paramMap 画面请求中的任何参数，都会成为检索的字段
     * @param model model
     * @param userBean session中的当前登录的用户信息
     * @param locale locale
     * @return
     */
    @RequestMapping("/main")
    public String main(@RequestParam Map<String, String> paramMap, Model model,
            @ModelAttribute(Constants.SESSION_USER) SessionUserBean userBean, Locale locale) {

        return super.main(paramMap, model, userBean, locale);
    }

    /**
     * 获取一览显示的数据，返回JSON格式。
     * 
     * @param commonFilterJson JSON字符串形式的过滤条件
     * @param page 页数
     * @param rows 每页的记录数
     * @param sort 排序的字段，CSV
     * @param order 顺序，CSV
     * @param locale
     * @return
     */
    @RequestMapping("/list")
    @ResponseBody
    public JsonResultBean list(
            @RequestParam(required = false, defaultValue = "") String commonFilterJson,
            @RequestParam(required = false, defaultValue = "1") int page,
            @RequestParam(required = false, defaultValue = "999999999") int rows,
            @RequestParam(required = false, defaultValue = "") String sort,
            @RequestParam(required = false, defaultValue = "asc") String order, Locale locale) {

        JsonResultBean jsonResult =
                super.list(commonFilterJson, null, page, rows, sort, order, locale);

        return jsonResult;
    }

    /**
     * 查找单条记录，返回Json格式。
     * 
     * @param id ID
     * @param locale
     * @return
     */
    @RequestMapping("/find")
    @ResponseBody
    @Override
    public JsonResultBean find(@RequestParam String id, Locale locale) {
        return super.find(id, locale);
    }

    /**
     * 新建，返回Json格式。
     * 
     * @param bean 表现层对象
     * @param userBean Session中的用户信息
     * @param locale
     * @return
     */
    @RequestMapping("/add")
    @ResponseBody
    @SysLogAnnotation(description = "项目预审", action = "新增")
    public JsonResultBean create(@ModelAttribute PrequalificationBean bean,
            @ModelAttribute(Constants.SESSION_USER) SessionUserBean userBean, Locale locale) {
        return super.create(bean, null, userBean, locale);
    }

    /**
     * 修改，返回Json格式。
     * 
     * @param bean 表现层对象
     * @param userBean Session中的用户信息
     * @param locale
     * @return
     */
    @RequestMapping("/edit")
    @ResponseBody
    @SysLogAnnotation(description = "项目预审", action = "更新")
    public JsonResultBean update(@ModelAttribute PrequalificationBean bean,
            @ModelAttribute(Constants.SESSION_USER) SessionUserBean userBean, Locale locale) {
        return super.update(bean, null, userBean, locale);
    }

    /**
     * 删除，返回Json格式。
     * 
     * @param idsToDelete 待删除的ID，CSV
     * @param userBean Session中的用户信息
     * @param locale
     * @return
     */
    @RequestMapping("/delete")
    @ResponseBody
    @SysLogAnnotation(description = "项目预审", action = "删除")
    public JsonResultBean delete(@RequestParam String idsToDelete,
            @ModelAttribute(Constants.SESSION_USER) SessionUserBean userBean, Locale locale) {
        return super.delete(idsToDelete, null, userBean, locale);
    }
    
    /**
	 * 管理平台提交审核
	 * 
	 * @param id
	 *            提交审核记录ID
	 * @param locale
	 * @return
	 */
	@RequestMapping("/auditSubmit")
	@ResponseBody
	@SysLogAnnotation(description = "项目预审", action = "审核")
	public JsonResultBean auditSubmit(@ModelAttribute(Constants.SESSION_USER) SessionUserBean userBean,
										@RequestParam String id, Locale locale) {
		JsonResultBean jsonResult = new JsonResultBean();

		Prequalification entity = this.getDataService().find(id);

		if (entity == null) {
			jsonResult.setStatus(JsonStatus.ERROR);
			jsonResult.setMessage(this.messageSource.getMessage("message.error.record.not.exist", null, locale));
			return jsonResult;
		}
		String dataId = entity.getId();
		String userName=userBean.getSessionLoginName();
		String creatorId = userBean.getSessionUserId();
		int oaType = OAAuditApiType.OA_TYPE_PREQUAF.value();

		// 向OA提交审核请求

		HashMap<String, Object> reqMap = new HashMap<>();
		reqMap.put("personName", entity.getApplicant().getUser().getId());
		reqMap.put("applicantDate", DateUtils.date2String(entity.getApplicantDate(), Constants.DATE_FORMAT));
		if (entity.getProject() != null) {
			reqMap.put("projectName", entity.getProject().getProjectName());
		} else {
			reqMap.put("projectName", "");
		}
		
		if (entity.getProjectKind() != null) {
			reqMap.put("optionName", entity.getProjectKind().getOptionName());
		} else {
			reqMap.put("optionName", "");
		}
		reqMap.put("constructionUnit", entity.getConstructionUnit());
		if (entity.getIsBusinessSupport() != null) {
			reqMap.put("isBusinessSupport", entity.getIsBusinessSupport() == 1
	                		? messageSource.getMessage(OnOffEnum.ON.resourceName(), null, locale)
	                        : messageSource.getMessage(OnOffEnum.OFF.resourceName(), null, locale));
		} else {
			reqMap.put("isBusinessSupport", "");
		}
		
		reqMap.put("headman", entity.getHeadman());
		reqMap.put("closingDate", DateUtils.date2String(entity.getClosingDate(), Constants.DATE_FORMAT));
		reqMap.put("pretrialDate", DateUtils.date2String(entity.getPretrialDate(), Constants.DATE_FORMAT));
		reqMap.put("qualificationAuditDeposit", entity.getQualificationAuditDeposit()+"");
		reqMap.put("registrationDate", DateUtils.date2String(entity.getRegistrationDate(), Constants.DATE_FORMAT));
		reqMap.put("prequalificationInformation", entity.getPrequalificationInformation());
		reqMap.put("remark", entity.getRemark());
		reqMap.put("specialCaseDescription", entity.getSpecialCaseDescription());

		String projectId = "";
		if (entity.getProject() != null) projectId = entity.getProject().getId();
		
		logger.info("[OA Audit] No.7 Prequalification");
		// 调用oa接口
		jsonResult = createWorkFlowManager.reqOaAudit(userName, dataId, creatorId, oaType, projectId, reqMap, request);
		if (jsonResult.getStatus() == JsonStatus.OK) {
			logger.info("[OA Audit] No.7 Prequalification Audit request success, history data id was " + dataId);
			//Prequalification preq = this.getDataService().find(id);
			//preq.setFlowStartDate(new Date());
			//preq.setFlowStatus(FlowStatusEnum.REVIEWING.value());
			//preq.setmDatetime(new Date());
			//preq.setModifier(userName);
			//this.getDataService().update(preq);
		}

		return jsonResult;
	}
}
