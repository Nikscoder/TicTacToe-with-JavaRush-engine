package com.TicTacToeGame;

import com.javarush.engine.cell.*;
import com.javarush.engine.cell.Key;

public class TicTacToeGame extends Game {

    private final int[][] model; // board for game
    private int currentPlayer; // actual user
    private boolean isGameStopped;

    //Constructor to initialize vairables and methods with massives
    public TicTacToeGame(){
        model = new int[3][3];
        //currentPlayer = 1;
    }

    public void startGame(){
        isGameStopped = false;
        currentPlayer=1;
        for (int i=0; i<model.length; i++){
            for (int j = 0; j<model[i].length; j++){
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

    public void computerTurn(){
        // if the central point (1,1) is empty (equeals 0) put 1
        if (model[1][1] == 0){
            setSignAndCheck(1,1);
            return;
        }
        for (int i = 0; i< model.length;i++){ //3
            for (int j = 0; j < model[i].length; j++){
                if (model[i][j] == 0){
                    setSignAndCheck(i,j);
                    return;
                }
            }
        }
    }
    // method to verify if this field is empty and we can paste X or O in this field
    public void onMouseLeftClick(int x, int y) {
        //showMessageDialog(Color.RED, "You"+ currentPlayer, Color.WHITE, 75);
        if (isGameStopped || model[x][y] != 0) {
            //showMessageDialog(Color.NONE, "Field isn't empty", Color.RED, 75);
            return;
        }
        setSignAndCheck(x,y);
        computerTurn();
        //currentPlayer = 3 - currentPlayer;
    }

    // method to do an update on the board
    public void setSignAndCheck (int x, int y){
        model[x][y] = currentPlayer;
        updateView(); // visible changed board after currentPlayer was done.

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
        else if (checkWin(x,y,currentPlayer) == false && hasEmptyCell() == false) {
            showMessageDialog(Color.NONE, " Draw!",  Color.BLUE, 75);
            isGameStopped = true;
        }
        currentPlayer = 3 - currentPlayer;
    }

    public boolean checkWin(int x, int y, int n){
        boolean winHorizontal = true;
        for (int j = 0; j<model[x].length;j++){
            if(model[x][j] != n){
                winHorizontal = false;
                break;
            }
        }
        boolean winVertical = true;
        for (int i = 0; i<model.length;i++){
            if (model[i][y] != n){
                winVertical = false;
                break;
            }
        }
        boolean winMainDiagonal = true;
        // 0,0 -> 1,1 -> 2,2
        for (int i = 0; i < model.length; i++) {
            if(model[i][i] != n) {
                winMainDiagonal = false;
                break;
            }
        }
        boolean winAntiDiagonal = true;
        // 0,2 -> 1,1 ->2,0
        //model.length = 3
        for (int i = 0; i < model.length; i++) {
            if (model[i][model.length-1-i] != n){
                winAntiDiagonal = false;
                break;
            }
        }
        if (winHorizontal || winVertical || winMainDiagonal || winAntiDiagonal){
            isGameStopped = true;
            return true;
        }
        return false;
    }

    // menu to start or finish the game
    public void onKeyPress(Key key) {
        if (key == Key.SPACE && isGameStopped == true || key == Key.ESCAPE) {
            startGame();
            updateView();
        }
    }

    public void initialize(){
        setScreenSize(3, 3);
        startGame();
        updateView();
    }
}