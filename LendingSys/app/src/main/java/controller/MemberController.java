package controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Random;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import model.Contract;
import model.Item;
import model.Member;
import utils.FileUtil;
import view.ConsoleView;

/**
 * ItemController class.
 */
public class MemberController {

  private List<Member> memberList;
  ConsoleView consoleView;
  private final Random random = new Random(); // Create a single instance of Random

  public MemberController(ConsoleView consoleView) {
    memberList = new ArrayList<>();
    this.consoleView = consoleView;
  }

  /**
   * Constructor.
   */
  public MemberController(MemberController other) {
    // Deep copy the properties of 'other' into this object.
    this.memberList = new ArrayList<>(other.memberList);
    this.consoleView = other.consoleView;
  }

  /**
   * Generate a random member id.
   */
  String generateMemberId() {
    String characters = "ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
    StringBuilder sb = new StringBuilder();
    for (int i = 0; i < 6; i++) {
      sb.append(characters.charAt(random.nextInt(characters.length())));
    }
    return sb.toString();
  }

  /**
   * Register member.
   */
  public void registerMember() {
    consoleView.displayPrompt("Please enter your username:");
    final String name = consoleView.getUserInput();
    consoleView.displayPrompt("Please enter your password:");
    final String password = consoleView.getUserInput();
    consoleView.displayPrompt("Please enter your email:");
    String email = consoleView.getUserInput();
    consoleView.displayPrompt("Please enter your phone number:");
    String phoneNumber = consoleView.getUserInput();
    if (isEmailOrPhoneUsed(email, phoneNumber)) {
      consoleView.displayMessage("Email or phone number already in use. Registration failed.");
      return;
    }

    String id = generateMemberId(); // Use the method here
    Member member = new Member(id, name, password, email, phoneNumber);
    memberList.add(member);
    FileUtil.saveObjectsToFile(memberList, "members.json");
    consoleView.displayMessage("Registered successfully!");
  }

  private boolean isEmailOrPhoneUsed(String email, String phoneNumber) {
    return memberList.stream()
        .anyMatch(member -> member.getEmail().equals(email)
            || member.getPhoneNumber().equals(phoneNumber));
  }

  /**
   * Login member.
   */
  public Member loginMember() {
    consoleView.displayPrompt("Please enter your username:");
    String name = consoleView.getUserInput();
    consoleView.displayPrompt("Please enter your password:");
    String password = consoleView.getUserInput();
    Member curMember = null;
    for (Member member : memberList) {
      if (member.getUserName().equals(name) && member.getPassword().equals(password)) {
        curMember = member;
        break;
      }
    }
    if (Objects.isNull(curMember)) {
      consoleView.displayMessage("The login fails because the user name or password is incorrect.");
    } else {
      consoleView.displayMessage("Login successful!");
    }
    return curMember;
  }

  /**
   * Show member list.
   */
  public void showMemberList(List<Item> itemList) {
    System.out.printf("%-20s %-15s %-15s %-10s%n", "Member ID", "Username", "itemCount", "Points");

    // print Member info
    for (Member member : memberList) {
      int itemCount = itemCount(itemList, member.getMemberId());
      System.out.printf("%-20s %-15s %-15d %-10.2f%n",
          member.getMemberId(), member.getUserName(), itemCount, member.getPoints());
    }
  }

  private int itemCount(List<Item> itemList, String memberIdToMatch) {
    int itemCount = 0;

    for (Item item : itemList) {
      if (item.getMemberId().equals(memberIdToMatch)) {
        itemCount++;
      }
    }

    return itemCount;
  }

  /**
   * Delete member.
   */
  public void deleteMember() {
    consoleView.displayPrompt("Please enter your memberId:");
    String id = consoleView.getUserInput();

    boolean isRemove = memberList.removeIf(member -> member.getMemberId().equals(id));
    if (isRemove) {
      consoleView.displayMessage("Member deleted successfully");
      FileUtil.saveObjectsToFile(memberList, "members.json");
    } else {
      consoleView.displayMessage("Member deletion failed");
    }
  }

