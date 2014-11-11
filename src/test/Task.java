package test;

import java.lang.Override;import java.lang.Thread;import java.util.concurrent.locks.ReentrantLock;

/**
 * Created by zhul9 on 11/11/2014.
 */
public class Task extends Thread{
	private byte[] bytes;
	private Reader reader;

	public Task(){
	}

	public Task(byte[] bytes, Reader reader){
		this.bytes = bytes;
		this.reader = reader;
	}

	@Override
	public void run() {
		super.run();
		int i=0;
		int count=0;
		while (i<bytes.length-3){
			if(bytes[i]==106 && bytes[i+1]==97 && bytes[i+2]==118 && bytes[i+3]==97){
				count++;
				i+=3;
			}else{
				i++;
			}
		}
		reader.addCount(count);
	}

}
