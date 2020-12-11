/**
 * 
 */
package project.edge.service.archive;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;

import javax.annotation.Resource;

import garage.origin.common.constant.OnOffEnum;
import garage.origin.dao.Dao;
import garage.origin.domain.business.OrderByDto;
import garage.origin.domain.filter.CommonFilter;
import garage.origin.service.GenericServiceImpl;
import project.edge.dao.archive.KnowledgeBaseDao;
import project.edge.domain.entity.KnowledgeBase;


/**
 * @author angel_000
 *
 */
@Service
public class KnowledgeBaseServiceImpl extends GenericServiceImpl<KnowledgeBase, String>
        implements KnowledgeBaseService {

    @Resource
    private KnowledgeBaseDao knowledgeBaseDao;

    @Override
    public Dao<KnowledgeBase, String> getDefaultDao() {
        return this.knowledgeBaseDao;
    }

    @Override
    public OrderByDto getDefaultOrder() {
        return new OrderByDto("archiveName", false);
    }

    /**
     * 如果是文件，且在当前目录下已有同名的，则覆盖并做版本管理。
     */
    @Override
    @Transactional
    public void create(KnowledgeBase entity) {

        if (OnOffEnum.OFF.value() == entity.getIsDir()) {

            CommonFilter f = new CommonFilter().addExact("id", entity.getId());
            if (this.knowledgeBaseDao.exist(f)) {

                this.update(entity);
                return;
            }
        }

        this.knowledgeBaseDao.create(entity);
    }

    @Override
    @Transactional
    public void deleteSub(Collection<String> pidList) {
        this.knowledgeBaseDao.deleteSub(pidList);
    }
}
