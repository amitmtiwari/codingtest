package com.test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicReference;

public class PromotionEngine {

    Map<String, Integer> skuPriceMap = new HashMap<>();
    List<PromotionObject> promotionList = new ArrayList<>();

    {
        skuPriceMap.put("A",50);
        skuPriceMap.put("B",30);
        skuPriceMap.put("C",20);
        skuPriceMap.put("D",15);

        PromotionObject promotionObject = new PromotionObject("A",3,false, "",130);
        PromotionObject promotionObject1 = new PromotionObject("B", 2,false, "",45);
        promotionList.add(promotionObject);
        promotionList.add(promotionObject1);
    }

    public Integer calculateOrderValue(Map<String, Integer> checkoutSKUs, List<PromotionObject> promotionList) {  //Input is SKU Id's and its count

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

    public static void main(String[] args) {
        Map<String, Integer> testData = new HashMap<>();
        testData.put("A",1);
        testData.put("B",1);
        testData.put("C", 1);
        PromotionEngine promotionEngine = new PromotionEngine();
        /*Integer totalCost = promotionEngine.calculateOrderValue(testData);
        System.out.println("Total Cost of SKU's is "+totalCost);*/

       /*Map<String, Integer> testData1 = new HashMap<>();
        testData1.put("A",5);
        testData1.put("B",5);
        testData1.put("C", 1);
        Integer totalCost1 =  promotionEngine.calculateOrderValue(testData1);
        System.out.println("Total Cost of SKU's is "+totalCost1);

        Map<String, Integer> testData2 = new HashMap<>();
        testData2.put("A",3);
        testData2.put("B",5);
        testData2.put("C", 1);
        testData2.put("D", 1);
        Integer totalCost2 =  promotionEngine.calculateOrderValue(testData2);
        System.out.println("Total Cost of SKU's is "+totalCost2);*/
    }
}
