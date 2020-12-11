package project.edge.dao.notice;

import java.util.List;

import org.springframework.stereotype.Repository;

import garage.origin.dao.HibernateDaoImpl;
import garage.origin.domain.business.OrderByDto;
import garage.origin.domain.business.PageCtrlDto;
import garage.origin.domain.filter.DataFilter;
import project.edge.domain.entity.UserNotify;

@Repository
public class NoticeUserNotifyDaoImpl extends HibernateDaoImpl<UserNotify, String> implements NoticeUserNotifyDao {

	@Override
    public List<UserNotify> list(DataFilter filter, List<OrderByDto> orders,
            PageCtrlDto page) {
        return getList(filter, orders, page);
    }

    @Override
    public List<UserNotify> list(DataFilter filter, List<OrderByDto> orders) {
        return getList(filter, orders, null);
    }
}
