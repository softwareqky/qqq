package project.edge.web.controller.acceptance;

import java.util.ArrayList;
import java.util.HashMap;
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
import garage.origin.common.constant.JsonStatus;
import garage.origin.domain.filter.CommonFilter;
import garage.origin.domain.view.JsonResultBean;
import garage.origin.domain.view.SessionUserBean;
import garage.origin.domain.view.converter.ViewConverter;
import garage.origin.service.Service;
import project.edge.common.constant.ControllerJsMapKeys;
import project.edge.common.constant.ControllerModelKeys;
import project.edge.common.constant.ControllerUrlMapKeys;
import project.edge.common.constant.DataTypeEnum;
import project.edge.domain.converter.ReviewExpertBeanConverter;
import project.edge.domain.entity.Expert;
import project.edge.domain.entity.Review;
import project.edge.domain.entity.ReviewExpert;
import project.edge.domain.view.ReviewExpertBean;
import project.edge.service.acceptance.ReviewExpertService;
import project.edge.web.controller.common.SingleGridController;

@Controller
@SessionAttributes(Constants.SESSION_USER)
@RequestMapping("/acceptance/review-expert")
public class ReviewExpertController extends SingleGridController<ReviewExpert, ReviewExpertBean> {

    private static final Logger logger = LoggerFactory.getLogger(ReviewExpertController.class);

    private static final String PID = "P7006";

    @Resource
    private ReviewExpertService reviewExpertService;

    @Override
    protected Logger getLogger() {
        return logger;
    }

    @Override
    public String getDataType() {
        return DataTypeEnum.REVIEW_EXPERT.value();
    }

    @Override
    protected Service<ReviewExpert, String> getDataService() {
        return this.reviewExpertService;
    }

    @Override
    protected String getPageId() {
        return PID;
    }

    @Override
    protected ViewConverter<ReviewExpert, ReviewExpertBean> getViewConverter() {
        return new ReviewExpertBeanConverter();
    }

    @Override
    protected String getJsCallbackObjName() {
        return this.getDataType();
    }

    @Override
    protected String getJavascriptJspPath() {
        return "/WEB-INF/jsp/acceptance/reviewExpertJs.jsp";
    }

    @Override
    protected Map<String, String> prepareJsMap(Map<String, String> idMap,
            Map<String, String> urlMap) {

        Map<String, String> jsMap = super.prepareJsMap(idMap, urlMap);

        // 打开新建，弹出人员通用选择画面
        jsMap.put(ControllerJsMapKeys.OPEN_ADD,
                "PopupSelectUtils.openExpertDblGridSelect(" + this.getJsCallbackObjName() + ")");

        return jsMap;
    }
    
    /**
     * 画面Open的入口方法，用于生成JSP。
     * 
     * @param paramMap 画面请求中的任何参数，都会成为检索的字段
     * @param reviewId 评审ID
     * @param model model
     * @param userBean session中的当前登录的用户信息
     * @param locale locale
     * @return
     */
    @SuppressWarnings("unchecked")
    @RequestMapping("/main")
    public String main(@RequestParam Map<String, String> paramMap,
            @RequestParam(required = true) String reviewId, Model model,
            @ModelAttribute(Constants.SESSION_USER) SessionUserBean userBean, Locale locale) {

        String r = super.main(paramMap, model, userBean, locale);
        Map<String, Object> modelMap = model.asMap();
        Map<String, String> urlMap =
                (Map<String, String>) modelMap.get(ControllerModelKeys.URL_MAP);

        String listUrl = urlMap.get(ControllerUrlMapKeys.LIST);
        urlMap.put(ControllerUrlMapKeys.LIST, listUrl + "?reviewId=" + reviewId);

        return r;
    }

    /**
     * 获取一览显示的数据，返回JSON格式。
     * 
     * @param commonFilterJson JSON字符串形式的过滤条件
     * @param reviewId 评审ID
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
            @RequestParam(required = true) String reviewId,
            @RequestParam(required = false, defaultValue = "1") int page,
            @RequestParam(required = false, defaultValue = "999999999") int rows,
            @RequestParam(required = false, defaultValue = "") String sort,
            @RequestParam(required = false, defaultValue = "asc") String order, Locale locale) {

        CommonFilter filter = new CommonFilter().addExact("review.id", reviewId);
        return super.list(commonFilterJson, filter, page, rows, sort, order, locale);
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
    public JsonResultBean create(@RequestParam(required = true) String review_,
            @RequestParam(required = true) List<String> expertList,
            @ModelAttribute(Constants.SESSION_USER) SessionUserBean userBean, Locale locale) {

        JsonResultBean jsonResult = new JsonResultBean();
        try {

            CommonFilter filter = new CommonFilter().addExact("review.id", review_);
            List<ReviewExpert> dbList = this.reviewExpertService.list(filter, null);
            Map<String, ReviewExpert> map = new HashMap<>();
            for (ReviewExpert dbAuth : dbList) {
                map.put(dbAuth.getExpert().getId(), dbAuth);
            }

            // 数据库是否已存在记录
            boolean hasEntity = false;

            List<ReviewExpert> listToCreate = new ArrayList<>();
            for (String person : expertList) {
                if (map.containsKey(person)) {
                    hasEntity = true;
                    continue;
                }

                ReviewExpert entity = new ReviewExpert();

                Review a = new Review();
                a.setId(review_);
                entity.setReview(a);

                Expert p = new Expert();
                p.setId(person);
                entity.setExpert(p);

                listToCreate.add(entity);
            }

            this.reviewExpertService.batchCreate(listToCreate);

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
     * 删除，返回Json格式。
     * 
     * @param idsToDelete 待删除的ID，CSV
     * @param userBean Session中的用户信息
     * @param locale
     * @return
     */
    @RequestMapping("/delete")
    @ResponseBody
    public JsonResultBean delete(@RequestParam String idsToDelete,
            @ModelAttribute(Constants.SESSION_USER) SessionUserBean userBean, Locale locale) {
        return super.delete(idsToDelete, null, userBean, locale);
    }
}
