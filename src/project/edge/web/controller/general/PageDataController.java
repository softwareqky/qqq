package project.edge.web.controller.general;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.SessionAttributes;

import garage.origin.common.constant.Constants;
import garage.origin.common.constant.JsonStatus;
import garage.origin.common.constant.OnOffEnum;
import garage.origin.domain.filter.CommonFilter;
import garage.origin.domain.view.FieldBean;
import garage.origin.domain.view.JsonResultBean;
import garage.origin.domain.view.SessionUserBean;
import project.edge.domain.entity.DataFields;
import project.edge.service.system.DataFieldsService;

@Controller
@SessionAttributes(Constants.SESSION_USER)
@RequestMapping("/page-data")
public class PageDataController {

	private static final Logger logger = LoggerFactory.getLogger(PageDataController.class);
	
	@Autowired
	private DataFieldsService dataFieldsService;
	
	@Resource
	protected MessageSource messageSource;
	
	@RequestMapping("/data-fields")
	@ResponseBody
	public JsonResultBean getDataFields(
			@RequestParam(required = true, defaultValue = "") String pageId,
			@ModelAttribute(Constants.SESSION_USER) SessionUserBean userBean, 
			Locale locale) {
		
		JsonResultBean jsonResult = new JsonResultBean();
		
		CommonFilter filter = new CommonFilter();
		filter.addExact("pageId", pageId)
				  .addExact("isEnabled", OnOffEnum.ON.value())
				  .addExact("isCommonVisible", OnOffEnum.ON.value());
		List<DataFields> fields = this.dataFieldsService.list(filter, null);
		List<FieldBean> beanList = new ArrayList<>();
		for (DataFields field: fields) {
			FieldBean fieldBean = new FieldBean(field, messageSource, locale);
			beanList.add(fieldBean);
		}
		
		
		jsonResult.setStatus(JsonStatus.OK);
		jsonResult.getDataMap().put(JsonResultBean.KEY_EASYUI_ROWS, beanList);
		
		return jsonResult;
	}
}
