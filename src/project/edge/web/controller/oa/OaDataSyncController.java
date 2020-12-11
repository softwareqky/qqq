package project.edge.web.controller.oa;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import garage.origin.common.constant.JsonStatus;
import garage.origin.domain.view.JsonResultBean;
import liquibase.util.StringUtils;
import project.edge.service.bidding.PurchaseOrderService;
import project.edge.service.budget.BudgetFeeService;
import project.edge.service.contract.PaymentContractService;

@RestController
@RequestMapping("/oa/data")
public class OaDataSyncController {
	private static final Logger logger = LoggerFactory.getLogger(OaDataSyncController.class);
	
	private static final String KIND_PURCHASE = "1"; // 申购信息
	private static final String KIND_TODO = "2"; // 待办事项
	private static final String KIND_CONTRACT_INFO = "3"; // 合同信息
	private static final String KIND_CONTRACT_LIST = "4"; // 合同台账
	private static final String KIND_CONTRACT_STATEMENT = "5"; // 合同结算
	private static final String KIND_CONTRACT_REPORT = "6"; // 合同报表
	private static final String KIND_EXPENSES = "7"; // 费用报销
	
	@Resource
	private PurchaseOrderService purchaseOrderService;
	
	@Resource
	private BudgetFeeService budgetFeeService;
	
	@Resource
	private PaymentContractService paymentContractService;
	
	@RequestMapping(value = "/sync", method = RequestMethod.POST)
	@ResponseBody
	public JsonResultBean synchronization(@RequestParam(required = true) String kind, @RequestParam(required = true) String data) {
		logger.info("[OA Data Sync] Receive sync call from oa. kind:" + kind);
		logger.info("[OA Data Sync] data:" + data);
		try {
			data = URLDecoder.decode(data, "utf-8");
			//logger.info("[OA Data Sync] data URL decoder:" + data);
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			//e.printStackTrace();
			logger.error("[OA Data Sync] data URL decoder Error. erro msg:" + e.getMessage());
		}
		
		JsonResultBean result = new JsonResultBean();

		if (StringUtils.isNotEmpty(data)) {
			if (StringUtils.isEmpty(kind) || StringUtils.isEmpty(data)) {
				result.setStatus(JsonStatus.ERROR);
				result.setMessage("Data format error.");
			} else {
				if (KIND_PURCHASE.equals(kind)) {
					result = purchaseOrderService.parseSyncData(data);
				} else if (KIND_TODO.equals(kind)) {
					
				} else if (KIND_CONTRACT_INFO.equals(kind)) {
					result = paymentContractService.parseSyncData(data);
				} else if (KIND_CONTRACT_STATEMENT.equals(kind)) {
					
				} else if (KIND_CONTRACT_REPORT.equals(kind)) {
					
				} else if (KIND_EXPENSES.equals(kind)) {
					result = budgetFeeService.parseSyncData(data);
				} else {
					result.setStatus(JsonStatus.ERROR);
					result.setMessage("Data kind is wrong.");
				}
			}
		}
		
		return result;
	}
}
