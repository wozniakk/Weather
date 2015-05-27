package utilities;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by kamil on 27.05.15.
 */
public class WoeidParser {

    public static boolean hasLocation(String result) {

        boolean has = false;
        try {
            JSONObject jObject = new JSONObject(result);
            if (jObject.getJSONObject("query").getInt("count") > 0)
                has = true;
            else
                has = false;
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return has;

    }

    public static String getWoeid(String result) {

        String woeid = null;
        try {
            JSONObject jObject = new JSONObject(result);
            if (jObject.getJSONObject("query").getInt("count") > 0)
                woeid = jObject.getJSONObject("query").getJSONObject("results").getJSONObject("place").getString("woeid");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return woeid;

    }

}
