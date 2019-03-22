//PART 1

import java.util.HashMap;

public class DatabaseStandard implements DatabaseInterface{
	private HashMap<String,String> database;
	
	 
	public DatabaseStandard (){
		database = new HashMap <String,String>(); //create initial hash map
	}
	
	//implementation of DatabaseInterface-----------------------------------------------------------------------------
	
	public String save (String plainPassword, String encryptedPassword){
		return database.put(encryptedPassword, plainPassword); 
				
	}
	
	public int size(){
		return database.size();
	}
	
	public String decrypt(String encryptedPassword){
		return database.get(encryptedPassword);
		//returns plain password corresponding to encrypted password
        //OR returns null if no password is associated to the encryptedPassword
	}
	
	public void printStatistics(){
		System.out.println("*** DatabaseStandardStatistics  ***");
		System.out.println("size is " + size() + " passwords");
		System.out.println("initial number of indexes when created: 16 (default)");
		System.out.println("*** End DatabaseStandard Statistics ***");
	}
	
	//End of DatabaseInterface implementation-----------------------------------------------------------------------------

}
