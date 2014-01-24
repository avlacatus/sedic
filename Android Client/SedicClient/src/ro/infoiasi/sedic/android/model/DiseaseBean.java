package ro.infoiasi.sedic.android.model;

import java.util.List;

public class DiseaseBean implements Bean {
	/**
     * 
     */
    private static final long serialVersionUID = 4026230489996669103L;
    private String diseaseName;
	private long diseaseId;
	private String diseaseURI;
	private List<DiseaseBean> diseaseChildren;

	public DiseaseBean() {
	}

	public DiseaseBean(String diseaseName, long diseaseId, String diseaseURI, List<DiseaseBean> diseaseChildren) {
		super();
		this.diseaseName = diseaseName;
		this.diseaseId = diseaseId;
		this.diseaseURI = diseaseURI;
		this.setDiseaseChildren(diseaseChildren);
	}

	@Override
	public String toString() {
		return "DiseaseBean [diseaseName=" + diseaseName + ", diseaseId=" + diseaseId + ", diseaseChildren="
				+ getDiseaseChildren() + "]";
	}

	public String getDiseaseName() {
		return diseaseName;
	}

	public void setDiseaseName(String diseaseName) {
		this.diseaseName = diseaseName;
	}

	public long getDiseaseId() {
		return diseaseId;
	}

	public void setDiseaseId(long diseaseId) {
		this.diseaseId = diseaseId;
	}

	public String getDiseaseURI() {
		return diseaseURI;
	}

	public void setDiseaseURI(String diseaseURI) {
		this.diseaseURI = diseaseURI;
	}

	public List<DiseaseBean> getDiseaseChildren() {
		return diseaseChildren;
	}

	public void setDiseaseChildren(List<DiseaseBean> diseaseChildren) {
		this.diseaseChildren = diseaseChildren;
	}

    @Override
    public long getBeanID() {
        return diseaseId;
    }

    @Override
    public String getBeanName() {
        return diseaseName;
    }

}
