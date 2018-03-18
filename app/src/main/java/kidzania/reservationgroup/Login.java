package kidzania.reservationgroup;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import kidzania.reservationgroup.API.MultiParamGetDataJSON;
import kidzania.reservationgroup.SQLite.DataSQLlite;

import static kidzania.reservationgroup.Misc.FuncGlobal.CheckConnection;
import static kidzania.reservationgroup.Misc.FuncGlobal.OpenLostConnection;
import static kidzania.reservationgroup.Misc.FuncGlobal.ShowDialogExitApp;
import static kidzania.reservationgroup.Misc.FuncGlobal.SingleDialodWithOutVoid;
import static kidzania.reservationgroup.Misc.FuncGlobal.clearAPIParams;
import static kidzania.reservationgroup.Misc.FuncGlobal.clearAPIValueParam;
import static kidzania.reservationgroup.Misc.FuncGlobal.encodePass;
import static kidzania.reservationgroup.Misc.FuncGlobal.hasConnection;
import static kidzania.reservationgroup.Misc.FuncGlobal.hideKeyboard;
import static kidzania.reservationgroup.Misc.VarGlobal.APIParameters;
import static kidzania.reservationgroup.Misc.VarGlobal.APIValueParams;
import static kidzania.reservationgroup.Misc.VarGlobal.FLAG;
import static kidzania.reservationgroup.Misc.VarGlobal.GRPRSVMOD;
import static kidzania.reservationgroup.Misc.VarGlobal.HEAD_LOGIN;
import static kidzania.reservationgroup.Misc.VarGlobal.ID_USER;
import static kidzania.reservationgroup.Misc.VarGlobal.U_LOGIN;
import static kidzania.reservationgroup.Misc.VarGlobal.U_PASS;
import static kidzania.reservationgroup.Misc.VarGlobal.dbHelper;
import static kidzania.reservationgroup.Misc.VarGlobal.isAdmin;
import static kidzania.reservationgroup.Misc.VarUrl.URL_USER_LOGIN;

public class Login extends AppCompatActivity {


    Button btnLogin;
    EditText etUserName, etPass;
    Boolean PermissionAccess;

    final String PARAM_USER_NAME="user_name";
    final String PARAM_PASSWORD="password";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        dbHelper = new DataSQLlite(this);

        hideKeyboard(this);

        initialization();

        CheckConnection(this);
    }

    private void initialization(){
        etUserName = (EditText) findViewById(R.id.etUserName);
        etPass     = (EditText) findViewById(R.id.etPass);
        btnLogin   = (Button) findViewById(R.id.btnLogin);

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                OnClickLogin();
            }
        });
    }

    private void AssignParam(){
        clearAPIParams();
        APIParameters.add(PARAM_USER_NAME);
        APIParameters.add(PARAM_PASSWORD);
    }

    private void AssignValueParam(){
        clearAPIValueParam();
        APIValueParams.add(etUserName.getText().toString());
        APIValueParams.add(encodePass(etPass.getText().toString()));
    }

    private void OnClickLogin(){
        AssignParam();
        AssignValueParam();
        if(hasConnection(Login.this)){
            MultiParamGetDataJSON AssignLogin = new MultiParamGetDataJSON();
            AssignLogin.init(APIValueParams, APIParameters, URL_USER_LOGIN, Login.this, JSON_LOGIN, true);
        }else{
            OpenLostConnection(this);
        }
    }

    MultiParamGetDataJSON.JSONObjectResult JSON_LOGIN = new MultiParamGetDataJSON.JSONObjectResult() {
        @Override
        public void gotJSONObject(JSONObject jsonObject) {

            try {
                JSONArray jsonArray = jsonObject.getJSONArray(HEAD_LOGIN);
                for(int i=0; i<jsonArray.length(); i++){
                    JSONObject Gr = jsonArray.getJSONObject(i);
                    ID_USER = Gr.getString("ID_USER");
                    U_LOGIN = Gr.getString("U_LOGIN");
                    U_PASS = Gr.getString("U_PASS");
                    GRPRSVMOD = Gr.getString("GRPRSVMOD");
                    FLAG = Gr.getString("FLAG");
                }
                //Toast.makeText(Login.this, idLogin+"sss", Toast.LENGTH_SHORT).show();
                PermissionAccess = !ID_USER.isEmpty();
                if (PermissionAccess){
                    isAdmin = (U_LOGIN.equals("indah") || U_LOGIN.equals("median"));
                    SaveIdentificationUser(ID_USER, U_LOGIN, U_PASS, GRPRSVMOD, FLAG);
                    OpenMainMenu();
                }else{
                    SingleDialodWithOutVoid(Login.this, getString(R.string.message_dialog_warning), getString(R.string.message_failed_login));
                }

            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    };

    private void SaveIdentificationUser(String ID_User, String U_LOGIN, String U_PASS, String GRP, String FLAG){
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        db.execSQL("delete from tbl_user");
        db.execSQL("insert into tbl_user(ID_USER, U_LOGIN, U_PASS, GRPRSVMOD, FLAG) values('" +
                ID_User +"','"+
                U_LOGIN +"','" +
                U_PASS + "','" +
                GRP +"','" +
                FLAG+"')");
    }

    @Override
    public void onBackPressed() {
        ShowDialogExitApp(this);
    }

    private void OpenMainMenu(){
        startActivity(new Intent(Login.this, MainMenu.class));
        finish();
    }

}
