package com.mindrefactory.example.connect3;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.GridLayout;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    private int[][] sequences = {{0, 1, 2}, {3, 4, 5}, {6, 7, 8}, {0, 4, 8},
            {2, 4, 6}, {0, 3, 6}, {1, 4, 7}, {2, 5, 8}};
    private static final int[] imageIds = {
            R.id.imageView1, R.id.imageView2, R.id.imageView3,
            R.id.imageView4, R.id.imageView5, R.id.imageView6,
            R.id.imageView7, R.id.imageView8, R.id.imageView9
    };
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

    protected void play(final View view) {
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

    protected void resetGame(final View view) {
        for(int count = 0; count < imageIds.length; count++) {
            ((ImageView)findViewById(imageIds[count])).setImageResource(0);
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
