package org.brainfork.brainfuck;

import java.io.*;
import java.net.URISyntaxException;
import java.util.*;

public class Main {

    static Memory memory = new Memory();
    static Stack<Integer> callStack = new Stack<>();
    static String script;
    static int ip = 0;

    public static void main(String[] args) {


        //System.err.println("Brainfuck interpreter by Licho");

        if(args.length != 1) {
            String progName = "bf";
            try {
                progName = new File(Main.class.getProtectionDomain().getCodeSource().getLocation().toURI().getPath()).getName();
            } catch (URISyntaxException e) {
                e.printStackTrace();
            }
            System.err.println("Usage: java -jar " + progName + " script.bf");
            System.exit(1);
        }

        try {
            BufferedReader file = new BufferedReader(new FileReader(args[0]));
            StringBuilder sb = new StringBuilder();
            String line;
            while((line = file.readLine()) != null) {
                sb.append(line);
            }
            script = sb.toString();
            file.close();
        } catch(FileNotFoundException ex) {
            System.err.println("File not found!");
            System.exit(1);
        } catch(IOException ex) {
            System.err.println("Cannot read file!");
            System.exit(1);
        }

        while(ip < script.length()) {
            switch(script.charAt(ip)) {
                case '>':
                    memory.Next();
                    break;
                case '<':
                    memory.Prev();
                    break;
                case '+':
                    memory.Inc();
                    break;
                case '-':
                    memory.Dec();
                    break;
                case '.':
                    System.out.print(memory.Get());
                    break;
                case ',':
                    try {
                        memory.Set((char)System.in.read());
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    break;
                case '[':
                    callStack.push(ip);
                    break;
                case ']':
                    int tempIp = 0;
                    try {
                        tempIp = callStack.pop() -1;
                    } catch (EmptyStackException ex) {
                        System.err.println("Call stack empty at IP " + ip + ". Cannot jump to loop start.");
                        System.exit(1);
                    }
                    if(memory.Get() != 0) {
                        ip = tempIp;
                    }
                    break;
                default:
                    break;
            }
            ++ip;
        }

    }
}
