package cn.lunkr.example.android.testmultiprocessreacte;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.Binder;
import android.os.Build;
import android.os.IBinder;
import android.os.Parcel;
import android.os.RemoteException;
import android.support.annotation.Nullable;
import android.util.Log;

/**
 * Created by xwpeng on 16-7-14.
 */
public class PushService2 extends Service {
    private NotificationManager mNm;
    private final static int NOTIFY_ID = 1;
    private MyBinder mBinder = new MyBinder();
    private final static String TAG = PushService2.class.getSimpleName(), DESCRIPTOR = "PushService2";

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        Log.d(TAG, "onBind");
        return mBinder;
    }

    @Override
    public void onCreate() {
        Log.d(TAG, "onCreate");
        super.onCreate();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.d(TAG, "onStartCommand");
        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public void onDestroy() {
        Log.d(TAG, "onDestroy");
        super.onDestroy();
    }

    public final void startNotification() {
        Log.d(TAG, "startNotification");
        Notification.Builder builder = new Notification.Builder(this);
        PendingIntent contentIntent = PendingIntent.getActivity(this, 0,
                new Intent(this, MainActivity.class), 0);
        builder.setContentIntent(contentIntent);
        builder.setSmallIcon(R.mipmap.ic_launcher);
        builder.setTicker("Foreground Service Start");
        builder.setContentTitle("Foreground Service");
        builder.setContentText("Make this service run in the foreground.");
        Notification notification;
        if (Build.VERSION.SDK_INT >= 16) {
            notification = builder.build();
        } else {
            notification = builder.getNotification();
        }
        mNm = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        mNm.notify(NOTIFY_ID, notification);//first parameter not < 0
    }

    public final void stopNotification() {
        Log.d(TAG, "stopNotification");
        mNm.cancel(NOTIFY_ID);
    }

    class MyBinder extends Binder {
        @Override
        protected boolean onTransact(int code, Parcel data, Parcel reply, int flags) throws RemoteException {
            switch (code) {
                case 1://startNotify
                    data.enforceInterface(DESCRIPTOR);
                    startNotification();
                    reply.writeNoException();
                    return true;
                case 2://stopNotify
                    data.enforceInterface(DESCRIPTOR);
                    stopNotification();
                    reply.writeNoException();
                    return true;
            }
            return super.onTransact(code, data, reply, flags);
        }
    }

}
