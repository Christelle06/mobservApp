package myapp.android.eurecom.fr.tripmemo;

import android.content.AsyncTaskLoader;
import android.content.Context;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.CharBuffer;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by alexandrefradet on 22/01/2017.
 */
public class TravelLoader extends AsyncTaskLoader<List<Travel>>{

    private String id;

    public TravelLoader(Context context, String id) {
        super(context);
        Log.i("main", "loader created");
        this.id = id;
    }

    @Override
    public List<Travel> loadInBackground() {
        Log.i("main", "loadInBackground");
        URL url;
        String jsonString = null;
        try {
            url = new URL("http://0-dot-mobservproject-156713.appspot.com/travel?id="+id);
            HttpURLConnection client = (HttpURLConnection) url.openConnection();
            InputStream stream = client.getInputStream();
            Reader reader = new InputStreamReader(stream, "UTF-8");
            jsonString = readAll(reader);
            Log.i("CHECK1", String.valueOf(client.getResponseCode()));
            Log.i("CHECK2",jsonString);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        if (jsonString != null) {
            List<Travel> result = null;
            try {
                JSONArray listOfTravels = new JSONArray(jsonString);
                int len = listOfTravels.length();
                result = new ArrayList<Travel>(len);
                Log.i("main", "json string len is " + len);

                for (int i = 0; i < len; i++) {
                    result.add(new Travel(listOfTravels.getJSONObject(i)));
                }
            } catch (JSONException e) {
                // TODO handle
                e.printStackTrace();
                return null;
            } catch (ParseException e) {
                e.printStackTrace();
            }
            return result;
        }else
            return null;
    }

    public static String readAll(Reader reader) throws IOException {

        StringBuilder builder = new StringBuilder(4096);
        for (CharBuffer buf = CharBuffer.allocate(512); (reader.read(buf)) > -1; buf.clear()) {
            builder.append(buf.flip());
        }
        return builder.toString();
    }
}
