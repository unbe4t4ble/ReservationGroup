package kidzania.reservationgroup.API;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static kidzania.reservationgroup.Misc.VarGlobal.GROUP_TIMEOUT;
import static kidzania.reservationgroup.Misc.VarGlobal.RESV_EMPTY_DATA;

/**
 * Modified by Mubarik Ahmad on 01/03/2017.
 * class ini untuk mengakomodir request json dengan banyak parameter.
 */
public class MultiParamGetDataJSON extends AsyncTask<Object,Object,Object> {

    private ProgressDialog pg;
    private Context context;
    private JSONObjectResult jres;

    private ArrayList<String> ValueParams = new ArrayList<>();
    private ArrayList<String> Parameters = new ArrayList<>();

    private String Strurl;
    private boolean UseDialog;

    public void init(ArrayList<String> ValueParam, ArrayList<String> Parameter, String url, Context con, JSONObjectResult jsonObjectResult, boolean useDialog){
        context = con;
        jres = jsonObjectResult;

        MultiParamGetDataJSON multiParamGetDataJSON = this;
        for(int i=0; i< ValueParam.size() ; i++) {
            ValueParams.add(ValueParam.get(i));
            Parameters.add(Parameter.get(i));
        }
        Strurl = url;
        UseDialog = useDialog;
        multiParamGetDataJSON.execute();
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        if (UseDialog) {
            pg = new ProgressDialog(context);
            pg.setTitle("Loading");
            pg.setMessage("Please wait ...");
            pg.setCancelable(false);
            pg.setCanceledOnTouchOutside(false);
            pg.show();
        }
    }

    @Override
    protected Object doInBackground(Object... params) {
        JSONObject jsonObject = null;
        JSONParser jsonParser = new JSONParser();

        String urls = Strurl;

        List<NameValuePair> param = new ArrayList<>();
        for(int i=0; i< ValueParams.size() ; i++) {
            param.add(new BasicNameValuePair(Parameters.get(i), ValueParams.get(i)));
        }
        try {
            jsonObject = jsonParser.getJsonObject(urls,param);
        } catch (IOException e) {
            e.printStackTrace();
        }

        return jsonObject;
    }

    @Override
    protected void onPostExecute(Object result) {
        super.onPostExecute(result);

        if(result != null){
            JSONObject jObj = (JSONObject) result;
            jres.gotJSONObject(jObj);
        }else{
            Intent intent_Group = new Intent(GROUP_TIMEOUT);
            context.sendBroadcast(intent_Group);

            Intent intent_Resv = new Intent(RESV_EMPTY_DATA);
            context.sendBroadcast(intent_Resv);
        }

        if (UseDialog) {
            pg.dismiss();
        }
    }

    public static abstract class JSONObjectResult{
        public abstract void gotJSONObject(JSONObject jsonObject);
    }

}
