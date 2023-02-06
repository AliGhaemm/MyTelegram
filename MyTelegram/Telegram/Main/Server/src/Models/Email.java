package Models;

import java.security.*;

public class Email {

    private String text;
    private int userId;
    private boolean isVisible;

    public Email (User user, String text) {
        this.userId = user.getId();
        this.text = text;
        this.isVisible = false;
    }

    public Email(String text, int userId) {
        this.text = text;
        this.userId = userId;
        this.isVisible = false;
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

    public String toString() { return this.text; }
}
