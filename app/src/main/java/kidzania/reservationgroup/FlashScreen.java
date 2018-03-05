package kidzania.reservationgroup;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.PixelFormat;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.view.Window;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import kidzania.reservationgroup.SQLite.DataSQLlite;

import static kidzania.reservationgroup.Misc.FuncGlobal.CheckConnection;
import static kidzania.reservationgroup.Misc.VarGlobal.FLAG;
import static kidzania.reservationgroup.Misc.VarGlobal.GRPRSVMOD;
import static kidzania.reservationgroup.Misc.VarGlobal.ID_USER;
import static kidzania.reservationgroup.Misc.VarGlobal.U_LOGIN;
import static kidzania.reservationgroup.Misc.VarGlobal.U_PASS;
import static kidzania.reservationgroup.Misc.VarGlobal.cursor;
import static kidzania.reservationgroup.Misc.VarGlobal.dbHelper;

public class FlashScreen extends AppCompatActivity {

    Thread splashTread;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_flash_screen);
        dbHelper = new DataSQLlite(this);
        StartAnimations();
        CheckConnection(this);
    }

    public void onAttachedToWindow() {
        super.onAttachedToWindow();
        Window window = getWindow();
        window.setFormat(PixelFormat.RGBA_8888);
    }

    private void StartAnimations() {
        Animation anim = AnimationUtils.loadAnimation(this, R.anim.alpha);
        anim.reset();
        RelativeLayout LaySplashScreen=(RelativeLayout) findViewById(R.id.LaySplashScreen);
        LaySplashScreen.clearAnimation();
        LaySplashScreen.startAnimation(anim);

        anim = AnimationUtils.loadAnimation(this, R.anim.translate);
        anim.reset();
        ImageView iv = (ImageView) findViewById(R.id.splash);
        iv.clearAnimation();
        iv.startAnimation(anim);

        splashTread = new Thread() {
            @Override
            public void run() {
                try {
                    int waited = 0;
                    while (waited < 3500) {
                        sleep(100);
                        waited += 100;
                    }

                } catch (InterruptedException e) {
                    e.printStackTrace();
                } finally {
                    //finish();
                }

            }
        };
        splashTread.start();
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                IdentificationUser();
            }
        }, 3000);

    }

    public void IdentificationUser(){
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        cursor = db.rawQuery("SELECT ID_USER, U_LOGIN, U_PASS, GRPRSVMOD, FLAG FROM TBL_USER",null);
        cursor.moveToFirst();
        if (cursor.getCount()>0) {
            cursor.moveToPosition(0);
            ID_USER = cursor.getString(cursor.getColumnIndex("ID_USER"));
            U_LOGIN = cursor.getString(cursor.getColumnIndex("U_LOGIN"));
            U_PASS = cursor.getString(cursor.getColumnIndex("U_PASS"));
            GRPRSVMOD = cursor.getString(cursor.getColumnIndex("GRPRSVMOD"));
            FLAG = cursor.getString(cursor.getColumnIndex("FLAG"));
            OpenMenu();
        }else{
            OpenLogin();
        }
    }

    private void OpenLogin(){
        startActivity(new Intent(FlashScreen.this, Login.class));
        finish();
    }

    private void OpenMenu(){
        startActivity(new Intent(FlashScreen.this, MainMenu.class));
        finish();
    }
}
