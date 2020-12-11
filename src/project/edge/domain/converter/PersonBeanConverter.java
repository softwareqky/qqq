package project.edge.domain.converter;

import org.springframework.context.MessageSource;
import org.springframework.util.StringUtils;

import java.text.ParseException;
import java.util.Date;
import java.util.Locale;

import javax.annotation.Resource;

import garage.origin.common.constant.Constants;
import garage.origin.common.constant.GenderEnum;
import garage.origin.common.constant.OnOffEnum;
import garage.origin.common.util.DateUtils;
import garage.origin.domain.view.AbstractSessionUserBean;
import garage.origin.domain.view.converter.ViewConverter;
import project.edge.common.constant.PersonStatusEnum;
import project.edge.domain.entity.Archive;
import project.edge.domain.entity.Dept;
import project.edge.domain.entity.Org;
import project.edge.domain.entity.Person;
import project.edge.domain.entity.Post;
import project.edge.domain.entity.User;
import project.edge.domain.view.PersonBean;
import project.edge.service.hr.PersonService;

/**
 * @author angel_000
 *         转换人员信息对应的view和entity的转换器。
 *
 */
public class PersonBeanConverter implements ViewConverter<Person, PersonBean> {


    @Resource
    private PersonService personService;

    @Override
    public PersonBean fromEntity(Person entity, MessageSource messageSource, Locale locale) {
        // TODO Auto-generated method stub

        PersonBean bean = new PersonBean();

        bean.setId(entity.getId());

        Org org = entity.getOrg();
        Dept dept = entity.getDept();
        Post post = entity.getPost();
        User user = entity.getUser();
        Archive photo = entity.getPhoto();

        if (org != null) {
            bean.setOrg_(org.getId());
            bean.setOrgText(org.getOrgName());
        }

        if (dept != null) {
            bean.setDept_(dept.getId());
            bean.setDeptText(dept.getDeptName());
        }

        if (post != null) {
            bean.setPost_(post.getId());
            bean.setPostText(post.getPostName());
        }

        if (user != null) {
            bean.setUser_(user.getId());
            bean.setUserText(user.getLoginName());
        }

        if (photo != null) {
            bean.setPhoto_(photo.getId());
            bean.setPhotoText(photo.getArchiveName());
        }

        bean.setJobNum(entity.getJobNum());
        bean.setPersonName(entity.getPersonName());
        bean.setAccountType(entity.getAccountType());
        bean.setSecurityLevel(entity.getSecurityLevel());
        if (entity.getGender() != null) {
            bean.setGenderText(messageSource
                    .getMessage(GenderEnum.getResouceName(entity.getGender()), null, locale));
        }
        if (entity.getBirthday() != null) {
            bean.setBirthday(DateUtils.date2String(entity.getBirthday(), Constants.DATE_FORMAT));
        }
        bean.setEthnicGroup(entity.getEthnicGroup());
        bean.setNativePlace(entity.getNativePlace());
        bean.setRegResidence(entity.getRegResidence());
        bean.setIdCardNum(entity.getIdCardNum());
        if (entity.getMaritalStatus() != null) {
            bean.setMaritalStatusText(entity.getMaritalStatus() == 1
                    ? messageSource.getMessage(OnOffEnum.ON.resourceName(), null, locale)
                    : messageSource.getMessage(OnOffEnum.OFF.resourceName(), null, locale));
        }
        bean.setPoliticalStatus(entity.getPoliticalStatus());
        if (entity.getBeMemberDate() != null) {
            bean.setBeMemberDate(
                    DateUtils.date2String(entity.getBeMemberDate(), Constants.DATE_FORMAT));
        }
        if (entity.getBePartyDate() != null) {
            bean.setBePartyDate(
                    DateUtils.date2String(entity.getBePartyDate(), Constants.DATE_FORMAT));
        }

        if (entity.getIsLabourUnion() != null) {
            bean.setIsLabourUnionText(entity.getIsLabourUnion() == 1
                    ? messageSource.getMessage(OnOffEnum.ON.resourceName(), null, locale)
                    : messageSource.getMessage(OnOffEnum.OFF.resourceName(), null, locale));
        }

        bean.setMajor(entity.getMajor());
        bean.setEducationRecord(entity.getEducationRecord());
        bean.setDegree(entity.getDegree());
        bean.setHealthInfo(entity.getHealthInfo());
        bean.setHeight(entity.getHeight());
        bean.setWeight(entity.getWeight());
        bean.setResidence(entity.getResidence());
        bean.setHomeAddress(entity.getHomeAddress());
        bean.setTempResidentNum(entity.getTempResidentNum());
        if (entity.getContractStartDate() != null) {
            bean.setContractStartDate(
                    DateUtils.date2String(entity.getContractStartDate(), Constants.DATE_FORMAT));
        }
        if (entity.getContractEndDate() != null) {
            bean.setContractEndDate(
                    DateUtils.date2String(entity.getContractEndDate(), Constants.DATE_FORMAT));
        }
        bean.setJobTitle(entity.getJobTitle());
        bean.setJobGroup(entity.getJobGroup());
        bean.setJobCall(entity.getJobCall());
        bean.setJobLevel(entity.getJobLevel());
        bean.setJobDesc(entity.getJobDesc());
        if (bean.getManagerId() != null) {
            bean.setManagerIdText(personService.find(bean.getManagerId()).getPersonName());
        }
        if (bean.getAssistantId() != null) {
            bean.setAssistantIdText(personService.find(bean.getAssistantId()).getPersonName());
        }
        if (bean.getStatus() != null) {
            bean.setStatusText(PersonStatusEnum.getResouceName(bean.getStatus()));
        }
        bean.setStatus(entity.getStatus());
        bean.setLocation(entity.getLocation());
        bean.setWorkRoom(entity.getWorkRoom());
        bean.setPhone(entity.getPhone());
        bean.setMobile(entity.getMobile());
        bean.setOtherPhone(entity.getOtherPhone());
        bean.setFax(entity.getFax());
        bean.setEmail(entity.getEmail());

        return bean;
    }

