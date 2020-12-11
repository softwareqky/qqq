/**
 * 
 */
package project.edge.service.hr;

import org.springframework.stereotype.Service;

import javax.annotation.Resource;

import garage.origin.dao.Dao;
import garage.origin.domain.business.OrderByDto;
import garage.origin.service.GenericServiceImpl;
import project.edge.dao.hr.PostDao;
import project.edge.domain.entity.Post;


/**
 * @author angel_000
 *         [t_post]对应的 Service。
 */
@Service
public class PostServiceImpl extends GenericServiceImpl<Post, String> implements PostService {

    @Resource
    private PostDao postDao;

    @Override
    public Dao<Post, String> getDefaultDao() {
        // TODO Auto-generated method stub
        return this.postDao;
    }

    @Override
    public OrderByDto getDefaultOrder() {
        return new OrderByDto("postName", false);
    }

}
