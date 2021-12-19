package info.behzadian;

import java.util.List;

public class StaffList {

    private List<Staff> staff;

    public StaffList() {
    }

    public StaffList(List<Staff> staff) {
        this.staff = staff;
    }

    public List<Staff> getStaff() {
        return staff;
    }

    public void setStaff(List<Staff> staff) {
        this.staff = staff;
    }
}
