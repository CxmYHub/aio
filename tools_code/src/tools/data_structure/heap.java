package tools.data_structure;
/**
<p>堆类</p><br>
堆是一种特殊的树状数据结构。<br>
取出元素时，将会从堆顶取出。<br>
本堆以数组实现，默认容量为255，即默认深度为8。<br>
堆顶元素存储在数组的第一个位置。
*/
public class heap
{
    /**
    <p>元素数组</p>
    */
    public int elements[];
    /**
    <p>元素数量</p>
    */
    public int size;
    /**
    <p>堆容量</p>
    */
    public int capacity;
    /**
    <p>构造方法</p><br>
    构造一个包含指定元素的堆。
    @param elements 堆的元素。
    */
    public heap(int... elements)
    {
        this.elements=new int[elements.length];
        System.arraycopy(elements,0,this.elements,0,elements.length);
        size=elements.length;
        capacity=elements.length;
    }
    /**
    <p>构造方法</p><br>
    构造一个指定容量的空堆。
    @param capacity 堆的容量。
    */
    public heap(int capacity)
    {
        elements=new int[capacity>0?capacity:1];
        size=0;
        this.capacity=elements.length;
    }
    /**
    <p>无参构造方法</p><br>
    构造一个默认容量为255的空堆。
    */
    public heap()
    {
        elements=new int[255];
        size=0;
        capacity=255;
    }
    /**
    <p>扩容</p><br>
    <p>此方法会修改调用对象。</p><br>
    对堆进行扩容。<br>
    新的堆容量=当前容量*2+1。
    @return 新的堆容量=当前容量*2+1。
    */
    public int dilate()
    {
        capacity=(capacity<<1)+1;
        int new_elements[]=new int[capacity];
        System.arraycopy(elements,0,new_elements,0,size);
        elements=new_elements;
        return capacity;
    }
    /**
    <p>扩容</p><br>
    <p>此方法会修改调用对象。</p><br>
    对堆进行扩容。<br>
    新的堆容量=当前容量+<code>more_capacity</code>。
    @param more_capacity 要扩展的容量。
    @return 新的堆容量=当前容量+<code>more_capacity</code>。
    */
    public int dilate(int more_capacity)
    {
        if(more_capacity<0)
        {
            return capacity;
        }
        capacity+=more_capacity;
        int new_elements[]=new int[capacity];
        System.arraycopy(elements,0,new_elements,0,size);
        elements=new_elements;
        return capacity;
    }
    /**
    <p>元素输入</p><br>
    <p>此方法会修改调用对象。</p><br>
    将元素插入堆的末尾。
    @param element 要插入的元素。
    @return 插入的元素。
    */
    public int input(int element)
    {
        if(size>=capacity)
        {
            dilate();
        }
        elements[size++]=element;
        return element;
    }
    /**
    <p>元素批量输入</p><br>
    <p>此方法会修改调用对象。</p><br>
    将多个元素插入堆的末尾。
    @param elements 要插入的多个元素。
    @return 插入的元素的数量。
    */
    public int input_more(int... elements)
    {
        for(int i=0;i<elements.length;i++)
        {
            if(size>=capacity)
            {
                dilate();
            }
            elements[size++]=elements[i];
        }
        return size;
    }
    /**
    <p>堆顶元素获取</p><br>
    获取堆顶元素但不取出。
    @return 堆顶元素。
    */
    public int get()
    {
        return elements[0];
    }
    /**
    <p>元素输出</p><br>
    <p>此方法会修改调用对象。</p><br>
    取出堆顶元素。
    @return 堆顶元素。
    */
    public int output()
    {
        if(size>0)
        {
            int result=elements[0];
            elements[0]=elements[size-1];
            size--;
            return result;
        }
        else
        {
            return Integer.MIN_VALUE;
        }
    }
    /**
    <p>字符串表示</p><br>
    @return 堆的字符串表示。
    */
    public String toString()
    {
        if(size==1)
        {
            return elements[0]+"";
        }
        else if(size>1)
        {
            int pins[]=new int[(int)(Math.log(capacity)/Math.log(2))+3];
            int pin=0;
            pins[0]=0;
            StringBuilder result=new StringBuilder();
            while(pin>=0)
            {
                result.append(elements[pins[pin]]);
                if((pins[pin]<<1)+1<size)
                {
                    result.append("{");
                    pins[pin+1]=(pins[pin]<<1)+1;
                    pin++;
                }
                else if((pins[pin]<<1)+2<size)
                {
                    result.append("{,");
                    pins[pin+1]=(pins[pin]<<1)+2;
                    pin++;
                }
                else
                {
                    boolean back=false;
                    do
                    {
                        if(back)
                        {
                            result.append("}");
                        }
                        pin--;
                        back=true;
                    }
                    while(pin>=0&&((pins[pin]<<1)+2>=size||(pins[pin]<<1)+2==pins[pin+1]));
                    if(pin>=0)
                    {
                        result.append(",");
                        pins[pin+1]=(pins[pin]<<1)+2;
                        pin++;
                    }
                }
            }
            return result.toString();
        }
        else
        {
            return "empty";
        }
    }
}