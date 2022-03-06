package com.lik.enums;

/**
 * 订单状态
 */
public enum OrderState {

    SAVE(0, "已保存"),
    SUBMIT(1, "已提交"),
    CANCEL(2, "已取消"),
    ;

    private Integer val;

    private String memo;

    OrderState(Integer val, String memo) {
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
