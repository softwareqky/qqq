/**
 * 
 */
package project.edge.service.system;

import garage.origin.service.Service;
import project.edge.domain.entity.SystemConfig;


/**
 * @author angel_000
 *         [t_system_config]对应的 Service。
 */
public interface SystemConfigService extends Service<SystemConfig, String> {

    /**
     * 根据配置项的键，返回bool型的值。
     * 
     * @param key
     * @return 当config_value等于"1"时返回true，否则返回false。
     */
    boolean getBoolConfig(String key);

    /**
     * 根据配置项的键，返回整型的值。
     * 
     * @param key
     * @return 如果没有找到匹配的键，或者config_value不是整型时，返回null
     */
    Integer getIntConfig(String key);

    /**
     * 根据配置项的键，返回整型的值。
     * 
     * @param key
     * @return 如果没有找到匹配的键，或者config_value不是整型时，返回defaultValue
     */
    int getIntConfig(String key, int defaultValue);

    /**
     * 根据配置项的键，返回字符型的值。
     * 
     * @param key
     * @return 如果没有找到匹配的键，返回null
     */
    String getStringConfig(String key);
    
}
