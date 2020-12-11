package project.edge.web.controller.project;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.SessionAttributes;

import garage.origin.common.constant.Constants;
import garage.origin.common.constant.JsonStatus;
import garage.origin.common.constant.OnOffEnum;
import garage.origin.domain.filter.CommonFilter;
import garage.origin.domain.view.ComboboxOptionBean;
import garage.origin.domain.view.JsonResultBean;
import garage.origin.domain.view.SessionUserBean;
import garage.origin.domain.view.converter.ViewConverter;
import garage.origin.service.Service;
import project.edge.common.constant.DataTypeEnum;
import project.edge.domain.converter.ProjectPersonBeanConverter;
import project.edge.domain.entity.ProjectPerson;
import project.edge.domain.entity.ProjectRole;
import project.edge.domain.entity.VirtualOrg;
import project.edge.domain.view.ProjectPersonBean;
import project.edge.service.project.ProjectPersonService;
import project.edge.service.project.ProjectRoleService;
import project.edge.service.project.VirtualOrgService;
import project.edge.web.controller.common.SingleGridController;
import project.edge.web.controller.general.DataPermissionService;

/**
 * 项目成员。从属于项目成员变更画面，作为项目成员变更画面的SubGrid。不需要main()来渲染画面。
 *
 */
@Controller
@SessionAttributes(Constants.SESSION_USER)
@RequestMapping("/project/project-person")
public class ProjectPersonController
        extends SingleGridController<ProjectPerson, ProjectPersonBean> {

    private static final Logger logger = LoggerFactory.getLogger(ProjectPersonController.class);
    
    private static final String PID = "P2061";

    @Resource
    private ProjectPersonService projectPersonService;

    @Resource
    private ProjectRoleService projectRoleService;

    @Resource
    private VirtualOrgService virtualOrgService;
	
	@Resource
	private DataPermissionService dataPermissionService;

    @Override
    protected Logger getLogger() {
        return logger;
    }

    @Override
    public String getDataType() {
        return DataTypeEnum.PROJECT_PERSON.value();
    }

    @Override
    protected String getPageId() {
        return PID;
    }

    @Override
    protected Service<ProjectPerson, String> getDataService() {
        return this.projectPersonService;
    }

    @Override
    protected ViewConverter<ProjectPerson, ProjectPersonBean> getViewConverter() {
        return new ProjectPersonBeanConverter();
    }

    /**
     * 获取一览显示的数据，返回JSON格式。
     * 
     * @param commonFilterJson JSON字符串形式的过滤条件
     * @param page 页数
     * @param rows 每页的记录数
     * @param sort 排序的字段，CSV
     * @param order 顺序，CSV
     * @param locale
     * @return
     */
    @RequestMapping("/sub-grid-list")
    @ResponseBody
    public JsonResultBean list(
            @RequestParam(required = false, defaultValue = "") String commonFilterJson,
            @RequestParam(required = false, defaultValue = "1") int page,
            @RequestParam(required = false, defaultValue = "999999999") int rows,
            @RequestParam(required = false, defaultValue = "") String sort,
			@ModelAttribute(Constants.SESSION_USER) SessionUserBean userBean,
            @RequestParam(required = false, defaultValue = "asc") String order, Locale locale) {

    	commonFilterJson = dataPermissionService.editFilterBySelfVirtualOrg(userBean, commonFilterJson);
    	CommonFilter f = new CommonFilter().addExact("person.isDeleted", OnOffEnum.OFF.value());
    	
        return super.list(commonFilterJson, f, page, rows, sort, order, locale);
    }

    /**
     * 查找单条记录，返回Json格式。
     * 
     * @param id ID
     * @param locale
     * @return
     */
    @RequestMapping("/sub-grid-find")
    @ResponseBody
    @Override
    public JsonResultBean find(@RequestParam String id, Locale locale) {

        JsonResultBean jsonResult = super.find(id, locale);
        try {
            ProjectPersonBean bean = (ProjectPersonBean) jsonResult.getDataMap()
                    .get(JsonResultBean.KEY_RETURN_OBJECT);

            bean.setPerson_(bean.getPersonText());

            // 获取下拉框的option，以便修改弹出画面上设置

            // 设置过滤信息
            CommonFilter filter = new CommonFilter().addExact("project.id", bean.getProject_());

            // 获取数据
            List<VirtualOrg> list = this.virtualOrgService.list(filter, null);
            List<ComboboxOptionBean> resultList = new ArrayList<>();

            for (VirtualOrg entity : list) {
                resultList.add(new ComboboxOptionBean(entity.getId(), entity.getVirtualOrgName()));
            }
            jsonResult.getDataMap().put("vorgs", resultList);

            // 设置过滤信息
            CommonFilter f = new CommonFilter().addExact("isDeleted", OnOffEnum.OFF.value());

            // 获取数据
            List<ProjectRole> rolelist = this.projectRoleService.list(f, null);
            List<ComboboxOptionBean> roleResultList = new ArrayList<>();

            for (ProjectRole entity : rolelist) {
                roleResultList
                        .add(new ComboboxOptionBean(entity.getId(), entity.getProjectRoleName()));
            }
            jsonResult.getDataMap().put("roles", roleResultList);


        } catch (Exception e) {
            this.getLogger().error("Exception finding the " + this.getDataType(), e);

            // 在JSON对象内设定服务器处理结果状态
            jsonResult.setStatus(JsonStatus.ERROR);
        }

        return jsonResult;
    }

    /**
     * 新建，返回Json格式。
     * 
     * @param bean 表现层对象
     * @param userBean Session中的用户信息
     * @param locale
     * @return
     */
    @RequestMapping("/sub-grid-add")
    @ResponseBody
    public JsonResultBean create(@ModelAttribute ProjectPersonBean bean,
            @RequestParam(required = true) List<String> personList,
            @ModelAttribute(Constants.SESSION_USER) SessionUserBean userBean, Locale locale) {

        JsonResultBean jsonResult = new JsonResultBean();
        try {

            CommonFilter filter = new CommonFilter().addWithin("person.id", personList)
                    .addExact("project.id", bean.getProject_());
            List<ProjectPerson> dbList = this.projectPersonService.list(filter, null);
            Map<String, ProjectPerson> map = new HashMap<>(); // key: person_id
            for (ProjectPerson dbItem : dbList) {
                map.put(dbItem.getPerson().getId(), dbItem);
            }

            // 数据库是否已存在记录
            boolean hasEntity = false;

            Date now = new Date();
            List<ProjectPerson> listToCreate = new ArrayList<>();
            for (String person : personList) {
                if (map.containsKey(person)) {
                    hasEntity = true;
                    continue;
                }

                bean.setPerson_(person);
                ProjectPerson entity = this.getViewConverter().toEntity(bean, null, userBean, now);

                listToCreate.add(entity);
            }

            this.projectPersonService.batchCreate(listToCreate);

            String msg = this.messageSource.getMessage("message.info.record.add.ok", null, locale);
            if (hasEntity) {
                msg += this.messageSource.getMessage("message.info.skip.same.record", null, locale);
            }

            // 准备JSON结果
            jsonResult.setStatus(JsonStatus.OK);
            jsonResult.setMessage(msg);

        } catch (Exception e) {
            this.getLogger().error("Exception creating the " + this.getDataType(), e);

            // 在JSON对象内设定服务器处理结果状态
            jsonResult.setStatus(JsonStatus.ERROR);
        }
        return jsonResult;
    }

    /**
     * 修改，返回Json格式。
     * 
     * @param bean 表现层对象
     * @param userBean Session中的用户信息
     * @param locale
     * @return
     */
    @RequestMapping("/sub-grid-edit")
    @ResponseBody
    public JsonResultBean update(@ModelAttribute ProjectPersonBean bean,
            @ModelAttribute(Constants.SESSION_USER) SessionUserBean userBean, Locale locale) {
        return super.update(bean, null, userBean, locale);
    }

    /**
     * 删除，返回Json格式。
     * 
     * @param idsToDelete 待删除的ID，CSV
     * @param userBean Session中的用户信息
     * @param locale
     * @return
     */
    @RequestMapping("/sub-grid-delete")
    @ResponseBody
    public JsonResultBean delete(@RequestParam String idsToDelete,
            @ModelAttribute(Constants.SESSION_USER) SessionUserBean userBean, Locale locale) {
        return super.delete(idsToDelete, null, userBean, locale);
    }

}
