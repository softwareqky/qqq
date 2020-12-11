package project.edge.domain.converter;

import java.io.File;
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

import garage.origin.common.constant.Constants;
import garage.origin.common.constant.PriceTypeEnum;
import garage.origin.common.constant.TenderingStatusEnum;
import garage.origin.common.util.DateUtils;
import garage.origin.domain.view.AbstractSessionUserBean;
import garage.origin.domain.view.converter.ViewConverter;
import project.edge.common.constant.FlowStatusEnum;
import project.edge.common.constant.FolderPath;
import project.edge.domain.entity.Archive;
import project.edge.domain.entity.DataOption;
import project.edge.domain.entity.Dept;
import project.edge.domain.entity.Person;
import project.edge.domain.entity.Project;
import project.edge.domain.entity.PurchaseOrder;
import project.edge.domain.entity.TenderingPlan;
import project.edge.domain.entity.TenderingPlanAttachment;
import project.edge.domain.entity.TenderingPurchase;
import project.edge.domain.entity.VirtualOrg;
import project.edge.domain.view.ArchiveBean;
import project.edge.domain.view.TenderingPlanBean;

/**
 * @author angel_000
 *         招标计划明细对应的view和entity的转换器。
 */
public class TenderingPlanBeanConverter implements ViewConverter<TenderingPlan, TenderingPlanBean> {

