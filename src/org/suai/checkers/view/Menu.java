package org.suai.checkers.view;
import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.File;
import java.io.IOException;
public class Menu extends JFrame implements ActionListener, MouseListener 
{
    private JToolBar toolbar;
    private JOptionPane msg;
    private JButton newGameButton;
    private JButton continueGameButton;
    private JButton rulesButton;
    private JButton exitButton;
    private JButton authorsButton;
    private JButton plVsPlButton;
    private JButton plVsComButton;
    private JButton backButton;
    private Board board;
    private JButton difficultyButton;
    private JButton back1Button;
    private JButton back2Button;
    private JComboBox<String> levels;
    private JButton selectPlayerButton;
    private JButton whitePlayerButton;
    private JButton blackPlayerButton;
    public Menu() throws IOException
    {
        super("Checkers game");
        msg = new JOptionPane();
        board = new Board();
        board.setVisible(false);
        toolbar = new JToolBar("Toolbar", JToolBar.VERTICAL);
        toolbar.setFloatable(false);
        selectPlayerButton = new JButton("Select player");
        selectPlayerButton.setBorderPainted(false);
        selectPlayerButton.setFont(new Font("Serif", Font.PLAIN, 30));
        selectPlayerButton.addActionListener(this);
        selectPlayerButton.setContentAreaFilled(false);
        toolbar.add(selectPlayerButton);
        selectPlayerButton.setVisible(false);
        whitePlayerButton = new JButton("White");
        whitePlayerButton.setBorderPainted(false);
        whitePlayerButton.setFont(new Font("Serif", Font.PLAIN, 30));
        whitePlayerButton.addActionListener(this);
        whitePlayerButton.setContentAreaFilled(false);
        toolbar.add(whitePlayerButton);
        whitePlayerButton.setVisible(false);
        blackPlayerButton = new JButton("Black");
        blackPlayerButton.setBorderPainted(false);
        blackPlayerButton.setFont(new Font("Serif", Font.PLAIN, 30));
        blackPlayerButton.addActionListener(this);
        blackPlayerButton.setContentAreaFilled(false);
        toolbar.add(blackPlayerButton);
        blackPlayerButton.setVisible(false);
        levels = new JComboBox<>();
        levels.addItem("Easy");
        levels.addItem("Medium");
        levels.addItem("Hard");
        levels.addActionListener(this);
        toolbar.add(levels);
        levels.setVisible(false);
        levels.setFont(new Font("Serif", Font.PLAIN, 15));
        difficultyButton = new JButton("Set difficulty");
        difficultyButton.setBorderPainted(false);
        difficultyButton.setFont(new Font("Serif", Font.PLAIN, 30));
        difficultyButton.addActionListener(this);
        difficultyButton.setContentAreaFilled(false);
        toolbar.add(difficultyButton);
        difficultyButton.setVisible(false);
        backButton = new JButton("Return to menu");
        backButton.setBorderPainted(false);
        backButton.setFont(new Font("Serif", Font.PLAIN, 30));
        backButton.addActionListener(this);
        backButton.setContentAreaFilled(false);
        back2Button = new JButton("Back");
        back2Button.setBorderPainted(false);
        back2Button.setFont(new Font("Serif", Font.PLAIN, 30));
        back2Button.addActionListener(this);
        back2Button.setContentAreaFilled(false);
        toolbar.add(back2Button);
        back2Button.setVisible(false);
        back1Button = new JButton("Back to select mode");
        back1Button.setBorderPainted(false);
        back1Button.setFont(new Font("Serif", Font.PLAIN, 30));
        back1Button.addActionListener(this);
        back1Button.setContentAreaFilled(false);
        board.getCancelGameButton().addActionListener(this);
        toolbar.add(back1Button);
        back1Button.addActionListener(this);
        back1Button.setVisible(false);
        plVsComButton = new JButton("Player vs computer");
        plVsComButton.setBorderPainted(false);
        plVsComButton.setFont(new Font("Serif", Font.PLAIN, 30));
        plVsComButton.addActionListener(this);
        plVsComButton.setContentAreaFilled(false);
        plVsPlButton = new JButton("Player vs player");
        plVsPlButton.setBorderPainted(false);
        plVsPlButton.setFont(new Font("Serif", Font.PLAIN, 30));
        plVsPlButton.addActionListener(this);
        plVsPlButton.setContentAreaFilled(false);
        newGameButton = new JButton("New game");
        newGameButton.setBorderPainted(false);
        newGameButton.setFont(new Font("Serif", Font.PLAIN, 30));
        newGameButton.addActionListener(this);
        toolbar.add(newGameButton);
        continueGameButton = new JButton("Continue");
        continueGameButton.addActionListener(this);
        continueGameButton.setBorderPainted(false);
        continueGameButton.setFont(new Font("Serif", Font.PLAIN, 30));
        toolbar.add(continueGameButton);
        rulesButton = new JButton("Rules");
        rulesButton.addActionListener(this);
        rulesButton.setBorderPainted(false);
        rulesButton.setFont(new Font("Serif", Font.PLAIN, 30));
        toolbar.add(rulesButton);
        authorsButton = new JButton("Authors");
        authorsButton.addActionListener(this);
        authorsButton.setBorderPainted(false);
        authorsButton.setFont(new Font("Serif", Font.PLAIN, 30));
        toolbar.add(authorsButton);
        exitButton = new JButton("Exit");
        exitButton.addActionListener(this);
        exitButton.setBorderPainted(false);
        exitButton.setFont(new Font("Serif", Font.PLAIN, 30));
        toolbar.add(exitButton);
        toolbar.add(plVsComButton);
        toolbar.add(plVsPlButton);
        toolbar.add(backButton);
        plVsComButton.setVisible(false);
        plVsPlButton.setVisible(false);
        backButton.setVisible(false);
        toolbar.setOpaque(false);
        newGameButton.setContentAreaFilled(false);
        continueGameButton.setContentAreaFilled(false);
        rulesButton.setContentAreaFilled(false);
        exitButton.setContentAreaFilled(false);
        authorsButton.setContentAreaFilled(false);
        try
        {
            setContentPane(new JPanelWithBackground("background.jpg"));
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
        add(toolbar);
        pack();
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);
        setResizable(false);
        setLocationRelativeTo(null);
    }

