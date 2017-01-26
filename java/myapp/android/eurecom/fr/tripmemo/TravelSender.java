package myapp.android.eurecom.fr.tripmemo;

import android.content.AsyncTaskLoader;
import android.content.Context;
import android.util.Log;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.Reader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.AbstractMap;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by alexandrefradet on 25/01/2017.
 */
public class TravelSender extends AsyncTaskLoader {

    private final String place;
    private final String arrival_date;
    private final String departure_date;
    private final String user_id;

    public TravelSender(Context context, String user_id, String place, String arrival_date, String departure_date) {
        super(context);
        this.place = place;
        this.arrival_date = arrival_date;
        this.departure_date = departure_date;
        this.user_id = user_id;
    }


    @Override
    public Object loadInBackground() {
        String strUrl = "http://0-dot-mobservproject-156713.appspot.com/travel";
        HttpURLConnection conn = null;
        try {
            URL url = new URL(strUrl);
            conn = (HttpURLConnection) url.openConnection();
            conn.setReadTimeout(15000);
            conn.setConnectTimeout(15000);
            conn.setRequestMethod("POST");
            conn.setDoOutput(true);
            conn.setDoInput(true);
            conn.setChunkedStreamingMode(0);

            List<AbstractMap.SimpleEntry> params = new ArrayList<>();
            params.add(new AbstractMap.SimpleEntry("place", place));
            params.add(new AbstractMap.SimpleEntry("arrival_date", arrival_date));
            params.add(new AbstractMap.SimpleEntry("departure_date", departure_date));
            params.add(new AbstractMap.SimpleEntry("user_id", user_id));
            Log.d("CHECK", String.valueOf(params));
            OutputStream os = conn.getOutputStream();
            BufferedWriter writer = new BufferedWriter(
                    new OutputStreamWriter(os, "UTF-8"));
            writer.write(UserLoader.getQuery(params));
            writer.flush();
            writer.close();
            os.close();

            String jsonString;
            InputStream stream = conn.getInputStream();
            Reader reader = new InputStreamReader(stream, "UTF-8");
            jsonString = TravelLoader.readAll(reader);

            conn.connect();
            Log.d("CHECK", String.valueOf(conn.getResponseCode()) + ": " + jsonString);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (conn != null)
                conn.disconnect();
        }
        return null;
    }
}
