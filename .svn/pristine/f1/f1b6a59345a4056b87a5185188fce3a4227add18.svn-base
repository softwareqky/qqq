/**
 * 
 */
package project.edge.domain.converter;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashSet;
import java.util.Locale;
import java.util.Set;

import org.springframework.context.MessageSource;
import org.springframework.util.StringUtils;

import garage.origin.common.constant.Constants;
import garage.origin.domain.view.AbstractSessionUserBean;
import garage.origin.domain.view.converter.ViewConverter;
import project.edge.common.constant.FolderPath;
import project.edge.domain.entity.Archive;
import project.edge.domain.entity.CapitalApply;
import project.edge.domain.entity.CapitalApplyAttachment;
import project.edge.domain.entity.DataOption;
import project.edge.domain.entity.Person;
import project.edge.domain.entity.Project;
import project.edge.domain.entity.VirtualOrg;
import project.edge.domain.view.ArchiveBean;
import project.edge.domain.view.CapitalApplyBean;


/**
 * @author wwy
 *         转换资金下达对应的view和entity的转换器。
 */
public class CapitalApplyBeanConverter
        implements ViewConverter<CapitalApply, CapitalApplyBean> {

    @Override
    public CapitalApplyBean fromEntity(CapitalApply entity, MessageSource messageSource,
            Locale locale) {
        // TODO Auto-generated method stub
    	CapitalApplyBean bean = new CapitalApplyBean();
		bean.setId(entity.getId());
		
        Project project = entity.getProject();
        if (project != null) {
        	bean.setProject_(project.getId());
            bean.setProjectText(project.getProjectName());
        }
        
        DataOption source = entity.getSource();
        if (source != null) {
            bean.setSource_(source.getId());
            bean.setSourceText(source.getOptionName());
        }
        
        Person person = entity.getCreator();
        if (person != null) {
            bean.setCreator_(person.getId());
            bean.setCreatorText(person.getPersonName());
        }
        
		VirtualOrg group = entity.getGroup();
        if (group != null) {
            bean.setGroup_(group.getId());
            bean.setGroupText(group.getVirtualOrgName());
        }
        
        bean.setApplyReason(entity.getApplyReason());
        bean.setApplyAmount(entity.getApplyAmount());
        
        // 附件处理
        ArchiveBeanConverter abConverter = new ArchiveBeanConverter();
        Set<CapitalApplyAttachment> capitalApplyAttachments =
                entity.getCapitalApplyAttachments();
        for (CapitalApplyAttachment pa : capitalApplyAttachments) {

            bean.getArchivesList()
                    .add(abConverter.fromEntity(pa.getArchive(), messageSource, locale));
        }
        
		return bean;
    }

    @Override
    public CapitalApply toEntity(CapitalApplyBean bean, CapitalApply oldEntity,
            AbstractSessionUserBean user, Date now) {
        // TODO Auto-generated method stub

    	CapitalApply entity = oldEntity;
        if (entity == null) { // 表示新建
            entity = new CapitalApply();
            if (!StringUtils.isEmpty(user.getSessionUserId())) {
                Person person = new Person();
                person.setId(user.getSessionUserId());
                entity.setCreator(person);
            }
            entity.setcDatetime(now);

        } else {
            entity.setModifier(user.getSessionUserId());
        }

        bean.setId(entity.getId()); // ID必须赋值
		entity.setmDatetime(now);
		
        if (!StringUtils.isEmpty(bean.getProject_())) {
            Project project = new Project();
            project.setId(bean.getProject_());
            entity.setProject(project);
        }
        if (!StringUtils.isEmpty(bean.getSource_())) {
            DataOption source = new DataOption();
            source.setId(bean.getSource_());
            entity.setSource(source);
        }
        if (!StringUtils.isEmpty(bean.getGroup_())) {
        	VirtualOrg group = new VirtualOrg();
        	group.setId(bean.getGroup_());
            entity.setGroup(group);
        }
        entity.setApplyAmount(bean.getApplyAmount());
        entity.setApplyReason(bean.getApplyReason());
        
        // 附件处理，此处只需处理新增加的ProjectAttachment
        Set<CapitalApplyAttachment> capitalApplyAttachments = new HashSet<>();

        ArchiveBeanConverter abConverter = new ArchiveBeanConverter();
        for (ArchiveBean ab : bean.getArchivesList()) {
        	capitalApplyAttachments.add(this.toAttachmentEntity(ab, abConverter, entity,user, now));
        }

        entity.setCapitalApplyAttachments(capitalApplyAttachments);
        
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
    private CapitalApplyAttachment toAttachmentEntity(ArchiveBean ab, ArchiveBeanConverter abConverter,
    		CapitalApply entity, AbstractSessionUserBean user, Date now) {

    	CapitalApplyAttachment attachment = new CapitalApplyAttachment();
        attachment.setCapitalApply(entity);
//changed start by huang 
//        /**
//         * 第零层：根
//         * 第一层：FolderPath.PROJECT(DIR)
//         * 第二层：Project.id(DIR)
//         * 第三层：Archive文件
//         */
//        ab.setLevel(3);
//        ab.setPid(entity.getId());
//        ab.setPath(Constants.SLASH + Constants.COMMA + FolderPath.BUDGET_TEMPLATE + Constants.COMMA
//                + entity.getId());
//        Archive aentity = abConverter.toEntity(ab, null, user, now);
//
//        // "\project\guid\xxx.doc"
//        // String extension = FilenameUtils.getExtension(ab.getArchiveName()).toLowerCase();
//        // String relativePath = File.separator + FolderPath.PROJECT + File.separator
//        // + entity.getId() + File.separator + aentity.getId() + Constants.DOT + extension;
//        String relativePath = File.separator + FolderPath.BUDGET_TEMPLATE + File.separator + entity.getId()
//                + File.separator + aentity.getId();
     
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

        ab.setPid(entity.getProject().getId() + "_" + FolderPath.CAPITAL_APPLY);

        ab.setPath(Constants.SLASH + Constants.COMMA + year + Constants.COMMA
                + entity.getProject().getId() + Constants.COMMA
                + entity.getProject().getId() + "_" + FolderPath.SYSTEM_PLACE_ON_FILE + Constants.COMMA
                + entity.getProject().getId() + "_" + FolderPath.CAPITAL_APPLY);
        Archive aentity = abConverter.toEntity(ab, null, user, now);
        
        String relativePath = File.separator + year + File.separator + entity.getProject().getId() + File.separator + entity.getProject().getId() + "_"
                + FolderPath.SYSTEM_PLACE_ON_FILE + File.separator + entity.getProject().getId() + "_"
                + FolderPath.CAPITAL_APPLY + File.separator + aentity.getId();
//changed end by huang 
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
