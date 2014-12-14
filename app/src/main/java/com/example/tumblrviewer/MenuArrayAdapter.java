package com.example.tumblrviewer;

import android.graphics.Point;
import android.view.Display;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.androidquery.AQuery;

import com.androidquery.util.Constants;
import com.example.tumblrviewer.model.Item;


/**
 * Created by Radek on 07/12/14.
 */
public class MenuArrayAdapter extends ArrayAdapter<Item> {

    private final LayoutInflater mInflater;
    private final int mResourceId;
    private AQuery mAQ;
    private Point mDisplaySize;
    public MenuArrayAdapter(Context context, int resource) {
        super(context, resource);

        mInflater = LayoutInflater.from(context);
        mResourceId = resource;
        mAQ = new AQuery(context);
        getDisplaySize(context);

    }

    public void getDisplaySize(Context context){

        WindowManager wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        Display display = wm.getDefaultDisplay();
        Point displaySize=new Point();
        display.getSize(displaySize);
        mDisplaySize=displaySize;

    }
    public void setPosts(Item[] posts) {

        //clear();
        for (Item item : posts) {

            add(item);
        }

        if (isEmpty()) {
            notifyDataSetInvalidated();
        } else {
            notifyDataSetChanged();
        }
        System.out.println("");
    }

    public void populateTags(LinearLayout ll, Item item){

        int maxWidth = mDisplaySize.x - 30;
        ll.removeAllViews();
        LinearLayout llLine=new LinearLayout(getContext());
        llLine.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        llLine.setOrientation(LinearLayout.HORIZONTAL);
        TextView header=new TextView(getContext());
        header.setText("Tags: ");
        llLine.addView(header);
        header.measure(0,0);

        int widthSoFar=header.getMeasuredWidth()+20;
        for (String tag: item.tags){
            TextView tagName=new TextView(getContext());
            tagName.setText(tag);
            tagName.setPadding(0,0,10,0);
            tagName.getEllipsize();
            //tagName.setTag(tag);
            tagName.setSingleLine();
            tagName.measure(0,0);
            widthSoFar +=tagName.getMeasuredWidth();
            if(widthSoFar >= maxWidth){

                ll.addView(llLine);

                llLine=new LinearLayout(getContext());
                llLine.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
                llLine.setOrientation(LinearLayout.HORIZONTAL);
                llLine.addView(tagName);
                llLine.setVisibility(View.VISIBLE);
                widthSoFar=tagName.getMeasuredWidth()+20;
            }
            else{

                llLine.addView(tagName);

            }

        }
        ll.addView(llLine);



    }
    @Override
    public View getView(int i, View convertView, ViewGroup viewGroup) {
    ;
        ViewHolder viewHolder = null;
        if (convertView == null) {

            viewHolder = new ViewHolder();
            convertView = mInflater.inflate(mResourceId, viewGroup, false);
            viewHolder.mImageView = (ImageView) convertView.findViewById(R.id.tumblr_photo_iv);
            viewHolder.mTagsLayout=(LinearLayout) convertView.findViewById(R.id.tags_layout);
            convertView.setTag(viewHolder);
        } else {

            viewHolder = (ViewHolder) convertView.getTag();
        }

        Item item = getItem(i);
        if (item.tags.length >0 ){
         /* for (String tag: item.tags){
               TextView tagName=new TextView(getContext());
               tagName.setText(tag);
               tagName.setPadding(10, 0, 0, 0);
               tagName.setVisibility(View.VISIBLE);
               tagName.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.MATCH_PARENT));
               viewHolder.mTagsLayout.addView(tagName);
           }
*/
            populateTags(viewHolder.mTagsLayout, item);
        }




        mAQ.id(viewHolder.mImageView).image(item.photos[0].photoUrl.uri, true, true, 600, 0, null, Constants.FADE_IN);
        return convertView;


    }


    private class ViewHolder {
        ImageView mImageView;
        LinearLayout mTagsLayout;

    }
}






