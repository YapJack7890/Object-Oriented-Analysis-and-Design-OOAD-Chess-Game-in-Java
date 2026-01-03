package model.piece;


import javax.swing.ImageIcon;

import model.ChessBoard;
import model.square.Square;

public abstract class Piece {
    
    private String pieceType;
    private pieceColor pieceColour;
    private String color;
    private ImageIcon piece_Icon;
    private int position_X;
    private int position_Y;


    protected Piece(int position_X, int position_Y, String pieceType, pieceColor pieceColor){
        this.position_X = position_X;
        this.position_Y = position_Y;
        this.pieceType = pieceType;
        this.pieceColour = pieceColor;
        switch(pieceColor){
            case YELLOW:
                color = "yellow";
                break;
             
            case BLUE:
                color = "blue";
                break;    
        }
        piece_Icon = new ImageIcon("model/lib/" + color + "_" + pieceType + ".png");
    
    }

    public ImageIcon getImageIcon() {
        return piece_Icon;
    }

    public String getPieceType() {
        return pieceType;
    }

    public pieceColor getPieceColour() {
        return pieceColour;
    }

    public int getPosition_X() {
        return position_X;
    }

    public int getPosition_Y() {
        return position_Y;
    }
    
    protected void setIcon(ImageIcon piece_Icon) {
        this.piece_Icon = piece_Icon;
    }

    protected void setPieceColour(pieceColor pieceColour) {
        this.pieceColour = pieceColour;
    }

    protected void setPieceType(String pieceType) {
        this.pieceType = pieceType;
    }

    protected void setPosition_X(int position_X) {
        this.position_X = position_X;
    }

    protected void setPosition_Y(int position_Y) {
        this.position_Y = position_Y;
    }


    //implements method at later time
    public abstract boolean isValidMove(ChessBoard chessBoard, Square fromSquare, Square toSquare);



}