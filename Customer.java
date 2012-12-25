package controller;

/**
 * Created with IntelliJ IDEA.
 * User: root
 * Date: 10/27/12
 * Time: 7:17 PM
 * To change this template use File | Settings | File Templates.
 */
public class Customer {
    private String cardNum;
    private String accNum;
    private String currentValue;
    private String name;
    private String family;
    private String passWd;
    public String lockCounter;

    public String getLockCounter() {
        return lockCounter;
    }

    public void setLockCounter(String lockCounter) {
        this.lockCounter = lockCounter;
    }

    public String getCardNum() {
        return cardNum;
    }

    public void setCardNum(String cardNum) {
        this.cardNum = cardNum;
    }

    public String getAccNum() {
        return accNum;
    }

    public void setAccNum(String accNum) {
        this.accNum = accNum;
    }

    public String getCurrentValue() {
        return currentValue;
    }

    public void setCurrentValue(String currentValue) {
        this.currentValue = currentValue;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getFamily() {
        return family;
    }

    public void setFamily(String family) {
        this.family = family;
    }

    public String getPassWd() {
        return passWd;
    }

    public void setPassWd(String passWd) {
        this.passWd = passWd;
    }
}
