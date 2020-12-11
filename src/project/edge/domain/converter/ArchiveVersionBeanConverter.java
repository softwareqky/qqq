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
import project.edge.domain.entity.Archive;
import project.edge.domain.view.ArchiveVersionBean;

/**
 * @author angel_000
 *         转换档案文件表信息对应的view和entity的转换器。
 */
public class ArchiveVersionBeanConverter implements ViewConverter<Archive, ArchiveVersionBean> {

    @Override
    public ArchiveVersionBean fromEntity(Archive entity, MessageSource messageSource,
            Locale locale) {

        ArchiveVersionBean bean = new ArchiveVersionBean();

        bean.setId(entity.getId());
        bean.setArchiveName(entity.getArchiveName());

        bean.setFileSize(entity.getFileSize());
        if (entity.getFileSize() != null) {
            int size = entity.getFileSize();
            bean.setFileSizeText(FileUtils.byteCountToDisplaySize(size));
        }

        bean.setRemark(CheckUtils.checkString(entity.getRemark()));
        bean.setRemarkHtmlEscaped(HtmlUtils.htmlEscape(bean.getRemark()));

        bean.setIsDeleted(entity.getIsDeleted());
        bean.setIsDeletedText(messageSource
                .getMessage(OnOffEnum.getResouceName(entity.getIsDeleted()), null, locale));
        bean.setmDatetime(
                DateUtils.date2String(entity.getmDatetime(), Constants.SIMPLE_DATE_TIME_FORMAT));

        bean.setModifier(entity.getCreator());
        if (entity.getModifier() != null) {
            bean.setModifier(entity.getModifier());
        }

        return bean;
    }

    @Override
    public Archive toEntity(ArchiveVersionBean bean, Archive oldEntity,
            AbstractSessionUserBean user, Date now) {
        return null;
    }

}
