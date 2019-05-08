package molecule;

public class Carbon extends Thread {
	
	private static int carbonCounter =0;
	private int id; // returns the id of a atom
	private Methane sharedMethane;//provides access to methane molecule
	
	public Carbon(Methane methane_obj) {
		Carbon.carbonCounter+=1;
		id=carbonCounter;
		this.sharedMethane = methane_obj;
	}
	
	public void run() {
	    try {
	    	sharedMethane.mutex.acquire();//thread must acquire lock
			sharedMethane.addCarbon();//accesses the carbon atom from methane
			if (sharedMethane.getHydrogen() >= 4){
				System.out.println("---Group ready for bonding---"); //which ever atom comes first will run statement
				sharedMethane.hydrogensQ.release(4);
				sharedMethane.removeHydrogen(4);
				sharedMethane.carbonQ.release();
				sharedMethane.removeCarbon(1);//removes the carbon molecule
			}
			else{
				sharedMethane.mutex.release();//thread releases lock
			}

			sharedMethane.carbonQ.acquire(); // 
			sharedMethane.bond("C"+ this.id); //atom bonds with others


			sharedMethane.barrier.b_wait();//threads waiting at barrier
			sharedMethane.mutex.release();//lock released
	    }
	    catch (InterruptedException ex) { /* not handling this  */}
	}

}