package kidzania.reservationgroup;

import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

import kidzania.reservationgroup.Adapter.SQLiteListAdapterPackage;
import kidzania.reservationgroup.Misc.CalcPack;
import kidzania.reservationgroup.SQLite.DataSQLlite;

import static kidzania.reservationgroup.Misc.FuncGlobal.getFormatedCurrency;
import static kidzania.reservationgroup.Misc.FuncGlobal.getInformationUser;
import static kidzania.reservationgroup.Misc.FuncGlobal.getNormalNumber;
import static kidzania.reservationgroup.Misc.VarGlobal.HARGA_PACKAGE;
import static kidzania.reservationgroup.Misc.VarGlobal.ID_NUM_RESER;
import static kidzania.reservationgroup.Misc.VarGlobal.ID_PACK;
import static kidzania.reservationgroup.Misc.VarGlobal.SENDER_CLASS;
import static kidzania.reservationgroup.Misc.VarGlobal.STR_PACK;
import static kidzania.reservationgroup.Misc.VarGlobal.TAG_PACK;

public class AddPackage extends AppCompatActivity {

    EditText edtPackage, edtQuantity, edtPrice, edtTotal;
    Button btnSavePackage;
    TextView viewTotal, viewItems;
    ListView ListPackage;

    DataSQLlite dbHelper;
    protected Cursor cursor;

    SQLiteListAdapterPackage ListAdapter ;

