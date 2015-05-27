package utilities;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.widget.Toast;

import com.example.kamil.weather.AdditionalInformationFragment;
import com.example.kamil.weather.BasicInformationFragment;
import com.example.kamil.weather.MainActivity;
import com.example.kamil.weather.MyPagerAdapter;
import com.example.kamil.weather.R;
import com.example.kamil.weather.UpcomingAdapter;
import com.example.kamil.weather.UpcomingDaysFragment;

import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.StatusLine;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

/**
 * Created by kamil on 27.05.15.
 */
public class WeatherRequest extends AsyncTask<String, Void, String> {

    private Context context;
    private ProgressDialog progressDialog;

    public WeatherRequest(Context context) {
        this.context = context;
        progressDialog = new ProgressDialog(context);
    }

    private void showProgress() {
        progressDialog.setTitle(R.string.weather_title);
        progressDialog.setMessage(context.getResources().getString(R.string.weather_message));
        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progressDialog.setCancelable(false);
        progressDialog.show();
    }

    private void hideProgress() {
        progressDialog.dismiss();
    }

    @Override
    protected void onPreExecute() {
        showProgress();
    }

    @Override
    protected String doInBackground(String... params) {
        HttpClient httpclient = new DefaultHttpClient();
        HttpResponse response = null;
        String responseString = null;
        try {
            response = httpclient.execute(new HttpGet(
                    "https://query.yahooapis.com/v1/public/yql?q=select%20*%20from%0Aweather.forecast%20where%20woeid%3D" + params[0] + "%20and%20u%3D%22" + MainActivity.UNITS + "%22&format=json&callback="
            ));
            StatusLine statusLine = response.getStatusLine();
            if(statusLine.getStatusCode() == HttpStatus.SC_OK){
                ByteArrayOutputStream out = new ByteArrayOutputStream();
                response.getEntity().writeTo(out);
                responseString = out.toString();
                out.close();
            } else{
                response.getEntity().getContent().close();
            }
        } catch (ClientProtocolException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return responseString;
    }

    @Override
    protected void onPostExecute(String result) {
        hideProgress();
//        Toast toast = Toast.makeText(context, result, Toast.LENGTH_SHORT);
//        toast.show();
        //PARSE
        MainActivity.currentBasic = WeatherParser.getWeatherBasic(result);
        MainActivity.currentAdditional = WeatherParser.getWeatherAdditional(result);
        MainActivity.currentUpcomingItems = WeatherParser.getWeatherUpcomingItems(result);
        MainActivity.refreshed = true;

        BasicInformationFragment.refreshView();
        AdditionalInformationFragment.refreshView();
        UpcomingDaysFragment.refreshAdapter();
        MainActivity.saveSettings();
        MainActivity.saveRequestToFile(context, result);

    }

}
