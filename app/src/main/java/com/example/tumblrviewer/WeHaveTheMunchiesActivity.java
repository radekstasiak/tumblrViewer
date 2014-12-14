package com.example.tumblrviewer;

import android.app.Activity;
import android.os.Bundle;
import android.view.Window;
import android.widget.AbsListView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.androidquery.AQuery;
import com.androidquery.callback.AjaxCallback;
import com.androidquery.callback.AjaxStatus;
import com.example.tumblrviewer.model.HomeResponse;

import org.json.JSONException;
import org.json.JSONObject;

public class WeHaveTheMunchiesActivity extends Activity {

    private AQuery mAQ;
    private TextView mResultTextView;
    private ListView mListView;
    private MenuArrayAdapter mItemArrayAdapter;
    private int postOffset;
    private int postCount;
    

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_list_viewer);
        mListView = (ListView) findViewById(R.id.items_lv);
        mAQ = new AQuery(this);
        mItemArrayAdapter = new MenuArrayAdapter(this, R.layout.item_on_list);
        mListView.setAdapter(mItemArrayAdapter);
        postOffset=0;
        postCount=0;
        //loadImages(postOffset);



        mListView.setOnScrollListener(new AbsListView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(AbsListView absListView, int i) {

            }

            @Override
            public void onScroll(AbsListView absListView, int firstVisibleItem, int visibleItemCount, int totalItemCount) {

                int lastItem = firstVisibleItem + visibleItemCount;
                if(lastItem == totalItemCount ){

                    if(postOffset <= postCount) {
                        loadImages(postOffset);
                        postOffset=postOffset+20;

                    }
                }
            }
        });

    }


    private void loadImages(int offset) {

        String url = Constants.TUMBLR_API_BLOG_URL + Constants.TUMBLR_API_BLOG_HOSTNAME +
                Constants.TUMBLR_API_CONTENT_TYPE + Constants.TUMBLR_API_KEY_NAME +
                Constants.TUMBLR_API_KEY + "&type=photo&offset=" + Integer.toString(offset);


        AjaxCallback<JSONObject> cb = new AjaxCallback<JSONObject>();
        cb.url(url).type(JSONObject.class).weakHandler(this, "jsonCallback");
        mAQ.ajax(cb);


    }

    public void jsonCallback(String url, JSONObject json, AjaxStatus status) throws JSONException {

        if (json != null) {
            HomeResponse homeResponse = HomeResponse.fromJsonObject(json.getJSONObject("response"));

            if (postCount == 0 ){
                postCount = json.getJSONObject("response").getInt("total_posts");
            }

            mItemArrayAdapter.setPosts(homeResponse.items);
        }

    }


}
