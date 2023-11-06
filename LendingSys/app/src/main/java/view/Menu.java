package view;

import controller.ContractController;
import controller.ItemController;
import controller.MemberController;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Scanner;
import model.Member;
import model.MenuItem;
import strategy.SearchByCategoryStrategy;
import strategy.SearchByNameStartsWithStrategy;
import utils.TimeManager;

/**
 * Menu class.
 */
public class Menu {
  public static final String blue = "\u001B[34m";
  public static final String cyan = "\u001B[36m";

  private final Scanner scanner = new Scanner(System.in, StandardCharsets.UTF_8.name());

  private final MemberController memberController;
  private Member curMember;
  private final ItemController itemController;
  private final ContractController contractController;

  /**
   * Menu constructor.
   */
  public Menu(final MemberController memberController, ConsoleView consoleView, Member curMember,
                final ItemController itemController, final ContractController contractController) {
    this.memberController = Objects.requireNonNull(memberController, 
    "MemberController must not be null");
    this.itemController = Objects.requireNonNull(itemController, "ItemController must not be null");
    this.contractController = Objects.requireNonNull(contractController, 
    "ContractController must not be null");
  }


  /**
   * Create menu.
   */
  public MenuItem createMenu() {
    MenuItem menu = new MenuItem("Menu", false);

    MenuItem signIn = new MenuItem("Sign in", () -> {
      memberController.registerMember();
    }, false);
    MenuItem login = new MenuItem("Log in", () -> {
      Member loginMember = memberController.loginMember();
      if (!Objects.isNull(loginMember)) {
        curMember = loginMember;
      }
    }, false);

    MenuItem advanceTime = new MenuItem("Advance Time by 8 Days", () -> {
      // contractController.changeContractDate();
      TimeManager.advanceDay(8);
      System.out.println("Time advanced by 8 days. Current day: " 
          + TimeManager.getCurrentDay());
      contractController.showAllExpiredContracts();
    }, true);

    MenuItem memberManage = new MenuItem("Member Manage", false);

    MenuItem itemManage = new MenuItem("Item manage", false);

    MenuItem contract = new MenuItem("Contract manage", false);

    final MenuItem searchItem = new MenuItem("Search Item", false);

    MenuItem logOut = new MenuItem("Log out", () -> {
      curMember = null;
    }, true);

    menu.addSubMenu(signIn);
    menu.addSubMenu(login);
    menu.addSubMenu(advanceTime);
    menu.addSubMenu(memberManage);
    menu.addSubMenu(itemManage);
    menu.addSubMenu(contract);
    menu.addSubMenu(logOut);
    itemManage.addSubMenu(searchItem);

    memberManage.addSubMenu(new MenuItem("Member List", () -> {
      memberController.showMemberList(itemController.getItemList());
    }, false));
    memberManage.addSubMenu(new MenuItem("Find Member", () -> {
      memberController.findMember();
    }, false));
    memberManage.addSubMenu(new MenuItem("Show Member", () -> {
      memberController.showMemberInfo(itemController.getItemList(),
          contractController.getAllContracts());
    }, true));
    memberManage.addSubMenu(new MenuItem("Update Member Info", () -> {
      memberController.updateMemberInfo(curMember);
    }, true));

    memberManage.addSubMenu(new MenuItem("Delete Member", () -> {
      memberController.deleteMember();
    }, true));

    itemManage.addSubMenu(new MenuItem("Show Item", () -> {
      itemController.showItemList();
    }, false));

    itemManage.addSubMenu(new MenuItem("Find Item", () -> {
      itemController.findItem();
    }, false));

    searchItem.addSubMenu(new MenuItem("Search Item Name Start With", () -> {
      itemController.searchItems("Please enter the item name start with",
          new SearchByNameStartsWithStrategy());
    }, false));


    searchItem.addSubMenu(new MenuItem("Search Item By Category", () -> {
      itemController.searchItems("Please enter the item Category", new SearchByCategoryStrategy());
    }, false));

    itemManage.addSubMenu(new MenuItem("Add Item", () -> {
      itemController.addItem(curMember);
    }, true));
    itemManage.addSubMenu(new MenuItem("Update Item", () -> {
      itemController.updateItem();
    }, true));
    itemManage.addSubMenu(new MenuItem("Delete Item", () -> {
      itemController.removeItem();
    }, true));


    contract.addSubMenu(new MenuItem("Create Contract", () -> {
      contractController.addContract(curMember, itemController.getItemList());
    }, true));
    contract.addSubMenu(new MenuItem("Show Contract", () -> {
      contractController.showAllContractList();
    }, false));

    contract.addSubMenu(new MenuItem("Expire Contract", () -> {
      contractController.showAllExpiredContracts();
    }, true));

    return menu;
  }

  /**
   * Display menu.
   */
  public void displayMenu(MenuItem menu) {
    while (true) {
      System.out.println(blue + "==== " + menu.getLabel() + " ====");

      List<MenuItem> displayableMenuItems = new ArrayList<>();
      for (MenuItem subMenuItem : menu.getSubMenuItems()) {
        if (("Sign in".equals(subMenuItem.getLabel()) || "Log in".equals(subMenuItem.getLabel()))
            && !Objects.isNull(curMember)) {
          continue; // Skip "Sign in" and "Log in" if a member is logged in
        }
        if ("Log out".equals(subMenuItem.getLabel()) && Objects.isNull(curMember)) {
          continue; // Skip "Log out" if no member is logged in
        }
        displayableMenuItems.add(subMenuItem);
      }

      for (int i = 0; i < displayableMenuItems.size(); i++) {
        MenuItem subMenuItem = displayableMenuItems.get(i);
        System.out.println(blue + (i + 1) + ". " + subMenuItem.getLabel());
      }

      System.out.println("0. Quit");
      System.out.print(cyan + "Please select operation: ");

      String inputLine = scanner.nextLine();
      try {
        int choice = Integer.parseInt(inputLine);

        if (choice == 0) {
          return;
        } else if (choice >= 1 && choice <= displayableMenuItems.size()) {
          MenuItem selectedMenuItem = displayableMenuItems.get(choice - 1);
          if (selectedMenuItem.getIsNeedLogin() && Objects.isNull(curMember)) {
            System.out.println("Please log in");
          } else {
            if (!selectedMenuItem.getSubMenuItems().isEmpty()) {
              displayMenu(selectedMenuItem);
            } else {
              selectedMenuItem.execute();
              if ("Log in".equals(selectedMenuItem.getLabel())
                  || "Log out".equals(selectedMenuItem.getLabel())) {
                menu = createMenu(); // Refresh the menu after login or logout
                continue; // Continue displaying the refreshed menu
              }
            }
          }
        } else {
          System.out.println("Invalid selection, please re-enter.");
        }
      } catch (NumberFormatException e) {
        System.out.println("Invalid input. Please enter a number.");
      }
    }
  }
}
