/**
 * 
 */
package project.edge.domain.mobile.converter;

import org.springframework.context.MessageSource;

import java.util.List;
import java.util.Locale;

import garage.origin.common.constant.Constants;
import garage.origin.common.util.CheckUtils;
import project.edge.domain.entity.Page;
import project.edge.domain.mobile.view.ApplicationBean;

/**
 * @author angel_000
 *
 */
public class ApplicationBeanConverter {

    /**
     * 转换登录信息，包括用户信息、角色信息。
     * 
     * @param pages
     * @param messageSource
     * @param locale
     * @return
     */
    public ApplicationBean fromEntity(List<Page> pageList, MessageSource messageSource,
            Locale locale) {

        ApplicationBean bean = new ApplicationBean();

        StringBuilder builder = new StringBuilder();
        for (Page p : pageList) {
            builder.append(p.getId()).append(Constants.COMMA);
        }
        if (builder.length() > 0) {
            builder.delete(builder.length() - 1, builder.length()); // 移除末尾的逗号
        }
        bean.setPageIds(CheckUtils.checkString(builder.toString()));

        return bean;

    }
}
