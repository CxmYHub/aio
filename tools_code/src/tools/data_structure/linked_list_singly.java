package tools.data_structure;
/**
<p>单向链表类。</p><br>
单向链表属于链表的一种，是一种线性数据结构。<br>
对比数组，链表具有动态大小的优势，即可以在运行时根据需要动态添加或删除元素。<br>
同时，链表也可以在任意位置进行插入和删除操作。<br>
但链表的访问时间是线性的，即需要遍历链表才能访问到目标元素。<br><br>
本单向链表以头节点形式实现，头节点中的元素无效。
*/
public class linked_list_singly
{
    public int element=0;
    public linked_list_singly next=null;
    /**
    构造一个包含多个元素的单向链表。
    @param numbers 多个元素。
    */
    public linked_list_singly(int... numbers)
    {
        linked_list_singly new_node=this;
        for(int i=0;i<numbers.length;i++)
        {
            new_node.next=new linked_list_singly(numbers[i],' ');
            new_node=new_node.next;
        }
    }
    /**
    构造一个指定元素和后继节点的单向链表节点。
    @param element 元素。
    @param next 后继节点。
    */
    public linked_list_singly(int element,linked_list_singly next)
    {
        this.element=element;
        this.next=next;
    }
    /**
    构造一个空单向链表。
    */
    public linked_list_singly()
    {
    }
    private linked_list_singly(int number,char inner_constant)
    {
        element=number;
    }
    /**
    判断单向链表是否为空。
    @return 是否为空。
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
        for(linked_list_singly pin=this.next;pin!=null;pin=pin.next,count++);
        return count;
    }
    /**
    获取单向链表中指定索引位置的元素。
    @param index 索引。
    @return 索引位置的元素。<br>
    若索引&lt;0，则返回<code>Integer.MIN_VALUE</code>。<br>
    若索引≥元素数量，则返回<code>Integer.MAX_VALUE</code>。
    */
    public int element_at(int index)
    {
        if(index>=0)
        {
            int count=0;
            for(linked_list_singly pin=this.next;pin!=null;pin=pin.next,count++);
            if(index>=count)
            {
                return Integer.MAX_VALUE;
            }
            else
            {
                linked_list_singly pin=this.next;
                for(;index>0;index--,pin=pin.next);
                return pin.element;
            }
        }
        else
        {
            return Integer.MIN_VALUE;
        }
    }
    /**
    获取单向链表中第一个出现指定元素的索引。
    @param element 目标元素。
    @return 目标元素的首个索引。<br>
    若单向链表中不存在目标元素，则返回<code>Integer.MIN_VALUE</code>。
    */
    public int index_of(int element)
    {
        int result=0;
        for(linked_list_singly pin=this.next;pin!=null;pin=pin.next,result++)
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
        for(linked_list_singly pin=this.next;pin!=null;pin=pin.next,count++);
        int result[]=new int[count];
        int i=0;
        for(linked_list_singly pin=this.next;pin!=null;pin=pin.next,i++)
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
    public int input_tail(int number)
    {
        linked_list_singly pin=this;
        int position=0;
        for(;pin.next!=null;pin=pin.next,position++);
        pin.next=new linked_list_singly(number,' ');
        return position;
    }
    /**
	<p>此方法会修改调用对象。</p><br>
    向单向链表的末尾插入多个元素。
    @param numbers 要插入的多个元素。
    @return 插入的位置。<br>
    若元素数组为空，则返回<code>Integer.MIN_VALUE</code>。
    */
    public int input_more_tail(int... numbers)
    {
        if(numbers!=null&&numbers.length>0)
        {
            linked_list_singly pin=this;
            int position=0;
            for(;pin.next!=null;pin=pin.next,position++);
            pin.next=new linked_list_singly(numbers).next;
            return position;
        }
        else
        {
            return Integer.MIN_VALUE;
        }
    }
    /**
	<p>此方法会修改调用对象。</p><br>
    向单向链表的末尾插入另一个单向链表。
    @param list 要插入的单向链表。
    @return 插入的位置。<br>
    若要插入的单向链表为空，则返回<code>Integer.MIN_VALUE</code>。
    */
    public int input_list_tail(linked_list_singly list)
    {
        if(list!=null&&list.next!=null)
        {
            linked_list_singly pin=this;
            int position=0;
            for(;pin.next!=null;pin=pin.next,position++);
            pin.next=list==null?null:list.next;
            return position;
        }
        else
        {
            return Integer.MIN_VALUE;
        }
    }
    /**
	<p>此方法会修改调用对象。</p><br>
    向单向链表的开头插入一个元素。
    @param number 要插入的元素。
    @return 插入的位置。
    */
    public int input_head(int number)
    {
        next=new linked_list_singly(number,next);
        return 0;
    }
    /**
	<p>此方法会修改调用对象。</p><br>
    向单向链表的开头插入多个元素。
    @param numbers 要插入的多个元素。
    @return 插入的位置。<br>
    若元素数组为空，则返回<code>Integer.MIN_VALUE</code>。
    */
    public int input_more_head(int... numbers)
    {
        if(numbers!=null&&numbers.length>0)
        {
            linked_list_singly sub_list=new linked_list_singly(numbers).next;
            linked_list_singly end=sub_list;
            for(;end.next!=null;end=end.next);
            end.next=next;
            next=sub_list;
            return 0;
        }
        else
        {
            return Integer.MIN_VALUE;
        }
    }
    /**
	<p>此方法会修改调用对象。</p><br>
    向单向链表的开头插入另一个单向链表。
    @param list 要插入的单向链表。
    @return 插入的位置。<br>
    若要插入的单向链表为空，则返回<code>Integer.MIN_VALUE</code>。
    */
    public int input_list_head(linked_list_singly list)
    {
        if(list!=null&&list.next!=null)
        {
            linked_list_singly end=list.next;
            for(;end.next!=null;end=end.next);
            end.next=next;
            next=list.next;
            return 0;
        }
        else
        {
            return Integer.MIN_VALUE;
        }
    }
    /**
	<p>此方法会修改调用对象。</p><br>
    向单向链表中指定索引位置插入一个元素。
    @param index 索引。<br>
    <ul>
        <li>当索引≤0时，将元素插入单向链表开头。</li>
        <li>当索引≥单向链表的元素数量时，将元素插入单向链表末尾。</li>
    </ul>
    @param number 要插入的元素。
    @return 插入的位置。
    */
    public int insert(int index,int number)
    {
        linked_list_singly pin=this;
        int position=0;
        for(;pin.next!=null&&index>0;pin=pin.next,index--,position++);
        linked_list_singly insert_node=new linked_list_singly(number,pin.next);
        pin.next=insert_node;
        return position;
    }
    /**
	<p>此方法会修改调用对象。</p><br>
    向单向链表中指定索引位置插入多个元素。
    @param index 索引。<br>
    <ul>
        <li>当索引≤0时，将元素插入单向链表开头。</li>
        <li>当索引≥单向链表的元素数量时，将元素插入单向链表末尾。</li>
    </ul>
    @param numbers 要插入的多个元素。
    @return 插入的位置。<br>
    若元素数组为空，则返回<code>Integer.MIN_VALUE</code>。
    */
    public int insert_more(int index,int... numbers)
    {
        if(numbers!=null&&numbers.length>0)
        {
            linked_list_singly pin=this;
            int position=0;
            for(;pin.next!=null&&index>0;pin=pin.next,index--,position++);
            linked_list_singly insert_start=new linked_list_singly(numbers).next;
            linked_list_singly end=insert_start;
            for(;end.next!=null;end=end.next);
            end.next=pin.next;
            pin.next=insert_start;
            return position;
        }
        else
        {
            return Integer.MIN_VALUE;
        }
    }
    /**
	<p>此方法会修改调用对象。</p><br>
    向单向链表中指定索引位置插入另一个单向链表。
    @param index 索引。<br>
    <ul>
        <li>当索引≤0时，将元素插入单向链表开头。</li>
        <li>当索引≥单向链表的元素数量时，将元素插入单向链表末尾。</li>
    </ul>
    @param list 要插入的单向链表。
    @return 插入的位置。<br>
    若要插入的单向链表为空，则返回<code>Integer.MIN_VALUE</code>。
    */
    public int insert_list(int index,linked_list_singly list)
    {
        if(list!=null&&list.next!=null)
        {
            linked_list_singly pin=this;
            int position=0;
            for(;pin.next!=null&&index>0;pin=pin.next,index--,position++);
            linked_list_singly end=list;
            for(;end.next!=null;end=end.next);
            end.next=pin.next;
            pin.next=list.next;
            return position;
        }
        else
        {
            return Integer.MIN_VALUE;
        }
    }
    /**
	<p>此方法会修改调用对象。</p><br>
    删除单向链表末尾的多个元素。
    @param count 要删除的元素数量。
    @return 删除的元素数量。<br>
    若<code>count</code>&gt;元素数量或<code>count</code>&lt;0或单向链表为空，则返回<code>Integer.MIN_VALUE</code>，此时不删除。
    */
    public int remove_tail(int count)
    {
        if(count>0&&next!=null)
        {
            int delete_count=count;
            linked_list_singly front=this;
            linked_list_singly back=this;
            for(;count>0;count--,front=front.next)
            {
                if(front.next==null)
                {
                    return Integer.MIN_VALUE;
                }
            }
            for(;front.next!=null;front=front.next,back=back.next);
            back.next=null;
            return delete_count;
        }
        else
        {
            return next!=null?0:Integer.MIN_VALUE;
        }
    }
    /**
	<p>此方法会修改调用对象。</p><br>
    删除单向链表开头的多个元素。
    @param count 要删除的元素数量。
    @return 删除的元素数量。<br>
    若<code>count</code>&gt;元素数量或<code>count</code>&lt;0或单向链表为空，则返回<code>Integer.MIN_VALUE</code>，此时不删除。
    */
    public int remove_head(int count)
    {
        if(count>0&&next!=null)
        {
            int delete_count=count;
            linked_list_singly pin=this;
            for(;count>0;count--,pin=pin.next)
            {
                if(pin.next==null)
                {
                    return Integer.MIN_VALUE;
                }
            }
            next=pin.next;
            return delete_count;
        }
        else
        {
            return next!=null?0:Integer.MIN_VALUE;
        }
    }
    /**
	<p>此方法会修改调用对象。</p><br>
    删除单向链表中指定索引位置的元素。
    @param index 要删除的元素的索引。
    @return 删除的元素。<br>
    若索引无效或单向链表为空，则返回<code>Integer.MIN_VALUE</code>，此时不删除。
    */
    public int remove_index(int index)
    {
        if(index>=0&&next!=null)
        {
            linked_list_singly pin=this;
            for(;index>0;index--,pin=pin.next)
            {
                if(pin.next==null)
                {
                    return Integer.MIN_VALUE;
                }
            }
            int delete_element=pin.next.element;
            pin.next=pin.next.next;
            return delete_element;
        }
        else
        {
            return Integer.MIN_VALUE;
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
        for(linked_list_singly pin=this;pin.next!=null;)
        {
            if(pin.next.element==element)
            {
                linked_list_singly start=pin;
                linked_list_singly end=pin.next.next;
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
    删除单向链表中所有在[<code>min_element</code>,<code>max_element</code>]范围内的元素。
    @param min_element 删除范围的下限（包含）。
    @param max_element 删除范围的上限（包含）。
    @return 删除的元素数量。
    */
    public int remove_element(int min_element,int max_element)
    {
        int count=0;
        for(linked_list_singly pin=this;pin.next!=null;)
        {
            if(pin.next.element>=min_element&&pin.next.element<=max_element)
            {
                linked_list_singly start=pin;
                linked_list_singly end=pin.next.next;
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
    如果单向链表为空，则返回<code>Integer.MIN_VALUE</code>。
    */
    public int sort_ascend()
    {
        int count=0;
        for(linked_list_singly pin=this.next;pin!=null;pin=pin.next,count++);
        if(count>=2)
        {
            linked_list_singly lists[]=new linked_list_singly[count];
            lists[0]=this;
            int pin=1;
            for(linked_list_singly list=this.next.next;list!=null;)
            {
                linked_list_singly head=new linked_list_singly(1,list);
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
                    linked_list_singly left=lists[left_index];
                    linked_list_singly right=lists[right_index];
                    linked_list_singly start=right;
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
                                linked_list_singly temp=left.next;
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
    如果单向链表为空，则返回<code>Integer.MIN_VALUE</code>。
    */
    public int sort_descend()
    {
        int count=0;
        for(linked_list_singly pin=this.next;pin!=null;pin=pin.next,count++);
        if(count>=2)
        {
            linked_list_singly lists[]=new linked_list_singly[count];
            lists[0]=this;
            int pin=1;
            for(linked_list_singly list=this.next.next;list!=null;)
            {
                linked_list_singly head=new linked_list_singly(1,list);
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
                    linked_list_singly left=lists[left_index];
                    linked_list_singly right=lists[right_index];
                    linked_list_singly start=right;
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
                                linked_list_singly temp=left.next;
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
        linked_list_singly pin=this;
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