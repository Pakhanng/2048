package me.pakhang.game2048.view.widget;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.drawable.BitmapDrawable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.GridLayout;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import me.pakhang.game2048.R;
import me.pakhang.game2048.misc.Config;

public class GameView extends GridLayout {

    private static final String TAG = "cbh";

    private Item[] mItems;
    private Random mRandom = new Random();
    private List<Integer> mEmptyItemIndexList = new ArrayList<>();
    private BitmapDrawable mBgDrawabale; // 加入所有格子后，使用cache重新设置背景，使背景带格子，优化动画效果

    public GameView(Context context, AttributeSet attrs) {
        super(context, attrs);
        setRowCount(Config.LEVEL);
        setColumnCount(Config.LEVEL);
    }

    @Override
    protected void onMeasure(int widthSpec, int heightSpec) {
        super.onMeasure(widthSpec, heightSpec);

        int length = Math.min(MeasureSpec.getSize(widthSpec), MeasureSpec.getSize(heightSpec));
        Log.d(TAG, "onMeasure() called with: length = [" + length + "]");

        // 初始化所有格子
        if (mItems == null) {
            mItems = new Item[Config.LEVEL * Config.LEVEL];
            int itemLength = (length - (Config.LEVEL + 1) * 35) / Config.LEVEL;
            for (int i = 0; i < Config.LEVEL * Config.LEVEL; i++) {
                mItems[i] = new Item(getContext(), i);
                MarginLayoutParams layoutParams = new MarginLayoutParams(itemLength, itemLength);
                layoutParams.topMargin = layoutParams.leftMargin = 35;
                addView(mItems[i], layoutParams);
                measureChild(mItems[i], widthSpec, heightSpec);
            }
        }

        setMeasuredDimension(length, length);
    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);
        Log.d(TAG, "onLayout: ");
        if (mBgDrawabale == null) {
            setDrawingCacheEnabled(true);
            mBgDrawabale = new BitmapDrawable(getResources(), getDrawingCache());
            setBackground(mBgDrawabale);
        }
    }


    public Item getItem(int x, int y) {
        return mItems[x + y * Config.LEVEL];
    }

    public void addNumber() {
        checkEmpty();
        if (mEmptyItemIndexList.isEmpty()) {
            return;
        }
        int index = mEmptyItemIndexList.get(mRandom.nextInt(mEmptyItemIndexList.size()));
        mItems[index].setNumber(Math.random() > 0.75f ? 4 : 2); // 生成2或4
        // 动画
        Animation animation = AnimationUtils.loadAnimation(getContext(), R.anim.item_scale);
        mItems[index].startAnimation(animation);
    }

    /**
     * 重新加载空格子到集合里
     */
    private void checkEmpty() {
        mEmptyItemIndexList.clear();
        for (Item item : mItems) {
            if (item.getNumber() == 0) {
                mEmptyItemIndexList.add(item.index);
            }
        }
    }

}
