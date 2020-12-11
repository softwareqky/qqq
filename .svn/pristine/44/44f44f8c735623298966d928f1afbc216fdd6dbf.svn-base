/**
 * 
 */
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
import garage.origin.common.constant.GenderEnum;
import garage.origin.common.constant.OnOffEnum;
import garage.origin.common.util.DateUtils;
import garage.origin.domain.view.AbstractSessionUserBean;
import garage.origin.domain.view.converter.ViewConverter;
import project.edge.common.constant.FolderPath;
import project.edge.common.constant.ProjectAttachmentTypeEnum;
import project.edge.domain.entity.Archive;
import project.edge.domain.entity.DataOption;
import project.edge.domain.entity.Expert;
import project.edge.domain.entity.ExpertAttachment;
import project.edge.domain.entity.Person;
import project.edge.domain.view.ArchiveBean;
import project.edge.domain.view.ExpertBean;


/**
 * @author angel_000
 *         转换专家信息对应的view和entity的转换器。
 */
public class ExpertBeanConverter implements ViewConverter<Expert, ExpertBean> {

    @Override
    public ExpertBean fromEntity(Expert entity, MessageSource messageSource, Locale locale) {
        // TODO Auto-generated method stub

        ExpertBean bean = new ExpertBean();
        bean.setId(entity.getId());

        Person person = entity.getPerson();
        if (person != null) {
            bean.setPerson_(person.getId());
            bean.setPersonText(person.getPersonName());
        }

        bean.setIsInternal(entity.getIsInternal());

        bean.setIsInternalText(entity.getIsInternal() == 1
                ? messageSource.getMessage(OnOffEnum.ON.resourceName(), null, locale)
                : messageSource.getMessage(OnOffEnum.OFF.resourceName(), null, locale));


        bean.setIsBlackList(entity.getIsBlacklist());
        bean.setIsBlackListText(entity.getIsBlacklist() == 1
                ? messageSource.getMessage(OnOffEnum.ON.resourceName(), null, locale)
                : messageSource.getMessage(OnOffEnum.OFF.resourceName(), null, locale));

        bean.setExpertName(entity.getExpertName());


        if (entity.getGender() != null) {
            bean.setGender(entity.getGender());
            bean.setGenderText(messageSource
                    .getMessage(GenderEnum.getResouceName(entity.getGender()), null, locale));
        }

        if (entity.getBirthday() != null) {
            bean.setBirthday(DateUtils.date2String(entity.getBirthday(), Constants.DATE_FORMAT));
        }
        bean.setIdCardNum(entity.getIdCardNum());
        bean.setBankCardNum(entity.getBankCardNum());
        bean.setPoliticalStatus(entity.getPoliticalStatus());
        bean.setGraduatedFrom(entity.getGraduatedFrom());
        bean.setMajor(entity.getMajor());
        bean.setJobMajor(entity.getJobMajor());
        bean.setEducationRecord(entity.getEducationRecord());
        bean.setDegree(entity.getDegree());
        bean.setCompany(entity.getCompany());
        bean.setJobTitle(entity.getJobTitle());
        bean.setRegion(entity.getRegion());
        bean.setMobile(entity.getMobile());
        bean.setEmail(entity.getEmail());

        Archive photo = entity.getPhoto();
        if (photo != null) {
            bean.setPhoto_(photo.getId());
            bean.setPhotoText(photo.getArchiveName());
        }

        bean.setExpertDesc(entity.getExpertDesc());

        DataOption expertDomain = entity.getExpertDomain();
        if (expertDomain != null) {
            bean.setExpertDomain_(expertDomain.getId());
            bean.setExpertDomainText(expertDomain.getOptionName());
        }

        bean.setSpeciality(entity.getSpeciality());
        bean.setProfessionalAge(entity.getProfessionalAge());

        // 附件处理
        ArchiveBeanConverter abConverter = new ArchiveBeanConverter();
        Set<ExpertAttachment> expertAttachments = entity.getExpertAttachments();
        for (ExpertAttachment pa : expertAttachments) {

            bean.getArchivesList()
                    .add(abConverter.fromEntity(pa.getArchive(), messageSource, locale));

        }
        bean.setRemark(entity.getRemark());

        return bean;
    }

