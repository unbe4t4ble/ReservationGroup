package kidzania.reservationgroup.Misc;

import android.content.Context;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import kidzania.reservationgroup.API.MultiParamGetDataJSON;
import kidzania.reservationgroup.SQLite.DataSQLlite;

import static kidzania.reservationgroup.Misc.FuncGlobal.clearAPIParams;
import static kidzania.reservationgroup.Misc.FuncGlobal.clearAPIValueParam;
import static kidzania.reservationgroup.Misc.VarGlobal.APIParameters;
import static kidzania.reservationgroup.Misc.VarGlobal.APIValueParams;
import static kidzania.reservationgroup.Misc.VarGlobal.BusBefore;
import static kidzania.reservationgroup.Misc.VarGlobal.HEAD_GET_DATA_PACKAGE_RESERV;
import static kidzania.reservationgroup.Misc.VarGlobal.HEAD_STATUS;
import static kidzania.reservationgroup.Misc.VarGlobal.ID_NUM_RESER;
import static kidzania.reservationgroup.Misc.VarGlobal.LunchBefore;
import static kidzania.reservationgroup.Misc.VarGlobal.NOTIF_FINISH;
import static kidzania.reservationgroup.Misc.VarGlobal.SouvernirBefore;
import static kidzania.reservationgroup.Misc.VarGlobal.cursor;
import static kidzania.reservationgroup.Misc.VarUrl.URL_DELETE_PACKAGE_RESV;
import static kidzania.reservationgroup.Misc.VarUrl.URL_GET_DATA_PACKAGE_RESERV;
import static kidzania.reservationgroup.Misc.VarUrl.URL_SEND_PACKAGE_RESV;

public class GetPackageReserv {

    private Context context;
    private DataSQLlite dbHelper;
    private final String PARAM_TAG = "tag_pack";
    private final String PARAM_ID = "id_num_reser";

    private String[] Array_TAGPACK = new String[]{
            "RESERV_PLUNCH",
            "RESERV_PBUS",
            "RESERV_PSOUV",
            "RESERV_POTH"
    };

    private String[] ArrayDELETE_TAGPACK = new String[]{
            "GRP_DelLunch",
            "GRP_DelBus",
            "GRP_DelSouv",
            "GRP_DelOther"
    };

    private String[] ArrayINSERT_TAGPACK = new String[]{
            "GRP_InsLunch",
            "GRP_InsBus",
            "GRP_InsSouv",
            "GRP_InsOther"
    };

    private String[] ArraySELECT_TAGPACK = new String[]{
            "GRP_GetLunch",
            "GRP_GetBus",
            "GRP_GetSouv",
            "GRP_GetOther"
    };


    public GetPackageReserv (Context con){
        this.context = con;
        dbHelper = new DataSQLlite(con);
    }

    public void excuteGet(){
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        db.execSQL("DELETE FROM TRANSPACKAGE WHERE ID_NUM_RESER=" + ID_NUM_RESER);
        insertPackageLunch();
    }

    private void  SaveTRANSPACKAGE(String ID_NUM_RESER, String ID_PACKAGE, String PNAME, String PRICE, String QUANTITY, String TOTAL,String STATUS){
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        db.execSQL("INSERT INTO TRANSPACKAGE(ID_NUM_RESER, ID_PACKAGE, PNAME, PRICE, QUANTITY, TOTAL, STATUS) VALUES('" +
                ID_NUM_RESER +"','"+
                ID_PACKAGE +"','" +
                PNAME +"','"+
                PRICE +"','" +
                QUANTITY +"','"+
                TOTAL +"','" +
                STATUS + "')");
    }

    private void AssignParamAndValue(String VALUE_TAG){
        clearAPIParams();
        clearAPIValueParam();
        APIParameters.add(PARAM_TAG);
        APIParameters.add(PARAM_ID);
        APIValueParams.add(VALUE_TAG);
        APIValueParams.add(ID_NUM_RESER);
    }

    private void insertPackageLunch(){
        AssignParamAndValue(ArraySELECT_TAGPACK[0]);
        MultiParamGetDataJSON getDataLunch = new MultiParamGetDataJSON();
        getDataLunch.init(APIValueParams, APIParameters, URL_GET_DATA_PACKAGE_RESERV, context, json_lunch, true);
    }

