package utils;

import java.text.SimpleDateFormat;
import java.util.Random;

/**
 * TimeManager class.
 */
public class TimeManager {
  private static int currentDay = 0;
  private static final Random random = new Random(); // Static instance of Random

  public static int getCurrentDay() {
    return currentDay;
  }

  public static void advanceDay() {
    currentDay++;
  }

  public static void advanceDay(int days) {
    currentDay += days;
  }

  /**
   * Generate a random id.
   */
  public static String getRandomId() {
    long timestamp = System.currentTimeMillis();

    // Use bitwise operation to ensure non-negative result
    long randomValue = random.nextLong() & Long.MAX_VALUE;

    long uniqueId = timestamp + randomValue;

    return Long.toString(uniqueId).substring(0, 6);
  }

  //might not need this
  public static String formatDate(java.util.Date date) {
    SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    return dateFormat.format(date);
  }
}
