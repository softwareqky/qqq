package project.edge.service.project;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.io.File;
import javax.annotation.Resource;

import garage.origin.common.constant.Constants;
import garage.origin.common.constant.OnOffEnum;
import garage.origin.dao.Dao;
import garage.origin.domain.business.OrderByDto;
import garage.origin.service.GenericServiceImpl;
import project.edge.common.constant.FolderPath;
import project.edge.dao.archive.ArchiveDao;
import project.edge.dao.project.ProjectPersonChangeDao;
import project.edge.domain.entity.Archive;
import project.edge.domain.entity.ProjectPersonChange;

/**
 * @author angel_000
 *         [t_project_person_change]对应的 Service。
 */
@Service
public class ProjectPersonChangeServiceImpl extends GenericServiceImpl<ProjectPersonChange, String>
        implements ProjectPersonChangeService {

    @Resource
    private ProjectPersonChangeDao projectPersonChangeDao;

    @Resource
    private ArchiveDao archiveDao;

    @Override
    public Dao<ProjectPersonChange, String> getDefaultDao() {
        return this.projectPersonChangeDao;
    }

    @Override
    public OrderByDto getDefaultOrder() {
        return new OrderByDto("id", false);
    }

    /**
     * 增加处理附件文件。
     */
    @Override
    @Transactional
    public void create(ProjectPersonChange entity) {

        this.archiveDao.create(this.getDirArchive(entity));

        for (Archive a : entity.getArchives()) {
            this.archiveDao.create(a);
        }

        this.projectPersonChangeDao.create(entity);
    }

    /**
     * 增加处理附件文件。
     */
    @Override
    @Transactional
    public void update(ProjectPersonChange entity) {
        if (entity == null || StringUtils.isEmpty(entity.getId())) {
            return;
        }

        // 如果实体对应的文件夹Archive不存在，则创建
        if (this.archiveDao.find(entity.getId()) == null) {
            this.archiveDao.create(this.getDirArchive(entity));
        }

        // 创建新增的文件，同时将修改后保留的文件id存入map
        for (Archive a : entity.getArchives()) {

            // 修改后保留的Archive在Converter中只设了id，其他字段未设置
            if (!StringUtils.isEmpty(a.getRelativePath())) {
                this.archiveDao.create(a);
            }
        }

        super.update(entity);

        for (Archive archiveToDelete : entity.getArchivesToDelete()) {
            this.archiveDao.delete(archiveToDelete);
        }
    }
    
    @Transactional
    public void setData(ProjectPersonChange entity) {
    	if (entity == null || StringUtils.isEmpty(entity.getId())) {
            return;
        }
    	super.update(entity);
    }

    /**
     * 创建实体对应的文件夹Archive。
     * 
     * @return
     */
    private Archive getDirArchive(ProjectPersonChange entity) {

        // 实体对应的文件夹Archive
        Archive dir = new Archive();
        dir.setId(entity.getId());
        dir.setArchiveName(entity.getId());
        dir.setIsDir(OnOffEnum.ON.value());
        dir.setRelativePath(
                File.separator + FolderPath.PROJECT_PERSON_CHANGE + File.separator + dir.getId());
        dir.setFileSize(null);
        dir.setPid(FolderPath.PROJECT_PERSON_CHANGE);

        /**
         * 第零层：根
         * 第一层：FolderPath.PROJECT_PERSON_CHANGE(DIR)
         * 第二层：ProjectGroup.id(DIR)
         * 第三层：Archive文件
         */
        dir.setLevel(2);
        dir.setPath(Constants.SLASH + Constants.COMMA + FolderPath.PROJECT_PERSON_CHANGE);
        dir.setIsDeleted(OnOffEnum.OFF.value());
        dir.setCreator(entity.getCreator());
        dir.setcDatetime(entity.getcDatetime());
        dir.setmDatetime(entity.getmDatetime());

        return dir;
    }

}
