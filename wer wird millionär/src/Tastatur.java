import java.io.*;

public class Tastatur
  // Anfang Attribute
  // Ende Attribute
{
  // Anfang Methoden
  public static String stringInput( )
  {
    String rueck;

    try
    {
      DataInput t = new DataInputStream( System.in );
      rueck = t.readLine();
    }

    catch (IOException e){return null;};
    return rueck;
  }


  public static char charInput( )
  {
    char rueck='0';

    try
    {
      DataInput t = new DataInputStream( System.in );
      rueck = t.readLine().charAt(0);
    }

    catch (IOException e){System.out.println ("Fehler!");};
    return rueck;
  }



  public static int intInput( )
  {
    boolean t=false;
    int rueck;
    String temp;

    do
    {
      temp = stringInput();

      try
      {
        rueck = Integer.parseInt(temp);
        t = true;
      }

      catch(NumberFormatException e)
      {
        t=false;
        System.out.println ("Fehler! Bitte Integer-Zahl eingeben!");
        rueck = 0;
      }

    }
    while (t ==false);

    return rueck;
  }


  public static double doubleInput( )
  {
    String temp;
    double rueck;
    boolean t=false;

    do
    {
      temp=stringInput();

      try
      {
        rueck = Double.parseDouble(temp);
        t = true;
      }

      catch(NumberFormatException e)
      {
        System.out.println ("Fehler! Bitte Fliesskommazahl eingeben!");
        rueck = 0;
      }

    }
    while (t ==false);
    return rueck;
  }
  public static long longInput() {
    String temp;
    long rueck;
    boolean t=false;

    do
    {
      temp=stringInput();

      try
      {
        rueck = Long.parseLong(temp);
        t = true;
      }

      catch(NumberFormatException e)
      {
        System.out.println ("Fehler! Bitte Fliesskommazahl eingeben!");
        rueck = 0;
      }

    }
    while (t ==false);
    return rueck;
  }

  public static float floatInput() {
    String temp;
    float rueck;
    boolean t=false;

    do
    {
      temp=stringInput();

      try
      {
        rueck = Float.parseFloat(temp);
        t = true;
      }

      catch(NumberFormatException e)
      {
        System.out.println ("Fehler! Bitte Fliesskommazahl eingeben!");
        rueck = 0;
      }

    }
    while (t ==false);
    return rueck;
  }

  public static int byteInput() {
    while(true){
      try{
        return System.in.read();
      }catch(Exception e){
        System.out.println("Bitte geben sie ein Zeichen ein!");
      }
    }

  }
  
  public static byte[] bytesInput(){
    try{
      if(System.in.available() > 0){
        return bytesInput(System.in.available());
      }else{
        return null;
      }
    }catch(Exception e){
      return null;
    }
  }
  
  public static byte[] bytesInput(int length){
    if(length <= 0){
      return null;
    }
    while(true){
      try{
        int read = 0;
        byte[] dat = new byte[length];
        read = System.in.read(dat);
        if(read == -1){
          dat = null;
        }
        else if(read != length){
          byte[] temp = dat;
          dat = new byte[read];
          for(int i = 0; i<read; i++){
            dat[i] = temp[i];
          }
        }
      }catch(Exception e){
        System.out.println("Bitte geben sie " + length + " Buchstaben ein!");
      }
    }
  }
  
  public static boolean booleanInput() {
    String temp;
    boolean rueck;
    boolean t=false;

    do
    {
      temp=stringInput();

      try
      {
        rueck = Boolean.parseBoolean(temp);
        t = true;
      }

      catch(NumberFormatException e)
      {
        System.out.println ("Fehler! Bitte Fliesskommazahl eingeben!");
        rueck = false;
      }

    }
    while (t ==false);
    return rueck;
  }

  // Ende Methoden


}