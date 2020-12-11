/**
 * 
 */
package project.edge.domain.converter;

import java.io.File;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Locale;
import java.util.Map;
import java.util.Set;

import org.springframework.context.MessageSource;
import org.springframework.util.StringUtils;

import garage.origin.common.constant.Constants;
import garage.origin.domain.view.AbstractSessionUserBean;
import garage.origin.domain.view.converter.ViewConverter;
import project.edge.common.constant.FolderPath;
import project.edge.domain.entity.Archive;
import project.edge.domain.entity.DataOption;
import project.edge.domain.entity.Tenderee;
import project.edge.domain.entity.TendereeAttachment;
import project.edge.domain.view.ArchiveBean;
import project.edge.domain.view.TendereeBean;


/**
 * @author angel_000
 *
 */
public class TendereeBeanConverter implements ViewConverter<Tenderee, TendereeBean> {

    @Override
    public TendereeBean fromEntity(Tenderee entity, MessageSource messageSource, Locale locale) {
        TendereeBean bean = new TendereeBean();
        bean.setId(entity.getId());
        bean.setAddress(entity.getAddress());
        bean.setBankAccount(entity.getBankAccount());
        DataOption unitProperty = entity.getUnitProperty();

        if (unitProperty != null) {
            bean.setUnitProperty_(unitProperty.getId());
            bean.setUnitPropertyText(unitProperty.getOptionName());
        }

        DataOption businessType = entity.getBusinessCategory();

        if (businessType != null) {
            bean.setBusinessCategory_(businessType.getId());
            bean.setBusinessCategoryText(businessType.getOptionName());
        }
        
        bean.setBusinessLicense(entity.getBusinessLicense());
        bean.setClientele(entity.getClientele());
        bean.setCompanyName(entity.getCompanyName());
        bean.setContactNumber(entity.getContactNumber());
        bean.setContacts(entity.getContacts());
        bean.setCorporateNumber(entity.getCorporateNumber());
        bean.setCorporateRepresentative(entity.getCorporateRepresentative());
        bean.setCountry(entity.getCountry());
        bean.setDepositBank(entity.getDepositBank());
        bean.setDutyParagraph(entity.getDutyParagraph());
        bean.setEmail(entity.getEmail());
        bean.setFax(entity.getFax());
        bean.setFixedAssets(entity.getFixedAssets());
        bean.setIdCard(entity.getIdCard());
        bean.setIsSupplier(entity.getIsSupplier());
        bean.setPostalCode(entity.getPostalCode());
        bean.setProductOverview(entity.getProductOverview());
        bean.setQualifications(entity.getQualifications());
        bean.setRaiseFundsAbility(entity.getRaiseFundsAbility());
        bean.setSupplierCode(entity.getSupplierCode());
        bean.setUnitIntroduction(entity.getUnitIntroduction());
        bean.setWebsite(entity.getWebsite());
        bean.setFixedAssets(entity.getFixedAssets());

        // 附件处理
        ArchiveBeanConverter abConverter = new ArchiveBeanConverter();
        Set<TendereeAttachment> archives = entity.getArchives();

        for (TendereeAttachment archive : archives) {
            bean.getArchiveList().add(abConverter.fromEntity(archive.getArchive(), messageSource, locale));
        }
        return bean;
    }

