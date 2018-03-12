package vazquez.guillermo.mapchat.MapChatObjects;

/**
 * Created by guillermo on 3/8/18.
 */

public class Person {
    double Long;
    double Lat;
    String userName;

    public Person(String userName, int Long, int Lat) {
        this.Long = Long;
        this.Lat = Lat;
        this.userName = userName;
    }

    public double getLong() {
        return Long;
    }

    public double getLat() {
        return Lat;
    }

    public String getUserName() {
        return userName;
    }
}
