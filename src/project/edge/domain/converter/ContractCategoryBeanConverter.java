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
import garage.origin.common.util.DateUtils;
import garage.origin.domain.view.AbstractSessionUserBean;
import garage.origin.domain.view.converter.ViewConverter;
import project.edge.domain.entity.ContractCategory;
import project.edge.domain.entity.Project;
import project.edge.domain.view.ContractCategoryBean;


/**
 * @author angel_000
 *
 */
public class ContractCategoryBeanConverter
        implements ViewConverter<ContractCategory, ContractCategoryBean> {

    @Override
    public ContractCategoryBean fromEntity(ContractCategory entity, MessageSource messageSource,
            Locale locale) {

        ContractCategoryBean bean = new ContractCategoryBean();
        bean.setId(entity.getId());
        bean.setCategoryName(entity.getCategoryName());
        bean.setCategoryCode(entity.getCategoryCode());
        bean.setIsLeaf(entity.getIsLeaf());
        bean.setPid(entity.getPid());
        bean.setDataSource(entity.getDataSource());
        bean.setFieldOrder(entity.getFieldOrder());
        bean.setRemark(entity.getRemark());

        return bean;
    }

    @Override
    public ContractCategory toEntity(ContractCategoryBean bean, ContractCategory oldEntity,
            AbstractSessionUserBean user, Date now) {

        ContractCategory entity = oldEntity;

        if (entity == null) { // 表示新建
            entity = new ContractCategory();

            entity.setCreator(user.getSessionUserId());
            entity.setcDatetime(now);
        } else { // 表示修改
            entity.setModifier(user.getSessionUserId());
        }

        bean.setId(entity.getId()); // ID必须赋值
        entity.setPid(bean.getPid());
        entity.setmDatetime(now);

        entity.setCategoryName(bean.getCategoryName());
        entity.setCategoryCode(bean.getCategoryCode());
        entity.setFieldOrder(bean.getFieldOrder());
        entity.setIsLeaf(bean.getIsLeaf());
        entity.setDataSource(bean.getDataSource());

        return entity;
    }

}
