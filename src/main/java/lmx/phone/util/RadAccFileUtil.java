package lmx.phone.util;

import java.io.FileNotFoundException;
import java.io.RandomAccessFile;

public class RadAccFileUtil {
	
	private static RandomAccessFile raf = null;
	public static long readedOffset = 0;
	public static long readedLength = 0;
	public static boolean isDealed = true;
	
	/**
	 * get RandomAccessFile to handle the file 'data.txt'
	 * @return
	 */
	public static RandomAccessFile getFileHander() {
		if(raf == null) {
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
