package project.edge.dao.auth;

import org.hibernate.criterion.Restrictions;
import org.hibernate.sql.JoinType;
import org.springframework.stereotype.Repository;
import org.springframework.util.StringUtils;

import garage.origin.dao.HibernateDaoImpl;
import project.edge.domain.entity.Role;


/**
 * [t_role]对应的DAO。
 */
@Repository
public class RoleDaoImpl extends HibernateDaoImpl<Role, String> implements RoleDao {

    /**
     * 获取单条实体。
     * 
     * @param id 主键
     * @return 实体对象，如果没有找到实体，则返回null
     */
    @Override
    public Role find(String id) {
        if (StringUtils.isEmpty(id)) {
            return null;
        }

        // 这里将关联的信息全部取出，主要是为了配合新建/修改后重新获取实体信息，然后将这个完整的实体信息显示到一览画面中
        Role role = (Role) session().createCriteria(this.type)
                .createAlias("pages", "pages", JoinType.LEFT_OUTER_JOIN)
                .add(Restrictions.eq("id", id)).uniqueResult();
        return role;
    }
}
