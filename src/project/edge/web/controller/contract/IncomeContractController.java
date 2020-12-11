package project.edge.web.controller.contract;

import java.io.IOException;
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
import project.edge.domain.converter.IncomeContractBeanConverter;
import project.edge.domain.entity.IncomeContract;
import project.edge.domain.view.ArchiveBean;
import project.edge.domain.view.IncomeContractBean;
import project.edge.service.contract.IncomeContractService;
import project.edge.web.controller.common.TreeGridUploadController;

/**
 * 收入合同画面
 *
 */
@Controller
@SessionAttributes(Constants.SESSION_USER)
@RequestMapping("/income-contract-statement")
public class IncomeContractController
        extends TreeGridUploadController<IncomeContract, IncomeContractBean> {

    private static final Logger logger = LoggerFactory.getLogger(IncomeContractController.class);

    private static final String PID = "P4005";

    @Resource
    private IncomeContractService incomeContractService;

    @Override
    protected Logger getLogger() {
        return logger;
    }

    @Override
    public String getDataType() {
        return DataTypeEnum.INCOME_CONTRACT.value();
    }

    @Override
    protected Service<IncomeContract, String> getDataService() {
        return this.incomeContractService;
    }

    @Override
    protected String getPageId() {
        return PID;
    }

    @Override
    protected ViewConverter<IncomeContract, IncomeContractBean> getViewConverter() {
        return new IncomeContractBeanConverter();
    }

    /**
     * 画面Open的入口方法，用于生成JSP。
     * 
     * @param paramMap 画面请求中的任何参数，都会成为检索的字段
     * @param model model
     * @param userBean session中的当前登录的用户信息
     * @param locale locale
     * @return
    @RequestMapping("/main")
    public String main(@RequestParam Map<String, String> paramMap, Model model,
            @ModelAttribute(Constants.SESSION_USER) SessionUserBean userBean, Locale locale) {

        return super.main(paramMap, model, userBean, locale);
    }
     */

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
    @RequestMapping("/sub-grid-find")
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
    @RequestMapping("/sub-grid-add")
    @SysLogAnnotation(description = "收入合同管理", action = "新增")
    public void create(HttpServletRequest request, HttpServletResponse response,
            @ModelAttribute IncomeContractBean bean,
            @ModelAttribute(Constants.SESSION_USER) SessionUserBean userBean, Locale locale) {

        Map<String, List<ArchiveBean>> fieldNameArchiveListMap = new HashMap<>();
        fieldNameArchiveListMap.put("archives_", bean.getArchivesList());

        // 项目组的文件，位于/project-group/id文件夹内，id是项目组的主键值
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
    @RequestMapping("/sub-grid-edit")
    @SysLogAnnotation(description = "收入合同管理", action = "更新")
    public void update(HttpServletRequest request, HttpServletResponse response,
            @ModelAttribute IncomeContractBean bean,
            @ModelAttribute(Constants.SESSION_USER) SessionUserBean userBean, Locale locale) {

        Map<String, List<ArchiveBean>> fieldNameArchiveListMap = new HashMap<>();
        fieldNameArchiveListMap.put("archives_", bean.getArchivesList());

        // 项目组的文件，位于/project-group/id文件夹内，id是项目组的主键值
        super.updateWithUpload(request, response, bean, fieldNameArchiveListMap, userBean, locale);
    }

    @Override
    protected void postUpdate(IncomeContract entity, IncomeContract oldEntity,
            IncomeContractBean bean, java.util.Map<String, Object> paramMap) throws IOException {
        super.postUpdate(entity, oldEntity, bean, paramMap);
        this.deleteArchiveFiles(entity.getArchivesToDelete());
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
    @SysLogAnnotation(description = "收入合同管理", action = "删除")
    public JsonResultBean delete(@RequestParam String idsToDelete,
            @ModelAttribute(Constants.SESSION_USER) SessionUserBean userBean, Locale locale) {
        return super.delete(idsToDelete, null, userBean, locale);
    }
}
