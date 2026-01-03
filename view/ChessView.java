package view;

import controller.ChessController;
import model.ChessBoard;
import model.square.Square;
import model.piece.Plus;
import model.piece.Time;
import model.piece.Piece;

import java.awt.*;
import javax.swing.*;
import java.awt.image.BufferedImage;
import java.awt.geom.AffineTransform;


public class ChessView extends JFrame {
    private JPanel chessBoardPanel;
    private ChessBoard chessBoard;
    private ChessController chessController;
    private boolean flipped = false;
    public JButton newGameButton;
    public JButton loadGameButton;
    public JButton saveGameButton;
    public JButton resignButton;
    public JButton drawButton;

    // constructor
    public ChessView(ChessBoard chessboard) {
        super("Talabia Chess");
        this.chessBoard = chessboard;
        chessController = new ChessController(this, chessboard);
        initializeGUI(chessboard);
    }

    // GUI initialization
    private void initializeGUI(ChessBoard chessboard) {
        setLayout(new BorderLayout());

        JPanel toolbar = new JPanel(new FlowLayout(FlowLayout.LEFT));
        add(toolbar, BorderLayout.NORTH);

        JPanel toolbar2 = new JPanel(new GridLayout(8, 2));
        add(toolbar2, BorderLayout.EAST);

        newGameButton = createButton("Start Game");
        loadGameButton = createButton("Load Game");
        saveGameButton = createButton("Save Game");
        resignButton = createButton("Resign");
        drawButton = createButton("Request Draw");

        toolbar.add(newGameButton);
        toolbar.add(loadGameButton);
        toolbar.add(saveGameButton);
        toolbar2.add(resignButton);
        toolbar2.add(drawButton);

        chessBoardPanel = createChessboardPanel(chessboard);
        add(chessBoardPanel, BorderLayout.CENTER);

        setResizable(true); // Allow window resizing
        setSize(500, 500);
        setVisible(true);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    // buttons
    private JButton createButton(String label) {
        JButton button = new JButton(label);
        button.addActionListener(chessController);

        return button;
    }

    // create chess board
    private JPanel createChessboardPanel(ChessBoard chessboard) {
        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(6, 7));

        Square[][] squares = chessboard.getSquares();

        for (int y = 0; y < 6; y++) {
            for (int x = 0; x < 7; x++) {

                JPanel squarePanel = createSquare(squares[y][x]);
                squarePanel.setName(Integer.toString(y) + Integer.toString(x));

                panel.add(squarePanel);
            }
        }

        return panel;
    }

//the squares
private JPanel createSquare(Square square) {
    JPanel squarePanel = new JPanel();
    squarePanel.setPreferredSize(new Dimension(60, 60));
    if ((square.getX() + square.getY()) % 2 == 0) {
        squarePanel.setBackground(Color.WHITE);
    } else {
        squarePanel.setBackground(Color.BLACK);
    }

    squarePanel.addMouseListener(chessController);

    return squarePanel;
}
    

    //update the plus and time
    public void updatePlusTime() {
        Component[] components = chessBoardPanel.getComponents();
        JPanel[] panels = new JPanel[components.length];

        for (int i = 0; i < components.length; i++) {
            panels[i] = (JPanel) components[i];
        }

        JLabel icon;
        Square sq = null;
        for (int i = 0; i < 6; i++) {
            for (int j = 0; j < 7; j++) { 
                //seaching for squares with time / plus
                if (chessBoard.getSquareAt(j, i).getPiece() instanceof Time || chessBoard.getSquareAt(j, i).getPiece() instanceof Plus)
                {
                    sq = chessBoard.getSquareAt(j, i);
                    // get the corresponding panel
                    for (int k = panels.length - 1; k > -1; k--) {
                        if ((Integer.toString(sq.getX()) + Integer.toString(sq.getY())).equals(panels[k].getName())) {
                            icon = (JLabel)panels[k].getComponent(0);
                            icon.setIcon(resizeIcon(chessBoard.getSquareAt(j, i).getPiece()));
                            panels[k].revalidate();
                            panels[k].repaint();
                        }
                    }
                    

                }
                
            }
        }

    }

 
    // use to update the chess position when called
    public void updateChessPosition(String startPaneName, String endPaneName, Piece selectedPiece) {
        // iterating through the components to search for the Panes
        Component[] components = chessBoardPanel.getComponents();
        JPanel sourcePane = null;
        JPanel destinationPane = null;

        for (Component component : components) {
            // search for the source Pane
            if (component instanceof JPanel && startPaneName.equals(((JPanel) component).getName())) {
                sourcePane = (JPanel) component;
            }
            // search for the destination Pane
            if (component instanceof JPanel && endPaneName.equals(((JPanel) component).getName())) {
                destinationPane = (JPanel) component;
            }

        }

        // clearing out the source Pane's image
        JLabel sourceLabel = (JLabel) sourcePane.getComponent(0);
        sourceLabel.setIcon(null);
        sourcePane.revalidate();
        sourcePane.repaint();

        JLabel destinationLabel;
        /* because we didn't add JLabel in all square Pane when creating so this will
         add the JLabel if it doesn't have*/
        if (destinationPane.getComponentCount() == 0) {
            destinationLabel = new JLabel();
            destinationPane.add(destinationLabel);

        } else {
            destinationLabel = (JLabel) destinationPane.getComponent(0);
        }

        // setting the destination Pane's image
        destinationLabel.setIcon(resizeIcon(selectedPiece));
        destinationPane.revalidate();
        destinationPane.repaint();

    }

