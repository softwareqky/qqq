package project.edge.domain.view;

import garage.origin.domain.view.ViewBean;

public class ProjectCheckExpertBean implements ViewBean {

	private String id;

	private String projectCheck_;
    private String checkedContent;
    private String expert_;
    private String expertText;

    @Override
    public String getId() {
        // TODO Auto-generated method stub
        return this.id;
    }


    public void setId(String id) {
        this.id = id;
    }

    
    public String getProjectCheck_() {
        // TODO Auto-generated method stub
        return this.projectCheck_;
    }


    public void setProjectCheck_(String projectCheck_) {
        this.projectCheck_ = projectCheck_;
    }
    
    public String getCheckedContent() {
        return this.checkedContent;
    }


    public void setCheckedContent(String checkedContent) {
        this.checkedContent = checkedContent;
    }


    public String getExpert_() {
        return this.expert_;
    }


    public void setExpert_(String expert_) {
        this.expert_ = expert_;
    }


    public String getExpertText() {
        return this.expertText;
    }


    public void setExpertText(String expertText) {
        this.expertText = expertText;
    }

}
