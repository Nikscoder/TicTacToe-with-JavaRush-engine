package com.TicTacToeGame;

import com.javarush.engine.cell.*;
import com.javarush.engine.cell.Key;

public class TicTacToeGame extends Game {

    private final int[][] model; // board for game
    private int currentPlayer; // actual user
    private boolean isGameStopped;
    private int SCORE = 0;

    //Constructor to initialize vairables and methods with massives
    public TicTacToeGame(){
        model = new int[3][3];
    }

    public void initialize(){
        setScreenSize(3, 3);
        startGame();
        updateView();
    }

    public void startGame(){
        isGameStopped = false;
        currentPlayer=1;

        for (int i=0; i<model.length; i++) {
            for (int j = 0; j < model[i].length; j++) {
                model[i][j] = 0;
            }
        }
    }

    public void updateCellView(int x, int y, int value){
        if (value == 1){
            setCellValueEx(x,y,Color.WHITE, "X", Color.RED);
        }
        else if (value == 2){
            setCellValueEx(x,y,Color.WHITE, "O", Color.BLUE);
        }
        else {
            setCellValueEx(x,y,Color.WHITE, " ", Color.WHITE);
        }
    }

    public boolean hasEmptyCell(){
        for (int i = 0; i<model.length; i++){
            for (int j = 0; j< model.length; j++){
                if(model[i][j] == 0){
                    return true;
                }
            }
        }
        return false;
    }

    public void updateView(){
        for (int i = 0; i < model.length; i++){
            for (int j = 0; j < model[i].length; j++) {
                updateCellView(i,j, model[i][j]);
            }
        }
    }

    public void computerTurn() {
        // if the central point (1,1) is empty (equals 0) put 1
        if (model[1][1] == 0) {
            setSignAndCheck(1, 1);
            return;
        }
        // computer try to win (verify of the empty cells on the board)
        for (int i = 0; i < model.length; i++) {
            for (int j = 0; j < model[i].length; j++) {
                if (model[i][j] == 0 && checkFutureWin(i, j, currentPlayer)) {
                    setSignAndCheck(i, j);
                    return;
                }
            }
        }
        // prevent the winning of the user and move in this cell to protect.
        for (int i = 0; i < model.length; i++) {
            for (int j = 0; j < model[i].length; j++) {
                if (model[i][j] == 0 && checkFutureWin(i, j, 3-currentPlayer)) {
                    setSignAndCheck(i, j);
                    return;
                }
            }
        }
        // if no above options are vslid, then move to  empty cell on the board
        for (int i = 0; i < model.length; i++) {
            for (int j = 0; j < model[i].length; j++) {
                if (model[i][j] == 0) {
                    setSignAndCheck(i, j);
                    return;
                }
            }
        }
    }

    // checkFutureWin method to verify if by the next move we can  win
    public boolean checkFutureWin(int x, int y, int n){
        if (model[x][y] != 0) {
            return false;
        }
        int originalBoardState = model[x][y];
        // do move to check
        model[x][y] = n;
        //check the move
        boolean willWin = checkWin(x,y,n);
        // restore the original state of the board
        model[x][y] = originalBoardState;

        return willWin;

    }
    // method to verify if this field is empty, and we can move X in this cell
    public void onMouseLeftClick(int x, int y) {
        //showMessageDialog(Color.RED, "You"+ currentPlayer, Color.WHITE, 75);
        if (isGameStopped || model[x][y] != 0) {
            //showMessageDialog(Color.NONE, "Field isn't empty", Color.RED, 75);
            return;
        }
        setSignAndCheck(x,y);
        if(!isGameStopped){
            currentPlayer = 3 - currentPlayer; // 2 <--> 1
            computerTurn();
            if (!isGameStopped){
                currentPlayer = 3 - currentPlayer;
            }
        }
    }

    // method to do an update on the board
    public void setSignAndCheck (int x, int y){
        model[x][y] = currentPlayer;
        updateView(); // visible changed board after currentPlayer was moved.

        // check if we have the winner
        if (checkWin(x, y, currentPlayer)){
            isGameStopped = true;
            if (currentPlayer == 1) {
                showMessageDialog(Color.NONE, "You Win!", Color.GREEN, 75);
            }
            else {
                showMessageDialog(Color.NONE, "Game over!", Color.RED, 75);
            }
        }
        else if (!hasEmptyCell()) {
            isGameStopped = true;
            showMessageDialog(Color.NONE, " Draw!",  Color.BLUE, 75);
        }
    }

    public boolean checkWin(int x, int y, int n){
        if (model[x][0] == n && model[x][1] == n && model[x][2] == n)
            return true;
        if (model[0][y] == n && model[1][y] == n && model[2][y] == n)
            return true;
        if (model[0][0] == n && model[1][1] == n && model[2][2] == n)
            return true;
        if (model[0][2] == n && model[1][1] == n && model[2][0] == n)
            return true;
        return false;
    }

    // menu to start or finish the game
    public void onKeyPress(Key key) {
        if (key == Key.SPACE && isGameStopped || key == Key.ESCAPE) {
            SCORE++;
            setScore(SCORE);
            startGame();
            updateView();
        }
    }

}