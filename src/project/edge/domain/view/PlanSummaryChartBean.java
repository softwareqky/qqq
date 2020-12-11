/**
 * 
 */
package project.edge.domain.view;


/**
 * @author angel_000
 *
 */
public class PlanSummaryChartBean {

    private String id;
    private String planName;
    private Double progress;

    public String getId() {
        return this.id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getPlanName() {
        return this.planName;
    }

    public void setPlanName(String planName) {
        this.planName = planName;
    }

    public Double getProgress() {
        return this.progress;
    }

    public void setProgress(Double progress) {
        this.progress = progress;
    }

}
