package lmx.phone.util;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;

public class FileHandler {
	
	private boolean isChanged = false;
	
	public static Integer offset = 0;
	public static Integer length = 0;
	
	public void updateFileMeta(Integer offset,Integer length) {
		this.setChanged(true);
		this.setOffset(offset);
		this.setLength(length);
	}
	
	public void afterRead() {
		this.setChanged(false);
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	public Integer getOffset() {
		return offset;
	}

	public void setOffset(Integer offset) {
		FileHandler.offset = offset;
	}

	public Integer getLength() {
		return length;
	}

	public void setLength(Integer length) {
		FileHandler.length = length;
	}
    
    public boolean isChanged() {
		return isChanged;
	}

	public void setChanged(boolean isChanged) {
		this.isChanged = isChanged;
	}

	/** 
	 *  
	 * @param skip 	: the length to skip
	 * @param str 	: the string to insert
	 * @param fileName : file name
	 */  
	@SuppressWarnings("resource")
	public static void beiju(long skip, String str, String fileName){  
	    try {  
	        RandomAccessFile raf = new RandomAccessFile(fileName,"rw");  
	        if(skip <  0 || skip > raf.length()){  
	            System.out.println("invalid param 'skip', requirs greater than 0 and less than the length of the file");  
	            return ;  
	        }  
	        byte[] b = str.getBytes();  
	        raf.setLength(raf.length() + b.length);
	        // carry the bytes to leave space of str
	        for(long i = raf.length() - 1; i > b.length + skip - 1; i--){  
	            raf.seek(i - b.length);  
	            byte temp = raf.readByte();  
	            raf.seek(i);  
	            raf.writeByte(temp);  
	        }  
	        raf.seek(skip);  
	        raf.write(b);  
	        raf.close();  
	    } catch (Exception e) {  
	        e.printStackTrace();  
	    }  
	}  
	
	public static void main(String[] args) throws Exception {  
        // Ԥ�����ļ���ռ�Ĵ��̿ռ䣬�����лᴴ��һ��ָ����С���ļ�  
        RandomAccessFile raf = new RandomAccessFile("D://abc.txt", "rw");  
        raf.setLength(1024*1024); // Ԥ���� 1M ���ļ��ռ�  
        raf.close();  
          
        // ��Ҫд����ļ�����  
        String s1 = "��һ���ַ���";  
        String s2 = "�ڶ����ַ���";  
        String s3 = "�������ַ���";  
        String s4 = "���ĸ��ַ���";  
        String s5 = "������ַ���";  
          
        // ���ö��߳�ͬʱд��һ���ļ�  
        new FileWriteThread(1024*1,s1.getBytes()).start(); // ���ļ���1024�ֽ�֮��ʼд������  
        new FileWriteThread(1024*2,s2.getBytes()).start(); // ���ļ���2048�ֽ�֮��ʼд������  
        new FileWriteThread(1024*3,s3.getBytes()).start(); // ���ļ���3072�ֽ�֮��ʼд������  
        new FileWriteThread(1024*4,s4.getBytes()).start(); // ���ļ���4096�ֽ�֮��ʼд������  
        new FileWriteThread(1024*5,s5.getBytes()).start(); // ���ļ���5120�ֽ�֮��ʼд������  
    }

	// �����߳����ļ���ָ��λ��д��ָ������  
    static class FileWriteThread extends Thread{  
        private int skip;  
        private byte[] content;  
          
        public FileWriteThread(int skip,byte[] content){  
            this.skip = skip;  
            this.content = content;  
        }  
          
        public void run(){  
            RandomAccessFile raf = null;  
            try {  
                raf = new RandomAccessFile("D://abc.txt", "rw");  
                raf.seek(skip);  
                raf.write(content);  
            } catch (FileNotFoundException e) {  
                e.printStackTrace();  
            } catch (IOException e) {  
                // TODO Auto-generated catch block  
                e.printStackTrace();  
            } finally {  
                try {  
                    raf.close();  
                } catch (Exception e) {  
                }  
            }  
        }  
    }  
  

}
