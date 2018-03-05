package kidzania.reservationgroup;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import kidzania.reservationgroup.API.MultiParamGetDataJSON;
import kidzania.reservationgroup.Adapter.ListPersonalAdapter;
import kidzania.reservationgroup.Model.PersonalModel;
import kidzania.reservationgroup.ViewHolder.WrapContentLinearLayoutManager;

import static kidzania.reservationgroup.Misc.FuncGlobal.CheckConnection;
import static kidzania.reservationgroup.Misc.FuncGlobal.OpenLostConnection;
import static kidzania.reservationgroup.Misc.FuncGlobal.clearAPIParams;
import static kidzania.reservationgroup.Misc.FuncGlobal.clearAPIValueParam;
import static kidzania.reservationgroup.Misc.FuncGlobal.getInformationUser;
import static kidzania.reservationgroup.Misc.FuncGlobal.hasConnection;
import static kidzania.reservationgroup.Misc.VarGlobal.APIParameters;
import static kidzania.reservationgroup.Misc.VarGlobal.APIValueParams;
import static kidzania.reservationgroup.Misc.VarGlobal.GROUP_TIMEOUT;
import static kidzania.reservationgroup.Misc.VarGlobal.GROUP_TRY_AGAIN;
import static kidzania.reservationgroup.Misc.VarGlobal.HEAD_GROUP_PERSONAL;
import static kidzania.reservationgroup.Misc.VarGlobal.ID_USER;
import static kidzania.reservationgroup.Misc.VarUrl.URL_GROUP_PERSONAL;

public class GroupPersonal extends AppCompatActivity {

    public static ListPersonalAdapter listPersonalAdapter;
    RecyclerView myRecyclerviewPersonal;

    private WrapContentLinearLayoutManager linearLayoutManager;
    private boolean isLastPage = false;
    private boolean isLoading = false;

    public static List<PersonalModel> data = new ArrayList<>();

    private int LIMIT_FIRST = 1;
    private int LIMIT_COUNT;
    private int TOTAL_DATA = 0;
    private int PAGE_SIZE = 6;
    private String FIND_TEXT = "";

