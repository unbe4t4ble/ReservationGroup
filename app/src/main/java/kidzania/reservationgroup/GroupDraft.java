package kidzania.reservationgroup;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.support.v7.app.AlertDialog;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import kidzania.reservationgroup.API.MultiParamGetDataJSON;

import static kidzania.reservationgroup.Misc.FuncGlobal.OpenLostConnection;
import static kidzania.reservationgroup.Misc.FuncGlobal.clearAPIParams;
import static kidzania.reservationgroup.Misc.FuncGlobal.clearAPIValueParam;
import static kidzania.reservationgroup.Misc.FuncGlobal.hasConnection;
import static kidzania.reservationgroup.Misc.FuncGlobal.setDefaultGroup;
import static kidzania.reservationgroup.Misc.VarGlobal.ADDR;
import static kidzania.reservationgroup.Misc.VarGlobal.AMNT_A;
import static kidzania.reservationgroup.Misc.VarGlobal.AMNT_C;
import static kidzania.reservationgroup.Misc.VarGlobal.AMNT_FILTRP;
import static kidzania.reservationgroup.Misc.VarGlobal.AMNT_T;
import static kidzania.reservationgroup.Misc.VarGlobal.APIParameters;
import static kidzania.reservationgroup.Misc.VarGlobal.APIValueParams;
import static kidzania.reservationgroup.Misc.VarGlobal.AREA;
import static kidzania.reservationgroup.Misc.VarGlobal.BGT_TRIP;
import static kidzania.reservationgroup.Misc.VarGlobal.CITY;
import static kidzania.reservationgroup.Misc.VarGlobal.DISTRICT;
import static kidzania.reservationgroup.Misc.VarGlobal.EMAIL;
import static kidzania.reservationgroup.Misc.VarGlobal.FAX;
import static kidzania.reservationgroup.Misc.VarGlobal.FECHA_ALTA;
import static kidzania.reservationgroup.Misc.VarGlobal.FILTRP;
import static kidzania.reservationgroup.Misc.VarGlobal.GET_DETAIL_DATA_GROUP;
import static kidzania.reservationgroup.Misc.VarGlobal.GRADE;
import static kidzania.reservationgroup.Misc.VarGlobal.GRADE_TYP;
import static kidzania.reservationgroup.Misc.VarGlobal.GRPNAME;
import static kidzania.reservationgroup.Misc.VarGlobal.GTYPE;
import static kidzania.reservationgroup.Misc.VarGlobal.HEAD_GET_DETAIL_GROUP;
import static kidzania.reservationgroup.Misc.VarGlobal.HEAD_GROUP_DRAFT;
import static kidzania.reservationgroup.Misc.VarGlobal.HEAD_STATUS;
import static kidzania.reservationgroup.Misc.VarGlobal.IDUSR_OWN;
import static kidzania.reservationgroup.Misc.VarGlobal.ID_NUM_ESC;
import static kidzania.reservationgroup.Misc.VarGlobal.IS_EDIT;
import static kidzania.reservationgroup.Misc.VarGlobal.NO_HP;
import static kidzania.reservationgroup.Misc.VarGlobal.PHONE;
import static kidzania.reservationgroup.Misc.VarGlobal.PIC;
import static kidzania.reservationgroup.Misc.VarGlobal.PLC_TRIP;
import static kidzania.reservationgroup.Misc.VarGlobal.POSITION_DATA;
import static kidzania.reservationgroup.Misc.VarGlobal.POSTING_GROUP;
import static kidzania.reservationgroup.Misc.VarGlobal.PRINCIPAL;
import static kidzania.reservationgroup.Misc.VarGlobal.PRINC_HP;
import static kidzania.reservationgroup.Misc.VarGlobal.PROVINCE;
import static kidzania.reservationgroup.Misc.VarGlobal.SENDER_CLASS;
import static kidzania.reservationgroup.Misc.VarGlobal.STATUS_GROUP;
import static kidzania.reservationgroup.Misc.VarGlobal.TAKE_PHOTO_GROUP;
import static kidzania.reservationgroup.Misc.VarGlobal.ZIPCODE;
import static kidzania.reservationgroup.Misc.VarGlobal.isAdmin;
import static kidzania.reservationgroup.Misc.VarUrl.URL_DETAIL_DATA_GROUP;
import static kidzania.reservationgroup.Misc.VarUrl.URL_GROUP_DRAFT;
import static kidzania.reservationgroup.Misc.VarUrl.URL_GROUP_DRAFT_ADMIN;
import static kidzania.reservationgroup.Misc.VarUrl.URL_SEND_TO_DATA;

public class GroupDraft extends ParentListGroup {

