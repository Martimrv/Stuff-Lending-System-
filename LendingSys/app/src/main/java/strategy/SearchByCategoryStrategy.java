package strategy;

import interfaces.SearchStrategy;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import model.Item;


/**
 * SearchByCategoryStrategy class.
 */
public class SearchByCategoryStrategy implements SearchStrategy {
  @Override
  public boolean matches(Item item, String query) {
    Pattern pattern = Pattern.compile(query, Pattern.CASE_INSENSITIVE);
    Matcher matcher = pattern.matcher(item.getCategory());
    return matcher.find();
  }
}