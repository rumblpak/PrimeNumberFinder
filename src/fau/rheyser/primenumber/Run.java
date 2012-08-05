package fau.rheyser.primenumber;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.text.method.ScrollingMovementMethod;
import android.util.Log;
import android.widget.TextView;
import android.os.Message;
import java.util.concurrent.locks.*;

/**
 * Creates the threads to check if numbers between 0 and 10000 are prime.
 * @author Ryan Heyser
 * @version 1.0
 * @see Activity
 * @see Lock
 * @see ReentrantLock
 * @see ReentrantReadWriteLock * @see Handler
 * @see Thread
 * @see Runnable
 * 
 */
public class Run extends Activity {
	private static final String TAG = "Primes";
	
	public static String numberthreads = "fau.rheyser.primenumber.threads";
	public String primes1 = "";
	public String primes2 = "";
	public String primes3 = "";
	public String primes4 = "";
	public int numPrimes1 = 0;
	public int numPrimes2 = 0;
	public int numPrimes3 = 0;
	public int numPrimes4 = 0;
	
	public static final int threads_one = 1;
	public static final int threads_two = 2;
	public static final int threads_three = 3;
	public static final int threads_four = 4;
	
	public long time1,time2;
	
	private Thread th1;
	private Thread th2;
	private Thread th3;
	private Thread th4;
	
	//private Lock lock = new ReentrantReadWriteLock().writeLock();
	//private Lock handlerlock = new ReentrantReadWriteLock().writeLock();
	
	private int thread;
	private static TextView txt;
	
	/**
	 * Handlers create a message queue to process runnable objects in a specific order
	 * @see Handler
	 * @see Handler.handleMessage
	 */
	private Handler handler = new Handler() {
		/* (non-Javadoc)
		 * @see android.os.Handler#handleMessage(android.os.Message)
		 */
		@Override
		public void handleMessage(Message msg) {
			//Log.d(TAG, "onSendMessage: " + msg.obj.toString());
			//handlerlock.lock();
			txt = (TextView)findViewById(R.id.primes);
			//txt.setText(txt.getText()+primes+System.getProperty("line.separator"));
			//txt.setText("" + msg.obj.toString() + System.getProperty("line.separator"));
			txt.setText(txt.getText() + msg.obj.toString() + "\n");
			txt.setMovementMethod(new ScrollingMovementMethod());
			//handlerlock.unlock();
		}
	};
	
	private Message msg;
	
	/* (non-Javadoc)
	 * @see android.app.Activity#onCreate(android.os.Bundle)
	 */
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		Log.d(TAG, "onCreateRun");
		int threads = getIntent().getIntExtra(numberthreads, 0);
		thread = threadHelper(threads);
		
		super.setContentView(R.layout.run);
		
