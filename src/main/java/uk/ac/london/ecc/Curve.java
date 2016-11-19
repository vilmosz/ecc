package uk.ac.london.ecc;

public class Curve {

	private Long a;
	private Long b;

	public Curve(Long a, Long b) {
		super();
		this.a = a;
		this.b = b;
	}

	public Long getA() {
		return a;
	}

	public Long getB() {
		return b;
	}

	public void setA(Long a) {
		this.a = a;
	}

	public void setB(Long b) {
		this.b = b;
	}

	@Override
	public String toString() {
		return "Curve [a=" + a + ", b=" + b + "]";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((a == null) ? 0 : a.hashCode());
		result = prime * result + ((b == null) ? 0 : b.hashCode());
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
		Curve other = (Curve) obj;
		if (a == null) {
			if (other.a != null)
				return false;
		} else if (!a.equals(other.a))
			return false;
		if (b == null) {
			if (other.b != null)
				return false;
		} else if (!b.equals(other.b))
			return false;
		return true;
	}

}
