package com.example.tumblrviewer;

import android.app.Activity;
import android.os.AsyncTask;
import android.os.Bundle;
import android.widget.TextView;

import com.androidquery.AQuery;
import com.androidquery.callback.AjaxCallback;
import com.androidquery.callback.AjaxStatus;
import com.example.tumblrviewer.model.HomeResponse;
import com.example.tumblrviewer.utils.LoadMoreListView;

import org.json.JSONException;
import org.json.JSONObject;

public class WeHaveTheMunchiesActivity extends Activity {

    private AQuery mAQ;
    private TextView mResultTextView;
    private TextView mTitleTextView;
    private LoadMoreListView mListView;
    private MenuArrayAdapter mItemArrayAdapter;
    private int postOffset;
    private int postCount;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //requestWindowFeature(Window.FEATURE_NO_TITLE);
        mTitleTextView = (TextView) findViewById(R.id.title_tv);

        setContentView(R.layout.activity_list_viewer);
        mListView = (LoadMoreListView) findViewById(R.id.items_lv);
        mAQ = new AQuery(this);
        mItemArrayAdapter = new MenuArrayAdapter(this, R.layout.item_on_list);
        mListView.setAdapter(mItemArrayAdapter);
        postOffset = 0;
        postCount = 0;
        loadImages(postOffset);
        mListView.setOnLoadMoreListener(new LoadMoreListView.OnLoadMoreListener() {

            public void onLoadMore() {
                new LoadDataTask().execute();
            }

        });





       /* mListView.setOnScrollListener(new AbsListView.OnScrollListener() {
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
        });*/

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

            if (postCount == 0) {
                postCount = json.getJSONObject("response").getInt("total_posts");
            }

            mItemArrayAdapter.setPosts(homeResponse.items);
            postOffset = postOffset + 20;
        }

    }


    private class LoadDataTask extends AsyncTask<Void, Void, Void> {

        @Override
        protected Void doInBackground(Void... params) {

            if (isCancelled()) {
                return null;
            }

            // Simulates a background task
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
            }

            //for (int i = 0; i < mNames.length; i++)
            //    mListItems.add(mNames[i]);
            loadImages(postOffset);

            return null;
        }

        @Override
        protected void onPostExecute(Void result) {
            // mItemArrayAdapter.add("Added after load more");

            // We need notify the adapter that the data have been changed
            mItemArrayAdapter.notifyDataSetChanged();

            // Call onLoadMoreComplete when the LoadMore task, has finished
            mListView.onLoadMoreComplete();

            super.onPostExecute(result);
        }
    }
}
