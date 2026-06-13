package tools.data_structure;
/**
<p>最大栈类。</p><br>
最大栈是一种特殊的栈，除了基本的入栈、出栈操作外，还可以在O(1)时间内获取栈中的最大元素。<br>
本最大栈以数组实现，默认容量为16。
@see stack
*/
public class stack_max extends stack
{
    public int max_elements[];
    /**
    构造一个指定容量的空最大栈。
    @param capacity 栈的容量。
    @see stack#stack(int)
    */
    public stack_max(int capacity)
    {
        super(capacity);
        max_elements=new int[capacity];
    }
    /**
    构造一个默认容量为16的空最大栈。
    @see stack#stack()
    */
    public stack_max()
    {
        super();
        max_elements=new int[16];
    }
    /**
	<p>此方法会修改调用对象。</p><br>
    对最大栈进行扩容。<br>
    新的栈容量=当前容量*2+2。
    @return 新的栈容量=当前容量*2+2。
    */
    public int dilate()
    {
        capacity=(capacity<<1)+2;
        int new_elements[]=new int[capacity];
        int new_max_elements[]=new int[capacity];
        System.arraycopy(elements,0,new_elements,0,top);
        System.arraycopy(max_elements,0,new_max_elements,0,top);
        elements=new_elements;
        max_elements=new_max_elements;
        return capacity;
    }
    /**
	<p>此方法会修改调用对象。</p><br>
    对最大栈进行扩容。<br>
    新的栈容量=当前容量+<code>more_capacity</code>。
    @param more_capacity 要扩展的容量。
    @return 新的栈容量=当前容量+<code>more_capacity</code>。
    */
    public int dilate(int more_capacity)
    {
        if(more_capacity<0)
        {
            return more_capacity;
        }
        capacity+=more_capacity;
        int new_elements[]=new int[capacity];
        int new_max_elements[]=new int[capacity];
        System.arraycopy(elements,0,new_elements,0,top);
        System.arraycopy(max_elements,0,new_max_elements,0,top);
        elements=new_elements;
        max_elements=new_max_elements;
        return capacity;
    }
    /**
    获取栈中最大的元素但不弹出。
    @return 栈中最大的元素。<br>
    若栈为空，则返回<code>Integer.MIN_VALUE</code>。
    */
    public int max_element()
    {
        if(top>0)
        {
            return max_elements[top-1];
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
    @return 栈中元素的数量。
    @see stack#input(int)
    */
    public int input(int element)
    {
        super.input(element);
        if(top==1)
        {
            max_elements[0]=element;
        }
        else
        {
            max_elements[top-1]=Math.max(max_elements[top-2],element);
        }
        return top;
    }
    /**
	<p>此方法会修改调用对象。</p><br>
    将多个元素压入栈中。
    @param elements 要压入栈中的元素数组。
    @return 栈中元素的数量。
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
                max_elements[0]=element;
            }
            else
            {
                max_elements[top-1]=Math.max(max_elements[top-2],element);
            }
        }
        return top;
    }
}