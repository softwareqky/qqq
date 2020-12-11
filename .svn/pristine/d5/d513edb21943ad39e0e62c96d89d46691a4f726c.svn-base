/**
 * 
 */
package project.edge.domain.converter;

import org.springframework.context.MessageSource;
import org.springframework.util.StringUtils;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Locale;
import java.util.Map;
import java.util.Set;

import garage.origin.common.constant.Constants;
import garage.origin.domain.view.AbstractSessionUserBean;
import garage.origin.domain.view.converter.ViewConverter;
import project.edge.common.constant.FolderPath;
import project.edge.common.constant.ObjectiveTypeEnum;
import project.edge.domain.entity.Archive;
import project.edge.domain.entity.Person;
import project.edge.domain.entity.QualitySpecification;
import project.edge.domain.entity.QualitySpecificationAttachment;
import project.edge.domain.view.ArchiveBean;
import project.edge.domain.view.QualitySpecificationBean;


/**
 * @author angel_000
 *
 */
public class QualitySpecificationBeanConverter
        implements ViewConverter<QualitySpecification, QualitySpecificationBean> {

    @Override
    public QualitySpecificationBean fromEntity(QualitySpecification entity,
            MessageSource messageSource, Locale locale) {

        QualitySpecificationBean bean = new QualitySpecificationBean();

        bean.setId(entity.getId());
        bean.setSpecificationName(entity.getSpecificationName());
        bean.setPublishingUnit(entity.getPublishingUnit());
        bean.setRecordType(entity.getRecordType());
        bean.setRecordTypeText(messageSource
                .getMessage(ObjectiveTypeEnum.getResouceName(entity.getRecordType()), null, locale));

        Person publisher = entity.getPublisher();
        if (publisher != null) {
            bean.setPublisher_(publisher.getId());
            bean.setPublisherText(publisher.getPersonName());
        }
        bean.setRemark(entity.getRemark());
        
        // 附件处理
        ArchiveBeanConverter archConverter = new ArchiveBeanConverter();
        Set<QualitySpecificationAttachment> attachments = entity.getQualitySpecificationAttachments();
        for (QualitySpecificationAttachment a : attachments) {
        	bean.getArchivesList().add(archConverter.fromEntity(a.getArchive(), messageSource, locale));
        }

        return bean;
    }

    @Override
    public QualitySpecification toEntity(QualitySpecificationBean bean,
            QualitySpecification oldEntity, AbstractSessionUserBean user, Date now) {
        // TODO Auto-generated method stub
        QualitySpecification entity = oldEntity;
        if (entity == null) { // 表示新建
            entity = new QualitySpecification();

            entity.setCreator(user.getSessionUserId());
            entity.setcDatetime(now);

        } else {
            entity.setModifier(user.getSessionUserId());
        }

        bean.setId(entity.getId()); // ID必须赋值
        entity.setmDatetime(now);

        entity.setSpecificationName(bean.getSpecificationName());
        entity.setPublishingUnit(bean.getPublishingUnit());
        entity.setRecordType(bean.getRecordType());

        if (!StringUtils.isEmpty(bean.getPublisher_())) {
            Person publisher = new Person();
            publisher.setId(bean.getPublisher_());
            entity.setPublisher(publisher);
        }

        entity.setRemark(bean.getRemark());
        
        // 找出需要删除的附件
        if (oldEntity != null) {
        	Map<String, String> map = new HashMap<>();
            for (String aid : bean.getArchivesReservedList()) {
                map.put(aid, aid);
            }
            for (QualitySpecificationAttachment dbAttachment: oldEntity.getQualitySpecificationAttachments()) {
            	if (!map.containsKey(dbAttachment.getArchive().getId())) {
            		entity.getArchivesToDelete().add(dbAttachment);
            	}
            }
        }
        
        // 附件
        Set<QualitySpecificationAttachment> archives = new HashSet<>();
        ArchiveBeanConverter archConverter = new ArchiveBeanConverter();
        for (ArchiveBean a : bean.getArchivesList()) {
        	archives.add(this.toAttachmentEntity(a, archConverter, entity, 1, user, now));
        }
        entity.setQualitySpecificationAttachments(archives);
        
        return entity;
    }
    
    private QualitySpecificationAttachment toAttachmentEntity(ArchiveBean ab,
            ArchiveBeanConverter abConverter, QualitySpecification entity, int attachmentType,
            AbstractSessionUserBean user, Date now) {

    	QualitySpecificationAttachment attachment = new QualitySpecificationAttachment();
        attachment.setQualitySpecification(entity);

        
        /**
         * 第零层：根
         * 第一层：年份
         * 第二层：项目名称+项目编号 FolderPath.PROJECT(DIR)
         * 第三层: 系统归档
         * 第四层：文件夹名
         * 第五层：Archive文件
         */
        ab.setLevel(1);

        ab.setPid(FolderPath.QUALITY_SPECIFICATION);
        ab.setPath(Constants.SLASH + "_" + FolderPath.QUALITY_SPECIFICATION);
        
        Archive aentity = abConverter.toEntity(ab, null, user, now);
        String relativePath = File.separator + FolderPath.QUALITY_SPECIFICATION + File.separator + aentity.getId();        
        ab.setRelativePath(relativePath);
        aentity.setRelativePath(relativePath);

        attachment.setArchive(aentity);
        return attachment;
    }

}
