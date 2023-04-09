package be;

public class Admin {

    private int adminID;

    private int userID;

    public Admin(int adminID, int userID) {
        this.adminID = adminID;
        this.userID = userID;
    }

    public Admin(int userID) {
        this.userID = userID;
    }

    public int getAdminID() {
        return adminID;
    }

    public void setAdminID(int adminID) {
        this.adminID = adminID;
    }

    public int getUserID() {
        return userID;
    }

    public void setUserID(int userID) {
        this.userID = userID;
    }

    @Override
    public String toString() {
        return "Admin{" +
                "adminID=" + adminID +
                ", userID=" + userID +
                '}';
    }
}
