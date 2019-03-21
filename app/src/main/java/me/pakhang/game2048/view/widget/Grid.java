package me.pakhang.game2048.view.widget;

import android.content.Context;
import android.transition.TransitionManager;
import android.util.AttributeSet;
import android.util.Log;
import android.view.animation.ScaleAnimation;
import android.widget.GridLayout;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import me.pakhang.game2048.Config;

public class Grid extends GridLayout {

    private static final String TAG = "cbh";
    private static final int BLOCK_COUNT = Config.LEVEL * Config.LEVEL;

    private Block[] mBlocks;
    private Random mRandom = new Random();
    private List<Integer> mEmptyBlockIndexList = new ArrayList<>();

    public Grid(Context context, AttributeSet attrs) {
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
        if (mBlocks == null) {
            mBlocks = new Block[BLOCK_COUNT];
            int blockLength = (length - (Config.LEVEL + 1) * 35) / Config.LEVEL;
            for (int i = 0; i < BLOCK_COUNT; i++) {
                mBlocks[i] = new Block(getContext(), i);
                MarginLayoutParams layoutParams = new MarginLayoutParams(blockLength, blockLength);
                layoutParams.topMargin = layoutParams.leftMargin = 35;
                addView(mBlocks[i], layoutParams);
                measureChild(mBlocks[i], widthSpec, heightSpec);
            }
        }

        setMeasuredDimension(length, length);
    }

    public void addNumber() {
        loadEmptyBlockIndexList();
        if (mEmptyBlockIndexList.isEmpty()) {
            return;
        }
        int index = mEmptyBlockIndexList.get(mRandom.nextInt(mEmptyBlockIndexList.size()));
        mBlocks[index].setNumber(Math.random() > 0.75f ? 4 : 2); // 生成2或4
//        mBlocks[index].startAnimation(new ScaleAnimation(0,1,0,1));

    }

    private void loadEmptyBlockIndexList() {
        mEmptyBlockIndexList.clear();
        for (Block block : mBlocks) {
            if (block.getNumber() == 0) {
                mEmptyBlockIndexList.add(block.index);
            }
        }
    }

    // x, y => index
    public int getBlockIndex(int x, int y) {
        return x + y * Config.LEVEL;
    }

    public Block getBlock(int x, int y) {
        return mBlocks[getBlockIndex(x, y)];
    }

}
