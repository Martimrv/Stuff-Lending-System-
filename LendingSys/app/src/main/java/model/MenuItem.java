package model;

import java.util.ArrayList;
import java.util.List;

/**
 * MenuItem class.
 */
public class MenuItem {
  private String label;
  private List<MenuItem> subMenuItems;
  private Runnable action;
  private Boolean isNeedLogin;

  /**
   * Constructor.
   */
  public MenuItem(String label, Boolean isNeedLogin) {
    this.label = label;
    this.subMenuItems = new ArrayList<>();
    this.isNeedLogin = isNeedLogin;
  }

  /**
   * Constructor.
   */
  public MenuItem(String label, Runnable action, Boolean isNeedLogin) {
    this.label = label;
    this.action = action;
    this.subMenuItems = new ArrayList<>();
    this.isNeedLogin = isNeedLogin;
  }

  public void addSubMenu(MenuItem subMenuItem) {
    subMenuItems.add(subMenuItem);
  }

  /**
   * Execute the action.
   */
  public void execute() {
    if (action != null) {
      action.run();
    }
  }

  public String getLabel() {
    return label;
  }

  public List<MenuItem> getSubMenuItems() {
    return new ArrayList<>(subMenuItems);
  }

  public void setLabel(String label) {
    this.label = label;
  }

  public void setSubMenuItems(List<MenuItem> subMenuItems) {
    this.subMenuItems = new ArrayList<>(subMenuItems);
  }

  public Runnable getAction() {
    return action;
  }

  public void setAction(Runnable action) {
    this.action = action;
  }

  public Boolean getIsNeedLogin() {
    return isNeedLogin;
  }

  public void setIsNeedLogin(Boolean isNeedLogin) {
    this.isNeedLogin = isNeedLogin;
  }
    
}
