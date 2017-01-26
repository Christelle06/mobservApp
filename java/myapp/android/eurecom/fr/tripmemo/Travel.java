package myapp.android.eurecom.fr.tripmemo;

import android.support.annotation.NonNull;
import android.text.format.DateFormat;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

/**
 * Created by alexandrefradet on 21/01/2017.
 */
public class Travel {
    private final String user_id;
    private final String place;
    private final Date arrival_date;
    private final Date departure_date;
    SimpleDateFormat format = new SimpleDateFormat("dd-MM-yyyy");

    public Travel(String place, Date arrival_date, Date departure_date, String user_id){
        this.place = place;
        this.arrival_date = arrival_date;
        this.departure_date = departure_date;
        this.user_id = user_id;
    }

    public Travel(JSONObject jsonObject) throws JSONException, ParseException {
        user_id = jsonObject.getString("user_id");
        place = jsonObject.getString("place");
        arrival_date = format.parse(jsonObject.getString("arrival_date"));
        departure_date = format.parse(jsonObject.getString("departure_date"));
    }
    public String toString(){
        return String.format("%s : \nArrival: %s - %s - %s \nDeparture: %s - %s - %s", place, DateFormat.format("dd", arrival_date), DateFormat.format("MMM", arrival_date),DateFormat.format("yyyy", arrival_date),
                DateFormat.format("dd", departure_date), DateFormat.format("MMM", departure_date),DateFormat.format("yyyy", departure_date));
    }

    public String get(String arg1){
        switch (arg1){
            case "user_id":
                return user_id;
            case "arrival_date":
                return DateFormat.format("dd", arrival_date)+"-"+DateFormat.format("MM", arrival_date)+"-"+DateFormat.format("yyyy", arrival_date);
            case "departure_date":
                return DateFormat.format("dd", departure_date)+"-"+DateFormat.format("MM", departure_date)+"-"+DateFormat.format("yyyy", departure_date);
            case "place":
                return place;
        }
        String error = "Error";
        return error;
    }
}
