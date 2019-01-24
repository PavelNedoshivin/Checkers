package org.suai.checkers.model;
import java.io.IOException;
import java.util.LinkedList;
import java.util.ListIterator;
public class CheckersAI
{
    private CheckersBoard desk;
    private CheckerNode root;
    private int difficulty;
    public CheckersAI(CheckersBoard v, int diff)
    {
        desk = v;
        root = new CheckerNode(0, null, null);
        difficulty = diff;
    }
    private CheckerNode calculateFunction(int level, CheckerNode parent) throws IOException
    {
        CheckerNode choice = null;
        if ((level / 2 == difficulty) && (level % 2 == 1))
        {
            return choice;
        }
        makeNodes(parent);
        desk.deleteMark();
        desk.couldEat();
        int max = -100;
        int min = 100;
        LinkedList l = parent.getSuccessors();
        ListIterator it = l.listIterator();
        while (it.hasNext())
        {
            CheckerNode obj = (CheckerNode)it.next();
            desk.chooseChecker(obj.getFrom().getRow(), obj.getFrom().getColumn());
            if (obj.hasChain())
            {
                LinkedList chain = obj.getChain();
                ListIterator ch = chain.listIterator();
                while (ch.hasNext())
                {
                    CheckerCell o = (CheckerCell)ch.next();
                    desk.makeTurn(o.getRow(), o.getColumn());
                    desk.couldEat();
                    desk.chooseChecker(o.getRow(), o.getColumn());
                }
                desk.deleteMark();
            }
            else
            {
                desk.makeTurn(obj.getTo().getRow(), obj.getTo().getColumn());
            }
            desk.saveBoard("log.txt");
            calculateFunction(level + 1, obj);
            desk.cancelTurn();
            desk.couldEat();
        }
        it = l.listIterator();
        while (it.hasNext())
        {
            CheckerNode obj = (CheckerNode)it.next();
            if (level % 2 == 0)
            {
                if (obj.getValue() > max)
                {
                    max = obj.getValue();
                    choice = obj;
                    parent.setChoice(obj);
                    parent.changeValue(max);
                }
            }
            else
            {
                if (obj.getValue() < min)
                {
                    min = obj.getValue();
                    choice = obj;
                    parent.setChoice(obj);
                    parent.changeValue(min);
                }
            }
        }
        desk.deleteMark();
        return choice;
    }
    public void makeTurn() throws IOException
    {
        calculateFunction(0, root);
        CheckerNode choice = root.getChoice();
        desk.couldEat();
        desk.chooseChecker(choice.getFrom().getRow(), choice.getFrom().getColumn());
        if (choice.hasChain())
        {
            LinkedList chain = choice.getChain();
            ListIterator ch = chain.listIterator();
            while (ch.hasNext())
            {
                CheckerCell obj = (CheckerCell)ch.next();
                desk.makeTurn(obj.getRow(), obj.getColumn());
                desk.couldEat();
                desk.chooseChecker(obj.getRow(), obj.getColumn());
            }
        }
        else
        {
            desk.makeTurn(choice.getTo().getRow(), choice.getTo().getColumn());
        }
        desk.deleteMark();
    }
    private void makeNodes(CheckerNode parent) throws IOException
    {
        LinkedList nodes = desk.getNodes();
        if (nodes == null)
        {
            return;
        }
        ListIterator it = nodes.listIterator();
        while (it.hasNext())
        {
            CheckerNode cn = (CheckerNode)it.next();
            parent.addSuccessor(cn);
        }
    }
}