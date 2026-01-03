

import model.ChessBoard;
import controller.ChessController;
import view.ChessView;
import model.GameModel;
import model.square.Square;


public class Game {
    public static void main(String[] args) {
        ChessBoard chessBoard = ChessBoard.getInstance();
        GameModel gameModel = new GameModel();
        ChessView chessView = new ChessView(chessBoard);//SwingChessView(); // Or JavaFXChessView
        int row = 7, col = 6;
    }
}