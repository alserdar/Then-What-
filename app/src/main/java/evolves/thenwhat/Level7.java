package evolves.thenwhat;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.animation.PropertyValuesHolder;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.media.MediaPlayer;
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
import android.widget.ProgressBar;

import evolves.thenwhat.toast_pack.OurToast;

public class Level7 extends AppCompatActivity {

    Handler m_handler;
    Runnable m_handlerTask ;
    int _count=1;
    AlertDialog alert ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_level_seven);

        final Button listen = (Button)findViewById(R.id.listen);
        final Button pause = (Button)findViewById(R.id.pause);


        final LinearLayout layUp = (LinearLayout) findViewById(R.id.layUp);
        final LinearLayout layDown = (LinearLayout) findViewById(R.id.layDown);
        final ProgressBar bar = (ProgressBar)findViewById(R.id.listenbar);

        final MediaPlayer mPlayer = MediaPlayer.create(Level7.this, R.raw.dont_lose_your_mind);


        listen.setBackgroundResource(R.mipmap.play);
        pause.setBackgroundResource(R.mipmap.pause);

        pause.setEnabled(false);


        listen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                listen.setEnabled(false);
                pause.setEnabled(true);
                listen.setBackgroundResource(R.mipmap.c_play);
                pause.setBackgroundResource(R.mipmap.pause);

                mPlayer.start();

                m_handler = new Handler();
                m_handlerTask = new Runnable()
                {
                    @Override
                    public void run() {
                        if(_count<=390)
                        {
                            if (mPlayer.isPlaying())
                            {
                                bar.setProgress(_count);
                                bar.setMax(390);
                                _count++;
                            }else
                            {

                            }
                        }else
                        {

                            m_handler.removeCallbacks(m_handlerTask);
                        }
                        m_handler.postDelayed(m_handlerTask, 1000);
                    }
                };
                m_handlerTask.run();

                Handler handler = new Handler();
                Runnable run = new Runnable() {

                    @Override
                    public void run() {

                        mPlayer.stop();
                        new OurToast().myToast(getBaseContext() , "Hope you enjoyed");
                        LevelUpDialog();

                    }
                };
                handler.postDelayed(run, 390000);

            }



        });



        pause.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                listen.setEnabled(true);
                pause.setEnabled(false);
                pause.setBackgroundResource(R.mipmap.c_pause);
                listen.setBackgroundResource(R.mipmap.play);


                if (mPlayer.isPlaying())
                {
                    mPlayer.pause();
                }else
                {

                }
            }
        });


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
    }

    private void LevelUpDialog() {

        if (alert != null && alert.isShowing()) {

        }else {
            AlertDialog.Builder builder = new AlertDialog.Builder(Level7.this , R.style.levelUpDialog);
            builder.setCancelable(true);
            alert = builder.create();
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
                    editor.putString("Level7", "Level7");
                    editor.apply();
                    Intent i = new Intent(Level7.this , Level8.class);
                    startActivity(i);
                    finish();
                }
            });
        }
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
