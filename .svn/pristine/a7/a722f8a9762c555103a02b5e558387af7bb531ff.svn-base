package project.edge.web.controller.archive;

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
import garage.origin.domain.view.JsonResultBean;
import garage.origin.domain.view.SessionUserBean;
import garage.origin.domain.view.converter.ViewConverter;
import garage.origin.service.Service;
import project.edge.common.SysLogAnnotation;
import project.edge.common.constant.DataTypeEnum;
import project.edge.domain.converter.PaperLibraryBeanConverter;
import project.edge.domain.entity.PaperLibrary;
import project.edge.domain.view.PaperLibraryBean;
import project.edge.service.archive.PaperLibraryService;
import project.edge.web.controller.common.TreeGridController;

/**
 * 纸质档案库
 * @author wd983
 *
 */
@Controller
@SessionAttributes(Constants.SESSION_USER)
@RequestMapping("/archive/paper-library")
public class PaperLibraryController extends TreeGridController<PaperLibrary, PaperLibraryBean> {

	private static final Logger logger = LoggerFactory.getLogger(PaperLibraryController.class);
    private static final String PAPERLIBRARY_VIEW_NAME = "archive/paperLibraryJs";
    private static final String PID = "P901001";
    
    @Resource
    private PaperLibraryService paperLibraryService;
    
	@Override
	protected Logger getLogger() {
		// TODO Auto-generated method stub
		return logger;
	}

	@Override
	public String getDataType() {
		// TODO Auto-generated method stub
		return DataTypeEnum.PAPER_LIBRARY.value();
	}

	@Override
	protected Service<PaperLibrary, String> getDataService() {
		// TODO Auto-generated method stub
		return this.paperLibraryService;
	}

	@Override
	protected String getPageId() {
		// TODO Auto-generated method stub
		return PID;
	}

	@Override
	protected ViewConverter<PaperLibrary, PaperLibraryBean> getViewConverter() {
		// TODO Auto-generated method stub
		return new PaperLibraryBeanConverter();
	}

//	@Override
//    protected String getMainView(Map<String, String> paramMap) {
//        return PAPERLIBRARY_VIEW_NAME;
//    }
	
//	@Override
//    protected Map<String, String> prepareJsMap(Map<String, String> idMap,
//            Map<String, String> urlMap) {
//        Map<String, String> jsMap = super.prepareJsMap(idMap, urlMap);
//
//        // 打开新建和修改画面时，增加回调，处理省市下拉框联动
//
//        // 打开新建
//        jsMap.put(ControllerJsMapKeys.OPEN_ADD,
//                String.format("CrudUtils.openAddFormDialog('#%1$s', PROJECT_SET);",
//                        idMap.get(ControllerIdMapKeys.ADD_FORM_DIALOG)));
//
//
//        return jsMap;
//    }


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
    @ResponseBody
    @SysLogAnnotation(description = "纸质档案库", action = "新增")
    public JsonResultBean create(@ModelAttribute PaperLibraryBean bean,
            @ModelAttribute(Constants.SESSION_USER) SessionUserBean userBean, Locale locale) {
        return super.create(bean, null, userBean, locale);
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
    @SysLogAnnotation(description = "纸质档案库", action = "更新")
    public JsonResultBean update(@ModelAttribute PaperLibraryBean bean,
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
    @RequestMapping("/delete")
    @ResponseBody
    @SysLogAnnotation(description = "纸质档案库", action = "删除")
    public JsonResultBean delete(@RequestParam String idsToDelete,
            @ModelAttribute(Constants.SESSION_USER) SessionUserBean userBean, Locale locale) {
        return super.delete(idsToDelete, null, userBean, locale);
    }

}
