package tools.data_structure;
/**
<p>双向链表类。</p><br>
双向链表属于链表的一种，是一种线性数据结构。<br>
对比数组，链表具有动态大小的优势，即可以在运行时根据需要动态添加或删除元素。<br>
同时，链表也可以在任意位置进行插入和删除操作。<br>
但链表的访问时间是线性的，即需要遍历链表才能访问到目标元素。<br><br>
本双向链表以管理类+内部节点类形式实现，管理器存储链表的元素数量和首尾指针，内部节点存储链表的元素。<br>
当索引大于等于0时，从首节点开始向后计数，称为正向索引。<br>
当索引小于0时，从尾节点开始向前计数，即当n&lt;0时，第n个节点表示第count+n+1个节点，或倒数第-n个节点，称为反向索引。
*/
public class linked_list_doubly
{
    /**
    <p>双向链表节点类。</p><br>
    用于存储双向链表中的元素。<br>
    每个节点包含元素、前驱节点指针和后继节点指针。<br>
    前驱节点指针指向当前节点的前一个节点，后继节点指针指向当前节点的后一个节点。
    */
    public static class linked_list_doubly_node
    {
        public int element;
        public linked_list_doubly_node previous;
        public linked_list_doubly_node next;
        public linked_list_doubly_node(int element,linked_list_doubly_node previous,linked_list_doubly_node next)
        {
            this.element=element;
            this.previous=previous;
            this.next=next;
        }
        public linked_list_doubly_node(int element)
        {
            this.element=element;
            this.previous=null;
            this.next=null;
        }
        public linked_list_doubly_node()
        {
            this.element=0;
            this.previous=null;
            this.next=null;
        }
    }
    public int count=0;
    public linked_list_doubly_node head=null;
    public linked_list_doubly_node tail=null;
    /**
    构造一个包含多个元素的双向链表。
    @param numbers 多个元素。
    */
    public linked_list_doubly(int... numbers)
    {
        count=numbers.length;
        head=new linked_list_doubly_node(numbers[0]);
        linked_list_doubly_node temp=head;
        for(int i=1;i<count;i++,temp=temp.next)
        {
            temp.next=new linked_list_doubly_node(numbers[i],temp,null);
        }
        tail=temp;
    }
    /**
    构造一个空双向链表。
    */
    public linked_list_doubly()
    {
        head=null;
        tail=null;
    }
    /**
    判断双向链表是否为空。
    @return 如果双向链表为空则返回true，否则返回false。
    */
    public boolean is_empty()
    {
        return count==0;
    }
    /**
    获取双向链表的元素数量。
    @return 双向链表的元素数量。
    */
    public int element_count()
    {
        return count;
    }
    /**
    获取双向链表中指定索引位置的元素。
    @param index 索引。<br>
    <ul>
        <li>index≥0表示从首节点开始向后计数，表示第index+1个元素。</li>
        <li>index&lt;0表示从尾节点开始向前计数，表示第count+index+1个元素。</li>
    </ul>
    @return 索引位置的元素。<br>
    若索引无效，则返回Integer.MAX_VALUE。
    */
    public int element_at(int index)
    {
        if(index>=0&&index<count)
        {
            linked_list_doubly_node pin=head;
            for(;index>0;pin=pin.next,index--);
            return pin.element;
        }
        else if(index<0&&index>=-count)
        {
            linked_list_doubly_node pin=tail;
            for(;index<-1;pin=pin.previous,index++);
            return pin.element;
        }
        else
        {
            return Integer.MAX_VALUE;
        }
    }
    /**
    获取双向链表中从前向后第一个出现指定元素的正向索引。
    @param element 目标元素。
    @return 目标元素的首个正向索引。<br>
    若双向链表中不存在目标元素，则返回Integer.MIN_VALUE。
    */
    public int index_forward(int element)
    {
        int result=0;
        for(linked_list_doubly_node pin=head;pin!=null;pin=pin.next,result++)
        {
            if(pin.element==element)
            {
                return result;
            }
        }
        return Integer.MIN_VALUE;
    }
    /**
    获取双向链表中从后向前第一个出现指定元素的反向索引。
    @param element 目标元素。
    @return 目标元素的首个反向索引。<br>
    若双向链表中不存在目标元素，则返回Integer.MIN_VALUE。
    */
    public int index_backward(int element)
    {
        int result=-1;
        for(linked_list_doubly_node pin=tail;pin!=null;pin=pin.previous,result--)
        {
            if(pin.element==element)
            {
                return result;
            }
        }
        return Integer.MIN_VALUE;
    }
    /**
    正向遍历双向链表。
    @return 正向遍历结果。
    */
    public int[] traversal_forward()
    {
        int result[]=new int[count];
        linked_list_doubly_node temp=head;
        for(int i=0;i<count;i++,temp=temp.next)
        {
            result[i]=temp.element;
        }
        return result;
    }
    /**
    反向遍历双向链表。
    @return 反向遍历结果。
    */
    public int[] traversal_backward()
    {
        int result[]=new int[count];
        linked_list_doubly_node temp=tail;
        for(int i=0;i<count;i++,temp=temp.previous)
        {
            result[i]=temp.element;
        }
        return result;
    }
    /**
	<p>此方法会修改调用对象。</p><br>
    向双向链表的末尾插入一个元素。
    @param number 要插入的元素。
    @return 插入的位置的反向索引。
    */
    public int input_tail(int number)
    {
        linked_list_doubly_node pin=tail;
        pin.next=new linked_list_doubly_node(number,pin,null);
        tail=pin.next;
        count++;
        return -1;
    }
    /**
	<p>此方法会修改调用对象。</p><br>
    向双向链表的末尾插入多个元素。
    @param numbers 要插入的多个元素。
    @return 插入的位置的反向索引。<br>
    若元素数组为空，则返回Integer.MIN_VALUE。
    */
    public int input_more_tail(int... numbers)
    {
        if(numbers!=null&&numbers.length>0)
        {
            linked_list_doubly_node pin=tail;
            for(int i=0;i<numbers.length;pin=pin.next,i++)
            {
                pin.next=new linked_list_doubly_node(numbers[i],pin,null);
            }
            tail=pin;
            count+=numbers.length;
            return -numbers.length;
        }
        else
        {
            return Integer.MIN_VALUE;
        }
    }
    /**
	<p>此方法会修改调用对象。</p><br>
    向双向链表的末尾插入另一个双向链表。
    @param list 要插入的双向链表。
    @return 插入的位置的反向索引。<br>
    若要插入的双向链表为空，则返回Integer.MIN_VALUE。
    */
    public int input_list_tail(linked_list_doubly list)
    {
        if(list!=null&&list.head!=null&&list.tail!=null)
        {
            linked_list_doubly_node pin=tail;
            list.head.previous=pin;
            pin.next=list.head;
            tail=list.tail;
            count+=list.count;
            return -list.count;
        }
        else
        {
            return Integer.MIN_VALUE;
        }
    }
    /**
	<p>此方法会修改调用对象。</p><br>
    向双向链表的开头插入一个元素。
    @param number 要插入的元素。
    @return 插入的位置的正向索引。
    */
    public int input_head(int number)
    {
        linked_list_doubly_node pin=head;
        pin.previous=new linked_list_doubly_node(number,null,pin);
        head=pin.previous;
        count++;
        return 0;
    }
    /**
	<p>此方法会修改调用对象。</p><br>
    向双向链表的开头插入多个元素。
    @param numbers 要插入的多个元素。
    @return 插入的位置的正向索引。<br>
    若元素数组为空，则返回Integer.MIN_VALUE。
    */
    public int input_more_head(int... numbers)
    {
        if(numbers!=null&&numbers.length>0)
        {
            linked_list_doubly_node pin=head;
            for(int i=numbers.length-1;i>=0;pin=pin.previous,i--)
            {
                pin.previous=new linked_list_doubly_node(numbers[i],null,pin);
            }
            head=pin;
            count+=numbers.length;
            return numbers.length-1;
        }
        else
        {
            return Integer.MIN_VALUE;
        }
    }
    /**
	<p>此方法会修改调用对象。</p><br>
    向双向链表的开头插入另一个双向链表。
    @param list 要插入的双向链表。
    @return 插入的位置的正向索引。<br>
    若要插入的双向链表为空，则返回Integer.MIN_VALUE。
    */
    public int input_list_head(linked_list_doubly list)
    {
        if(list!=null&&list.head!=null&&list.tail!=null)
        {
            linked_list_doubly_node pin=head;
            list.tail.next=pin;
            pin.previous=list.tail;
            head=list.head;
            count+=list.count;
            return list.count-1;
        }
        else
        {
            return Integer.MIN_VALUE;
        }
    }
    public String toString()
    {
        StringBuilder result=new StringBuilder("[");
        linked_list_doubly_node temp=head;
        while(temp!=null)
        {
            result.append(temp.element);
            if(temp.next!=null)
            {
                result.append("<=>");
            }
            temp=temp.next;
        }
        result.append("]");
        return result.toString();
    }
}