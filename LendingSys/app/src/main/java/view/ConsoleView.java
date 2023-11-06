package view;

import java.nio.charset.StandardCharsets;
import java.util.Scanner;

/**
 * ConsoleView class.
 */
public class ConsoleView {

  private Scanner scanner;
  public final String green;
  public final String yellow;

  /**
   * Constructor.
   */
  public ConsoleView() {
    scanner = new Scanner(System.in, StandardCharsets.UTF_8.name()); // Specify charset
    green = "\u001B[32m";
    yellow = "\u001B[33m";
  }

  public void displayMessage(String message) {
    System.out.println(green + message);
  }

  public void displayPrompt(String prompt) {
    System.out.println(yellow + prompt);
  }

  public String getUserInput() {
    return scanner.nextLine();
  }

  /**
   * Get user input as an integer.
   */
  public int getUserInputInt() {
    String input = scanner.nextLine();
    try {
      return Integer.parseInt(input);
    } catch (NumberFormatException e) {
      System.out.println("Cannot resolve input as an integer.");
    }
    return 0;
  }

  public void closeScanner() {
    scanner.close();
  }
}
