package project.edge.web.controller.archive;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.SessionAttributes;

import garage.origin.common.constant.Constants;
import garage.origin.common.constant.JsonStatus;
import garage.origin.common.constant.OnOffEnum;
import garage.origin.domain.business.FilterFieldInfoDto;
import garage.origin.domain.business.OrderByDto;
import garage.origin.domain.filter.CommonFilter;
import garage.origin.domain.view.ComboboxOptionBean;
import garage.origin.domain.view.JsonResultBean;
import garage.origin.domain.view.SessionUserBean;
import garage.origin.domain.view.converter.ViewConverter;
import garage.origin.service.Service;
import project.edge.common.SysLogAnnotation;
import project.edge.common.constant.AuthTypeEnum;
import project.edge.common.constant.ControllerJsMapKeys;
import project.edge.common.constant.ControllerModelKeys;
import project.edge.common.constant.DataTypeEnum;
import project.edge.domain.converter.ArchiveAuthorityBeanConverter;
import project.edge.domain.entity.Archive;
import project.edge.domain.entity.ArchiveAuthority;
import project.edge.domain.entity.Person;
import project.edge.domain.view.ArchiveAuthorityBean;
import project.edge.service.archive.ArchiveAuthorityService;
import project.edge.service.archive.ArchiveService;
import project.edge.web.controller.common.TreeGridController;

/**
 * 档案权限画面。
 *
 */
