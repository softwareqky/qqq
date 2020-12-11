/**
 * 
 */
package project.edge.domain.converter;

import org.springframework.context.MessageSource;
import org.springframework.util.StringUtils;

import java.util.Date;
import java.util.Locale;

import garage.origin.common.constant.Constants;
import garage.origin.common.util.DateUtils;
import garage.origin.domain.view.AbstractSessionUserBean;
import garage.origin.domain.view.converter.ViewConverter;
import project.edge.common.constant.FlowStatusEnum;
import project.edge.common.constant.InvolvedProjectGroupEnum;
import project.edge.common.constant.LinkStatusEnum;
import project.edge.domain.entity.Link;
import project.edge.domain.entity.Person;
import project.edge.domain.entity.Site;
import project.edge.domain.view.LinkBean;


/**
 * @author angel_000
 *         转换链路信息对应的view和entity的转换器。
 */
public class LinkBeanConverter implements ViewConverter<Link, LinkBean> {

    @Override
    public LinkBean fromEntity(Link entity, MessageSource messageSource, Locale locale) {
        // TODO Auto-generated method stub

        LinkBean bean = new LinkBean();
        bean.setId(entity.getId());
        bean.setLinkName(entity.getLinkName());

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

        // if (entity.getIsProgrammableLinkType() != null) {
        // bean.setIsProgrammableLinkType(entity.getIsProgrammableLinkType());
        // bean.setIsProgrammableLinkTypeText(entity.getIsProgrammableLinkType() == 1
        // ? messageSource.getMessage(OnOffEnum.ON.resourceName(), null, locale)
        // : messageSource.getMessage(OnOffEnum.OFF.resourceName(), null, locale));
        // }
        //
        // if (entity.getIsSdnLinkType() != null) {
        // bean.setIsSdnLinkType(entity.getIsSdnLinkType());
        // bean.setIsSDNLinkTypeText(entity.getIsSdnLinkType() == 1
        // ? messageSource.getMessage(OnOffEnum.ON.resourceName(), null, locale)
        // : messageSource.getMessage(OnOffEnum.OFF.resourceName(), null, locale));
        // }

        bean.setLinkType(entity.getLinkType());

        Person personInCharge = entity.getPersonInCharge();
        if (personInCharge != null) {
            bean.setPersonInCharge_(personInCharge.getId());
            bean.setPersonInChargeText(personInCharge.getPersonName());
        }

        if (entity.getDataLinkStatus() != null) {
            bean.setDataLinkStatus(entity.getDataLinkStatus());
            bean.setDataLinkStatusText(messageSource.getMessage(
                    LinkStatusEnum.getResouceName(entity.getDataLinkStatus()), null, locale));
        }

        bean.setDistance(entity.getDistance());
        bean.setTransmissionLayerBandwidth(entity.getTransmissionLayerBandwidth());
        bean.setDataLayerBandwidth(entity.getDataLayerBandwidth());
        bean.setTransmitLinkStatus(entity.getTransmitLinkStatus());

        if (entity.getInvolvedProjectGroup() != null) {
            bean.setInvolvedProjectGroup(entity.getInvolvedProjectGroup());
            bean.setInvolvedProjectGroupText(messageSource.getMessage(
                    InvolvedProjectGroupEnum.getResouceName(entity.getInvolvedProjectGroup()), null,
                    locale));
        }
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
        

        return bean;
    }

    @Override
    public Link toEntity(LinkBean bean, Link oldEntity, AbstractSessionUserBean user, Date now) {
        // TODO Auto-generated method stub

        Link entity = oldEntity;
        if (entity == null) { // 表示新建

            entity = new Link();
            entity.setCreator(user.getSessionUserId());
            entity.setcDatetime(now);
            entity.setFlowStatus(0);

        } else {
            entity.setModifier(user.getSessionUserId());
        }

        bean.setId(entity.getId()); // ID必须赋值
        entity.setmDatetime(now);
        entity.setLinkName(bean.getLinkName());

        if (!StringUtils.isEmpty(bean.getEndA_())) {
            Site sitea = new Site();
            sitea.setId(bean.getEndA_());
            entity.setEndA(sitea);
        }

        if (!StringUtils.isEmpty(bean.getEndB_())) {
            Site siteb = new Site();
            siteb.setId(bean.getEndB_());
            entity.setEndB(siteb);
        }
        
        if (!StringUtils.isEmpty(bean.getPersonInCharge_())) {
            Person personInCharge = new Person();
            personInCharge.setId(bean.getPersonInCharge_());
            entity.setPersonInCharge(personInCharge);
        }

        // entity.setIsProgrammableLinkType(bean.getIsProgrammableLinkType());
        // entity.setIsSdnLinkType(bean.getIsSdnLinkType());
        entity.setLinkType(bean.getLinkType());
        entity.setDataLinkStatus(bean.getDataLinkStatus());
        entity.setDistance(bean.getDistance());
        entity.setTransmissionLayerBandwidth(bean.getTransmissionLayerBandwidth());
        entity.setDataLayerBandwidth(bean.getDataLayerBandwidth());
        entity.setTransmitLinkStatus(bean.getTransmitLinkStatus());
        entity.setInvolvedProjectGroup(bean.getInvolvedProjectGroup());
        entity.setRemark(bean.getRemark());


        return entity;
    }



}
