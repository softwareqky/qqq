package project.edge.domain.converter;

import java.io.File;
import java.math.RoundingMode;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Locale;
import java.util.Map;
import java.util.Set;

import org.springframework.context.MessageSource;
import org.springframework.util.StringUtils;

import com.ibm.icu.math.BigDecimal;

import garage.origin.common.constant.Constants;
import garage.origin.common.util.DateUtils;
import garage.origin.domain.view.AbstractSessionUserBean;
import garage.origin.domain.view.converter.ViewConverter;
import project.edge.common.constant.FlowStatusEnum;
import project.edge.common.constant.FolderPath;
import project.edge.common.constant.ProjectAttachmentTypeEnum;
import project.edge.domain.entity.Archive;
import project.edge.domain.entity.DataOption;
import project.edge.domain.entity.Person;
import project.edge.domain.entity.Project;
import project.edge.domain.entity.PurchaseOrder;
import project.edge.domain.entity.PurchaseOrderAttachment;
import project.edge.domain.entity.VirtualOrg;
import project.edge.domain.view.ArchiveBean;
import project.edge.domain.view.PurchaseOrderBean;

/**
 * @author angel_000
 *         采购订单表对应的view和entity的转换器。
 */
public class PurchaseOrderBeanConverter implements ViewConverter<PurchaseOrder, PurchaseOrderBean> {

    @Override
    public PurchaseOrderBean fromEntity(PurchaseOrder entity, MessageSource messageSource,
            Locale locale) {
        PurchaseOrderBean bean = new PurchaseOrderBean();
        bean.setId(entity.getId());

        bean.setPurchaseOrderNo(entity.getPurchaseOrderNo());
        Project project = entity.getProject();
        if (project != null) {
            bean.setProject_(project.getId());
            bean.setProjectText(project.getProjectName());
            bean.setProjectNum(project.getProjectNum());
        }
        
        Person applicant = entity.getApplicant();
        if (applicant != null) {
        	bean.setApplicant_(applicant.getId());
        	bean.setApplicantText(applicant.getPersonName());
        }
        bean.setApplicantContact(entity.getApplicantContact());
        bean.setPurchaseReason(entity.getPurchaseReason());
        bean.setReceivableCompany(entity.getReceivableCompany());
        if (entity.getPaymentType() != null) {
        	bean.setPaymentType_(entity.getPaymentType().getId());
        	bean.setPaymentTypeText(entity.getPaymentType().getOptionName());
        }
        
        bean.setTotalAmount(entity.getTotalAmount());
        bean.setFlowStatus(entity.getFlowStatus());
        bean.setFlowStatusText(messageSource
                .getMessage(FlowStatusEnum.getResouceName(entity.getFlowStatus()), null, locale));
        bean.setRemark(entity.getRemark());
        
        if (entity.getPurchaseType() != null) {
        	bean.setPurchaseType_(entity.getPurchaseType().getId());
        	bean.setPurchaseTypeText(entity.getPurchaseType().getOptionName());
        }
        DataOption purchaseKind = entity.getPurchaseKind();
        if (purchaseKind != null) {
        	bean.setPurchaseKind_(purchaseKind.getId());
        	bean.setPurchaseKindText(purchaseKind.getOptionName());
        }
        if (entity.getAuditApplicant() != null) {
        	bean.setAuditApplicant_(entity.getAuditApplicant().getId());
        	bean.setAuditApplicantText(entity.getAuditApplicant().getPersonName());
        }
        
        bean.setPurchaseName(entity.getPurchaseName());
        if (entity.getVirtualOrg() != null) {
            bean.setVirtualOrg_(entity.getVirtualOrg().getId());
            bean.setVirtualOrgText(entity.getVirtualOrg().getVirtualOrgName());
        }
        if (entity.getInputApplicant() != null) {
        	bean.setInputApplicant_(entity.getInputApplicant().getId());
        	bean.setInputApplicantText(entity.getInputApplicant().getPersonName());
        }
        if (entity.getRecordTime() != null) {
            bean.setRecordTime(DateUtils.date2String(entity.getRecordTime(), Constants.DATE_FORMAT));
        }
        bean.setExtId(entity.getExtId());
        if (entity.getPaymentTime() != null) {
        	bean.setPaymentTime(DateUtils.date2String(entity.getPaymentTime(), Constants.DATE_FORMAT));
        }
        if (entity.getPaymentRate() != null) {
//        	BigDecimal a = new BigDecimal(entity.getPaymentRate());
//        	a = a.multiply(new BigDecimal(100)).setScale(2, BigDecimal.ROUND_HALF_UP);
//        	bean.setPaymentRate(a.toString() + "%");
        	bean.setPaymentRate(entity.getPaymentRate().toString());
        }
        bean.setPaymentAmount(entity.getPaymentAmount());
        
        if (entity.getcDatetime() != null) {
            bean.setcDatetime(DateUtils.date2String(entity.getcDatetime(), Constants.DATE_FORMAT));
        }
        
        // 附件处理
        ArchiveBeanConverter abConverter = new ArchiveBeanConverter();
        Set<PurchaseOrderAttachment> purchaseOrderAttachments =
                entity.getPurchaseOrderAttachments();
        for (PurchaseOrderAttachment pa : purchaseOrderAttachments) {

            bean.getArchivesList()
                    .add(abConverter.fromEntity(pa.getArchive(), messageSource, locale));
        }
        return bean;
    }

