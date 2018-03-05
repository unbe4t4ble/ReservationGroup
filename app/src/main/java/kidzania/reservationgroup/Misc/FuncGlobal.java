package kidzania.reservationgroup.Misc;

import android.app.Activity;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import kidzania.reservationgroup.NoConnection;
import kidzania.reservationgroup.R;

import static kidzania.reservationgroup.Misc.VarDate.DayName;
import static kidzania.reservationgroup.Misc.VarGlobal.*;
import static kidzania.reservationgroup.Misc.VarUrl.TOKEN;

/**
 * Created by mubarik on 06/11/2017.
 */

public class FuncGlobal {

    public static void clearAPIValueParam(){
        APIValueParams.clear();
        APIValueParams.add(TOKEN);
    }

    public static void clearAPIParams(){
        APIParameters.clear();
        APIParameters.add("key");
    }

    public static void hideKeyboard(Activity activity){
        activity.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);
    }

    public static String encodePass(String ValueText){
        String encodeResult;
        int i, LengthArray;
        int CodeAscii ;
        char ValueChar;

        i = 0;
        LengthArray = ValueText.length() + 1;
        encodeResult = "";
        while ( i < LengthArray-1 ) {
            ValueChar = ValueText.charAt(i);
            CodeAscii = (int) ValueChar;
            CodeAscii = CodeAscii + LengthArray + i;
            encodeResult = encodeResult + Character.toString((char)CodeAscii);
            i++;
        }
        return encodeResult;
    }

    public static boolean hasConnection(Context context) {

        ConnectivityManager cm = (ConnectivityManager) context.getSystemService(
                Context.CONNECTIVITY_SERVICE);

        NetworkInfo wifiNetwork = cm.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
        if (wifiNetwork != null && wifiNetwork.isConnected()) {
            return true;
        }

        NetworkInfo mobileNetwork = cm.getNetworkInfo(ConnectivityManager.TYPE_MOBILE);
        if (mobileNetwork != null && mobileNetwork.isConnected()) {
            return true;
        }

        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
        if (activeNetwork != null && activeNetwork.isConnected()) {
            return true;
        }

        return false;
    }

    public static void SingleDialodWithOutVoid(Context context, String Judul, String Pesan){
        AlertDialog.Builder sayWindows = new AlertDialog.Builder(context);
        View mView = LayoutInflater.from(context).inflate(R.layout.popup_message, null);
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
            }
        });
        mAlertDialog.show();
    }

    public static void ShowDialogExitApp(final Activity activity){
        AlertDialog.Builder sayWindows = new AlertDialog.Builder(activity);
        View mView = LayoutInflater.from(activity).inflate(R.layout.pop_two_button, null);
        final TextView txtJudul = (TextView) mView.findViewById(R.id.txtJudul);
        final TextView txtMessage = (TextView) mView.findViewById(R.id.txtMessage);
        final Button btnYes = (Button) mView.findViewById(R.id.btnYes);
        final Button btnNo = (Button) mView.findViewById(R.id.btnNo);
        txtJudul.setText(activity.getString(R.string.message_dialog_confirmation));
        txtMessage.setText(activity.getString(R.string.message_exit_app));
        sayWindows.setView(mView);
        final AlertDialog mAlertDialog = sayWindows.create();

        btnYes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mAlertDialog.dismiss();
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                    activity.finishAffinity();
                }
                System.exit(0);
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

    public static void CheckConnection(Context context){
        if(!hasConnection(context)){
            Intent intent = new Intent(context, NoConnection.class);
            context.startActivity(intent);
        }
    }

    public static String DateAndTimeNow(){
        Calendar c = Calendar.getInstance();
        SimpleDateFormat dateNow = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss");
        return " "+dateNow.format(c.getTime());
    }

    public static void DefaultVarGroup(){
        ID_NUM_ESC = ""; //ID Group
        GRPNAME = ""; //Nama Group
        FECHA_ALTA = ""; //Tanggal pembuatan
        STATUS_GROUP = "";
        GRADE = ""; //Tingkat SD/SMP/SMA
        GRADE_TYP = ""; // Tipe Tingkat SD Negeri/SD Swasta dll.
        ADDR = ""; //Alamat Sekolah/Group
        PROVINCE = ""; // Provinci
        CITY = ""; // Kota/Kabupaten.
        DISTRICT = ""; //Kecamatan
        AREA = ""; //Kelurahan
        ZIPCODE = ""; // Kodepos
        PHONE = ""; // telepon sekolah
        FAX = ""; //Fax sekolah
        EMAIL = ""; //email sekolah
        PRINCIPAL = ""; // kepala sekolah/group
        PRINC_HP = ""; // no handphone kepala sekolah
        PIC = ""; // PIC
        NO_HP = ""; // no handphone PIC
        AMNT_T = ""; // jumlah anak dibawah 5 tahun
        AMNT_C = ""; // jumlah anak anak
        AMNT_A = ""; // jumlah guru/dewasa.
        BGT_TRIP = ""; //Bugdet trip
        AMNT_FILTRP = ""; // frekuensi trip
        FILTRP = ""; // field trip/jadwal wisata
        PLC_TRIP = ""; // place trip/tempat wisata
        IDUSR_OWN = ""; // pemilik group/ id Sales
    }

    public static boolean isDeviceSupportCamera(Context context) {
        if (context.getPackageManager().hasSystemFeature(
                PackageManager.FEATURE_CAMERA)) {
            // this device has a camera
            return true;
        } else {
            // no camera on this device
            return false;
        }
    }

    public static String getFormatedCurrency(Context context,String value) {
        String hasil = "";
        try {
            Locale current = context.getResources().getConfiguration().locale;
            DecimalFormat formatter = (DecimalFormat) DecimalFormat.getCurrencyInstance(current);
            formatter.setMinimumFractionDigits(0);
            hasil = formatter.format(Double.valueOf(value));
            hasil = hasil.replace("$","");
            hasil = hasil.replace("Rp","");
            hasil = hasil.replace("£","");
            hasil = hasil.replace(",",".");
            return hasil;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return hasil;
    }

    public static String getNormalNumber(String value){
        String hasil = "";
        hasil = value.replace(",","");
        hasil = hasil.replace(".", "");
        hasil = hasil.replace("Rp ", "");
        return hasil;
    }

    public static void OpenLostConnection(Context context){
        context.startActivity(new Intent(context, NoConnection.class));
    }

    public static boolean CanEditShift(Context context) {
        boolean hasil = false;
        if ((DayName.equals(context.getString(R.string.str_day_friday))) ||
                (DayName.equals(context.getString(R.string.str_day_saturday))) ||
                (DayName.equals(context.getString(R.string.str_day_sunday)))) {
            hasil = true;
        }
        return hasil;
    }

    public static void showTimePicker(Context context, final EditText edt){
        Calendar mcurrentTime = Calendar.getInstance();
        int hour = mcurrentTime.get(Calendar.HOUR_OF_DAY);
        int minute = mcurrentTime.get(Calendar.MINUTE);
        TimePickerDialog mTimePicker;
        mTimePicker = new TimePickerDialog(context, new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker timePicker, int selectedHour, int selectedMinute) {
                String menit;
                if (selectedMinute < 10) {
                    menit = "0"+selectedMinute;
                    edt.setText( selectedHour + ":" + menit);
                }else {
                    edt.setText(selectedHour + ":" + selectedMinute);
                }
            }
        }, hour, minute, true);//Yes 24 hour time
        mTimePicker.setTitle("Select Time");
        mTimePicker.show();
    }

    public static void setDefaultVar(){
        ID_NUM_ESC_RESERVATION = "";
        STR_ID_NUM_ESC = "";
        ID_RESP_ESC = "";
        STR_ID_RESP_ESC = "";
        ID_NUM_AGE = "";
        STR_ID_RESP_AGE = "";
        ID_RESP_AGE = "";
        ID_PAQ = "";
        STR_ID_PAQ = "";
        PriceTodd = "";
        PriceChild = "";
        PriceAdult = "";
        IDPACK_ADD = "";
        STR_IDPACK_ADD = "";
        PriceAddTodd = "";
        PriceAddChild = "";
        PriceAddAdult = "";
        BEBE = "";
        INFANTE = "";
        NINO = "";
        NDISC = "";
        ADULTO = "";
        ADISC = "";
        INSEN = "";
        SENIOR = "";
        HANDICAP = "";
        PROM = "";
        ID_LUNCH = "";
        CANT_LUNCH = "";
        ID_LUNCH1 = "";
        DOC_NO = "";
        ID_SOUV = "";
        SETTLED = "";
        TOTAL_APAGAR = "";
        STATUS_RESERV = "";
        ADD_T5 = "";
        ADD_C5 = "";
        ADD_A5 = "";
        ADD_T7 = "";
        ADD_C7 = "";
        ADD_A7 = "";
        COMPLIMENT5 = "";
        COMPLIMENT7 = "";
        STATUS_RESERVATION = "";
        GPO = "";
        OTH_BUS = "";
        PRIV_CAR = "";
        COMIDA = "";
        SVR_ADICIONAL = "";
        CANT_SVRADI = "";
        PROMOTOR_CODE = "";
        TAX = "";
        RSV_NO = "";
        CB_NAME = "";
        CB_BNAME = "";
        CB_ACCNO = "";
        CB_HP = "";
        CB_TODDLER = "";
        CB_CHILD = "";
        CB_ADULT = "";
        PC = "";
        GRPVOU = "";
        RSV_T5 = "";
        RSV_C5 = "";
        RSV_A5 = "";
        RSV_T7 = "";
        RSV_C7 = "";
        RSV_A7 = "";
        RSV_OTH = "";
        RSV_SEN = "";
        RSV_HAN = "";
        IDPACK_ADD = "";
        NOTE = "";
    }

    public static boolean isValidDate(Context context){
        boolean validDate = true;

        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        Date strDate = null;
        try {
            strDate = sdf.parse(FECHA_VISITA);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        if (System.currentTimeMillis() > strDate.getTime()) {
            Toast.makeText(context, "Invalid date.", Toast.LENGTH_LONG).show();
            validDate = false;
        }
        return validDate;
    }

}
