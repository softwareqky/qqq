package project.edge.domain.converter;

import java.util.Locale;

import org.springframework.context.MessageSource;

import project.edge.domain.entity.PlanTask;
import project.edge.domain.view.MainInfoTaskListBean;

public class MainInfoTaskListBeanConverter {

	public MainInfoTaskListBean fromEntity(PlanTask t, MessageSource messageSource, Locale locale) {

		if (t == null) {
			return null;
		}

		String[] statusArr = { "", "未启动", "延期中", "进行中", "完成", "搁置", "取消" };
		MainInfoTaskListBean bean = new MainInfoTaskListBean();
		bean.setId(t.getPlan().getId());
		bean.setTaskId(t.getId());
		bean.setName(t.getTaskName());
		bean.setPlanName(t.getPlan().getPlanName());
		bean.setStatus(statusArr[t.getFlowStatus()]);
		if (t.getPlan().getIsComplete() != null) {
			bean.setProgress(t.getPlan().getIsComplete() == 1
					? messageSource.getMessage("ui.fields.plan.completed", null, locale)
					: messageSource.getMessage("ui.fields.plan.not.incomplete", null, locale));
		} else {
			bean.setProgress(messageSource.getMessage("ui.fields.plan.not.incomplete", null, locale));
		}
		return bean;

	}
}
