/**
 * 
 */
package project.edge.domain.converter;

import java.io.File;
import java.text.ParseException;
import java.text.SimpleDateFormat;
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
import project.edge.domain.entity.Person;
import project.edge.domain.entity.Project;
import project.edge.domain.entity.ProjectPerson;
import project.edge.domain.entity.QualityObjective;
import project.edge.domain.entity.QualityReport;
import project.edge.domain.entity.QualityReportAttachment;
import project.edge.domain.view.ArchiveBean;
import project.edge.domain.view.QualityReportBean;


/**
 * @author angel_000
 *
 */
public class QualityReportBeanConverter implements ViewConverter<QualityReport, QualityReportBean> {

    @Override
    public QualityReportBean fromEntity(QualityReport entity, MessageSource messageSource,
            Locale locale) {

        QualityReportBean bean = new QualityReportBean();

        bean.setId(entity.getId());
        Project project = entity.getProject();

        if (project != null) {
            bean.setProject_(project.getId());
            bean.setProjectText(project.getProjectName());
        }

        bean.setReportCode(entity.getReportCode());

        QualityObjective objective = entity.getObjective();
        if (objective != null) {
            bean.setObjective_(objective.getId());
            bean.setObjectiveText(objective.getTaskName());
        }
        bean.setReportResult(entity.getReportResult());
        bean.setRemark(entity.getRemark());

        ProjectPerson reporter = entity.getReporter();
        if (reporter != null) {
            bean.setReporter_(reporter.getId());
            bean.setReporterText(reporter.getPerson().getPersonName());
        }

        if (entity.getReportDate() != null) {
            bean.setReportDate(
                    DateUtils.date2String(entity.getReportDate(), Constants.DATE_FORMAT));
        }

        // 附件处理
        ArchiveBeanConverter abConverter = new ArchiveBeanConverter();
        Set<QualityReportAttachment> qualityReportAttachments =
                entity.getQualityReportAttachments();
        for (QualityReportAttachment pa : qualityReportAttachments) {

            bean.getArchivesList()
                    .add(abConverter.fromEntity(pa.getArchive(), messageSource, locale));

        }

        return bean;
    }

    @Override
    public QualityReport toEntity(QualityReportBean bean, QualityReport oldEntity,
            AbstractSessionUserBean user, Date now) {

        QualityReport entity = oldEntity;
        if (entity == null) { // 表示新建
            entity = new QualityReport();

            entity.setCreator(user.getSessionUserId());
            entity.setcDatetime(now);

        } else {
            entity = oldEntity.clone();
            entity.setModifier(user.getSessionUserId());

            // 修改后，仍然保留的档案文件
            Map<String, String> map = new HashMap<>();
            for (String aid : bean.getArchivesReservedList()) {
                map.put(aid, aid);
            }

            // 找出需要删除的Archive
            for (QualityReportAttachment dbAttachment : oldEntity.getQualityReportAttachments()) {
                if (!map.containsKey(dbAttachment.getArchive().getId())) {
                    entity.getAttachmentsToDelete().add(dbAttachment);
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

        entity.setReportCode(bean.getReportCode());

        if (!StringUtils.isEmpty(bean.getObjective_())) {
            QualityObjective objective = new QualityObjective();
            objective.setId(bean.getObjective_());
            entity.setObjective(objective);
        }
        entity.setReportResult(bean.getReportResult());
        entity.setRemark(bean.getRemark());

        if (!StringUtils.isEmpty(bean.getReporter_())) {
//            Person reporter = new Person();
//            reporter.setId(bean.getReporter_());
//            entity.setReporter(reporter);
            ProjectPerson projectPerson = new ProjectPerson();
        	Person reporter = new Person();
        	reporter.setPersonName(bean.getReporterText());
            projectPerson.setId(bean.getReporter_());
            projectPerson.setPerson(reporter);
            entity.setReporter(projectPerson);
        }

        if (!StringUtils.isEmpty(bean.getReportDate())) {
            try {
                entity.setReportDate(
                        DateUtils.string2Date(bean.getReportDate(), Constants.DATE_FORMAT));
            } catch (ParseException e) {
                // do nothing
            }
        }

        // 附件处理，此处只需处理新增加的ProjectAttachment
        Set<QualityReportAttachment> qualityReportAttachments = new HashSet<>();

        ArchiveBeanConverter abConverter = new ArchiveBeanConverter();
        for (ArchiveBean ab : bean.getArchivesList()) {
            qualityReportAttachments.add(this.toAttachmentEntity(ab, abConverter, entity,
                    ProjectAttachmentTypeEnum.ARCHIVE.value(), user, now));
        }

        entity.setQualityReportAttachments(qualityReportAttachments);

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
    private QualityReportAttachment toAttachmentEntity(ArchiveBean ab,
            ArchiveBeanConverter abConverter, QualityReport entity, int attachmentType,
            AbstractSessionUserBean user, Date now) {

        QualityReportAttachment attachment = new QualityReportAttachment();
        attachment.setQualityReport(entity);

//        /**
//         * 第零层：根
//         * 第一层：FolderPath.PROJECT(DIR)
//         * 第二层：Project.id(DIR)
//         * 第三层：Archive文件
//         */
//        ab.setLevel(3);
//        ab.setPid(entity.getId());
//        ab.setPath(Constants.SLASH + Constants.COMMA + FolderPath.QUALITY_REPORT + Constants.COMMA
//                + entity.getId());
//        Archive aentity = abConverter.toEntity(ab, null, user, now);
//
//        // "\project\guid\xxx.doc"
//        // String extension = FilenameUtils.getExtension(ab.getArchiveName()).toLowerCase();
//        // String relativePath = File.separator + FolderPath.PROJECT + File.separator
//        // + entity.getId() + File.separator + aentity.getId() + Constants.DOT + extension;
//        String relativePath = File.separator + FolderPath.QUALITY_REPORT + File.separator
//                + entity.getId() + File.separator + aentity.getId();
        
        //add start by huang 
        /**
         * 第零层：根
         * 第一层：年份
         * 第二层：项目名称+项目编号 FolderPath.PROJECT(DIR)
         * 第三层: 系统归档
         * 第四层：文件夹名
         * 第五层：Archive文件
         */
        ab.setLevel(5);

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy");
        Date date = new Date();
        String year = sdf.format(date);

        ab.setPid(entity.getProject().getId() + "_" + FolderPath.QUALITY_REPORT);

        ab.setPath(Constants.SLASH + Constants.COMMA + year + Constants.COMMA
                + entity.getProject().getId() + Constants.COMMA
                + entity.getProject().getId() + "_" + FolderPath.SYSTEM_PLACE_ON_FILE + Constants.COMMA
                + entity.getProject().getId() + "_" + FolderPath.QUALITY_REPORT);
        
        Archive aentity = abConverter.toEntity(ab, null, user, now);
        
        String relativePath = File.separator + year + File.separator + entity.getProject().getId() + File.separator + entity.getProject().getId() + "_"
                + FolderPath.SYSTEM_PLACE_ON_FILE + File.separator + entity.getProject().getId() + "_"
                + FolderPath.QUALITY_REPORT + File.separator + aentity.getId();
        
        //add end by huang 
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