    private MultiParamGetDataJSON.JSONObjectResult json_lunch = new MultiParamGetDataJSON.JSONObjectResult() {
        @Override
        public void gotJSONObject(JSONObject jsonObject) {
            try {
                JSONArray jsonArray = jsonObject.getJSONArray(HEAD_GET_DATA_PACKAGE_RESERV);
                for (int i = 0; i < jsonArray.length(); i++) {
                    JSONObject objData = jsonArray.getJSONObject(i);
                    SaveTRANSPACKAGE(
                            ID_NUM_RESER,
                            objData.getString("ID"),
                            objData.getString("PNAME"),
                            objData.getString("PRICE"),
                            objData.getString("QTY"),
                            String.valueOf(objData.getInt("PRICE")*objData.getInt("QTY")),
                            Array_TAGPACK[0]
                    );
                }
                insertPackageBus();
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    };

    private void insertPackageBus(){
        AssignParamAndValue(ArraySELECT_TAGPACK[1]);
        MultiParamGetDataJSON getDataBus = new MultiParamGetDataJSON();
        getDataBus.init(APIValueParams, APIParameters, URL_GET_DATA_PACKAGE_RESERV, context, json_bus, true);
    }

    private MultiParamGetDataJSON.JSONObjectResult json_bus = new MultiParamGetDataJSON.JSONObjectResult() {
        @Override
        public void gotJSONObject(JSONObject jsonObject) {
            try {
                JSONArray jsonArray = jsonObject.getJSONArray(HEAD_GET_DATA_PACKAGE_RESERV);
                for (int i = 0; i < jsonArray.length(); i++) {
                    JSONObject objData = jsonArray.getJSONObject(i);
                    SaveTRANSPACKAGE(
                            ID_NUM_RESER,
                            objData.getString("ID"),
                            objData.getString("PNAME"),
                            objData.getString("PRICE"),
                            objData.getString("QTY"),
                            String.valueOf(objData.getInt("PRICE")*objData.getInt("QTY")),
                            Array_TAGPACK[1]
                    );
                }
                insertPackageSouv();
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    };

    private void insertPackageSouv(){
        AssignParamAndValue(ArraySELECT_TAGPACK[2]);
        MultiParamGetDataJSON getDataSouv = new MultiParamGetDataJSON();
        getDataSouv.init(APIValueParams, APIParameters, URL_GET_DATA_PACKAGE_RESERV, context, json_souv, true);
    }

    private MultiParamGetDataJSON.JSONObjectResult json_souv = new MultiParamGetDataJSON.JSONObjectResult() {
        @Override
        public void gotJSONObject(JSONObject jsonObject) {
            try {
                JSONArray jsonArray = jsonObject.getJSONArray(HEAD_GET_DATA_PACKAGE_RESERV);
                for (int i = 0; i < jsonArray.length(); i++) {
                    JSONObject objData = jsonArray.getJSONObject(i);
                    SaveTRANSPACKAGE(
                            ID_NUM_RESER,
                            objData.getString("ID"),
                            objData.getString("PNAME"),
                            objData.getString("PRICE"),
                            objData.getString("QTY"),
                            String.valueOf(objData.getInt("PRICE")*objData.getInt("QTY")),
                            Array_TAGPACK[2]
                    );
                }
                insertPackageOther();
                getPackBeforeModif();
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    };

    private void insertPackageOther(){
        AssignParamAndValue(ArraySELECT_TAGPACK[3]);
        MultiParamGetDataJSON getDataOther = new MultiParamGetDataJSON();
        getDataOther.init(APIValueParams, APIParameters, URL_GET_DATA_PACKAGE_RESERV, context, json_other, true);
    }

    private MultiParamGetDataJSON.JSONObjectResult json_other = new MultiParamGetDataJSON.JSONObjectResult() {
        @Override
        public void gotJSONObject(JSONObject jsonObject) {
            try {
                JSONArray jsonArray = jsonObject.getJSONArray(HEAD_GET_DATA_PACKAGE_RESERV);
                for (int i = 0; i < jsonArray.length(); i++) {
                    JSONObject objData = jsonArray.getJSONObject(i);
                    SaveTRANSPACKAGE(
                            ID_NUM_RESER,
                            objData.getString("ID"),
                            objData.getString("PNAME"),
                            objData.getString("PRICE"),
                            objData.getString("QTY"),
                            String.valueOf(objData.getInt("PRICE")*objData.getInt("QTY")),
                            Array_TAGPACK[3]
                    );
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    };

    private void AssignDeleteParamAndValue(){
        clearAPIParams();
        clearAPIValueParam();
        for (String aArrayDELETE_TAGPACK : ArrayDELETE_TAGPACK) {
            APIParameters.add("tag_pack_delete[]");
            APIValueParams.add(aArrayDELETE_TAGPACK);
        }
        APIParameters.add("id_num_reser");
        APIValueParams.add(ID_NUM_RESER);
    }

    public void excuteDeleteAndInsert(){
        AssignDeleteParamAndValue();
        MultiParamGetDataJSON deleteData = new MultiParamGetDataJSON();
        deleteData.init(APIValueParams, APIParameters, URL_DELETE_PACKAGE_RESV, context, json_delete, true);
    }

    private MultiParamGetDataJSON.JSONObjectResult json_delete = new MultiParamGetDataJSON.JSONObjectResult() {
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
                    excutePackageLunch();
                }
            }catch (JSONException e) {
                e.printStackTrace();
            }
        }
    };

    private void AssignInsertParamAndValue(String TAG, String TAG_TABLE){
        clearAPIParams();
        clearAPIValueParam();
        APIParameters.add("tag_pack");
        APIValueParams.add(TAG);

        APIParameters.add("id_num_reser");
        APIValueParams.add(ID_NUM_RESER);

        SQLiteDatabase db = dbHelper.getReadableDatabase();
        String strSQL = "SELECT ID_PACKAGE, PRICE, QUANTITY FROM TRANSPACKAGE "+
                "WHERE ID_NUM_RESER="+ID_NUM_RESER+" AND STATUS = '"+TAG_TABLE+"'";
        cursor = db.rawQuery(strSQL,null);
        cursor.moveToFirst();
        if (cursor.getCount()>0) {
            do {
                APIParameters.add("id_pack[]");
                APIValueParams.add(cursor.getString(cursor.getColumnIndex("ID_PACKAGE")));
                APIParameters.add("qty_pack[]");
                APIValueParams.add(cursor.getString(cursor.getColumnIndex("QUANTITY")));
            } while (cursor.moveToNext());
        }
    }

    private void excutePackageLunch(){
        AssignInsertParamAndValue(ArrayINSERT_TAGPACK[0], Array_TAGPACK[0]);
        MultiParamGetDataJSON dataLunch = new MultiParamGetDataJSON();
        dataLunch.init(APIValueParams, APIParameters, URL_SEND_PACKAGE_RESV, context, json_insert_lunch, true);
    }

    private MultiParamGetDataJSON.JSONObjectResult json_insert_lunch = new MultiParamGetDataJSON.JSONObjectResult() {
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
                    excutePackageBus();
                }
            }catch (JSONException e) {
                e.printStackTrace();
            }
        }
    };

    private void excutePackageBus(){
        AssignInsertParamAndValue(ArrayINSERT_TAGPACK[1], Array_TAGPACK[1]);
        MultiParamGetDataJSON dataBus = new MultiParamGetDataJSON();
        dataBus.init(APIValueParams, APIParameters, URL_SEND_PACKAGE_RESV, context, json_insert_bus, true);
    }

    private MultiParamGetDataJSON.JSONObjectResult json_insert_bus = new MultiParamGetDataJSON.JSONObjectResult() {
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
                    excutePackageSouv();
                }
            }catch (JSONException e) {
                e.printStackTrace();
            }
        }
    };

