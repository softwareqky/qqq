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
import garage.origin.common.constant.OnOffEnum;
import garage.origin.common.util.DateUtils;
import garage.origin.domain.view.AbstractSessionUserBean;
import garage.origin.domain.view.converter.ViewConverter;
import project.edge.common.constant.FolderPath;
import project.edge.domain.entity.Archive;
import project.edge.domain.entity.DataOption;
import project.edge.domain.entity.Issue;
import project.edge.domain.entity.IssueAttachment;
import project.edge.domain.entity.Person;
import project.edge.domain.entity.Project;
import project.edge.domain.entity.ProjectPerson;
import project.edge.domain.entity.VirtualOrg;
import project.edge.domain.view.ArchiveBean;
import project.edge.domain.view.IssueBean;


/**
 * @author angel_000
 *         转换问题信息对应的view和entity的转换器。
 */
public class IssueBeanConverter implements ViewConverter<Issue, IssueBean> {

    @Override
    public IssueBean fromEntity(Issue entity, MessageSource messageSource, Locale locale) {
        // TODO Auto-generated method stub

        IssueBean bean = new IssueBean();

        bean.setId(entity.getId());

        Project project = entity.getProject();
        if (project != null) {
            bean.setProject_(project.getId());
            bean.setProjectText(project.getProjectName());
        }

        VirtualOrg virtualOrg = entity.getVirtualOrg();
        if (virtualOrg != null) {
            bean.setVirtualOrg_(virtualOrg.getId());
            bean.setVirtualOrgText(virtualOrg.getVirtualOrgName());
        }

        bean.setIssueTitle(entity.getIssueTitle());

        DataOption issueType = entity.getIssueType();
        if (issueType != null) {
            bean.setIssueType_(issueType.getId());
            bean.setIssueTypeText(issueType.getOptionName());
        }

        DataOption issuePriority = entity.getIssuePriority();
        if (issuePriority != null) {
            bean.setIssuePriority_(issuePriority.getId());
            bean.setIssuePriorityText(issuePriority.getOptionName());
        }

        if (entity.getInputDate() != null) {
            bean.setInputDate(DateUtils.date2String(entity.getInputDate(), Constants.DATE_FORMAT));
        }

        if (entity.getSolveDatetime() != null) {
            bean.setSolveDatetime(
                    DateUtils.date2String(entity.getSolveDatetime(), Constants.DATE_FORMAT));
        }

        ProjectPerson assignTo = entity.getAssignTo();
        if (assignTo != null) {
            bean.setAssignTo_(assignTo.getId());
            bean.setAssignToText(assignTo.getPerson().getPersonName());
        }

        bean.setInternalVerify(entity.getInternalVerify());
        bean.setInternalVerifyFeedback(entity.getInternalVerifyFeedback());
        bean.setExternalVerify(entity.getExternalVerify());
        bean.setExternalVerifyFeedback(entity.getExternalVerifyFeedback());
        bean.setIssueDesc(entity.getIssueDesc());

        ProjectPerson reassignTo = entity.getReassignTo();
        if (reassignTo != null) {
            bean.setReassignTo_(reassignTo.getId());
            bean.setReassignToText(reassignTo.getPerson().getPersonName());
        }

        bean.setReassignReason(entity.getReassignReason());
        bean.setSolveContent(entity.getSolveContent());

        DataOption solveStatus = entity.getSolveStatus();
        if (solveStatus != null) {
            bean.setSolveStatus_(solveStatus.getId());
            bean.setSolveStatusText(solveStatus.getOptionName());
        }
        
        Person person = entity.getCreator();
        if (person != null) {
            bean.setCreator_(person.getId());
            bean.setCreatorText(person.getPersonName());
        }

        // 附件处理
        ArchiveBeanConverter abConverter = new ArchiveBeanConverter();
        Set<IssueAttachment> issureAttachments = entity.getIssueAttachments();

        for (IssueAttachment pa : issureAttachments) {
            if (pa.getIsSolved() == OnOffEnum.ON.value()) {
                bean.getSlovearchiveList()
                        .add(abConverter.fromEntity(pa.getArchive(), messageSource, locale));
            } else {
                bean.getArchiveList()
                        .add(abConverter.fromEntity(pa.getArchive(), messageSource, locale));
            }
        }
        return bean;
    }

