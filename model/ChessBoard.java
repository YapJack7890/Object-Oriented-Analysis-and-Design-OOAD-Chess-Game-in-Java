package model;

import model.square.Square;
import model.piece.HourglassFactory;
import model.piece.Piece;
import model.piece.Sun;
import model.piece.SunFactory;
import model.piece.TimeFactory;
import model.piece.pieceColor;
import model.piece.PointFactory;
import model.piece.PlusFactory;

import java.awt.*;
import javax.swing.*;
import java.awt.event.*;

public class ChessBoard {
    private Square[][] squares = new Square[6][7];
    private int positionX;
    private int positionY;
    private static ChessBoard instance;
    private PointFactory pointFactory = new PointFactory();
    private SunFactory sunFactory = new SunFactory();
    private HourglassFactory hourglassFactory = new HourglassFactory();
    private TimeFactory timeFactory = new TimeFactory();
    private PlusFactory plusFactory = new PlusFactory();
    private boolean sunIsCaptured = false;
    
    public ChessBoard() {
        initializeSquares();
    }

    //Singleton
    public static ChessBoard getInstance() {
        if (instance == null) {
            synchronized (ChessBoard.class) {
                if (instance == null) {
                    instance = new ChessBoard(); //create a singleton instance
                }
            }
        }
        return instance; //return the instance
        
    }
    private void initializeSquares() {
        for (int row = 0; row < 6; row++) {
            for (int col = 0; col < 7; col++) {
                squares[row][col] = new Square(row, col);
            }
        }
    }

    public Square[][] getSquares() {
        return squares;
    }

    public int getPositionX() {
        return positionX;
    }

    public int getPositionY() {
        return positionY;
    }

    public Square getSquareAt(int x, int y) {
        return squares[y][x];
    }

    //swap plus and time
    public void swap_PlusAndTimes(){
        //swap plus and times
        for (int row = 0; row < 6; row++) {
            for (int col = 0; col < 7; col++) {
                if(squares[row][col].getPiece() != null){
                    if(squares[row][col].getPiece().getPieceType() == "Plus"){
                        if(squares[row][col].getPiece().getPieceColour() == pieceColor.BLUE){
                            squares[row][col].setPiece(timeFactory.createChessPiece(col, row, pieceColor.BLUE));
                        }
                        else if(squares[row][col].getPiece().getPieceColour() == pieceColor.YELLOW){
                            squares[row][col].setPiece(timeFactory.createChessPiece(col, row, pieceColor.YELLOW));
                        }
                        
                    }
                    else if(squares[row][col].getPiece().getPieceType() == "Time"){
                        if(squares[row][col].getPiece().getPieceColour() == pieceColor.BLUE){
                            squares[row][col].setPiece(plusFactory.createChessPiece(col, row, pieceColor.BLUE));
                        }
                        else if(squares[row][col].getPiece().getPieceColour() == pieceColor.YELLOW){
                            squares[row][col].setPiece(plusFactory.createChessPiece(col, row, pieceColor.YELLOW));
                        }
                    }
                }
            }
        }
    }

    public void startBoard() {
        for (int row = 0; row < 6; row++) {
            for (int col = 0; col < 7; col++) {
            //blue side
             if (row == 0) {
                if (col == 0 || col == 6) {
                    squares[row][col].setPiece(plusFactory.createChessPiece(row, col, pieceColor.BLUE));
                } 
                else if (col == 1 || col == 5) {
                    squares[row][col].setPiece(hourglassFactory.createChessPiece(row, col, pieceColor.BLUE));
                } 
                else if (col == 2 || col == 4) {
                    squares[row][col].setPiece(timeFactory.createChessPiece(row, col, pieceColor.BLUE));
                } 
                else {
                    squares[row][col].setPiece(sunFactory.createChessPiece(row, col, pieceColor.BLUE));
                }

            }

            if (row == 1) {
                    squares[row][col].setPiece(pointFactory.createChessPiece(row, col, pieceColor.BLUE));
            }


            //yellow side
            if (row == 5) {
                if (col == 0 || col == 6) {
                    squares[row][col].setPiece(plusFactory.createChessPiece(row, col, pieceColor.YELLOW));
                } 
                else if (col == 1 || col == 5) {
                    squares[row][col].setPiece(hourglassFactory.createChessPiece(row, col, pieceColor.YELLOW));
                } 
                else if (col == 2 || col == 4) {
                    squares[row][col].setPiece(timeFactory.createChessPiece(row, col, pieceColor.YELLOW));
                } 
                else {
                    squares[row][col].setPiece(sunFactory.createChessPiece(row, col, pieceColor.YELLOW));
                }
            }

            if (row == 4) {
                squares[row][col].setPiece(pointFactory.createChessPiece(row, col, pieceColor.YELLOW));
            }
        }   
    }
    }

    public void resetCheckmateflag() {
        this.sunIsCaptured = false;
    }

    public boolean isCheckmate() {
        int noOfSun = 0;
        for (int row = 0; row < 6; row++) {
            for (int col = 0; col < 7; col++) {
                if(squares[row][col].getPiece() != null){
                    if(squares[row][col].getPiece() instanceof Sun){
                        noOfSun ++;
                    }
                }

            }

            if (noOfSun > 1) {
                sunIsCaptured = false;
                break;
            } else {
                sunIsCaptured = true;
            }
        
        }
        return sunIsCaptured;
    }

}