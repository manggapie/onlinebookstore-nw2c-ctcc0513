
package com.jasmineenriquez.casestudy;

// PasswordStrength class
public class PasswordStrength {
    private boolean lengthValid;
    private boolean uppercaseValid;
    private boolean lowercaseValid;
    private boolean numericValid;
    private boolean specialValid;
    private int uppercaseCount;
    private int lowercaseCount;
    private int numericCount;
    private int specialCount;

    public boolean isLengthValid() {
        return lengthValid;
    }

    public void setLengthValid(boolean lengthValid) {
        this.lengthValid = lengthValid;
    }

    public boolean hasUppercase() {
        return uppercaseValid;
    }

    public void setUppercaseValid(boolean uppercaseValid) {
        this.uppercaseValid = uppercaseValid;
    }

    public boolean hasLowercase() {
        return lowercaseValid;
    }

    public void setLowercaseValid(boolean lowercaseValid) {
        this.lowercaseValid = lowercaseValid;
    }

    public boolean hasNumeric() {
        return numericValid;
    }

    public void setNumericValid(boolean numericValid) {
        this.numericValid = numericValid;
    }

    public boolean hasSpecial() {
        return specialValid;
    }

    public void setSpecialValid(boolean specialValid) {
        this.specialValid = specialValid;
    }

    public int getUppercaseCount() {
        return uppercaseCount;
    }

    public void setUppercaseCount(int uppercaseCount) {
        this.uppercaseCount = uppercaseCount;
    }

    public int getLowercaseCount() {
        return lowercaseCount;
    }

    public void setLowercaseCount(int lowercaseCount) {
        this.lowercaseCount = lowercaseCount;
    }

    public int getNumericCount() {
        return numericCount;
    }

    public void setNumericCount(int numericCount) {
        this.numericCount = numericCount;
    }

    public int getSpecialCount() {
        return specialCount;
    }

    public void setSpecialCount(int specialCount) {
        this.specialCount = specialCount;
    }
}