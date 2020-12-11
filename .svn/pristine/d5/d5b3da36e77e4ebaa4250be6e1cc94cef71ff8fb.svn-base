package project.edge.domain.converter;

import java.util.Date;
import java.util.Locale;

import org.springframework.context.MessageSource;
import org.springframework.util.StringUtils;

import garage.origin.common.constant.Constants;
import garage.origin.common.util.DateUtils;
import garage.origin.domain.view.AbstractSessionUserBean;
import garage.origin.domain.view.converter.ViewConverter;
import project.edge.common.constant.ProjectPersonOnDutyEnum;
import project.edge.domain.entity.Person;
import project.edge.domain.entity.Project;
import project.edge.domain.entity.ProjectPerson;
import project.edge.domain.entity.ProjectRole;
import project.edge.domain.entity.VirtualOrg;
import project.edge.domain.view.ProjectPersonBean;

/**
 * 转换项目成员对应的view和entity的转换器。
 *
 */
public class ProjectPersonBeanConverter implements ViewConverter<ProjectPerson, ProjectPersonBean> {

	@Override
	public ProjectPersonBean fromEntity(ProjectPerson entity, MessageSource messageSource, Locale locale) {

		ProjectPersonBean bean = new ProjectPersonBean();

		bean.setId(entity.getId());

		Project project = entity.getProject();
		if (project != null) {
			bean.setProject_(project.getId());
			bean.setProjectText(project.getProjectName());
		}

		Person person = entity.getPerson();
		if (person != null) {
			bean.setPerson_(person.getId());
			bean.setPersonText(person.getPersonName());
		}

		ProjectRole projectRole = entity.getProjectRole();
		if (projectRole != null) {
			bean.setProjectRole_(projectRole.getId());
			bean.setProjectRoleText(projectRole.getProjectRoleName());
		}

		VirtualOrg virtualOrg = entity.getVirtualOrg();
		if (virtualOrg != null) {
			bean.setVirtualOrg_(virtualOrg.getId());
			bean.setVirtualOrgText(virtualOrg.getVirtualOrgName());
		}

		bean.setRemark(entity.getRemark());

		Person creator = entity.getCreator();
		Person modifier = entity.getModifier();

		if (modifier != null) {
			bean.setCreator_(modifier.getId());
			bean.setCreatorText(modifier.getPersonName());
		} else {
			bean.setCreator_(creator.getId());
			bean.setCreatorText(creator.getPersonName());
		}

		bean.setmDatetime(DateUtils.date2String(entity.getmDatetime(), Constants.DATE_FORMAT));
		// bean.setIsOnDuty(entity.getIsOnDuty());

		bean.setIsOnDuty_(entity.getIsOnDuty());
		bean.setIsOnDutyText(messageSource
				.getMessage(ProjectPersonOnDutyEnum.getResouceName(entity.getIsOnDuty()), null, locale));

		return bean;
	}

	@Override
	public ProjectPerson toEntity(ProjectPersonBean bean, ProjectPerson oldEntity, AbstractSessionUserBean user,
			Date now) {

		ProjectPerson entity = oldEntity;

		if (entity == null) { // 表示新建
			entity = new ProjectPerson();

			if (!StringUtils.isEmpty(user.getSessionUserId())) {
				Person person = new Person();
				person.setId(user.getSessionUserId());
				person.setPersonName(user.getSessionUserName());
				entity.setCreator(person);
			}
			entity.setcDatetime(now);

			if (!StringUtils.isEmpty(bean.getProject_())) {
				Project project = new Project();
				project.setId(bean.getProject_());
				project.setProjectName(bean.getProjectText());
				entity.setProject(project);
			}

			if (!StringUtils.isEmpty(bean.getPerson_())) {
				Person person = new Person();
				person.setId(bean.getPerson_());
				entity.setPerson(person);
			}

		} else { // 表示修改
			// entity.setModifier(user.getSessionUserId());
			if (!StringUtils.isEmpty(user.getSessionUserId())) {
				Person person = new Person();
				person.setId(user.getSessionUserId());
				entity.setModifier(person);
			}
			entity.setmDatetime(now);
		}

		bean.setId(entity.getId()); // ID必须赋值
		entity.setmDatetime(now);

		if (!StringUtils.isEmpty(bean.getProjectRole_())) {
			ProjectRole projectRole = new ProjectRole();
			projectRole.setId(bean.getProjectRole_());
			entity.setProjectRole(projectRole);
		}

		if (!StringUtils.isEmpty(bean.getVirtualOrg_())) {
			VirtualOrg virtualOrg = new VirtualOrg();
			virtualOrg.setId(bean.getVirtualOrg_());
			entity.setVirtualOrg(virtualOrg);
		}

		entity.setRemark(bean.getRemark());
		entity.setIsOnDuty(bean.getIsOnDuty_());

		return entity;
	}

}
