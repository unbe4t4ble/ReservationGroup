package kidzania.reservationgroup.ViewHolder;

import android.content.Intent;
import android.os.Handler;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import kidzania.reservationgroup.R;

import static kidzania.reservationgroup.Misc.VarGlobal.GET_TEXT_SEARCH;

/**
 * Created by mubarik on 13/11/2017.
 */

public class SearchViewHolder extends RecyclerView.ViewHolder {

    //public ImageView btnSelect;
    public TextView txtSearchID, txtSeacrhText;
    public CardView myCardviewSearch;

    public static final String ID_TEXT_SEARCH = "ID_TEXT_SEARCH";
    public static final String TEXT_SEARCH = "TEXT_SEARCH";

    public SearchViewHolder(View itemView) {
        super(itemView);
        myCardviewSearch = (CardView) itemView.findViewById(R.id.myCardviewSearch);
        final ImageView btnSelect = (ImageView) itemView.findViewById(R.id.btnSelect);
        txtSearchID = (TextView) itemView.findViewById(R.id.txtSearchID);
        txtSeacrhText = (TextView) itemView.findViewById(R.id.txtSeacrhText);
        btnSelect.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(final View v, MotionEvent event) {
                btnSelect.setImageResource(R.drawable.check_valid);
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        Intent intent = new Intent(GET_TEXT_SEARCH);
                        intent.putExtra(ID_TEXT_SEARCH, txtSearchID.getText().toString());
                        intent.putExtra(TEXT_SEARCH, txtSeacrhText.getText().toString());
                        v.getContext().sendBroadcast(intent);
                    }
                }, 200);
                return false;
            }
        });
    }
}
