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
import static kidzania.reservationgroup.Misc.VarGlobal.AREA;
import static kidzania.reservationgroup.Misc.VarGlobal.GET_TEXT_SEARCH;
import static kidzania.reservationgroup.Misc.VarGlobal.HEAD_SEARCH_DISTRICT_AREA;
import static kidzania.reservationgroup.Misc.VarGlobal.ID_CITY;
import static kidzania.reservationgroup.Misc.VarGlobal.ID_DISTRICT;
import static kidzania.reservationgroup.Misc.VarGlobal.ID_PROVINCE;
import static kidzania.reservationgroup.Misc.VarGlobal.SENDER_CLASS;
import static kidzania.reservationgroup.Misc.VarGlobal.ZIPCODE;
import static kidzania.reservationgroup.Misc.VarUrl.URL_SEARCH_DISTRIK_AREA;
import static kidzania.reservationgroup.ViewHolder.SearchViewHolder.TEXT_SEARCH;

/**
 * Created by mubarik on 14/11/2017.
 */

public class SearchDistrikArea extends ParentSearchList{

    @Override
    public void onResume() {
        super.onResume();
        registerReceiver(mMessageDistrikArea, new IntentFilter(GET_TEXT_SEARCH));
    }

    //Must unregister onPause()
    @Override
    protected void onPause() {
        super.onPause();
        unregisterReceiver(mMessageDistrikArea);
    }

    //This is the handler that will manager to process the broadcast intent
    private BroadcastReceiver mMessageDistrikArea = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            String[] DistrikArea = intent.getStringExtra(TEXT_SEARCH).split(" : ");
            AREA = DistrikArea[0];
            ZIPCODE = DistrikArea[1];
            SENDER_CLASS = "SearchDistrikArea";
            onBackPressed();
        }
    };

    @Override
    public void AssignParam(){
        super.AssignParam();
        APIParameters.add("id_province");
        APIParameters.add("id_city");
        APIParameters.add("id_distrik");
    }

    @Override
    public void AssignValueParam(){
        super.AssignValueParam();
        APIValueParams.add(ID_PROVINCE);
        APIValueParams.add(ID_CITY);
        APIValueParams.add(ID_DISTRICT);
    }

    @Override
    public void getSearch(){
        AssignParam();
        AssignValueParam();
        HEADER_SEARCH = HEAD_SEARCH_DISTRICT_AREA;
        if(hasConnection(SearchDistrikArea.this)) {
            MultiParamGetDataJSON getDataProvince = new MultiParamGetDataJSON();
            getDataProvince.init(APIValueParams, APIParameters, URL_SEARCH_DISTRIK_AREA, SearchDistrikArea.this, json_search, false);
        }else{
            OpenLostConnection(this);
        }
    }
}
