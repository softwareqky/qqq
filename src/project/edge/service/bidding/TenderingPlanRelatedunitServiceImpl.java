package project.edge.service.bidding;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import garage.origin.dao.Dao;
import garage.origin.domain.business.OrderByDto;
import garage.origin.service.GenericServiceImpl;
import project.edge.dao.bidding.TenderingPlanDao;
import project.edge.dao.bidding.TenderingPlanRelatedunitDao;
import project.edge.domain.entity.PurchaseOrder;
import project.edge.domain.entity.TenderingPurchase;
import project.edge.domain.entity.TenderingPlan;
import project.edge.domain.entity.TenderingPlanRelatedunit;

/**
 * [t_tenderee]对应的 Service。
 */
@Service
public class TenderingPlanRelatedunitServiceImpl extends GenericServiceImpl<TenderingPlanRelatedunit, String>
        implements TenderingPlanRelatedunitService {

    @Resource
    private TenderingPlanRelatedunitDao tenderingPlanRelatedunitDao;
    
    @Resource
    private TenderingPlanDao tenderingPlanDao;

    @Override
    public Dao<TenderingPlanRelatedunit, String> getDefaultDao() {
        // TODO Auto-generated method stub
        return this.tenderingPlanRelatedunitDao;
    }

    @Override
    public OrderByDto getDefaultOrder() {
        return new OrderByDto("cDatetime");
    }
    
    /**
     * 增加所属项目处理
     */
    @Override
    @Transactional
    public void create(TenderingPlanRelatedunit entity) {

        if (entity == null || StringUtils.isEmpty(entity.getId())) {
            return;
        }

        if (entity.getTenderingPlan() != null) {
        	TenderingPlan tenderingPlan = tenderingPlanDao.find(entity.getTenderingPlan().getId());
        	if (tenderingPlan != null) {
        		entity.setProject(tenderingPlan.getProject());
        	}
        }
        
        super.create(entity);
    }

    /**
     * 增加所属项目处理
     */
    @Override
    @Transactional
    public void update(TenderingPlanRelatedunit entity) {
        if (entity == null || StringUtils.isEmpty(entity.getId())) {
            return;
        }

        if (entity.getTenderingPlan() != null) {
        	TenderingPlan tenderingPlan = tenderingPlanDao.find(entity.getTenderingPlan().getId());
        	if (tenderingPlan != null) {
        		entity.setProject(tenderingPlan.getProject());
        	}
        }

        super.update(entity);
    }

}
