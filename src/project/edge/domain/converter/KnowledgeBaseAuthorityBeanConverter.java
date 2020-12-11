/**
 * 
 */
package project.edge.domain.converter;

import org.springframework.context.MessageSource;
import org.springframework.util.StringUtils;

import java.util.Date;
import java.util.Locale;

import garage.origin.domain.view.AbstractSessionUserBean;
import garage.origin.domain.view.converter.ViewConverter;
import project.edge.common.constant.AuthTypeEnum;
import project.edge.domain.entity.KnowledgeBase;
import project.edge.domain.entity.KnowledgeBaseAuthority;
import project.edge.domain.entity.Person;
import project.edge.domain.view.KnowledgeBaseAuthorityBean;


/**
 * @author angel_000
 *         转换知识库文件权限表信息对应的view和entity的转换器。
 */
public class KnowledgeBaseAuthorityBeanConverter
        implements ViewConverter<KnowledgeBaseAuthority, KnowledgeBaseAuthorityBean> {

    @Override
    public KnowledgeBaseAuthorityBean fromEntity(KnowledgeBaseAuthority entity,
            MessageSource messageSource, Locale locale) {

        KnowledgeBaseAuthorityBean bean = new KnowledgeBaseAuthorityBean();

        bean.setId(entity.getId());

        bean.setKnowledgeBase_(entity.getKnowledgeBase().getId());
        bean.setKnowledgeBaseText(entity.getKnowledgeBase().getArchiveName());
        if (entity.getPerson() != null) {
            bean.setPerson_(entity.getPerson().getId());
            bean.setPersonText(entity.getPerson().getPersonName());
        }

        bean.setAccountType(entity.getAccountType());
        bean.setAuthType(entity.getAuthType());
        bean.setAuthTypeText(messageSource
                .getMessage(AuthTypeEnum.getResouceName(entity.getAuthType()), null, locale));
        bean.setIsInherit(entity.getIsInherit());

        return bean;
    }

    @Override
    public KnowledgeBaseAuthority toEntity(KnowledgeBaseAuthorityBean bean,
            KnowledgeBaseAuthority oldEntity, AbstractSessionUserBean user, Date now) {

        KnowledgeBaseAuthority entity = oldEntity;

        if (entity == null) { // 表示新建
            entity = new KnowledgeBaseAuthority();

            entity.setCreator(user.getSessionUserId());
            entity.setcDatetime(now);

        } else { // 表示修改
            entity.setModifier(user.getSessionUserId());
        }

        bean.setId(entity.getId()); // ID必须赋值
        entity.setmDatetime(now);

        KnowledgeBase k = new KnowledgeBase();
        k.setId(bean.getKnowledgeBase_());
        entity.setKnowledgeBase(k);

        if (!StringUtils.isEmpty(bean.getPerson_())) {
            Person p = new Person();
            p.setId(bean.getPerson_());
            entity.setPerson(p);
        }

        entity.setAccountType(bean.getAccountType());
        entity.setAuthType(bean.getAuthType());
        entity.setIsInherit(bean.getIsInherit());

        return entity;
    }

}
