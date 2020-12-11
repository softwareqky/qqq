package project.edge.service.bidding;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import com.ibm.icu.math.BigDecimal;

import garage.origin.common.constant.Constants;
import garage.origin.common.constant.OnOffEnum;
import garage.origin.dao.Dao;
import garage.origin.domain.business.OrderByDto;
import garage.origin.domain.filter.CommonFilter;
import garage.origin.service.GenericServiceImpl;
import project.edge.common.constant.FolderPath;
import project.edge.dao.archive.ArchiveDao;
import project.edge.dao.bidding.PurchaseOrderDao;
import project.edge.dao.bidding.PurchaseOrderDetailAttachmentDao;
import project.edge.dao.bidding.PurchaseOrderDetailDao;
import project.edge.domain.entity.Archive;
import project.edge.domain.entity.Project;
import project.edge.domain.entity.PurchaseOrder;
import project.edge.domain.entity.PurchaseOrderDetail;
import project.edge.domain.entity.PurchaseOrderDetailAttachment;

/**
 * [t_purchase_order_detail]对应的 Service。
 */
@Service
public class PurchaseOrderDetailServiceImpl extends GenericServiceImpl<PurchaseOrderDetail, String>
        implements PurchaseOrderDetailService {

    @Resource
    private PurchaseOrderDetailDao purchaseOrderDetailDao;

    @Resource
    private ArchiveDao archiveDao;

    @Resource
    private PurchaseOrderDao purchaseOrderDao;

    @Resource
    private PurchaseOrderDetailAttachmentDao purchaseOrderDetailAttachmentDao;

    @Override
    public Dao<PurchaseOrderDetail, String> getDefaultDao() {
        return this.purchaseOrderDetailDao;
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
    public void create(PurchaseOrderDetail entity) {

        if (entity == null || StringUtils.isEmpty(entity.getId())) {
            return;
        }

//        // 如果项目文件夹对应的Archive不存在，则创建
//        if (this.archiveDao.find(FolderPath.PURCHASEORDERDETAIL) == null) {
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
     
        if(this.archiveDao.find(entity.getProject().getId() + "_" + FolderPath.PURCHASEORDERDETAIL) == null) {
            this.archiveDao.create(this.getPurchaseOrderDetailRootDirArchive(year,entity));
        }
        
        
        this.purchaseOrderDetailDao.create(entity);
        for (PurchaseOrderDetailAttachment a : entity.getPurchaseOrderDetailAttachments()) {
            this.archiveDao.create(a.getArchive());
            this.purchaseOrderDetailAttachmentDao.create(a);
        }
        
        // 更新申购订单表
        updatePurchaseOrderPaymentInfo(entity);
    }

    /**
     * 增加处理附件文件。
     */
    @Override
    @Transactional
    public void update(PurchaseOrderDetail entity) {
        if (entity == null || StringUtils.isEmpty(entity.getId())) {
            return;
        }

//        // 如果项目文件夹对应的Archive不存在，则创建
//        if (this.archiveDao.find(FolderPath.PURCHASEORDERDETAIL) == null) {
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
        
        if(this.archiveDao.find(entity.getProject().getId() + "_" + FolderPath.PURCHASEORDERDETAIL) == null) {
            this.archiveDao.create(this.getPurchaseOrderDetailRootDirArchive(year,entity));
        }

        // 创建新增的文件
        for (PurchaseOrderDetailAttachment attachment : entity
                .getPurchaseOrderDetailAttachments()) {

            this.archiveDao.create(attachment.getArchive());
            this.purchaseOrderDetailAttachmentDao.create(attachment);
        }

        super.update(entity);
        
        // 更新申购订单表
        updatePurchaseOrderPaymentInfo(entity);

        for (PurchaseOrderDetailAttachment attachmentToDelete : entity.getAttachmentsToDelete()) {
            this.archiveDao.delete(attachmentToDelete.getArchive());
        }
    }

    @Transactional
    private void updatePurchaseOrderPaymentInfo(PurchaseOrderDetail purchaseOrderDetail) {
    	CommonFilter filter = new CommonFilter().addExact("purchaseOrder.id", purchaseOrderDetail.getPurchaseOrder().getId());
    	List<PurchaseOrderDetail> list = this.list(filter, null);
    	
    	// 申购订单表付款金额
    	BigDecimal totalPay = BigDecimal.ZERO;
    	for(PurchaseOrderDetail entity : list) {
    		if (entity.getTotalPaymentAmount() != null) {
    			BigDecimal detailTotalPay = new BigDecimal(String.valueOf(entity.getTotalPaymentAmount()));
    			totalPay = totalPay.add(detailTotalPay);
    		}
    	}
    	
    	// 申购订单表付款比例
    	BigDecimal paymentRate = BigDecimal.ZERO;
    	PurchaseOrder purchaseOrderEntity = purchaseOrderDao.find(purchaseOrderDetail.getPurchaseOrder().getId());
    	BigDecimal totalAmount = new BigDecimal(String.valueOf(purchaseOrderEntity.getTotalAmount()));
    	paymentRate = totalPay.divide(totalAmount);
    	
    	purchaseOrderEntity.setPaymentAmount(totalPay.doubleValue());
    	purchaseOrderEntity.setPaymentRate(paymentRate.doubleValue());
    	purchaseOrderDao.update(purchaseOrderEntity);
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
    private Archive getPurchaseOrderDetailRootDirArchive(String year, PurchaseOrderDetail entity) {

        // 实体对应的文件夹Archive
        Archive dir = new Archive();
        dir.setId(entity.getProject().getId() + "_" + FolderPath.PURCHASEORDERDETAIL);
        dir.setArchiveName("采购明细"); // 项目名不允许修改，所以不考虑变更项目名引起项目文件夹改名的问题
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
    private Archive getDirArchive(PurchaseOrderDetail entity) {

        // 实体对应的文件夹Archive
        Archive dir = new Archive();
        dir.setId(entity.getId());

        // 寻找采购单号
        if (!StringUtils.isEmpty(entity.getPurchaseOrder().getId())) {
            PurchaseOrder p = this.purchaseOrderDao.find(entity.getPurchaseOrder().getId());
            if (p != null) {
                dir.setArchiveName(p.getPurchaseOrderNo() + "_" + entity.getId());
            } else {
                dir.setArchiveName(entity.getPurchaseOrder().getId() + "_" + entity.getId());
            }
        } else {
            dir.setArchiveName(entity.getPurchaseOrder().getId() + "_" + entity.getId());
        }


        dir.setIsDir(OnOffEnum.ON.value());
        dir.setRelativePath(
                File.separator + FolderPath.PURCHASEORDERDETAIL + File.separator + dir.getId());
        dir.setFileSize(null);
        dir.setPid(FolderPath.PURCHASEORDERDETAIL);

        /**
         * 第零层：根
         * 第一层：FolderPath.EXPERT(DIR)
         * 第二层：ProjectGroup.id(DIR)
         * 第三层：Archive文件
         */
        dir.setLevel(2);
        dir.setPath(Constants.SLASH + Constants.COMMA + FolderPath.PURCHASEORDERDETAIL);
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
        dir.setId(FolderPath.PURCHASEORDERDETAIL);
        dir.setArchiveName("信息明细登记归档");
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
}
