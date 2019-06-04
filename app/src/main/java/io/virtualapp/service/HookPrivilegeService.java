package io.virtualapp.service;

import android.app.Service;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.os.Messenger;
import android.os.RemoteException;

import pers.turing.technician.fasthook.HookMethodManager;

public class HookPrivilegeService extends Service {

    private Messenger serviceMessenger = new Messenger(new ServiceHandler());

    @Override
    public IBinder onBind(Intent intent) {
        return serviceMessenger.getBinder();
    }

    private class ServiceHandler extends Handler {

        HookMethodManager manager;

        public ServiceHandler(){
            super();
            manager = HookMethodManager.Instance(getApplicationContext());
        }

        @Override
        public void handleMessage(Message msg) {
            Bundle data = msg.getData();
            String appName = data.getString("app_name");
            boolean[] check = manager.get_hook_method_service(appName);
            Messenger clientMessenger = msg.replyTo;
            if(clientMessenger != null) {
                Message msgToClient = Message.obtain();
                Bundle sent = new Bundle();
                sent.putBoolean("pri_0", check[0]);
                sent.putBoolean("pri_1", check[1]);
                sent.putBoolean("pri_2", check[2]);
                sent.putBoolean("pri_3", check[3]);
                sent.putBoolean("pri_4", check[4]);
                msgToClient.setData(sent);
                try {
                    clientMessenger.send(msgToClient);
                }catch(RemoteException e){
                    e.printStackTrace();
                }
            }
        }
    }
}
