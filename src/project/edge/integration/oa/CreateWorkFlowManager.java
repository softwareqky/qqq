package project.edge.integration.oa;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.serializer.ValueFilter;

import cn.com.weaver.service.webservice.CreateWorkFlowPortTypeProxy;
import garage.origin.common.constant.Constants;
import garage.origin.common.constant.JsonStatus;
import garage.origin.common.util.DateUtils;
import garage.origin.domain.filter.CommonFilter;
import garage.origin.domain.view.JsonResultBean;
import project.edge.common.constant.FlowStatusEnum;
import project.edge.common.constant.SystemConfigKeys;
import project.edge.domain.entity.OaFlowHistory;
import project.edge.domain.entity.Project;
import project.edge.domain.entity.ProjectPerson;
import project.edge.service.flow.OaFlowHistoryService;
import project.edge.service.project.ProjectPersonChangeService;
import project.edge.service.project.ProjectPersonService;
import project.edge.service.project.ProjectService;
import project.edge.service.project.VirtualOrgService;
import project.edge.service.system.SystemConfigService;

@Component
public class CreateWorkFlowManager {
	private static final Logger logger = LoggerFactory.getLogger(CreateWorkFlowManager.class);

	private static final String REQ_SUCCESS = "0";
	private static final String REQ_FAIL = "1";
	@Resource
	private ProjectService projectService;
	@Resource
	private OaFlowHistoryService oaFlowHistoryService;
	@Resource
	private SystemConfigService systemConfigService;
	@Resource
	private ProjectPersonChangeService projectPersonChangeService;
	@Resource
	private VirtualOrgService virtualOrgService;
	@Resource
	private ProjectPersonService projectPersonService;
	
	public JsonResultBean reqOaAudit(String userName, String dataId, String creatorId, int oaType, String projectId,
									HashMap<String, Object> reqMap, HttpServletRequest request) {
		JsonResultBean jsonResult = new JsonResultBean();
		String rspJson = "";
		// 插入到t_oa_flow_history
		try {
			String hId = UUID.randomUUID().toString();
			OaFlowHistory oaFlowHistory = new OaFlowHistory();
			//oaFlowHistory.setCategory(FlowCategoryEnum.PROJECT_APPLICATION.value());
			oaFlowHistory.setCategory(oaType);
			oaFlowHistory.setcDatetime(new Date());
			oaFlowHistory.setCreator(userName);
			oaFlowHistory.setFlowAction(FlowStatusEnum.UNSUBMITTED.value());
			oaFlowHistory.setFlowDatetime(new Date());
			oaFlowHistory.setRelatedFormId(dataId);
			oaFlowHistory.setmDatetime(new Date());
			oaFlowHistory.setId(hId);
			oaFlowHistoryService.create(oaFlowHistory);
			
			String contextPath = request.getContextPath();
			String notifyPath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort()
					+ contextPath + "/";

			// 查询对应专业组
			String creatorVirtualOrgName = "";
			CommonFilter f1 = new CommonFilter();
			f1 = new CommonFilter().addExact("person.id", creatorId);
			if (!StringUtils.isEmpty(projectId)) {
				f1 = f1.addExact("project.id", projectId);
			}
			List<ProjectPerson> projectPersonList = this.projectPersonService.list(f1, null);
			if (projectPersonList != null && projectPersonList.size() > 0) {
				creatorVirtualOrgName = projectPersonList.get(0).getVirtualOrg().getVirtualOrgName();
			} else {
				logger.error("[OA Audit] Virtual Org Not Found. Project Id:{}", projectId);
			}
			
			// 查询project
			String projectNum = "";
			String projectName = "";
			Project projectEntity = projectService.find(projectId);
			if (projectEntity != null) {
				projectNum = projectEntity.getProjectNum();
				projectName = projectEntity.getProjectName();
			}
			
			// 添加OA共通参数
			reqMap.put("creatorId", creatorId);
			reqMap.put("type", String.valueOf(oaType));
			reqMap.put("dataId", hId);
			reqMap.put("callBackUrl", notifyPath + "oaAuditRes/notice.json");
			reqMap.put("createTime", DateUtils.date2String(new Date(), Constants.SIMPLE_DATE_TIME_FORMAT));
			reqMap.put("creatorVirtualOrg", creatorVirtualOrgName);
			if (!reqMap.containsKey("projectNum")) reqMap.put("projectNum", projectNum);
			if (!reqMap.containsKey("projectName")) reqMap.put("projectName", projectName);

			String reqJson = JSON.toJSONString(reqMap, filter);
			logger.info("[OA Audit] parameter: {}", reqJson);
			String oaUrl = "https://" + systemConfigService.getStringConfig(SystemConfigKeys.OA_IP) + ":"
			//		+ systemConfigService.getStringConfig(SystemConfigKeys.OA_PORT) + "/services/CreateWorkFlow";
					+ "443/services/CreateWorkFlow";
			//oaUrl = "https://oa.fnii.cn/services/CreateWorkFlow"; // TODO: 该URL为目前OA侧提供的测试服务器访问地址，后期以实际为准
			logger.info("[OA Audit] url: {}", oaUrl);
			CreateWorkFlowPortTypeProxy s = new CreateWorkFlowPortTypeProxy(oaUrl);
			rspJson = s.createWorkFlow(reqJson);
			JSONObject jsonObj = JSON.parseObject(rspJson);
			logger.info("[OA Audit] Response: {}", rspJson);
			if (jsonObj.getString("result").equals(REQ_SUCCESS)) {
				// 提交成功 需要将flowhistory action 修改为REVIEWING 审核中
				oaFlowHistory = oaFlowHistoryService.find(hId);
				oaFlowHistory.setFlowAction(FlowStatusEnum.REVIEWING.value());
				oaFlowHistoryService.update(oaFlowHistory);

				// 准备JSON结果
				jsonResult.setStatus(JsonStatus.OK);
				jsonResult.setMessage("请求成功，请耐心等待OA系统审批结果");
				jsonResult.getDataMap().put(JsonResultBean.KEY_RETURN_OBJECT, rspJson);
			} else {
				jsonResult.setStatus(JsonStatus.ERROR);
				jsonResult.setMessage(jsonObj.getString("message"));
				jsonResult.getDataMap().put(JsonResultBean.KEY_RETURN_OBJECT, rspJson);
			}

		} catch (Exception e) {

			logger.error("提交审批reqOaAudit中发生异常{}", e.getMessage());
			// 在JSON对象内设定服务器处理结果状态
			jsonResult.setStatus(JsonStatus.ERROR);
			e.printStackTrace();
		}
		return jsonResult;
	}
	
	private static ValueFilter filter = new ValueFilter() {
	    @Override
	    public Object process(Object obj, String s, Object v) {
	        if (v == null)
	            return "";
	        
	        if (v instanceof ArrayList) {
	            return v;
	        } else {
	        	return String.valueOf(v);
	        }
	    }
	};
}
