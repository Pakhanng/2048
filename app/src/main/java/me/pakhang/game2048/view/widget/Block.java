package me.pakhang.game2048.view.widget;

import android.content.Context;
import android.content.res.ColorStateList;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.util.Log;
import android.view.Gravity;

import androidx.appcompat.widget.AppCompatTextView;
import me.pakhang.game2048.Config;
import me.pakhang.game2048.R;

public class Block extends AppCompatTextView {

    public int index, x, y;
    private int mNumber;
    private GradientDrawable mGd;

    private Block(Context context) {
        super(context);
    }

    public Block(Context context, int index) {
        super(context);
        this.index = index;
        x = index % Config.LEVEL;
        y = index / Config.LEVEL;
        setBackgroundResource(R.drawable.shape_block_bg);
        setGravity(Gravity.CENTER);
        setTextSize(40); //TODO 根据数字调整大小
        //圆角
        mGd = new GradientDrawable();
        mGd.setCornerRadius(20);
    }

    public void setNumber(int number) {
        if (number == 0) {
            setText("");
            setBackgroundColor(Color.parseColor("#BFB0A6"));
        } else {
            setText(String.valueOf(number));
            mGd.setColor(calcColor(number));
            setBackground(mGd);
            setTextColor(number < 8 ? Color.BLACK : Color.WHITE);
//            setBackgroundColor(calcColor(number));
//            setBackgroundColor(Color.rgb(255,0,0));
        }
        mNumber = number;
    }

    public int getNumber() {
        return mNumber;
    }

    public void moveTo(int targetX, int targetY) {

    }

    private int calcColor(int number) {
//        if (number == 0) return Color.rgb(191, 176, 166);
//        int pow = (int) Math.pow(number, 0.5);
//        Log.d("cbh", "calcColor() called with: pow = [" + pow + "]");
//        int alpha = (255 / 11) * pow;
//        return Color.argb(alpha, 255, 0, 0);

        String colorStr;
        switch (number) {
            case 0:
                colorStr = "#CCC0B3";
                break;
            case 2:
                colorStr = "#EEE4DA";
                break;
            case 4:
                colorStr = "#EDE0C8";
                break;
            case 8:
                colorStr = "#F2B179";// #F2B179
                break;
            case 16:
                colorStr = "#F49563";
                break;
            case 32:
                colorStr = "#F5794D";
                break;
            case 64:
                colorStr = "#F55D37";
                break;
            case 128:
                colorStr = "#EEE863";
                break;
            case 256:
                colorStr = "#EDB04D";
                break;
            case 512:
                colorStr = "#ECB04D";
                break;
            case 1024:
                colorStr = "#EB9437";
                break;
            case 2048:
                colorStr = "#EA7821";
                break;
            default:
                colorStr = "#EA7821";
                break;
        }
        return Color.parseColor(colorStr);
    }
}
