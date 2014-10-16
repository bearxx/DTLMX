package lmx.phone.main;

import java.util.HashMap;
import java.util.Map;


/**
 * This is a test of analysis of one's phone records
 * If one's payment drop fast, it means that he or she want to change number or service package
 * @author MiXian
 *
 */
public class Test {

	public static void main(String[] args) throws Exception {
//		EsperPart.runEsper();
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("success", true);
		map.put("data", "huhuh");
		map.put("message", "result");
//		"{"success":true,"data":"huhuh","message":"result"}"
		System.out.println(map);
		
	}

}
