package model.piece;

// Concrete Factory for Sun
public class SunFactory implements PieceFactory {
    @Override
    public Piece createChessPiece(int position_X, int position_Y, pieceColor color) {
        return new Sun(position_X, position_Y, "Sun", color);
    }
}