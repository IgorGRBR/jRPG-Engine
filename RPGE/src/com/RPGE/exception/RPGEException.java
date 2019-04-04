package com.RPGE.exception;

// Exceptions!

public class RPGEException extends Exception
{
    public String text;

    public RPGEException()
    {
        text = "An exception has occured!";
    }

    public RPGEException(String str)
    {
        text = str;
    }

    @Override
    public String toString()
    {
        return "RPGE: " + text;
    }

    @Override
    public void printStackTrace()
    {
        System.out.println(this.toString());
        System.exit(1);
    }
}
