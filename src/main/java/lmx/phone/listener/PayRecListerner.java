package lmx.phone.listener;

import java.util.ArrayList;
import java.util.List;

import lmx.phone.domain.Phone;
import lmx.phone.domain.PhoneResult;
import lmx.phone.esper.EsperPart;

import com.espertech.esper.client.EventBean;
import com.espertech.esper.client.UpdateListener;

public class PayRecListerner {

	public static UpdateListener getShowListener() {
		return new UpdateListener() {
			public void update(EventBean[] newEs, EventBean[] oldEs) {
				int len = newEs.length;
				for(int i = 0 ; i < len ; i++) {
						System.out.println("[event]:" + newEs[i].get("id")+"-"+newEs[i].get("payment")
								+ newEs[i].get("timestamp")+ newEs[i].get("user"));
						EsperPart.phoneList.add(new Phone(
								(String)newEs[i].get("id"), 
								(String)newEs[i].get("user"),
								String.valueOf(newEs[i].get("payment")), 
								String.valueOf(newEs[i].get("timestamp"))));
				}
			}
			
		};
	}
	
	public static UpdateListener getAvgListener() {
		return new UpdateListener() {
			public void update(EventBean[] newEs, EventBean[] oldEs) {
				int len = newEs.length;
				for(int i = 0 ; i < len ; i++) {
					System.out.println("[avg]:"+newEs[i].get("id")+"-"+newEs[i].get("user")+"-"+newEs[i].get("avgPay"));
				}
			}
		};
	}

	public static UpdateListener getTriggerListener() {
		return new UpdateListener() {
			public void update(EventBean[] newEs, EventBean[] oldEs) {
				List<PhoneResult> res = new ArrayList<PhoneResult>();
				System.out.println("fwl");
				int len = newEs.length;
				for(int i = 0 ; i < len ; i++) {
					System.out.println("[[ trigger ]]" + newEs[i].get("id") + ": " + newEs[i].get("user")
							+ ": " + newEs[i].get("newPay") + ": " + newEs[i].get("avgPayment"));
					res.add( new PhoneResult((String)newEs[i].get("id"), (String)newEs[i].get("user"),
							String.valueOf(newEs[i].get("newPay")), String.valueOf(newEs[i].get("avgPayment"))) );
				}
				EsperPart.result = res;
			}
		};
	}

}
