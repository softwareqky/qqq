package project.edge.domain.converter;

import java.io.File;
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
import project.edge.domain.entity.Archive;
import project.edge.domain.entity.BudgetTemplate;
import project.edge.domain.entity.BudgetTemplateAttachment;
import project.edge.domain.entity.Person;
import project.edge.domain.entity.Project;
import project.edge.domain.entity.VirtualOrg;
import project.edge.domain.view.ArchiveBean;
import project.edge.domain.view.BudgetTemplateBean;

/**
 * @author angel_000 转换项目成员对应的view和entity的转换器。
 *
 */
public class BudgetTemplateBeanConverter
        implements ViewConverter<BudgetTemplate, BudgetTemplateBean> {

    @Override
    public BudgetTemplateBean fromEntity(BudgetTemplate entity, MessageSource messageSource,
            Locale locale) {
        // TODO Auto-generated method stub
        BudgetTemplateBean bean = new BudgetTemplateBean();

        bean.setId(entity.getId());

        Project project = entity.getProject();

        if (project != null) {
            bean.setProject_(project.getId());
            bean.setProjectText(project.getProjectName());
        }

        bean.setTemplateName(entity.getTemplateName());
        bean.setTemplateDesc(entity.getTemplateDesc());
		
        VirtualOrg group = entity.getGroup();
        if (group != null) {
            bean.setGroup_(group.getId());
            bean.setGroupText(group.getVirtualOrgName());
        }
        
        if (entity.getcDatetime() != null) {
			bean.setcDatetime(DateUtils.date2String(entity.getcDatetime(), Constants.DATE_FORMAT));
		}

		Person person = entity.getCreator();
		if (person != null) {
			bean.setCreator_(person.getId());
			bean.setCreatorText(person.getPersonName());
		}

        // 附件处理
        ArchiveBeanConverter abConverter = new ArchiveBeanConverter();
        Set<BudgetTemplateAttachment> archives = entity.getArchives();

        for (BudgetTemplateAttachment archive : archives) {
            bean.getArchiveList().add(abConverter.fromEntity(archive.getArchive(), messageSource, locale));
        }
        return bean;
    }

    @Override
    public BudgetTemplate toEntity(BudgetTemplateBean bean, BudgetTemplate oldEntity,
            AbstractSessionUserBean user, Date now) {
        // TODO Auto-generated method stub
        BudgetTemplate entity = oldEntity;

        if (entity == null) { // 表示新建
            entity = new BudgetTemplate();
			if (!StringUtils.isEmpty(user.getSessionUserId())) {
				Person person = new Person();
				person.setId(user.getSessionUserId());
				entity.setCreator(person);
			}
            entity.setcDatetime(now);
        } else { // 表示修改
            entity.setModifier(user.getSessionUserId());

            // 在Controller.postUpdate中，需要比较新老entity的关联的档案文件，所以必须clone
            // clone之后，清空外键关联字段的值
            entity = oldEntity.clone();
            
            // 修改后，仍然保留的档案文件
            entity.setArchives(new HashSet<>());
            
            Map<String, String> map = new HashMap<>();
            for (String aid : bean.getArchiveReservedList()) {
                map.put(aid, aid);
            }
            for (String aid : bean.getArchiveReservedList()) {
                map.put(aid, aid);
            }

            // 找出需要删除的Archive
            for (BudgetTemplateAttachment dbArchive : oldEntity.getArchives()) {
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

        entity.setTemplateName(bean.getTemplateName());
        entity.setTemplateDesc(bean.getTemplateDesc());
        
        if (!StringUtils.isEmpty(bean.getGroup_())) {
        	VirtualOrg group = new VirtualOrg();
        	group.setId(bean.getGroup_());
            entity.setGroup(group);
        }
        
        // 附件处理，此处只需处理新增加的ProjectAttachment
        Set<BudgetTemplateAttachment> budgetTemplateAttachments = new HashSet<>();

        ArchiveBeanConverter abConverter = new ArchiveBeanConverter();

        for (ArchiveBean ab : bean.getArchiveList()) {
        	budgetTemplateAttachments.add(this.toAttachmentEntity(ab, abConverter, entity,user, now));
        }
        entity.setArchives(budgetTemplateAttachments);

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
    private BudgetTemplateAttachment toAttachmentEntity(ArchiveBean ab, ArchiveBeanConverter abConverter,
    		BudgetTemplate entity, AbstractSessionUserBean user, Date now) {

    	BudgetTemplateAttachment attachment = new BudgetTemplateAttachment();
        attachment.setBudgetTemplate(entity);
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

        ab.setPid(entity.getProject().getId() + "_" + FolderPath.BUDGET_TEMPLATE);

        ab.setPath(Constants.SLASH + Constants.COMMA + year + Constants.COMMA
                + entity.getProject().getId() + Constants.COMMA
                + entity.getProject().getId() + "_" + FolderPath.SYSTEM_PLACE_ON_FILE + Constants.COMMA
                + entity.getProject().getId() + "_" + FolderPath.BUDGET_TEMPLATE);
        
        Archive aentity = abConverter.toEntity(ab, null, user, now);
        
        String relativePath = File.separator + year + File.separator + entity.getProject().getId() + File.separator + entity.getProject().getId() + "_"
                + FolderPath.SYSTEM_PLACE_ON_FILE + File.separator + entity.getProject().getId() + "_"
                + FolderPath.BUDGET_TEMPLATE + File.separator + aentity.getId();
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
