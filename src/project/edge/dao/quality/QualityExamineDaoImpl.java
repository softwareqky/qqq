package project.edge.dao.quality;

import org.hibernate.Criteria;
import org.hibernate.criterion.Restrictions;
import org.hibernate.sql.JoinType;
import org.springframework.stereotype.Repository;
import org.springframework.util.StringUtils;

import java.util.List;

import garage.origin.dao.HibernateDaoImpl;
import garage.origin.domain.business.OrderByDto;
import garage.origin.domain.business.PageCtrlDto;
import garage.origin.domain.filter.DataFilter;
import project.edge.domain.entity.QualityExamine;

/**
 * @author angel_000
 *         [t_quality_examine]对应的DAO。
 */
@Repository
public class QualityExamineDaoImpl extends HibernateDaoImpl<QualityExamine, String>
        implements QualityExamineDao {

    @Override
    public List<QualityExamine> list(DataFilter filter, List<OrderByDto> orders, PageCtrlDto page) {
        return getList(filter, orders, page);
    }

    @Override
    public List<QualityExamine> list(DataFilter filter, List<OrderByDto> orders) {
        return getList(filter, orders, null);
    }



    @Override
    protected void setListCriteriaAlias(Criteria query) {
        query.createAlias("project", "pj", JoinType.LEFT_OUTER_JOIN)
                .createAlias("report", "report", JoinType.LEFT_OUTER_JOIN)
                .createAlias("examinePerson", "examinePerson", JoinType.LEFT_OUTER_JOIN);
        query.createAlias("qualityExamineAttachments", "qualityExamineAttachments",
                JoinType.LEFT_OUTER_JOIN).createAlias("qualityExamineAttachments.archive",
                        "archives", JoinType.LEFT_OUTER_JOIN); // one-to-many
    }

    /**
     * 获取单条实体。
     * 
     * @param id 主键
     * @return 实体对象，如果没有找到实体，则返回null
     */
    @Override
    public QualityExamine find(String id) {
        if (StringUtils.isEmpty(id)) {
            return null;
        }

        // 这里将关联的信息全部取出，主要是为了配合新建/修改后重新获取实体信息，然后将这个完整的实体信息显示到一览画面中
        QualityExamine examine = (QualityExamine) session().createCriteria(this.type)
                .createAlias("project", "pj", JoinType.LEFT_OUTER_JOIN)
                .createAlias("report", "report", JoinType.LEFT_OUTER_JOIN)
                .createAlias("examinePerson", "examinePerson", JoinType.LEFT_OUTER_JOIN)
                .createAlias("qualityExamineAttachments", "qualityExamineAttachments",
                        JoinType.LEFT_OUTER_JOIN)
                .createAlias("qualityExamineAttachments.archive", "archives",
                        JoinType.LEFT_OUTER_JOIN)
                .add(Restrictions.eq("id", id)).uniqueResult();
        return examine;
    }
}
