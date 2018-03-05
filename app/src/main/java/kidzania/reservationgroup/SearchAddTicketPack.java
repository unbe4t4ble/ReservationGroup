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
import static kidzania.reservationgroup.Misc.VarDate.DateReservMM;
import static kidzania.reservationgroup.Misc.VarGlobal.APIParameters;
import static kidzania.reservationgroup.Misc.VarGlobal.APIValueParams;
import static kidzania.reservationgroup.Misc.VarGlobal.GET_TEXT_SEARCH;
import static kidzania.reservationgroup.Misc.VarGlobal.HEAD_GET_DATA_HARGA_TICKET_PACK;
import static kidzania.reservationgroup.Misc.VarGlobal.HEAD_GET_DATA_TICKET_PACK;
import static kidzania.reservationgroup.Misc.VarGlobal.IDPACK_ADD;
import static kidzania.reservationgroup.Misc.VarGlobal.ID_PAQ;
import static kidzania.reservationgroup.Misc.VarGlobal.PriceAddAdult;
import static kidzania.reservationgroup.Misc.VarGlobal.PriceAddChild;
import static kidzania.reservationgroup.Misc.VarGlobal.PriceAddTodd;
import static kidzania.reservationgroup.Misc.VarGlobal.SENDER_CLASS;
import static kidzania.reservationgroup.Misc.VarGlobal.STR_IDPACK_ADD;
import static kidzania.reservationgroup.Misc.VarUrl.URL_GET_DATA_HARGA_TICKET_PACK;
import static kidzania.reservationgroup.Misc.VarUrl.URL_GET_DATA_TICKET_PACK;
import static kidzania.reservationgroup.ViewHolder.SearchViewHolder.ID_TEXT_SEARCH;
import static kidzania.reservationgroup.ViewHolder.SearchViewHolder.TEXT_SEARCH;

/**
 * Created by mubarik on 14/11/2017.
 */

public class SearchAddTicketPack extends ParentSearchList{

    @Override
    public void onResume() {
        super.onResume();
        registerReceiver(mMessageTicketPack, new IntentFilter(GET_TEXT_SEARCH));
    }

    //Must unregister onPause()
    @Override
    protected void onPause() {
        super.onPause();
        unregisterReceiver(mMessageTicketPack);
    }

    //This is the handler that will manager to process the broadcast intent
    private BroadcastReceiver mMessageTicketPack = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            IDPACK_ADD = intent.getStringExtra(ID_TEXT_SEARCH);
            STR_IDPACK_ADD = intent.getStringExtra(TEXT_SEARCH);
            SENDER_CLASS = "SearchAddTicketPack";
            getHargaTiket();
        }
    };

    @Override
    public void AssignParam(){
        super.AssignParam();
        APIParameters.add("DateReservMM");
    }

    @Override
    public void AssignValueParam(){
        super.AssignValueParam();
        APIValueParams.add(DateReservMM);
    }

    @Override
    public void getSearch(){
        AssignParam();
        AssignValueParam();
        HEADER_SEARCH = HEAD_GET_DATA_TICKET_PACK;
        if(hasConnection(SearchAddTicketPack.this)) {
            MultiParamGetDataJSON getDataTicketPack = new MultiParamGetDataJSON();
            getDataTicketPack.init(APIValueParams, APIParameters, URL_GET_DATA_TICKET_PACK, SearchAddTicketPack.this, json_search, false);
        }else{
            OpenLostConnection(this);
        }
    }

    private void ParamGetHarga(){
        clearAPIParams();
        APIParameters.add("ID_NUM");
    }

    private void ValueParamGetHarga(){
        clearAPIValueParam();
        APIValueParams.add(ID_PAQ);
    }

    private void getHargaTiket(){
        ParamGetHarga();
        ValueParamGetHarga();
        MultiParamGetDataJSON getDataHarga = new MultiParamGetDataJSON();
        getDataHarga.init(APIValueParams, APIParameters, URL_GET_DATA_HARGA_TICKET_PACK, SearchAddTicketPack.this, json_harga, true);
    }

    MultiParamGetDataJSON.JSONObjectResult json_harga = new MultiParamGetDataJSON.JSONObjectResult() {
        @Override
        public void gotJSONObject(JSONObject jsonObject) {
            try {
                JSONArray jsonArray = jsonObject.getJSONArray(HEAD_GET_DATA_HARGA_TICKET_PACK);
                for (int i = 0; i < jsonArray.length(); i++) {
                    JSONObject objData = jsonArray.getJSONObject(i);
                    PriceAddTodd = getFormatedCurrency(SearchAddTicketPack.this,objData.getString("P_BEBE"));
                    PriceAddChild = getFormatedCurrency(SearchAddTicketPack.this,objData.getString("P_NINO"));
                    PriceAddAdult = getFormatedCurrency(SearchAddTicketPack.this,objData.getString("P_ADULTO"));
                }
                onBackPressed();
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    };
}
