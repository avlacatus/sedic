package ro.infoiasi.sedic.android.model;

import java.util.List;

public class RemedyBean implements Bean {
	/**
     * 
     */
	private static final long serialVersionUID = -5700600767634342420L;
	private String remedyName;
	private long remedyId;
	private String remedyURI;
	private long remedyPlantId;
	private MedicalConditionBean medicalCondition;
	private List<String> partPlantUsages;
	private List<DrugBean> adjuvantUsage;
	private List<DiseaseBean> therapeuticalUsage;
	private List<Bean> frequentUsage;
	private List<Bean> reportedUsage;

	public RemedyBean() {
	}

	@Override
	public String toString() {
		return "RemedyBean [remedyName=" + remedyName + ", remedyId=" + remedyId + ", remedyURI=" + remedyURI
				+ ", remedyPlantId=" + remedyPlantId + ", adjuvantUsage=" + adjuvantUsage + ", frequentUsage="
				+ frequentUsage + ", reportedUsage=" + reportedUsage + ", therapeuticalUsage=" + therapeuticalUsage
				+ "]";
	}

	public String getRemedyName() {
		return remedyName;
	}

	public void setRemedyName(String remedyName) {
		this.remedyName = remedyName;
	}

	public long getRemedyId() {
		return remedyId;
	}

	public void setRemedyId(long remedyId) {
		this.remedyId = remedyId;
	}

	public String getRemedyURI() {
		return remedyURI;
	}

	public void setRemedyURI(String remedyURI) {
		this.remedyURI = remedyURI;
	}

	public long getRemedyPlantId() {
		return remedyPlantId;
	}

	public void setRemedyPlantId(long remedyPlantId) {
		this.remedyPlantId = remedyPlantId;
	}

	public List<DrugBean> getAdjuvantUsages() {
		return adjuvantUsage;
	}

	public void setAdjuvantUsages(List<DrugBean> adjuvantUsage) {
		this.adjuvantUsage = adjuvantUsage;
	}

	public List<DiseaseBean> getTherapeuticalUsages() {
		return therapeuticalUsage;
	}

	public void setTherapeuticalUsages(List<DiseaseBean> therapeuticalUsage) {
		this.therapeuticalUsage = therapeuticalUsage;
	}

	public List<Bean> getFrequentUsages() {
		return frequentUsage;
	}

	public void setFrequentUsages(List<Bean> frequentUsage) {
		this.frequentUsage = frequentUsage;
	}

	public List<Bean> getReportedUsages() {
		return reportedUsage;
	}

	public void setReportedUsages(List<Bean> reportedUsage) {
		this.reportedUsage = reportedUsage;
	}

	@Override
	public long getBeanID() {
		return remedyId;
	}

	@Override
	public String getBeanName() {
		return remedyName;
	}

	public List<String> getPartPlantUsages() {
		return partPlantUsages;
	}

	public void setPartPlantUsages(List<String> partPlantUsages) {
		this.partPlantUsages = partPlantUsages;
	}

	public MedicalConditionBean getMedicalCondition() {
		return medicalCondition;
	}

	public void setMedicalCondition(MedicalConditionBean medicalCondition) {
		this.medicalCondition = medicalCondition;
	}

}
