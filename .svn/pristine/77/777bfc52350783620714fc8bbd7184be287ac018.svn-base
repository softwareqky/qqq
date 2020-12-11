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
import project.edge.common.constant.FlowStatusEnum;
import project.edge.common.constant.FolderPath;
import project.edge.domain.entity.Archive;
import project.edge.domain.entity.DataOption;
import project.edge.domain.entity.Person;
import project.edge.domain.entity.Project;
import project.edge.domain.entity.ProjectInspect;
import project.edge.domain.entity.ProjectInspectAttachment;
import project.edge.domain.entity.Site;
import project.edge.domain.view.ArchiveBean;
import project.edge.domain.view.ProjectInspectBean;

/**
 * @author angel_000
 *         转换项目巡查表对应的view和entity的转换器。
 *
 */
public class ProjectInspectBeanConverter
        implements ViewConverter<ProjectInspect, ProjectInspectBean> {

    @Override
    public ProjectInspectBean fromEntity(ProjectInspect entity, MessageSource messageSource,
            Locale locale) {
        // TODO Auto-generated method stub
        ProjectInspectBean bean = new ProjectInspectBean();

        bean.setId(entity.getId());

        Site site = entity.getSite();
        if (site != null) {
            bean.setSite_(site.getId());
            bean.setSiteText(site.getStationName());
        }
        DataOption inspectType = entity.getInspectType();
        if (inspectType != null) {
            bean.setInspectType_(inspectType.getId());
            bean.setInspectTypeText(inspectType.getOptionName());
        }

        Project project = entity.getProject();
        if (project != null) {
            bean.setProject_(project.getId());
            bean.setProjectText(project.getProjectName());
        }

        bean.setInspectPlan(entity.getInspectPlan());
        bean.setInspectArea(entity.getInspectArea());
        bean.setInspectBasis(entity.getInspectBasis());
        bean.setInspectContent(entity.getInspectContent());
        Person inspector = entity.getInspector();
        if (inspector != null) {
        	bean.setInspector_(inspector.getId());
        	bean.setInspectorText(inspector.getPersonName());
        }
        bean.setIsImprove(entity.getIsImprove());
        if (entity.getIsImprove() != null) {
        	bean.setIsImproveText(entity.getIsImprove() == 1
                ? messageSource.getMessage(OnOffEnum.ON.resourceName(), null, locale)
                : messageSource.getMessage(OnOffEnum.OFF.resourceName(), null, locale));
        }

        DataOption inspectResult = entity.getInspectResult();
        if (inspectResult != null) {
            bean.setInspectResult_(inspectResult.getId());
            bean.setInspectResultText(inspectResult.getOptionName());
        }

        DataOption verifyResult = entity.getVerifyResult();
        if (verifyResult != null) {
            bean.setVerifyResult_(verifyResult.getId());
            bean.setVerifyResultText(verifyResult.getOptionName());
        }

        if (entity.getInspectDate() != null) {
            bean.setInspectDate(
                    DateUtils.date2String(entity.getInspectDate(), Constants.DATE_FORMAT));
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
        bean.setInspectResultContent(entity.getInspectResultContent());
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

        // ArchiveBeanConverter abConverter = new ArchiveBeanConverter();
        // Set<Archive> archives = entity.getArchives();
        // for (Archive archive : archives) {
        // bean.getArchivesList().add(abConverter.fromEntity(archive, messageSource, locale));
        // }

        DataOption inspectStatus = entity.getInspectStatus();
        if (inspectStatus != null) {
            bean.setInspectStatus_(inspectStatus.getId());
            bean.setInspectStatusText(inspectStatus.getOptionName());
        }

        // 附件处理
        ArchiveBeanConverter abConverter = new ArchiveBeanConverter();
        Set<ProjectInspectAttachment> projectInspectAttachments =
                entity.getProjectInspectAttachments();

        for (ProjectInspectAttachment pa : projectInspectAttachments) {
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
    public ProjectInspect toEntity(ProjectInspectBean bean, ProjectInspect oldEntity,
            AbstractSessionUserBean user, Date now) {
        // TODO Auto-generated method stub
        ProjectInspect entity = oldEntity;
        //Set<Archive> archives = new HashSet<>();

        if (entity == null) { // 表示新建
            entity = new ProjectInspect();

            entity.setRemark("");
            entity.setCreator(user.getSessionUserId());
            entity.setcDatetime(now);
        } else { // 表示修改
            // 在Controller.postUpdate中，需要比较新老entity的关联的档案文件，所以必须clone
            // clone之后，清空外键关联字段的值

            entity.setModifier(user.getSessionUserId());
            entity = oldEntity.clone();

            // entity.setArchives(new HashSet<>());
            //
            // // 修改后，仍然保留的档案文件
            // Map<String, String> map = new HashMap<>();
            // for (String aid : bean.getArchivesReservedList()) {
            // Archive a = new Archive();
            // a.setId(aid);
            // archives.add(a);
            // map.put(aid, aid);
            // }
            //
            // // 找出需要删除的Archive
            // for (Archive dbArchive : oldEntity.getArchives()) {
            // if (!map.containsKey(dbArchive.getId())) {
            // entity.getArchivesToDelete().add(dbArchive);
            // }
            // }

            // 修改后，仍然保留的档案文件
            entity.setProjectInspectAttachments(new HashSet<>());

            Map<String, String> map = new HashMap<>();
            for (String aid : bean.getArchiveReservedList()) {
                map.put(aid, aid);
            }

            for (String aid : bean.getImprovearchiveReservedList()) {
                map.put(aid, aid);
            }
            // 找出需要删除的Archive
            for (ProjectInspectAttachment dbAttachment : oldEntity.getProjectInspectAttachments()) {

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

            if (!StringUtils.isEmpty(bean.getSite_())) {
                Site site = new Site();
                site.setId(bean.getSite_());
                entity.setSite(site);
            }

            if (!StringUtils.isEmpty(bean.getInspectType_())) {
                DataOption inspectType = new DataOption();
                inspectType.setId(bean.getInspectType_());
                entity.setInspectType(inspectType);
            }
            
            if (!StringUtils.isEmpty(bean.getProject_())) {
                Project project = new Project();
                project.setId(bean.getProject_());
                project.setProjectName(bean.getProjectText());
                entity.setProject(project);
            }

            if (!StringUtils.isEmpty(bean.getInspectPlan()))entity.setInspectPlan(bean.getInspectPlan());
            if (!StringUtils.isEmpty(bean.getInspectArea()))entity.setInspectArea(bean.getInspectArea());
            if (!StringUtils.isEmpty(bean.getInspectBasis()))entity.setInspectBasis(bean.getInspectBasis());
            if (!StringUtils.isEmpty(bean.getInspectContent()))entity.setInspectContent(bean.getInspectContent());
            if (!StringUtils.isEmpty(bean.getInspector_())) {
            	Person inspector = new Person();
            	inspector.setId(bean.getInspector_());
            	entity.setInspector(inspector);
            }
            if (!StringUtils.isEmpty(bean.getIsImprove()))entity.setIsImprove(bean.getIsImprove());

            if (!StringUtils.isEmpty(bean.getInspectResult_())) {
                DataOption inspectResult = new DataOption();
                inspectResult.setId(bean.getInspectResult_());
                entity.setInspectResult(inspectResult);
            }
            
            if (!StringUtils.isEmpty(bean.getInspectDate())) {
                try {
                    entity.setInspectDate(
                            DateUtils.string2Date(bean.getInspectDate(), Constants.DATE_FORMAT));
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
            
            if (!StringUtils.isEmpty(bean.getInspectResultContent()))entity.setInspectResultContent(bean.getInspectResultContent());
            
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
            
            // entity.setFlowStatus(bean.getFlowStatus()); // 流程审批状态由oa回调时设定
        //}

        if (!StringUtils.isEmpty(bean.getInspectStatus_())) {
            DataOption inspectStatus = new DataOption();
            inspectStatus.setId(bean.getInspectStatus_());
            entity.setInspectStatus(inspectStatus);
        }

        if (!StringUtils.isEmpty(bean.getVerifyResult_())) {
            DataOption verifyResult = new DataOption();
            verifyResult.setId(bean.getVerifyResult_());
            entity.setVerifyResult(verifyResult);
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
        if (!StringUtils.isEmpty(bean.getImproveResultVerify()))entity.setImproveResultVerify(bean.getImproveResultVerify());

        // ArchiveBeanConverter abConverter = new ArchiveBeanConverter();
        // for (ArchiveBean ab : bean.getArchivesList()) {
        //
        // /**
        // * 第零层：根
        // * 第一层：FolderPath.PROJECT_GROUP(DIR)
        // * 第二层：ProjectGroup.id(DIR)
        // * 第三层：Archive文件
        // */
        // ab.setLevel(3);
        // ab.setPid(entity.getId());
        // ab.setPath(Constants.SLASH + Constants.COMMA + FolderPath.PROJECT_INSPECT
        // + Constants.COMMA + entity.getId());
        // Archive aentity = abConverter.toEntity(ab, null, user, now);
        // archives.add(aentity);
        //
        // // "\project-group\guid\xxx.doc"
        // String relativePath = File.separator + FolderPath.PROJECT_INSPECT + File.separator
        // + entity.getId() + File.separator + ab.getRelativePath();
        // ab.setRelativePath(relativePath);
        // aentity.setRelativePath(relativePath);
        //
        // }
        // entity.setArchives(archives);

        // 附件处理，此处只需处理新增加的ProjectAttachment
        Set<ProjectInspectAttachment> projectInspectAttachments = new HashSet<>();

        ArchiveBeanConverter abConverter = new ArchiveBeanConverter();
        for (ArchiveBean ab : bean.getArchiveList()) {
            projectInspectAttachments.add(this.toAttachmentEntity(ab, abConverter, entity,
                    OnOffEnum.OFF.value(), user, now));
        }
        for (ArchiveBean ab : bean.getImprovearchiveList()) {
            projectInspectAttachments.add(this.toAttachmentEntity(ab, abConverter, entity,
                    OnOffEnum.ON.value(), user, now));
        }
        entity.setProjectInspectAttachments(projectInspectAttachments);

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
    private ProjectInspectAttachment toAttachmentEntity(ArchiveBean ab,
            ArchiveBeanConverter abConverter, ProjectInspect entity, short isImprove,
            AbstractSessionUserBean user, Date now) {

        ProjectInspectAttachment attachment = new ProjectInspectAttachment();
        attachment.setIsImprove(isImprove);
        attachment.setProjectInspect(entity);

// add start by huang 20200509
//        /**
//         * 第零层：根
//         * 第一层：FolderPath.PROJECT(DIR)
//         * 第二层：Project.id(DIR)
//         * 第三层：Archive文件
//         */
//        ab.setLevel(3);
//        ab.setPid(entity.getId());
//        ab.setPath(Constants.SLASH + Constants.COMMA + FolderPath.PROJECT_INSPECT + Constants.COMMA
//                + entity.getId());
//        Archive aentity = abConverter.toEntity(ab, null, user, now);
        
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

        ab.setPid(entity.getProject().getId() + "_" + FolderPath.PROJECT_INSPECT);

        ab.setPath(Constants.SLASH + Constants.COMMA + year + Constants.COMMA
                + entity.getProject().getId() + Constants.COMMA
                + entity.getProject().getId() + "_" + FolderPath.SYSTEM_PLACE_ON_FILE + Constants.COMMA
                + entity.getProject().getId() + "_" + FolderPath.PROJECT_INSPECT);
        
        Archive aentity = abConverter.toEntity(ab, null, user, now);
        
        String relativePath = File.separator + year + File.separator + entity.getProject().getId() + File.separator + entity.getProject().getId() + "_"
                + FolderPath.SYSTEM_PLACE_ON_FILE + File.separator + entity.getProject().getId() + "_"
                + FolderPath.PROJECT_INSPECT + File.separator + aentity.getId();

        // "\project\guid\xxx.doc"
        // String extension = FilenameUtils.getExtension(ab.getArchiveName()).toLowerCase();
        // String relativePath = File.separator + FolderPath.PROJECT + File.separator
        // + entity.getId() + File.separator + aentity.getId() + Constants.DOT + extension;
//        String relativePath = File.separator + FolderPath.PROJECT_INSPECT + File.separator
//                + entity.getId() + File.separator + aentity.getId();

        //chagned by huang 20200509
        
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
