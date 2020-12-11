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
import project.edge.domain.entity.AcceptanceCheck;
import project.edge.domain.entity.AcceptanceCheckAttachment;
import project.edge.domain.entity.Archive;
import project.edge.domain.entity.DataOption;
import project.edge.domain.entity.Person;
import project.edge.domain.entity.Project;
import project.edge.domain.entity.ProjectPerson;
import project.edge.domain.view.AcceptanceCheckBean;
import project.edge.domain.view.ArchiveBean;

/**
 * @author angel_000
 *         转换验收信息表对应的view和entity的转换器。
 *
 */
public class AcceptanceCheckBeanConverter
        implements ViewConverter<AcceptanceCheck, AcceptanceCheckBean> {

    @Override
    public AcceptanceCheckBean fromEntity(AcceptanceCheck entity, MessageSource messageSource,
            Locale locale) {
        // TODO Auto-generated method stub
        AcceptanceCheckBean bean = new AcceptanceCheckBean();

        bean.setId(entity.getId());

        Project project = entity.getProject();
        DataOption acceptanceCheckType = entity.getAcceptanceCheckType();
        DataOption acceptanceCheckKind = entity.getAcceptanceCheckKind();

        if (project != null) {
            bean.setProject_(project.getId());
            bean.setProjectText(project.getProjectName());
        }

        if (acceptanceCheckType != null) {
            bean.setAcceptanceCheckType_(acceptanceCheckType.getId());
            bean.setAcceptanceCheckTypeText(acceptanceCheckType.getOptionName());
        }
        
        if (acceptanceCheckKind != null) {
            bean.setAcceptanceCheckKind_(acceptanceCheckKind.getId());
            bean.setAcceptanceCheckKindText(acceptanceCheckKind.getOptionName());
        }

        bean.setCheckingUnit(entity.getCheckingUnit());
        bean.setChechedUnit(entity.getChechedUnit());
        bean.setCheckedContent(entity.getCheckedContent());
        bean.setCheckBasis(entity.getCheckBasis());
        ProjectPerson checker = entity.getChecker();
        if (checker != null) {
            bean.setChecker_(checker.getId());
            bean.setCheckerText(checker.getPerson().getPersonName());
        }
        bean.setCheckResultContent(entity.getCheckResultContent());
        
        bean.setIsImprove(entity.getIsImprove());
        if (entity.getIsImprove() != null) {
            bean.setIsImproveText(entity.getIsImprove() == 1
                ? messageSource.getMessage(OnOffEnum.ON.resourceName(), null, locale)
                : messageSource.getMessage(OnOffEnum.OFF.resourceName(), null, locale));
        }
        
        DataOption acceptanceCheckResult = entity.getAcceptanceCheckResult();
        DataOption acceptanceVerifyResult = entity.getAcceptanceCheckType();

        if (acceptanceCheckResult != null) {
            bean.setAcceptanceCheckResult_(acceptanceCheckResult.getId());
            bean.setAcceptanceCheckResultText(acceptanceCheckResult.getOptionName());
        }

        if (acceptanceVerifyResult != null) {
            bean.setAcceptanceVerifyResult_(acceptanceVerifyResult.getId());
            bean.setAcceptanceVerifyResultText(acceptanceVerifyResult.getOptionName());
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

        DataOption acceptanceCheckStatus = entity.getAcceptanceCheckStatus();
        if (acceptanceCheckStatus != null) {
            bean.setAcceptanceCheckStatus_(acceptanceCheckStatus.getId());
            bean.setAcceptanceCheckStatusText(acceptanceCheckStatus.getOptionName());
        }

        // 附件处理
        ArchiveBeanConverter abConverter = new ArchiveBeanConverter();
        Set<AcceptanceCheckAttachment> acceptanceCheckAttachment =
                entity.getAcceptanceCheckAttachments();

        for (AcceptanceCheckAttachment pa : acceptanceCheckAttachment) {
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
    public AcceptanceCheck toEntity(AcceptanceCheckBean bean, AcceptanceCheck oldEntity,
            AbstractSessionUserBean user, Date now) {
        // TODO Auto-generated method stub
        AcceptanceCheck entity = oldEntity;

        if (entity == null) { // 表示新建
            entity = new AcceptanceCheck();

            entity.setRemark("");
            entity.setCreator(user.getSessionUserId());
            entity.setcDatetime(now);
        } else { // 表示修改
            entity.setModifier(user.getSessionUserId());

            entity = oldEntity.clone();
            // 修改后，仍然保留的档案文件
            entity.setAcceptanceCheckAttachments(new HashSet<>());

            Map<String, String> map = new HashMap<>();
            for (String aid : bean.getArchiveReservedList()) {
                map.put(aid, aid);
            }
            // 找出需要删除的Archive
            for (AcceptanceCheckAttachment dbAttachment : oldEntity
                    .getAcceptanceCheckAttachments()) {

                boolean needCheck = false;
                String uploadType = bean.getUploadFileType();
                if ("edit".equals(uploadType)) {
                    needCheck = true;
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

            if (!StringUtils.isEmpty(bean.getAcceptanceCheckType_())) {
                DataOption acceptanceCheckType = new DataOption();
                acceptanceCheckType.setId(bean.getAcceptanceCheckType_());
                entity.setAcceptanceCheckType(acceptanceCheckType);
            }

            if (!StringUtils.isEmpty(bean.getAcceptanceCheckKind_())) {
                DataOption acceptanceCheckKind = new DataOption();
                acceptanceCheckKind.setId(bean.getAcceptanceCheckKind_());
                entity.setAcceptanceCheckKind(acceptanceCheckKind);
            }

            if (!StringUtils.isEmpty(bean.getAcceptanceCheckStatus_())) {
                DataOption acceptanceCheckStatus = new DataOption();
                acceptanceCheckStatus.setId(bean.getAcceptanceCheckStatus_());
                entity.setAcceptanceCheckStatus(acceptanceCheckStatus);
            }

            entity.setCheckingUnit(bean.getCheckingUnit());
            entity.setChechedUnit(bean.getChechedUnit());
            entity.setCheckedContent(bean.getCheckedContent());
            entity.setCheckBasis(bean.getCheckBasis());
            if (!StringUtils.isEmpty(bean.getChecker_())) {
                ProjectPerson checker = new ProjectPerson();
                Person person = new Person();
                person.setPersonName(bean.getCheckerText());
                checker.setId(bean.getChecker_());
                checker.setPerson(person);
                entity.setChecker(checker);
            }
            entity.setIsImprove(bean.getIsImprove());

            if (!StringUtils.isEmpty(bean.getAcceptanceCheckResult_())) {
                DataOption acceptanceCheckResult = new DataOption();
                acceptanceCheckResult.setId(bean.getAcceptanceCheckResult_());
                entity.setAcceptanceCheckResult(acceptanceCheckResult);
            }
            
            if (!StringUtils.isEmpty(bean.getCheckResultContent())) {
            	entity.setCheckResultContent(bean.getCheckResultContent());
            }
            
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

            entity.setCheckResultContent(bean.getCheckResultContent());

            entity.setRemark(bean.getRemark());
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

        entity.setImproveReq(bean.getImproveReq());
        entity.setImprovePlan(bean.getImprovePlan());
        if (!StringUtils.isEmpty(bean.getVerifyDate())) {
            try {
                entity.setVerifyDate(
                        DateUtils.string2Date(bean.getVerifyDate(), Constants.DATE_FORMAT));
            } catch (ParseException e) {
                // do nothing
            }
        }

        entity.setImproveResultVerify(bean.getImproveResultVerify());

        if (!StringUtils.isEmpty(bean.getAcceptanceVerifyResult_())) {
            DataOption acceptanceVerifyResult = new DataOption();
            acceptanceVerifyResult.setId(bean.getAcceptanceVerifyResult_());
            entity.setAcceptanceVerifyResult(acceptanceVerifyResult);
        }

        // 附件处理，此处只需处理新增加的ProjectAttachment
        Set<AcceptanceCheckAttachment> acceptanceCheckAttachments = new HashSet<>();

        ArchiveBeanConverter abConverter = new ArchiveBeanConverter();
        for (ArchiveBean ab : bean.getArchiveList()) {
            acceptanceCheckAttachments.add(this.toAttachmentEntity(ab, abConverter, entity,
                    OnOffEnum.OFF.value(), user, now));
        }
        entity.setAcceptanceCheckAttachments(acceptanceCheckAttachments);

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
    private AcceptanceCheckAttachment toAttachmentEntity(ArchiveBean ab,
            ArchiveBeanConverter abConverter, AcceptanceCheck entity, short isImprove,
            AbstractSessionUserBean user, Date now) {

        AcceptanceCheckAttachment attachment = new AcceptanceCheckAttachment();
        attachment.setIsImprove(isImprove);
        attachment.setAcceptanceCheck(entity);

//        /**
//         * 第零层：根
//         * 第一层：FolderPath.PROJECT(DIR)
//         * 第二层：Project.id(DIR)
//         * 第三层：Archive文件
//         */
//        ab.setLevel(3);
//        ab.setPid(entity.getId());
//        ab.setPath(Constants.SLASH + Constants.COMMA + FolderPath.ACCEPTANCE_CHECK + Constants.COMMA
//                + entity.getId());
//        Archive aentity = abConverter.toEntity(ab, null, user, now);
//
//        // "\project\guid\xxx.doc"
//        // String extension = FilenameUtils.getExtension(ab.getArchiveName()).toLowerCase();
//        // String relativePath = File.separator + FolderPath.PROJECT + File.separator
//        // + entity.getId() + File.separator + aentity.getId() + Constants.DOT + extension;
//        String relativePath = File.separator + FolderPath.ACCEPTANCE_CHECK + File.separator
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

        ab.setPid(entity.getProject().getId() + "_" + FolderPath.ACCEPTANCE_CHECK);

        ab.setPath(Constants.SLASH + Constants.COMMA + year + Constants.COMMA
                + entity.getProject().getId() + Constants.COMMA
                + entity.getProject().getId() + "_" + FolderPath.SYSTEM_PLACE_ON_FILE + Constants.COMMA
                + entity.getProject().getId() + "_" + FolderPath.ACCEPTANCE_CHECK);
        
        Archive aentity = abConverter.toEntity(ab, null, user, now);
        
        String relativePath = File.separator + year + File.separator + entity.getProject().getId() + File.separator + entity.getProject().getId() + "_"
                + FolderPath.SYSTEM_PLACE_ON_FILE + File.separator + entity.getProject().getId() + "_"
                + FolderPath.ACCEPTANCE_CHECK + File.separator + aentity.getId();
        
        
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
