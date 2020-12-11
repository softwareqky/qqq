package project.edge.web.controller.budget;

import java.math.BigDecimal;
import java.util.Locale;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.SessionAttributes;

import com.mysql.cj.util.StringUtils;

import garage.origin.common.constant.Constants;
import garage.origin.common.constant.JsonStatus;
import garage.origin.domain.view.JsonOaResultBean;
import garage.origin.domain.view.SessionUserBean;
import project.edge.domain.entity.BudgetFee;
import project.edge.service.budget.BudgetFeeService;

/**
 * @author wwy oa费用报销数据接口
 */
@Controller
@SessionAttributes(Constants.SESSION_USER)
public class BudgetFeeFromOaController {

	private static final Logger logger = LoggerFactory.getLogger(BudgetFeeFromOaController.class);

	@Resource
	private BudgetFeeService budgetFeeService;

	/**
	 * 获取oa费用报销数据。
	 * 
	 * @param locale
	 * @return
	 */
	@RequestMapping("/interface/oa-budget-fee")
	@ResponseBody
	public JsonOaResultBean getBudgetFee(@RequestParam(required = false, defaultValue = "") String bh, // 编号
			@RequestParam(required = false, defaultValue = "") String zymc, // 专业名称
			@RequestParam(required = false, defaultValue = "") String dw, // 单位
			@RequestParam(required = false, defaultValue = "") String sl, // 数量
			@RequestParam(required = false, defaultValue = "") String djy, // 单价（元）
			@RequestParam(required = false, defaultValue = "") String bxjey, // 金额（元）
			@RequestParam(required = false, defaultValue = "") String zdmc, // 站点名称
			@RequestParam(required = false, defaultValue = "") String fyxm, // 费用项目
			@RequestParam(required = false, defaultValue = "") String bz, // 备注
			@RequestParam(required = false, defaultValue = "") String mainid, // 主ID
			@ModelAttribute(Constants.SESSION_USER) SessionUserBean userBean, Locale locale) {

		JsonOaResultBean jsonResult = new JsonOaResultBean();
		try {

			BudgetFee entity = new BudgetFee();
			if (!StringUtils.isNullOrEmpty(bh)) {
				entity.setBh(bh);
			}
			if (!StringUtils.isNullOrEmpty(zymc)) {
				entity.setZymc(zymc);
			}
			if (!StringUtils.isNullOrEmpty(dw)) {
				entity.setDw(dw);
			}
			if (!StringUtils.isNullOrEmpty(sl)) {
				entity.setSl(Integer.parseInt(sl));
			}
			if (!StringUtils.isNullOrEmpty(djy)) {
				entity.setDjy(new BigDecimal(djy));
			}
			if (!StringUtils.isNullOrEmpty(bxjey)) {
				entity.setBxjey(new BigDecimal(bxjey));
			}
			if (!StringUtils.isNullOrEmpty(zdmc)) {
				entity.setZdmc(zdmc);
			}
			if (!StringUtils.isNullOrEmpty(fyxm)) {
				entity.setFyxm(fyxm);
			}
			if (!StringUtils.isNullOrEmpty(bz)) {
				entity.setBz(bz);
			}
			if (!StringUtils.isNullOrEmpty(mainid)) {
				entity.setMainid(Integer.parseInt(mainid));
			}
			budgetFeeService.create(entity);
			// 准备JSON结果
			jsonResult.setStatus(JsonStatus.OK);

		} catch (Exception e) {
			logger.error("Exception main.", e);
			// 在JSON对象内设定服务器处理结果状态
			jsonResult.setStatus(JsonStatus.ERROR);
		}

		return jsonResult;

	}
}
