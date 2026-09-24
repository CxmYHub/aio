package aio.datastructure;
/**
<p>单向链表类</p><br>
单向链表属于链表的一种，是一种线性数据结构。<br>
对比数组，链表具有动态大小的优势，即可以在运行时根据需要动态添加或删除元素。<br>
同时，链表也可以在任意位置进行插入和删除操作。<br>
但链表的访问时间是线性的，即需要遍历链表才能访问到目标元素。<br><br>
本单向链表以头结点形式实现，头结点中的元素无效。
*/
public class LinkedListSingly {
    /**
    <p>元素</p>
    */
    public int element;
    /**
    <p>后继结点指针</p>
    */
    public LinkedListSingly next=null;
    /**
    <p>全参结点构造方法</p><br>
    构造一个指定元素和后继结点的单向链表结点。
    @param element 元素。
    @param next 后继结点。
    */
    public LinkedListSingly(int element,LinkedListSingly next) {
        this.element=element;
        this.next=next;
    }
    /**
    <p>构造方法</p><br>
    构造一个包含多个元素的单向链表。
    @param numbers 多个元素。
    */
    public LinkedListSingly(int... numbers) {
        LinkedListSingly newNode=this;
        for(int i=0;i<numbers.length;i++) {
            newNode.next=new LinkedListSingly(numbers[i],' ');
            newNode=newNode.next;
        }
    }
    /**
    <p>无参构造方法</p><br>
    构造一个空单向链表。
    */
    public LinkedListSingly() {
        element=0;
    }
    /**
    <p>构造方法</p><br>
    构造一个指定元素的单向链表结点。
    @param number 元素。
    @param innerConstant 哑元，用于区分方法。
    */
    private LinkedListSingly(int number,char innerConstant) {
        element=number;
    }
    /**
    <p>空判断</p><br>
    判断单向链表是否为空。
    @return 是否为空。
    */
    public boolean isEmpty() {
        return next==null;
    }
    /**
    <p>元素计数</p><br>
    获取单向链表的元素数量。
    @return 单向链表的元素数量。
    */
    public int elementCount() {
        int count=0;
        for(LinkedListSingly pin=next;pin!=null;pin=pin.next,count++);
        return count;
    }
    /**
    <p>元素获取</p><br>
    获取单向链表中指定索引位置的元素。
    @param index 索引。
    @return 索引位置的元素。<br>
    若索引&lt;0，则返回<code>Integer.MIN_VALUE</code>。<br>
    若索引≥元素数量，则返回<code>Integer.MAX_VALUE</code>。
    */
    public int elementAt(int index) {
        if(index>=0) {
            int count=0;
            for(LinkedListSingly pin=next;pin!=null;pin=pin.next,count++);
            if(index>=count) {
                return Integer.MAX_VALUE;
            } else {
                LinkedListSingly pin=next;
                for(;index>0;index--,pin=pin.next);
                return pin.element;
            }
        } else {
            return Integer.MIN_VALUE;
        }
    }
    /**
    <p>索引查询</p><br>
    获取单向链表中第一个出现指定元素的索引。
    @param element 目标元素。
    @return 目标元素的首个索引。<br>
    若单向链表中不存在目标元素，则返回<code>Integer.MIN_VALUE</code>。
    */
    public int indexOf(int element) {
        int result=0;
        for(LinkedListSingly pin=next;pin!=null;pin=pin.next,result++) {
            if(pin.element==element) {
                return result;
            }
        }
        return Integer.MIN_VALUE;
    }
    /**
    <p>遍历</p><br>
    遍历单向链表。
    @return 遍历结果。
    */
    public int[] traversal() {
        int count=0;
        for(LinkedListSingly pin=next;pin!=null;pin=pin.next,count++);
        int result[]=new int[count];
        int i=0;
        for(LinkedListSingly pin=next;pin!=null;pin=pin.next,i++) {
            result[i]=pin.element;
        }
        return result;
    }
    /**
    <p>元素尾插</p><br>
    <p>此方法会修改调用对象。</p><br>
    向单向链表的末尾插入一个元素。
    @param number 要插入的元素。
    @return 插入的位置。
    */
    public int inputBack(int number) {
        LinkedListSingly pin=this;
        int position=0;
        for(;pin.next!=null;pin=pin.next,position++);
        pin.next=new LinkedListSingly(number,' ');
        return position;
    }
    /**
    <p>元素批量尾插</p><br>
    <p>此方法会修改调用对象。</p><br>
    向单向链表的末尾插入多个元素。
    @param numbers 要插入的多个元素。
    @return 插入的位置。<br>
    若元素数组为空，则返回<code>Integer.MIN_VALUE</code>。
    */
    public int inputMoreBack(int... numbers) {
        if(numbers!=null&&numbers.length>0) {
            LinkedListSingly pin=this;
            int position=0;
            for(;pin.next!=null;pin=pin.next,position++);
            pin.next=new LinkedListSingly(numbers).next;
            return position;
        } else {
            return Integer.MIN_VALUE;
        }
    }
    /**
    <p>列表尾插</p><br>
    <p>此方法会修改调用对象。</p><br>
    向单向链表的末尾插入另一个单向链表。
    @param list 要插入的单向链表。
    @return 插入的位置。<br>
    若要插入的单向链表为空，则返回<code>Integer.MIN_VALUE</code>。
    */
    public int inputListBack(LinkedListSingly list) {
        if(list!=null&&list.next!=null) {
            LinkedListSingly pin=this;
            int position=0;
            for(;pin.next!=null;pin=pin.next,position++);
            pin.next=list==null?null:list.next;
            return position;
        } else {
            return Integer.MIN_VALUE;
        }
    }
    /**
    <p>元素头插</p><br>
    <p>此方法会修改调用对象。</p><br>
    向单向链表的开头插入一个元素。
    @param number 要插入的元素。
    @return 插入的位置。
    */
    public int inputFront(int number) {
        next=new LinkedListSingly(number,next);
        return 0;
    }
    /**
    <p>元素批量头插</p><br>
    <p>此方法会修改调用对象。</p><br>
    向单向链表的开头插入多个元素。
    @param numbers 要插入的多个元素。
    @return 插入的位置。<br>
    若元素数组为空，则返回<code>Integer.MIN_VALUE</code>。
    */
    public int inputMoreFront(int... numbers) {
        if(numbers!=null&&numbers.length>0) {
            LinkedListSingly subList=new LinkedListSingly(numbers).next;
            LinkedListSingly end=subList;
            for(;end.next!=null;end=end.next);
            end.next=next;
            next=subList;
            return 0;
        } else {
            return Integer.MIN_VALUE;
        }
    }
    /**
    <p>列表头插</p><br>
    <p>此方法会修改调用对象。</p><br>
    向单向链表的开头插入另一个单向链表。
    @param list 要插入的单向链表。
    @return 插入的位置。<br>
    若要插入的单向链表为空，则返回<code>Integer.MIN_VALUE</code>。
    */
    public int inputListFront(LinkedListSingly list) {
        if(list!=null&&list.next!=null) {
            LinkedListSingly end=list.next;
            for(;end.next!=null;end=end.next);
            end.next=next;
            next=list.next;
            return 0;
        } else {
            return Integer.MIN_VALUE;
        }
    }
    /**
    <p>元素输入</p><br>
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
    public int insert(int index,int number) {
        LinkedListSingly pin=this;
        int position=0;
        for(;pin.next!=null&&index>0;pin=pin.next,index--,position++);
        LinkedListSingly insertNode=new LinkedListSingly(number,pin.next);
        pin.next=insertNode;
        return position;
    }
    /**
    <p>元素批量输入</p><br>
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
    public int insertMore(int index,int... numbers) {
        if(numbers!=null&&numbers.length>0) {
            LinkedListSingly pin=this;
            int position=0;
            for(;pin.next!=null&&index>0;pin=pin.next,index--,position++);
            LinkedListSingly insertStart=new LinkedListSingly(numbers).next;
            LinkedListSingly end=insertStart;
            for(;end.next!=null;end=end.next);
            end.next=pin.next;
            pin.next=insertStart;
            return position;
        } else {
            return Integer.MIN_VALUE;
        }
    }
    /**
    <p>列表输入</p><br>
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
    public int insertList(int index,LinkedListSingly list) {
        if(list!=null&&list.next!=null) {
            LinkedListSingly pin=this;
            int position=0;
            for(;pin.next!=null&&index>0;pin=pin.next,index--,position++);
            LinkedListSingly end=list;
            for(;end.next!=null;end=end.next);
            end.next=pin.next;
            pin.next=list.next;
            return position;
        } else {
            return Integer.MIN_VALUE;
        }
    }
    /**
    <p>批量尾删</p><br>
    <p>此方法会修改调用对象。</p><br>
    删除单向链表末尾的多个元素。
    @param count 要删除的元素数量。
    @return 删除的元素数量。<br>
    若<code>count</code>&gt;元素数量或<code>count</code>&lt;0或单向链表为空，则返回<code>Integer.MIN_VALUE</code>，此时不删除。
    */
    public int removeBack(int count) {
        if(count>0&&next!=null) {
            int deleteCount=count;
            LinkedListSingly front=this;
            LinkedListSingly back=this;
            for(;count>0;count--,front=front.next) {
                if(front.next==null) {
                    return Integer.MIN_VALUE;
                }
            }
            for(;front.next!=null;front=front.next,back=back.next);
            back.next=null;
            return deleteCount;
        } else {
            return next!=null?0:Integer.MIN_VALUE;
        }
    }
    /**
    <p>批量头删</p><br>
    <p>此方法会修改调用对象。</p><br>
    删除单向链表开头的多个元素。
    @param count 要删除的元素数量。
    @return 删除的元素数量。<br>
    若<code>count</code>&gt;元素数量或<code>count</code>&lt;0或单向链表为空，则返回<code>Integer.MIN_VALUE</code>，此时不删除。
    */
    public int removeFront(int count) {
        if(count>0&&next!=null) {
            int deleteCount=count;
            LinkedListSingly pin=this;
            for(;count>0;count--,pin=pin.next) {
                if(pin.next==null) {
                    return Integer.MIN_VALUE;
                }
            }
            next=pin.next;
            return deleteCount;
        } else {
            return next!=null?0:Integer.MIN_VALUE;
        }
    }
    /**
    <p>指定位置元素删除/p><br>
    <p>此方法会修改调用对象。</p><br>
    删除单向链表中指定索引位置的元素。
    @param index 要删除的元素的索引。
    @return 删除的元素。<br>
    若索引无效或单向链表为空，则返回<code>Integer.MIN_VALUE</code>，此时不删除。
    */
    public int removeIndex(int index) {
        if(index>=0&&next!=null) {
            LinkedListSingly pin=this;
            for(;index>0;index--,pin=pin.next) {
                if(pin.next==null) {
                    return Integer.MIN_VALUE;
                }
            }
            int deleteElement=pin.next.element;
            pin.next=pin.next.next;
            return deleteElement;
        } else {
            return Integer.MIN_VALUE;
        }
    }
    /**
    <p>单个元素删除</p><br>
    <p>此方法会修改调用对象。</p><br>
    删除单向链表中所有的指定元素。
    @param element 要删除的元素。
    @return 删除的元素数量。
    */
    public int removeElement(int element) {
        int count=0;
        for(LinkedListSingly pin=this;pin.next!=null;) {
            if(pin.next.element==element) {
                LinkedListSingly start=pin;
                LinkedListSingly end=pin.next.next;
                for(count++;end!=null&&end.element==element;end=end.next,count++);
                start.next=end;
            } else {
                pin=pin.next;
            }
        }
        return count;
    }
    /**
    <p>区间元素删除</p><br>
    <p>此方法会修改调用对象。</p><br>
    删除单向链表中所有在[<code>minElement</code>,<code>maxElement</code>]区间内的元素。
    @param minElement 区间下限（包含）。
    @param maxElement 区间上限（包含）。
    @return 删除的元素数量。
    */
    public int removeElement(int minElement,int maxElement) {
        int count=0;
        for(LinkedListSingly pin=this;pin.next!=null;) {
            if(pin.next.element>=minElement&&pin.next.element<=maxElement) {
                LinkedListSingly start=pin;
                LinkedListSingly end=pin.next.next;
                for(count++;end!=null&&end.element>=minElement&&end.element<=maxElement;end=end.next,count++);
                start.next=end;
            } else {
                pin=pin.next;
            }
        }
        return count;
    }
    /**
    <p>升序排序</p><br>
    <p>此方法会修改调用对象。</p><br>
    对单向链表进行升序排序。
    @return 排序后的第一个元素。<br>
    如果单向链表为空，则返回<code>Integer.MIN_VALUE</code>。
    */
    public int sortAscend() {
        int count=0;
        for(LinkedListSingly pin=next;pin!=null;pin=pin.next,count++);
        if(count>=2) {
            LinkedListSingly lists[]=new LinkedListSingly[count];
            lists[0]=this;
            int pin=1;
            for(LinkedListSingly list=next.next;list!=null;) {
                LinkedListSingly head=new LinkedListSingly(1,list);
                list=list.next;
                lists[pin++]=head;
                head.next.next=null;
            }
            next.next=null;
            int size=lists.length;
            while(size>1) {
                for(int leftIndex=0,rightIndex=(size+1)>>1;rightIndex<size;rightIndex++,leftIndex++) {
                    LinkedListSingly left=lists[leftIndex];
                    LinkedListSingly right=lists[rightIndex];
                    LinkedListSingly start=right;
                    while(left.next!=null&&right.next!=null) {
                        boolean insert=false;
                        while(right.next!=null&&left.next!=null&&right.next.element<=left.next.element) {
                            right=right.next;
                            insert=true;
                        }
                        if(insert) {
                            if(left.next!=null) {
                                LinkedListSingly temp=left.next;
                                left.next=start.next;
                                start.next=right.next;
                                right.next=temp;
                                left=temp;
                                right=start;
                            } else {
                                break;
                            }
                        } else {
                            if(left.next.next!=null) {
                                left=left.next;
                            } else {
                                left.next.next=start.next;
                                right.next=null;
                                break;
                            }
                        }
                    }
                    if(right.next!=null) {
                        left.next=start.next;
                        right.next=null;
                    }
                }
                size=(size+1)>>1;
            }
            return lists[0].next.element;
        } else if(count==1) {
            return next.element;
        } else {
            return Integer.MIN_VALUE;
        }
    }
    /**
    <p>降序排序</p><br>
    <p>此方法会修改调用对象。</p><br>
    对单向链表进行降序排序。
    @return 排序后的第一个元素。<br>
    如果单向链表为空，则返回<code>Integer.MIN_VALUE</code>。
    */
    public int sortDescend() {
        int count=0;
        for(LinkedListSingly pin=next;pin!=null;pin=pin.next,count++);
        if(count>=2) {
            LinkedListSingly lists[]=new LinkedListSingly[count];
            lists[0]=this;
            int pin=1;
            for(LinkedListSingly list=next.next;list!=null;) {
                LinkedListSingly head=new LinkedListSingly(1,list);
                list=list.next;
                lists[pin++]=head;
                head.next.next=null;
            }
            next.next=null;
            int size=lists.length;
            while(size>1) {
                for(int leftIndex=0,rightIndex=(size+1)>>1;rightIndex<size;rightIndex++,leftIndex++) {
                    LinkedListSingly left=lists[leftIndex];
                    LinkedListSingly right=lists[rightIndex];
                    LinkedListSingly start=right;
                    while(left.next!=null&&right.next!=null) {
                        boolean insert=false;
                        while(right.next!=null&&left.next!=null&&right.next.element>=left.next.element) {
                            right=right.next;
                            insert=true;
                        }
                        if(insert) {
                            if(left.next!=null) {
                                LinkedListSingly temp=left.next;
                                left.next=start.next;
                                start.next=right.next;
                                right.next=temp;
                                left=temp;
                                right=start;
                            } else {
                                break;
                            }
                        } else {
                            if(left.next.next!=null) {
                                left=left.next;
                            } else {
                                left.next.next=start.next;
                                right.next=null;
                                break;
                            }
                        }
                    }
                    if(right.next!=null) {
                        left.next=start.next;
                        right.next=null;
                    }
                }
                size=(size+1)>>1;
            }
            return lists[0].next.element;
        } else if(count==1) {
            return next.element;
        } else {
            return Integer.MIN_VALUE;
        }
    }
    /**
    <p>字符串表示</p><br>
    @return 单向链表的字符串表示。
    */
    public String toString() {
        StringBuilder result=new StringBuilder("[");
        LinkedListSingly pin=this;
        while(pin.next!=null) {
            pin=pin.next;
            result.append(pin.element);
            if(pin.next!=null) {
                result.append("->");
            }
        }
        result.append("]");
        return result.toString();
    }
}