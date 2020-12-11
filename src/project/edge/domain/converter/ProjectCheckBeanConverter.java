package project.edge.domain.converter;

import org.springframework.context.MessageSource;
import org.springframework.util.StringUtils;

import java.io.File;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Locale;
import java.util.Map;
import java.util.Set;

import garage.origin.common.constant.Constants;
import garage.origin.common.constant.OnOffEnum;
import garage.origin.common.util.DateUtils;
import garage.origin.domain.view.AbstractSessionUserBean;
import garage.origin.domain.view.converter.ViewConverter;
import project.edge.common.constant.FlowStatusEnum;
import project.edge.common.constant.FolderPath;
import project.edge.domain.entity.Archive;
import project.edge.domain.entity.DataOption;
import project.edge.domain.entity.Project;
import project.edge.domain.entity.ProjectCheck;
import project.edge.domain.entity.ProjectCheckAttachment;
import project.edge.domain.view.ArchiveBean;
import project.edge.domain.view.ProjectCheckBean;

/**
 * @author angel_000
 *         转换项目检查表息对应的view和entity的转换器。
 *
 */
public class ProjectCheckBeanConverter implements ViewConverter<ProjectCheck, ProjectCheckBean> {

    @Override
    public ProjectCheckBean fromEntity(ProjectCheck entity, MessageSource messageSource,
            Locale locale) {
        // TODO Auto-generated method stub
        ProjectCheckBean bean = new ProjectCheckBean();

        bean.setId(entity.getId());

        Project project = entity.getProject();
        if (project != null) {
            bean.setProject_(project.getId());
            bean.setProjectText(project.getProjectName());
        }

        DataOption checkType = entity.getCheckType();
        if (checkType != null) {
            bean.setCheckType_(checkType.getId());
            bean.setCheckTypeText(checkType.getOptionName());
        }
        
        DataOption checkKind = entity.getCheckKind();
        if (checkKind != null) {
        	bean.setCheckKind_(checkKind.getId());
        	bean.setCheckKindText(checkKind.getOptionName());
        }
        
        bean.setCheckingUnit(entity.getCheckingUnit());
        bean.setChechedUnit(entity.getChechedUnit());
        bean.setCheckedContent(entity.getCheckedContent());
        bean.setCheckBasis(entity.getCheckBasis());

        /*
         * Set<Archive> archives = entity.getArchives();
         * 
         * if (archives != null && !archives.isEmpty()) {
         * for (Archive archive : archives) {
         * bean.getArchive_().add(archive.getId());
         * }
         * }
         */
        bean.setChecker(entity.getChecker());

        bean.setNotification(entity.getNotification());
        bean.setAdditionalExplanation(entity.getAdditionalExplanation());
        bean.setIsImprove(entity.getIsImprove());
        if (!StringUtils.isEmpty(entity.getIsImprove())) {
	        bean.setIsImproveText(entity.getIsImprove() == 1
	                ? messageSource.getMessage(OnOffEnum.ON.resourceName(), null, locale)
	                : messageSource.getMessage(OnOffEnum.OFF.resourceName(), null, locale));
        }

        DataOption chechResult = entity.getCheckResult();
        if (chechResult != null) {
            bean.setCheckResult_(chechResult.getId());
            bean.setCheckResultText(chechResult.getOptionName());
        }

        DataOption verifyResult = entity.getVerifyResult();
        if (verifyResult != null) {
            bean.setVerifyResult_(verifyResult.getId());
            bean.setVerifyResultText(verifyResult.getOptionName());
        }

        if (entity.getCheckDate() != null) {
            bean.setCheckDate(DateUtils.date2String(entity.getCheckDate(), Constants.DATE_FORMAT));
        }

        if (entity.getImproveReqDate() != null) {
            bean.setImproveReqDate(
                    DateUtils.date2String(entity.getImproveReqDate(), Constants.DATE_FORMAT));
        }


        if (entity.getImprovePlanDate() != null) {
            bean.setImprovePlanDate(
                    DateUtils.date2String(entity.getImprovePlanDate(), Constants.DATE_FORMAT));
        }


        if (entity.getImproveActualDate() != null) {
            bean.setImproveActualDate(
                    DateUtils.date2String(entity.getImproveActualDate(), Constants.DATE_FORMAT));
        }

        bean.setCheckResultContent(entity.getCheckResultContent());
        bean.setImproveReq(entity.getImproveReq());
        bean.setImprovePlan(entity.getImprovePlan());

        if (entity.getVerifyDate() != null) {
            bean.setVerifyDate(
                    DateUtils.date2String(entity.getVerifyDate(), Constants.DATE_FORMAT));
        }
        bean.setImproveResultVerify(entity.getImproveResultVerify());
        bean.setRemark(entity.getRemark());

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

        DataOption checkStatus = entity.getCheckStatus();
        if (checkStatus != null) {
            bean.setCheckStatus_(checkStatus.getId());
            bean.setCheckStatusText(checkStatus.getOptionName());
        }

        // 附件处理
        ArchiveBeanConverter abConverter = new ArchiveBeanConverter();
        Set<ProjectCheckAttachment> projectCheckAttachments = entity.getProjectCheckAttachments();

        for (ProjectCheckAttachment pa : projectCheckAttachments) {
            if (pa.getIsImprove() == OnOffEnum.ON.value()) {
                bean.getImprovearchiveList()
                        .add(abConverter.fromEntity(pa.getArchive(), messageSource, locale));
            } else {
                bean.getArchiveList()
                        .add(abConverter.fromEntity(pa.getArchive(), messageSource, locale));
            }
        }

        return bean;
    }

