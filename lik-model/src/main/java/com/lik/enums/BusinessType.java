package com.lik.enums;

public enum BusinessType {

    ORDER(1, "订单")
    ;

    private Integer val;

    private String memo;

    BusinessType(Integer val, String memo) {
        this.val = val;
        this.memo = memo;
    }

    public Integer getVal() {
        return this.val;
    }

    public String getMemo() {
        return this.memo;
    }
}
