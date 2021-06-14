package cn.zhangle.myapplication;


import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;



import java.io.Serializable;
import java.util.ArrayList;

import cn.zhangle.myapplication.domain.News;

public class MainActivity extends BaseActivity implements View.OnClickListener {
    MyHelper myHelper;
    private EditText mEtnumber;
    private EditText mEtname;
    private EditText mPrice;
    private EditText mEtphone;
    private TextView mTvShow;
    private Button mBtnAdd;
    private Button mBtnQuery;
    private Button mBtnUpdate;
    private Button mBtnDelete;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        myHelper =new MyHelper(this);
        init();
    }

    private void init() {
        mPrice = (EditText)findViewById(R.id.et_price);
        mEtnumber=(EditText) findViewById(R.id.et_number);
        mEtname=(EditText) findViewById(R.id.et_name);
        mEtphone=(EditText) findViewById(R.id.from);
        mTvShow =(TextView) findViewById(R.id.show);
        mBtnAdd =(Button) findViewById(R.id.add);
        mBtnQuery =(Button) findViewById(R.id.query);
        mBtnUpdate=(Button) findViewById(R.id.update);
        mBtnDelete =(Button) findViewById(R.id.delete);
        mBtnAdd.setOnClickListener(this);
        mBtnQuery.setOnClickListener(this);
        mBtnUpdate.setOnClickListener(this);
        mBtnDelete.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        String strings="";
        String title;
        String date;
        String from;
        String id;
        SQLiteDatabase db;
        ContentValues values;
        title = mEtname.getText().toString();
        date = mEtnumber.getText().toString();
        from = mEtphone.getText().toString();
        id = mPrice.getText().toString();
        switch (v.getId()) {
            case R.id.add:

                db = myHelper.getWritableDatabase();
                values = new ContentValues();
                values.put("title", title);
                values.put("date", date);
                values.put("address", from);
                db.insert("information", null, values);
                Toast.makeText(this, "添加成功", Toast.LENGTH_LONG).show();
                db.close();
                break;
            case R.id.query:
                Intent intent = new Intent(MainActivity.this, ShowActivity.class);
                Bundle args = new Bundle();
                ArrayList<News> Goods = new ArrayList<>();
                db = myHelper.getReadableDatabase();
//                mTvShow.setText("");
                Cursor cursor = db.query("information", null, null, null, null, null, null);

                if (cursor.getCount() == 0) {
                    mTvShow.setText(" ");
                    showToast("没有数据");
                } else {
                    while (cursor.moveToNext()) {
                        News good = new News();
                        good.setId(cursor.getInt(0));
                        good.setTitle(cursor.getString(1));
                        good.setDate(cursor.getString(2));
                        good.setFrom(cursor.getString(3));
                        if (good != null) {
                            Goods.add(good);
                        }
                    }
                }
                args.putSerializable("good", (Serializable) Goods);
                intent.putExtra("args",args);
                cursor.close();
                db.close();
                startActivity(intent);
                break;
            case R.id.update:
                db = myHelper.getWritableDatabase();
                values = new ContentValues();
                values.put("_id", id );
                values.put("title", title);
                values.put("date", date);
                values.put("address", from );
                db.update("information", values, "_id=?", new String[]{mPrice.getText().toString()});
                showToast("修改成功");
                db.close();
                break;
            case R.id.delete:
                db = myHelper.getWritableDatabase();
                db.delete("information", "_id=?", new String[]{mPrice.getText().toString()});
                showToast("删除成功");
                db.close();
                break;
        }

    }
    class MyHelper extends SQLiteOpenHelper{

        public MyHelper( Context context) {
            super(context, "news.db", null, 1);
        }
        @Override
        public void onCreate(SQLiteDatabase db) {
            db.execSQL("CREATE TABLE information(_id INTEGER PRIMARY KEY AUTOINCREMENT,title VARCHAR(10),date VARCHAR(10),address VERCHAR(20))");
        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        }
    }
}