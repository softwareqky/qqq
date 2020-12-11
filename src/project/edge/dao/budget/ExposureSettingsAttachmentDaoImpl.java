package project.edge.dao.budget;

import org.springframework.stereotype.Repository;

import garage.origin.dao.HibernateDaoImpl;
import project.edge.domain.entity.ExposureSettingsAttachment;

/**
 * @author angel_000
 *         [t_plan_history_task_attachment]对应的DAO。
 */
@Repository
public class ExposureSettingsAttachmentDaoImpl
        extends HibernateDaoImpl<ExposureSettingsAttachment, String>
        implements ExposureSettingsAttachmentDao {

}
