package io.virtualapp.home;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.Switch;

import com.lody.virtual.client.ipc.VPackageManager;

import io.virtualfasthook.R;

public class SettingsActivity extends AppCompatActivity {

    private static String TAG = "SettingsActivity";

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.settings);
        VPackageManager manager = VPackageManager.get();
        Intent intent = getIntent();
        String app = intent.getStringExtra("app_name");
        int checked = manager.getHookedPrivacy(app);
        Log.d(TAG, String.format("onCreate: checked is %x\n", checked));
        Switch canema = (Switch)findViewById(R.id.switch_camera);
        canema.setChecked((checked & 1) == 1);
        canema.setOnCheckedChangeListener((button, check)->{
            int cur = manager.getHookedPrivacy(app);
            if(check){
                manager.setHookedPrivacy(app, cur | 1);
            }else{
                manager.setHookedPrivacy(app, cur & 0xfe);
            }
        });
        Switch net = (Switch)findViewById(R.id.switch_net);
        net.setChecked((checked & 2) == 0x2);
        net.setOnCheckedChangeListener((button, check)->{
            int cur = manager.getHookedPrivacy(app);
            if(check){
                manager.setHookedPrivacy(app, cur | 2);
            }else{
                manager.setHookedPrivacy(app, cur & 0xfd);
            }
        });
        Switch sms = (Switch)findViewById(R.id.switch_sms);
        sms.setChecked((checked & 4) == 0x4);
        sms.setOnCheckedChangeListener((button, check)->{

            int cur = manager.getHookedPrivacy(app);
            if(check){
                manager.setHookedPrivacy(app, cur | 4);
            }else{
                manager.setHookedPrivacy(app, cur & 0xfb);
            }
        });
        Switch imei =(Switch)findViewById(R.id.switch_imei);
        imei.setChecked((checked & 8) == 0x8);
        imei.setOnCheckedChangeListener((button, check)->{

            int cur = manager.getHookedPrivacy(app);
            if(check){
                manager.setHookedPrivacy(app, cur | 0x8);
            }else{
                manager.setHookedPrivacy(app, cur & 0xf7);
            }
        });
        Switch task = (Switch)findViewById(R.id.switch_task);
        task.setChecked((checked & 16) == 0x10);
        task.setOnCheckedChangeListener((button, check)->{

            int cur = manager.getHookedPrivacy(app);
            if(check){
                manager.setHookedPrivacy(app, cur | 16);
            }else{
                manager.setHookedPrivacy(app, cur & 0xdf);
            }
        });
    }
}
