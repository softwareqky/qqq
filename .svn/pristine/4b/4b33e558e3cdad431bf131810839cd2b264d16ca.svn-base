/**
 * 
 */
package project.edge.domain.converter;

import java.io.File;
import java.text.ParseException;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Locale;
import java.util.Map;
import java.util.Set;

import org.springframework.context.MessageSource;
import org.springframework.util.StringUtils;

import garage.origin.common.constant.Constants;
import garage.origin.common.util.DateUtils;
import garage.origin.domain.view.AbstractSessionUserBean;
import garage.origin.domain.view.converter.ViewConverter;
import project.edge.common.constant.FolderPath;
import project.edge.common.constant.ObjectiveTypeEnum;
import project.edge.domain.entity.Archive;
import project.edge.domain.entity.Project;
import project.edge.domain.entity.QualityAccident;
import project.edge.domain.entity.QualityAccidentAttachment;
import project.edge.domain.view.ArchiveBean;
import project.edge.domain.view.QualityAccidentBean;


/**
 * @author angel_000
 *
 */
public class QualityAccidentBeanConverter
        implements ViewConverter<QualityAccident, QualityAccidentBean> {

    @Override
    public QualityAccidentBean fromEntity(QualityAccident entity, MessageSource messageSource,
            Locale locale) {

        QualityAccidentBean bean = new QualityAccidentBean();
        Project project = entity.getProject();
        bean.setId(entity.getId());
        bean.setAccidentName(entity.getAccidentName());

        if (project != null) {
            bean.setProject_(project.getId());
            bean.setProjectText(project.getProjectName());
        }

        if (entity.getAccidentDate() != null) {
            bean.setAccidentDate(
                    DateUtils.date2String(entity.getAccidentDate(), Constants.DATE_FORMAT));
        }
        bean.setRemark(entity.getRemark());

        bean.setArchiveType(entity.getArchiveType());
        bean.setArchiveTypeText(messageSource.getMessage(
                ObjectiveTypeEnum.getResouceName((Integer) entity.getArchiveType()), null, locale));


        bean.setArchiveMaterials(entity.getArchiveMaterials());
        bean.setContent(entity.getContent());

        // 附件处理
        ArchiveBeanConverter archConverter = new ArchiveBeanConverter();
        Set<QualityAccidentAttachment> attachments = entity.getAttachments();
        for (QualityAccidentAttachment a : attachments) {
        	bean.getArchivesList().add(archConverter.fromEntity(a.getArchive(), messageSource, locale));
        }
        
        return bean;
    }

    @Override
    public QualityAccident toEntity(QualityAccidentBean bean, QualityAccident oldEntity,
            AbstractSessionUserBean user, Date now) {

        QualityAccident entity = oldEntity;
        if (entity == null) { // 表示新建
            entity = new QualityAccident();

            entity.setCreator(user.getSessionUserId());
            entity.setcDatetime(now);

        } else {
            entity.setModifier(user.getSessionUserId());
        }

        bean.setId(entity.getId()); // ID必须赋值
        entity.setmDatetime(now);

        if (!StringUtils.isEmpty(bean.getProject_())) {
            Project project = new Project();
            project.setId(bean.getProject_());
            project.setProjectName(bean.getProjectText());
            entity.setProject(project);
        }

        entity.setAccidentName(bean.getAccidentName());

        if (!StringUtils.isEmpty(bean.getAccidentDate())) {
            try {
                entity.setAccidentDate(
                        DateUtils.string2Date(bean.getAccidentDate(), Constants.DATE_FORMAT));
            } catch (ParseException e) {
                // do nothing
            }
        }

        entity.setRemark(bean.getRemark());
        entity.setArchiveType(bean.getArchiveType());
        entity.setArchiveMaterials(bean.getArchiveMaterials());
        entity.setContent(bean.getContent());
        
        // 需要删除的附件
        if (oldEntity != null) {
        	Map<String, String> map = new HashMap<>();
            for (String aid : bean.getArchivesReservedList()) {
            	map.put(aid, aid);
            }
            for (QualityAccidentAttachment dbAttachment: oldEntity.getAttachments()) {
            	if (!map.containsKey(dbAttachment.getArchive().getId())) {
            		entity.getAttachmentsToDelete().add(dbAttachment);
            	}
            }
        }
        
        // 需要添加的附件
        Set<QualityAccidentAttachment> attachments = new HashSet<>();
        ArchiveBeanConverter archConverter = new ArchiveBeanConverter();
        for (ArchiveBean a : bean.getArchivesList()) {
        	attachments.add(this.toAttachmentEntity(a, archConverter, entity, 1, user, now));
        }
        entity.setAttachments(attachments);

        return entity;
    }

    private QualityAccidentAttachment toAttachmentEntity(ArchiveBean ab,
            ArchiveBeanConverter abConverter, QualityAccident entity, int attachmentType,
            AbstractSessionUserBean user, Date now) {
    	
    	QualityAccidentAttachment attachment = new QualityAccidentAttachment();
    	attachment.setQualityAccident(entity);
    	
    	ab.setLevel(1);
    	
    	ab.setPid(FolderPath.QUALITY_ACCIDENT);
		ab.setPath(Constants.SLASH + "_" + FolderPath.QUALITY_ACCIDENT);
		 
		Archive aentity = abConverter.toEntity(ab, null, user, now);
		String relativePath = File.separator + FolderPath.QUALITY_ACCIDENT + File.separator + aentity.getId();        
		ab.setRelativePath(relativePath);
		aentity.setRelativePath(relativePath);
		
		attachment.setArchive(aentity);
    	return attachment;
    }
}
