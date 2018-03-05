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
import kidzania.reservationgroup.Adapter.ListGroupAdapter;
import kidzania.reservationgroup.Model.GroupModel;
import kidzania.reservationgroup.ViewHolder.WrapContentLinearLayoutManager;

import static kidzania.reservationgroup.Misc.FuncGlobal.CheckConnection;
import static kidzania.reservationgroup.Misc.FuncGlobal.clearAPIParams;
import static kidzania.reservationgroup.Misc.FuncGlobal.clearAPIValueParam;
import static kidzania.reservationgroup.Misc.VarGlobal.APIParameters;
import static kidzania.reservationgroup.Misc.VarGlobal.APIValueParams;
import static kidzania.reservationgroup.Misc.VarGlobal.GROUP_TIMEOUT;
import static kidzania.reservationgroup.Misc.VarGlobal.GROUP_TRY_AGAIN;
import static kidzania.reservationgroup.Misc.VarGlobal.ID_USER;

public class ParentListGroup extends AppCompatActivity {

    public static ListGroupAdapter listGroupAdapter;
    RecyclerView myRecyclerviewGroup;

    private WrapContentLinearLayoutManager linearLayoutManager;
    private boolean isLastPage = false;
    private boolean isLoading = false;

    public static List<GroupModel> data = new ArrayList<>();

    private int LIMIT_FIRST = 1;
    private int LIMIT_COUNT;
    private int TOTAL_DATA = 0;
    private int PAGE_SIZE = 6;
    private String FIND_TEXT = ""; //untuk nama group
    private String FIND_TEXT_DISTRIK = "";

    final String PARAM_ID_USER="id_user";
    final String PARAM_PAGING_FIRST="paging_first";
    final String PARAM_PAGING_LAST="paging_last";
    final String PARAM_FIND_TEXT="find_text";
    final String PARAM_FIND_TEXT_DISTRIK="find_text_distrik";

