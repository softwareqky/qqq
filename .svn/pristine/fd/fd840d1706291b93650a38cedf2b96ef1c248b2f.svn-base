/**
 * 
 */
package project.edge.web.controller.facility;

import java.util.Date;
import java.util.List;
import java.util.Locale;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.MessageSource;
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
import garage.origin.domain.filter.CommonFilter;
import garage.origin.domain.view.JsonResultBean;
import garage.origin.domain.view.SessionUserBean;
import project.edge.domain.converter.MapDefinedIconBeanConvert;
import project.edge.domain.entity.DefinedSegmentIcon;
import project.edge.domain.entity.DefinedSiteIcon;
import project.edge.domain.view.MapDefinedIconBean;
import project.edge.service.system.DefinedSegmentIconService;
import project.edge.service.system.DefinedSiteIconService;

/**
 * 驾驶舱自定义画面。
 *
 */
@Controller
@SessionAttributes(Constants.SESSION_USER)
@RequestMapping("/system/dashboard-custom")
public class DashboardCustomization {

    private static final Logger logger = LoggerFactory.getLogger(DashboardCustomization.class);

    private static final String PID = "P1140";

    /** 如果在general文件夹中创建了index.jsp，则此处可以是general/index */
    private static final String CUSTOM_VIEW_NAME = "facility/dashboardCustomization";

    @Resource
    protected MessageSource messageSource;

    @Resource
    private DefinedSiteIconService definedSiteIconService;

    @Resource
    private DefinedSegmentIconService definedSegmentIconService;

    protected Logger getLogger() {
        return logger;
    }

    protected String getPageId() {
        return PID;
    }

    /**
     * 打开主画面。
     */
    @RequestMapping("/main")
    public String main(@ModelAttribute(Constants.SESSION_USER) SessionUserBean userBean,
            Model model, HttpServletResponse response, Locale locale) {

        return CUSTOM_VIEW_NAME;
    }

    /**
     * 更新自定义图标信息
     * 
     * @param locale
     * @return
     */
    @RequestMapping("/update-dashboard-icon")
    @ResponseBody
    public JsonResultBean updateDefinedIcon(
            @RequestParam(required = false, defaultValue = "") String siteType,
            @RequestParam(required = false, defaultValue = "") String name,
            @RequestParam(required = false, defaultValue = "") String x,
            @RequestParam(required = false, defaultValue = "") String y,
            @RequestParam(required = false, defaultValue = "") String segmentType,
            @RequestParam(required = false, defaultValue = "") String width,
            @RequestParam(required = false, defaultValue = "") String color,
            @RequestParam(required = false, defaultValue = "") String lineStyle, Model model,
            @ModelAttribute(Constants.SESSION_USER) SessionUserBean userBean, Locale locale) {

        JsonResultBean jsonResult = new JsonResultBean();
        try {

            MapDefinedIconBeanConvert converter = new MapDefinedIconBeanConvert();

            Date date = new Date();
            if (!StringUtils.isEmpty(siteType)) {
                CommonFilter filter = new CommonFilter().addExact("siteType", siteType);
                List<DefinedSiteIcon> siteIconsList =
                        this.definedSiteIconService.list(filter, null);

                if (siteIconsList != null) {

                    this.definedSiteIconService.update(converter.toEntitySite(
                            siteIconsList.get(0).getId(), siteIconsList.get(0).getIsMap(), siteType,
                            name, x, y, null, userBean, date, siteIconsList.get(0).getSize()));
                }

            }
            if (!StringUtils.isEmpty(segmentType)) {
                CommonFilter filter = new CommonFilter().addExact("segmentType", segmentType);
                List<DefinedSegmentIcon> segmentIconsList =
                        this.definedSegmentIconService.list(filter, null);

                this.definedSegmentIconService.update(converter.toEntitySeg(
                        segmentIconsList.get(0).getId(), segmentIconsList.get(0).getIsMap(),
                        segmentType, width, color, lineStyle, null, userBean, date));
            }



            // 准备JSON结果
            jsonResult.setStatus(JsonStatus.OK);
            jsonResult.setMessage(
                    this.messageSource.getMessage("message.info.record.modify.ok", null, locale));
            jsonResult.getDataMap().put(JsonResultBean.KEY_RETURN_OBJECT, "");

        } catch (Exception e) {
            getLogger().error("Exception updating the custom", e);

            // 在JSON对象内设定服务器处理结果状态
            jsonResult.setStatus(JsonStatus.ERROR);
        }

        return jsonResult;

    }
    
