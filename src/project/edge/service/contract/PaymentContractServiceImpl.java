package project.edge.service.contract;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.annotation.Resource;

import org.apache.commons.io.FileUtils;
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
import garage.origin.common.constant.OnOffEnum;
import garage.origin.dao.Dao;
import garage.origin.domain.business.OrderByDto;
import garage.origin.domain.filter.CommonFilter;
import garage.origin.domain.view.JsonResultBean;
import garage.origin.service.GenericServiceImpl;
import project.edge.common.constant.DataSourceEnum;
import project.edge.common.constant.DataTypeEnum;
import project.edge.common.constant.FlowStatusEnum;
import project.edge.common.constant.FolderPath;
import project.edge.common.constant.OaProcessConstant;
import project.edge.common.constant.ProjectAttachmentTypeEnum;
import project.edge.common.constant.SystemConfigKeys;
import project.edge.dao.archive.ArchiveDao;
import project.edge.dao.bidding.PurchaseOrderDao;
import project.edge.dao.contract.PaymentContractAttachmentDao;
import project.edge.dao.contract.PaymentContractDao;
import project.edge.dao.contract.PaymentContractStatementDao;
import project.edge.domain.converter.ArchiveBeanConverter;
import project.edge.domain.converter.PaymentContractBeanConverter;
import project.edge.domain.entity.Archive;
import project.edge.domain.entity.ContractCategory;
import project.edge.domain.entity.DataOption;
import project.edge.domain.entity.PaymentContract;
import project.edge.domain.entity.PaymentContractAttachment;
import project.edge.domain.entity.PaymentContractStatement;
import project.edge.domain.entity.Project;
import project.edge.domain.entity.PurchaseOrder;
import project.edge.domain.view.ArchiveBean;
import project.edge.service.system.DataOptionService;
import project.edge.service.system.SystemConfigService;

/**
 * [t_payment_contract]对应的 Service。
 */
