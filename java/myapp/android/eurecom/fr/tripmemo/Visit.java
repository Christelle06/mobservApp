package myapp.android.eurecom.fr.tripmemo;

import java.util.Date;

/**
 * Created by alexandrefradet on 21/01/2017.
 */
public class Visit {
    private final String monument;
    private final Date day;
    private final int moment;
    private final String travel_id;

    public Visit(String monument, Date day, int moment, String travel_id){
        this.monument = monument;
        this.day = day;
        this.moment = moment;
        this.travel_id = travel_id;
    }
}
