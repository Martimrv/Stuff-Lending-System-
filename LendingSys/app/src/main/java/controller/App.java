package controller;

import com.alibaba.fastjson.TypeReference;
import java.lang.reflect.Type;
import java.util.List;
import java.util.Objects;
import model.Contract;
import model.Item;
import model.Member;
import model.MenuItem;
import utils.FileUtil;
import view.ConsoleView;
import view.Menu;

/**
 * App class.
 */
public class App {

  /**
   * Main method.
   */
  public static void main(String[] args) {
    ConsoleView consoleView = new ConsoleView();
    MemberController memberController = new MemberController(consoleView);
    ContractController contractController = new ContractController(consoleView, memberController);
    final ItemController itemController = new ItemController(consoleView, contractController);

    // Initialize Members
    Type memberListType = new TypeReference<List<Member>>() {
    }.getType();
    List<Member> loadedMembers = FileUtil.loadObjectsFromFile("members.json", memberListType);

    Member m1 = null;
    Member m2 = null;
    Member m3 = null;

    if (Objects.isNull(loadedMembers)) {
      m1 = new Member(memberController.generateMemberId(),
          "M1Username", "M1Password", "M1@email.com", "M1Phone", 500);
      m2 = new Member(memberController.generateMemberId(),
          "M2Username", "M2Password", "M2@email.com", "M2Phone", 100);
      m3 = new Member(memberController.generateMemberId(),
          "M3Username", "M3Password", "M3@email.com", "M3Phone", 100);
      // Add members to MemberController
      memberController.addMemberDirectly(m1);
      memberController.addMemberDirectly(m2);
      memberController.addMemberDirectly(m3);
    } else {
      memberController.loadMemberList(loadedMembers);
      for (Member m : loadedMembers) {
        if (m.getUserName().equals("M1Username")) {
          m1 = m;
        } else if (m.getUserName().equals("M2Username")) {
          m2 = m;
        } else if (m.getUserName().equals("M3Username")) {
          m3 = m;
        }
      }
    }
    Type itemType = new TypeReference<List<Item>>() {
    }.getType();
    List<Item> loadItems = FileUtil.loadObjectsFromFile("item.json", itemType);
    Item i2 = null;
    if (Objects.isNull(loadItems)) {
      // Initialize Items for M1 using m1's actual member ID
      Item i1 = new Item("I1", m1.getMemberId(), "Item1", "Category1", 50, "Description1", 1);
      i2 = new Item("I2", m1.getMemberId(), "Item2", "Category2", 10, "Description2", 1);

      itemController.addItemDirectly(i1);
      itemController.addItemDirectly(i2);
    } else {
      itemController.loadItemList(loadItems);
      for (Object obj : loadItems) {
        Item i = (Item) obj;
        if (i.getName().equals("I2")) {
          i2 = i;
        }
      }
    }
    Type contractType = new TypeReference<List<Item>>() {
    }.getType();
    List<Contract> loadContracList = FileUtil.loadObjectsFromFile("contract.json", contractType);

    if (Objects.isNull(loadContracList)) {
      // Initialize Contract for M3
      Contract contract = new Contract("C1", m3.getMemberId(),
          i2, 5, 5, 7, 1, 3);
      i2.setStatus(2); // 1. Not borrowed 2. Borrowed

      contractController.addContractDirectly(contract);
    } else {
      contractController.loadContractList(loadContracList);
    }

    Menu menu = new Menu(memberController, consoleView, null,
        itemController, contractController);

    consoleView.displayMessage("Welcome to the lending system");

    MenuItem rootMenu = menu.createMenu();
    menu.displayMenu(rootMenu);
  }
}
