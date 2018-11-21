package org.test.tree;

import java.util.ArrayList;
import java.util.List;

/**
 * avl树：平衡二叉搜索树
 * <p>
 * 概念：他是一颗空树，或者每个节点的左右子树的高度之差的绝对值不超过1
 */
public class AVLTree {

    /**
     * 遍历方式
     * 前序
     * 中序
     * 后序
     * 层级
     */
    public static final int QIANXU = 1;
    public static final int ZHONGXU = 2;
    public static final int HOUXU = 3;
    public static final int CENGXJI = 4;

    private AvlNodeInteger root;
    private int size;

    public AVLTree() {

    }

    public AVLTree(AvlNodeInteger root) {
        this.root = root;
    }

    /**
     * 判断值是否存在
     */
    public boolean contains(Integer value) {
        AvlNodeInteger curNode = root;
        if (null == curNode) {
            return false;
        }
        while (null != curNode) {
            if (value > curNode.getValue()) {
                curNode = curNode.getRight();
            } else if (value < curNode.getValue()) {
                curNode = curNode.getLeft();
            } else {
                return true;
            }

        }
        return false;
    }

    public void printGraph(int style) {
        if (root == null) {
            return;
        }

        switch (style) {
            case QIANXU:
                qianxu(root);
                System.out.println();
                break;
            case ZHONGXU:
                zhongxu(root);
                System.out.println();
                break;
            case HOUXU:
                houxu(root);
                System.out.println();
                break;
            case CENGXJI:
                List a = new ArrayList();
                a.add(root);
                chengxu(a);
                System.out.println();
                break;
        }
    }

    /**
     * 插入值
     */
    public void insert(Integer value) throws Exception {
        if (null == root) {
            initRoot(value);
            size++;
            return;
        }
        if (contains(value)) {
            throw new Exception("值已经存在了");
        }

        insertNode(root, value);
        size++;
    }

    /**
     * 删除值
     */
    public void remove(Integer value) {
        if (null == root) {
            return;
        }
        if (!contains(value)) {
            return;
        }
        deleteNode(root, value);
        size--;
    }

    /**
     * 删除值
     *
     * @param parent
     * @param value
     */
    private AvlNodeInteger deleteNode(AvlNodeInteger parent, Integer value) {
        if (parent == null) {
            return null;
        }
        if (value < parent.getValue()) {
            // 左递归
            AvlNodeInteger newLeft = deleteNode(parent.getLeft(), value);
            if (height(parent.getRight()) - height(parent.getLeft()) > 1) {
                AvlNodeInteger tmpNode = parent.getRight();
                // 判断是rr还是rl
                if (height(tmpNode.getLeft()) - height(tmpNode.getRight()) > 1) {
                    parent = rightLeftRotate(parent);
                } else {
                    parent = rightRightRotate(parent);
                }
            }
        } else if (value > parent.getValue()) {
            // 右递归
            AvlNodeInteger newRight = deleteNode(parent.getRight(), value);
            if (height(newRight.getLeft()) - height(newRight.getRight()) > 1) {
                AvlNodeInteger tmpNode = parent.getRight();
                //判断是lr 还是ll
                if (height(tmpNode.getRight()) - height(tmpNode.getLeft()) > 1) {
                    parent = leftRightRotate(parent);
                } else {
                    parent = leftLeftRotate(parent);
                }
            }
        } else {
            // 相等
            if (null != parent.getLeft() && null != parent.getRight()) {
                //判断高度，高的一方，拿出最大 ，最小节点 作为替换节点
                if (parent.getLeft().getHeight() > parent.getRight().getHeight()) {
                    // 在左子树中找到最大的点，并在左子树中删除
                    AvlNodeInteger leftMax = getMax(parent.getLeft());
                    parent.setLeft(deleteNode(parent.getLeft(), leftMax.getValue()));
                    // 将最大的节点当作parent
                    leftMax.setLeft(parent.getLeft());
                    leftMax.setRight(parent.getRight());
                    leftMax.setHeight(maxHight(leftMax.getLeft(), leftMax.getRight()) + 1);
                    parent = leftMax;
                } else {
                    AvlNodeInteger rightMin = getMin(parent.getRight());
                    parent.setRight(deleteNode(parent.getRight(), rightMin.getValue()));

                    rightMin.setLeft(parent.getLeft());
                    rightMin.setRight(parent.getRight());
                    rightMin.setHeight(maxHight(rightMin.getLeft(), rightMin.getRight()) + 1);

                    parent = rightMin;
                }
            } else {
                // 将不为空的一方替换
                parent = parent.getRight() == null ?
                        parent.getLeft() == null ? null : parent.getLeft() :
                        parent.getRight();
            }
        }
        return parent;
    }

