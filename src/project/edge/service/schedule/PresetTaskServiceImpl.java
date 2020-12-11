package project.edge.service.schedule;

import org.springframework.stereotype.Service;

import javax.annotation.Resource;

import garage.origin.dao.Dao;
import garage.origin.domain.business.OrderByDto;
import garage.origin.service.GenericServiceImpl;
import project.edge.dao.schedule.PresetTaskDao;
import project.edge.domain.entity.PresetTask;

/**
 * @author angel_000
 *         [t_preset_task]对应的 Service。
 */
@Service
public class PresetTaskServiceImpl extends GenericServiceImpl<PresetTask, String>
        implements PresetTaskService {

    @Resource
    private PresetTaskDao presetTaskDao;

    @Override
    public Dao<PresetTask, String> getDefaultDao() {
        // TODO Auto-generated method stub
        return this.presetTaskDao;
    }

    @Override
    public OrderByDto getDefaultOrder() {
        return new OrderByDto("cDatetime", false);
    }
}
