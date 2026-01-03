package model.piece;

import model.ChessBoard;
import model.square.Square;

public class Hourglass extends Piece{

    protected Hourglass(int pos_X, int pos_Y, String pieceType, pieceColor pieceColor) {
        super(pos_X, pos_Y, pieceType, pieceColor);

    }

    @Override
    public boolean isValidMove(ChessBoard chessBoard, Square fromSquare, Square toSquare) {
        Boolean valid = false;
        int deltaX = Math.abs(toSquare.getX() - fromSquare.getX());
        int deltaY = Math.abs(toSquare.getY() - fromSquare.getY());

        System.out.println("Hourglass chess is moved from:" + fromSquare.getCoordinate() + " to:" + toSquare.getCoordinate());

        // Check if the move is in a 3x2 L shape in any orientation
        if ((deltaX == 2 && deltaY == 1) || (deltaX == 1 && deltaY == 2)) {
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
                valid = true;
            } else {
                // Handle the case where the destination square is occupied by own piece
                System.out.println("Invalid move: Destination square is occupied by own piece.");
                valid = false;
            }
        } else {
            // Throw an IllegalArgumentException for an invalid move
            throw new IllegalArgumentException("Invalid move: Hourglass piece moves in a 3x2 L shape.");
        }

        return valid;
    }

    private boolean isOpponentPiece(Piece piece) {
        // Check if the piece is not null and has a different color
        return piece != null && !piece.getPieceColour().equals(this.getPieceColour());
    }
}