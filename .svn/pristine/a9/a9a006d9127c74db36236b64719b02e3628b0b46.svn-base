/**
 * 
 */
package project.edge.service.system;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;

import garage.origin.common.constant.OnOffEnum;
import garage.origin.dao.Dao;
import garage.origin.service.GenericServiceImpl;
import project.edge.dao.system.SystemConfigDao;
import project.edge.domain.entity.SystemConfig;


/**
 * @author angel_000
 *         [t_system_config]对应的 Service。
 */
@Service
public class SystemConfigServiceImpl extends GenericServiceImpl<SystemConfig, String>
        implements SystemConfigService {

    @Resource
    private SystemConfigDao systemConfigDao;

    @Override
    public Dao<SystemConfig, String> getDefaultDao() {
        // TODO Auto-generated method stub
        return this.systemConfigDao;
    }

    @Override
    @Transactional
    public boolean getBoolConfig(String key) {

        if (StringUtils.isEmpty(key)) {
            return false;
        }

        SystemConfig config = this.systemConfigDao.find(key);
        if (config == null) {
            return false;
        }

        if (OnOffEnum.ON.value().toString().equals(config.getConfigValue())) {
            return true;
        }

        return false;
    }

    @Override
    @Transactional
    public Integer getIntConfig(String key) {

        if (StringUtils.isEmpty(key)) {
            return null;
        }

        SystemConfig config = this.systemConfigDao.find(key);
        if (config == null) {
            return null;
        }

        try {
            int value = Integer.parseInt(config.getConfigValue());
            return value;

        } catch (Exception e) {
            // do nothing
        }

        return null;
    }

    @Override
    @Transactional
    public int getIntConfig(String key, int defaultValue) {
        if (StringUtils.isEmpty(key)) {
            return defaultValue;
        }

        SystemConfig config = this.systemConfigDao.find(key);
        if (config == null) {
            return defaultValue;
        }

        try {
            int value = Integer.parseInt(config.getConfigValue());
            return value;

        } catch (Exception e) {
            // do nothing
        }

        return defaultValue;
    }

    @Override
    @Transactional
    public String getStringConfig(String key) {

        if (StringUtils.isEmpty(key)) {
            return null;
        }

        SystemConfig config = this.systemConfigDao.find(key);
        if (config == null) {
            return null;
        }
        return config.getConfigValue();
    }

}
