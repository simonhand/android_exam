package cn.zhangle.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import androidx.appcompat.app.AppCompatActivity;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import cn.zhangle.myapplication.domain.News;

public class ShowActivity extends AppCompatActivity {
    private ListView mListView;
    protected void onCreate(Bundle savedInstanceState) {
        if (savedInstanceState!=null){
            String string = "Bundle{";
            for (String key : savedInstanceState.keySet()) {
                string += " " + key + " => " + savedInstanceState.get(key) + ";";
            }
            string += " }Bundle";
            System.out.println("hahah" + string);}
        else {
            System.out.println("为空了");
        }
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show);
        int id = 1;
        Intent intent = getIntent();
        mListView = (ListView) findViewById(R.id.infomation);
        Bundle args = intent.getBundleExtra("args");
        ArrayList<News> people = (ArrayList<News>) args.getSerializable("good");
        System.out.println("哈哈"+people.toString());
        List<HashMap<String, Object>> data = new ArrayList<HashMap<String,Object>>();
        for (News item : people) {
            HashMap<String, Object> items = new HashMap<String, Object>();
            items.put("id",item.getId());
            items.put("title", item.getTitle());
            items.put("date", item.getDate());
            items.put("from", item.getFrom());
            data.add(items);
        }
        SimpleAdapter adapter = new SimpleAdapter(this, data, R.layout.information_items,new String[]{"id","title","date", "from"}, new int[]{R.id.tv_order,R.id.tv_name,R.id.tv_category,R.id.tv_number,});
        mListView.setAdapter(adapter);
    }
}
