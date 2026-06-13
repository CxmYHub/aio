package tools.data_structure;
/**
<p>树类。</p><br>
树是一种非线性数据结构。<br>
其每个节点包含一个元素和零个或多个子节点。<br>
其中：
<ul>
    <li>没有子节点的节点称为叶节点。</li>
    <li>没有父节点的节点称为根节点。</li>
</ul>
<br>本树以孩子兄弟表示法实现。
*/
public class tree
{
    public int element;
    public tree child;
    public tree next;
    /**
    通过树字符串构造一个树。
    @param tree_string 树字符串。<br>
    树字符串的格式为：<code>根节点{子树1,子树2,子树3,...}...</code>。<br>
    例如：<code>A{B{D,E},C{F,G,H,I}}</code>。<br>
    */
    public tree(String tree_string)
    {
        char tree_chars[]=tree_string.toCharArray();
        tree pins[]=new tree[10];
        int pin=0,capacity=10;
        pins[0]=this;
        int this_element=0;
        for(int i=0;i<tree_chars.length;i++)
        {
            if(tree_chars[i]>='0'&&tree_chars[i]<='9')
            {
                this_element*=10;
                this_element+=tree_chars[i]-'0';
            }
            else if(tree_chars[i]>='A'&&tree_chars[i]<='Z'||tree_chars[i]>='a'&&tree_chars[i]<='z')
            {
                this_element=tree_chars[i];
            }
            else if(tree_chars[i]==',')
            {
                if(tree_chars[i-1]!='}')
                {
                    pins[pin].element=this_element;
                    this_element=0;
                }
                pins[pin].next=new tree();
                pins[pin]=pins[pin].next;
            }
            else if(tree_chars[i]=='{')
            {
                if(pin>=capacity)
                {
                    capacity=(capacity<<1)+2;
                    tree new_pins[]=new tree[capacity];
                    System.arraycopy(pins,0,new_pins,0,pin);
                    pins=new_pins;
                }
                pins[pin].element=this_element;
                this_element=0;
                pins[pin].child=new tree();
                pins[pin+1]=pins[pin].child;
                pin++;
            }
            else if(tree_chars[i]=='}')
            {
                if(tree_chars[i-1]!='}')
                {
                    pins[pin].element=this_element;
                    this_element=0;
                }
                pin--;
            }
        }
        if(this_element!=0)
        {
            pins[pin].element=this_element;
        }
    }
    /**
    通过元素构造一个树。
    @param element 元素。
    */
    public tree(int element)
    {
        this.element=element;
    }
    private tree()
    {
    }
    /**
    计算树的节点数。
    @return 树的节点数。
    */
    public int count()
    {
        tree pins[]=new tree[10];
        int pin=1,capacity=10;
        pins[0]=this;
        int count=0;
        while(pin>0)
        {
            tree now=pins[--pin];
            count++;
            if(now.child!=null)
            {
                pins[pin++]=now.child;
            }
            if(now.next!=null)
            {
                if(pin>=capacity)
                {
                    capacity=(capacity<<1)+2;
                    tree new_pins[]=new tree[capacity];
                    System.arraycopy(pins,0,new_pins,0,pin);
                    pins=new_pins;
                }
                pins[pin++]=now.next;
            }
        }
        return count;
    }
    /**
    计算树的深度。
    @return 树的深度。
    */
    public int depth()
    {
        tree pins[]=new tree[10];
        int top=0,rear=1,capacity=10;
        boolean overturn=false;
        pins[0]=this;
        int level_size=1;
        int depth=0;
        while(level_size>0)
        {
            depth++;
            tree now;
            int next_level_size=0;
            for(;level_size>0;level_size--)
            {
                now=pins[top++];
                if(top>=capacity)
                {
                    top=0;
                    overturn=false;
                }
                if(now.child!=null)
                {
                    for(now=now.child;now!=null;now=now.next)
                    {
                        if(top==rear&&overturn)
                        {
                            tree new_pins[]=new tree[(capacity<<1)+2];
                            System.arraycopy(pins,top,new_pins,0,capacity-top);
                            System.arraycopy(pins,0,new_pins,capacity-top,rear);
                            pins=new_pins;
                            top=0;
                            rear=capacity;
                            capacity=(capacity<<1)+2;
                            overturn=false;
                        }
                        pins[rear++]=now;
                        next_level_size++;
                        if(rear>=capacity)
                        {
                            rear=0;
                            overturn=true;
                        }
                    }
                }
            }
            level_size=next_level_size;
        }
        return depth;
    }
    /**
    先序遍历树。
    @return 先序遍历结果。
    */
    public int[] traversal_preorder()
    {
        tree pins[]=new tree[10];
        int pin=1,capacity=10;
        pins[0]=this;
        int result[]=new int[10];
        int count=0,result_count=10;
        while(pin>0)
        {
            tree now=pins[--pin];
            if(count>=result_count)
            {
                result_count=result_count*2+2;
                int new_result[]=new int[result_count];
                for(int i=0;i<count;i++)
                {
                    new_result[i]=result[i];
                }
                result=new_result;
            }
            result[count++]=now.element;
            if(now.next!=null)
            {
                pins[pin++]=now.next;
            }
            if(now.child!=null)
            {
                if(pin>=capacity)
                {
                    capacity=(capacity<<1)+2;
                    tree new_pins[]=new tree[capacity];
                    System.arraycopy(pins,0,new_pins,0,pin);
                    pins=new_pins;
                }
                pins[pin++]=now.child;
            }
        }
        if(count<result_count)
        {
            int new_result[]=new int[count];
            for(int i=0;i<count;i++)
            {
                new_result[i]=result[i];
            }
            result=new_result;
        }
        return result;
    }
    /**
    后序遍历树。
    @return 后序遍历结果。
    */
    public int[] traversal_postorder()
    {
        tree level_last[]=new tree[10];
        int level=0,capacity=10;
        level_last[0]=this;
        boolean back=false;
        int result[]=new int[10];
        int count=0,result_count=10;
        while(level>=0)
        {
            tree now=level_last[level];
            for(;!back&&now.child!=null;now=now.child)
            {
                if(level+1>=capacity)
                {
                    capacity=(capacity<<1)+2;
                    tree new_level_last[]=new tree[capacity];
                    System.arraycopy(level_last,0,new_level_last,0,level+1);
                    level_last=new_level_last;
                }
                level_last[++level]=now.child;
            }
            while(now!=null&&(now.child==null||back))
            {
                if(count>=result_count)
                {
                    result_count=result_count*2+2;
                    int new_result[]=new int[result_count];
                    for(int i=0;i<count;i++)
                    {
                        new_result[i]=result[i];
                    }
                    result=new_result;
                }
                result[count++]=now.element;
                now=now.next;
                level_last[level]=now;
                back=false;
            }
            if(now==null)
            {
                level--;
                back=true;
            }
        }
        if(count<result_count)
        {
            int new_result[]=new int[count];
            for(int i=0;i<count;i++)
            {
                new_result[i]=result[i];
            }
            result=new_result;
        }
        return result;
    }
    /**
    层序遍历树。
    @return 层序遍历结果。
    */
    public int[] traversal_levelorder()
    {
        int node_count=count();
        tree pins[]=new tree[node_count];
        int pin=1;
        pins[0]=this;
        int result[]=new int[node_count];
        int count=0;
        for(;count<pins.length;count++)
        {
            tree now=pins[count];
            result[count]=now.element;
            for(tree childs=now.child;childs!=null;childs=childs.next)
            {
                pins[pin++]=childs;
            }
        }
        return result;
    }
    /**
	<p>此方法会修改调用对象。</p><br>
    向树中指定元素的子节点插入一个元素。
    @param element 要插入的元素。
    @param target 要插入的位置的元素。
    @return 插入的元素。<br>
    若目标元素不存在，则返回<code>Integer.MIN_VALUE</code>。
    */
    public int insert_to(int element,int target)
    {
        tree pins[]=new tree[10];
        int pin=1,capacity=10;
        pins[0]=this;
        while(pin>0)
        {
            tree now=pins[--pin];
            if(now.element==target)
            {
                if(now.child==null)
                {
                    now.child=new tree(element);
                }
                else
                {
                    tree last=now.child;
                    for(;last.next!=null;last=last.next);
                    last.next=new tree(element);
                }
                return element;
            }
            if(now.next!=null)
            {
                pins[pin++]=now.next;
            }
            if(now.child!=null)
            {
                if(pin>=capacity)
                {
                    capacity=(capacity<<1)+2;
                    tree new_pins[]=new tree[capacity];
                    System.arraycopy(pins,0,new_pins,0,pin);
                    pins=new_pins;
                }
                pins[pin++]=now.child;
            }
        }
        return Integer.MIN_VALUE;
    }
    /**
	<p>此方法会修改调用对象。</p><br>
    从树中删除一个元素。<br>
    若存在多个相同元素，则只删除先序遍历序列中出现的第一个。
    @param element 要删除的元素。
    @return 删除的元素。<br>
    若元素不存在，则返回<code>Integer.MIN_VALUE</code>。
    */
    public int remove(int element)
    {
        if(this.element==element)
        {
            this.element=0;
            this.child=null;
            this.next=null;
            return element;
        }
        tree pins[]=new tree[10];
        int pin=1,capacity=10;
        pins[0]=this;
        while(pin>0)
        {
            tree now=pins[--pin];
            if(now.next!=null)
            {
                if(now.next.element==element)
                {
                    now.next=now.next.next;
                    return element;
                }
                pins[pin++]=now.next;
            }
            if(now.child!=null)
            {
                if(now.child.element==element)
                {
                    now.child=now.child.next;
                    return element;
                }
                if(pin>=capacity)
                {
                    capacity=(capacity<<1)+2;
                    tree new_pins[]=new tree[capacity];
                    System.arraycopy(pins,0,new_pins,0,pin);
                    pins=new_pins;
                }
                pins[pin++]=now.child;
            }
        }
        return Integer.MIN_VALUE;
    }
    public String toString()
    {
        tree pins[]=new tree[10];
        int pin=0,capacity=10;
        pins[0]=this;
        StringBuilder result=new StringBuilder("");
        while(pin>=0)
        {
            result.append(pins[pin].element);
            if(pins[pin].child!=null)
            {
                if(pin+1>=capacity)
                {
                    capacity=(capacity<<1)+2;
                    tree new_pins[]=new tree[capacity];
                    System.arraycopy(pins,0,new_pins,0,pin+1);
                    pins=new_pins;
                }
                result.append("{");
                pins[pin+1]=pins[pin].child;
                pin++;
            }
            else if(pins[pin].next!=null)
            {
                result.append(",");
                pins[pin]=pins[pin].next;
            }
            else
            {
                do
                {
                    if(pin>0)
                    {
                        result.append("}");
                    }
                    pin--;
                }
                while(pin>=0&&pins[pin].next==null);
                if(pin>0)
                {
                    result.append(",");
                    pins[pin]=pins[pin].next;
                }
            }
        }
        return result.toString();
    }
}