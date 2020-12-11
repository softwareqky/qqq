package project.edge.domain.converter;

import org.springframework.context.MessageSource;
import org.springframework.util.StringUtils;

import java.util.Date;
import java.util.Locale;

import garage.origin.common.constant.OnOffEnum;
import garage.origin.domain.view.AbstractSessionUserBean;
import garage.origin.domain.view.converter.ViewConverter;
import project.edge.domain.entity.Archive;
import project.edge.domain.entity.DataOption;
import project.edge.domain.entity.RelatedUnit;
import project.edge.domain.view.RelatedUnitBean;

/**
 * @author angel_000
 *         转换往来单位对应的view和entity的转换器。
 */
public class RelatedUnitBeanConverter implements ViewConverter<RelatedUnit, RelatedUnitBean> {

    @Override
    public RelatedUnitBean fromEntity(RelatedUnit entity, MessageSource messageSource,
            Locale locale) {
        // TODO Auto-generated method stub
        RelatedUnitBean bean = new RelatedUnitBean();
        bean.setId(entity.getId());
        bean.setUnitName(entity.getUnitName());
        bean.setUnitCode(entity.getUnitCode());

        DataOption unitType = entity.getUnitType();

        if (unitType != null) {
            bean.setUnitType_(unitType.getId());
            bean.setUnitTypeText(unitType.getOptionName());
        }
        DataOption region = entity.getRegion();

        if (region != null) {
            bean.setRegion_(region.getId());
            bean.setRegionText(region.getOptionName());
        }
        DataOption group = entity.getUnitGroup();

        if (group != null) {
            bean.setUnitGroup_(group.getId());
            bean.setUnitGroupText(group.getOptionName());
        }
        DataOption unitProperty = entity.getUnitProperty();

        if (unitProperty != null) {
            bean.setUnitProperty_(unitProperty.getId());
            bean.setUnitPropertyText(unitProperty.getOptionName());
        }

        DataOption businessType = entity.getBusinessType();

        if (businessType != null) {
            bean.setBusinessType_(businessType.getId());
            bean.setBusinessTypeText(businessType.getOptionName());
        }

        bean.setBrand(entity.getBrand());
        bean.setProductInfo(entity.getProductInfo());
        bean.setContact(entity.getContact());
        bean.setContactMobile(entity.getContactMobile());
        bean.setUnitPhone(entity.getUnitPhone());
        bean.setFax(entity.getFax());
        bean.setLegalRepresentative(entity.getLegalRepresentative());
        bean.setQulification(entity.getQulification());
        bean.setUnitDesc(entity.getUnitDesc());
        bean.setZipCode(entity.getZipCode());
        bean.setCountry(entity.getCountry());
        bean.setEmail(entity.getEmail());
        bean.setWebsite(entity.getWebsite());
        bean.setBusinessLicense(entity.getBusinessLicense());
        bean.setTaxNum(entity.getTaxNum());
        bean.setBankAccount(entity.getBankAccount());
        bean.setBankDeposit(entity.getBankDeposit());
        bean.setDelegation(entity.getDelegation());
        bean.setFundAbility(entity.getFundAbility());
        bean.setFixedAsset(entity.getFixedAsset());
        bean.setIdCardNum(entity.getIdCardNum());

        Archive attachment = entity.getAttachment();
        if (attachment != null) {
            bean.setAttachment_(attachment.getArchiveName());
            bean.setAttachmentText(attachment.getArchiveName());
        }

        bean.setIsRecommended(entity.getIsRecommended());
        bean.setIsRecommendedText(entity.getIsRecommended() == 1
                ? messageSource.getMessage(OnOffEnum.ON.resourceName(), null, locale)
                : messageSource.getMessage(OnOffEnum.OFF.resourceName(), null, locale));

        bean.setIsBlackList(entity.getIsBlacklist());
        bean.setIsBlackListText(entity.getIsBlacklist() == 1
                ? messageSource.getMessage(OnOffEnum.ON.resourceName(), null, locale)
                : messageSource.getMessage(OnOffEnum.OFF.resourceName(), null, locale));

        return bean;
    }

    @Override
    public RelatedUnit toEntity(RelatedUnitBean bean, RelatedUnit oldEntity,
            AbstractSessionUserBean user, Date now) {
        // TODO Auto-generated method stub

        RelatedUnit entity = oldEntity;
        if (entity == null) { // 表示新建
            entity = new RelatedUnit();

            entity.setIsRecommended(OnOffEnum.OFF.value());
            entity.setCreator(user.getSessionUserId());
            entity.setcDatetime(now);

        } else {
            entity.setModifier(user.getSessionUserId());
            entity.setIsDeleted(OnOffEnum.OFF.value());
        }

        bean.setId(entity.getId()); // ID必须赋值
        entity.setmDatetime(now);

        if (!StringUtils.isEmpty(bean.getUnitType_())) {
            DataOption unitType = new DataOption();
            unitType.setId(bean.getUnitType_());
            entity.setUnitType(unitType);
        }

        if (!StringUtils.isEmpty(bean.getRegion_())) {
            DataOption region = new DataOption();
            region.setId(bean.getRegion_());
            entity.setRegion(region);
        }

        if (!StringUtils.isEmpty(bean.getUnitGroup_())) {
            DataOption unitGroup = new DataOption();
            unitGroup.setId(bean.getUnitGroup_());
            entity.setUnitGroup(unitGroup);
        }

        if (!StringUtils.isEmpty(bean.getUnitProperty_())) {
            DataOption unitProperty = new DataOption();
            unitProperty.setId(bean.getUnitProperty_());
            entity.setUnitProperty(unitProperty);
        }

        if (!StringUtils.isEmpty(bean.getBusinessType_())) {
            DataOption businessType = new DataOption();
            businessType.setId(bean.getBusinessType_());
            entity.setBusinessType(businessType);
        }

        entity.setIsBlacklist(bean.getIsBlackList());
        
        entity.setUnitName(bean.getUnitName());
        entity.setUnitCode(bean.getUnitCode());
        entity.setBrand(bean.getBrand());
        entity.setProductInfo(bean.getProductInfo());
        entity.setContact(bean.getContact());
        entity.setContactMobile(bean.getContactMobile());
        entity.setUnitPhone(bean.getUnitPhone());
        entity.setFax(bean.getFax());
        entity.setLegalRepresentative(bean.getLegalRepresentative());
        entity.setQulification(bean.getQulification());
        entity.setUnitDesc(bean.getUnitDesc());
        entity.setUnitAddress(bean.getUnitAddress());
        entity.setZipCode(bean.getZipCode());
        entity.setCountry(bean.getCountry());
        entity.setEmail(bean.getEmail());
        entity.setWebsite(bean.getWebsite());
        entity.setBusinessLicense(bean.getBusinessLicense());
        entity.setTaxNum(bean.getTaxNum());
        entity.setBankAccount(bean.getBankAccount());
        entity.setBankDeposit(bean.getBankDeposit());
        entity.setDelegation(bean.getDelegation());
        entity.setFundAbility(bean.getFundAbility());
        entity.setFixedAsset(bean.getFixedAsset());
        entity.setIdCardNum(bean.getIdCardNum());

        if (!StringUtils.isEmpty(bean.getAttachment_())) {
            Archive attachment = new Archive();
            attachment.setId(bean.getAttachment_());
            entity.setAttachment(attachment);
        }

        entity.setIsRecommended(bean.getIsRecommended());
        return entity;
    }

}
