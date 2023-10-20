package org.example;

import java.util.Optional;
import java.util.Random;
import java.util.concurrent.TimeUnit;

public class Function {

    public class Computation {
        final static int CASE1_ATTEMPTS = 3;
        static int attempt = CASE1_ATTEMPTS;

        public static Optional<Optional<Integer>> compfunc(int n, char funcChar) {
//            switch (n) {
//                case 0:
//                    try {
//                        TimeUnit.SECONDS.sleep(2);
//                    } catch (InterruptedException ie) {
//                        return Optional.of(Optional.empty());
//                    }
//                    return Optional.of(Optional.empty());
//
//                case 1:
//                    try {
//                        TimeUnit.SECONDS.sleep(1);
//                    } catch (InterruptedException ie) {
//                        return Optional.of(Optional.empty());
//                    }
//                    attempt--;
//                    if (attempt != 0)
//                        return Optional.empty();
//                    attempt = CASE1_ATTEMPTS;
//                    return Optional.of(Optional.of(5));
//
//                default:
//            }

            switch (funcChar) {
                case 'f':
                    switch (n) {
                        case 0:
                            try {
                                TimeUnit.SECONDS.sleep(2);
                            } catch (InterruptedException ie) {
                                return Optional.of(Optional.empty());
                            }
                            return Optional.of(Optional.of(1));
                        case 1:
                            try {
                                TimeUnit.SECONDS.sleep(3);
                            } catch (InterruptedException ie) {
                                return Optional.of(Optional.empty());
                            }
                            return Optional.of(Optional.empty());
                        case 2:
                            try {
                                TimeUnit.SECONDS.sleep(1);
                            } catch (InterruptedException ie) {
                                return Optional.of(Optional.empty());
                            }
                            attempt--;
                            if(attempt != 0)
                                return Optional.empty();
                            return new Random().nextInt(3) > 0 ?
                                Optional.of(Optional.of(6)) :
                                Optional.of(Optional.of(- CASE1_ATTEMPTS + attempt));
                        case 3:
                            try {
                                TimeUnit.SECONDS.sleep(new Random().nextInt(2) + 1);
                            } catch (InterruptedException ie) {
                                return Optional.of(Optional.empty());
                            }
                            attempt--;
                            if(attempt != 0)
                                return Optional.empty();
                            return Optional.of(Optional.of(11));
                        case 7:
                            try {
                                TimeUnit.SECONDS.sleep(1);
                            } catch (InterruptedException ie) {
                                return Optional.of(Optional.empty());
                            }
                            return Optional.of(Optional.of(120));
                        case 8:
                            try {
                                TimeUnit.SECONDS.sleep(2);
                            } catch (InterruptedException ie) {
                                return Optional.of(Optional.empty());
                            }
                            return Optional.of(Optional.of(15));
                    }
                case 'g':
                    switch (n) {
                        case 0:
                            try {
                                TimeUnit.SECONDS.sleep(3);
                            } catch (InterruptedException ie) {
                                return Optional.of(Optional.empty());
                            }
                            return Optional.of(Optional.empty());
                        case 1:
                            try {
                                TimeUnit.SECONDS.sleep(2);
                            } catch (InterruptedException ie) {
                                return Optional.of(Optional.empty());
                            }
                            return Optional.of(Optional.of(1));
                        case 2:
                            try {
                                TimeUnit.SECONDS.sleep(new Random().nextInt(2) + 1);
                            } catch (InterruptedException ie) {
                                return Optional.of(Optional.empty());
                            }
                            attempt--;
                            if(attempt != 0)
                                return Optional.empty();
                            return Optional.of(Optional.of(3));
                        case 3:
                            try {
                                TimeUnit.SECONDS.sleep(3);
                            } catch (InterruptedException ie) {
                                return Optional.of(Optional.empty());
                            }
                            return Optional.of(Optional.of(13));
                        case 7:
                            try {
                                TimeUnit.SECONDS.sleep(1);
                            } catch (InterruptedException ie) {
                                return Optional.of(Optional.empty());
                            }
                            attempt--;
                            if (attempt != 0)
                                return Optional.empty();
                            return new Random().nextInt(3) > 0 ?
                                    Optional.of(Optional.of(100)) :
                                    Optional.of(Optional.of(- CASE1_ATTEMPTS + attempt));
                        case 8:
                            try {
                                TimeUnit.SECONDS.sleep(2);
                            } catch (InterruptedException ie) {
                                return Optional.of(Optional.empty());
                            }
                            return Optional.of(Optional.of(21));
                    }
            }

            return Optional.of(Optional.empty());
        }

        public static int getAttempts() {
            return attempt;
        }
    }

    public static void main(String[] args) {
        System.out.println("Computing function " + args[0] + "(x) with x = " + args[1] + "...");
        char funcChar = args[0].charAt(0);
        int x = Integer.parseInt(args[1]);
        var result = Computation.compfunc(x, funcChar);
        while (result.isEmpty()) {
            System.out.println(funcChar + "(x) - Non-critical error, retrying...");
            System.out.println((Computation.CASE1_ATTEMPTS - Computation.getAttempts()) + " non-critical errors");
            result = Computation.compfunc(x, funcChar);
        }
        if(result.get().isEmpty()){
            System.exit(-1);
        }
        System.exit(result.get().get());
    }
}
