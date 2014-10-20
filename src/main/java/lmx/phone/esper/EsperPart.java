package lmx.phone.esper;

import java.io.IOException;
import java.io.RandomAccessFile;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.log4j.Logger;

import lmx.phone.domain.DJSystem;
import lmx.phone.domain.PhoneResult;
import lmx.phone.domain.Phone;
import lmx.phone.epl.Epl;
import lmx.phone.listener.PayRecListerner;
import lmx.phone.util.RadAccFileUtil;

import com.espertech.esper.client.EPAdministrator;
import com.espertech.esper.client.EPRuntime;
import com.espertech.esper.client.EPServiceProvider;
import com.espertech.esper.client.EPServiceProviderManager;
import com.espertech.esper.client.EPStatement;

public class EsperPart {
	private final static Logger logger = Logger.getLogger(EsperPart.class);
	/**
	 * the next six static fields are what is used by esper
	 */
	private static EPServiceProvider epService = EPServiceProviderManager.getDefaultProvider();
	private static EPAdministrator admin = epService.getEPAdministrator();
	private static EPRuntime runtime = epService.getEPRuntime();
	private static EPStatement showState;
	private static EPStatement insertState;
	private static EPStatement triState;

	private static SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	
	/**
	 *  phoneList is the data stored in memory
	 */
	public static List<Phone> phoneList = new ArrayList<Phone>();
	
	/**
	 *  result is the result produced by esper,
	 *  that means that id a payment is 30 less than the average pay,
	 *  there will be a result store as <b>PhoneResult</b> 
	 */
	public static List<PhoneResult> result = new ArrayList<PhoneResult>();
	/**
	 *  whether the result is a new one, if it is, the method should take <b>result List<PhoneResult> </b>
	 *  and reset the <b>isNewRes</b> to false
	 */
	public static boolean isNewRes = false;
	
	/**
	 * if the esper part about phone is initialed, this will be true
	 */
	public static boolean isReady = false;
	
	public static void runEsper() throws Exception{
		RandomAccessFile raf = RadAccFileUtil.getFileHander();
		logger.info("phone esper is running");
		isReady = true;
		while(DJSystem.isWoriking) {
			Thread.sleep(100);
			// if new record is not handled
			if(RadAccFileUtil.isDealed == false) {
				System.out.println("|| readed line length: " + RadAccFileUtil.readedOffset);
				System.out.println("|| file length : " + raf.length());
				try {
					int allLength = (int) raf.length();
					while(RadAccFileUtil.readedOffset < allLength) {
						raf.seek(RadAccFileUtil.readedOffset);
						String line = raf.readLine();
						RadAccFileUtil.readedOffset += line.length()+2;
						dealLineData(line);
					}
					RadAccFileUtil.isDealed = true;
					RadAccFileUtil.readedLength = raf.length();
				} catch (IOException e) {
//					throw e;
				}
			}
		}
	}
	
	/**
	 * stop esper and abandon file resource
	 */
	public static void stop() throws Exception{
		showState.destroy();
		insertState.destroy();
		triState.destroy();
//		runtime.
//		admin.stopAllStatements();
//		admin.
//		epService.removeAllStatementStateListeners();
		DJSystem.isWoriking = false;
		isReady = false;
		RadAccFileUtil.getFileHander().close();
	}
	
	/**
	 * add records to file, this will not send the event to runtime.
	 * 
	 * @param id
	 * @param user
	 * @param payment
	 * @throws Exception
	 */
	public static void addRealRecordToFile(String id, String user, int payment) throws Exception{
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String dataStr = id+"#"+user+"#"+payment+"#"+sdf.format(new Date())+(System.getProperty("line.separator"));
		logger.info("This is what is added to the tail of the file: " + dataStr);
		RandomAccessFile raf = RadAccFileUtil.getFileHander();
		try {
			System.out.println(raf.length());
			raf.seek(RadAccFileUtil.readedOffset);
			raf.write(dataStr.getBytes());
			RadAccFileUtil.isDealed = false;
		} catch(Exception e) {
			logger.error("Write file exception");
			throw(e);
		}
	}
	
	/**
	 * initialize all need to run the system
	 * @throws Exception
	 */
	public static void init() throws Exception {
		// init the esper environment
		initEsper();
		// deal with the data that is already in data.txt
		RandomAccessFile raf = RadAccFileUtil.getFileHander();
		if(raf.length() > 2) {
			while(raf.getFilePointer() < raf.length()) {
				String line = raf.readLine();
				dealLineData(line);
				logger.info("Already happened event in file : " + line);
				RadAccFileUtil.readedOffset += line.length()+2;
				raf.seek(RadAccFileUtil.readedOffset);
			}			
		}
		RadAccFileUtil.isDealed = true;
		RadAccFileUtil.readedLength = RadAccFileUtil.readedOffset = raf.length();
	}
	
	/**
	 * initialize the environment of Esper
	 */
	private static void initEsper() {
		admin.createEPL(Epl.ctxCreate);
		showState = admin.createEPL(Epl.ctxSelect);
		insertState = admin.createEPL(Epl.insertAvg);
		triState = admin.createEPL(Epl.triggerAlert);
		
		showState.addListener(PayRecListerner.getShowListener());
		insertState.addListener(PayRecListerner.getAvgListener());
		triState.addListener(PayRecListerner.getTriggerListener());

		showState.start();
		insertState.start();
		triState.start();
	}
	
	/**
	 * parse a line of string, create the event, send it.
	 * @param line : the string presentation for event
	 * @throws Exception
	 */
	private static void dealLineData(String line) throws Exception {
		String[] datas = line.split("#");
		runtime.sendEvent(new Phone(datas[0],datas[1],
				Integer.valueOf(datas[2]), sdf.parse(datas[3])));
	}
	
	public static void main(String[] args) throws Exception{
		System.out.println("esper starting");
		EsperPart.init();
		EsperPart.runEsper();
		Thread.sleep(3000);
		addRealRecordToFile("1", "u", 1000);
		addRealRecordToFile("1", "u", 1001);
		addRealRecordToFile("1", "u", 1003);
	}
}
