package tools.data_structure;
/**
<p>双向链表类。</p><br>
双向链表属于链表的一种，是一种线性数据结构。<br>
对比数组，链表具有动态大小的优势，即可以在运行时根据需要动态添加或删除元素。<br>
同时，链表也可以在任意位置进行插入和删除操作。<br>
但链表的访问时间是线性的，即需要遍历链表才能访问到目标元素。<br><br>
本双向链表以管理类+内部节点类形式实现，管理器存储链表的元素数量和首尾指针，内部节点存储链表的元素。<br>
当索引大于等于0时，从首节点开始向后计数，称为正向索引。<br>
当索引小于0时，从尾节点开始向前计数，即当n&lt;0时，第n个节点表示第<code>count+n+1</code>个节点，或倒数第-n个节点，称为反向索引。
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
        head=tail=new linked_list_doubly_node(numbers[0]);
        for(int i=1;i<count;i++,tail=tail.next)
        {
            tail.next=new linked_list_doubly_node(numbers[i],tail,null);
        }
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
    @return 是否为空。
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
        <li><code>index</code>≥0表示从首节点开始向后计数，表示第<code>index+1</code>个元素。</li>
        <li><code>index</code>&lt;0表示从尾节点开始向前计数，表示第<code>count+index+1</code>个元素。</li>
    </ul>
    @return 索引位置的元素。<br>
    若索引无效，则返回<code>Integer.MAX_VALUE</code>。
    */
    public int element_at(int index)
    {
        if(index>=0&&index<count)
        {
            linked_list_doubly_node pin=head;
            for(;index>0;index--,pin=pin.next);
            return pin.element;
        }
        else if(index<0&&index>=-count)
        {
            linked_list_doubly_node pin=tail;
            for(;index<-1;index++,pin=pin.previous);
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
    若双向链表中不存在目标元素，则返回<code>Integer.MIN_VALUE</code>。
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
    若双向链表中不存在目标元素，则返回<code>Integer.MIN_VALUE</code>。
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
    计算双向链表中指定索引的相反索引。
    @param index 要转换的索引。
    @return 相反索引。<br>
    若输入的是正向索引，则返回对应位置的反向索引。<br>
    若输入的是反向索引，则返回对应位置的正向索引。
    */
    public int reverse_index(int index)
    {
        return index>=0?index-count:index+count;
    }
    /**
    计算双向链表中指定索引的最小索引。
    @param index 索引。
    @return 最小索引。<br>
    即指定索引位置的正向索引和反向索引中绝对值较小的一个。
    */
    public int min_index(int index)
    {
        int reversed_index=index>=0?index-count:index+count;
        return (reversed_index>=0?reversed_index:-reversed_index)>=(index>=0?index:-index)?index:reversed_index;
    }
    /**
    正向遍历双向链表。
    @return 正向遍历结果。
    */
    public int[] traversal_forward()
    {
        int result[]=new int[count];
        linked_list_doubly_node pin=head;
        for(int i=0;i<count;i++,pin=pin.next)
        {
            result[i]=pin.element;
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
        linked_list_doubly_node pin=tail;
        for(int i=0;i<count;i++,pin=pin.previous)
        {
            result[i]=pin.element;
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
        if(count==0)
        {
            head=tail=new linked_list_doubly_node(number);
        }
        else
        {
            tail.next=new linked_list_doubly_node(number,tail,null);
            tail=tail.next;
        }
        count++;
        return -1;
    }
    /**
	<p>此方法会修改调用对象。</p><br>
    向双向链表的末尾插入多个元素。
    @param numbers 要插入的多个元素。
    @return 插入的位置的反向索引。<br>
    若元素数组为空，则返回<code>Integer.MIN_VALUE</code>。
    */
    public int input_more_tail(int... numbers)
    {
        if(numbers!=null&&numbers.length>0)
        {
            if(count==0)
            {
                head=tail=new linked_list_doubly_node(numbers[0]);
            }
            else
            {
                tail.next=new linked_list_doubly_node(numbers[0],tail,null);
                tail=tail.next;
            }
            for(int i=1;i<numbers.length;i++,tail=tail.next)
            {
                tail.next=new linked_list_doubly_node(numbers[i],tail,null);
            }
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
    若要插入的双向链表为空，则返回<code>Integer.MIN_VALUE</code>。
    */
    public int input_list_tail(linked_list_doubly list)
    {
        if(list!=null&&list.count>0)
        {
            if(count==0)
            {
                head=list.head;
            }
            else
            {
                list.head.previous=tail;
                tail.next=list.head;
            }
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
        if(count==0)
        {
            head=tail=new linked_list_doubly_node(number);
        }
        else
        {
            head.previous=new linked_list_doubly_node(number,null,head);
            head=head.previous;
        }
        count++;
        return 0;
    }
    /**
	<p>此方法会修改调用对象。</p><br>
    向双向链表的开头插入多个元素。
    @param numbers 要插入的多个元素。
    @return 插入的位置的正向索引。<br>
    若元素数组为空，则返回<code>Integer.MIN_VALUE</code>。
    */
    public int input_more_head(int... numbers)
    {
        if(numbers!=null&&numbers.length>0)
        {
            if(count==0)
            {
                head=tail=new linked_list_doubly_node(numbers[numbers.length-1]);
            }
            else
            {
                head.previous=new linked_list_doubly_node(numbers[numbers.length-1],null,head);
                head=head.previous;
            }
            for(int i=numbers.length-2;i>=0;i--,head=head.previous)
            {
                head.previous=new linked_list_doubly_node(numbers[i],null,head);
            }
            count+=numbers.length;
            return 0;
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
    若要插入的双向链表为空，则返回<code>Integer.MIN_VALUE</code>。
    */
    public int input_list_head(linked_list_doubly list)
    {
        if(list!=null&&list.count>0)
        {
            if(count==0)
            {
                tail=list.tail;
            }
            else
            {
                list.tail.next=head;
                head.previous=list.tail;
            }
            head=list.head;
            count+=list.count;
            return 0;
        }
        else
        {
            return Integer.MIN_VALUE;
        }
    }
    /**
	<p>此方法会修改调用对象。</p><br>
    向双向链表中指定索引位置插入一个元素。
    @param index 索引。<br>
    <ul>
        <li>当正向索引≥双向链表的元素数量时，将元素插入双向链表末尾。</li>
        <li>当反向索引≤双向链表的元素数量的相反数-1时，将元素插入双向链表开头。</li>
    </ul>
    @param number 要插入的元素。
    @return 插入位置的最短索引。<br>
    即插入位置的正向索引和反向索引中绝对值较小的一个。
    */
    public int insert(int index,int number)
    {
        if(count==0)
        {
            head=tail=new linked_list_doubly_node(number);
            count=1;
            return 0;
        }
        else
        {
            if(index==0||index<-count)
            {
                head.previous=new linked_list_doubly_node(number,null,head);
                head=head.previous;
                count++;
                return 0;
            }
            else if(index==-1||index>=count)
            {
                tail.next=new linked_list_doubly_node(number,tail,null);
                tail=tail.next;
                count++;
                return -1;
            }
            int reversed_index=index>=0?index-count-1:index+count+1;
            index=(reversed_index>=0?reversed_index:-reversed_index)>=(index>=0?index:-index)?index:reversed_index;
            int position=1;
            if(index>0)
            {
                linked_list_doubly_node pin=head;
                for(;index>1;index--,position++,pin=pin.next);
                pin.next=new linked_list_doubly_node(number,pin,pin.next);
                pin.next.next.previous=pin.next;
                count++;
                return position;
            }
            else
            {
                linked_list_doubly_node pin=tail;
                for(position=-2;index<-2;index++,position--,pin=pin.previous);
                pin.previous=new linked_list_doubly_node(number,pin.previous,pin);
                pin.previous.previous.next=pin.previous;
                count++;
                int reversed_position=position>=0?position-count:position+count;
                position=(reversed_position>=0?reversed_position:-reversed_position)>=(position>=0?position:-position)?position:reversed_position;
                return position;
            }
        }
    }
    /**
	<p>此方法会修改调用对象。</p><br>
    向双向链表中指定索引位置插入多个元素。
    @param index 索引。<br>
    <ul>
        <li>当正向索引≥双向链表的元素数量时，将元素插入双向链表末尾。</li>
        <li>当反向索引≤双向链表的元素数量的相反数-1时，将元素插入双向链表开头。</li>
    </ul>
    @param numbers 要插入的多个元素。
    @return 插入位置的最短索引。<br>
    即插入位置的正向索引和反向索引中绝对值较小的一个。
    */
    public int insert_more(int index,int... numbers)
    {
        if(count==0)
        {
            count=numbers.length;
            head=tail=new linked_list_doubly_node(numbers[0]);
            for(int i=1;i<count;i++,tail=tail.next)
            {
                tail.next=new linked_list_doubly_node(numbers[i],tail,null);
            }
            return 0;
        }
        else
        {
            if(index==0||index<-count)
            {
                for(int i=numbers.length-1;i>=0;i--,head=head.previous)
                {
                    head.previous=new linked_list_doubly_node(numbers[i],null,head);
                }
                count+=numbers.length;
                return 0;
            }
            else if(index==-1||index>=count)
            {
                for(int i=0;i<numbers.length;i++,tail=tail.next)
                {
                    tail.next=new linked_list_doubly_node(numbers[i],tail,null);
                }
                count+=numbers.length;
                return numbers.length<count>>1?-numbers.length:count-numbers.length;
            }
            int reversed_index=index>=0?index-count-1:index+count+1;
            index=(reversed_index>=0?reversed_index:-reversed_index)>=(index>=0?index:-index)?index:reversed_index;
            int position=1;
            if(index>0)
            {
                linked_list_doubly_node pin=head;
                for(;index>1;index--,position++,pin=pin.next);
                for(int i=0;i<numbers.length;i++,pin=pin.next)
                {
                    pin.next=new linked_list_doubly_node(numbers[i],pin,pin.next);
                }
                pin.next.previous=pin;
                count+=numbers.length;
                return position;
            }
            else
            {
                linked_list_doubly_node pin=tail;
                for(position=-numbers.length-1;index<-2;index++,position--,pin=pin.previous);
                for(int i=numbers.length-1;i>=0;i--,pin=pin.previous)
                {
                    pin.previous=new linked_list_doubly_node(numbers[i],pin.previous,pin);
                }
                pin.previous.next=pin;
                count+=numbers.length;
                int reversed_position=position>=0?position-count:position+count;
                position=(reversed_position>=0?reversed_position:-reversed_position)>=(position>=0?position:-position)?position:reversed_position;
                return position;
            }
        }
    }
    /**
	<p>此方法会修改调用对象。</p><br>
    向双向链表中指定索引位置插入另一个双向链表。
    @param index 索引。<br>
    <ul>
        <li>当正向索引≥双向链表的元素数量时，将元素插入双向链表末尾。</li>
        <li>当反向索引≤双向链表的元素数量的相反数-1时，将元素插入双向链表开头。</li>
    </ul>
    @param list 要插入的双向链表。
    @return 插入位置的最短索引。<br>
    即插入位置的正向索引和反向索引中绝对值较小的一个。
    */
    public int insert_list(int index,linked_list_doubly list)
    {
        if(count==0)
        {
            head=list.head;
            tail=list.tail;
            count=list.count;
            return 0;
        }
        else
        {
            if(index==0||index<-count)
            {
                head.previous=list.tail;
                list.tail.next=head;
                head=list.head;
                count+=list.count;
                return 0;
            }
            else if(index==-1||index>=count)
            {
                tail.next=list.head;
                list.head.previous=tail;
                tail=list.tail;
                count+=list.count;
                return list.count<count>>1?-list.count:count-list.count;
            }
            int reversed_index=index>=0?index-count-1:index+count+1;
            index=(reversed_index>=0?reversed_index:-reversed_index)>=(index>=0?index:-index)?index:reversed_index;
            int position=1;
            if(index>0)
            {
                linked_list_doubly_node pin=head;
                for(;index>1;index--,position++,pin=pin.next);
                pin.next.previous=list.tail;
                list.tail.next=pin.next;
                pin.next=list.head;
                list.head.previous=pin;
                count+=list.count;
                return position;
            }
            else
            {
                linked_list_doubly_node pin=tail;
                for(position=-list.count-1;index<-2;index++,position--,pin=pin.previous);
                pin.previous.next=list.head;
                list.head.previous=pin.previous;
                pin.previous=list.tail;
                list.tail.next=pin;
                count+=list.count;
                int reversed_position=position>=0?position-count:position+count;
                position=(reversed_position>=0?reversed_position:-reversed_position)>=(position>=0?position:-position)?position:reversed_position;
                return position;
            }
        }
    }
    /**
	<p>此方法会修改调用对象。</p><br>
    删除双向链表末尾的多个元素。
    @param count 要删除的元素数量。
    @return 删除的元素数量。<br>
    若<code>count</code>&gt;元素数量或<code>count</code>&lt;0或双向链表为空，则返回<code>Integer.MIN_VALUE</code>，此时不删除。
    */
    public int remove_tail(int count)
    {
        if(count==this.count)
        {
            head=null;
            tail=null;
            this.count=0;
            return count;
        }
        else if(count>0&&count<this.count)
        {
            int delete_count=count;
            for(;count>0;count--,tail=tail.previous);
            tail.next=null;
            this.count-=delete_count;
            return delete_count;
        }
        else
        {
            return (count==0&&this.count!=0)?0:Integer.MIN_VALUE;
        }
    }
    /**
	<p>此方法会修改调用对象。</p><br>
    删除双向链表开头的多个元素。
    @param count 要删除的元素数量。
    @return 删除的元素数量。<br>
    若<code>count</code>&gt;元素数量或<code>count</code>&lt;0或双向链表为空，则返回<code>Integer.MIN_VALUE</code>，此时不删除。
    */
    public int remove_head(int count)
    {
        if(count==this.count)
        {
            head=null;
            tail=null;
            this.count=0;
            return count;
        }
        else if(count>0&&count<this.count)
        {
            int delete_count=count;
            for(;count>0;count--,head=head.next);
            head.previous=null;
            this.count-=delete_count;
            return delete_count;
        }
        else
        {
            return (count==0&&this.count!=0)?0:Integer.MIN_VALUE;
        }
    }
    /**
	<p>此方法会修改调用对象。</p><br>
    删除双向链表中指定索引位置的元素。
    @param index 要删除的元素的索引。<br>
    <ul>
        <li>当正向索引≥双向链表的元素数量时，视为无效索引。</li>
        <li>当反向索引&lt;双向链表的元素数量的相反数时，视为无效索引。</li>
    </ul>
    @return 删除的元素。<br>
    若索引无效或双向链表为空，则返回<code>Integer.MIN_VALUE</code>，此时不删除。
    */
    public int remove_index(int index)
    {
        if(index>=-count&&index<count)
        {
            int reversed_index=index>=0?index-count:index+count;
            index=(reversed_index>=0?reversed_index:-reversed_index)>=(index>=0?index:-index)?index:reversed_index;
            if(index==0)
            {
                int deleted_element=head.element;
                head=head.next;
                if(count>1)
                {
                    head.previous=null;
                }
                else
                {
                    tail=null;
                }
                count--;
                return deleted_element;
            }
            else if(index==-1)
            {
                int deleted_element=tail.element;
                tail=tail.previous;
                if(count>1)
                {
                    tail.next=null;
                }
                else
                {
                    head=null;
                }
                count--;
                return deleted_element;
            }
            else if(index>0)
            {
                linked_list_doubly_node pin=head;
                for(;index>0;index--,pin=pin.next);
                int deleted_element=pin.element;
                pin.previous.next=pin.next;
                pin.next.previous=pin.previous;
                count--;
                return deleted_element;
            }
            else
            {
                linked_list_doubly_node pin=tail;
                for(;index<-1;index++,pin=pin.previous);
                int deleted_element=pin.element;
                pin.previous.next=pin.next;
                pin.next.previous=pin.previous;
                count--;
                return deleted_element;
            }
        }
        else
        {
            return Integer.MIN_VALUE;
        }
    }
    /**
	<p>此方法会修改调用对象。</p><br>
    删除双向链表中所有的指定元素。
    @param element 要删除的元素。
    @return 删除的元素数量。
    */
    public int remove_element(int element)
    {
        int after_count=count;
        int delete_count=0;
        linked_list_doubly_node pin=head;
        for(;pin!=null&&pin.element==element;pin=pin.next,after_count--);
        if(after_count!=count)
        {
            if(pin==null)
            {
                head=null;
                tail=null;
                delete_count+=count-after_count;
                count=0;
                return delete_count;
            }
            else
            {
                pin.previous=null;
                head=pin;
                delete_count+=count-after_count;
                count=after_count;
            }
        }
        for(pin=tail;pin!=null&&pin.element==element;pin=pin.previous,after_count--);
        if(after_count!=count)
        {
            if(pin==null)
            {
                head=null;
                tail=null;
                delete_count+=count-after_count;
                count=0;
                return delete_count;
            }
            else
            {
                pin.next=null;
                tail=pin;
                delete_count+=count-after_count;
                count=after_count;
            }
        }
        for(;pin!=null;pin=pin.previous)
        {
            if(pin.element==element)
            {
                pin.previous.next=pin.next;
                pin.next.previous=pin.previous;
                delete_count++;
                count--;
            }
        }
        return delete_count;
    }
    /**
	<p>此方法会修改调用对象。</p><br>
    删除双向链表中所有在[<code>min_element</code>,<code>max_element</code>]范围内的元素。
    @param min_element 删除范围的下限（包含）。
    @param max_element 删除范围的上限（包含）。
    @return 删除的元素数量。
    */
    public int remove_element(int min_element,int max_element)
    {
        int after_count=count;
        int delete_count=0;
        linked_list_doubly_node pin=head;
        for(;pin!=null&&pin.element>=min_element&&pin.element<=max_element;pin=pin.next,after_count--);
        if(after_count!=count)
        {
            if(pin==null)
            {
                head=null;
                tail=null;
                delete_count+=count-after_count;
                count=0;
                return delete_count;
            }
            else
            {
                pin.previous=null;
                head=pin;
                delete_count+=count-after_count;
                count=after_count;
            }
        }
        for(pin=tail;pin!=null&&pin.element>=min_element&&pin.element<=max_element;pin=pin.previous,after_count--);
        if(after_count!=count)
        {
            if(pin==null)
            {
                head=null;
                tail=null;
                delete_count+=count-after_count;
                count=0;
                return delete_count;
            }
            else
            {
                pin.next=null;
                tail=pin;
                delete_count+=count-after_count;
                count=after_count;
            }
        }
        for(;pin!=null;pin=pin.previous)
        {
            if(pin.element>=min_element&&pin.element<=max_element)
            {
                pin.previous.next=pin.next;
                pin.next.previous=pin.previous;
                delete_count++;
                count--;
            }
        }
        return delete_count;
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