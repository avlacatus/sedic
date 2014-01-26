package ro.infoiasi.sedic.android.model;

public class MedicalFactorBean implements Bean {
	/**
	 * 
	 */
	private static final long serialVersionUID = -6968250146827663974L;
	private String medicalFactorName;
	private long medicalFactorId;
	private String medicalFactorURI;

	public MedicalFactorBean() {
	}

	public MedicalFactorBean(String medicalFactorName, long medicalFactorId, String medicalFactorURI) {
		super();
		this.medicalFactorName = medicalFactorName;
		this.medicalFactorId = medicalFactorId;
		this.medicalFactorURI = medicalFactorURI;
	}

	public String getMedicalFactorName() {
		return medicalFactorName;
	}

	public void setMedicalFactorName(String medicalFactorName) {
		this.medicalFactorName = medicalFactorName;
	}

	public long getMedicalFactorId() {
		return medicalFactorId;
	}

	public void setMedicalFactorId(long medicalFactorId) {
		this.medicalFactorId = medicalFactorId;
	}

	public String getMedicalFactorURI() {
		return medicalFactorURI;
	}

	public void setMedicalFactorURI(String medicalFactorURI) {
		this.medicalFactorURI = medicalFactorURI;
	}

	@Override
	public String toString() {
		return "MedicalFactorBean [medicalFactorName=" + medicalFactorName + ", medicalFactorId=" + medicalFactorId
				+ ", medicalFactorURI=" + medicalFactorURI + "]";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + (int) (medicalFactorId ^ (medicalFactorId >>> 32));
		result = prime * result + ((medicalFactorName == null) ? 0 : medicalFactorName.hashCode());
		result = prime * result + ((medicalFactorURI == null) ? 0 : medicalFactorURI.hashCode());
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
		MedicalFactorBean other = (MedicalFactorBean) obj;
		if (medicalFactorId != other.medicalFactorId)
			return false;
		if (medicalFactorName == null) {
			if (other.medicalFactorName != null)
				return false;
		} else if (!medicalFactorName.equals(other.medicalFactorName))
			return false;
		if (medicalFactorURI == null) {
			if (other.medicalFactorURI != null)
				return false;
		} else if (!medicalFactorURI.equals(other.medicalFactorURI))
			return false;
		return true;
	}

	@Override
	public long getBeanID() {
		return medicalFactorId;
	}

	@Override
	public String getBeanName() {
		return medicalFactorName;
	}

}
