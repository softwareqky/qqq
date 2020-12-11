package project.edge.web.controller.general;

import javax.annotation.Resource;

import org.apache.axis.utils.StringUtils;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

import garage.origin.common.constant.FieldValueType;
import garage.origin.domain.view.SessionUserBean;
import project.edge.domain.entity.ProjectPerson;
import project.edge.domain.entity.VirtualOrg;
import project.edge.service.project.ProjectPersonService;
import project.edge.service.project.VirtualOrgService;

@Service
public class DataPermissionService {
	
	@Resource
	ProjectPersonService projectPersonService;
	
	@Resource
	VirtualOrgService virtualOrgService;

	public String editFilterBySelfVirtualOrg(SessionUserBean userBean, String commonFilterJson) {
		
		if (userBean.getIsSuper()) {
			return commonFilterJson;
		}
		
		// 如果是领导所在项目组或管理员，默认取得所有项目组数据
		// 否则，只可取得本项目组数据
		ProjectPerson pPerson = projectPersonService.findByUserId(userBean.getSessionUserId());
		
		// 如果没有分配到任何Project，什么也查不出来
		if (pPerson == null) {
			commonFilterJson = this.getDataFilter("aabbcc");
		} 
		// 如果不是领导所在项目组，只能查询本项目组信息
		else if (!this.isLeaderVirtualOrg(pPerson.getVirtualOrg())) {
			
			VirtualOrg virtualOrg = pPerson.getVirtualOrg();
			
			// 如果已经有了筛选
			if (!StringUtils.isEmpty(commonFilterJson)) {
				JSONObject json = JSONObject.parseObject(commonFilterJson);
				JSONArray filterFieldList = json.getJSONArray("filterFieldList");
				
				// 如果设置了VirtualOrg且不是登录用户所属项目的Org，设置为一个虚拟项目ID（查不到任何数据）
				if (commonFilterJson.contains("virtualOrg_")) {
					for (int i = 0; i < filterFieldList.size(); i++) {
						JSONObject field = filterFieldList.getJSONObject(i);
						if (field.getString("fieldName").equalsIgnoreCase("virtualOrg_")) {
							if (!field.getString("value").equalsIgnoreCase(virtualOrg.getId())) {
								field.put("value", "aabbcc");
								field.put("valueType", FieldValueType.STRING);
							}
							break;
						}
					}
				}
				// 否则新增一个virtualOrg的筛选，筛选目标是
				else {
					JSONObject orgField = new JSONObject();
					orgField.put("fieldName", "virtualOrg_");
					orgField.put("value", virtualOrg.getId());
					orgField.put("valueType", FieldValueType.STRING);
					filterFieldList.add(orgField);
				}
				
				commonFilterJson = JSONObject.toJSONString(json);
			}
			// 如果没有任何筛选，添加关于virtualOrg的筛选
			else {
				commonFilterJson = this.getDataFilter(virtualOrg.getId());
			}
		}
		
		return commonFilterJson;
	}
	
	private boolean isLeaderVirtualOrg(VirtualOrg org) {
		
		if (org == null) return false;
		
		String name = org.getVirtualOrgName();
		if (name.equals("工程经理部") || name.equals("重大项目管理办公室")) {
			return true;
		}
		return false;
	}
	
	private String getDataFilter(String virtualOrgId) {
		
		JSONObject json = new JSONObject();
		JSONArray filterFieldList = new JSONArray();
		JSONObject orgField = new JSONObject();
		orgField.put("fieldName", "virtualOrg_");
		orgField.put("value", virtualOrgId);
		orgField.put("valueType", FieldValueType.STRING);
		filterFieldList.add(orgField);
		json.put("filterFieldList", filterFieldList);
		return JSONObject.toJSONString(json);
	}
}
