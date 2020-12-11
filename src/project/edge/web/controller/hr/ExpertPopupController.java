package project.edge.web.controller.hr;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;

import garage.origin.common.constant.Constants;
import garage.origin.common.constant.OnOffEnum;
import garage.origin.domain.business.OrderByDto;
import garage.origin.domain.view.ComboboxOptionBean;
import garage.origin.domain.view.SessionUserBean;
import project.edge.common.constant.ControllerUrlMapKeys;
import project.edge.common.constant.DataTypeEnum;
import project.edge.domain.entity.Expert;
import project.edge.domain.view.ExpertBean;
import project.edge.service.system.DataOptionService;
import project.edge.web.controller.common.DoubleGridSelectorController;

@Controller
@SessionAttributes(Constants.SESSION_USER)
@RequestMapping("/hr/expert-selector")
public class ExpertPopupController extends DoubleGridSelectorController<Expert, ExpertBean> {

	private static final Logger logger = LoggerFactory.getLogger(PersonPopupController.class);

    private static final String PID = "P17011";
    
    @Resource
    private DataOptionService dataOptionService;
    
	@Override
	protected OrderByDto getGridDefaultOrder() {
		// TODO Auto-generated method stub
		return new OrderByDto("expertName");
	}

	@Override
	protected Logger getLogger() {
		// TODO Auto-generated method stub
		return logger;
	}

	@Override
	public String getDataType() {
		// TODO Auto-generated method stub
		return DataTypeEnum.EXPERT.value();
	}
    
	@Override
	protected String getPageId() {
		// TODO Auto-generated method stub
		return PID;
	}
	
	@Override
	public String getSideDataType() {
        return null;
    }
	
	@Override
    protected Map<String, String> prepareUrlMap() {

        Map<String, String> urlMap = super.prepareUrlMap();

        // 专家一览URL
        String contextPath = this.context.getContextPath();
        urlMap.put(ControllerUrlMapKeys.SUB_GRID_LIST, contextPath + "/hr/expert/list.json");

        return urlMap;
    }

	/**
     * 获取画面的各个下拉列表项，用于新增/修改/检索/批量修改画面。有下拉列表的子类必须重写此方法。
     * 
     * @param locale
     * @return key:[v_data_option].option_source，value:[v_data_option]
     */
    @Override
    protected Map<String, List<ComboboxOptionBean>> prepareOptionMap(Locale locale) {

        Map<String, List<ComboboxOptionBean>> optionMap =
                new HashMap<String, List<ComboboxOptionBean>>();

        ArrayList<ComboboxOptionBean> onOffMode = new ArrayList<ComboboxOptionBean>();
        for (OnOffEnum onoffEnum : OnOffEnum.values()) {
            onOffMode.add(new ComboboxOptionBean(onoffEnum.value().toString(),
                    messageSource.getMessage(onoffEnum.resourceName(), null, locale)));
        }
        optionMap.put("OnOff", onOffMode);

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
}
