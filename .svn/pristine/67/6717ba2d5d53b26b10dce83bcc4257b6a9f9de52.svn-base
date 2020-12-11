package project.edge.web.controller.archive;

import java.util.List;
import java.util.Locale;
import java.util.Map;

import javax.annotation.Resource;

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
import garage.origin.common.constant.OnOffEnum;
import garage.origin.domain.business.OrderByDto;
import garage.origin.domain.filter.CommonFilter;
import garage.origin.domain.view.JsonResultBean;
import garage.origin.domain.view.SessionUserBean;
import garage.origin.domain.view.converter.ViewConverter;
import garage.origin.service.Service;
import project.edge.common.constant.ControllerModelKeys;
import project.edge.common.constant.ControllerUrlMapKeys;
import project.edge.common.constant.DataTypeEnum;
import project.edge.domain.converter.ArchiveVersionBeanConverter;
import project.edge.domain.entity.Archive;
import project.edge.domain.view.ArchiveVersionBean;
import project.edge.service.archive.ArchiveService;
import project.edge.web.controller.common.SingleGridController;

/**
 * 档案版本画面。
 *
 */
@Controller
@SessionAttributes(Constants.SESSION_USER)
@RequestMapping("/archive/archive-version")
public class ArchiveVersionController extends SingleGridController<Archive, ArchiveVersionBean> {

    private static final Logger logger = LoggerFactory.getLogger(ArchiveVersionController.class);

    private static final String PID = "P9003";

    @Resource
    private ArchiveService archiveService;

    @Override
    protected Logger getLogger() {
        return logger;
    }

    @Override
    public String getDataType() {
        return DataTypeEnum.ARCHIVE_VERSION.value();
    }

    @Override
    protected Service<Archive, String> getDataService() {
        return this.archiveService;
    }

    @Override
    protected String getPageId() {
        return PID;
    }

    @Override
    protected OrderByDto getGridDefaultOrder() {
        return new OrderByDto("mDatetime", true);
    }

    @Override
    protected ViewConverter<Archive, ArchiveVersionBean> getViewConverter() {
        return new ArchiveVersionBeanConverter();
    }

    @SuppressWarnings("unchecked")
    @RequestMapping("/main")
    public String main(@RequestParam Map<String, String> paramMap,
            @RequestParam(required = true) String id, Model model,
            @ModelAttribute(Constants.SESSION_USER) SessionUserBean userBean, Locale locale) {

        String r = super.main(paramMap, model, userBean, locale);

        // 不使用分页
        model.addAttribute(ControllerModelKeys.IS_PAGE, false);

        // 单选
        model.addAttribute(ControllerModelKeys.IS_SINGLE_SELECT, true);

        // 自动行高
        model.addAttribute("autoRowHeight", true);



        // 对list.json，添加query param
        Map<String, Object> modelMap = model.asMap();
        Map<String, String> urlMap =
                (Map<String, String>) modelMap.get(ControllerModelKeys.URL_MAP);

        String contextPath = this.context.getContextPath();
        String listUrl = contextPath + "/archive/archive-version/list.json?id=" + id;
        urlMap.put(ControllerUrlMapKeys.LIST, listUrl);

        return r;
    }

    @RequestMapping("/list")
    @ResponseBody
    public JsonResultBean list(
            @RequestParam(required = false, defaultValue = "") String commonFilterJson,
            @RequestParam(required = true) String id,
            @RequestParam(required = false, defaultValue = "1") int page,
            @RequestParam(required = false, defaultValue = "999999999") int rows,
            @RequestParam(required = false, defaultValue = "") String sort,
            @RequestParam(required = false, defaultValue = "asc") String order, Locale locale) {

        CommonFilter filter = new CommonFilter().addExact("versionOf", id);
        JsonResultBean jsonResult =
                super.list(commonFilterJson, filter, page, rows, sort, order, locale);

        @SuppressWarnings("unchecked")
        List<ArchiveVersionBean> list = (List<ArchiveVersionBean>) jsonResult.getDataMap()
                .get(JsonResultBean.KEY_EASYUI_ROWS);

        Archive entity = this.archiveService.find(id);
        ArchiveVersionBean bean =
                this.getViewConverter().fromEntity(entity, this.messageSource, locale);
        bean.setIsCurrent(OnOffEnum.ON.value());

        list.add(0, bean);

        return jsonResult;
    }

}