    @Override
    public Tenderee toEntity(TendereeBean bean, Tenderee oldEntity, AbstractSessionUserBean user,
            Date now) {
        // TODO Auto-generated method stub
        Tenderee entity = oldEntity;
        //Set<Archive> archives = new HashSet<>();

        if (entity == null) { // 表示新建
            entity = new Tenderee();

            entity.setCreator(user.getSessionUserId());
            entity.setcDatetime(now);
        } else { // 表示修改
            // 在Controller.postUpdate中，需要比较新老entity的关联的档案文件，所以必须clone
            // clone之后，清空外键关联字段的值
            entity = oldEntity.clone();
            entity.setArchives(new HashSet<>());

            entity.setModifier(user.getSessionUserId());

            // 修改后，仍然保留的档案文件
            entity.setArchives(new HashSet<>());
            
            Map<String, String> map = new HashMap<>();
            for (String aid : bean.getArchiveReservedList()) {
                map.put(aid, aid);
            }
            for (String aid : bean.getArchiveReservedList()) {
                map.put(aid, aid);
            }

            // 找出需要删除的Archive
            for (TendereeAttachment dbArchive : oldEntity.getArchives()) {
                if (!map.containsKey(dbArchive.getId())) {
                    entity.getArchivesToDelete().add(dbArchive);
                }
            }
        }

        bean.setId(entity.getId()); // ID必须赋值
        entity.setmDatetime(now);
        entity.setAddress(bean.getAddress());
        entity.setBankAccount(bean.getBankAccount());
        if (!StringUtils.isEmpty(bean.getUnitProperty_())) {
            DataOption unitProperty = new DataOption();
            unitProperty.setId(bean.getUnitProperty_());
            entity.setUnitProperty(unitProperty);
        }

        if (!StringUtils.isEmpty(bean.getBusinessCategory_())) {
            DataOption businessType = new DataOption();
            businessType.setId(bean.getBusinessCategory_());
            entity.setBusinessCategory(businessType);
        }
        entity.setBusinessLicense(bean.getBusinessLicense());
        entity.setClientele(bean.getClientele());
        entity.setCompanyName(bean.getCompanyName());
        entity.setContactNumber(bean.getContactNumber());
        entity.setContacts(bean.getContacts());
        entity.setCorporateNumber(bean.getCorporateNumber());
        entity.setCorporateRepresentative(bean.getCorporateRepresentative());
        entity.setCountry(bean.getCountry());
        entity.setDepositBank(bean.getDepositBank());
        entity.setDutyParagraph(bean.getDutyParagraph());
        entity.setEmail(bean.getEmail());
        entity.setFax(bean.getFax());
        entity.setFixedAssets(bean.getFixedAssets());
        entity.setIdCard(bean.getIdCard());
        entity.setIsSupplier(bean.getIsSupplier());
        entity.setPostalCode(bean.getPostalCode());
        entity.setProductOverview(bean.getProductOverview());
        entity.setQualifications(bean.getQualifications());
        entity.setRaiseFundsAbility(bean.getRaiseFundsAbility());
        entity.setSupplierCode(bean.getSupplierCode());
        entity.setUnitIntroduction(bean.getUnitIntroduction());
        entity.setWebsite(bean.getWebsite());
        entity.setFixedAssets(bean.getFixedAssets());

        // 附件处理，此处只需处理新增加的ProjectAttachment
        Set<TendereeAttachment> tendereeAttachments = new HashSet<>();

        ArchiveBeanConverter abConverter = new ArchiveBeanConverter();

        for (ArchiveBean ab : bean.getArchiveList()) {
        	tendereeAttachments.add(this.toAttachmentEntity(ab, abConverter, entity,user, now));
        }
        entity.setArchives(tendereeAttachments);

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
    private TendereeAttachment toAttachmentEntity(ArchiveBean ab, ArchiveBeanConverter abConverter,
    		Tenderee entity, AbstractSessionUserBean user, Date now) {

    	TendereeAttachment attachment = new TendereeAttachment();
        attachment.setTenderee(entity);

        /**
         * 第零层：根
         * 第一层：FolderPath.PROJECT(DIR)
         * 第二层：Project.id(DIR)
         * 第三层：Archive文件
         */
        ab.setLevel(3);
        ab.setPid(entity.getId());
        ab.setPath(Constants.SLASH + Constants.COMMA + FolderPath.TENDERRE + Constants.COMMA
                + entity.getId());
        Archive aentity = abConverter.toEntity(ab, null, user, now);

        // "\project\guid\xxx.doc"
        // String extension = FilenameUtils.getExtension(ab.getArchiveName()).toLowerCase();
        // String relativePath = File.separator + FolderPath.PROJECT + File.separator
        // + entity.getId() + File.separator + aentity.getId() + Constants.DOT + extension;
        String relativePath = File.separator + FolderPath.TENDERRE + File.separator + entity.getId()
                + File.separator + aentity.getId();
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
