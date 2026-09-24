package aio.datastructure;
/**
<p>双端队列类</p><br>
双端队列是一种特殊的队列，与队列相比，双端队列在队列两端都可以进行插入和删除操作。<br>
本双端队列以数组实现，属于循环队列，默认容量为256。
@see aio.datastructure.Queue
*/
public class Deque extends Queue {
    /**
    <p>构造方法</p><br>
    构造一个指定容量的空双端队列。
    @param capacity 双端队列的容量。
    @see Queue#Queue(int)
    */
    public Deque(int capacity) {
        super(capacity);
    }
    /**
    <p>无参构造方法</p><br>
    构造一个默认容量为256的空双端队列。
    @see Queue#Queue()
    */
    public Deque() {
        super();
    }
    /**
    <p>队尾入队</p><br>
    <p>此方法会修改调用对象。</p><br>
    将元素从双端队列的末尾入队。
    @param element 要入队的元素。
    @return 双端队列中元素的数量。
    */
    public int inputBack(int element) {
        return super.input(element);
    }
    /**
    <p>队尾批量入队</p><br>
    <p>此方法会修改调用对象。</p><br>
    将多个元素从双端队列的末尾入队。
    @param elements 要入队的多个元素。
    @return 双端队列中元素的数量。
    */
    public int inputMoreBack(int... elements) {
        return super.inputMore(elements);
    }
    /**
    <p>队头入队</p><br>
    <p>此方法会修改调用对象。</p><br>
    将元素从双端队列的开头入队。
    @param element 要入队的元素。
    @return 双端队列中元素的数量。
    */
    public int inputFront(int element) {
        if(front==rear&&overturn) {
            dilate();
        }
        front--;
        if(front<0) {
            front=capacity-1;
            overturn=true;
        }
        this.elements[front]=element;
        return overturn?capacity+rear-front:rear-front;
    }
    /**
    <p>队头批量入队</p><br>
    <p>此方法会修改调用对象。</p><br>
    将多个元素从双端队列的开头入队。
    @param elements 要入队的多个元素。
    @return 双端队列中元素的数量。
    */
    public int inputMoreFront(int... elements) {
        for(int element:elements) {
            if(front==rear&&overturn) {
                dilate();
            }
            front--;
            if(front<0) {
                front=capacity-1;
                overturn=true;
            }
            this.elements[front]=element;
        }
        return overturn?capacity+rear-front:rear-front;
    }
    /**
    <p>队尾元素获取</p><br>
    获取队尾元素但不出队。
    @return 队尾元素。<br>
    若队列为空，则返回<code>Integer.MIN_VALUE</code>。
    */
    public int getBack() {
        if(front!=rear||overturn) {
            return elements[rear==0?capacity-1:rear-1];
        } else {
            return Integer.MIN_VALUE;
        }
    }
    /**
    <p>队头元素获取</p><br>
    获取队头元素但不出队。
    @return 队头元素。<br>
    若队列为空，则返回<code>Integer.MIN_VALUE</code>。
    */
    public int getFront() {
        return super.get();
    }
    /**
    <p>队尾出队</p><br>
    <p>此方法会修改调用对象。</p><br>
    队尾元素出队。
    @return 出队的队尾元素。<br>
    若双端队列为空，则返回<code>Integer.MIN_VALUE</code>。
    */
    public int outputBack() {
        if(front!=rear||overturn) {
            rear--;
            if(rear<0) {
                rear=capacity-1;
                overturn=false;
            }
            return elements[rear];
        } else {
            return Integer.MIN_VALUE;
        }
    }
    /**
    <p>队头出队</p><br>
    <p>此方法会修改调用对象。</p><br>
    队头元素出队。
    @return 出队的队头元素。<br>
    若双端队列为空，则返回<code>Integer.MIN_VALUE</code>。
    */
    public int outputFront() {
        return super.output();
    }
}