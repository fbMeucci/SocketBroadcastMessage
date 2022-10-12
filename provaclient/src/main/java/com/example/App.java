package com.example;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class App 
{
    public static void main( String[] args ) throws Exception
    {
        Socket s = new Socket("localhost", 3000);
        
        // per parlare
        PrintWriter pr = new PrintWriter(s.getOutputStream(), true);
        
        // per ascoltare
        BufferedReader br = new BufferedReader(new InputStreamReader(s.getInputStream()));

        // per la tastiera
        BufferedReader tastiera = new BufferedReader(new InputStreamReader(System.in));

        pr.println("Eccomi");
        System.out.println(br.readLine()); // rivevo: benvenuti dammi il tuo peso
        pr.println(tastiera.readLine()); // leggo da tastiera il peso e lo invio
        System.out.println(br.readLine()); // ricevo: dammi l'altezza
        pr.println(tastiera.readLine()); // leggo da tastiera l'altezza e la invio
        System.out.println(br.readLine()); //rivevo il BMI 
        pr.println("Grazie e ciao");
        
        s.close();
    }
}
