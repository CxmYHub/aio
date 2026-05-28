package tools.data_structure;
/**
<p>升序堆类。</p><br>
升序堆是一种特殊的堆，其每个节点的元素都小于或等于其子节点的元素。<br>
每次取出的元素都是堆中最小的元素。
@see heap
*/
public class heap_ascend extends heap
{
    /**
    构造一个指定容量的空升序堆。
    @param capacity 堆的容量。
    @see heap#heap(int)
    */
    public heap_ascend(int capacity)
    {
        super(capacity);
    }
    /**
    构造一个默认容量为255的空升序堆。
    @see heap#heap()
    */
    public heap_ascend()
    {
        super();
    }
    /**
    构造一个升序堆，包含指定元素。
    @param elements 要包含的元素。
    @see heap#heap(int...)
    */
    public heap_ascend(int... elements)
    {
        super(elements);
        regular_all();
    }
    /**
	<p>此方法会修改调用对象。</p><br>
    将元素插入升序堆。
    @param element 要插入的元素。
    @return 插入的元素。
    @see heap#input(int)
    */
    public int input(int element)
    {
        super.input(element);
        regular_last();
        return element;
    }
    /**
	<p>此方法会修改调用对象。</p><br>
    将多个元素插入升序堆。
    @param elements 要插入的多个元素。
    @return 插入的元素数量。
    @see heap#input_more(int...)
    */
    public int input_more(int... elements)
    {
        int count=super.input_more(elements);
        regular_all();
        return count;
    }
    /**
	<p>此方法会修改调用对象。</p><br>
    从升序堆中取出最小元素。
    @return 最小元素。
    @see heap#output()
    */
    public int output()
    {
        int min=super.output();
        regular_top();
        return min;
    }
    /**
	<p>此方法会修改调用对象。</p><br>
    对升序堆中的所有元素进行调整，使其满足升序堆的性质。
    @return 调整后的堆顶元素。
    */
    public int regular_all()
    {
        for(int i=(size-2)/2;i>=0;i--)
        {
            int pin=i;
            int number=elements[pin];
            while(pin<=(size-2)/2)
            {
                int left=2*pin+1;
                int right=2*pin+2;
                int min;
                if(right<size)
                {
                    min=elements[left]<elements[right]?left:right;
                }
                else
                {
                    min=left;
                }
                if(elements[min]<number)
                {
                    elements[pin]=elements[min];
                    elements[min]=number;
                    pin=min;
                }
                else
                {
                    elements[pin]=number;
                    break;
                }
            }
        }
        return elements[0];
    }
    /**
	<p>此方法会修改调用对象。</p><br>
    对升序堆中的堆顶元素进行调整，使其满足升序堆的性质。
    @return 调整后的堆顶元素。
    */
    public int regular_top()
    {
        int pin=0;
        int number=elements[pin];
        while(pin<=(size-2)/2)
        {
            int left=2*pin+1;
            int right=2*pin+2;
            int min;
            if(right<size)
            {
                min=elements[left]<elements[right]?left:right;
            }
            else
            {
                min=left;
            }
            if(elements[min]<number)
            {
                elements[pin]=elements[min];
                elements[min]=number;
                pin=min;
            }
            else
            {
                elements[pin]=number;
                break;
            }
        }
        return elements[0];
    }
    /**
	<p>此方法会修改调用对象。</p><br>
    对升序堆中的最后一个元素进行调整，使其满足升序堆的性质。
    @return 调整后的堆顶元素。
    */
    public int regular_last()
    {
        int pin=size-1;
        int number=elements[pin];
        while(pin>=0)
        {
            int parent=(pin-1)/2;
            if(elements[parent]>number)
            {
                elements[pin]=elements[parent];
                elements[parent]=number;
                pin=parent;
            }
            else
            {
                elements[pin]=number;
                break;
            }
        }
        return elements[0];
    }
}