    @Override
    public Person toEntity(PersonBean bean, Person oldEntity, AbstractSessionUserBean user,
            Date now) {
        // TODO Auto-generated method stub

        Person entity = oldEntity;

        if (entity == null) { // 表示新建
            entity = new Person();

            entity.setIsLabourUnion(OnOffEnum.OFF.value());

            entity.setCreator(user.getSessionUserId());
            entity.setcDatetime(now);
        } else { // 表示修改
            entity.setModifier(user.getSessionUserId());
            entity.setIsDeleted(OnOffEnum.OFF.value());
        }

        bean.setId(entity.getId()); // ID必须赋值
        entity.setmDatetime(now);

        if (!StringUtils.isEmpty(bean.getOrg_())) {
            Org org = new Org();
            org.setId(bean.getOrg_());
            entity.setOrg(org);
        }

        if (!StringUtils.isEmpty(bean.getDept_())) {
            Dept dept = new Dept();
            dept.setId(bean.getDept_());
            entity.setDept(dept);
        }

        if (!StringUtils.isEmpty(bean.getPost_())) {
            Post post = new Post();
            post.setId(bean.getPost_());
            entity.setPost(post);
        }

        if (!StringUtils.isEmpty(bean.getUser_())) {
            User user1 = new User();
            user1.setId(bean.getUser_());
            entity.setUser(user1);
        }

        if (!StringUtils.isEmpty(bean.getPhoto_())) {
            Archive photo = new Archive();
            photo.setId(bean.getPhoto_());
            entity.setPhoto(photo);
        }

        entity.setJobNum(bean.getJobNum());
        entity.setPersonName(bean.getPersonName());
        entity.setAccountType(bean.getAccountType());
        entity.setSecurityLevel(bean.getSecurityLevel());
        entity.setGender(bean.getGender());

        if (!StringUtils.isEmpty(bean.getBirthday())) {
            try {
                entity.setBirthday(
                        DateUtils.string2Date(bean.getBirthday(), Constants.DATE_FORMAT));
            } catch (ParseException e) {
                // do nothing
            }
        }

        entity.setEthnicGroup(bean.getEthnicGroup());
        entity.setNativePlace(bean.getNativePlace());
        entity.setRegResidence(bean.getRegResidence());
        entity.setIdCardNum(bean.getIdCardNum());
        entity.setMaritalStatus(bean.getMaritalStatus());
        entity.setPoliticalStatus(bean.getPoliticalStatus());

        if (!StringUtils.isEmpty(bean.getBeMemberDate())) {
            try {
                entity.setBeMemberDate(
                        DateUtils.string2Date(bean.getBeMemberDate(), Constants.DATE_FORMAT));
            } catch (ParseException e) {
                // do nothing
            }
        }

        if (!StringUtils.isEmpty(bean.getBePartyDate())) {
            try {
                entity.setBePartyDate(
                        DateUtils.string2Date(bean.getBePartyDate(), Constants.DATE_FORMAT));
            } catch (ParseException e) {

                // do nothing
            }
        }

        entity.setIsLabourUnion(bean.getIsLabourUnion());
        entity.setMajor(bean.getMajor());
        entity.setEducationRecord(bean.getEducationRecord());
        entity.setDegree(bean.getDegree());
        entity.setHealthInfo(bean.getHealthInfo());
        entity.setHeight(bean.getHeight());
        entity.setWeight(bean.getWeight());
        entity.setResidence(bean.getResidence());
        entity.setHomeAddress(bean.getHomeAddress());
        entity.setTempResidentNum(bean.getTempResidentNum());

        if (!StringUtils.isEmpty(bean.getContractStartDate())) {
            try {
                entity.setContractStartDate(
                        DateUtils.string2Date(bean.getContractStartDate(), Constants.DATE_FORMAT));
            } catch (ParseException e) {

                // do nothing
            }
        }

        if (!StringUtils.isEmpty(bean.getContractEndDate())) {
            try {
                entity.setContractEndDate(
                        DateUtils.string2Date(bean.getContractEndDate(), Constants.DATE_FORMAT));
            } catch (ParseException e) {

                // do nothing
            }
        }

        entity.setJobTitle(bean.getJobTitle());
        entity.setJobGroup(bean.getJobGroup());
        entity.setJobCall(bean.getJobCall());
        entity.setJobLevel(bean.getJobLevel());
        entity.setJobDesc(bean.getJobDesc());
        entity.setManagerId(bean.getManagerId());
        entity.setAssistantId(bean.getAssistantId());
        entity.setStatus(bean.getStatus());
        entity.setLocation(bean.getLocation());
        entity.setWorkRoom(bean.getWorkRoom());
        entity.setPhone(bean.getPhone());
        entity.setMobile(bean.getMobile());
        entity.setOtherPhone(bean.getOtherPhone());
        entity.setFax(bean.getFax());
        entity.setEmail(bean.getEmail());

        return entity;
    }

}