  public List<Member> getMemberList() {
    return new ArrayList<>(memberList);
  }

  /**
   * Update member info.
   */
  public void updateMemberInfo(Member curMember) {
    consoleView.displayPrompt("Please enter the email address you want to update:");
    String email = consoleView.getUserInput();

    consoleView.displayPrompt("Please enter the phone address you want to update:");
    String phone = consoleView.getUserInput();

    curMember.setEmail(email);
    curMember.setPhoneNumber(phone);
    FileUtil.saveObjectsToFile(memberList, "members.json");
    consoleView.displayMessage("Update successful!");
  }

  /**
   * Show member info.
   */
  public void showMemberInfo(List<Item> itemList, List<Contract> contractList) {
    System.out.printf("%-20s %-15s %-20s %-20s %-20s %-20s %-20s %-15s %-10s %-10s%n",
        "Member ID", "Username", "Email", "Phone", "itemName",
        "category", "points", "borrowerName", "startDay", "endDay");
    System.out.println("------------------------------------------------------------"
        + "--------------------------------------------");

    for (Member member : memberList) {
      boolean hasAssociatedItem = false; // Flag to check if member has any associated items

      for (Item item : itemList) {
        if (item.getMemberId().equals(member.getMemberId())) {
          hasAssociatedItem = true; // Set the flag to true if an associated item is found

          Member borrow = null;
          int startDay = 0;
          int endDay = 0;
          for (Contract contract : contractList) {
            if (contract.getItem().getItemId().equals(item.getItemId())) {
              borrow = findMemberById(contract.getBorrowerId());
              startDay = contract.getStartDay();
              endDay = contract.getEndDay();
            }
          }
          System.out.printf("%-20s %-15s %-20s  %-20s %-20s %-20s %-20.2f %-15s %-10d %-10d%n",
              member.getMemberId(), member.getUserName(), member.getEmail(),
              member.getPhoneNumber(), item.getName(), item.getCategory(),
              item.getPoints(), borrow != null ? borrow.getUserName() : "", startDay, endDay);
        }
      }

      // If no associated item is found, print member info
      if (!hasAssociatedItem) {
        System.out.printf("%-20s %-15s %-20s  %-20s %-20s %-20s %-20s %-15s %-10s %-10s%n",
            member.getMemberId(), member.getUserName(), member.getEmail(),
            member.getPhoneNumber(), "None", "None", "None", "None", "0", "0");
      }
    }
  }

  Member findMemberById(String id) {
    for (Member member : memberList) {
      if (member.getMemberId().equals(id)) {
        return member;
      }
    }
    return null;
  }

  /**
   * Find member.
   */
  public void findMember() {
    consoleView.displayPrompt("Please enter the member name");
    String memberName = consoleView.getUserInput();

    System.out.printf("%-20s %-15s %-20s %-15s %-10s%n", "Member ID", "Username", "Email",
        "Phone", "Points");
    System.out.println("----------------------------------------------------------");

    for (Member member : memberList) {
      Pattern pattern = Pattern.compile(memberName, Pattern.CASE_INSENSITIVE);
      Matcher matcher = pattern.matcher(member.getUserName());
      if (matcher.find()) {
        System.out.printf("%-20s %-15s %-20s %-15s %-10.2f%n",
            member.getMemberId(), member.getUserName(), member.getEmail(),
            member.getPhoneNumber(), member.getPoints());
      }
    }
  }

  // Method to directly add a member to the memberList
  public void addMemberDirectly(Member member) {
    memberList.add(member);
    FileUtil.saveObjectsToFile(memberList, "members.json");
  }

  public void loadMemberList(List<Member> list) {
    memberList = new ArrayList<>(list);
  }

}
