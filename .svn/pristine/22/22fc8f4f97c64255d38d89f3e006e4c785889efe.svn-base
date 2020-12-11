package project.edge.domain.converter;

import java.util.Locale;

import org.springframework.context.MessageSource;

import garage.origin.common.constant.Constants;
import garage.origin.common.util.DateUtils;
import project.edge.domain.entity.NoticeAnnouncement;
import project.edge.domain.view.MainInfoNoticeInfoListBean;

/**
 * @author angel_000
 *
 */
public class MainInfoNoticeListBeanConverter {

	/**
	 * 使用系统默认配置。
	 * 
	 * @param p
	 * @param messageSource
	 * @param locale
	 * @return
	 */
	public MainInfoNoticeInfoListBean fromEntity(NoticeAnnouncement n, MessageSource messageSource, Locale locale) {

		if (n == null) {
			return null;
		}

		MainInfoNoticeInfoListBean bean = new MainInfoNoticeInfoListBean();
		bean.setId(n.getId());

		bean.setNoticeTitle(n.getTittle());

		if (n.getRecivers() != null) {
			bean.setOriginator(n.getRecivers());
		}

		if (n.getcDatetime() != null) {
			bean.setTime(DateUtils.date2String(n.getcDatetime(), Constants.DATE_FORMAT));
		}
		bean.setDetail(n.getContent());
		return bean;
	}
}
