package aio.datastructure;
/**
<p>哈希表类/散列表类</p><br>
哈希表是一种数据结构，用于存储键值对。<br>
每个键都映射到一个确定的位置，称为哈希值。<br>
通过哈希值可以快速访问对应的值。<br><br>
本哈希表默认容量为257。<br>
负载因子为0.6666666666666667，即2/3。<br>
使用随机化哈希函数避免被先验攻击。
*/
public class HashMap {
    /**
    <p>哈希表结点类</p><br>
    用于存储哈希表中的键值对。<br>
    每个结点包含键、值和后继结点指针。<br>
    后继结点指针指向链表中的下一个结点，用于处理哈希冲突。
    */
    public static class HashMapListNode {
        /**
        <p>键</p><br>
        */
        public int key;
        /**
        <p>值</p><br>
        */
        public int value;
        /**
        <p>后继结点指针</p><br>
        */
        public HashMapListNode next;
        /**
        <p>构造方法</p><br>
        构造一个包含指定键值对的哈希表结点。
        @param key 键。
        @param value 值。
        */
        public HashMapListNode(int key,int value) {
            this.key=key;
            this.value=value;
            next=null;
        }
    }
    /**
    <p>哈希表候选容量表</p><br>
    <p>共24个元素。</p>
    */
    public static final int PRIME[]={257,521,1049,2099,4201,8419,16843,33703,67409,134837,269683,539389,1078787,2157587,4315183,8630387,17260781,34521589,69043189,138086407,276172823,552345671,1104691373,2147483647};
    /**
    <p>哈希因子1</p>
    */
    public final int hashingFactor1;
    /**
    <p>哈希因子2</p>
    */
    public final int hashingFactor2;
    /**
    <p>哈希表结点数组</p>
    */
    public HashMapListNode elements[];
    /**
    <p>哈希表中键值对的数量</p>
    */
    public int size;
    /**
    <p>哈希表容量</p>
    */
    public int capacity;
    /**
    <p>哈希表容量在候选容量表中的索引</p>
    */
    public int capacityPin; {
        int randomNumber=(int)(Math.random()*2147483647)+1;
        hashingFactor1=randomNumber%2==0?randomNumber+1:randomNumber;
        randomNumber=(int)(Math.random()*2147483647)+1;
        hashingFactor2=randomNumber%2==0?randomNumber+1:randomNumber;
    }
    /**
    <p>构造方法</p><br>
    构造一个大于等于参考容量的最小质数容量的空哈希表。
    @param capacity 哈希表的参考容量。
    */
    public HashMap(int capacity) {
        int left=0,right=PRIME.length-1;
        capacityPin=PRIME.length-1;
        while(left<=right) {
            int middle=(left+right)/2;
            if(PRIME[middle]>=capacity) {
                capacityPin=middle;
                right=middle-1;
            } else {
                left=middle+1;
            }
        }
        capacity=PRIME[capacityPin];
        this.capacity=capacity;
        elements=new HashMapListNode[capacity];
        size=0;
    }
    /**
    <p>无参构造方法</p><br>
    构造一个默认容量为257的空哈希表。
    */
    public HashMap() {
        capacity=257;
        capacityPin=0;
        elements=new HashMapListNode[capacity];
        size=0;
    }
    /**
    <p>动态哈希</p><br>
    对1个整数进行哈希处理，返回该整数在本哈希表中的哈希值。<br>
    @param number 要哈希的整数。
    @return 该整数在本哈希表中的哈希值。
    */
    public int randomHash(int number) {
        number^=number>>>16;
        number*=hashingFactor1;
        number^=number>>>13;
        number*=hashingFactor2;
        number^=number>>>16;
        return number;
    }
    /**
    <p>静态哈希</p><br>
    对1个整数进行哈希处理，返回该整数的默认哈希值。<br>
    @param number 要哈希的整数。
    @return 该整数的默认哈希值。
    */
    public static int hash(int number) {
        number^=number>>>16;
        number*=0x85ebca6b;
        number^=number>>>13;
        number*=0xc2b2ae35;
        number^=number>>>16;
        return number;
    }
    /**
    <p>扩容</p><br>
    <p>此方法会修改调用对象。</p><br>
    对哈希表进行扩容。<br>
    新的哈希表容量=大于当前容量2倍的最小质数。
    @return 新的哈希表容量=大于当前容量2倍的最小质数。<br>
    若当前容量已为最大容量2147483647，则返回<code>Integer.MIN_VALUE</code>。
    */
    public int dilate() {
        if(capacityPin==PRIME.length-1) {
            return Integer.MIN_VALUE;
        }
        int newCapacity=PRIME[++capacityPin];
        HashMapListNode newElements[]=new HashMapListNode[newCapacity];
        for(int i=0;i<capacity;i++) {
            if(elements[i]!=null) {
                HashMapListNode newNow=elements[i];
                while(newNow!=null) {
                    int newPin=(randomHash(newNow.key)%newCapacity+newCapacity)%newCapacity;
                    if(newElements[newPin]==null) {
                        newElements[newPin]=newNow;
                        HashMapListNode temp=newNow;
                        newNow=newNow.next;
                        temp.next=null;
                    } else {
                        HashMapListNode now=newElements[newPin];
                        while(now.next!=null) {
                            now=now.next;
                        }
                        now.next=newNow;
                        HashMapListNode temp=newNow;
                        newNow=newNow.next;
                        temp.next=null;
                    }
                }
            }
        }
        elements=newElements;
        capacity=newCapacity;
        return capacity;
    }
    /**
    <p>元素输入</p><br>
    <p>此方法会修改调用对象。</p><br>
    向哈希表中插入一个键值对。<br>
    如果哈希表中已存在相同键，则更新对应的值。<br>
    如果哈希表中元素数量超过容量的2/3，则先扩展容量。
    @param key 键。
    @param value 值。
    @return 插入或更新后的索引。
    */
    public int input(int key,int value) {
        if(size>=capacity*2/3) {
            dilate();
        }
        int pin=(randomHash(key)%capacity+capacity)%capacity;
        if(elements[pin]==null) {
            elements[pin]=new HashMapListNode(key,value);
        } else {
            HashMapListNode now=elements[pin];
            while(now.next!=null) {
                if(now.key==key) {
                    now.value=value;
                    return pin;
                }
                now=now.next;
            }
            if(now.key==key) {
                now.value=value;
                return pin;
            }
            now.next=new HashMapListNode(key,value);
        }
        size++;
        return pin;
    }
    /**
    <p>元素获取</p><br>
    获取哈希表中指定键对应的值。
    @param key 键。
    @return 键对应的值。<br>
    若键不存在，则返回<code>Integer.MIN_VALUE</code>。
    */
    public int get(int key) {
        int pin=(randomHash(key)%capacity+capacity)%capacity;
        if(elements[pin]==null) {
            return Integer.MIN_VALUE;
        } else {
            HashMapListNode now=elements[pin];
            while(now!=null) {
                if(now.key==key) {
                    return now.value;
                }
                now=now.next;
            }
            return Integer.MIN_VALUE;
        }
    }
    /**
    <p>元素删除</p><br>
    <p>此方法会修改调用对象。</p><br>
    删除哈希表中指定的键值对。
    @param key 键。
    @return 删除的键对应的值。<br>
    若键不存在，则返回<code>Integer.MIN_VALUE</code>。
    */
    public int remove(int key) {
        int pin=(randomHash(key)%capacity+capacity)%capacity;
        if(elements[pin]==null) {
            return Integer.MIN_VALUE;
        } else {
            HashMapListNode now=elements[pin];
            if(now.key==key) {
                elements[pin]=now.next;
                size--;
                return now.value;
            } else {
                for(;now.next!=null;now=now.next) {
                    if(now.next.key==key) {
                        int result=now.next.value;
                        now.next=now.next.next;
                        size--;
                        return result;
                    }
                }
                return Integer.MIN_VALUE;
            }
        }
    }
    /**
    <p>字符串表示</p><br>
    @return 哈希表的字符串表示。
    */
    public String toString() {
        if(size==0) {
            return "[]";
        }
        StringBuilder result=new StringBuilder("[");
        for(int i=0;i<capacity;i++) {
            if(elements[i]!=null) {
                result.append("[");
                for(HashMapListNode now=elements[i];now!=null;now=now.next) {
                    result.append(now.key+":"+now.value+",");
                }
                result.deleteCharAt(result.length()-1);
                result.append("],");
            }
        }
        result.deleteCharAt(result.length()-1);
        result.append("]");
        return result.toString();
    }
}