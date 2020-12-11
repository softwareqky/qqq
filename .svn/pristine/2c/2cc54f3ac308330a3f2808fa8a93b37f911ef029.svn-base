/**
 * 
 */
package project.edge.service.budget;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import garage.origin.common.constant.OnOffEnum;
import garage.origin.dao.Dao;
import garage.origin.service.GenericServiceImpl;
import project.edge.dao.budget.PredefinedRuleDao;
import project.edge.domain.entity.PredefinedRule;

/**
 * @author angel_000 [t_predefined_rule]对应的 Service。
 */
@Service
public class PredefinedRuleServiceImpl extends GenericServiceImpl<PredefinedRule, String>
		implements PredefinedRuleService {

	@Resource
	private PredefinedRuleDao predefinedRuleDao;

	@Override
	public Dao<PredefinedRule, String> getDefaultDao() {
		// TODO Auto-generated method stub
		return this.predefinedRuleDao;
	}

	@Override
	@Transactional
	public boolean getBoolConfig(String key) {

		if (StringUtils.isEmpty(key)) {
			return false;
		}

		PredefinedRule config = this.predefinedRuleDao.find(key);
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

		PredefinedRule config = this.predefinedRuleDao.find(key);
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

		PredefinedRule config = this.predefinedRuleDao.find(key);
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

		PredefinedRule config = this.predefinedRuleDao.find(key);
		if (config == null) {
			return null;
		}
		return config.getConfigValue();
	}

}
