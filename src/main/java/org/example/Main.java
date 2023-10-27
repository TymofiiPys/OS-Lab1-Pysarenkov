package org.example;

import java.io.*;
import java.util.AbstractMap;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;
import java.util.concurrent.*;
import java.util.function.Supplier;

public class Main {
    private Supplier<Integer> functionExec(final int x, final char funcChar) {
        if (funcChar != 'f' && funcChar != 'g') {
            return new Supplier<Integer>() {
                @Override
                public Integer get() {
                    return null;
                }
            };
        }
        return new Supplier<Integer>() {
            @Override
            public Integer get() {
                ProcessBuilder builder = new ProcessBuilder();

                builder.command("java", "Function.java", String.valueOf(funcChar), String.valueOf(x));
                builder.directory(new File("src/main/java/org/example"));

                try {
                    Process process = builder.start();

                    String[] attempts = {""};
                    Thread outputReader = new Thread(() -> {
                        try (BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()))) {
                            String line;
                            while ((line = reader.readLine()) != null) {
                                if (line.contains("non-critical errors")) {
                                    attempts[0] = line;
                                    continue;
                                }
                                System.out.println(line);
                            }
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    });
                    outputReader.start();

                    if (!process.waitFor(5, TimeUnit.SECONDS)) {
                        process.destroyForcibly();
                        if (attempts[0].isEmpty()) {
                            attempts[0] = "0 non-critical errors";
                        }
                        System.out.println("Computation time limit exceeded on function "
                                + funcChar + "(x) with " + attempts[0]);
                        System.out.println("Exited with code " + process.exitValue());
                        return -1;
                    }

                    outputReader.join();

                    int exitCode = process.exitValue();
                    if (exitCode < 0) {
                        String s = "Critical error on function " + funcChar + "(x)";
                        if (exitCode == -3) {
                            s += " due to reaching non-critical errors amount limit of 3";
                        } else {
                            s += " due to function being undefined on this argument";
                        }
                        System.out.println(s);
                    } else {
                        System.out.println(funcChar + "(x) = " + exitCode);
                    }
                    return exitCode;
                } catch (IOException | InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
        };
    }

    private int GCD(int f, int g) {
        if (g == 0) {
            return f;
        }
        return GCD(g, f % g);
    }

    public void startManager() {
        /* Values of x, on which functions MAY return success:
        0; 1; 2; 3; 7; 8 */
        Scanner input = new Scanner(System.in);
        HashMap<String, Integer> hm = new HashMap<>();

        while (true) {
            System.out.print("Enter integer x or exit: ");
            String xString = input.nextLine();
            if (xString.equals("exit")) {
                break;
            }

            int x;
            try {
                x = Integer.parseInt(xString);
            } catch (NumberFormatException e) {
                System.out.println("The input is not an integer");
                continue;
            }

            int fRes = -1, gRes = -1;
            CompletableFuture<Integer> f = null;
            CompletableFuture<Integer> g = null;
            boolean isInMapF = false;
            boolean isInMapG = false;
            if (hm.containsKey('f' + "; " + x)) {
                fRes = hm.get('f' + "; " + x);
                isInMapF = true;
            } else {
                f = CompletableFuture.supplyAsync(functionExec(x, 'f'));
            }
            if (hm.containsKey('g' + "; " + x)) {
                gRes = hm.get('g' + "; " + x);
                isInMapG = true;
            } else {
                g = CompletableFuture.supplyAsync(functionExec(x, 'g'));
            }

            try {
                if (!isInMapF) {
                    fRes = f.get();
                    hm.put('f' + "; " + x, fRes);
                }
                if (!isInMapG) {
                    gRes = g.get();
                    hm.put('g' + "; " + x, gRes);
                }

                if (fRes < 0 || gRes < 0) {
                    System.out.println("GCD cannot be computed");
                } else {
                    System.out.println("GCD(f(x); g(x)) = " + GCD(fRes, gRes));
                }
            } catch (InterruptedException | ExecutionException e) {
                e.printStackTrace();
            }
        }

    }

    public static void main(String[] args) {
        Main m = new Main();
        m.startManager();
    }
}