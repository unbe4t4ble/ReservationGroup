package kidzania.reservationgroup;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Gravity;
import android.widget.LinearLayout;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import kidzania.reservationgroup.SQLite.DataSQLlite;

import static kidzania.reservationgroup.Misc.FuncGlobal.getFormatedCurrency;
import static kidzania.reservationgroup.Misc.VarDate.DateReservDD;
import static kidzania.reservationgroup.Misc.VarGlobal.ARR_TIME;
import static kidzania.reservationgroup.Misc.VarGlobal.AllAmountGetPaket;
import static kidzania.reservationgroup.Misc.VarGlobal.COMPLIMENT5;
import static kidzania.reservationgroup.Misc.VarGlobal.HORA_SALIDA;
import static kidzania.reservationgroup.Misc.VarGlobal.ID_NUM_RESER;
import static kidzania.reservationgroup.Misc.VarGlobal.NOTE;
import static kidzania.reservationgroup.Misc.VarGlobal.PriceAdult;
import static kidzania.reservationgroup.Misc.VarGlobal.PriceChild;
import static kidzania.reservationgroup.Misc.VarGlobal.PriceTodd;
import static kidzania.reservationgroup.Misc.VarGlobal.STR_ID_NUM_ESC;
import static kidzania.reservationgroup.Misc.VarGlobal.STR_ID_RESP_ESC;
import static kidzania.reservationgroup.Misc.VarGlobal.TURNO;
import static kidzania.reservationgroup.Misc.VarGlobal.TempAddAdult;
import static kidzania.reservationgroup.Misc.VarGlobal.TempAddChild;
import static kidzania.reservationgroup.Misc.VarGlobal.TempAddTodd;
import static kidzania.reservationgroup.Misc.VarGlobal.TempAdult;
import static kidzania.reservationgroup.Misc.VarGlobal.TempBaby;
import static kidzania.reservationgroup.Misc.VarGlobal.TempChild;
import static kidzania.reservationgroup.Misc.VarGlobal.TempHandyCap;
import static kidzania.reservationgroup.Misc.VarGlobal.TempSenior;
import static kidzania.reservationgroup.Misc.VarGlobal.TempTodd;
import static kidzania.reservationgroup.Misc.VarGlobal.isCombineAll;
import static kidzania.reservationgroup.Misc.VarGlobal.isCombineLunch;
import static kidzania.reservationgroup.Misc.VarGlobal.isCombineSouvenir;


public class ViewReport extends AppCompatActivity {

    TableLayout Table1, Table2;
    TextView RepTglKedatangan, RepWaktuKedatangan, RepNamaGroup, RepSales, RepKeterangan;
    int number = 1;

    DataSQLlite dbHelper;
    protected Cursor cursor;

