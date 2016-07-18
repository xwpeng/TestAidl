package cn.lunkr.example.android.testmultiprocessreacte;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.os.RemoteException;
import android.view.View;
import android.widget.Button;

/**
 * Created by xwpeng on 16-7-14.
 */
public class MainActivity2 extends Activity implements View.OnClickListener {
    private Button startService;

    private Button stopService;

    private Button bindService;

    private Button unbindService;

    private Button startNotify;

    private Button stopNotify;

    private IBinder myBinder;

    private ServiceConnection connection = new ServiceConnection() {

        @Override
        public void onServiceDisconnected(ComponentName name) {
            myBinder = null;
        }

        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            myBinder = service;
        }

    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        startService = (Button) findViewById(R.id.start_service);
        stopService = (Button) findViewById(R.id.stop_service);
        bindService = (Button) findViewById(R.id.bind_service);
        unbindService = (Button) findViewById(R.id.unbind_service);
        startNotify = (Button) findViewById(R.id.start_notify);
        stopNotify = (Button) findViewById(R.id.stop_notify);
        startService.setOnClickListener(this);
        stopService.setOnClickListener(this);
        bindService.setOnClickListener(this);
        unbindService.setOnClickListener(this);
        startNotify.setOnClickListener(this);
        stopNotify.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.start_service:
                Intent startIntent = new Intent(this, PushService2.class);
                startService(startIntent);
                break;
            case R.id.stop_service:
                Intent stopIntent = new Intent(this, PushService2.class);
                stopService(stopIntent);
                break;
            case R.id.bind_service:
                Intent bindIntent = new Intent(this, PushService2.class);
                bindService(bindIntent, connection, BIND_AUTO_CREATE);
                break;
            case R.id.unbind_service:
                unbindService(connection);
                break;
            case R.id.start_notify:
                if (myBinder != null) {
                    bindMethod(1);
                }
                break;
            case R.id.stop_notify:
                if (myBinder != null) {
                    bindMethod(2);
                }
                break;
            default:
                break;
        }
    }

    public void bindMethod(int id) {
        if (myBinder == null) {
            return;
        }
        android.os.Parcel _data = android.os.Parcel.obtain();
        android.os.Parcel _reply = android.os.Parcel.obtain();
        try {
            _data.writeInterfaceToken("PushService2");
            myBinder.transact(id, _data, _reply, 0);
        } catch (RemoteException e) {
            e.printStackTrace();
        } finally {
            _data.recycle();
            _reply.recycle();
        }
    }
}
