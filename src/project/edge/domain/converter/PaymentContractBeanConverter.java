/**
 * 
 */
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
import garage.origin.common.constant.OnOffEnum;
import garage.origin.common.util.DateUtils;
import garage.origin.domain.view.AbstractSessionUserBean;
import garage.origin.domain.view.converter.ViewConverter;
import project.edge.common.constant.FlowStatusEnum;
import project.edge.common.constant.FolderPath;
import project.edge.common.constant.OaProcessConstant;
import project.edge.common.constant.ProjectAttachmentTypeEnum;
import project.edge.domain.entity.Archive;
import project.edge.domain.entity.ContractCategory;
import project.edge.domain.entity.DataOption;
import project.edge.domain.entity.PaymentContract;
import project.edge.domain.entity.PaymentContractAttachment;
import project.edge.domain.entity.Person;
import project.edge.domain.entity.Project;
import project.edge.domain.entity.PurchaseOrder;
import project.edge.domain.entity.VirtualOrg;
import project.edge.domain.view.ArchiveBean;
import project.edge.domain.view.PaymentContractBean;


/**
 * @author angel_000
 *
 */
public class PaymentContractBeanConverter
        implements ViewConverter<PaymentContract, PaymentContractBean> {

    @Override
    public PaymentContractBean fromEntity(PaymentContract entity, MessageSource messageSource,
            Locale locale) {

        PaymentContractBean bean = new PaymentContractBean();
        bean.setId(entity.getId());

        PurchaseOrder purchaseOrder = entity.getPurchaseOrder();
        if (purchaseOrder != null) {
            bean.setPurchaseOrder_(purchaseOrder.getId());
            bean.setPurchaseOrderText(purchaseOrder.getPurchaseName());
        }

        Project project = entity.getProject();
        if (project != null) {
            bean.setProject_(project.getId());
            bean.setProjectText(project.getProjectName());
        }
        VirtualOrg virtualOrg = entity.getVirtualOrg();
        if (virtualOrg != null) {
            bean.setVirtualOrg_(virtualOrg.getId());
            bean.setVirtualOrgText(virtualOrg.getVirtualOrgName());
        }
        bean.setContractName(entity.getContractName());
        bean.setSerialNumber(entity.getSerialNumber());
        bean.setContractYear(entity.getContractYear());
        bean.setContractCount(entity.getContractCount());
        bean.setContractAmount(entity.getContractAmount());

        if (entity.getIsTemporaryPricing() != null) {
            bean.setIsTemporaryPricing(entity.getIsTemporaryPricing());
            bean.setIsTemporaryPricingText(entity.getIsTemporaryPricing() == 1
                    ? messageSource.getMessage(OnOffEnum.ON.resourceName(), null, locale)
                    : messageSource.getMessage(OnOffEnum.OFF.resourceName(), null, locale));
        }
        bean.setAmountExcludingTax(entity.getAmountExcludingTax());
        if (entity.getContractKind() != null) {
            bean.setContractKind_(entity.getContractKind().getId());
            bean.setContractKindText(entity.getContractKind().getCategoryName());
        }
        bean.setContractAttribute(entity.getContractAttribute());

        if (entity.getContractTime() != null) {
            bean.setContractTime(
                    DateUtils.date2String(entity.getContractTime(), Constants.DATE_FORMAT));
        }

        bean.setOntractAddress(entity.getOntractAddress());

        if (entity.getStartTime() != null) {
            bean.setStartTime(DateUtils.date2String(entity.getStartTime(), Constants.DATE_FORMAT));
        }

        if (entity.getEndTime() != null) {
            bean.setEndTime(DateUtils.date2String(entity.getEndTime(), Constants.DATE_FORMAT));
        }

        bean.setPartyA(entity.getPartyA());
        bean.setPartyB(entity.getPartyB());

        Person signingPeople = entity.getSigningPeople();
        if (signingPeople != null) {
            bean.setSigningPeople_(signingPeople.getId());
            // bean.setSigningPeopleText(signingPeople.getPersonName());
        }

        bean.setPartyBContact(entity.getPartyBContact());
        bean.setPartyAContactInfo(entity.getPartyAContactInfo());
        bean.setPartyBContactInfo(entity.getPartyBContactInfo());
        bean.setBriefIntroduction(entity.getBriefIntroduction());
        DataOption sealType = entity.getSealType();
        if (sealType != null) {
        	bean.setSealType_(sealType.getId());
        	bean.setSealTypeText(sealType.getOptionName());
        }

        if (entity.getIsIncludePendingData() != null) {
            bean.setIsIncludePendingData(entity.getIsIncludePendingData());
            bean.setIsIncludePendingDataText(entity.getIsIncludePendingData() == 1
                    ? messageSource.getMessage(OnOffEnum.ON.resourceName(), null, locale)
                    : messageSource.getMessage(OnOffEnum.OFF.resourceName(), null, locale));
        }
        bean.setVirtualContract(entity.getVirtualContract());
        bean.setContractReturnInfo(entity.getContractReturnInfo());
        bean.setCountersignatureReturn(entity.getCountersignatureReturn());
        bean.setMainProvisions(entity.getMainProvisions());
        bean.setRemark(entity.getRemark());

		if (entity.getFlowStartDate() != null) {
			bean.setFlowStartDate(DateUtils.date2String(entity.getFlowStartDate(), Constants.DATE_FORMAT));
		}

		if (entity.getFlowEndDate() != null) {
			bean.setFlowEndDate(DateUtils.date2String(entity.getFlowEndDate(), Constants.DATE_FORMAT));
		}
		bean.setFlowStatus(entity.getFlowStatus());
		bean.setFlowStatusText(
				messageSource.getMessage(FlowStatusEnum.getResouceName(entity.getFlowStatus()), null, locale));


        Person entryPerson = entity.getEntryPerson();
        if (entryPerson != null) {
            bean.setEntryPerson_(entryPerson.getId());
            // bean.setEntryPersonText(entryPerson.getPersonName());
        }

        if (entity.getEntryTime() != null) {
            bean.setEntryTime(DateUtils.date2String(entity.getEntryTime(), Constants.DATE_FORMAT));
        }
        
        // 附件处理
        ArchiveBeanConverter abConverter = new ArchiveBeanConverter();
        Set<PaymentContractAttachment> paymentContractAttachments =
                entity.getPaymentContractAttachments();
        for (PaymentContractAttachment pa : paymentContractAttachments) {

            bean.getArchivesList()
                    .add(abConverter.fromEntity(pa.getArchive(), messageSource, locale));
        }
        return bean;
    }

    @Override
    public PaymentContract toEntity(PaymentContractBean bean, PaymentContract oldEntity,
            AbstractSessionUserBean user, Date now) {

        PaymentContract entity = oldEntity;

        if (entity == null) { // 表示新建
            entity = new PaymentContract();

            entity.setCreator(user.getSessionUserId());
            entity.setcDatetime(now);
            
            entity.setEntryTime(now);
        } else { // 表示修改
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
            for (PaymentContractAttachment dbAttachment : oldEntity.getPaymentContractAttachments()) {
                if (!map.containsKey(dbAttachment.getArchive().getId())) {
                    entity.getAttachmentsToDelete().add(dbAttachment);
                }

            }
        }

        bean.setId(entity.getId()); // ID必须赋值
        entity.setmDatetime(now);

        if (!StringUtils.isEmpty(bean.getPurchaseOrder_())) {
            PurchaseOrder purchaseOrder = new PurchaseOrder();
            purchaseOrder.setId(bean.getPurchaseOrder_());
            entity.setPurchaseOrder(purchaseOrder);
        }

        if (!StringUtils.isEmpty(bean.getProject_())) {
            Project project = new Project();
            project.setId(bean.getProject_());
            project.setProjectName(bean.getProjectText());
            entity.setProject(project);
        } else {
        	// TODO: 【日报20200611.txt】合同对应采购编号不是必填项，空的也能录入
        	// 项目id固定为一个CENI项目，ID固定
        	Project project = new Project();
        	project.setId(OaProcessConstant.PROJECT_ID);
        	entity.setProject(project);
        }

        if (!StringUtils.isEmpty(bean.getVirtualOrg_())) {
            VirtualOrg virtualOrg = new VirtualOrg();
            virtualOrg.setId(bean.getVirtualOrg_());
            entity.setVirtualOrg(virtualOrg);
        }
        entity.setContractName(bean.getContractName());
        entity.setSerialNumber(bean.getSerialNumber());
        entity.setContractYear(bean.getContractYear());
        entity.setContractCount(bean.getContractCount());
        entity.setContractAmount(bean.getContractAmount());
        entity.setIsTemporaryPricing(bean.getIsTemporaryPricing());
        entity.setAmountExcludingTax(bean.getAmountExcludingTax());
        if (!StringUtils.isEmpty(bean.getContractKind_())) {
        	ContractCategory contractCategory = new ContractCategory();
        	contractCategory.setId(bean.getContractKind_());
        	entity.setContractKind(contractCategory);
        }
        entity.setContractAttribute(bean.getContractAttribute());

        if (!StringUtils.isEmpty(bean.getContractTime())) {
            try {
                entity.setContractTime(
                        DateUtils.string2Date(bean.getContractTime(), Constants.DATE_FORMAT));
            } catch (ParseException e) {

                // do nothing
            }
        }

        entity.setOntractAddress(bean.getOntractAddress());

        if (!StringUtils.isEmpty(bean.getStartTime())) {
            try {
                entity.setStartTime(
                        DateUtils.string2Date(bean.getStartTime(), Constants.DATE_FORMAT));
            } catch (ParseException e) {

                // do nothing
            }
        }

        if (!StringUtils.isEmpty(bean.getEndTime())) {
            try {
                entity.setEndTime(DateUtils.string2Date(bean.getEndTime(), Constants.DATE_FORMAT));
            } catch (ParseException e) {

                // do nothing
            }
        }
        entity.setPartyA(bean.getPartyA());
        entity.setPartyB(bean.getPartyB());

        if (!StringUtils.isEmpty(bean.getSigningPeople_())) {
            Person signingPeople = new Person();
            signingPeople.setId(bean.getSigningPeople_());
            entity.setSigningPeople(signingPeople);
        }

        entity.setPartyBContact(bean.getPartyBContact());
        entity.setPartyAContactInfo(bean.getPartyAContactInfo());
        entity.setPartyBContactInfo(bean.getPartyBContactInfo());
        entity.setBriefIntroduction(bean.getBriefIntroduction());
        if (!StringUtils.isEmpty(bean.getSealType_())) {
        	DataOption sealType = new DataOption();
        	sealType.setId(bean.getSealType_());
        	entity.setSealType(sealType);
        }
        entity.setIsIncludePendingData(bean.getIsIncludePendingData());
        entity.setVirtualContract(bean.getVirtualContract());
        entity.setContractReturnInfo(bean.getContractReturnInfo());
        entity.setCountersignatureReturn(bean.getCountersignatureReturn());
        entity.setMainProvisions(bean.getMainProvisions());
        entity.setRemark(bean.getRemark());
        
        if (!StringUtils.isEmpty(bean.getFlowStartDate())) {
			try {
				entity.setFlowStartDate(DateUtils.string2Date(bean.getFlowStartDate(), Constants.DATE_FORMAT));
			} catch (ParseException e) {
				// do nothing
			}
		}

		if (!StringUtils.isEmpty(bean.getFlowEndDate())) {
			try {
				entity.setFlowEndDate(DateUtils.string2Date(bean.getFlowEndDate(), Constants.DATE_FORMAT));
			} catch (ParseException e) {
				// do nothing
			}
		}
		entity.setFlowStatus(bean.getFlowStatus());

        if (!StringUtils.isEmpty(bean.getEntryPerson_())) {
            Person entryPerson = new Person();
            entryPerson.setId(bean.getEntryPerson_());
            entity.setEntryPerson(entryPerson);
        }

        if (!StringUtils.isEmpty(bean.getEntryTime())) {
            try {
                entity.setEntryTime(
                        DateUtils.string2Date(bean.getEntryTime(), Constants.DATE_FORMAT));
            } catch (ParseException e) {

                // do nothing
            }
        }
        
        if (bean.getDataSource() != null) {
        	entity.setDataSource(bean.getDataSource());
        }
        
        // 附件处理，此处只需处理新增加的ProjectAttachment
        Set<PaymentContractAttachment> paymentContractAttachments = new HashSet<>();

        ArchiveBeanConverter abConverter = new ArchiveBeanConverter();
        for (ArchiveBean ab : bean.getArchivesList()) {
        	paymentContractAttachments.add(this.toAttachmentEntity(ab, abConverter, entity,
                    ProjectAttachmentTypeEnum.ARCHIVE.value(), user, now));
        }

        entity.setPaymentContractAttachments(paymentContractAttachments);

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
    public PaymentContractAttachment toAttachmentEntity(ArchiveBean ab,
            ArchiveBeanConverter abConverter, PaymentContract entity, int attachmentType,
            AbstractSessionUserBean user, Date now) {

    	PaymentContractAttachment attachment = new PaymentContractAttachment();
        attachment.setPaymentContract(entity);

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

        ab.setPid(entity.getProject().getId() + "_" + FolderPath.PAYMENT_CONTRACT);

        ab.setPath(Constants.SLASH + Constants.COMMA + year + Constants.COMMA
                + entity.getProject().getId() + Constants.COMMA
                + entity.getProject().getId() + "_" + FolderPath.SYSTEM_PLACE_ON_FILE + Constants.COMMA
                + entity.getProject().getId() + "_" + FolderPath.PAYMENT_CONTRACT);
        
        Archive aentity = abConverter.toEntity(ab, null, user, now);
        
        String relativePath = File.separator + year + File.separator + entity.getProject().getId() + File.separator + entity.getProject().getId() + "_"
                + FolderPath.SYSTEM_PLACE_ON_FILE + File.separator + entity.getProject().getId() + "_"
                + FolderPath.PAYMENT_CONTRACT + File.separator + aentity.getId();

        ab.setRelativePath(relativePath);
        aentity.setRelativePath(relativePath);

        attachment.setArchive(aentity);
        return attachment;
    }

}
