package myapp.android.eurecom.fr.tripmemo;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.Switch;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by alexandrefradet on 21/01/2017.
 */
public class DetailsActivity extends AppCompatActivity {

    Date scheduled_event;
    List<Date> time = new ArrayList<Date>();
    SimpleDateFormat format = new SimpleDateFormat("dd-MM-yyyy");
    String[] strDate = {"20-11-2017","21-11-2017","22-11-2017","23-11-2017"};

    protected void onCreate(Bundle savedInstanceState) {

        try {
            for(int i=0;i<strDate.length;i++) {
                Date date = format.parse(strDate[i]);
                time.add(date);
            }
        } catch (ParseException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        super.onCreate(savedInstanceState);
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
            String str = format.format(time.get(i))+" morning";
            String str1 = format.format(time.get(i))+" afternoon";
            timeStr.add(str);
            timeStr.add(str1);
        }
        final Switch addEvent = (Switch) findViewById(R.id.switch1);
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
                Log.i("CHECK", String.valueOf(choice[0]) + " / " + String.valueOf(time.size()));
                if(choice[0] != -1) {
                    scheduled_event = time.get(choice[0]);
                    Log.i("CHECK", "time of event: " + scheduled_event);
                    dialog.dismiss();
                }else{
                    addEvent.setChecked(false);
                }
            }
        });
        final AlertDialog dialogChoice = builder.create();
        addEvent.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (addEvent.isChecked()) {
                    dialogChoice.show();
                }
            }
        });
    }
}