    @Override
    public void onResume(){
        super.onResume();
        registerReceiver(mMessageGroupData, new IntentFilter(GET_DETAIL_DATA_GROUP));
        registerReceiver(mMessagePhotoImage, new IntentFilter(TAKE_PHOTO_GROUP));
        registerReceiver(mMessageGroupPosting, new IntentFilter(POSTING_GROUP));
        refreshData();
    }

    //Must unregister onPause()
    @Override
    protected void onPause() {
        super.onPause();
        unregisterReceiver(mMessageGroupData);
        unregisterReceiver(mMessagePhotoImage);
        unregisterReceiver(mMessageGroupPosting);
    }

    //This is the handler that will manager to process the broadcast intent
    private BroadcastReceiver mMessageGroupData = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            SENDER_CLASS = "GroupDraft";
            getDataDetailGroup();
        }
    };


    private BroadcastReceiver mMessagePhotoImage = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            OpenPhoto();
        }
    };

    private BroadcastReceiver mMessageGroupPosting = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            sendGroupPosting();
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
            case R.id.btn_add_group:
                showDialogGType();
                return true;
            case R.id.btn_refresh:
                refreshData();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void refreshData(){
        SENDER_CLASS = "GroupDraft";
        super.refreshData();
    }

    @Override
    public void initialization(){
        SENDER_CLASS = "GroupDraft";
        super.initialization();
    }

    @Override
    public void getGroup(){
        super.getGroup();
        HEADER_GROUP = HEAD_GROUP_DRAFT;
        if(hasConnection(GroupDraft.this)) {
            if (!isAdmin) {
                MultiParamGetDataJSON getDataDraft = new MultiParamGetDataJSON();
                getDataDraft.init(APIValueParams, APIParameters, URL_GROUP_DRAFT, GroupDraft.this, json_group, false);
            }else{
                MultiParamGetDataJSON getDataDraft = new MultiParamGetDataJSON();
                getDataDraft.init(APIValueParams, APIParameters, URL_GROUP_DRAFT_ADMIN, GroupDraft.this, json_group, false);
            }
        }else{
            OpenLostConnection(this);
        }
    }

    private void showDialogGType(){
        AlertDialog.Builder sayWindows = new AlertDialog.Builder(GroupDraft.this);
        View mView = getLayoutInflater().inflate(R.layout.group_type, null);
        final RadioButton gtSchool = (RadioButton) mView.findViewById(R.id.gtSchool);
        final RadioButton gtCompany = (RadioButton) mView.findViewById(R.id.gtCompany);
        final RadioButton gtCourse = (RadioButton) mView.findViewById(R.id.gtCourse);
        final RadioButton gtFoundation = (RadioButton) mView.findViewById(R.id.gtFoundation);
        final RadioButton gtPersonal = (RadioButton) mView.findViewById(R.id.gtPersonal);
        gtSchool.setChecked(true);
        GTYPE = "School";
        gtSchool.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                GTYPE = "School";
            }
        });
        gtCompany.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                GTYPE = "Company";
            }
        });

        gtCourse.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                GTYPE = "Course";
            }
        });
        gtFoundation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                GTYPE = "Foundation";
            }
        });
        gtPersonal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                GTYPE = "Personal";
            }
        });

        sayWindows.setPositiveButton("ok", null);
        sayWindows.setNegativeButton("cancel", null);
        sayWindows.setView(mView);
        final AlertDialog mAlertDialog = sayWindows.create();
        mAlertDialog.setOnShowListener(new DialogInterface.OnShowListener() {
            @Override
            public void onShow(final DialogInterface dialog) {

                Button b = mAlertDialog.getButton(AlertDialog.BUTTON_POSITIVE);
                b.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        setDefaultGroup();
                        if (GTYPE.equals("Personal")){
                            OpenListGroupPersonal();
                        }else {
                            OpenAddGroup();
                        }
                        //Toast.makeText(GroupDraft.this, GTYPE, Toast.LENGTH_SHORT).show();
                        mAlertDialog.dismiss();
                    }
                });
            }
        });
        mAlertDialog.show();
    }

    private void AssignParam(){
        clearAPIParams();
        APIParameters.add("id_num_esc");
    }

    private void AssignValueParam(){
        clearAPIValueParam();
        APIValueParams.add(ID_NUM_ESC);
    }

    private void getDataDetailGroup(){
        AssignParam();
        AssignValueParam();
        MultiParamGetDataJSON getDetailData = new MultiParamGetDataJSON();
        getDetailData.init(APIValueParams, APIParameters, URL_DETAIL_DATA_GROUP, GroupDraft.this, json_detail_data, true);
    }

    public MultiParamGetDataJSON.JSONObjectResult json_detail_data = new MultiParamGetDataJSON.JSONObjectResult() {
        @Override
        public void gotJSONObject(JSONObject jsonObject) {
            try {
                JSONArray jsonArray = jsonObject.getJSONArray(HEAD_GET_DETAIL_GROUP);
                for (int i = 0; i < jsonArray.length(); i++) {
                    JSONObject objData = jsonArray.getJSONObject(i);
                    GRPNAME = objData.getString("GRPNAME");
                    FECHA_ALTA = objData.getString("FECHA_ALTA");
                    STATUS_GROUP = objData.getString("STATUS");
                    GTYPE = objData.getString("GTYPE");
                    GRADE = objData.getString("GRADE");
                    GRADE_TYP = objData.getString("GRADE_TYP");
                    ADDR = objData.getString("ADDR");
                    AREA = objData.getString("AREA");
                    DISTRICT = objData.getString("DISTRICT");
                    CITY = objData.getString("CITY");
                    PROVINCE = objData.getString("PROVINCE");
                    PHONE = objData.getString("PHONE");
                    EMAIL = objData.getString("EMAIL");
                    FAX = objData.getString("FAX");
                    ZIPCODE = objData.getString("ZIPCODE");
                    PRINCIPAL = objData.getString("PRINCIPAL");
                    PRINC_HP = objData.getString("PRINC_HP");
                    PIC = objData.getString("PIC");
                    NO_HP = objData.getString("NO_HP");
                    AMNT_T = objData.getString("AMNT_T");
                    AMNT_C = objData.getString("AMNT_C");
                    AMNT_A = objData.getString("AMNT_A");
                    AMNT_FILTRP = objData.getString("AMNT_FILTRP");
                    FILTRP = objData.getString("FILTRP");
                    BGT_TRIP = objData.getString("BGT_TRIP");
                    PLC_TRIP = objData.getString("PLC_TRIP");
                    IDUSR_OWN = objData.getString("IDUSR_OWN");
                }
                IS_EDIT = true;
                startActivity(new Intent(GroupDraft.this, AddGroup.class));
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    };

    private void OpenAddGroup(){
        IS_EDIT = false;
        startActivity(new Intent(GroupDraft.this, AddGroup.class));
    }

    private void OpenPhoto(){
        startActivity(new Intent(GroupDraft.this, Photo.class));
    }

    private void OpenListGroupPersonal(){
        startActivity(new Intent(GroupDraft.this, GroupPersonal.class));
    }

    private void AssignParamPosting(){
        clearAPIParams();
        APIParameters.add("id_num_esc");
    }

    private void AssignValueParamPosting(){
        clearAPIValueParam();
        APIValueParams.add(ID_NUM_ESC);
    }

    public void sendGroupPosting(){
        DialogPosting();
    }

    private void DialogPosting(){
        AlertDialog.Builder sayWindows = new AlertDialog.Builder(GroupDraft.this);
        View mView = LayoutInflater.from(GroupDraft.this).inflate(R.layout.pop_two_button, null);
        final TextView txtJudul = (TextView) mView.findViewById(R.id.txtJudul);
        final TextView txtMessage = (TextView) mView.findViewById(R.id.txtMessage);
        final Button btnYes = (Button) mView.findViewById(R.id.btnYes);
        final Button btnNo = (Button) mView.findViewById(R.id.btnNo);
        txtJudul.setText(getString(R.string.message_dialog_confirmation));
        txtMessage.setText(getString(R.string.message_send_to_posting)+" '"+GRPNAME+"' ?");
        sayWindows.setView(mView);
        final AlertDialog mAlertDialog = sayWindows.create();

        btnYes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mAlertDialog.dismiss();
                excutePosting();
            }
        });
        btnNo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mAlertDialog.dismiss();
            }
        });
        mAlertDialog.show();
    }

    private void excutePosting(){
        AssignParamPosting();
        AssignValueParamPosting();
        MultiParamGetDataJSON posting = new MultiParamGetDataJSON();
        posting.init(APIValueParams, APIParameters, URL_SEND_TO_DATA, GroupDraft.this, json_send_posting, false);
    }

    MultiParamGetDataJSON.JSONObjectResult json_send_posting = new MultiParamGetDataJSON.JSONObjectResult() {
        @Override
        public void gotJSONObject(JSONObject jsonObject) {
            try {
                JSONArray jsonArray = jsonObject.getJSONArray(HEAD_STATUS);
                String ID = null;
                for (int i = 0; i < jsonArray.length(); i++) {
                    JSONObject objData = jsonArray.getJSONObject(i);
                    ID = objData.getString("STATUS");
                }
                if (!TextUtils.isEmpty(ID)) {
                    data.remove(POSITION_DATA);
                    listGroupAdapter.notifyItemRemoved(POSITION_DATA);
                    listGroupAdapter.notifyItemRangeChanged(POSITION_DATA, data.size());
                }

            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    };



}
