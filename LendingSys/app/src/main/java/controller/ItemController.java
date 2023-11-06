package controller;

import interfaces.SearchStrategy;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import model.Contract;
import model.Item;
import model.Member;
import utils.FileUtil;
import utils.TimeManager;
import view.ConsoleView;

/**
 * ItemController class.
 */
public class ItemController {
  private List<Item> itemList;
  ConsoleView consoleView;
  private ContractController contractController;

  /**
   * Constructor.
   */
  public ItemController(ConsoleView consoleView, ContractController contractController) {
    this.consoleView = consoleView;
    this.contractController = contractController;
    itemList = new ArrayList<>();
  }

  /**
   * Add item.
   */
  public void addItem(Member curMember) {
    consoleView.displayPrompt("name:");
    final String name = consoleView.getUserInput();

    consoleView.displayPrompt("category");
    final String category = consoleView.getUserInput();

    consoleView.displayPrompt("Points");
    String points = consoleView.getUserInput();

    consoleView.displayPrompt("description");
    String description = consoleView.getUserInput();

    String id = TimeManager.getRandomId();
    double doublePoints = 0;
    try {
      doublePoints = Double.parseDouble(points);
    } catch (NumberFormatException e) {
      System.out.println("Cannot convert a string to a double " + e.getMessage());
    }

    Item item = new Item(id, curMember.getMemberId(), name, category, doublePoints, description, 1);
    itemList.add(item);

    curMember.setPoints(curMember.getPoints() + 100);
    FileUtil.saveObjectsToFile(itemList, "item.json");
    consoleView.displayMessage("add item success");
  }

  /**
   * Remove item.
   */
  public void removeItem() {
    consoleView.displayPrompt("Please enter the Item ID you want to delete:");
    String id = consoleView.getUserInput();

    boolean isRemove = itemList.removeIf(item -> item.getItemId().equals(id));
    if (isRemove) {
      contractController.removeContractsByItemId(id);
      FileUtil.saveObjectsToFile(itemList, "item.json");
      consoleView.displayMessage("item and associated contracts deleted successfully");
    } else {
      consoleView.displayMessage("item deletion failed");
    }
  }

  /**
   * Find item.
   */
  public void findItem() {
    consoleView.displayPrompt("Please enter the item name");
    String itemName = consoleView.getUserInput();

    System.out.printf("%-20s %-15s %-15s %-10s %-20s %-10s%n",
        "ID", "Name", "Category", "Points", "Description", "Status");
    System.out.println("----------------------------------------------------------");

    // print item list
    for (Item item : itemList) {
      Pattern pattern = Pattern.compile(itemName, Pattern.CASE_INSENSITIVE);
      Matcher matcher = pattern.matcher(item.getName());
      if (matcher.find()) {
        System.out.printf("%-20s %-15s %-15s %-10.2f %-20s %-10d%n",
            item.getItemId(), item.getName(), item.getCategory(),
            item.getPoints(), item.getDescription(), item.getStatus());
      }
    }
  }

  /**
   * Search items.
   */
  public void searchItems(String prompt, SearchStrategy strategy) {
    consoleView.displayPrompt(prompt);
    String query = consoleView.getUserInput();
    System.out.printf("%-20s %-15s %-15s %-10s %-20s %-10s%n",
            "ID", "Name", "Category", "Points", "Description", "Status");
    System.out.println("----------------------------------------------------------");
    for (Item item : itemList) {
      if (strategy.matches(item, query)) {
        System.out.printf("%-20s %-15s %-15s %-10.2f %-20s %-10d%n",
                item.getItemId(), item.getName(), item.getCategory(),
                item.getPoints(), item.getDescription(), item.getStatus());
      }
    }
  }

