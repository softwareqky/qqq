package project.edge.service.facility;

import org.springframework.stereotype.Service;

import javax.annotation.Resource;

import garage.origin.dao.Dao;
import garage.origin.domain.business.OrderByDto;
import garage.origin.service.GenericServiceImpl;
import project.edge.dao.facility.LinkDao;
import project.edge.domain.entity.Link;

/**
 * @author angel_000
 *         [t_link]对应的 Service。
 */
@Service
public class LinkServiceImpl extends GenericServiceImpl<Link, String> implements LinkService {

    @Resource
    private LinkDao linkDao;

    @Override
    public Dao<Link, String> getDefaultDao() {
        // TODO Auto-generated method stub
        return this.linkDao;
    }

    @Override
    public OrderByDto getDefaultOrder() {
        return new OrderByDto("cDatetime", false);
    }

}
