package strategy;

import interfaces.SearchStrategy;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import model.Item;


/**
 * SearchByNameStartsWithStrategy class.
 */
public class SearchByNameStartsWithStrategy implements SearchStrategy {
  @Override
  public boolean matches(Item item, String query) {
    Pattern pattern = Pattern.compile("^" + Pattern.quote(query) + ".*", Pattern.CASE_INSENSITIVE);
    Matcher matcher = pattern.matcher(item.getName());
    return matcher.find();
  }
}