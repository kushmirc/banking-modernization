public class Complaint_POJO{
	
	String complaint_no;
	String actno;
	String compalaint_date;
	String subject;
	String description;
	String status;
	String closed;
	
	public Complaint_POJO(String complaint_no,String actno,String compalaint_date,String subject,String description,String status,String closed){
		
		this.complaint_no = complaint_no;
		this.actno = actno;
		this.compalaint_date = compalaint_date;
		this.subject = subject;
		this.description = description;
		this.status = status;
		this.closed = closed;
	}
	
	public Complaint_POJO(){
	}
	
	public void setComplaint_no(String complaint_no) {
		this.complaint_no = complaint_no;
	}
	
	
	public String getComplaint_no() {
		return complaint_no;
	}
	
	public void setActno(String actno) {
		this.actno = actno;
	}
	
	public String getActno() {
		return actno;
	}
	
	public void setCompalaint_date(String compalaint_date) {
		this.compalaint_date = compalaint_date;
	}
	
	public String getCompalaint_date() {
		return compalaint_date;
	}
	
	public void setSubject(String subject) {
		this.subject = subject;
	}
	
	public String getSubject() {
		return subject;
	}
	
	public void setDescription(String description) {
		this.description = description;
	}
	
	public String getDescription() {
		return description;
	}	
	
	public void setStatus(String status) {
		this.status = status;
	}	
	
	public String getStatus() {
		return status;
	}
	
	public void setClosed(String closed) {
		this.closed = closed;
	}
	
	public String getClosed() {
		return closed;
	}
	
	
	
}