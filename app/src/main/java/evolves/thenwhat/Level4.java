package evolves.thenwhat;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.animation.PropertyValuesHolder;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.preference.PreferenceManager;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.animation.AccelerateInterpolator;
import android.widget.Button;
import android.widget.LinearLayout;

import evolves.thenwhat.toast_pack.OurToast;

public class Level4 extends AppCompatActivity implements SensorEventListener {

    private SensorManager sensorMan;
    private Sensor accelerometer;

    private float[] mGravity;
    private float mAccel;
    private float mAccelCurrent;
    private float mAccelLast;
    float resultIs ;


    Handler m_handler;//= new Handler();
    Runnable m_handlerTask ;
    int _count=1;


    Button ambuButton ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_level_four);

        // In onCreate method
        sensorMan = (SensorManager) getSystemService(SENSOR_SERVICE);
        accelerometer = sensorMan.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);

        final LinearLayout layUp = (LinearLayout) findViewById(R.id.layUp);
        final LinearLayout layDown = (LinearLayout) findViewById(R.id.layDown);
        ambuButton = (Button) findViewById(R.id.abmuLevelFour);



        AnimatorSet animatorSet = new AnimatorSet();
        animatorSet.playTogether(
                ObjectAnimator.ofFloat(layDown, "translationY", -1000, 0),
                ObjectAnimator.ofFloat(layDown, "alpha", 0, 1),
                ObjectAnimator.ofFloat(layUp, "translationX", -200, 0)
        );
        animatorSet.setInterpolator(new AccelerateInterpolator());
        animatorSet.setDuration(2000);
        animatorSet.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {

                AnimatorSet animatorSet2 = new AnimatorSet();
                animatorSet2.playTogether(
                        ObjectAnimator.ofFloat(layDown, "scaleX", 1f, 0.5f, 1f),
                        ObjectAnimator.ofFloat(layDown, "scaleY", 1f, 0.5f, 1f)
                );
                animatorSet2.setInterpolator(new AccelerateInterpolator());
                animatorSet2.setDuration(1000);
                animatorSet2.start();

            }
        });
        animatorSet.start();


        ambuButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                mAccel = 0.00f;
                mAccelCurrent = SensorManager.GRAVITY_EARTH;
                mAccelLast = SensorManager.GRAVITY_EARTH;

                new OurToast().myToast(getBaseContext(), "the ambulance had 40 seconds to arrive .");
                sensorMan.registerListener(Level4.this, accelerometer,
                        SensorManager.SENSOR_DELAY_UI);

                final ShakeEventListener shake = new ShakeEventListener();
                shake.setOnShakeListener(new ShakeEventListener.OnShakeListener() {
                    @Override
                    public void onShake() {

                        sensorMan.unregisterListener(Level4.this , accelerometer);
                        new OurToast().myToast(getBaseContext(), "You killed the Man !");
                        wrongWay();
                    }
                });

                Handler handler = new Handler();
                Runnable run = new Runnable() {

                    @Override
                    public void run() {

                        if (resultIs < 1)
                        {
                            sensorMan.unregisterListener(Level4.this , accelerometer);
                            new OurToast().myToast(getBaseContext(), "You saved the Man ");
                            LevelUpDialog();
                        }

                    }
                };
                handler.postDelayed(run, 40000);
            }
        });
    }
    @Override
    public void onSensorChanged(SensorEvent event) {

        if (event.sensor.getType() == Sensor.TYPE_ACCELEROMETER) {
            mGravity = event.values.clone();
            // Shake detection
            float x = mGravity[0];
            float y = mGravity[1];
            float z = mGravity[2];
            mAccelLast = mAccelCurrent;
            mAccelCurrent = (float) Math.sqrt(x * x + y * y + z * z);
            float delta = mAccelCurrent - mAccelLast;
            mAccel = mAccel * 0.9f + delta;
            // Make this higher or lower according to how much
            // motion you want to detect

            resultIs = mAccel ;
        }

    }


    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {
        // required method
    }

    private void LevelUpDialog() {


        AlertDialog.Builder builder = new AlertDialog.Builder(Level4.this , R.style.levelUpDialog);
        builder.setCancelable(true);
        AlertDialog alert = builder.create();
        final View decorView = alert
                .getWindow()
                .getDecorView();
        ObjectAnimator scaleDown = ObjectAnimator.ofPropertyValuesHolder(decorView,
                PropertyValuesHolder.ofFloat("scaleX", 0.0f, 1.0f),
                PropertyValuesHolder.ofFloat("scaleY", 0.0f, 1.0f),
                PropertyValuesHolder.ofFloat("alpha", 0.0f, 1.0f));
        scaleDown.setDuration(1000);
        scaleDown.start();
        alert.show();
        alert.setOnDismissListener(new DialogInterface.OnDismissListener() {
            @Override
            public void onDismiss(DialogInterface dialogInterface) {

                SharedPreferences saved = PreferenceManager.getDefaultSharedPreferences(getBaseContext());
                final SharedPreferences.Editor editor = saved.edit();
                editor.putString("Level4", "Level4");
                editor.apply();
                Intent i = new Intent(Level4.this , Level5.class);
                startActivity(i);
                finish();
            }
        });
    }

    private void wrongWay() {


        AlertDialog.Builder builder = new AlertDialog.Builder(Level4.this , R.style.cannotComplete);
        builder.setCancelable(true);
        AlertDialog alert = builder.create();
        final View decorView = alert
                .getWindow()
                .getDecorView();
        ObjectAnimator scaleDown = ObjectAnimator.ofPropertyValuesHolder(decorView,
                PropertyValuesHolder.ofFloat("scaleX", 0.0f, 1.0f),
                PropertyValuesHolder.ofFloat("scaleY", 0.0f, 1.0f),
                PropertyValuesHolder.ofFloat("alpha", 0.0f, 1.0f));
        scaleDown.setDuration(1000);
        scaleDown.start();
        alert.show();
    }


    @Override
    public void onBackPressed() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this , R.style.DialogAnimation);
        builder.setTitle("Exit !");
        builder.setMessage("Are you sure ?");
        builder.setCancelable(true);
        builder.setIcon(R.mipmap.tw_icon);
        builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });
        builder.setPositiveButton("Exit", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                            finishAffinity();
                        }else
                        {
                            sensorMan.unregisterListener(Level4.this);
                            Intent i = new Intent(getBaseContext() , FinishActivity.class);
                            i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                            startActivity(i);
                        }
                    }
                }).start();
            }
        });
        AlertDialog alert = builder.create();

        final View decorView = alert
                .getWindow()
                .getDecorView();
        ObjectAnimator scaleDown = ObjectAnimator.ofPropertyValuesHolder(decorView,
                PropertyValuesHolder.ofFloat("scaleX", 0.0f, 1.0f),
                PropertyValuesHolder.ofFloat("scaleY", 0.0f, 1.0f),
                PropertyValuesHolder.ofFloat("alpha", 0.0f, 1.0f));
        scaleDown.setDuration(1000);
        scaleDown.start();
        alert.show();

    }
}
