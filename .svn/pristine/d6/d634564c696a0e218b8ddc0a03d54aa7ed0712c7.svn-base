package project.edge.web.controller.oa;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Locale;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.SessionAttributes;

import garage.origin.common.constant.Constants;
import garage.origin.common.constant.JsonStatus;
import garage.origin.domain.business.OrderByDto;
import garage.origin.domain.filter.CommonFilter;
import garage.origin.domain.view.JsonResultBean;
import garage.origin.domain.view.SessionUserBean;
import project.edge.domain.converter.OaFlowHistoryBeanConverter;
import project.edge.domain.entity.OaFlowHistory;
import project.edge.domain.view.OaFlowHistoryBean;
import project.edge.service.flow.OaFlowHistoryService;

@Controller
@SessionAttributes(Constants.SESSION_USER)
@RequestMapping("/oa/flowhistory")
public class OaFlowHistoryController {
	private static final Logger logger = LoggerFactory.getLogger(OaFlowHistoryController.class);
	
	@Resource
	protected MessageSource messageSource;
	
	@Resource
	private OaFlowHistoryService oaFlowHistoryService;
	
	@RequestMapping("/list")
    @ResponseBody
    public JsonResultBean list(String dataId,
            @ModelAttribute(Constants.SESSION_USER) SessionUserBean userBean, Locale locale) {
        JsonResultBean jsonResult = new JsonResultBean();
        try {
            CommonFilter filter = new CommonFilter();
            filter.addExact("relatedFormId", dataId);

            List<OrderByDto> orderList =
                    Collections.singletonList(new OrderByDto("cDatetime", false));

            List<OaFlowHistory> nodes =
                    this.oaFlowHistoryService.list(filter, orderList);

            List<OaFlowHistoryBean> resultBeans = new ArrayList<>();

            OaFlowHistoryBeanConverter converter = new OaFlowHistoryBeanConverter();
            for (OaFlowHistory node : nodes) {
                resultBeans.add(converter.fromEntity(node, messageSource, locale));
            }

            // 准备JSON结果
            jsonResult.setStatus(JsonStatus.OK);
            jsonResult.getDataMap().put(JsonResultBean.KEY_EASYUI_ROWS, resultBeans);

        } catch (Exception e) {
            logger.error("Exception list oa flow history.", e);

            // 在JSON对象内设定服务器处理结果状态
            jsonResult.setStatus(JsonStatus.ERROR);
        }

        return jsonResult;
    }
}
