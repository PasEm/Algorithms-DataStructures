package ru.itis;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class MyMapTester {
    private MyMap<String, Integer> map;

    @Before
    public void setUp(){
        map = new MyMap<>();
    }

    @Test
    public void test1(){
        map.put("Marsel", 23);
        map.put("Marsel", 24);
        int result = map.getValue("Marsel");
        Assert.assertEquals(24, result);
    }

    @Test(expected = NullPointerException.class)
    public void test2(){
        map.put("Marsel", 23);
        int result1 = map.getValue("Maksim");
    }

    @Test
    public void test3(){
        map.put("Marsel", 23);
        map.put("Marsel", 24);
        map.put("Maksim", 18);
        map.put("Mars", 22);
        map.put("Andromeda", 35);
        Assert.assertEquals(24, (int) map.getValue("Marsel"));
        Assert.assertEquals(18, (int) map.getValue("Maksim"));
        Assert.assertEquals(22, (int) map.getValue("Mars"));
        Assert.assertEquals(35, (int) map.getValue("Andromeda"));
    }
}
