module com.TicTacToeGame {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.TicTacToeGame to javafx.fxml;
    exports com.TicTacToeGame;
}