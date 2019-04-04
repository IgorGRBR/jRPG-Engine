package com.RPGE.file;

import java.io.*;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;

public class RPGEFile
{
    private String path;
    private boolean loaded;
    public ArrayList<ArrayList<String>> content;
    private int current_line;

    public RPGEFile(String p)
    {
        content = new ArrayList<>();
        path = p;
        loaded = false;
        current_line = 0;
    }

    public RPGEFile(String p, boolean l)
    {
        content = new ArrayList<>();
        path = p;
        loaded = l;
        current_line = 0;
        if (l)
        {
            this.load();
        }
    }

    public void load()
    {
        //Make sure list is empty
        content.clear();
        BufferedReader br;
        try
        {
            br = new BufferedReader(new FileReader(this.path));
            String line = br.readLine();
            while(line != null)
            {
                if (line.length() > 0 && line.charAt(0) != '#')
                {
                    ArrayList<String> split_line = new ArrayList<>(
                            Arrays.asList(line.split("\\s+")));
                    this.content.add(split_line);
                }
                line = br.readLine();
            }
            br.close();
        }
        catch (FileNotFoundException e)
        {
            e.printStackTrace();
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
    }

    public void saveChanges()
    {
        try
        {
            BufferedWriter bw = new BufferedWriter(new FileWriter(path));
            for (ArrayList<String> list : content)
            {
                if (list.size() > 0)
                {
                    for (String item : list)
                    {
                        bw.write(item + " ");
                    }
                    bw.write('\n');
                }
            }

            bw.close();
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
    }

    public ArrayList<String> getLine()
    {
        if (current_line < content.size())
        {
            return content.get(current_line++);
        }
        else
        {
            current_line = 0;
            return null;
        }
    }

}
