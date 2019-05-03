package io.virtualapp.home;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

import io.virtualfasthook.R;

public class SettingsActivity extends AppCompatActivity {

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.settings);
        ListView settingList;
        settingList = (ListView)findViewById(R.id.settings_list);
        List<String> items = new ArrayList<>();
        items.add("设置1");
        items.add("设置2");
        items.add("设置3");
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_checked);
        adapter.addAll(items);
        settingList.setAdapter(adapter);
    }
}
