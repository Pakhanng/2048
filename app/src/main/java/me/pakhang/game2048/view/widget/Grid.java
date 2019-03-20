package me.pakhang.game2048.view.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.widget.GridLayout;

import java.util.Random;

import me.pakhang.game2048.Config;

public class Grid extends GridLayout {

    private static final String TAG = "cbh";
    private static final int BLOCK_COUNT = Config.LEVEL * Config.LEVEL;

    private final Block[] mBlocks;
    private Random mRandom = new Random();

    public Grid(Context context, AttributeSet attrs) {
        super(context, attrs);
        setRowCount(Config.LEVEL);
        setColumnCount(Config.LEVEL);
        mBlocks = new Block[BLOCK_COUNT];
    }

    @Override
    protected void onMeasure(int widthSpec, int heightSpec) {
        super.onMeasure(widthSpec, heightSpec);

        int length = Math.min(MeasureSpec.getSize(widthSpec), MeasureSpec.getSize(heightSpec));

        MarginLayoutParams params = (MarginLayoutParams) getLayoutParams();
        int margin = params.leftMargin;
        int blockLength = length / Config.LEVEL;
        Log.d(TAG, "onMeasure() called with: length = [" + length + "], blockLength = [" + blockLength + "]");
        for (int i = 0; i < BLOCK_COUNT; i++) {
            mBlocks[i] = new Block(getContext());
            MarginLayoutParams layoutParams = new MarginLayoutParams(blockLength - 10, blockLength - 10);
            layoutParams.topMargin = layoutParams.bottomMargin = layoutParams.leftMargin = layoutParams.rightMargin = 5;
            addView(mBlocks[i], layoutParams);
            measureChild(mBlocks[i], widthSpec, heightSpec);
        }
        setMeasuredDimension(length, length);
    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);
        Log.d(TAG, "onLayout() called with: changed = [" + changed + "], left = [" + left
                + "], top = [" + top + "], right = [" + right + "], bottom = [" + bottom + "]");
    }

    public void addNumber() {
        int i = mRandom.nextInt(BLOCK_COUNT);
        while (mBlocks[i].getNumber() != 0) {
            i = mRandom.nextInt(BLOCK_COUNT);
        }
        mBlocks[i].setNumber(2 * (mRandom.nextInt(2) + 1)); //生成2或4
        Log.d(TAG, "addNumber: i=" + i);
    }

    public int getBlockIndex(int x, int y) {
        return x + y * Config.LEVEL;
    }

    public Block getBlock(int x, int y) {
        return mBlocks[getBlockIndex(x, y)];
    }

}
