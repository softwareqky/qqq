package project.edge.domain.converter;

import java.util.Date;
import java.util.Locale;
import java.util.Set;

import javax.annotation.Resource;

import org.springframework.context.MessageSource;

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
import project.edge.domain.entity.ProjectPerson;
import project.edge.domain.entity.User;
import project.edge.domain.view.PersonBean;
import project.edge.service.hr.PersonService;

public class ProjectPersonDetailBeanConverter implements ViewConverter<ProjectPerson, PersonBean> {

    @Resource
    private PersonService personService;
    
	@Override
	public PersonBean fromEntity(ProjectPerson entityOrg, MessageSource messageSource, Locale locale) {
		// TODO Auto-generated method stub
		PersonBean bean = new PersonBean();
		Person entity = entityOrg.getPerson();
        bean.setId(entityOrg.getId());

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
	public ProjectPerson toEntity(PersonBean bean, ProjectPerson oldEntity, AbstractSessionUserBean user, Date now) {
		// TODO Auto-generated method stub
		return null;
	}

}
