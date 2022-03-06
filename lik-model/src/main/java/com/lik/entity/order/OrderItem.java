package com.lik.entity.order;

import com.baomidou.mybatisplus.annotation.*;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * <p>
 * 订单明细
 * </p>
 *
 * @author lik
 * @since 2022-02-04
 */
@TableName("bs_order_item")
public class OrderItem implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
     * 创建时间
     */
    @TableField(fill = FieldFill.INSERT)
    private LocalDateTime createTime;

    /**
     * 最后更新时间
     */
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private LocalDateTime updateTime;

    /**
     * 订单
     */
    private Long orderId;

    /**
     * 产品id
     */
    private Long productId;

    /**
     * 产品名称
     */
    private String productName;

    /**
     * 产品编码
     */
    private String productCode;

    /**
     * 数量
     */
    private BigDecimal quantity;

    /**
     * 单价
     */
    private BigDecimal price;

    /**
     * 行总金额
     */
    private BigDecimal rowAmount;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
    public LocalDateTime getCreateTime() {
        return createTime;
    }

    public void setCreateTime(LocalDateTime createTime) {
        this.createTime = createTime;
    }
    public LocalDateTime getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(LocalDateTime updateTime) {
        this.updateTime = updateTime;
    }
    public Long getOrderId() {
        return orderId;
    }

    public void setOrderId(Long orderId) {
        this.orderId = orderId;
    }
    public Long getProductId() {
        return productId;
    }

    public void setProductId(Long productId) {
        this.productId = productId;
    }
    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }
    public String getProductCode() {
        return productCode;
    }

    public void setProductCode(String productCode) {
        this.productCode = productCode;
    }
    public BigDecimal getQuantity() {
        return quantity;
    }

    public void setQuantity(BigDecimal quantity) {
        this.quantity = quantity;
    }
    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }
    public BigDecimal getRowAmount() {
        return rowAmount;
    }

    public void setRowAmount(BigDecimal rowAmount) {
        this.rowAmount = rowAmount;
    }

    @Override
    public String toString() {
        return "OrderItem{" +
            "id=" + id +
            ", createTime=" + createTime +
            ", updateTime=" + updateTime +
            ", orderId=" + orderId +
            ", productId=" + productId +
            ", productName=" + productName +
            ", productCode=" + productCode +
            ", quantity=" + quantity +
            ", price=" + price +
            ", rowAmount=" + rowAmount +
        "}";
    }
}
