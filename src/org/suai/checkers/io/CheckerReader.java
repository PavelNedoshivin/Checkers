package org.suai.checkers.io;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.LinkedList;
public class CheckerReader
{
    private int numTurn;
    private int countdown;
    public LinkedList loadHistory(String filename) throws IOException
    {
        LinkedList res = new LinkedList();
        FileInputStream file;
        try
        {
            file = new FileInputStream(filename);
        }
        catch (IOException e)
        {
            return null;
        }
        BufferedReader reader = new BufferedReader(new InputStreamReader(file));
        int counter = 0;
        while (reader.readLine() != null)
        {
            counter++;
        }
        if (counter == 0)
        {
            return null;
        }
        reader.close();
        file = new FileInputStream(filename);
        reader = new BufferedReader(new InputStreamReader(file));
        for (int i = 0; i < counter; i++)
        {
            String s = reader.readLine();
            res.add(s);
        }
        reader.close();
        return res;
    }
    public Object[] loadSettings(String filename) throws IOException
    {
        Object[] res = new Object[3];
        FileInputStream file;
        try
        {
            file = new FileInputStream(filename);
        }
        catch(Exception e)
        {
            return null;
        }
        BufferedReader reader = new BufferedReader(new InputStreamReader(file));
        String s = reader.readLine();
        res[0] = Boolean.parseBoolean(s);
        s = reader.readLine();
        res[1] = Integer.parseInt(s);
        s = reader.readLine();
        res[2] = Boolean.parseBoolean(s);
        reader.close();
        return res;
    }
    public String loadBoard(String filename) throws IOException
    {
        StringBuilder res = new StringBuilder(100);
        FileInputStream file;
        try
        {
            file = new FileInputStream(filename);
        }
        catch (IOException e)
        {
            return null;
        }
        BufferedReader reader = new BufferedReader(new InputStreamReader(file));
        int counter = 0;
        while (reader.readLine() != null)
        {
            counter++;
        }
        if (counter == 0)
        {
            return null;
        }
        reader.close();
        file = new FileInputStream(filename);
        reader = new BufferedReader(new InputStreamReader(file));
        for (int i = 0; i < counter; i++)
        {
            String s = reader.readLine();
            if (i == counter - 10)
            {
                numTurn = Integer.parseInt(s);
            }
            if (i == counter - 9)
            {
                countdown = Integer.parseInt(s);
            }
            if (i >= counter - 8)
            {
                res.append(s);
            }
        }
        reader.close();
        return res.toString();
    }
    public void cancelTurn(String logname) throws IOException
    {
        CheckerWriter cw = new CheckerWriter();
        FileInputStream file = new FileInputStream(logname);
        BufferedReader reader = new BufferedReader(new InputStreamReader(file));
        int counter = 0;
        while (reader.readLine() != null)
        {
            counter++;
        }
        reader.close();
        file = new FileInputStream(logname);
        reader = new BufferedReader(new InputStreamReader(file));
        FileOutputStream file2 = new FileOutputStream("tmp.txt");
        BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(file2));
        for (int i = 0; i < counter - 10; i++)
        {
            String s = reader.readLine();
            writer.write(s);
            writer.write("\n");
        }
        reader.close();
        writer.close();
        cw.clearLog(logname);
        file = new FileInputStream("tmp.txt");
        reader = new BufferedReader(new InputStreamReader(file));
        file2 = new FileOutputStream(logname);
        writer = new BufferedWriter(new OutputStreamWriter(file2));
        for (int i = 0; i < counter - 10; i++)
        {
            String s = reader.readLine();
            writer.write(s);
            writer.write("\n");
        }
        reader.close();
        writer.close();
    }
    public int getTurn()
    {
        return numTurn;
    }
    public int getCountdown()
    {
        return countdown;
    }
}