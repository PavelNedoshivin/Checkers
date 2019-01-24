package org.suai.checkers.view;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.io.*;
import java.util.LinkedList;
import java.util.ListIterator;

import org.suai.checkers.io.CheckerReader;
import org.suai.checkers.io.CheckerWriter;
import org.suai.checkers.model.CheckersAI;
import org.suai.checkers.model.CheckersBoard;

public class Board extends JPanel implements ActionListener, MouseListener {
    private JLabel message;
    private boolean isChosen, isOver, vsComp, compTurn;
    private int chosenRow, chosenColumn;
    private int goToRow, goToColumn;
    private int diff;
    private CheckersBoard desk;
    private JButton cancelButton;
    private JButton cancelGameButton;
    private JComboBox<String> moves;
    private JLabel message1;
    private boolean flag, adding;
    public Board() throws IOException
    {
        flag = false;
        adding = false;
        setLayout(null);
        setBackground(Color.WHITE);
        addMouseListener(this);
        message = new JLabel("", JLabel.CENTER);
        message.setFont(new  Font("Serif", Font.PLAIN, 18));
        message.setForeground(Color.red);
        message.setBounds(100, 670, 150, 50);
        add(message);
        message.setVisible(true);
        message1 = new JLabel("History of moves");
        message1.setFont(new  Font("Serif", Font.PLAIN, 18));
        message1.setBounds(700, 5, 150, 70);
        add(message1);
        cancelButton = new JButton("Cancel turn");
        cancelButton.setFont(new  Font("Serif", Font.PLAIN, 18));
        cancelButton.setBounds(270, 670, 150, 50);
        cancelButton.setBorderPainted(false);
        cancelButton.setContentAreaFilled(false);
        add(cancelButton);
        cancelGameButton = new JButton("Return to menu");
        cancelGameButton.setFont(new  Font("Serif", Font.PLAIN, 18));
        cancelGameButton.setBounds(440, 670, 150, 50);
        cancelGameButton.setBorderPainted(false);
        cancelGameButton.setContentAreaFilled(false);
        add(cancelGameButton);
        moves = new JComboBox<>();
        moves.addActionListener(this);
        add(moves);
        moves.setVisible(true);
        moves.setFont(new Font("Serif", Font.PLAIN, 15));
        moves.setBounds(700, 60, 200, 50);
        cancelButton.addActionListener(this);
        desk = new CheckersBoard(true, "log.txt");
        isChosen = false;
        isOver = false;
        vsComp = false;
        compTurn = false;
        diff = 0;
    }
    public JButton getCancelGameButton()
    {
        return cancelGameButton;
    }
    public void setMode(boolean v, int d, boolean who) throws IOException
    {
        vsComp = v;
        diff = d;
        compTurn = who;
        if ((compTurn == false) && (vsComp == true))
        {
            CheckersAI comp = new CheckersAI(desk, diff);
            comp.makeTurn();
            desk.saveBoard("log.txt");
            changeMessage();
            repaint();
        }
        CheckerWriter cw = new CheckerWriter();
        cw.saveSettings("settings.txt", vsComp, diff, compTurn);
    }
    @Override
    public void actionPerformed(ActionEvent evt)
    {
        Object src = evt.getSource();
        if (src == cancelButton)
        {
            try
            {
                if ((vsComp == true) && (compTurn == false) && (desk.getTurn() == 1))
                {
                    return;
                }
                desk.cancelTurn();
                CheckerWriter cw = new CheckerWriter();
                cw.cancelTurnHistory("history.txt");
                if (moves.getItemCount() > 1)
                {
                    moves.removeItemAt(moves.getItemCount() - 1);
                }
                if (vsComp == true)
                {
                    desk.cancelTurn();
                }
            }
            catch (IOException err)
            {
                err.printStackTrace();
            }
            if (isOver)
            {
                isOver = false;
            }
            desk.couldEat();
            isChosen = false;
            changeMessage();
            repaint();
        }
        if(src == moves)
        {
            try
            {
                if (!flag)
                {
                    int index = moves.getSelectedIndex() + 1;
                    if (vsComp == true)
                    {
                        index *= 2;
                    }
                    returnToMove(index);
                }
            }
            catch (IOException err)
            {
                err.printStackTrace();
            }
        }
    }
    public void changeMessage()
    {
        if (desk.whoseTurn())
        {
            message.setText("BLACK turn");

        }
        else
        {
            message.setText("WHITE turn");
        }
    }
    public void doNewGame() throws IOException
    {
        desk = new CheckersBoard(false, "log.txt");
        CheckerWriter cw = new CheckerWriter();
        cw.clearHistory();
        changeMessage();
        if (moves.getItemCount() > 1)
        {
            moves.removeAllItems();
        }
        repaint();
    }
    public void doContinue() throws IOException
    {
        flag = true;
        desk = new CheckersBoard(true, "log.txt");
        if (!(gameOver()))
        {
            desk.couldEat();
            changeMessage();
        }
        repaint();
        CheckerReader cr = new CheckerReader();
        Object[] settings = cr.loadSettings("settings.txt");
        if (settings == null)
        {
            vsComp = false;
            diff = 0;
            compTurn = false;
        }
        else
        {
            vsComp = (boolean)settings[0];
            diff = (int)settings[1];
            compTurn = (boolean)settings[2];
        }
        if ((moves.getItemCount() == 0)) 
        {
            LinkedList<String> l = cr.loadHistory("history.txt");
            if (l != null)
            {
                ListIterator<String> it = l.listIterator();
                while (it.hasNext()) 
                {
                    String cur = it.next();
                    moves.addItem(cur);
                }
            }
        }
        flag = false;
    }
    public boolean gameOver()
    {
        if (desk == null)
        {
            return false;
        }
        if (desk.gameOver() > 0)
        {
            switch(desk.gameOver())
            {
                case 1:
                    message.setText("WHITE wins");
                    break;
                case 2:
                    message.setText("BLACK wins");
                    break;
                case 3:
                    message.setText("DRAW");
            }
            isChosen = false;
            isOver = true;
            return true;
        }
        return false;
    }
    public void doChooseChecker(int row, int col)
    {
        if (!isOver)
        {
            isChosen = desk.chooseChecker(row, col);
        }
        chosenRow = row;
        chosenColumn = col;
        repaint();
    }
    public void returnToMove(int num) throws IOException
    {
        if (adding == true)
        {
            return;
        }
        for (int i = desk.getTurn(); i > num; i--)
        {
            if (vsComp == true)
            {
                if ((i == desk.getTurn()) && (compTurn == false))
                {
                    i--;
                }
                desk.cancelTurn();
                i--;
            }
            desk.cancelTurn();
            CheckerWriter cw = new CheckerWriter();
            cw.cancelTurnHistory("history.txt");
            if (moves.getItemCount() > 1)
            {
                moves.removeItemAt(moves.getItemCount() - 1);
            }
        }
        desk.couldEat();
        isChosen = false;
        changeMessage();
        repaint();
    }
    public String getMove()
    {
        StringBuilder res = new StringBuilder(100);
        if (vsComp == true)
        {
            res.append(desk.getTurn() + 1);
        }
        else
        {
            res.append(desk.getTurn());
        }
        res.append(": ");
        res.append((char)(chosenColumn + 'A'));
        res.append(Integer.toString(chosenRow + 1));
        res.append(" - ");
        res.append((char)(goToColumn + 'A'));
        res.append(Integer.toString(goToRow + 1));
        return res.toString();
    }
    public void doMakeMove(int row, int col) throws IOException
    {
        int a = desk.makeTurn(row, col);
        if (a == 0)
        {
            adding = true;
            desk.saveBoard("log.txt");
            if (gameOver())
            {
                repaint();
                return;
            }
            isChosen = false;
            goToRow = row;
            goToColumn = col;
            CheckerWriter cw = new CheckerWriter();
            cw.saveHistory("history.txt", getMove());
            moves.addItem(getMove());
            if (vsComp == true)
            {
                CheckersAI comp = new CheckersAI(desk, diff);
                comp.makeTurn();
                desk.saveBoard("log.txt");
                if (gameOver())
                {
                    repaint();
                    return;
                }
                isChosen = false;
            }
            desk.couldEat();
            adding = false;
        }
        else
        {
            if (a == 1)
            {
                desk.couldEat();
                doChooseChecker(row, col);
                return;
            }
            if (a == 2)
            {
                return;
            }
        }
        changeMessage();
        repaint();
    }
    @Override
    public void repaint()
    {
        super.repaint();
        revalidate();
    }
    @Override
    public void paintComponent(Graphics g)
    {
        super.paintComponent(g);
        String str = desk.toString();
        int cnt = 0;
        for (int i = 0; i < 8; i++)
        {
            for (int j = 0; j < 8; j++)
            {
                if (i % 2 == j % 2)
                {
                    g.setColor(Color.LIGHT_GRAY);
                }
                else
                {
                    g.setColor(Color.DARK_GRAY);
                }
                g.fillRect(j * 83, i * 83, 83, 83);
                switch(str.charAt(cnt))
                {
                    case '1':
                        g.setColor(Color.WHITE);
                        g.fillOval(12 + j * 83, 12 + i * 83, 50, 50);
                        break;
                    case '2':
                        g.setColor(Color.BLACK);
                        g.fillOval(12 + j * 83, 12 + i * 83, 50, 50);
                        break;
                    case '3':
                        g.setColor(Color.WHITE);
                        g.fillOval(12 + j * 83, 12 + i *83, 50, 50);
                        g.setColor(Color.BLACK);
                        g.drawString("K", 30 + j * 83, 30 + i * 83);
                        break;
                    case '4':
                        g.setColor(Color.BLACK);
                        g.fillOval(12 + j * 83, 12 + i *83, 50, 50);
                        g.setColor(Color.WHITE);
                        g.drawString("K", 30 + j * 83, 30 + i * 83);
                        break;
                    case '5':
                        g.setColor(Color.GREEN);
                        g.drawOval(12 + j * 83, 12 + i * 83, 50, 50);
                        break;
                    case '6':
                        g.setColor(Color.RED);
                        g.drawOval(12 + j * 83, 12 + i * 83, 50, 50);
                        break;
                }
                cnt++;
            }
            cnt++;
        }
        String coordinates = "ABCDEFGH";
        for(int i = 1; i < 9; i++){
            g.setColor(Color.DARK_GRAY);
            g.drawString(coordinates.substring(i - 1, i), i * 83 - 25, 680);
        }
        for(Integer j = 1; j < 9; j++){
            g.setColor(Color.DARK_GRAY);
            g.drawString(j.toString(), 670, j * 83 - 25 );
        }
        if (isChosen)
        {
            g.setColor(Color.YELLOW);
            g.drawOval(7 + chosenColumn * 83, 7 + chosenRow * 83, 60, 60);
        }

    }
    @Override
    public void mousePressed(MouseEvent evt)
    {
        int col = evt.getX() / 83;
        int row = evt.getY() / 83;
        if (col >= 0 && col < 8 && row >= 0 && row < 8)
        {
            if (isChosen == false)
            {
                doChooseChecker(row,col);
            }
            else
            {
                try {
                    doMakeMove(row, col);
                } catch (IOException err) {
                    err.printStackTrace();
                }
            }
        }
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
}