package kidzania.reservationgroup;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import kidzania.reservationgroup.Misc.Version;
import kidzania.reservationgroup.SQLite.DataSQLlite;

import static kidzania.reservationgroup.Misc.FuncGlobal.CheckConnection;
import static kidzania.reservationgroup.Misc.FuncGlobal.ShowDialogExitApp;
import static kidzania.reservationgroup.Misc.VarGlobal.U_LOGIN;
import static kidzania.reservationgroup.Misc.VarGlobal.dbHelper;

public class MainMenu extends AppCompatActivity {

    Button btnReservation, btnGroup;
    TextView txtUsernameLogin, txtVersion;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_menu);

        dbHelper = new DataSQLlite(this);
        initialization();
        CheckConnection(this);
    }

    private void initialization(){
        btnReservation = (Button) findViewById(R.id.btnReservation);
        btnGroup = (Button) findViewById(R.id.btnGroup);
        txtUsernameLogin = (TextView) findViewById(R.id.txtUsernameLogin);
        txtVersion = (TextView) findViewById(R.id.txtVersion);
        txtUsernameLogin.setText(" "+U_LOGIN);
        txtVersion.setText(" "+Version.VERSION);

        btnReservation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //SingleDialodWithOutVoid(MainMenu.this,"Infomasi", "Belum beres cuyy..");
                OpenQuotaRegister();
            }
        });

        btnGroup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                OpenGroupMenu();
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.button_logout, menu);
        return true;
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_cart:
                AlertDialog.Builder sayWindows = new AlertDialog.Builder(MainMenu.this);
                View mView = LayoutInflater.from(MainMenu.this).inflate(R.layout.pop_two_button, null);
                final TextView txtJudul = (TextView) mView.findViewById(R.id.txtJudul);
                final TextView txtMessage = (TextView) mView.findViewById(R.id.txtMessage);
                final Button btnYes = (Button) mView.findViewById(R.id.btnYes);
                final Button btnNo = (Button) mView.findViewById(R.id.btnNo);
                txtJudul.setText(getString(R.string.message_dialog_confirmation));
                txtMessage.setText(getString(R.string.message_logout_app));
                sayWindows.setView(mView);
                final AlertDialog mAlertDialog = sayWindows.create();

                btnYes.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        mAlertDialog.dismiss();
                        DeleteIdentificationUser();
                        OpenLogin();
                    }
                });
                btnNo.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        mAlertDialog.dismiss();
                    }
                });
                mAlertDialog.show();
                return (true);
        }
        return (super.onOptionsItemSelected(item));
    }

    private void DeleteIdentificationUser(){
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        db.execSQL("delete from tbl_user");
    }


    @Override
    public void onBackPressed() {
        ShowDialogExitApp(this);
    }

    private void OpenQuotaRegister(){
        startActivity(new Intent(MainMenu.this, QuotaRegister.class));
    }

    private void OpenGroupMenu(){
        startActivity(new Intent(MainMenu.this, GroupMenu.class));
    }

    private void OpenLogin(){
        startActivity(new Intent(MainMenu.this, Login.class));
    }
}