    @Override
    public void actionPerformed(ActionEvent evt)
    {
        Object src = evt.getSource();
        if (src == exitButton)
        {
            System.exit(0);

        }
        if (src == rulesButton)
        {
            msg.showMessageDialog(this, " At the beginning of the game there are 12 white and 12 black \"simple\" checkers, located opposite each other on black cells on the field 8x8.\n" +
                    " The game always starts by white checkers. \"Simple\" checkers can walk diagonally forward one cell .\n" +
                    " If the checker during the game comes to the opposite edge of the field, it turns into a king.\n" +
                    " King can walk an unlimited number of cells diagonally forward and back and jump through one checker in any direction along the diagonal.\n" +
                    " Jump is necessary, if there are several ways, the player can choose any one, but go through it till the end.\n" +
                    " If both opponents have one king, it is allowed do only 20 moves, after which, if no one has won, draw is fixed. \n" +
                    " The goal of the game is to \"eat\" or \"lock\" (to deprive the move) all checkers of enemy ", "Checkers - Rules", 1);
            pack();
        }
        if (src == authorsButton)
        {
            msg.showMessageDialog(this, " Design & Artwork:\n Vladimir Markovskiy \n\n Programming:\n Pavel Nedoshivin\n\n 2018 SUAI", "Checkers - Authors", 1);
            pack();
        }
        if (src == newGameButton || src == back1Button)
        {
            newGameButton.setVisible(false);
            authorsButton.setVisible(false);
            rulesButton.setVisible(false);
            continueGameButton.setVisible(false);
            exitButton.setVisible(false);
            plVsComButton.setVisible(true);
            plVsPlButton.setVisible(true);
            backButton.setVisible(true);
            difficultyButton.setVisible(false);
            back1Button.setVisible(false);
            levels.setVisible(false);
            back2Button.setVisible(false);
            selectPlayerButton.setVisible(false);
            whitePlayerButton.setVisible(false);
            blackPlayerButton.setVisible(false);
            pack();
        }
        if (src == plVsComButton)
        {
            newGameButton.setVisible(false);
            authorsButton.setVisible(false);
            rulesButton.setVisible(false);
            continueGameButton.setVisible(false);
            exitButton.setVisible(false);
            back2Button.setVisible(false);
            plVsComButton.setVisible(false);
            plVsPlButton.setVisible(false);
            difficultyButton.setVisible(true);
            backButton.setVisible(false);
            back1Button.setVisible(true);
            levels.setVisible(false);
            selectPlayerButton.setVisible(false);
            whitePlayerButton.setVisible(false);
            blackPlayerButton.setVisible(false);
            pack();
        }
        if (src == plVsPlButton)
        {
            try
            {
                board.setMode(false, 0, false);
            }
            catch (IOException ex)
            {
                ex.printStackTrace();
            }
            newGameButton.setVisible(false);
            authorsButton.setVisible(false);
            rulesButton.setVisible(false);
            back1Button.setVisible(false);
            continueGameButton.setVisible(false);
            exitButton.setVisible(false);
            plVsComButton.setVisible(false);
            plVsPlButton.setVisible(false);
            backButton.setVisible(false);
            difficultyButton.setVisible(false);
            back2Button.setVisible(false);
            levels.setVisible(false);
            getContentPane().setVisible(false);
            setContentPane(board);
            try
            {
                board.doNewGame();
            }
            catch (IOException e)
            {
                e.printStackTrace();
            }
            board.setVisible(true);
            setSize(920,755);
            setLocationRelativeTo(null);

        }
        if (src == continueGameButton) {
            if(!board.gameOver()) {
                newGameButton.setVisible(false);
                authorsButton.setVisible(false);
                rulesButton.setVisible(false);
                continueGameButton.setVisible(false);
                exitButton.setVisible(false);
                plVsComButton.setVisible(false);
                plVsPlButton.setVisible(false);
                backButton.setVisible(false);
                back1Button.setVisible(false);
                getContentPane().setVisible(false);
                difficultyButton.setVisible(false);
                back2Button.setVisible(false);
                levels.setVisible(false);
                selectPlayerButton.setVisible(false);
                whitePlayerButton.setVisible(false);
                blackPlayerButton.setVisible(false);
                setContentPane(board);
                try {
                    board.doContinue();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                board.setVisible(true);
                setSize(920, 755);
                setLocationRelativeTo(null);
                repaint();
            }
            else
            {
                continueGameButton.setEnabled(true);
                msg.showMessageDialog(this, "No game to continue");
                pack();
            }
        }
        if(src == board.getCancelGameButton())
        {
            newGameButton.setVisible(true);
            authorsButton.setVisible(true);
            rulesButton.setVisible(true);
            continueGameButton.setVisible(true);
            exitButton.setVisible(true);
            plVsComButton.setVisible(false);
            plVsPlButton.setVisible(false);
            backButton.setVisible(false);
            levels.setVisible(false);
            back1Button.setVisible(false);
            back2Button.setVisible(false);
            selectPlayerButton.setVisible(false);
            whitePlayerButton.setVisible(false);
            blackPlayerButton.setVisible(false);
            getContentPane().setVisible(false);
            try
            {
                setContentPane(new JPanelWithBackground("background.jpg"));
            } catch (IOException e)
            {
                e.printStackTrace();
            }
            add(toolbar);
            pack();

        }
        if (src == backButton)
        {
            newGameButton.setVisible(true);
            authorsButton.setVisible(true);
            rulesButton.setVisible(true);
            continueGameButton.setVisible(true);
            exitButton.setVisible(true);
            plVsComButton.setVisible(false);
            plVsPlButton.setVisible(false);
            backButton.setVisible(false);
            back1Button.setVisible(false);
            difficultyButton.setVisible(false);
            levels.setVisible(false);
            back2Button.setVisible(false);
            selectPlayerButton.setVisible(false);
            whitePlayerButton.setVisible(false);
            blackPlayerButton.setVisible(false);
            pack();
        }
        if(src == difficultyButton)
        {
            levels.setVisible(true);
            newGameButton.setVisible(false);
            authorsButton.setVisible(false);
            rulesButton.setVisible(false);
            continueGameButton.setVisible(false);
            exitButton.setVisible(false);
            plVsComButton.setVisible(false);
            plVsPlButton.setVisible(false);
            backButton.setVisible(false);
            back1Button.setVisible(false);
            difficultyButton.setVisible(false);
            back2Button.setVisible(true);
            selectPlayerButton.setVisible(false);
            whitePlayerButton.setVisible(false);
            blackPlayerButton.setVisible(false);
            pack();
        }
        if(src == back2Button)
        {
            levels.setVisible(false);
            newGameButton.setVisible(false);
            authorsButton.setVisible(false);
            rulesButton.setVisible(false);
            continueGameButton.setVisible(false);
            exitButton.setVisible(false);
            plVsComButton.setVisible(false);
            plVsPlButton.setVisible(false);
            backButton.setVisible(false);
            back1Button.setVisible(true);
            difficultyButton.setVisible(true);
            back2Button.setVisible(false);
            selectPlayerButton.setVisible(false);
            whitePlayerButton.setVisible(false);
            blackPlayerButton.setVisible(false);
            pack();
        }
        if(src == selectPlayerButton) {
            selectPlayerButton.setVisible(false);
            whitePlayerButton.setVisible(true);
            blackPlayerButton.setVisible(true);
            levels.setVisible(false);
            newGameButton.setVisible(false);
            authorsButton.setVisible(false);
            rulesButton.setVisible(false);
            continueGameButton.setVisible(false);
            exitButton.setVisible(false);
            plVsComButton.setVisible(false);
            plVsPlButton.setVisible(false);
            backButton.setVisible(false);
            back1Button.setVisible(false);
            difficultyButton.setVisible(false);
            back2Button.setVisible(true);
            pack();
        }
        if (src == whitePlayerButton) 
        {
            if (levels.getSelectedItem() == "Easy") 
            {
                getContentPane().setVisible(false);
                setContentPane(board);
                board.setVisible(true);
                setSize(920, 755);
                setLocationRelativeTo(null);
                try {
                    board.doNewGame();
                    board.setMode(true, 0, true);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (levels.getSelectedItem() == "Medium") 
            {
                getContentPane().setVisible(false);
                setContentPane(board);
                board.setVisible(true);
                setSize(920, 755);
                setLocationRelativeTo(null);
                try 
                {
                    board.doNewGame();
                    board.setMode(true, 1, true);
                } 
                catch (IOException e) 
                {
                    e.printStackTrace();
                }
            }
            if (levels.getSelectedItem() == "Hard") 
            {
                getContentPane().setVisible(false);
                setContentPane(board);
                board.setVisible(true);
                setSize(920, 755);
                setLocationRelativeTo(null);
                try 
                {
                    board.doNewGame();
                    board.setMode(true, 2, true);
                } 
                catch (IOException e) 
                {
                    e.printStackTrace();
                }
            }
        }
        if (src == blackPlayerButton)
        {
            if (levels.getSelectedItem() == "Easy") 
            {
                getContentPane().setVisible(false);
                setContentPane(board);
                board.setVisible(true);
                setSize(920, 755);
                setLocationRelativeTo(null);
                try 
                {
                    board.doNewGame();
                    board.setMode(true, 0, false);
                } 
                catch (IOException e) 
                {
                    e.printStackTrace();
                }
            }
            if (levels.getSelectedItem() == "Medium") 
            {
                getContentPane().setVisible(false);
                setContentPane(board);
                board.setVisible(true);
                setSize(920, 755);
                setLocationRelativeTo(null);
                try 
                {
                    board.doNewGame();
                    board.setMode(true, 1, false);
                } 
                catch (IOException e) 
                {
                    e.printStackTrace();
                }
            }
            if (levels.getSelectedItem() == "Hard") 
            {
                getContentPane().setVisible(false);
                setContentPane(board);
                board.setVisible(true);
                setSize(920, 755);
                setLocationRelativeTo(null);
                try 
                {
                    board.doNewGame();
                    board.setMode(true, 2, false);
                } 
                catch (IOException e) 
                {
                    e.printStackTrace();
                }
            }
        }
        if(src == levels)
        {
            if (levels.getSelectedItem() == "Easy")
            {
                selectPlayerButton.setVisible(true);
                levels.setVisible(false);
                whitePlayerButton.setVisible(false);
                blackPlayerButton.setVisible(false);
                pack();
            }
            if (levels.getSelectedItem() == "Medium")
            {
                selectPlayerButton.setVisible(true);
                levels.setVisible(false);
                whitePlayerButton.setVisible(false);
                blackPlayerButton.setVisible(false);
                pack();
            }
            if(levels.getSelectedItem() == "Hard")
            {
                selectPlayerButton.setVisible(true);
                levels.setVisible(false);
                whitePlayerButton.setVisible(false);
                blackPlayerButton.setVisible(false);
                pack();
            }
        }
    }
    @Override
    public void mousePressed(MouseEvent evt)
    {

    }
    @Override
    public void mouseReleased(MouseEvent evt)
    {

    }
    @Override
    public void mouseClicked(MouseEvent evt)
    {

    }
    @Override
    public void mouseEntered(MouseEvent evt)
    {

    }
    @Override
    public void mouseExited(MouseEvent evt)
    {

    }
    private class JPanelWithBackground extends JPanel
    {
        private Image backgroundImage;
        public JPanelWithBackground(String fileName) throws IOException
        {
            backgroundImage = ImageIO.read(new File(fileName));
        }
        @Override
        public void paintComponent(Graphics g)
        {
            super.paintComponent(g);
            g.drawImage(backgroundImage, 0, 0, this);
        }
    }
}