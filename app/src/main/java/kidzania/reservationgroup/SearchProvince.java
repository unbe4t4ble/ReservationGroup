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
import static kidzania.reservationgroup.Misc.VarGlobal.GET_TEXT_SEARCH;
import static kidzania.reservationgroup.Misc.VarGlobal.HEAD_SEARCH_PROVINCE;
import static kidzania.reservationgroup.Misc.VarGlobal.ID_PROVINCE;
import static kidzania.reservationgroup.Misc.VarGlobal.PROVINCE;
import static kidzania.reservationgroup.Misc.VarGlobal.SENDER_CLASS;
import static kidzania.reservationgroup.Misc.VarUrl.URL_SEARCH_PROVINCE;
import static kidzania.reservationgroup.ViewHolder.SearchViewHolder.ID_TEXT_SEARCH;
import static kidzania.reservationgroup.ViewHolder.SearchViewHolder.TEXT_SEARCH;

/**
 * Created by mubarik on 13/11/2017.
 */

public class SearchProvince extends ParentSearchList {

    @Override
    public void onResume() {
        super.onResume();
        registerReceiver(mMessageProvince, new IntentFilter(GET_TEXT_SEARCH));
    }

    //Must unregister onPause()
    @Override
    protected void onPause() {
        super.onPause();
        unregisterReceiver(mMessageProvince);
    }

    //This is the handler that will manager to process the broadcast intent
    private BroadcastReceiver mMessageProvince = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            ID_PROVINCE = intent.getStringExtra(ID_TEXT_SEARCH);
            PROVINCE = intent.getStringExtra(TEXT_SEARCH);
            SENDER_CLASS = "SearchProvince";
            onBackPressed();
        }
    };

    @Override
    public void getSearch(){
        super.getSearch();
        HEADER_SEARCH = HEAD_SEARCH_PROVINCE;
        if(hasConnection(SearchProvince.this)) {
            MultiParamGetDataJSON getDataProvince = new MultiParamGetDataJSON();
            getDataProvince.init(APIValueParams, APIParameters, URL_SEARCH_PROVINCE, SearchProvince.this, json_search, false);
        }else{
            OpenLostConnection(this);
        }
    }
}
