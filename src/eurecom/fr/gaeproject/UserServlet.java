package eurecom.fr.gaeproject;

import java.io.IOException;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.appengine.api.datastore.DatastoreService;
import com.google.appengine.api.datastore.DatastoreServiceFactory;
import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.KeyFactory;

public class UserServlet extends HttpServlet {
	public void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
		String userId = req.getParameter("id");
		String userFirstName = req.getParameter("first_name");
		String userLastName = req.getParameter("last_name");
		String userEmail = req.getParameter("email");
		DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();
		Entity user;
		user = new Entity("User", userId);
		user.setProperty("id", userId);
		user.setProperty("first_name", userFirstName);
		user.setProperty("last_name", userLastName);
		user.setProperty("email", userEmail);
		datastore.put(user);
		resp.getWriter().println("User " + userId + " saved with key " +
				KeyFactory.keyToString(user.getKey())+ "!");
	}
}
