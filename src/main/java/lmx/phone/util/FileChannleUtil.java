package lmx.phone.util;

import java.io.FileNotFoundException;
import java.io.RandomAccessFile;
import java.nio.channels.FileChannel;

/**
 * get the writer and reader for random access file
 * @author MiXian
 *
 */
public class FileChannleUtil {
	
	public static FileChannel fcWriter = null;
	public static FileChannel fcReader = null;
	
	public static FileChannel getFileChannelWriter() {
		if(fcWriter == null) {
			synchronized (FileChannel.class) {
				try {
					fcWriter = new RandomAccessFile("data.text","rw").getChannel();
				} catch (FileNotFoundException e) {
					e.printStackTrace();
				}
			}
		}
		return fcWriter;
	}
	
	public static FileChannel getFileChannelReader() {
		if(fcWriter == null) {
			synchronized (FileChannel.class) {
				try {
					fcWriter = new RandomAccessFile("data.text","r").getChannel();
				} catch (FileNotFoundException e) {
					e.printStackTrace();
				}
			}
		}
		return fcWriter;
	}
}
