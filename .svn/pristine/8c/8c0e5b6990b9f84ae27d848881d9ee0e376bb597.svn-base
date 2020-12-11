package project.edge.integration.oa;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import garage.origin.common.constant.Constants;
import garage.origin.common.constant.OnOffEnum;
import garage.origin.common.util.CheckUtils;
import garage.origin.common.util.DateUtils;
import localhost.services.HrmService.HrmServicePortTypeProxy;
import project.edge.domain.entity.Dept;
import project.edge.domain.entity.Org;
import project.edge.domain.entity.Person;
import project.edge.domain.entity.Post;
import project.edge.domain.entity.Role;
import project.edge.domain.entity.User;
import weaver.hrm.webservice.DepartmentBean;
import weaver.hrm.webservice.JobTitleBean;
import weaver.hrm.webservice.SubCompanyBean;
import weaver.hrm.webservice.UserBean;

/**
 * 从OA的人力资源接口中拉取数据，转换成实体。
 *
 */
public class HrmProxy {

    private static final Logger logger = LoggerFactory.getLogger(HrmProxy.class);

    private final static String IP_TEMPLATE = "http://%1$s:%2$s";
    private final static String ENDPOINT_TEMPLATE = "http://%1$s:%2$s/services/HrmService";

    /**
     * 获取分部。
     * 
     * @param ip OA的IP
     * @param port OA的端口
     * @return
     * @throws Exception
     */
    private List<SubCompanyBean> fetchSubCompany(String ip, int port) throws Exception {

        HrmServicePortTypeProxy service =
                new HrmServicePortTypeProxy(String.format(ENDPOINT_TEMPLATE, ip, port));

        SubCompanyBean[] companyBeanArray =
                service.getHrmSubcompanyInfo(String.format(IP_TEMPLATE, ip, port));

        if (companyBeanArray == null) {
            logger.error("Fetch SubCompany NULL!");
            return new ArrayList<>();
        }
        logger.info("Fetch SubCompany count: {}", companyBeanArray.length);
        return Arrays.asList(companyBeanArray);
    }

    /**
     * 将分部转换成组织，并设置组织树的层级。
     * 
     * @param ip OA的IP
     * @param port OA的端口
     * @return
     * @throws Exception
     */
    public List<Org> listOrg(String ip, int port) throws Exception {

        List<Org> resultList = new ArrayList<>();

        // 按广度优先构建树形结构
        List<Org> tempList = new ArrayList<>(); // 作为下一次遍历时的上级节点列表
        List<Org> childList = new ArrayList<>(); // 非根节点
        Map<String, Org> idMap = new HashMap<>(); // key: Org.id，用来处理孤儿节点
        for (SubCompanyBean bean : this.fetchSubCompany(ip, port)) {

            Org entity = this.convertSubCompanyBean(bean);

            if (StringUtils.isEmpty(entity.getPid())) { // 第0层根节点

                entity.setPid(null); // 强制设为null，防止出现空字符串
                entity.setLevel(0);
                tempList.add(entity);
                resultList.add(entity);
            } else {
                childList.add(entity);
            }

            idMap.put(entity.getId(), entity);
        }

        Map<String, List<Org>> pidMap = new HashMap<>(); // key: Org.pid，进入pidMap的item一定有父节点
        for (Org c : childList) {

            String pid = c.getPid();
            if (idMap.containsKey(pid)) {

                if (pidMap.containsKey(pid)) {
                    pidMap.get(pid).add(c);
                } else {
                    List<Org> list = new ArrayList<>();
                    list.add(c);
                    pidMap.put(pid, list);
                }

            } else {
                // 处理孤儿节点，将其视为根节点
                logger.warn("Orphan org detected! ID: {}, PID: {}", c.getId(), pid);

                c.setPid(null); // 强制设为null
                c.setLevel(0);
                tempList.add(c);
                resultList.add(c);
            }
        }

        while (pidMap.size() > 0) {
            List<Org> pList = tempList;
            tempList = new ArrayList<>();

            for (Org p : pList) {
                String pid = p.getId();
                if (pidMap.containsKey(pid)) {

                    for (Org o : pidMap.get(pid)) {
                        o.setLevel(p.getLevel() + 1);
                        tempList.add(o);
                        resultList.add(o);
                    }

                    pidMap.remove(pid);
                    if (pidMap.size() == 0) {
                        break;
                    }
                }
            }
        }

        return resultList;
    }

