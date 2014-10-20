package lmx.phone.epl;

import lmx.phone.domain.Phone;

public class Epl {
	/**
	 * create context of record partition by its id
	 */
	public final static String ctxCreate = "create context PayCtx partition by id from " + Phone.class.getName();
	
	/**
	 * select context items, once a new event happened, this will be triggered
	 */
	public final static String ctxSelect = "context PayCtx select * from " + Phone.class.getName();
	
	/**
	 * insert the average payment of last 3 times to r_stream, this will add the average payment to listener's newEvent[],
	 * but attention: if there are 3 new events, and when the new one comes, the 3 old events will produce a average payment,
	 * and be added to the newEvent[] with the index of 0 .
	 */
	public final static String insertAvg = "context PayCtx insert rstream into AvgRec(id, user, avgPay) "
						+ " select u.id as id, u.user as user, avg(payment) as avgPay from " + Phone.class.getName() + ".win:length(3) as u";
	
	/**
	 * when this event happened, you should inform client, example China Mobile, that this gay may change his pay style.
	 * This event is that a user paid 30 less than the average payment, and this change may affect
	 * its function provided by client,  
	 */
	public final static String triggerAlert = "select a.avgPay as avgPayment, a.id as id, r.user as user, r.payment as newPay from "
						+ Phone.class.getName() + " as r unidirectional, "
						+ " AvgRec.std:unique(id).win:length(100) as a"
						+ " where a.id = r.id and a.avgPay >= (r.payment + 30)";
}
