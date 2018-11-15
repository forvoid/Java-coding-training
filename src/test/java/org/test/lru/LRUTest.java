package org.test.lru;


import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * 测试编写的 Lru 算法事例
 *
 * @author taowenxiang
 * @date 2018/11/15
 * @since 1.0
 */
public class LRUTest {

    static LruRealizatin lruRealizatin;

    @BeforeEach
    void before() {
        // 构建参数
        lruRealizatin = new LruRealizatin(3);
        lruRealizatin.put("1", "1");
        lruRealizatin.put("2", "2");
        lruRealizatin.put("3", "3");
        lruRealizatin.put("4", "4");
        lruRealizatin.put("1", "5");
        lruRealizatin.put("6", "6");
        lruRealizatin.put("7", "7");
    }

    @Test
    void succeedingTest() {
        assertEquals("5", lruRealizatin.get("1"));
        assertEquals("6", lruRealizatin.get("6"));
        assertEquals("7", lruRealizatin.get("7"));
    }
    @Test
    void succeedingTest2() {
        // 测试多次 get 后是否会调整node 的位置
        lruRealizatin.get("6");
        assertEquals(null, lruRealizatin.getNode("6").getNext());
        lruRealizatin.get("7");
        assertEquals(null, lruRealizatin.getNode("7").getNext());

    }
    @Test
    void failingTest() {
        assertEquals(null,lruRealizatin.get("2"));
        assertEquals(null,lruRealizatin.get("3"));
        assertEquals(null,lruRealizatin.get("4"));
        assertEquals(null,lruRealizatin.get("5"));
        assertEquals(null,lruRealizatin.get("0"));
    }

    @Test
    void failingTest2() {
        // 是否可以获取到上一级的数据
        assertEquals("1",lruRealizatin.getNode("6").getPre().getKey());
        assertEquals("5",lruRealizatin.getNode("6").getPre().getValue());
        // 是否可以获取到下一级的数据
        assertEquals("7",lruRealizatin.getNode("6").getNext().getKey());
        assertEquals("7",lruRealizatin.getNode("6").getNext().getValue());
    }




}
