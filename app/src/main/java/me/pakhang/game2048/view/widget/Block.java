package me.pakhang.game2048.view.widget;

import android.content.Context;
import android.graphics.Color;
import android.view.Gravity;

import androidx.appcompat.widget.AppCompatTextView;
import me.pakhang.game2048.Config;

public class Block extends AppCompatTextView {

    private int mNumber;
    public int index, x, y;

    private Block(Context context) {
        super(context);
    }

    public Block(Context context, int index) {
        super(context);
        this.index = index;
        x = index % Config.LEVEL;
        y = index / Config.LEVEL;
        setBackgroundColor(Color.parseColor("#c3c3c3"));
        setGravity(Gravity.CENTER);
        setTextSize(24); //TODO 根据数字调整大小
    }

    public void setNumber(int number) {
        if (number == 0) {
            setText("");
            setBackgroundColor(Color.parseColor("#c3c3c3"));
        } else {
            setText(String.valueOf(number));
            setBackgroundColor(Color.YELLOW);
        }
        mNumber = number;
    }

    public int getNumber() {
        return mNumber;
    }

    public void moveTo(int targetX, int targetY) {

    }
}
