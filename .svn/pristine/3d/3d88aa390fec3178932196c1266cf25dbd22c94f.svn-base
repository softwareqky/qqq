package project.edge.dao.schedule;

import org.apache.commons.lang3.StringUtils;
import org.hibernate.criterion.Restrictions;
import org.hibernate.sql.JoinType;
import org.springframework.stereotype.Repository;

import garage.origin.dao.HibernateDaoImpl;
import project.edge.domain.entity.PlanTaskAttachment;

/**
 * @author angel_000
 *         [t_plan_task_attachment]对应的DAO。
 */
@Repository
public class PlanTaskAttachmentDaoImpl extends HibernateDaoImpl<PlanTaskAttachment, String>
        implements PlanTaskAttachmentDao {

	@Override
	public PlanTaskAttachment find(String id) {
		
		if (StringUtils.isEmpty(id)) {
			return null;
		}
		
		PlanTaskAttachment attachment = 
			(PlanTaskAttachment) session().createCriteria(this.type)
				.createAlias("planTask", "planTask", JoinType.LEFT_OUTER_JOIN)
				.createAlias("archive", "archive", JoinType.LEFT_OUTER_JOIN)
				.add(Restrictions.eq("id", id)).uniqueResult();
		
		return attachment;
	}
}
