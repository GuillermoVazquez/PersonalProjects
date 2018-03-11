package vazquez.guillermo.mapchat;

import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by guillermo on 3/10/18.
 */

public class Connections {

    public void postAction() {
        try {
            URL url = new URL("https://kamorris.com/lab/register_location.php");
            HttpURLConnection client = (HttpURLConnection) url.openConnection();
            client.setRequestMethod("POST");
            client.setRequestProperty();
        } catch (Exception e) {}
    }
}