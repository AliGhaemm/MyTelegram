package Models;

import Controller.*;

import javax.xml.crypto.*;
import java.io.*;

public class PhoneNumber {

    private String countryCode;
    private String mainPart;
    private boolean isVisible;

    public PhoneNumber(String countryCode, String mainPart) {
        this.countryCode = countryCode;
        this.mainPart = mainPart;
        this.isVisible = false;
    }

    public String getCountryCode() {
        return countryCode;
    }

    public void setCountryCode(String countryCode) {
        this.countryCode = countryCode;
    }

    public String getMainPart() {
        return mainPart;
    }

    public void setMainPart(String mainPart) {
        this.mainPart = mainPart;
    }

    public boolean isVisible() {
        return isVisible;
    }

    public void setVisible(boolean visible) {
        isVisible = visible;
    }

    public String toString() {
        return this.countryCode+this.mainPart;
    }

    public void showPhoneNumber (DataOutputStream outputStream) throws IOException{
        RequestHandler.connectionV(this.countryCode + " " + this.mainPart);
    }
}
