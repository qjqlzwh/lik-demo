package com.lik.entity.product;

import com.baomidou.mybatisplus.annotation.*;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * <p>
 * 库存日志
 * </p>
 *
 * @author lik
 * @since 2022-02-13
 */
@TableName("bs_product_inventory_log")
public class ProductInventoryLog implements Serializable {

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
     * 产品id
     */
    private Long productId;

    /**
     * 仓库id
     */
    private Long warehouseId;

    /**
     * 目标id
     */
    private Long targetId;

    /**
     * 目标明细id
     */
    private Long targetItemId;

    /**
     * 目标类型
     */
    private Integer targetType;

    /**
     * 数量 正数+ 负数-
     */
    private BigDecimal quantity;

    /**
     * 备注
     */
    private String memo;

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
    public Long getProductId() {
        return productId;
    }

    public void setProductId(Long productId) {
        this.productId = productId;
    }
    public Long getWarehouseId() {
        return warehouseId;
    }

    public void setWarehouseId(Long warehouseId) {
        this.warehouseId = warehouseId;
    }
    public Long getTargetId() {
        return targetId;
    }

    public void setTargetId(Long targetId) {
        this.targetId = targetId;
    }
    public Long getTargetItemId() {
        return targetItemId;
    }

    public void setTargetItemId(Long targetItemId) {
        this.targetItemId = targetItemId;
    }
    public Integer getTargetType() {
        return targetType;
    }

    public void setTargetType(Integer targetType) {
        this.targetType = targetType;
    }
    public BigDecimal getQuantity() {
        return quantity;
    }

    public void setQuantity(BigDecimal quantity) {
        this.quantity = quantity;
    }
    public String getMemo() {
        return memo;
    }

    public void setMemo(String memo) {
        this.memo = memo;
    }

    @Override
    public String toString() {
        return "ProductInventoryLog{" +
            "id=" + id +
            ", createTime=" + createTime +
            ", updateTime=" + updateTime +
            ", productId=" + productId +
            ", warehouseId=" + warehouseId +
            ", targetId=" + targetId +
            ", targetItemId=" + targetItemId +
            ", targetType=" + targetType +
            ", quantity=" + quantity +
            ", memo=" + memo +
        "}";
    }
}
