package me.pakhang.game2048.view.widget;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.view.Gravity;

import androidx.appcompat.widget.AppCompatTextView;
import me.pakhang.game2048.R;
import me.pakhang.game2048.misc.Config;

public class Item extends AppCompatTextView {

    public int index, x, y;
    private int mNumber;
    private GradientDrawable mGd;

    private Item(Context context) {
        super(context);
    }

    public Item(Context context, int index) {
        super(context);
        this.index = index;
        x = index % Config.LEVEL;
        y = index / Config.LEVEL;
        setGravity(Gravity.CENTER);
        setTextSize(32);
        mGd = new GradientDrawable();
        mGd.setCornerRadius(20);
        setNumber(0);
    }

    public void setNumber(int number) {
        mNumber = number;
        setTextColor(number < 8 ? Color.BLACK : Color.WHITE); // 2 和 4 显示黑色，其他显示白色
        setText(number > 0 ? String.valueOf(number) : ""); // 0 为空白格
        mGd.setColor(selectColor());
        setBackground(mGd);
    }

    public int getNumber() {
        return mNumber;
    }

    private int selectColor() {
        switch (mNumber) {
            case 0:
                return getResources().getColor(R.color.color0, null);
            case 2:
                return getResources().getColor(R.color.color2, null);
            case 4:
                return getResources().getColor(R.color.color4, null);
            case 8:
                return getResources().getColor(R.color.color8, null);
            case 16:
                return getResources().getColor(R.color.color16, null);
            case 32:
                return getResources().getColor(R.color.color32, null);
            case 64:
                return getResources().getColor(R.color.color64, null);
            case 128:
                return getResources().getColor(R.color.color128, null);
            case 256:
                return getResources().getColor(R.color.color256, null);
            case 512:
                return getResources().getColor(R.color.color512, null);
            case 1024:
                return getResources().getColor(R.color.color1024, null);
            default:
                return getResources().getColor(R.color.color2048, null);
        }
    }
}