    final String PARAM_ID_USER="id_user";
    final String PARAM_PAGING_FIRST="paging_first";
    final String PARAM_PAGING_LAST="paging_last";
    final String PARAM_FIND_TEXT="find_text";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_group_personal);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        CheckConnection(this);
        initialization();
    }

    @Override
    public void onResume() {
        super.onResume();
        getInformationUser();
        registerReceiver(mMessageTimeOut, new IntentFilter(GROUP_TIMEOUT));
        registerReceiver(mMessageLoadMore, new IntentFilter(GROUP_TRY_AGAIN));
        refreshData();
    }

    //Must unregister onPause()
    @Override
    protected void onPause() {
        super.onPause();
        unregisterReceiver(mMessageTimeOut);
        unregisterReceiver(mMessageLoadMore);
    }

    //This is the handler that will manager to process the broadcast intent
    private BroadcastReceiver mMessageTimeOut = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            listPersonalAdapter.notifyDataSetChanged();
            listPersonalAdapter.setTryAgain();
        }
    };

    //This is the handler that will manager to process the broadcast intent
    private BroadcastReceiver mMessageLoadMore = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            listPersonalAdapter.notifyDataSetChanged();
            listPersonalAdapter.setLoading();
            getPersonal();
        }
    };

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.button_refresh, menu);
        inflater.inflate(R.menu.button_cari, menu);
        inflater.inflate(R.menu.button_add_group, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                return true;
            case R.id.btn_cari:
                DialogFindGroup();
                return true;
            case R.id.btn_refresh:
                refreshData();
                return true;
            case R.id.btn_add_group:
                //showDialogGType();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void initialization() {
        myRecyclerviewPersonal = (RecyclerView) findViewById(R.id.myRecyclerviewPersonal);
        listPersonalAdapter = new ListPersonalAdapter(data);
        myRecyclerviewPersonal.setAdapter(listPersonalAdapter);
        linearLayoutManager = new WrapContentLinearLayoutManager(GroupPersonal.this);
        myRecyclerviewPersonal.setLayoutManager(linearLayoutManager);

        myRecyclerviewPersonal.addOnScrollListener(recyclerViewOnScrollListener);

        LIMIT_COUNT = LIMIT_FIRST;
        clear();
    }

    private void AssignParam(){
        clearAPIParams();
        APIParameters.add(PARAM_ID_USER);
        APIParameters.add(PARAM_PAGING_FIRST);
        APIParameters.add(PARAM_PAGING_LAST);
        APIParameters.add(PARAM_FIND_TEXT);
    }

    private void AssignValueParam(){
        clearAPIValueParam();
        APIValueParams.add(ID_USER);
        APIValueParams.add(String.valueOf(LIMIT_COUNT));
        APIValueParams.add(String.valueOf(PAGE_SIZE));
        APIValueParams.add(FIND_TEXT);
    }

    private void getPersonal(){
        isLoading = false;
        AssignParam();
        AssignValueParam();
        if(hasConnection(GroupPersonal.this)) {
            MultiParamGetDataJSON getGroupData = new MultiParamGetDataJSON();
            getGroupData.init(APIValueParams, APIParameters, URL_GROUP_PERSONAL, GroupPersonal.this, json_personal, false);
        }else{
            OpenLostConnection(this);
        }
    }

    public MultiParamGetDataJSON.JSONObjectResult json_personal = new MultiParamGetDataJSON.JSONObjectResult() {
        @Override
        public void gotJSONObject(JSONObject jsonObject) {
            try {
                JSONArray jsonArray = jsonObject.getJSONArray(HEAD_GROUP_PERSONAL);
                for (int i = 0; i < jsonArray.length(); i++) {
                    JSONObject objData = jsonArray.getJSONObject(i);
                    PersonalModel model = new PersonalModel();
                    model.setid(objData.getString("ID"));
                    model.setname(objData.getString("NAME"));
                    model.setphone(objData.getString("PHONE"));
                    model.setid_card(objData.getString("ID_CARD"));
                    model.setaddress(objData.getString("ADDRESS"));
                    model.setarea(objData.getString("AREA"));
                    model.setdistrict(objData.getString("DISTRICT"));
                    model.setcity(objData.getString("CITY"));
                    model.setprovince(objData.getString("PROVINCE"));
                    model.setzipcode(objData.getString("ZIPCODE"));
                    model.setemail(objData.getString("EMAIL"));
                    model.setfax(objData.getString("FAX"));
                    data.add(model);
                    LIMIT_COUNT++;
                    TOTAL_DATA++;
                }
                listPersonalAdapter.notifyItemInserted(data.size());

                if (jsonArray.length() < (PAGE_SIZE - 1)){
                    listPersonalAdapter.setHideLoadingAndTryAgain();
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    };

    private RecyclerView.OnScrollListener recyclerViewOnScrollListener = new RecyclerView.OnScrollListener() {
        @Override
        public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
            super.onScrollStateChanged(recyclerView, newState);
        }

        @Override
        public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
            super.onScrolled(recyclerView, dx, dy);
            int visibleItemCount = linearLayoutManager.getChildCount();
            int totalItemCount = linearLayoutManager.getItemCount();
            int firstVisibleItemPosition = linearLayoutManager.findFirstVisibleItemPosition();

            if (!isLoading && !isLastPage) {
                if ((visibleItemCount + firstVisibleItemPosition) >= totalItemCount
                        && firstVisibleItemPosition >= 0
                        && totalItemCount >= PAGE_SIZE) {
                    isLoading = true;
                    if ((PAGE_SIZE - TOTAL_DATA) == 1) {
                        PAGE_SIZE = PAGE_SIZE + (PAGE_SIZE - 1);
                        listPersonalAdapter.notifyDataSetChanged();
                        listPersonalAdapter.setLoading();
                        new Handler().postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                getPersonal();
                            }
                        }, 3000);
                    }else{
                        listPersonalAdapter.notifyDataSetChanged();
                        listPersonalAdapter.setLoading();
                    }

                }else{
                    if (visibleItemCount >= PAGE_SIZE) {
                        listPersonalAdapter.notifyDataSetChanged();
                        listPersonalAdapter.setLoading();
                    }
                }
            }
        }
    };

    public void DialogFindGroup(){
        AlertDialog.Builder sayWindows = new AlertDialog.Builder(GroupPersonal.this);
        View mView = LayoutInflater.from(GroupPersonal.this).inflate(R.layout.pop_up_dialog_search, null);
        final EditText edtCari = (EditText) mView.findViewById(R.id.edtCari);
        final Button btnCari = (Button) mView.findViewById(R.id.btnSearchText);
        sayWindows.setView(mView);
        final AlertDialog mAlertDialog = sayWindows.create();

        btnCari.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mAlertDialog.dismiss();
                setDefaultValue();
                FIND_TEXT = edtCari.getText().toString().trim();
                getPersonal();
            }
        });
        mAlertDialog.show();
    }

    private void clear() {
        data.clear();
        listPersonalAdapter.notifyItemRangeRemoved(0, data.size());
        listPersonalAdapter.notifyDataSetChanged();
        listPersonalAdapter.setLoading();
    }

    private void setDefaultValue(){
        LIMIT_COUNT = 1;
        PAGE_SIZE = 6;
        TOTAL_DATA = 0;
        FIND_TEXT ="";
        clear();
    }

    private void refreshData(){
        setDefaultValue();
        getPersonal();
    }
}
