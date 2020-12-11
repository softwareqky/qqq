package project.edge.service.budget;

import java.math.BigDecimal;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.apache.http.client.utils.DateUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

import garage.origin.common.constant.Constants;
import garage.origin.common.constant.JsonStatus;
import garage.origin.dao.Dao;
import garage.origin.domain.business.OrderByDto;
import garage.origin.domain.filter.CommonFilter;
import garage.origin.domain.view.JsonResultBean;
import garage.origin.service.GenericServiceImpl;
import project.edge.common.constant.OaProcessConstant;
import project.edge.dao.budget.BudgetFeeDao;
import project.edge.dao.budget.BudgetMainfeeDao;
import project.edge.dao.hr.PersonDao;
import project.edge.dao.project.VirtualOrgDao;
import project.edge.domain.entity.BudgetFee;
import project.edge.domain.entity.BudgetMainfee;
import project.edge.domain.entity.Person;
import project.edge.domain.entity.PurchaseOrder;
import project.edge.domain.entity.VirtualOrg;
import project.edge.service.bidding.PurchaseOrderService;

/**
 * 
 * @author zjy
 *[t_budget_fee]对应的 Service。
 */
@Service
public class BudgetFeeServiceImpl extends GenericServiceImpl<BudgetFee, String> implements BudgetFeeService {

	private static final Logger logger = LoggerFactory.getLogger(BudgetFeeServiceImpl.class);
	
    @Resource
    private BudgetFeeDao budgetFeeDao;
    
    @Resource
    private BudgetMainfeeDao budgetMainfeeDao;
    
    @Resource
    private PersonDao personDao;
    
    @Resource
    private VirtualOrgDao virtualOrgDao;
    
    @Resource
    private PurchaseOrderService purchaseOrderService;
    
    @Override
    public Dao<BudgetFee, String> getDefaultDao() {
        // TODO Auto-generated method stub
        return this.budgetFeeDao;
    }

    @Override
    public OrderByDto getDefaultOrder() {
        return new OrderByDto("bh", false);
    }
    
    @Override
    @Transactional(readOnly=true)
    public List<Object> getBudgetFeeList(String startDate, String endDate) {
    	return this.budgetFeeDao.getBudgetFeeList(startDate, endDate);
    }
    
    @Override
    @Transactional(readOnly=true)
    public List<Object> getFeeBySite(String startDate, String endDate) {
    	return this.budgetFeeDao.getFeeBySite(startDate, endDate);
    }
    
    @Override
    @Transactional(readOnly=true)
    public List<Object> getSiteNameList(String startDate, String endDate) {
    	return this.budgetFeeDao.getSiteNameList(startDate, endDate);
    }
    
    @Override
    @Transactional(readOnly=true)
    public List<Object> getFeeByYearAndCode(String year) {
    	return this.budgetFeeDao.getFeeByYearAndCode(year);
    }

