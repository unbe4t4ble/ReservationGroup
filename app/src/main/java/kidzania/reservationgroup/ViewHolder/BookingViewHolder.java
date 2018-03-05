package kidzania.reservationgroup.ViewHolder;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import kidzania.reservationgroup.API.MultiParamGetDataJSON;
import kidzania.reservationgroup.ModifReservation;
import kidzania.reservationgroup.R;

import static kidzania.reservationgroup.Misc.FuncGlobal.SingleDialodWithOutVoid;
import static kidzania.reservationgroup.Misc.FuncGlobal.clearAPIParams;
import static kidzania.reservationgroup.Misc.FuncGlobal.clearAPIValueParam;
import static kidzania.reservationgroup.Misc.FuncGlobal.setDefaultVar;
import static kidzania.reservationgroup.Misc.VarGlobal.ADD_A5;
import static kidzania.reservationgroup.Misc.VarGlobal.ADD_A7;
import static kidzania.reservationgroup.Misc.VarGlobal.ADD_C5;
import static kidzania.reservationgroup.Misc.VarGlobal.ADD_C7;
import static kidzania.reservationgroup.Misc.VarGlobal.ADD_T5;
import static kidzania.reservationgroup.Misc.VarGlobal.ADD_T7;
import static kidzania.reservationgroup.Misc.VarGlobal.ADISC;
import static kidzania.reservationgroup.Misc.VarGlobal.ADULTO;
import static kidzania.reservationgroup.Misc.VarGlobal.APIParameters;
import static kidzania.reservationgroup.Misc.VarGlobal.APIValueParams;
import static kidzania.reservationgroup.Misc.VarGlobal.ARR_TIME;
import static kidzania.reservationgroup.Misc.VarGlobal.BEBE;
import static kidzania.reservationgroup.Misc.VarGlobal.CANT_LUNCH;
import static kidzania.reservationgroup.Misc.VarGlobal.CANT_SVRADI;
import static kidzania.reservationgroup.Misc.VarGlobal.CB_ACCNO;
import static kidzania.reservationgroup.Misc.VarGlobal.CB_ADULT;
import static kidzania.reservationgroup.Misc.VarGlobal.CB_BNAME;
import static kidzania.reservationgroup.Misc.VarGlobal.CB_CHILD;
import static kidzania.reservationgroup.Misc.VarGlobal.CB_HP;
import static kidzania.reservationgroup.Misc.VarGlobal.CB_NAME;
import static kidzania.reservationgroup.Misc.VarGlobal.CB_TODDLER;
import static kidzania.reservationgroup.Misc.VarGlobal.COMIDA;
import static kidzania.reservationgroup.Misc.VarGlobal.COMPLIMENT5;
import static kidzania.reservationgroup.Misc.VarGlobal.COMPLIMENT7;
import static kidzania.reservationgroup.Misc.VarGlobal.DOC_NO;
import static kidzania.reservationgroup.Misc.VarGlobal.GPO;
import static kidzania.reservationgroup.Misc.VarGlobal.GRPVOU;
import static kidzania.reservationgroup.Misc.VarGlobal.HANDICAP;
import static kidzania.reservationgroup.Misc.VarGlobal.HEAD_GET_DATA_RESERVATION;
import static kidzania.reservationgroup.Misc.VarGlobal.HEAD_STATUS;
import static kidzania.reservationgroup.Misc.VarGlobal.HORA_SALIDA;
import static kidzania.reservationgroup.Misc.VarGlobal.IDPACK_ADD;
import static kidzania.reservationgroup.Misc.VarGlobal.ID_LUNCH;
import static kidzania.reservationgroup.Misc.VarGlobal.ID_LUNCH1;
import static kidzania.reservationgroup.Misc.VarGlobal.ID_NUM_AGE;
import static kidzania.reservationgroup.Misc.VarGlobal.ID_NUM_ESC_RESERVATION;
import static kidzania.reservationgroup.Misc.VarGlobal.ID_NUM_RESER;
import static kidzania.reservationgroup.Misc.VarGlobal.ID_PAQ;
import static kidzania.reservationgroup.Misc.VarGlobal.ID_RESP_AGE;
import static kidzania.reservationgroup.Misc.VarGlobal.ID_RESP_ESC;
import static kidzania.reservationgroup.Misc.VarGlobal.ID_SOUV;
import static kidzania.reservationgroup.Misc.VarGlobal.INFANTE;
import static kidzania.reservationgroup.Misc.VarGlobal.INSEN;
import static kidzania.reservationgroup.Misc.VarGlobal.NDISC;
import static kidzania.reservationgroup.Misc.VarGlobal.NINO;
import static kidzania.reservationgroup.Misc.VarGlobal.NOTE;
import static kidzania.reservationgroup.Misc.VarGlobal.OTH_BUS;
import static kidzania.reservationgroup.Misc.VarGlobal.PC;
import static kidzania.reservationgroup.Misc.VarGlobal.PRIV_CAR;
import static kidzania.reservationgroup.Misc.VarGlobal.PROM;
import static kidzania.reservationgroup.Misc.VarGlobal.PROMOTOR_CODE;
import static kidzania.reservationgroup.Misc.VarGlobal.PriceAddAdult;
import static kidzania.reservationgroup.Misc.VarGlobal.PriceAddChild;
import static kidzania.reservationgroup.Misc.VarGlobal.PriceAddTodd;
import static kidzania.reservationgroup.Misc.VarGlobal.PriceAdult;
import static kidzania.reservationgroup.Misc.VarGlobal.PriceChild;
import static kidzania.reservationgroup.Misc.VarGlobal.PriceTodd;
import static kidzania.reservationgroup.Misc.VarGlobal.RSV_A5;
import static kidzania.reservationgroup.Misc.VarGlobal.RSV_A7;
import static kidzania.reservationgroup.Misc.VarGlobal.RSV_C5;
import static kidzania.reservationgroup.Misc.VarGlobal.RSV_C7;
import static kidzania.reservationgroup.Misc.VarGlobal.RSV_HAN;
import static kidzania.reservationgroup.Misc.VarGlobal.RSV_NO;
import static kidzania.reservationgroup.Misc.VarGlobal.RSV_OTH;
import static kidzania.reservationgroup.Misc.VarGlobal.RSV_SEN;
import static kidzania.reservationgroup.Misc.VarGlobal.RSV_T5;
import static kidzania.reservationgroup.Misc.VarGlobal.RSV_T7;
import static kidzania.reservationgroup.Misc.VarGlobal.SENIOR;
import static kidzania.reservationgroup.Misc.VarGlobal.SETTLED;
import static kidzania.reservationgroup.Misc.VarGlobal.STATUS_RESERV;
import static kidzania.reservationgroup.Misc.VarGlobal.STATUS_RESERVATION;
import static kidzania.reservationgroup.Misc.VarGlobal.STR_IDPACK_ADD;
import static kidzania.reservationgroup.Misc.VarGlobal.STR_ID_NUM_ESC;
import static kidzania.reservationgroup.Misc.VarGlobal.STR_ID_PAQ;
import static kidzania.reservationgroup.Misc.VarGlobal.STR_ID_RESP_AGE;
import static kidzania.reservationgroup.Misc.VarGlobal.STR_ID_RESP_ESC;
import static kidzania.reservationgroup.Misc.VarGlobal.SVR_ADICIONAL;
import static kidzania.reservationgroup.Misc.VarGlobal.TAX;
import static kidzania.reservationgroup.Misc.VarGlobal.TOTAL_APAGAR;
import static kidzania.reservationgroup.Misc.VarGlobal.USUARIO_ALTA;
import static kidzania.reservationgroup.Misc.VarGlobal.isModifReservation;
import static kidzania.reservationgroup.Misc.VarUrl.URL_EDIT_CANCEL_BOOKING;
import static kidzania.reservationgroup.Misc.VarUrl.URL_GET_DATA_RESERVATION_ID;

