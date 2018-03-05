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
import static kidzania.reservationgroup.Misc.VarGlobal.HEAD_GET_DATA_SUPPORTER;
import static kidzania.reservationgroup.Misc.VarGlobal.SENDER_CLASS;
import static kidzania.reservationgroup.Misc.VarGlobal.STR_USUARIO_ALTA;
import static kidzania.reservationgroup.Misc.VarGlobal.USUARIO_ALTA;
import static kidzania.reservationgroup.Misc.VarUrl.URL_GET_DATA_SUPPORTER;
import static kidzania.reservationgroup.ViewHolder.SearchViewHolder.ID_TEXT_SEARCH;
import static kidzania.reservationgroup.ViewHolder.SearchViewHolder.TEXT_SEARCH;

/**
 * Created by mubarik on 14/11/2017.
 */

public class SearchSupporter extends ParentSearchList{

    @Override
    public void onResume() {
        super.onResume();
        registerReceiver(mMessageSupporter, new IntentFilter(GET_TEXT_SEARCH));
    }

    //Must unregister onPause()
    @Override
    protected void onPause() {
        super.onPause();
        unregisterReceiver(mMessageSupporter);
    }

    //This is the handler that will manager to process the broadcast intent
    private BroadcastReceiver mMessageSupporter = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            USUARIO_ALTA = intent.getStringExtra(ID_TEXT_SEARCH);
            STR_USUARIO_ALTA = intent.getStringExtra(TEXT_SEARCH);
            SENDER_CLASS = "SearchSupporter";
            onBackPressed();
        }
    };

    @Override
    public void getSearch(){
        super.getSearch();
        HEADER_SEARCH = HEAD_GET_DATA_SUPPORTER;
        if(hasConnection(SearchSupporter.this)) {
            MultiParamGetDataJSON getDataSupporter = new MultiParamGetDataJSON();
            getDataSupporter.init(APIValueParams, APIParameters, URL_GET_DATA_SUPPORTER, SearchSupporter.this, json_search, false);
        }else{
            OpenLostConnection(this);
        }
    }
}