		if(thread == 1) {
			Log.d(TAG, "onThread = 1");
			
			/*
			 * Create the new thread but does NOT start it
			 * this just defines what the thread will do when it does
			 * get called as th1.start();
			*/
			th1 = new Thread(new Runnable() {
				public void run(){
					for(int i = 0; i < 10000 ; i++) {
						if(IsPrime.isPrime(i)) {
							primes1 = primes1 + String.valueOf(i) + "\n";
							numPrimes1++;
						}
					}
				}
			});
			
			/*
			 * java.lang.System.currentTimeMillis() gets the current system
			 * time in milliseconds, it is useful for timing how long an 
			 * operation has taken
			 */
			time1 = java.lang.System.currentTimeMillis();
			th1.start();
			/*
			 * Checks to see if the thread is still running code.
			 * However, it is buggy and if it has already finished
			 * when the while loop starts, it will cause an infinite loop
			 */
			while(th1.isAlive());
			time2 = java.lang.System.currentTimeMillis();
			msg = handler.obtainMessage();
			msg.obj = primes1;
			handler.sendMessage(msg);
		}
		else if(thread == 2) {
			Log.d(TAG, "onThread = 2");
			th1 = new Thread(new Runnable() {
				public void run(){
					for(int i = 0; i < 5000 ; i++) {
						if(IsPrime.isPrime(i)) {
							primes1 = primes1 + String.valueOf(i) + "\n";
							numPrimes1++;
						}
					}
				}
			});
			th2 = new Thread(new Runnable() {
				public void run(){
					for(int i =5001; i < 10000 ; i++) {
						if(IsPrime.isPrime(i)) {
							primes2 = primes2 + String.valueOf(i) + "\n";
							numPrimes2++;
						}
					}
				}
			});
			time1 = java.lang.System.currentTimeMillis();
			th1.start();
			th2.start();
			//while(th1.isAlive() && th2.isAlive());
			//Must check in reverse order because isAlive freezes in the emulator
			while(th2.isAlive());
			while(th1.isAlive());
			time2 = java.lang.System.currentTimeMillis();
			msg = handler.obtainMessage();
			msg.obj = primes1 + primes2;
			handler.sendMessage(msg);
		}
		else if(thread == 3) {
			Log.d(TAG, "onThread = 3");
			th1 = new Thread(new Runnable() {
				public void run(){
					for(int i = 0; i < 3333 ; i++) {
						if(IsPrime.isPrime(i)) {
							primes1 = primes1 + String.valueOf(i) + "\n";
							numPrimes1++;
						}
					}
				}
			});
			th2 = new Thread(new Runnable() {
				public void run(){
					for(int i =3334; i < 6666 ; i++) {
						if(IsPrime.isPrime(i)) {
							primes2 = primes2 + String.valueOf(i) + "\n";
							numPrimes2++;
						}
					}
				}
			});
			th3 = new Thread(new Runnable() {
				public void run(){
					for(int i =6667; i < 10000 ; i++) {
						if(IsPrime.isPrime(i)) {
							primes3 = primes3 + String.valueOf(i) + "\n";
							numPrimes3++;
						}
					}
				}
			});
			time1 = java.lang.System.currentTimeMillis();
			th1.start();
			th2.start();
			th3.start();
			//while(th1.isAlive() && th2.isAlive() && th3.isAlive());
			//Must check in reverse order because isAlive freezes in the emulator
			while(th3.isAlive());
			while(th2.isAlive());
			while(th1.isAlive());
			time2 = java.lang.System.currentTimeMillis();
			msg = handler.obtainMessage();
			msg.obj = primes1 + primes2 + primes3;
			handler.sendMessage(msg);
		}
		else if(thread == 4) {
			Log.d(TAG, "onThread = 4");
			th1 = new Thread(new Runnable() {
				public void run(){
					for(int i = 0; i < 2500 ; i++) {
						if(IsPrime.isPrime(i)) {
							primes1 = primes1 + String.valueOf(i) + "\n";
							numPrimes1++;
						}
					}
				}
			});
			th2 = new Thread(new Runnable() {
				public void run(){
					for(int i =2501; i < 5000 ; i++) {
						if(IsPrime.isPrime(i)) {
							primes2 = primes2 + String.valueOf(i) + "\n";
							numPrimes2++;
						}
					}
				}
			});
			th3 = new Thread(new Runnable() {
				public void run(){
					for(int i =5001; i < 7500 ; i++) {
						if(IsPrime.isPrime(i)) {
							primes3 = primes3 + String.valueOf(i) + "\n";
							numPrimes3++;
						}
					}
				}
			});
			th4 = new Thread(new Runnable() {
				public void run(){
					for(int i = 7501; i < 10000 ; i++) {
						if(IsPrime.isPrime(i)) {
							primes4 = primes4 + String.valueOf(i) + "\n";
							numPrimes4++;
						}
					}
				}
			});
			time1 = java.lang.System.currentTimeMillis();
			th1.start();
			th2.start();
			th3.start();
			th4.start();
			//while(th1.isAlive() && th2.isAlive() && th3.isAlive() && th4.isAlive());
			//Must check in reverse order because isAlive freezes in the emulator
			while(th4.isAlive());
			while(th3.isAlive());
			while(th2.isAlive());
			while(th1.isAlive());
			time2 = java.lang.System.currentTimeMillis();
			msg = handler.obtainMessage();
			msg.obj = primes1 + primes2 + primes3 + primes4;
			handler.sendMessage(msg);
			
		}
		else {
			Log.d(TAG, "Thread does not equal 1,2,3,4");
		}
		
		//Time in Seconds
		long time = (time2 - time1);
		//primes = "Time to generate in (ms): " + String.valueOf(time);
		msg = handler.obtainMessage();
		msg.obj = "Time to generate in (ms): " + String.valueOf(time);
		handler.sendMessage(msg);
		
		int numPrimes = numPrimes1 + numPrimes2 + numPrimes3 + numPrimes4;
		msg = handler.obtainMessage();
		msg.obj = "Number of Primes Found: " + String.valueOf(numPrimes);
		handler.sendMessage(msg);
		numPrimes1 = numPrimes2 = numPrimes3 = numPrimes4 = 0;
	}
	
	/**
	 * Takes the input from Main Menu's openStartDialog and sets the correct 
	 * number of threads.  While unnecessary for this purpose, if the purpose 
	 * was different it proves useful to check the dialog this way.
	 * @see MainMenu.openStartDialog
	 * @param threads input from openStartDialog
	 * @return Number of threads to start
	 */
	private int threadHelper(int threads) {
		//Log.d(TAG, "threadHelper");
		//Log.d(TAG, String.valueOf(threads));
		int th;
		switch(threads+1) {
		case threads_one:
			th = 1;
			break;
		case threads_two:
			th = 2;
			break;
		case threads_three:
			th = 3;
			break;
		case threads_four:
			th = 4;
			break;
		default:
			Log.d(TAG, "Defaulting in threadHelper");
			th = 1;
			break;
		}
		return th;
	}
	
	
}

