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
import android.text.InputFilter;
import android.view.View;
import android.view.animation.AccelerateInterpolator;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;

import java.util.Random;

import evolves.thenwhat.toast_pack.OurToast;

public class Level5 extends AppCompatActivity {

    EditText petName ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_level_five);

        final LinearLayout layUp = (LinearLayout) findViewById(R.id.layUp);
        final LinearLayout layDown = (LinearLayout) findViewById(R.id.layDown);
        final Button giftBox = (Button) findViewById(R.id.giftLevelFive);
        final LinearLayout gift = (LinearLayout) findViewById(R.id.theGift);


        final Random random = new Random();

        giftBox.setBackgroundResource(R.mipmap.gift_box);

        giftBox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                giftBox.setBackgroundResource(R.mipmap.gift_opend);
                giftBox.setEnabled(false);
                int gifts [] = {R.mipmap.dog_2 ,
                        R.mipmap.elephant ,
                        R.mipmap.penguin ,
                        R.mipmap.elephant_2 ,
                        R.mipmap.unicorn ,
                        R.mipmap.cat
                        , R.mipmap.dog
                        , R.mipmap.duck
                        , R.mipmap.gorilla
                        , R.mipmap.owl
                        , R.mipmap.goat ,
                        R.mipmap.turtle ,
                        R.mipmap.elephant ,
                        R.mipmap.frog ,
                        R.mipmap.bat ,
                        R.mipmap.penguin_2 ,
                        R.mipmap.lion};

                int whichOne = random.nextInt(16);

                gift.setBackgroundResource(gifts[whichOne]);
                AnimatorSet animatorSet2 = new AnimatorSet();
                animatorSet2.playTogether(
                        ObjectAnimator.ofFloat(gift, "scaleX", 1f, 0.5f, 1f),
                        ObjectAnimator.ofFloat(gift, "scaleY", 1f, 0.5f, 1f)
                );
                animatorSet2.setInterpolator(new AccelerateInterpolator());
                animatorSet2.setDuration(1000);
                animatorSet2.start();

                Handler handler = new Handler();
                Runnable run = new Runnable() {

                    @Override
                    public void run() {

                        gottaPet();

                    }
                };
                handler.postDelayed(run, 3000);


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



    private void gottaPet ()
    {
        AlertDialog.Builder builder = new AlertDialog.Builder(this , R.style.DialogAnimation);
        builder.setTitle("Congrats ! ");
        builder.setMessage("You got a pet ,Give it a name");
        builder.setIcon(R.mipmap.tw_icon);
        petName = new EditText(Level5.this);
        int maxLength = 10;
        InputFilter[] fArray = new InputFilter[1];
        fArray[0] = new InputFilter.LengthFilter(maxLength);
        petName.setFilters(fArray);
        builder.setView(petName);
        builder.setCancelable(false);
        builder.setNegativeButton("Ok", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

                if (petName.getText().toString().trim().equals(""))
                {
                    new OurToast().myToast(getBaseContext() , "Come on , it deserve a name ");
                    gottaPet();

                }else
                {
                    LevelUpDialog();
                }
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


        AlertDialog.Builder builder = new AlertDialog.Builder(Level5.this , R.style.levelUpDialog);
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
                editor.putString("Level5", "Level5");
                editor.putString("petName", petName.getText().toString());
                editor.apply();
                Intent i = new Intent(Level5.this , Level6.class);
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
