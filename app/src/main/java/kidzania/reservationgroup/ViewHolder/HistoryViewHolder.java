package kidzania.reservationgroup.ViewHolder;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import kidzania.reservationgroup.R;

public class HistoryViewHolder extends RecyclerView.ViewHolder {

    public TextView textviewField1,textviewField2,textviewField3;
    private Context context;

    public HistoryViewHolder(View itemView) {
        super(itemView);
        context = itemView.getContext();
        textviewField1 = (TextView) itemView.findViewById(R.id.textViewField1);
        textviewField2 = (TextView) itemView.findViewById(R.id.textViewField2);
        textviewField3 = (TextView) itemView.findViewById(R.id.textViewField3);
    }
}
