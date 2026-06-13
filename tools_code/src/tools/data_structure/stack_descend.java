package tools.data_structure;
/**
<p>单调递减栈类。</p><br>
单调递减栈是一种特殊的栈，其出栈序列是单调递减的。<br>
每次取出的元素都是当前栈中最大的元素。
*/
public class stack_descend
{
    public int elements[];
    public int top;
    public int capacity;
    /**
    构造一个指定容量的空单调递减栈。
    @param capacity 栈的容量。
    */
    public stack_descend(int capacity)
    {
        elements=new int[capacity];
        top=0;
        this.capacity=capacity;
    }
    /**
    构造一个默认容量为16的空单调递减栈。
    */
    public stack_descend()
    {
        elements=new int[16];
        top=0;
        this.capacity=16;
    }
    /**
    判断栈是否为空。
    @return 是否为空。
    */
    public boolean is_empty()
    {
        return top==0;
    }
    /**
    判断栈是否已满。
    @return 是否已满。
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
    对单调递减栈进行扩容。<br>
    新的栈容量=当前容量*2+2。
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
    对单调递减栈进行扩容。<br>
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
        int new_elements[]=new int[capacity+more_capacity];
        System.arraycopy(elements,0,new_elements,0,top);
        elements=new_elements;
        capacity+=more_capacity;
        return capacity;
    }
    /**
	<p>此方法会修改调用对象。</p><br>
    将元素压入栈中。<br>
    同时将栈中所有大于待压入元素的元素弹出。
    @param element 要压入栈中的元素。
    @return 出栈序列。<br>
    若待压入元素是栈中最大的元素，则不出栈，返回空数组。
    */
    public int[] input(int element)
    {
        int result_length=0;
        for(int i=top-1;i>=0&&elements[i]>element;i--,result_length++);
        int result[]=new int[result_length];
        for(int i=0;i<result_length;i++)
        {
            result[i]=elements[--top];
        }
        if(top>=capacity)
        {
            dilate();
        }
        this.elements[top++]=element;
        return result;
    }
    /**
	<p>此方法会修改调用对象。</p><br>
    将多个元素压入栈中。<br>
    同时将栈中所有大于待压入元素的元素弹出。
    @param elements 要压入栈中的多个元素。
    @return 每个插入元素对应的出栈序列。<br>
    若当前元素未导致出栈，则该元素对应的出栈序列为空数组。
    */
    public int[][] input_more(int... elements)
    {
        int result[][]=new int[elements.length][];
        for(int i=0;i<elements.length;i++)
        {
            int element=elements[i];
            int result_length=0;
            for(int j=top-1;j>=0&&this.elements[j]>element;j--,result_length++);
            result[i]=new int[result_length];
            for(int j=0;j<result_length;j++)
            {
                result[i][j]=this.elements[--top];
            }
            if(top>=capacity)
            {
                dilate();
            }
            this.elements[top++]=element;
        }
        return result;
    }
    /**
    获取栈顶元素但不弹出。
    @return 栈顶元素。<br>
    若栈为空，则返回<code>Integer.MIN_VALUE</code>。
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
    弹出栈顶元素。<br>
    @return 弹出的栈顶元素。<br>
    若栈为空，则返回<code>Integer.MIN_VALUE</code>。
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