    @Override
    public Expert toEntity(ExpertBean bean, Expert oldEntity, AbstractSessionUserBean user,
            Date now) {
        // TODO Auto-generated method stub
        Expert entity = oldEntity;

        if (entity == null) { // 表示新建
            entity = new Expert();

            entity.setCreator(user.getSessionUserId());
            entity.setcDatetime(now);
        } else { // 表示修改
            // 在Controller.postUpdate中，需要比较新老entity的关联的档案文件，所以必须clone
            // clone之后，清空外键关联字段的值
            entity = oldEntity.clone();

            entity.setModifier(user.getSessionUserId());

            // 修改后，仍然保留的档案文件
            Map<String, String> map = new HashMap<>();
            for (String aid : bean.getArchivesReservedList()) {
                map.put(aid, aid);
            }

            // 找出需要删除的Archive
            for (ExpertAttachment dbAttachment : oldEntity.getExpertAttachments()) {
                if (!map.containsKey(dbAttachment.getArchive().getId())) {
                    entity.getAttachmentsToDelete().add(dbAttachment);
                }

            }
        }

        bean.setId(entity.getId()); // ID必须赋值
        entity.setmDatetime(now);

        if (!StringUtils.isEmpty(bean.getPerson_())) {
            Person person = new Person();
            person.setId(bean.getPerson_());
            entity.setPerson(person);
        }

        entity.setIsInternal(bean.getIsInternal());
        entity.setExpertName(bean.getExpertName());
        entity.setGender(bean.getGender());

        if (!StringUtils.isEmpty(bean.getBirthday())) {
            try {
                entity.setBirthday(
                        DateUtils.string2Date(bean.getBirthday(), Constants.DATE_FORMAT));
            } catch (ParseException e) {
                // do nothing
            }
        }
        
        entity.setIsBlacklist(bean.getIsBlackList());
        
        entity.setIdCardNum(bean.getIdCardNum());
        entity.setBankCardNum(bean.getBankCardNum());
        entity.setPoliticalStatus(bean.getPoliticalStatus());
        entity.setGraduatedFrom(bean.getGraduatedFrom());
        entity.setMajor(bean.getMajor());
        entity.setJobMajor(bean.getJobMajor());
        entity.setEducationRecord(bean.getEducationRecord());
        entity.setDegree(bean.getDegree());
        entity.setCompany(bean.getCompany());
        entity.setJobTitle(bean.getJobTitle());
        entity.setRegion(bean.getRegion());
        entity.setMobile(bean.getMobile());
        entity.setEmail(bean.getEmail());

        if (!StringUtils.isEmpty(bean.getPhoto_())) {
            Archive photo = new Archive();
            photo.setId(bean.getPhoto_());
            entity.setPhoto(photo);
        }

        entity.setExpertDesc(bean.getExpertDesc());

        if (!StringUtils.isEmpty(bean.getExpertDomain_())) {
            DataOption expertDomain = new DataOption();
            expertDomain.setId(bean.getExpertDomain_());
            entity.setExpertDomain(expertDomain);
        }

        entity.setSpeciality(bean.getSpeciality());
        entity.setProfessionalAge(bean.getProfessionalAge());

        // 附件处理，此处只需处理新增加的ProjectAttachment
        Set<ExpertAttachment> expertAttachments = new HashSet<>();

        ArchiveBeanConverter abConverter = new ArchiveBeanConverter();
        for (ArchiveBean ab : bean.getArchivesList()) {
            expertAttachments.add(this.toAttachmentEntity(ab, abConverter, entity,
                    ProjectAttachmentTypeEnum.ARCHIVE.value(), user, now));
        }

        entity.setExpertAttachments(expertAttachments);

        entity.setRemark(bean.getRemark());

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
    private ExpertAttachment toAttachmentEntity(ArchiveBean ab, ArchiveBeanConverter abConverter,
            Expert entity, int attachmentType, AbstractSessionUserBean user, Date now) {

        ExpertAttachment attachment = new ExpertAttachment();
        attachment.setExpert(entity);

        /**
         * 第零层：根
         * 第一层：FolderPath.PROJECT(DIR)
         * 第二层：Project.id(DIR)
         * 第三层：Archive文件
         */
        ab.setLevel(3);
        ab.setPid(entity.getId());
        ab.setPath(Constants.SLASH + Constants.COMMA + FolderPath.EXPERT + Constants.COMMA
                + entity.getId());
        Archive aentity = abConverter.toEntity(ab, null, user, now);

        // "\project\guid\xxx.doc"
        // String extension = FilenameUtils.getExtension(ab.getArchiveName()).toLowerCase();
        // String relativePath = File.separator + FolderPath.PROJECT + File.separator
        // + entity.getId() + File.separator + aentity.getId() + Constants.DOT + extension;
        String relativePath = File.separator + FolderPath.EXPERT + File.separator + entity.getId()
                + File.separator + aentity.getId();
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
