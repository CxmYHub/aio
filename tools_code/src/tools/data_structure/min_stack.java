package tools.data_structure;
/**
<p>最小栈类。</p><br>
最小栈是一种特殊的栈，除了基本的入栈、出栈操作外，还可以在O(1)时间内获取栈中的最小元素。<br>
本最小栈以数组实现，默认容量为16。
@see stack
*/
public class min_stack extends stack
{
    public int min_elements[];
    /**
    构造一个指定容量的空最小栈。
    @param capacity 栈的容量。
    @see stack#stack(int)
    */
    public min_stack(int capacity)
    {
        super(capacity);
        min_elements=new int[capacity];
    }
    /**
    构造一个默认容量为16的空最小栈。
    @see stack#stack()
    */
    public min_stack()
    {
        super();
        min_elements=new int[16];
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
        int new_min_elements[]=new int[capacity];
        System.arraycopy(elements,0,new_elements,0,top);
        System.arraycopy(min_elements,0,new_min_elements,0,top);
        elements=new_elements;
        min_elements=new_min_elements;
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
        capacity=capacity+more_capacity;
        int new_elements[]=new int[capacity];
        int new_min_elements[]=new int[capacity];
        System.arraycopy(elements,0,new_elements,0,top);
        System.arraycopy(min_elements,0,new_min_elements,0,top);
        elements=new_elements;
        min_elements=new_min_elements;
        return capacity;
    }
    /**
    获取栈中最小的元素但不弹出。
    @return 栈中最小的元素，如果栈为空则返回Integer.MIN_VALUE。
    */
    public int min_element()
    {
        if(top>0)
        {
            return min_elements[top-1];
        }
        else
        {
            return Integer.MIN_VALUE;
        }
    }
    /**
	<p>此方法会修改调用对象。</p><br>
    将元素压入栈中。
    @param element 要压入栈中的元素。
    @return 栈的容量，注意不是元素数量。
    @see stack#input(int)
    */
    public int input(int element)
    {
        super.input(element);
        if(top==1)
        {
            min_elements[0]=element;
        }
        else
        {
            min_elements[top-1]=Math.min(min_elements[top-2],element);
        }
        return capacity;
    }
    /**
	<p>此方法会修改调用对象。</p><br>
    将多个元素压入栈中。
    @param elements 要压入栈中的元素数组。
    @return 压入栈中的元素数量，即elements.length。
    @see stack#input_more(int...)
    @see stack#input(int)
    */
    public int input_more(int... elements)
    {
        for(int element:elements)
        {
            super.input(element);
            if(top==1)
            {
                min_elements[0]=element;
            }
            else
            {
                min_elements[top-1]=Math.min(min_elements[top-2],element);
            }
        }
        return elements.length;
    }
}