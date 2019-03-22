//PART 2

import java.util.AbstractMap.SimpleEntry;

public class DatabaseMine implements DatabaseInterface {
	// this is a prime number that gives the number of addresses
    // these constructors must create your hash tables with enough positions N
    // to hold the entries you will insert; you may experiment with primes N
	
	private int N; //number of addresses, prime number
	private SimpleEntry<String,String>[] database; //hash map will be stored in an array of Entry<K, V> elements 
	private int totalProbes= 0;
	private int timesProbed = 0;
	private int size = 0; //size of hash table
	private boolean isFull = false; // check if the hash map is full
	private int displacements = 0;
	
	// here you pick suitable default N
	public DatabaseMine (){ //default constructor
		N = 79997; //large prime number 
		database= new SimpleEntry[N];	
	}
	 
	// here the N is given by the user
	public DatabaseMine(int N){ //Second constructor that overrides previous constructor
		this.N = N;
		database = new SimpleEntry[N];
	}
	
	int hashFunction (String key){
		int address=key.hashCode()%N;
		return (address>=0)?address:(address+N); //return encrypted password
		}
	
	//implementation of DatabaseInterface----------------------------------------------------------------------------------------------------------------
	
	public String save (String plainPassword, String encryptedPassword){
		
		if (size == N){ //if capacity of hash table is full
			System.out.println("hash table is full. no more encrypted passwords can be added");
			return null;
		}
		
		else{ //if hash table is not full
			int value = hashFunction (encryptedPassword);
			int linearProbing = 0;
			int numberOfProbes = 1; // 1 probe always done by default
			
			// if address has already been taken, increment the number of displacements 
			if(database[(value)%N]!= null){
				if (!database[(value)%N].getKey().equals(encryptedPassword)){
					displacements++;
				}
			}
			
			while ((database[(value+ linearProbing)%N]!=null) && (!(database[(value+linearProbing)%N].getKey().equals(encryptedPassword)))){
				linearProbing ++;
				numberOfProbes++;
			}
			
			totalProbes+=numberOfProbes;
			timesProbed++;
			
			if(database[(value+linearProbing)%N] == null){
				database[(value + linearProbing)%N] = new SimpleEntry(encryptedPassword, plainPassword);
				size++;
				return null;
			}
			else {// identical key already exists in the database
				String old = database[(value + linearProbing) % N].getValue();
				database [(value+linearProbing) % N].setValue(plainPassword);
				return old;
			}
			
		}
	}
	
	public int size(){
		return size;
	}
	
	
	
	public String decrypt (String encrytpedPassword){
		for (int i = 0; i < database.length; i++){
			if(database[i] != null){
				if(database[i].getKey().equals(encrytpedPassword)){
					return database[i].getValue(); //returns plain password corresponding to encrypted password
				}
			}
			
		}
		return null; //return null if no encryptedPassword matches
	}
	
	public void printStatistics() {
		// important statistics must be collected (here or during construction) and printed here: size, number of indexes, load factor, average number of probes and average number of displacements (see output example below)
		double loadFactor = (double) size/N; //load factor formula is n/N
		double avgProbes =(double) totalProbes/timesProbed; //calculate average number of probes
		System.out.println("*** DatabaseStandardStatistics  ***");
		System.out.println("size is " + size() + " passwords");
		System.out.println("number of indexes is " + N );
		System.out.println("load Factor is " + loadFactor);
		System.out.println("average number of probes is " + avgProbes);
		System.out.println("number of displacements (due to collisions) is " + displacements);
		System.out.println("*** End DatabaseStandard Statistics ***");
			
	}
	
	//End of DatabaseInterface implementation----------------------------------------------------------------------------------------------------------------
}