    private void excutePackageSouv(){
        AssignInsertParamAndValue(ArrayINSERT_TAGPACK[2], Array_TAGPACK[2]);
        MultiParamGetDataJSON dataSouv = new MultiParamGetDataJSON();
        dataSouv.init(APIValueParams, APIParameters, URL_SEND_PACKAGE_RESV, context, json_insert_souv, true);
    }

    private MultiParamGetDataJSON.JSONObjectResult json_insert_souv = new MultiParamGetDataJSON.JSONObjectResult() {
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
                    excutePackageOther();
                }
            }catch (JSONException e) {
                e.printStackTrace();
            }
        }
    };

    private void excutePackageOther(){
        AssignInsertParamAndValue(ArrayINSERT_TAGPACK[3], Array_TAGPACK[3]);
        MultiParamGetDataJSON dataOther = new MultiParamGetDataJSON();
        dataOther.init(APIValueParams, APIParameters, URL_SEND_PACKAGE_RESV, context, json_insert_other, true);
    }

    private MultiParamGetDataJSON.JSONObjectResult json_insert_other = new MultiParamGetDataJSON.JSONObjectResult() {
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
                    Intent intent = new Intent(NOTIF_FINISH);
                    context.sendBroadcast(intent);
                }
            }catch (JSONException e) {
                e.printStackTrace();
            }
        }
    };

    private void getPackBeforeModif(){
        LunchBefore = GetDataPackBefore("RESERV_PLUNCH");
        SouvernirBefore = GetDataPackBefore("RESERV_PSOUV");
        BusBefore = GetDataPackBefore("RESERV_PBUS");
    }

    private String GetDataPackBefore(String status){
        String NamePackage = "";
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        cursor = db.rawQuery("SELECT PNAME FROM TRANSPACKAGE WHERE ID_NUM_RESER = "+ID_NUM_RESER+" AND STATUS='"+status+"'",null);
        cursor.moveToFirst();
        if (cursor.getCount()>0) {
            cursor.moveToPosition(0);
            NamePackage = cursor.getString(cursor.getColumnIndex("PNAME"));
        }
        return NamePackage;
    }



}
