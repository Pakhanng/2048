package me.pakhang.game2048;

import android.annotation.SuppressLint;
import android.content.Context;
import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;

import java.util.ArrayList;
import java.util.LinkedList;

import me.pakhang.game2048.misc.Action;
import me.pakhang.game2048.misc.Config;
import me.pakhang.game2048.view.widget.GameView;
import me.pakhang.game2048.view.widget.Item;

public class GameController {

    private static final String TAG = "cbh";

    private GameView mGameView;
    private GestureDetector mGestureDetector;

    private boolean mNeedAddNumber;

    private GameController(Context context) {
        // 把 onTouchEvent 转成手势事件来处理
        mGestureDetector = new GestureDetector(context, new GestureDetector.SimpleOnGestureListener() {
            private static final int DISTANCE = 50;

            @Override
            public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
                float dx = e2.getX() - e1.getX();
                float dy = e2.getY() - e1.getY();
                if (Math.abs(dx) > Math.abs(dy)) {
                    if (dx > DISTANCE) {
                        move(Action.RIGHT);
                    } else if (dx < (0 - DISTANCE)){
                        move(Action.LEFT);
                    }
                } else {
                    if (dy > DISTANCE) {
                        move(Action.DOWN);
                    } else if (dy < (0 - DISTANCE)){
                        move(Action.UP);
                    }
                }
                return true;
            }
        });
    }

    public static GameController getInstance(Context context) {
        return new GameController(context);
    }

    @SuppressLint("ClickableViewAccessibility")
    public void setGameView(GameView view) {
        this.mGameView = view;
        mGameView.setOnTouchListener((v, event) -> {
            // 把 onTouchEvent 转成手势事件来处理
            mGestureDetector.onTouchEvent(event);
            return true;
        });
    }

    public void startGame() {
        mGameView.post(() -> {
            mGameView.addNumber();
            mGameView.addNumber();
        });
    }

    public void move(Action action) {
        boolean move = false;
        ArrayList<Integer> list = new ArrayList<>(); //保存原数列
        LinkedList<Integer> queue = new LinkedList<>(); //保存移动合并后的数列
        // 嵌套循环，上下滑时 i 为列（ x 轴)，j 为行( y 轴)，左右滑相反
        for (int i = 0; i < Config.LEVEL; i++) {
            // 1.生成移动，合并后的数字队列
            for (int j = 0; j < Config.LEVEL; j++) {
                int number = getItemByAction(action, i, j).getNumber();
                list.add(number);
                if (number == 0) continue; //跳过空白格
                Integer last = queue.peekLast();
                if (last != null && last == number) {
                    // 相同数字合并
                    queue.removeLast();
                    queue.add(number * 2);
                } else {
                    // 空列或数字不同则入列
                    queue.add(number);
                }
            }
            // 2.判断是否发生移动或合并
            for (int j = 0; j < queue.size(); j++) {
                if (!list.get(j).equals(queue.get(j))) {
                    move = true;
                    break;
                }
            }

            if (move) {
//                // 3.计算移动距离，生成动画
//                int listIndex = 0;
//                boolean mergeNext = false;
//                int preQueueIndex = 0;
//                Log.d(TAG, "move() called with: list = " + list);
//                Log.d(TAG, "move() called with: queue = " + queue);
//                for (int queueIndex = 0; queueIndex < queue.size(); queueIndex++) {
//                    int n = queue.get(queueIndex);
//                    while (listIndex < Config.LEVEL) {
//                        int m = list.get(listIndex);
//                        Log.d(TAG, "move() called with: queueIndex="+queueIndex+", listIndex="+listIndex +", n="+n+", m="+m);
//                        if (m == 0) {
//                            listIndex++;
//                            continue;
//                        }
//                        if (m == n) {
//                            translate(getItemByAction(action, i, listIndex), getItemByAction(action, i, queueIndex), action, listIndex - queueIndex, queue.get(queueIndex));
//                            listIndex++;
//                            break;
//                        } else if (m == n / 2) {
//                            if (mergeNext) {
//                                translate(getItemByAction(action, i, listIndex), getItemByAction(action, i, queueIndex), action, listIndex - preQueueIndex, queue.get(queueIndex));
//                                mergeNext = false;
//                                listIndex++;
//                                break;
//                            } else {
//                                translate(getItemByAction(action, i, listIndex), getItemByAction(action, i, queueIndex), action, listIndex - queueIndex, queue.get(queueIndex));
//                                mergeNext = true;
//                                preQueueIndex = listIndex;
//                                listIndex++;
//                            }
//                        }
//                    }
//                }
                // 4.设置新数字
                for (int j = 0; j < Config.LEVEL; j++) {
                    Integer newNumber = queue.pollFirst();
                    if (newNumber == null) newNumber = 0;
                    getItemByAction(action, i, j).setNumber(newNumber);
                }
            }
            list.clear();
            queue.clear();
        }

        // 4.新增数字
        if (move) {
            mGameView.addNumber();
            mNeedAddNumber = true;
        }

        printMatrix();
    }

    /**
     * 不同动作，for循环中的 i 和 j 代表不同的格子
     */
    private Item getItemByAction(Action action, int i, int j) {
        switch (action) {
            case UP:
                return mGameView.getItem(i, j);

            case DOWN:
                return mGameView.getItem(i, Config.LEVEL - 1 - j);

            case LEFT:
                return mGameView.getItem(j, i);

            case RIGHT:
                return mGameView.getItem(Config.LEVEL - 1 - j, i);

            default:
                throw new IllegalArgumentException();
        }
    }

    private void translate(Item item, Item target, Action action, int distance, int number) {
        if (distance == 0) {
            return;
        }
        TranslateAnimation translateAnimation;
        switch (action) {
            case UP:
                translateAnimation = new TranslateAnimation(
                        0,
                        0,
                        0,
                        0 - (item.getHeight() + 35) * distance);
                break;

            case DOWN:
                translateAnimation = new TranslateAnimation(
                        0,
                        0,
                        0,
                        (item.getHeight() + 35) * distance);
                break;

            case LEFT:
                translateAnimation = new TranslateAnimation(
                        0,
                        0 - (item.getHeight() + 35) * distance,
                        0,
                        0);
                break;

            case RIGHT:
                translateAnimation = new TranslateAnimation(
                        0,
                        (item.getHeight() + 35) * distance,
                        0,
                        0);
                break;

            default:
                throw new IllegalArgumentException();

        }
        translateAnimation.setDuration(200);
        translateAnimation.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                target.setNumber(number);
                item.setNumber(0);
                if (mNeedAddNumber) {
                    mGameView.addNumber();
                    mNeedAddNumber = false;
                }
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });
        Log.d("cbh", "startAnimation: ");
        item.startAnimation(translateAnimation);
    }

    private void printMatrix() {
        StringBuilder sb = new StringBuilder(" \n");
        for (int i = 0; i < Config.LEVEL; i++) {
            for (int j = 0; j < Config.LEVEL; j++) {
                sb.append(mGameView.getItem(j, i).getNumber()).append("\t");
            }
            sb.append("\n");
        }
        Log.d("matrix", sb.toString());
    }

}
