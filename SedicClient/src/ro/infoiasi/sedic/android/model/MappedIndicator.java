package ro.infoiasi.sedic.android.model;

public class MappedIndicator implements Bean {

	private static final long serialVersionUID = 1L;
	private int id;
	private int km;
	private String observations;
	private Road road;
	private Indicator indicator;

	public int getID() {
		return id;
	}

	public void setID(int id) {
		this.id = id;
	}

	public int getKm() {
		return km;
	}

	public void setKm(int km) {
		this.km = km;
	}

	public String getObservations() {
		return observations;
	}

	public void setObservations(String observatii) {
		this.observations = observatii;
	}

	public Road getRoad() {
		return road;
	}

	public void setRoad(Road road) {
		this.road = road;
	}

	public Indicator getIndicator() {
		return indicator;
	}

	public void setIndicator(Indicator indicator) {
		this.indicator = indicator;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o)
			return true;
		if (o == null || getClass() != o.getClass())
			return false;

		MappedIndicator that = (MappedIndicator) o;

		if (id != that.id)
			return false;
		if (km != that.km)
			return false;
		if (observations != null ? !observations.equals(that.observations)
				: that.observations != null)
			return false;

		return true;
	}

	@Override
	public int hashCode() {
		int result = id;
		result = 31 * result + km;
		result = 31 * result
				+ (observations != null ? observations.hashCode() : 0);
		return result;
	}

}
