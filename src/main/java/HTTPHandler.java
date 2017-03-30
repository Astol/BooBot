import com.google.gson.Gson;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.lang.*;

/**
 * Created by Alexander on 2017-03-29.
 */
public class HTTPHandler {

    public static String getHTTPJSON(String address) {
        try {
            StringBuilder json = new StringBuilder("");
            URL url = new URL(address);
            BufferedReader br = new BufferedReader(new InputStreamReader(url.openStream()));
            String strTemp;
            while (null != (strTemp = br.readLine())) {
                json.append(strTemp);
            }
            return json.toString();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return "";
    }
}
