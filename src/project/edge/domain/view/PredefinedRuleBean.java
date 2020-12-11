/**
 * 
 */
package project.edge.domain.view;

import garage.origin.domain.view.ViewBean;


/**
 * @author angel_000
 *        保存系统配置的表现层DTO。
 */
public class PredefinedRuleBean implements ViewBean {
    
    private String id;
    private String configValue;
    
    public String getId() {
        return this.id;
    }
    
    public void setId(String id) {
        this.id = id;
    }
    
    public String getConfigValue() {
        return this.configValue;
    }
    
    public void setConfigValue(String configValue) {
        this.configValue = configValue;
    }

}
