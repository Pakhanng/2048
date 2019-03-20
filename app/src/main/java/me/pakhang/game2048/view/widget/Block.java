package me.pakhang.game2048.view.widget;

import android.content.Context;
import android.graphics.Color;
import android.util.Log;
import android.view.Gravity;

import androidx.appcompat.widget.AppCompatTextView;

public class Block extends AppCompatTextView {

    private int mNumber;

    public Block(Context context) {
        super(context);
        setBackgroundColor(Color.parseColor("#c3c3c3"));
        setGravity(Gravity.CENTER);
        setTextSize(24); //TODO 根据数字调整大小
    }

    public void setNumber(int number) {
        Log.d("cbh", "setNumber() called with: number = [" + number + "]");
        mNumber = number;
        setText(String.valueOf(number));
        postInvalidate();
    }

    public int getNumber() {
        return mNumber;
    }
}
