package model.piece;

import model.ChessBoard;
import model.square.Square;


public class Sun extends Piece{
    public Sun(int pos_X, int pos_Y, String pieceType, pieceColor pieceColor) {
        super(pos_X, pos_Y, pieceType, pieceColor);
    }

    @Override
    public boolean isValidMove(ChessBoard chessBoard, Square fromSquare, Square toSquare) {
        Boolean valid = false;
        int deltaX = Math.abs(toSquare.getX() - fromSquare.getX());
        int deltaY = Math.abs(toSquare.getY() - fromSquare.getY());

        

        // Check if the move is only one step in any direction
        if ((deltaX == 1 && deltaY == 0) || (deltaX == 0 && deltaY == 1) || (deltaX == 1 && deltaY == 1)) {
            // Check if the destination square is empty or occupied by an opponent's piece
            if (!toSquare.occupied() || isOpponentPiece(toSquare.getPiece())) {

                //check if its Sun
                if (toSquare.getPiece() instanceof Sun) {
                    System.out.println("Sun is captured!");
                    //need to call endgame here
                }

                // Perform the move
                toSquare.setPiece(this);
                fromSquare.setPiece(null);
                System.out.println("Sun chess is moved from:" + fromSquare.getCoordinate() + " to:" + toSquare.getCoordinate());
                valid = true;

            } else {
                // Handle the case where the destination square is occupied by own piece
                System.out.println("Invalid move: Destination square is occupied by own piece.");
                valid = false;
            }
        } else {
            valid = false;
            // Handle the case where the move is not in a valid Sun shape
            System.out.println("Invalid move: Sun piece can move only one step in any direction.");
            
        }

        return valid;
    }

    private boolean isOpponentPiece(Piece piece) {
        // Check if the piece is not null and has a different color
        return piece != null && !piece.getPieceColour().equals(this.getPieceColour());
    }
}