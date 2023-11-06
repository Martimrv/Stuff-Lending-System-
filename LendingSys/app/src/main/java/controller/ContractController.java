package controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import model.Contract;
import model.Item;
import model.Member;
import utils.FileUtil;
import utils.TimeManager;
import view.ConsoleView;

/**
 * ContractController class.
 */
public class ContractController {
  private List<Contract> contractList;
  ConsoleView consoleView;
  private MemberController memberController;
  Map<Integer, String> statusMap = new HashMap<>();

  /**
   * Constructor.
   */
  public ContractController(ConsoleView consoleView, MemberController memberController) {
    contractList = new ArrayList<>();
    this.consoleView = consoleView;
    this.memberController = memberController;
    statusMap.put(1, "Placed an order");
    statusMap.put(3, "Completed");
  }

  /**
   * Constructor.
   */
  public ContractController(ContractController other) {
    // Deep copy the properties of 'other' into this object.
    this.contractList = new ArrayList<>(other.contractList);
    this.consoleView = other.consoleView;
    this.memberController = new MemberController(other.memberController);
    this.statusMap = new HashMap<>(other.statusMap);
  }

  /**
   * Add contract.
   */
  public void addContract(Member curMember, List<Item> itemList) {
    consoleView.displayPrompt("Please enter the id of item");
    String itemId = consoleView.getUserInput();

    consoleView.displayPrompt("Please, enter a start day: ");
    final int startDay = consoleView.getUserInputInt();

    consoleView.displayPrompt("Please, enter an end day: ");
    final int endDay = consoleView.getUserInputInt();

    consoleView.displayPrompt("Please enter the total number of loan days: ");
    final int days = consoleView.getUserInputInt();

    Item choiceItem = null;
    for (Item item : itemList) {
      if (item.getItemId().equals(itemId)) {
        choiceItem = item;
        break;
      }
    }

    if (Objects.isNull(choiceItem)) {
      consoleView.displayMessage("The item id does not exist");
      return;
    }

    boolean flag = false;
    for (Contract contract : contractList) {
      if (contract.getItem().getItemId().equals(itemId)) {
        if (contract.getStartDay() <= startDay && contract.getEndDay() >= startDay
            || contract.getStartDay() <= endDay && contract.getEndDay() >= endDay) {
          flag = true;
          break;
        }
      }
    }

    if (flag) {
      consoleView.displayMessage("This item has been lent out");
      return;
    }

    double totalCreditsRequired = choiceItem.getPoints() * days;
    if (curMember.getPoints() < totalCreditsRequired) {
      consoleView.displayMessage("Insufficient user points");
      return;
    }

    final String borrowerId = curMember.getMemberId();
    String id = TimeManager.getRandomId();
    int currentDate = TimeManager.getCurrentDay(); // Get the current day from TimeManager
    // int startDay = currentDate; // Start day is the current day
    // int endDay = startDay + days; // End day is start day + number of days

    Contract contract = new Contract(id, borrowerId, choiceItem, currentDate,
        startDay, endDay, 1, days);
    contractList.add(contract);
    choiceItem.setStatus(2);
    FileUtil.saveObjectsToFile(contractList, "contract.json");
    // Member lender = memberController.findMemberById(choiceItem.getMemberId());
    // curMember.setPoints(curMember.getPoints() - totalCreditsRequired);
    // lender.setPoints(lender.getPoints() + totalCreditsRequired);
    consoleView.displayPrompt("Contract creation success");
  }

  /**
   * Return contract.
   */
  public void expiringContract(Member curMember) {
    List<Contract> contracts = this.getContracts(curMember);
    System.out.printf("%-20s %-20s %-20s %-10s%n",
        "Contract ID", "itemName", "days", "Status");
    System.out.println(
        "---------------------------------------------------------------"
            + "-----------------------------");

    int currentDay = TimeManager.getCurrentDay(); // Get the current day from TimeManager

    for (Contract contract : contracts) {
      // Check if the contract's end day is before the current day
      if (contract.getEndDay() < currentDay) {
        System.out.printf("%-20s %-20s %-20d %-10s%n",
            contract.getContractId(), contract.getItem().getName(), contract.getDays(),
            statusMap.get(contract.getStatus()));
      }
    }
  }

  /**
   * Return contract.
   */
  public List<Contract> getContracts(Member curMember) {
    List<Contract> list = new ArrayList<>();

    for (Contract contract : contractList) {
      if (contract.getBorrowerId().equals(curMember.getMemberId())) {
        list.add(contract);
      }
    }

    return list;

  }

  public List<Contract> getAllContracts() {
    return new ArrayList<>(contractList);
  }

