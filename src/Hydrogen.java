package molecule;

public class Hydrogen extends Thread {

	private static int carbonCounter =0;
	private int id; // used to identify the number of the hydrogen atom
	private Methane sharedMethane; //gives hydrogen access to the methane elements
	
	
	public Hydrogen(Methane methane_obj) {
		Hydrogen.carbonCounter+=1;
		id=carbonCounter;
		this.sharedMethane = methane_obj;
	}
	
	public void run() {
	    try {
	    	sharedMethane.mutex.acquire();//acquire lock
			sharedMethane.addHydrogen();
			if ((sharedMethane.getHydrogen() >= 4) &&(sharedMethane.getCarbon() >= 1)){
				System.out.println("---Group ready for bonding---"); //which ever atom comes first will run statement
				sharedMethane.hydrogensQ.release(4);
				sharedMethane.removeHydrogen(4);//remove the 4 hydrogens atoms
				sharedMethane.carbonQ.release();
				sharedMethane.removeCarbon(1); //remove carbon atom
			}
			else{
				sharedMethane.mutex.release();//release lock
			}
			
			sharedMethane.hydrogensQ.acquire();
			sharedMethane.bond("H"+ this.id); //atom bonds with others

			sharedMethane.barrier.b_wait();//waits for other threads
	    }
	   	catch (InterruptedException ex) { /* not handling this  */}
	}
}