  /**
   * Update item.
   */
  public void updateItem() {
    consoleView.displayPrompt("Please enter the id of the item you want to modify:");
    String id = consoleView.getUserInput();

    consoleView.displayPrompt("name");
    String name = consoleView.getUserInput();

    consoleView.displayPrompt("category");
    String category = consoleView.getUserInput();

    consoleView.displayPrompt("Points");
    String points = consoleView.getUserInput();

    consoleView.displayPrompt("description");
    String description = consoleView.getUserInput();
    boolean flag = true;
    double doublePoints = 0;
    try {
      doublePoints = Double.parseDouble(points);
    } catch (NumberFormatException e) {
      System.out.println("Cannot convert a string to a double: " + e.getMessage());
    }
    for (Item item : itemList) {
      if (item.getItemId().equals(id)) {
        flag = false;
        item.setName(name);
        item.setCategory(category);
        item.setPoints(doublePoints);
        item.setDescription(description);
        break;
      }
    }

    if (flag) {
      consoleView.displayPrompt("The modification failed, "
          + "and the corresponding product was not found");
    } else {
      FileUtil.saveObjectsToFile(itemList, "item.json");
      consoleView.displayPrompt("Modified successfully");
    }
  }

  /**
   * Create contract for item.
   */
  public void createContractForItem(Member borrower) {
    consoleView.displayPrompt("Enter the Item ID you want to borrow:");
    String itemId = consoleView.getUserInput();

    Item itemToBorrow = findItemById(itemId);
    if (itemToBorrow == null) {
      consoleView.displayMessage("Item not found.");
      return;
    }

    consoleView.displayPrompt("Enter the number of days you want to borrow:");
    int days = Integer.parseInt(consoleView.getUserInput());

    String contractId = TimeManager.getRandomId();
    int startDay = TimeManager.getCurrentDay(); // Using TimeManager to get the current day
    int endDay = startDay + days; // Start day + number of days

    Contract newContract = new Contract(contractId, borrower.getMemberId(),
        itemToBorrow, startDay, startDay, endDay, 1, days);
    // Assuming the Item class has a method to add a contract
    itemToBorrow.addContract(newContract);
    consoleView.displayMessage("Contract created successfully!");
  }

  /**
   * View contracts for item.
   */
  public void viewContractsForItem() {
    consoleView.displayPrompt("Enter the Item ID:");
    String itemId = consoleView.getUserInput();

    Item item = findItemById(itemId);
    if (item == null) {
      consoleView.displayMessage("Item not found.");
      return;
    }

    List<Contract> contracts = item.getContracts();
    for (Contract contract : contracts) {
      // Display contract details. You can format this as you like.
      System.out.println("Contract ID: " + contract.getContractId());
      System.out.println("Borrower ID: " + contract.getBorrowerId());
      System.out.println("Create Date: " + contract.getCreateDate());
      System.out.println("Status: " + contract.getStatus());
      System.out.println("Days: " + contract.getDays());
      System.out.println("-------------------------------");
    }
  }

  private Item findItemById(String itemId) {
    for (Item item : itemList) {
      if (item.getItemId().equals(itemId)) {
        return item;
      }
    }
    return null;
  }

  public List<Item> getItemList() {
    return new ArrayList<>(itemList);
  }

  /**
   * Show item list.
   */
  public void showItemList() {
    System.out.printf("%-20s %-15s %-15s %-10s %-20s %-10s%n",
        "ID", "Name", "Category", "Points", "Description", "Status");
    System.out.println("----------------------------------------------------------");

    // print item list
    for (Item item : itemList) {
      System.out.printf("%-20s %-15s %-15s %-10.2f %-20s %-10d%n",
          item.getItemId(), item.getName(), item.getCategory(),
          item.getPoints(), item.getDescription(), item.getStatus());
    }
  }

  /**
   * Add item directly.
   */
  public void addItemDirectly(Item item) {
    itemList.add(item);
    FileUtil.saveObjectsToFile(itemList, "item.json");
  }

  public void loadItemList(List<Item> list) {
    itemList = list;
  }
}
