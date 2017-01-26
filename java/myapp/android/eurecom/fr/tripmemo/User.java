package myapp.android.eurecom.fr.tripmemo;

import java.util.List;

/**
 * Created by alexandrefradet on 21/01/2017.
 */
public class User {
    private final String id;
    private final String first_name;
    private final String last_name;
    private final String email;

    public User(String id, String first_name, String last_name, String email){
        this.id = id;
        this.first_name = first_name;
        this.last_name = last_name;
        this.email = email;
    }

    public String get(String arg1){
        switch (arg1){
            case "id":
                return id;
            case "first_name":
                return first_name;
            case "last_name":
                return last_name;
            case "email":
                return email;
        }
        String error = "Error";
        return error;
    }
}
