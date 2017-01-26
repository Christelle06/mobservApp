package eurecom.fr.gaeproject;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.appengine.api.datastore.DatastoreService;
import com.google.appengine.api.datastore.DatastoreServiceFactory;
import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.FetchOptions;
import com.google.appengine.api.datastore.KeyFactory;
import com.google.appengine.api.datastore.Query;
import com.google.appengine.api.datastore.Query.FilterOperator;
import com.google.appengine.repackaged.com.google.gson.Gson;

public class TravelServlet extends HttpServlet {
	public void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
		DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();
		Query query = new Query("Travel").setFilter(new Query.FilterPredicate("user_id", FilterOperator.EQUAL, req.getParameter("id"))).addSort("place", Query.SortDirection.ASCENDING);
		List<Entity> travels = datastore.prepare(query).asList(FetchOptions.Builder.withDefaults());
		Gson gson = new Gson();
		int index = 0;
		List<Travel> results = new ArrayList<Travel>();
		for(Entity travel : travels){
			Travel strContact = new Travel(travel);
			results.add(strContact);
		}
		String jsonTravel = gson.toJson(results);
		PrintWriter out = resp.getWriter();
		out.println(jsonTravel);
	}
	public void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
		String travelPlace = req.getParameter("place");
		String travelArrivalDate = req.getParameter("arrival_date");
		String travelDepartureDate = req.getParameter("departure_date");
		String travelUserId = req.getParameter("user_id");
		DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();
		Entity travel;
		travel = new Entity("Travel", travelPlace+travelUserId);
		travel.setProperty("place", travelPlace);
		travel.setProperty("arrival_date", travelArrivalDate);
		travel.setProperty("departure_date", travelDepartureDate);
		travel.setProperty("user_id", travelUserId);
		datastore.put(travel);
		resp.getWriter().println(KeyFactory.keyToString(travel.getKey()));
	}
}