@Service
public class PaymentContractServiceImpl extends GenericServiceImpl<PaymentContract, String>
        implements PaymentContractService {
	private static final Logger logger = LoggerFactory.getLogger(PaymentContractServiceImpl.class);
	
    @Resource
    private PaymentContractDao paymentContractDao;
    
    @Resource
    protected SystemConfigService systemConfigService;

    @Resource
    private DataOptionService dataOptionService;
    
    @Resource
    private PurchaseOrderDao purchaseOrderDao;
    
    @Resource
    private ContractCategoryService contractCategoryService;
    
    @Resource
    private PaymentContractAttachmentDao paymentContractAttachmentDao;

    @Resource
    private PaymentContractStatementDao paymentContractStatementDao;
    @Resource
    private ArchiveDao archiveDao;
    
    @Override
    public Dao<PaymentContract, String> getDefaultDao() {
        return this.paymentContractDao;
    }

    @Override
    public OrderByDto getDefaultOrder() {
        return new OrderByDto("cDatetime");
    }
    
    @Override
    @Transactional
    public void create(PaymentContract entity) {

        if (entity == null || StringUtils.isEmpty(entity.getId())) {
            return;
        }

        // 自动设置对应项目
        if (entity.getPurchaseOrder() != null) {
            PurchaseOrder purchaseOrder = purchaseOrderDao.find(entity.getPurchaseOrder().getId());
            Project project = purchaseOrder.getProject();
            entity.setProject(project);
        }
        
        //如果年份文件夹存在，则创建
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy");
        Date date = new Date();
        String year = sdf.format(date);
        
        if (this.archiveDao.find(year) == null) {
            this.archiveDao.create(this.getYearRootDirArchive(year));
        }
        
        if(this.archiveDao.find(entity.getProject().getId()) == null) {
            this.archiveDao.create(this.getProjectRootDirArchive(year,entity.getProject()));
        }
        
        if(this.archiveDao.find(entity.getProject().getId() + "_" + FolderPath.SYSTEM_PLACE_ON_FILE) == null) {
            this.archiveDao.create(this.getSystemPlaceOnFileRootDirArchive(year,entity.getProject()));
        }
     
        if(this.archiveDao.find(entity.getProject().getId() + "_" + FolderPath.PAYMENT_CONTRACT) == null) {
            this.archiveDao.create(this.getPurchaseOrderRootDirArchive(year,entity));
        }
        
        this.paymentContractDao.create(entity);
        
        for (PaymentContractAttachment a : entity.getPaymentContractAttachments()) {
            this.archiveDao.create(a.getArchive());
            this.paymentContractAttachmentDao.create(a);
        }
    }
    
    @Override
    @Transactional
    public void update(PaymentContract entity) {
        if (entity == null || StringUtils.isEmpty(entity.getId())) {
            return;
        }

        // 自动设置对应项目
        if (entity.getPurchaseOrder() != null) {
            PurchaseOrder purchaseOrder = purchaseOrderDao.find(entity.getPurchaseOrder().getId());
            Project project = purchaseOrder.getProject();
            entity.setProject(project);
        }
        
        //如果年份文件夹存在，则创建
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy");
        Date date = new Date();
        String year = sdf.format(date);
        
        if (this.archiveDao.find(year) == null) {
            this.archiveDao.create(this.getYearRootDirArchive(year));
        }
        
        if(this.archiveDao.find(entity.getProject().getId()) == null) {
            this.archiveDao.create(this.getProjectRootDirArchive(year,entity.getProject()));
        }
        
        if(this.archiveDao.find(entity.getProject().getId() + "_" + FolderPath.SYSTEM_PLACE_ON_FILE) == null) {
            this.archiveDao.create(this.getSystemPlaceOnFileRootDirArchive(year,entity.getProject()));
        }
        
        if(this.archiveDao.find(entity.getProject().getId() + "_" + FolderPath.PURCHASEORDER) == null) {
            this.archiveDao.create(this.getPurchaseOrderRootDirArchive(year,entity));
        }

        // 创建新增的文件
        for (PaymentContractAttachment attachment : entity.getPaymentContractAttachments()) {

            this.archiveDao.create(attachment.getArchive());
            this.paymentContractAttachmentDao.create(attachment);
        }
        
        super.update(entity);
        
        for (PaymentContractAttachment attachmentToDelete : entity.getAttachmentsToDelete()) {
            this.archiveDao.delete(attachmentToDelete.getArchive());
        }
    }
    
    /**
     * 创建年的根文件夹Archive。
     * 
     * @return
     */
    private Archive getYearRootDirArchive(String year) {

        // 实体对应的文件夹Archive
        Archive dir = new Archive();
        dir.setId(year);
        dir.setArchiveName(year + "年");
        dir.setIsDir(OnOffEnum.ON.value());
        dir.setRelativePath(File.separator + dir.getId());
        dir.setFileSize(null);
        dir.setPid(Constants.SLASH);

        /**
         * 第零层：根
         * 第一层：FolderPath.PROJECT(DIR)
         * 第二层：Project.id(DIR)
         * 第三层：Archive文件
         */
        dir.setLevel(1);
        dir.setPath(Constants.SLASH);
        dir.setIsDeleted(OnOffEnum.OFF.value());
        dir.setCreator(Constants.ADMIN_USER_ID);

        Date now = new Date();
        dir.setcDatetime(now);
        dir.setmDatetime(now);

        return dir;
    }
    
    /**
     * 创建项目的根文件夹Archive。
     * 
     * @return
     */
    private Archive getProjectRootDirArchive(String year, Project entity) {

        // 实体对应的文件夹Archive
        Archive dir = new Archive();
        dir.setId(entity.getId());
        dir.setArchiveName(entity.getProjectName() + "_" + entity.getProjectNum());
        dir.setIsDir(OnOffEnum.ON.value());
        dir.setRelativePath(File.separator + year + File.separator + dir.getId());
        dir.setFileSize(null);
        dir.setPid(year);

        /**
         * 第零层：根
         * 第一层：FolderPath.PROJECT(DIR)
         * 第二层：Project.id(DIR)
         * 第三层：Archive文件
         */
        dir.setLevel(2);
        dir.setPath(Constants.SLASH + Constants.COMMA + year);
        dir.setIsDeleted(OnOffEnum.OFF.value());
        dir.setCreator(Constants.ADMIN_USER_ID);

        Date now = new Date();
        dir.setcDatetime(now);
        dir.setmDatetime(now);

        return dir;
    }
    
    /**
     * 创建实体对应的文件夹Archive。
     * 
     * @return
     */
    private Archive getSystemPlaceOnFileRootDirArchive(String year, Project entity) {

        // 实体对应的文件夹Archive
        Archive dir = new Archive();
        dir.setId(entity.getId() + "_" + FolderPath.SYSTEM_PLACE_ON_FILE);
        dir.setArchiveName("系统归档"); // 项目名不允许修改，所以不考虑变更项目名引起项目文件夹改名的问题
        dir.setIsDir(OnOffEnum.ON.value());
        dir.setRelativePath(File.separator + year + File.separator + entity.getId() + File.separator
                + dir.getId());
        dir.setFileSize(null);
        dir.setPid(entity.getId());

        /**
         * 第零层：根
         * 第一层：FolderPath.PROJECT(DIR)
         * 第二层：Project.id(DIR)
         * 第三层：Archive文件
         */
        dir.setLevel(3);
        dir.setPath(Constants.SLASH + Constants.COMMA + year + Constants.COMMA + entity.getId());
        dir.setIsDeleted(OnOffEnum.OFF.value());
//        Person person = entity.getCreator();
//        if (person != null) {
//            dir.setCreator(person.getId());
//        }
        
        dir.setCreator(Constants.ADMIN_USER_ID);
        
        Date now = new Date();
        dir.setcDatetime(now);
        dir.setmDatetime(now);

        return dir;
    }
    
    /**
     * 创建实体对应的文件夹Archive。
     * 
     * @return
     */
    private Archive getPurchaseOrderRootDirArchive(String year, PaymentContract entity) {

        // 实体对应的文件夹Archive
        Archive dir = new Archive();
        dir.setId(entity.getProject().getId() + "_" + FolderPath.PAYMENT_CONTRACT);
        dir.setArchiveName("支出合同"); // 项目名不允许修改，所以不考虑变更项目名引起项目文件夹改名的问题
        dir.setIsDir(OnOffEnum.ON.value());
        dir.setRelativePath(File.separator + year + File.separator + entity.getProject().getId() + File.separator
                + entity.getProject().getId() + "_" + FolderPath.SYSTEM_PLACE_ON_FILE + File.separator
                + dir.getId());
        dir.setFileSize(null);
        dir.setPid(entity.getProject().getId() + "_" + FolderPath.SYSTEM_PLACE_ON_FILE);

        /**
         * 第零层：根
         * 第一层：FolderPath.PROJECT(DIR)
         * 第二层：Project.id(DIR)
         * 第三层：Archive文件
         */
        dir.setLevel(4);
        dir.setPath(Constants.SLASH + Constants.COMMA + year + Constants.COMMA + entity.getProject().getId()
                + Constants.COMMA + entity.getProject().getId() + "_" + FolderPath.SYSTEM_PLACE_ON_FILE);
        dir.setIsDeleted(OnOffEnum.OFF.value());
//        Person person = entity.getProject().getCreator();
//        if (person != null) {
//            dir.setCreator(person.getId());
//        }

        dir.setCreator(Constants.ADMIN_USER_ID);
        dir.setcDatetime(entity.getcDatetime());
        dir.setmDatetime(entity.getmDatetime());

        return dir;
    }

    /**
     * 创建实体对应的文件夹Archive。
     * 
     * @return
     */
    private Archive getDirArchive(PaymentContract entity) {

        // 实体对应的文件夹Archive
        Archive dir = new Archive();
        dir.setId(entity.getId());
        dir.setArchiveName(entity.getContractName());
        dir.setIsDir(OnOffEnum.ON.value());
        dir.setRelativePath(
                File.separator + FolderPath.PAYMENT_CONTRACT + File.separator + dir.getId());
        dir.setFileSize(null);
        dir.setPid(FolderPath.PAYMENT_CONTRACT);

        /**
         * 第零层：根
         * 第一层：FolderPath.EXPERT(DIR)
         * 第二层：ProjectGroup.id(DIR)
         * 第三层：Archive文件
         */
        dir.setLevel(2);
        dir.setPath(Constants.SLASH + Constants.COMMA + FolderPath.PAYMENT_CONTRACT);
        dir.setIsDeleted(OnOffEnum.OFF.value());
        dir.setCreator(entity.getCreator());
        dir.setcDatetime(entity.getcDatetime());
        dir.setmDatetime(entity.getmDatetime());

        return dir;
    }

    /**
     * 创建项目的根文件夹Archive。
     * 
     * @return
     */
    private Archive getProjectRootDirArchive() {

        // 实体对应的文件夹Archive
        Archive dir = new Archive();
        dir.setId(FolderPath.PAYMENT_CONTRACT);
        dir.setArchiveName("支出合同归档");
        dir.setIsDir(OnOffEnum.ON.value());
        dir.setRelativePath(File.separator + dir.getId());
        dir.setFileSize(null);
        dir.setPid(Constants.SLASH);

        /**
         * 第零层：根
         * 第一层：FolderPath.PROJECT(DIR)
         * 第二层：Project.id(DIR)
         * 第三层：Archive文件
         */
        dir.setLevel(1);
        dir.setPath(Constants.SLASH);
        dir.setIsDeleted(OnOffEnum.OFF.value());
        dir.setCreator(Constants.ADMIN_USER_ID);

        Date now = new Date();
        dir.setcDatetime(now);
        dir.setmDatetime(now);

        return dir;
    }
    
    @Transactional
    public JsonResultBean parseSyncData(String data) {
		JsonResultBean result = new JsonResultBean();
		
		logger.info("[OA Data Sync] parseSyncData in PaymentContractService.");
		JSONObject dataObj = JSONObject.parseObject(data);
		
		String extId = dataObj.getString("extId");
		if (StringUtils.isEmpty(extId)) {
			result.setStatus(JsonStatus.ERROR);
			result.setMessage("extId is null.");
			return result;
		}
		String rootFolderPath =
                this.systemConfigService.getStringConfig(SystemConfigKeys.ARCHIVE_ROOT_FOLDER_PATH);
		
		PaymentContract paymentContract = new PaymentContract();
		//List<PaymentContractDetail> purchaseOrderDetailList = new ArrayList<>();
		
		// 确认同步类型
		CommonFilter f = new CommonFilter();
		f.addExact("extId", extId).addExact("dataSource", DataSourceEnum.FROM_OA.value());
		List<OrderByDto> orders = new ArrayList<OrderByDto>();
		orders.add(new OrderByDto("cDatetime"));
		List<PaymentContract> list = paymentContractDao.list(f, orders);
		if (list.size() < 1) {
			// 新增同步流程
			paymentContract.setDataSource(DataSourceEnum.FROM_OA.value());
			paymentContract.setExtId(extId);
			paymentContract.setFlowStatus(FlowStatusEnum.REVIEW_PASSED.value());
			Project project = new Project();
			project.setId(OaProcessConstant.PROJECT_ID);
			paymentContract.setProject(project);
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
		
		String serialNumber = dataObj.getString("projectNum");
		String contractName = dataObj.getString("htmc");
		String startTime = dataObj.getString("htkssj");
		String endTime = dataObj.getString("htjssj");
		String contractAmount = dataObj.getString("htje");
		String sealType = dataObj.getString("yzlx");
		String briefIntroduction = dataObj.getString("htgy");
		String partyA = dataObj.getString("htjf");
		String partyB = dataObj.getString("htyf");
		String contractKind = dataObj.getString("htlb");
		String approvalId = dataObj.getString("approvalId");
		String approver = dataObj.getString("approver");
		String approvalTime = dataObj.getString("approvalTime");
		
		JSONArray archives = dataObj.getJSONArray("archives");
		Set<PaymentContractAttachment> paymentContractAttachment = new HashSet<>();
		for (int i=0; i<archives.size(); i++) {
			logger.info("[OA Data Sync] archives loop in " + i);
			JSONObject obj = archives.getJSONObject(i);
			String fileStr = obj.get("attachmentFile").toString();
			String fileName = obj.get("attachmentFileName").toString();
			int strLen = fileStr.length();
			int fileSize = strLen-(strLen/8)*2;
			ArchiveBean archive = new ArchiveBean();
			archive.setArchiveName(fileName);
			archive.setIsDir(OnOffEnum.OFF.value());
			archive.setFileSize(fileSize);
			
			ArchiveBeanConverter abConverter = new ArchiveBeanConverter();
			PaymentContractBeanConverter beanConverter = new PaymentContractBeanConverter();
			PaymentContractAttachment attachment = beanConverter.toAttachmentEntity(archive, abConverter, paymentContract,
                    ProjectAttachmentTypeEnum.ARCHIVE.value(), null, new Date());
			paymentContractAttachment.add(attachment);

			String baseFolderPath = attachment.getArchive().getRelativePath();
			String filePath = rootFolderPath.concat(baseFolderPath);
			logger.info("[OA Data Sync] filePath: " + filePath);
            File targetDir = new File(filePath);
            if (!targetDir.exists()) {
                try {
					FileUtils.forceMkdir(targetDir);
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
            }
			filePath = filePath.concat(File.separator).concat(fileName);
			fileStr = fileStr.replaceAll("\\r\\n", "").replaceAll("\\r", "").replaceAll("\\n", "").replaceAll(" ", "+");
			logger.info(fileStr);
			if (!garage.origin.common.util.FileUtils.GenerateBase64File(fileStr, filePath)) {
				// 附件生成失败
				logger.error("[OA Data Sync] file generate failed.");
				result.setStatus(JsonStatus.ERROR);
				result.setMessage("archive create failed.");
				return result;
			}
		}
		paymentContract.setPaymentContractAttachments(paymentContractAttachment);
				
		paymentContract.setContractName(contractName);
		paymentContract.setSerialNumber(serialNumber);
		String[] pattern = new String[]{"yyyy-MM-dd","yyyyMMdd","yyyy/MM/dd","yyyyMMdd","yyyyMMddHHmmss","yyyy-MM-dd HH:mm:ss","yyyy/MM/dd HH:mm:ss"};
		if (!StringUtils.isEmpty(startTime)) {
			Date stDate = DateUtils.parseDate(startTime, pattern);
			paymentContract.setStartTime(stDate);
		}
		if (!StringUtils.isEmpty(endTime)) {
			Date etDate = DateUtils.parseDate(endTime, pattern);
			paymentContract.setEndTime(etDate);
		}
		if (!StringUtils.isEmpty(contractAmount)) {
			contractAmount = contractAmount.replaceAll(",", "");
			Double d = Double.valueOf(contractAmount);
			paymentContract.setContractAmount(d);
		}
		if (!StringUtils.isEmpty(sealType)) {
	        List<String> dataTypeList = new ArrayList<>();
	        dataTypeList.add(DataTypeEnum.SEAL_TYPE.value());
	        DataOption d = null;
	        
	        f = new CommonFilter().addWithin("dataType", dataTypeList);
	        List<DataOption> sealTypeList = this.dataOptionService.list(f, null);
	        if (sealTypeList != null) {
		        for (DataOption o : sealTypeList) {
		        	if (o.getOptionName().equals(sealType)) {
		        		d = o;
		        		break;
		        	}
		        }
	        }
	        if (d != null)
	        	paymentContract.setSealType(d);
		}
		paymentContract.setBriefIntroduction(briefIntroduction);
		paymentContract.setPartyA(partyA);
		paymentContract.setPartyB(partyB);
		if (!StringUtils.isEmpty(contractKind)) {
			ContractCategory d = null;
	        f = new CommonFilter().addExact("isLeaf", (short)1);
	        orders = new ArrayList<>();
	        orders.add(new OrderByDto("fieldOrder", true));
	        List<ContractCategory> contractCategoryList = this.contractCategoryService.list(f, orders);
	        if (contractCategoryList != null) {
	        	for (ContractCategory item : contractCategoryList) {
	        		if (item.getCategoryName().equals(contractKind)) {
	        			d = item;
	        			break;
	        		}
	        	}
	        }
	        if (d != null)
	            paymentContract.setContractKind(d);
		}
		

		JSONArray detailList = dataObj.getJSONArray("dataDetail");
		// 申购明细分解
		List<PaymentContractStatement> paymentContractStatementList = new ArrayList<>();
		for (int i=0; i<detailList.size(); i++) {
			JSONArray purchaseOrderDetail = detailList.getJSONArray(i);
			for (int j=0; j<purchaseOrderDetail.size(); j++) {
				JSONObject obj = purchaseOrderDetail.getJSONObject(j);
				String paymentCondition = obj.getString("sfktj");
				String finalContractAmount = obj.getString("sfkje");
				String remark = obj.getString("bz");
				PaymentContractStatement paymentContractStatement = new PaymentContractStatement();
				paymentContractStatement.setPaymentCondition(paymentCondition);
				if (!StringUtils.isEmpty(finalContractAmount)) {
					finalContractAmount = finalContractAmount.replaceAll(",", "");
					paymentContractStatement.setFinalContractAmount(Double.parseDouble(finalContractAmount));
				}
				paymentContractStatement.setRemark(remark);
				paymentContractStatement.setcDatetime(new Date());
				paymentContractStatement.setmDatetime(new Date());
				paymentContractStatement.setContractName(paymentContract.getContractName());
				paymentContractStatement.setContractNo(paymentContract.getSerialNumber());
				paymentContractStatement.setContractKind(paymentContract.getContractKind());
				paymentContractStatement.setCreator(OaProcessConstant.CREATOR_NAME);
				paymentContractStatementList.add(paymentContractStatement);
			}
		}
		
		//保存对象
		paymentContractDao.create(paymentContract);
		for (PaymentContractStatement paymentContractStatement : paymentContractStatementList) {
			paymentContractStatement.setPaymentContract(paymentContract);
			paymentContractStatementDao.create(paymentContractStatement);
		}
		
		for (PaymentContractAttachment attachment : paymentContract.getPaymentContractAttachments()) {
            this.archiveDao.create(attachment.getArchive());
            this.paymentContractAttachmentDao.create(attachment);
        }
		
		return result;
	}
    
    @Transactional
    public void setData(PaymentContract entity) {
    	if (entity == null || StringUtils.isEmpty(entity.getId())) {
            return;
        }
    	super.update(entity);
    }
}
