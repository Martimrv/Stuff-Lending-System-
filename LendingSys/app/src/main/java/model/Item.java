package model;

import java.util.ArrayList;
import java.util.List;

/**
 * Item class.
 */
public class Item {
  private String itemId;
  private String memberId;
  private String name;
  private String category;
  private double points;
  private String description;
  private int status;
  private List<Contract> contracts;

  /**
   * Constructor.
   */
  public Item(String itemId, String memberId, String name, String category,
      double points, String description, int status) {
    this.itemId = itemId;
    this.memberId = memberId;
    this.name = name;
    this.category = category;
    this.points = points;
    this.description = description;
    this.status = status;
    this.contracts = new ArrayList<>();
  }

  public String getItemId() {
    return itemId;
  }

  public void setItemId(String itemId) {
    this.itemId = itemId;
  }

  public String getMemberId() {
    return memberId;
  }

  public void setMemberId(String memberId) {
    this.memberId = memberId;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getCategory() {
    return category;
  }

  public void setCategory(String category) {
    this.category = category;
  }

  public String getDescription() {
    return description;
  }

  public void setDescription(String description) {
    this.description = description;
  }

  public int getStatus() {
    return status;
  }

  public void setStatus(int status) {
    this.status = status;
  }

  public double getPoints() {
    return points;
  }

  public void setPoints(double points) {
    this.points = points;
  }

  public List<Contract> getContracts() {
    return new ArrayList<>(contracts);
  }

  public void addContract(Contract contract) {
    this.contracts.add(contract);
  }

  public void removeContract(Contract contract) {
    this.contracts.remove(contract);
  }

  /**
   * Copy the item.
   */
  public Item copy() {
    Item newItem = new Item(this.itemId, this.memberId, this.name, this.category,
        this.points, this.description, this.status);
    for (Contract contract : this.contracts) {
      newItem.addContract(contract);
    }
    return newItem;
  }
}