package project.edge.domain.converter;

import java.util.Date;
import java.util.Locale;

import org.apache.axis.utils.StringUtils;
import org.springframework.context.MessageSource;

import garage.origin.common.constant.OnOffEnum;
import garage.origin.domain.view.AbstractSessionUserBean;
import garage.origin.domain.view.converter.ViewConverter;
import project.edge.common.constant.PaperLibraryStatusEnum;
import project.edge.domain.entity.PaperLibrary;
import project.edge.domain.entity.Project;
import project.edge.domain.view.PaperLibraryBean;

public class PaperLibraryBeanConverter implements ViewConverter<PaperLibrary, PaperLibraryBean> {

	@Override
	public PaperLibraryBean fromEntity(PaperLibrary entity, MessageSource messageSource, Locale locale) {
		// TODO Auto-generated method stub
		PaperLibraryBean bean = new PaperLibraryBean();
		
		bean.setId(entity.getId());
		bean.setArchiveNo(entity.getArchiveNo());
		bean.setArchiveName(entity.getArchiveName());
		
		Project project = entity.getProject();
		if (project != null) {
			bean.setProject_(project.getId());
			bean.setProjectText(project.getProjectName());
		}
		
		bean.setRelativeBox(entity.getRelativeBox());
		bean.setIsCopy(entity.getIsCopy());
		bean.setIsCopyText(entity.getIsCopy() == 1
                ? messageSource.getMessage(OnOffEnum.ON.resourceName(), null, locale)
                : messageSource.getMessage(OnOffEnum.OFF.resourceName(), null, locale));
		bean.setIsSecret(entity.getIsSecret());
		bean.setIsSecretText(entity.getIsSecret() == 1
                ? messageSource.getMessage(OnOffEnum.ON.resourceName(), null, locale)
                : messageSource.getMessage(OnOffEnum.OFF.resourceName(), null, locale));
		bean.setStatus(entity.getStatus());
		bean.setStatusText(messageSource
                .getMessage(PaperLibraryStatusEnum.getResouceName((int)entity.getStatus()), null, locale));
		bean.setRemark(entity.getRemark());
		bean.setIsDeleted(entity.getIsDeleted());
		
		return bean;
	}

	@Override
	public PaperLibrary toEntity(PaperLibraryBean bean, PaperLibrary oldEntity, AbstractSessionUserBean user,
			Date now) {
		// TODO Auto-generated method stub
		PaperLibrary entity = oldEntity;
		
		if (entity == null) {
			// 新增
			entity = new PaperLibrary();
			entity.setCreator(user.getSessionUserId());
			entity.setcDatetime(now);
			entity.setModifier("");
		} else {
			// 变更
			entity.setModifier(user.getSessionUserId());
		}
		
		bean.setId(entity.getId());
		entity.setArchiveName(bean.getArchiveName());
		entity.setArchiveNo(bean.getArchiveNo());
		if (!StringUtils.isEmpty(bean.getProject_())) {
			Project project = new Project();
			project.setId(bean.getProject_());
	        project.setProjectName(bean.getProjectText());
			entity.setProject(project);
		}
		entity.setRelativeBox(bean.getRelativeBox());
		entity.setIsCopy(bean.getIsCopy());
		entity.setIsSecret(bean.getIsSecret());
		entity.setStatus(bean.getStatus());
		entity.setRemark(bean.getRemark());
		entity.setmDatetime(now);
		entity.setIsDeleted(OnOffEnum.OFF.value());
		
		return entity;
	}

}