    @Override
    public PurchaseOrder toEntity(PurchaseOrderBean bean, PurchaseOrder oldEntity,
            AbstractSessionUserBean user, Date now) {
        // TODO Auto-generated method stub
        PurchaseOrder entity = oldEntity;

        if (entity == null) { // 表示新建
            entity = new PurchaseOrder();

            entity.setCreator(user.getSessionUserId());
            entity.setcDatetime(now);

        } else {
            // 在Controller.postUpdate中，需要比较新老entity的关联的档案文件，所以必须clone
            // clone之后，清空外键关联字段的值
            entity = oldEntity.clone();
            entity.setModifier(user.getSessionUserId());
            // 修改后，仍然保留的档案文件
            Map<String, String> map = new HashMap<>();
            for (String aid : bean.getArchivesReservedList()) {
                map.put(aid, aid);
            }

            // 找出需要删除的Archive
            for (PurchaseOrderAttachment dbAttachment : oldEntity.getPurchaseOrderAttachments()) {
                if (!map.containsKey(dbAttachment.getArchive().getId())) {
                    entity.getAttachmentsToDelete().add(dbAttachment);
                }

            }
        }
        bean.setId(entity.getId()); // ID必须赋值
        entity.setmDatetime(now);
        entity.setPurchaseOrderNo(bean.getPurchaseOrderNo());
        if (!StringUtils.isEmpty(bean.getProject_())) {
            Project project = new Project();
            project.setId(bean.getProject_());
            project.setProjectName(bean.getProjectText());
            entity.setProject(project);
        }
        if (!StringUtils.isEmpty(bean.getApplicant_())) {
        	Person applicant = new Person();
        	applicant.setId(bean.getApplicant_());
        	entity.setApplicant(applicant);
        }
        entity.setApplicantContact(bean.getApplicantContact());
        entity.setPurchaseReason(bean.getPurchaseReason());
        entity.setReceivableCompany(bean.getReceivableCompany());
        if (!StringUtils.isEmpty(bean.getPaymentType_())) {
        	DataOption paymentType = new DataOption();
        	paymentType.setId(bean.getPaymentType_());
        	entity.setPaymentType(paymentType);
        }
        entity.setTotalAmount(bean.getTotalAmount());
//        if (!StringUtils.isEmpty(bean.getFlowStatus())) {
//        	entity.setFlowStatus(bean.getFlowStatus());
//        }
        entity.setRemark(bean.getRemark());
        
        if (!StringUtils.isEmpty(bean.getPurchaseType_())) {
        	DataOption purchaseType = new DataOption();
        	purchaseType.setId(bean.getPurchaseType_());
        	entity.setPurchaseType(purchaseType);
        }
        if (!StringUtils.isEmpty(bean.getPurchaseKind_())) {
        	DataOption purchaseKind = new DataOption();
        	purchaseKind.setId(bean.getPurchaseKind_());
        	entity.setPurchaseKind(purchaseKind);
        }
        if (!StringUtils.isEmpty(bean.getAuditApplicant_())) {
            Person auditApplicant = new Person();
            auditApplicant.setId(bean.getAuditApplicant_());
            entity.setAuditApplicant(auditApplicant);
        }
        entity.setPurchaseName(bean.getPurchaseName());
        if (!StringUtils.isEmpty(bean.getVirtualOrg_())) {
        	VirtualOrg virtualOrg = new VirtualOrg();
        	virtualOrg.setId(bean.getVirtualOrg_());
            entity.setVirtualOrg(virtualOrg);
        }
        if (!StringUtils.isEmpty(bean.getInputApplicant_())) {
        	Person person = new Person();
        	person.setId(bean.getInputApplicant_());
        	entity.setInputApplicant(person);
        }
        if (!StringUtils.isEmpty(bean.getRecordTime())) {
            try {
				entity.setRecordTime(DateUtils.string2Date(bean.getRecordTime(), Constants.DATE_FORMAT));
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
        }
        if (!StringUtils.isEmpty(bean.getPaymentTime())) {
        	try {
        		entity.setPaymentTime(DateUtils.string2Date(bean.getPaymentTime(), Constants.DATE_FORMAT));
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
        }
        
        //entity.setPaymentRate(Double.valueOf(bean.getPaymentRate())); // 申购订单无需进行设定值
        entity.setPaymentAmount(bean.getPaymentAmount());
        if (!StringUtils.isEmpty(bean.getVirtualOrg_())) {
        	VirtualOrg virtualOrg = new VirtualOrg();
        	virtualOrg.setId(bean.getVirtualOrg_());
        	entity.setVirtualOrg(virtualOrg);
        }
        
        // 附件处理，此处只需处理新增加的ProjectAttachment
        Set<PurchaseOrderAttachment> purchaseOrderAttachments = new HashSet<>();

        ArchiveBeanConverter abConverter = new ArchiveBeanConverter();
        for (ArchiveBean ab : bean.getArchivesList()) {
            purchaseOrderAttachments.add(this.toAttachmentEntity(ab, abConverter, entity,
                    ProjectAttachmentTypeEnum.ARCHIVE.value(), user, now));
        }

        entity.setPurchaseOrderAttachments(purchaseOrderAttachments);
        return entity;
    }

    /**
     * 把ArchiveBean转换成Archive，同时创建对应的ProjectAttachment并返回。
     * 
     * @param ab
     * @param abConverter
     * @param entity
     * @param attachmentType
     * @param user
     * @param now
     * @return
     */
    public PurchaseOrderAttachment toAttachmentEntity(ArchiveBean ab,
            ArchiveBeanConverter abConverter, PurchaseOrder entity, int attachmentType,
            AbstractSessionUserBean user, Date now) {

        PurchaseOrderAttachment attachment = new PurchaseOrderAttachment();
        attachment.setPurchaseOrder(entity);

//changed start by huang 
//        /**
//         * 第零层：根
//         * 第一层：FolderPath.PROJECT(DIR)
//         * 第二层：Project.id(DIR)
//         * 第三层：Archive文件
//         */
//        ab.setLevel(3);
//        ab.setPid(entity.getId());
//        ab.setPath(Constants.SLASH + Constants.COMMA + FolderPath.PURCHASEORDER + Constants.COMMA
//                + entity.getId());
//        Archive aentity = abConverter.toEntity(ab, null, user, now);
//
//        // "\project\guid\xxx.doc"
//        // String extension = FilenameUtils.getExtension(ab.getArchiveName()).toLowerCase();
//        // String relativePath = File.separator + FolderPath.PROJECT + File.separator
//        // + entity.getId() + File.separator + aentity.getId() + Constants.DOT + extension;
//        String relativePath = File.separator + FolderPath.PURCHASEORDER + File.separator
//                + entity.getId() + File.separator + aentity.getId();
      
        /**
         * 第零层：根
         * 第一层：年份
         * 第二层：项目名称+项目编号 FolderPath.PROJECT(DIR)
         * 第三层: 系统归档
         * 第四层：文件夹名
         * 第五层：Archive文件
         */
        ab.setLevel(5);

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy");
        Date date = new Date();
        String year = sdf.format(date);

        ab.setPid(entity.getProject().getId() + "_" + FolderPath.PURCHASEORDER);

        ab.setPath(Constants.SLASH + Constants.COMMA + year + Constants.COMMA
                + entity.getProject().getId() + Constants.COMMA
                + entity.getProject().getId() + "_" + FolderPath.SYSTEM_PLACE_ON_FILE + Constants.COMMA
                + entity.getProject().getId() + "_" + FolderPath.PURCHASEORDER);
        
        Archive aentity = abConverter.toEntity(ab, null, user, now);
        
        String relativePath = File.separator + year + File.separator + entity.getProject().getId() + File.separator + entity.getProject().getId() + "_"
                + FolderPath.SYSTEM_PLACE_ON_FILE + File.separator + entity.getProject().getId() + "_"
                + FolderPath.PURCHASEORDER + File.separator + aentity.getId();

//end by huang
        ab.setRelativePath(relativePath);
        aentity.setRelativePath(relativePath);

        // Archive:
        // id/level/pid/(id)path/relativePath由converter各自设置，
        // archiveName/isDir/fileSize在Controller中统一设置，
        // fileDigest暂不使用

        attachment.setArchive(aentity);
        return attachment;
    }

}