	@Transactional
    public JsonResultBean parseSyncData(String data) {
		JsonResultBean result = new JsonResultBean();
		
		JSONObject dataArrayObj = JSONObject.parseObject(data);
		Date now = new Date();
		
		String extId = dataArrayObj.getString("mainid");
		if (StringUtils.isEmpty(extId)) {
			logger.error("[OA Data Sync] extId is not exist.");
			result.setStatus(JsonStatus.ERROR);
			result.setMessage("mainid is null.");
			return result;
		}
		
		// 确认同步类型
		CommonFilter f = new CommonFilter();
		f.addExact("mainid", Integer.valueOf(extId));
		List<OrderByDto> orders = new ArrayList<OrderByDto>();
		orders.add(new OrderByDto("cDatetime"));
		List<BudgetFee> list = budgetFeeDao.list(f, orders);
		if (list.size() < 1) {
			// 新增同步流程
			//budgetFee.setMainid(Integer.valueOf(extId));
		} else {
			// 更新同步流程
			// OA侧确认结果：不会出现更新的可能
			//              流程归档 就不能改数据
			//purchaseOrder = list.get(0);
			//f = new CommonFilter().addExact("purchaseOrder.id", purchaseOrder.getId());
			result.setStatus(JsonStatus.ERROR);
			result.setMessage("update is not support.");
			return result;
		}
		
		String requestPersonId = dataArrayObj.getString("sqr");
		String requestTime = dataArrayObj.getString("createTime");
		String virtualOrgId = dataArrayObj.getString("gzz");
		String workflowname = dataArrayObj.getString("workflowname");
		String approvalId = dataArrayObj.getString("approvelId");
		String approver = dataArrayObj.getString("approver");
		String approvalTime = dataArrayObj.getString("approvalTime");
		String payRatio = dataArrayObj.getString("fkbl");
		String payAmount = dataArrayObj.getString("fkje");
		String payType = dataArrayObj.getString("fklx");
		String purchaseNo = dataArrayObj.getString("sgd");
		String stockNo = dataArrayObj.getString("rckd");
		
		BudgetMainfee budgetMainfee = new BudgetMainfee();
		budgetMainfee.setMainid(extId);
		if (!StringUtils.isEmpty(requestPersonId)) {
			Person person = personDao.find(requestPersonId);
			if (person != null) {
				budgetMainfee.setRequestPerson(person);
			}
		}

		if (!StringUtils.isEmpty(virtualOrgId)) {
			CommonFilter filter = new CommonFilter();
			filter.addExact("virtualOrgName", virtualOrgId);
			List<VirtualOrg> vList = virtualOrgDao.list(filter, null);
			if (vList != null && vList.size()>0) {
				budgetMainfee.setVirtualOrg(vList.get(0));
			}
		}
		
		if (!StringUtils.isEmpty(approver)) {
			Person person = personDao.find(approver);
			if (person != null) {
				budgetMainfee.setApprover(person);
			}
		}
		
		if (!StringUtils.isEmpty(payRatio)) {
			budgetMainfee.setPayRatio(new BigDecimal(payRatio));
		}
		if (!StringUtils.isEmpty(payAmount)) {
			payAmount = payAmount.replaceAll(",", "");
			budgetMainfee.setPayAmount(new BigDecimal(payAmount));
		}
		
		budgetMainfee.setWorkflowName(workflowname);
		budgetMainfee.setApprovalId(approvalId);
		
		if (!StringUtils.isEmpty(approvalTime)) {
			try {
				budgetMainfee.setApprovalTime(garage.origin.common.util.DateUtils.string2Date(approvalTime, Constants.DATE_FORMAT));
            } catch (ParseException e) {
                // do nothing
            }
		}

		if (!StringUtils.isEmpty(requestTime)) {
			try {
				budgetMainfee.setRequestTime(garage.origin.common.util.DateUtils.string2Date(requestTime, Constants.DATE_FORMAT));
            } catch (ParseException e) {
                // do nothing
            }
		}
		
		budgetMainfee.setPayType(payType);
		budgetMainfee.setPurchaseNo(purchaseNo);
		budgetMainfee.setStockNo(stockNo);
		budgetMainfee.setCreator(OaProcessConstant.CREATOR_NAME);
		budgetMainfee.setcDatetime(now);
		budgetMainfee.setDataSource(0); // 0:oa同步；1:管理平台人工录入
		
		if (!StringUtils.isEmpty(purchaseNo)) {
			// 同步申购信息
			CommonFilter filter = new CommonFilter().addExact("extId", purchaseNo);
			List<PurchaseOrder> purchaseList = purchaseOrderService.list(filter, null);
			if (purchaseList.size() > 0) {
				if (purchaseList.size()>1) {
					logger.error("[OA Data Sync] purchaseOrder has multi record. extId=" + extId);
				}
				PurchaseOrder entity = purchaseList.get(0);
				if (!StringUtils.isEmpty(payRatio)) {
					entity.setPaymentRate(budgetMainfee.getPayRatio().doubleValue());
				}
				if (!StringUtils.isEmpty(payAmount)) {
					entity.setPaymentAmount(budgetMainfee.getPayAmount().doubleValue());
				}
				purchaseOrderService.setData(entity);
			} else {
				logger.error("[OA Data Sync] purchaseOrder sync failed. purchaseNo=" + purchaseNo);
			}
		}
		
		budgetMainfeeDao.create(budgetMainfee);
		
		JSONArray dataDetailList = dataArrayObj.getJSONArray("dataDetail");
		for (int index = 0; index<dataDetailList.size(); index++) {
			logger.info("[OA Data Sync] BudgetFee : loop index: " + index);
			JSONObject dataObj = dataDetailList.getJSONObject(index);
			logger.info("[OA Data Sync] BudgetFee : loop data: " + dataObj);
			
			BudgetFee budgetFee = new BudgetFee();
			budgetFee.setMainfee(budgetMainfee);
			budgetFee.setMainid(Integer.valueOf(extId));
			budgetFee.setApprovalId(approvalId);
			if (!StringUtils.isEmpty(approver)) {
				Person person = personDao.find(approver);
				if (person != null) {
					budgetFee.setApprover(person.getPersonName());
				}
			}
			if (!StringUtils.isEmpty(approvalTime)) {
				budgetFee.setApprovalTime(DateUtils.parseDate(approvalTime));
			}
			
			String bh = dataObj.getString("bh");
			String zymc = dataObj.getString("zymc");
			String dw = dataObj.getString("dw");
			String sl = dataObj.getString("sl");
			String djy = dataObj.getString("djy");
			String bxjey = dataObj.getString("bxjey");
			String zdmc = dataObj.getString("zdmc");
			String fyxm = dataObj.getString("fyxm");
			String bz = dataObj.getString("bz");
			
			budgetFee.setBh(bh);
			budgetFee.setZymc(zymc);
			budgetFee.setDw(dw);
			if (!StringUtils.isEmpty(sl)) {
				sl = sl.replaceAll(",", "");
				budgetFee.setSl(Integer.valueOf(sl));
			}
			if (!StringUtils.isEmpty(djy)) {
				djy = djy.replaceAll(",", "");
				budgetFee.setDjy(new BigDecimal(djy));
			}
			if (!StringUtils.isEmpty(bxjey)) {
				bxjey = bxjey.replaceAll(",", "");
				budgetFee.setBxjey(new BigDecimal(bxjey));
			}
			budgetFee.setZdmc(zdmc);
			budgetFee.setFyxm(fyxm);
			budgetFee.setBz(bz);
			budgetFee.setApprovalId(approvalId);
			budgetFee.setApprover(approver);
			if (!StringUtils.isEmpty(approvalTime)) budgetFee.setApprovalTime(DateUtils.parseDate(approvalTime));
			
			budgetFee.setCreator(OaProcessConstant.CREATOR_NAME);
			budgetFee.setcDatetime(now);
			budgetFee.setmDatetime(now);
			
			budgetFeeDao.create(budgetFee);
		}
		
		result.setStatus(JsonStatus.OK);
		return result;
	}
}
