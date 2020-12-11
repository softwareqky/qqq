package project.edge.domain.converter;

import org.apache.commons.io.FilenameUtils;
import org.springframework.context.MessageSource;
import org.springframework.util.StringUtils;

import java.io.File;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Locale;
import java.util.Map;
import java.util.Set;

import garage.origin.common.constant.Constants;
import garage.origin.common.constant.OnOffEnum;
import garage.origin.domain.view.AbstractSessionUserBean;
import garage.origin.domain.view.converter.ViewConverter;
import project.edge.common.constant.FolderPath;
import project.edge.domain.entity.Archive;
import project.edge.domain.entity.Person;
import project.edge.domain.entity.ProjectGroup;
import project.edge.domain.view.ArchiveBean;
import project.edge.domain.view.ProjectGroupBean;

/**
 * 转换项目组对应的view和entity的转换器。
 */
public class ProjectGroupBeanConverter implements ViewConverter<ProjectGroup, ProjectGroupBean> {

    @Override
    public ProjectGroupBean fromEntity(ProjectGroup entity, MessageSource messageSource,
            Locale locale) {

        ProjectGroupBean bean = new ProjectGroupBean();
        bean.setId(entity.getId());
        bean.setGroupName(entity.getGroupName());
        bean.setGroupDesc(entity.getGroupDesc());

        Person person = entity.getLeader();

        if (person != null) {
            bean.setLeader_(person.getId());
            bean.setLeaderText(person.getPersonName());
        } else {
            bean.setLeader_("");
            bean.setLeaderText("");
        }
        bean.setLeaderName(entity.getLeaderName());
        bean.setLeaderMobile(entity.getLeaderMobile());

        ArchiveBeanConverter abConverter = new ArchiveBeanConverter();
        Set<Archive> archives = entity.getArchives();
        for (Archive archive : archives) {
            bean.getArchivesList().add(abConverter.fromEntity(archive, messageSource, locale));
        }

        return bean;
    }

    @Override
    public ProjectGroup toEntity(ProjectGroupBean bean, ProjectGroup oldEntity,
            AbstractSessionUserBean user, Date now) {

        ProjectGroup entity = oldEntity;

        Set<Archive> archives = new HashSet<>();

        if (entity == null) { // 表示新建
            entity = new ProjectGroup();

            entity.setCreator(user.getSessionUserId());
            entity.setcDatetime(now);
        } else { // 表示修改

            // 在Controller.postUpdate中，需要比较新老entity的关联的档案文件，所以必须clone
            // clone之后，清空外键关联字段的值
            entity = oldEntity.clone();
            entity.setArchives(new HashSet<>());
            entity.setLeader(null);

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

        entity.setIsDeleted(OnOffEnum.OFF.value());

        entity.setGroupName(bean.getGroupName());
        entity.setGroupDesc(bean.getGroupDesc());
        entity.setLeaderName(bean.getLeaderName());
        entity.setLeaderMobile(bean.getLeaderMobile());

        if (!StringUtils.isEmpty(bean.getLeader_())) {
            Person person = new Person();
            person.setId(bean.getLeader_());
            entity.setLeader(person);
        }

        ArchiveBeanConverter abConverter = new ArchiveBeanConverter();
        for (ArchiveBean ab : bean.getArchivesList()) {

            /**
             * 第零层：根
             * 第一层：FolderPath.PROJECT_GROUP(DIR)
             * 第二层：ProjectGroup.id(DIR)
             * 第三层：Archive文件
             */
            ab.setLevel(3);
            ab.setPid(entity.getId());
            ab.setPath(Constants.SLASH + Constants.COMMA + FolderPath.PROJECT_GROUP
                    + Constants.COMMA + entity.getId());
            Archive aentity = abConverter.toEntity(ab, null, user, now);
            archives.add(aentity);

            // "\project-group\guid\xxx.doc"
            String extension = FilenameUtils.getExtension(ab.getArchiveName()).toLowerCase();
            String relativePath = File.separator + FolderPath.PROJECT_GROUP + File.separator
                    + entity.getId() + File.separator + aentity.getId() + Constants.DOT + extension;
            ab.setRelativePath(relativePath);
            aentity.setRelativePath(relativePath);

            // Archive:
            // id/level/pid/(id)path/relativePath由converter各自设置，
            // archiveName/isDir/fileSize在Controller中统一设置，
            // fileDigest暂不使用
        }
        entity.setArchives(archives);

        return entity;
    }

}
