package model.piece;

// Concrete Factory for Point
public class PointFactory implements PieceFactory {
    @Override
    public Piece createChessPiece(int position_X, int position_Y, pieceColor color) {
        return new Point(position_X, position_Y, "Point", color);
    }
}