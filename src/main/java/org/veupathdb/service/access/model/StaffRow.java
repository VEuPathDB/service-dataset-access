package org.veupathdb.service.access.model;

public class StaffRow extends UserRow
{
  private int staffId;
  private boolean isOwner;

  public int getStaffId() {
    return staffId;
  }

  public void setStaffId(int staffId) {
    this.staffId = staffId;
  }

  public boolean isOwner() {
    return isOwner;
  }

  public void setOwner(boolean owner) {
    isOwner = owner;
  }
}
