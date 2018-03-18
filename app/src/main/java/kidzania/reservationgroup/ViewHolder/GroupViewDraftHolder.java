package kidzania.reservationgroup.ViewHolder;

import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import kidzania.reservationgroup.R;

import static kidzania.reservationgroup.Misc.VarGlobal.GET_DETAIL_DATA_GROUP;
import static kidzania.reservationgroup.Misc.VarGlobal.GRPNAME;
import static kidzania.reservationgroup.Misc.VarGlobal.IDUSR_OWN;
import static kidzania.reservationgroup.Misc.VarGlobal.ID_NUM_ESC;
import static kidzania.reservationgroup.Misc.VarGlobal.POSITION_DATA;
import static kidzania.reservationgroup.Misc.VarGlobal.POSTING_GROUP;
import static kidzania.reservationgroup.Misc.VarGlobal.TAKE_PHOTO_GROUP;
import static kidzania.reservationgroup.Misc.VarGlobal.isAdmin;

public class GroupViewDraftHolder extends RecyclerView.ViewHolder {
    public TextView textviewField1,textviewField2,textviewField3,textviewField4,textviewField5,textviewField6,textviewField7;
    public TextView textviewField8,textviewField9,textviewField10,textviewField11,textviewField12,textviewField13,textviewField14;
    public TextView textviewField15,textviewField16, txtID_NUM_ESC, txtIDUSR_OWN;

    public GroupViewDraftHolder(View itemView) {
        super(itemView);
        txtID_NUM_ESC = (TextView) itemView.findViewById(R.id.txtID_NUM_ESC);
        txtIDUSR_OWN = (TextView) itemView.findViewById(R.id.txtIDUSR_OWN);
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
        Button btnViewImage = (Button) itemView.findViewById(R.id.btnViewImage);
        btnViewImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ID_NUM_ESC = txtID_NUM_ESC.getText().toString();
                Intent intent = new Intent(TAKE_PHOTO_GROUP);
                v.getContext().sendBroadcast(intent);
            }
        });
        Button btnEditData = (Button) itemView.findViewById(R.id.btnEditData);
        btnEditData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ID_NUM_ESC = txtID_NUM_ESC.getText().toString();
                POSITION_DATA = getPosition();
                Intent intent = new Intent(GET_DETAIL_DATA_GROUP);
                v.getContext().sendBroadcast(intent);
            }
        });

        ImageButton btnPosting = (ImageButton) itemView.findViewById(R.id.btnPosting);
        if(!isAdmin){
            btnPosting.setVisibility(View.GONE);
        }

        btnPosting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ID_NUM_ESC = txtID_NUM_ESC.getText().toString();
                IDUSR_OWN = txtIDUSR_OWN.getText().toString();
                GRPNAME = textviewField1.getText().toString();
                POSITION_DATA = getPosition();
                Intent intent = new Intent(POSTING_GROUP);
                v.getContext().sendBroadcast(intent);
                //Toast.makeText(v.getContext(), "test", Toast.LENGTH_SHORT).show();
            }
        });
    }

}
