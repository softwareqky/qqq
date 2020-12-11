package project.edge.domain.view;

/**
 * @author angel_000
 *
 */
public class DefinedSiteIconBean {

    private String id;
    private String siteType;
    private String iconName;
    private String x;
    private String y;
    private Short size;
    
    public Short getSize() {
		return size;
	}

	public void setSize(Short size) {
		this.size = size;
	}

	public String getId() {
        return this.id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getSiteType() {
        return this.siteType;
    }

    public void setSiteType(String siteType) {
        this.siteType = siteType;
    }

    public String getIconName() {
        return this.iconName;
    }

    public void setIconName(String iconName) {
        this.iconName = iconName;
    }

    public String getX() {
        return this.x;
    }

    public void setX(String x) {
        this.x = x;
    }

    public String getY() {
        return this.y;
    }

    public void setY(String y) {
        this.y = y;
    }

}
