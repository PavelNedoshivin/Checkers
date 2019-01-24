package org.suai.checkers.model;
import java.io.*;
import java.util.LinkedList;
import java.util.ListIterator;
import org.suai.checkers.io.CheckerReader;
import org.suai.checkers.io.CheckerWriter;
/*
Markers:
0 - empty
1 - white
2 - black
3 - white king
4 - black king
5 - could go
6 - must eat
*/
public class CheckersBoard
{
    private final CheckerCell[][] desk;
    private int white;
    private int black;
    private boolean who;
    private final LinkedList markedGreen;
    private final LinkedList markedRed;
    private final LinkedList eaters;
    private int countdown;
    private boolean startCountdown, wKing, bKing;
    private int whiteKings;
    private int blackKings;
    private boolean mustEat;
    private CheckerCell chosen;
    private int numTurn;
    public CheckersBoard(boolean cont, String logname) throws IOException
    {
        desk = new CheckerCell[8][8];
        markedGreen = new LinkedList();
        markedRed = new LinkedList();
        eaters = new LinkedList();
        who = false;
        countdown = 20;
        numTurn = 0;
        if (cont == true)
        {
            loadBoard(logname);
        }
        else
        {
            CheckerWriter cw = new CheckerWriter();
            cw.clearLog(logname);
            initBoard();
        }
        getCheckers();
        if (((whiteKings > 0) && (blackKings > 0)) || (countdown < 20))
        {
            startCountdown = true;
        }
    }
    public CheckersBoard(CheckersBoard o)
    {
        desk = new CheckerCell[8][8];
        for (int i = 0; i < 8; i++)
        {
            System.arraycopy(o.desk[i], 0, desk[i], 0, 8);
        }
        markedGreen = new LinkedList();
        markedRed = new LinkedList();
        eaters = new LinkedList();
        who = o.who;
        countdown = o.countdown;
        numTurn = o.numTurn;
        getCheckers();
        if (((whiteKings > 0) && (blackKings > 0)) || (countdown < 20))
        {
            startCountdown = true;
        }
    }
    public int getTurn()
    {
        return numTurn;
    }
    private void getCheckers()
    {
        white = 0;
        black = 0;
        for (int i = 0; i < 8; i++)
        {
            for (int j = 0; j < 8; j++)
            {
                switch(desk[i][j].getCode())
                {
                    case 1:
                        white++;
                        break;
                    case 2:
                        black++;
                        break;
                    case 3:
                        whiteKings++;
                        white++;
                        wKing = true;
                        break;
                    case 4:
                        blackKings++;
                        black++;
                        bKing = true;
                        break;
                }
            }
        }
    }
    private void initBoard() throws IOException
    {
        for (int i = 0; i < 8; i++)
        {
            for (int j = 0; j < 8; j++)
            {
                desk[i][j] = new CheckerCell(0, i, j);
            }
        }
        for (int i = 0; i < 3; i++)
        {
            int flag = i % 2;
            for (int j = 0; j < 8; j++)
            {
                if ((flag == 0) && (j == 0))
                {
                    j++;
                }
                desk[i][j].changeCode(2);
                j++;
            }
        }
        for (int i = 5; i < 8; i++)
        {
            int flag = i % 2;
            for (int j = 0; j < 8; j++)
            {
                if ((flag == 0) && (j == 0))
                {
                    j++;
                }
                desk[i][j].changeCode(1);
                j++;
            }
        }
        saveBoard("log.txt");
    }
    @Override
    public String toString()
    {
        StringBuilder res = new StringBuilder(100);
        for (int i = 0; i < 8; i++)
        {
            for (int j = 0; j < 8; j++)
            {
                res.append(desk[i][j].toString());
            }
            res.append("\n");
        }
        return res.toString();
    }
    public void saveBoard(String logname) throws IOException
    {
        CheckerWriter cw = new CheckerWriter();
        cw.saveBoard(logname, numTurn, countdown, toString());
    }
    private void getNodesChain(LinkedList res, CheckerNode o,
                              CheckerCell chosenCell, CheckerCell goTo) throws IOException
    {
        CheckerNode v = new CheckerNode(o);
        v.addToChain(new CheckerCell(goTo));
        couldEat();
        LinkedList safeRed = new LinkedList(markedRed);
        ListIterator red = safeRed.listIterator();
        chooseChecker(goTo.getRow(), goTo.getColumn());
        while (red.hasNext())
        {
            CheckerCell obj = (CheckerCell)red.next();
            switch (makeTurn(obj.getRow(), obj.getColumn()))
            {
                case 0:
                    if (!whoseTurn())
                    {
                        v.changeValue(black - white);
                    }
                    else
                    {
                        v.changeValue(white - black);
                    }
                    v.addToChain(obj);
                    res.add(new CheckerNode(v));
                    v = new CheckerNode(o);
                    v.addToChain(new CheckerCell(goTo));
                    break;
                case 1:
                    saveBoard("log.txt");
                    getNodesChain(res, v, goTo, obj);
                    break;
                case 2:
                    break;
            }
            saveBoard("log.txt");
            cancelTurn();
            couldEat();
            chooseChecker(goTo.getRow(), goTo.getColumn());
        }
        deleteMark();
        cancelTurn();
        couldEat();
    }
    private void getNodesMustEat(LinkedList res) throws IOException
    {
        LinkedList safeEaters = new LinkedList(eaters);
        ListIterator eat = safeEaters.listIterator();
        while (eat.hasNext())
        {
            CheckerCell chosenCell = (CheckerCell)eat.next();
            chooseChecker(chosenCell.getRow(), chosenCell.getColumn());
            LinkedList choises = new LinkedList(markedRed);
            ListIterator ch = choises.listIterator();
            while (ch.hasNext())
            {
                CheckerCell goTo = (CheckerCell)ch.next();
                switch (makeTurn(goTo.getRow(), goTo.getColumn()))
                {
                    case 0:                        
                        if (!whoseTurn())
                        {
                            res.add(new CheckerNode(black - white, chosenCell, goTo));
                        }
                        else
                        {
                            res.add(new CheckerNode(white - black, chosenCell, goTo));
                        }
                        saveBoard("log.txt");
                        cancelTurn();
                        couldEat();
                        chooseChecker(chosenCell.getRow(), chosenCell.getColumn());
                        break;
                    case 1:
                        saveBoard("log.txt");
                        getNodesChain(res, new CheckerNode(black - white, chosenCell, null),
                                chosenCell, goTo);
                        break;
                    case 2:
                        break;
                }
            }
            deleteMark();
            couldEat();
        }
    }
    public LinkedList getNodes() throws IOException
    {
        if (gameOver() > 0)
        {
            return null;
        }
        LinkedList res = new LinkedList();
        mustEat = couldEat();
        if (mustEat)
        {
            getNodesMustEat(res);
        }
        else
        {
            for (int i = 0; i < 8; i++)
            {
                for (int j = 0; j < 8; j++)
                {
                    if (isYours(desk[i][j]))
                    {
                        chooseChecker(i, j);
                    }
                    int k = 0;
                    while (k < markedGreen.size())
                    {
                        CheckerCell goTo = (CheckerCell)markedGreen.get(k);
                        makeTurn(goTo.getRow(), goTo.getColumn());
                        if (!whoseTurn())
                        {
                            res.add(new CheckerNode(black - white, new CheckerCell(desk[i][j]), goTo));
                        }
                        else
                        {
                            res.add(new CheckerNode(white - black, new CheckerCell(desk[i][j]), goTo));
                        }
                        saveBoard("log.txt");
                        cancelTurn();
                        chooseChecker(i, j);
                        k++;
                    }
                    deleteMark();
                }
            }
        }
        return res;
    }
    private boolean couldTurn()
    {
        mustEat = couldEat();
        for (int i = 0; i < 8; i++)
        {
            for (int j = 0; j < 8; j++)
            {
                if (isYours(desk[i][j]))
                {
                    chooseChecker(i, j);
                }
                if (!(markedGreen.isEmpty()))
                {
                    deleteMark();
                    return true;
                }
            }
        }
        if (!(markedRed.isEmpty()))
        {
            deleteMark();
            return true;
        }
        return false;
    }
    public void cancelTurn() throws IOException
    {
        if (numTurn == 0)
        {
            return;
        }
        CheckerReader cr = new CheckerReader();
        cr.cancelTurn("log.txt");
        loadBoard("log.txt");
        getCheckers();
    }
    public void loadBoard(String filename) throws IOException
    {
        CheckerReader cr = new CheckerReader();
        String s = cr.loadBoard(filename);
        if (s == null)
        {
            CheckerWriter cw = new CheckerWriter();
            cw.clearLog(filename);
            initBoard();
            return;
        }
        int counter = 0;
        for (int i = 0; i < 8; i++)
        {
            for (int j = 0; j < 8; j++)
            {
                desk[i][j] = new CheckerCell(s.charAt(counter) - '0', i, j);
                counter++;
            }
        }
        numTurn = cr.getTurn();
        if (numTurn % 2 == 0)
        {
            who = false;
        }
        else
        {
            who = true;
        }
        countdown = cr.getCountdown();
    }
    private boolean isOpposite(CheckerCell o)
    {
        if ((who == true) && ((o.getCode() == 1) || (o.getCode() == 3)))
        {
            return true;
        }
        return ((who == false) && ((o.getCode() == 2) || (o.getCode() == 4)));
    }
    public boolean couldEat()
    {
        markedRed.clear();
        eaters.clear();
        boolean flag = false;
        for (int i = 0; i < 8; i++)
        {
            for (int j = 0; j < 8; j++)
            {
                if (((who == true) && (desk[i][j].getCode() == 2)) ||
                        ((who == false) && (desk[i][j].getCode() == 1)))
                {
                    if ((i < 6) && (j > 1))
                    {
                        if ((desk[i + 2][j - 2].getCode() == 0) && (isOpposite(desk[i + 1][j - 1])))
                        {
                            markedRed.add(new CheckerCell(desk[i + 2][j - 2]));
                            flag = true;
                            eaters.add(new CheckerCell(desk[i][j]));
                        }
                    }
                    if ((i < 6) && (j < 6))
                    {
                        if ((desk[i + 2][j + 2].getCode() == 0) && (isOpposite(desk[i + 1][j + 1])))
                        {
                            markedRed.add(new CheckerCell(desk[i + 2][j + 2]));
                            flag = true;
                            eaters.add(new CheckerCell(desk[i][j]));
                        }
                    }
                    if ((i > 1) && (j > 1))
                    {
                        if ((desk[i - 2][j - 2].getCode() == 0) && (isOpposite(desk[i - 1][j - 1])))
                        {
                            markedRed.add(new CheckerCell(desk[i - 2][j - 2]));
                            flag = true;
                            eaters.add(new CheckerCell(desk[i][j]));
                        }
                    }
                    if ((i > 1) && (j < 6))
                    {
                        if ((desk[i - 2][j + 2].getCode() == 0) && (isOpposite(desk[i - 1][j + 1])))
                        {
                            markedRed.add(new CheckerCell(desk[i - 2][j + 2]));
                            flag = true;
                            eaters.add(new CheckerCell(desk[i][j]));
                        }
                    }
                }
                if (((who == true) && (desk[i][j].getCode() == 4)) ||
                        ((who == false) && (desk[i][j].getCode() == 3)))
                {
                    boolean anotherFlag = false;
                    for (int k = i - 1; k > 0; k--)
                    {
                        if (i + j - k == 8)
                        {
                            break;
                        }
                        if (isYours(desk[k][i + j - k]))
                        {
                            break;
                        }
                        if (isOpposite(desk[k][i + j - k]))
                        {
                            if (anotherFlag)
                            {
                                break;
                            }
                            anotherFlag = true;
                            int temp = k;
                            boolean subFlag = false;
                            k--;
                            if (i + j - k == 8)
                            {
                                break;
                            }
                            while (desk[k][i + j - k].getCode() == 0)
                            {
                                markedRed.add(new CheckerCell(desk[k][i + j - k]));
                                flag = true;
                                subFlag = true;
                                k--;
                                if ((i + j - k == 8) || (k < 0))
                                {
                                    break;
                                }
                            }
                            if (subFlag)
                            {
                                eaters.add(new CheckerCell(desk[i][j]));
                            }
                            k = temp;
                        }
                    }
                    anotherFlag = false;
                    for (int k = i - 1; k > 0; k--)
                    {
                        if (j - i + k < 0)
                        {
                            break;
                        }
                        if (isYours(desk[k][j - i + k]))
                        {
                            break;
                        }
                        if (isOpposite(desk[k][j - i + k]))
                        {
                            if (anotherFlag)
                            {
                                break;
                            }
                            anotherFlag = true;
                            int temp = k;
                            boolean subFlag = false;
                            k--;
                            if (j - i + k < 0)
                            {
                                break;
                            }
                            while (desk[k][j - i + k].getCode() == 0)
                            {
                                markedRed.add(new CheckerCell(desk[k][j - i + k]));
                                flag = true;
                                subFlag = true;
                                k--;
                                if ((j - i + k < 0) || (k < 0))
                                {
                                    break;
                                }
                            }
                            if (subFlag)
                            {
                                eaters.add(new CheckerCell(desk[i][j]));
                            }
                            k = temp;
                        }
                    }
                    anotherFlag = false;
                    for (int k = i + 1; k < 7; k++)
                    {
                        if (i + j - k < 0)
                        {
                            break;
                        }
                        if (isYours(desk[k][i + j - k]))
                        {
                            break;
                        }
                        if (isOpposite(desk[k][i + j - k]))
                        {
                            if (anotherFlag)
                            {
                                break;
                            }
                            anotherFlag = true;
                            int temp = k;
                            boolean subFlag = false;
                            k++;
                            if (i + j - k < 0)
                            {
                                break;
                            }
                            while (desk[k][i + j - k].getCode() == 0)
                            {
                                markedRed.add(new CheckerCell(desk[k][i + j - k]));
                                flag = true;
                                subFlag = true;
                                k++;
                                if ((i + j - k < 0) || (k > 7))
                                {
                                    break;
                                }
                            }
                            if (subFlag)
                            {
                                eaters.add(new CheckerCell(desk[i][j]));
                            }
                            k = temp;
                        }
                    }
                    anotherFlag = false;
                    for (int k = i + 1; k < 7; k++)
                    {
                        if (j - i + k == 8)
                        {
                            break;
                        }
                        if (isYours(desk[k][j - i + k]))
                        {
                            break;
                        }
                        if (isOpposite(desk[k][j - i + k]))
                        {
                            if (anotherFlag)
                            {
                                break;
                            }
                            anotherFlag = true;
                            int temp = k;
                            boolean subFlag = false;
                            k++;
                            if (j - i + k == 8)
                            {
                                break;
                            }
                            while (desk[k][j - i + k].getCode() == 0)
                            {
                                markedRed.add(new CheckerCell(desk[k][j - i + k]));
                                flag = true;
                                subFlag = true;
                                k++;
                                if ((j - i + k == 8) || (k > 7))
                                {
                                    break;
                                }
                            }
                            if (subFlag)
                            {
                                eaters.add(new CheckerCell(desk[i][j]));
                            }
                            k = temp;
                        }
                    }
                }
            }
        }
        mustEat = flag;
        if (mustEat)
        {
            ListIterator it = markedRed.listIterator();
            while (it.hasNext())
            {
                CheckerCell obj = (CheckerCell)it.next();
                desk[obj.getRow()][obj.getColumn()].changeCode(6);
            }
        }
        return flag;
    }
    private boolean isYours(CheckerCell o)
    {
        if ((who == true) && ((o.getCode() == 2) || (o.getCode() == 4)))
        {
            return true;
        }
        return ((who == false) && ((o.getCode() == 1) || (o.getCode() == 3)));
    }
    public boolean chooseChecker(int row, int column)
    {
        chosen = null;
        markedGreen.clear();
        if (!(isYours(desk[row][column])))
        {
            return false;
        }
        if (mustEat)
        {
            ListIterator it = eaters.listIterator();
            while (it.hasNext())
            {
                CheckerCell obj = (CheckerCell)it.next();
                if ((obj.getRow() == row) && (obj.getColumn() == column))
                {
                    chosen = desk[row][column];
                    return true;
                }
            }
            return false;
        }
        chosen = desk[row][column];
        if ((chosen.getCode() == 1) || (chosen.getCode() == 2))
        {
            if (who)
            {
                row++;
            }
            else
            {
                row--;
            }
            if (column > 0)
            {
                if (desk[row][column - 1].getCode() == 0)
                {
                    markedGreen.add(new CheckerCell(desk[row][column - 1]));
                    desk[row][column - 1].changeCode(5);
                }
            }
            if (column < 7)
            {
                if (desk[row][column + 1].getCode() == 0)
                {
                    markedGreen.add(new CheckerCell(desk[row][column + 1]));
                    desk[row][column + 1].changeCode(5);
                }
            }
        }
        if ((chosen.getCode() == 3) || (chosen.getCode() == 4))
        {
            for (int i = row - 1; i >= 0; i--)
            {
                if (row + column - i == 8)
                {
                    break;
                }
                if (desk[i][row + column - i].getCode() > 0)
                {
                    break;
                }
                if (desk[i][row + column - i].getCode() == 0)
                {
                    markedGreen.add(new CheckerCell(desk[i][row + column - i]));
                    desk[i][row + column - i].changeCode(5);
                }
            }
            for (int i = row - 1; i >= 0; i--)
            {
                if (column - row + i < 0)
                {
                    break;
                }
                if (desk[i][column - row + i].getCode() > 0)
                {
                    break;
                }
                if (desk[i][column - row + i].getCode() == 0)
                {
                    markedGreen.add(new CheckerCell(desk[i][column - row + i]));
                    desk[i][column - row + i].changeCode(5);
                }
            }
            for (int i = row + 1; i < 8; i++)
            {
                if (row + column - i < 0)
                {
                    break;
                }
                if (desk[i][row + column - i].getCode() > 0)
                {
                    break;
                }
                if (desk[i][row + column - i].getCode() == 0)
                {
                    markedGreen.add(new CheckerCell(desk[i][row + column - i]));
                    desk[i][row + column - i].changeCode(5);
                }
            }
            for (int i = row + 1; i < 8; i++)
            {
                if (column - row + i == 8)
                {
                    break;
                }
                if (desk[i][column - row + i].getCode() > 0)
                {
                    break;
                }
                if (desk[i][column - row + i].getCode() == 0)
                {
                    markedGreen.add(new CheckerCell(desk[i][column - row + i]));
                    desk[i][column - row + i].changeCode(5);
                }
            }
        }
        if ((!(markedGreen.isEmpty())) && (chosen != null))
        {
            return true;
        }
        else
        {
            return false;
        }
    }
    private void eatChecker(int r, int c)
    {
        if (desk[r][c].getCode() == 3)
        {
            whiteKings--;
        }
        if (desk[r][c].getCode() == 4)
        {
            blackKings--;
        }
        desk[r][c].changeCode(0);
        if (who)
        {
            white--;
        }
        else
        {
            black--;
        }
    }
    public int makeTurn(int r, int c)
    {
        if ((r == chosen.getRow()) || (c == chosen.getColumn()))
        {
            return 2;
        }
        if (((chosen.getCode() == 1) || (chosen.getCode() == 2)))
        {
            if ((chosen.getColumn() < c - 2) || (chosen.getColumn() > c + 2)
                    || (chosen.getRow() < r - 2) || (chosen.getRow() > r + 2))
            {
                return 2;
            }
        }
        if (!mustEat)
        {
            boolean flag = true;
            ListIterator it = markedGreen.listIterator();
            while (it.hasNext())
            {
                CheckerCell obj = (CheckerCell)it.next();
                if ((obj.getRow() == r) && (obj.getColumn() == c))
                {
                    flag = false;
                    break;
                }
            }
            if (flag)
            {
                return 2;
            }
            deleteMark();
            desk[r][c].changeCode(chosen.getCode());
            desk[chosen.getRow()][chosen.getColumn()].changeCode(0);
        }
        else
        {
            boolean flag = true;
            ListIterator it = markedRed.listIterator();
            while (it.hasNext())
            {
                CheckerCell obj = (CheckerCell)it.next();
                if ((obj.getRow() == r) && (obj.getColumn() == c))
                {
                    flag = false;
                    break;
                }
            }
            if (flag == true)
            {
                return 2;
            }
            deleteMark();
            desk[r][c].changeCode(chosen.getCode());
            if ((chosen.getCode() == 1) || (chosen.getCode() == 2))
            {
                eatChecker((r + chosen.getRow()) / 2, (c + chosen.getColumn()) / 2);
            }
            else
            {
                if (r < chosen.getRow())
                {
                    for (int i = chosen.getRow() - 1; i > r; i--)
                    {
                        if (c > chosen.getColumn())
                        {
                            if (chosen.getRow() + chosen.getColumn() - i < 8)
                            {
                                if (isOpposite(desk[i][chosen.getRow() + chosen.getColumn() - i]))
                                {
                                    eatChecker(i, chosen.getRow() + chosen.getColumn() - i);
                                    break;
                                }
                            }
                        }
                        else
                        {
                            if (chosen.getColumn() - chosen.getRow() + i >= 0)
                            {
                                if (isOpposite(desk[i][chosen.getColumn() - chosen.getRow() + i]))
                                {
                                    eatChecker(i, chosen.getColumn() - chosen.getRow() + i);
                                    break;
                                }
                            }
                        }
                    }
                }
                else
                {
                    for (int i = chosen.getRow() + 1; i < r; i++)
                    {
                        if (c > chosen.getColumn())
                        {
                            if (chosen.getColumn() - chosen.getRow() + i < 8)
                            {
                                if (isOpposite(desk[i][chosen.getColumn() - chosen.getRow() + i]))
                                {
                                    eatChecker(i, chosen.getColumn() - chosen.getRow() + i);
                                    break;
                                }
                            }
                        }
                        else
                        {
                            if (chosen.getRow() + chosen.getColumn() - i >= 0)
                            {
                                if (isOpposite(desk[i][chosen.getRow() + chosen.getColumn() - i]))
                                {
                                    eatChecker(i, chosen.getRow() + chosen.getColumn() - i);
                                    break;
                                }
                            }
                        }
                    }
                }
            }
            desk[chosen.getRow()][chosen.getColumn()].changeCode(0);
            if ((who == false) && (r == 0))
            {
                desk[r][c].changeCode(3);
            }
            if ((who == true) && (r == 7))
            {
                desk[r][c].changeCode(4);
            }
            if ((whiteKings > 0) && (blackKings > 0))
            {
                startCountdown = true;
            }
            if (couldEat())
            {
                if (chooseChecker(r,c))
                {
                    deleteMark();
                    return 1;
                }
                deleteMark();
            }
        }
        numTurn++;
        if ((who == false) && (r == 0))
        {
            desk[r][c].changeCode(3);
            wKing = true;
        }
        if ((who == true) && (r == 7))
        {
            desk[r][c].changeCode(4);
            bKing = true;
        }
        if ((wKing == true) && (bKing == true))
        {
            startCountdown = true;
        }
        if (startCountdown)
        {
            countdown--;
        }
        who = !who;
        return 0;
    }
    public void deleteMark()
    {
        while (markedGreen.isEmpty() == false)
        {
            CheckerCell obj = (CheckerCell)markedGreen.poll();
            desk[obj.getRow()][obj.getColumn()].changeCode(obj.getCode());
        }
        while (markedRed.isEmpty() == false)
        {
            CheckerCell obj = (CheckerCell)markedRed.poll();
            desk[obj.getRow()][obj.getColumn()].changeCode(obj.getCode());
        }
    }
    public boolean whoseTurn()
    {
        return who;
    }
    public int gameOver()
    {
        if ((couldTurn() == false))
        {
            if (who == false)
            {
                return 2;
            }
            else
            {
                return 1;
            }
        }
        if (white == 0)
        {
            return 2;
        }
        if (black == 0)
        {
            return 1;
        }
        if (countdown == 0)
        {
            return 3;
        }
        return 0;
    }
}