    /**
     * 获得节点的最大值的节点
     *
     * @param currentNode
     */
    private AvlNodeInteger getMax(AvlNodeInteger currentNode) {
        if (null != currentNode.getRight()) {
            return getMax(currentNode);
        }
        return currentNode;
    }

    /**
     * 获得当前节点的最小值
     *
     * @param currentNode
     */
    private AvlNodeInteger getMin(AvlNodeInteger currentNode) {
        if (null != currentNode.getLeft()) {
            return getMin(currentNode);
        }
        return currentNode;
    }

    private void initRoot(Integer val) {
        AvlNodeInteger avlTree = new AvlNodeInteger(val);
        this.root = avlTree;
    }

    /**
     * 在树中插入值
     */
    private AvlNodeInteger insertNode(AvlNodeInteger parent, Integer value) {
        if (parent == null) {
            return createSingleNode(value);
        }
        // 判断大小值，如果是小于就在左边，大于就在右边
        if (value < parent.value) {
            // 循环插入到对应的值中
            parent.setLeft(insertNode(parent.getLeft(), value));
            if (height(parent.getLeft()) - height(parent.getRight()) > 1) {
                Integer compareValue = (Integer) parent.getLeft().getValue();
                if (value < compareValue) {
                    parent = leftLeftRotate(parent);
                } else {
                    parent = leftRightRotate(parent);
                }
            }
        }
        if (value > parent.getValue()) {
            parent.setRight(insertNode(parent.getRight(), value));
            if (height(parent.getRight()) - height(parent.getLeft()) > 1) {
                Integer compareValue = parent.getRight().getValue();
                if (value > compareValue) {
                    parent = rightRightRotate(parent);
                } else {
                    parent = rightLeftRotate(parent);
                }
            }
        }
        parent.setHeight(maxHight(parent.getLeft(), parent.getRight()) + 1);
        return parent;
    }

    private AvlNodeInteger createSingleNode(Integer value) {
        return new AvlNodeInteger(value);
    }

    /**
     * 树的左旋左旋（LL）
     */
    private AvlNodeInteger leftLeftRotate(AvlNodeInteger node) {

        //提供左子树的高度
        AvlNodeInteger newRoot = node.getLeft();
        node.setLeft(newRoot.right);
        newRoot.setRight(node);

        // 重新计算node newRoot的高度信息
        node.setHeight(maxHight(node.getLeft(), node.getRight()) + 1);
        newRoot.setHeight(maxHight(newRoot.getLeft(), newRoot.getRight()) + 1);
        return newRoot;
    }


    /**
     * 树的右右旋（RR）
     */
    private AvlNodeInteger rightRightRotate(AvlNodeInteger node) {
        AvlNodeInteger newRoot = node.getRight();
        node.setRight(newRoot.getLeft());
        newRoot.setLeft(node);

        node.setHeight(maxHight(node.getLeft(), node.getRight()) + 1);
        newRoot.setHeight(maxHight(newRoot.getLeft(), newRoot.getRight()) + 1);
        return newRoot;
    }