    int totalbayar = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_report);
        dbHelper = new DataSQLlite(this);
        initialization();
    }

    private void initialization(){
        Table1 = (TableLayout) findViewById(R.id.Table1);
        Table2 = (TableLayout) findViewById(R.id.Table2);
        RepTglKedatangan = (TextView) findViewById(R.id.RepTglKedatangan);
        RepTglKedatangan.setText(DateReservDD);
        RepWaktuKedatangan = (TextView) findViewById(R.id.RepWaktuKedatangan);
        if (TURNO == 0){
            RepWaktuKedatangan.setText("Pagi ("+ARR_TIME+" - "+HORA_SALIDA+")");
        }else {
            RepWaktuKedatangan.setText("Siang ("+ARR_TIME+" - "+ARR_TIME+")");
        }

        RepNamaGroup = (TextView) findViewById(R.id.RepNamaGroup);
        RepNamaGroup.setText(STR_ID_NUM_ESC);
        RepSales = (TextView) findViewById(R.id.RepSales);
        RepSales.setText(STR_ID_RESP_ESC);
        RepKeterangan = (TextView) findViewById(R.id.RepKeterangan);
        RepKeterangan.setText(NOTE);
        addTicketToddler();
        addTicketChild();
        addTicketAdult();
        addTicketOther();
        addTicketAddToodler();
        addTicketAddChild();
        addTicketAddAdult();
        addTicketCompliment();
        addPacketLunch();
        selectLunch();
        addPacketSouv();
        selectSouv();
        addPacketBus();
        selectBus();
        addPacketOther();
        selectOther();
        addTotal();
        addBank();
    }

    private void createTextView(int id, String Number, String Uraian, String Qty, String Harga, String Jumlah){

        TableRow Row = tableRow(id);

        TextView txtNumber = textView(Number);
        txtNumber.setGravity(Gravity.CENTER);
        TextView txtUraian = textView(Uraian);
        TextView txtQty = textView(Qty);
        txtQty.setGravity(Gravity.CENTER);
        TextView txtHarga = textView(Harga);
        txtHarga.setGravity(Gravity.RIGHT);
        TextView txtJumlah = textView(Jumlah);
        txtJumlah.setGravity(Gravity.RIGHT);

        Row.addView(txtNumber);
        Row.addView(txtUraian);
        Row.addView(txtQty);
        Row.addView(txtHarga);
        Row.addView(txtJumlah);
    }

    private TextView textView(String textlabel){
        TextView textView = new TextView(this);
        textView.setText(textlabel);
        textView.setTextColor(Color.BLACK);
        textView.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.FILL_PARENT, TableRow.LayoutParams.WRAP_CONTENT));
        return textView;
    }

    private TableRow tableRow(int id){
        TableRow Row = new TableRow(this);
        Row.setId(id);
        Row.setOrientation(LinearLayout.HORIZONTAL);
        Row.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.FILL_PARENT, TableRow.LayoutParams.WRAP_CONTENT));
        Table2.addView(Row, new TableLayout.LayoutParams(TableLayout.LayoutParams.FILL_PARENT, TableLayout.LayoutParams.WRAP_CONTENT));
        return Row;
    }

    private String price(String oldPrice){
        int price = Integer.valueOf(oldPrice);
        if ((isCombineLunch) &&(CountPackageLunch()>0)){
            price = price + PricePackage("RESERV_PLUNCH");
        }else
        if ((isCombineSouvenir) &&(CountPackageSouv()>0)){
            price = price + PricePackage("RESERV_PSOUV");
        }else
        if ((isCombineAll) && (CountPackageLunch()>0) && (CountPackageSouv()>0)){
            price = price + PricePackage("RESERV_PLUNCH") + PricePackage("RESERV_PSOUV");
        }
        return String.valueOf(price);
    }

    private int PricePackage(String NamePackage){
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        String strSQL = "SELECT PRICE FROM TRANSPACKAGE WHERE ID_NUM_RESER="+ID_NUM_RESER+" AND STATUS = '"+NamePackage+"' LIMIT 1";
        cursor = db.rawQuery(strSQL,null);
        cursor.moveToFirst();
        if (cursor.getCount()>0) {
            return cursor.getInt(0);
        }else{
            return 0;
        }
    }


    private void addTicketToddler(){
        if (Integer.valueOf(TempTodd) > 0 ){
            createTextView(number, String.valueOf(number)+".",
                    "Tiket Masuk Usia 2-3 Tahun",
                    TempTodd,
                    getFormatedCurrency(ViewReport.this, price(PriceTodd)),
                    getFormatedCurrency(ViewReport.this, String.valueOf(Integer.valueOf(TempTodd)*Integer.valueOf(price(PriceTodd)))));
            number = number + 1;
            totalbayar = totalbayar + (Integer.valueOf(TempTodd)*Integer.valueOf(price(PriceTodd)));
        }
    }

    private void addTicketChild(){
        if (Integer.valueOf(TempChild) > 0 ){
            createTextView(number, String.valueOf(number)+".",
                    "Tiket Masuk Usia 4-16 Tahun",
                    TempChild,
                    getFormatedCurrency(ViewReport.this, price(PriceChild)),
                    getFormatedCurrency(ViewReport.this, String.valueOf(Integer.valueOf(TempChild)*Integer.valueOf(price(PriceChild)))));
            number = number + 1;
            totalbayar = totalbayar + (Integer.valueOf(TempChild)*Integer.valueOf(price(PriceChild)));
        }
    }

    private void addTicketAdult(){
        if (Integer.valueOf(TempAdult) > 0 ){
            createTextView(number, String.valueOf(number)+".",
                    "Tiket Masuk Dewasa",
                    TempAdult,
                    getFormatedCurrency(ViewReport.this, price(PriceAdult)),
                    getFormatedCurrency(ViewReport.this, String.valueOf(Integer.valueOf(TempAdult)*Integer.valueOf(price(PriceAdult)))));
            number = number + 1;
            totalbayar = totalbayar + (Integer.valueOf(TempAdult)*Integer.valueOf(price(PriceAdult)));
        }
    }

    private void addTicketOther(){
        if ((Integer.valueOf(TempBaby) > 0)||(Integer.valueOf(TempSenior) > 0)||(Integer.valueOf(TempHandyCap) > 0)){
            createTextView(number, String.valueOf(number)+".",
                    "Tiket Masuk Baby / Senior / Handicap",
                    String.valueOf(Integer.valueOf(TempBaby)+Integer.valueOf(TempSenior)+Integer.valueOf(TempHandyCap)), "0", "0");
            number = number + 1;
        }
    }

    private void addTicketAddToodler(){
        if (Integer.valueOf(TempAddTodd) > 0 ){
            createTextView(number, String.valueOf(number)+".",
                    "Tambahan tiket Masuk Usia 2-3 Tahun",
                    TempAddTodd, getFormatedCurrency(ViewReport.this, PriceTodd),
                    getFormatedCurrency(ViewReport.this, String.valueOf(Integer.valueOf(TempAddTodd)*Integer.valueOf(PriceTodd))));
            number = number + 1;
            totalbayar = totalbayar + (Integer.valueOf(TempAddTodd)*Integer.valueOf(PriceTodd));
        }
    }

    private void addTicketAddChild(){
        if (Integer.valueOf(TempAddChild) > 0 ){
            createTextView(number, String.valueOf(number)+".",
                    "Tambahan tiket Masuk Usia 4-16 Tahun",
                    TempAddChild, getFormatedCurrency(ViewReport.this, PriceChild),
                    getFormatedCurrency(ViewReport.this, String.valueOf(Integer.valueOf(TempAddChild)*Integer.valueOf(PriceChild))));
            number = number + 1;
            totalbayar = totalbayar + (Integer.valueOf(TempAddChild)*Integer.valueOf(PriceChild));
        }
    }

    private void addTicketAddAdult(){
        if (Integer.valueOf(TempAddAdult) > 0 ){
            createTextView(number, String.valueOf(number)+".",
                    "Tambahan tiket Masuk Dewasa",
                    TempAddAdult,
                    getFormatedCurrency(ViewReport.this, PriceAdult),
                    getFormatedCurrency(ViewReport.this, String.valueOf(Integer.valueOf(TempAddAdult)*Integer.valueOf(PriceAdult))));
            number = number + 1;
            totalbayar = totalbayar + (Integer.valueOf(TempAddAdult)*Integer.valueOf(PriceAdult));
        }
    }

    private void addTicketCompliment(){
        if (Integer.valueOf(COMPLIMENT5) > 0 ){
            createTextView(number, String.valueOf(number)+".",
                    "Tiket Masuk Pendamping Dewasa (Free Ticket)",
                    COMPLIMENT5,
                    "- "+getFormatedCurrency(ViewReport.this, price(PriceAdult)),
                    "- "+getFormatedCurrency(ViewReport.this, String.valueOf(Integer.valueOf(COMPLIMENT5)*Integer.valueOf(price(PriceAdult)))));
            number = number + 1;
            totalbayar = totalbayar - (Integer.valueOf(COMPLIMENT5)*Integer.valueOf(price(PriceAdult)));
        }
    }


    private void selectLunch(){
        boolean hasCombine = false;
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        String strSQL = "SELECT PNAME, PRICE, QUANTITY, TOTAL FROM TRANSPACKAGE "+
                "WHERE ID_NUM_RESER="+ID_NUM_RESER+" AND STATUS = 'RESERV_PLUNCH'";
        cursor = db.rawQuery(strSQL,null);
        cursor.moveToFirst();
        int no = 0;
        if (cursor.getCount()>0) {
            do {
                no = no + 1;
                String pname = cursor.getString(cursor.getColumnIndex("PNAME"));
                String price = cursor.getString(cursor.getColumnIndex("PRICE"));
                String qty = cursor.getString(cursor.getColumnIndex("QUANTITY"));
                if(isCombineLunch || isCombineAll) {
                    if (Integer.valueOf(qty) == AllAmountGetPaket &&(!hasCombine)) {
                        price = "0";
                        createTextView(number, "", String.valueOf(no)+". "+pname,
                                qty,
                                getFormatedCurrency(ViewReport.this, price),
                                getFormatedCurrency(ViewReport.this, String.valueOf(Integer.valueOf(qty)*Integer.valueOf(price))));
                        hasCombine = true;
                    }else if ((Integer.valueOf(qty) > AllAmountGetPaket)&&(!hasCombine)){
                            qty = String.valueOf(Integer.valueOf(qty) - AllAmountGetPaket);
                            createTextView(number, "", String.valueOf(no) + ". " + pname,
                                    String.valueOf(AllAmountGetPaket),
                                    "0",
                                    "0");
                            no = no + 1;
                            createTextView(number, "", String.valueOf(no) + ". " + pname,
                                    qty,
                                    getFormatedCurrency(ViewReport.this, price),
                                    getFormatedCurrency(ViewReport.this, String.valueOf(Integer.valueOf(qty) * Integer.valueOf(price))));
                            hasCombine = true;
                    }else {
                        createTextView(number, "", String.valueOf(no)+". "+pname,
                                qty,
                                getFormatedCurrency(ViewReport.this,price),
                                getFormatedCurrency(ViewReport.this, String.valueOf(Integer.valueOf(qty)*Integer.valueOf(price))));
                    }
                }else{
                    createTextView(number, "", String.valueOf(no)+". "+pname,
                            qty,
                            getFormatedCurrency(ViewReport.this, price),
                            getFormatedCurrency(ViewReport.this, String.valueOf(Integer.valueOf(qty)*Integer.valueOf(price))));
                }

                totalbayar = totalbayar + (Integer.valueOf(price)*Integer.valueOf(qty));
            } while (cursor.moveToNext());
        }
    }

    private int CountPackageLunch(){
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        String strSQL = "SELECT COUNT(*) AS TOTAL FROM TRANSPACKAGE WHERE ID_NUM_RESER="+ID_NUM_RESER+" AND STATUS = 'RESERV_PLUNCH'";
        cursor = db.rawQuery(strSQL,null);
        cursor.moveToFirst();
        if (cursor.getCount()>0) {
            return cursor.getInt(0);
        }else{
            return 0;
        }
    }

    private void addPacketLunch(){
        if (CountPackageLunch() > 0){
            createTextView(number, String.valueOf(number)+".", "Paket Makan", " ", "", "");
            number = number + 1;
        }
    }

    private void selectSouv(){
        boolean hasCombine = false;
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        String strSQL = "SELECT PNAME, PRICE, QUANTITY, TOTAL FROM TRANSPACKAGE "+
                "WHERE ID_NUM_RESER="+ID_NUM_RESER+" AND STATUS = 'RESERV_PSOUV'";
        cursor = db.rawQuery(strSQL,null);
        cursor.moveToFirst();
        int no = 0;
        if (cursor.getCount()>0) {
            do {
                no = no + 1;
                String pname = cursor.getString(cursor.getColumnIndex("PNAME"));
                String price = cursor.getString(cursor.getColumnIndex("PRICE"));
                String qty = cursor.getString(cursor.getColumnIndex("QUANTITY"));
                if(isCombineSouvenir || isCombineAll) {
                    if (Integer.valueOf(qty) == AllAmountGetPaket&&(!hasCombine)) {
                        price = "0";
                        createTextView(number, "", String.valueOf(no)+". "+pname,
                                qty,
                                getFormatedCurrency(ViewReport.this, price),
                                getFormatedCurrency(ViewReport.this, String.valueOf(Integer.valueOf(qty)*Integer.valueOf(price))));
                        hasCombine = true;
                    }else if ((Integer.valueOf(qty) > AllAmountGetPaket)&&(!hasCombine)){
                        qty = String.valueOf(Integer.valueOf(qty) - AllAmountGetPaket);
                        createTextView(number, "", String.valueOf(no) + ". " + pname,
                                String.valueOf(AllAmountGetPaket),
                                "0",
                                "0");
                        no = no + 1;
                        createTextView(number, "", String.valueOf(no) + ". " + pname,
                                qty,
                                getFormatedCurrency(ViewReport.this, price),
                                getFormatedCurrency(ViewReport.this, String.valueOf(Integer.valueOf(qty) * Integer.valueOf(price))));
                        hasCombine = true;
                    }else {
                        createTextView(number, "", String.valueOf(no)+". "+pname,
                                qty,
                                getFormatedCurrency(ViewReport.this, price),
                                getFormatedCurrency(ViewReport.this, String.valueOf(Integer.valueOf(qty)*Integer.valueOf(price))));
                    }
                }else{
                    createTextView(number, "", String.valueOf(no)+". "+pname,
                            qty,
                            getFormatedCurrency(ViewReport.this, price),
                            getFormatedCurrency(ViewReport.this, String.valueOf(Integer.valueOf(qty)*Integer.valueOf(price))));
                }
                totalbayar = totalbayar + (Integer.valueOf(price)*Integer.valueOf(qty));
            } while (cursor.moveToNext());
        }
    }

    private int CountPackageSouv(){
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        String strSQL = "SELECT COUNT(*) AS TOTAL FROM TRANSPACKAGE WHERE ID_NUM_RESER="+ID_NUM_RESER+" AND STATUS = 'RESERV_PSOUV'";
        cursor = db.rawQuery(strSQL,null);
        cursor.moveToFirst();
        if (cursor.getCount()>0) {
            return cursor.getInt(0);
        }else{
            return 0;
        }
    }

    private void addPacketSouv(){
        if (CountPackageSouv() > 0){
            createTextView(number, String.valueOf(number)+".", "Paket Merchandise", " ", "", "");
            number = number + 1;
        }
    }

    private void selectBus(){
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        String strSQL = "SELECT PNAME, PRICE, QUANTITY, TOTAL FROM TRANSPACKAGE "+
                "WHERE ID_NUM_RESER="+ID_NUM_RESER+" AND STATUS = 'RESERV_PBUS'";
        cursor = db.rawQuery(strSQL,null);
        cursor.moveToFirst();
        int no = 0;
        if (cursor.getCount()>0) {
            do {
                no = no + 1;
                String pname = cursor.getString(cursor.getColumnIndex("PNAME"));
                String price = cursor.getString(cursor.getColumnIndex("PRICE"));
                String qty = cursor.getString(cursor.getColumnIndex("QUANTITY"));
                createTextView(number, "", String.valueOf(no)+". "+pname,
                        qty,
                        getFormatedCurrency(ViewReport.this, price),
                        getFormatedCurrency(ViewReport.this, String.valueOf(Integer.valueOf(qty)*Integer.valueOf(price))));
                totalbayar = totalbayar + (Integer.valueOf(price)*Integer.valueOf(qty));
            } while (cursor.moveToNext());
        }
    }

    private int CountPackageBus(){
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        String strSQL = "SELECT COUNT(*) AS TOTAL FROM TRANSPACKAGE WHERE ID_NUM_RESER="+ID_NUM_RESER+" AND STATUS = 'RESERV_PBUS'";
        cursor = db.rawQuery(strSQL,null);
        cursor.moveToFirst();
        if (cursor.getCount()>0) {
            return cursor.getInt(0);
        }else{
            return 0;
        }
    }

    private void addPacketBus(){
        if (CountPackageBus() > 0){
            createTextView(number, String.valueOf(number)+".", "Paket Bus", "", "", "");
            number = number + 1;
        }
    }

    private void selectOther(){
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        String strSQL = "SELECT PNAME, PRICE, QUANTITY, TOTAL FROM TRANSPACKAGE "+
                "WHERE ID_NUM_RESER="+ID_NUM_RESER+" AND STATUS = 'RESERV_POTH'";
        cursor = db.rawQuery(strSQL,null);
        cursor.moveToFirst();
        int no = 0;
        if (cursor.getCount()>0) {
            do {
                no = no + 1;
                String pname = cursor.getString(cursor.getColumnIndex("PNAME"));
                String price = cursor.getString(cursor.getColumnIndex("PRICE"));
                String qty = cursor.getString(cursor.getColumnIndex("QUANTITY"));
                createTextView(number, "", String.valueOf(no)+". "+pname,
                        qty,
                        getFormatedCurrency(ViewReport.this, price),
                        getFormatedCurrency(ViewReport.this, String.valueOf(Integer.valueOf(qty)*Integer.valueOf(price))));
                totalbayar = totalbayar + (Integer.valueOf(price)*Integer.valueOf(qty));
            } while (cursor.moveToNext());
        }
    }

    private int CountPackageOther(){
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        String strSQL = "SELECT COUNT(*) AS TOTAL FROM TRANSPACKAGE WHERE ID_NUM_RESER="+ID_NUM_RESER+" AND STATUS = 'RESERV_POTH'";
        cursor = db.rawQuery(strSQL,null);
        cursor.moveToFirst();
        if (cursor.getCount()>0) {
            return cursor.getInt(0);
        }else{
            return 0;
        }
    }

    private void addPacketOther(){
        if (CountPackageOther() > 0){
            createTextView(number, String.valueOf(number)+".", "Paket Lain-lain", "", "", "");
            number = number + 1;
        }
    }

    private void addTotal(){
        for(int i=0; i<5; i++) {
            createTextView(number, "", "", "", "", "");
            number = number + 1;
        }
        createTextView(number, "", "", "", "Jumlah", getFormatedCurrency(ViewReport.this, String.valueOf(totalbayar)));
        number = number + 1;
    }

    private void addBank(){
        for(int i=0; i<3; i++) {
            createTextView(number, "", "", "", "", "");
            number = number + 1;
        }
        createTextView(number, "", "Untuk pembayaran transfer, harap ditujukan ke :", "", "","");
        number = number + 1;
        createTextView(number, "", "Bank                 : BCA KCP SUDIRMAN MANSION", "", "","");
        number = number + 1;
        createTextView(number, "", "No. Rekening : 537.511.1222", "", "","");
        number = number + 1;
        createTextView(number, "", "Atas Nama     : PT.ARYAN INDONESIA", "", "","");
        number = number + 1;
    }

}
