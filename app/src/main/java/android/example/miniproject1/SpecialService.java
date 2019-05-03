package android.example.miniproject1;

import android.Manifest;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Build;
import android.os.IBinder;
import android.support.v4.app.ActivityCompat;
import android.widget.Toast;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.NotificationManagerCompat;
import android.support.annotation.RequiresApi;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;

import java.math.BigInteger;
import static java.lang.Math.*;
import static java.math.BigInteger.valueOf;

import java.util.Random;

public class SpecialService extends Service implements SensorEventListener {
    public SpecialService() {
    }

    private SensorManager mSensorManager;
    private Sensor mCompass;

    float compass;

    Random rand = new Random();

    private Context mContext;
    private boolean isGpsEnabled = false;
    private boolean isNetworkEnabled = false;
    private boolean canGetLocation = false;
    private Location mLocation;
    private double mLatitude;
    private double mLongitude;
    private static final long MIN_DISTANCE_CHANGE_FOR_UPDATE = 10;
    private static final long MIN_TIME_FOR_UPDATE = 60000;
    private LocationManager mLocationManager;

    private NotificationCompat.Builder notification_builder; // This will be used to build your notification
    private NotificationManagerCompat notification_manager;

    @Override
    public void onCreate() {
        Toast.makeText(this, "Your special service has stated", Toast.LENGTH_LONG).show();

        NotificationManager notification_manager = (NotificationManager) this
                .getSystemService(this.NOTIFICATION_SERVICE);

        mSensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        //mCompass = mSensorManager.getDefaultSensor(Sensor.TYPE_ORIENTATION);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {//This portion of the code handles newer APIs
            String chanel_id = "3000";
            CharSequence name = "Channel Name";
            String description = "Chanel Description";
            int importance = NotificationManager.IMPORTANCE_LOW;
            NotificationChannel mChannel = new NotificationChannel(chanel_id, name, importance);
            mChannel.setDescription(description);
            mChannel.enableLights(true);
            mChannel.setLightColor(Color.BLUE);
            notification_manager.createNotificationChannel(mChannel);
            notification_builder = new NotificationCompat.Builder(this, chanel_id);
        } else {
            notification_builder = new NotificationCompat.Builder(this); //this code handles older APIs
        }

        //getLocation();
        runTread();
        //getRandNumber();
    }

    public void onAccuracyChanged(Sensor sensor, int accuracy) {
    }

    public void onSensorChanged(SensorEvent event) {
        compass = Math.round(event.values[0]);
        getRandNumber();
    }

    public void runTread() {
        try {
            Thread.sleep(10000);

        } catch (Exception e) {
            e.printStackTrace();
        }
        getRandNumber();
    }

    public Location getLocation() {
        try {
            mLocationManager = (LocationManager) mContext.getSystemService(LOCATION_SERVICE);
            isGpsEnabled = mLocationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);
            isNetworkEnabled = mLocationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER);

            if (!isGpsEnabled && !isNetworkEnabled) {
                Toast.makeText(this, "Location Services didn't work.", Toast.LENGTH_LONG).show();
                /*no location provider enabled*/
            } else {
                this.canGetLocation = true;
                if (isNetworkEnabled) {

                    if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                        return null;
                    }
                    mLocationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, MIN_TIME_FOR_UPDATE, MIN_DISTANCE_CHANGE_FOR_UPDATE, (LocationListener) this);

                    if (mLocationManager != null) {

                        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                            return null;
                        }
                        mLocation = mLocationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);

                        if (mLocation != null) {

                            mLatitude = mLocation.getLatitude();
                            mLongitude = mLocation.getLongitude();
                        }
                    }
                    /*if gps is enabled then get location using gps*/
                    if (isGpsEnabled) {

                        if (mLocation == null) {

                            mLocationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, MIN_TIME_FOR_UPDATE, MIN_DISTANCE_CHANGE_FOR_UPDATE, (LocationListener) this);

                            if (mLocationManager != null) {

                                mLocation = mLocationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);

                                if (mLocation != null) {

                                    mLatitude = mLocation.getLatitude();
                                    mLongitude = mLocation.getLongitude();
                                }
                            }
                        }
                    }
                }
            }
        } catch (Exception e) {

            e.printStackTrace();
        }

        return mLocation;
    }

    public void getRandNumber()
    {
        final int randNum = rand.nextInt(10000);
        BigInteger bigInt = valueOf(randNum);
        int isPrime = 1;
        String message = "";

        if(bigInt.isProbablePrime(isPrime))
        {
            message = randNum + "\nYour special number is prime!";
        }
        else
        {
            message = randNum + "\nYour special number isn't prime...";
        }

        //Toast.makeText(this, "Special service will initiate now", Toast.LENGTH_LONG).show();
        notification_builder.setSmallIcon(R.drawable.baseline_rowing_black_18dp)
                .setContentTitle("Your special number has arrived!")
                .setContentText(message)
                .setAutoCancel(true);

        Notification notification = notification_builder.build();
        NotificationManager manager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        manager.notify(1, notification);
    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        throw new UnsupportedOperationException("Not yet implemented");
    }
}