    /**
     * 树的左右旋转(LR)
     * 先对左子节点右旋
     * 再对本身节点左左旋
     */
    private AvlNodeInteger leftRightRotate(AvlNodeInteger node) {
        node.setLeft(rightRightRotate(node.getLeft()));
        return leftLeftRotate(node);
    }


    /**
     * 右左旋转(RL)
     * 先对右节点左左旋
     * 再对本节点做右旋
     */
    private AvlNodeInteger rightLeftRotate(AvlNodeInteger node) {
        node.setRight(leftLeftRotate(node.getRight()));
        return rightRightRotate(node);
    }

    /**
     * 求左右子树节点的最大高度
     */
    private int maxHight(AvlNodeInteger left, AvlNodeInteger right) {
        return height(left) > height(right) ? height(left) : height(right);
    }

    /**
     * 求一个节点的最大高度
     */
    private int height(AvlNodeInteger node) {
        return null == node ? 0 : node.getHeight();
    }

    /**
     * 前序遍历
     * 1 跟节点
     * 2 左节点
     * 3 右节点
     */
    private void qianxu(AvlNodeInteger parent) {
        System.out.print(parent.getValue() + " , ");
        if (null != parent.getLeft()) {
            qianxu(parent.getLeft());
        }
        if (null != parent.getRight()) {
            qianxu(parent.getRight());
        }
    }

    /**
     * 中序遍历
     * 1 左节点
     * 2 根节点
     * 3 右节点
     */
    private void zhongxu(AvlNodeInteger parent) {
        if (null != parent.getLeft()) {
            zhongxu(parent.getLeft());
        }
        System.out.print(parent.getValue() + " , ");
        if (null != parent.getRight()) {
            zhongxu(parent.getRight());
        }
    }

    /**
     * 后序遍历
     * 1 左节点
     * 2 右节点
     * 3 根节点
     */
    private void houxu(AvlNodeInteger parent) {
        if (null != parent.getLeft()) {
            houxu(parent.getLeft());
        }
        if (null != parent.getRight()) {
            houxu(parent.getRight());
        }

        System.out.print(parent.getValue() + " , ");
    }

    /**
     * 层序遍历
     * 一层一层的打印
     *
     * @param parent
     */
    private void chengxu(List<AvlNodeInteger> parent) {
        if (null == parent || parent.size() == 0) {
            return;
        }
        List<AvlNodeInteger> avlNodeIntegers = new ArrayList<>();
        int k = 0;
        for (int i = 0; i < parent.size(); i++) {
            AvlNodeInteger currentNode = parent.get(i);
            System.out.print(currentNode.getValue() + ",");
            if (null != currentNode.getLeft()) {
                avlNodeIntegers.add(currentNode.getLeft());
                k++;
            }
            if (null != currentNode.getRight()) {
                avlNodeIntegers.add(currentNode.getRight());
                k++;
            }
        }
//        System.out.println("-----------------------" + k);
        chengxu(avlNodeIntegers);
    }

    public static class AvlNodeInteger {
        private Integer value;
        private Integer height;
        private AvlNodeInteger left;
        private AvlNodeInteger right;

        public AvlNodeInteger(int value) {
            initNode(value, null, null, 1);
        }

        public AvlNodeInteger(int value, AvlNodeInteger left, AvlNodeInteger right) {
            initNode(value, left, right, null);
        }

        private void initNode(int value, AvlNodeInteger left, AvlNodeInteger right, Integer height) {
            this.value = value;
            this.height = height;
            this.right = right;
            this.left = left;
        }

        public Integer getValue() {
            return value;
        }

        public void setValue(Integer value) {
            this.value = value;
        }

        public Integer getHeight() {
            return height;
        }

        public void setHeight(Integer height) {
            this.height = height;
        }

        public AvlNodeInteger getLeft() {
            return left;
        }

        public void setLeft(AvlNodeInteger left) {
            this.left = left;
        }

        public AvlNodeInteger getRight() {
            return right;
        }

        public void setRight(AvlNodeInteger right) {
            this.right = right;
        }
    }
}
