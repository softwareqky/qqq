package project.edge.domain.converter;

import org.springframework.context.MessageSource;
import org.springframework.util.StringUtils;

import java.io.File;
import java.text.ParseException;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Locale;
import java.util.Map;
import java.util.Set;

import garage.origin.common.constant.Constants;
import garage.origin.common.util.DateUtils;
import garage.origin.domain.view.AbstractSessionUserBean;
import garage.origin.domain.view.converter.ViewConverter;
import project.edge.common.constant.FlowStatusEnum;
import project.edge.common.constant.FolderPath;
import project.edge.domain.entity.Archive;
import project.edge.domain.entity.Project;
import project.edge.domain.entity.ProjectPersonChange;
import project.edge.domain.view.ArchiveBean;
import project.edge.domain.view.ProjectPersonChangeBean;

/**
 * @author angel_000
 *         转换项目成员变更申请表对应的view和entity的转换器。
 */
public class ProjectPersonChangeBeanConverter
        implements ViewConverter<ProjectPersonChange, ProjectPersonChangeBean> {


    @Override
    public ProjectPersonChangeBean fromEntity(ProjectPersonChange entity,
            MessageSource messageSource, Locale locale) {
        // TODO Auto-generated method stub
        ProjectPersonChangeBean bean = new ProjectPersonChangeBean();


        bean.setId(entity.getId());

        Project project = entity.getProject();

        if (project != null) {
            bean.setProject_(project.getId());
            bean.setProjectText(project.getProjectName());
        }
        bean.setChangeReason(entity.getChangeReason());
        bean.setChangeRemark(entity.getChangeRemark());
        if (entity.getFlowStartDate() != null) {
            bean.setFlowStartDate(
                    DateUtils.date2String(entity.getFlowStartDate(), Constants.DATE_FORMAT));
        }

        if (entity.getFlowEndDate() != null) {
            bean.setFlowEndDate(
                    DateUtils.date2String(entity.getFlowEndDate(), Constants.DATE_FORMAT));
        }
        bean.setFlowStatus(entity.getFlowStatus());
        bean.setFlowStatusText(messageSource
                .getMessage(FlowStatusEnum.getResouceName(entity.getFlowStatus()), null, locale));

        ArchiveBeanConverter abConverter = new ArchiveBeanConverter();
        Set<Archive> archives = entity.getArchives();

        for (Archive archive : archives) {
            bean.getArchivesList().add(abConverter.fromEntity(archive, messageSource, locale));
        }

        return bean;
    }

    @Override
    public ProjectPersonChange toEntity(ProjectPersonChangeBean bean, ProjectPersonChange oldEntity,
            AbstractSessionUserBean user, Date now) {
        // TODO Auto-generated method stub
        ProjectPersonChange entity = oldEntity;

        Set<Archive> archives = new HashSet<>();

        if (entity == null) { // 表示新建
            entity = new ProjectPersonChange();

            entity.setCreator(user.getSessionUserId());
            entity.setcDatetime(now);
        } else { // 表示修改

            // 在Controller.postUpdate中，需要比较新老entity的关联的档案文件，所以必须clone
            // clone之后，清空外键关联字段的值
            entity = oldEntity.clone();
            entity.setArchives(new HashSet<>());
            entity.setModifier(user.getSessionUserId());

            // 修改后，仍然保留的档案文件
            Map<String, String> map = new HashMap<>();
            for (String aid : bean.getArchivesReservedList()) {
                Archive a = new Archive();
                a.setId(aid);
                archives.add(a);
                map.put(aid, aid);
            }

            // 找出需要删除的Archive
            for (Archive dbArchive : oldEntity.getArchives()) {
                if (!map.containsKey(dbArchive.getId())) {
                    entity.getArchivesToDelete().add(dbArchive);
                }
            }
        }

        bean.setId(entity.getId()); // ID必须赋值
        entity.setmDatetime(now);

        if (!StringUtils.isEmpty(bean.getProject_())) {
            Project project = new Project();
            project.setId(bean.getProject_());
            project.setProjectName(bean.getProjectText());
            entity.setProject(project);
        }
        entity.setChangeReason(bean.getChangeReason());
        entity.setChangeRemark(bean.getChangeRemark());

        if (!StringUtils.isEmpty(bean.getFlowStartDate())) {
            try {
                entity.setFlowStartDate(
                        DateUtils.string2Date(bean.getFlowStartDate(), Constants.DATE_FORMAT));
            } catch (ParseException e) {
                // do nothing
            }
        }

        if (!StringUtils.isEmpty(bean.getFlowEndDate())) {
            try {
                entity.setFlowEndDate(
                        DateUtils.string2Date(bean.getFlowEndDate(), Constants.DATE_FORMAT));
            } catch (ParseException e) {
                // do nothing
            }
        }

        entity.setFlowStatus(bean.getFlowStatus());

        ArchiveBeanConverter abConverter = new ArchiveBeanConverter();
        for (ArchiveBean ab : bean.getArchivesList()) {

            /**
             * 第零层：根
             * 第一层：FolderPath.PROJECT_PERSON_CHANGE(DIR)
             * 第二层：ProjectGroup.id(DIR)
             * 第三层：Archive文件
             */
            ab.setLevel(3);
            ab.setPid(entity.getId());
            ab.setPath(Constants.SLASH + Constants.COMMA + FolderPath.PROJECT_PERSON_CHANGE
                    + Constants.COMMA + entity.getId());
            Archive aentity = abConverter.toEntity(ab, null, user, now);
            archives.add(aentity);

            // "\project-group\guid\xxx.doc"
            String relativePath = File.separator + FolderPath.PROJECT_PERSON_CHANGE + File.separator
                    + entity.getId() + File.separator + ab.getRelativePath();
            ab.setRelativePath(relativePath);
            aentity.setRelativePath(relativePath);

            // Archive:
            // id/level/pid/(id)path/relativePath由converter设置，
            // archiveName/isDir/fileSize在Controller中设置，
            // fileDigest暂不使用
        }
        entity.setArchives(archives);

        return entity;
    }

}
