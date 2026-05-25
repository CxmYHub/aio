package tools.data_structure;
/**
<p>单向链表类。</p><br>
单向链表属于链表的一种，是一种线性数据结构。<br>
对比数组，链表具有动态大小的优势，即可以在运行时根据需要动态添加或删除元素。<br>
同时，链表也可以在任意位置进行插入和删除操作。<br>
但链表的访问时间是线性的，即需要遍历链表才能访问到目标元素。<br>
<p>本单向链表以头节点形式实现，头节点不存储元素，而是存储了链表的元素数量。</p>
*/
public class linked_list
{
    public static class linked_list_node
    {
        public int element;
        public linked_list_node next;
        public linked_list_node(int number)
        {
            element=number;
        }
    }
    public int element_count=0;
    public linked_list_node head;
    /**
    通过多个整数构造单向链表。
    @param numbers 多个整数。
    */
    public linked_list(int... numbers)
    {
        element_count=numbers.length;
        head=new linked_list_node(numbers[0]);
        linked_list_node temp=head;
        for(int i=1;i<element_count;i++)
        {
            temp.next=new linked_list_node(numbers[i]);
            temp=temp.next;
        }
    }
    /**
    构造一个空单向链表。
    */
    public linked_list()
    {
        element_count=0;
        head=null;
    }
    /**
    判断单向链表是否为空。
    @return 如果单向链表为空则返回true，否则返回false。
    */
    public boolean is_empty()
    {
        return element_count==0;
    }
    /**
    获取单向链表的元素数量。
    @return 单向链表的元素数量。
    */
    public int element_count()
    {
        return element_count;
    }
    /**
    获取单向链表中指定索引位置的元素。
    @param index 索引位置。
    @return 如果索引有效则返回对应元素，否则返回Integer.MIN_VALUE(索引&lt;0)或Integer.MAX_VALUE(索引≥元素数量)。
    */
    public int element_at(int index)
    {
        if(index<0)
        {
            return Integer.MIN_VALUE;
        }
        else if(index>=element_count)
        {
            return Integer.MAX_VALUE;
        }
        else
        {
            linked_list_node temp=head;
            for(;index>0;index--)
            {
                temp=temp.next;
            }
            return temp.element;
        }
    }
    /**
    获取单向链表中第一个出现指定元素的索引位置。
    @param element 目标元素。
    @return 如果单向链表中存在目标元素则返回其索引位置，否则返回Integer.MIN_VALUE。
    */
    public int index_of(int element)
    {
        int result=0;
        linked_list_node temp=head;
        while(temp!=null)
        {
            if(temp.element==element)
            {
                return result;
            }
            else
            {
                temp=temp.next;
                result++;
            }
        }
        return Integer.MIN_VALUE;
    }
    /**
	<p>此方法会修改调用对象。</p><br>
    在单向链表末尾插入一个元素。
    @param number 要插入的元素。
    @return 插入的元素。
    */
    public int input(int number)
    {
        if(head==null)
        {
            head=new linked_list_node(number);
            element_count=1;
            return number;
        }
        else
        {
            linked_list_node temp=head;
            while(temp.next!=null)
            {
                temp=temp.next;
            }
            temp.next=new linked_list_node(number);
            element_count++;
            return number;
        }
    }
    /**
	<p>此方法会修改调用对象。</p><br>
    在单向链表末尾插入多个元素。
    @param numbers 要插入的元素数组。
    @return 插入的元素数量。
    */
    public int input_more(int... numbers)
    {
        linked_list_node temp=head;
        if(head==null)
        {
            head=new linked_list_node(numbers[0]);
            temp=head;
        }
        else
        {
            while(temp.next!=null)
            {
                temp=temp.next;
            }
            temp.next=new linked_list_node(numbers[0]);
            temp=temp.next;
        }
        for(int i=1;i<numbers.length;i++)
        {
            temp.next=new linked_list_node(numbers[i]);
            temp=temp.next;
        }
        element_count+=numbers.length;
        return numbers.length;
    }
    /**
	<p>此方法会修改调用对象。</p><br>
    在单向链表末尾插入一个子单向链表。
    @param sub_list 要插入的子单向链表。
    @return 插入的元素数量。
    */
    public int input(linked_list sub_list)
    {
        if(head==null)
        {
            head=sub_list.head;
        }
        else
        {
            linked_list_node temp=head;
            while(temp.next!=null)
            {
                temp=temp.next;
            }
            temp.next=sub_list.head;
        }
        element_count+=sub_list.element_count;
        return element_count;
    }
    /**
	<p>此方法会修改调用对象。</p><br>
    删除单向链表末尾的多个元素。
    @param count 要删除的元素数量。
    @return 如果删除成功则返回剩余元素数量，否则返回Integer.MIN_VALUE(count&gt;元素数量，此时不删除)。
    */
    public int remove_last(int count)
    {
        if(count>0&&count<=element_count)
        {
            if(count==element_count)
            {
                head=null;
            }
            else
            {
                linked_list_node temp=head;
                for(int i=1;i<element_count-count;i++)
                {
                    temp=temp.next;
                }
                temp.next=null;
            }
            element_count-=count;
            return element_count;
        }
        else
        {
            return Integer.MIN_VALUE;
        }
    }
    /**
	<p>此方法会修改调用对象。</p><br>
    删除单向链表开头的多个元素。
    @param count 要删除的元素数量。
    @return 如果删除成功则返回剩余元素数量，否则返回Integer.MIN_VALUE(count&gt;元素数量，此时不删除)。
    */
    public int remove_first(int count)
    {
        if(count>0&&count<=element_count)
        {
            linked_list_node temp=head;
            for(int i=0;i<count;i++)
            {
                temp=temp.next;
            }
            head=temp;
            element_count-=count;
            return element_count;
        }
        else
        {
            return Integer.MIN_VALUE;
        }
    }
    /**
	<p>此方法会修改调用对象。</p><br>
    删除单向链表中所有的指定元素。
    @param element 目标元素。
    @return 删除的元素数量。
    */
    public int remove_element(int element)
    {
        int count=0;
        while(head.element==element)
        {
            head=head.next;
            element_count--;
            count++;
        }
        linked_list_node temp=head;
        while(temp.next!=null)
        {
            if(temp.next.element==element)
            {
                temp.next=temp.next.next;
                element_count--;
                count++;
            }
            else
            {
                temp=temp.next;
            }
        }
        return count;
    }
    /**
	<p>此方法会修改调用对象。</p><br>
    删除单向链表中所有在[min_element,max_element]范围内的元素。
    @param min_element 范围的下限（包含）。
    @param max_element 范围的上限（包含）。
    @return 删除的元素数量。
    */
    public int remove_element(int min_element,int max_element)
    {
        int count=0;
        while(head.element>=min_element&&head.element<=max_element)
        {
            head=head.next;
            element_count--;
            count++;
        }
        linked_list_node temp=head;
        while(temp.next!=null)
        {
            if(temp.next.element>=min_element&&temp.next.element<=max_element)
            {
                temp.next=temp.next.next;
                element_count--;
                count++;
            }
            else
            {
                temp=temp.next;
            }
        }
        return count;
    }
    /**
	<p>此方法会修改调用对象。</p><br>
    对单向链表进行升序排序。
    @return 排序后的第一个元素。<br>
    如果链表为空则返回Integer.MIN_VALUE。
    */
    public int sort_ascend()
    {
        if(head==null)
        {
            return Integer.MIN_VALUE;
        }
        linked_list_node temp1,temp2;
		int last_swap=element_count-1;
		for(int i=0;i<element_count-1;i++)
		{
			int new_last_swap=0;
            if(head.element>head.next.element)
            {
                temp1=head;
                temp2=head.next;
                temp1.next=temp2.next;
                temp2.next=temp1;
                head=temp2;
                new_last_swap=0;
            }
            linked_list_node pin=head;
			for(int j=1;j<last_swap;j++)
			{
				if(pin.next.element>pin.next.next.element)
				{
                    temp1=pin.next;
                    temp2=pin.next.next;
                    temp1.next=temp2.next;
                    temp2.next=temp1;
                    pin.next=temp2;
					new_last_swap=j;
				}
                pin=pin.next;
			}
			if(new_last_swap==0)
			{
				break;
			}
			last_swap=new_last_swap;
		}
        return head.element;
    }
    /**
	<p>此方法会修改调用对象。</p><br>
    对单向链表进行降序排序。
    @return 排序后的第一个元素。<br>
    如果链表为空则返回Integer.MIN_VALUE。
    */
    public int sort_descend()
    {
        if(head==null)
        {
            return Integer.MIN_VALUE;
        }
        linked_list_node temp1,temp2;
		int last_swap=element_count-1;
		for(int i=0;i<element_count-1;i++)
		{
			int new_last_swap=0;
            if(head.element<head.next.element)
            {
                temp1=head;
                temp2=head.next;
                temp1.next=temp2.next;
                temp2.next=temp1;
                head=temp2;
                new_last_swap=0;
            }
            linked_list_node pin=head;
			for(int j=1;j<last_swap;j++)
			{
				if(pin.next.element<pin.next.next.element)
				{
                    temp1=pin.next;
                    temp2=pin.next.next;
                    temp1.next=temp2.next;
                    temp2.next=temp1;
                    pin.next=temp2;
					new_last_swap=j;
				}
                pin=pin.next;
			}
			if(new_last_swap==0)
			{
				break;
			}
			last_swap=new_last_swap;
		}
        return head.element;
    }
    public String toString()
    {
        StringBuilder result=new StringBuilder("[");
        linked_list_node temp=head;
        while(temp!=null)
        {
            result.append(temp.element);
            temp=temp.next;
            if(temp!=null)
            {
                result.append("->");
            }
        }
        result.append("]");
        return result.toString();
    }
}