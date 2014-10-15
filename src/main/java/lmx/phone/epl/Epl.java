package lmx.phone.epl;

import lmx.phone.domain.Record;

public class Epl {
	/**
	 * create context of record partition by its id
	 */
	public final static String ctxCreate = "create context PayCtx partition by id from " + Record.class.getName();
	
	/**
	 * select context items
	 */
	public final static String ctxSelect = "context PayCtx select * from " + Record.class.getName();
	
	/**
	 * insert the average payment of last 3 times to stream
	 */
	public final static String insertAvg = "context PayCtx insert rstream into AvgRec(id, user, avgPay) "
						+ " select u.id as id, u.user as user, avg(payment) as avgPay from " + Record.class.getName() + ".win:length(3) as u";
	
	/**
	 * when this event happened, you should inform user
	 */
	public final static String triggerAlert = "select a.avgPay as avgPayment, a.id as id, r.user as user, r.payment as newPay from AvgRec.std:unique(id).win:length(100) as a, "
						+ Record.class.getName() + " as r unidirectional where a.id = r.id and a.avgPay >= (r.payment + 30)";
}