    @Override
    public Issue toEntity(IssueBean bean, Issue oldEntity, AbstractSessionUserBean user, Date now) {
        // TODO Auto-generated method stub

        Issue entity = oldEntity;
        // Set<IssueAttachment> archives = new HashSet<>();

        if (entity == null) { // 表示新建
            entity = new Issue();

            if (!StringUtils.isEmpty(user.getSessionUserId())) {
                Person person = new Person();
                person.setId(user.getSessionUserId());
                entity.setCreator(person);
            }
            entity.setcDatetime(now);
        } else { // 表示修改
            entity.setModifier(user.getSessionUserId());

            entity = oldEntity.clone();
            // 修改后，仍然保留的档案文件
            entity.setIssueAttachments(new HashSet<>());

            Map<String, String> map = new HashMap<>();
            for (String aid : bean.getArchiveReservedList()) {
                map.put(aid, aid);
            }

            for (String aid : bean.getSolveArchiveReservedList()) {
                map.put(aid, aid);
            }
            // 找出需要删除的Archive
            for (IssueAttachment dbAttachment : oldEntity.getIssueAttachments()) {

                boolean needCheck = false;
                String uploadType = bean.getUploadFileType();
                int attachmentType = dbAttachment.getIsSolved();

                if (StringUtils.isEmpty(uploadType)) {
                    needCheck = attachmentType == OnOffEnum.OFF.value();
                } else if ("solve".equals(uploadType)) {
                    needCheck = attachmentType == OnOffEnum.ON.value();
                }

                if (needCheck && !map.containsKey(dbAttachment.getArchive().getId())) {
                    entity.getAttachmentsToDelete().add(dbAttachment);
                }
            }
        }

        bean.setId(entity.getId()); // ID必须赋值
        entity.setmDatetime(now);

        if (StringUtils.isEmpty(bean.getUploadFileType())) {
            if (!StringUtils.isEmpty(bean.getProject_())) {
                Project project = new Project();
                project.setId(bean.getProject_());
                project.setProjectName(bean.getProjectText());
                entity.setProject(project);
            }

            if (!StringUtils.isEmpty(bean.getVirtualOrg_())) {
                VirtualOrg virtualOrg = new VirtualOrg();
                virtualOrg.setId(bean.getVirtualOrg_());
                entity.setVirtualOrg(virtualOrg);
            }

            entity.setIssueTitle(bean.getIssueTitle());

            if (!StringUtils.isEmpty(bean.getIssueType_())) {
                DataOption issueType = new DataOption();
                issueType.setId(bean.getIssueType_());
                entity.setIssueType(issueType);
            }

            if (!StringUtils.isEmpty(bean.getIssuePriority_())) {
                DataOption issuePriority = new DataOption();
                issuePriority.setId(bean.getIssuePriority_());
                entity.setIssuePriority(issuePriority);
            }

            if (!StringUtils.isEmpty(bean.getInputDate())) {
                try {
                    entity.setInputDate(
                            DateUtils.string2Date(bean.getInputDate(), Constants.DATE_FORMAT));
                } catch (ParseException e) {
                    // do nothing
                }
            }

            if (!StringUtils.isEmpty(bean.getAssignTo_())) {
                ProjectPerson assignTo = new ProjectPerson();
                Person person = new Person();
                person.setPersonName(bean.getAssignToText());
                assignTo.setId(bean.getAssignTo_());
                assignTo.setPerson(person);
                entity.setAssignTo(assignTo);
            }

            entity.setIssueDesc(bean.getIssueDesc());
        }

        if (!StringUtils.isEmpty(bean.getSolveDatetime())) {
            try {
                entity.setSolveDatetime(
                        DateUtils.string2Date(bean.getSolveDatetime(), Constants.DATE_FORMAT));
            } catch (ParseException e) {
                // do nothing
            }
        }

        entity.setInternalVerify(bean.getInternalVerify());
        entity.setInternalVerifyFeedback(bean.getInternalVerifyFeedback());
        entity.setExternalVerify(bean.getExternalVerify());
        entity.setExternalVerifyFeedback(bean.getExternalVerifyFeedback());

        if (!StringUtils.isEmpty(bean.getReassignTo_())) {
            ProjectPerson reassignTo = new ProjectPerson();
            Person person = new Person();
            person.setPersonName(bean.getReassignToText());
            reassignTo.setId(bean.getReassignTo_());
            reassignTo.setPerson(person);
            entity.setReassignTo(reassignTo);
        }

        entity.setReassignReason(bean.getReassignReason());
        entity.setSolveContent(bean.getSolveContent());

        if (!StringUtils.isEmpty(bean.getSolveStatus_())) {
            DataOption solveStatus = new DataOption();
            solveStatus.setId(bean.getSolveStatus_());
            entity.setSolveStatus(solveStatus);
        }

        // 附件处理，此处只需处理新增加的ProjectAttachment
        Set<IssueAttachment> issueAttachments = new HashSet<>();

        ArchiveBeanConverter abConverter = new ArchiveBeanConverter();
        for (ArchiveBean ab : bean.getArchiveList()) {
            issueAttachments.add(this.toAttachmentEntity(ab, abConverter, entity,
                    OnOffEnum.OFF.value(), user, now));
        }
        for (ArchiveBean ab : bean.getSlovearchiveList()) {
            issueAttachments.add(this.toAttachmentEntity(ab, abConverter, entity,
                    OnOffEnum.ON.value(), user, now));
        }
        entity.setIssueAttachments(issueAttachments);
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
    private IssueAttachment toAttachmentEntity(ArchiveBean ab, ArchiveBeanConverter abConverter,
            Issue entity, short isSolve, AbstractSessionUserBean user, Date now) {

        IssueAttachment attachment = new IssueAttachment();
        attachment.setIsSolved(isSolve);
        attachment.setIssue(entity);

//        /**
//         * 第零层：根
//         * 第一层：FolderPath.PROJECT(DIR)
//         * 第二层：Project.id(DIR)
//         * 第三层：Archive文件
//         */
//        ab.setLevel(3);
//        ab.setPid(entity.getId());
//        ab.setPath(Constants.SLASH + Constants.COMMA + FolderPath.ISSUE + Constants.COMMA
//                + entity.getId());
//        Archive aentity = abConverter.toEntity(ab, null, user, now);
//
//        // "\project\guid\xxx.doc"
//        // String extension = FilenameUtils.getExtension(ab.getArchiveName()).toLowerCase();
//        // String relativePath = File.separator + FolderPath.PROJECT + File.separator
//        // + entity.getId() + File.separator + aentity.getId() + Constants.DOT + extension;
//        String relativePath = File.separator + FolderPath.ISSUE + File.separator + entity.getId()
//                + File.separator + aentity.getId();
        
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

        ab.setPid(entity.getProject().getId() + "_" + FolderPath.ISSUE);

        ab.setPath(Constants.SLASH + Constants.COMMA + year + Constants.COMMA
                + entity.getProject().getId() + Constants.COMMA
                + entity.getProject().getId() + "_" + FolderPath.SYSTEM_PLACE_ON_FILE + Constants.COMMA
                + entity.getProject().getId() + "_" + FolderPath.ISSUE);
        
        Archive aentity = abConverter.toEntity(ab, null, user, now);
        
        String relativePath = File.separator + year + File.separator + entity.getProject().getId() + File.separator + entity.getProject().getId() + "_"
                + FolderPath.SYSTEM_PLACE_ON_FILE + File.separator + entity.getProject().getId() + "_"
                + FolderPath.ISSUE + File.separator + aentity.getId();
        
        
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
