package project.edge.web.apiService.facility;

import java.util.Locale;

import garage.origin.domain.filter.CommonFilter;
import garage.origin.domain.view.JsonResultBean;
import garage.origin.domain.view.ViewBean;


public interface FacilityApiService {

	// 添加历史操作记录
	public void addHistoryRecord(String targetId, int targetType, int createType, String content, String userId);
	
	// 取得更新的内容
	public String getUpdateContent(ViewBean prev, ViewBean next, String PID, String dataType, Locale locale);
	
	// 取得历史记录
	public JsonResultBean getHistoryList(CommonFilter filter, int page, int rows, String sort, String order, Locale locale);
}