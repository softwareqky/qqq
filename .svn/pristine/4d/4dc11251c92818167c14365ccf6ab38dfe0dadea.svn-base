/**
 * 
 */
package project.edge.domain.converter;

import java.io.File;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Locale;
import java.util.Map;
import java.util.Set;

import org.springframework.context.MessageSource;
import org.springframework.util.StringUtils;

import garage.origin.common.constant.Constants;
import garage.origin.domain.view.AbstractSessionUserBean;
import garage.origin.domain.view.converter.ViewConverter;
import project.edge.common.constant.FolderPath;
import project.edge.common.constant.ObjectiveTypeEnum;
import project.edge.domain.entity.Archive;
import project.edge.domain.entity.Person;
import project.edge.domain.entity.Project;
import project.edge.domain.entity.ProjectPerson;
import project.edge.domain.entity.QualityObjective;
import project.edge.domain.entity.QualityObjectiveAttachment;
import project.edge.domain.entity.QualitySpecification;
import project.edge.domain.view.ArchiveBean;
import project.edge.domain.view.QualityObjectiveBean;


/**
 * @author angel_000
 *
 */
public class QualityObjectiveBeanConverter
        implements ViewConverter<QualityObjective, QualityObjectiveBean> {

    @Override
    public QualityObjectiveBean fromEntity(QualityObjective entity, MessageSource messageSource,
            Locale locale) {
        QualityObjectiveBean bean = new QualityObjectiveBean();

        bean.setId(entity.getId());

        Project project = entity.getProject();

        if (project != null) {
            bean.setProject_(project.getId());
            bean.setProjectText(project.getProjectName());
        }

        bean.setTaskCode(entity.getTaskCode());
        bean.setTaskName(entity.getTaskName());

        bean.setObjecttiveType(entity.getObjecttiveType());
        bean.setObjecttiveTypeText(messageSource.getMessage(
                ObjectiveTypeEnum.getResouceName(entity.getObjecttiveType()), null, locale));
        /*
         * bean.setObjecttiveTypeText(messageSource
         * .getMessage(FlowStatusEnum.getResouceName(entity.getObjecttiveType()), null, locale));
         */

        ProjectPerson leader = entity.getLeader();
        if (leader != null) {
            bean.setLeader_(leader.getId());
            bean.setLeaderText(leader.getPerson().getPersonName());
        }

        bean.setRemark(entity.getRemark());

        QualitySpecification specification = entity.getSpecification();
        if (specification != null) {
            bean.setSpecification_(specification.getId());
            bean.setSpecificationText(specification.getSpecificationName());
        }
        
        // 附件处理
        ArchiveBeanConverter archConverter = new ArchiveBeanConverter();
        Set<QualityObjectiveAttachment> attachments = entity.getAttachments();
        for (QualityObjectiveAttachment a : attachments) {
        	bean.getArchivesList().add(archConverter.fromEntity(a.getArchive(), messageSource, locale));
        }

        return bean;
    }

    @Override
    public QualityObjective toEntity(QualityObjectiveBean bean, QualityObjective oldEntity,
            AbstractSessionUserBean user, Date now) {

        QualityObjective entity = oldEntity;

        if (entity == null) { // 表示新建
            entity = new QualityObjective();

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

        entity.setTaskCode(bean.getTaskCode());
        entity.setTaskName(bean.getTaskName());
        entity.setObjecttiveType(bean.getObjecttiveType());

        if (!StringUtils.isEmpty(bean.getLeader_())) {
        	ProjectPerson leader = new ProjectPerson();
        	Person person = new Person();
            person.setPersonName(bean.getLeaderText());
            leader.setId(bean.getLeader_());
            leader.setPerson(person);
            entity.setLeader(leader);
        }

        entity.setRemark(bean.getRemark());

        if (!StringUtils.isEmpty(bean.getSpecification_())) {
            QualitySpecification specification = new QualitySpecification();
            specification.setId(bean.getSpecification_());
            entity.setSpecification(specification);
        }
        
        // 需要删除的附件
        if (oldEntity != null) {
        	Map<String, String> map = new HashMap<>();
            for (String aid : bean.getArchivesReservedList()) {
            	map.put(aid, aid);
            }
            for (QualityObjectiveAttachment dbAttachment: oldEntity.getAttachments()) {
            	if (!map.containsKey(dbAttachment.getArchive().getId())) {
            		entity.getAttachmentsToDelete().add(dbAttachment);
            	}
            }
        }
        
        // 需要添加的附件
        Set<QualityObjectiveAttachment> attachments = new HashSet<>();
        ArchiveBeanConverter archConverter = new ArchiveBeanConverter();
        for (ArchiveBean a : bean.getArchivesList()) {
        	attachments.add(this.toAttachmentEntity(a, archConverter, entity, 1, user, now));
        }
        entity.setAttachments(attachments);
        
        return entity;
    }

    private QualityObjectiveAttachment toAttachmentEntity(ArchiveBean ab,
            ArchiveBeanConverter abConverter, QualityObjective entity, int attachmentType,
            AbstractSessionUserBean user, Date now) {
    
    	QualityObjectiveAttachment attachment = new QualityObjectiveAttachment();
    	attachment.setQualityObjective(entity);
    	
    	ab.setLevel(1);
    	
    	ab.setPid(FolderPath.QUALITY_OBJECTIVE);
		ab.setPath(Constants.SLASH + "_" + FolderPath.QUALITY_OBJECTIVE);
		 
		Archive aentity = abConverter.toEntity(ab, null, user, now);
		String relativePath = File.separator + FolderPath.QUALITY_OBJECTIVE + File.separator + aentity.getId();        
		ab.setRelativePath(relativePath);
		aentity.setRelativePath(relativePath);
		
		attachment.setArchive(aentity);
		return attachment;
    }
}
