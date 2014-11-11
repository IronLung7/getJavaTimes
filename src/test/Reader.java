package test;

import java.io.*;
import java.io.File;import java.io.FileInputStream;import java.io.FileNotFoundException;import java.io.IOException;import java.lang.Exception;import java.lang.String;import java.lang.System;import java.util.concurrent.locks.ReentrantLock;

/**
 * Created by zhul9 on 11/11/2014.
 */
public class Reader {
	private int count = 0;
	private final ReentrantLock lock = new ReentrantLock();
	private final int READ_COUNT=17;

	public void run(){
		File f = new File("C:\\Users\\zhul9\\Desktop\\test.txt");
		FileInputStream fis = null;

		try {
			fis = new FileInputStream(f);
			byte[] bytes = new byte[READ_COUNT];
			byte[] boundary = new byte[READ_COUNT];
			int boundaryCount=0;
			int n=0;

			if((n=fis.read(bytes))!=-1){
				Task task = new Task(bytes,this);//TODO
				task.run();
				if(n == READ_COUNT){
					boundary[boundaryCount]= bytes[READ_COUNT-3];
					boundary[boundaryCount+1]=bytes[READ_COUNT-2];
					boundary[boundaryCount+2]=bytes[READ_COUNT-1];
					boundaryCount += 3;
				}
			}
			while((n=fis.read(bytes))!=-1)
			{
				Task task = new Task(bytes,this);//TODO
				task.run();
				if(n>3){
					boundary[boundaryCount]= bytes[0];
					boundary[boundaryCount+1]=bytes[1];
					boundary[boundaryCount+2]=bytes[2];
					boundaryCount += 3;
				}
				if(boundaryCount -6 <READ_COUNT){
					Task boundaryTask = new Task(boundary,this);//TODO
					boundaryTask.run();
					boundaryCount=0;
					boundary=new byte[READ_COUNT];
				}
				if(n == READ_COUNT){
					boundary[boundaryCount]= bytes[READ_COUNT-3];
					boundary[boundaryCount+1]=bytes[READ_COUNT-2];
					boundary[boundaryCount+2]=bytes[READ_COUNT-1];
					boundaryCount += 3;
				}

			}
			Task boundaryTask = new Task(boundary,this);//TODO
			boundaryTask.run();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	public void addCount(int add) {
		try{
			lock.lock();
			count +=add;
		}
		catch(Exception e){
			e.printStackTrace();
		}
		finally{
			System.out.println("释放"+" add:"+add+"  count:"+count);
			lock.unlock();
		}
	}


	public static void main(String args[]){
		Reader reader = new Reader();
		reader.run();
	}
}
