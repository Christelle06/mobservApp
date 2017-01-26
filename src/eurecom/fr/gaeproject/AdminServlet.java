package eurecom.fr.gaeproject;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.appengine.api.datastore.DatastoreService;
import com.google.appengine.api.datastore.DatastoreServiceFactory;
import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.EntityNotFoundException;
import com.google.appengine.api.datastore.Key;
import com.google.appengine.api.datastore.KeyFactory;

public class AdminServlet extends HttpServlet {
	public void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
		resp.setContentType("text/html");
		// Let's output the basic HTML headers
		PrintWriter out = resp.getWriter();
		out.println("<html><head><title>Modify a contact</title></head><body>");
		// Get the datastore
		DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();
		// Get the entity by key
		Entity monument = null;
		String name = "", place = "", description = "", price_normal = "", price_student ="", price_kid = "", pict= "", location = "";
		try {
			monument = datastore.get(KeyFactory.stringToKey(req.getParameter("id")));
			name = (monument.getProperty("name") != null) ? (String) monument.getProperty("name") : "";
			place = (monument.getProperty("place") != null) ? (String) monument.getProperty("place") : "";
			description = (monument.getProperty("description") != null) ? (String) monument.getProperty("description") : "";
			price_normal = (monument.getProperty("price_normal") != null) ? (String) monument.getProperty("price_normal") : "";
			price_student = (monument.getProperty("price_student") != null) ? (String) monument.getProperty("price_student") : "";
			price_kid = (monument.getProperty("price_kid") != null) ? (String) monument.getProperty("price_kid") : "";
			pict = (monument.getProperty("pict") != null) ? (String) monument.getProperty("pict") : "";
			location = (monument.getProperty("location") != null) ? (String) monument.getProperty("location") : "";
		} catch (EntityNotFoundException e) {
			resp.getWriter().println("<p>Creating a new contact</p>");
		} catch (NullPointerException e) {
		// id paramenter not present il the URL
			resp.getWriter().println("<p>Creating a new contact</p>");
		}
		out.println("<form action=\"admin\" method=\"post\" name=\"monument\">");
		// Let's build the form
		out.println("<label>Name: </label><input name=\"name\" value=\"" + name + "\"/><br/>"
		+ "<label>Place: </label><input name=\"place\" value=\"" + place + "\"/><br/>"
		+ "<label>Description: </label><input name=\"description\" value=\"" + description + "\"/><br/>"
		+ "<label>Price_normal: </label><input name=\"price_normal\" value=\"" + price_normal + "\"/><br/>"
		+ "<label>Price_student: </label><input name=\"price_student\" value=\"" + price_student + "\"/><br/>"
		+ "<label>Price_kid: </label><input name=\"price_kid\" value=\"" + price_kid + "\"/><br/>"
		+ "<label>Pict: </label><input name=\"pict\" value=\"" + pict + "\"/><br/>"
		+ "<label>Location: </label><input name=\"location\" value=\"" + location + "\"/><br/>");
		out.println("<br/><input type=\"submit\" value=\"Continue\"/></form></body></html>");
	}
		/**
		* Save a contact in the DB. The contact can be new or already existent.
		*/
	public void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
		// Retrieve informations from the request
		String monumentName = req.getParameter("name");
		String monumentPlace = req.getParameter("place");
		String monumentDescription = req.getParameter("description");
		String monumentPriceNormal = req.getParameter("price_normal");
		String monumentPriceStudent = req.getParameter("price_student");
		String monumentPriceKid = req.getParameter("price_kid");
		String monumentPict = req.getParameter("pict");
		String monumentLocation = req.getParameter("location");
		// Take a reference of the datastore
		DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();
		// Generate or retrieve the key associated with an existent contact
		// Create or modify the entity associated with the contact
		Entity monument;
		monument = new Entity("Monument", monumentName);
		monument.setProperty("name", monumentName);
		monument.setProperty("place", monumentPlace);
		monument.setProperty("description", monumentDescription);
		monument.setProperty("price_normal", monumentPriceNormal);
		monument.setProperty("price_student", monumentPriceStudent);
		monument.setProperty("price_kid", monumentPriceKid);
		monument.setProperty("pict", monumentPict);
		monument.setProperty("location", monumentLocation);
		// Save in the Datastore
		datastore.put(monument);
		resp.getWriter().println(req.getParameter("name"));
		resp.getWriter().println("Monument " + monumentName + " saved with key " +
		KeyFactory.keyToString(monument.getKey())+ "!");
	}
}