    // resize the image
    private ImageIcon resizeIcon(Piece piece) {
        ImageIcon pieceIcon = null;
        ImageIcon originalIcon = piece.getImageIcon();
        Image originalImage = originalIcon.getImage();

        // resize image
        Image resizedImage = originalImage.getScaledInstance(60, 60, Image.SCALE_SMOOTH);
        pieceIcon = new ImageIcon(resizedImage);
        return pieceIcon;
    }

    public void flipBoard() {
        Component[] components = chessBoardPanel.getComponents();
        JPanel[] panels = new JPanel[components.length];
    
        for (int i = 0; i < components.length; i++) {
            panels[i] = (JPanel) components[i];
        }
    
        chessBoardPanel.removeAll();
    
        for (int i = panels.length - 1; i >= 0; i--) {
            JPanel panel = panels[i];
            flipSquarePanel(panel);  // Flip the individual square panel
            chessBoardPanel.add(panel);
        }

        isFlip();
        chessBoardPanel.revalidate();
        chessBoardPanel.repaint();
    }
    
    // Helper method to flip an individual square panel
    private void flipSquarePanel(JPanel squarePanel) {
        Component[] components = squarePanel.getComponents();

        for (Component component : components) {
            if (component instanceof JLabel) {
                JLabel icon = (JLabel) component;
                rotateIcon180Degrees(icon);
            }
        }  

        squarePanel.revalidate();
        squarePanel.repaint();
    }

    // Helper method to rotate an ImageIcon by 180 degrees
    private void rotateIcon180Degrees(JLabel icon) {
        Icon currentIcon = icon.getIcon();
        if (currentIcon instanceof ImageIcon) {
            ImageIcon originalIcon = (ImageIcon) currentIcon;
            Image originalImage = originalIcon.getImage();

            // Rotate image by 180 degrees
            AffineTransform transform = new AffineTransform();
            transform.rotate(Math.PI, originalImage.getWidth(null) / 2.0, originalImage.getHeight(null) / 2.0);
            BufferedImage rotatedImage = new BufferedImage(originalImage.getWidth(null), originalImage.getHeight(null), BufferedImage.TYPE_INT_ARGB);
            Graphics2D g = rotatedImage.createGraphics();
            g.drawImage(originalImage, transform, null);
            g.dispose();

            icon.setIcon(new ImageIcon(rotatedImage));
        }
    }

    public void updateView() {
        Component[] components = chessBoardPanel.getComponents();
        JPanel[] chessPanel = new JPanel[components.length];
        JLabel chessLabel = null;
        int index = 0;
        if (flipped == true) {
            index = 41;
        }

        for (int i = 0; i < 6; i++) {
            for (int j = 0; j < 7; j++) {
                if (components[index] instanceof JPanel) {
                    chessPanel[index] = (JPanel) components[index];
                    
                    if (chessPanel[index].getComponentCount() != 0 
                        && chessPanel[index].getComponent(0) instanceof JLabel) {
                            chessLabel = (JLabel) chessPanel[index].getComponent(0);
                            chessLabel.setIcon(null);
                    } else {
                        chessLabel = new JLabel();
                        chessPanel[index].add(chessLabel);
                    }
                }

                if (chessBoard.getSquareAt(j, i).getPiece() != null) {
                    chessLabel.setIcon(resizeIcon(chessBoard.getSquareAt(j, i).getPiece()));
                    chessPanel[index].add(chessLabel);
                }
                if (flipped == true) {
                    index --;
                } else {
                    index ++;
                }
                
            }
        }

        chessBoardPanel.revalidate();
        chessBoardPanel.repaint();
    
    }

    public void resetFlip() {
        if (flipped == true) {
            flipBoard();
        }
    }

    private boolean isFlip() {
        if (flipped == true) {
            flipped = false;
        } else {
            flipped = true;
        }

        return flipped;
    }

}