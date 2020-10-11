package evolves.thenwhat;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

public class Launcher extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_luancher);

        TextView launcher = (TextView)findViewById(R.id.launcherText) ;

        Thread timer = new Thread() {
            @Override
            public void run() {
                try {
                    sleep(1000);
                } catch (InterruptedException e) {
                    e.getMessage();
                } finally {

                    new Thread(new Runnable() {
                        @Override
                        public void run() {

                            try {
                                SharedPreferences savedData = PreferenceManager.getDefaultSharedPreferences(getBaseContext());
                                String userName = savedData.getString("userNickname" , null) ;
                                String userVoice = savedData.getString("userVoice" , null) ;
                                String levelTwo = savedData.getString("Level2" , null) ;
                                String quizLevelThree = savedData.getString("quizLevelThree" , null) ;
                                String levelThree = savedData.getString("Level3" , null) ;
                                String levelFour = savedData.getString("Level4" , null) ;
                                String levelFive = savedData.getString("Level5" , null) ;
                                String levelSix = savedData.getString("Level6" , null) ;
                                String levelSeven = savedData.getString("Level7" , null) ;
                                String levelEight = savedData.getString("Level8" , null) ;
                                String levelNine = savedData.getString("Level9" , null) ;
                                String levelTen = savedData.getString("Level10" , null) ;
                                String levelFEleven = savedData.getString("Level11" , null) ;
                                if (userName!= null)
                                {
                                    Intent lunch = new Intent(Launcher.this, Level1.class);
                                    startActivity(lunch);
                                    finish();
                                    if(userVoice != null)
                                    {
                                        Intent lunch1 = new Intent(Launcher.this, Level2.class);
                                        startActivity(lunch1);
                                        finish();
                                        if (levelTwo != null)
                                        {
                                            Intent lunch2 = new Intent(Launcher.this, Level3_Quiz_throwYourPhone.class);
                                            startActivity(lunch2);
                                            finish();

                                            if (quizLevelThree != null)
                                            {
                                                Intent lunch3 = new Intent(Launcher.this, Level3.class);
                                                startActivity(lunch3);
                                                finish();

                                                if (levelThree != null)
                                                {
                                                    Intent lunch4 = new Intent(Launcher.this, Level4.class);
                                                    startActivity(lunch4);
                                                    finish();

                                                    if (levelFour != null)
                                                    {
                                                        Intent lunch5 = new Intent(Launcher.this, Level5.class);
                                                        startActivity(lunch5);
                                                        finish();

                                                        if (levelFive != null)
                                                        {
                                                            Intent lunch6 = new Intent(Launcher.this, Level6.class);
                                                            startActivity(lunch6);
                                                            finish();

                                                            if (levelSix != null) {
                                                                Intent lunch7 = new Intent(Launcher.this, Level7.class);
                                                                startActivity(lunch7);
                                                                finish();

                                                                if (levelSeven != null) {
                                                                    Intent lunch8 = new Intent(Launcher.this, Level8.class);
                                                                    startActivity(lunch8);
                                                                    finish();


                                                                    if (levelEight != null) {
                                                                        Intent lunch9 = new Intent(Launcher.this, Level9.class);
                                                                        startActivity(lunch9);
                                                                        finish();


                                                                        if (levelNine != null) {
                                                                            Intent lunch10 = new Intent(Launcher.this, Level_10.class);
                                                                            startActivity(lunch10);
                                                                            finish();

                                                                            if (levelTen != null) {
                                                                                Intent lunch11 = new Intent(Launcher.this, Level_11.class);
                                                                                startActivity(lunch11);
                                                                                finish();


                                                                            }
                                                                        }
                                                                    }
                                                                }
                                                            }
                                                        }
                                                    }
                                                }
                                            }
                                        }
                                    }
                                }else
                                {
                                    Intent lunch = new Intent(Launcher.this, WelcomeActivity.class);
                                    startActivity(lunch);
                                    finish();
                                }
                            }catch (Exception e)
                            {
                                e.getMessage();
                            }

                        }
                    }).start();
                }
            }
        };
        timer.start();
    }

    @Override
    public void onBackPressed() {

    }
}
