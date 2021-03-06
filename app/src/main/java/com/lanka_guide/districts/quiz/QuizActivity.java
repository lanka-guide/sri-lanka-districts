package com.lanka_guide.districts.quiz;

import android.content.res.Configuration;
import android.content.res.Resources;
import android.graphics.Color;
import android.graphics.ColorFilter;
import android.graphics.PorterDuff;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.lanka_guide.districts.Districts;
import com.lanka_guide.districts.R;

import java.util.HashSet;
import java.util.List;
import java.util.Locale;
import java.util.Set;
import java.util.concurrent.TimeUnit;

public class QuizActivity extends AppCompatActivity {

    private Button option1;
    private Button option2;
    private Button option3;
    private Button option4;
    private ColorFilter defaultButtonBackground;
    private ViewPager mViewPager;
    private Set<String> correctAnswers = new HashSet<>();
    private Set<String> showedDistricts = new HashSet<>();
    private AdView mAdView;
    private long startTime;
    private Locale locale;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        new Districts(getApplicationContext());


        Resources resources = getResources();
        Configuration configuration = resources.getConfiguration();
        locale = configuration.locale;
        getApplicationContext().createConfigurationContext(configuration);

        setContentView(R.layout.quiz_activity);

        mAdView = (AdView) findViewById(R.id.quizAdView);
        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);

        mViewPager = (ViewPager) findViewById(R.id.viewPageAndroid);
        QuizMapsSliderAdapter adapterView = new QuizMapsSliderAdapter(this);
        mViewPager.setAdapter(adapterView);
        mViewPager.addOnPageChangeListener(new CustomOnPageChangeListener());

        correctAnswers.clear();
        setAnswers();
        defaultButtonBackground = option1.getBackground().getColorFilter();

        startTime = System.currentTimeMillis();

    }

    private void setAnswers() {
        int currentDistrict = mViewPager.getCurrentItem();
        String districtName = Districts.getName(currentDistrict);
        List<String> answers = Districts.getAnswers(districtName);
        option1 = (Button) findViewById(R.id.option1);
        option1.setText(answers.get(0));

        option2 = (Button) findViewById(R.id.option2);
        option2.setText(answers.get(1));

        option3 = (Button) findViewById(R.id.option3);
        option3.setText(answers.get(2));

        option4 = (Button) findViewById(R.id.option4);
        option4.setText(answers.get(3));

        showedDistricts.add(districtName);
    }

    public void clickAnswer(View view) {
        int currentDistrict = mViewPager.getCurrentItem();
        String correctName = Districts.getName(currentDistrict);
        switch (view.getId()) {
            case R.id.option1:
                showAnswer(correctName, option1);
                break;
            case R.id.option2:
                showAnswer(correctName, option2);
                break;
            case R.id.option3:
                showAnswer(correctName, option3);
                break;
            case R.id.option4:
                showAnswer(correctName, option4);
                break;
        }
    }

    private void showAnswer(String correctAnswer, Button selectedButton) {
        String selectedAnswer = selectedButton.getText().toString();

        if (selectedAnswer.equals(correctAnswer)) {
            selectedButton.getBackground().setColorFilter(Color.GREEN, PorterDuff.Mode.SRC_ATOP);
            disableAllButtons();
            selectedButton.setEnabled(true);
            correctAnswers.add(correctAnswer);
        } else {
            selectedButton.getBackground().setColorFilter(Color.RED, PorterDuff.Mode.SRC_ATOP);
            disableAllButtons();

            if (option1.getText().equals(correctAnswer)) {
                option1.getBackground().setColorFilter(Color.GREEN, PorterDuff.Mode.SRC_ATOP);
                option1.setEnabled(true);
                option1.invalidate();
            } else if (option2.getText().equals(correctAnswer)) {
                option2.getBackground().setColorFilter(Color.GREEN, PorterDuff.Mode.SRC_ATOP);
                option2.setEnabled(true);
                option2.invalidate();
            } else if (option3.getText().equals(correctAnswer)) {
                option3.getBackground().setColorFilter(Color.GREEN, PorterDuff.Mode.SRC_ATOP);
                option3.setEnabled(true);
                option3.invalidate();
            } else if (option4.getText().equals(correctAnswer)) {
                option4.getBackground().setColorFilter(Color.GREEN, PorterDuff.Mode.SRC_ATOP);
                option4.setEnabled(true);
                option4.invalidate();
            }

        }
        selectedButton.invalidate();

        if (correctAnswers.size() == 25) {
            TextView resultText = (TextView) findViewById(R.id.quizResult);
            resultText.setVisibility(View.VISIBLE);
            resultText.bringToFront();

            showTimeSpent();
        } else if (showedDistricts.size() == 25) {
            Configuration config = new Configuration();
            config.setLocale(locale);

            getBaseContext().getResources().updateConfiguration(config,
                    getBaseContext().getResources().getDisplayMetrics());

            TextView resultText = (TextView) findViewById(R.id.quizResult);
            resultText.setVisibility(View.VISIBLE);
            resultText.bringToFront();

            resultText.setText(String.format(getString(R.string.quizNumOfCorrectAns), correctAnswers.size()));
            resultText.setTextColor(Color.BLACK);
            resultText.setTextSize(15);

            showTimeSpent();
        } else {
            TextView slideForNextText = (TextView) findViewById(R.id.quizSlideForNext);
            slideForNextText.setVisibility(View.VISIBLE);
            slideForNextText.bringToFront();
        }
    }

    private void showTimeSpent() {
        TextView timeSpentText = (TextView) findViewById(R.id.quizTime);
        timeSpentText.setVisibility(View.VISIBLE);
        timeSpentText.bringToFront();

        long timeSpent = System.currentTimeMillis() - startTime;
        String timeSpentStr = getTimeSpenStr(timeSpent);
        timeSpentText.setText(timeSpentStr);
    }

    private String getTimeSpenStr(long timeSpent) {
        return String.format(Locale.ENGLISH, "%02d:%02d",
                TimeUnit.MILLISECONDS.toMinutes(timeSpent),
                TimeUnit.MILLISECONDS.toSeconds(timeSpent) -
                        TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(timeSpent))
        );
    }

    private void disableAllButtons() {
        option1.setEnabled(false);
        option2.setEnabled(false);
        option3.setEnabled(false);
        option4.setEnabled(false);
    }

    private void resetAllButtons() {
        option1.setEnabled(true);
        option2.setEnabled(true);
        option3.setEnabled(true);
        option4.setEnabled(true);

        option1.getBackground().setColorFilter(defaultButtonBackground);
        option2.getBackground().setColorFilter(defaultButtonBackground);
        option3.getBackground().setColorFilter(defaultButtonBackground);
        option4.getBackground().setColorFilter(defaultButtonBackground);

        option1.invalidate();
        option2.invalidate();
        option3.invalidate();
        option4.invalidate();

        TextView slideForNextText = (TextView) findViewById(R.id.quizSlideForNext);
        slideForNextText.setVisibility(View.GONE);
        slideForNextText.bringToFront();
    }

    private class CustomOnPageChangeListener implements ViewPager.OnPageChangeListener {

        @Override
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

        }

        @Override
        public void onPageSelected(int position) {
            setAnswers();
            resetAllButtons();
        }

        @Override
        public void onPageScrollStateChanged(int state) {

        }
    }
}