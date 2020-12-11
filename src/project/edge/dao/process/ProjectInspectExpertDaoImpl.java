package project.edge.dao.process;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.criterion.Restrictions;
import org.hibernate.sql.JoinType;
import org.springframework.stereotype.Repository;
import org.springframework.util.StringUtils;

import garage.origin.dao.HibernateDaoImpl;
import garage.origin.domain.business.OrderByDto;
import garage.origin.domain.business.PageCtrlDto;
import garage.origin.domain.filter.DataFilter;
import project.edge.domain.entity.Project;
import project.edge.domain.entity.ProjectInspectExpert;

@Repository
public class ProjectInspectExpertDaoImpl extends HibernateDaoImpl<ProjectInspectExpert, String> implements ProjectInspectExpertDao {
	@Override
    public List<ProjectInspectExpert> list(DataFilter filter, List<OrderByDto> orders, PageCtrlDto page) {
        return getList(filter, orders, page);
    }

    @Override
    public List<ProjectInspectExpert> list(DataFilter filter, List<OrderByDto> orders) {
        return getList(filter, orders, null);
    }

    @Override
    protected void setListCriteriaAlias(Criteria query) {
        // 默认是 inner join，这里显示指定left join
        query.createAlias("projectInspect", "projectInspect", JoinType.LEFT_OUTER_JOIN)
                .createAlias("expert", "expert", JoinType.LEFT_OUTER_JOIN);
        
    }

    /**
     * 获取单条实体。
     * 
     * @param id 主键
     * @return 实体对象，如果没有找到实体，则返回null
     */
    @Override
    public ProjectInspectExpert find(String id) {
        if (StringUtils.isEmpty(id)) {
            return null;
        }

        // 这里将关联的信息全部取出，主要是为了配合新建/修改后重新获取实体信息，然后将这个完整的实体信息显示到一览画面中
        ProjectInspectExpert projectInspectExpert = (ProjectInspectExpert) session().createCriteria(this.type)
        		.createAlias("projectInspect", "projectInspect", JoinType.LEFT_OUTER_JOIN)
                .createAlias("expert", "expert", JoinType.LEFT_OUTER_JOIN)
                .add(Restrictions.eq("id", id)).uniqueResult();
        return projectInspectExpert;
    }
}
