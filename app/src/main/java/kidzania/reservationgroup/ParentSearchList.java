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
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import kidzania.reservationgroup.API.MultiParamGetDataJSON;
import kidzania.reservationgroup.Adapter.ListSearchAdapter;
import kidzania.reservationgroup.Model.SearchModel;
import kidzania.reservationgroup.ViewHolder.WrapContentLinearLayoutManager;

import static kidzania.reservationgroup.Misc.FuncGlobal.CheckConnection;
import static kidzania.reservationgroup.Misc.FuncGlobal.clearAPIParams;
import static kidzania.reservationgroup.Misc.FuncGlobal.clearAPIValueParam;
import static kidzania.reservationgroup.Misc.FuncGlobal.getInformationUser;
import static kidzania.reservationgroup.Misc.VarGlobal.APIParameters;
import static kidzania.reservationgroup.Misc.VarGlobal.APIValueParams;
import static kidzania.reservationgroup.Misc.VarGlobal.GROUP_TIMEOUT;
import static kidzania.reservationgroup.Misc.VarGlobal.GROUP_TRY_AGAIN;

public class ParentSearchList extends AppCompatActivity {

    public static ListSearchAdapter listSearchAdapter;

    private WrapContentLinearLayoutManager linearLayoutManager;
    private boolean isLastPage = false;
    private boolean isLoading = false;

    public static List<SearchModel> dataSearch = new ArrayList<>();

    private int LIMIT_FIRST = 1;
    private int LIMIT_COUNT;
    private int TOTAL_DATA = 0;
    private int PAGE_SIZE = 51;
    private String FIND_TEXT = "";

    final String PARAM_PAGING_FIRST="paging_first";
    final String PARAM_PAGING_LAST="paging_last";
    final String PARAM_FIND_TEXT="find_text";

    public static String HEADER_SEARCH;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_parent_search_list);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        initialization();
        CheckConnection(this);
        getSearch();
    }

    @Override
    public void onResume() {
        super.onResume();
        getInformationUser();
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
            listSearchAdapter.notifyDataSetChanged();
            listSearchAdapter.setTryAgain();
        }
    };

    //This is the handler that will manager to process the broadcast intent
    private BroadcastReceiver mMessageLoadMore = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            listSearchAdapter.notifyDataSetChanged();
            listSearchAdapter.setLoading();
            getSearch();
        }
    };

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
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
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    public void initialization() {
        RecyclerView myRecyclerviewGroup = (RecyclerView) findViewById(R.id.myRecyclerviewSearch);
        listSearchAdapter = new ListSearchAdapter(dataSearch);
        myRecyclerviewGroup.setAdapter(listSearchAdapter);
        linearLayoutManager = new WrapContentLinearLayoutManager(ParentSearchList.this);
        myRecyclerviewGroup.setLayoutManager(linearLayoutManager);

        myRecyclerviewGroup.addOnScrollListener(recyclerViewOnScrollListener);

        LIMIT_COUNT = LIMIT_FIRST;
        clear();
    }

    public void AssignParam(){
        clearAPIParams();
        APIParameters.add(PARAM_PAGING_FIRST);
        APIParameters.add(PARAM_PAGING_LAST);
        APIParameters.add(PARAM_FIND_TEXT);
    }

    public void AssignValueParam(){
        clearAPIValueParam();
        APIValueParams.add(String.valueOf(LIMIT_COUNT));
        APIValueParams.add(String.valueOf(PAGE_SIZE));
        APIValueParams.add(FIND_TEXT);
    }

    public void getSearch(){
        isLoading = false;
        AssignParam();
        AssignValueParam();
    }

    public MultiParamGetDataJSON.JSONObjectResult json_search = new MultiParamGetDataJSON.JSONObjectResult() {
        @Override
        public void gotJSONObject(JSONObject jsonObject) {
            try {
                JSONArray jsonArray = jsonObject.getJSONArray(HEADER_SEARCH);
                for (int i = 0; i < jsonArray.length(); i++) {
                    JSONObject objData = jsonArray.getJSONObject(i);
                    SearchModel model = new SearchModel();
                    model.setid_search(objData.getString("ID"));
                    model.settext_search(objData.getString("STR_ID"));
                    dataSearch.add(model);
                    LIMIT_COUNT++;
                    TOTAL_DATA++;
                }
                listSearchAdapter.notifyItemInserted(dataSearch.size());
                if (jsonArray.length() < (PAGE_SIZE - 1)){
                    listSearchAdapter.setHideLoadingAndTryAgain();
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
                        PAGE_SIZE = PAGE_SIZE + (PAGE_SIZE-1);
                        new Handler().postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                getSearch();
                            }
                        }, 3000);
                    }else{
                        listSearchAdapter.notifyDataSetChanged();
                        listSearchAdapter.setLoading();
                    }
                }else{
                    if (visibleItemCount >= PAGE_SIZE) {
                        listSearchAdapter.notifyDataSetChanged();
                        listSearchAdapter.setLoading();
                    }
                }
            }
        }
    };

    public void DialogFindGroup(){
        AlertDialog.Builder sayWindows = new AlertDialog.Builder(ParentSearchList.this);
        View mView = LayoutInflater.from(ParentSearchList.this).inflate(R.layout.pop_up_dialog_search, null);
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
                getSearch();
            }
        });
        mAlertDialog.show();
    }

    private void clear() {
        dataSearch.clear();
        listSearchAdapter.notifyItemRangeRemoved(0, dataSearch.size());
        listSearchAdapter.notifyDataSetChanged();
        listSearchAdapter.setLoading();
    }

    private void setDefaultValue(){
        LIMIT_COUNT = 1;
        PAGE_SIZE = 51;
        TOTAL_DATA = 0;
        FIND_TEXT ="";
        clear();
    }

    public void ShowDialog(String Judul, String Pesan){
        AlertDialog.Builder sayWindows = new AlertDialog.Builder(this);
        View mView = LayoutInflater.from(this).inflate(R.layout.popup_message, null);
        final TextView txtJudul = (TextView) mView.findViewById(R.id.txtJudul);
        final TextView txtMessage = (TextView) mView.findViewById(R.id.txtMessage);
        final Button btnOkMessage = (Button) mView.findViewById(R.id.btnOkMessage);
        txtJudul.setText(Judul);
        txtMessage.setText(Pesan);
        sayWindows.setView(mView);
        final AlertDialog mAlertDialog = sayWindows.create();

        btnOkMessage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mAlertDialog.dismiss();
                getSearch();
            }
        });
        mAlertDialog.show();
    }

}
