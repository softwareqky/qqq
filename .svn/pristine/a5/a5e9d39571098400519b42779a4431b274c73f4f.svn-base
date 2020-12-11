package project.edge.service.system;

import org.junit.Assert;
import org.junit.Test;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;

import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import project.edge.domain.entity.SystemConfig;
import project.edge.test.util.BaseCoreTests;

public class SystemConfigServiceTests extends BaseCoreTests {

    @Resource
    private SystemConfigService systemConfigService;

    /**
     * 内容：创建一条系统配置
     * 
     * 基准：创建成功且数据显示正确
     */
    @Test
    public void testCreate() {

        SystemConfig config = new SystemConfig();
        config.setId("a");
        config.setConfigValue("b");
        config.setmDatetime(new Date());
        config.setModifier("c");

        this.systemConfigService.create(config);
        session().flush();

        String sql = "select * from t_system_config where id = :id";
        List<Map<String, Object>> list =
                this.jdbcTemplate.queryForList(sql, new MapSqlParameterSource("id", "a"));
        Map<String, Object> objectMap = list.get(0);

        Assert.assertEquals("b", objectMap.get("config_value"));
    }
}
