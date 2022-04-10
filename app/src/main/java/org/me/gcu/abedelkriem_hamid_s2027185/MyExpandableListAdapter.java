package org.me.gcu.abedelkriem_hamid_s2027185;

import android.content.Context;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class MyExpandableListAdapter extends BaseExpandableListAdapter {
    private Context mContext;
    private List<Item> list;
    private HashMap<String, List<Item>> mHashChild;

    public MyExpandableListAdapter(Context cx, List<Item> list) { //}, HashMap<String, List<Item>> songData){
        this.mContext = cx;
        this.list = list;
        //this.mHashChild = songData;
    }

    @Override
    public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {
        if(convertView == null){
            LayoutInflater inflater = (LayoutInflater) this.mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.list_group, parent, false);
        }

        Item item = list.get(groupPosition);

        TextView listTitle = (TextView) convertView.findViewById(R.id.listTitle);
        listTitle.setTypeface(null, Typeface.BOLD);
        listTitle.setText( item.getTitle() );

        return convertView;
    }

    @Override
    public int getGroupCount() {
        return list.size();
    }

    @Override
    public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {
        if(convertView == null){
            LayoutInflater inflater = (LayoutInflater) this.mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.item_layout, parent, false);
        }
        final Item item = (Item) getChild(groupPosition, childPosition);

        TextView itemTitle = (TextView) convertView.findViewById(R.id.itemTitle);
        TextView itemDescription = (TextView) convertView.findViewById(R.id.itemDescription);
        TextView itemPubDate = (TextView) convertView.findViewById(R.id.itemPubDate);

        itemTitle.setPadding(10, 0, 10, 0);
        itemDescription.setPadding(20, 20, 20, 20);
        itemPubDate.setPadding(20, 0, 20, 0);

        itemTitle.setText( "Title:" + item.getTitle() );
        itemDescription.setText("Description:\n" + item.getDescription());
        itemPubDate.setText( "Pub Date: " + item.getPubDate() );

        itemTitle.setTextSize(22);
        itemDescription.setTextSize(20);
        itemPubDate.setTextSize(20);

        return convertView;
    }
    @Override
    public int getChildrenCount(int groupPosition) {
        //return this.mHashChild.get(mAlbumList.get(groupPosition).albumTitle).size();
        return 1;//todo
    }

    @Override
    public Object getGroup(int groupPosition) {
        return this.list.get(groupPosition).getTitle();
    }

    @Override
    public Object getChild(int groupPosition, int childPosition) {
        return this.list.get( childPosition);//mHashChild.get(this.mAlbumList.get(groupPosition).albumTitle).get(childPosition);
    }

    @Override
    public long getGroupId(int groupPosition) {
        return groupPosition;
    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return childPosition;
    }

    @Override
    public boolean hasStableIds() {
        return true;
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return true;
    }
}
