package com.xing.android;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;

//import com.xing.android.demo.CalendarHomeDemoActivity;
import com.xing.android.common.ui.CommonBaseAdapter;
import com.xing.android.demo.CalendarHomeDemoActivity;
import com.xing.android.demo.activity.AnimationHomeActivity;

import java.util.ArrayList;
import java.util.List;

public class HomeActivity extends Activity {

    private final String LOG_TAG = this.getClass().getSimpleName();

    private ListView mTopicListView;
    private TopicAdapter mTopicAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        mTopicListView = (ListView) findViewById(R.id.lv_topic);

        mTopicAdapter = new TopicAdapter(this, null);
        mTopicListView.setAdapter(mTopicAdapter);

        initData();

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_home, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private void initData() {
        List<Topic> list = new ArrayList<Topic>();
        list.add(new Topic("动画", "包括补间动画,帧动画,属性动画,Activity跳转动画,5.0之后的动画", AnimationHomeActivity.class));
        list.add(new Topic("日历", "包括单选,多选,连选,多个连选, 混合模式", CalendarHomeDemoActivity.class));
        mTopicAdapter.setData(list);
    }


    public static class Topic {
        public String title;
        public String info;
        public Class activity;

        public Topic(String title, String info, Class activity) {
            this.title = title;
            this.info = info;
            this.activity = activity;
        }
    }

    public static class TopicAdapter extends CommonBaseAdapter<Topic> {

        public TopicAdapter(Context context, List<Topic> list) {
            super(context, list);
        }

        @Override
        protected View newView(int position, ViewGroup parent, SparseArray<View> sparseArray) {
            View view = LayoutInflater.from(mContext).inflate(R.layout.item_home_topic, null);
            if(sparseArray != null) {
                sparseArray.put(R.id.tv_topic_title, view.findViewById(R.id.tv_topic_title));
                sparseArray.put(R.id.tv_topic_info, view.findViewById(R.id.tv_topic_info));
            }
            return view;
        }

        @Override
        protected void bindView(int position, View convertView, ViewGroup parent, SparseArray<View> sparseArray) {
            TextView titleView = (TextView) sparseArray.get(R.id.tv_topic_title);
            TextView infoView = (TextView) sparseArray.get(R.id.tv_topic_info);
            final Topic topic = getItem(position);
            if(topic != null) {
                titleView.setText(topic.title);
                infoView.setText(topic.info);
            }
            convertView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(topic != null && topic.activity != null) {
                        Intent intent = new Intent(mContext, topic.activity);
                        mContext.startActivity(intent);
                    }
                }
            });
        }
    }
}
