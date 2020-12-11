/**
 * 
 */
package project.edge.domain.converter;

import org.springframework.context.MessageSource;

import java.util.Date;
import java.util.Locale;

import garage.origin.domain.view.AbstractSessionUserBean;
import garage.origin.domain.view.converter.ViewConverter;
import project.edge.domain.entity.DataOption;
import project.edge.domain.view.DataOptionBean;


/**
 * @author angel_000
 *         转换编码字典信息对应的view和entity的转换器。
 */
public class DataOptionProjectKindBeanConverter implements ViewConverter<DataOption, DataOptionBean> {

    @Override
    public DataOptionBean fromEntity(DataOption entity, MessageSource messageSource,
            Locale locale) {
        // TODO Auto-generated method stub

        DataOptionBean bean = new DataOptionBean();
        bean.setId(entity.getId());
        bean.setDataType(entity.getDataType());
        bean.setOptionName(entity.getOptionName());
        bean.setOptionCode(entity.getOptionCode());
        bean.setOptionOrder(entity.getOptionOrder());
        bean.setRemark(entity.getRemark());

        return bean;
    }

    @Override
    public DataOption toEntity(DataOptionBean bean, DataOption oldEntity,
            AbstractSessionUserBean user, Date now) {
        // TODO Auto-generated method stub
        DataOption entity = oldEntity;

        if (entity == null) { // 表示新建
            entity = new DataOption();
            entity.setCreator(user.getSessionUserId());
            entity.setcDatetime(now);

        } else { // 表示修改
            entity.setModifier(user.getSessionUserId());
        }

        bean.setId(entity.getId()); // ID必须赋值
        entity.setmDatetime(now);

        entity.setDataType(bean.getDataType());
        entity.setOptionCode(bean.getOptionCode());
        entity.setOptionName(bean.getOptionName());
        entity.setOptionOrder(bean.getOptionOrder());
        entity.setRemark(bean.getRemark());

        return entity;
    }

}
