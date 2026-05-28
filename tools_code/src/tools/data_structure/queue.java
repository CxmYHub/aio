package tools.data_structure;
/**
<p>队列类。</p><br>
队列是一种先进先出(FIFO)的数据结构。<br>
本队列以数组实现，属于循环队列，默认容量为256。
*/
public class queue
{
    public int elements[];
    public int top;
    public int rear;
    public int capacity;
    public boolean overturn=false;
    /**
    构造一个指定容量的空队列。
    @param capacity 队列的容量。
    */
    public queue(int capacity)
    {
        elements=new int[capacity];
        top=0;
        rear=0;
        this.capacity=capacity;
    }
    /**
    构造一个默认容量为256的空队列。
    */
    public queue()
    {
        elements=new int[256];
        top=0;
        rear=0;
        this.capacity=256;
    }
    /**
    判断队列是否为空。
    @return 是否为空。
    */
    public boolean is_empty()
    {
        return top==rear&&!overturn;
    }
    /**
    判断队列是否已满。
    @return 是否已满。
    */
    public boolean is_full()
    {
        return top==rear&&overturn;
    }
    /**
    获取队列中元素的数量。
    @return 队列中元素的数量。
    */
    public int element_count()
    {
        return overturn?capacity+rear-top:rear-top;
    }
    /**
    获取队列中剩余空间的数量。
    @return 队列中剩余空间的数量。
    */
    public int empty_count()
    {
        return overturn?top-rear:capacity+top-rear;
    }
    /**
	<p>此方法会修改调用对象。</p><br>
    对队列进行扩容。<br>
    新的队列容量=当前容量*2+2。
    @return 新的队列容量=当前容量*2+2。
    */
    public int dilate()
    {
        int new_elements[]=new int[(capacity<<1)+2];
        if(!overturn)
        {
            System.arraycopy(elements,top,new_elements,0,rear-top);
        }
        else
        {
            System.arraycopy(elements,top,new_elements,0,capacity-top);
            System.arraycopy(elements,0,new_elements,capacity-top,rear);
        }
        elements=new_elements;
        top=0;
        rear=overturn?capacity+rear-top:rear-top;
        overturn=false;
        capacity=(capacity<<1)+2;
        return capacity;
    }
    /**
	<p>此方法会修改调用对象。</p><br>
    对队列进行扩容。<br>
    新的队列容量=当前容量+more_capacity。
    @param more_capacity 要扩展的容量。
    @return 新的队列容量=当前容量+more_capacity。
    */
    public int dilate(int more_capacity)
    {
        if(more_capacity<0)
        {
            return more_capacity;
        }
        int new_elements[]=new int[capacity+more_capacity];
        if(!overturn)
        {
            System.arraycopy(elements,top,new_elements,0,rear-top);
        }
        else
        {
            System.arraycopy(elements,top,new_elements,0,capacity-top);
            System.arraycopy(elements,0,new_elements,capacity-top,rear);
        }
        elements=new_elements;
        top=0;
        rear=overturn?capacity+rear-top:rear-top;
        overturn=false;
        capacity+=more_capacity;
        return capacity;
    }
    /**
	<p>此方法会修改调用对象。</p><br>
    将元素入队。
    @param element 要入队的元素。
    @return 队列中元素的数量。
    */
    public int input(int element)
    {
        if(top==rear&&overturn)
        {
            dilate();
        }
        this.elements[rear++]=element;
        if(rear>=capacity)
        {
            rear=0;
            overturn=true;
        }
        return overturn?capacity+rear-top:rear-top;
    }
    /**
	<p>此方法会修改调用对象。</p><br>
    将多个元素入队。
    @param elements 要入队的多个元素。
    @return 队列中元素的数量。
    */
    public int input_more(int... elements)
    {
        for(int element:elements)
        {
            if(top==rear&&overturn)
            {
                dilate();
            }
            this.elements[rear++]=element;
            if(rear>=capacity)
            {
                rear=0;
                overturn=true;
            }
        }
        return overturn?capacity+rear-top:rear-top;
    }
    /**
    获取队头元素但不出队。
    @return 队头元素。<br>
    若队列为空，则返回Integer.MIN_VALUE。
    */
    public int get()
    {
        if(top!=rear||overturn)
        {
            return elements[top];
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
    若队列为空，则返回Integer.MIN_VALUE。
    */
    public int output()
    {
        if(top<capacity-1&&(top!=rear||overturn))
        {
            return elements[top++];
        }
        else if(top==capacity-1&&(top!=rear||overturn))
        {
            top=0;
            overturn=false;
            return elements[capacity-1];
        }
        else
        {
            return Integer.MIN_VALUE;
        }
    }
}