package project.edge.integration.oa;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import garage.origin.common.constant.OnOffEnum;
import project.edge.common.constant.SystemConfigKeys;
import project.edge.domain.entity.Dept;
import project.edge.domain.entity.Org;
import project.edge.domain.entity.Person;
import project.edge.domain.entity.Post;
import project.edge.domain.entity.User;
import project.edge.service.auth.UserService;
import project.edge.service.hr.PersonService;
import project.edge.service.hr.PostService;
import project.edge.service.org.DeptService;
import project.edge.service.org.OrgService;
import project.edge.service.system.SystemConfigService;

/**
 * 用来导入OA的人力资源数据。
 *
 */
@Component
public class HrmManager {

    private static final Logger logger = LoggerFactory.getLogger(HrmManager.class);

    @Resource
    private OrgService orgService;

    @Resource
    private DeptService deptService;

    @Resource
    private PostService postService;

    @Resource
    private PersonService personService;

    @Resource
    private SystemConfigService systemConfigService;

	@Resource
	private UserService userService;

    public boolean doImport() {

        try {

            String ip = this.systemConfigService.getStringConfig(SystemConfigKeys.OA_IP);
            int port = this.systemConfigService.getIntConfig(SystemConfigKeys.OA_PORT, 89);

            HrmProxy proxy = new HrmProxy();

            List<Org> orgList = proxy.listOrg(ip, port);
            Map<String, Org> orgMap = new HashMap<>();
            for (Org o : orgList) {
                orgMap.put(o.getId(), o);
                logger.info("doImport Org o: {}", o.getOrgName());
            }

            List<Dept> deptList = proxy.listDept(ip, port, orgMap);
            Map<String, Dept> deptMap = new HashMap<>();
            for (Dept d : deptList) {
                deptMap.put(d.getId(), d);
                logger.info("doImport Dept d: {}", d.getDeptName());
            }

            List<Post> postList = proxy.listPost(ip, port, deptMap);
            Map<String, Post> postMap = new HashMap<>();
            for (Post p : postList) {
                postMap.put(p.getId(), p);
                logger.info("doImport Post p: {}", p.getPostName());
            }

            List<Person> personList = proxy.listPerson(ip, port, deptMap, postMap);
            Map<String, Person> personMap = new HashMap<>();
            for (Person p : personList) {
                personMap.put(p.getId(), p);
                logger.info("doImport Person Name:" + p.getPersonName()+ " id:" + p.getId() + " loginName:" + p.getUser().getLoginName());
            }

            // 因为有依赖关系，所以4个数据类型是同时获取的，其中只要有一种数据获取和解析失败，整个同步就终止

            List<Org> dbOrgList = this.orgService.list(null, null);
            List<Dept> dbDeptList = this.deptService.list(null, null);
            List<Post> dbPostList = this.postService.list(null, null);
            List<Person> dbPersonList = this.personService.list(null, null);

            Collection<Org> orgAddList = null;
            List<Org> orgUpdateList = new ArrayList<>();
            Collection<Dept> deptAddList = null;
            List<Dept> deptUpdateList = new ArrayList<>();
            Collection<Post> postAddList = null;
            List<Post> postUpdateList = new ArrayList<>();
            Collection<Person> personAddList = null;
            List<Person> personUpdateList = new ArrayList<>();

            if (orgMap.size() >= 0) {
                for (Org dbOrg : dbOrgList) {

                    String dbId = dbOrg.getId();
                    if (orgMap.containsKey(dbId)) {
                        orgUpdateList.add(orgMap.get(dbId));
                        orgMap.remove(dbId);

                    } else if (dbOrg.getIsDeleted() == OnOffEnum.OFF.value()) {
                        dbOrg.setIsDeleted(OnOffEnum.ON.value());
                        orgUpdateList.add(dbOrg);
                    }
                }
                if (!orgMap.isEmpty()) {
                    orgAddList = orgMap.values();
                }
            }

            if (deptMap.size() >= 0) {
                for (Dept dbDept : dbDeptList) {

                    String dbId = dbDept.getId();
                    if (deptMap.containsKey(dbId)) {
                        deptUpdateList.add(deptMap.get(dbId));
                        deptMap.remove(dbId);

                    } else if (dbDept.getIsDeleted() == OnOffEnum.OFF.value()) {
                        dbDept.setIsDeleted(OnOffEnum.ON.value());
                        deptUpdateList.add(dbDept);
                    }
                }
                if (!deptMap.isEmpty()) {
                    deptAddList = deptMap.values();
                }
            }

            if (postList.size() >= 0) {
                for (Post dbPost : dbPostList) {

                    String dbId = dbPost.getId();
                    if (postMap.containsKey(dbId)) {
                        postUpdateList.add(postMap.get(dbId));
                        postMap.remove(dbId);

                    } else if (dbPost.getIsDeleted() == OnOffEnum.OFF.value()) {
                        dbPost.setIsDeleted(OnOffEnum.ON.value());
                        postUpdateList.add(dbPost);
                    }
                }
                if (!postMap.isEmpty()) {
                    postAddList = postMap.values();
                }
            }

            if (personMap.size() >= 0) {
            	//SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
                //Date date = new Date();
                //String dateTime = sdf.format(date);
                
                for (Person dbPerson : dbPersonList) {

                    String dbId = dbPerson.getId();
                    if (personMap.containsKey(dbId)) {

                        Person p = personMap.get(dbId);

                        if ((p.getStatus() == 4) || (p.getStatus() == 5) || (p.getStatus() == 6)
                                || (p.getStatus() == 7)) {
                            p.setIsDeleted(OnOffEnum.ON.value());
                            p.getUser().setIsDeleted(OnOffEnum.ON.value());
                            
                            // 已删除用户loginName加时间标记（OA侧处理方式为直接清空loginName）
                            //String loginName = p.getUser().getLoginName() + "_" + dateTime;
                            //p.getUser().setLoginName(loginName);
                        }

                        personUpdateList.add(p);
                        personMap.remove(dbId);

                    } else if (dbPerson.getIsDeleted() == OnOffEnum.OFF.value()) {
                    	if (!"admin".equals(dbPerson.getUser().getLoginName())) {
                    		dbPerson.setIsDeleted(OnOffEnum.ON.value());
                            dbPerson.getUser().setIsDeleted(OnOffEnum.ON.value());
                            personUpdateList.add(dbPerson);
                    	}
                    }
                }
                if (!personMap.isEmpty()) {
                    personAddList = personMap.values();
                }
            }
            logger.info("Update Person List:");
            if (personUpdateList != null && personUpdateList.size() > 0) {
	            for (Person entity : personUpdateList) {
	            	// 更新用户关联权限留用原设定值
	    			User orgUser = this.userService.find(entity.getUser().getId());
	    			if (orgUser != null) {
	    				entity.getUser().setRole(orgUser.getRole());
	    			}
	            	logger.info("Update Person Name:" + entity.getPersonName()+ " id:" + entity.getId() + " loginName:" + entity.getUser().getLoginName());
	            }
            }
            logger.info("Add Person List:");
            if (personAddList != null && personAddList.size() > 0) {
	            for (Person entity : personAddList) {
	            	logger.info("Add Person Name:" + entity.getPersonName()+ " id:" + entity.getId() + " loginName:" + entity.getUser().getLoginName());
	            }
            }

            this.orgService.create(orgAddList);
            this.orgService.update(orgUpdateList);
            this.deptService.create(deptAddList);
            this.deptService.update(deptUpdateList);
            this.postService.create(postAddList);
            this.postService.update(postUpdateList);
            this.personService.update(personUpdateList);
            this.personService.create(personAddList);

        } catch (Exception e) {

            logger.error("Exception improt OA hrm data.", e);
            return false;
        }

        return true;
    }

}
