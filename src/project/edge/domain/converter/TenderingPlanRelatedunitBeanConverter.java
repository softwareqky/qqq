package project.edge.domain.converter;

import java.text.ParseException;
import java.util.Date;
import java.util.Locale;

import org.apache.commons.lang3.StringUtils;
import org.springframework.context.MessageSource;

import garage.origin.common.constant.Constants;
import garage.origin.common.util.DateUtils;
import garage.origin.domain.view.AbstractSessionUserBean;
import garage.origin.domain.view.converter.ViewConverter;
import project.edge.common.constant.TenderResultTypeEnum;
import project.edge.domain.entity.DataOption;
import project.edge.domain.entity.RelatedUnit;
import project.edge.domain.entity.TenderingPlan;
import project.edge.domain.entity.TenderingPlanRelatedunit;
import project.edge.domain.view.TenderingPlanRelatedunitBean;

/**
 * @author angel_000
 *         转换往来单位对应的view和entity的转换器。
 */
public class TenderingPlanRelatedunitBeanConverter implements ViewConverter<TenderingPlanRelatedunit, TenderingPlanRelatedunitBean> {

    @Override
    public TenderingPlanRelatedunitBean fromEntity(TenderingPlanRelatedunit entity, MessageSource messageSource,
            Locale locale) {
        // TODO Auto-generated method stub
    	TenderingPlanRelatedunitBean bean = new TenderingPlanRelatedunitBean();
        bean.setId(entity.getId());
        TenderingPlan tenderingPlan = entity.getTenderingPlan();
        if (tenderingPlan != null) {
        	bean.setTenderingPlan_(tenderingPlan.getId());
        	bean.setTenderingPlanText(tenderingPlan.getTenderingName());
        }
        
        RelatedUnit relatedUnit = entity.getRelatedUnit();
        if (relatedUnit != null) {
        	bean.setRelatedUnit_(relatedUnit.getId());
        	bean.setRelatedUnitText(relatedUnit.getUnitName());
        }

        bean.setTenderingResult(entity.getTenderingResult());
        bean.setTenderingResultText(messageSource.getMessage(TenderResultTypeEnum.getResouceName(entity.getTenderingResult()), null, locale));
        bean.setTenderWinTime(DateUtils.date2String(entity.getTenderWinTime(), Constants.DATE_FORMAT));
        
        bean.setUnitName(entity.getRelatedUnit().getUnitName());
        bean.setUnitCode(entity.getRelatedUnit().getUnitCode());
//        DataOption unitType = entity.getRelatedUnit().getUnitType();
//
//        if (unitType != null) {
//            bean.setUnitType_(unitType.getId());
//            bean.setUnitTypeText(unitType.getOptionName());
//        }
//        DataOption region = entity.getRelatedUnit().getRegion();
//
//        if (region != null) {
//            bean.setRegion_(region.getId());
//            bean.setRegionText(region.getOptionName());
//        }
//        DataOption group = entity.getRelatedUnit().getUnitGroup();
//
//        if (group != null) {
//            bean.setUnitGroup_(group.getId());
//            bean.setUnitGroupText(group.getOptionName());
//        }
//        DataOption unitProperty = entity.getRelatedUnit().getUnitProperty();
//
//        if (unitProperty != null) {
//            bean.setUnitProperty_(unitProperty.getId());
//            bean.setUnitPropertyText(unitProperty.getOptionName());
//        }
//
//        DataOption businessType = entity.getRelatedUnit().getBusinessType();
//
//        if (businessType != null) {
//            bean.setBusinessType_(businessType.getId());
//            bean.setBusinessTypeText(businessType.getOptionName());
//        }

        bean.setBrand(entity.getRelatedUnit().getBrand());
        bean.setProductInfo(entity.getRelatedUnit().getProductInfo());
        bean.setContact(entity.getRelatedUnit().getContact());
        bean.setContactMobile(entity.getRelatedUnit().getContactMobile());
        bean.setUnitPhone(entity.getRelatedUnit().getUnitPhone());
        bean.setFax(entity.getRelatedUnit().getFax());
        bean.setLegalRepresentative(entity.getRelatedUnit().getLegalRepresentative());
        bean.setQulification(entity.getRelatedUnit().getQulification());
        bean.setUnitDesc(entity.getRelatedUnit().getUnitDesc());
        bean.setZipCode(entity.getRelatedUnit().getZipCode());
        bean.setCountry(entity.getRelatedUnit().getCountry());
        bean.setEmail(entity.getRelatedUnit().getEmail());
        bean.setWebsite(entity.getRelatedUnit().getWebsite());
        bean.setBusinessLicense(entity.getRelatedUnit().getBusinessLicense());
        bean.setTaxNum(entity.getRelatedUnit().getTaxNum());
        bean.setBankAccount(entity.getRelatedUnit().getBankAccount());
        bean.setBankDeposit(entity.getRelatedUnit().getBankDeposit());
        bean.setDelegation(entity.getRelatedUnit().getDelegation());
        bean.setFundAbility(entity.getRelatedUnit().getFundAbility());
        bean.setFixedAsset(entity.getRelatedUnit().getFixedAsset());
        bean.setIdCardNum(entity.getRelatedUnit().getIdCardNum());
        
        return bean;
    }

    @Override
    public TenderingPlanRelatedunit toEntity(TenderingPlanRelatedunitBean bean, TenderingPlanRelatedunit oldEntity,
            AbstractSessionUserBean user, Date now) {
        // TODO Auto-generated method stub

    	TenderingPlanRelatedunit entity = oldEntity;
        if (entity == null) { // 表示新建
            entity = new TenderingPlanRelatedunit();
            entity.setCreator(user.getSessionUserId());
            entity.setcDatetime(now);

        } else {
        	entity.setModifier(user.getSessionUserId());
        	entity.setmDatetime(now);
        }

        bean.setId(entity.getId()); // ID必须赋值
        if (!StringUtils.isEmpty(bean.getTenderingPlan_())) {
        	TenderingPlan tenderingPlan = new TenderingPlan();
        	tenderingPlan.setId(bean.getTenderingPlan_());
        	entity.setTenderingPlan(tenderingPlan);
        }
        
        if (!StringUtils.isEmpty(bean.getRelatedUnit_())) {
        	RelatedUnit relatedUnit = new RelatedUnit();
        	relatedUnit.setId(bean.getRelatedUnit_());
        	entity.setRelatedUnit(relatedUnit);
        }
        
        //if (bean.getTenderingResult() != null) {
        	entity.setTenderingResult(bean.getTenderingResult());
        //}
        
        if (!StringUtils.isEmpty(bean.getTenderWinTime())) {
            try {
                entity.setTenderWinTime(
                        DateUtils.string2Date(bean.getTenderWinTime(), Constants.DATE_FORMAT));
            } catch (ParseException e) {
                // do nothing
            }
        } else {
        	entity.setTenderWinTime(null);
        }
        
        return entity;
    }

}
