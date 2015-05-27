package utilities;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

import com.example.kamil.weather.MainActivity;
import com.example.kamil.weather.R;

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
public class WoeidRequest extends AsyncTask<String, Void, String> {

    private Context context;
    private ProgressDialog progressDialog;

    public WoeidRequest(Context context) {
        this.context = context;
        progressDialog = new ProgressDialog(context);
    }

    private void showProgress() {
        progressDialog.setTitle(R.string.woeid_title);
        progressDialog.setMessage(context.getResources().getString(R.string.woeid_message));
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
                    "https://query.yahooapis.com/v1/public/yql?q=select%20*%20from%0Ageo.places(1)%20where%20text%3D%22" + params[0] + "%22&format=json&callback="
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
        if (WoeidParser.hasLocation(result)) {
            new WeatherRequest(context).execute(MainActivity.WOEID = WoeidParser.getWoeid(result) );
        } else {
            new AlertDialog.Builder(context)
                    .setTitle(R.string.parser_title)
                    .setMessage(R.string.parser_message)
                    .setPositiveButton(R.string.parser_ok, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {

                        }
                    })
                    .show();
        }
    }

}
