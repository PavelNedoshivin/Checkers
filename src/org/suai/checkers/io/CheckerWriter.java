package org.suai.checkers.io;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
public class CheckerWriter
{
    public void clearHistory() throws IOException
    {
        FileOutputStream fout = new FileOutputStream("history.txt", false);
        BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(fout));
        writer.close();
    }
    public void saveHistory(String filename, String s) throws IOException
    {
        FileOutputStream fout = new FileOutputStream(filename, true);
        BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(fout));
        writer.write(s + "\n");
        writer.close();
    }
    public void saveSettings(String filename, 
            boolean vsComp, int diff, boolean compTurn) throws IOException
    {
        FileOutputStream fout = new FileOutputStream(filename, false);
        BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(fout));
        writer.write(Boolean.toString(vsComp) + "\n");
        writer.write(Integer.toString(diff) + "\n");
        writer.write(Boolean.toString(compTurn) + "\n");
        writer.close();
    }
    public void clearLog(String logname) throws IOException
    {
        FileOutputStream fout = new FileOutputStream(logname, false);
        BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(fout));
        writer.close();
    }
    public void saveBoard(String filename, int turn, int count, String s) throws IOException
    {
        FileOutputStream fout = new FileOutputStream(filename, true);
        BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(fout));
        writer.write(Integer.toString(turn) + "\n");
        writer.write(Integer.toString(count) + "\n");
        writer.write(s);
        writer.close();
    }
    public void cancelTurnHistory(String filename) throws IOException
    {
        CheckerWriter cw = new CheckerWriter();
        FileInputStream file = new FileInputStream(filename);
        BufferedReader reader = new BufferedReader(new InputStreamReader(file));
        int counter = 0;
        while (reader.readLine() != null)
        {
            counter++;
        }
        reader.close();
        file = new FileInputStream(filename);
        reader = new BufferedReader(new InputStreamReader(file));
        FileOutputStream file2 = new FileOutputStream("tmp.txt");
        BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(file2));
        for (int i = 0; i < counter - 1; i++)
        {
            String s = reader.readLine();
            writer.write(s);
            writer.write("\n");
        }
        reader.close();
        writer.close();
        cw.clearHistory();
        file = new FileInputStream("tmp.txt");
        reader = new BufferedReader(new InputStreamReader(file));
        file2 = new FileOutputStream(filename);
        writer = new BufferedWriter(new OutputStreamWriter(file2));
        for (int i = 0; i < counter - 1; i++)
        {
            String s = reader.readLine();
            writer.write(s);
            writer.write("\n");
        }
        reader.close();
        writer.close();
    }
}