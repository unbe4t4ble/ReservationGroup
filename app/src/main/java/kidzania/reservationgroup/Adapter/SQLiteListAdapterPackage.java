package kidzania.reservationgroup.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

import kidzania.reservationgroup.R;


public class SQLiteListAdapterPackage extends BaseAdapter {

    Context context;
    ArrayList<String> NAME_PACKAGE;
    ArrayList<String> QUANTITY;
    ArrayList<String> PRICE;
    ArrayList<String> TOTAL ;

    private int[] colors = new int[] { 0x30a2def7,0x30ffffff  };

    public SQLiteListAdapterPackage(
    		Context context2,
    		ArrayList<String> NAME_PACKAGE,
    		ArrayList<String> QUANTITY,
    		ArrayList<String> PRICE,
    		ArrayList<String> TOTAL
    		) 
    {
        	
    	this.context = context2;
        this.NAME_PACKAGE = NAME_PACKAGE;
        this.QUANTITY = QUANTITY;
        this.PRICE = PRICE;
        this.TOTAL = TOTAL ;
    }

    public int getCount() {
        // TODO Auto-generated method stub
        return NAME_PACKAGE.size();
    }

    public Object getItem(int position) {
        // TODO Auto-generated method stub
        return null;
    }

    public long getItemId(int position) {
        // TODO Auto-generated method stub
        return 0;
    }

    public View getView(int position, View child, ViewGroup parent) {
    	
        Holder holder;
        
        LayoutInflater layoutInflater;
        
        if (child == null) {
            layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            child = layoutInflater.inflate(R.layout.listview_package, null);
            
            holder = new Holder();

            int ColorPosition = position % colors.length;
            child.setBackgroundColor(colors[ColorPosition]);
            
            holder.textviewField1 = (TextView) child.findViewById(R.id.textViewField1);
            holder.textviewField2 = (TextView) child.findViewById(R.id.textViewField2);
            holder.textviewField3 = (TextView) child.findViewById(R.id.textViewField3);
            holder.textviewField4 = (TextView) child.findViewById(R.id.textViewField4);
            
            child.setTag(holder);
            
        } else {
        	
        	holder = (Holder) child.getTag();
        }
        holder.textviewField1.setText(NAME_PACKAGE.get(position));
        holder.textviewField2.setText(QUANTITY.get(position));
        holder.textviewField3.setText(PRICE.get(position));
        holder.textviewField4.setText(TOTAL.get(position));

        return child;
    }

    public class Holder {
        TextView textviewField1;
        TextView textviewField2;
        TextView textviewField3;
        TextView textviewField4;
    }

}