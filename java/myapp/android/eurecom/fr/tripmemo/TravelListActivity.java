package myapp.android.eurecom.fr.tripmemo;

import android.app.LoaderManager;
import android.content.Intent;
import android.content.Loader;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.List;

/**
 * Created by alexandrefradet on 22/01/2017.
 */
public class TravelListActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<List<Travel>> {

    private ListView listView;
    private String ind;
    private boolean anyTravel;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_travels);

        listView = (ListView) findViewById(R.id.listView);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Travel travel = (Travel) listView.getItemAtPosition(position);
                setView(travel);
            }
        });
        ind = getIntent().getStringExtra("id");
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        return false;
    }

    @Override
    protected void onResume() {
        super.onResume();

        Log.i("main", "onResume");
        getLoaderManager().restartLoader(0, null, this);
    }

    @Override
    public Loader<List<Travel>> onCreateLoader(int id, Bundle args) {
        Log.i("main", "creating loader");
        TravelLoader loader = new TravelLoader(this,ind);
        loader.forceLoad();
        return loader;
    }

    @Override
    public void onLoadFinished(Loader<List<Travel>> arg0, List<Travel> arg1) {
        Log.i("main", "onLoadFinished");
        anyTravel = (arg1.size() != 0);
        Log.i("CHECK", String.valueOf(anyTravel));
        listView.setAdapter(new ArrayAdapter<Travel>(this, android.R.layout.simple_list_item_1, arg1));
    }

    @Override
    public void onLoaderReset(Loader<List<Travel>> arg0) {
        // TODO Auto-generated method stub

    }

    public void setView(Travel travel){
        Intent intent = new Intent(getBaseContext(), DefineActivity.class);
        intent.putExtra("id",ind);
        intent.putExtra("place",travel.get("place"));
        intent.putExtra("arrival_date", travel.get("arrival_date"));
        intent.putExtra("departure_date",travel.get("departure_date"));
        startActivity(intent);
    }
    public void newTravel(View v){
        Intent intent = new Intent(getBaseContext(), DefineActivity.class);
        intent.putExtra("id",ind);
        startActivity(intent);
    }
}
