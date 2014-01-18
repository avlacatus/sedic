package ro.infoiasi.sedic.android.model;

import java.util.Collection;

public class Road implements Bean {

	private static final long serialVersionUID = 1L;
	private int id;
	private String indicative;
	private String type;
	private int length;
	private String region;
	private double[] firstCoordinates;
	private double[] secondCoordinates;
	private Collection<MappedIndicator> mappedIndicators;

	@Override
	public int getID() {
		return id;
	}

	@Override
	public void setID(int id) {
		this.id = id;
	}

	public String getIndicative() {
		return indicative;
	}

	public void setIndicative(String indicative) {
		this.indicative = indicative;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public int getLength() {
		return length;
	}

	public void setLength(int length) {
		this.length = length;
	}

	public String getRegion() {
		return region;
	}

	public void setRegion(String region) {
		this.region = region;
	}

	public Collection<MappedIndicator> getMappedIndicators() {
		return mappedIndicators;
	}

	public void setMappedIndicators(Collection<MappedIndicator> mappedIndicators) {
		this.mappedIndicators = mappedIndicators;
	}
	
	public double[] getFirstCoordinates() {
		return firstCoordinates;
	}

	public void setFirstCoordinates(double[] firstCoordinates) {
		this.firstCoordinates = firstCoordinates;
	}

	public double[] getSecondCoordinates() {
		return secondCoordinates;
	}

	public void setSecondCoordinates(double[] secondCoordinates) {
		this.secondCoordinates = secondCoordinates;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o)
			return true;
		if (o == null || getClass() != o.getClass())
			return false;

		Road that = (Road) o;

		if (id != that.id)
			return false;
		if (length != that.length)
			return false;
		if (indicative != null ? !indicative.equals(that.indicative)
				: that.indicative != null)
			return false;
		if (region != null ? !region.equals(that.region) : that.region != null)
			return false;
		if (type != null ? !type.equals(that.type) : that.type != null)
			return false;

		return true;
	}

	@Override
	public int hashCode() {
		int result = id;
		result = 31 * result + (indicative != null ? indicative.hashCode() : 0);
		result = 31 * result + (type != null ? type.hashCode() : 0);
		result = 31 * result + length;
		result = 31 * result + (region != null ? region.hashCode() : 0);
		return result;
	}

}
