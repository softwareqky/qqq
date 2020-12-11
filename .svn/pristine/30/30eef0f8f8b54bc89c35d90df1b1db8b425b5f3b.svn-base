/**
 * 
 */
package project.edge.domain.converter;

import org.apache.commons.io.FileUtils;
import org.springframework.context.MessageSource;
import org.springframework.web.util.HtmlUtils;

import java.util.Date;
import java.util.Locale;

import garage.origin.common.constant.Constants;
import garage.origin.common.constant.OnOffEnum;
import garage.origin.common.util.CheckUtils;
import garage.origin.common.util.DateUtils;
import garage.origin.domain.view.AbstractSessionUserBean;
import garage.origin.domain.view.converter.ViewConverter;
import project.edge.domain.entity.KnowledgeBase;
import project.edge.domain.view.KnowledgeBaseBean;


/**
 * @author angel_000
 *         转换知识库文件表信息对应的view和entity的转换器。
 */
public class KnowledgeBaseBeanConverter implements ViewConverter<KnowledgeBase, KnowledgeBaseBean> {

    @Override
    public KnowledgeBaseBean fromEntity(KnowledgeBase entity, MessageSource messageSource,
            Locale locale) {

        KnowledgeBaseBean bean = new KnowledgeBaseBean();

        bean.setId(entity.getId());
        bean.setArchiveName(entity.getArchiveName());
        bean.setIsDir(entity.getIsDir());

        bean.setFileSize(entity.getFileSize());
        if (OnOffEnum.ON.value() == entity.getIsDir()) {
            bean.setFileSizeText(Constants.MINUS);
        } else {
            int size = entity.getFileSize();
            bean.setFileSizeText(FileUtils.byteCountToDisplaySize(size));
        }

        bean.setPid(entity.getPid());
        bean.setLevel(entity.getLevel());
        bean.setRemark(CheckUtils.checkString(entity.getRemark()));
        bean.setRemarkHtmlEscaped(HtmlUtils.htmlEscape(bean.getRemark()));
        bean.setmDatetime(
                DateUtils.date2String(entity.getmDatetime(), Constants.SIMPLE_DATE_TIME_FORMAT));

        bean.setRelativePath(entity.getRelativePath());
        bean.setFileDigest(entity.getFileDigest());
        bean.setPath(entity.getPath());

        return bean;
    }

    @Override
    public KnowledgeBase toEntity(KnowledgeBaseBean bean, KnowledgeBase oldEntity,
            AbstractSessionUserBean user, Date now) {

        KnowledgeBase entity = oldEntity;

        if (entity == null) { // 表示新建
            entity = new KnowledgeBase();

            entity.setCreator(user.getSessionUserId());
            entity.setcDatetime(now);

            // 只有在新建时，才需要设置这些字段
            entity.setIsDir(bean.getIsDir());

            if (OnOffEnum.OFF.value() == bean.getIsDir()) {
                entity.setFileSize(bean.getFileSize());
            }

            entity.setFileDigest(bean.getFileDigest());

            entity.setPid(bean.getPid());

            entity.setLevel(bean.getLevel());
            entity.setPath(bean.getPath());
            entity.setRelativePath(bean.getRelativePath());

        } else { // 表示修改
            entity.setModifier(user.getSessionUserId());
        }

        bean.setId(entity.getId()); // ID必须赋值
        entity.setmDatetime(now);

        entity.setIsDeleted(OnOffEnum.OFF.value());

        // 画面上修改时，只变更文件名和备注
        entity.setArchiveName(bean.getArchiveName());
        entity.setRemark(bean.getRemark());

        return entity;
    }

}