    @Override
    public ProjectCheck toEntity(ProjectCheckBean bean, ProjectCheck oldEntity,
            AbstractSessionUserBean user, Date now) {
        // TODO Auto-generated method stub
        ProjectCheck entity = oldEntity;

        if (entity == null) { // 表示新建
            entity = new ProjectCheck();

            entity.setRemark("");
            entity.setCreator(user.getSessionUserId());
            entity.setcDatetime(now);
        } else { // 表示修改
            entity.setModifier(user.getSessionUserId());

            entity = oldEntity.clone();
            // 修改后，仍然保留的档案文件
            entity.setProjectCheckAttachments(new HashSet<>());

            Map<String, String> map = new HashMap<>();
            for (String aid : bean.getArchiveReservedList()) {
                map.put(aid, aid);
            }

            for (String aid : bean.getImprovearchiveReservedList()) {
                map.put(aid, aid);
            }
            // 找出需要删除的Archive
            for (ProjectCheckAttachment dbAttachment : oldEntity.getProjectCheckAttachments()) {

                boolean needCheck = false;
                String uploadType = bean.getUploadFileType();
                int attachmentType = dbAttachment.getIsImprove();

                if (StringUtils.isEmpty(uploadType)) {
                    needCheck = attachmentType == OnOffEnum.OFF.value();
                } else if ("verification".equals(uploadType)) {
                    needCheck = attachmentType == OnOffEnum.ON.value();
                }

                if (needCheck && !map.containsKey(dbAttachment.getArchive().getId())) {
                    entity.getAttachmentsToDelete().add(dbAttachment);
                }
            }
        }

        bean.setId(entity.getId()); // ID必须赋值
        entity.setmDatetime(now);

        //if (StringUtils.isEmpty(bean.getUploadFileType())) {

            if (!StringUtils.isEmpty(bean.getProject_())) {
                Project project = new Project();
                project.setId(bean.getProject_());
                project.setProjectName(bean.getProjectText());
                entity.setProject(project);
            }

            if (!StringUtils.isEmpty(bean.getCheckType_())) {
                DataOption checkType = new DataOption();
                checkType.setId(bean.getCheckType_());
                entity.setCheckType(checkType);
            }
            
            if (!StringUtils.isEmpty(bean.getCheckKind_())) {
                DataOption checkKind = new DataOption();
                checkKind.setId(bean.getCheckKind_());
                entity.setCheckKind(checkKind);
            }

            if (!StringUtils.isEmpty(bean.getCheckingUnit()))entity.setCheckingUnit(bean.getCheckingUnit());
            if (!StringUtils.isEmpty(bean.getChechedUnit()))entity.setChechedUnit(bean.getChechedUnit());
            if (!StringUtils.isEmpty(bean.getCheckedContent()))entity.setCheckedContent(bean.getCheckedContent());
            if (!StringUtils.isEmpty(bean.getCheckBasis()))entity.setCheckBasis(bean.getCheckBasis());

            if (!StringUtils.isEmpty(bean.getChecker()))entity.setChecker(bean.getChecker());
            if (!StringUtils.isEmpty(bean.getIsImprove()))entity.setIsImprove(bean.getIsImprove());

            if (!StringUtils.isEmpty(bean.getNotification()))entity.setNotification(bean.getNotification());
            if (!StringUtils.isEmpty(bean.getAdditionalExplanation()))entity.setAdditionalExplanation(bean.getAdditionalExplanation());

            if (!StringUtils.isEmpty(bean.getCheckResult_())) {
                DataOption checkResult = new DataOption();
                checkResult.setId(bean.getCheckResult_());
                entity.setCheckResult(checkResult);
            }

//            if (!StringUtils.isEmpty(bean.getCheckStatus_())) {
//                DataOption checkStatus = new DataOption();
//                checkStatus.setId(bean.getCheckStatus_());
//                entity.setCheckStatus(checkStatus);
//            }

            if (!StringUtils.isEmpty(bean.getCheckDate())) {
                try {
                    entity.setCheckDate(
                            DateUtils.string2Date(bean.getCheckDate(), Constants.DATE_FORMAT));
                } catch (ParseException e) {
                    // do nothing
                }
            }

            if (!StringUtils.isEmpty(bean.getImproveReqDate())) {
                try {
                    entity.setImproveReqDate(
                            DateUtils.string2Date(bean.getImproveReqDate(), Constants.DATE_FORMAT));
                } catch (ParseException e) {
                    // do nothing
                }
            }

            if (!StringUtils.isEmpty(bean.getCheckResultContent()))entity.setCheckResultContent(bean.getCheckResultContent());
            if (!StringUtils.isEmpty(bean.getRemark()))entity.setRemark(bean.getRemark());
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
            // entity.setFlowStatus(bean.getFlowStatus()); // 审批流程状态应该由oa回调设定
        //}

        if (!StringUtils.isEmpty(bean.getCheckStatus_())) {
            DataOption checkStatus = new DataOption();
            checkStatus.setId(bean.getCheckStatus_());
            entity.setCheckStatus(checkStatus);
        }
        
        if (!StringUtils.isEmpty(bean.getImprovePlanDate())) {
            try {
                entity.setImprovePlanDate(
                        DateUtils.string2Date(bean.getImprovePlanDate(), Constants.DATE_FORMAT));
            } catch (ParseException e) {
                // do nothing
            }
        }

        if (!StringUtils.isEmpty(bean.getImproveActualDate())) {
            try {
                entity.setImproveActualDate(
                        DateUtils.string2Date(bean.getImproveActualDate(), Constants.DATE_FORMAT));
            } catch (ParseException e) {
                // do nothing
            }
        }

        if (!StringUtils.isEmpty(bean.getImproveReq()))entity.setImproveReq(bean.getImproveReq());
        if (!StringUtils.isEmpty(bean.getImprovePlan()))entity.setImprovePlan(bean.getImprovePlan());

        if (!StringUtils.isEmpty(bean.getVerifyDate())) {
            try {
                entity.setVerifyDate(
                        DateUtils.string2Date(bean.getVerifyDate(), Constants.DATE_FORMAT));
            } catch (ParseException e) {
                // do nothing
            }
        }
        entity.setImproveResultVerify(bean.getImproveResultVerify());

        if (!StringUtils.isEmpty(bean.getVerifyResult_())) {
            DataOption verifyResult = new DataOption();
            verifyResult.setId(bean.getVerifyResult_());
            entity.setVerifyResult(verifyResult);
        }


        // 附件处理，此处只需处理新增加的ProjectAttachment
        Set<ProjectCheckAttachment> projectCheckAttachments = new HashSet<>();

        ArchiveBeanConverter abConverter = new ArchiveBeanConverter();
        for (ArchiveBean ab : bean.getArchiveList()) {
            projectCheckAttachments.add(this.toAttachmentEntity(ab, abConverter, entity,
                    OnOffEnum.OFF.value(), user, now));
        }
        for (ArchiveBean ab : bean.getImprovearchiveList()) {
            projectCheckAttachments.add(this.toAttachmentEntity(ab, abConverter, entity,
                    OnOffEnum.ON.value(), user, now));
        }
        entity.setProjectCheckAttachments(projectCheckAttachments);

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
    private ProjectCheckAttachment toAttachmentEntity(ArchiveBean ab,
            ArchiveBeanConverter abConverter, ProjectCheck entity, short isImprove,
            AbstractSessionUserBean user, Date now) {

        ProjectCheckAttachment attachment = new ProjectCheckAttachment();
        attachment.setIsImprove(isImprove);
        attachment.setProjectCheck(entity);

// changed start by huang 20200509
//        /**
//         * 第零层：根
//         * 第一层：FolderPath.PROJECT(DIR)
//         * 第二层：Project.id(DIR)
//         * 第三层：Archive文件
//         */
//        ab.setLevel(3);
//        ab.setPid(entity.getId());
//        ab.setPath(Constants.SLASH + Constants.COMMA + FolderPath.PROJECT_CHECK + Constants.COMMA
//                + entity.getId());
//        Archive aentity = abConverter.toEntity(ab, null, user, now);
//
//        // "\project\guid\xxx.doc"
//        // String extension = FilenameUtils.getExtension(ab.getArchiveName()).toLowerCase();
//        // String relativePath = File.separator + FolderPath.PROJECT + File.separator
//        // + entity.getId() + File.separator + aentity.getId() + Constants.DOT + extension;
//        String relativePath = File.separator + FolderPath.PROJECT_CHECK + File.separator
//                + entity.getId() + File.separator + aentity.getId();
       
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

        ab.setPid(entity.getProject().getId() + "_" + FolderPath.PROJECT_CHECK);

        ab.setPath(Constants.SLASH + Constants.COMMA + year + Constants.COMMA
                + entity.getProject().getId() + Constants.COMMA
                + entity.getProject().getId() + "_" + FolderPath.SYSTEM_PLACE_ON_FILE + Constants.COMMA
                + entity.getProject().getId() + "_" + FolderPath.PROJECT_CHECK);
        // chagned end by huang 20200404
        
        Archive aentity = abConverter.toEntity(ab, null, user, now);
        
        String relativePath = File.separator + year + File.separator + entity.getProject().getId() + File.separator + entity.getProject().getId() + "_"
                + FolderPath.SYSTEM_PLACE_ON_FILE + File.separator + entity.getProject().getId() + "_"
                + FolderPath.PROJECT_CHECK + File.separator + aentity.getId();
        
//changed end by huang 20200509        
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
