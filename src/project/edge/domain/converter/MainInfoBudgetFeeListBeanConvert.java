package project.edge.domain.converter;

import org.springframework.context.MessageSource;

import java.util.Locale;

import garage.origin.common.constant.Constants;
import garage.origin.common.util.DateUtils;
import project.edge.domain.entity.BudgetFee;
import project.edge.domain.view.MainInfoBudgetFeeListBean;

/**
 * @author angel_000
 *
 */
public class MainInfoBudgetFeeListBeanConvert {

    /**
     * 使用系统默认配置。
     * 
     * @param p
     * @param messageSource
     * @param locale
     * @return
     */
    public MainInfoBudgetFeeListBean fromEntity(BudgetFee b, MessageSource messageSource,
            Locale locale) {

        if (b == null) {
            return null;
        }

        MainInfoBudgetFeeListBean bean = new MainInfoBudgetFeeListBean();
        bean.setId(b.getId());
        bean.setUsedSum(b.getBxjey());
        if (b.getcDatetime() != null) {
            bean.setTime(DateUtils.date2String(b.getcDatetime(), Constants.DATE_FORMAT));
        }

        return bean;

    }
}