    ArrayList<String> NAME_PACK_ArrayList = new ArrayList<>();
    ArrayList<String> QUANTITY_ArrayList = new ArrayList<>();
    ArrayList<String> PRICE_ArrayList = new ArrayList<>();
    ArrayList<String> TOTAL_ArrayList = new ArrayList<>();
    ArrayList<Integer> Id_PACK_ArrayList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_package);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        dbHelper = new DataSQLlite(this);
        initialization();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void initialization(){
        edtPackage = (EditText) findViewById(R.id.edtPackage);
        edtQuantity = (EditText) findViewById(R.id.edtQuantity);
        edtPrice = (EditText) findViewById(R.id.edtPrice);
        edtTotal = (EditText) findViewById(R.id.edtTotal);
        viewTotal = (TextView) findViewById(R.id.viewTotal);
        viewTotal.setText("0");
        viewItems = (TextView) findViewById(R.id.viewItems);
        viewItems.setText("0");
        btnSavePackage = (Button) findViewById(R.id.btnSavePackage);
        ListPackage = (ListView) findViewById(R.id.ListPackage);
        edtPackage.setOnClickListener(showSearchPackage);
        btnSavePackage.setOnClickListener(savePackage);

        ListPackage.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, final int position, long id) {
                AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(AddPackage.this);
                alertDialogBuilder.setTitle("'"+String.valueOf(NAME_PACK_ArrayList.get(position))+"' "+getString(R.string.message_confirm_delete_package1));
                alertDialogBuilder
                        .setMessage(getString(R.string.message_confirm_delete_package1))
                        .setCancelable(false)
                        .setPositiveButton(getString(R.string.btn_yes_message),
                                new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int id) {
                                        DeletePackage(String.valueOf(Id_PACK_ArrayList.get(position)));
                                        onResume();
                                    }
                                })

                        .setNegativeButton(getString(R.string.btn_no_message), new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {

                                dialog.cancel();
                            }
                        });

                AlertDialog alertDialog = alertDialogBuilder.create();
                alertDialog.show();
            }
        });
    }

    @Override
    public void onResume(){
        super.onResume();
        getInformationUser();
        showResultSearch();
        ShowSQLiteDBdata();
        SUMPackage();
    }

    private void showResultSearch(){
        if (SENDER_CLASS.equals("SearchPackage")){
            edtPackage.setText(STR_PACK);
            edtPrice.setText(HARGA_PACKAGE);

            int total = Integer.valueOf(getNormalNumber(HARGA_PACKAGE));
            String qty = edtQuantity.getText().toString();

            if ((!TextUtils.isEmpty(qty)) && (Integer.valueOf(qty) > 0)){
                total = total * Integer.valueOf(qty);
            }

            edtTotal.setText(getFormatedCurrency(this,String.valueOf(total)));
            setOnChangeEditTextMain();
        }
    }

    View.OnClickListener showSearchPackage = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            startActivity(new Intent(AddPackage.this, SearchPackage.class));
        }
    };

    View.OnClickListener savePackage = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            String harga = getNormalNumber(HARGA_PACKAGE);
            String total_harga = getNormalNumber(edtTotal.getText().toString());
            SaveTRANSPACKAGE(ID_NUM_RESER,
                    ID_PACK,
                    STR_PACK,
                    harga,
                    edtQuantity.getText().toString(),
                    total_harga,
                    TAG_PACK);
        }
    };



    private void setOnChangeEditTextMain() {
        CalcPack edtQuantityChange = new CalcPack(this, edtQuantity, edtPrice, edtTotal);
        edtQuantity.addTextChangedListener(edtQuantityChange);
    }

    private void SaveTRANSPACKAGE(String ID_NUM_RESER, String ID_PACKAGE, String PNAME, String PRICE, String QUANTITY, String TOTAL,String STATUS){

        SQLiteDatabase db = dbHelper.getWritableDatabase();

        if (!ID_PACKAGE.equals("0")) {
            if ((!(edtQuantity.getText().toString().isEmpty())) &&
                    (!(edtQuantity.getText().toString().equals("0")))) {

                DeletePackage(ID_PACKAGE);

                String strSQLInsert = "insert into TRANSPACKAGE(ID_NUM_RESER, ID_PACKAGE, PNAME, PRICE, QUANTITY, TOTAL, STATUS) values('" +
                        ID_NUM_RESER +"','"+
                        ID_PACKAGE +"','" +
                        PNAME +"','"+
                        PRICE +"','" +
                        QUANTITY +"','"+
                        TOTAL +"','" +
                        STATUS + "')";

                db.execSQL(strSQLInsert);

            }else{
                Toast.makeText(AddPackage.this, getString(R.string.message_invalid_quantity_package), Toast.LENGTH_LONG).show();
            }
        }
        onResume();
    }

    private void ShowSQLiteDBdata() {

        SQLiteDatabase db = dbHelper.getReadableDatabase();
        String strSQL = "SELECT ID_PACKAGE, PNAME, PRICE, QUANTITY, TOTAL FROM TRANSPACKAGE "+
                "WHERE ID_NUM_RESER="+ID_NUM_RESER+" AND STATUS = '"+TAG_PACK+"'";
        cursor = db.rawQuery(strSQL,null);

        NAME_PACK_ArrayList.clear();
        QUANTITY_ArrayList.clear();
        PRICE_ArrayList.clear();
        TOTAL_ArrayList.clear();
        Id_PACK_ArrayList.clear();

        cursor.moveToFirst();
        if (cursor.getCount()>0) {
            do {
                NAME_PACK_ArrayList.add(cursor.getString(cursor.getColumnIndex("PNAME")));

                QUANTITY_ArrayList.add(cursor.getString(cursor.getColumnIndex("QUANTITY")));

                PRICE_ArrayList.add(getFormatedCurrency(this, cursor.getString(cursor.getColumnIndex("PRICE"))));

                TOTAL_ArrayList.add(getFormatedCurrency(this, cursor.getString(cursor.getColumnIndex("TOTAL"))));

                Id_PACK_ArrayList.add(cursor.getInt(cursor.getColumnIndex("ID_PACKAGE")));

            } while (cursor.moveToNext());
        }

        ListAdapter = new SQLiteListAdapterPackage(AddPackage.this,

                NAME_PACK_ArrayList,
                QUANTITY_ArrayList,
                PRICE_ArrayList,
                TOTAL_ArrayList

        );

        ListPackage.setAdapter(ListAdapter);

        cursor.close();
    }

    private void SUMPackage(){
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        String strSQL = "SELECT SUM(TOTAL) AS TOTAL, COUNT(*) AS COUNT FROM TRANSPACKAGE "+
                "WHERE ID_NUM_RESER="+ID_NUM_RESER+" AND STATUS = '"+TAG_PACK+"'";
        cursor = db.rawQuery(strSQL,null);
        cursor.moveToFirst();
        if (cursor.getCount()>0) {
            viewTotal.setText(getFormatedCurrency(this, cursor.getString(cursor.getColumnIndex("TOTAL"))));
            viewItems.setText(cursor.getString(cursor.getColumnIndex("COUNT")));
        }
    }

    private void DeletePackage(String xID_PACKAGE){
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        String strSQL = "DELETE FROM TRANSPACKAGE "+
                "WHERE ID_PACKAGE ="+xID_PACKAGE+" AND ID_NUM_RESER="+ID_NUM_RESER+" AND STATUS = '"+TAG_PACK+"'";
        db.execSQL(strSQL);
    }
}
