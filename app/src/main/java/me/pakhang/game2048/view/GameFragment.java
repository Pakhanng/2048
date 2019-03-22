package me.pakhang.game2048.view;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import me.pakhang.game2048.GameController;
import me.pakhang.game2048.misc.Action;
import me.pakhang.game2048.R;
import me.pakhang.game2048.view.widget.GameView;

public class GameFragment extends Fragment {

    private static final String TAG = "cbh";

    private final GameController mController;

    @BindView(R.id.grid)
    GameView mGameView;
    
    @OnClick({R.id.up, R.id.down, R.id.left, R.id.right})
    void onClick(View view) {
        switch (view.getId()) {
            case R.id.up:
                mController.move(Action.UP);
                break;

            case R.id.down:
                mController.move(Action.DOWN);
                break;

            case R.id.left:
                mController.move(Action.LEFT);
                break;

            case R.id.right:
                mController.move(Action.RIGHT);
                break;
        }
    }

    public GameFragment() {
        mController = GameController.getInstance(getContext());
    }

    public static GameFragment newInstance() {
        return new GameFragment();
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
        mController.setGameView(mGameView);
        mController.startGame();
    }

}
