package ro.infoiasi.sedic.android.model;

import java.util.List;

public class MedicalConditionBean implements Bean {
	/**
	 * 
	 */
	private static final long serialVersionUID = -6968250146827663974L;
	private String medicalConditionName;
	private long medicalConditionId;
	private String medicalConditionURI;
	private int medicalConditionMinAge;
	private List<MedicalFactorBean> medicalFactors;
	private List<DiseaseBean> contraindicatedDiseases;

	public MedicalConditionBean() {
	}

	public MedicalConditionBean(String medicalConditionName, long medicalConditionId, String medicalConditionURI,
			int medicalConditionMinAge, List<MedicalFactorBean> medicalFactors,
			List<DiseaseBean> contraindicatedDiseases) {
		super();
		this.medicalConditionName = medicalConditionName;
		this.medicalConditionId = medicalConditionId;
		this.medicalConditionURI = medicalConditionURI;
		this.medicalConditionMinAge = medicalConditionMinAge;
		this.medicalFactors = medicalFactors;
		this.contraindicatedDiseases = contraindicatedDiseases;
	}

	public String getMedicalConditionName() {
		return medicalConditionName;
	}

	public void setMedicalConditionName(String medicalConditionName) {
		this.medicalConditionName = medicalConditionName;
	}

	public long getMedicalConditionId() {
		return medicalConditionId;
	}

	public void setMedicalConditionId(long medicalConditionId) {
		this.medicalConditionId = medicalConditionId;
	}

	public String getMedicalConditionURI() {
		return medicalConditionURI;
	}

	public void setMedicalConditionURI(String medicalConditionURI) {
		this.medicalConditionURI = medicalConditionURI;
	}

	public int getMedicalConditionMinAge() {
		return medicalConditionMinAge;
	}

	public void setMedicalConditionMinAge(int medicalConditionMinAge) {
		this.medicalConditionMinAge = medicalConditionMinAge;
	}

	public List<MedicalFactorBean> getMedicalFactors() {
		return medicalFactors;
	}

	public void setMedicalFactors(List<MedicalFactorBean> medicalFactors) {
		this.medicalFactors = medicalFactors;
	}

	public List<DiseaseBean> getContraindicatedDiseases() {
		return contraindicatedDiseases;
	}

	public void setContraindicatedDiseases(List<DiseaseBean> contraindicatedDiseases) {
		this.contraindicatedDiseases = contraindicatedDiseases;
	}

	@Override
	public long getBeanID() {
		return medicalConditionId;
	}

	@Override
	public String getBeanName() {
		return medicalConditionName;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((contraindicatedDiseases == null) ? 0 : contraindicatedDiseases.hashCode());
		result = prime * result + (int) (medicalConditionId ^ (medicalConditionId >>> 32));
		result = prime * result + medicalConditionMinAge;
		result = prime * result + ((medicalConditionName == null) ? 0 : medicalConditionName.hashCode());
		result = prime * result + ((medicalConditionURI == null) ? 0 : medicalConditionURI.hashCode());
		result = prime * result + ((medicalFactors == null) ? 0 : medicalFactors.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		MedicalConditionBean other = (MedicalConditionBean) obj;
		if (contraindicatedDiseases == null) {
			if (other.contraindicatedDiseases != null)
				return false;
		} else if (!contraindicatedDiseases.equals(other.contraindicatedDiseases))
			return false;
		if (medicalConditionId != other.medicalConditionId)
			return false;
		if (medicalConditionMinAge != other.medicalConditionMinAge)
			return false;
		if (medicalConditionName == null) {
			if (other.medicalConditionName != null)
				return false;
		} else if (!medicalConditionName.equals(other.medicalConditionName))
			return false;
		if (medicalConditionURI == null) {
			if (other.medicalConditionURI != null)
				return false;
		} else if (!medicalConditionURI.equals(other.medicalConditionURI))
			return false;
		if (medicalFactors == null) {
			if (other.medicalFactors != null)
				return false;
		} else if (!medicalFactors.equals(other.medicalFactors))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "MedicalConditionBean [medicalConditionName=" + medicalConditionName + ", medicalConditionId="
				+ medicalConditionId + ", medicalConditionURI=" + medicalConditionURI + ", medicalConditionMinAge="
				+ medicalConditionMinAge + ", medicalFactors=" + medicalFactors + ", contraindicatedDiseases="
				+ contraindicatedDiseases + ", getMedicalConditionName()=" + getMedicalConditionName()
				+ ", getMedicalConditionId()=" + getMedicalConditionId() + ", getMedicalConditionURI()="
				+ getMedicalConditionURI() + ", getMedicalConditionMinAge()=" + getMedicalConditionMinAge()
				+ ", getMedicalFactors()=" + getMedicalFactors() + ", getContraindicatedDiseases()="
				+ getContraindicatedDiseases() + ", getBeanID()=" + getBeanID() + ", getBeanName()=" + getBeanName()
				+ ", getClass()=" + getClass() + ", hashCode()=" + hashCode() + ", toString()=" + super.toString()
				+ "]";
	}

}
