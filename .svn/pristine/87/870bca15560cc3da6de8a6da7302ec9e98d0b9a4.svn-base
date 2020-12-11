package project.edge.service.hr;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import garage.origin.dao.Dao;
import garage.origin.domain.business.OrderByDto;
import garage.origin.service.GenericServiceImpl;
import project.edge.dao.auth.RoleDao;
import project.edge.dao.auth.UserDao;
import project.edge.dao.hr.PersonDao;
import project.edge.domain.entity.Person;
import project.edge.domain.entity.Role;
import project.edge.domain.entity.User;

/**
 * [t_person]对应的 Service。
 */
@Service
public class PersonServiceImpl extends GenericServiceImpl<Person, String> implements PersonService {

	@Resource
	private PersonDao personDao;

	@Resource
	private UserDao userDao;

	@Resource
	private RoleDao roleDao;

	@Override
	public Dao<Person, String> getDefaultDao() {
		return this.personDao;
	}

	@Override
	public OrderByDto getDefaultOrder() {
		return new OrderByDto("personName", false);
	}

	@Override
	@Transactional
	public void create(Collection<Person> entities) {
		if (entities == null || entities.isEmpty()) {
			return;
		}

		Role r = roleDao.find("3d5e66d5-48b3-4264-8b66-3f7033369f8d");

		for (Person p : entities) {
			User u = p.getUser();
			u.setRole(r);
			u.setPerson(null);
			this.userDao.create(u);
			this.personDao.create(p);

			u.setPerson(p);
		}
	}

	@Override
	@Transactional
	public void update(Collection<Person> entities) {
		List<User> userList = null;
		if (entities != null) {
			userList = entities.stream().map(p -> p.getUser()).collect(Collectors.toList());
		}
		/*User user = null;
		User oldUser = null;
		Role r = null;
		for (int i = 0; i < userList.size(); i++) {
			user = userList.get(i);
			oldUser = userDao.find(user.getId());
			r = roleDao.find(oldUser.getRole().getId());
			user.setRole(r);
			this.userDao.update(user);
		}*/
		this.userDao.update(userList);
		
		super.update(entities);
	}
}
