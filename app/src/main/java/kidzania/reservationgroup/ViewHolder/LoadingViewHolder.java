package kidzania.reservationgroup.ViewHolder;

import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;

import kidzania.reservationgroup.R;

import static kidzania.reservationgroup.Misc.VarGlobal.GROUP_TRY_AGAIN;

/**
 * Created by mubarik on 08/11/2017.
 */

public class LoadingViewHolder extends RecyclerView.ViewHolder{

    public ProgressBar progressBar;
    public Button btnTryAgain;

    public LoadingViewHolder(View itemView) {
        super(itemView);

        progressBar = (ProgressBar) itemView.findViewById(R.id.loading);
        btnTryAgain = (Button) itemView.findViewById(R.id.btnTryAgain);
        btnTryAgain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(GROUP_TRY_AGAIN);
                v.getContext().sendBroadcast(intent);
            }
        });
    }
}
