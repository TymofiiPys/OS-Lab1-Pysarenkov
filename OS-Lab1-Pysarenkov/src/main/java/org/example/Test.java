package org.example;

import java.io.File;
import java.io.IOException;
import java.util.concurrent.TimeUnit;

public class Test {
    public static void main(String[] args) {
        ProcessBuilder builder = new ProcessBuilder();
//        builder.directory(new File("E:\\Univ\\РО\\Labs\\DS-Lab5-Pysarenkov"));
        builder.command("E:\\Univ\\РО\\Labs\\DS-Lab5-Pysarenkov\\main.exe");
        try {
            Process pr = builder.start();
            if(!pr.waitFor(5, TimeUnit.SECONDS)){
                System.out.println("still running...");
            }

        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}
