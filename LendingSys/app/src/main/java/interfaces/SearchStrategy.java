package interfaces;

import model.Item;

/**
 * Interface for search strategies.
 */
public interface SearchStrategy {
  boolean matches(Item item, String query);
}
