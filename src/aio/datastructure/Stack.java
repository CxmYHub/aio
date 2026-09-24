package aio.datastructure;
/**
<p>栈类</p><br>
栈是一种后进先出(LIFO)的数据结构。<br>
本栈以数组实现，默认容量为16。
*/
public class Stack {
    /**
    <p>元素数组</p>
    */
    public int elements[];
    /**
    <p>栈顶指针</p>
    */
    public int top;
    /**
    <p>栈容量</p>
    */
    public int capacity;
    /**
    <p>构造方法</p><br>
    构造一个指定容量的空栈。
    @param capacity 栈的容量。
    */
    public Stack(int capacity) {
        elements=new int[capacity];
        top=0;
        this.capacity=capacity;
    }
    /**
    <p>无参构造方法</p><br>
    构造一个默认容量为16的空栈。
    */
    public Stack() {
        super();
        elements=new int[16];
        top=0;
        capacity=16;
    }
    /**
    <p>空判断</p><br>
    判断栈是否为空。
    @return 是否为空。
    */
    public boolean isEmpty() {
        return top==0;
    }
    /**
    <p>满判断</p><br>
    判断栈是否已满。
    @return 是否已满。
    */
    public boolean isFull() {
        return top==capacity;
    }
    /**
    <p>元素计数</p><br>
    获取栈中元素的数量。
    @return 栈中元素的数量。
    */
    public int elementCount() {
        return top;
    }
    /**
    <p>剩余空间计数</p><br>
    获取栈中剩余空间的数量。
    @return 栈中剩余空间的数量。
    */
    public int emptyCount() {
        return capacity-top;
    }
    /**
    <p>扩容</p><br>
    <p>此方法会修改调用对象。</p><br>
    对栈进行扩容。<br>
    新的栈容量=当前容量*2+2。
    @return 新的栈容量=当前容量*2+2。
    */
    public int dilate() {
        capacity=(capacity<<1)+2;
        int newElements[]=new int[capacity];
        System.arraycopy(elements,0,newElements,0,top);
        elements=newElements;
        return capacity;
    }
    /**
    <p>扩容</p><br>
    <p>此方法会修改调用对象。</p><br>
    对栈进行扩容。<br>
    新的栈容量=当前容量+<code>moreCapacity</code>。
    @param moreCapacity 要扩展的容量。
    @return 新的栈容量=当前容量+<code>moreCapacity</code>。
    */
    public int dilate(int moreCapacity) {
        if(moreCapacity<0) {
            return moreCapacity;
        }
        int newElements[]=new int[capacity+moreCapacity];
        System.arraycopy(elements,0,newElements,0,top);
        elements=newElements;
        capacity+=moreCapacity;
        return capacity;
    }
    /**
    <p>元素压入</p><br>
    <p>此方法会修改调用对象。</p><br>
    将元素压入栈中。
    @param element 要压入栈中的元素。
    @return 栈中元素的数量。
    */
    public int input(int element) {
        if(top>=capacity) {
            dilate();
        }
        this.elements[top++]=element;
        return top;
    }
    /**
    <p>元素批量压入</p><br>
    <p>此方法会修改调用对象。</p><br>
    将多个元素压入栈中。
    @param elements 要压入栈中的元素数组。
    @return 栈中元素的数量。
    */
    public int inputMore(int... elements) {
        for(int element:elements) {
            if(top>=capacity) {
                dilate();
            }
            this.elements[top++]=element;
        }
        return top;
    }
    /**
    <p>元素获取</p><br>
    获取栈顶元素但不弹出。
    @return 栈顶元素。<br>
    若栈为空，则返回<code>Integer.MIN_VALUE</code>。
    */
    public int get() {
        if(top>0) {
            return elements[top-1];
        } else {
            return Integer.MIN_VALUE;
        }
    }
    /**
    <p>元素弹出</p><br>
    <p>此方法会修改调用对象。</p><br>
    弹出栈顶元素。
    @return 弹出的栈顶元素。<br>
    若栈为空，则返回<code>Integer.MIN_VALUE</code>。
    */
    public int output() {
        if(top>0) {
            return elements[--top];
        } else {
            return Integer.MIN_VALUE;
        }
    }
}