package tools.data_structure;
/**
<p>双端队列类。</p><br>
双端队列是一种特殊的队列，与队列相比，双端队列在队列两端都可以进行插入和删除操作。<br>
本双端队列以数组实现，属于循环队列，默认容量为256。
@see tools.data_structure.queue
*/
public class deque extends queue
{
    /**
    构造一个指定容量的空双端队列。
    @param capacity 双端队列的容量。
    @see queue#queue(int)
    */
    public deque(int capacity)
    {
        super(capacity);
    }
    /**
    构造一个默认容量为256的空双端队列。
    @see queue#queue()
    */
    public deque()
    {
        super();
    }
    /**
	<p>此方法会修改调用对象。</p><br>
    将元素从双端队列的末尾入队。
    @param element 要入队的元素。
    @return 双端队列中元素的数量。
    */
    public int input_back(int element)
    {
        return super.input(element);
    }
    /**
	<p>此方法会修改调用对象。</p><br>
    将多个元素从双端队列的末尾入队。
    @param elements 要入队的多个元素。
    @return 双端队列中元素的数量。
    */
    public int input_more_back(int... elements)
    {
        return super.input_more(elements);
    }
    /**
	<p>此方法会修改调用对象。</p><br>
    将元素从双端队列的开头入队。
    @param element 要入队的元素。
    @return 双端队列中元素的数量。
    */
    public int input_front(int element)
    {
        if(front==rear&&overturn)
        {
            dilate();
        }
        front--;
        if(front<0)
        {
            front=capacity-1;
            overturn=true;
        }
        this.elements[front]=element;
        return overturn?capacity+rear-front:rear-front;
    }
    /**
	<p>此方法会修改调用对象。</p><br>
    将多个元素从双端队列的开头入队。
    @param elements 要入队的多个元素。
    @return 双端队列中元素的数量。
    */
    public int input_more_front(int... elements)
    {
        for(int element:elements)
        {
            if(front==rear&&overturn)
            {
                dilate();
            }
            front--;
            if(front<0)
            {
                front=capacity-1;
                overturn=true;
            }
            this.elements[front]=element;
        }
        return overturn?capacity+rear-front:rear-front;
    }
    /**
    获取队尾元素但不出队。
    @return 队尾元素。<br>
    若队列为空，则返回<code>Integer.MIN_VALUE</code>。
    */
    public int get_back()
    {
        if(front!=rear||overturn)
        {
            return elements[rear==0?capacity-1:rear-1];
        }
        else
        {
            return Integer.MIN_VALUE;
        }
    }
    /**
    获取队头元素但不出队。
    @return 队头元素。<br>
    若队列为空，则返回<code>Integer.MIN_VALUE</code>。
    */
    public int get_front()
    {
        return super.get();
    }
    /**
	<p>此方法会修改调用对象。</p><br>
    队尾元素出队。
    @return 出队的队尾元素。<br>
    若双端队列为空，则返回<code>Integer.MIN_VALUE</code>。
    */
    public int output_back()
    {
        if(front!=rear||overturn)
        {
            rear--;
            if(rear<0)
            {
                rear=capacity-1;
                overturn=false;
            }
            return elements[rear];
        }
        else
        {
            return Integer.MIN_VALUE;
        }
    }
    /**
	<p>此方法会修改调用对象。</p><br>
    队头元素出队。
    @return 出队的队头元素。<br>
    若双端队列为空，则返回<code>Integer.MIN_VALUE</code>。
    */
    public int output_front()
    {
        return super.output();
    }
}