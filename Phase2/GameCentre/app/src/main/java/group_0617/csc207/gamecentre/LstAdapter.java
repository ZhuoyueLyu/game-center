package group_0617.csc207.gamecentre;

import java.util.ArrayList;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;
/*
Table code design adapted from
http://www.worldbestlearningcenter.com/tips/Android-ListView-Header.htm
Zhuoyue Lyu Nov 4 2018
*/
public class LstAdapter extends ArrayAdapter<String> {
    int groupid;
    String[] item_list;
    ArrayList<String> desc;
    Context context;

    public LstAdapter(Context context, int vg, int id, String[] item_list){
        super(context,vg, id, item_list);
        this.context=context;
        groupid=vg;
        this.item_list=item_list;

    }
    // Hold views of the ListView to improve its scrolling performance
    static class ViewHolder {
        public TextView txtUsername;
        public TextView txtEasy;
        public TextView txtMedium;
        public TextView txtHard;


    }

    public View getView(int position, View convertView, ViewGroup parent) {

        View rowView = convertView;
        // Inflate the rowlayout.xml file if convertView is null
        if(rowView==null){
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            rowView= inflater.inflate(groupid, parent, false);
            ViewHolder viewHolder = new ViewHolder();
            viewHolder.txtUsername= (TextView) rowView.findViewById(R.id.txtUsername);
            viewHolder.txtEasy= (TextView) rowView.findViewById(R.id.txtEasy);
            viewHolder.txtMedium= (TextView) rowView.findViewById(R.id.txtMedium);
            viewHolder.txtHard= (TextView) rowView.findViewById(R.id.txtHard);
            rowView.setTag(viewHolder);

        }
        // Set text to each TextView of ListView item
        String[] items=item_list[position].split("__");
        ViewHolder holder = (ViewHolder) rowView.getTag();
        holder.txtUsername.setText(items[0]);
        holder.txtEasy.setText(items[1]);
        holder.txtMedium.setText(items[2]);
        holder.txtHard.setText(items[3]);
        return rowView;
    }

}
