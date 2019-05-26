package io.virtualapp.home;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.Switch;

import io.virtualfasthook.R;
import pers.turing.technician.fasthook.HookMethodManager;

public class SettingsActivity extends AppCompatActivity {

    private HookMethodManager manager;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.settings);
        manager = HookMethodManager.Instance(null);
        Button res = (Button)findViewById(R.id.res);
        res.setOnClickListener((e)->{
            finish();
        });
        Intent intent = getIntent();
        String app = intent.getStringExtra("app_name");
        boolean[] checked = manager.get_hook_method(intent.getStringExtra("app_name"));
        Switch canema = (Switch)findViewById(R.id.switch_camera);
        canema.setChecked(checked[0]);
        canema.setOnCheckedChangeListener((button, check)->{
            if(check){
                manager.register_hook_method(app, 0);
            }else{
                manager.unregister_hook_method(app, 0);
            }
        });
        Switch net = (Switch)findViewById(R.id.switch_net);
        net.setChecked(checked[1]);
        net.setOnCheckedChangeListener((button, check)->{
            if(check){
                manager.register_hook_method(app, 1);
            }else{
                manager.unregister_hook_method(app, 1);
            }
        });
        Switch sms = (Switch)findViewById(R.id.switch_sms);
        sms.setChecked(checked[2]);
        sms.setOnCheckedChangeListener((button, check)->{
            if(check){
                manager.register_hook_method(app, 2);
            }else{
                manager.unregister_hook_method(app, 2);
            }
        });
        Switch imei =(Switch)findViewById(R.id.switch_imei);
        imei.setChecked(checked[3]);
        imei.setOnCheckedChangeListener((button, check)->{
            if(check){
                manager.register_hook_method(app, 3);
            }else{
                manager.unregister_hook_method(app, 3);
            }
        });
        Switch task = (Switch)findViewById(R.id.switch_task);
        task.setChecked(checked[4]);
        task.setOnCheckedChangeListener((button, check)->{
            if(check){
                manager.register_hook_method(app, 4);
            }else{
                manager.unregister_hook_method(app, 4);
            }
        });
    }

}
