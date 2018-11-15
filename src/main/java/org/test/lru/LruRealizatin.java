package org.test.lru;

import java.util.HashMap;

/**
 * 实现最少访问算法
 * 最少访问算法 主要解决在一定内存情况下 对热点数据进行缓存 这里就是用来区分热点数据和非热点数据的并且对热点数据进行内存缓存
 * https://mp.weixin.qq.com/s/B5xiVeW22ZumbI9KfrYJSg
 *
 * @author taowenxiang
 * @date 2018/11/15
 * @since 1.0
 */
public class LruRealizatin {

    /**
     * 头节点数据信息
     */
    private Node head;

    /**
     * 尾节点数据信息
     */
    private Node end;

    /**
     * 缓存上线
     */
    private int limit;

    /**
     * hashMap 保存各个节点的引用信息
     */
    private HashMap<String,Node> hashMap;

    public LruRealizatin(int limit) {
        this.limit = limit;
        this.hashMap = new HashMap<>();
    }

    /**
     * 删除节点
     */
    public void remove(String key) {
        Node node = hashMap.get(key);
        if (node == null) {
            return;
        }
        remove(node);
        hashMap.remove(key);
    }

    /**
     * 从 lru 集合中获取到值
     */
    public String get(String key) {
        Node node = hashMap.get(key);
        if (node ==null) {
            return null;
        }
        // 对活跃数据进行刷新
        refreshNode(node);
        return node.value;
    }



    /**
     * 向 lru 集合中传入值和参数
     *
     */
    public void put(String key,String value) {
        Node node = hashMap.get(key);
        if (node == null) {
            if (hashMap.size() >= limit) {
                String oldKey = remove(head);
                hashMap.remove(oldKey);
            }
            node = new Node(key,value);
            addNode(node);
            hashMap.put(key,node);
        }else {
            node.value = value;
            refreshNode(node);
        }
    }

    /**
     * 得到 key 根据 node
     */
    public Node getNode(String key) {
        return hashMap.get(key);
    }
    /**
     * 刷新被访问节点的位置
     *
     * 1、如果存在节点，将节点删除后再从最后面插入
     * 2、本来就是尾节点无需改动
     */
    private void refreshNode(Node node) {
        if ( end == node) {
            return;
        }
        remove(node);
        addNode(node);
    }
    /**
     * 删除节点
     * 1、查看节点的上级是否存在 如果存在 将上级节点指定给下级节点或者为空
     * 2、查看节点的下级节点是否存在 如果存在 将下级节点指定给节点的上级节点或者为空
     */
    private String remove(Node node) {
        if (node == end) {
            end = node.pre;
        }else if (node == head) {
            head = node.next;
        } else {
            node.pre.next = node.next;
            node.next.pre = node.pre;
        }
        return node.key;
    }
    /**
     * 添加节点 在尾部
     */
    private void addNode(Node node) {
        if (end != null) {
            // 在 end 后面添加节点
            end.next = node;
            node.pre = end;
            node.next = null;
        }
        // 将 end 引用到最后节点
        end = node;
        if (head == null) {
            head = node;
        }
    }
    /**
     * 双向节点 链表的一个节点，中有上级节点和下级节点的引用
     */
    class Node{
        private Node pre;
        private Node next;
        private String key;
        private String value;

        public Node(String key, String value) {
            this.key = key;
            this.value = value;
        }

        public Node getPre() {
            return pre;
        }

        public void setPre(Node pre) {
            this.pre = pre;
        }

        public Node getNext() {
            return next;
        }

        public void setNext(Node next) {
            this.next = next;
        }

        public String getKey() {
            return key;
        }

        public void setKey(String key) {
            this.key = key;
        }

        public String getValue() {
            return value;
        }

        public void setValue(String value) {
            this.value = value;
        }
    }
}
