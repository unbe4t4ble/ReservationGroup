package kidzania.reservationgroup.API;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by mubarik on 07/03/2017.
 * class ini untuk melakukan pengiriman data ke webservice.
 * class ini merupakan methode insert data ke database melalui webservice.
 */

public class SendDataJSON extends AsyncTask<String,String,String> {
    private ProgressDialog pg;
    private Context context;

    private ArrayList<String> ValueParams = new ArrayList<>();
    private ArrayList<String> Parameters = new ArrayList<>();

    private String Strurl;
    private boolean showPG;

    public void SendData(ArrayList<String> ValueParam, ArrayList<String> Parameter, String url, Context con, boolean ShowPG){
        context = con;
        SendDataJSON sendDataJSON = this;
        for(int i = 0; ValueParam.size() > i; i++) {
            ValueParams.add(ValueParam.get(i));
            Parameters.add(Parameter.get(i));
        }
        //sendDataJSON.execute(url);
        showPG = ShowPG;
        Strurl = url;
        sendDataJSON.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
        //sendDataJSON.execute();
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        if (showPG) {
            pg = new ProgressDialog(context);
            pg.setTitle("Loading ");
            pg.setMessage("Please wait ...");
            pg.setCancelable(false);
            pg.setCanceledOnTouchOutside(false);
            pg.show();
        }
    }

    @Override
    protected String doInBackground(String... params) {
        JSONObject jsonObject = null;
        JSONParser jsonParser = new JSONParser();

        String a="";
        String urls = Strurl;

        List<NameValuePair> param = new ArrayList<>();
        for(int i=0; i< ValueParams.size() ; i++) {
            param.add(new BasicNameValuePair(Parameters.get(i), ValueParams.get(i)));
        }
        try {
            jsonObject = jsonParser.getJsonObject(urls,param);
            a = "success";
        } catch (IOException e) {
            a = "fail";
            e.printStackTrace();
        }

        return a;
    }

    @Override
    protected void onPostExecute(String result) {
        super.onPostExecute(result);
        if (showPG) {
            pg.dismiss();
        }
    }
}
