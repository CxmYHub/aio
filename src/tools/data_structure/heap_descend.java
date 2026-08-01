package tools.data_structure;
/**
<p>降序堆类</p><br>
降序堆是一种特殊的堆，其每个结点的元素都大于或等于其子结点的元素。<br>
每次取出的元素都是堆中最大的元素。
@see heap
*/
public class heap_descend extends heap
{
    /**
    <p>构造方法</p><br>
    构造一个降序堆，包含指定元素。
    @param elements 要包含的元素。
    @see heap#heap(int...)
    */
    public heap_descend(int... elements)
    {
        super(elements);
        regular_all();
    }
    /**
    <p>构造方法</p><br>
    构造一个指定容量的空降序堆。
    @param capacity 堆的容量。
    @see heap#heap(int)
    */
    public heap_descend(int capacity)
    {
        super(capacity);
    }
    /**
    <p>无参构造方法</p><br>
    构造一个默认容量为255的空降序堆。
    @see heap#heap()
    */
    public heap_descend()
    {
        super();
    }
    /**
    <p>元素输入</p><br>
    <p>此方法会修改调用对象。</p><br>
    将元素插入降序堆。
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
    <p>元素批量输入</p><br>
    <p>此方法会修改调用对象。</p><br>
    将多个元素插入降序堆。
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
    <p>元素输出</p><br>
    <p>此方法会修改调用对象。</p><br>
    从降序堆中取出最大元素。
    @return 最大元素。
    @see heap#output()
    */
    public int output()
    {
        int min=super.output();
        regular_top();
        return min;
    }
    /**
    <p>所有元素调整</p><br>
    <p>此方法会修改调用对象。</p><br>
    对降序堆中的所有元素进行调整，使其满足降序堆的性质。
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
                int max;
                if(right<size)
                {
                    max=elements[left]>elements[right]?left:right;
                }
                else
                {
                    max=left;
                }
                if(elements[max]>number)
                {
                    elements[pin]=elements[max];
                    elements[max]=number;
                    pin=max;
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
    <p>堆顶元素调整</p><br>
    <p>此方法会修改调用对象。</p><br>
    对降序堆中的堆顶元素进行调整，使其满足降序堆的性质。
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
            int max;
            if(right<size)
            {
                max=elements[left]>elements[right]?left:right;
            }
            else
            {
                max=left;
            }
            if(elements[max]>number)
            {
                elements[pin]=elements[max];
                elements[max]=number;
                pin=max;
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
    <p>末元素调整</p><br>
    <p>此方法会修改调用对象。</p><br>
    对降序堆中的最后一个元素进行调整，使其满足降序堆的性质。
    @return 调整后的堆顶元素。
    */
    public int regular_last()
    {
        int pin=size-1;
        int number=elements[pin];
        while(pin>=0)
        {
            int parent=(pin-1)/2;
            if(elements[parent]<number)
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