package uk.ac.london.ecc;

public class Ecc {

	private Long a;
	private Long b;
	private Long p;

	public Ecc() {
		super();
	}

	public Ecc(Long a, Long b, Long p) {
		super();
		this.a = a;
		this.b = b;
		this.p = p;
	}

	@Override
	public String toString() {
		return "Ecc [a=" + a + ", b=" + b + ", p=" + p + "]";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((a == null) ? 0 : a.hashCode());
		result = prime * result + ((b == null) ? 0 : b.hashCode());
		result = prime * result + ((p == null) ? 0 : p.hashCode());
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
		Ecc other = (Ecc) obj;
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
		if (p == null) {
			if (other.p != null)
				return false;
		} else if (!p.equals(other.p))
			return false;
		return true;
	}

	public Long getA() {
		return a;
	}

	public void setA(Long a) {
		this.a = a;
	}

	public Long getB() {
		return b;
	}

	public void setB(Long b) {
		this.b = b;
	}

	public Long getP() {
		return p;
	}

	public void setP(Long p) {
		this.p = p;
	}
		
}
