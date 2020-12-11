package project.edge.web.apiService.facility;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.stream.Collectors;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import garage.origin.common.constant.JsonStatus;
import garage.origin.common.constant.OnOffEnum;
import garage.origin.domain.business.OrderByDto;
import garage.origin.domain.business.PageCtrlDto;
import garage.origin.domain.filter.CommonFilter;
import garage.origin.domain.view.FieldBean;
import garage.origin.domain.view.JsonResultBean;
import garage.origin.domain.view.ViewBean;
import project.edge.domain.converter.FacilityHistoryBeanConverter;
import project.edge.domain.entity.DataFields;
import project.edge.domain.entity.FacilityHistory;
import project.edge.domain.entity.User;
import project.edge.service.auth.UserService;
import project.edge.service.facility.FacilityHistoryService;
import project.edge.service.system.DataFieldsService;

@Service
public class FacilityApiServiceImpl implements FacilityApiService {
	
	@Autowired
	private FacilityHistoryService historyService;
	
	@Autowired
	private DataFieldsService dataFieldsService;
	
	@Autowired
    private FacilityHistoryService siteHistoryService;
	
	@Autowired
	private UserService userService;
	
	@Resource
	protected MessageSource messageSource;

	@Override
	public void addHistoryRecord(String targetId, int targetType, int createType, String content, String userId) {
		
		User user = userService.find(userId);
		
		FacilityHistory history = new FacilityHistory();
		history.setContent(content);
		history.setCreateType(createType);
		history.setCreateBy(user.getPerson());
		history.setTargetId(targetId);
		history.setTargetType(targetType);
		history.setCreateAt(new Date());

		historyService.create(history);
	}

	@Override
	public String getUpdateContent(ViewBean prev, ViewBean next, String PID, String dataType, Locale locale) {
		
		List<String> contentList = new ArrayList<>();
		
		// 取得所有字段
		CommonFilter dataFieldsFilter = 
				new CommonFilter().addExact("dataType", dataType)
								  .addExact("pageId", PID)
								  .addExact("isEditVisible", OnOffEnum.ON.value());
		List<DataFields> updateFields = dataFieldsService.list(dataFieldsFilter, null);
		
		// 对于每个可能被更新的字段，逐个比较
		for (int i = 0; i < updateFields.size(); i++) {
			
			DataFields field = updateFields.get(i);
			FieldBean fieldBean = new FieldBean(field, this.messageSource, locale);
			
			// 取得前后有变化的字段，并保存
			String fieldName = fieldBean.getFieldName();
			if (fieldName.endsWith("_")) {
				fieldName = fieldName.substring(0, fieldName.length() - 1) + "Text";
				System.out.println(fieldName);
			}
			
			String prevValue = getReflectedValue(prev, fieldName);
			String nextValue = getReflectedValue(next, fieldName);
			
			System.out.println("【" + fieldBean.getCaptionName() + "】 前值=" + prevValue + " 现值=" + nextValue);
			
			if (!prevValue.equals(nextValue)) {
				contentList.add("【" + fieldBean.getCaptionName() + "】 " + prevValue + " -> " + nextValue);
			}
		}
		
		return contentList.stream().collect(Collectors.joining("\n"));
	}

	/**
	 * 取得反射的字段值
	 * @param obj
	 * @param fieldName
	 * @return
	 */
	private String getReflectedValue(Object obj, String fieldName) {
		
		try {
           String firstLetter = fieldName.substring(0, 1).toUpperCase();
           String getter = "get" + firstLetter + fieldName.substring(1);
           Method method = obj.getClass().getMethod(getter, new Class[] {});    
           Object value = method.invoke(obj, new Object[] {});  
           return value.toString();    
       } catch (Exception e) {    
           return "";    
       }     
	}

	@Override
	public JsonResultBean getHistoryList(CommonFilter filter, int page, int rows, String sort, String order,
			Locale locale) {
		
		JsonResultBean jsonResult = new JsonResultBean();
    	
    	try {
    		
    		// 设置分页信息
    		PageCtrlDto pageCtrl = new PageCtrlDto();
    		if (page > 0 && rows > 0) {
    			pageCtrl.setCurrentPage(page);
    			pageCtrl.setPageSize(rows);
    		}

    		// 设置排序信息
    		List<OrderByDto> orders = new ArrayList<OrderByDto>();
    		if (!StringUtils.isEmpty(sort)) {
    			String[] orderArray = StringUtils.commaDelimitedListToStringArray(order);
    			String[] sortArray = StringUtils.commaDelimitedListToStringArray(sort);
    			for (int i = 0; i < orderArray.length; i++) {

    				// 将bean的Text后缀的名字改为对应的entity的名字，即去掉Text后缀，规则见[t_data_fields].field_name_view
    				String beanSort = sortArray[i];
    				if (beanSort.endsWith("Text")) {
    					beanSort = beanSort.substring(0, beanSort.length() - 4);
    				}
    				orders.add(new OrderByDto(beanSort, orderArray[i].equalsIgnoreCase(OrderByDto.DESC)));
    			}
    		}

    		// 获取分页后的数据
    		List<FacilityHistory> list = siteHistoryService.list(filter, orders, pageCtrl);
    		List<ViewBean> resultList = new ArrayList<>();
    		for (FacilityHistory entity : list) {
    			FacilityHistoryBeanConverter beanConverter = new FacilityHistoryBeanConverter();
    			resultList.add(beanConverter.fromEntity(entity, this.messageSource, locale));
    		}
    		long total = pageCtrl.getRecordAmount();

    		// 准备JSON结果
    		jsonResult.setStatus(JsonStatus.OK);
    		jsonResult.getDataMap().put(JsonResultBean.KEY_EASYUI_TOTAL, total);// easyui-datagrid分页用
    		jsonResult.getDataMap().put(JsonResultBean.KEY_EASYUI_ROWS, resultList);// easyui-datagrid分页用
    		
    	} catch (Exception e) {
    		e.printStackTrace();
    		List<ViewBean> resultList = new ArrayList<>();
    		jsonResult.getDataMap().put(JsonResultBean.KEY_EASYUI_TOTAL, 0);// easyui-datagrid分页用
    		jsonResult.getDataMap().put(JsonResultBean.KEY_EASYUI_ROWS, resultList);// easyui-datagrid分页用
    	}
		
		return jsonResult;
	}
}
