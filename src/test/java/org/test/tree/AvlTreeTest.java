package org.test.tree;


import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.test.lru.LruRealizatin;

/**
 * 二叉搜索树测试
 */
public class AvlTreeTest {


    static AVLTree avlTree;

    @BeforeEach
    void before() throws Exception {
        // 构建参数
        avlTree = new AVLTree();
        avlTree.insert(1);
        avlTree.insert(2);
        avlTree.insert(5);
        avlTree.insert(8);
        avlTree.insert(9);
    }

    /**
     * ll旋
     */
    @Test
    void testLL() throws Exception {

        avlTree.insert(0);
        avlTree.printGraph(AVLTree.QIANXU);
        avlTree.printGraph(AVLTree.ZHONGXU);
        avlTree.printGraph(AVLTree.HOUXU);
        avlTree.printGraph(AVLTree.CENGXJI);
    }

    /**
     * rr旋
     */
    @Test
    void testRR() throws Exception {
        avlTree.insert(6);
        avlTree.insert(7);
        avlTree.insert(8);
        avlTree.insert(9);
        avlTree.printGraph(AVLTree.QIANXU);
        avlTree.printGraph(AVLTree.ZHONGXU);
        avlTree.printGraph(AVLTree.HOUXU);
        avlTree.printGraph(AVLTree.CENGXJI);
    }



}
