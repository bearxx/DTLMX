package lmx.phone.domain;

import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.Date;

import lmx.phone.esper.EsperPart;
import lmx.phone.util.FileHandler;
import lmx.phone.util.RadAccFileUtil;

/**
 * The records of payment
 * @author MiXian
 *
 */
public class Record {
	private String id;
	private String user;
	private int payment;
	private Date timestamp;
	
	public Record(String id, String user, int payment, Date timestamp) {
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

	/**
	 * create test data
	 * @return
	 */
	public static Record[] getTestRecords() {
		Record[] records = new Record[100];
		for(int i=0; i<99; i++) {
			records[i] = new Record("0000"+i%2,"admin",58,new Date());
		}
		records[99] = new Record("00001","admin",10,new Date());
		return records;
	}
	
	public static void main(String[] s) throws Exception {
		EsperPart.addRealRecord("123","u1",90);
		EsperPart.addRealRecord("124","u2",40);
		EsperPart.addRealRecord("125","u2",40);
	}

	
}
 