package project.edge.service.notice;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import garage.origin.dao.Dao;
import garage.origin.domain.business.OrderByDto;
import garage.origin.service.GenericServiceImpl;
import project.edge.dao.notice.NoticeNotifyDao;
import project.edge.domain.entity.Notify;

@Service
public class NoticeNotifyServiceImpl extends GenericServiceImpl<Notify, String>
	implements NoticeNotifyService {

	@Resource
	private NoticeNotifyDao noticeNotifyDao;

	@Override
	public Dao<Notify, String> getDefaultDao() {
		return this.noticeNotifyDao;
	}

	@Override
	public OrderByDto getDefaultOrder() {
		return new OrderByDto("createAt", true);
	}
}