    @Override
    public TenderingPlanBean fromEntity(TenderingPlan entity, MessageSource messageSource,
            Locale locale) {
        TenderingPlanBean bean = new TenderingPlanBean();
        Person applicant = entity.getApplicant();
        if (applicant != null) {
            bean.setApplicant_(applicant.getId());
            bean.setApplicantText(applicant.getPersonName());
        }
        bean.setApprovalNumber(entity.getApprovalNumber());
        bean.setEstimatedPrice(entity.getEstimatedPrice());
        bean.setEvaluatingMethods(entity.getEvaluatingMethods());
        bean.setFlowStatus(bean.getFlowStatus());
        bean.setId(entity.getId());
        if (entity.getPlanEndTime() != null) {
            bean.setPlanEndTime(
                    DateUtils.date2String(entity.getPlanEndTime(), Constants.DATE_FORMAT));
        }
        if (entity.getPlanStartTime() != null) {
            bean.setPlanStartTime(
                    DateUtils.date2String(entity.getPlanStartTime(), Constants.DATE_FORMAT));
        }
        
        if(entity.getTenderingStatus() != null) {
            bean.setTenderingStatus_(entity.getTenderingStatus());
            bean.setTenderingStatusText(messageSource.getMessage(TenderingStatusEnum.getResouceName(entity.getTenderingStatus()), null, locale));
            //bean.setTenderingStatusText(entity.getTenderingStatus() == 1
            //        ? messageSource.getMessage(TenderingStatusEnum.DAILY.resourceName(), null, locale)
            //        : messageSource.getMessage(TenderingStatusEnum.ONCE.resourceName(), null, locale));
        }

        if(entity.getPriceType() != null) {
            bean.setPriceType_(entity.getPriceType());
            bean.setPriceTypeText(messageSource.getMessage(PriceTypeEnum.getResouceName(entity.getPriceType()), null, locale));
            //bean.setPriceTypeText(entity.getPriceType() == 1
            //        ? messageSource.getMessage(TenderingStatusEnum.DAILY.resourceName(), null, locale)
            //        : messageSource.getMessage(TenderingStatusEnum.ONCE.resourceName(), null, locale));
        }

        DataOption businessType = entity.getTenderingType();

        if (businessType != null) {
            bean.setTenderingType_(businessType.getId());
            bean.setTenderingTypeText(businessType.getOptionName());
        }
        bean.setPriceUnit(entity.getPriceUnit());
        Project project = entity.getProject();
        if (project != null) {
            bean.setProject_(project.getId());
            bean.setProjectText(project.getProjectName());
        }
        //PurchaseOrder purchaseOrder = entity.getPurchaseOrder();
        //if (purchaseOrder != null) {
        //    bean.setPurchaseOrder_(purchaseOrder.getId());
        //    bean.setPurchaseOrderText(purchaseOrder.getPurchaseOrderNo());
        //}
        if (entity.getRecordTime() != null) {
            bean.setRecordTime(
                    DateUtils.date2String(entity.getRecordTime(), Constants.DATE_FORMAT));
        }
        bean.setRemark(entity.getRemark());
        if (entity.getTenderAssessmentEndTime() != null) {
            bean.setTenderAssessmentEndTime(DateUtils
                    .date2String(entity.getTenderAssessmentEndTime(), Constants.DATE_FORMAT));
        }
        if (entity.getTenderAssessmentStartTime() != null) {
            bean.setTenderAssessmentStartTime(DateUtils
                    .date2String(entity.getTenderAssessmentStartTime(), Constants.DATE_FORMAT));
        }
        Dept dept = entity.getTenderingDept();
        if (dept != null) {
            bean.setTenderingDept_(dept.getId());
            bean.setTenderingDeptText(dept.getDeptName());
        }
        VirtualOrg virtualOrg = entity.getVirtualOrg();
        if (virtualOrg != null) {
            bean.setVirtualOrg_(virtualOrg.getId());
            bean.setVirtualOrgText(virtualOrg.getVirtualOrgName());
        }
        Person tenderingLeader = entity.getTenderingLeader();
        if (tenderingLeader != null) {
            bean.setTenderingLeader_(tenderingLeader.getId());
            bean.setTenderingLeaderText(tenderingLeader.getPersonName());
        }
        bean.setTenderingMethod(entity.getTenderingMethod());
        bean.setTenderingName(entity.getTenderingName());
        bean.setTenderingNo(entity.getTenderingNo());

        if (entity.getTenderOpenEndTime() != null) {
            bean.setTenderOpenEndTime(
                    DateUtils.date2String(entity.getTenderOpenEndTime(), Constants.DATE_FORMAT));
        }
        if (entity.getTenderOpenTime() != null) {
            bean.setTenderOpenTime(
                    DateUtils.date2String(entity.getTenderOpenTime(), Constants.DATE_FORMAT));
        }
        bean.setTenderScope(entity.getTenderScope());
        
        if (entity.getFlowStartDate() != null) {
            bean.setFlowStartDate(
                    DateUtils.date2String(entity.getFlowStartDate(), Constants.DATE_FORMAT));
        }

        if (entity.getFlowEndDate() != null) {
            bean.setFlowEndDate(
                    DateUtils.date2String(entity.getFlowEndDate(), Constants.DATE_FORMAT));
        }
        
        bean.setFlowStatus(entity.getFlowStatus());
        bean.setFlowStatusText(messageSource.getMessage(FlowStatusEnum.getResouceName(entity.getFlowStatus()), null, locale));
        // 附件处理
        ArchiveBeanConverter abConverter = new ArchiveBeanConverter();
        Set<TenderingPlanAttachment> archives = entity.getArchives();

        for (TenderingPlanAttachment archive : archives) {
        	if (archive.getAttachmentType() == 0) {
        		bean.getArchiveDraftFirstList().add(abConverter.fromEntity(archive.getArchive(), messageSource, locale));
        	} else if (archive.getAttachmentType() == 1) {
        		bean.getArchiveDraftFinalList().add(abConverter.fromEntity(archive.getArchive(), messageSource, locale));
        	} else if (archive.getAttachmentType() == 2) {
        		bean.getArchiveTechnicalDocList().add(abConverter.fromEntity(archive.getArchive(), messageSource, locale));
        	} else if (archive.getAttachmentType() == 3) {
        		bean.getArchiveAnnouncementList().add(abConverter.fromEntity(archive.getArchive(), messageSource, locale));
        	} else if (archive.getAttachmentType() == 4) {
        		bean.getArchiveTenderWinList().add(abConverter.fromEntity(archive.getArchive(), messageSource, locale));
        	}
        }
        
        // 采购计划
        Set<TenderingPurchase> purchaseOrders = entity.getPurchases();
        if (purchaseOrders != null && purchaseOrders.size() > 0) {
        	TenderingPurchase purchaseOrder = purchaseOrders.iterator().next();
            bean.setPurchaseOrder_(purchaseOrder.getId());
            bean.setPurchaseOrderText(purchaseOrder.getPurchaseOrder().getPurchaseName());
        }
        return bean;
    }