  /**
   * Return contract.
   */
  public void showContractList(Member curMember) {
    System.out.println("Showing contracts for member ID: " + curMember.getMemberId());
    List<Contract> contracts = this.getContracts(curMember);
    System.out.printf("%-20s %-20s %-20s %-20s %-20s %-20s %-10s%n",
        "Contract ID", "memberName", "email", "itemName", "Start Day", "End Day", "Status");
    System.out.println(
        "-----------------------------------------------"
            + "-----------------------------------------------------------------");

    for (Contract contract : contracts) {
      System.out.printf("%-20s %-20s %-20s %-20s %-20d %-20d %-10s%n",
          contract.getContractId(), curMember.getUserName(), curMember.getEmail(),
          contract.getItem().getName(),
          contract.getStartDay(), contract.getEndDay(), statusMap.get(contract.getStatus()));
    }
  }

  public void removeContractsByItemId(String itemId) {
    contractList.removeIf(contract -> contract.getItem().getItemId().equals(itemId));
    FileUtil.saveObjectsToFile(contractList, "contract.json");
  }

  /**
   * Return contract.
   */
  public void addContractDirectly(Contract contract) {
    contractList.add(contract);
    FileUtil.saveObjectsToFile(contractList, "contract.json");
  }

  public void loadContractList(List<Contract> list) {
    contractList = list;
  }

  /**
   * Return contract.
   */
  public void showAllContractList() {
    System.out.println("Showing all contracts");
    List<Contract> contracts = this.getAllContracts();
    System.out.printf("%-20s %-20s %-20s %-20s %-20s %-20s %-10s%n",
        "Contract ID", "Borrower Name", "Item Name", "Start Day", "End Day", "Days", "Status");
    System.out.println(
        "-----------------------------------------------"
            + "-----------------------------------------------------------------");

    for (Contract contract : contracts) {
      Member borrower = memberController.findMemberById(contract.getBorrowerId());
      System.out.printf("%-20s %-20s %-20s %-20d %-20d %-20d %-10s%n",
          contract.getContractId(), borrower.getUserName(), contract.getItem().getName(),
          contract.getStartDay(), contract.getEndDay(), contract.getDays(),
          statusMap.get(contract.getStatus()));
    }
  }

  /**
   * Return contract.
   */
  public void showAllExpiredContracts() {
    System.out.println("Showing all expired contracts");
    List<Contract> contracts = this.getAllContracts();
    System.out.printf("%-20s %-20s %-20s %-20s %-20s %-10s%n",
        "Contract ID", "Borrower ID", "Item Name", "Start Day", "End Day", "Status");
    System.out.println(
        "-----------------------------------------------"
            + "-------------------------------------------------------------");

    int currentDay = TimeManager.getCurrentDay(); // Get the current day from TimeManager

    for (Contract contract : contracts) {
      // Check if the contract's end day is before the current day
      if (contract.getEndDay() < currentDay) {
        double totalCreditsRequired = contract.getItem().getPoints() * contract.getDays();
        Member borrow = memberController.findMemberById(contract.getBorrowerId());
        Member lender = memberController.findMemberById(contract.getItem().getMemberId());
        borrow.setPoints(borrow.getPoints() - totalCreditsRequired);
        lender.setPoints(lender.getPoints() + totalCreditsRequired);
        System.out.printf("%-20s %-20s %-20s %-20d %-20d %-10s%n",
            contract.getContractId(), contract.getBorrowerId(), contract.getItem().getName(),
            contract.getStartDay(), contract.getEndDay(), statusMap.get(contract.getStatus()));
      }
    }
  }

  /**
   * Return contract.
   */
  public void changeContractDate() {
    consoleView.displayPrompt("Please enter the id of contract");
    String contractId = consoleView.getUserInput();

    Contract curContract = null;
    for (Contract contract : contractList) {
      if (contract.getContractId().equals(contractId)) {
        curContract = contract;
      }
    }

    if (curContract == null) {
      consoleView.displayMessage("Contract not found.");
      return;
    }

    consoleView.displayPrompt("Please, enter day: ");
    int day = consoleView.getUserInputInt();

    Member borrow = memberController.findMemberById(curContract.getBorrowerId());
    final Member lender = memberController.findMemberById(curContract.getItem().getMemberId());

    double totalCreditsRequired = curContract.getItem().getPoints() * day;
    if (borrow.getPoints() < totalCreditsRequired) {
      consoleView.displayMessage("Insufficient user points");
      return;
    }
    curContract.setDays(curContract.getDays() + day);
    curContract.setEndDay(curContract.getEndDay() + day);
    borrow.setPoints(borrow.getPoints() - totalCreditsRequired);
    lender.setPoints(lender.getPoints() + totalCreditsRequired);

    consoleView.displayMessage("Successfully modified borrowing days");
  }

}
