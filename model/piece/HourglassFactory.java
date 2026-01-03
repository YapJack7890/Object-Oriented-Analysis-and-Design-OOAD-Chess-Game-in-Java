package model.piece;

// Concrete Factory for Hourglass
public class HourglassFactory implements PieceFactory {
    @Override
    public Piece createChessPiece(int position_X, int position_Y, pieceColor color) {
        return new Hourglass(position_X, position_Y, "Hourglass", color);
    }
}