    /**
     * 更新大图标小图标的设置
     * @param type
     * @param size
     * @param userBean
     * @param locale
     * @return
     */
    @RequestMapping("/update-icon-size")
    @ResponseBody
    public JsonResultBean updateIconSize(@RequestParam String type, @RequestParam Short size,
            @ModelAttribute(Constants.SESSION_USER) SessionUserBean userBean, Locale locale) {
    	
    	JsonResultBean jsonResult = new JsonResultBean();
    	
    	try {
            
    		CommonFilter filter = new CommonFilter();
    		filter.addStart("siteType", type);
    		List<DefinedSiteIcon> siteIconList = this.definedSiteIconService.list(filter, null);
    		
    		for (DefinedSiteIcon icon: siteIconList) {
    			icon.setSize(size);
    			this.definedSiteIconService.update(icon);
    		}

            // 准备JSON结果
            jsonResult.setStatus(JsonStatus.OK);
            jsonResult.setMessage(this.messageSource.getMessage("message.info.record.modify.ok", null, locale));

        } catch (Exception e) {
            getLogger().error("Exception updating the custom icon size", e);

            // 在JSON对象内设定服务器处理结果状态
            jsonResult.setStatus(JsonStatus.ERROR);
        }
    	
    	return jsonResult;
    }

    /**
     * 获取自定义图标信息
     * 
     * @param locale
     * @return
     */
    @RequestMapping("/get-dashboard-icon")
    @ResponseBody
    public JsonResultBean getDefinedIcon(Model model,
            @ModelAttribute(Constants.SESSION_USER) SessionUserBean userBean, Locale locale) {
        JsonResultBean jsonResult = new JsonResultBean();
        try {
            MapDefinedIconBean mapDefinedIconBean = new MapDefinedIconBean();

            MapDefinedIconBeanConvert converter = new MapDefinedIconBeanConvert();

            CommonFilter filter = new CommonFilter();
            filter.addExact("isMap", (short) 0);

            List<DefinedSiteIcon> siteIconsList = this.definedSiteIconService.list(filter, null);

            List<DefinedSegmentIcon> segmentIconsList =
                    this.definedSegmentIconService.list(filter, null);

            if ((siteIconsList != null) && (siteIconsList.size() > 0)) {
                for (DefinedSiteIcon d : siteIconsList) {
                    mapDefinedIconBean.getSiteIconList()
                            .add(converter.fromEntitySite(d, this.messageSource, locale));
                }
            }

            if ((segmentIconsList != null) && (segmentIconsList.size() > 0)) {
                for (DefinedSegmentIcon d : segmentIconsList) {
                    mapDefinedIconBean.getSegmentIconList()
                            .add(converter.fromEntitySeg(d, this.messageSource, locale));
                }
            }

            // 准备JSON结果
            jsonResult.setStatus(JsonStatus.OK);
            jsonResult.setMessage(
                    this.messageSource.getMessage("message.info.record.modify.ok", null, locale));
            jsonResult.getDataMap().put(JsonResultBean.KEY_RETURN_OBJECT, mapDefinedIconBean);

        } catch (Exception e) {
            getLogger().error("Exception updating the custom", e);

            // 在JSON对象内设定服务器处理结果状态
            jsonResult.setStatus(JsonStatus.ERROR);
        }

        return jsonResult;

    }
}
