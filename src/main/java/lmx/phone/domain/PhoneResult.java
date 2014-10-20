package lmx.phone.domain;

/**
 * This is what is discovered by esper,
 * and how they are stored in the stream
 * @author MiXian
 *
 */
public class PhoneResult {
	
	private String id;
	private String user;
	private String newPayment;
	private String avgPayment;
	
	public PhoneResult(String id, String user,
			String newPayment, String avgPayment) {
		this.id = id;
		this.user = user;
		this.newPayment = newPayment;
		this.avgPayment = avgPayment;
	}
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getUser() {
		return user;
	}
	public void setUser(String user) {
		this.user = user;
	}
	public String getNewPayment() {
		return newPayment;
	}
	public void setNewPayment(String newPayment) {
		this.newPayment = newPayment;
	}
	public String getAvgPayment() {
		return avgPayment;
	}
	public void setAvgPayment(String avgPayment) {
		this.avgPayment = avgPayment;
	}
	
	public String toString() {
		return "PhoneResult :[id:"+id+" ,name:"+user+" ,last paid:"+newPayment+" ,average in last 3 payments:"+avgPayment+"]";
	}
	

}
