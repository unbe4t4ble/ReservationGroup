package kidzania.reservationgroup.API;

import android.util.Log;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.conn.ConnectTimeoutException;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.SocketTimeoutException;
import java.util.List;

/**
  * kelas ini untuk memparsing data dari jason.
 */


public class JSONParser {
    private JSONObject jsonObject;
    private String jsonString="";
    private InputStream is;

    public JSONObject getJsonObject(String url, List<NameValuePair> param) throws IOException {

        HttpParams httpParameters = new BasicHttpParams();
        DefaultHttpClient client = new DefaultHttpClient(httpParameters);
        HttpPost httpPost = new HttpPost(url);
        try {

             //Setting timeout pada request json, jika koneksi internet lemot.
            HttpConnectionParams.setConnectionTimeout(httpParameters, 10000);
            HttpConnectionParams.setSoTimeout(httpParameters, 10000);

            //melakukan request json.
            httpPost.setEntity(new UrlEncodedFormEntity(param));
            HttpResponse httpResponse = client.execute(httpPost);
            HttpEntity httpEntity = httpResponse.getEntity();
            is = httpEntity.getContent();
        } catch(ConnectTimeoutException | SocketTimeoutException e){
            Log.e("Timeout Exception: ", e.toString());
        } catch (IOException e) {
            e.printStackTrace();
        }

        try {
            BufferedReader reader = new BufferedReader(new InputStreamReader(is, "iso-8859-1"));
            StringBuilder sb = new StringBuilder();
            String line = "";
            while ((line = reader.readLine()) != null) {
                sb.append(line).append("/n");
            }
            jsonString = sb.toString();

            jsonObject = null;
        }catch(Exception e){
            e.printStackTrace();
        }
        try {
            jsonObject = new JSONObject(jsonString);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return jsonObject;
    }

}
