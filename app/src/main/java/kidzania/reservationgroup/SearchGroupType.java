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
import static kidzania.reservationgroup.Misc.VarGlobal.GRADE;
import static kidzania.reservationgroup.Misc.VarGlobal.GRADE_TYP;
import static kidzania.reservationgroup.Misc.VarGlobal.HEAD_SEARCH_GROUPTYPE;
import static kidzania.reservationgroup.Misc.VarGlobal.SENDER_CLASS;
import static kidzania.reservationgroup.Misc.VarUrl.URL_SEARCH_GROUPTYPE;
import static kidzania.reservationgroup.ViewHolder.SearchViewHolder.TEXT_SEARCH;

/**
 * Created by mubarik on 13/11/2017.
 */

public class SearchGroupType extends ParentSearchList {

    @Override
    public void onResume() {
        super.onResume();
        registerReceiver(mMessageGroupType, new IntentFilter(GET_TEXT_SEARCH));
    }

    //Must unregister onPause()
    @Override
    protected void onPause() {
        super.onPause();
        unregisterReceiver(mMessageGroupType);
    }

    //This is the handler that will manager to process the broadcast intent
    private BroadcastReceiver mMessageGroupType = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            GRADE_TYP = intent.getStringExtra(TEXT_SEARCH);
            SENDER_CLASS = "SearchGroupType";
            onBackPressed();
        }
    };

    @Override
    public void AssignParam(){
        super.AssignParam();
        APIParameters.add("grade");
    }

    @Override
    public void AssignValueParam(){
        super.AssignValueParam();
        APIValueParams.add(GRADE);
    }

    @Override
    public void getSearch(){
        AssignParam();
        AssignValueParam();
        HEADER_SEARCH = HEAD_SEARCH_GROUPTYPE;
        if(hasConnection(SearchGroupType.this)) {
            MultiParamGetDataJSON getDataProvince = new MultiParamGetDataJSON();
            getDataProvince.init(APIValueParams, APIParameters, URL_SEARCH_GROUPTYPE, SearchGroupType.this, json_search, false);
        }else{
            OpenLostConnection(this);
        }
    }
}
