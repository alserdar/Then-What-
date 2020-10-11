package evolves.thenwhat;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.animation.PropertyValuesHolder;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.preference.PreferenceManager;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.animation.AccelerateInterpolator;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.util.Random;

import evolves.thenwhat.toast_pack.OurToast;

public class Level2 extends AppCompatActivity {

    Handler m_handler;
    Runnable m_handlerTask ;
    int _count=1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_level_two);

        final LinearLayout layUp = (LinearLayout)findViewById(R.id.layUp) ;
        final LinearLayout layDown = (LinearLayout)findViewById(R.id.layDown) ;

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

    }

    @Override
    protected void onStart() {
        super.onStart();

        final TextView txtTwo = (TextView)findViewById(R.id.mathQuizLevelTwo);
        final EditText solve = (EditText) findViewById(R.id.solveMathQuiz);
        final Button done = (Button) findViewById(R.id.doneSolveTwo);
        final ProgressBar bar = (ProgressBar) findViewById(R.id.proLevelTwo);

        final String math = "22 * 2 ";
        final String  math1 = " 5 * 12" ;
        final String math2 = "36 + 6 ";
        final String math3 = "120 - 8 ";
        final String math4 = "13 + 31 ";
        final String math5 = "9 + 9 + 9 ";
        final Random r = new Random();
        final String [] calculate = { math , math1 , math2 , math3 , math4 , math5 };
        final String  ran = calculate[r.nextInt(5)] ;
        txtTwo.setText(ran);

        txtTwo.setText(ran);

        m_handler = new Handler();
        m_handlerTask = new Runnable()
        {
            @Override
            public void run() {
                if(_count<=7)
                {
                    bar.setProgress(_count);
                    bar.setMax(7);
                    _count++;

                }
                else
                {
                    m_handler.removeCallbacks(m_handlerTask);
                    final String  ran = calculate[r.nextInt(5)] ;
                    txtTwo.setText(ran);
                    _count = 0 ;

                    done.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {

                            switch (ran)
                            {
                                case math :
                                    if (solve.getText().toString().trim().equals("44"))
                                    {
                                        new OurToast().myToast(getBaseContext() , "Clever");
                                        LevelUpDialog();
                                    }else
                                    {
                                        new OurToast().myToast(getBaseContext() , "No !");
                                    }
                                    break;

                                case math1 :
                                    if (solve.getText().toString().trim().equals("60"))
                                    {
                                        new OurToast().myToast(getBaseContext() , "Clever");
                                        LevelUpDialog();
                                    }else
                                    {
                                        new OurToast().myToast(getBaseContext() , "No !");
                                    }
                                    break;
                                case math2 :
                                    if (solve.getText().toString().trim().equals("42"))
                                    {
                                        new OurToast().myToast(getBaseContext() , "Clever");
                                        LevelUpDialog();
                                    }else
                                    {
                                        new OurToast().myToast(getBaseContext() , "No !");
                                    }
                                    break;
                                case math3 :
                                    if (solve.getText().toString().trim().equals("112"))
                                    {
                                        new OurToast().myToast(getBaseContext() , "Clever");
                                        LevelUpDialog();
                                    }else
                                    {
                                        new OurToast().myToast(getBaseContext() , "No !");
                                    }
                                    break;
                                case math4 :
                                    if (solve.getText().toString().trim().equals("44"))
                                    {
                                        new OurToast().myToast(getBaseContext() , "Clever");
                                        LevelUpDialog();
                                    }else
                                    {
                                        new OurToast().myToast(getBaseContext() , "No !");
                                    }
                                    break;
                                case math5 :
                                    if (solve.getText().toString().trim().equals("27"))
                                    {
                                        new OurToast().myToast(getBaseContext() , "Clever");
                                        LevelUpDialog();
                                    }else
                                    {
                                        new OurToast().myToast(getBaseContext() , "No !");
                                    }
                                    break;
                            }

                        }
                    });

                }
                m_handler.postDelayed(m_handlerTask, 1000);
            }
        };
        m_handlerTask.run();

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

    private void LevelUpDialog() {


        AlertDialog.Builder builder = new AlertDialog.Builder(Level2.this , R.style.levelUpDialog);
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
                editor.putString("Level2", "Level2");
                editor.apply();
                Intent i = new Intent(Level2.this , Level3_Quiz_throwYourPhone.class);
                startActivity(i);
                finish();
            }
        });
    }
}