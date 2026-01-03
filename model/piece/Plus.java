package model.piece;

import model.ChessBoard;
import model.square.Square;


public class Plus extends Piece{

    protected Plus(int position_X, int position_Y, String pieceType, pieceColor pieceColor) {
        super(position_X, position_Y, pieceType, pieceColor);
        
    }

    @Override
    public boolean isValidMove(ChessBoard chessBoard, Square fromSquare, Square toSquare) {
        boolean valid = false;
        int deltaX = toSquare.getX() - fromSquare.getX();
        int deltaY = toSquare.getY() - fromSquare.getY();

    if ((deltaX == 0 && deltaY != 0) || (deltaX != 0 && deltaY == 0)) {
            // Check if the destination square is empty or occupied by an opponent's piece

            if(!isPathBlocked(chessBoard, fromSquare, toSquare))
                if (!toSquare.occupied() || isOpponentPiece(toSquare.getPiece()) ) {

                    //check if its Sun
                    if (toSquare.getPiece() instanceof Sun) {
                        System.out.println("Sun is captured!");
                        //need to call endgame here
                    }
                
                    // Perform the move
                    toSquare.setPiece(this);
                    fromSquare.setPiece(null);
                
                    System.out.println("Plus chess is moved from:" + fromSquare.getCoordinate() + " to:" + toSquare.getCoordinate());
                    valid = true;
                } else {
                    // Handle the case where the destination square is occupied by own piece
                    System.out.println("Invalid move: Destination square is occupied by own piece.");
                    valid = false;
                }
            else{
                valid = false;
                System.out.println("Invalid move: Path is blocked.");
            }
        
        } else {
            valid = false;
            // Throw an IllegalArgumentException for an invalid move
            throw new IllegalArgumentException("Invalid move: Plus piece moves in a straight line.");
        }
        return valid;
    }

    private boolean isPathBlocked(ChessBoard chessBoard, Square fromSquare, Square toSquare) {

        int direction = getDirection(fromSquare, toSquare);
        Square[][] squares = chessBoard.getSquares();
        boolean blocked = false;

        //return recurseCheck(chessBoard, fromSquare, toSquare, direction, fromSquare.getX(), fromSquare.getY(), 0);
        System.out.println("Direction: " + direction);
        if(direction == 0){//up
            for(int i = fromSquare.getX()- 1; i > toSquare.getX(); i--){
                if(squares[i][fromSquare.getY()].occupied()){
                    blocked = true;
                }
            }
        }
        else if(direction == 1){//right
            for(int i = fromSquare.getY() + 1; i < toSquare.getY(); i++){
                if(squares[fromSquare.getX()][i].occupied()){
                    blocked = true;
                }
            }
        }
        else if(direction == 2){//down
            for(int i = fromSquare.getX()+1; i < toSquare.getX(); i++){
                if(squares[i][fromSquare.getY()].occupied()){
                    blocked = true;
                }
            }
        }
        else if(direction == 3){//left
            for(int i = fromSquare.getY() - 1; i > toSquare.getY(); i--){
                if(squares[fromSquare.getX()][i].occupied()){
                    blocked = true;
                }
            }
        }
        
        return blocked;
        
    }
    

    private int getDirection(Square fromSquare, Square toSquare) {

        int deltaX = toSquare.getX() - fromSquare.getX();
        int deltaY = toSquare.getY() - fromSquare.getY();
        int direction = 0;

        if (deltaX > 0) {//down
            direction = 2;
        } else if (deltaX < 0) {//up
            direction = 0;
        }
        else if (deltaY > 0) {//left
            direction = 1;
        } else if (deltaY < 0) {//right
            direction = 3;
        }
        return direction;
    }

    

    private boolean isOpponentPiece(Piece piece) {
        return piece != null && !piece.getPieceColour().equals(this.getPieceColour());
    }

}