    @Override
    public TenderingPlan toEntity(TenderingPlanBean bean, TenderingPlan oldEntity,
            AbstractSessionUserBean user, Date now) {
        TenderingPlan entity = oldEntity;
        Set<Archive> archives = new HashSet<>();

        if (entity == null) { // 表示新建
            entity = new TenderingPlan();

            entity.setCreator(user.getSessionUserId());
            entity.setcDatetime(now);
            entity.setRecordTime(now);
        } else { // 表示修改
            // 在Controller.postUpdate中，需要比较新老entity的关联的档案文件，所以必须clone
            // clone之后，清空外键关联字段的值
            entity = oldEntity.clone();
            entity.setArchives(new HashSet<>());

            entity.setModifier(user.getSessionUserId());

            // 修改后，仍然保留的档案文件
            Map<String, String> map = new HashMap<>();
            for (String aid : bean.getArchiveDraftFirstReservedList()) {
                Archive a = new Archive();
                a.setId(aid);
                archives.add(a);
                map.put(aid, aid);
            }
            for (String aid : bean.getArchiveDraftFinalReservedList()) {
                Archive a = new Archive();
                a.setId(aid);
                archives.add(a);
                map.put(aid, aid);
            }
            for (String aid : bean.getArchiveTechnicalDocReservedList()) {
                Archive a = new Archive();
                a.setId(aid);
                archives.add(a);
                map.put(aid, aid);
            }
            for (String aid : bean.getArchiveAnnouncementReservedList()) {
                Archive a = new Archive();
                a.setId(aid);
                archives.add(a);
                map.put(aid, aid);
            }
            for (String aid : bean.getArchiveTenderWinReservedList()) {
                Archive a = new Archive();
                a.setId(aid);
                archives.add(a);
                map.put(aid, aid);
            }

            // 找出需要删除的Archive
            for (TenderingPlanAttachment dbArchive : oldEntity.getArchives()) {
                if (!map.containsKey(dbArchive.getArchive().getId())) {
                    entity.getArchivesToDelete().add(dbArchive);
                }
            }
        }
        bean.setId(entity.getId()); // ID必须赋值
        entity.setmDatetime(now);
        entity.setApprovalNumber(bean.getApprovalNumber());
        entity.setEstimatedPrice(bean.getEstimatedPrice());
        entity.setEvaluatingMethods(bean.getEvaluatingMethods());
        
        if (!StringUtils.isEmpty(bean.getFlowStartDate())) {
            try {
                entity.setFlowStartDate(
                        DateUtils.string2Date(bean.getFlowStartDate(), Constants.DATE_FORMAT));
            } catch (ParseException e) {
                // do nothing
            }
        }

        if (!StringUtils.isEmpty(bean.getFlowEndDate())) {
            try {
                entity.setFlowEndDate(
                        DateUtils.string2Date(bean.getFlowEndDate(), Constants.DATE_FORMAT));
            } catch (ParseException e) {
                // do nothing
            }
        }
        
        entity.setFlowStatus(bean.getFlowStatus());
        entity.setPriceType(bean.getPriceType_());
        entity.setPriceUnit(bean.getPriceUnit());
        entity.setTenderingMethod(bean.getTenderingMethod());
        entity.setTenderingName(bean.getTenderingName());
        entity.setTenderingNo(bean.getTenderingNo());
        entity.setTenderingStatus(bean.getTenderingStatus_());
        entity.setTenderScope(bean.getTenderScope());
        entity.setRemark(bean.getRemark());

        if (!StringUtils.isEmpty(bean.getApplicant_())) {
            Person applicant = new Person();
            applicant.setId(bean.getApplicant_());
            entity.setApplicant(applicant);
        }
        if (!StringUtils.isEmpty(bean.getProject_())) {
            Project project = new Project();
            project.setId(bean.getProject_());
            project.setProjectName(bean.getProjectText());
            entity.setProject(project);
        }
        if (!StringUtils.isEmpty(bean.getPurchaseOrder_())) {
        	Set<TenderingPurchase> tenderingPurchases = new HashSet<TenderingPurchase>();
            PurchaseOrder purchaseOrder = new PurchaseOrder();
            purchaseOrder.setId(bean.getPurchaseOrder_());
            TenderingPurchase tenderingPurchase = new TenderingPurchase();
            tenderingPurchase.setTenderingPlan(entity);
            tenderingPurchase.setPurchaseOrder(purchaseOrder);
            tenderingPurchase.setcDatetime(now);
            tenderingPurchase.setCreator(user.getSessionLoginName());
            tenderingPurchases.add(tenderingPurchase);
            entity.setPurchases(tenderingPurchases);
        }
        if (!StringUtils.isEmpty(bean.getTenderingDept_())) {
            Dept dept = new Dept();
            dept.setId(bean.getTenderingDept_());
            entity.setTenderingDept(dept);
        }

        if (!StringUtils.isEmpty(bean.getVirtualOrg_())) {
            VirtualOrg virtualOrg = new VirtualOrg();
            virtualOrg.setId(bean.getVirtualOrg_());
            entity.setVirtualOrg(virtualOrg);
        }
        
        if (!StringUtils.isEmpty(bean.getTenderingLeader_())) {
            Person tenderingLeader = new Person();
            tenderingLeader.setId(bean.getTenderingLeader_());
            entity.setTenderingLeader(tenderingLeader);
        }
        if (!StringUtils.isEmpty(bean.getTenderingType_())) {
            DataOption tenderingType = new DataOption();
            tenderingType.setId(bean.getTenderingType_());
            entity.setTenderingType(tenderingType);
        }

        if (!StringUtils.isEmpty(bean.getPlanEndTime())) {
            try {
                entity.setPlanEndTime(
                        DateUtils.string2Date(bean.getPlanEndTime(), Constants.DATE_FORMAT));
            } catch (ParseException e) {
                // do nothing
            }
        }
        if (!StringUtils.isEmpty(bean.getPlanStartTime())) {
            try {
                entity.setPlanStartTime(
                        DateUtils.string2Date(bean.getPlanStartTime(), Constants.DATE_FORMAT));
            } catch (ParseException e) {
                // do nothing
            }
        }
        if (!StringUtils.isEmpty(bean.getTenderOpenTime())) {
            try {
                entity.setTenderOpenTime(
                        DateUtils.string2Date(bean.getTenderOpenTime(), Constants.DATE_FORMAT));
            } catch (ParseException e) {
                // do nothing
            }
        }
        if (!StringUtils.isEmpty(bean.getTenderOpenEndTime())) {
            try {
                entity.setTenderOpenEndTime(
                        DateUtils.string2Date(bean.getTenderOpenEndTime(), Constants.DATE_FORMAT));
            } catch (ParseException e) {
                // do nothing
            }
        }
        if (!StringUtils.isEmpty(bean.getTenderAssessmentStartTime())) {
            try {
                entity.setTenderAssessmentStartTime(DateUtils
                        .string2Date(bean.getTenderAssessmentStartTime(), Constants.DATE_FORMAT));
            } catch (ParseException e) {
                // do nothing
            }
        }
        if (!StringUtils.isEmpty(bean.getTenderAssessmentEndTime())) {
            try {
                entity.setTenderAssessmentEndTime(DateUtils
                        .string2Date(bean.getTenderAssessmentEndTime(), Constants.DATE_FORMAT));
            } catch (ParseException e) {
                // do nothing
            }
        }
        if (!StringUtils.isEmpty(bean.getRecordTime())) {
            try {
                entity.setRecordTime(
                        DateUtils.string2Date(bean.getRecordTime(), Constants.DATE_FORMAT));
            } catch (ParseException e) {
                // do nothing
            }
        }
        // 附件处理，此处只需处理新增加的ProjectAttachment
        Set<TenderingPlanAttachment> tenderingPlanAttachments = new HashSet<>();

        ArchiveBeanConverter abConverter = new ArchiveBeanConverter();

        for (ArchiveBean ab : bean.getArchiveDraftFirstList()) {
        	tenderingPlanAttachments.add(this.toAttachmentEntity(ab, abConverter, entity, 0, user, now));
        }
        for (ArchiveBean ab : bean.getArchiveDraftFinalList()) {
        	tenderingPlanAttachments.add(this.toAttachmentEntity(ab, abConverter, entity, 1, user, now));
        }
        for (ArchiveBean ab : bean.getArchiveTechnicalDocList()) {
        	tenderingPlanAttachments.add(this.toAttachmentEntity(ab, abConverter, entity, 2, user, now));
        }
        for (ArchiveBean ab : bean.getArchiveAnnouncementList()) {
        	tenderingPlanAttachments.add(this.toAttachmentEntity(ab, abConverter, entity, 3, user, now));
        }
        for (ArchiveBean ab : bean.getArchiveTenderWinList()) {
        	tenderingPlanAttachments.add(this.toAttachmentEntity(ab, abConverter, entity, 4, user, now));
        }
        entity.setArchives(tenderingPlanAttachments);

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
    private TenderingPlanAttachment toAttachmentEntity(ArchiveBean ab, ArchiveBeanConverter abConverter,
    		TenderingPlan entity, int attachmentType, AbstractSessionUserBean user, Date now) {

    	TenderingPlanAttachment attachment = new TenderingPlanAttachment();
        attachment.setTenderingPlan(entity);
        attachment.setAttachmentType(attachmentType);

        
//        /**
//         * 第零层：根
//         * 第一层：FolderPath.PROJECT(DIR)
//         * 第二层：Project.id(DIR)
//         * 第三层：Archive文件
//         */
//        ab.setLevel(3);
//        ab.setPid(entity.getId());
//        ab.setPath(Constants.SLASH + Constants.COMMA + FolderPath.TENDERING_PLAN + Constants.COMMA
//                + entity.getId());
//        Archive aentity = abConverter.toEntity(ab, null, user, now);
//
//        // "\project\guid\xxx.doc"
//        // String extension = FilenameUtils.getExtension(ab.getArchiveName()).toLowerCase();
//        // String relativePath = File.separator + FolderPath.PROJECT + File.separator
//        // + entity.getId() + File.separator + aentity.getId() + Constants.DOT + extension;
//        String relativePath = File.separator + FolderPath.TENDERING_PLAN + File.separator + entity.getId()
//                + File.separator + aentity.getId();
        
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

        ab.setPid(entity.getProject().getId() + "_" + FolderPath.TENDERING_PLAN);

        ab.setPath(Constants.SLASH + Constants.COMMA + year + Constants.COMMA
                + entity.getProject().getId() + Constants.COMMA
                + entity.getProject().getId() + "_" + FolderPath.SYSTEM_PLACE_ON_FILE + Constants.COMMA
                + entity.getProject().getId() + "_" + FolderPath.TENDERING_PLAN);
        
        Archive aentity = abConverter.toEntity(ab, null, user, now);
        
        String relativePath = File.separator + year + File.separator + entity.getProject().getId() + File.separator + entity.getProject().getId() + "_"
                + FolderPath.SYSTEM_PLACE_ON_FILE + File.separator + entity.getProject().getId() + "_"
                + FolderPath.TENDERING_PLAN + File.separator + aentity.getId();
        
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
