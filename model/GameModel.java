package model;

import model.piece.HourglassFactory;
import model.piece.Piece;
import model.square.Square;
import model.piece.PlusFactory;
import model.piece.PointFactory;
import model.piece.SunFactory;
import model.piece.TimeFactory;
import model.piece.pieceColor;

import java.io.BufferedWriter;
import java.io.BufferedReader;
import java.io.FileWriter;
import java.io.FileReader;
import java.io.IOException;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.io.File;

/**
 * The gameModel contains the methods that help manages the game state,
 * provides methods for saving and loading games, handles player turns, and keeps track of the current player.
 * 
 * @Author: Yap Jack
 * @version 5.0
 * since 26-1-2024
 */

public class GameModel {
    private ChessBoard chessBoard; 
    private pieceColor currentPlayer;
    private PointFactory pointFactory = new PointFactory();
    private SunFactory sunFactory = new SunFactory();
    private HourglassFactory hourglassFactory = new HourglassFactory();
    private TimeFactory timeFactory = new TimeFactory();
    private PlusFactory plusFactory = new PlusFactory();
    private Square square;
    private pieceColor checkMateColor = null;
    private int counter = 0;
        
    // Constructor that initializes the chessboard
    //constructor contains the current player
    public GameModel() {
        this.chessBoard = ChessBoard.getInstance(); 
        this.currentPlayer = pieceColor.YELLOW;
    }

    // Function that starts the game
    public void start_Game(){
       currentPlayer = pieceColor.YELLOW;
       clearBoard();
       chessBoard.startBoard();
        get_Winner();
    }

    //Function that saves the game
    public void save_Game(){
        try {
            // Use a file chooser to get the file name and location from the user
            JFileChooser fileChooser = new JFileChooser();
            fileChooser.setDialogTitle("Save Game");
            fileChooser.setFileFilter(new FileNameExtensionFilter("Text Files (*.txt)", "txt"));
    
            int userChoice = fileChooser.showSaveDialog(null);
    
            if (userChoice == JFileChooser.APPROVE_OPTION) {
                File selectedFile = fileChooser.getSelectedFile();
                
                // Check if the selected file already exists
                if (selectedFile.exists()) {
                    int result = JOptionPane.showConfirmDialog(
                            null,
                            "The file already exists. Do you want to overwrite it?",
                            "File Overwrite Confirmation",
                            JOptionPane.YES_NO_OPTION
                    );
    
                    if (result != JOptionPane.YES_OPTION) {
                        // User chose not to overwrite the file
                        return;
                    }
                }
    
                String fileName = selectedFile.getAbsolutePath();
    
                // Check the file extension
                if (!fileName.toLowerCase().endsWith(".txt")) {
                    JOptionPane.showMessageDialog(null, "Invalid file type. Please select a .txt file.");
                }

                // Try block with resources to automatically close the BufferedWriter
                try (BufferedWriter writer = new BufferedWriter(new FileWriter(fileName))) {
                    // Save the current player's turn
                    writer.write("CurrentPlayer: " + currentPlayer + "\n");
                    //Save the counter
                    writer.write("Counter: " + getCounter() + "\n");
                    // Save the state of the chessboard
                    for (int row = 0; row < 6; row++) {
                        for (int col = 0; col < 7; col++) {
                            Square square = chessBoard.getSquareAt(col, row);
                            Piece piece = square.getPiece();
                            System.out.print("square: " + row + col + " ");
                            if (piece != null) {
                                String pieceString = piece.toString();
                                if (pieceString.contains("Point")){
                                    pieceColor color = piece.getPieceColour();
                                    System.out.println("Piece: Point");
                                    writer.write("Point" + "_" + color.toString() + "_" + row + "_" + col + " ");
                                }else if (pieceString.contains("Plus")) {
                                    pieceColor color = piece.getPieceColour();
                                    System.out.println("Piece: Plus");
                                    writer.write("Plus" + "_" + color.toString() + "_" + row + "_" + col + " ");
                                }else if (pieceString.contains("Sun")) {
                                    pieceColor color = piece.getPieceColour();
                                    System.out.println("Piece: Sun");
                                   writer.write("Sun" + "_" + color.toString() + "_" + row + "_" + col + " ");
                                }else if (pieceString.contains("Time")) {
                                    pieceColor color = piece.getPieceColour();
                                    System.out.println("Piece: Time");
                                    writer.write("Time" + "_" + color.toString() + "_" + row + "_" + col + " ");
                                }else if (pieceString.contains("Hourglass")) {
                                    pieceColor color = piece.getPieceColour();
                                    System.out.println("Piece: Hourglass");
                                    writer.write("Hourglass" + "_" + color.toString() + "_" + row + "_" + col + " ");
                                }
                            }
                        }
                    }
                }

                System.out.println("Game saved successfully.");
                JOptionPane.showMessageDialog(null, "Game saved successfully to: " + fileName);
             } else if (userChoice == JFileChooser.CANCEL_OPTION) {
                // User canceled the save operation
                JOptionPane.showMessageDialog(null, "Save operation canceled.");
            }
        } catch (IOException e) {
            System.err.println("Error saving game: " + e.getMessage());
            JOptionPane.showMessageDialog(null, "Error saving game: " + e.getMessage());
        }
    }

