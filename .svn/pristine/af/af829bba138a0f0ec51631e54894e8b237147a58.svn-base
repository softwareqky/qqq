package project.edge.test.util;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.junit.runner.RunWith;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;

/**
 * 单元测试的抽象类。不加载appContext-config.xml，改为加载testContext-config.xml
 * 
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(
        locations = {"classpath*:testContext-*.xml", "classpath*:appContext-database.xml"})
@Transactional
public abstract class BaseCoreTests {

    @Resource
    protected SessionFactory sessionFactory;

    @Resource
    protected NamedParameterJdbcTemplate jdbcTemplate;

    /**
     * 从SessionFactory获取Session。
     * 
     * @return Session对象
     */
    protected Session session() {
        // 事务必须是开启的(Required)，否则获取不到
        return this.sessionFactory.getCurrentSession();
    }
}
