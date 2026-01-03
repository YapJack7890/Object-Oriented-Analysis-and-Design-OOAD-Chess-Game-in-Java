package model.square;

import javax.swing.ImageIcon;

import model.piece.Piece;

public class Square {
    private int x, y, xy;
    private Piece pieceOnSquare;

    public Square(int x,int y) {
        this.x = x;
        this.y = y;
        this.xy = x*10 + y;
    }

    public void setPiece(Piece piece) {
        this.pieceOnSquare = piece;
    }

    public void removePiece() {
        this.pieceOnSquare = null;
    }

    public Piece getPiece(){
        return pieceOnSquare;
    }
    public int getY() {
        return y;
    }

    public int getX() {
        return x;
    }

    public int getCoordinate() {
        return xy;
    }

    public boolean occupied() {
        if(pieceOnSquare != null)
            return true;
        else
            return false;
    }
}