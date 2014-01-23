package ro.infoiasi.sedic.android.model;

import java.util.List;

public class DrugBean implements Bean {
    /**
     * 
     */
    private static final long serialVersionUID = -7680380557823510283L;
    private String drugName;
    private long drugId;
    private String drugURI;
    private List<DrugBean> children;

    public DrugBean() {

    }

    public DrugBean(String drugName, long drugId, String drugURI, List<DrugBean> children) {
        super();
        this.drugName = drugName;
        this.drugId = drugId;
        this.drugURI = drugURI;
        this.children = children;
    }

    @Override
    public String toString() {
        return "DrugBean [drugName=" + drugName + ", drugId=" + drugId + ", parents=" + children + "]";
    }

    public String getDrugName() {
        return drugName;
    }

    public void setDrugName(String drugName) {
        this.drugName = drugName;
    }

    public long getDrugId() {
        return drugId;
    }

    public void setDrugId(long drugId) {
        this.drugId = drugId;
    }

    public String getDrugURI() {
        return drugURI;
    }

    public void setDrugURI(String drugURI) {
        this.drugURI = drugURI;
    }

    public List<DrugBean> getDrugChildren() {
        return children;
    }

    public void setDrugChildren(List<DrugBean> children) {
        this.children = children;
    }

    @Override
    public long getBeanID() {
        return drugId;
    }

    @Override
    public String getBeanName() {
        return drugName;
    }

}
