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
import android.widget.TextView;

import evolves.thenwhat.toast_pack.OurToast;

public class Level6 extends AppCompatActivity implements SensorEventListener {

    private SensorManager sensorMan;
    private Sensor accelerometer;

    private float[] mGravity;
    private float mAccel;
    private float mAccelCurrent;
    private float mAccelLast;

    Handler m_handler;//= new Handler();
    Runnable m_handlerTask ;
    int step = 0 ;


    Button walkingMan ;
    TextView missionSix , counterWalk ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_level_six);

        // In onCreate method
        sensorMan = (SensorManager)getSystemService(SENSOR_SERVICE);
        accelerometer = sensorMan.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);

        missionSix = (TextView)findViewById(R.id.missionSix);
        counterWalk = (TextView)findViewById(R.id.counterWalkingMan);
        walkingMan = (Button) findViewById(R.id.walkingMan);



        final LinearLayout layUp = (LinearLayout)findViewById(R.id.layUp);
        final LinearLayout layDown = (LinearLayout)findViewById(R.id.layDown);


        AnimatorSet animatorSet = new AnimatorSet();
        animatorSet.playTogether(
                ObjectAnimator.ofFloat(layDown,"translationY",-1000,0),
                ObjectAnimator.ofFloat(layDown,"alpha",0,1),
                ObjectAnimator.ofFloat(layUp,"translationX",-200,0)
        );
        animatorSet.setInterpolator(new AccelerateInterpolator());
        animatorSet.setDuration(2000);
        animatorSet.addListener(new AnimatorListenerAdapter(){
            @Override public void onAnimationEnd(Animator animation) {

                AnimatorSet animatorSet2 = new AnimatorSet();
                animatorSet2.playTogether(
                        ObjectAnimator.ofFloat(layDown,"scaleX", 1f, 0.5f, 1f),
                        ObjectAnimator.ofFloat(layDown,"scaleY", 1f, 0.5f, 1f)
                );
                animatorSet2.setInterpolator(new AccelerateInterpolator());
                animatorSet2.setDuration(1000);
                animatorSet2.start();

            }
        });
        animatorSet.start();

        walkingMan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                mAccel = 0.00f;
                mAccelCurrent = SensorManager.GRAVITY_EARTH;
                mAccelLast = SensorManager.GRAVITY_EARTH;

                sensorMan.registerListener(Level6.this, accelerometer,
                        SensorManager.SENSOR_DELAY_UI);
                counterWalk.setText(String.valueOf(0));

                try {
                    m_handler = new Handler();
                    m_handlerTask = new Runnable()
                    {
                        @Override
                        public void run() {
                            if(mAccel > 1)
                            {
                                counterWalk.setText(""+step);
                                step++;
                                if (step==20)
                                {
                                sensorMan.unregisterListener(Level6.this , accelerometer);
                                new OurToast().myToast(getBaseContext() , "Your pet is Really Happy ");
                                LevelUpDialog();
                                }
                            }
                            else
                            {
                                m_handler.removeCallbacks(m_handlerTask);
                            }
                            m_handler.postDelayed(m_handlerTask, 2000);
                        }
                    };
                    m_handlerTask.run();


                } catch (IllegalStateException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            }
        });
    }

    @Override
    public void onSensorChanged(SensorEvent event) {

        if (event.sensor.getType() == Sensor.TYPE_ACCELEROMETER){
            mGravity = event.values.clone();
            // Shake detection
            float x = mGravity[0];
            float y = mGravity[1];
            float z = mGravity[2];
            mAccelLast = mAccelCurrent;
            mAccelCurrent = (float)Math.sqrt(x*x + y*y + z*z);
            float delta = mAccelCurrent - mAccelLast;
            mAccel = mAccel * 0.9f + delta;
            // Make this higher or lower according to how much
            // motion you want to detect
        }

    }


    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {
        // required method
    }
    private void LevelUpDialog() {


        AlertDialog.Builder builder = new AlertDialog.Builder(Level6.this , R.style.levelUpDialog);
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
                editor.putString("Level6", "Level6");
                editor.apply();
                Intent i = new Intent(Level6.this , Level7.class);
                startActivity(i);
                finish();
            }
        });
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
                            sensorMan.unregisterListener(Level6.this);
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


/*
  Location locationA = new Location("point A");

        locationA.setLatitude(0);
        locationA.setLongitude(0);

        Location locationB = new Location("point B");

        locationB.setLatitude(17);
        locationB.setLongitude(17);

        float distance = locationA.distanceTo(locationB);

        if (distance > 17)
        {

        }


 */