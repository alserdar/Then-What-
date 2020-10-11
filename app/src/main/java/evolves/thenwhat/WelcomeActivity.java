package evolves.thenwhat;

import android.animation.ObjectAnimator;
import android.animation.PropertyValuesHolder;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import evolves.thenwhat.toast_pack.OurToast;

public class WelcomeActivity extends AppCompatActivity {

    private static final String TAG = "evolves.thenwhat";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);

        SectionsPagerAdapter mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());

        ViewPager mViewPager = (ViewPager) findViewById(R.id.container);
        mViewPager.setAdapter(mSectionsPagerAdapter);

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_welcome, menu);
        return true;
    }

    public class SectionsPagerAdapter extends FragmentPagerAdapter {

        public SectionsPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            switch (position)
            {
                case 0 :
                    return FirstWelcome.newInstance(position + 1);
                case 1:
                    return SecondWelcome.newInstance(position + 1);
                case 2:
                    return ThirdWelcome.newInstance(position + 1);
            }

            return null ;
        }

        @Override
        public int getCount() {
            // Show 3 total pages.
            return 3;
        }
    }

    public static class FirstWelcome extends Fragment {
        private static final String ARG_SECTION_NUMBER = "section_number";

        public static FirstWelcome newInstance(int sectionNumber) {
            FirstWelcome fragment = new FirstWelcome();
            Bundle args = new Bundle();
            args.putInt(ARG_SECTION_NUMBER, sectionNumber);
            fragment.setArguments(args);
            return fragment;
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            View rootView = inflater.inflate(R.layout.first_welcome, container, false);

            rootView.getBackground().setAlpha(127);
            return rootView;
        }
    }

    public static class SecondWelcome extends Fragment {
        private static final String ARG_SECTION_NUMBER = "section_number";

        public static SecondWelcome newInstance(int sectionNumber) {
            SecondWelcome fragment = new SecondWelcome();
            Bundle args = new Bundle();
            args.putInt(ARG_SECTION_NUMBER, sectionNumber);
            fragment.setArguments(args);
            return fragment;
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            View rootView = inflater.inflate(R.layout.second_welcome, container, false);

            rootView.getBackground().setAlpha(127);
            return rootView;
        }
    }
    public static class ThirdWelcome extends Fragment {
        private static final String ARG_SECTION_NUMBER = "section_number";

        public static ThirdWelcome newInstance(int sectionNumber) {
            ThirdWelcome fragment = new ThirdWelcome();
            Bundle args = new Bundle();
            args.putInt(ARG_SECTION_NUMBER, sectionNumber);
            fragment.setArguments(args);
            return fragment;
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            View rootView = inflater.inflate(R.layout.third_welcome, container, false);
            final TextView userNick = (TextView) rootView.findViewById(R.id.userNickname);
            final Button submitNickname = (Button) rootView.findViewById(R.id.submitNickname);

            rootView.getBackground().setAlpha(127);
            TextWatcher watcher = new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                }

                @Override
                public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                    if (userNick.getText().toString().trim().equals(""))
                    {
                        submitNickname.setClickable(false);
                    }else
                    {
                        submitNickname.setClickable(true);
                    }

                }

                @Override
                public void afterTextChanged(Editable editable) {

                }
            };

            userNick.addTextChangedListener(watcher);

            submitNickname.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    if (userNick.getText().toString().trim().equals(""))
                    {
                        submitNickname.setClickable(false);
                        userNick.setError("Type your Nickname");
                    }else
                    {
                        submitNickname.setClickable(true);
                        SharedPreferences saved = PreferenceManager.getDefaultSharedPreferences(getContext());
                        final SharedPreferences.Editor editor = saved.edit();
                        editor.putString("userNickname", userNick.getText().toString());
                        editor.apply();
                        LevelUpDialog(getContext());
                    }
                }
            });

            return rootView;
        }
    }

    private static void LevelUpDialog(final Context context) {


        AlertDialog.Builder builder = new AlertDialog.Builder(context , R.style.levelUpDialog);
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

                Intent i = new Intent(context , Level1.class);
                context.startActivity(i);
                new OurToast().myToast(context , "Cool !");
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
                        finish();
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
