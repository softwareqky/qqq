package project.edge.service.bidding;

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
import garage.origin.common.util.DateUtils;
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
import project.edge.dao.bidding.PurchaseOrderAttachmentDao;
import project.edge.dao.bidding.PurchaseOrderDao;
import project.edge.dao.bidding.PurchaseOrderDetailDao;
import project.edge.dao.project.ProjectDao;
import project.edge.domain.converter.ArchiveBeanConverter;
import project.edge.domain.converter.PurchaseOrderBeanConverter;
import project.edge.domain.entity.Archive;
import project.edge.domain.entity.BudgetEstimateSum;
import project.edge.domain.entity.DataOption;
import project.edge.domain.entity.Person;
import project.edge.domain.entity.Project;
import project.edge.domain.entity.PurchaseOrder;
import project.edge.domain.entity.PurchaseOrderAttachment;
import project.edge.domain.entity.PurchaseOrderDetail;
import project.edge.domain.entity.VirtualOrg;
import project.edge.domain.view.ArchiveBean;
import project.edge.service.budget.BudgetEstimateSumService;
import project.edge.service.project.VirtualOrgService;
import project.edge.service.system.DataOptionService;
import project.edge.service.system.SystemConfigService;

/**
 * [t_purchase_order]对应的 Service。
 */
