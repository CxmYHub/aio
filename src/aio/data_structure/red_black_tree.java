package aio.data_structure;
/**
<p>红黑树类</p><br>
红黑树是一种自平衡二叉查找树。<br>
其规则较为宽松，平衡了读取和修改操作的效率。<br>
二叉查找树满足以下三条性质：<br>
<ol>
    <li>二叉查找树的左右子树都是二叉查找树。</li>
    <li>左子树中的所有结点的元素都小于根结点的元素。</li>
    <li>右子树中的所有结点的元素都大于根结点的元素。</li>
</ol><br>
除二叉查找树基本性质外，红黑树还满足以下五条性质：<br>
<ol>
    <li>结点是红色的或黑色的。</li>
    <li>根结点是黑色的。</li>
    <li>所有叶结点都是黑色的。</li>
    <li>如果一个结点是红色的，那么它的子结点都是黑色的。即不存在连续的红色结点。</li>
    <li>从任意结点到其每个叶结点的所有简单路径都包含相同数量的黑色结点。即黑高相等。</li>
</ol><br>
本红黑树采用头结点设计，头结点的左子结点为根结点。<br>
其每个结点有一个元素，一个颜色，左、右两个子结点，一个父结点。
*/
public class red_black_tree
{
    /**
    <p>ANSI红色转义符</p>
    */
    public static final String ansi_red_color="\u001B[31m";
    /**
    <p>ANSI默认转义符</p>
    */
    public static final String ansi_default_color="\u001B[39m";
    /**
    <p>结点元素</p>
    */
    public int element;
    /**
    <p>结点颜色</p><br>
    <ul>
        <li>=<code>true</code>：红色</li>
        <li>=<code>false</code>：黑色</li>
    </ul>
    */
    public boolean is_red;
    /**
    <p>左子结点指针</p>
    */
    public red_black_tree left;
    /**
    <p>右子结点指针</p>
    */
    public red_black_tree right;
    /**
    <p>父结点指针</p>
    */
    public red_black_tree parent;
    /**
    <p>NIL结点构造方法</p><br>
    构造一个特殊的叶结点。
    @param NIL 哑元，用于创建NIL结点。
    */
    public red_black_tree(char NIL)
    {
        element=Integer.MIN_VALUE;
        is_red=false;
        left=this;
        right=this;
        parent=this;
    }
    /**
    <p>NIL结点</p><br>
    特殊空结点，用于替代null以避免空指针异常。
    */
    public static final red_black_tree NIL=new red_black_tree('N');
    /**
    <p>结点构造方法</p><br>
    构造一个红黑树结点。
    @param element 元素。
    */
    public red_black_tree(int element)
    {
        this.element=element;
        is_red=true;
        left=NIL;
        right=NIL;
        parent=NIL;
    }
    /**
    <p>左旋红黑树</p><br>
    <p>此方法会修改调用对象。</p><br>
    左旋将原根结点的右子结点作为新根结点。<br>
    将原根结点右子树的左子结点作为原根结点的右子结点。<br>
    <code>O{L,R{rl,rr}}</code>-><code>R{O{L,rl},rr}</code>
    @param tree 红黑树。
    @return 左旋后的红黑树根结点，即原树的右子结点。<br>
    若原树的右子结点为NIL，则返回NIL。
    */
    public static red_black_tree left_rotate(red_black_tree tree)
    {
        if(tree==NIL||tree.right==NIL)
        {
            return NIL;
        }
        red_black_tree root_parent=tree.parent;
        red_black_tree right=tree.right;
        red_black_tree right_left=right.left;
        tree.right=right_left;
        if(right_left!=NIL)
        {
            right_left.parent=tree;
        }
        right.left=tree;
        tree.parent=right;
        right.parent=root_parent;
        if(root_parent!=NIL)
        {
            if(root_parent.left==tree)
            {
                root_parent.left=right;
            }
            else
            {
                root_parent.right=right;
            }
        }
        return right;
    }
    /**
    <p>右旋红黑树</p><br>
    <p>此方法会修改调用对象。</p><br>
    右旋将原根结点的左子结点作为新根结点。<br>
    将原根结点左子树的右子结点作为原根结点的左子结点。<br>
    <code>O{L{ll,lr},R}</code>-><code>L{ll,O{lr,R}}</code>
    @param tree 红黑树。
    @return 右旋后的红黑树根结点，即原树的左子结点。<br>
    若原树的左子结点为NIL，则返回NIL。
    */
    public static red_black_tree right_rotate(red_black_tree tree)
    {
        if(tree==NIL||tree.left==NIL)
        {
            return NIL;
        }
        red_black_tree root_parent=tree.parent;
        red_black_tree left=tree.left;
        red_black_tree left_right=left.right;
        tree.left=left_right;
        if(left_right!=NIL)
        {
            left_right.parent=tree;
        }
        left.right=tree;
        tree.parent=left;
        left.parent=root_parent;
        if(root_parent!=NIL)
        {
            if(root_parent.left==tree)
            {
                root_parent.left=left;
            }
            else
            {
                root_parent.right=left;
            }
        }
        return left;
    }
    /**
    <p>构造方法</p><br>
    构造一个包含指定元素的红黑树。
    @param elements 多个元素。
    */
    public red_black_tree(int... elements)
    {
        this.element=0;
        is_red=false;
        right=NIL;
        parent=NIL;
        left=new red_black_tree(elements[0]);
        left.parent=this;
        left.is_red=false;
        select_elements:
        for(int element:elements)
        {
            red_black_tree now=left;
            while(true)
            {
                if(element<now.element)
                {
                    if(now.left==NIL)
                    {
                        now.left=new red_black_tree(element);
                        now.left.parent=now;
                        now=now.left;
                        break;
                    }
                    now=now.left;
                }
                else if(element>now.element)
                {
                    if(now.right==NIL)
                    {
                        now.right=new red_black_tree(element);
                        now.right.parent=now;
                        now=now.right;
                        break;
                    }
                    now=now.right;
                }
                else
                {
                    continue select_elements;
                }
            }
            while(now.parent.is_red)
            {
                red_black_tree grand_parent=now.parent.parent;
                red_black_tree uncle=grand_parent.left==now.parent?grand_parent.right:grand_parent.left;
                if(now.parent.is_red&&uncle.is_red)
                {
                    now.parent.is_red=false;
                    uncle.is_red=false;
                    grand_parent.is_red=true;
                    now=grand_parent;
                }
                else if(now.parent.is_red&&!uncle.is_red)
                {
                    if(grand_parent.left.right==now)
                    {
                        now=now.parent;
                        left_rotate(now);
                    }
                    else if(grand_parent.right.left==now)
                    {
                        now=now.parent;
                        right_rotate(now);
                    }
                    if(grand_parent.left.left==now)
                    {
                        now=right_rotate(grand_parent);
                        now.is_red=false;
                        grand_parent.is_red=true;
                        continue select_elements;
                    }
                    else if(grand_parent.right.right==now)
                    {
                        now=left_rotate(grand_parent);
                        now.is_red=false;
                        grand_parent.is_red=true;
                        continue select_elements;
                    }
                }
            }
            is_red=false;
            left.is_red=false;
        }
    }
    /**
    <p>无参构造方法</p><br>
    构造一个空红黑树。
    */
    public red_black_tree()
    {
        element=0;
        is_red=false;
        left=NIL;
        right=NIL;
        parent=NIL;
    }
    /**
    <p>遍历</p><br>
    中序遍历红黑树。
    @return 中序遍历结果。
    */
    public int[] traversal()
    {
        red_black_tree pins[]=new red_black_tree[10];
        int pin=0,capacity=10;
        red_black_tree now=left;
        int result[]=new int[10];
        int count=0,result_count=10;
        while(now!=NIL||pin>0)
        {
            for(;now!=NIL;now=now.left)
            {
                if(pin>=capacity)
                {
                    capacity=(capacity<<1)+2;
                    red_black_tree new_pins[]=new red_black_tree[capacity];
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
        red_black_tree now=left;
        if(now==NIL)
        {
            left=new red_black_tree(element);
            left.parent=this;
            left.is_red=false;
            return true;
        }
        while(true)
        {
            if(element<now.element)
            {
                if(now.left==NIL)
                {
                    now.left=new red_black_tree(element);
                    now.left.parent=now;
                    now=now.left;
                    break;
                }
                now=now.left;
            }
            else if(element>now.element)
            {
                if(now.right==NIL)
                {
                    now.right=new red_black_tree(element);
                    now.right.parent=now;
                    now=now.right;
                    break;
                }
                now=now.right;
            }
            else
            {
                return false;
            }
        }
        while(now.parent.is_red)
        {
            red_black_tree grand_parent=now.parent.parent;
            red_black_tree uncle=grand_parent.left==now.parent?grand_parent.right:grand_parent.left;
            if(now.parent.is_red&&uncle.is_red)
            {
                now.parent.is_red=false;
                uncle.is_red=false;
                grand_parent.is_red=true;
                now=grand_parent;
            }
            else if(now.parent.is_red&&!uncle.is_red)
            {
                if(grand_parent.left.right==now)
                {
                    now=now.parent;
                    left_rotate(now);
                }
                else if(grand_parent.right.left==now)
                {
                    now=now.parent;
                    right_rotate(now);
                }
                if(grand_parent.left.left==now)
                {
                    now=right_rotate(grand_parent);
                    now.is_red=false;
                    grand_parent.is_red=true;
                    return true;
                }
                else if(grand_parent.right.right==now)
                {
                    now=left_rotate(grand_parent);
                    now.is_red=false;
                    grand_parent.is_red=true;
                    return true;
                }
            }
        }
        is_red=false;
        left.is_red=false;
        return true;
    }
    /**
    <p>元素批量输入</p><br>
    <p>此方法会修改调用对象。</p><br>
    向红黑树中插入多个元素。<br>
    若元素重复，则仅插入一次，忽略剩余的重复元素。
    @param elements 要插入的多个元素。
    @return 忽略的元素数量。
    */
    public int input_more(int... elements)
    {
        int duplicate=0;
        select_elements:
        for(int element:elements)
        {
            red_black_tree now=left;
            if(now==NIL)
            {
                left=new red_black_tree(element);
                left.parent=this;
                left.is_red=false;
                continue;
            }
            while(true)
            {
                if(element<now.element)
                {
                    if(now.left==NIL)
                    {
                        now.left=new red_black_tree(element);
                        now.left.parent=now;
                        now=now.left;
                        break;
                    }
                    now=now.left;
                }
                else if(element>now.element)
                {
                    if(now.right==NIL)
                    {
                        now.right=new red_black_tree(element);
                        now.right.parent=now;
                        now=now.right;
                        break;
                    }
                    now=now.right;
                }
                else
                {
                    duplicate++;
                    continue select_elements;
                }
            }
            while(now.parent.is_red)
            {
                red_black_tree grand_parent=now.parent.parent;
                red_black_tree uncle=grand_parent.left==now.parent?grand_parent.right:grand_parent.left;
                if(now.parent.is_red&&uncle.is_red)
                {
                    now.parent.is_red=false;
                    uncle.is_red=false;
                    grand_parent.is_red=true;
                    now=grand_parent;
                }
                else if(now.parent.is_red&&!uncle.is_red)
                {
                    if(grand_parent.left.right==now)
                    {
                        now=now.parent;
                        left_rotate(now);
                    }
                    else if(grand_parent.right.left==now)
                    {
                        now=now.parent;
                        right_rotate(now);
                    }
                    if(grand_parent.left.left==now)
                    {
                        now=right_rotate(grand_parent);
                        now.is_red=false;
                        grand_parent.is_red=true;
                        continue select_elements;
                    }
                    else if(grand_parent.right.right==now)
                    {
                        now=left_rotate(grand_parent);
                        now.is_red=false;
                        grand_parent.is_red=true;
                        continue select_elements;
                    }
                }
            }
            is_red=false;
            left.is_red=false;
        }
        return duplicate;
    }
    /**
    <p>元素深度计算</p><br>
    获取红黑树中元素的深度。
    @param element 要获取深度的元素。
    @return 元素的深度。<br>
    若元素不存在，则返回<code>Integer.MIN_VALUE</code>。
    */
    public int get_depth(int element)
    {
        red_black_tree now=left;
        int depth=0;
        for(;now.element!=element;depth++)
        {
            if(now==NIL)
            {
                return Integer.MIN_VALUE;
            }
            if(element<now.element)
            {
                now=now.left;
            }
            else if(element>now.element)
            {
                now=now.right;
            }
        }
        return depth;
    }
    /**
    <p>元素删除</p><br>
    <p>此方法会修改调用对象。</p><br>
    从红黑树中删除一个元素。
    @param element 要删除的元素。
    @return 是否成功删除。
    */
    public boolean remove(int element)
    {
        red_black_tree now=left;
        if(now==NIL)
        {
            return false;
        }
        while(now.element!=element)
        {
            if(element<now.element)
            {
                if(now.left==NIL)
                {
                    return false;
                }
                now=now.left;
            }
            else if(element>now.element)
            {
                if(now.right==NIL)
                {
                    return false;
                }
                now=now.right;
            }
        }
        if(now.left!=NIL&&now.right!=NIL)
        {
            red_black_tree next=now.right;
            while(next.left!=NIL)
            {
                next=next.left;
            }
            now.element=next.element;
            now=next;
        }
        red_black_tree sibling=now.parent.left==now?now.parent.right:now.parent.left;
        boolean now_is_left=now==now.parent.left;
        if(now.left==NIL&&now.right==NIL)
        {
            if(now.is_red||now==left)
            {
                if(now_is_left)
                {
                    now.parent.left=NIL;
                }
                else
                {
                    now.parent.right=NIL;
                }
            }
            else
            {
                red_black_tree deleting=now;
                while(!now.is_red&&left!=now)
                {
                    sibling=now.parent.left==now?now.parent.right:now.parent.left;
                    now_is_left=now==now.parent.left;
                    if(sibling.is_red)
                    {
                        sibling.is_red=false;
                        now.parent.is_red=true;
                        if(now_is_left)
                        {
                            left_rotate(now.parent);
                        }
                        else
                        {
                            right_rotate(now.parent);
                        }
                    }
                    else if(!sibling.left.is_red&&!sibling.right.is_red)
                    {
                        sibling.is_red=true;
                        now=now.parent;
                    }
                    else if(now_is_left)
                    {
                        if(sibling.left.is_red&&!sibling.right.is_red)
                        {
                            sibling.left.is_red=false;
                            sibling.is_red=true;
                            right_rotate(sibling);
                        }
                        else if(sibling.right.is_red)
                        {
                            left_rotate(now.parent);
                            boolean temp=now.parent.is_red;
                            now.parent.is_red=sibling.is_red;
                            sibling.is_red=temp;
                            sibling.right.is_red=false;
                            break;
                        }
                    }
                    else
                    {
                        if(!sibling.left.is_red&&sibling.right.is_red)
                        {
                            sibling.right.is_red=false;
                            sibling.is_red=true;
                            left_rotate(sibling);
                        }
                        else if(sibling.left.is_red)
                        {
                            right_rotate(now.parent);
                            boolean temp=now.parent.is_red;
                            now.parent.is_red=sibling.is_red;
                            sibling.is_red=temp;
                            sibling.left.is_red=false;
                            break;
                        }
                    }
                }
                if(deleting.parent.left==deleting)
                {
                    deleting.parent.left=NIL;
                }
                else
                {
                    deleting.parent.right=NIL;
                }
                now.is_red=false;
            }
        }
        else if(now.left!=NIL)
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
            now.is_red=false;
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
            now.is_red=false;
        }
        return true;
    }
    /**
    <p>字符串表示</p><br>
    @return 红黑树的字符串表示。
    */
    public String toString()
    {
        if(parent==NIL&&left==NIL)
        {
            return "";
        }
        red_black_tree pins[]=new red_black_tree[10];
        int pin=0,capacity=10;
        pins[0]=parent==NIL?left:this;
        StringBuilder result=new StringBuilder(ansi_default_color);
        while(pin>=0)
        {
            result.append((pins[pin].is_red?ansi_red_color:"")+pins[pin].element+ansi_default_color);
            if(pins[pin].left!=NIL)
            {
                if(pin+1>=capacity)
                {
                    capacity=(capacity<<1)+2;
                    red_black_tree new_pins[]=new red_black_tree[capacity];
                    System.arraycopy(pins,0,new_pins,0,pin+1);
                    pins=new_pins;
                }
                result.append("{");
                pins[pin+1]=pins[pin].left;
                pin++;
            }
            else if(pins[pin].right!=NIL)
            {
                if(pin+1>=capacity)
                {
                    capacity=(capacity<<1)+2;
                    red_black_tree new_pins[]=new red_black_tree[capacity];
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
                while(pin>=0&&(pins[pin].right==NIL||pins[pin].right==pins[pin+1]));
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