/**
 * Created by mubarik on 06/11/2017.
 */

public class BookingViewHolder extends RecyclerView.ViewHolder {

    public TextView textID_BOOKING, textID_Group, textID_PROMOTOR, textID_SUPPORTER, textID_RESP_ESC, textTanggalBuat;
    public TextView textviewField1,textviewField2,textviewField3,textviewField4,textviewField5,textviewField6,textviewField7;
    public TextView textviewField8,textviewField9,textviewField10,textviewField11,textviewField12,textviewField13,textviewField14;
    public TextView textviewField15,textviewField16;
    Context context;

    public BookingViewHolder(final View itemView) {
        super(itemView);
        context = itemView.getContext();
        textID_BOOKING = (TextView) itemView.findViewById(R.id.textID_BOOKING);
        textID_Group = (TextView) itemView.findViewById(R.id.textID_Group);
        textID_PROMOTOR = (TextView) itemView.findViewById(R.id.textID_PROMOTOR);
        textID_SUPPORTER = (TextView) itemView.findViewById(R.id.textID_SUPPORTER);
        textID_RESP_ESC = (TextView) itemView.findViewById(R.id.textID_RESP_ESC);
        textTanggalBuat = (TextView) itemView.findViewById(R.id.textTanggalBuat);

        textviewField1 = (TextView) itemView.findViewById(R.id.textViewField1);
        textviewField2 = (TextView) itemView.findViewById(R.id.textViewField2);
        textviewField3 = (TextView) itemView.findViewById(R.id.textViewField3);
        textviewField4 = (TextView) itemView.findViewById(R.id.textViewField4);
        textviewField5 = (TextView) itemView.findViewById(R.id.textViewField5);

        textviewField6 = (TextView) itemView.findViewById(R.id.textViewField6);
        textviewField7 = (TextView) itemView.findViewById(R.id.textViewField7);
        textviewField8 = (TextView) itemView.findViewById(R.id.textViewField8);
        textviewField9 = (TextView) itemView.findViewById(R.id.textViewField9);
        textviewField10 = (TextView) itemView.findViewById(R.id.textViewField10);

        textviewField11 = (TextView) itemView.findViewById(R.id.textViewField11);
        textviewField12 = (TextView) itemView.findViewById(R.id.textViewField12);
        textviewField13 = (TextView) itemView.findViewById(R.id.textViewField13);
        textviewField14 = (TextView) itemView.findViewById(R.id.textViewField14);
        textviewField15 = (TextView) itemView.findViewById(R.id.textViewField15);
        textviewField16 = (TextView) itemView.findViewById(R.id.textViewField16);
        /*
        ImageView btnChangeDate = (ImageView) itemView.findViewById(R.id.btnChangeDate);
        btnChangeDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ID_NUM_RESER = textID_BOOKING.getText().toString();
                STATUS_RESERV = textviewField3.getText().toString();
                NINO = textviewField7.getText().toString();
                ADULTO = textviewField8.getText().toString();
                if (STATUS_RESERV.equals("CANCEL")) {
                    SingleDialodWithOutVoid(
                            v.getContext(),
                            v.getContext().getString(R.string.message_dialog_warning),
                            v.getContext().getString(R.string.message_warning_change_date));
                }else{
                    v.getContext().startActivity(new Intent(v.getContext(), ChangeDateReservation.class));
                }
            }
        });
        ImageView btnCancelBooking = (ImageView) itemView.findViewById(R.id.btnCancelBooking);
        btnCancelBooking.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ID_NUM_RESER = textID_BOOKING.getText().toString();
                DialogCancelBooking();
            }
        });
        */

        ImageView btnModifBooking = (ImageView) itemView.findViewById(R.id.btnModifBooking);
        btnModifBooking.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ID_NUM_RESER = textID_BOOKING.getText().toString();
                getDataReservation();
                //itemView.getContext().startActivity(new Intent(itemView.getContext(), ModifReservation.class));
            }
        });
    }

    private void DialogCancelBooking(){
        AlertDialog.Builder sayWindows = new AlertDialog.Builder(context);
        View mView = LayoutInflater.from(context).inflate(R.layout.pop_two_button, null);
        final TextView txtJudul = (TextView) mView.findViewById(R.id.txtJudul);
        final TextView txtMessage = (TextView) mView.findViewById(R.id.txtMessage);
        final Button btnYes = (Button) mView.findViewById(R.id.btnYes);
        final Button btnNo = (Button) mView.findViewById(R.id.btnNo);
        txtJudul.setText(context.getString(R.string.message_dialog_confirmation));
        txtMessage.setText("Do you really want to cancel selected booking quota?");
        sayWindows.setView(mView);
        final AlertDialog mAlertDialog = sayWindows.create();

        btnYes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mAlertDialog.dismiss();
                cancelBooking();
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

    private void AssignParam(){
        clearAPIParams();
        clearAPIValueParam();
        APIParameters.add("ID_NUM_RESER");
        APIValueParams.add(ID_NUM_RESER);
    }

    private void cancelBooking(){
        AssignParam();
        MultiParamGetDataJSON SaveChangeDate = new MultiParamGetDataJSON();
        SaveChangeDate.init(APIValueParams, APIParameters, URL_EDIT_CANCEL_BOOKING, context, json_cancel, true);
    }

    private MultiParamGetDataJSON.JSONObjectResult json_cancel = new MultiParamGetDataJSON.JSONObjectResult() {
        @Override
        public void gotJSONObject(JSONObject jsonObject){
            try {
                JSONArray jsonArray = jsonObject.getJSONArray(HEAD_STATUS);
                boolean isSuccess = false;
                for (int i = 0; i < jsonArray.length(); i++) {
                    JSONObject objData = jsonArray.getJSONObject(i);
                    isSuccess = objData.getString("STATUS").equals("1");
                }
                if (isSuccess){
                    SingleDialodWithOutVoid(context, context.getString(R.string.message_dialog_Information), context.getString(R.string.message_success_edit_data));
                }else{
                    SingleDialodWithOutVoid(context, context.getString(R.string.message_dialog_warning), context.getString(R.string.message_failed_edit_data));
                }
            }catch (JSONException e) {
                e.printStackTrace();
            }
        }
    };


    private void getDataReservation(){
        AssignParam();
        MultiParamGetDataJSON getDataID = new MultiParamGetDataJSON();
        getDataID.init(APIValueParams, APIParameters, URL_GET_DATA_RESERVATION_ID, context, json_reservation, true);
    }

    private MultiParamGetDataJSON.JSONObjectResult json_reservation = new MultiParamGetDataJSON.JSONObjectResult() {
        @Override
        public void gotJSONObject(JSONObject jsonObject){
            try {
                JSONArray jsonArray = jsonObject.getJSONArray(HEAD_GET_DATA_RESERVATION);
                setDefaultVar();
                for (int i = 0; i < jsonArray.length(); i++) {
                    JSONObject objData = jsonArray.getJSONObject(i);
                    ID_NUM_ESC_RESERVATION = objData.getString("ID_NUM_ESC_RESERVATION");
                    STR_ID_NUM_ESC = objData.getString("STR_ID_NUM_ESC");
                    ID_RESP_ESC = objData.getString("ID_RESP_ESC");
                    STR_ID_RESP_ESC = objData.getString("STR_ID_RESP_ESC");
                    ID_NUM_AGE = objData.getString("ID_NUM_AGE");
                    STR_ID_RESP_AGE = objData.getString("STR_ID_RESP_AGE");
                    ID_RESP_AGE = objData.getString("ID_RESP_AGE");
                    ID_PAQ = objData.getString("ID_PAQ");
                    STR_ID_PAQ = objData.getString("STR_ID_PAQ");
                    PriceTodd = objData.getString("PRICETODD");
                    PriceChild = objData.getString("PRICECHILD");
                    PriceAdult = objData.getString("PRICEADULT");
                    IDPACK_ADD = objData.getString("IDPACK_ADD");
                    STR_IDPACK_ADD = objData.getString("STR_IDPACK_ADD");
                    PriceAddTodd = objData.getString("PRICEADDTODD");
                    PriceAddChild = objData.getString("PRICEADDCHILD");
                    PriceAddAdult = objData.getString("PRICEADDADULT");
                    BEBE = objData.getString("BEBE");
                    INFANTE = objData.getString("INFANTE");
                    NINO = objData.getString("NINO");
                    NDISC = objData.getString("NDISC");
                    ADULTO = objData.getString("ADULTO");
                    ADISC = objData.getString("ADISC");
                    INSEN = objData.getString("INSEN");
                    SENIOR = objData.getString("SENIOR");
                    HANDICAP = objData.getString("HANDICAP");
                    PROM = objData.getString("PROM");
                    ID_LUNCH = objData.getString("ID_LUNCH");
                    CANT_LUNCH = objData.getString("CANT_LUNCH");
                    ID_LUNCH1 = objData.getString("ID_LUNCH1");
                    DOC_NO = objData.getString("DOC_NO");
                    ID_SOUV = objData.getString("ID_SOUV");
                    SETTLED = objData.getString("SETTLED");
                    ARR_TIME = objData.getString("ARR_TIME");
                    TOTAL_APAGAR = objData.getString("TOTAL_APAGAR");
                    STATUS_RESERV = objData.getString("STATUS_RESERV");
                    ADD_T5 = objData.getString("ADD_T5");
                    ADD_C5 = objData.getString("ADD_C5");
                    ADD_A5 = objData.getString("ADD_A5");
                    ADD_T7 = objData.getString("ADD_T7");
                    ADD_C7 = objData.getString("ADD_C7");
                    ADD_A7 = objData.getString("ADD_A7");
                    COMPLIMENT5 = objData.getString("COMPLIMENT5");
                    COMPLIMENT7 = objData.getString("COMPLIMENT7");
                    STATUS_RESERVATION = objData.getString("STATUS");
                    HORA_SALIDA = objData.getString("HORA_SALIDA");
                    GPO = objData.getString("GPO");
                    OTH_BUS = objData.getString("OTH_BUS");
                    PRIV_CAR = objData.getString("PRIV_CAR");
                    COMIDA = objData.getString("COMIDA");
                    SVR_ADICIONAL = objData.getString("SVR_ADICIONAL");
                    CANT_SVRADI = objData.getString("CANT_SVRADI");
                    PROMOTOR_CODE = objData.getString("PROMOTOR_CODE");
                    USUARIO_ALTA = objData.getString("USUARIO_ALTA");
                    TAX = objData.getString("TAX");
                    RSV_NO = objData.getString("RSV_NO");
                    CB_NAME = objData.getString("CB_NAME");
                    CB_BNAME = objData.getString("CB_BNAME");
                    CB_ACCNO = objData.getString("CB_ACCNO");
                    CB_HP = objData.getString("CB_HP");
                    CB_TODDLER = objData.getString("CB_TODDLER");
                    CB_CHILD = objData.getString("CB_CHILD");
                    CB_ADULT = objData.getString("CB_ADULT");
                    PC = objData.getString("PC");
                    GRPVOU = objData.getString("GRPVOU");
                    RSV_T5 = objData.getString("RSV_T5");
                    RSV_C5 = objData.getString("RSV_C5");
                    RSV_A5 = objData.getString("RSV_A5");
                    RSV_T7 = objData.getString("RSV_T7");
                    RSV_C7 = objData.getString("RSV_C7");
                    RSV_A7 = objData.getString("RSV_A7");
                    RSV_OTH = objData.getString("RSV_OTH");
                    RSV_SEN = objData.getString("RSV_SEN");
                    RSV_HAN = objData.getString("RSV_HAN");
                    IDPACK_ADD = objData.getString("IDPACK_ADD");
                    NOTE = objData.getString("NOTE");
                }
                isModifReservation = false;
                context.startActivity(new Intent(itemView.getContext(), ModifReservation.class));
            }catch (JSONException e) {
                e.printStackTrace();
            }
        }
    };



}
