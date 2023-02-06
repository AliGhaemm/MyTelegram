package Models;

public class Username {

    private String text;
    private int userId;
    private boolean isVisible;

    public Username (User user, String text) {
        this.userId = user.getId();
        this.text = text;
        this.isVisible = true;
    }

    public Username (int userId, String text) {
        this.userId = userId;
        this.text = text;
        this.isVisible = true;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public boolean isVisible() {
        return isVisible;
    }

    public void setVisible(boolean visible) {
        isVisible = visible;
    }

    public String toString () {
        return this.text;
    }
}
