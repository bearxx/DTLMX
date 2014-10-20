package lmx.phone.util;

import java.io.FileNotFoundException;
import java.io.RandomAccessFile;

public class RadAccFileUtil {
	
	/**
	 * The handler of randAccessFile
	 */
	private static RandomAccessFile raf = null;
	/**
	 * The offset already read by esper
	 */
	public static long readOffset = 0;
	/**
	 * Whether new record is dealt by esper
	 */
	public static boolean isDealt = true;
	
	/**
	 * Whether the connection and resources are closed
	 */
	public static boolean isClosed = true;
	
	/**
	 * get RandomAccessFile to handle the file 'data.txt'
	 * @return
	 */
	public static RandomAccessFile getFileHander() {
		if(raf == null || isClosed == true) {
			System.out.println("++++  null and return new");
			synchronized(RadAccFileUtil.class) {
				try {
					raf = new RandomAccessFile("data.txt","rw");
				} catch (FileNotFoundException e) {
					e.printStackTrace();
				}
			}
		}
		return raf;
	}
}
