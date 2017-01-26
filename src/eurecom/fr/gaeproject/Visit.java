package eurecom.fr.gaeproject;

public class Visit {
	
	String monument;
	String day;
	int moment;
	String travel_id;
	
	public Visit(String monument, String day, int moment, String travel_id){
		this.monument = monument;
		this.day = day;
		this.moment = moment;
		this.travel_id = travel_id;
	}
	
	public String get(String arg1){
		switch (arg1){
			case "travel_id":
				return this.travel_id;
			case "monument":
				return this.monument;
			case "day":
				return this.day;
		}
		String error = "Error";
		return error;
	}
	
	public int get_moment(){
		return this.moment;
	}
}
