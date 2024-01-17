
package com.jasmineenriquez.casestudy;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;

public class VoucherSystem {
    private Map<String, Voucher> vouchers;

    public VoucherSystem() {
        vouchers = new HashMap<>();
    }

    public void addVoucher(String code, double amount, LocalDate expirationDate) {
        Voucher voucher = new Voucher(code, amount, expirationDate);
        vouchers.put(code, voucher);
    }

    public boolean isVoucherCodeValid(String code) {
        return vouchers.containsKey(code);
    }

    public boolean isVoucherExpired(String code) {
        Voucher voucher = vouchers.get(code);
        if (voucher != null) {
            LocalDate expirationDate = voucher.getExpirationDate();
            return expirationDate.isBefore(LocalDate.now());
        }
        return false;
    }

    public double getVoucherAmount(String code) {
        Voucher voucher = vouchers.get(code);
        if (voucher != null) {
            return voucher.getAmount();
        }
        return 0.0;
    }

    private class Voucher {
        private String code;
        private double amount;
        private LocalDate expirationDate;

        public Voucher(String code, double amount, LocalDate expirationDate) {
            this.code = code;
            this.amount = amount;
            this.expirationDate = expirationDate;
        }

        public String getCode() {
            return code;
        }

        public double getAmount() {
            return amount;
        }

        public LocalDate getExpirationDate() {
            return expirationDate;
        }
    }
}