    public static String HEADER_GROUP;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_parent_list_group);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        CheckConnection(this);
        initialization();
    }

    @Override
    public void onResume() {
        super.onResume();
        registerReceiver(mMessageTimeOut, new IntentFilter(GROUP_TIMEOUT));
        registerReceiver(mMessageLoadMore, new IntentFilter(GROUP_TRY_AGAIN));
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
            listGroupAdapter.notifyDataSetChanged();
            listGroupAdapter.setTryAgain();
        }
    };

    //This is the handler that will manager to process the broadcast intent
    private BroadcastReceiver mMessageLoadMore = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            listGroupAdapter.notifyDataSetChanged();
            listGroupAdapter.setLoading();
            getGroup();
        }
    };

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.button_refresh, menu);
        inflater.inflate(R.menu.button_cari, menu);
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
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    public void initialization() {
        myRecyclerviewGroup = (RecyclerView) findViewById(R.id.myRecyclerviewGroup);
        listGroupAdapter = new ListGroupAdapter(data);
        myRecyclerviewGroup.setAdapter(listGroupAdapter);
        linearLayoutManager = new WrapContentLinearLayoutManager(ParentListGroup.this);
        myRecyclerviewGroup.setLayoutManager(linearLayoutManager);

        myRecyclerviewGroup.addOnScrollListener(recyclerViewOnScrollListener);

        LIMIT_COUNT = LIMIT_FIRST;
        clear();
    }

    private void AssignParam(){
        clearAPIParams();
        APIParameters.add(PARAM_ID_USER);
        APIParameters.add(PARAM_PAGING_FIRST);
        APIParameters.add(PARAM_PAGING_LAST);
        APIParameters.add(PARAM_FIND_TEXT);
        APIParameters.add(PARAM_FIND_TEXT_DISTRIK);
    }

    private void AssignValueParam(){
        clearAPIValueParam();
        APIValueParams.add(ID_USER);
        APIValueParams.add(String.valueOf(LIMIT_COUNT));
        APIValueParams.add(String.valueOf(PAGE_SIZE));
        APIValueParams.add(FIND_TEXT);
        APIValueParams.add(FIND_TEXT_DISTRIK);
    }

    public void getGroup(){
        isLoading = false;
        AssignParam();
        AssignValueParam();
    }

    public MultiParamGetDataJSON.JSONObjectResult json_group = new MultiParamGetDataJSON.JSONObjectResult() {
        @Override
        public void gotJSONObject(JSONObject jsonObject) {
            try {
                JSONArray jsonArray = jsonObject.getJSONArray(HEADER_GROUP);
                for (int i = 0; i < jsonArray.length(); i++) {
                    JSONObject objData = jsonArray.getJSONObject(i);
                    GroupModel model = new GroupModel();
                    model.setid_num_esc(objData.getString("ID_NUM_ESC"));
                    model.setgrpname(objData.getString("GRPNAME"));
                    model.setfecha_alta(objData.getString("FECHA_ALTA"));
                    model.setgrade(objData.getString("GRADE"));
                    model.setgrade_typ(objData.getString("GRADE_TYP"));
                    model.setaddr(objData.getString("ADDR"));
                    model.setarea(objData.getString("AREA"));
                    model.setdistrict(objData.getString("DISTRICT"));
                    model.setcity(objData.getString("CITY"));
                    model.setprovince(objData.getString("PROVINCE"));
                    model.setphone(objData.getString("PHONE"));
                    model.setemail(objData.getString("EMAIL"));
                    model.setprincipal(objData.getString("PRINCIPAL"));
                    model.setprinc_hp(objData.getString("PRINC_HP"));
                    model.setpic(objData.getString("PIC"));
                    model.setno_hp(objData.getString("NO_HP"));
                    model.setulogin(objData.getString("ULOGIN"));
                    model.setidusr_own(objData.getString("IDUSR_OWN"));
                    data.add(model);
                    LIMIT_COUNT++;
                    TOTAL_DATA++;
                }
                listGroupAdapter.notifyItemInserted(data.size());

                if (jsonArray.length() < (PAGE_SIZE - 1)){
                    listGroupAdapter.setHideLoadingAndTryAgain();
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
                        listGroupAdapter.notifyDataSetChanged();
                        listGroupAdapter.setLoading();
                        new Handler().postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                getGroup();
                            }
                        }, 3000);
                    }else{
                        listGroupAdapter.notifyDataSetChanged();
                        listGroupAdapter.setLoading();
                    }

                }else{
                    if (visibleItemCount >= PAGE_SIZE) {
                        listGroupAdapter.notifyDataSetChanged();
                        listGroupAdapter.setLoading();
                    }
                }
            }
        }
    };

    public void DialogFindGroup(){
        AlertDialog.Builder sayWindows = new AlertDialog.Builder(ParentListGroup.this);
        View mView = LayoutInflater.from(ParentListGroup.this).inflate(R.layout.pop_up_dialog_search_group, null);
        final EditText edtCariGroupName = (EditText) mView.findViewById(R.id.edtCariGroupName);
        final EditText edtDistricName = (EditText) mView.findViewById(R.id.edtCariGroupDistrict);
        final Button btnCari = (Button) mView.findViewById(R.id.btnSearchText);
        sayWindows.setView(mView);
        final AlertDialog mAlertDialog = sayWindows.create();

        btnCari.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mAlertDialog.dismiss();
                setDefaultValue();
                FIND_TEXT = edtCariGroupName.getText().toString().trim();
                FIND_TEXT_DISTRIK = edtDistricName.getText().toString().trim();
                getGroup();
            }
        });
        mAlertDialog.show();
    }

    private void clear() {
        data.clear();
        listGroupAdapter.notifyItemRangeRemoved(0, data.size());
        listGroupAdapter.notifyDataSetChanged();
        listGroupAdapter.setLoading();
    }

    public void setDefaultValue(){
        LIMIT_COUNT = 1;
        PAGE_SIZE = 6;
        TOTAL_DATA = 0;
        FIND_TEXT ="";
        FIND_TEXT_DISTRIK = "";
        clear();
    }

    public void refreshData(){
        setDefaultValue();
        getGroup();
    }

}
