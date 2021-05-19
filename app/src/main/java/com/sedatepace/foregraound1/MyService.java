package com.sedatepace.foregraound1;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Build;
import android.os.IBinder;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.RequiresApi;
import androidx.core.app.NotificationCompat;

public class MyService extends Service {
    BackgroundTask task;

    int value = 0;

    public MyService() {
    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        throw new UnsupportedOperationException("Not yet implemented");
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {

        task = new BackgroundTask();
        task.execute();

        initializeNotification();

        return START_NOT_STICKY;
    }

    private void initializeNotification() {
        NotificationCompat.Builder builder = new NotificationCompat.Builder(this, "1");
        builder.setSmallIcon(R.mipmap.ic_launcher);
        NotificationCompat.BigTextStyle style = new NotificationCompat.BigTextStyle();
        style.bigText("설정을 보려면 누르세요");
        builder.setContentText(null);
        builder.setContentInfo(null);
        builder.setOngoing(true);
        builder.setStyle(style);
        builder.setWhen(0);
        builder.setShowWhen(false);

        Intent notificationonIntent = new Intent(this, MainActivity.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(this,0,notificationonIntent,0);
        builder.setContentIntent(pendingIntent);
        NotificationManager manager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
            manager.createNotificationChannel(new NotificationChannel("1", "포그라운드 서비스", NotificationManager.IMPORTANCE_NONE));

        }
        Notification notification = builder.build();
        startForeground(1, notification);
    }

    private void noti(int value) {
        String ch = Integer.toString(value);
        NotificationCompat.Builder builder = new NotificationCompat.Builder(this,ch );
        builder.setSmallIcon(R.mipmap.ic_launcher);
        NotificationCompat.BigTextStyle style = new NotificationCompat.BigTextStyle();
        style.setBigContentTitle("제목"+ch);
        style.setSummaryText("서비스 동작중"+ch);
        builder.setContentText(null);
        builder.setContentInfo(null);
        builder.setOngoing(false);
        builder.setStyle(style);
        builder.setWhen(0);
        builder.setShowWhen(false);



        Intent notificationonIntent = new Intent(this, MainActivity.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(this,0,notificationonIntent,0);
        builder.setContentIntent(pendingIntent);
        NotificationManager manager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);


        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
            manager.createNotificationChannel(new NotificationChannel(ch, "포그라운드 서비스", NotificationManager.IMPORTANCE_HIGH));

        }
        manager.notify(value, builder.build());
    }

    class BackgroundTask extends AsyncTask<Integer, String, Integer> {
        String result ="";

        @RequiresApi(api = Build.VERSION_CODES.CUPCAKE)
        @Override
        protected Integer doInBackground(Integer... integers) {

            while(isCancelled() == false){
                try{
                    Log.e("앱로그", value+"번째 실행중");
//                    Toast.makeText(getApplicationContext(),value + "번째 실행중", Toast.LENGTH_SHORT).show();
                    if(value>0){
                        noti(value);
                    }

                    Thread.sleep(10000);
                    value++;
                }catch (InterruptedException ex){

                }
            }
            return value;
        }

        @Override
        protected void onPostExecute(Integer integer) {
          Log.e("앱로그","onPostExecute()");
          value = 0;
        }

        @Override
        protected void onProgressUpdate(String... values) {
            Log.e("앱로그", "onProgressUpdate()업데이트");
        }

        @Override
        protected void onCancelled() {
            value = 0;
        }


    }

    @Override
    public void onDestroy() {
        Toast.makeText(getApplicationContext(),"onDestory()",Toast.LENGTH_SHORT).show();
        super.onDestroy();
        Log.d("MyService", "onDestory");
        task.cancel(true);


    }
}