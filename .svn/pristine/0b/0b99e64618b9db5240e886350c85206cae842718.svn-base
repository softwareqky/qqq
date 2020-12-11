/**
 * 
 */
package project.edge.domain.converter;

import org.springframework.context.MessageSource;
import org.springframework.util.StringUtils;

import java.text.ParseException;
import java.util.Date;
import java.util.Locale;

import garage.origin.common.constant.Constants;
import garage.origin.common.util.DateUtils;
import garage.origin.domain.view.AbstractSessionUserBean;
import garage.origin.domain.view.converter.ViewConverter;
import project.edge.domain.entity.ContractAccount;
import project.edge.domain.entity.Project;
import project.edge.domain.view.ContractAccountBean;


/**
 * @author angel_000
 *
 */
public class ContractAccountBeanConverter
        implements ViewConverter<ContractAccount, ContractAccountBean> {

    @Override
    public ContractAccountBean fromEntity(ContractAccount entity, MessageSource messageSource,
            Locale locale) {

        ContractAccountBean bean = new ContractAccountBean();
        bean.setId(entity.getId());

        bean.setContractNo(entity.getContractNo());
        bean.setContractName(entity.getContractName());
        bean.setFinalContractAmount(entity.getFinalContractAmount());

        if (entity.getSigningTime() != null) {
            bean.setSigningTime(
                    DateUtils.date2String(entity.getSigningTime(), Constants.DATE_FORMAT));
        }

        Project project = entity.getProject();
        if (project != null) {
            bean.setProject_(project.getId());
            bean.setProjectText(project.getProjectName());
        }

        bean.setAccumulatedCollectionAmount(entity.getAccumulatedCollectionAmount());
        bean.setCumulativeInvoiced(entity.getCumulativeInvoiced());
        bean.setAccumulatedInvoiceNotIssued(entity.getAccumulatedInvoiceNotIssued());

        return bean;
    }

    @Override
    public ContractAccount toEntity(ContractAccountBean bean, ContractAccount oldEntity,
            AbstractSessionUserBean user, Date now) {

        ContractAccount entity = oldEntity;

        if (entity == null) { // 表示新建
            entity = new ContractAccount();

            entity.setCreator(user.getSessionUserId());
            entity.setcDatetime(now);
        } else { // 表示修改
            entity.setModifier(user.getSessionUserId());
        }

        bean.setId(entity.getId()); // ID必须赋值
        entity.setmDatetime(now);

        entity.setContractNo(bean.getContractNo());
        entity.setContractName(bean.getContractName());
        entity.setFinalContractAmount(bean.getFinalContractAmount());

        if (!StringUtils.isEmpty(bean.getSigningTime())) {
            try {
                entity.setSigningTime(
                        DateUtils.string2Date(bean.getSigningTime(), Constants.DATE_FORMAT));
            } catch (ParseException e) {

                // do nothing
            }
        }

        if (!StringUtils.isEmpty(bean.getProject_())) {
            Project project = new Project();
            project.setId(bean.getProject_());
            project.setProjectName(bean.getProjectText());
            entity.setProject(project);
        }

        entity.setAccumulatedCollectionAmount(bean.getAccumulatedCollectionAmount());
        entity.setCumulativeInvoiced(bean.getCumulativeInvoiced());
        entity.setAccumulatedInvoiceNotIssued(bean.getAccumulatedInvoiceNotIssued());

        return entity;
    }

}
