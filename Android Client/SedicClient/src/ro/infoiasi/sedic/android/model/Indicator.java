package ro.infoiasi.sedic.android.model;

import java.util.Arrays;
import java.util.Collection;

public class Indicator implements Bean {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private int id;
	private String code;
	private String name;
	private byte[] img;

	public int getID() {
		return id;
	}

	public void setID(int id) {
		this.id = id;
	}

	public byte[] getImg() {
		return img;
	}

	public void setImg(byte[] img) {
		this.img = img;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o)
			return true;
		if (o == null || getClass() != o.getClass())
			return false;

		Indicator that = (Indicator) o;

		if (id != that.id)
			return false;
		if (code != null ? !code.equals(that.code) : that.code != null)
			return false;
		if (name != null ? !name.equals(that.name) : that.name != null)
			return false;
		if (!Arrays.equals(img, that.img))
			return false;

		return true;
	}

	@Override
	public int hashCode() {
		int result = id;
		result = 31 * result + (code != null ? code.hashCode() : 0);
		result = 31 * result + (name != null ? name.hashCode() : 0);
		result = 31 * result + (img != null ? Arrays.hashCode(img) : 0);
		return result;
	}

	private Collection<MappedIndicator> maparesById;

	public Collection<MappedIndicator> getMaparesById() {
		return maparesById;
	}

	public void setMaparesById(Collection<MappedIndicator> maparesById) {
		this.maparesById = maparesById;
	}

	@Override
	public String toString() {
		return new StringBuilder().append(id).append("\n").append(code)
				.append("\n").append(name).toString();
	}
}
