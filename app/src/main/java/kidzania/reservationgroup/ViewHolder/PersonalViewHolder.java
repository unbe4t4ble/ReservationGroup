package kidzania.reservationgroup.ViewHolder;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import kidzania.reservationgroup.API.MultiParamGetDataJSON;
import kidzania.reservationgroup.AddGroup;
import kidzania.reservationgroup.R;

import static kidzania.reservationgroup.Misc.FuncGlobal.clearAPIParams;
import static kidzania.reservationgroup.Misc.FuncGlobal.clearAPIValueParam;
import static kidzania.reservationgroup.Misc.FuncGlobal.setDefaultGroup;
import static kidzania.reservationgroup.Misc.VarGlobal.ADDR;
import static kidzania.reservationgroup.Misc.VarGlobal.APIParameters;
import static kidzania.reservationgroup.Misc.VarGlobal.APIValueParams;
import static kidzania.reservationgroup.Misc.VarGlobal.AREA;
import static kidzania.reservationgroup.Misc.VarGlobal.CITY;
import static kidzania.reservationgroup.Misc.VarGlobal.DISTRICT;
import static kidzania.reservationgroup.Misc.VarGlobal.EMAIL;
import static kidzania.reservationgroup.Misc.VarGlobal.FAX;
import static kidzania.reservationgroup.Misc.VarGlobal.GRPNAME;
import static kidzania.reservationgroup.Misc.VarGlobal.HEAD_GET_DATA_PERSONAL_ID;
import static kidzania.reservationgroup.Misc.VarGlobal.IS_PERSONAL;
import static kidzania.reservationgroup.Misc.VarGlobal.NO_HP;
import static kidzania.reservationgroup.Misc.VarGlobal.PIC;
import static kidzania.reservationgroup.Misc.VarGlobal.PIC_ID;
import static kidzania.reservationgroup.Misc.VarGlobal.PROVINCE;
import static kidzania.reservationgroup.Misc.VarGlobal.ZIPCODE;
import static kidzania.reservationgroup.Misc.VarUrl.URL_GET_DATA_PERSONAL_ID;

public class PersonalViewHolder extends RecyclerView.ViewHolder {
    public TextView textviewField1,textviewField2,textviewField3,textviewField4,textviewField5,textviewField6,textviewField7;
    public TextView textviewField8,textviewField9,textviewField10,textviewField11,textViewID;
    private Context context;
    private String ID_PERSONAL;

    public PersonalViewHolder(View itemView) {
        super(itemView);
        context = itemView.getContext();
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
        textViewID = (TextView) itemView.findViewById(R.id.textViewID);
        Button btnSelectGroup = (Button) itemView.findViewById(R.id.btnSelectGroup);
        btnSelectGroup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ID_PERSONAL = textViewID.getText().toString();
                getDataPersonal();
            }
        });
    }

    private void getDataPersonal(){
        clearAPIParams();
        clearAPIValueParam();
        APIParameters.add("id");
        APIValueParams.add(ID_PERSONAL);
        MultiParamGetDataJSON getDataID = new MultiParamGetDataJSON();
        getDataID.init(APIValueParams, APIParameters, URL_GET_DATA_PERSONAL_ID, context, json_personal, true);
    }

    private MultiParamGetDataJSON.JSONObjectResult json_personal = new MultiParamGetDataJSON.JSONObjectResult() {
        @Override
        public void gotJSONObject(JSONObject jsonObject){
            try {
                JSONArray jsonArray = jsonObject.getJSONArray(HEAD_GET_DATA_PERSONAL_ID);
                for (int i = 0; i < jsonArray.length(); i++) {
                    JSONObject objData = jsonArray.getJSONObject(i);
                    setDefaultGroup();
                    PIC_ID = objData.getString("ID");
                    ADDR = objData.getString("ADDRESS");
                    PROVINCE = objData.getString("PROVINCE");
                    DISTRICT = objData.getString("DISTRICT");
                    AREA = objData.getString("AREA");
                    CITY = objData.getString("CITY");
                    ZIPCODE = objData.getString("ZIPCODE");
                    PIC = objData.getString("NAME");
                    NO_HP = objData.getString("PHONE");
                    FAX = objData.getString("FAX");
                    EMAIL = objData.getString("EMAIL");
                    GRPNAME = "";
                }
                IS_PERSONAL = true;
                context.startActivity(new Intent(itemView.getContext(), AddGroup.class));
            }catch (JSONException e) {
                e.printStackTrace();
            }
        }
    };
}
