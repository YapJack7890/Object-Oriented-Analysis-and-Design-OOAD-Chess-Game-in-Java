package model.piece;

// Concrete Factory for Time
public class TimeFactory implements PieceFactory {
    @Override
    public Piece createChessPiece(int position_X, int position_Y, pieceColor color) {
        return new Time(position_X, position_Y, "Time", color);
    }
}