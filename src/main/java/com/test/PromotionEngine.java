package com.test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicReference;

public class PromotionEngine {

    public Integer calculateOrderValue(Map<String, Integer> checkoutSKUs, List<PromotionObject> promotionList, Map<String, Integer> skuPriceMap) {  //Input is SKU Id's and its count

        List<PromotionObject> forGroupedPromotionalObject = new ArrayList<>();
        AtomicReference<Integer> totalPrice = new AtomicReference<>(0);
        checkoutSKUs.forEach((sku, skuCount) -> {
            Integer unitPrice = skuPriceMap.get(sku);
            Integer skuPrice = 0;

            PromotionObject promotionObject = checkOffer(sku, promotionList);
            if(promotionObject != null) {
                skuPrice = calculatePriceOfSku(sku, skuCount, unitPrice, skuPrice, promotionObject);
            } else {
                skuPrice = skuCount * unitPrice;
            }
            Integer finalSkuPrice = skuPrice;
            totalPrice.updateAndGet(v -> v + finalSkuPrice);
        });
       // System.out.println(totalPrice);
        return totalPrice.get();

    }

    private Integer applyGrouping(List<PromotionObject> forGroupedPromotionalObject, String sku, Integer skuCount, Integer unitPrice, Integer skuPrice, PromotionObject promotionObject) {
        PromotionObject groupedPromotionObject = forGroupedPromotionalObject.stream().filter(u -> u.getGroupedWith().contains(sku)).findAny().orElse(null);
        Integer price = 0 ;
        if(groupedPromotionObject == null) {
            price = calculatePriceOfSku(sku, skuCount, unitPrice, skuPrice, promotionObject);
            forGroupedPromotionalObject.add(promotionObject);
        }
        return price;
    }

    private Integer calculatePriceOfSku(String sku, Integer skuCount, Integer unitPrice, Integer skuPrice, PromotionObject promotionObject) {
        Integer promotionalCount = promotionObject.getSkuCount();
        Integer remainderCount = (skuCount % promotionalCount);
        Integer dividendCount = (skuCount / promotionalCount);
        if (dividendCount != 0)
            skuPrice += (dividendCount * promotionObject.getPrice());//totalPrice.updateAndGet(v -> v + promotionObject.getPrice());   // apply promotional price
        if (remainderCount != 0)
            skuPrice += (remainderCount * unitPrice);//totalPrice.updateAndGet(v -> v + (remainderCount * unitPrice));   //apply unit price

        return skuPrice;
    }

    private PromotionObject checkOffer(String sku,List<PromotionObject> promotionList) {
        PromotionObject promotionObject = promotionList.stream().filter(u -> u.getSkuId().contains(sku)).findFirst().orElse(null);
        return promotionObject;
    }
}
