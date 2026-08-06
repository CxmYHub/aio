package aio.data_structure;
/**
<p>二叉查找树类</p><br>
二叉查找树是一种用于快速查找数据的二叉树。<br>
二叉查找树满足以下三条性质：<br>
<ol>
    <li>二叉查找树的左右子树都是二叉查找树。</li>
    <li>左子树中的所有结点的元素都小于根结点的元素。</li>
    <li>右子树中的所有结点的元素都大于根结点的元素。</li>
</ol><br>
本二叉查找树采用头结点设计，头结点的左子结点为根结点。<br>
其每个结点有一个元素，左、右两个子结点，一个父结点。
*/
public class binary_search_tree
{
    /**
    <p>结点元素</p>
    */
    public int element;
    /**
    <p>左子结点指针</p>
    */
    public binary_search_tree left;
    /**
    <p>右子结点指针</p>
    */
    public binary_search_tree right;
    /**
    <p>父结点指针</p>
    */
    public binary_search_tree parent;
    /**
    <p>结点构造方法</p><br>
    构造一个二叉查找树结点。
    @param element 元素。
    */
    public binary_search_tree(int element)
    {
        this.element=element;
        left=null;
        right=null;
        parent=null;
    }
    /**
    <p>构造方法</p><br>
    构造一个包含指定元素的二叉查找树。
    @param elements 多个元素。
    */
    public binary_search_tree(int... elements)
    {
        this.element=0;
        left=null;
        right=null;
        parent=null;
        left=new binary_search_tree(elements[0]);
        left.parent=this;
        for(int element:elements)
        {
            binary_search_tree now=left;
            while(true)
            {
                if(element<now.element)
                {
                    if(now.left==null)
                    {
                        now.left=new binary_search_tree(element);
                        now.left.parent=now;
                        now=now.left;
                        break;
                    }
                    now=now.left;
                }
                else if(element>now.element)
                {
                    if(now.right==null)
                    {
                        now.right=new binary_search_tree(element);
                        now.right.parent=now;
                        now=now.right;
                        break;
                    }
                    now=now.right;
                }
                else
                {
                    break;
                }
            }
        }
    }
    /**
    <p>无参构造方法</p><br>
    构造一个空二叉查找树。
    @param element 元素。
    */
    public binary_search_tree()
    {
        element=0;
        left=null;
        right=null;
        parent=null;
    }
    /**
    <p>遍历</p><br>
    中序遍历二叉查找树。
    @return 中序遍历结果。
    */
    public int[] traversal()
    {
        binary_search_tree pins[]=new binary_search_tree[10];
        int pin=0,capacity=10;
        binary_search_tree now=left;
        int result[]=new int[10];
        int count=0,result_count=10;
        while(now!=null||pin>0)
        {
            for(;now!=null;now=now.left)
            {
                if(pin>=capacity)
                {
                    capacity=(capacity<<1)+2;
                    binary_search_tree new_pins[]=new binary_search_tree[capacity];
                    System.arraycopy(pins,0,new_pins,0,pin);
                    pins=new_pins;
                }
                pins[pin++]=now;
            }
            now=pins[--pin];
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
            now=now.right;
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
    <p>元素输入</p><br>
    <p>此方法会修改调用对象。</p><br>
    向红黑树中插入一个元素。
    @param element 要插入的元素。
    @return 是否成功插入。
    */
    public boolean input(int element)
    {
        binary_search_tree now=left;
        if(now==null)
        {
            left=new binary_search_tree(element);
            left.parent=this;
            return true;
        }
        while(true)
        {
            if(element<now.element)
            {
                if(now.left==null)
                {
                    now.left=new binary_search_tree(element);
                    now.left.parent=now;
                    return true;
                }
                now=now.left;
            }
            else if(element>now.element)
            {
                if(now.right==null)
                {
                    now.right=new binary_search_tree(element);
                    now.right.parent=now;
                    return true;
                }
                now=now.right;
            }
            else
            {
                return false;
            }
        }
    }
    /**
    <p>元素批量输入</p><br>
    <p>此方法会修改调用对象。</p><br>
    向二叉查找树中插入多个元素。<br>
    若元素重复，则仅插入一次，忽略剩余的重复元素。
    @param elements 要插入的多个元素。
    @return 忽略的元素数量。
    */
    public int input_more(int... elements)
    {
        int duplicate=0;
        for(int element:elements)
        {
            binary_search_tree now=left;
            if(now==null)
            {
                left=new binary_search_tree(element);
                left.parent=this;
                continue;
            }
            while(true)
            {
                if(element<now.element)
                {
                    if(now.left==null)
                    {
                        now.left=new binary_search_tree(element);
                        now.left.parent=now;
                        now=now.left;
                        break;
                    }
                    now=now.left;
                }
                else if(element>now.element)
                {
                    if(now.right==null)
                    {
                        now.right=new binary_search_tree(element);
                        now.right.parent=now;
                        now=now.right;
                        break;
                    }
                    now=now.right;
                }
                else
                {
                    duplicate++;
                    break;
                }
            }
        }
        return duplicate;
    }
    /**
    <p>元素深度计算</p><br>
    获取二叉查找树中元素的深度。
    @param element 要获取深度的元素。
    @return 元素的深度。<br>
    若元素不存在，则返回<code>Integer.MIN_VALUE</code>。
    */
    public int get_depth(int element)
    {
        binary_search_tree now=left;
        if(now==null)
        {
            return Integer.MIN_VALUE;
        }
        int depth=0;
        for(;now.element!=element;depth++)
        {
            if(element<now.element)
            {
                now=now.left;
            }
            else if(element>now.element)
            {
                now=now.right;
            }
            if(now==null)
            {
                return Integer.MIN_VALUE;
            }
        }
        return depth;
    }
    /**
    <p>元素删除</p><br>
    <p>此方法会修改调用对象。</p><br>
    从二叉查找树中删除一个元素。
    @param element 要删除的元素。
    @return 是否成功删除。
    */
    public boolean remove(int element)
    {
        binary_search_tree now=left;
        if(now==null)
        {
            return false;
        }
        while(now.element!=element)
        {
            if(element<now.element)
            {
                if(now.left==null)
                {
                    return false;
                }
                now=now.left;
            }
            else if(element>now.element)
            {
                if(now.right==null)
                {
                    return false;
                }
                now=now.right;
            }
        }
        if(now.left!=null&&now.right!=null)
        {
            binary_search_tree next=now.right;
            while(next.left!=null)
            {
                next=next.left;
            }
            now.element=next.element;
            now=next;
        }
        boolean now_is_left=now==now.parent.left;
        if(now.left==null&&now.right==null)
        {
            if(now_is_left)
            {
                now.parent.left=null;
            }
            else
            {
                now.parent.right=null;
            }
            return true;
        }
        else if(now.left!=null)
        {
            if(now_is_left)
            {
                now.parent.left=now.left;
            }
            else
            {
                now.parent.right=now.left;
            }
            now.left.parent=now.parent;
            now=now.left;
        }
        else
        {
            if(now_is_left)
            {
                now.parent.left=now.right;
            }
            else
            {
                now.parent.right=now.right;
            }
            now.right.parent=now.parent;
            now=now.right;
        }
        return true;
    }
    /**
    <p>字符串表示</p><br>
    @return 二叉查找树的字符串表示。
    */
    public String toString()
    {
        if(left==null)
        {
            return "";
        }
        binary_search_tree pins[]=new binary_search_tree[10];
        int pin=0,capacity=10;
        pins[0]=left;
        StringBuilder result=new StringBuilder("");
        while(pin>=0)
        {
            result.append(""+pins[pin].element);
            if(pins[pin].left!=null)
            {
                if(pin+1>=capacity)
                {
                    capacity=(capacity<<1)+2;
                    binary_search_tree new_pins[]=new binary_search_tree[capacity];
                    System.arraycopy(pins,0,new_pins,0,pin+1);
                    pins=new_pins;
                }
                result.append("{");
                pins[pin+1]=pins[pin].left;
                pin++;
            }
            else if(pins[pin].right!=null)
            {
                if(pin+1>=capacity)
                {
                    capacity=(capacity<<1)+2;
                    binary_search_tree new_pins[]=new binary_search_tree[capacity];
                    System.arraycopy(pins,0,new_pins,0,pin+1);
                    pins=new_pins;
                }
                result.append("{,");
                pins[pin+1]=pins[pin].right;
                pin++;
            }
            else
            {
                boolean back=false;
                do
                {
                    if(back)
                    {
                        result.append("}");
                    }
                    pin--;
                    back=true;
                }
                while(pin>=0&&(pins[pin].right==null||pins[pin].right==pins[pin+1]));
                if(pin>=0)
                {
                    result.append(",");
                    pins[pin+1]=pins[pin].right;
                    pin++;
                }
            }
        }
        return result.toString();
    }
}