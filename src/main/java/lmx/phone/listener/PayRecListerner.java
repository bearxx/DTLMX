package lmx.phone.listener;

import com.espertech.esper.client.EventBean;
import com.espertech.esper.client.UpdateListener;

public class PayRecListerner {

	public static UpdateListener getShowListener() {
		return new UpdateListener() {
			public void update(EventBean[] newEs, EventBean[] oldEs) {
				int len = newEs.length;
				for(int i = 0 ; i < len ; i++) {
					System.out.println("[event]:" + newEs[i].get("id")+"-"+newEs[i].get("payment"));
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
				System.out.println("fwl");
				int len = newEs.length;
				for(int i = 0 ; i < len ; i++) {
					System.out.println("[[ trigger ]]" + newEs[0].get("id") + ": " + newEs[i].get("user")
							+ ": " + newEs[i].get("newPay") + ": " + newEs[i].get("avgPayment"));
				}
			}
		};
	}

}
