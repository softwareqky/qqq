package project.edge.domain.converter;

import org.springframework.context.MessageSource;
import org.springframework.util.StringUtils;

import java.util.Date;
import java.util.Locale;

import garage.origin.domain.view.AbstractSessionUserBean;
import garage.origin.domain.view.converter.ViewConverter;
import project.edge.domain.entity.Dept;
import project.edge.domain.entity.Org;
import project.edge.domain.view.DeptBean;

/**
 * @author angel_000
 *         转换部门信息对应的view和entity的转换器。
 */
public class DeptBeanConverter implements ViewConverter<Dept, DeptBean> {

    @Override
    public DeptBean fromEntity(Dept entity, MessageSource messageSource, Locale locale) {
        // TODO Auto-generated method stub

        DeptBean bean = new DeptBean();
        bean.setId(entity.getId());

        Org org = entity.getOrg();

        if (org != null) {
            bean.setOrg_(org.getId());
            bean.setOrgText(org.getOrgName());
        }
        bean.setDeptName(entity.getDeptName());
        bean.setShortName(entity.getShortName());
        bean.setDeptCode(entity.getDeptCode());
        bean.setPid(entity.getPid());
        bean.setLevel(bean.getLevel());
        bean.setDeptOrder(entity.getDeptOrder());

        return bean;
    }

    @Override
    public Dept toEntity(DeptBean bean, Dept oldEntity, AbstractSessionUserBean user, Date now) {
        // TODO Auto-generated method stub
        Dept entity = oldEntity;
        if (entity == null) { // 表示新建
            entity = new Dept();

            entity.setCreator(user.getSessionUserId());
            entity.setcDatetime(now);
        } else {
            entity.setModifier(user.getSessionUserId());
        }

        bean.setId(entity.getId());// ID必须赋值
        entity.setmDatetime(now);

        if (!StringUtils.isEmpty(bean.getOrg_())) {
            Org org = new Org();
            org.setId(bean.getOrg_());
            entity.setOrg(org);
        }

        entity.setDeptName(bean.getDeptName());
        entity.setShortName(bean.getShortName());
        entity.setDeptCode(bean.getDeptCode());
        entity.setPid(bean.getPid());
        entity.setLevel(bean.getLevel());
        entity.setDeptOrder(bean.getDeptOrder());

        return entity;
    }

}
