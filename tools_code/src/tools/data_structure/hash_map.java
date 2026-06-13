package tools.data_structure;
/**
<p>哈希表类/散列表类。</p><br>
哈希表是一种数据结构，用于存储键值对。<br>
每个键都映射到一个唯一的位置，称为哈希值。<br>
通过哈希值可以快速访问对应的值。<br><br>
本哈希表默认容量为257。<br>
负载因子为0.6666666666666667，即2/3。<br>
使用随机化哈希函数避免被先验攻击。
*/
public class hash_map
{
    /**
    <p>哈希表节点类。</p><br>
    用于存储哈希表中的键值对。<br>
    每个节点包含键、值和后继节点指针。<br>
    后继节点指针指向链表中的下一个节点，用于处理哈希冲突。
    */
    public static class hash_map_list_node
    {
        public int key;
        public int value;
        public hash_map_list_node next;
        public hash_map_list_node(int key,int value)
        {
            this.key=key;
            this.value=value;
            next=null;
        }
    }
    /**
    <p>共24个元素。</p><br>
    哈希表候选容量表。<br>
    */
    public static final int prime[]={257,521,1049,2099,4201,8419,16843,33703,67409,134837,269683,539389,1078787,2157587,4315183,8630387,17260781,34521589,69043189,138086407,276172823,552345671,1104691373,2147483647};
    public final int hashing_factor1;
    public final int hashing_factor2;
    public hash_map_list_node elements[];
    public int size;
    public int capacity;
    public int capacity_pin;
    {
        int random_number=(int)(Math.random()*2147483647)+1;
        hashing_factor1=random_number%2==0?random_number+1:random_number;
        random_number=(int)(Math.random()*2147483647)+1;
        hashing_factor2=random_number%2==0?random_number+1:random_number;
    }
    /**
    构造一个大于等于参考容量的最小质数容量的空哈希表。
    @param capacity 哈希表的参考容量。
    */
    public hash_map(int capacity)
    {
        int left=0,right=prime.length-1;
        capacity_pin=prime.length-1;
        while(left<=right)
        {
            int middle=(left+right)/2;
            if(prime[middle]>=capacity)
            {
                capacity_pin=middle;
                right=middle-1;
            }
            else
            {
                left=middle+1;
            }
        }
        capacity=prime[capacity_pin];
        this.capacity=capacity;
        elements=new hash_map_list_node[capacity];
        size=0;
    }
    /**
    构造一个默认容量为257的空哈希表。
    */
    public hash_map()
    {
        capacity=257;
        capacity_pin=0;
        elements=new hash_map_list_node[capacity];
        size=0;
    }
    public int random_hash(int number)
    {
        number^=number>>>16;
        number*=hashing_factor1;
        number^=number>>>13;
        number*=hashing_factor2;
        number^=number>>>16;
        return number;
    }
    /**
    对1个整数进行哈希处理，返回该整数的默认哈希值。<br>
    @param number 要哈希的整数。
    @return 该整数的默认哈希值。
    */
    public static int hash(int number)
    {
        number^=number>>>16;
        number*=0x85ebca6b;
        number^=number>>>13;
        number*=0xc2b2ae35;
        number^=number>>>16;
        return number;
    }
    /**
	<p>此方法会修改调用对象。</p><br>
    对哈希表进行扩容。<br>
    新的哈希表容量=大于当前容量2倍的最小质数。
    @return 新的哈希表容量=大于当前容量2倍的最小质数。
    */
    public int dilate()
    {
        int new_capacity=prime[++capacity_pin];
        hash_map_list_node new_elements[]=new hash_map_list_node[new_capacity];
        for(int i=0;i<capacity;i++)
        {
            if(elements[i]!=null)
            {
                hash_map_list_node new_now=elements[i];
                while(new_now!=null)
                {
                    int new_pin=(random_hash(new_now.key)%new_capacity+new_capacity)%new_capacity;
                    if(new_elements[new_pin]==null)
                    {
                        new_elements[new_pin]=new_now;
                        hash_map_list_node temp=new_now;
                        new_now=new_now.next;
                        temp.next=null;
                    }
                    else
                    {
                        hash_map_list_node now=new_elements[new_pin];
                        while(now.next!=null)
                        {
                            now=now.next;
                        }
                        now.next=new_now;
                        hash_map_list_node temp=new_now;
                        new_now=new_now.next;
                        temp.next=null;
                    }
                }
            }
        }
        elements=new_elements;
        capacity=new_capacity;
        return capacity;
    }
    /**
	<p>此方法会修改调用对象。</p><br>
    向哈希表中插入一个键值对。<br>
    如果哈希表中已存在相同键，则更新对应的值。<br>
    如果哈希表中元素数量超过容量的2/3，则先扩展容量。
    @param key 键。
    @param value 值。
    @return 插入或更新后的索引。
    */
    public int input(int key,int value)
    {
        if(size>=capacity*2/3)
        {
            dilate();
        }
        int pin=(random_hash(key)%capacity+capacity)%capacity;
        if(elements[pin]==null)
        {
            elements[pin]=new hash_map_list_node(key,value);
        }
        else
        {
            hash_map_list_node now=elements[pin];
            while(now.next!=null)
            {
                if(now.key==key)
                {
                    now.value=value;
                    return pin;
                }
                now=now.next;
            }
            if(now.key==key)
            {
                now.value=value;
                return pin;
            }
            now.next=new hash_map_list_node(key,value);
        }
        size++;
        return pin;
    }
    /**
    获取哈希表中指定键对应的值。
    @param key 键。
    @return 键对应的值。<br>
    若键不存在，则返回<code>Integer.MIN_VALUE</code>。
    */
    public int get(int key)
    {
        int pin=(random_hash(key)%capacity+capacity)%capacity;
        if(elements[pin]==null)
        {
            return Integer.MIN_VALUE;
        }
        else
        {
            hash_map_list_node now=elements[pin];
            while(now!=null)
            {
                if(now.key==key)
                {
                    return now.value;
                }
                now=now.next;
            }
            return Integer.MIN_VALUE;
        }
    }
    /**
    <p>此方法会修改调用对象。</p><br>
    删除哈希表中指定的键值对。
    @param key 键。
    @return 删除的键对应的值。<br>
    若键不存在，则返回<code>Integer.MIN_VALUE</code>。
    */
    public int remove(int key)
    {
        int pin=(random_hash(key)%capacity+capacity)%capacity;
        if(elements[pin]==null)
        {
            return Integer.MIN_VALUE;
        }
        else
        {
            hash_map_list_node now=elements[pin];
            if(now.key==key)
            {
                elements[pin]=now.next;
                size--;
                return now.value;
            }
            else
            {
                for(;now.next!=null;now=now.next)
                {
                    if(now.next.key==key)
                    {
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
    public String toString()
    {
        if(size==0)
        {
            return "[]";
        }
        StringBuilder result=new StringBuilder("[");
        for(int i=0;i<capacity;i++)
        {
            if(elements[i]!=null)
            {
                result.append("[");
                for(hash_map_list_node now=elements[i];now!=null;now=now.next)
                {
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