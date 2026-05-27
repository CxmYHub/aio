package tools.data_structure;
/**
<p>单向链表类。</p><br>
单向链表属于链表的一种，是一种线性数据结构。<br>
对比数组，链表具有动态大小的优势，即可以在运行时根据需要动态添加或删除元素。<br>
同时，链表也可以在任意位置进行插入和删除操作。<br>
但链表的访问时间是线性的，即需要遍历链表才能访问到目标元素。<br><br>
本单向链表以头节点形式实现，头节点中的元素无效。
*/
public class linked_list
{
    public int element=0;
    public linked_list next=null;
    /**
    构造一个包含多个元素的单向链表。
    @param numbers 多个元素。
    */
    public linked_list(int... numbers)
    {
        linked_list new_node=this;
        for(int i=0;i<numbers.length;i++)
        {
            new_node.next=new linked_list(numbers[i],' ');
            new_node=new_node.next;
        }
        new_node.element=numbers[numbers.length-1];
    }
    /**
    构造一个指定元素和后继节点的单向链表节点。
    */
    public linked_list(int element,linked_list next)
    {
        this.element=element;
        this.next=next;
    }
    /**
    构造一个空单向链表。
    */
    public linked_list()
    {
    }
    private linked_list(int number,char inner_constant)
    {
        element=number;
    }
    /**
    判断单向链表是否为空。
    @return 如果单向链表为空则返回true，否则返回false。
    */
    public boolean is_empty()
    {
        return next==null;
    }
    /**
    获取单向链表的元素数量。
    @return 单向链表的元素数量。
    */
    public int element_count()
    {
        int count=0;
        for(linked_list pin=this.next;pin!=null;pin=pin.next,count++);
        return count;
    }
    /**
    获取单向链表中指定索引位置的元素。
    @param index 索引。
    @return 如果索引有效则返回对应元素，否则返回Integer.MIN_VALUE(索引&lt;0)或Integer.MAX_VALUE(索引≥元素数量)。
    */
    public int element_at(int index)
    {
        if(index<0)
        {
            return Integer.MIN_VALUE;
        }
        else
        {
            int count=0;
            for(linked_list pin=this.next;pin!=null;pin=pin.next,count++);
            if(index>=count)
            {
                return Integer.MAX_VALUE;
            }
            else
            {
                linked_list pin=this.next;
                for(;index>0;pin=pin.next,index--);
                return pin.element;
            }
        }
    }
    /**
    获取单向链表中第一个出现指定元素的索引。
    @param element 目标元素。
    @return 如果单向链表中存在目标元素则返回其索引，否则返回Integer.MIN_VALUE。
    */
    public int index_of(int element)
    {
        int result=0;
        for(linked_list pin=this.next;pin!=null;pin=pin.next,result++)
        {
            if(pin.element==element)
            {
                return result;
            }
        }
        return Integer.MIN_VALUE;
    }
    /**
    遍历单向链表。
    @return 遍历结果。
    */
    public int[] traversal()
    {
        int count=0;
        for(linked_list pin=this.next;pin!=null;pin=pin.next,count++);
        int result[]=new int[count];
        int i=0;
        for(linked_list pin=this.next;pin!=null;pin=pin.next,i++)
        {
            result[i]=pin.element;
        }
        return result;
    }
    /**
	<p>此方法会修改调用对象。</p><br>
    向单向链表的末尾插入一个元素。
    @param number 要插入的元素。
    @return 插入的位置。
    */
    public int input(int number)
    {
        linked_list pin=this;
        int count=0;
        for(;pin.next!=null;pin=pin.next,count++);
        pin.next=new linked_list(number,' ');
        return count;
    }
    /**
	<p>此方法会修改调用对象。</p><br>
    向单向链表的末尾插入多个元素。
    @param numbers 要插入的多个元素。
    @return 插入的位置。
    */
    public int input_more(int... numbers)
    {
        linked_list pin=this;
        int count=0;
        for(;pin.next!=null;pin=pin.next,count++);
        pin.next=new linked_list(numbers).next;
        return count;
    }
    /**
	<p>此方法会修改调用对象。</p><br>
    向单向链表的末尾插入一个子链表。
    @param sub_list 要插入的子链表。
    @return 插入的位置。
    */
    public int input_list(linked_list sub_list)
    {
        linked_list pin=this;
        int count=0;
        for(pin=this;pin.next!=null;pin=pin.next,count++);
        pin.next=sub_list.next;
        return count;
    }
    /**
	<p>此方法会修改调用对象。</p><br>
    向单向链表中指定索引位置插入一个元素。<br>
    当索引大于单向链表的元素数量时，将插入一系列空节点直至索引位置，最后插入目标元素。
    @param index 索引。
    @param number 要插入的元素。
    @return 插入的位置。
    */
    public int insert(int index,int number)
    {
        linked_list pin=this;
        int count=index;
        for(;pin.next!=null&&index>0;pin=pin.next,index--);
        for(;index>0;pin=pin.next,index--)
        {
            pin.next=new linked_list(0,' ');
        }
        linked_list insert_node=new linked_list(number,' ');
        insert_node.next=pin.next;
        pin.next=insert_node;
        return count;
    }
    /**
	<p>此方法会修改调用对象。</p><br>
    向单向链表中指定索引位置插入多个元素。
    @param index 索引。
    @param numbers 要插入的多个元素。
    @return 插入的位置。
    */
    public int insert_more(int index,int... numbers)
    {
        linked_list pin=this;
        int count=index;
        for(;pin.next!=null&&index>0;pin=pin.next,index--);
        for(;index>0;pin=pin.next,index--)
        {
            pin.next=new linked_list(0,' ');
        }
        linked_list insert_start=new linked_list(numbers).next;
        linked_list end=insert_start;
        for(;end.next!=null;end=end.next);
        end.next=pin.next;
        pin.next=insert_start;
        return count;
    }
    /**
	<p>此方法会修改调用对象。</p><br>
    向单向链表中指定索引位置插入一个子链表。
    @param index 索引。
    @param sub_list 要插入的子链表。
    @return 插入的位置。
    */
    public int insert_list(int index,linked_list sub_list)
    {
        linked_list pin=this;
        int count=index;
        for(;pin.next!=null&&index>0;pin=pin.next,index--);
        for(;index>0;pin=pin.next,index--)
        {
            pin.next=new linked_list(0,' ');
        }
        linked_list end=sub_list.next;
        for(;end.next!=null;end=end.next);
        end.next=pin.next;
        pin.next=sub_list.next;
        return count;
    }
    /**
	<p>此方法会修改调用对象。</p><br>
    删除单向链表末尾的多个元素。
    @param count 要删除的元素数量。
    @return 如果删除成功则返回剩余元素数量，否则返回Integer.MIN_VALUE(count&gt;元素数量，此时不删除)。
    */
    public int remove_last(int count)
    {
        if(count<0)
        {
            return Integer.MIN_VALUE;
        }
        else
        {
            linked_list front=this;
            linked_list back=this;
            for(;count>0;front=front.next,count--)
            {
                if(front.next==null)
                {
                    return Integer.MIN_VALUE;
                }
            }
            int node_count=0;
            for(;front.next!=null;front=front.next,back=back.next,node_count++);
            back.next=null;
            return node_count;
        }
    }
    /**
	<p>此方法会修改调用对象。</p><br>
    删除单向链表开头的多个元素。
    @param count 要删除的元素数量。
    @return 如果删除成功则返回删除的元素数量，否则返回Integer.MIN_VALUE(count&gt;元素数量，此时不删除)。
    */
    public int remove_first(int count)
    {
        if(count<0)
        {
            return Integer.MIN_VALUE;
        }
        else
        {
            int delete_count=count;
            linked_list pin=this;
            for(;count>0;pin=pin.next,count--)
            {
                if(pin.next==null)
                {
                    return Integer.MIN_VALUE;
                }
            }
            next=pin.next;
            return delete_count;
        }
    }
    /**
	<p>此方法会修改调用对象。</p><br>
    删除单向链表中所有的指定元素。
    @param element 要删除的元素。
    @return 删除的元素数量。
    */
    public int remove_element(int element)
    {
        int count=0;
        for(linked_list pin=this;pin.next!=null;)
        {
            if(pin.next.element==element)
            {
                linked_list start=pin;
                linked_list end=pin.next.next;
                for(count++;end!=null&&end.element==element;end=end.next,count++);
                start.next=end;
            }
            else
            {
                pin=pin.next;
            }
        }
        return count;
    }
    /**
	<p>此方法会修改调用对象。</p><br>
    删除单向链表中所有在[min_element,max_element]范围内的元素。
    @param min_element 删除范围的下限（包含）。
    @param max_element 删除范围的上限（包含）。
    @return 删除的元素数量。
    */
    public int remove_element(int min_element,int max_element)
    {
        int count=0;
        for(linked_list pin=this;pin.next!=null;)
        {
            if(pin.next.element>=min_element&&pin.next.element<=max_element)
            {
                linked_list start=pin;
                linked_list end=pin.next.next;
                for(count++;end!=null&&end.element>=min_element&&end.element<=max_element;end=end.next,count++);
                start.next=end;
            }
            else
            {
                pin=pin.next;
            }
        }
        return count;
    }
    /**
	<p>此方法会修改调用对象。</p><br>
    对单向链表进行升序排序。
    @return 排序后的第一个元素。<br>
    如果单向链表为空，则返回Integer.MIN_VALUE。
    */
    public int sort_ascend()
    {
        int count=0;
        for(linked_list pin=this.next;pin!=null;pin=pin.next,count++);
        if(count>=2)
        {
            linked_list lists[]=new linked_list[count];
            lists[0]=this;
            int pin=1;
            for(linked_list list=this.next.next;list!=null;)
            {
                linked_list head=new linked_list(1,list);
                list=list.next;
                lists[pin++]=head;
                head.next.next=null;
            }
            next.next=null;
            int size=lists.length;
            while(size>1)
            {
                for(int left_index=0,right_index=(size+1)>>1;right_index<size;right_index++,left_index++)
                {
                    linked_list left=lists[left_index];
                    linked_list right=lists[right_index];
                    linked_list start=right;
                    while(left.next!=null&&right.next!=null)
                    {
                        boolean insert=false;
                        while(right.next!=null&&left.next!=null&&right.next.element<=left.next.element)
                        {
                            right=right.next;
                            insert=true;
                        }
                        if(insert)
                        {
                            if(left.next!=null)
                            {
                                linked_list temp=left.next;
                                left.next=start.next;
                                start.next=right.next;
                                right.next=temp;
                                left=temp;
                                right=start;
                            }
                            else
                            {
                                break;
                            }
                        }
                        else
                        {
                            if(left.next.next!=null)
                            {
                                left=left.next;
                            }
                            else
                            {
                                left.next.next=start.next;
                                right.next=null;
                                break;
                            }
                        }
                    }
                    if(right.next!=null)
                    {
                        left.next=start.next;
                        right.next=null;
                    }
                }
                size=(size+1)>>1;
            }
            return lists[0].next.element;
        }
        else if(count==1)
        {
            return next.element;
        }
        else
        {
            return Integer.MIN_VALUE;
        }
    }
    /**
	<p>此方法会修改调用对象。</p><br>
    对单向链表进行降序排序。
    @return 排序后的第一个元素。<br>
    如果单向链表为空，则返回Integer.MIN_VALUE。
    */
    public int sort_descend()
    {
        int count=0;
        for(linked_list pin=this.next;pin!=null;pin=pin.next,count++);
        if(count>=2)
        {
            linked_list lists[]=new linked_list[count];
            lists[0]=this;
            int pin=1;
            for(linked_list list=this.next.next;list!=null;)
            {
                linked_list head=new linked_list(1,list);
                list=list.next;
                lists[pin++]=head;
                head.next.next=null;
            }
            next.next=null;
            int size=lists.length;
            while(size>1)
            {
                for(int left_index=0,right_index=(size+1)>>1;right_index<size;right_index++,left_index++)
                {
                    linked_list left=lists[left_index];
                    linked_list right=lists[right_index];
                    linked_list start=right;
                    while(left.next!=null&&right.next!=null)
                    {
                        boolean insert=false;
                        while(right.next!=null&&left.next!=null&&right.next.element>=left.next.element)
                        {
                            right=right.next;
                            insert=true;
                        }
                        if(insert)
                        {
                            if(left.next!=null)
                            {
                                linked_list temp=left.next;
                                left.next=start.next;
                                start.next=right.next;
                                right.next=temp;
                                left=temp;
                                right=start;
                            }
                            else
                            {
                                break;
                            }
                        }
                        else
                        {
                            if(left.next.next!=null)
                            {
                                left=left.next;
                            }
                            else
                            {
                                left.next.next=start.next;
                                right.next=null;
                                break;
                            }
                        }
                    }
                    if(right.next!=null)
                    {
                        left.next=start.next;
                        right.next=null;
                    }
                }
                size=(size+1)>>1;
            }
            return lists[0].next.element;
        }
        else if(count==1)
        {
            return next.element;
        }
        else
        {
            return Integer.MIN_VALUE;
        }
    }
    public String toString()
    {
        StringBuilder result=new StringBuilder("[");
        linked_list pin=this;
        while(pin.next!=null)
        {
            pin=pin.next;
            result.append(pin.element);
            if(pin.next!=null)
            {
                result.append("->");
            }
        }
        result.append("]");
        return result.toString();
    }
}