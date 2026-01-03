package model.piece;

import model.ChessBoard;
import model.square.Square;

public class Point extends Piece {
    private boolean reachEnd = false;

    public Point(int pos_X, int pos_Y, String pieceType, pieceColor pieceColor) {
        super(pos_X, pos_Y, pieceType, pieceColor);
    }

    @Override
    public boolean isValidMove(ChessBoard chessBoard, Square fromSquare, Square toSquare) {
        boolean valid = false;
        int deltaX = toSquare.getX() - fromSquare.getX();
        int deltaY = toSquare.getY() - fromSquare.getY();

        // Check if the move is 1 or 2 steps forward or backward
        if (moveForward(deltaX, deltaY, fromSquare, toSquare)
                && ((getPieceColour().equals(pieceColor.YELLOW) && !reachEnd)
                        || (getPieceColour().equals(pieceColor.BLUE) && reachEnd))) {
        //since this point piece can move 2 steps so need to make sure it doesn't skip over other pieces, code originated from nazhan's plus code
            if (!isPathBlocked(chessBoard, fromSquare, toSquare)) {
                // Check if the destination square is empty or has a piece of the opposite color
                if (!toSquare.occupied() || toSquare.getPiece().getPieceColour() != getPieceColour()) {
                    // Perform the move

                    toSquare.setPiece(this);
                    if (toSquare.getX() == 0) {
                        pointReachEnd();
                    }
                    fromSquare.setPiece(null);
                    updatePosition(chessBoard, toSquare);
                    System.out.println(
                            "Point chess is moved from:" + fromSquare.getCoordinate() + " to:"
                                    + toSquare.getCoordinate());
                    valid = true;
                } else {
                    // Handle the case where the destination square is occupied by a piece of the
                    // same color
                    System.out.println("Invalid move: Destination square is occupied by a piece of the same color.");
                    valid = false;
                }
            }
        } else if (moveBackward(deltaX, deltaY, fromSquare, toSquare)
                && ((getPieceColour().equals(pieceColor.BLUE) && !reachEnd)
                        || (getPieceColour().equals(pieceColor.YELLOW) && reachEnd))) {

            if (!isPathBlocked(chessBoard, fromSquare, toSquare)) {
                // Check if the destination square is empty or has a piece of the opposite color
                if (!toSquare.occupied() || toSquare.getPiece().getPieceColour() != getPieceColour()) {
                    // Perform the move

                    toSquare.setPiece(this);

                    if (toSquare.getX() == 5) {
                        pointReachEnd();
                    }
                    fromSquare.setPiece(null);
                    updatePosition(chessBoard, toSquare);
                    System.out.println(
                            "Point chess is moved from:" + fromSquare.getCoordinate() + " to:"
                                    + toSquare.getCoordinate());
                    valid = true;

                } else {
                    valid = false;
                    // Handle the case where the move is not valid
                    throw new IllegalArgumentException("Invalid Point move");
                }
            }
        } else {
            System.out.println("Invalid Point move_");
        }

        return valid;
    }

    private boolean pointReachEnd() {
        if (reachEnd == false) {
            reachEnd = true;
        } else {
            reachEnd = false;
        }
        System.out.println("Reach end? " + reachEnd);
        return reachEnd;
    }

    private boolean moveForward(int deltaX, int deltaY, Square fromSquare, Square toSquare) {
        return (deltaX == -1 || deltaX == -2) && (getPosition_Y() == fromSquare.getY()) && deltaY == 0;
    }

    private boolean moveBackward(int deltaX, int deltaY, Square fromSquare, Square toSquare) {
        return (deltaX == 1 || deltaX == 2) && (getPosition_Y() == fromSquare.getY()) && deltaY == 0;
    }

    private void updatePosition(ChessBoard chessBoard, Square toSquare) {
        int newX = toSquare.getX();
        int newY = toSquare.getY();

        setPosition_X(newX);
        setPosition_Y(newY);
    }

    private boolean isPathBlocked(ChessBoard chessBoard, Square fromSquare, Square toSquare) {

        int direction = getDirection(fromSquare, toSquare);
        Square[][] squares = chessBoard.getSquares();
        boolean blocked = false;

        System.out.println("Direction: " + direction);
        if (direction == 0) {// up
            for (int i = fromSquare.getX() - 1; i > toSquare.getX(); i--) {
                if (squares[i][fromSquare.getY()].occupied()) {
                    blocked = true;
                }
            }
        } else if (direction == 2) {// down
            for (int i = fromSquare.getX() + 1; i < toSquare.getX(); i++) {
                if (squares[i][fromSquare.getY()].occupied()) {
                    blocked = true;
                }
            }
        }

        return blocked;

    }

    private int getDirection(Square fromSquare, Square toSquare) {

        int deltaX = toSquare.getX() - fromSquare.getX();
        int direction = 0;

        if (deltaX > 0) {// down
            direction = 2;
        } else if (deltaX < 0) {// up
            direction = 0;
        }
        return direction;
    }

}