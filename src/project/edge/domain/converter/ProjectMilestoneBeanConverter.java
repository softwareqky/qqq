
package project.edge.domain.converter;

import java.util.Date;
import java.util.Locale;

import org.springframework.context.MessageSource;
import org.springframework.util.StringUtils;

import garage.origin.common.constant.Constants;
import garage.origin.common.util.DateUtils;
import garage.origin.domain.view.AbstractSessionUserBean;
import garage.origin.domain.view.converter.ViewConverter;
import project.edge.domain.entity.DataOption;
import project.edge.domain.entity.Person;
import project.edge.domain.entity.Project;
import project.edge.domain.entity.ProjectMilestone;
import project.edge.domain.view.ProjectMilestoneBean;

/**
 * @author angel_000 转换虚拟组织表对应的view和entity的转换器。
 *
 */
public class ProjectMilestoneBeanConverter implements ViewConverter<ProjectMilestone, ProjectMilestoneBean> {

	@Override
	public ProjectMilestoneBean fromEntity(ProjectMilestone entity, MessageSource messageSource, Locale locale) {

		ProjectMilestoneBean bean = new ProjectMilestoneBean();

		Project project = entity.getProject();
		if (project != null) {
			bean.setProject_(project.getId());
			bean.setProjectText(project.getProjectName());
		}

		bean.setId(entity.getId());
		bean.setMilestoneName(entity.getMilestoneName());
		DataOption type = entity.getType();
		if (type != null) {
			bean.setType_(type.getId());
			bean.setTypeText(type.getOptionName());
		}
		if (entity.getcDatetime() != null) {
			bean.setcDatetime(DateUtils.date2String(entity.getcDatetime(), Constants.DATE_FORMAT));
		}

		Person person = entity.getCreator();
		if (person != null) {
			bean.setCreator_(person.getId());
			bean.setCreatorText(person.getPersonName());
		}
		return bean;
	}

	@Override
	public ProjectMilestone toEntity(ProjectMilestoneBean bean, ProjectMilestone oldEntity,
			AbstractSessionUserBean user, Date now) {
		ProjectMilestone entity = oldEntity;

		if (entity == null) { // 表示新建
			entity = new ProjectMilestone();

			if (!StringUtils.isEmpty(user.getSessionUserId())) {
				Person person = new Person();
				person.setId(user.getSessionUserId());
				entity.setCreator(person);
			}
			entity.setcDatetime(now);

		} else {// 表示修改
			if (!StringUtils.isEmpty(user.getSessionUserId())) {
				Person person = new Person();
				person.setId(user.getSessionUserId());
				entity.setModifier(person);
			}
		}

		if (!StringUtils.isEmpty(bean.getProject_())) {
			Project project = new Project();
			project.setId(bean.getProject_());
			project.setProjectName(bean.getProjectText());
			entity.setProject(project);
		}

		bean.setId(entity.getId()); // ID必须赋值
		entity.setmDatetime(now);

		entity.setMilestoneName(bean.getMilestoneName());
		if (!StringUtils.isEmpty(bean.getType_())) {
			DataOption type = new DataOption();
			type.setId(bean.getType_());
			entity.setType(type);
		}

		return entity;
	}

}
