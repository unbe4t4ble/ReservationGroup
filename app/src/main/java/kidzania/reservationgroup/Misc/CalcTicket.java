package kidzania.reservationgroup.Misc;

import android.content.Context;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.widget.EditText;
import android.widget.TextView;

import static kidzania.reservationgroup.Misc.FuncGlobal.getFormatedCurrency;
import static kidzania.reservationgroup.Misc.FuncGlobal.getNormalNumber;

/**
 * Created by mubarik on 08/02/2017.
 */

public class CalcTicket implements TextWatcher {

    private int nilai1, nilai2;
    private EditText editText;
    private TextView textView;
    private String harga;
    private Context context;

    public CalcTicket(Context con, EditText editText, TextView textView, String xharga) {
        this.context = con;
        this.editText = editText;
        this.textView = textView;
        this.harga = xharga;
    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {
        if (!(TextUtils.isEmpty(this.editText.getText().toString()))  && (harga != null)) {
            nilai1 = Integer.valueOf(this.editText.getText().toString());
            nilai2 = Integer.valueOf(getNormalNumber(harga));
        }
    }
    @Override
    public void afterTextChanged(Editable s) {
        if (!(TextUtils.isEmpty(this.editText.getText().toString()))) {
            String Hasil = String.valueOf(nilai1 * nilai2);
            this.textView.setText(getFormatedCurrency(context,Hasil));
        }else{
            this.textView.setText(getFormatedCurrency(context,getNormalNumber(harga)));
        }
    }

}


