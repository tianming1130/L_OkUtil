package com.example.administrator.l_okutil;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;

import com.example.administrator.l_okutil.adapter.MenuAdapter;
import com.example.administrator.l_okutil.bean.Menu;
import com.example.administrator.l_okutil.net.MyCallBack;
import com.example.administrator.l_okutil.net.OkUtil;

import java.util.List;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{
    private static final String TAG ="MainActivity" ;
    private Button btnGetData;
    private RecyclerView recyclerView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        btnGetData=(Button)findViewById(R.id.btn_get_data);
        recyclerView=(RecyclerView)findViewById(R.id.recycler_view);

        btnGetData.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        OkUtil.getInstance().getData(new MyCallBack<List<Menu>>(){

            @Override
            public void onResponse(List<Menu> data) {
                MenuAdapter adapter=new MenuAdapter(R.layout.item_view,data);
                recyclerView.setLayoutManager(new LinearLayoutManager(MainActivity.this));
                recyclerView.setAdapter(adapter);
            }
        });
    }
}
