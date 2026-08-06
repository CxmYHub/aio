package aio.data_structure;
/**
<p>B+树类</p><br>
B+树是一种自平衡的树结构，用于存储和检索数据。<br>
其具有两种结点：<br>
<ul>
    <li>
        内部结点。<br>
        包含多个关键字和对应的子结点指针。<br>
        关键字存储数据的界限。<br>
        若数据≥界限，则数据位于界限的右子树。<br>
        若数据≤界限，则数据位于界限的左子树。
    </li>
    <li>
        叶结点。<br>
        包含多个关键字和对应的指针。<br>
        此外，还包含一个指向下一个叶结点的指针。
    </li>
</ul><br>
B+树满足以下四条性质，其中，m为树的阶：<br>
<ol>
    <li>每个结点至多有m个子结点。</li>
    <li>根结点至少有2个子结点，其他结点至少有m/2个子结点。</li>
    <li>有k个子结点的结点包含k-1个关键字。</li>
    <li>所有叶结点都位于同一层。</li>
</ol><br>
本B+树以头结点形式实现，头结点无元素和子结点，其后继结点为B+树的根结点。<br>
默认阶数为256，支持的最小阶数为4。
*/
public class b_plus_tree
{
    /**
    <p>阶</p><br>
    每个结点的子结点数量上限。<br>
    默认为256。
    */
    public final int order;
    /**
    <p>子结点指针数组</p><br>
    指向子结点的指针数组。<br>
    <ul>
        <li>根结点，包含[2,<code>order</code>]个子结点。</li>
        <li>内部结点，包含[<code>order/2</code>,<code>order</code>]个子结点。</li>
        <li>叶结点，没有子结点。</li>
    </ul>
    */
    public b_plus_tree[] children;
    /**
    <p>关键字数组</p><br>
    存储数据界线或数据。<br>
    <ul>
        <li>根结点/内部结点，存储数据的界限。<br>
        若数据≥界限，则数据位于界限的右子树。<br>
        若数据&lt;界限，则数据位于界限的左子树。
        </li>
        <li>叶结点，存储数据。</li>
    </ul>
    */
    public int[] elements;
    /**
    <p>元素数量</p><br>
    结点中存储的元素数量。
    */
    public int count;
    /**
    <p>叶结点后继指针</p><br>
    <ul>
        <li>头结点，指向根结点。</li>
        <li>根结点/内部结点，没有后继。</li>
        <li>叶结点，指向下一个叶结点。</li>
    </ul>
    */
    public b_plus_tree next;
    /**
    <p>结点类型</p><br>
    <ul>
        <li>=-1：头结点。</li>
        <li>=0：根结点。</li>
        <li>=1：内部结点。</li>
        <li>=2：叶结点。</li>
    </ul>
    */
    public int type;
    /**
    <p>结点构造方法</p><br>
    构造一个指定阶数的B+树结点。
    @param order 树的阶。
    @param type 结点类型。<br>
    <ul>
        <li>=-1：头结点。</li>
        <li>=0：根结点。</li>
        <li>=1：内部结点。</li>
        <li>=2：叶结点。</li>
    </ul>
    */
    public b_plus_tree(int order,int type)
    {
        this.order=order;
        this.type=type;
        if(type<=1)
        {
            children=new b_plus_tree[order];
            elements=new int[order-1];
            count=0;
            next=null;
        }
        else
        {
            children=null;
            elements=new int[order];
            count=0;
            next=null;
        }
    }
    /**
    <p>构造方法</p><br>
    构造一个指定阶数的空B+树。
    @param order 树的阶。
    */
    public b_plus_tree(int order)
    {
        this.order=order;
        type=-1;
        children=null;
        elements=null;
        count=0;
    }
    /**
    <p>无参构造方法</p><br>
    构造一个默认阶数为256的空B+树。
    */
    public b_plus_tree()
    {
        order=256;
        type=-1;
        children=null;
        elements=null;
        count=0;
    }
    /**
    <p>单元素计数</p><br>
    获取B+树中指定元素的数量。
    @param element 元素。
    @return 元素的数量。
    */
    public int count(int element)
    {
        if(this.next==null||this.next.count==0)
        {
            return 0;
        }
        b_plus_tree now=this.next;
        while(now.type<2)
        {
            int left=0,right=now.count-2;
            int target=now.count-1;
            while(left<=right)
            {
                int middle=(left+right)/2;
                if(now.elements[middle]>=element)
                {
                    target=middle;
                    right=middle-1;
                }
                else
                {
                    left=middle+1;
                }
            }
            now=now.children[target];
        }
        if(now.elements[now.count-1]<element&&now.next!=null)
        {
            now=now.next;
        }
        int left=0,right=now.count-1;
        int left_index=now.count;
        while(left<=right)
        {
            int middle=(left+right)/2;
            if(now.elements[middle]>=element)
            {
                left_index=middle;
                right=middle-1;
            }
            else
            {
                left=middle+1;
            }
        }
        int total=0;
        for(;now!=null;now=now.next)
        {
            if(now.elements[now.count-1]==element)
            {
                total+=now.count-left_index;
                left_index=0;
            }
            else if(now.elements[left_index]>element)
            {
                return total;
            }
            else
            {
                left=left_index;
                right=now.count-1;
                int right_index=now.count;
                while(left<=right)
                {
                    int middle=(left+right)/2;
                    if(now.elements[middle]>element)
                    {
                        right_index=middle;
                        right=middle-1;
                    }
                    else
                    {
                        left=middle+1;
                    }
                }
                return total+right_index-left_index;
            }
        }
        return total;
    }
    /**
    <p>区间元素计数</p><br>
    获取B+树中[<code>min</code>,<code>max</code>]区间内元素的数量。
    @param min 最小值。
    @param max 最大值。
    @return [<code>min</code>,<code>max</code>]区间内元素的数量。
    */
    public int count(int min,int max)
    {
        if(next==null||next.count==0)
        {
            return 0;
        }
        b_plus_tree now=next;
        while(now.type<2)
        {
            int left=0,right=now.count-2;
            int target=now.count-1;
            while(left<=right)
            {
                int middle=(left+right)/2;
                if(now.elements[middle]>=min)
                {
                    target=middle;
                    right=middle-1;
                }
                else
                {
                    left=middle+1;
                }
            }
            now=now.children[target];
        }
        if(now.elements[now.count-1]<min&&now.next!=null)
        {
            now=now.next;
        }
        int left=0,right=now.count-1;
        int left_index=now.count;
        while(left<=right)
        {
            int middle=(left+right)/2;
            if(now.elements[middle]>=min)
            {
                left_index=middle;
                right=middle-1;
            }
            else
            {
                left=middle+1;
            }
        }
        int total=0;
        for(;now!=null;now=now.next)
        {
            if(now.elements[now.count-1]<=max)
            {
                total+=now.count-left_index;
                left_index=0;
            }
            else if(now.elements[left_index]>max)
            {
                return total;
            }
            else
            {
                left=left_index;
                right=now.count-1;
                int right_index=now.count;
                while(left<=right)
                {
                    int middle=(left+right)/2;
                    if(now.elements[middle]>max)
                    {
                        right_index=middle;
                        right=middle-1;
                    }
                    else
                    {
                        left=middle+1;
                    }
                }
                return total+right_index-left_index;
            }
        }
        return total;
    }
    /**
    <p>所有元素计数</p><br>
    计算B+树中元素的数量。
    @return 元素的数量。
    */
    public int count()
    {
        if(next==null||next.count==0)
        {
            return 0;
        }
        b_plus_tree now=next;
        int count=0;
        for(;now.type<2;now=now.children[0]);
        for(;now!=null;now=now.next)
        {
            count+=now.count;
        }
        return count;
    }
    /**
    <p>叶结点遍历</p><br>
    通过叶结点链表遍历B+树。
    @return 遍历结果。
    */
    public int[] traversal()
    {
        if(next==null||next.count==0)
        {
            return new int[0];
        }
        b_plus_tree now=next;
        for(;now.type<2;now=now.children[0]);
        int result[]=new int[count()];
        int pin=0;
        for(;now!=null;now=now.next)
        {
            System.arraycopy(now.elements,0,result,pin,now.count);
            pin+=now.count;
        }
        return result;
    }
    /**
    <p>元素输入</p><br>
    <p>此方法会修改调用对象。</p><br>
    将一个元素添加到B+树中。
    @param element 要添加的元素。
    @return 是否成功添加。
    */
    public boolean input(int element)
    {
        if(next==null)
        {
            next=new b_plus_tree(order,2);
        }
        b_plus_tree now=next;
        if(now.count==0)
        {
            now.elements[0]=element;
            now.count++;
            return true;
        }
        b_plus_tree pins[]=new b_plus_tree[10];
        int indexs[]=new int[10];
        int pin=0,capacity=10;
        while(now.type<2)
        {
            if(pin>=capacity)
            {
                capacity=(capacity<<1)+2;
                b_plus_tree new_pins[]=new b_plus_tree[capacity];
                int new_indexs[]=new int[capacity];
                System.arraycopy(pins,0,new_pins,0,pin);
                System.arraycopy(indexs,0,new_indexs,0,pin);
                pins=new_pins;
                indexs=new_indexs;
            }
            pins[pin]=now;
            int left=0,right=now.count-2;
            int target=now.count-1;
            while(left<=right)
            {
                int middle=(left+right)/2;
                if(now.elements[middle]>element)
                {
                    target=middle;
                    right=middle-1;
                }
                else
                {
                    left=middle+1;
                }
            }
            indexs[pin++]=target;
            now=now.children[target];
        }
        int left=0,right=now.count-1;
        int target=now.count;
        while(left<=right)
        {
            int middle=(left+right)/2;
            if(now.elements[middle]>element)
            {
                target=middle;
                right=middle-1;
            }
            else
            {
                left=middle+1;
            }
        }
        System.arraycopy(now.elements,target,now.elements,target+1,now.count-target);
        now.elements[target]=element;
        now.count++;
        if(pin>0&&target==0&&indexs[pin-1]>0)
        {
            pins[pin-1].elements[indexs[pin-1]-1]=element;
        }
        if(now.count>=order)
        {
            int middle=order/2;
            b_plus_tree new_node=new b_plus_tree(order,2);
            System.arraycopy(now.elements,middle,new_node.elements,0,order-middle);
            middle=order/2;
            now.count=middle;
            new_node.count=order-middle;
            new_node.next=now.next;
            now.next=new_node;
            for(pin--;pin>=0;pin--)
            {
                now=pins[pin];
                int index=indexs[pin];
                System.arraycopy(now.children,index+1,now.children,index+2,now.count-index-1);
                System.arraycopy(now.elements,index,now.elements,index+1,now.count-index-1);
                now.children[index+1]=new_node;
                b_plus_tree temp=new_node;
                while(temp.type<2)
                {
                    temp=temp.children[0];
                }
                now.elements[index]=temp.elements[0];
                now.count++;
                if(now.count>=order)
                {
                    new_node=new b_plus_tree(order,1);
                    System.arraycopy(now.children,middle,new_node.children,0,order-middle-1);
                    System.arraycopy(now.elements,middle,new_node.elements,0,order-middle-1);
                    middle=order/2;
                    now.count=middle;
                    new_node.count=order-middle;
                    new_node.children[new_node.count-1]=now.children[order-1];
                    new_node.next=now.next;
                    now.next=new_node;
                    if(pin==0)
                    {
                        now.type=1;
                        b_plus_tree new_root=new b_plus_tree(order,0);
                        new_root.children[0]=now;
                        new_root.children[1]=new_node;
                        temp=new_node;
                        while(temp.type<2)
                        {
                            temp=temp.children[0];
                        }
                        new_root.elements[0]=temp.elements[0];
                        new_root.count=2;
                        next=new_root;
                        return true;
                    }
                }
                else
                {
                    break;
                }
            }
            if(next.type!=0)
            {
                b_plus_tree new_root=new b_plus_tree(order,0);
                new_root.children[0]=now;
                new_root.children[1]=new_node;
                new_root.elements[0]=new_node.elements[0];
                new_root.count=2;
                next=new_root;
                return true;
            }
        }
        return true;
    }
    /**
    <p>区间元素获取</p><br>
    获取B+树中[<code>min</code>,<code>max</code>]区间内的元素。
    @param min 最小值。
    @param max 最大值。
    @return [<code>min</code>,<code>max</code>]区间内的元素。
    */
    public int[] get(int min,int max)
    {
        if(next==null||next.count==0)
        {
            return new int[0];
        }
        b_plus_tree now=next;
        while(now.type<2)
        {
            int left=0,right=now.count-2;
            int target=now.count-1;
            while(left<=right)
            {
                int middle=(left+right)/2;
                if(now.elements[middle]>=min)
                {
                    target=middle;
                    right=middle-1;
                }
                else
                {
                    left=middle+1;
                }
            }
            now=now.children[target];
        }
        if(now.elements[now.count-1]<min&&now.next!=null)
        {
            now=now.next;
        }
        int left=0,right=now.count-1;
        int left_index=now.count;
        while(left<=right)
        {
            int middle=(left+right)/2;
            if(now.elements[middle]>=min)
            {
                left_index=middle;
                right=middle-1;
            }
            else
            {
                left=middle+1;
            }
        }
        int result[]=new int[10];
        int total=0,capacity=10;
        for(;now!=null;now=now.next)
        {
            if(now.elements[now.count-1]<=max)
            {
                if(total+now.count-left_index>=capacity)
                {
                    capacity=(capacity<<1)+order;
                    int new_result[]=new int[capacity];
                    System.arraycopy(result,0,new_result,0,total);
                    result=new_result;
                }
                System.arraycopy(now.elements,left_index,result,total,now.count-left_index);
                total+=now.count-left_index;
                left_index=0;
            }
            else if(now.elements[left_index]>max)
            {
                int returning[]=new int[total];
                System.arraycopy(result,0,returning,0,total);
                return returning;
            }
            else
            {
                left=left_index;
                right=now.count-1;
                int right_index=now.count;
                while(left<=right)
                {
                    int middle=(left+right)/2;
                    if(now.elements[middle]>max)
                    {
                        right_index=middle;
                        right=middle-1;
                    }
                    else
                    {
                        left=middle+1;
                    }
                }
                if(total+now.count-left_index>=capacity)
                {
                    capacity=(capacity<<1)+order;
                    int new_result[]=new int[capacity];
                    System.arraycopy(result,0,new_result,0,total);
                    result=new_result;
                }
                System.arraycopy(now.elements,left_index,result,total,right_index-left_index);
                total+=right_index-left_index;
                int returning[]=new int[total];
                System.arraycopy(result,0,returning,0,total);
                return returning;
            }
        }
        int returning[]=new int[total];
        System.arraycopy(result,0,returning,0,total);
        return returning;
    }
    /**
    <p>元素删除（首个匹配）</p><br>
    <p>此方法会修改调用对象。</p><br>
    删除B+树中首个匹配的元素。
    @param element 要删除的元素。
    @return 是否成功删除。
    */
    public boolean remove(int element)
    {
        if(next==null||next.count==0)
        {
            return false;
        }
        b_plus_tree now=next;
        b_plus_tree pins[]=new b_plus_tree[10];
        int indexs[]=new int[10];
        int pin=0,capacity=10;
        while(now.type<2)
        {
            if(pin>=capacity)
            {
                capacity=(capacity<<1)+2;
                b_plus_tree new_pins[]=new b_plus_tree[capacity];
                int new_indexs[]=new int[capacity];
                System.arraycopy(pins,0,new_pins,0,pin);
                System.arraycopy(indexs,0,new_indexs,0,pin);
                pins=new_pins;
                indexs=new_indexs;
            }
            pins[pin]=now;
            int left=0,right=now.count-2;
            int target=now.count-1;
            while(left<=right)
            {
                int middle=(left+right)/2;
                if(now.elements[middle]>element)
                {
                    target=middle;
                    right=middle-1;
                }
                else
                {
                    left=middle+1;
                }
            }
            indexs[pin++]=target;
            now=now.children[target];
        }
        int left=0,right=now.count-1;
        int right_index=0;
        while(left<=right)
        {
            int middle=(left+right)/2;
            if(now.elements[middle]<=element)
            {
                right_index=middle;
                left=middle+1;
            }
            else
            {
                right=middle-1;
            }
        }
        if(now.elements[right_index]!=element)
        {
            return false;
        }
        System.arraycopy(now.elements,right_index+1,now.elements,right_index,now.count-right_index-1);
        now.count--;
        if(pin==0)
        {
            if(next.count==0)
            {
                next=null;
            }
            return true;
        }
        int now_min=now.elements[0];
        b_plus_tree parent;
        int parent_index;
        if(right_index==0)
        {
            int pin_max=pin-1;
            do
            {
                parent=pins[pin_max];
                parent_index=indexs[pin_max];
                pin_max--;
            }
            while(parent_index==0&&pin_max>=0);
            if(parent_index>0)
            {
                parent.elements[parent_index-1]=now_min;
            }
        }
        parent=pins[pin-1];
        parent_index=indexs[pin-1];
        b_plus_tree previous_node=parent_index>0?parent.children[parent_index-1]:null;
        b_plus_tree next_node=parent_index+1<parent.count?parent.children[parent_index+1]:null;
        if(now.count<order/2)
        {
            if(previous_node!=null&&previous_node.count>order/2)
            {
                System.arraycopy(now.elements,0,now.elements,1,now.count);
                now.elements[0]=previous_node.elements[--previous_node.count];
                now.count++;
                parent.elements[parent_index-1]=now.elements[0];
            }
            else if(next_node!=null&&next_node.count>order/2)
            {
                now.elements[now.count++]=next_node.elements[0];
                System.arraycopy(next_node.elements,1,next_node.elements,0,next_node.count-1);
                next_node.count--;
                parent.elements[parent_index]=next_node.elements[0];
            }
            else
            {
                if(previous_node!=null)
                {
                    System.arraycopy(now.elements,0,previous_node.elements,previous_node.count,now.count);
                    previous_node.count+=now.count;
                    previous_node.next=now.next;
                    parent.count--;
                    System.arraycopy(parent.elements,parent_index,parent.elements,parent_index-1,parent.count-parent_index);
                    System.arraycopy(parent.children,parent_index+1,parent.children,parent_index,parent.count-parent_index);
                }
                else if(next_node!=null)
                {
                    System.arraycopy(next_node.elements,0,now.elements,now.count,next_node.count);
                    now.count+=next_node.count;
                    now.next=next_node.next;
                    parent.count--;
                    System.arraycopy(parent.elements,parent_index+1,parent.elements,parent_index,parent.count-parent_index-1);
                    System.arraycopy(parent.children,parent_index+2,parent.children,parent_index+1,parent.count-parent_index-1);
                }
                for(pin--;pin>0;pin--)
                {
                    now=pins[pin];
                    parent=pins[pin-1];
                    parent_index=indexs[pin-1];
                    previous_node=parent_index>0?parent.children[parent_index-1]:null;
                    next_node=parent_index+1<parent.count?parent.children[parent_index+1]:null;
                    if(now.count<order/2)
                    {
                        if(previous_node!=null&&previous_node.count>order/2)
                        {
                            System.arraycopy(now.elements,0,now.elements,1,now.count);
                            System.arraycopy(now.children,0,now.children,1,now.count);
                            now.elements[0]=parent.elements[parent_index-1];
                            now.children[0]=previous_node.children[--previous_node.count];
                            parent.elements[parent_index-1]=previous_node.elements[previous_node.count-1];
                            now.count++;
                            break;
                        }
                        else if(next_node!=null&&next_node.count>order/2)
                        {
                            now.elements[now.count-1]=parent.elements[parent_index];
                            now.children[now.count++]=next_node.children[0];
                            int next_node_min_leaf_element=next_node.elements[0];
                            System.arraycopy(next_node.elements,1,next_node.elements,0,next_node.count-2);
                            System.arraycopy(next_node.children,1,next_node.children,0,next_node.count-2);
                            next_node.children[next_node.count-2]=next_node.children[next_node.count-1];
                            parent.elements[parent_index]=next_node_min_leaf_element;
                            next_node.count--;
                            break;
                        }
                        else
                        {
                            if(previous_node!=null)
                            {
                                previous_node.elements[previous_node.count-1]=parent.elements[parent_index-1];
                                System.arraycopy(now.elements,0,previous_node.elements,previous_node.count,now.count);
                                System.arraycopy(now.children,0,previous_node.children,previous_node.count,now.count);
                                previous_node.count+=now.count;
                                parent.count--;
                                System.arraycopy(parent.elements,parent_index,parent.elements,parent_index-1,parent.count-parent_index);
                                System.arraycopy(parent.children,parent_index+1,parent.children,parent_index,parent.count-parent_index);
                            }
                            else if(next_node!=null)
                            {
                                now.elements[now.count-1]=parent.elements[parent_index];
                                System.arraycopy(next_node.elements,0,now.elements,now.count,next_node.count);
                                System.arraycopy(next_node.children,0,now.children,now.count,next_node.count);
                                now.count+=next_node.count;
                                parent.count--;
                                System.arraycopy(parent.elements,parent_index+1,parent.elements,parent_index,parent.count-parent_index-1);
                                System.arraycopy(parent.children,parent_index+2,parent.children,parent_index+1,parent.count-parent_index-1);
                            }
                        }
                    }
                }
                if(next.type==0&&next.count==1)
                {
                    b_plus_tree new_root=next.children[0];
                    new_root.type=new_root.children==null?2:0;
                    next=new_root;
                    return true;
                }
            }
        }
        return true;
    }
    /**
    <p>元素删除（所有匹配）</p><br>
    <p>此方法会修改调用对象。</p><br>
    删除B+树中所有匹配的元素。
    @param element 要删除的元素。
    @return 删除的元素数量。
    */
    public int remove_all(int element)
    {
        int count=count(element);
        int fail=0;
        for(int time=0;time<count;time++)
        {
            fail+=remove(element)?0:1;
        }
        return count-fail;
    }
    /**
    <p>字符串表示</p><br>
    @return B+树的字符串表示。
    */
    public String toString()
    {
        if(this.next==null)
        {
            return order+"[]";
        }
        b_plus_tree now=this.next;
        for(;now.type<2;now=now.children[0]);
        StringBuilder result=new StringBuilder(order+"[");
        result.append("[");
        if(now.count>0)
        {
            result.append(now.elements[0]);
        }
        for(int i=1;i<now.count;i++)
        {
            result.append(","+now.elements[i]);
        }
        result.append("]");
        now=now.next;
        while(now!=null)
        {
            result.append("->[");
            if(now.count>0)
            {
                result.append(now.elements[0]);
            }
            for(int i=1;i<now.count;i++)
            {
                result.append(","+now.elements[i]);
            }
            result.append("]");
            now=now.next;
        }
        result.append("]");
        return result.toString();
    }
}