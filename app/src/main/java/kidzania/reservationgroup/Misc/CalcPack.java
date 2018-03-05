package kidzania.reservationgroup.Misc;

import android.content.Context;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.widget.EditText;

import static kidzania.reservationgroup.Misc.FuncGlobal.getFormatedCurrency;
import static kidzania.reservationgroup.Misc.FuncGlobal.getNormalNumber;


public class CalcPack implements TextWatcher {

    private int nilai1, nilai2;
    private EditText edtQuantity, edtTotal;
    private Context context;
    private String harga;

    public CalcPack(Context con, EditText edtQuantity, EditText edtHarga, EditText edtTotal) {
        this.context = con;
        this.edtTotal = edtTotal;
        this.edtQuantity = edtQuantity;
        this.harga = getNormalNumber(edtHarga.getText().toString());
    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {
        if (!(TextUtils.isEmpty(this.edtQuantity.getText().toString()))  &&
            !(TextUtils.isEmpty(this.harga)) ) {
            nilai1 = Integer.valueOf(this.edtQuantity.getText().toString());
            nilai2 = Integer.valueOf(this.harga);
        }
    }
    @Override
    public void afterTextChanged(Editable s) {
        String qty = edtQuantity.getText().toString();
        if ((!TextUtils.isEmpty(qty)) && (Integer.valueOf(qty) > 0)) {
            String Hasil = String.valueOf(nilai1 * nilai2);
            this.edtTotal.setText(getFormatedCurrency(context, Hasil));
        }else{
            this.edtTotal.setText(getFormatedCurrency(context, this.harga));
        }
    }

}
