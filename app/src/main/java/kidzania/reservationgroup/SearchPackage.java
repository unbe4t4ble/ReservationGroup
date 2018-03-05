package kidzania.reservationgroup;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import kidzania.reservationgroup.API.MultiParamGetDataJSON;

import static kidzania.reservationgroup.Misc.FuncGlobal.OpenLostConnection;
import static kidzania.reservationgroup.Misc.FuncGlobal.clearAPIParams;
import static kidzania.reservationgroup.Misc.FuncGlobal.clearAPIValueParam;
import static kidzania.reservationgroup.Misc.FuncGlobal.getFormatedCurrency;
import static kidzania.reservationgroup.Misc.FuncGlobal.hasConnection;
import static kidzania.reservationgroup.Misc.VarDate.DateReservYYYY;
import static kidzania.reservationgroup.Misc.VarGlobal.APIParameters;
import static kidzania.reservationgroup.Misc.VarGlobal.APIValueParams;
import static kidzania.reservationgroup.Misc.VarGlobal.GET_TEXT_SEARCH;
import static kidzania.reservationgroup.Misc.VarGlobal.HARGA_PACKAGE;
import static kidzania.reservationgroup.Misc.VarGlobal.HEAD_GET_DATA_HARGA_PACKAGE;
import static kidzania.reservationgroup.Misc.VarGlobal.HEAD_GET_DATA_PACKAGE;
import static kidzania.reservationgroup.Misc.VarGlobal.ID_PACK;
import static kidzania.reservationgroup.Misc.VarGlobal.SENDER_CLASS;
import static kidzania.reservationgroup.Misc.VarGlobal.STR_PACK;
import static kidzania.reservationgroup.Misc.VarGlobal.TAG_PACK;
import static kidzania.reservationgroup.Misc.VarUrl.URL_GET_DATA_HARGA_PACKAGE;
import static kidzania.reservationgroup.Misc.VarUrl.URL_GET_DATA_PACKAGE;
import static kidzania.reservationgroup.ViewHolder.SearchViewHolder.ID_TEXT_SEARCH;
import static kidzania.reservationgroup.ViewHolder.SearchViewHolder.TEXT_SEARCH;

/**
 * Created by mubarik on 12/01/2018.
 */

public class SearchPackage extends ParentSearchList {

    @Override
    public void onResume() {
        super.onResume();
        registerReceiver(mMessagePackage, new IntentFilter(GET_TEXT_SEARCH));
    }

    //Must unregister onPause()
    @Override
    protected void onPause() {
        super.onPause();
        unregisterReceiver(mMessagePackage);
    }

    //This is the handler that will manager to process the broadcast intent
    private BroadcastReceiver mMessagePackage = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            ID_PACK = intent.getStringExtra(ID_TEXT_SEARCH);
            STR_PACK = intent.getStringExtra(TEXT_SEARCH);
            SENDER_CLASS = "SearchPackage";
            getHargaPackage();
        }
    };

    @Override
    public void AssignParam(){
        super.AssignParam();
        APIParameters.add("tag_pack");
        APIParameters.add("DateReservYYYY");
    }

    @Override
    public void AssignValueParam(){
        super.AssignValueParam();
        APIValueParams.add(TAG_PACK);
        APIValueParams.add(DateReservYYYY);
    }

    @Override
    public void getSearch(){
        AssignParam();
        AssignValueParam();
        HEADER_SEARCH = HEAD_GET_DATA_PACKAGE;
        if(hasConnection(SearchPackage.this)) {
            MultiParamGetDataJSON getDataPackage = new MultiParamGetDataJSON();
            getDataPackage.init(APIValueParams, APIParameters, URL_GET_DATA_PACKAGE, SearchPackage.this, json_search, false);
        }else{
            OpenLostConnection(this);
        }
    }

    private void ParamGetHarga(){
        clearAPIParams();
        APIParameters.add("tag_pack");
        APIParameters.add("id");
    }

    private void ValueParamGetHarga(){
        clearAPIValueParam();
        APIValueParams.add(TAG_PACK);
        APIValueParams.add(ID_PACK);
    }

    private void getHargaPackage(){
        ParamGetHarga();
        ValueParamGetHarga();
        MultiParamGetDataJSON getDataHarga = new MultiParamGetDataJSON();
        getDataHarga.init(APIValueParams, APIParameters, URL_GET_DATA_HARGA_PACKAGE, SearchPackage.this, json_harga, true);
    }

    MultiParamGetDataJSON.JSONObjectResult json_harga = new MultiParamGetDataJSON.JSONObjectResult() {
        @Override
        public void gotJSONObject(JSONObject jsonObject) {
            try {
                JSONArray jsonArray = jsonObject.getJSONArray(HEAD_GET_DATA_HARGA_PACKAGE);
                for (int i = 0; i < jsonArray.length(); i++) {
                    JSONObject objData = jsonArray.getJSONObject(i);
                    HARGA_PACKAGE = getFormatedCurrency(SearchPackage.this,objData.getString("PRICE"));
                }
                onBackPressed();
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    };

}
