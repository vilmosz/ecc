package uk.ac.london.assignment.model.csv;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Student {

	private String studentCode;
	private String name;
	private Integer a;
	private Integer b;
	private Integer k;
	private Integer order;
	private Integer px;
	private Integer py;
	private Integer qx;
	private Integer qy;
	private Integer rx;
	private Integer ry;

	@JsonProperty(value = "Student code")
	public void setStudentCode(String studentCode) {
		this.studentCode = studentCode;
	}

	@JsonProperty(value = "Name")
	public void setName(String name) {
		this.name = name;
	}

	@JsonProperty(value = "a")
	public void setA(Integer a) {
		this.a = a;
	}

	@JsonProperty(value = "b")
	public void setB(Integer b) {
		this.b = b;
	}

	@JsonProperty(value = "k")
	public void setK(Integer k) {
		this.k = k;
	}

	@JsonProperty(value = "order")
	public void setOrder(Integer order) {
		this.order = order;
	}

	public String getStudentCode() {
		return studentCode;
	}

	public String getName() {
		return name;
	}

	public Integer getA() {
		return a;
	}

	public Integer getB() {
		return b;
	}

	public Integer getK() {
		return k;
	}

	public Integer getOrder() {
		return order;
	}

	@Override
	public String toString() {
		return "Student [code=" + studentCode + ", name=" + name + ", a=" + a + ", b=" + b + ", k=" + k + "]";
	}

	public Integer getPx() {
		return px;
	}

	@JsonProperty(value = "px")
	public void setPx(Integer px) {
		this.px = px;
	}

	public Integer getPy() {
		return py;
	}

	@JsonProperty(value = "py")
	public void setPy(Integer py) {
		this.py = py;
	}

	public Integer getQx() {
		return qx;
	}

	@JsonProperty(value = "qx")
	public void setQx(Integer qx) {
		this.qx = qx;
	}

	public Integer getQy() {
		return qy;
	}

	@JsonProperty(value = "qy")
	public void setQy(Integer qy) {
		this.qy = qy;
	}

	public Integer getRx() {
		return rx;
	}

	@JsonProperty(value = "rx")
	public void setRx(Integer rx) {
		this.rx = rx;
	}

	public Integer getRy() {
		return ry;
	}

	@JsonProperty(value = "ry")
	public void setRy(Integer ry) {
		this.ry = ry;
	}
	
}
