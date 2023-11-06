package model;


/**
 * Contract class.
 */
public class Contract {
  private String contractId;
  private String borrowerId;
  private Item item;
  private int createDate;
  private int startDay;
  private int endDay;
  private int status;
  private int days;

  /**
   * Constructor.
   */
  public Contract(String contractId, String borrowerId, Item item,
      int createDate, int startDay, int endDay, int status, int days) {
    this.contractId = contractId;
    this.borrowerId = borrowerId;
    this.item = item.copy(); // Store a copy of the item
    this.createDate = createDate;
    this.startDay = startDay;
    this.endDay = endDay;
    this.status = status;
    this.days = days;
  }

  public String getContractId() {
    return contractId;
  }

  public void setContractId(String contractId) {
    this.contractId = contractId;
  }

  public String getBorrowerId() {
    return borrowerId;
  }

  public void setBorrowerId(String borrowerId) {
    this.borrowerId = borrowerId;
  }

  public int getCreateDate() {
    return createDate;
  }

  public void setCreateDate(int createDate) {
    this.createDate = createDate;
  }

  public int getStatus() {
    return status;
  }

  public void setStatus(int status) {
    this.status = status;
  }

  public Item getItem() {
    return item.copy(); // Return a copy of the item
  }

  public void setItem(Item item) {
    this.item = item.copy(); // Store a copy of the item
  }

  public int getDays() {
    return days;
  }

  public void setDays(int days) {
    this.days = days;
  }

  public int getStartDay() {
    return startDay;
  }

  public void setStartDay(int startDay) {
    this.startDay = startDay;
  }

  public int getEndDay() {
    return endDay;
  }

  public void setEndDay(int endDay) {
    this.endDay = endDay;
  }
}
