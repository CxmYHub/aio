package aio.datastructure;
/**
<p>双向链表类</p><br>
双向链表属于链表的一种，是一种线性数据结构。<br>
对比数组，链表具有动态大小的优势，即可以在运行时根据需要动态添加或删除元素。<br>
同时，链表也可以在任意位置进行插入和删除操作。<br>
但链表的访问时间是线性的，即需要遍历链表才能访问到目标元素。<br><br>
本双向链表以管理类+内部结点类形式实现，管理器存储链表的元素数量和首尾指针，内部结点存储链表的元素。<br>
当索引大于等于0时，从首结点开始向后计数，称为正向索引。<br>
当索引小于0时，从尾结点开始向前计数，即当n&lt;0时，第n个结点表示第<code>count+n+1</code>个结点，或倒数第-n个结点，称为反向索引。
*/
public class LinkedListDoubly {
    /**
    <p>双向链表结点类</p><br>
    用于存储双向链表中的元素。<br>
    每个结点包含元素、前驱结点指针和后继结点指针。<br>
    前驱结点指针指向当前结点的前一个结点，后继结点指针指向当前结点的后一个结点。
    */
    public static class LinkedListDoublyNode {
        /**
        <p>元素</p><br>
        */
        public int element;
        /**
        <p>前驱结点指针</p><br>
        */
        public LinkedListDoublyNode previous;
        /**
        <p>后继结点指针</p><br>
        */
        public LinkedListDoublyNode next;
        /**
        <p>全参构造方法</p><br>
        通过元素、前驱和后继结点指针构造一个双向链表结点。
        @param element 元素。
        @param previous 前驱结点指针。
        @param next 后继结点指针。
        */
        public LinkedListDoublyNode(int element,LinkedListDoublyNode previous,LinkedListDoublyNode next) {
            this.element=element;
            this.previous=previous;
            this.next=next;
        }
        /**
        <p>构造方法</p><br>
        构造一个包含指定元素的双向链表结点。
        @param element 元素。
        */
        public LinkedListDoublyNode(int element) {
            this.element=element;
            previous=null;
            next=null;
        }
        /**
        <p>无参构造方法</p><br>
        构造一个元素为0的双向链表结点。
        */
        public LinkedListDoublyNode() {
            element=0;
            previous=null;
            next=null;
        }
    }
    /**
    <p>元素数量</p>
    */
    public int size=0;
    /**
    <p>首结点指针</p>
    */
    public LinkedListDoublyNode head=null;
    /**
    <p>尾结点指针</p>
    */
    public LinkedListDoublyNode tail=null;
    /**
    <p>构造方法</p><br>
    构造一个包含多个元素的双向链表。
    @param numbers 多个元素。
    */
    public LinkedListDoubly(int... numbers) {
        size=numbers.length;
        head=tail=new LinkedListDoublyNode(numbers[0]);
        for(int i=1;i<size;i++,tail=tail.next) {
            tail.next=new LinkedListDoublyNode(numbers[i],tail,null);
        }
    }
    /**
    <p>无参构造方法</p><br>
    构造一个空双向链表。
    */
    public LinkedListDoubly() {
        head=null;
        tail=null;
    }
    /**
    <p>空判断</p><br>
    判断双向链表是否为空。
    @return 是否为空。
    */
    public boolean isEmpty() {
        return size==0;
    }
    /**
    <p>元素计数</p><br>
    获取双向链表的元素数量。
    @return 双向链表的元素数量。
    */
    public int elementCount() {
        return size;
    }
    /**
    <p>元素获取</p><br>
    获取双向链表中指定索引位置的元素。
    @param index 索引。<br>
    <ul>
        <li><code>index</code>≥0表示从首结点开始向后计数，表示第<code>index+1</code>个元素。</li>
        <li><code>index</code>&lt;0表示从尾结点开始向前计数，表示第<code>count+index+1</code>个元素。</li>
    </ul>
    @return 索引位置的元素。<br>
    若索引无效，则返回<code>Integer.MAX_VALUE</code>。
    */
    public int elementAt(int index) {
        if(index>=0&&index<size) {
            LinkedListDoublyNode pin=head;
            for(;index>0;index--,pin=pin.next);
            return pin.element;
        } else if(index<0&&index>=-size) {
            LinkedListDoublyNode pin=tail;
            for(;index<-1;index++,pin=pin.previous);
            return pin.element;
        } else {
            return Integer.MAX_VALUE;
        }
    }
    /**
    <p>索引查询</p><br>
    获取双向链表中从前向后第一个出现指定元素的正向索引。
    @param element 目标元素。
    @return 目标元素的首个正向索引。<br>
    若双向链表中不存在目标元素，则返回<code>Integer.MIN_VALUE</code>。
    */
    public int indexForward(int element) {
        int result=0;
        for(LinkedListDoublyNode pin=head;pin!=null;pin=pin.next,result++) {
            if(pin.element==element) {
                return result;
            }
        }
        return Integer.MIN_VALUE;
    }
    /**
    <p>索引查询</p><br>
    获取双向链表中从后向前第一个出现指定元素的反向索引。
    @param element 目标元素。
    @return 目标元素的首个反向索引。<br>
    若双向链表中不存在目标元素，则返回<code>Integer.MIN_VALUE</code>。
    */
    public int indexBackward(int element) {
        int result=-1;
        for(LinkedListDoublyNode pin=tail;pin!=null;pin=pin.previous,result--) {
            if(pin.element==element) {
                return result;
            }
        }
        return Integer.MIN_VALUE;
    }
    /**
    <p>索引反相</p><br>
    计算双向链表中指定索引的相反索引。
    @param index 要转换的索引。
    @return 相反索引。<br>
    若输入的是正向索引，则返回对应位置的反向索引。<br>
    若输入的是反向索引，则返回对应位置的正向索引。
    */
    public int reverseIndex(int index) {
        return index>=0?index-size:index+size;
    }
    /**
    <p>最小索引转换</p><br>
    计算双向链表中指定索引的最小索引。
    @param index 索引。
    @return 最小索引。<br>
    即指定索引位置的正向索引和反向索引中绝对值较小的一个。
    */
    public int minIndex(int index) {
        int reversedIndex=index>=0?index-size:index+size;
        return (reversedIndex>=0?reversedIndex:-reversedIndex)>=(index>=0?index:-index)?index:reversedIndex;
    }
    /**
    <p>正向遍历</p><br>
    正向遍历双向链表。
    @return 正向遍历结果。
    */
    public int[] traversalForward() {
        int result[]=new int[size];
        LinkedListDoublyNode pin=head;
        for(int i=0;i<size;i++,pin=pin.next) {
            result[i]=pin.element;
        }
        return result;
    }
    /**
    <p>反向遍历</p><br>
    反向遍历双向链表。
    @return 反向遍历结果。
    */
    public int[] traversalBackward() {
        int result[]=new int[size];
        LinkedListDoublyNode pin=tail;
        for(int i=0;i<size;i++,pin=pin.previous) {
            result[i]=pin.element;
        }
        return result;
    }
    /**
    <p>元素尾插</p><br>
    <p>此方法会修改调用对象。</p><br>
    向双向链表的末尾插入一个元素。
    @param number 要插入的元素。
    @return 插入的位置的反向索引。
    */
    public int inputBack(int number) {
        if(size==0) {
            head=tail=new LinkedListDoublyNode(number);
        } else {
            tail.next=new LinkedListDoublyNode(number,tail,null);
            tail=tail.next;
        }
        size++;
        return -1;
    }
    /**
    <p>元素批量尾插</p><br>
    <p>此方法会修改调用对象。</p><br>
    向双向链表的末尾插入多个元素。
    @param numbers 要插入的多个元素。
    @return 插入的位置的反向索引。<br>
    若元素数组为空，则返回<code>Integer.MIN_VALUE</code>。
    */
    public int inputMoreBack(int... numbers) {
        if(numbers!=null&&numbers.length>0) {
            if(size==0) {
                head=tail=new LinkedListDoublyNode(numbers[0]);
            } else {
                tail.next=new LinkedListDoublyNode(numbers[0],tail,null);
                tail=tail.next;
            }
            for(int i=1;i<numbers.length;i++,tail=tail.next) {
                tail.next=new LinkedListDoublyNode(numbers[i],tail,null);
            }
            size+=numbers.length;
            return -numbers.length;
        } else {
            return Integer.MIN_VALUE;
        }
    }
    /**
    <p>列表尾插</p><br>
    <p>此方法会修改调用对象。</p><br>
    向双向链表的末尾插入另一个双向链表。
    @param list 要插入的双向链表。
    @return 插入的位置的反向索引。<br>
    若要插入的双向链表为空，则返回<code>Integer.MIN_VALUE</code>。
    */
    public int inputListBack(LinkedListDoubly list) {
        if(list!=null&&list.size>0) {
            if(size==0) {
                head=list.head;
            } else {
                list.head.previous=tail;
                tail.next=list.head;
            }
            tail=list.tail;
            size+=list.size;
            return -list.size;
        } else {
            return Integer.MIN_VALUE;
        }
    }
    /**
    <p>元素头插</p><br>
    <p>此方法会修改调用对象。</p><br>
    向双向链表的开头插入一个元素。
    @param number 要插入的元素。
    @return 插入的位置的正向索引。
    */
    public int inputFront(int number) {
        if(size==0) {
            head=tail=new LinkedListDoublyNode(number);
        } else {
            head.previous=new LinkedListDoublyNode(number,null,head);
            head=head.previous;
        }
        size++;
        return 0;
    }
    /**
    <p>元素批量头插</p><br>
    <p>此方法会修改调用对象。</p><br>
    向双向链表的开头插入多个元素。
    @param numbers 要插入的多个元素。
    @return 插入的位置的正向索引。<br>
    若元素数组为空，则返回<code>Integer.MIN_VALUE</code>。
    */
    public int inputMoreFront(int... numbers) {
        if(numbers!=null&&numbers.length>0) {
            if(size==0) {
                head=tail=new LinkedListDoublyNode(numbers[numbers.length-1]);
            } else {
                head.previous=new LinkedListDoublyNode(numbers[numbers.length-1],null,head);
                head=head.previous;
            }
            for(int i=numbers.length-2;i>=0;i--,head=head.previous) {
                head.previous=new LinkedListDoublyNode(numbers[i],null,head);
            }
            size+=numbers.length;
            return 0;
        } else {
            return Integer.MIN_VALUE;
        }
    }
    /**
    <p>列表头插</p><br>
    <p>此方法会修改调用对象。</p><br>
    向双向链表的开头插入另一个双向链表。
    @param list 要插入的双向链表。
    @return 插入的位置的正向索引。<br>
    若要插入的双向链表为空，则返回<code>Integer.MIN_VALUE</code>。
    */
    public int inputListFront(LinkedListDoubly list) {
        if(list!=null&&list.size>0) {
            if(size==0) {
                tail=list.tail;
            } else {
                list.tail.next=head;
                head.previous=list.tail;
            }
            head=list.head;
            size+=list.size;
            return 0;
        } else {
            return Integer.MIN_VALUE;
        }
    }
    /**
    <p>元素输入</p><br>
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
    public int insert(int index,int number) {
        if(size==0) {
            head=tail=new LinkedListDoublyNode(number);
            size=1;
            return 0;
        } else {
            if(index==0||index<-size) {
                head.previous=new LinkedListDoublyNode(number,null,head);
                head=head.previous;
                size++;
                return 0;
            } else if(index==-1||index>=size) {
                tail.next=new LinkedListDoublyNode(number,tail,null);
                tail=tail.next;
                size++;
                return -1;
            }
            int reversedIndex=index>=0?index-size-1:index+size+1;
            index=(reversedIndex>=0?reversedIndex:-reversedIndex)>=(index>=0?index:-index)?index:reversedIndex;
            int position=1;
            if(index>0) {
                LinkedListDoublyNode pin=head;
                for(;index>1;index--,position++,pin=pin.next);
                pin.next=new LinkedListDoublyNode(number,pin,pin.next);
                pin.next.next.previous=pin.next;
                size++;
                return position;
            } else {
                LinkedListDoublyNode pin=tail;
                for(position=-2;index<-2;index++,position--,pin=pin.previous);
                pin.previous=new LinkedListDoublyNode(number,pin.previous,pin);
                pin.previous.previous.next=pin.previous;
                size++;
                int reversedPosition=position>=0?position-size:position+size;
                position=(reversedPosition>=0?reversedPosition:-reversedPosition)>=(position>=0?position:-position)?position:reversedPosition;
                return position;
            }
        }
    }
    /**
    <p>元素批量输入</p><br>
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
    public int insertMore(int index,int... numbers) {
        if(size==0) {
            size=numbers.length;
            head=tail=new LinkedListDoublyNode(numbers[0]);
            for(int i=1;i<size;i++,tail=tail.next) {
                tail.next=new LinkedListDoublyNode(numbers[i],tail,null);
            }
            return 0;
        } else {
            if(index==0||index<-size) {
                for(int i=numbers.length-1;i>=0;i--,head=head.previous) {
                    head.previous=new LinkedListDoublyNode(numbers[i],null,head);
                }
                size+=numbers.length;
                return 0;
            } else if(index==-1||index>=size) {
                for(int i=0;i<numbers.length;i++,tail=tail.next) {
                    tail.next=new LinkedListDoublyNode(numbers[i],tail,null);
                }
                size+=numbers.length;
                return numbers.length<size>>1?-numbers.length:size-numbers.length;
            }
            int reversedIndex=index>=0?index-size-1:index+size+1;
            index=(reversedIndex>=0?reversedIndex:-reversedIndex)>=(index>=0?index:-index)?index:reversedIndex;
            int position=1;
            if(index>0) {
                LinkedListDoublyNode pin=head;
                for(;index>1;index--,position++,pin=pin.next);
                for(int i=0;i<numbers.length;i++,pin=pin.next) {
                    pin.next=new LinkedListDoublyNode(numbers[i],pin,pin.next);
                }
                pin.next.previous=pin;
                size+=numbers.length;
                return position;
            } else {
                LinkedListDoublyNode pin=tail;
                for(position=-numbers.length-1;index<-2;index++,position--,pin=pin.previous);
                for(int i=numbers.length-1;i>=0;i--,pin=pin.previous) {
                    pin.previous=new LinkedListDoublyNode(numbers[i],pin.previous,pin);
                }
                pin.previous.next=pin;
                size+=numbers.length;
                int reversedPosition=position>=0?position-size:position+size;
                position=(reversedPosition>=0?reversedPosition:-reversedPosition)>=(position>=0?position:-position)?position:reversedPosition;
                return position;
            }
        }
    }
    /**
    <p>列表输入</p><br>
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
    public int insertList(int index,LinkedListDoubly list) {
        if(size==0) {
            head=list.head;
            tail=list.tail;
            size=list.size;
            return 0;
        } else {
            if(index==0||index<-size) {
                head.previous=list.tail;
                list.tail.next=head;
                head=list.head;
                size+=list.size;
                return 0;
            } else if(index==-1||index>=size) {
                tail.next=list.head;
                list.head.previous=tail;
                tail=list.tail;
                size+=list.size;
                return list.size<size>>1?-list.size:size-list.size;
            }
            int reversedIndex=index>=0?index-size-1:index+size+1;
            index=(reversedIndex>=0?reversedIndex:-reversedIndex)>=(index>=0?index:-index)?index:reversedIndex;
            int position=1;
            if(index>0) {
                LinkedListDoublyNode pin=head;
                for(;index>1;index--,position++,pin=pin.next);
                pin.next.previous=list.tail;
                list.tail.next=pin.next;
                pin.next=list.head;
                list.head.previous=pin;
                size+=list.size;
                return position;
            } else {
                LinkedListDoublyNode pin=tail;
                for(position=-list.size-1;index<-2;index++,position--,pin=pin.previous);
                pin.previous.next=list.head;
                list.head.previous=pin.previous;
                pin.previous=list.tail;
                list.tail.next=pin;
                size+=list.size;
                int reversedPosition=position>=0?position-size:position+size;
                position=(reversedPosition>=0?reversedPosition:-reversedPosition)>=(position>=0?position:-position)?position:reversedPosition;
                return position;
            }
        }
    }
    /**
    <p>批量尾删</p><br>
    <p>此方法会修改调用对象。</p><br>
    删除双向链表末尾的多个元素。
    @param count 要删除的元素数量。
    @return 删除的元素数量。<br>
    若<code>count</code>&gt;元素数量或<code>count</code>&lt;0或双向链表为空，则返回<code>Integer.MIN_VALUE</code>，此时不删除。
    */
    public int removeBack(int count) {
        if(count==size) {
            head=null;
            tail=null;
            size=0;
            return count;
        } else if(count>0&&count<size) {
            int deleteCount=count;
            for(;count>0;count--,tail=tail.previous);
            tail.next=null;
            size-=deleteCount;
            return deleteCount;
        } else {
            return (count==0&&size!=0)?0:Integer.MIN_VALUE;
        }
    }
    /**
    <p>批量头删</p><br>
    <p>此方法会修改调用对象。</p><br>
    删除双向链表开头的多个元素。
    @param count 要删除的元素数量。
    @return 删除的元素数量。<br>
    若<code>count</code>&gt;元素数量或<code>count</code>&lt;0或双向链表为空，则返回<code>Integer.MIN_VALUE</code>，此时不删除。
    */
    public int removeFront(int count) {
        if(count==size) {
            head=null;
            tail=null;
            size=0;
            return count;
        } else if(count>0&&count<size) {
            int deleteCount=count;
            for(;count>0;count--,head=head.next);
            head.previous=null;
            size-=deleteCount;
            return deleteCount;
        } else {
            return (count==0&&size!=0)?0:Integer.MIN_VALUE;
        }
    }
    /**
    <p>指定位置元素删除</p><br>
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
    public int removeIndex(int index) {
        if(index>=-size&&index<size) {
            int reversedIndex=index>=0?index-size:index+size;
            index=(reversedIndex>=0?reversedIndex:-reversedIndex)>=(index>=0?index:-index)?index:reversedIndex;
            if(index==0) {
                int deletedElement=head.element;
                head=head.next;
                if(size>1) {
                    head.previous=null;
                } else {
                    tail=null;
                }
                size--;
                return deletedElement;
            } else if(index==-1) {
                int deletedElement=tail.element;
                tail=tail.previous;
                if(size>1) {
                    tail.next=null;
                } else {
                    head=null;
                }
                size--;
                return deletedElement;
            } else if(index>0) {
                LinkedListDoublyNode pin=head;
                for(;index>0;index--,pin=pin.next);
                int deletedElement=pin.element;
                pin.previous.next=pin.next;
                pin.next.previous=pin.previous;
                size--;
                return deletedElement;
            } else {
                LinkedListDoublyNode pin=tail;
                for(;index<-1;index++,pin=pin.previous);
                int deletedElement=pin.element;
                pin.previous.next=pin.next;
                pin.next.previous=pin.previous;
                size--;
                return deletedElement;
            }
        } else {
            return Integer.MIN_VALUE;
        }
    }
    /**
    <p>单个元素删除</p><br>
    <p>此方法会修改调用对象。</p><br>
    删除双向链表中所有的指定元素。
    @param element 要删除的元素。
    @return 删除的元素数量。
    */
    public int removeElement(int element) {
        int afterCount=size;
        int deleteCount=0;
        LinkedListDoublyNode pin=head;
        for(;pin!=null&&pin.element==element;pin=pin.next,afterCount--);
        if(afterCount!=size) {
            if(pin==null) {
                head=null;
                tail=null;
                deleteCount+=size-afterCount;
                size=0;
                return deleteCount;
            } else {
                pin.previous=null;
                head=pin;
                deleteCount+=size-afterCount;
                size=afterCount;
            }
        }
        for(pin=tail;pin!=null&&pin.element==element;pin=pin.previous,afterCount--);
        if(afterCount!=size) {
            if(pin==null) {
                head=null;
                tail=null;
                deleteCount+=size-afterCount;
                size=0;
                return deleteCount;
            } else {
                pin.next=null;
                tail=pin;
                deleteCount+=size-afterCount;
                size=afterCount;
            }
        }
        for(;pin!=null;pin=pin.previous) {
            if(pin.element==element) {
                pin.previous.next=pin.next;
                pin.next.previous=pin.previous;
                deleteCount++;
                size--;
            }
        }
        return deleteCount;
    }
    /**
    <p>区间元素删除</p><br>
    <p>此方法会修改调用对象。</p><br>
    删除双向链表中所有在[<code>minElement</code>,<code>maxElement</code>]区间内的元素。
    @param minElement 区间下限（包含）。
    @param maxElement 区间上限（包含）。
    @return 删除的元素数量。
    */
    public int removeElement(int minElement,int maxElement) {
        int afterCount=size;
        int deleteCount=0;
        LinkedListDoublyNode pin=head;
        for(;pin!=null&&pin.element>=minElement&&pin.element<=maxElement;pin=pin.next,afterCount--);
        if(afterCount!=size) {
            if(pin==null) {
                head=null;
                tail=null;
                deleteCount+=size-afterCount;
                size=0;
                return deleteCount;
            } else {
                pin.previous=null;
                head=pin;
                deleteCount+=size-afterCount;
                size=afterCount;
            }
        }
        for(pin=tail;pin!=null&&pin.element>=minElement&&pin.element<=maxElement;pin=pin.previous,afterCount--);
        if(afterCount!=size) {
            if(pin==null) {
                head=null;
                tail=null;
                deleteCount+=size-afterCount;
                size=0;
                return deleteCount;
            } else {
                pin.next=null;
                tail=pin;
                deleteCount+=size-afterCount;
                size=afterCount;
            }
        }
        for(;pin!=null;pin=pin.previous) {
            if(pin.element>=minElement&&pin.element<=maxElement) {
                pin.previous.next=pin.next;
                pin.next.previous=pin.previous;
                deleteCount++;
                size--;
            }
        }
        return deleteCount;
    }
    /**
    <p>字符串表示</p><br>
    @return 双向链表的字符串表示。
    */
    public String toString() {
        StringBuilder result=new StringBuilder("[");
        LinkedListDoublyNode temp=head;
        while(temp!=null) {
            result.append(temp.element);
            if(temp.next!=null) {
                result.append("<=>");
            }
            temp=temp.next;
        }
        result.append("]");
        return result.toString();
    }
}