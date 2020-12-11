package project.edge.domain.view;

import garage.origin.domain.view.ViewBean;

public class TenderingPurchaseBean implements ViewBean {
	private String id;

    private String tenderingPlan_;
    private String tenderingPlanText;
    private String purchaseOrder_;
    private String purchaseOrderText;
    private String creator;
    private String cDatetime;

    @Override
    public String getId() {
        // TODO Auto-generated method stub
        return this.id;
    }
    
    
    public void setId(String id) {
        this.id = id;
    }
    
    public String getTenderingPlan_() {
        // TODO Auto-generated method stub
        return this.tenderingPlan_;
    }
    
    
    public void setTenderingPlan_(String tenderingPlan_) {
        this.tenderingPlan_ = tenderingPlan_;
    }
    
    
    public String getTenderingPlanText() {
        return this.tenderingPlanText;
    }


    public void setTenderingPlanText(String tenderingPlanText) {
        this.tenderingPlanText = tenderingPlanText;
    }


    public String getPurchaseOrder_() {
        return this.purchaseOrder_;
    }


    public void setPurchaseOrder_(String purchaseOrder_) {
        this.purchaseOrder_ = purchaseOrder_;
    }


    public String getPurchaseOrderText() {
        return this.purchaseOrderText;
    }


    public void setPurchaseOrderText(String purchaseOrderText) {
        this.purchaseOrderText = purchaseOrderText;
    }
    

    public String getCreator() {
        return creator;
    }

    public void setCreator(String creator) {
        this.creator = creator;
    }

    public String getcDatetime() {
        return cDatetime;
    }

    public void setcDatetime(String cDatetime) {
        this.cDatetime = cDatetime;
    }
}
