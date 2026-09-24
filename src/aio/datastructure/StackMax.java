package aio.datastructure;
/**
<p>最大栈类</p><br>
最大栈是一种特殊的栈，除了基本的入栈、出栈操作外，还可以在O(1)时间内获取栈中的最大元素。<br>
本最大栈以数组实现，默认容量为16。
@see Stack
*/
public class StackMax extends Stack {
    /**
    <p>最大元素数组</p>
    */
    public int maxElements[];
    /**
    <p>构造方法</p><br>
    构造一个指定容量的空最大栈。
    @param capacity 栈的容量。
    @see Stack#Stack(int)
    */
    public StackMax(int capacity) {
        super(capacity);
        maxElements=new int[capacity];
    }
    /**
    <p>无参构造方法</p><br>
    构造一个默认容量为16的空最大栈。
    @see Stack#Stack()
    */
    public StackMax() {
        super();
        maxElements=new int[16];
    }
    /**
    <p>扩容</p><br>
    <p>此方法会修改调用对象。</p><br>
    对最大栈进行扩容。<br>
    新的栈容量=当前容量*2+2。
    @return 新的栈容量=当前容量*2+2。
    */
    public int dilate() {
        capacity=(capacity<<1)+2;
        int newElements[]=new int[capacity];
        int newMaxElements[]=new int[capacity];
        System.arraycopy(elements,0,newElements,0,top);
        System.arraycopy(maxElements,0,newMaxElements,0,top);
        elements=newElements;
        maxElements=newMaxElements;
        return capacity;
    }
    /**
    <p>扩容</p><br>
    <p>此方法会修改调用对象。</p><br>
    对最大栈进行扩容。<br>
    新的栈容量=当前容量+<code>moreCapacity</code>。
    @param moreCapacity 要扩展的容量。
    @return 新的栈容量=当前容量+<code>moreCapacity</code>。
    */
    public int dilate(int moreCapacity) {
        if(moreCapacity<0) {
            return moreCapacity;
        }
        capacity+=moreCapacity;
        int newElements[]=new int[capacity];
        int newMaxElements[]=new int[capacity];
        System.arraycopy(elements,0,newElements,0,top);
        System.arraycopy(maxElements,0,newMaxElements,0,top);
        elements=newElements;
        maxElements=newMaxElements;
        return capacity;
    }
    /**
    <p>最大元素获取</p><br>
    获取栈中最大的元素但不弹出。
    @return 栈中最大的元素。<br>
    若栈为空，则返回<code>Integer.MIN_VALUE</code>。
    */
    public int maxElement() {
        if(top>0) {
            return maxElements[top-1];
        } else {
            return Integer.MIN_VALUE;
        }
    }
    /**
    <p>元素压入</p><br>
    <p>此方法会修改调用对象。</p><br>
    将元素压入栈中。
    @param element 要压入栈中的元素。
    @return 栈中元素的数量。
    @see Stack#input(int)
    */
    public int input(int element) {
        super.input(element);
        if(top==1) {
            maxElements[0]=element;
        } else {
            maxElements[top-1]=Math.max(maxElements[top-2],element);
        }
        return top;
    }
    /**
    <p>元素批量压入</p><br>
    <p>此方法会修改调用对象。</p><br>
    将多个元素压入栈中。
    @param elements 要压入栈中的元素数组。
    @return 栈中元素的数量。
    @see Stack#inputMore(int...)
    @see Stack#input(int)
    */
    public int inputMore(int... elements) {
        for(int element:elements) {
            super.input(element);
            if(top==1) {
                maxElements[0]=element;
            } else {
                maxElements[top-1]=Math.max(maxElements[top-2],element);
            }
        }
        return top;
    }
}