    private Org convertSubCompanyBean(SubCompanyBean bean) {

        Org entity = new Org();
        entity.setId(bean.get_subcompanyid());
        entity.setPid(bean.get_supsubcompanyid());
        entity.setOrgCode(bean.get_code());
        entity.setOrgName(bean.get_fullname());
        entity.setShortName(bean.get_shortname());
        entity.setOrgOrder(CheckUtils.checkStringToInteger(bean.get_showorder()));
        entity.setIsDeleted(
                OnOffEnum.ON.value().toString().equals(bean.get_canceled()) ? OnOffEnum.ON.value()
                        : OnOffEnum.OFF.value());

        Date changeDate = new Date();
        try {
            if (!StringUtils.isEmpty(bean.getLastChangdate())) {
                changeDate = DateUtils.string2Date(bean.getLastChangdate(), Constants.DATE_FORMAT);
            }
        } catch (Exception e) {
            // logger.error("Exception parsing lastChangedate of SubCompanyBean.", e);
        }
        entity.setcDatetime(changeDate);
        entity.setmDatetime(changeDate);
        entity.setCreator(Constants.ADMIN_USER_ID);

        return entity;
    }

    /**
     * 获取部门。
     * 
     * @return
     * @throws Exception
     */
    private List<DepartmentBean> fetchDepartment(String ip, int port) throws Exception {

        HrmServicePortTypeProxy service =
                new HrmServicePortTypeProxy(String.format(ENDPOINT_TEMPLATE, ip, port));

        /**
         * subcompanyId 分部id
         */
        DepartmentBean[] deptArray =
                service.getHrmDepartmentInfo(String.format(IP_TEMPLATE, ip, port), null);

        if (deptArray == null) {
            logger.error("Fetch Department NULL!");
            return new ArrayList<>();
        }
        logger.info("Fetch Department count: {}", deptArray.length);
        return Arrays.asList(deptArray);
    }

    /**
     * 转换部门，并设置部门树的层级。
     * 
     * @param ip OA的IP
     * @param port OA的端口
     * @param orgMap 组织Map
     * @return
     * @throws Exception
     */
    public List<Dept> listDept(String ip, int port, Map<String, Org> orgMap) throws Exception {

        List<Dept> resultList = new ArrayList<>();

        // 按广度优先构建树形结构
        List<Dept> tempList = new ArrayList<>(); // 作为下一次遍历时的上级节点列表
        List<Dept> childList = new ArrayList<>(); // 非根节点
        Map<String, Dept> idMap = new HashMap<>(); // key: Dept.id，用来处理孤儿节点
        for (DepartmentBean bean : this.fetchDepartment(ip, port)) {

            Dept entity = this.convertDepartmentBean(bean, orgMap);
            if (entity == null) {
                logger.error("Org not found for dept with id {} and org id {} !",
                        bean.get_departmentid(), bean.get_subcompanyid());
                continue;
            }

            if (StringUtils.isEmpty(entity.getPid())) { // 第0层根节点

                entity.setPid(null); // 强制设为null，防止出现空字符串
                entity.setLevel(0);
                tempList.add(entity);
                resultList.add(entity);
            } else {
                childList.add(entity);
            }

            idMap.put(entity.getId(), entity);
        }

        Map<String, List<Dept>> pidMap = new HashMap<>(); // key: Dept.pid，进入pidMap的item一定有父节点
        for (Dept c : childList) {

            String pid = c.getPid();
            if (idMap.containsKey(pid)) {

                if (pidMap.containsKey(pid)) {
                    pidMap.get(pid).add(c);
                } else {
                    List<Dept> list = new ArrayList<>();
                    list.add(c);
                    pidMap.put(pid, list);
                }

            } else {
                // 处理孤儿节点，将其视为根节点
                logger.warn("Orphan dept detected! ID: {}, PID: {}", c.getId(), pid);

                c.setPid(null); // 强制设为null
                c.setLevel(0);
                tempList.add(c);
                resultList.add(c);
            }
        }

        while (pidMap.size() > 0) {
            List<Dept> pList = tempList;
            tempList = new ArrayList<>();

            for (Dept p : pList) {
                String pid = p.getId();
                if (pidMap.containsKey(pid)) {

                    for (Dept d : pidMap.get(pid)) {
                        d.setLevel(p.getLevel() + 1);
                        tempList.add(d);
                        resultList.add(d);
                    }

                    pidMap.remove(pid);
                    if (pidMap.size() == 0) {
                        break;
                    }
                }
            }
        }

        return resultList;
    }

