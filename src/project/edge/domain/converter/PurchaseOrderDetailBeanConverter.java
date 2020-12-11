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

import javax.annotation.Resource;

import org.springframework.context.MessageSource;
import org.springframework.util.StringUtils;

import garage.origin.common.constant.Constants;
import garage.origin.common.util.DateUtils;
import garage.origin.domain.view.AbstractSessionUserBean;
import garage.origin.domain.view.converter.ViewConverter;
import project.edge.common.constant.FolderPath;
import project.edge.common.constant.ProjectAttachmentTypeEnum;
import project.edge.dao.bidding.PurchaseOrderDao;
import project.edge.domain.entity.Archive;
import project.edge.domain.entity.BudgetEstimateSum;
import project.edge.domain.entity.City;
import project.edge.domain.entity.DataOption;
import project.edge.domain.entity.Person;
import project.edge.domain.entity.Project;
import project.edge.domain.entity.Province;
import project.edge.domain.entity.PurchaseOrder;
import project.edge.domain.entity.PurchaseOrderDetail;
import project.edge.domain.entity.PurchaseOrderDetailAttachment;
import project.edge.domain.entity.Site;
import project.edge.domain.view.ArchiveBean;
import project.edge.domain.view.PurchaseOrderDetailBean;

public class PurchaseOrderDetailBeanConverter
        implements ViewConverter<PurchaseOrderDetail, PurchaseOrderDetailBean> {
	
	@Resource
	private PurchaseOrderDao purchaseOrderDao;

    @Override
    public PurchaseOrderDetailBean fromEntity(PurchaseOrderDetail entity,
            MessageSource messageSource, Locale locale) {
        PurchaseOrderDetailBean bean = new PurchaseOrderDetailBean();
        PurchaseOrder purchaseOrder = entity.getPurchaseOrder();
        if (purchaseOrder != null) {
            bean.setPurchaseOrder_(purchaseOrder.getId());
            bean.setPurchaseOrderText(purchaseOrder.getPurchaseOrderNo());
        }
        bean.setParentBudgetname(entity.getParentBudgetname());
        bean.setMaterialName(entity.getMaterialName());
        bean.setSpecificationType(entity.getSpecificationType());
        bean.setPurchaseQuantity(entity.getPurchaseQuantity());
        bean.setId(entity.getId());
        bean.setMeasurementUnit(entity.getMeasurementUnit());
        bean.setUnitPrice(entity.getUnitPrice());
        bean.setSumMoney(entity.getSumMoney());
        bean.setUsageInfo(entity.getUsageInfo());
        bean.setRemark(entity.getRemark());
        DataOption purchaseKind = entity.getPurchaseKind();
        if (purchaseKind != null) {
        	bean.setPurchaseKind_(purchaseKind.getId());
        	bean.setPurchaseKindText(purchaseKind.getOptionName());
        }
        Province province = entity.getProvince();
        if (province != null) {
            bean.setProvince_(province.getId());
            bean.setProvinceText(province.getProvinceName());
        }

        City city = entity.getCity();
        if (city != null) {
            bean.setCity_(city.getId());
            bean.setCityText(city.getCityName());
        }
        
        Site site = entity.getSite();
        if (site != null) {
			bean.setSite_(site.getId());
			bean.setSiteText(site.getStationName());
		}
        
        if (entity.getReserveTime() != null) {
            bean.setReserveTime(DateUtils.date2String(entity.getReserveTime(), Constants.DATE_FORMAT));
        }

        Person applicant = entity.getApplicant();
        if (applicant != null) {
            bean.setApplicant_(applicant.getId());
            bean.setApplicantText(applicant.getPersonName());
        }
        
        bean.setPaymentNum(entity.getPaymentNum());
        bean.setPaymentAmount(entity.getPaymentAmount());
        bean.setTotalPaymentNum(entity.getTotalPaymentNum());
        bean.setTotalPaymentAmount(entity.getTotalPaymentAmount());
        if (!StringUtils.isEmpty(entity.getBudgetEstimateSum())) {
        	bean.setBudgetEstimateSum_(entity.getBudgetEstimateSum().getId());
            bean.setBudgetEstimateSumText(entity.getBudgetEstimateSum().getName());
            bean.setBudgetEstimateCode(entity.getBudgetEstimateSum().getCode());
        }
        
        // 附件处理
        ArchiveBeanConverter abConverter = new ArchiveBeanConverter();
        Set<PurchaseOrderDetailAttachment> purchaseOrderDetailAttachments =
                entity.getPurchaseOrderDetailAttachments();
        for (PurchaseOrderDetailAttachment pa : purchaseOrderDetailAttachments) {

            bean.getArchivesList()
                    .add(abConverter.fromEntity(pa.getArchive(), messageSource, locale));
        }
        return bean;
    }

    @Override
    public PurchaseOrderDetail toEntity(PurchaseOrderDetailBean bean, PurchaseOrderDetail oldEntity,
            AbstractSessionUserBean user, Date now) {
        // TODO Auto-generated method stub
        PurchaseOrderDetail entity = oldEntity;

        if (entity == null) { // 表示新建
            entity = new PurchaseOrderDetail();

            entity.setCreator(user.getSessionUserId());
            entity.setcDatetime(now);

        } else {
            entity = oldEntity.clone();
            entity.setModifier(user.getSessionUserId());

            // 修改后，仍然保留的档案文件
            Map<String, String> map = new HashMap<>();
            for (String aid : bean.getArchivesReservedList()) {
                map.put(aid, aid);
            }

            // 找出需要删除的Archive
            for (PurchaseOrderDetailAttachment dbAttachment : oldEntity
                    .getPurchaseOrderDetailAttachments()) {
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
        	entity.setProject(project);
        }
        entity.setParentBudgetname(bean.getParentBudgetname());
        entity.setMaterialName(bean.getMaterialName());
        entity.setSpecificationType(bean.getSpecificationType());
        entity.setPurchaseQuantity(bean.getPurchaseQuantity());
        entity.setMeasurementUnit(bean.getMeasurementUnit());
        entity.setUnitPrice(bean.getUnitPrice());
        entity.setSumMoney(bean.getSumMoney());
        entity.setUsageInfo(bean.getUsageInfo());
        entity.setRemark(bean.getRemark());

        if (!StringUtils.isEmpty(bean.getPurchaseKind_())) {
        	DataOption purchaseKind = new DataOption();
        	purchaseKind.setId(bean.getPurchaseKind_());
        	entity.setPurchaseKind(purchaseKind);
        }
        if (!StringUtils.isEmpty(bean.getProvince_())) {
            Province province = new Province();
            province.setId(bean.getProvince_());
            entity.setProvince(province);
        }

        if (!StringUtils.isEmpty(bean.getCity_())) {
            City city = new City();
            city.setId(bean.getCity_());
            entity.setCity(city);
        }
        
        if (!StringUtils.isEmpty(bean.getSite_())) {
			Site site = new Site();
			site.setId(bean.getSite_());
			entity.setSite(site);
		}
        
        if (!StringUtils.isEmpty(bean.getReserveTime())) {
            try {
				entity.setReserveTime(DateUtils.string2Date(bean.getReserveTime(), Constants.DATE_FORMAT));
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
        }
        
        if (!StringUtils.isEmpty(bean.getApplicant_())) {
            Person applicant = new Person();
            applicant.setId(bean.getApplicant_());
            entity.setApplicant(applicant);
        }
        
        entity.setPaymentNum(bean.getPaymentNum());
        entity.setPaymentAmount(bean.getPaymentAmount());
        entity.setTotalPaymentNum(bean.getTotalPaymentNum());
        entity.setTotalPaymentAmount(bean.getTotalPaymentAmount());
        
        BudgetEstimateSum sum = new BudgetEstimateSum();
        sum.setId(bean.getBudgetEstimateSum_());
        sum.setName(bean.getBudgetEstimateSumText());
        entity.setBudgetEstimateSum(sum);
        
        // 附件处理，此处只需处理新增加的ProjectAttachment
        Set<PurchaseOrderDetailAttachment> purchaseOrderDetailAttachments = new HashSet<>();

        ArchiveBeanConverter abConverter = new ArchiveBeanConverter();
        for (ArchiveBean ab : bean.getArchivesList()) {
            purchaseOrderDetailAttachments.add(this.toAttachmentEntity(ab, abConverter, entity,
                    ProjectAttachmentTypeEnum.ARCHIVE.value(), user, now));
        }

        entity.setPurchaseOrderDetailAttachments(purchaseOrderDetailAttachments);
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
    private PurchaseOrderDetailAttachment toAttachmentEntity(ArchiveBean ab,
            ArchiveBeanConverter abConverter, PurchaseOrderDetail entity, int attachmentType,
            AbstractSessionUserBean user, Date now) {

        PurchaseOrderDetailAttachment attachment = new PurchaseOrderDetailAttachment();
        attachment.setPurchaseOrderDetail(entity);

//changed start by huang 
//        /**
//         * 第零层：根
//         * 第一层：FolderPath.PROJECT(DIR)
//         * 第二层：Project.id(DIR)
//         * 第三层：Archive文件
//         */
//        ab.setLevel(3);
//        ab.setPid(entity.getId());
//        ab.setPath(Constants.SLASH + Constants.COMMA + FolderPath.PURCHASEORDERDETAIL + Constants.COMMA
//                + entity.getId());
//        Archive aentity = abConverter.toEntity(ab, null, user, now);
//
//        // "\project\guid\xxx.doc"
//        // String extension = FilenameUtils.getExtension(ab.getArchiveName()).toLowerCase();
//        // String relativePath = File.separator + FolderPath.PROJECT + File.separator
//        // + entity.getId() + File.separator + aentity.getId() + Constants.DOT + extension;
//        String relativePath = File.separator + FolderPath.PURCHASEORDERDETAIL + File.separator
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

        ab.setPid(entity.getProject().getId() + "_" + FolderPath.PURCHASEORDERDETAIL);

        ab.setPath(Constants.SLASH + Constants.COMMA + year + Constants.COMMA
                + entity.getProject().getId() + Constants.COMMA
                + entity.getProject().getId() + "_" + FolderPath.SYSTEM_PLACE_ON_FILE + Constants.COMMA
                + entity.getProject().getId() + "_" + FolderPath.PURCHASEORDERDETAIL);
        
        Archive aentity = abConverter.toEntity(ab, null, user, now);
        
        String relativePath = File.separator + year + File.separator + entity.getProject().getId() + File.separator + entity.getProject().getId() + "_"
                + FolderPath.SYSTEM_PLACE_ON_FILE + File.separator + entity.getProject().getId() + "_"
                + FolderPath.PURCHASEORDERDETAIL + File.separator + aentity.getId();        
        
//changed end by  huang 
        
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
