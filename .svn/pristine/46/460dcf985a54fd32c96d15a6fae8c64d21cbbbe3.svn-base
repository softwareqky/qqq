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
import garage.origin.common.constant.OnOffEnum;
import garage.origin.domain.view.AbstractSessionUserBean;
import garage.origin.domain.view.converter.ViewConverter;
import project.edge.common.constant.FolderPath;
import project.edge.domain.entity.Archive;
import project.edge.domain.entity.CompletedInfo;
import project.edge.domain.entity.CompletedInfoAttachment;
import project.edge.domain.entity.DataOption;
import project.edge.domain.entity.Project;
import project.edge.domain.entity.ProjectSet;
import project.edge.domain.view.ArchiveBean;
import project.edge.domain.view.CompletedInfoBean;

/**
 * @author angel_000
 *         转换竣工信息表对应的view和entity的转换器。
 *
 */
public class CompletedInfoBeanConverter
        implements ViewConverter<CompletedInfo, CompletedInfoBean> {
	private final static short ARCHIVE_TYPE1 = 1;
	private final static short ARCHIVE_TYPE2 = 2;
	private final static short ARCHIVE_TYPE3 = 3;
	private final static short ARCHIVE_TYPE4 = 4;
	private final static short ARCHIVE_TYPE_COMPLETE_CERT = 20;

    @Override
    public CompletedInfoBean fromEntity(CompletedInfo entity, MessageSource messageSource,
            Locale locale) {
        // TODO Auto-generated method stub
        CompletedInfoBean bean = new CompletedInfoBean();

        bean.setId(entity.getId());

        Project project = entity.getProject();

        if (project != null) {
            bean.setProject_(project.getId());
            bean.setProjectText(project.getProjectName());
            bean.setProjectNum(project.getProjectNum());
            bean.setProjectGovernmentNum(project.getGovernmentProjectNum());

            DataOption projectStatus = project.getProjectStatus();
            if (projectStatus != null) {
                bean.setProjectStatus_(projectStatus.getId());
                if (projectStatus.getOptionName()!=null) 
                bean.setProjectStatusText(projectStatus.getOptionName());
            }
            
            DataOption projectStage = project.getProjectStage();
            if (projectStage != null) {
            	bean.setProjectStage_(projectStage.getId());
            	bean.setProjectStageText(projectStage.getOptionName());
            }
            
            ProjectSet set = project.getProjectSet();
            if (set != null) {
                bean.setProjectSet_(set.getId());
                bean.setProjectSetText(
                        String.format("(%1$s) %2$s", set.getProjectNum(), set.getProjectName()));
            }
            
            DataOption projectKind = project.getProjectKind();
            if (projectKind != null) {
                bean.setProjectKind_(projectKind.getId());
                bean.setProjectKindText(projectKind.getOptionName());
            }
            
            bean.setProjectSource(project.getProjectSource());
            
            bean.setSpecialCategory(project.getSpecialCategory());
        }

        bean.setPerformanceCheck(entity.getPerformanceCheck());
        bean.setCompletedContent(entity.getCompletedContent());
        bean.setRemark(entity.getRemark());
        
        // 附件处理
        ArchiveBeanConverter abConverter = new ArchiveBeanConverter();
        Set<CompletedInfoAttachment> completedInfoAttachment =
                entity.getCompletedInfoAttachments();

        for (CompletedInfoAttachment pa : completedInfoAttachment) {
        	if (pa.getArchiveType() == ARCHIVE_TYPE1) {
        		bean.getArchiveList()
                    .add(abConverter.fromEntity(pa.getArchive(), messageSource, locale));
        	} else if (pa.getArchiveType() == ARCHIVE_TYPE2) {
        		bean.getArchive2List()
                .add(abConverter.fromEntity(pa.getArchive(), messageSource, locale));
        	} else if (pa.getArchiveType() == ARCHIVE_TYPE3) {
        		bean.getArchive3List()
                .add(abConverter.fromEntity(pa.getArchive(), messageSource, locale));
        	} else if (pa.getArchiveType() == ARCHIVE_TYPE4) {
        		bean.getArchive4List()
                .add(abConverter.fromEntity(pa.getArchive(), messageSource, locale));
        	} else if (pa.getArchiveType() == ARCHIVE_TYPE_COMPLETE_CERT) {
        		bean.getArchiveCertList()
                .add(abConverter.fromEntity(pa.getArchive(), messageSource, locale));
        	}
        }

        return bean;
    }

    @Override
    public CompletedInfo toEntity(CompletedInfoBean bean, CompletedInfo oldEntity,
            AbstractSessionUserBean user, Date now) {
        // TODO Auto-generated method stub
        CompletedInfo entity = oldEntity;

        if (entity == null) { // 表示新建
            entity = new CompletedInfo();

            entity.setRemark("");
            entity.setCreator(user.getSessionUserId());
            entity.setcDatetime(now);
        } else { // 表示修改
            entity.setModifier(user.getSessionUserId());

            entity = oldEntity.clone();
            // 修改后，仍然保留的档案文件
            entity.setCompletedInfoAttachments(new HashSet<>());

            Map<String, String> map = new HashMap<>();
            for (String aid : bean.getArchiveReservedList()) {
                map.put(aid, aid);
            }
            for (String aid : bean.getArchive2ReservedList()) {
                map.put(aid, aid);
            }
            for (String aid : bean.getArchive3ReservedList()) {
                map.put(aid, aid);
            }
            for (String aid : bean.getArchive4ReservedList()) {
                map.put(aid, aid);
            }

            // 找出需要删除的Archive
            for (CompletedInfoAttachment dbAttachment : oldEntity
                    .getCompletedInfoAttachments()) {

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
        entity.setPerformanceCheck(bean.getPerformanceCheck());
        entity.setCompletedContent(bean.getCompletedContent());
        entity.setmDatetime(now);

        if (StringUtils.isEmpty(bean.getUploadFileType())) {

            if (!StringUtils.isEmpty(bean.getProject_())) {
                Project project = new Project();
                project.setId(bean.getProject_());
                project.setProjectName(bean.getProjectText());
                entity.setProject(project);
            }

            entity.setRemark(bean.getRemark());
        }

        // 附件处理，此处只需处理新增加的ProjectAttachment
        Set<CompletedInfoAttachment> completedInfoAttachments = new HashSet<>();

        ArchiveBeanConverter abConverter = new ArchiveBeanConverter();
        for (ArchiveBean ab : bean.getArchiveList()) {
        	CompletedInfoAttachment attachment =  this.toAttachmentEntity(ab, abConverter, entity, OnOffEnum.OFF.value(), user, now);
        	attachment.setArchiveType(ARCHIVE_TYPE1);
            completedInfoAttachments.add(attachment);
        }

        for (ArchiveBean ab : bean.getArchive2List()) {
        	CompletedInfoAttachment attachment =  this.toAttachmentEntity(ab, abConverter, entity, OnOffEnum.OFF.value(), user, now);
        	attachment.setArchiveType(ARCHIVE_TYPE2);
            completedInfoAttachments.add(attachment);
        }

        for (ArchiveBean ab : bean.getArchive3List()) {
        	CompletedInfoAttachment attachment =  this.toAttachmentEntity(ab, abConverter, entity, OnOffEnum.OFF.value(), user, now);
        	attachment.setArchiveType(ARCHIVE_TYPE3);
            completedInfoAttachments.add(attachment);
        }

        for (ArchiveBean ab : bean.getArchive4List()) {
        	CompletedInfoAttachment attachment =  this.toAttachmentEntity(ab, abConverter, entity, OnOffEnum.OFF.value(), user, now);
        	attachment.setArchiveType(ARCHIVE_TYPE4);
            completedInfoAttachments.add(attachment);
        }

        entity.setCompletedInfoAttachments(completedInfoAttachments);

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
    private CompletedInfoAttachment toAttachmentEntity(ArchiveBean ab,
            ArchiveBeanConverter abConverter, CompletedInfo entity, short isImprove,
            AbstractSessionUserBean user, Date now) {

        CompletedInfoAttachment attachment = new CompletedInfoAttachment();
        attachment.setCompletedInfo(entity);

        /**
         * 第零层：根
         * 第一层：FolderPath.PROJECT(DIR)
         * 第二层：Project.id(DIR)
         * 第三层：Archive文件
         */
        ab.setLevel(3);
        ab.setPid(entity.getId());
        ab.setPath(Constants.SLASH + Constants.COMMA + FolderPath.PROJECT_COMPLETED + Constants.COMMA
                + entity.getId());
        Archive aentity = abConverter.toEntity(ab, null, user, now);

        // "\project\guid\xxx.doc"
        // String extension = FilenameUtils.getExtension(ab.getArchiveName()).toLowerCase();
        // String relativePath = File.separator + FolderPath.PROJECT + File.separator
        // + entity.getId() + File.separator + aentity.getId() + Constants.DOT + extension;
        String relativePath = File.separator + FolderPath.PROJECT_COMPLETED + File.separator
                + entity.getId() + File.separator + aentity.getId();
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
