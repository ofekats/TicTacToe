import java.awt.*;
import java.awt.event.*;
import javax.swing.*;


public class TicTacToe {
    int boardWidth = 600;
    int boardHeight = 700;

    JFrame frame = new JFrame("Tic-Toc-Toe");
    JLabel textLabel = new JLabel();
    JPanel textPanel = new JPanel();
    JPanel boardPanel = new JPanel();

    JLabel winLabel = new JLabel();
    JPanel winPanel = new JPanel();

    JButton[][] board = new JButton[3][3];
    JButton playAgain = new JButton();
    JPanel playAgainPanel = new JPanel();
    String playerX = "X";
    String playerO = "O";
    String currentPlayer =  playerX;
    String winner = "";
    int Xwin = 0;
    int Owin = 0;

    boolean gameOver = false;
    int turns = 0;

    Color purple = new Color(204, 153, 255);
    Color gray = new Color(224, 224, 224);
    Color ogawa = new Color(30 , 165, 192);

    TicTacToe(){
        frame.setVisible(true);
        frame.setSize(boardWidth, boardHeight);
        frame.setLocationRelativeTo(null);
        frame.setResizable(false);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(new BorderLayout());

        
        textLabel.setBackground(purple);
        textLabel.setForeground(gray);
        textLabel.setFont(new Font("Ariel", Font.BOLD, 50));
        textLabel.setHorizontalAlignment(JLabel.CENTER);
        textLabel.setText("Tic-Toc-Toe");
        textLabel.setOpaque(true);

        textPanel.setLayout(new BorderLayout());
        textPanel.add(textLabel);
        frame.add(textPanel, BorderLayout.NORTH);

        winLabel.setBackground(gray);
        winLabel.setForeground(purple);
        winLabel.setFont(new Font("Ariel", Font.CENTER_BASELINE, 30));
        // winLabel.setHorizontalAlignment(JLabel.CENTER);
        winLabel.setText("X: " + Xwin + ", O: " + Owin);
        winLabel.setOpaque(true);

        winPanel.setLayout(new BorderLayout());
        winPanel.add(winLabel);

        boardPanel.setLayout(new GridLayout(3, 3));
        boardPanel.setBackground(Color.white);
        frame.add(boardPanel);

        playAgainPanel.setLayout(new BorderLayout());
        playAgainPanel.add(playAgain);

        // Add winPanel and playAgainPanel to a new panel using FlowLayout
        JPanel bottomPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        bottomPanel.setBackground(gray);
        bottomPanel.add(winPanel);
        bottomPanel.add(playAgainPanel);
        frame.add(bottomPanel, BorderLayout.SOUTH);

        playAgain.setBackground(ogawa);
        playAgain.setForeground(Color.white);
        playAgain.setFont(new Font("Ariel", Font.BOLD, 20));
        playAgain.setFocusable(false);
        playAgain.setText("Press to play again");
        playAgain.setVisible(false);
        playAgain.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e){
                cleanBourd();
                gameOver = false;
                currentPlayer = winner == playerX ? playerX : playerO;
                textLabel.setText(currentPlayer + "'s turn!");
                turns = 0;
                textLabel.setBackground(purple);
                textLabel.setForeground(gray);
            }
            
        });

        for (int row = 0; row < 3; ++row)
        {
            for(int col = 0; col < 3; ++col)
            {
                JButton tile = new JButton();
                board[row][col] = tile;
                boardPanel.add(tile);

                tile.setBackground(gray);
                tile.setForeground(purple);
                tile.setFont(new Font("Ariel", Font.BOLD, 120));
                tile.setFocusable(false);

                tile.addActionListener(new ActionListener() {
                    public void actionPerformed(ActionEvent e){
                        if(gameOver){
                            return;
                        }
                        JButton tile = (JButton) e.getSource();
                        if(tile.getText() == ""){
                            tile.setText(currentPlayer);
                            ++turns;
                            checkWinner();
                            if(!gameOver){
                                currentPlayer = currentPlayer == playerX ? playerO : playerX;
                                textLabel.setText(currentPlayer + "'s turn!");
                            }
                            else{
                                playAgain.setVisible(true);
                                if(winner == playerX){
                                    ++Xwin;
                                }
                                else if(winner == playerO){
                                    ++Owin;
                                }
                                winLabel.setText("X: " + Xwin + ", O: " + Owin);
                            }
                            
                        }
                        
                    }
                    
                });
            }
        }
    }

    void checkWinner(){
        //horizontal
        for(int row=0; row < 3; ++row)
        {
            if(board[row][0].getText() == ""){
                continue;
            }
            if(board[row][0].getText() == board[row][1].getText() && 
                board[row][1].getText() == board[row][2].getText()){
                    for(int i = 0; i < 3; ++i)
                    {
                        setWinner(board[row][i]);
                    }
                    gameOver = true;
                    return;
                }
        }

        //vertical
        for(int col=0; col < 3; ++col)
        {
            if(board[0][col].getText() == ""){
                continue;
            }
            if(board[0][col].getText() == board[1][col].getText() && 
                board[1][col].getText() == board[2][col].getText()){
                    for(int i = 0; i < 3; ++i)
                    {
                        setWinner(board[i][col]);
                    }
                    gameOver = true;
                    return;
                }
        }

        //diagonally
        if(board[0][0].getText() == ""){
            return;
        }
        if(board[0][0].getText() == board[1][1].getText() && 
            board[1][1].getText() == board[2][2].getText()){
                for(int i = 0; i < 3; ++i)
                {
                    setWinner(board[i][i]);
                }
                gameOver = true;
                return;
        }

        //anti- diagonally
        if(board[0][2].getText() == ""){
            return;
        }
        if(board[0][2].getText() == board[1][1].getText() && 
            board[1][1].getText() == board[2][0].getText()){
                setWinner(board[0][2]);
                setWinner(board[1][1]);
                setWinner(board[2][0]);
                gameOver = true;
                return;
        }

        //tie
        if(turns == 9)
        {
            tiesetBackground();
            gameOver=true;
            winner= "";
            return;
        }

    }

    void setWinner(JButton tile)
    {
        tile.setForeground(gray);
        tile.setBackground(purple);
        winner = currentPlayer;
        textLabel.setText(currentPlayer + " is the winner!!!");
        textLabel.setBackground(ogawa);
        textLabel.setForeground(Color.white);
    }

    void tiesetBackground(){
        for (int row = 0; row < 3; ++row)
        {
            for(int col = 0; col < 3; ++col)
            {
                board[row][col].setBackground(Color.black);
                board[row][col].setForeground(Color.white);
            }
        }
        textLabel.setText("it's a tie!");
    }   

    void cleanBourd(){
        for (int row = 0; row < 3; ++row)
        {
            for(int col = 0; col < 3; ++col)
            {
                board[row][col].setText("");
                board[row][col].setBackground(gray);
                board[row][col].setForeground(purple);
            }
        }
        playAgain.setVisible(false);
    }
}

