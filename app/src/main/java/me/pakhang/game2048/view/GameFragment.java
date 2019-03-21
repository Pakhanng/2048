package me.pakhang.game2048.view;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.LinkedList;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import me.pakhang.game2048.Action;
import me.pakhang.game2048.Config;
import me.pakhang.game2048.R;
import me.pakhang.game2048.view.widget.Block;
import me.pakhang.game2048.view.widget.Grid;

public class GameFragment extends Fragment {

    @BindView(R.id.grid)
    Grid mGrid;

    @OnClick({R.id.up, R.id.down, R.id.left, R.id.right})
    void onClick(View view) {
        switch (view.getId()) {
            case R.id.up:
                move(Action.UP);
                break;

            case R.id.down:
                move(Action.DOWN);
                break;

            case R.id.left:
                move(Action.LEFT);
                break;

            case R.id.right:
                move(Action.RIGHT);
                break;
        }
    }

    public GameFragment() {
        // Required empty public constructor
    }

    public static GameFragment newInstance() {
        GameFragment fragment = new GameFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_game, container, false);
        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mGrid.post(this::startGame);
    }


    private void startGame() {
        mGrid.addNumber();
        mGrid.addNumber();
    }

    private void move(Action action) {
        int[][] newMarix = new int[Config.LEVEL][Config.LEVEL];
        LinkedList<Integer> queue = new LinkedList<>();
        boolean move = false;
        for (int i = 0; i < Config.LEVEL; i++) {
            // 1.生成移动，合并后的数字队列
            for (int j = 0; j < Config.LEVEL; j++) {
                Block block = getBlockByAction(action, i, j);
                int number = block.getNumber();
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
                Block block = getBlockByAction(action, i, j);
                if (block.getNumber() != queue.get(j)) {
                    move = true;
                    break;
                }
            }

            // 3.设置新数字
            if (move) {
                for (int j = 0; j < Config.LEVEL; j++) {
                    Integer newNumber = queue.pollFirst();
                    if (newNumber == null) newNumber = 0;
                    newMarix[i][j] = newNumber;
                    getBlockByAction(action, i, j).setNumber(newNumber);
                }
            } else {
                queue.clear();
            }
        }

        Log.d("cbh", "onClick: move=" + move);
        if (move) {
            mGrid.addNumber();
        }
        printMatrix();

    }

    private void printMatrix() {
        StringBuilder sb = new StringBuilder(" \n");
        for (int i = 0; i < Config.LEVEL; i++) {
            for (int j = 0; j < Config.LEVEL; j++) {
                sb.append(mGrid.getBlock(j, i).getNumber()).append("\t");
            }
            sb.append("\n");
        }
        Log.d("matrix", sb.toString());
    }

    private Block getBlockByAction(Action action, int i, int j) {
        switch (action) {
            case UP:
                return mGrid.getBlock(i, j);

            case DOWN:
                return mGrid.getBlock(i, Config.LEVEL - 1 - j);

            case LEFT:
                return mGrid.getBlock(j, i);

            case RIGHT:
                return mGrid.getBlock(Config.LEVEL - 1 - j, i);

            default:
                throw new IllegalArgumentException();
        }
    }

}
