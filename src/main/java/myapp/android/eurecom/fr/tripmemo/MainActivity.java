package myapp.android.eurecom.fr.tripmemo;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.Context;
import android.content.DialogInterface;
import android.content.res.TypedArray;
import android.database.DataSetObserver;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.BaseAdapter;
import android.widget.CompoundButton;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Gallery;
import android.widget.GridView;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    Calendar c = Calendar.getInstance();
    int arrivalYear = c.get(Calendar.YEAR);
    int arrivalMonth = c.get(Calendar.MONTH);
    int arrivalDay = c.get(Calendar.DAY_OF_MONTH);
    int departureYear = c.get(Calendar.YEAR);
    int departureMonth = c.get(Calendar.MONTH);
    int departureDay = c.get(Calendar.DAY_OF_MONTH);
    ImageButton calendarB1, calendarB2;
    Date scheduled_event;
    List<Date> time = new ArrayList<Date>();
    SimpleDateFormat format = new SimpleDateFormat("dd-MM-yyyy");
    String[] strDate = {"20-11-2017","21-11-2017","22-11-2017","23-11-2017"};


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        try {
            for(int i=0;i<strDate.length;i++) {
                Date date = format.parse(strDate[i]);
                time.add(date);
            }
        } catch (ParseException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

    }

    public void nextStep(View v){
        setContentView(R.layout.activity_define);
        // Get a reference to the AutoCompleteTextView in the layout
        AutoCompleteTextView textView = (AutoCompleteTextView) findViewById(R.id.autocomplete_country);
        // Get the string array
        String[] countries = getResources().getStringArray(R.array.countries_array);
        // Create the adapter and set it to the AutoCompleteTextView
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, countries);
        textView.setAdapter(adapter);
        calendarB1 = (ImageButton) findViewById(R.id.imageButton);
        calendarB1.setImageResource(R.drawable.calendar);
        calendarB2 = (ImageButton) findViewById(R.id.imageButton2);
        calendarB2.setImageResource(R.drawable.calendar);
    }

    class ArrivalDatePicker extends DialogFragment implements DatePickerDialog.OnDateSetListener{
        @Override
        public Dialog onCreateDialog(Bundle savedInstanceState) {
            // TODO Auto-generated method stub
            // Use the current date as the default date in the picker
            DatePickerDialog dialog = new DatePickerDialog(MainActivity.this, this, arrivalYear, arrivalMonth, arrivalDay);
            return dialog;

        }
        public void onDateSet(DatePicker view, int year, int monthOfYear,
                              int dayOfMonth) {
            // TODO Auto-generated method stub
            // Do something with the date chosen by the user
            arrivalYear = year;
            arrivalMonth = monthOfYear;
            arrivalDay = dayOfMonth;
            TextView dateText = (TextView) findViewById(R.id.textView6);
            String date = arrivalDay+"/"+(arrivalMonth+1)+"/"+arrivalYear;
            dateText.setText(date);
            Log.d("UPDATE", "Update Arrival");
        }
    }

    class DepartureDatePicker extends DialogFragment implements DatePickerDialog.OnDateSetListener{
        @Override
        public Dialog onCreateDialog(Bundle savedInstanceState) {
            // TODO Auto-generated method stub
            // Use the current date as the default date in the picker
            DatePickerDialog dialog = new DatePickerDialog(MainActivity.this, this, arrivalYear, arrivalMonth, arrivalDay);
            return dialog;

        }
        public void onDateSet(DatePicker view, int year, int monthOfYear,
                              int dayOfMonth) {
            // TODO Auto-generated method stub
            // Do something with the date chosen by the user
            departureYear = year;
            departureMonth = monthOfYear;
            departureDay = dayOfMonth;
            TextView dateText2 = (TextView) findViewById(R.id.textView7);
            String date2 = departureDay + "/" + (departureMonth+1) + "/" + departureYear;
            dateText2.setText(date2);
            Log.d("UPDATE", "Update Departure");
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

    public void travelPlan(final View v){
        setContentView(R.layout.activity_travelplan);
        GridView gridview = (GridView) findViewById(R.id.gridview);
        gridview.setAdapter(new ImageAdapter(this));
        gridview.setNumColumns(2);

        gridview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View v,
                                    int position, long id) {
                getDetail(v);
            }
        });
    }

    public class ImageAdapter extends BaseAdapter {
        private Context mContext;

        public ImageAdapter(Context c) {
            mContext = c;
        }

        public int getCount() {
            return mThumbIds.length;
        }

        public Object getItem(int position) {
            return null;
        }

        public long getItemId(int position) {
            return 0;
        }

        // create a new ImageView for each item referenced by the Adapter
        public View getView(int position, View convertView, ViewGroup parent) {
            ImageView imageView;
            if (convertView == null) {
                // if it's not recycled, initialize some attributes
                imageView = new ImageView(mContext);
                imageView.setLayoutParams(new GridView.LayoutParams(680, 680));
                imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
                imageView.setPadding(8, 8, 8, 8);
            } else {
                imageView = (ImageView) convertView;
            }

            imageView.setImageResource(mThumbIds[position]);
            return imageView;
        }

        // references to our images
        private Integer[] mThumbIds = {
                R.drawable.sample_2, R.drawable.sample_3,
                R.drawable.sample_4, R.drawable.sample_5,
                R.drawable.sample_6, R.drawable.sample_7,
                R.drawable.sample_0, R.drawable.sample_1,
                R.drawable.sample_2, R.drawable.sample_3,
                R.drawable.sample_4, R.drawable.sample_5,
                R.drawable.sample_6, R.drawable.sample_7,
                R.drawable.sample_0, R.drawable.sample_1,
                R.drawable.sample_2, R.drawable.sample_3,
                R.drawable.sample_4, R.drawable.sample_5,
                R.drawable.sample_6, R.drawable.sample_7
        };
    }

    public void getDetail(View v){
        setContentView(R.layout.activity_detail);
        final int[] choice = {-1};
        ImageView imageDescription = (ImageView) findViewById(R.id.imageView);
        imageDescription.setScaleType(ImageView.ScaleType.CENTER_CROP);
        imageDescription.setImageResource(R.drawable.eiffel_tower);
        final AlertDialog.Builder builder = new AlertDialog.Builder(this);

        DialogInterface.OnClickListener listener = new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                choice[0] = which;
            }
        };
        List<String> timeStr = new ArrayList<String>();
        for(int i=0; i<time.size(); i++){
            String str = format.format(time.get(i));
            timeStr.add(str);
        }
        builder.setSingleChoiceItems(timeStr.toArray(new String[timeStr.size()]), choice[0], listener);
        builder.setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        builder.setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Log.i("CHECK", String.valueOf(choice[0])+" / "+String.valueOf(time.size()));
                scheduled_event = time.get(choice[0]);
                Log.i("CHECK","time of event: "+scheduled_event);
                dialog.dismiss();
            }
        });
        Switch addEvent = (Switch) findViewById(R.id.switch1);
        final AlertDialog dialog = builder.create();
        addEvent.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                dialog.show();
            }
        });
    }
}