    private Dept convertDepartmentBean(DepartmentBean bean, Map<String, Org> orgMap) {

        if (!orgMap.containsKey(bean.get_subcompanyid())) {
            return null;
        }
        Dept entity = new Dept();
        entity.setId(bean.get_departmentid());
        entity.setOrg(orgMap.get(bean.get_subcompanyid()));
        entity.setPid(bean.get_supdepartmentid());
        entity.setDeptCode(bean.get_code());
        entity.setDeptName(bean.get_fullname());
        entity.setShortName(bean.get_shortname());
        entity.setDeptOrder(CheckUtils.checkStringToInteger(bean.get_showorder()));
        entity.setIsDeleted(
                OnOffEnum.ON.value().toString().equals(bean.get_canceled()) ? OnOffEnum.ON.value()
                        : OnOffEnum.OFF.value());

        Date changeDate = new Date();
        try {
            if (!StringUtils.isEmpty(bean.getLastChangdate())) {
                changeDate = DateUtils.string2Date(bean.getLastChangdate(), Constants.DATE_FORMAT);
            }
        } catch (Exception e) {
            // logger.error("Exception parsing lastChangedate of DepartmentBean.", e);
        }
        entity.setcDatetime(changeDate);
        entity.setmDatetime(changeDate);
        entity.setCreator(Constants.ADMIN_USER_ID);

        return entity;
    }

    /**
     * 获取岗位。
     * 
     * @param ip OA的IP
     * @param port OA的端口
     * @return
     * @throws Exception
     */
    private List<JobTitleBean> fetchJobTitle(String ip, int port) throws Exception {

        HrmServicePortTypeProxy service =
                new HrmServicePortTypeProxy(String.format(ENDPOINT_TEMPLATE, ip, port));

        /**
         * subcompanyId 分部id
         * departmentId 部门id
         */
        JobTitleBean[] jobTitleArray =
                service.getHrmJobTitleInfo(String.format(IP_TEMPLATE, ip, port), null, null);

        if (jobTitleArray == null) {
            logger.error("Fetch JobTitle NULL!");
            return new ArrayList<>();
        }
        logger.info("Fetch JobTitle count: {}", jobTitleArray.length);
        return Arrays.asList(jobTitleArray);
    }

    /**
     * 转换岗位。
     * 
     * @param ip OA的IP
     * @param port OA的端口
     * @param deptMap 部门Map
     * @return
     * @throws Exception
     */
    public List<Post> listPost(String ip, int port, Map<String, Dept> deptMap) throws Exception {

        List<Post> resultList = new ArrayList<>();
        for (JobTitleBean bean : this.fetchJobTitle(ip, port)) {

            Post entity = this.convertJobTitleBean(bean, deptMap);
            if (entity == null) {
                logger.error("Dept not found for post with id {} and dept id {} !",
                        bean.get_jobtitleid(), bean.get_departmentid());
                continue;
            }

            resultList.add(entity);
        }

        return resultList;
    }

    private Post convertJobTitleBean(JobTitleBean bean, Map<String, Dept> deptMap) {

        Post entity = new Post();
        entity.setId(bean.get_jobtitleid());

        if (deptMap.containsKey(bean.get_departmentid())) {
            entity.setDept(deptMap.get(bean.get_departmentid()));
            entity.setOrg(deptMap.get(bean.get_departmentid()).getOrg());
        } else {
            entity.setDept(null);
            entity.setOrg(null);
        }

        entity.setPostName(bean.get_fullname());
        entity.setShortName(bean.get_shortname());

        entity.setResponsibility(bean.get_jobresponsibility());
        entity.setQualification(bean.get_jobcompetency());
        entity.setRelatedDoc(bean.get_jobdoc());
        entity.setRemark(bean.get_jobtitleremark());

        Date changeDate = new Date();
        try {
            if (!StringUtils.isEmpty(bean.get_lastChangdate())) {
                changeDate = DateUtils.string2Date(bean.get_lastChangdate(), Constants.DATE_FORMAT);
            }
        } catch (Exception e) {
            // logger.error("Exception parsing lastChangedate of JobTitleBean.", e);
        }
        entity.setcDatetime(changeDate);
        entity.setmDatetime(changeDate);
        entity.setCreator(Constants.ADMIN_USER_ID);

        return entity;
    }

