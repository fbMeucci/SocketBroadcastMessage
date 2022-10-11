package com.example;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

public class App 
{
    public static void main( String[] args ) throws Exception
    {
        ServerSocket ss = new ServerSocket(3000);
        System.out.println("Server in ascolto sulla porta 3000");
        Socket s = ss.accept();
        System.out.println("Client connesso");

        InputStreamReader in = new InputStreamReader(s.getInputStream());
        BufferedReader br = new BufferedReader(in);
        String peso = br.readLine();
        System.out.println("Client peso: " + peso);
        String altezza = br.readLine();
        System.out.println("Client altezza: " + altezza);
        Double bmi = Double.valueOf(peso) / (Double.valueOf(altezza) * Double.valueOf(altezza));

        PrintWriter pr = new PrintWriter(s.getOutputStream());
        pr.println("BMI: " + bmi);
        pr.flush();

        String strF = br.readLine();
        System.out.println("Client: " + strF);

        s.close();
        ss.close();

    }
}
