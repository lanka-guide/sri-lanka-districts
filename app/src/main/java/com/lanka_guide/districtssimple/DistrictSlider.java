package com.lanka_guide.districtssimple;

import android.graphics.Color;
import android.graphics.ColorFilter;
import android.graphics.PorterDuff;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import java.util.List;

public class DistrictSlider extends AppCompatActivity {

    private Button option1;
    private Button option2;
    private Button option3;
    private Button option4;
    private ColorFilter defaultButtonBackground;
    private ViewPager mViewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.android_image_slider_activity);

        mViewPager = (ViewPager) findViewById(R.id.viewPageAndroid);
        DistrictSliderAdapter adapterView = new DistrictSliderAdapter(this);
        mViewPager.setAdapter(adapterView);
        mViewPager.addOnPageChangeListener(new CustomOnPageChangeListener());

        setAnswers();
        defaultButtonBackground = option1.getBackground().getColorFilter();
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