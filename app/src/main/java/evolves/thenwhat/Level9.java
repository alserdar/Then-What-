package evolves.thenwhat;

import android.animation.ObjectAnimator;
import android.animation.PropertyValuesHolder;
import android.annotation.TargetApi;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.preference.PreferenceManager;
import android.provider.Settings;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import evolves.thenwhat.toast_pack.OurToast;

public class Level9 extends AppCompatActivity {


    Handler m_handler;//= new Handler();
    Runnable m_handlerTask ;
    int _count=1;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_level_nine);

        Button noConnection = (Button)findViewById(R.id.noInternet);
        final TextView counter = (TextView) findViewById(R.id.counterAirPlaneMode);

        noConnection.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                if (isAirplaneModeOn(Level9.this))
                {
                    new OurToast().myToast(getBaseContext() , "Good");
                    try {
                        m_handler = new Handler();
                        m_handlerTask = new Runnable()
                        {
                            @Override
                            public void run() {
                                if(_count<=10 && isAirplaneModeOn(Level9.this))
                                {

                                    counter.setText(""+_count);
                                    _count++;

                                    if (_count > 10)
                                    {
                                        LevelUpDialog();
                                    }
                                }else
                                {
                                    m_handler.removeCallbacks(m_handlerTask);
                                }
                                m_handler.postDelayed(m_handlerTask, 1000);
                            }
                        };
                        m_handlerTask.run();


                    } catch (IllegalStateException e) {

                        e.printStackTrace();
                    }


                }else
                {
                    new OurToast().myToast(getBaseContext() , "Yalla ?");
                }
            }
        });
    }

    @SuppressWarnings("deprecation")
    @TargetApi(Build.VERSION_CODES.JELLY_BEAN_MR1)
    public static boolean isAirplaneModeOn(Context context) {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.JELLY_BEAN_MR1) {
            return Settings.System.getInt(context.getContentResolver(),
                    Settings.System.AIRPLANE_MODE_ON, 0) != 0;
        } else {
            return Settings.Global.getInt(context.getContentResolver(),
                    Settings.Global.AIRPLANE_MODE_ON, 0) != 0;
        }
    }

    private void LevelUpDialog() {


        AlertDialog.Builder builder = new AlertDialog.Builder(Level9.this , R.style.levelUpDialog);
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
                editor.putString("Level9", "Level9");
                editor.apply();
                Intent i = new Intent(Level9.this , Level_10.class);
                startActivity(i);
                finish();
            }
        });
    }

    private void wrongWay() {


        AlertDialog.Builder builder = new AlertDialog.Builder(Level9.this , R.style.cannotComplete);
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
