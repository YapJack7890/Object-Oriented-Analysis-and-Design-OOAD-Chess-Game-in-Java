package model.piece;

// Concrete Factory for Plus
public class PlusFactory implements PieceFactory {
    @Override
    public Piece createChessPiece(int position_X, int position_Y, pieceColor color) {
        return new Plus(position_X, position_Y, "Plus", color);
    }
}