@Service
public class PurchaseOrderServiceImpl extends GenericServiceImpl<PurchaseOrder, String>
        implements PurchaseOrderService {
	private static final Logger logger = LoggerFactory.getLogger(PurchaseOrderServiceImpl.class);
	
    @Resource
    private PurchaseOrderDao purchaseOrderDao;

    @Resource
    private ArchiveDao archiveDao;
    
    @Resource
    private ProjectDao projectDao;

    @Resource
    private PurchaseOrderAttachmentDao purchaseOrderAttachmentDao;
    
    @Resource
    private PurchaseOrderDetailDao purchaseOrderDetailDao;
    
    @Resource
    protected SystemConfigService systemConfigService;
    
    @Resource
    private VirtualOrgService virtualOrgService;
    
    @Resource
    private DataOptionService dataOptionService;
    
    @Resource
    private BudgetEstimateSumService budgetEstimateSumService;

    @Override
    public Dao<PurchaseOrder, String> getDefaultDao() {
        return this.purchaseOrderDao;
    }

    @Override
    public OrderByDto getDefaultOrder() {
        return new OrderByDto("cDatetime");
    }

    /**
     * 增加处理附件文件。
     */
    @Override
    @Transactional
    public void create(PurchaseOrder entity) {

        if (entity == null || StringUtils.isEmpty(entity.getId())) {
            return;
        }
        
        // 设置默认编号
        if (StringUtils.isEmpty(entity.getPurchaseOrderNo())) {
            entity.setPurchaseOrderNo(generatePurchaseOrderNo());
        }
        
        // 增加项目内部编号的获取，防止建档时项目编号无法获取
        if (entity.getProject() != null) {
            Project project = projectDao.find(entity.getProject().getId());
            entity.setProject(project);
        }
//
//        // 如果项目文件夹对应的Archive不存在，则创建
//        if (this.archiveDao.find(FolderPath.PURCHASEORDER) == null) {
//            this.archiveDao.create(this.getProjectRootDirArchive());
//        }

//        // 如果Project entity对应的文件夹Archive不存在，则创建
//        if (this.archiveDao.find(entity.getId()) == null) {
//            this.archiveDao.create(this.getDirArchive(entity));
//        }
        
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
        
        this.purchaseOrderDao.create(entity);
        for (PurchaseOrderAttachment a : entity.getPurchaseOrderAttachments()) {
            this.archiveDao.create(a.getArchive());
            this.purchaseOrderAttachmentDao.create(a);
        }
    }

    @Transactional
    private String generatePurchaseOrderNo() {
    	String purchaseOrderNum = "CGxxx";
		try {

			String template = "CG${yyyyMMdd}xxx";
			int elStart = template.indexOf("${");
			int elEnd = template.lastIndexOf("}");
			String el = template.substring(elStart + 2, elEnd);
			String v = DateUtils.date2String(new Date(), el);

			CommonFilter f = new CommonFilter().addAnywhere("purchaseOrderNo", v);
			List<OrderByDto> orders = new ArrayList<OrderByDto>();
			orders.add(new OrderByDto("cDatetime"));
			List<PurchaseOrder> purchaseOrder = this.purchaseOrderDao.list(f, orders);
			String retstr = "";
			if (purchaseOrder != null) {
				retstr = String.format("%06d", (purchaseOrder.size() + 1));
			} else {
				retstr = "000001";
			}

			purchaseOrderNum = template.substring(0, elStart) + v + retstr;

		} catch (Exception e) {
			logger.error(e.getMessage());
		}

		return purchaseOrderNum;
	}
    
    /**
     * 增加处理附件文件。
     */
    @Override
    @Transactional
    public void update(PurchaseOrder entity) {
        if (entity == null || StringUtils.isEmpty(entity.getId())) {
            return;
        }
        
        // 增加项目内部编号的获取，防止建档时项目编号无法获取
        if (entity.getProject() != null) {
            Project project = projectDao.find(entity.getProject().getId());
            entity.setProject(project);
        }
        
//        // 如果项目文件夹对应的Archive不存在，则创建
//        if (this.archiveDao.find(FolderPath.PURCHASEORDER) == null) {
//            this.archiveDao.create(this.getProjectRootDirArchive());
//        }
//
//        // 如果Project entity对应的文件夹Archive不存在，则创建
//        if (this.archiveDao.find(entity.getId()) == null) {
//            this.archiveDao.create(this.getDirArchive(entity));
//        }
        
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
        for (PurchaseOrderAttachment attachment : entity.getPurchaseOrderAttachments()) {

            this.archiveDao.create(attachment.getArchive());
            this.purchaseOrderAttachmentDao.create(attachment);
        }

        super.update(entity);


        for (PurchaseOrderAttachment attachmentToDelete : entity.getAttachmentsToDelete()) {
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
    private Archive getPurchaseOrderRootDirArchive(String year, PurchaseOrder entity) {

        // 实体对应的文件夹Archive
        Archive dir = new Archive();
        dir.setId(entity.getProject().getId() + "_" + FolderPath.PURCHASEORDER);
        dir.setArchiveName("信息登记"); // 项目名不允许修改，所以不考虑变更项目名引起项目文件夹改名的问题
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
    private Archive getDirArchive(PurchaseOrder entity) {

        // 实体对应的文件夹Archive
        Archive dir = new Archive();
        dir.setId(entity.getId());
        dir.setArchiveName(entity.getPurchaseOrderNo());
        dir.setIsDir(OnOffEnum.ON.value());
        dir.setRelativePath(
                File.separator + FolderPath.PURCHASEORDER + File.separator + dir.getId());
        dir.setFileSize(null);
        dir.setPid(FolderPath.PURCHASEORDER);

        /**
         * 第零层：根
         * 第一层：FolderPath.EXPERT(DIR)
         * 第二层：ProjectGroup.id(DIR)
         * 第三层：Archive文件
         */
        dir.setLevel(2);
        dir.setPath(Constants.SLASH + Constants.COMMA + FolderPath.PURCHASEORDER);
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
        dir.setId(FolderPath.PURCHASEORDER);
        dir.setArchiveName("信息登记归档");
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
		
		logger.info("[OA Data Sync] parseSyncData in PurchaseOrderService.");
		Date now = new Date();
		JSONObject dataObj = JSONObject.parseObject(data);
		
		String extId = dataObj.getString("lcid");
		if (StringUtils.isEmpty(extId)) {
			logger.error("[OA Data Sync] extId is not exist.");
			result.setStatus(JsonStatus.ERROR);
			result.setMessage("extId is null.");
			return result;
		}
		
		String rootFolderPath =
                this.systemConfigService.getStringConfig(SystemConfigKeys.ARCHIVE_ROOT_FOLDER_PATH);
		
		PurchaseOrder purchaseOrder = new PurchaseOrder();
		List<PurchaseOrderDetail> purchaseOrderDetailList = new ArrayList<>();
		
		// 确认同步类型
		CommonFilter f = new CommonFilter();
		f.addExact("extId", extId).addExact("dataSource", DataSourceEnum.FROM_OA.value());
		List<OrderByDto> orders = new ArrayList<OrderByDto>();
		orders.add(new OrderByDto("cDatetime"));
		List<PurchaseOrder> list = purchaseOrderDao.list(f, orders);
		if (list.size() < 1) {
			// 新增同步流程
			purchaseOrder.setDataSource(DataSourceEnum.FROM_OA.value());
			purchaseOrder.setExtId(extId);
			purchaseOrder.setFlowStatus(FlowStatusEnum.REVIEW_PASSED.value());
			Project project = new Project();
			project.setId(OaProcessConstant.PROJECT_ID);
			purchaseOrder.setProject(project);
		} else {
			// 更新同步流程
			// OA侧确认结果：不会出现更新的可能
			//              流程归档 就不能改数据
			//purchaseOrder = list.get(0);
			//f = new CommonFilter().addExact("purchaseOrder.id", purchaseOrder.getId());
			logger.error("[OA Data Sync] update is not support.");
			result.setStatus(JsonStatus.ERROR);
			result.setMessage("update is not support.");
			return result;
		}
		
		String applicant = dataObj.getString("sqr");
		String applicantContact = dataObj.getString("lxfs");
		String purchaseReason = dataObj.getString("sqly");
		if (!StringUtils.isEmpty(dataObj.getString("archives"))) {
			JSONArray archives = dataObj.getJSONArray("archives");
			Set<PurchaseOrderAttachment> purchaseOrderAttachments = new HashSet<>();
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
				PurchaseOrderBeanConverter beanConverter = new PurchaseOrderBeanConverter();
				PurchaseOrderAttachment attachment = beanConverter.toAttachmentEntity(archive, abConverter, purchaseOrder,
	                    ProjectAttachmentTypeEnum.ARCHIVE.value(), null, new Date());
				purchaseOrderAttachments.add(attachment);
	
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
			purchaseOrder.setPurchaseOrderAttachments(purchaseOrderAttachments);
		}
		String receivableCompany = dataObj.getString("skdw");
		String paymentType = dataObj.getString("fklx");
		String totalAmount = dataObj.getString("jehjy");
		String gzz = dataObj.getString("gzz");
		String projectName = dataObj.getString("xmm");
		String nmnbbh = dataObj.getString("xmnbbh");
		String purchaseOrderNo = dataObj.getString("sgbh");
		String approvalId = dataObj.getString("approvalId");
		String approver = dataObj.getString("approver");
		String approvalTime = dataObj.getString("approvalTime");
		if (!StringUtils.isEmpty(dataObj.getString("dataDetail"))) {
			JSONArray detailList = dataObj.getJSONArray("dataDetail");
			// 申购明细分解
			for (int i=0; i<detailList.size(); i++) {
				logger.info("[OA Data Sync] purchaseOrderDetail List loop in " + i);
				JSONObject obj = detailList.getJSONObject(i);
				//for (int j=0; j<purchaseOrderDetail.size(); j++) {
					//logger.info("[OA Data Sync] purchaseOrderDetail data object loop in " + j);
					//JSONObject obj = purchaseOrderDetail.getJSONObject(j);
					String materialName = obj.getString("zcmc");
					String specificationType = obj.getString("ggxh");
					String purchaseQuantity = obj.getString("sl");
					String measurementUnit = obj.getString("dw");
					String unitPrice = obj.getString("dj");
					String sumMoney = obj.getString("je");
					String usage = obj.getString("yt");
					PurchaseOrderDetail entity = new PurchaseOrderDetail();
					entity.setMaterialName(materialName);
					entity.setSpecificationType(specificationType);
					if (!StringUtils.isEmpty(purchaseQuantity)) {
						entity.setPurchaseQuantity(Integer.valueOf(purchaseQuantity));
					}
					entity.setMeasurementUnit(measurementUnit);
					if (!StringUtils.isEmpty(unitPrice)) {
						entity.setUnitPrice(Double.valueOf(unitPrice));
					}
					if (!StringUtils.isEmpty(sumMoney)) {
						entity.setSumMoney(Double.valueOf(sumMoney));
					}
					entity.setUsageInfo(usage);
					entity.setcDatetime(new Date());
					entity.setCreator("oa");
					entity.setmDatetime(new Date(0));
					
					// 设定固定项目
					Project project = new Project();
					project.setId(OaProcessConstant.PROJECT_ID);
					entity.setProject(project);
					
					// 采购订单明细的新增字段[budget_estimate_sum]通过oa同步字段zcmc在[t_budget_estimate_sum]中匹配name字段获取
					CommonFilter filter = new CommonFilter().addExact("name", materialName);
					List<BudgetEstimateSum> budgetEstimateSumList = this.budgetEstimateSumService.list(filter, null);
					if (budgetEstimateSumList.size() > 0) {
						entity.setBudgetEstimateSum(budgetEstimateSumList.get(0));
					} else {
						logger.info("budget_estimate_sum was not found. extId=" + extId + ", materialName=" + materialName);
					}
					
					purchaseOrderDetailList.add(entity);
				//}
			}
		}
		String workflowname = dataObj.getString("workflowname");
		// CENI项目申购单（设备、工具类）
		logger.info("[OA Data Sync] workflowname: " + workflowname);
		if (workflowname.length() >= 17) {
			String orgTitle = workflowname.substring(0, 17);
			if ("CENI项目申购单（设备、工具类）".equals(orgTitle)) {
				DataOption purchaseKind = new DataOption();
				purchaseKind.setId("PURCHASE_KIND_1");
				purchaseOrder.setPurchaseKind(purchaseKind);
			} else {
				DataOption purchaseKind = new DataOption();
				purchaseKind.setId("PURCHASE_KIND_2");
				purchaseOrder.setPurchaseKind(purchaseKind);
			}
		}
		String title = dataObj.getString("title");
		purchaseOrder.setPurchaseName(title);
		
		// 查询所属专业组
		if (!StringUtils.isEmpty(gzz)) {
			CommonFilter orgFilter = new CommonFilter();
	        orgFilter.addAnywhere("virtualOrgName", gzz);     
	        List<VirtualOrg> virtualOrgs = virtualOrgService.list(orgFilter, null);
	        if (virtualOrgs != null && virtualOrgs.size()>0) {
		        logger.info("[OA Data Sync] list virtual org size=" + virtualOrgs.size());
	        	purchaseOrder.setVirtualOrg(virtualOrgs.get(0));
	        }
		}
		
		//PurchaseOrder purchaseOrder = new PurchaseOrder();
		if (!StringUtils.isEmpty(applicant)) {
			Person entity = new Person();
			entity.setId(applicant);
			purchaseOrder.setApplicant(entity);
		}
		purchaseOrder.setApplicantContact(applicantContact);
		purchaseOrder.setPurchaseReason(purchaseReason);
		purchaseOrder.setReceivableCompany(receivableCompany);
		if (!StringUtils.isEmpty(paymentType)) {
			CommonFilter filter = new CommonFilter().addExact("dataType", DataTypeEnum.PAYMENT_TYPE.value());
			List<DataOption> dataOptionsList = this.dataOptionService.list(filter, null);
			for (DataOption o : dataOptionsList) {
				if (paymentType.equals(o.getOptionName())) {
					purchaseOrder.setPaymentType(o);
					break;
				}
			}
			
		}
		if (!StringUtils.isEmpty(totalAmount)) {
			Double entity = Double.valueOf(totalAmount);
			purchaseOrder.setTotalAmount(entity);
		}
		purchaseOrder.setcDatetime(now);
		purchaseOrder.setCreator(OaProcessConstant.CREATOR_NAME);
		purchaseOrder.setmDatetime(now);
		
		// 设置审批节点（审批人）
		if (!StringUtils.isEmpty(approver)) {
			Person auditApplicant = new Person();
			auditApplicant.setId(approver);
			purchaseOrder.setAuditApplicant(auditApplicant);
		}
		
		// 设置默认编号
        if (StringUtils.isEmpty(purchaseOrderNo)) {
        	purchaseOrderNo = generatePurchaseOrderNo();
        	logger.info("[OA Data Sync] auto generate purchase order no: " + purchaseOrderNo);
        }
        purchaseOrder.setPurchaseOrderNo(purchaseOrderNo);
		
		//创建对象
		purchaseOrderDao.create(purchaseOrder);
		for (int i=0; i<purchaseOrderDetailList.size(); i++) {
			PurchaseOrderDetail purchaseOrderDetail = purchaseOrderDetailList.get(i);
			purchaseOrderDetail.setPurchaseOrder(purchaseOrder);
			purchaseOrderDetailDao.create(purchaseOrderDetail);
		}
		for (PurchaseOrderAttachment attachment : purchaseOrder.getPurchaseOrderAttachments()) {
            this.archiveDao.create(attachment.getArchive());
            this.purchaseOrderAttachmentDao.create(attachment);
        }
		
		return result;
	}
    
    @Transactional
    public void setData(PurchaseOrder entity) {
    	if (entity == null || StringUtils.isEmpty(entity.getId())) {
            return;
        }
    	super.update(entity);
    }
}
