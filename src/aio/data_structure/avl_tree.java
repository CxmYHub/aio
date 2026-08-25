package aio.data_structure;
/**
<p>AVL树类</p><br>
AVL树是一种自平衡二叉查找树。<br>
其规则严格，读取操作更快，但修改操作的效率略低。<br>
二叉查找树满足以下三条性质：<br>
<ol>
    <li>二叉查找树的左右子树都是二叉查找树。</li>
    <li>左子树中的所有结点的元素都小于根结点的元素。</li>
    <li>右子树中的所有结点的元素都大于根结点的元素。</li>
</ol><br>
除二叉查找树基本性质外，AVL树还满足以下两条性质：<br>
<ol>
    <li>AVL树的左右子树都是AVL树。</li>
    <li>左右子树的高度差至多为1。</li>
</ol><br>
本AVL树采用头结点设计，头结点的左子结点为根结点。<br>
其每个结点有一个元素，一个平衡因子，左、右两个子结点，一个父结点。
*/
public class avl_tree
{
    /**
    <p>结点元素</p>
    */
    public int element;
    /**
    <p>平衡因子</p><br>
    平衡因子=右子树高度-左子树高度
    */
    public int balance_factor;
    /**
    <p>左子结点指针</p>
    */
    public avl_tree left;
    /**
    <p>右子结点指针</p>
    */
    public avl_tree right;
    /**
    <p>父结点指针</p>
    */
    public avl_tree parent;
    /**
    <p>结点构造方法</p><br>
    构造一个AVL树结点。
    @param element 元素。
    */
    public avl_tree(int element)
    {
        this.element=element;
        balance_factor=0;
        left=null;
        right=null;
        parent=null;
    }
    /**
    <p>无参构造方法</p><br>
    构造一个空AVL树。
    */
    public avl_tree()
    {
        element=0;
        balance_factor=0;
        left=null;
        right=null;
        parent=null;
    }
    /**
    <p>左旋AVL树</p><br>
    <p>此方法会修改调用对象。</p><br>
    左旋将原根结点的右子结点作为新根结点。<br>
    将原根结点右子树的左子结点作为原根结点的右子结点。<br>
    <code>O{L,R{rl,rr}}</code>-><code>R{O{L,rl},rr}</code>
    @param tree AVL树。
    @return 左旋后的AVL树根结点，即原树的右子结点。<br>
    若原树没有右子结点，则返回null。
    */
    public static avl_tree left_rotate(avl_tree tree)
    {
        if(tree==null||tree.right==null)
        {
            return null;
        }
        avl_tree root_parent=tree.parent;
        avl_tree right=tree.right;
        avl_tree right_left=right.left;
        tree.right=right_left;
        if(right_left!=null)
        {
            right_left.parent=tree;
        }
        right.left=tree;
        tree.parent=right;
        right.parent=root_parent;
        if(root_parent!=null)
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
    <p>右旋AVL树</p><br>
    <p>此方法会修改调用对象。</p><br>
    右旋将原根结点的左子结点作为新根结点。<br>
    将原根结点左子树的右子结点作为原根结点的左子结点。<br>
    <code>O{L{ll,lr},R}</code>-><code>L{ll,O{lr,R}}</code>
    @param tree AVL树。
    @return 右旋后的AVL树根结点，即原树的左子结点。<br>
    若原树没有左子结点，则返回null。
    */
    public static avl_tree right_rotate(avl_tree tree)
    {
        if(tree==null||tree.left==null)
        {
            return null;
        }
        avl_tree root_parent=tree.parent;
        avl_tree left=tree.left;
        avl_tree left_right=left.right;
        tree.left=left_right;
        if(left_right!=null)
        {
            left_right.parent=tree;
        }
        left.right=tree;
        tree.parent=left;
        left.parent=root_parent;
        if(root_parent!=null)
        {
            if(root_parent.right==tree)
            {
                root_parent.right=left;
            }
            else
            {
                root_parent.left=left;
            }
        }
        return left;
    }
    /**
    <p>构造方法</p><br>
    构造一个包含指定元素的AVL树。
    @param elements 多个元素。
    */
    public avl_tree(int... elements)
    {
        this.element=0;
        balance_factor=0;
        right=null;
        parent=null;
        left=new avl_tree(elements[0]);
        left.parent=this;
        select_elements:
        for(int element:elements)
        {
            avl_tree now=left;
            while(true)
            {
                if(element<now.element)
                {
                    if(now.left==null)
                    {
                        now.left=new avl_tree(element);
                        now.left.parent=now;
                        now.balance_factor--;
                        for(avl_tree parent=now.parent;parent!=this&&(now.balance_factor==1||now.balance_factor==-1);now=parent,parent=now.parent)
                        {
                            parent.balance_factor+=parent.right==now?1:-1;
                        }
                        if(now.balance_factor==0)
                        {
                            continue select_elements;
                        }
                        break;
                    }
                    now=now.left;
                }
                else if(element>now.element)
                {
                    if(now.right==null)
                    {
                        now.right=new avl_tree(element);
                        now.right.parent=now;
                        now.balance_factor++;
                        for(avl_tree parent=now.parent;parent!=this&&(now.balance_factor==1||now.balance_factor==-1);now=parent,parent=now.parent)
                        {
                            parent.balance_factor+=parent.right==now?1:-1;
                        }
                        if(now.balance_factor==0)
                        {
                            continue select_elements;
                        }
                        break;
                    }
                    now=now.right;
                }
                else
                {
                    continue select_elements;
                }
            }
            if(now.balance_factor>=2)
            {
                avl_tree right=now.right;
                if(right.balance_factor>=0)
                {
                    left_rotate(now);
                    now.balance_factor=0;
                    right.balance_factor=0;
                }
                else
                {
                    avl_tree right_left=right.left;
                    right_rotate(right);
                    left_rotate(now);
                    switch(right_left.balance_factor)
                    {
                        case 1->
                        {
                            now.balance_factor=-1;
                            right.balance_factor=0;
                        }
                        case 0->
                        {
                            now.balance_factor=0;
                            right.balance_factor=0;
                        }
                        case -1->
                        {
                            now.balance_factor=0;
                            right.balance_factor=1;
                        }
                    }
                    right_left.balance_factor=0;
                }
            }
            else if(now.balance_factor<=-2)
            {
                avl_tree left=now.left;
                if(left.balance_factor>0)
                {
                    avl_tree left_right=left.right;
                    left_rotate(left);
                    right_rotate(now);
                    switch(left_right.balance_factor)
                    {
                        case -1->
                        {
                            now.balance_factor=1;
                            left.balance_factor=0;
                        }
                        case 0->
                        {
                            now.balance_factor=0;
                            left.balance_factor=0;
                        }
                        case 1->
                        {
                            now.balance_factor=0;
                            left.balance_factor=-1;
                        }
                    }
                    left_right.balance_factor=0;
                }
                else
                {
                    right_rotate(now);
                    now.balance_factor=0;
                    left.balance_factor=0;
                }
            }
        }
    }
    /**
    <p>遍历</p><br>
    中序遍历AVL树。
    @return 中序遍历结果。
    */
    public int[] traversal()
    {
        avl_tree pins[]=new avl_tree[10];
        int pin=0,capacity=10;
        avl_tree now=left;
        int result[]=new int[10];
        int count=0,result_count=10;
        while(now!=null||pin>0)
        {
            for(;now!=null;now=now.left)
            {
                if(pin>=capacity)
                {
                    capacity=(capacity<<1)+2;
                    avl_tree new_pins[]=new avl_tree[capacity];
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
    向AVL树中插入一个元素。
    @param element 要插入的元素。
    @return 是否成功插入。
    */
    public boolean input(int element)
    {
        avl_tree now=left;
        if(now==null)
        {
            left=new avl_tree(element);
            left.parent=this;
            return true;
        }
        while(true)
        {
            if(element<now.element)
            {
                if(now.left==null)
                {
                    now.left=new avl_tree(element);
                    now.left.parent=now;
                    now.balance_factor--;
                    for(avl_tree parent=now.parent;parent!=this&&(now.balance_factor==1||now.balance_factor==-1);now=parent,parent=now.parent)
                    {
                        parent.balance_factor+=parent.right==now?1:-1;
                    }
                    if(now.balance_factor==0)
                    {
                        return true;
                    }
                    break;
                }
                now=now.left;
            }
            else if(element>now.element)
            {
                if(now.right==null)
                {
                    now.right=new avl_tree(element);
                    now.right.parent=now;
                    now.balance_factor++;
                    for(avl_tree parent=now.parent;parent!=this&&(now.balance_factor==1||now.balance_factor==-1);now=parent,parent=now.parent)
                    {
                        parent.balance_factor+=parent.right==now?1:-1;
                    }
                    if(now.balance_factor==0)
                    {
                        return true;
                    }
                    break;
                }
                now=now.right;
            }
            else
            {
                return false;
            }
        }
        if(now.balance_factor>=2)
        {
            avl_tree right=now.right;
            if(right.balance_factor>=0)
            {
                left_rotate(now);
                now.balance_factor=0;
                right.balance_factor=0;
            }
            else
            {
                avl_tree right_left=right.left;
                right_rotate(right);
                left_rotate(now);
                switch(right_left.balance_factor)
                {
                    case 1->
                    {
                        now.balance_factor=-1;
                        right.balance_factor=0;
                    }
                    case 0->
                    {
                        now.balance_factor=0;
                        right.balance_factor=0;
                    }
                    case -1->
                    {
                        now.balance_factor=0;
                        right.balance_factor=1;
                    }
                }
                right_left.balance_factor=0;
            }
        }
        else if(now.balance_factor<=-2)
        {
            avl_tree left=now.left;
            if(left.balance_factor>0)
            {
                avl_tree left_right=left.right;
                left_rotate(left);
                right_rotate(now);
                switch(left_right.balance_factor)
                {
                    case -1->
                    {
                        now.balance_factor=1;
                        left.balance_factor=0;
                    }
                    case 0->
                    {
                        now.balance_factor=0;
                        left.balance_factor=0;
                    }
                    case 1->
                    {
                        now.balance_factor=0;
                        left.balance_factor=-1;
                    }
                }
                left_right.balance_factor=0;
            }
            else
            {
                right_rotate(now);
                now.balance_factor=0;
                left.balance_factor=0;
            }
        }
        return true;
    }
    /**
    <p>元素批量输入</p><br>
    <p>此方法会修改调用对象。</p><br>
    向AVL树中插入多个元素。<br>
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
            avl_tree now=left;
            if(now==null)
            {
                left=new avl_tree(element);
                left.parent=this;
                continue;
            }
            while(true)
            {
                if(element<now.element)
                {
                    if(now.left==null)
                    {
                        now.left=new avl_tree(element);
                        now.left.parent=now;
                        now.balance_factor--;
                        for(avl_tree parent=now.parent;parent!=this&&(now.balance_factor==1||now.balance_factor==-1);now=parent,parent=now.parent)
                        {
                            parent.balance_factor+=parent.right==now?1:-1;
                        }
                        if(now.balance_factor==0)
                        {
                            continue select_elements;
                        }
                        break;
                    }
                    now=now.left;
                }
                else if(element>now.element)
                {
                    if(now.right==null)
                    {
                        now.right=new avl_tree(element);
                        now.right.parent=now;
                        now.balance_factor++;
                        for(avl_tree parent=now.parent;parent!=this&&(now.balance_factor==1||now.balance_factor==-1);now=parent,parent=now.parent)
                        {
                            parent.balance_factor+=parent.right==now?1:-1;
                        }
                        if(now.balance_factor==0)
                        {
                            continue select_elements;
                        }
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
            if(now.balance_factor>=2)
            {
                avl_tree right=now.right;
                if(right.balance_factor>=0)
                {
                    left_rotate(now);
                    now.balance_factor=0;
                    right.balance_factor=0;
                }
                else
                {
                    avl_tree right_left=right.left;
                    right_rotate(right);
                    left_rotate(now);
                    switch(right_left.balance_factor)
                    {
                        case 1->
                        {
                            now.balance_factor=-1;
                            right.balance_factor=0;
                        }
                        case 0->
                        {
                            now.balance_factor=0;
                            right.balance_factor=0;
                        }
                        case -1->
                        {
                            now.balance_factor=0;
                            right.balance_factor=1;
                        }
                    }
                    right_left.balance_factor=0;
                }
            }
            else if(now.balance_factor<=-2)
            {
                avl_tree left=now.left;
                if(left.balance_factor>0)
                {
                    avl_tree left_right=left.right;
                    left_rotate(left);
                    right_rotate(now);
                    switch(left_right.balance_factor)
                    {
                        case -1->
                        {
                            now.balance_factor=1;
                            left.balance_factor=0;
                        }
                        case 0->
                        {
                            now.balance_factor=0;
                            left.balance_factor=0;
                        }
                        case 1->
                        {
                            now.balance_factor=0;
                            left.balance_factor=-1;
                        }
                    }
                    left_right.balance_factor=0;
                }
                else
                {
                    right_rotate(now);
                    now.balance_factor=0;
                    left.balance_factor=0;
                }
            }
        }
        return duplicate;
    }
    /**
    <p>元素深度计算</p><br>
    获取AVL树中元素的深度。
    @param element 要获取深度的元素。
    @return 元素的深度。<br>
    若元素不存在，则返回<code>Integer.MIN_VALUE</code>。
    */
    public int get_depth(int element)
    {
        avl_tree now=left;
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
    从AVL树中删除一个元素。
    @param element 要删除的元素。
    @return 是否成功删除。
    */
    public boolean remove(int element)
    {
        avl_tree now=left;
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
            avl_tree next=now.right;
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
        }
        for(now=now.parent;now!=this;now_is_left=now==now.parent.left,now=now.parent)
        {
            now.balance_factor-=now_is_left?-1:1;
            if(now.balance_factor>=2)
            {
                avl_tree right=now.right;
                if(right.balance_factor>=0)
                {
                    left_rotate(now);
                    switch(right.balance_factor)
                    {
                        case 1->
                        {
                            now.balance_factor=0;
                            right.balance_factor=0;
                        }
                        case 0->
                        {
                            now.balance_factor=1;
                            right.balance_factor=-1;
                        }
                    }
                }
                else
                {
                    avl_tree right_left=right.left;
                    left_rotate(right);
                    right_rotate(now);
                    switch(right_left.balance_factor)
                    {
                        case 1->
                        {
                            now.balance_factor=-1;
                            left.balance_factor=0;
                        }
                        case 0->
                        {
                            now.balance_factor=0;
                            left.balance_factor=0;
                        }
                        case -1->
                        {
                            now.balance_factor=0;
                            left.balance_factor=1;
                        }
                    }
                    right_left.balance_factor=0;
                }
            }
            else if(now.balance_factor<=-2)
            {
                avl_tree left=now.left;
                if(left.balance_factor>0)
                {
                    avl_tree left_right=left.right;
                    left_rotate(left);
                    right_rotate(now);
                    switch(left_right.balance_factor)
                    {
                        case -1->
                        {
                            now.balance_factor=1;
                            left.balance_factor=0;
                        }
                        case 0->
                        {
                            now.balance_factor=0;
                            left.balance_factor=0;
                        }
                        case 1->
                        {
                            now.balance_factor=0;
                            left.balance_factor=-1;
                        }
                    }
                    left_right.balance_factor=0;
                }
                else
                {
                    right_rotate(now);
                    switch(left.balance_factor)
                    {
                        case -1->
                        {
                            now.balance_factor=0;
                            left.balance_factor=0;
                        }
                        case 0->
                        {
                            now.balance_factor=-1;
                            left.balance_factor=1;
                        }
                    }
                }
            }
            else if(now.balance_factor==1||now.balance_factor==-1)
            {
                break;
            }
        }
        return true;
    }
    /**
    <p>字符串表示</p><br>
    @return AVL树的字符串表示。
    */
    public String toString()
    {
        if(parent==null&&left==null)
        {
            return "";
        }
        avl_tree pins[]=new avl_tree[10];
        int pin=0,capacity=10;
        pins[0]=parent==null?left:this;
        StringBuilder result=new StringBuilder("");
        while(pin>=0)
        {
            result.append(""+pins[pin].element);
            if(pins[pin].left!=null)
            {
                if(pin+1>=capacity)
                {
                    capacity=(capacity<<1)+2;
                    avl_tree new_pins[]=new avl_tree[capacity];
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
                    avl_tree new_pins[]=new avl_tree[capacity];
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