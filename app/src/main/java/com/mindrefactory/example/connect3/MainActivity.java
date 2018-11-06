package com.mindrefactory.example.connect3;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayout;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    private int[][] sequences = {{0, 1, 2}, {3, 4, 5}, {6, 7, 8}, {0, 4, 8},
            {2, 4, 6}, {0, 3, 6}, {1, 4, 7}, {2, 5, 8}};
    private static final int BOARD_SIZE = 9;
    private int[] boardPlayArray;
    private int currentPlay;
    private Player currentPlayer;
    private GameState currentGameState;
    private TextView titleMessage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        titleMessage = findViewById(R.id.messageBox);
        setupNewGame();
    }

    private boolean hasWinner() {
        for(int[] sequence : sequences) {
            if(boardPlayArray[sequence[0]] != 0
                    && boardPlayArray[sequence[0]] == boardPlayArray[sequence[1]]
                    && boardPlayArray[sequence[1]] == boardPlayArray[sequence[2]]) {
                return true;
            }
        }
        return false;
    }

    public void play(View view) {
        if(currentGameState == GameState.RUNNING && isBlankSpace(view)) {
            dropPiece(view);
            currentGameState = checkGameState();
            if(currentGameState == GameState.RUNNING) {
                changePlayerTurn();
            }
            else if(currentGameState == GameState.WIN) {
                finishGame(String.format("%s player has won the game!", currentPlayer));
            } else {
                finishGame("No winners for this match!");
            }
        }
    }

    private GameState checkGameState() {
        if(hasWinner()) {
            return GameState.WIN;
        } else if(currentPlay == BOARD_SIZE) {
            return GameState.DRAW;
        }
        return GameState.RUNNING;
    }

    private void finishGame(String message) {
        titleMessage.setText(message);
        findViewById(R.id.resetButton).setVisibility(View.VISIBLE);
        findViewById(R.id.quitButton).setVisibility(View.VISIBLE);
    }

    private void changePlayerTurn() {
        currentPlayer = currentPlayer == Player.RED ? Player.YELLOW: Player.RED;
        titleMessage.setText(String.format("%s plays now...", currentPlayer));
    }

    private boolean isBlankSpace(final View view) {
        ImageView imageView = (ImageView) view;
        Integer tagValue = Integer.parseInt(imageView.getTag().toString());
        return boardPlayArray[tagValue] == 0;
    }

    private void dropPiece(final View view) {
        ImageView imageView = (ImageView) view;
        Integer tagValue = Integer.parseInt(imageView.getTag().toString());
        imageView.setTranslationY(-800f);
        imageView.setImageResource(currentPlayer.getPlayerImage());
        imageView.animate().rotation(100).translationYBy(800f).setDuration(500).start();
        boardPlayArray[tagValue] = currentPlayer.getIntValue();
        currentPlay++;
    }

    public void resetGame(View view) {
        GridLayout gridLayout = findViewById(R.id.grid);
        for(int count = 0; count < gridLayout.getChildCount(); count++) {
            ((ImageView)gridLayout.getChildAt(count)).setImageDrawable(null);
        }
        setupNewGame();
    }

    protected void exitGame(View view) {
        finishAndRemoveTask();
    }

    private void setupNewGame() {
        boardPlayArray = new int[BOARD_SIZE];
        titleMessage.setText("Configuring new match...");
        currentPlay = 0;
        currentPlayer = Math.round(Math.random()) == 1 ? Player.YELLOW : Player.RED;
        titleMessage.setText(String.format("%s plays now...", currentPlayer));
        currentGameState = GameState.RUNNING;
        findViewById(R.id.resetButton).setVisibility(View.INVISIBLE);
        findViewById(R.id.quitButton).setVisibility(View.INVISIBLE);
    }
}