    /**
     * 获取人员。
     * 
     * @param ip OA的IP
     * @param port OA的端口
     * @return
     * @throws Exception
     */
    private List<UserBean> fetchUser(String ip, int port) throws Exception {

        HrmServicePortTypeProxy service =
                new HrmServicePortTypeProxy(String.format(ENDPOINT_TEMPLATE, ip, port));

        /**
         * subcompanyId 分部id
         * departmentId 部门id
         * jobtitleId 岗位id
         * lastChangeDate 最后修改时间
         * workCode 人员编码
         */
        UserBean[] userArray = service.getHrmUserInfo(String.format(IP_TEMPLATE, ip, port), null,
                null, null, null, null);

        if (userArray == null) {
            logger.error("Fetch User NULL!");
            return new ArrayList<>();
        }
        logger.info("Fetch User count: {}", userArray.length);
        return Arrays.asList(userArray);
    }

    /**
     * 转换人员。
     * 
     * @param ip OA的IP
     * @param port OA的端口
     * @param deptMap 部门Map
     * @return
     * @throws Exception
     */
    public List<Person> listPerson(String ip, int port, Map<String, Dept> deptMap,
            Map<String, Post> postMap) throws Exception {

        List<Person> resultList = new ArrayList<>();
        for (UserBean bean : this.fetchUser(ip, port)) {

            Person entity = this.convertUserBean(bean, deptMap, postMap);
            if (entity == null) {
                logger.error("Dept not found for user with id {} and dept id {} !",
                        bean.getUserid(), bean.getDepartmentid());
                continue;
            }

            resultList.add(entity);
        }

        return resultList;
    }

