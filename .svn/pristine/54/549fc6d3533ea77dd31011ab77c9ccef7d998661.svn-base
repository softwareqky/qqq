package project.edge.service.hr;

import org.springframework.stereotype.Service;

import javax.annotation.Resource;

import garage.origin.dao.Dao;
import garage.origin.domain.business.OrderByDto;
import garage.origin.service.GenericServiceImpl;
import project.edge.dao.hr.ExpertAchievementDao;
import project.edge.domain.entity.ExpertAchievement;

/**
 * @author angel_000
 *         [t_expert_achievement]对应的 Service。
 */
@Service
public class ExpertAchievementServiceImpl extends GenericServiceImpl<ExpertAchievement, String>
        implements ExpertAchievementService {

    @Resource
    private ExpertAchievementDao expertAchievementDao;

    @Override
    public Dao<ExpertAchievement, String> getDefaultDao() {
        // TODO Auto-generated method stub
        return this.expertAchievementDao;
    }

    @Override
    public OrderByDto getDefaultOrder() {
        return new OrderByDto("achievementName", false);
    }
}
