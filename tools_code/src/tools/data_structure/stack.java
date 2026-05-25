package tools.data_structure;
/**
<p>栈类。</p><br>
栈是一种后进先出(LIFO)的数据结构。<br>
本栈以数组实现，默认容量为16。
*/
public class stack
{
    public int elements[];
    public int top;
    public int capacity;
    /**
    构造一个指定容量的空栈。
    @param capacity 栈的容量。
    */
    public stack(int capacity)
    {
        elements=new int[capacity];
        top=0;
        this.capacity=capacity;
    }
    /**
    构造一个默认容量为16的空栈。
    */
    public stack()
    {
        super();
        elements=new int[16];
        top=0;
        this.capacity=16;
    }
    /**
    判断栈是否为空。
    @return 如果栈为空则返回true，否则返回false。
    */
    public boolean is_empty()
    {
        return top==0;
    }
    /**
    判断栈是否已满。
    @return 如果栈已满则返回true，否则返回false。
    */
    public boolean is_full()
    {
        return top==capacity;
    }
    /**
    获取栈中元素的数量。
    @return 栈中元素的数量。
    */
    public int element_count()
    {
        return top;
    }
    /**
    获取栈中剩余空间的数量。
    @return 栈中剩余空间的数量。
    */
    public int empty_count()
    {
        return capacity-top;
    }
    /**
	<p>此方法会修改调用对象。</p><br>
    扩展栈的容量=当前容量*2+2。
    @return 新的栈容量=当前容量*2+2。
    */
    public int dilate()
    {
        capacity=(capacity<<1)+2;
        int new_elements[]=new int[capacity];
        System.arraycopy(elements,0,new_elements,0,top);
        elements=new_elements;
        return capacity;
    }
    /**
	<p>此方法会修改调用对象。</p><br>
    扩展栈的容量=当前容量+more_capacity。
    @param more_capacity 要扩展的容量。
    @return 新的栈容量=当前容量+more_capacity。
    */
    public int dilate(int more_capacity)
    {
        if(more_capacity<0)
        {
            return more_capacity;
        }
        int new_elements[]=new int[capacity+more_capacity];
        System.arraycopy(elements,0,new_elements,0,top);
        elements=new_elements;
        capacity+=more_capacity;
        return capacity;
    }
    /**
	<p>此方法会修改调用对象。</p><br>
    将元素压入栈中。
    @param element 要压入栈中的元素。
    @return 栈的容量，注意不是元素数量。
    */
    public int input(int element)
    {
        if(top>=capacity)
        {
            dilate();
        }
        this.elements[top++]=element;
        return capacity;
    }
    /**
	<p>此方法会修改调用对象。</p><br>
    将多个元素压入栈中。
    @param elements 要压入栈中的元素数组。
    @return 压入栈中的元素数量，即elements.length。
    */
    public int input_more(int... elements)
    {
        for(int element:elements)
        {
            if(top>=capacity)
            {
                dilate();
            }
            this.elements[top++]=element;
        }
        return elements.length;
    }
    /**
    获取栈顶元素但不弹出。
    @return 栈顶元素，如果栈为空则返回Integer.MIN_VALUE。
    */
    public int get()
    {
        if(top>0)
        {
            return elements[top-1];
        }
        else
        {
            return Integer.MIN_VALUE;
        }
    }
    /**
	<p>此方法会修改调用对象。</p><br>
    弹出栈顶元素。
    @return 弹出的栈顶元素，如果栈为空则返回Integer.MIN_VALUE。
    */
    public int output()
    {
        if(top>0)
        {
            return elements[--top];
        }
        else
        {
            return Integer.MIN_VALUE;
        }
    }
}