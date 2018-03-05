package kidzania.reservationgroup;

import kidzania.reservationgroup.API.MultiParamGetDataJSON;

import static kidzania.reservationgroup.Misc.FuncGlobal.OpenLostConnection;
import static kidzania.reservationgroup.Misc.FuncGlobal.hasConnection;
import static kidzania.reservationgroup.Misc.VarGlobal.APIParameters;
import static kidzania.reservationgroup.Misc.VarGlobal.APIValueParams;
import static kidzania.reservationgroup.Misc.VarGlobal.HEAD_GROUP_AVAIL;
import static kidzania.reservationgroup.Misc.VarGlobal.SENDER_CLASS;
import static kidzania.reservationgroup.Misc.VarUrl.URL_GROUP_AVAIL;

/**
 * Created by mubarik on 13/11/2017.
 */

public class GroupAvailGrp extends GroupData{

    @Override
    public void initialization(){
        SENDER_CLASS = "GroupAvailGrp";
        super.initialization();
    }

    @Override
    public void refreshData(){
        SENDER_CLASS = "GroupAvailGrp";
        super.refreshData();
    }

    @Override
    public void getGroup(){
        super.getGroup();
        HEADER_GROUP = HEAD_GROUP_AVAIL;
        if(hasConnection(GroupAvailGrp.this)) {
            MultiParamGetDataJSON getDataRejected = new MultiParamGetDataJSON();
            getDataRejected.init(APIValueParams, APIParameters, URL_GROUP_AVAIL, GroupAvailGrp.this, json_group, false);
        }else{
            OpenLostConnection(this);
        }
    }

}
