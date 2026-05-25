package tools.data_structure;
/**
<p>单调递增栈类。</p><br>
单调递增栈是一种特殊的栈，其出栈序列是单调递增的。<br>
每次取出的元素都是栈中最小的元素。
*/
public class ascend_stack
{
    public int elements[];
    public int top;
    public int capacity;
    /**
    构造一个指定容量的空单调递增栈。
    @param capacity 栈的容量。
    */
    public ascend_stack(int capacity)
    {
        elements=new int[capacity];
        top=0;
        this.capacity=capacity;
    }
    /**
    构造一个默认容量为16的空单调递增栈。
    */
    public ascend_stack()
    {
        elements=new int[16];
        top=0;
        this.capacity=16;
    }
    /**
    判断栈是否为空。
    @return 如果栈为空，则返回true；否则返回false。
    */
    public boolean is_empty()
    {
        return top==0;
    }
    /**
    判断栈是否已满。
    @return 如果栈已满，则返回true；否则返回false。
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
    将元素压入栈中。<br>
    会将栈中所有小于element的元素出栈。
    @param element 要压入栈中的元素。
    @return 出栈序列，若未出栈则返回空数组。
    */
    public int[] input(int element)
    {
        int result_length=0;
        for(int i=top-1;i>=0&&elements[i]<element;i--,result_length++);
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
    会将栈中所有小于element的元素出栈。
    @param elements 要压入栈中的元素数组。
    @return 每个插入元素对应的出栈序列<br>
    若当前元素未导致出栈则该元素对应的出栈序列为空数组。
    */
    public int[][] input_more(int... elements)
    {
        int result[][]=new int[elements.length][];
        for(int i=0;i<elements.length;i++)
        {
            int element=elements[i];
            int result_length=0;
            for(int j=top-1;j>=0&&this.elements[j]<element;j--,result_length++);
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