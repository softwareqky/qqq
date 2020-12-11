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
import project.edge.common.constant.ProjectAttachmentTypeEnum;
import project.edge.domain.entity.Archive;
import project.edge.domain.entity.DataOption;
import project.edge.domain.entity.Person;
import project.edge.domain.entity.Project;
import project.edge.domain.entity.ProjectPerformanceAward;
import project.edge.domain.entity.ProjectPerformanceAwardAttachment;
import project.edge.domain.entity.ProjectPerson;
import project.edge.domain.view.ArchiveBean;
import project.edge.domain.view.ProjectPerformanceAwardBean;

/**
 * @author angel_000
 *         转换奖罚信息对应的view和entity的转换器。
 */
public class ProjectPerformanceAwardBeanConverter
        implements ViewConverter<ProjectPerformanceAward, ProjectPerformanceAwardBean> {

    @Override
    public ProjectPerformanceAwardBean fromEntity(ProjectPerformanceAward entity,
            MessageSource messageSource, Locale locale) {
        ProjectPerformanceAwardBean bean = new ProjectPerformanceAwardBean();
        bean.setId(entity.getId());

        ProjectPerson person = entity.getPerson();
        if (person != null && person.getPerson() != null) {
            bean.setPerson_(person.getId());
            bean.setPersonText(person.getPerson().getPersonName());
        }

        Project project = entity.getProject();
        if (project != null) {
            bean.setProject_(project.getId());
            bean.setProjectText(project.getProjectName());
        }

        DataOption dataOption = entity.getAwardAndPunishmentType();
        if (dataOption != null) {
            bean.setRewardsType_(dataOption.getId());
            bean.setRewardsTypeText(dataOption.getOptionName());
        }
        bean.setAmount(entity.getAmount());
        if (entity.getExecuteTime() != null) {
            bean.setExecuteTime(
                    DateUtils.date2String(entity.getExecuteTime(), Constants.DATE_FORMAT));
        }
        bean.setExecuteBasis(entity.getExecuteBasis());
        bean.setExecuteReason(entity.getExecuteReason());
        bean.setRemark(entity.getRemark());

        // 附件处理
        ArchiveBeanConverter abConverter = new ArchiveBeanConverter();
        Set<ProjectPerformanceAwardAttachment> projectPerformanceAwardAttachments = entity.getProjectPerformanceAwardAttachments();
        for (ProjectPerformanceAwardAttachment pa : projectPerformanceAwardAttachments) {

            bean.getArchivesList()
                    .add(abConverter.fromEntity(pa.getArchive(), messageSource, locale));

        }
        
        return bean;
    }

    @Override
    public ProjectPerformanceAward toEntity(ProjectPerformanceAwardBean bean,
            ProjectPerformanceAward oldEntity, AbstractSessionUserBean user, Date now) {
        // TODO Auto-generated method stub
        ProjectPerformanceAward entity = oldEntity;
        //Set<Archive> archives = new HashSet<>();
        if (entity == null) { // 表示新建
            entity = new ProjectPerformanceAward();

            entity.setCreator(user.getSessionUserId());
            entity.setcDatetime(now);
        } else { // 表示修改
                 // 在Controller.postUpdate中，需要比较新老entity的关联的档案文件，所以必须clone
                 // clone之后，清空外键关联字段的值
            entity = oldEntity.clone();
            
            // 修改后，仍然保留的档案文件
            Map<String, String> map = new HashMap<>();
            for (String aid : bean.getArchivesReservedList()) {
                map.put(aid, aid);
            }
            
            // 找出需要删除的Archive
            for (ProjectPerformanceAwardAttachment dbAttachment : oldEntity.getProjectPerformanceAwardAttachments()) {
                if (!map.containsKey(dbAttachment.getArchive().getId())) {
                    entity.getAttachmentsToDelete().add(dbAttachment);
                }

            }

            entity.setModifier(user.getSessionUserId());

        }
        bean.setId(entity.getId()); // ID必须赋值
        entity.setmDatetime(now);

        if (!StringUtils.isEmpty(bean.getPerson_())) {
        	ProjectPerson projectPerson = new ProjectPerson();
        	Person person = new Person();
            person.setPersonName(bean.getPersonText());
            projectPerson.setId(bean.getPerson_());
            projectPerson.setPerson(person);
            entity.setPerson(projectPerson);
        }

        if (!StringUtils.isEmpty(bean.getProject_())) {
            Project project = new Project();
            project.setId(bean.getProject_());
            project.setProjectName(bean.getProjectText());
            entity.setProject(project);
        }

        if (!StringUtils.isEmpty(bean.getRewardsType_())) {
            DataOption dataOption = new DataOption();
            dataOption.setId(bean.getRewardsType_());
            entity.setAwardAndPunishmentType(dataOption);
        }

        entity.setAmount(bean.getAmount());
        entity.setRemark(bean.getRemark());
        entity.setExecuteBasis(bean.getExecuteBasis());
        entity.setExecuteReason(bean.getExecuteReason());
        if (!StringUtils.isEmpty(bean.getExecuteTime())) {
            try {
                entity.setExecuteTime(
                        DateUtils.string2Date(bean.getExecuteTime(), Constants.DATE_FORMAT));
            } catch (ParseException e) {
                // do nothing
            }
        }

        // 附件处理，此处只需处理新增加的ProjectAttachment
        Set<ProjectPerformanceAwardAttachment> projectPerformanceAwardAttachments = new HashSet<>();

        ArchiveBeanConverter abConverter = new ArchiveBeanConverter();
        for (ArchiveBean ab : bean.getArchivesList()) {
            projectPerformanceAwardAttachments.add(this.toAttachmentEntity(ab, abConverter, entity,
                    ProjectAttachmentTypeEnum.ARCHIVE.value(), user, now));
        }

        entity.setProjectPerformanceAwardAttachments(projectPerformanceAwardAttachments);
        entity.setFlowStatus(0);

        return entity;
    }

    
    /**
     * 把ArchiveBean转换成Archive，同时创建对应的ProjectAttachment并返回。
     * 
     * @param ab
     * @param abConverter
     * @param entity
     * @param attachmentType
     * @param user
     * @param now
     * @return
     */
    private ProjectPerformanceAwardAttachment toAttachmentEntity(ArchiveBean ab, ArchiveBeanConverter abConverter,
            ProjectPerformanceAward entity, int attachmentType, AbstractSessionUserBean user, Date now) {

        ProjectPerformanceAwardAttachment attachment = new ProjectPerformanceAwardAttachment();
        attachment.setAward(entity);

        /**
         * 第零层：根
         * 第一层：FolderPath.PROJECT(DIR)
         * 第二层：Project.id(DIR)
         * 第三层：Archive文件
         */
        ab.setLevel(3);
        ab.setPid(entity.getId());
        ab.setPath(Constants.SLASH + Constants.COMMA + FolderPath.AWARD + Constants.COMMA
                + entity.getId());
        Archive aentity = abConverter.toEntity(ab, null, user, now);

        // "\project\guid\xxx.doc"
        // String extension = FilenameUtils.getExtension(ab.getArchiveName()).toLowerCase();
        // String relativePath = File.separator + FolderPath.PROJECT + File.separator
        // + entity.getId() + File.separator + aentity.getId() + Constants.DOT + extension;
        String relativePath = File.separator + FolderPath.AWARD + File.separator + entity.getId()
                + File.separator + aentity.getId();
        ab.setRelativePath(relativePath);
        aentity.setRelativePath(relativePath);

        // Archive:
        // id/level/pid/(id)path/relativePath由converter各自设置，
        // archiveName/isDir/fileSize在Controller中统一设置，
        // fileDigest暂不使用

        attachment.setArchive(aentity);
        return attachment;
    }
}
