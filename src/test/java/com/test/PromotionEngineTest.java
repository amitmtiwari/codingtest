package com.test;

import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import static org.junit.Assert.*;

public class PromotionEngineTest {

    Map<String, Integer> skuPriceMap = new HashMap<>();
    List<PromotionObject> promotionList = new ArrayList<>();

    @Before
    public void setup() {
        skuPriceMap.put("A",50);
        skuPriceMap.put("B",30);
        skuPriceMap.put("C",20);
        skuPriceMap.put("D",15);


        PromotionObject promotionObject = new PromotionObject("A",3,false, "",130);
        PromotionObject promotionObject1 = new PromotionObject("B", 2,false, "",45);
        promotionList.add(promotionObject);
        promotionList.add(promotionObject1);
    }



    @Test
    public void scenario1() {

        Map<String, Integer> testData = new HashMap<>();
        testData.put("A",1);
        testData.put("B",1);
        testData.put("C", 1);
        PromotionEngine promotionEngine = new PromotionEngine();
        Integer totalCost = promotionEngine.calculateOrderValue(testData, promotionList, skuPriceMap);
        assertTrue(totalCost == 100);
    }

    @Test
    public void scenario1Failed() {

        Map<String, Integer> testData = new HashMap<>();
        testData.put("A",1);
        testData.put("B",1);
        testData.put("C", 1);
        PromotionEngine promotionEngine = new PromotionEngine();
        Integer totalCost = promotionEngine.calculateOrderValue(testData, promotionList, skuPriceMap);
        assertFalse(!(totalCost == 100));
    }

    @Test
    public void scenario2() {

        Map<String, Integer> testData1 = new HashMap<>();
        testData1.put("A",5);
        testData1.put("B",5);
        testData1.put("C", 1);
        PromotionEngine promotionEngine = new PromotionEngine();
        Integer totalCost = promotionEngine.calculateOrderValue(testData1, promotionList, skuPriceMap);
        assertTrue(totalCost == 370);
    }

    @Test
    public void scenario2Failed() {

        Map<String, Integer> testData1 = new HashMap<>();
        testData1.put("A",5);
        testData1.put("B",5);
        testData1.put("C", 1);
        PromotionEngine promotionEngine = new PromotionEngine();
        Integer totalCost = promotionEngine.calculateOrderValue(testData1, promotionList, skuPriceMap);
        assertFalse(!(totalCost == 370));
    }

    @Test
    public void scenario3() {

        Map<String, Integer> testData2 = new HashMap<>();
        testData2.put("A",3);
        testData2.put("B",5);
        testData2.put("C", 1);
        testData2.put("D", 1);
        PromotionEngine promotionEngine = new PromotionEngine();
        Integer totalCost = promotionEngine.calculateOrderValue(testData2, promotionList, skuPriceMap);
        assertEquals(280, (int) totalCost);
    }

}