    //function that loads the saved game 
    public void load_Game() {
        // Use a file chooser to get the file name and location from the user
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setDialogTitle("Load Game");
        fileChooser.setFileFilter(new FileNameExtensionFilter("Text Files (*.txt)", "txt"));
    
        int userChoice = fileChooser.showOpenDialog(null);
    
        if (userChoice == JFileChooser.APPROVE_OPTION) {
            File selectedFile = fileChooser.getSelectedFile();
            
            // Check the file extension before proceeding
            if (selectedFile.getName().toLowerCase().endsWith(".txt")) {
                String fileName = selectedFile.getAbsolutePath();
    
                try (BufferedReader reader = new BufferedReader(new FileReader(fileName))) {
                    System.out.println(fileName + " is Loading.");
                // Read the current player's turn
                    String currentPlayerLine = reader.readLine();

                if (currentPlayerLine != null && currentPlayerLine.startsWith("CurrentPlayer: ")) {
                    String currentPlayerString = currentPlayerLine.substring("CurrentPlayer: ".length());
                    currentPlayer = pieceColor.valueOf(currentPlayerString);
                    System.out.println("Current player set.");
                }
                String CounterLine = reader.readLine();
                if (CounterLine != null && CounterLine.startsWith("Counter: ")) {
                    String CounterString = CounterLine.substring("Counter: ".length());
                    counter = Integer.valueOf(CounterString);
                    System.out.println("Counter: " + counter);
                    setCounter(counter);
                }

                // Clear the board before loading the new state
                clearBoard();

                // Read the state of the chessboard
                String rowLine;
                while ((rowLine = reader.readLine()) != null) {
                    String[] pieces = rowLine.split(" ");
                    for (int col = 0; col < pieces.length; col++) {
                        String[] pieceInfo = pieces[col].split("_");
                        String pieceType = pieceInfo[0];
                        String Colour = pieceInfo[1];
                        String Row = pieceInfo[2];
                        String Col = pieceInfo[3];
                        int rowNum = Integer.valueOf(Row);
                        int colNum = Integer.valueOf(Col);
                        pieceColor PieceColor = pieceColor.valueOf(Colour);
                        square = chessBoard.getSquareAt(colNum, rowNum);
                        System.out.println(pieceType + Colour + Row + Col);
                        // Create and set the piece on the chessboard
                        switch (pieceType) {
                            case "Point":
                                square.setPiece(pointFactory.createChessPiece(rowNum, colNum, PieceColor));
                                break;
                            case "Sun":
                                square.setPiece(sunFactory.createChessPiece(rowNum, colNum, PieceColor));
                                break;
                            case "Hourglass":
                                square.setPiece(hourglassFactory.createChessPiece(rowNum, colNum, PieceColor));
                                break;
                            case "Time":
                                square.setPiece(timeFactory.createChessPiece(rowNum, colNum, PieceColor));
                                break;
                            case "Plus":
                                square.setPiece(plusFactory.createChessPiece(rowNum, colNum, PieceColor));
                                break;
                            default:
                                // Handle unknown piece type
                                break;
                        }
                    }
                }
                    JOptionPane.showMessageDialog(null, "Game loaded successfully from: " + fileName);
                    System.out.println("Game loaded successfully from: " + fileName);
                } catch (IOException e) {
                    System.err.println("Error loading game: " + e.getMessage());
                    JOptionPane.showMessageDialog(null, "Error loading game: " + e.getMessage());
                }
            } else {
                // Display an error message if the selected file has an invalid extension
                JOptionPane.showMessageDialog(null, "Invalid file type. Please select a .txt file.");
            }
        } else if (userChoice == JFileChooser.CANCEL_OPTION) {
            // User canceled the load operation
            JOptionPane.showMessageDialog(null, "Load operation canceled.");
        }
    }

    //function to print out resigning player 
    public void resigning(){
        System.out.println(currentPlayer + " resigns");
    }

    //function to print out draw 
    public void draw(){
        System.out.println("It is a draw");
    }

    //function to get current player
    public pieceColor get_CurrentPlayer(){
        return currentPlayer;
    }

    //function to switch player
    public void switchPlayer(){
        if(currentPlayer == pieceColor.BLUE){
            currentPlayer = pieceColor.YELLOW;
        } else {
            currentPlayer = pieceColor.BLUE;
        }
    }

    //function to get player 
    public void get_Winner(){
        if(checkMateColor != null){
            if(checkMateColor == pieceColor.BLUE){
                System.out.println("Blue is the winner");
            } else{
                System.out.println("Yellow is the winner");
            }
        }
    }

    //function to remove all pieces from the board 
    public void clearBoard() {
        for (int row = 0; row < 6; row++) {
            for (int col = 0; col < 7; col++) {
                Square square = chessBoard.getSquareAt(col, row);
                square.removePiece();
            }
        } 
    }

    // Get a piece based on its position
    public Piece getPiece(int positionX, int positionY) {
        if (isValidPosition(positionX, positionY)) {
            return chessBoard.getSquareAt(positionX, positionY).getPiece();
        } else {
            // Handle invalid position
            return null;
        }
    }

    // Check if the position is within the valid chessboard bounds
    private boolean isValidPosition(int positionX, int positionY) {
        return positionX >= 0 && positionX < 6 && positionY >= 0 && positionY < 7;
    }

    //function to set counter
    public void setCounter(int counter){
        this.counter = counter;
    }

    //function to increase counter by 1
    public int addCounter(){
        return counter + 1;
    }

    //function to get counter
    public int getCounter(){
        return counter;
    }
}