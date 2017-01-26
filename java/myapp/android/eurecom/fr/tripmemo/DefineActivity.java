package myapp.android.eurecom.fr.tripmemo;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.app.LoaderManager;
import android.content.Intent;
import android.content.Loader;
import android.content.res.Resources;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.DatePicker;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;
import java.text.ParseException;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by alexandrefradet on 21/01/2017.
 */
public class DefineActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<Object> {

    ImageButton calendarB1, calendarB2;
    Calendar c = Calendar.getInstance();
    int arrivalYear = c.get(Calendar.YEAR);
    int arrivalMonth = c.get(Calendar.MONTH);
    int arrivalDay = c.get(Calendar.DAY_OF_MONTH);
    int departureYear = c.get(Calendar.YEAR);
    int departureMonth = c.get(Calendar.MONTH);
    int departureDay = c.get(Calendar.DAY_OF_MONTH);
    AutoCompleteTextView textView;
    String place = null;
    String arrival_date = null;
    String departure_date = null;
    Calendar arrival_calendar = Calendar.getInstance();
    Calendar departure_calendar = Calendar.getInstance();

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_define);
        // Get a reference to the AutoCompleteTextView in the layout
        textView = (AutoCompleteTextView) findViewById(R.id.autocomplete_country);
        // Get the string array
        String[] countries = getResources().getStringArray(R.array.countries_array);
        // Create the adapter and set it to the AutoCompleteTextView
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, countries);

        //Get layout elements
        textView.setAdapter(adapter);
        calendarB1 = (ImageButton) findViewById(R.id.imageButton);
        calendarB1.setImageResource(R.drawable.calendar);
        calendarB2 = (ImageButton) findViewById(R.id.imageButton2);
        calendarB2.setImageResource(R.drawable.calendar);

        //If the travel exists already update the variables
        if(getIntent().getStringExtra("arrival_date") != null) {
            place = getIntent().getStringExtra("place");
            arrival_date = getIntent().getStringExtra("arrival_date");
            departure_date = getIntent().getStringExtra("departure_date");
            arrivalDay = Integer.parseInt(arrival_date.substring(0, 2));
            arrivalMonth = Integer.parseInt(arrival_date.substring(3, 5))-1;
            arrivalYear = Integer.parseInt(arrival_date.substring(6));
            departureDay = Integer.parseInt(departure_date.substring(0, 2));
            departureMonth = Integer.parseInt(departure_date.substring(3, 5))-1;
            departureYear = Integer.parseInt(departure_date.substring(6));
            arrival_calendar.set(arrivalYear, arrivalMonth, arrivalDay);
            departure_calendar.set(departureYear, departureMonth, departureDay);
        }
        //Set layout elements
        TextView dateText = (TextView) findViewById(R.id.textView6);
        dateText.setText(arrival_date);
        TextView dateText2 = (TextView) findViewById(R.id.textView7);
        dateText2.setText(departure_date);
        textView.setText(place);
    }

    @Override
    public Loader<Object> onCreateLoader(int id, Bundle args) {
        String arrivalDate = arrivalDay +"-"+ arrivalMonth + "-" + arrivalYear;
        String departureDate = departureDay +"-"+ departureMonth + "-" + departureYear;
        String place = String.valueOf(textView.getText());
        TravelSender loader = new TravelSender(this,getIntent().getStringExtra("id"),place,arrivalDate,departureDate);
        loader.forceLoad();
        return loader;
    }

    @Override
    public void onLoadFinished(Loader<Object> loader, Object data) {

    }

    @Override
    public void onLoaderReset(Loader<Object> loader) {

    }

    //Class to implement the calendar to set up the arrival date
    class ArrivalDatePicker extends DialogFragment implements DatePickerDialog.OnDateSetListener{
        @Override
        public Dialog onCreateDialog(Bundle savedInstanceState) {
            // TODO Auto-generated method stub
            // Use the current date as the default date in the picker
            DatePickerDialog dialog = new DatePickerDialog(DefineActivity.this, this, arrivalYear, arrival_calendar.get(Calendar.MONTH), arrivalDay);
            return dialog;

        }
        public void onDateSet(DatePicker view, int year, int monthOfYear,
                              int dayOfMonth) {
            // TODO Auto-generated method stub
            // Do something with the date chosen by the user
            arrivalYear = year;
            arrivalMonth = monthOfYear;
            arrivalDay = dayOfMonth;

            //Set the calendar object for arrival
            arrival_calendar = Calendar.getInstance();
            arrival_calendar.set(Calendar.YEAR, arrivalYear);
            arrival_calendar.set(Calendar.MONTH, arrivalMonth);
            arrival_calendar.set(Calendar.DAY_OF_MONTH, arrivalDay);

            //Check if the arrival date is right (i.e. arrival after today & before departure)
            if(arrival_calendar.after(c) && (departure_calendar.after(arrival_calendar) ||
                    departure_calendar.get(Calendar.DAY_OF_MONTH) == c.get(Calendar.DAY_OF_MONTH) && departure_calendar.get(Calendar.MONTH) == c.get(Calendar.MONTH)
                    && departure_calendar.get(Calendar.YEAR) == c.get(Calendar.YEAR))) {
                arrivalMonth += 1;
                TextView dateText = (TextView) findViewById(R.id.textView6);
                String month = (arrivalMonth <= 9) ? ("0" + arrivalMonth) : (""+arrivalMonth);
                String date = arrivalDay + "-" + month + "-" + arrivalYear;
                arrival_date = date;
                dateText.setText(date);
                Log.d("UPDATE", "Update Arrival");
            }else{
                arrival_calendar = Calendar.getInstance();
                arrivalYear = c.get(Calendar.YEAR);
                arrivalMonth = c.get(Calendar.MONTH);
                arrivalDay = c.get(Calendar.DAY_OF_MONTH);
                TextView dateText = (TextView) findViewById(R.id.textView6);
                dateText.setText("");
                Toast.makeText(DefineActivity.this, "The beginning of the travel must be after today.", Toast.LENGTH_SHORT).show();
            }
        }
    }

    //Same as the previous class but for departure date
    class DepartureDatePicker extends DialogFragment implements DatePickerDialog.OnDateSetListener{
        @Override
        public Dialog onCreateDialog(Bundle savedInstanceState) {
            // TODO Auto-generated method stub
            // Use the current date as the default date in the picker
            DatePickerDialog dialog = null;
            if(departure_calendar.before(arrival_calendar)) {
                dialog = new DatePickerDialog(DefineActivity.this, this, arrivalYear, arrival_calendar.get(Calendar.MONTH), arrivalDay);
            }else{
                dialog = new DatePickerDialog(DefineActivity.this, this, departure_calendar.get(Calendar.YEAR),
                        departure_calendar.get(Calendar.MONTH), departure_calendar.get(Calendar.DAY_OF_MONTH));
            }
            return dialog;

        }
        public void onDateSet(DatePicker view, int year, int monthOfYear,
                              int dayOfMonth) {
            // TODO Auto-generated method stub
            // Do something with the date chosen by the user
            departureYear = year;
            departureMonth = monthOfYear;
            departureDay = dayOfMonth;
            departure_calendar = Calendar.getInstance();
            departure_calendar.set(Calendar.YEAR, departureYear);
            departure_calendar.set(Calendar.MONTH, departureMonth);
            departure_calendar.set(Calendar.DAY_OF_MONTH, departureDay);
            if(departure_calendar.after(arrival_calendar) && departure_calendar.after(c)) {
                departureMonth += 1;
                TextView dateText2 = (TextView) findViewById(R.id.textView7);
                String month = (departureMonth <= 9) ? ("0" + departureMonth) : (""+departureMonth);
                String date2 = departureDay + "-" + month+ "-" + departureYear;
                departure_date = date2;
                dateText2.setText(date2);
                Log.d("UPDATE", "Update Departure");
            }else{
                departure_calendar = Calendar.getInstance();
                departureYear = c.get(Calendar.YEAR);
                departureMonth = c.get(Calendar.MONTH);
                departureDay = c.get(Calendar.DAY_OF_MONTH);
                TextView dateText2 = (TextView) findViewById(R.id.textView7);
                dateText2.setText("");
                Toast.makeText(DefineActivity.this, "The end of the travel must be after the beginning.", Toast.LENGTH_SHORT).show();
            }
        }
    }

    public void arrivalDate(View v){
        DialogFragment dialogFragment = new ArrivalDatePicker();
        dialogFragment.show(getFragmentManager(), "arrival_date_picker");
    }
    public void departureDate(View v){
        DialogFragment dialogFragment = new DepartureDatePicker();
        dialogFragment.show(getFragmentManager(), "departure_date_picker");
    }

    //Go to the next step is everything is alright
    public void travelPlan(final View v){
        Resources res = getResources();
        String[] countries = res.getStringArray(R.array.countries_array);
        if(Arrays.asList(countries).contains(textView.getText().toString())) {
            if(arrival_date != null && departure_date != null) {
                getLoaderManager().restartLoader(0, null, this);
                Intent intent = new Intent(getBaseContext(), TravelActivity.class);
                startActivity(intent);
            }else{
                Toast.makeText(DefineActivity.this, "Unvalid date", Toast.LENGTH_SHORT).show();
            }
        }else{
            Toast.makeText(DefineActivity.this, "Unvalid place to visit", Toast.LENGTH_SHORT).show();
        }
    }
    public void send(User user) throws IOException {
        getLoaderManager().restartLoader(0, null, this);
    }
}
