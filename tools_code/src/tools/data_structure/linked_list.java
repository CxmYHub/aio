package tools.data_structure;
/**
<p>单向链表类。</p><br>
单向链表属于链表的一种，是一种线性数据结构。<br>
对比数组，链表具有动态大小的优势，即可以在运行时根据需要动态添加或删除元素。<br>
同时，链表也可以在任意位置进行插入和删除操作。<br>
但链表的访问时间是线性的，即需要遍历链表才能访问到目标元素。<br>
<p>本单向链表以头节点形式实现，头节点不存储元素，而是存储链表中的元素数量。</p>
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
        element=numbers.length;
        linked_list temp=this;
        for(int i=0;i<numbers.length;i++)
        {
            temp.next=new linked_list(numbers[i],' ');
            temp=temp.next;
        }
        temp.element=numbers[numbers.length-1];
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
        element=0;
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
        return element==0;
    }
    /**
    获取单向链表的元素数量。
    @return 单向链表的元素数量。
    */
    public int element_count()
    {
        return element;
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
        else if(index>=element)
        {
            return Integer.MAX_VALUE;
        }
        else
        {
            linked_list temp=this;
            for(;index>=0;index--)
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
        linked_list temp=this;
        while(temp.next!=null)
        {
            if(temp.next.element==element)
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
        linked_list temp=this;
        while(temp.next!=null)
        {
            temp=temp.next;
        }
        temp.next=new linked_list(number,' ');
        element++;
        return number;
    }
    /**
	<p>此方法会修改调用对象。</p><br>
    在单向链表末尾插入多个元素。
    @param numbers 要插入的元素数组。
    @return 插入的元素数量。
    */
    public int input_more(int... numbers)
    {
        linked_list temp=this;
        while(temp.next!=null)
        {
            temp=temp.next;
        }
        temp.next=new linked_list(numbers).next;
        element+=numbers.length;
        return numbers.length;
    }
    /**
	<p>此方法会修改调用对象。</p><br>
    在单向链表末尾插入一个子链表。
    @param sub_list 要插入的子链表。
    @return 插入的元素数量。
    */
    public int input(linked_list sub_list)
    {
        linked_list temp=this;
        while(temp.next!=null)
        {
            temp=temp.next;
        }
        temp.next=sub_list.next;
        element+=sub_list.element;
        return element;
    }
    /**
	<p>此方法会修改调用对象。</p><br>
    删除单向链表末尾的多个元素。
    @param count 要删除的元素数量。
    @return 如果删除成功则返回剩余元素数量，否则返回Integer.MIN_VALUE(count&gt;元素数量，此时不删除)。
    */
    public int remove_last(int count)
    {
        if(count<0||count>element)
        {
            return Integer.MIN_VALUE;
        }
        else if(count==0)
        {
            return element;
        }
        else if(count==element)
        {
            element=0;
            next=null;
            return 0;
        }
        linked_list temp=this;
        for(int i=0;i<element-count;i++)
        {
            temp=temp.next;
        }
        temp.next=null;
        element-=count;
        return element;
    }
    /**
	<p>此方法会修改调用对象。</p><br>
    删除单向链表开头的多个元素。
    @param count 要删除的元素数量。
    @return 如果删除成功则返回剩余元素数量，否则返回Integer.MIN_VALUE(count&gt;元素数量，此时不删除)。
    */
    public int remove_first(int count)
    {
        if(count<0||count>element)
        {
            return Integer.MIN_VALUE;
        }
        else if(count==0)
        {
            return element;
        }
        else if(count==element)
        {
            element=0;
            next=null;
            return 0;
        }
        linked_list temp=this;
        for(int i=0;i<count;i++)
        {
            temp=temp.next;
        }
        next=temp.next;
        element-=count;
        return element;
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
        linked_list temp=this;
        while(temp.next!=null)
        {
            if(temp.next.element==element)
            {
                temp.next=temp.next.next;
                this.element--;
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
        linked_list temp=this;
        while(temp.next!=null)
        {
            if(temp.next.element>=min_element&&temp.next.element<=max_element)
            {
                temp.next=temp.next.next;
                element--;
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
    如果单向链表为空，则返回Integer.MIN_VALUE。
    */
    public int sort_ascend()
    {
        if(element>=2)
        {
            linked_list lists[]=new linked_list[element];
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
        else if(element==1)
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
        if(element>=2)
        {
            linked_list lists[]=new linked_list[element];
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
        else if(element==1)
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
        linked_list temp=this;
        while(temp.next!=null)
        {
            temp=temp.next;
            result.append(temp.element);
            if(temp.next!=null)
            {
                result.append("->");
            }
        }
        result.append("]");
        return result.toString();
    }
}