package lmx.phone.domain;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

/**
 * The records of payment.
 * It works as the event stream in real world,
 * imitating a user in the system of purchasing services
 * @author MiXian
 *
 */
public class Phone {
	
	private final static SimpleDateFormat sdf = new SimpleDateFormat("EEE MMM dd HH:mm:ss z yyyy", Locale.US);
	
	private String id;
	/**
	 * this is just a name, nothing more.
	 * id is the real identifier.
	 */
	private String user;
	private int payment;
	private Date timestamp;
	
	public Phone(String id, String user, String payment, String timestamp) {
		this.id = id;
		this.user = user;
		this.payment = Integer.valueOf(payment);
		try {
			this.timestamp = sdf.parse(timestamp);
		} catch (ParseException e) {
			e.printStackTrace();
		}
	}
	
	public Phone(String id, String user, int payment, Date timestamp)  {
		this.id = id;
		this.user = user;
		this.payment = payment;
		this.timestamp = timestamp;
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
	public int getPayment() {
		return payment;
	}
	public void setPayment(int payment) {
		this.payment = payment;
	}
	public Date getTimestamp() {
		return timestamp;
	}
	public void setTimestamp(Date timestamp) {
		this.timestamp = timestamp;
	}
	
	public String toString() {
		return "Phone:[id:"+id+" ,name:"+user+" ,last paid:"+payment+" ,time:"+timestamp+"]";
	}

}
 