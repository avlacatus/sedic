package ro.infoiasi.sedic.android.model;

import java.util.List;

public class RemedyBean {
	private String remedyName;
	private long remedyId;
	private String remedyURI;
	private long remedyPlantId;
	private List<String> adjuvantUsage;
	private List<String> frequentUsage;
	private List<String> reportedUsage;
	private List<String> therapeuticalUsage;

	public RemedyBean() {
	}

	public RemedyBean(String remedyName, long remedyId, String remedyURI,
			long remedyPlantId, List<String> adjuvantUsage,
			List<String> frequentUsage, List<String> reportedUsage,
			List<String> therapeuticalUsage) {
		super();
		this.remedyName = remedyName;
		this.remedyId = remedyId;
		this.remedyURI = remedyURI;
		this.remedyPlantId = remedyPlantId;
		this.adjuvantUsage = adjuvantUsage;
		this.frequentUsage = frequentUsage;
		this.reportedUsage = reportedUsage;
		this.therapeuticalUsage = therapeuticalUsage;
	}

	@Override
	public String toString() {
		return "RemedyBean [remedyName=" + remedyName + ", remedyId="
				+ remedyId + ", remedyURI=" + remedyURI + ", remedyPlantId="
				+ remedyPlantId + ", adjuvantUsage=" + adjuvantUsage
				+ ", frequentUsage=" + frequentUsage + ", reportedUsage="
				+ reportedUsage + ", therapeuticalUsage=" + therapeuticalUsage
				+ "]";
	}

	public List<String> getAdjuvantUsage() {
		return adjuvantUsage;
	}

	public void setAdjuvantUsage(List<String> adjuvantUsage) {
		this.adjuvantUsage = adjuvantUsage;
	}

	public List<String> getFrequentUsage() {
		return frequentUsage;
	}

	public void setFrequentUsage(List<String> frequentUsage) {
		this.frequentUsage = frequentUsage;
	}

	public List<String> getReportedUsage() {
		return reportedUsage;
	}

	public void setReportedUsage(List<String> reportedUsage) {
		this.reportedUsage = reportedUsage;
	}

	public List<String> getTherapeuticalUsage() {
		return therapeuticalUsage;
	}

	public void setTherapeuticalUsage(List<String> therapeuticalUsage) {
		this.therapeuticalUsage = therapeuticalUsage;
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

}
