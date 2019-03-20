package me.pakhang.game2048.view;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridLayout;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import me.pakhang.game2048.R;
import me.pakhang.game2048.view.widget.Block;
import me.pakhang.game2048.view.widget.Grid;

public class GameFragment extends Fragment {

    @BindView(R.id.grid)
    Grid mGrid;

    @OnClick({R.id.up, R.id.down, R.id.left, R.id.right})
    void onClick(View view) {
        startGame();
        switch (view.getId()) {
            case R.id.up:
                break;

            case R.id.down:
                break;

            case R.id.left:
                break;

            case R.id.right:
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

//        startGame();

//        mPool.post(new Runnable() {
//            @Override
//            public void run() {
//                mPool.addBlock(2048, 1, 2);
//            }
//        });
    }

    private void startGame() {
        mGrid.addNumber();
        mGrid.addNumber();
    }

}
