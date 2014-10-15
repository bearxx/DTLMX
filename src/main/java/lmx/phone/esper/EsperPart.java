package lmx.phone.esper;

import java.io.IOException;
import java.io.RandomAccessFile;
import java.text.SimpleDateFormat;
import java.util.Date;

import lmx.phone.domain.DJSystem;
import lmx.phone.domain.Record;
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
	public static EPServiceProvider epService = EPServiceProviderManager.getDefaultProvider();
	public static EPAdministrator admin = epService.getEPAdministrator();
	public static EPRuntime runtime = epService.getEPRuntime();

	private static SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	
	public static void runEsper() throws Exception{
		while(DJSystem.isWoriking) {
			RandomAccessFile raf = RadAccFileUtil.getFileHander();
			// if new record is not handled
			if(RadAccFileUtil.isDealed == false || RadAccFileUtil.readedOffset < raf.length()) {
				System.out.println("|| readed line length: " + RadAccFileUtil.readedOffset);
				System.out.println("|| file length : " + raf.length());
				try {
					raf.seek(RadAccFileUtil.readedOffset);
					int allLength = (int) raf.length();
					while(RadAccFileUtil.readedOffset < allLength) {
						raf.seek(RadAccFileUtil.readedOffset+1);
						String line = raf.readLine();
						System.out.println("|| line is : "+line);
						System.out.println("|| line length is : "+line.length());
						RadAccFileUtil.readedOffset += line.length()+1;
						dealLineData(line);
					}
					RadAccFileUtil.isDealed = true;
					RadAccFileUtil.readedLength = raf.length();
				} catch (IOException e) {
					
				}
			}
		}
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
	public static void initEsper() {
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
		runtime.sendEvent(new Record(datas[0],datas[1],
				Integer.valueOf(datas[2]), sdf.parse(datas[3])));
	}
	
	public static void main(String[] args) throws Exception{
		EsperPart.init();
		EsperPart.runEsper();
	}
}
