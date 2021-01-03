import java.util.Random;
import java.util.concurrent.Semaphore;

public class ReadersWritersSemaphore {
	
	public static void main(String[] args) {

		Database d = new Database();

		Reader r1 = new Reader(d);
		Reader r2 = new Reader(d);
		Reader r3 = new Reader(d);
		Reader r4 = new Reader(d);
		Reader r5 = new Reader(d);


		Writer w1 = new Writer(d,1);
		Writer w2 = new Writer(d,10);
		Writer w3 = new Writer(d,100);
		Writer w4 = new Writer(d,1000);
		Writer w5 = new Writer(d,10000);
		
		w1.start();
		w2.start();
		r1.start();
		r2.start();
		r3.start();
		r4.start();
		r5.start();
		w3.start();
		w4.start();
		w5.start();

	}
}

class Database { 
	int data = 0;
	int r = 0; // # active readers
	int w = 0; // # active writers (0 or 1)
	int ww = 0; // # waiting writers
	int wr = 0; // # waiting readers

	// Referred for semaphore https://docs.oracle.com/en/java/javase/11/docs/api/java.base/java/util/concurrent/Semaphore.html

	// s1 -> Translating synchronized methods, s2_r -> Handling waiting readers, s2_w -> Handling waiting writers
	Semaphore s1 = new Semaphore(1); 
	Semaphore s2_r = new Semaphore(0); 
	Semaphore s2_w = new Semaphore(0);


	public void request_read() {
	try {
				s1.acquire(); // Get s1 lock
	while (w == 1 || ww > 0) { // WW gives priority to waiting writers
					wr++; // Incrementing waiting readers count
					s1.release(); // Release s1 lock
					s2_r.acquire(); //Get reader lock
					//wait();
					s1.acquire(); // Get s1 lock
					wr--; // Decrementing waiting readers count
				}
				r++; // Increment active reader count
				s1.release(); // Release s1 lock
			} catch (Exception e) {
				System.err.println("ERROR: "+e);
			}
		}

	public void done_read() {
	try {
		s1.acquire(); // Get s1 lock
		r--; 
		s1.release(); // Release s1 lock

		// When waiting writers are found when a read is complete, release all queued writers and readers
	if (w==1 ||ww > 0) {
		if(s2_w.getQueueLength()>0) {
			s2_w.release(s2_w.getQueueLength());
		}
	}
	else {
		
		if(s2_r.getQueueLength()>0) {
			s2_r.release(s2_r.getQueueLength());
		}
	}

	}
	catch (Exception e) {
		System.err.println("ERROR: "+e);
	}

	//notifyAll();
		}

	public void request_write() {
	try {
				s1.acquire(); // Acquire synchronization lock
				while (r > 0 || w == 1) {
					ww++; 
					s1.release(); // Release sync lock
					s2_w.acquire(); // Acquire writing lock.
					//wait();
					s1.acquire(); // Get sync lock
					ww--;
				}
				w = 1;
				s1.release(); // Release sync lock
			} catch (Exception e) {
				System.err.println("ERROR: "+e);
			}

		}


	public void done_write() {
	try {
				s1.acquire(); // Acquire sync lock.
				w = 0;
				s1.release(); // Release sync lock.
			
				// When waiting writers are found when a read is complete, release all queued writers and readers
				if (w==1 ||ww > 0) {
					if(s2_w.getQueueLength()>0) {
						s2_w.release(s2_w.getQueueLength());
					}
				}
				else {
					
					if(s2_r.getQueueLength()>0) {
						s2_r.release(s2_r.getQueueLength());
					}
				}

	}
		 catch (Exception e) {
				System.err.println("ERROR: "+e);
			}

	//notifyAll();
		}


	int read() {
	return data;
		}
	void write(int x) {
			data = data + x;
		}

}

class Reader extends Thread {
	Database d;

	public Reader(Database d) {
		this.d = d;
	}

	public void run() {

		for (int i = 0; i < 5; i++){		
			d.request_read();
			System.out.println(d.read());
			d.done_read();
			
		}
	}
}

class Writer extends Thread {
	Database d;
	int x;

	public Writer(Database d, int x) {
		this.d = d;
		this.x = x;
	}

	public void run() {
		for (int i = 0; i < 5; i++) {			
			d.request_write();
			d.write(x);
			try { Thread.sleep(150); }
			catch (Exception e) {}
			d.done_write();
		}
	}
}

