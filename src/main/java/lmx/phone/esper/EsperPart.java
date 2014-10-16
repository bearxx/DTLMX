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
import lmx.phone.util.FileHandler;
import lmx.phone.util.RadAccFileUtil;

import com.espertech.esper.client.EPAdministrator;
import com.espertech.esper.client.EPRuntime;
import com.espertech.esper.client.EPServiceProvider;
import com.espertech.esper.client.EPServiceProviderManager;
import com.espertech.esper.client.EPStatement;

public class EsperPart {
	private final static Logger logger = Logger.getLogger(EsperPart.class);
	
	public static EPServiceProvider epService = EPServiceProviderManager.getDefaultProvider();
	public static EPAdministrator admin = epService.getEPAdministrator();
	public static EPRuntime runtime = epService.getEPRuntime();

	private static SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	
	// phoneList is the data stored in memory
	public static List<Phone> phoneList = new ArrayList<Phone>();
	
	// result is the result produced by esper
	public static List<PhoneResult> result = new ArrayList<PhoneResult>();
	
	public static void runEsper() throws Exception{
		RandomAccessFile raf = RadAccFileUtil.getFileHander();
		logger.info("phone esper is running");
		while(DJSystem.isWoriking) {
			// if new record is not handled
			if(RadAccFileUtil.isDealed == false || RadAccFileUtil.readedOffset < raf.length()) {
				System.out.println("|| readed line length: " + RadAccFileUtil.readedOffset);
				System.out.println("|| file length : " + raf.length());
				try {
					raf.seek(RadAccFileUtil.readedOffset);
					int allLength = (int) raf.length();
					while(RadAccFileUtil.readedOffset < allLength) {
						raf.seek(RadAccFileUtil.readedOffset);
						String line = raf.readLine();
						System.out.println("|| line is : "+line);
						System.out.println("|| line length is : "+line.length());
						RadAccFileUtil.readedOffset += line.length()+2;
						dealLineData(line);
					}
					RadAccFileUtil.isDealed = true;
					RadAccFileUtil.readedLength = raf.length();
					System.out.println("##:" + phoneList.size());
					System.out.println("##:" + result.size());
				} catch (IOException e) {
					throw e;
				}
			}
		}
	}
	
	/**
	 * stop esper and abandon file resource
	 */
	public static void stop() throws Exception{
		DJSystem.isWoriking = false;
		RadAccFileUtil.getFileHander().close();
	}
	
	/**
	 * add records to file
	 * @param id
	 * @param user
	 * @param payment
	 * @throws Exception
	 */
	public static void addRealRecord(String id, String user, int payment) throws Exception{
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String dataStr = id+"#"+user+"#"+payment+"#"+sdf.format(new Date())+"\n";
		RandomAccessFile raf = RadAccFileUtil.getFileHander();
		try {
			System.out.println(raf.length());
			raf.seek(FileHandler.offset);
			raf.write(dataStr.getBytes());
			RadAccFileUtil.isDealed = false;
		} catch(Exception e) {
			System.out.println("write exception");
			throw(e);
		}
	}
	
	/**
	 * initialize all need to run the system
	 * @throws Exception
	 */
	public static void init() throws Exception {
		initEsper();
		// deal with the data that is already in data.txt
		RandomAccessFile raf = RadAccFileUtil.getFileHander();
		while(raf.getFilePointer() < raf.length()) {
			String line = raf.readLine();
			dealLineData(line);
		}
		RadAccFileUtil.isDealed = true;
		RadAccFileUtil.readedLength = RadAccFileUtil.readedOffset = raf.length();
	}
	
	/**
	 * initialize the environment of Esper
	 */
	private static void initEsper() {
		admin.createEPL(Epl.ctxCreate);
		EPStatement showState = admin.createEPL(Epl.ctxSelect);
		EPStatement insertState = admin.createEPL(Epl.insertAvg);
		EPStatement triState = admin.createEPL(Epl.triggerAlert);
		
		showState.addListener(PayRecListerner.getShowListener());
		insertState.addListener(PayRecListerner.getAvgListener());
		triState.addListener(PayRecListerner.getTriggerListener());
	}
	
	/**
	 * parse a line of string, create the event, send it
	 * @param line
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
	}
}
