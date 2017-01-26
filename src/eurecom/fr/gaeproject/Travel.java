package eurecom.fr.gaeproject;

import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.KeyFactory;

public class Travel {

	String user_id;
	String place;
	String arrival_date;
	String departure_date;
	String id;
	
	public Travel(String user_id, String place, String arrival_date, String departure_date, String id){
		this.user_id = user_id;
		this.place = place;
		this.arrival_date = arrival_date;
		this.departure_date = departure_date;
		this.id = id;
	}
	public Travel(Entity entity) {
		  // TODO get properties from entity and populate POJO
		  this.user_id=(String) entity.getProperty("user_id");
		  this.place=(String) entity.getProperty("place");
		  this.arrival_date=(String) entity.getProperty("arrival_date");
		  this.departure_date=(String) entity.getProperty("departure_date");
		  this.id=(String) entity.getProperty("id");
	}
	
	public String get(String arg1){
		switch (arg1){
			case "user_id":
				return this.user_id;
			case "id":
				return this.id;
			case "place":
				return this.place;
			case "arrival_date":
				return this.arrival_date;
			case "departure_date":
				return this.departure_date;
		}
		String error = "Error";
		return error;
	}
	
	public void set_place(String place){
		this.place = place;
	}
	public void set_arrival_date(String arrival_date){
		this.arrival_date = arrival_date;
	}
	public void set_departure_date(String departure_date){
		this.departure_date = departure_date;
	}
}
