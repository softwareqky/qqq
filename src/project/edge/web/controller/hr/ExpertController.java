/**
 * 
 */
package project.edge.web.controller.hr;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.SessionAttributes;

import garage.origin.common.constant.Constants;
import garage.origin.common.constant.GenderEnum;
import garage.origin.common.constant.OnOffEnum;
import garage.origin.domain.filter.CommonFilter;
import garage.origin.domain.view.ComboboxOptionBean;
import garage.origin.domain.view.JsonResultBean;
import garage.origin.domain.view.SessionUserBean;
import garage.origin.domain.view.converter.ViewConverter;
import garage.origin.service.Service;
import project.edge.common.SysLogAnnotation;
import project.edge.common.constant.DataTypeEnum;
import project.edge.domain.converter.ExpertBeanConverter;
import project.edge.domain.entity.Archive;
import project.edge.domain.entity.DataOption;
import project.edge.domain.entity.Expert;
import project.edge.domain.entity.ExpertAttachment;
import project.edge.domain.view.ArchiveBean;
import project.edge.domain.view.ExpertBean;
import project.edge.service.hr.ExpertService;
import project.edge.service.system.DataOptionService;
import project.edge.web.controller.common.SingleGridUploadController;


/**
 * 专家管理画面。
 *
 */
@Controller
@SessionAttributes(Constants.SESSION_USER)
@RequestMapping("/hr/expert")
public class ExpertController extends SingleGridUploadController<Expert, ExpertBean> {

    private static final Logger logger = LoggerFactory.getLogger(ExpertController.class);

    private static final String PID = "P17010";

    @Resource
    private ExpertService expertService;

    @Resource
    private DataOptionService dataOptionService;

    @Override
    protected Logger getLogger() {
        return logger;
    }

    @Override
    public String getDataType() {
        return DataTypeEnum.EXPERT.value();
    }

    @Override
    protected String getPageId() {
        return PID;
    }

    @Override
    protected Service<Expert, String> getDataService() {
        return this.expertService;
    }

    @Override
    protected ViewConverter<Expert, ExpertBean> getViewConverter() {
        return new ExpertBeanConverter();
    }


    /**
     * 获取画面的各个下拉列表项，用于新增/修改/检索/批量修改画面。有下拉列表的子类必须重写此方法。
     * 
     * @param locale
     * @return key:[v_data_option].option_source，value:[v_data_option]
     */
    @Override
    protected Map<String, List<ComboboxOptionBean>> prepareOptionMap(Locale locale) {

        Map<String, List<ComboboxOptionBean>> optionMap =
                new HashMap<String, List<ComboboxOptionBean>>();
        // 是否内部专家
        ArrayList<ComboboxOptionBean> onOffMode = new ArrayList<ComboboxOptionBean>();
        for (OnOffEnum onoffEnum : OnOffEnum.values()) {
            onOffMode.add(new ComboboxOptionBean(onoffEnum.value().toString(),
                    messageSource.getMessage(onoffEnum.resourceName(), null, locale)));
        }
        optionMap.put("OnOff", onOffMode);
        // 性别
        ArrayList<ComboboxOptionBean> genderMode = new ArrayList<ComboboxOptionBean>();

        for (GenderEnum genderEnum : GenderEnum.values()) {
            genderMode.add(new ComboboxOptionBean(genderEnum.value().toString(),
                    messageSource.getMessage(genderEnum.resourceName(), null, locale)));
        }
        optionMap.put("genderMode", genderMode);
        // 专家类型
        ArrayList<ComboboxOptionBean> expertDomainOption = new ArrayList<ComboboxOptionBean>();
        CommonFilter f = new CommonFilter().addExact("dataType", "EXPERT_DOMAIN");
        List<DataOption> list = this.dataOptionService.list(f, null);
        for (DataOption o : list) {
            expertDomainOption.add(new ComboboxOptionBean(o.getId(), o.getOptionName()));
        }
        optionMap.put("expertTypeOptions", expertDomainOption);
        return optionMap;



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

        return super.main(paramMap, model, userBean, locale);
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

        JsonResultBean jsonResult =
                super.list(commonFilterJson, null, page, rows, sort, order, locale);

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
    @SysLogAnnotation(description = "专家管理", action = "新增")
    public void create(HttpServletRequest request, HttpServletResponse response,
            @ModelAttribute ExpertBean bean,
            @ModelAttribute(Constants.SESSION_USER) SessionUserBean userBean, Locale locale) {

        // 也可以省去uploadType参数，直接用bean内的成员变量，这里是为了显示的说明用法
        // 参数在projectInitHidden.jsp中用GET的方式设定

        Map<String, List<ArchiveBean>> fieldNameArchiveListMap = new HashMap<>();
        fieldNameArchiveListMap.put("archives_", bean.getArchivesList());
        // 实际进度的文件，位于/plan-progress/id文件夹内，id是项目组的主键值
        super.createWithUpload(request, response, bean, null, fieldNameArchiveListMap, userBean,
                locale);



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
    @SysLogAnnotation(description = "专家管理", action = "更新")
    public void update(HttpServletRequest request, HttpServletResponse response,
            @ModelAttribute ExpertBean bean,
            @RequestParam(required = false, defaultValue = "") String uploadType,
            @ModelAttribute(Constants.SESSION_USER) SessionUserBean userBean, Locale locale) {

        // 也可以省去uploadType参数，直接用bean内的成员变量，这里是为了显示的说明用法
        // 参数在projectInitHidden.jsp中用GET的方式设定

        Map<String, List<ArchiveBean>> fieldNameArchiveListMap = new HashMap<>();
        fieldNameArchiveListMap.put("archives_", bean.getArchivesList());

        // 项目的文件，位于/project/id文件夹内，id是项目的主键值
        super.updateWithUpload(request, response, bean, fieldNameArchiveListMap, userBean, locale);
    }


    @Override
    protected void postUpdate(Expert entity, Expert oldEntity, ExpertBean bean,
            java.util.Map<String, Object> paramMap) throws IOException {
        super.postUpdate(entity, oldEntity, bean, paramMap);

        List<Archive> list = new ArrayList<>();
        for (ExpertAttachment attachment : entity.getAttachmentsToDelete()) {
            list.add(attachment.getArchive());
        }
        this.deleteArchiveFiles(list);
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
    @SysLogAnnotation(description = "专家管理", action = "删除")
    public JsonResultBean delete(@RequestParam String idsToDelete,
            @ModelAttribute(Constants.SESSION_USER) SessionUserBean userBean, Locale locale) {
        return super.delete(idsToDelete, null, userBean, locale);
    }
}