    private Person convertUserBean(UserBean bean, Map<String, Dept> deptMap,
            Map<String, Post> postMap) {

        if (!deptMap.containsKey(bean.getDepartmentid())) {
            return null;
        }
        Person entity = new Person();
        entity.setAccountType(bean.getAccounttype());
        entity.setAssistantId(bean.getAssistantid());

        Date beMemberDate = null;
        try {
            if (!StringUtils.isEmpty(bean.getBememberdate())) {
                beMemberDate = DateUtils.string2Date(bean.getBememberdate(), Constants.DATE_FORMAT);
            }
        } catch (Exception e) {
            logger.error("Exception parsing beMemberDate of UserBean.", e);
        }
        entity.setBeMemberDate(beMemberDate);

        Date bePartyDate = null;
        try {
            if (!StringUtils.isEmpty(bean.getBepartydate())) {
                bePartyDate = DateUtils.string2Date(bean.getBepartydate(), Constants.DATE_FORMAT);
            }
        } catch (Exception e) {
            logger.error("Exception parsing bePartyDate of UserBean.", e);
        }
        entity.setBePartyDate(bePartyDate);

        Date birthDate = null;
        try {
            if (!StringUtils.isEmpty(bean.getBirthday())) {
                birthDate = DateUtils.string2Date(bean.getBirthday(), Constants.DATE_FORMAT);
            }
        } catch (Exception e) {
            logger.error("Exception parsing birthDate of UserBean.", e);
        }
        entity.setBirthday(birthDate);

        entity.setIdCardNum(bean.getCertificatenum());
        entity.setDegree(bean.getDegree());
        entity.setDept(deptMap.get(bean.getDepartmentid()));
        entity.setEducationRecord(bean.getEducationlevel());
        entity.setEmail(bean.getEmail());

        Date enddate = null;
        try {
            if (!StringUtils.isEmpty(bean.getEnddate())) {
                enddate = DateUtils.string2Date(bean.getEnddate(), Constants.DATE_FORMAT);
            }
        } catch (Exception e) {
            logger.error("Exception parsing enddate of UserBean.", e);
        }
        entity.setContractEndDate(enddate);

        entity.setFax(bean.getFax());
        entity.setEthnicGroup(bean.getFolk());
        entity.setHealthInfo(bean.getHealthinfo());

        Double height = null;
        try {
            if (!StringUtils.isEmpty(bean.getHeight())) {
                height = Double.parseDouble(bean.getHeight());
                if (height > 9999999 || height < 0) {
                    height = null;
                }
            }
        } catch (Exception e) {
            logger.error("Exception parsing height of UserBean.", e);
        }
        entity.setHeight(height);

        entity.setHomeAddress(bean.getHomeaddress());

        if (!StringUtils.isEmpty(bean.getIslabouunion())) {
            entity.setIsLabourUnion(OnOffEnum.ON.value().toString().equals(bean.getIslabouunion())
                    ? OnOffEnum.ON.value()
                    : OnOffEnum.OFF.value()); // TODO 待确认
        }

        entity.setJobTitle(bean.getJobactivityid());
        entity.setJobDesc(bean.getJobactivitydesc());
        entity.setJobCall(bean.getJobcall());
        entity.setJobGroup(bean.getJobgroupid());
        entity.setJobLevel(bean.getJoblevel());

        String jobtitle = bean.getJobtitle();
        if (!StringUtils.isEmpty(jobtitle)) {
            if (postMap.containsKey(jobtitle)) {
                entity.setPost(postMap.get(jobtitle));
            }
        }

        entity.setPersonName(bean.getLastname());
        entity.setLocation(bean.getLocationid());

        User user = new User();
        user.setLoginName(bean.getLoginid());

        entity.setManagerId(bean.getManagerid());

        if (!StringUtils.isEmpty(bean.getMaritalstatus())) {
            entity.setMaritalStatus("未婚".equals(bean.getMaritalstatus()) ? OnOffEnum.OFF.value()
                    : OnOffEnum.ON.value());
        }

        entity.setMobile(bean.getMobile());
        entity.setOtherPhone(bean.getMobilecall());
        entity.setNativePlace(bean.getNativeplace());

        user.setLoginPwd(bean.getPassword());

        entity.setPoliticalStatus(bean.getPolicy());
        entity.setRegResidence(bean.getRegresidentplace());
        entity.setSecurityLevel(bean.getSeclevel());

        if (!StringUtils.isEmpty(bean.getSex())) {
            entity.setGender(
                    "男".equals(bean.getSex()) ? OnOffEnum.ON.value() : OnOffEnum.OFF.value());
        }

        Date startdate = null;
        try {
            if (!StringUtils.isEmpty(bean.getStartdate())) {
                startdate = DateUtils.string2Date(bean.getStartdate(), Constants.DATE_FORMAT);
            }
        } catch (Exception e) {
            logger.error("Exception parsing startdate of UserBean.", e);
        }
        entity.setContractStartDate(startdate);

        if (!StringUtils.isEmpty(bean.getStatus())) {
            entity.setStatus(CheckUtils.checkStringToInteger(bean.getStatus())); // TODO 待确认
        }

        entity.setOrg(deptMap.get(bean.getDepartmentid()).getOrg());
        entity.setPhone(bean.getTelephone());
        entity.setTempResidentNum(bean.getTempresidentnumber());

        // User与Person的id完全一样
        entity.setId(bean.getUserid().toString());
        user.setId(entity.getId());

        Double weight = null;
        try {
            if (!StringUtils.isEmpty(bean.getWeight())) {
                weight = Double.parseDouble(bean.getWeight());
                if (weight > 9999999 || weight < 0) {
                    weight = null;
                }
            }
        } catch (Exception e) {
            logger.error("Exception parsing weitht of UserBean.", e);
        }
        entity.setWeight(weight);

        entity.setJobNum(bean.getWorkcode());
        entity.setWorkRoom(bean.getWorkroom());

        Date createdate = null;
        try {
            if (!StringUtils.isEmpty(bean.getCreatedate())) {
                createdate = DateUtils.string2Date(bean.getCreatedate(), Constants.DATE_FORMAT);
            }
        } catch (Exception e) {
            logger.error("Exception parsing createdate of UserBean.", e);
            createdate = new Date();
        }
        entity.setcDatetime(createdate);

        Date changeDate = null;
        try {
            if (!StringUtils.isEmpty(bean.getLastChangdate())) {
                changeDate = DateUtils.string2Date(bean.getLastChangdate(), Constants.DATE_FORMAT);
            }
        } catch (Exception e) {
            logger.error("Exception parsing lastChangedate of UserBean.", e);
            changeDate = new Date();
        }
        entity.setmDatetime(changeDate);
        entity.setCreator(Constants.ADMIN_USER_ID);

        user.setcDatetime(entity.getcDatetime());
        user.setCreator(entity.getCreator());
        user.setmDatetime(entity.getmDatetime());

//        Role r = new Role();
//        // r.setId(Constants.DB_NULL_VALUE);
//        r.setId("ADMIN_ROLE");
////        user.setRole(r);

        // 如果登录名为空，则分配UUID
        if (StringUtils.isEmpty(user.getLoginName())) {
            user.setLoginName(UUID.randomUUID().toString());
        }
        user.setPerson(entity);
        entity.setUser(user);

        return entity;
    }
}
