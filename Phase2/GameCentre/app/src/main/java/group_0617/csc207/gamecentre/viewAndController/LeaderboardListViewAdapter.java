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
Table code design adapted from
http://www.worldbestlearningcenter.com/tips/Android-ListView-Header.htm
Zhuoyue Lyu Nov 4 2018

This is an adapter which is responsible for adapting the game data to the leaderboardE
 Excluded from tests because it's a model lass.
*/

public class LeaderboardListViewAdapter extends ArrayAdapter<String> {
    /**
     * id of this list view
     */
    private int groupId;
    /**
     * The String[] that contains Game data
     */
    private String[] item_list;
    /**
     * The context
     */
    private Context context;

    public LeaderboardListViewAdapter(Context context,int vg,int id,String[] item_list) {
        super(context,vg,id,item_list);
        this.context = context;
        groupId = vg;
        this.item_list = item_list;

    }

    // Hold views of the ListView to improve its scrolling performance
    static class ViewHolder {
        TextView txtRank;
        public TextView txtUsername;
        TextView txtSocre;


    }

    @NonNull
    public View getView(int position,View convertView,@NonNull ViewGroup parent) {

        View rowView = convertView;
        // Inflate the rowlayout.xml file if convertView is null
        if (rowView == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            assert inflater != null;
            rowView = inflater.inflate(groupId,parent,false);
            ViewHolder viewHolder = new ViewHolder();

            viewHolder.txtRank = (TextView) rowView.findViewById(R.id.txtLeaderRank);
            viewHolder.txtUsername = (TextView) rowView.findViewById(R.id.txtLeaderUsername);
            viewHolder.txtSocre = (TextView) rowView.findViewById(R.id.txtLeaderScore);
            rowView.setTag(viewHolder);

        }
        // Set text to each TextView of ListView item
        String[] items = item_list[position].split("__");
        ViewHolder holder = (ViewHolder) rowView.getTag();

        holder.txtRank.setText(items[0]);
        holder.txtUsername.setText(items[1]);
        holder.txtSocre.setText(items[2]);
        return rowView;
    }

}
