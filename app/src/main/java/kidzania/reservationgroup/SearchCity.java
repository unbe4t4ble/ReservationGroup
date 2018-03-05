package kidzania.reservationgroup;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;

import kidzania.reservationgroup.API.MultiParamGetDataJSON;

import static kidzania.reservationgroup.Misc.FuncGlobal.OpenLostConnection;
import static kidzania.reservationgroup.Misc.FuncGlobal.hasConnection;
import static kidzania.reservationgroup.Misc.VarGlobal.APIParameters;
import static kidzania.reservationgroup.Misc.VarGlobal.APIValueParams;
import static kidzania.reservationgroup.Misc.VarGlobal.CITY;
import static kidzania.reservationgroup.Misc.VarGlobal.GET_TEXT_SEARCH;
import static kidzania.reservationgroup.Misc.VarGlobal.HEAD_SEARCH_CITY;
import static kidzania.reservationgroup.Misc.VarGlobal.ID_CITY;
import static kidzania.reservationgroup.Misc.VarGlobal.ID_PROVINCE;
import static kidzania.reservationgroup.Misc.VarGlobal.SENDER_CLASS;
import static kidzania.reservationgroup.Misc.VarUrl.URL_SEARCH_CITY;
import static kidzania.reservationgroup.ViewHolder.SearchViewHolder.ID_TEXT_SEARCH;
import static kidzania.reservationgroup.ViewHolder.SearchViewHolder.TEXT_SEARCH;

/**
 * Created by mubarik on 14/11/2017.
 */

public class SearchCity extends ParentSearchList{

    @Override
    public void onResume() {
        super.onResume();
        registerReceiver(mMessageCity, new IntentFilter(GET_TEXT_SEARCH));
    }

    //Must unregister onPause()
    @Override
    protected void onPause() {
        super.onPause();
        unregisterReceiver(mMessageCity);
    }

    //This is the handler that will manager to process the broadcast intent
    private BroadcastReceiver mMessageCity = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            ID_CITY = intent.getStringExtra(ID_TEXT_SEARCH);
            CITY = intent.getStringExtra(TEXT_SEARCH);
            SENDER_CLASS = "SearchCity";
            onBackPressed();
        }
    };

    @Override
    public void AssignParam(){
        super.AssignParam();
        APIParameters.add("id_province");
    }

    @Override
    public void AssignValueParam(){
        super.AssignValueParam();
        APIValueParams.add(ID_PROVINCE);
    }

    @Override
    public void getSearch(){
        AssignParam();
        AssignValueParam();
        HEADER_SEARCH = HEAD_SEARCH_CITY;
        if(hasConnection(SearchCity.this)) {
            MultiParamGetDataJSON getDataProvince = new MultiParamGetDataJSON();
            getDataProvince.init(APIValueParams, APIParameters, URL_SEARCH_CITY, SearchCity.this, json_search, false);
        }else{
            OpenLostConnection(this);
        }
    }
}
