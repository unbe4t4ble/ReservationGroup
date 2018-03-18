package kidzania.reservationgroup;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import kidzania.reservationgroup.API.MultiParamGetDataJSON;
import kidzania.reservationgroup.Adapter.ListHistoryAdapter;
import kidzania.reservationgroup.Model.HistoryModel;
import kidzania.reservationgroup.ViewHolder.WrapContentLinearLayoutManager;

import static kidzania.reservationgroup.Misc.FuncGlobal.CheckConnection;
import static kidzania.reservationgroup.Misc.FuncGlobal.OpenLostConnection;
import static kidzania.reservationgroup.Misc.FuncGlobal.clearAPIParams;
import static kidzania.reservationgroup.Misc.FuncGlobal.clearAPIValueParam;
import static kidzania.reservationgroup.Misc.FuncGlobal.getInformationUser;
import static kidzania.reservationgroup.Misc.FuncGlobal.hasConnection;
import static kidzania.reservationgroup.Misc.VarGlobal.APIParameters;
import static kidzania.reservationgroup.Misc.VarGlobal.APIValueParams;
import static kidzania.reservationgroup.Misc.VarGlobal.HEAD_GET_DATA_HISTORY;
import static kidzania.reservationgroup.Misc.VarGlobal.ID_NUM_RESER;
import static kidzania.reservationgroup.Misc.VarGlobal.RESV_EMPTY_DATA;
import static kidzania.reservationgroup.Misc.VarUrl.URL_GET_DATA_HISTORY;

public class HistoryReservation extends AppCompatActivity {

    public static ListHistoryAdapter listHistoryAdapter;
    RecyclerView myRecyclerviewHistory;
    private WrapContentLinearLayoutManager linearLayoutManager;
    public static List<HistoryModel> data = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history_reservation);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        CheckConnection(this);
        initialization();
    }

    @Override
    public void onResume() {
        super.onResume();
        getInformationUser();
        registerReceiver(mMessageNoData, new IntentFilter(RESV_EMPTY_DATA));
        refreshData();
    }

    //Must unregister onPause()
    @Override
    protected void onPause() {
        super.onPause();
        registerReceiver(mMessageNoData, new IntentFilter(RESV_EMPTY_DATA));
    }

    private BroadcastReceiver mMessageNoData = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            HideLoading();
        }
    };

    //This is the handler that will manager to process the broadcast intent
    public void HideLoading(){
        listHistoryAdapter.notifyDataSetChanged();
        listHistoryAdapter.setFalseLoading();
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.button_refresh, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                return true;
            case R.id.btn_refresh:
                refreshData();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void initialization() {
        myRecyclerviewHistory = (RecyclerView) findViewById(R.id.myRecyclerviewHistory);
        listHistoryAdapter = new ListHistoryAdapter(data);
        myRecyclerviewHistory.setAdapter(listHistoryAdapter);
        linearLayoutManager = new WrapContentLinearLayoutManager(HistoryReservation.this);
        myRecyclerviewHistory.setLayoutManager(linearLayoutManager);
    }

    private void AssignParam(){
        clearAPIParams();
        APIParameters.add("ID_NUM_RESER");
    }

    private void AssignValueParam(){
        clearAPIValueParam();
        APIValueParams.add(ID_NUM_RESER);
    }

    private void getHistory(){
        AssignParam();
        AssignValueParam();
        if(hasConnection(HistoryReservation.this)) {
            MultiParamGetDataJSON getGroupData = new MultiParamGetDataJSON();
            getGroupData.init(APIValueParams, APIParameters, URL_GET_DATA_HISTORY, HistoryReservation.this, json_history, false);
        }else{
            OpenLostConnection(this);
        }
    }

    public MultiParamGetDataJSON.JSONObjectResult json_history = new MultiParamGetDataJSON.JSONObjectResult() {
        @Override
        public void gotJSONObject(JSONObject jsonObject) {
            try {
                JSONArray jsonArray = jsonObject.getJSONArray(HEAD_GET_DATA_HISTORY);
                for (int i = 0; i < jsonArray.length(); i++) {
                    JSONObject objData = jsonArray.getJSONObject(i);
                    HistoryModel model = new HistoryModel();
                    model.setDateHist(objData.getString("FDATE"));
                    model.setUserHist(objData.getString("U_Login"));
                    model.setNoteHist(objData.getString("MODIF"));
                    data.add(model);
                }
                listHistoryAdapter.notifyItemInserted(data.size());
                listHistoryAdapter.setFalseLoading();
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    };

    public void clear() {
        data.clear();
        listHistoryAdapter.notifyItemRangeRemoved(0, data.size());
        listHistoryAdapter.notifyDataSetChanged();
        listHistoryAdapter.setLoading();
    }

    public void refreshData(){
        clear();
        getHistory();
    }

}
