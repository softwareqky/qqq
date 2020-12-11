package project.edge.domain.converter;



import org.springframework.context.MessageSource;
import org.springframework.util.StringUtils;

import java.util.Date;
import java.util.Locale;

import garage.origin.domain.view.AbstractSessionUserBean;
import garage.origin.domain.view.converter.ViewConverter;
import project.edge.domain.entity.Dept;
import project.edge.domain.entity.Org;
import project.edge.domain.entity.Post;
import project.edge.domain.view.PostBean;

/**
 * @author angel_000
 *         转换岗位信息对应的view和entity的转换器。
 */
public class PostBeanConverter implements ViewConverter<Post, PostBean> {

    @Override
    public PostBean fromEntity(Post entity, MessageSource messageSource, Locale locale) {
        // TODO Auto-generated method stub
        PostBean bean = new PostBean();

        bean.setId(entity.getId());
        Org org = entity.getOrg();
        Dept dept = entity.getDept();

        if (org != null) {
            bean.setOrg_(org.getId());
            bean.setOrgText(org.getOrgName());
        }

        if (dept != null) {
            bean.setDept_(dept.getId());
            bean.setDeptText(dept.getDeptName());
        }
        bean.setPostName(entity.getPostName());
        bean.setShortName(entity.getShortName());
        bean.setResponsibility(entity.getResponsibility());
        bean.setQualification(entity.getQualification());
        bean.setRelatedDoc(entity.getRelatedDoc());
        bean.setRemark(entity.getRemark());

        return bean;
    }

    @Override
    public Post toEntity(PostBean bean, Post oldEntity, AbstractSessionUserBean user, Date now) {
        // TODO Auto-generated method stub
        Post entity = oldEntity;
        if (entity == null) { // 表示新建
            entity = new Post();

            entity.setCreator(user.getSessionUserId());
            entity.setcDatetime(now);
        } else {
            entity.setModifier(user.getSessionUserId());
        }

        bean.setId(entity.getId()); // ID必须赋值
        entity.setmDatetime(now);

        if (!StringUtils.isEmpty(bean.getOrg_())) {
            Org org = new Org();
            org.setId(bean.getOrg_());
            entity.setOrg(org);
        }

        if (!StringUtils.isEmpty(bean.getDept_())) {
            Dept dept = new Dept();
            dept.setId(bean.getDept_());
            entity.setDept(dept);
        }

        entity.setPostName(bean.getPostName());
        entity.setShortName(bean.getShortName());
        entity.setResponsibility(bean.getResponsibility());
        entity.setQualification(bean.getQualification());
        entity.setRelatedDoc(bean.getRelatedDoc());
        entity.setRemark(bean.getRemark());

        return entity;
    }

}
