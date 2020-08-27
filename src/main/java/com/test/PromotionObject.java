package com.test;

public class PromotionObject {

    public PromotionObject() {
    }

    public PromotionObject(String skuId, Integer skuCount, boolean groupedSku, Integer price) {
        this.skuId = skuId;
        this.skuCount = skuCount;
        this.groupedSku = groupedSku;
        this.price = price;
    }

    public PromotionObject(String skuId, Integer skuCount, boolean groupedSku, String groupedWith, Integer price) {
        this.skuId = skuId;
        this.skuCount = skuCount;
        this.groupedSku = groupedSku;
        this.groupedWith = groupedWith;
        this.price = price;
    }

    private String skuId;

    private Integer skuCount;

    private boolean groupedSku;

    private String groupedWith;

    private Integer price;

    public String getSkuId() {
        return skuId;
    }

    public void setSkuId(String skuId) {
        this.skuId = skuId;
    }

    public Integer getSkuCount() {
        return skuCount;
    }

    public void setSkuCount(Integer skuCount) {
        this.skuCount = skuCount;
    }

    public boolean getGroupedSku() {
        return groupedSku;
    }

    public void setGroupedSku(boolean groupedSku) {
        this.groupedSku = groupedSku;
    }

    public Integer getPrice() {
        return price;
    }

    public void setPrice(Integer price) {
        this.price = price;
    }

    public String getGroupedWith() {
        return groupedWith;
    }

    public void setGroupedWith(String groupedWith) {
        this.groupedWith = groupedWith;
    }
}
