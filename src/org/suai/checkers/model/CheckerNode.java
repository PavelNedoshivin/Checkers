package org.suai.checkers.model;
import java.util.LinkedList;
public class CheckerNode
{
    private int value;
    private CheckerCell from;
    private CheckerCell to;
    private LinkedList successors;
    private CheckerNode choice;
    private LinkedList chain;
    public CheckerNode(int v, CheckerCell f, CheckerCell t)
    {
        value = v;
        choice = null;
        if (f != null)
        {
            from = new CheckerCell(f);
        }
        if (t != null)
        {
            to = new CheckerCell(t); 
        }
        successors = new LinkedList();
        chain = new LinkedList();
    }
    public CheckerNode(CheckerNode o)
    {
        value = o.value;
        if (o.from != null)
        {
            from = new CheckerCell(o.from);
        }
        else
        {
            from = null;
        }
        if (o.to != null)
        {
            to = new CheckerCell(o.to);
        }
        else
        {
            to = null;
        }
        successors = new LinkedList(o.successors);
        chain = new LinkedList(o.chain);
    }
    public void setChoice(CheckerNode v)
    {
        choice = v;
    }
    public CheckerNode getChoice()
    {
        return choice;
    }
    public LinkedList getChain()
    {
        return chain;
    }
    public boolean hasChain()
    {
        return !chain.isEmpty();
    }
    public void addToChain(CheckerCell v)
    {
        chain.add(v);
    }
    public CheckerCell getFrom()
    {
        return from;
    }
    public void changeValue(int v)
    {
        value = v;
    }
    public CheckerCell getTo()
    {
        return to;
    }
    public void addSuccessor(CheckerNode v)
    {
        successors.add(v);
    }
    public LinkedList getSuccessors()
    {
        return successors;
    }
    public int getValue()
    {
        return value;
    }
}