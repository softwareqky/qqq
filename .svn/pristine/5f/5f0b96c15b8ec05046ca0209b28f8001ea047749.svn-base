/**
 * 
 */
package project.edge.service.budget;

import org.springframework.stereotype.Service;

import javax.annotation.Resource;

import garage.origin.dao.Dao;
import garage.origin.domain.business.OrderByDto;
import garage.origin.service.GenericServiceImpl;
import project.edge.dao.budget.AccountInfoDao;
import project.edge.domain.entity.AccountInfo;


/**
 * @author wwy
 *         [t_account_info]对应的 Service。
 */
@Service
public class AccountInfoServiceImpl extends GenericServiceImpl<AccountInfo, String> implements AccountInfoService {

    @Resource
    private AccountInfoDao accountInfoDao;

    @Override
    public Dao<AccountInfo, String> getDefaultDao() {
        return this.accountInfoDao;
    }

    @Override
    public OrderByDto getDefaultOrder() {
        return new OrderByDto("cDatetime", false);
    }

}
