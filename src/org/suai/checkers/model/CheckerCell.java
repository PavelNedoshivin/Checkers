package org.suai.checkers.model;
public class CheckerCell
{
    private final int row;
    private final int column;
    private int code;
    public CheckerCell(int cod, int r, int c)
    {
        code = cod;
        row = r;
        column = c;
    }
    public CheckerCell(CheckerCell o)
    {
        code = o.getCode();
        row = o.getRow();
        column = o.getColumn();
    }
    public void changeCode(int value)
    {
        code = value;
    }
    public int getRow()
    {
        return row;
    }
    public int getColumn()
    {
        return column;
    }
    public int getCode()
    {
        return code;
    }
    @Override
    public String toString()
    {
        return Integer.toString(code);
    }
}