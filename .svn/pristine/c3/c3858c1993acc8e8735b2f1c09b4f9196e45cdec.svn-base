/**
 * 
 */
package project.edge.domain.converter;

import org.springframework.context.MessageSource;
import org.springframework.util.StringUtils;

import java.text.ParseException;
import java.util.Date;
import java.util.Locale;

import garage.origin.common.constant.Constants;
import garage.origin.common.constant.OnOffEnum;
import garage.origin.common.util.DateUtils;
import garage.origin.domain.view.AbstractSessionUserBean;
import garage.origin.domain.view.converter.ViewConverter;
import project.edge.common.constant.FacilityConstructionStatusEnum;
import project.edge.common.constant.FacilityStatusEnum;
import project.edge.common.constant.SegmentTypeEnum;
import project.edge.domain.entity.DataOption;
import project.edge.domain.entity.Person;
import project.edge.domain.entity.Segment;
import project.edge.domain.entity.SegmentSystemName;
import project.edge.domain.entity.Site;
import project.edge.domain.view.SegmentBean;


/**
 * @author angel_000
 *         转换中继信息对应的view和entity的转换器。
 */
public class SegmentBeanConverter implements ViewConverter<Segment, SegmentBean> {

    @Override
    public SegmentBean fromEntity(Segment entity, MessageSource messageSource, Locale locale) {
        // TODO Auto-generated method stub

        SegmentBean bean = new SegmentBean();

        bean.setId(entity.getId());

        Site a = entity.getEndA();
        if (a != null) {
            bean.setEndA_(a.getId());
            bean.setEndAText(a.getStationName());
        }

        Site b = entity.getEndB();
        if (b != null) {
            bean.setEndB_(b.getId());
            bean.setEndBText(b.getStationName());
        }

        bean.setReuseSegment(entity.getReuseSegment());
        
        if (entity.getSystemName() != null) {
        	bean.setSystemName(entity.getSystemName().getId());
            bean.setSystemNameText(entity.getSystemName().getName());
        }
        

        bean.setSegmentType(entity.getSegmentType());
        bean.setSegmentTypeText(messageSource
                .getMessage(SegmentTypeEnum.getResouceName(entity.getSegmentType()), null, locale));
        DataOption fibreType = entity.getFibreType();
        if (fibreType != null) {
            bean.setFibreType_(fibreType.getId());
            bean.setFibreTypeText(fibreType.getOptionName());
        }
        
        bean.setProgress(entity.getProgress());

        bean.setSegmentLength(entity.getSegmentLength());
        bean.setSegmentAttenuation(entity.getSegmentAttenuation());
        bean.setFormedAttenuation(entity.getFormedAttenuation());
        bean.setMaintainanceRemain(entity.getMaintainanceRemain());
        bean.setDesignAttenuation(entity.getDesignAttenuation());

        if (entity.getIsFibreTest() != null) {
            bean.setIsFibreTest(entity.getIsFibreTest());
            bean.setIsFibreTestText(entity.getIsFibreTest() == 1
                    ? messageSource.getMessage(OnOffEnum.ON.resourceName(), null, locale)
                    : messageSource.getMessage(OnOffEnum.OFF.resourceName(), null, locale));
        }

        bean.setFibreTest(entity.getFibreTest());

        if (entity.getIsFibreImprove() != null) {
            bean.setIsFibreImprove(entity.getIsFibreImprove());
            bean.setIsFibreImproveText(entity.getIsFibreImprove() == 1
                    ? messageSource.getMessage(OnOffEnum.ON.resourceName(), null, locale)
                    : messageSource.getMessage(OnOffEnum.OFF.resourceName(), null, locale));

        }
        bean.setFibreImprove(entity.getFibreImprove());

        if (entity.getIsOpticalLayerIntegrate() != null) {
            bean.setIsOpticalLayerIntegrate(entity.getIsOpticalLayerIntegrate());
            bean.setIsOpticalLayerIntegrateText(entity.getIsOpticalLayerIntegrate() == 1
                    ? messageSource.getMessage(OnOffEnum.ON.resourceName(), null, locale)
                    : messageSource.getMessage(OnOffEnum.OFF.resourceName(), null, locale));
        }

        bean.setOpticalLayerIntegrate(entity.getOpticalLayerIntegrate());

        if (entity.getIsBusinessIntegrate() != null) {
            bean.setIsBusinessIntegrate(entity.getIsBusinessIntegrate());
            bean.setIsBusinessIntegrateText(entity.getIsBusinessIntegrate() == 1
                    ? messageSource.getMessage(OnOffEnum.ON.resourceName(), null, locale)
                    : messageSource.getMessage(OnOffEnum.OFF.resourceName(), null, locale));
        }

        bean.setBusinessIntegrate(entity.getBusinessIntegrate());

        if (entity.getIsDataTest() != null) {
            bean.setIsDataTest(entity.getIsDataTest());
            bean.setIsDataTestText(entity.getIsDataTest() == 1
                    ? messageSource.getMessage(OnOffEnum.ON.resourceName(), null, locale)
                    : messageSource.getMessage(OnOffEnum.OFF.resourceName(), null, locale));
        }


        bean.setDataTest(entity.getDataTest());

        if (entity.getIsTransmitBusinessOn() != null) {
            bean.setIsTransmitBusinessOn(entity.getIsTransmitBusinessOn());
            bean.setIsTransmitBusinessOnText(entity.getIsTransmitBusinessOn() == 1
                    ? messageSource.getMessage(OnOffEnum.ON.resourceName(), null, locale)
                    : messageSource.getMessage(OnOffEnum.OFF.resourceName(), null, locale));
        }

        bean.setTransmitBusinessOn(entity.getTransmitBusinessOn());
        bean.setSegmentStatus(entity.getSegmentStatus());
        bean.setSegmentStatusText(messageSource.getMessage(
                FacilityConstructionStatusEnum.getResouceName(entity.getSegmentStatus()), null,
                locale));

        if (entity.getManualSegmentStatus() != null) {
            bean.setManualSegmentStatus(entity.getManualSegmentStatus());
            bean.setManualSegmentStatusText(messageSource.getMessage(
            		FacilityStatusEnum.getResouceName(entity.getManualSegmentStatus()), null,
                    locale));
        }

        if (entity.getInitMaterialArchieveDate() != null) {
            bean.setInitMaterialArchieveDate(
                    DateUtils.date2String(entity.getInitMaterialArchieveDate()));
        }

        Person personInCharge = entity.getPersonInCharge();
        if (personInCharge != null) {
            bean.setPersonInCharge_(personInCharge.getId());
            bean.setPersonInChargeText(personInCharge.getPersonName());
        }
        
        bean.setRemark(entity.getRemark());
        String format = "%1$s - %2$s";
        if ((a != null) && (b != null)) {
            bean.setPopupSelectInfo(String.format(format, a.getStationName(), b.getStationName()));
        }

        return bean;
    }

