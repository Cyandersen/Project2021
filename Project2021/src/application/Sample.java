package application;

public class Sample {
	private String procedure;
	private String date;
	private String department;
	private Double number;
	private Double charges;
	private Double total;

	public Sample()
	{
		this(null,null);
		
	}
	
	public Sample(String date, String procedure)
	{
		this.procedure=procedure;
		this.date=date;
		this.department="";
		this.number=0.0;
		this.charges=0.0;
		this.total=0.0;

	}
	
	public String getProcedure() {
		return procedure;
	}
	public void setProcedure(String procedure) {
		this.procedure = procedure;
	}
	public String getDate() {
		return date;
	}
	public void setDate(String date) {
		this.date = date;
	}
	public String getDepartment() {
		return department;
	}
	public void setDepartment(String department) {
		this.department = department;
	}
	public Double getNumber() {
		return number;
	}
	public void setNumber(Double number) {
		this.number = number;
	}
	public Double getCharges() {
		return charges;
	}
	public void setCharges(Double charges) {
		this.charges = charges;
	}

	public Double getTotal() {
		return total;
	}

	public void setTotal(Double total) {
		this.total = total;
	}
	
	
}