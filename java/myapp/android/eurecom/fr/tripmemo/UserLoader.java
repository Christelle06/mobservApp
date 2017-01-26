package myapp.android.eurecom.fr.tripmemo;

import android.content.AsyncTaskLoader;
import android.content.Context;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.Reader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.nio.CharBuffer;
import java.text.ParseException;
import java.util.AbstractMap;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by alexandrefradet on 25/01/2017.
 */
public class UserLoader extends AsyncTaskLoader<Object>{

    User user;
    public UserLoader(Context context, User user) {
        super(context);
        Log.i("main", "loader created");
        this.user = user;
    }

    @Override
    public List<Travel> loadInBackground() {
        Log.i("main", "loadInBackground");
        URL url = null;
        HttpURLConnection conn = null;
        try {
            url = new URL("http://0-dot-mobservproject-156713.appspot.com/user");
            conn = (HttpURLConnection) url.openConnection();
            conn.setReadTimeout(15000);
            conn.setConnectTimeout(15000);
            conn.setRequestMethod("POST");
            conn.setDoOutput(true);
            conn.setDoInput(true);
            conn.setChunkedStreamingMode(0);
            List<AbstractMap.SimpleEntry> params = new ArrayList<>();
            params.add(new AbstractMap.SimpleEntry("id", user.get("id")));
            params.add(new AbstractMap.SimpleEntry("first_name", user.get("first_name")));
            params.add(new AbstractMap.SimpleEntry("last_name", user.get("last_name")));
            params.add(new AbstractMap.SimpleEntry("email", user.get("email")));
            Log.d("CHECK", String.valueOf(params));
            OutputStream os = conn.getOutputStream();
            BufferedWriter writer = new BufferedWriter(
                    new OutputStreamWriter(os, "UTF-8"));
            writer.write(getQuery(params));
            writer.flush();
            writer.close();
            os.close();

            String jsonString;
            InputStream stream = conn.getInputStream();
            Reader reader = new InputStreamReader(stream, "UTF-8");
            jsonString = TravelLoader.readAll(reader);

            conn.connect();
            Log.d("CHECK", String.valueOf(conn.getResponseCode())+": "+jsonString);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            if(conn != null)
                conn.disconnect();
        }
        return null;
        }


    static String getQuery(List<AbstractMap.SimpleEntry> params) throws UnsupportedEncodingException
    {
        StringBuilder result = new StringBuilder();
        boolean first = true;

        for (AbstractMap.SimpleEntry pair : params)
        {
            if (first)
                first = false;
            else
                result.append("&");

            result.append(URLEncoder.encode(String.valueOf(pair.getKey()), "UTF-8"));
            result.append("=");
            result.append(URLEncoder.encode(String.valueOf(pair.getValue()), "UTF-8"));
        }

        return result.toString();
    }
}
