package vazquez.guillermo.mapchat.MapChatObjects;

/**
 * Created by guillermo on 3/8/18.
 */

public class Person {
    int Long;
    int Lat;
    String userName;

    public Person(String userName, int Long, int Lat) {
        this.Long = Long;
        this.Lat = Lat;
        this.userName = userName;
    }

    public int getLong() {
        return Long;
    }

    public int getLat() {
        return Lat;
    }

    public String getUserName() {
        return userName;
    }
}
