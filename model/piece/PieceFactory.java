package model.piece;

// Factory Interface
public interface PieceFactory {
    Piece createChessPiece(int position_X, int position_Y, pieceColor color);
}