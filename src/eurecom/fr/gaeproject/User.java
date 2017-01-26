package eurecom.fr.gaeproject;

public class User {
	
	String id;
	String first_name;
	String last_name;
	String email;
	
	public User(String id, String first_name, String last_name, String email){
		this.id = id;
		this.first_name = first_name;
		this.last_name = last_name;
		this.email = email;
	}
	public String get(String arg1){
		switch (arg1){
			case "id":
				return this.id;
			case "first_name":
				return this.first_name;
			case "last_name":
				return this.last_name;
			case "email":
				return this.email;
		}
		String error = "Error";
		return error;
	}
}
