package tools.data_structure;
/**
<p>双向链表类。</p><br>
双向链表属于链表的一种，是一种线性数据结构。<br>
对比数组，链表具有动态大小的优势，即可以在运行时根据需要动态添加或删除元素。<br>
同时，链表也可以在任意位置进行插入和删除操作。<br>
但链表的访问时间是线性的，即需要遍历链表才能访问到目标元素。<br><br>
本双向链表以管理类+内部节点类形式实现，管理器存储链表的元素数量和首尾指针，内部节点存储链表的元素。<br>
当索引大于等于0时，从首节点开始向后计数，称为正向索引。<br>
当索引小于0时，从尾节点开始向前计数，即当n<0时，第n个节点表示第count+n+1个节点，或倒数第-n个节点，称为反向索引。
*/
public class double_linked_list
{
    /**
    <p>双向链表节点类。</p><br>
    用于存储双向链表中的元素。<br>
    每个节点包含元素、前驱节点指针和后继节点指针。<br>
    前驱节点指针指向当前节点的前一个节点，后继节点指针指向当前节点的后一个节点。
    */
    public static class double_linked_list_node
    {
        public int element;
        public double_linked_list_node previous;
        public double_linked_list_node next;
        public double_linked_list_node(int element,double_linked_list_node previous,double_linked_list_node next)
        {
            this.element=element;
            this.previous=previous;
            this.next=next;
        }
        public double_linked_list_node(int element)
        {
            this.element=element;
            this.previous=null;
            this.next=null;
        }
        public double_linked_list_node()
        {
            this.element=0;
            this.previous=null;
            this.next=null;
        }
    }
    public int count=0;
    public double_linked_list_node head=null;
    public double_linked_list_node tail=null;
    /**
    构造一个包含多个元素的双向链表。
    @param numbers 多个元素。
    */
    public double_linked_list(int ...numbers)
    {
        count=numbers.length;
        head=new double_linked_list_node(numbers[0]);
        double_linked_list_node temp=head;
        for(int i=1;i<count;i++,temp=temp.next)
        {
            temp.next=new double_linked_list_node(numbers[i],temp,null);
        }
        tail=temp;
    }
    /**
    构造一个空双向链表。
    */
    public double_linked_list()
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
    @return 如果索引有效则返回对应元素，否则返回Integer.MAX_VALUE。
    */
    public int element_at(int index)
    {
        if(index>=0&&index<count)
        {
            double_linked_list_node pin=head;
            for(;index>0;pin=pin.next,index--);
            return pin.element;
        }
        else if(index<0&&index>=-count)
        {
            double_linked_list_node pin=tail;
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
    @return 如果双向链表中存在目标元素则返回其正向索引，否则返回Integer.MIN_VALUE。
    */
    public int index_forward(int element)
    {
        int result=0;
        for(double_linked_list_node pin=head;pin!=null;pin=pin.next,result++)
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
    @return 如果双向链表中存在目标元素则返回其反向索引，否则返回Integer.MIN_VALUE。
    */
    public int index_backward(int element)
    {
        int result=-1;
        for(double_linked_list_node pin=tail;pin!=null;pin=pin.previous,result--)
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
        double_linked_list_node temp=head;
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
        double_linked_list_node temp=tail;
        for(int i=0;i<count;i++,temp=temp.previous)
        {
            result[i]=temp.element;
        }
        return result;
    }
    public String toString()
    {
        StringBuilder result=new StringBuilder("[");
        double_linked_list_node temp=head;
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