    @Override
    public Segment toEntity(SegmentBean bean, Segment oldEntity, AbstractSessionUserBean user,
            Date now) {
        // TODO Auto-generated method stub

        Segment entity = oldEntity;
        if (entity == null) { // 表示新建
            entity = new Segment();

            entity.setCreator(user.getSessionUserId());
            entity.setcDatetime(now);

        } else {
            entity.setModifier(user.getSessionUserId());
        }

        bean.setId(entity.getId()); // ID必须赋值
        entity.setmDatetime(now);

        if (!StringUtils.isEmpty(bean.getEndA_())) {
            Site sitea = new Site();
            sitea.setId(bean.getEndA_());
            sitea.setStationName(bean.getEndAText());
            entity.setEndA(sitea);
        }

        if (!StringUtils.isEmpty(bean.getEndB_())) {
            Site siteb = new Site();
            siteb.setId(bean.getEndB_());
            siteb.setStationName(bean.getEndBText());
            entity.setEndB(siteb);
        }
        
        if (bean.getProgress() != null) {
        	entity.setProgress(bean.getProgress());
        }

        entity.setSegmentType(bean.getSegmentType());
        entity.setReuseSegment(bean.getReuseSegment());
        
        if (!StringUtils.isEmpty(bean.getSystemName())) {
        	SegmentSystemName systemName = new SegmentSystemName();
        	systemName.setId(bean.getSystemName());
        	systemName.setName(bean.getSystemNameText());
        	entity.setSystemName(systemName);
        }

        if (!StringUtils.isEmpty(bean.getFibreType_())) {
            DataOption fibreType = new DataOption();
            fibreType.setId(bean.getFibreType_());
            entity.setFibreType(fibreType);
        }

        entity.setSegmentLength(bean.getSegmentLength());
        entity.setSegmentAttenuation(bean.getSegmentAttenuation());
        entity.setFormedAttenuation(bean.getFormedAttenuation());
        entity.setMaintainanceRemain(bean.getMaintainanceRemain());
        entity.setDesignAttenuation(bean.getDesignAttenuation());
        entity.setIsFibreTest(bean.getIsFibreTest());
        entity.setFibreTest(bean.getFibreTest());
        entity.setIsFibreImprove(bean.getIsFibreImprove());
        entity.setFibreImprove(bean.getFibreImprove());
        entity.setIsOpticalLayerIntegrate(bean.getIsOpticalLayerIntegrate());
        entity.setOpticalLayerIntegrate(bean.getOpticalLayerIntegrate());
        entity.setIsBusinessIntegrate(bean.getIsBusinessIntegrate());
        entity.setBusinessIntegrate(bean.getBusinessIntegrate());
        entity.setIsDataTest(bean.getIsDataTest());
        entity.setDataTest(bean.getDataTest());
        entity.setIsTransmitBusinessOn(bean.getIsTransmitBusinessOn());
        entity.setTransmitBusinessOn(bean.getTransmitBusinessOn());
        entity.setSegmentStatus(bean.getSegmentStatus());
        entity.setManualSegmentStatus(bean.getManualSegmentStatus());
        entity.setRemark(bean.getRemark());
        
        if (!StringUtils.isEmpty(bean.getPersonInCharge_())) {
            Person personInCharge = new Person();
            personInCharge.setId(bean.getPersonInCharge_());
            entity.setPersonInCharge(personInCharge);
        }

        if (!StringUtils.isEmpty(bean.getInitMaterialArchieveDate())) {
            try {
                entity.setInitMaterialArchieveDate(DateUtils
                        .string2Date(bean.getInitMaterialArchieveDate(), Constants.DATE_FORMAT));
            } catch (ParseException e) {
                // TODO Auto-generated catch block
                // e.printStackTrace();
                // do nothing
            }
        }

        return entity;
    }

}
