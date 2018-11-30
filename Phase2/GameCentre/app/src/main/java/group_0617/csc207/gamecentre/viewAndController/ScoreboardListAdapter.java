package group_0617.csc207.gamecentre.viewAndController;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import group_0617.csc207.gamecentre.R;

/**
List adapter code design adapted from
http://www.worldbestlearningcenter.com/tips/Android-ListView-Header.htm
Zhuoyue Lyu Nov 4 2018

This is an adapter which is responsible for adapting the game data to the user scoreboard
 Excluded from tests because it's a view class.

*/
public class ScoreboardListAdapter extends ArrayAdapter<String> {
    /**
     * groupid of this list
     */
    private int groupid;
    /**
     * the string[] that contains game data
     */
    private String[] item_list;
    /**
     * context
     */
    private Context context;

    /**
     * view adapter
     */
    ScoreboardListAdapter(Context context,int vg,int id,String[] item_list){
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

    @NonNull
    public View getView(int position,View convertView,@NonNull ViewGroup parent) {

        View rowView = convertView;
        // Inflate the rowlayout.xml file if convertView is null
        if(rowView==null){
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            assert inflater != null;
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
