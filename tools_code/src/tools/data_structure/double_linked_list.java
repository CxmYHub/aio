package tools.data_structure;
/**
<p>双向链表类。</p><br>
双向链表属于链表的一种，是一种线性数据结构。<br>
对比数组，链表具有动态大小的优势，即可以在运行时根据需要动态添加或删除元素。<br>
同时，链表也可以在任意位置进行插入和删除操作。<br>
但链表的访问时间是线性的，即需要遍历链表才能访问到目标元素。<br>
<p>本双向链表以管理类+内部节点类形式实现，管理器存储链表的元素数量和头尾指针，内部节点存储链表的元素。</p>
*/
public class double_linked_list
{
    /**
    <p>双向链表节点类。</p><br>
    用于存储双向链表中的元素。<br>
    每个节点包含元素、前驱节点指针和后继节点指针。<br>
    前驱节点指针指向链表中的前一个节点，后继节点指针指向链表中的后一个节点。
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