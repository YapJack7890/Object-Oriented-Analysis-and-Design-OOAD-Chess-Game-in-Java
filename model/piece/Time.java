package model.piece;

import model.ChessBoard;
import model.square.Square;


public class Time extends Piece{

    protected Time(int position_X, int position_Y, String pieceType, pieceColor pieceColor) {
        super(position_X, position_Y, pieceType, pieceColor);
        
    }

    @Override
    public boolean isValidMove(ChessBoard chessBoard, Square fromSquare, Square toSquare) {
        boolean valid = false;
        int deltaX = toSquare.getX() - fromSquare.getX();
        int deltaY = toSquare.getY() - fromSquare.getY();

        

        if (deltaX == deltaY || deltaX == -deltaY) {

            if(!isPathBlocked(chessBoard, fromSquare, toSquare)){
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
                    System.out.println("Time chess is moved from:" + fromSquare.getCoordinate() + " to:" + toSquare.getCoordinate());
                    valid = true;
                } else {
                    valid = false;
                    // Handle the case where the destination square is occupied by own piece
                    System.out.println("Invalid move: Destination square is occupied by own piece.");
                }
            } else {
                valid = false;
                System.out.println("Invalid move: Path is blocked.");
            }
        } else {
            valid = false;
            // Throw an IllegalArgumentException for an invalid move
            throw new IllegalArgumentException("Invalid move: Time piece moves in a diagonal line.");
        }

        return valid;
    }

    private boolean isPathBlocked(ChessBoard chessBoard, Square fromSquare, Square toSquare) {
        boolean blocked = false;
        int direction = getDirection(fromSquare, toSquare);

        Square[][] squares = chessBoard.getSquares();

        switch (direction) {
            case 0:
                int j = fromSquare.getY() - 1;
                for (int i = fromSquare.getX() - 1; i > toSquare.getX(); i--) {
                    if (squares[i][j].occupied()) {
                        blocked = true;
                        break;
                    }
                    j--;
                }
                break;
            case 1:
                j = fromSquare.getY() + 1;
                for (int i = fromSquare.getX() - 1; i > toSquare.getX(); i--) {
                    if (squares[i][j].occupied()) {
                        blocked = true;
                        break;
                    }
                    j++;
                }
                break;
            case 2:
                j = fromSquare.getY() + 1;
                for (int i = fromSquare.getX() + 1; i < toSquare.getX(); i++) {
                    if (squares[i][j].occupied()) {
                        blocked = true;
                        break;
                    }
                    j++;
                }
                break;
            case 3:
            j = fromSquare.getY() - 1;
                for (int i = fromSquare.getX() + 1; i < toSquare.getX(); i++) {
                    if (squares[i][j].occupied()) {
                        blocked = true;
                        break;
                    }
                    j--;
                }
                break;
        }
        return blocked;
    }

    private int getDirection(Square fromSquare, Square toSquare) {
        int direction = 0;

        int deltaX = toSquare.getX() - fromSquare.getX();
        int deltaY = toSquare.getY() - fromSquare.getY();

        if (deltaX < 0 && deltaY < 0){
            direction =  0;
        }
        else if(deltaX<0 && deltaY>0){
            direction = 1;
        }
        else if(deltaX>0 && deltaY>0){
            direction = 2;
        }
        else if(deltaX>0 && deltaY<0){
            direction = 3;
        }        
        return direction;
    }

    private boolean isOpponentPiece(Piece piece) {
        
        return piece != null && !piece.getPieceColour().equals(this.getPieceColour());
    }

}