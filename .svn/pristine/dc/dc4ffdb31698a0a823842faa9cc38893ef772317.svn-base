/**
 * 
 */
package project.edge.domain.converter;

import java.text.ParseException;
import java.util.Date;
import java.util.Locale;

import org.springframework.context.MessageSource;
import org.springframework.util.StringUtils;

import garage.origin.common.constant.Constants;
import garage.origin.common.util.DateUtils;
import garage.origin.domain.view.AbstractSessionUserBean;
import garage.origin.domain.view.converter.ViewConverter;
import project.edge.domain.entity.CapitalApply;
import project.edge.domain.entity.CapitalReceive;
import project.edge.domain.entity.DataOption;
import project.edge.domain.entity.Person;
import project.edge.domain.entity.Project;
import project.edge.domain.view.CapitalReceiveBean;


/**
 * @author wwy
 *         转换资金下达对应的view和entity的转换器。
 */
public class CapitalReceiveBeanConverter
        implements ViewConverter<CapitalReceive, CapitalReceiveBean> {

    @Override
    public CapitalReceiveBean fromEntity(CapitalReceive entity, MessageSource messageSource,
            Locale locale) {
        // TODO Auto-generated method stub
    	CapitalReceiveBean bean = new CapitalReceiveBean();
		bean.setId(entity.getId());
		
		CapitalApply capitalApply = entity.getCapitalApply();
        if (capitalApply != null) {
            bean.setCapitalApply_(capitalApply.getId());
            bean.setCapitalApplyText(capitalApply.getApplyReason());
        }
        
		bean.setName(entity.getName());
		if (!StringUtils.isEmpty(entity.getYear())) {
			bean.setYear(entity.getYear());
		}
		bean.setReceiveMoney(entity.getReceiveMoney());
		if (entity.getReceiveTime() != null) {
			bean.setReceiveTime(DateUtils.date2String(entity.getReceiveTime(), Constants.DATE_FORMAT));
		}
        DataOption source = entity.getSource();
        if (source != null) {
            bean.setSource_(source.getId());
            bean.setSourceText(source.getOptionName());
        }
		bean.setRemark(entity.getRemark());
        Person person = entity.getCreator();
        if (person != null) {
            bean.setCreator_(person.getId());
            bean.setCreatorText(person.getPersonName());
        }
        
		return bean;
    }

    @Override
    public CapitalReceive toEntity(CapitalReceiveBean bean, CapitalReceive oldEntity,
            AbstractSessionUserBean user, Date now) {
        // TODO Auto-generated method stub

    	CapitalReceive entity = oldEntity;
        if (entity == null) { // 表示新建
            entity = new CapitalReceive();
            if (!StringUtils.isEmpty(user.getSessionUserId())) {
                Person person = new Person();
                person.setId(user.getSessionUserId());
                entity.setCreator(person);
            }
            entity.setcDatetime(now);

        } else {
            entity.setModifier(user.getSessionUserId());
        }

        bean.setId(entity.getId()); // ID必须赋值
		entity.setmDatetime(now);
		
        if (!StringUtils.isEmpty(bean.getCapitalApply_())) {
        	CapitalApply capitalApply = new CapitalApply();
        	capitalApply.setId(bean.getCapitalApply_());
            entity.setCapitalApply(capitalApply);
        }
        
        if (!StringUtils.isEmpty(bean.getProject_())) {
        	Project project = new Project();
        	project.setId(bean.getProject_());
        	entity.setProject(project);
        }
		
		entity.setName(bean.getName());
		if (!StringUtils.isEmpty(bean.getYear())) {
			entity.setYear(bean.getYear());
		}
		entity.setReceiveMoney(bean.getReceiveMoney());
        if (!StringUtils.isEmpty(bean.getReceiveTime())) {
            try {
                entity.setReceiveTime(
                        DateUtils.string2Date(bean.getReceiveTime(), Constants.DATE_FORMAT));
            } catch (ParseException e) {
                // do nothing
            }
        }
        if (!StringUtils.isEmpty(bean.getSource_())) {
            DataOption source = new DataOption();
            source.setId(bean.getSource_());
            entity.setSource(source);
        }
        entity.setRemark(bean.getRemark());
		return entity;
    }

}