@Controller
@SessionAttributes(Constants.SESSION_USER)
@RequestMapping("/archive/archive-auth")
public class ArchiveAuthorityController
        extends TreeGridController<ArchiveAuthority, ArchiveAuthorityBean> {

    private static final Logger logger = LoggerFactory.getLogger(ArchiveAuthorityController.class);

    private static final String PID = "P9002";

    @Resource
    private ArchiveAuthorityService archiveAuthorityService;

    @Resource
    private ArchiveService archiveService;

    @Override
    protected Logger getLogger() {
        return logger;
    }

    @Override
    public String getDataType() {
        return DataTypeEnum.ARCHIVE_AUTHORITY.value();
    }

    @Override
    protected Service<ArchiveAuthority, String> getDataService() {
        return this.archiveAuthorityService;
    }

    @Override
    protected String getPageId() {
        return PID;
    }

    @Override
    protected ViewConverter<ArchiveAuthority, ArchiveAuthorityBean> getViewConverter() {
        return new ArchiveAuthorityBeanConverter();
    }

    @Override
    protected String getJsCallbackObjName() {
        return this.getDataType();
    }

    @Override
    protected String getJavascriptJspPath() {
        return "/WEB-INF/jsp/archive/archiveAuthorityJs.jsp";
    }

    @Override
    protected String getSideDataType() {
        return DataTypeEnum.ARCHIVE.value();
    }

    @Override
    protected Map<String, List<ComboboxOptionBean>> prepareOptionMap(Locale locale) {

        Map<String, List<ComboboxOptionBean>> optionMap = new HashMap<>();

        // 权限
        List<ComboboxOptionBean> authOptions = new ArrayList<>();
        for (AuthTypeEnum authTypeEnum : AuthTypeEnum.values()) {
            authOptions.add(new ComboboxOptionBean(authTypeEnum.value().toString(),
                    this.messageSource.getMessage(authTypeEnum.resourceName(), null, locale)));
        }
        optionMap.put("authType", authOptions);

        return optionMap;
    }

    @Override
    protected Map<String, String> prepareJsMap(Map<String, String> idMap,
            Map<String, String> urlMap) {

        Map<String, String> jsMap = super.prepareJsMap(idMap, urlMap);

        // 打开新建，弹出人员通用选择画面
        jsMap.put(ControllerJsMapKeys.OPEN_ADD,
                "PopupSelectUtils.openPersonDblGridSelect(" + this.getJsCallbackObjName() + ")");

        return jsMap;
    }

    /**
     * 画面Open的入口方法，用于生成JSP。
     * 
     * @param paramMap 画面请求中的任何参数，都会成为检索的字段
     * @param model model
     * @param userBean session中的当前登录的用户信息
     * @param locale locale
     * @return
     */
    @RequestMapping("/main")
    public String main(@RequestParam Map<String, String> paramMap, Model model,
            @ModelAttribute(Constants.SESSION_USER) SessionUserBean userBean, Locale locale) {

        String r = super.main(paramMap, model, userBean, locale);

        // 不使用分页
        model.addAttribute(ControllerModelKeys.IS_PAGE, false);

        // 与[t_data_fields]对应，默认按姓名排序，但与实际的排序字段名是不同的，姓名实际排序字段名是person.personName
        model.addAttribute(ControllerModelKeys.DEFAULT_ORDER, new OrderByDto("personText"));

        // Datagrid行选择的事件，除了默认的逻辑，还需要特别处理删除按钮，当至少选中一条记录，且没有选中继承权限的记录时，才启用该按钮
        model.addAttribute(ControllerModelKeys.SELECT_HANDLER, "ARCHIVE_AUTHORITY.handleSelect");
        model.addAttribute(ControllerModelKeys.CLICK_CELL_HANDLER, "ARCHIVE_AUTHORITY.clickCellHandler");

        // Datagrid行双击事件，增加回调
        model.addAttribute(ControllerModelKeys.DBL_CLICK_ROW_HANDLER,
                "ARCHIVE_AUTHORITY.handleDblClickRow");

        return r;
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
    @RequestMapping("/list")
    @ResponseBody
    public JsonResultBean list(
            @RequestParam(required = false, defaultValue = "") String commonFilterJson,
            @RequestParam(required = false, defaultValue = "1") int page,
            @RequestParam(required = false, defaultValue = "999999999") int rows,
            @RequestParam(required = false, defaultValue = "") String sort,
            @RequestParam(required = false, defaultValue = "asc") String order, Locale locale) {

        JsonResultBean jsonResult = new JsonResultBean();
        try {

            List<ArchiveAuthorityBean> resultList = new ArrayList<ArchiveAuthorityBean>();

            // 指定的档案id
            String targetArchiveId = null;

            // 设置过滤信息
            CommonFilter filter = new CommonFilter();

            CommonFilter paramfilter = this.generateCommonFilter(null, commonFilterJson);
            if (paramfilter != null) {
                for (FilterFieldInfoDto f : paramfilter.getFilterFieldList()) {
                    if ("person.id".equals(f.getFieldName())) {
                        f.setFieldName("person.personName");
                        filter.getFilterFieldList().add(f);
                    } else if ("user".equals(f.getFieldName())) {
                        f.setFieldName("puser.loginName");
                        filter.getFilterFieldList().add(f);
                    } else if ("archive.id".equals(f.getFieldName())) {
                        targetArchiveId = f.getValue().toString(); // 特别处理
                    }
                }
            }

            if (StringUtils.isEmpty(targetArchiveId)) {
                jsonResult.setStatus(JsonStatus.OK);
                jsonResult.getDataMap().put(JsonResultBean.KEY_EASYUI_TOTAL, 0);
                jsonResult.getDataMap().put(JsonResultBean.KEY_EASYUI_ROWS, resultList);
                return jsonResult;
            }

            // 从指定的档案开始，一直到根节点，所有这些记录关联的权限都查询出来，用来计算权限的继承
            Archive archive = this.archiveService.find(targetArchiveId);
            List<String> aidList = new ArrayList<>(); // list内aid的顺序是，从指定的档案开始，一路向根节点
            aidList.add(targetArchiveId);
            String path = archive.getPath();
            if (!StringUtils.isEmpty(path)) {
                String[] idArray = StringUtils.commaDelimitedListToStringArray(path);
                for (int i = idArray.length - 1; i >= 0; i--) {
                    aidList.add(idArray[i]);
                }
            }
            filter.addWithin("archive.id", aidList);

            // 设置排序信息
            boolean isPersonNameOrder = false;
            List<OrderByDto> orders = new ArrayList<OrderByDto>();
            if (!StringUtils.isEmpty(sort)) {
                String[] orderArray = StringUtils.commaDelimitedListToStringArray(order);
                String[] sortArray = StringUtils.commaDelimitedListToStringArray(sort);
                for (int i = 0; i < orderArray.length; i++) {

                    String beanSort = sortArray[i];
                    if ("personText".equals(beanSort)) {
                        beanSort = "person.personName";
                        isPersonNameOrder = true;

                        // 暂时忽略对权限字段的排序
                        // } else if ("user".equals(beanSort)) {
                    } else {
                        beanSort = "puser.loginName";
                    }
                    orders.add(new OrderByDto(beanSort,
                            orderArray[i].equalsIgnoreCase(OrderByDto.DESC)));
                }
            }
            if (!isPersonNameOrder) {
                orders.add(new OrderByDto("person.personName")); // 默认按姓名排序
            }

            // 从指点的档案开始，一直到根节点，所有设定的权限都查询出来
            List<ArchiveAuthority> list = this.archiveAuthorityService.list(filter, orders);

            // 计算权限
            Map<String, Map<String, ArchiveAuthorityBean>> map = new LinkedHashMap<>(); // key:Person.id
            for (ArchiveAuthority auth : list) {

                String personId = auth.getPerson().getId();
                Map<String, ArchiveAuthorityBean> subMap = null; // key:Archive.id

                if (map.containsKey(personId)) {
                    subMap = map.get(personId);
                } else {
                    subMap = new HashMap<>();
                    map.put(personId, subMap);
                }

                String keyArchiveId = auth.getArchive().getId();
                subMap.put(keyArchiveId,
                        this.getViewConverter().fromEntity(auth, this.messageSource, locale));
            }

            // 用来辅助生成"继承自"(inheritFrom)字段的值，去重
            Map<String, String> pathArchiveIdMap = new HashMap<>(); // key和value都是Archive.id

            for (String personId : map.keySet()) {
                Map<String, ArchiveAuthorityBean> subMap = map.get(personId);
                for (String aid : aidList) {
                    if (subMap.containsKey(aid)) {

                        ArchiveAuthorityBean bean = subMap.get(aid);

                        // 如果是目标档案设定了权限，则不是继承，否则是继承的权限
                        short isInherit = targetArchiveId.equals(aid) ? OnOffEnum.OFF.value()
                                : OnOffEnum.ON.value();
                        bean.setIsInherit(isInherit);

                        for (String pathAid : bean.getPathList()) {
                            pathArchiveIdMap.put(pathAid, pathAid);
                        }

                        resultList.add(bean);

                        // 离目标档案最近的一个档案权限，作为继承权限
                        break;
                    }
                }
            }

            CommonFilter pathArchiveFilter =
                    new CommonFilter().addWithin("id", pathArchiveIdMap.values());
            List<Archive> pathArchiveList = this.archiveService.list(pathArchiveFilter, null);
            Map<String, Archive> pathArchiveMap = new HashMap<>();// key: Archive.id
            for (Archive pathArchive : pathArchiveList) {
                pathArchiveMap.put(pathArchive.getId(), pathArchive);
            }

            // 生成"继承自"(inheritFrom)字段的值
            for (ArchiveAuthorityBean bean : resultList) {
                if (OnOffEnum.ON.value() == bean.getIsInherit()) {
                    StringBuilder inheritFrom = new StringBuilder();
                    for (String aid : bean.getPathList()) {
                        Archive a = pathArchiveMap.get(aid);
                        inheritFrom.append(a.getArchiveName());

                        // 如果不是根节点，则加上分隔符/
                        if (a.getLevel() > 0) {
                            inheritFrom.append(Constants.SLASH);
                        }
                    }
                    inheritFrom.append(bean.getArchiveText());
                    bean.setInheritFrom(inheritFrom.toString());
                }
            }

            long total = resultList.size();

            // 准备JSON结果
            jsonResult.setStatus(JsonStatus.OK);
            jsonResult.getDataMap().put(JsonResultBean.KEY_EASYUI_TOTAL, total);// easyui-datagrid分页用
            jsonResult.getDataMap().put(JsonResultBean.KEY_EASYUI_ROWS, resultList);// easyui-datagrid分页用

        } catch (Exception e) {
            this.getLogger().error("Exception listing the " + this.getDataType(), e);

            // 在JSON对象内设定服务器处理结果状态
            jsonResult.setStatus(JsonStatus.ERROR);
        }

        return jsonResult;
    }

    /**
     * 查找单条记录，返回Json格式。
     * 
     * @param id ID
     * @param locale
     * @return
     */
    @RequestMapping("/find")
    @ResponseBody
    @Override
    public JsonResultBean find(@RequestParam String id, Locale locale) {

        return super.find(id, locale);
    }

    /**
     * 新建，返回Json格式。
     * 
     * @param bean 表现层对象
     * @param userBean Session中的用户信息
     * @param locale
     * @return
     */
    @RequestMapping("/add")
    @ResponseBody
    @SysLogAnnotation(description = "档案权限", action = "新增")
    public JsonResultBean create(@RequestParam(required = true) String archive_,
            @RequestParam(required = true) List<String> personList,
            @ModelAttribute(Constants.SESSION_USER) SessionUserBean userBean, Locale locale) {

        JsonResultBean jsonResult = new JsonResultBean();
        try {

            Archive archive = this.archiveService.find(archive_);
            if (OnOffEnum.ON.value() == archive.getIsDeleted() || archive.getVersionOf() != null) { // 不对已删除或历史版本进行权限设置
                String msg = this.messageSource.getMessage("message.error.record.not.exist", null,
                        locale);
                jsonResult.setStatus(JsonStatus.ERROR);
                jsonResult.setMessage(msg);

                return jsonResult;
            }

            CommonFilter filter = new CommonFilter().addExact("archive.id", archive_);
            List<ArchiveAuthority> dbList = this.archiveAuthorityService.list(filter, null);
            Map<String, ArchiveAuthority> map = new HashMap<>(); // key: person_id
            for (ArchiveAuthority dbAuth : dbList) {
                map.put(dbAuth.getPerson().getId(), dbAuth);
            }

            // 数据库是否已存在记录
            boolean hasEntity = false;

            Date now = new Date();
            List<ArchiveAuthority> listToCreate = new ArrayList<>();
            for (String person : personList) {
                if (map.containsKey(person)) {
                    hasEntity = true;
                    continue;
                }

                ArchiveAuthority entity = new ArchiveAuthority();

                Archive a = new Archive();
                a.setId(archive_);
                entity.setArchive(a);

                Person p = new Person();
                p.setId(person);
                entity.setPerson(p);

                entity.setAuthType(AuthTypeEnum.READ_ONLY.value());

                entity.setcDatetime(now);
                entity.setCreator(userBean.getSessionUserId());
                entity.setmDatetime(now);

                listToCreate.add(entity);
            }

            this.archiveAuthorityService.batchCreate(listToCreate);

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
    @RequestMapping("/edit")
    @ResponseBody
    @SysLogAnnotation(description = "档案权限", action = "更新")
    public JsonResultBean update(@ModelAttribute ArchiveAuthorityBean bean,
            @ModelAttribute(Constants.SESSION_USER) SessionUserBean userBean, Locale locale) {

        ArchiveAuthority entity = this.archiveAuthorityService.find(bean.getId());
        bean.setPerson_(entity.getPerson().getId()); // Person的关联保持不变

        if (entity.getArchive().getId().equals(bean.getArchive_())) {
            // 非继承，直接修改
            return super.update(bean, null, userBean, locale);
        }

        // 对于继承的权限，修改相当于新建
        return super.create(bean, null, userBean, locale);
    }

    /**
     * 删除，返回Json格式。
     * 
     * @param idsToDelete 待删除的ID，CSV
     * @param userBean Session中的用户信息
     * @param locale
     * @return
     */
    @RequestMapping("/delete")
    @ResponseBody
    @SysLogAnnotation(description = "档案权限", action = "删除")
    public JsonResultBean delete(@RequestParam String idsToDelete,
            @ModelAttribute(Constants.SESSION_USER) SessionUserBean userBean, Locale locale) {
        return super.delete(idsToDelete, null, userBean, locale);
    }

}
