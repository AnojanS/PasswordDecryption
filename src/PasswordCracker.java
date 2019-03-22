//PARTS 1 and 2

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Calendar;

public class PasswordCracker {
	 void createDatabase(ArrayList<String> commonPassword, DatabaseInterface database){
		
		// receives list of passwords and populates database with entries consisting
	    // of (key,value) pairs where the value is the password and the key is the
	    // encrypted password (encrypted using Sha1)
	    // in addition to passwords in commonPasswords list, this class is responsible to add mutated passwords according to rules 1-5.
		
		try{
		
			for (int i=0; i < commonPassword.size(); i++){
				database.save(commonPassword.get(i), Sha1.hash(commonPassword.get(i))); //original password's encryption
				
				Calendar x = Calendar.getInstance(); 
				String currentYear = String.valueOf(x.get(Calendar.YEAR));
				
				//Rule 1
				if (Character.isLetter(commonPassword.get(i).charAt(0))){ 
					
					String rule1 = commonPassword.get(i).substring(0, 1).toUpperCase() + commonPassword.get(i).substring(1);
					database.save(rule1, Sha1.hash(rule1));
					
					//rules 1,3,5 combined
					String rules135= commonPassword.get(i).substring(0, 1).toUpperCase() + commonPassword.get(i).substring(1);
					rules135 = rules135.replace("i", "1").replace("e", "3");
					database.save(rules135, Sha1.hash(rules135));
					
					//rules 1,2,3,5 combined
					String rules1234= commonPassword.get(i).substring(0, 1).toUpperCase() + commonPassword.get(i).substring(1);
					rules1234 = rules1234.replace("a", "@").replace("e", "3") + currentYear;
					database.save(rules1234, Sha1.hash(rules1234));
					
					//rules 1,2,3,4,5 combined
					String rules12345 = commonPassword.get(i).substring(0, 1).toUpperCase() + commonPassword.get(i).substring(1);
					rules12345 = rules12345.replace("a", "@").replace("e", "3").replace("i", "1") + currentYear;
					database.save(rules12345, Sha1.hash(rules12345));
				}
				
				//Rule2
				String rule2 = commonPassword.get(i)+ currentYear;
				database.save(rule2, Sha1.hash(rule2));
				
				//Rule3
				String rule3 = commonPassword.get(i).replace("a", "@");
				database.save(rule3, Sha1.hash(rule3));
				
					//rules 2,3 combined
					String rules23= rule3 +currentYear;
					database.save(rules23,Sha1.hash(rules23));
				
				//Rule4
				String rule4 = commonPassword.get(i).replace("e", "3");
				database.save(rule4, Sha1.hash(rule4));
				
					//rules 2,3,4 combined
					String rules234= commonPassword.get(i).replace("e", "3").replace("a", "@") +currentYear;
					database.save(rules234,Sha1.hash(rules234));
				
				//Rule 5
				String rule5 = commonPassword.get(i).replaceAll("i", "1");
				database.save(rule5, Sha1.hash(rule5));
				
					//rules 3,5 combined
					String rules35 = commonPassword.get(i).replace("e", "3").replace("i", "1");
					database.save(rules35,Sha1.hash(rules35));
					
			}
			
		}catch (UnsupportedEncodingException e) {
				System.out.println("could not hash using SHA1");
			}
		}
	 
	//Crack encrypted password using the database
	 String crackPassword(String encryptedPassword, DatabaseInterface database){
		 String decryptedPassword = database.decrypt(encryptedPassword);
		 if(decryptedPassword != null){
			 return decryptedPassword; //return original password 
		 }
		 else{
			 return "encrypted password not found"; //if password not found in database
		 }
			
	 }
		
	}


