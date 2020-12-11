/**
 * 
 */
package project.edge.domain.view;

import garage.origin.domain.view.ViewBean;


/**
 * @author angel_000
 *
 */
public class AccountInfoBean implements ViewBean {

    /*
     * (non-Javadoc)
     * 
     * @see garage.origin.domain.view.ViewBean#getId()
     */

    private String id;
    private String accountNumber;
    private String accountName;
    private String containingProjects;
    private String startUpFunds;
    private String otherIncome;
    private String paymentIntoAccount;
    private String projectBorrowing;
    private String borrowingInterest;
    private String returnPrincipal;
    private String returnInterest;
    private String contractPayableAmount;
    private String indirectExpensesPayable;
    private String accountAvailableBalance;
    private String unpaidContractAmount;
    private String noIndirectExpensesIncurred;
    private String accountBalance;
    private String operation;

    @Override
    public String getId() {
        // TODO Auto-generated method stub
        return this.id;
    }

    public void setId(String id) {
        this.id = id;
    }


    public String getAccountNumber() {
        return this.accountNumber;
    }


    public void setAccountNumber(String accountNumber) {
        this.accountNumber = accountNumber;
    }


    public String getAccountName() {
        return this.accountName;
    }


    public void setAccountName(String accountName) {
        this.accountName = accountName;
    }


    public String getContainingProjects() {
        return this.containingProjects;
    }


    public void setContainingProjects(String containingProjects) {
        this.containingProjects = containingProjects;
    }


    public String getStartUpFunds() {
        return this.startUpFunds;
    }


    public void setStartUpFunds(String startUpFunds) {
        this.startUpFunds = startUpFunds;
    }


    public String getOtherIncome() {
        return this.otherIncome;
    }


    public void setOtherIncome(String otherIncome) {
        this.otherIncome = otherIncome;
    }


    public String getPaymentIntoAccount() {
        return this.paymentIntoAccount;
    }


    public void setPaymentIntoAccount(String paymentIntoAccount) {
        this.paymentIntoAccount = paymentIntoAccount;
    }


    public String getProjectBorrowing() {
        return this.projectBorrowing;
    }


    public void setProjectBorrowing(String projectBorrowing) {
        this.projectBorrowing = projectBorrowing;
    }


    public String getBorrowingInterest() {
        return this.borrowingInterest;
    }


    public void setBorrowingInterest(String borrowingInterest) {
        this.borrowingInterest = borrowingInterest;
    }


    public String getReturnPrincipal() {
        return this.returnPrincipal;
    }


    public void setReturnPrincipal(String returnPrincipal) {
        this.returnPrincipal = returnPrincipal;
    }


    public String getReturnInterest() {
        return this.returnInterest;
    }


    public void setReturnInterest(String returnInterest) {
        this.returnInterest = returnInterest;
    }


    public String getContractPayableAmount() {
        return this.contractPayableAmount;
    }


    public void setContractPayableAmount(String contractPayableAmount) {
        this.contractPayableAmount = contractPayableAmount;
    }


    public String getIndirectExpensesPayable() {
        return this.indirectExpensesPayable;
    }


    public void setIndirectExpensesPayable(String indirectExpensesPayable) {
        this.indirectExpensesPayable = indirectExpensesPayable;
    }


    public String getAccountAvailableBalance() {
        return this.accountAvailableBalance;
    }


    public void setAccountAvailableBalance(String accountAvailableBalance) {
        this.accountAvailableBalance = accountAvailableBalance;
    }


    public String getUnpaidContractAmount() {
        return this.unpaidContractAmount;
    }


    public void setUnpaidContractAmount(String unpaidContractAmount) {
        this.unpaidContractAmount = unpaidContractAmount;
    }


    public String getNoIndirectExpensesIncurred() {
        return this.noIndirectExpensesIncurred;
    }


    public void setNoIndirectExpensesIncurred(String noIndirectExpensesIncurred) {
        this.noIndirectExpensesIncurred = noIndirectExpensesIncurred;
    }


    public String getAccountBalance() {
        return this.accountBalance;
    }


    public void setAccountBalance(String accountBalance) {
        this.accountBalance = accountBalance;
    }


    public String getOperation() {
        return this.operation;
    }


    public void setOperation(String operation) {
        this.operation = operation;
    }

}
