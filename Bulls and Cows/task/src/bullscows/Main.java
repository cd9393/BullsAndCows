package bullscows;

import java.util.Random;
import java.util.Scanner;
import java.util.regex.Pattern;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        String secretCode = null;

        while(secretCode == null) {
            int lengthOfCode;
            System.out.println("Please, enter the secret code's length:");
            String input = scanner.nextLine();
            if (Pattern.matches("[0-9]+",input)) {
                lengthOfCode = Integer.parseInt(input);
            } else {
                System.out.println("Error: " + input + " isn't a valid number");
                break;
            }

            System.out.println("Input the number of possible symbols in the code:");
            String numberOfInputsResponse = scanner.nextLine();
            int numberOfSymbols;
            if (Pattern.matches("[0-9]+", numberOfInputsResponse)){
                numberOfSymbols = Integer.parseInt(numberOfInputsResponse);
                if (lengthOfCode > numberOfSymbols) {
                    System.out.println("Error: can't generate a secret number with a length greater than " + numberOfSymbols + " because there aren't enough unique digits.");
                    break;
                }else if (lengthOfCode <= 0) {
                    System.out.println("Error: Length of code has to be greater than 0");
                    break;
                } else if (numberOfSymbols > 36) {
                    System.out.println("Error: maximum number of possible symbols in the code is 36 (0-9, a-z).");
                    break;
                }else {
                    secretCode = generateSecretCode(lengthOfCode, numberOfSymbols);
                    outputSecretCode(secretCode,numberOfSymbols);
                }
            } else {
                System.out.println("Error: " + numberOfInputsResponse + " is not a valid number!");
                break;
            }

        }

        if (secretCode != null) {
            System.out.println("Okay, let's start a game!");
            boolean gameRunning = true;
            int turn = 1;
            while(gameRunning) {
                gameRunning = playGame(turn, secretCode);
                turn++;
            }

            System.out.println("Congratulations! You guessed the secret code.");
        }

    }

    public static void outputSecretCode(String secretCode, int numberOfSymbols) {
        StringBuilder output = new StringBuilder();
        output.append("The secret code is prepared: ");

        for (int i = 0; i < secretCode.length(); i++){
            output.append('*');
        }
        if(numberOfSymbols <= 10) {
            output.append(" (0-" + (numberOfSymbols - 1) + ")");
        } else {
            output.append(" (0-9, a-" + Character.forDigit(numberOfSymbols - 1,numberOfSymbols) + ")");
        }

        System.out.println(output.toString());
    }

    public static boolean playGame(int turn, String secretCode) {
        System.out.println("Turn " + turn + ":");
        Scanner scanner = new Scanner(System.in);
        String input = scanner.next();

        int cows = 0;
        int bulls = 0;

        for (int i = 0; i < secretCode.length(); i++){
            if (input.charAt(i) == secretCode.charAt(i)) {
                bulls++;
            } else if (secretCode.contains(Character.toString(input.charAt(i)))) {
                cows++;
            }
        }

        if (cows > 0 && bulls > 0) {
            System.out.println("Grade: " + bulls + " bull(s) and " + cows + " cow(s).");
        } else if ( cows > 0 && bulls == 0) {
            System.out.println("Grade: " + cows + " cow(s).");
        } else if (cows == 0 && bulls > 0) {
            System.out.println("Grade: " + bulls + " bull(s).");
        } else {
            System.out.println("Grade: None.");
        }

        return bulls == secretCode.length() ? false:true;
    }

    public static String generateSecretCode(int length, int numberOfSymbols) {
            Random random = new Random();
            StringBuilder secretCode = new StringBuilder();

            while(secretCode.length() < length) {
                int randomDigit = random.nextInt(numberOfSymbols - 1);
                String symbol = Character.toString(Character.forDigit(randomDigit,numberOfSymbols));
                if (secretCode.indexOf(symbol) < 0) {
                    secretCode.append(symbol);
                }
            }
            return secretCode.toString();
    }
}
