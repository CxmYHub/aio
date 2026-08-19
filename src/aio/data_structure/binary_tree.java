package aio.data_structure;
/**
<p>二叉树类</p><br>
二叉树是一种特殊的树状数据结构。<br>
注意二叉树不是树。<br>
本二叉树的每个结点有左、右两个子结点。<br>
其中：
<ul>
    <li>没有子结点的结点称为叶结点。</li>
    <li>没有父结点的结点称为根结点。</li>
</ul>
*/
public class binary_tree
{
    /**
    <p>结点元素</p>
    */
    public int element;
    /**
    <p>左子树指针</p>
    */
    public binary_tree left;
    /**
    <p>右子树指针</p>
    */
    public binary_tree right;
    /**
    <p>结点构造方法</p><br>
    构造一个包含指定元素的二叉树结点。
    @param element 结点元素。
    */
    private binary_tree(int element)
    {
        this.element=element;
    }
    /**
    <p>构造方法</p><br>
    通过二叉树字符串构造一个二叉树。<br>
    若传入的结点元素为字符，则存储其ASCII码值。
    @param tree_string 二叉树字符串。<br>
    二叉树字符串的格式为：<code>根结点{左子树,右子树}...</code>。<br>
    例如：<code>A{B{D,E},C{F{H},G{,I}}}</code>。
    */
    public binary_tree(String tree_string)
    {
        char tree_chars[]=tree_string.toCharArray();
        binary_tree pins[]=new binary_tree[10];
        int pin=0,capacity=10;
        pins[0]=this;
        int this_element=0,i=0;
        boolean is_right=false;
        for(;i<tree_chars.length;i++)
        {
            if(tree_chars[i]>='0'&&tree_chars[i]<='9')
            {
                element*=10;
                element+=tree_chars[i]-'0';
            }
            else if(tree_chars[i]>='A'&&tree_chars[i]<='Z'||tree_chars[i]>='a'&&tree_chars[i]<='z')
            {
                element=tree_chars[i];
            }
            else
            {
                break;
            }
        }
        for(i++;i<tree_chars.length;i++)
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
                if(tree_chars[i-1]!='{'&&tree_chars[i-1]!='}')
                {
                    if(is_right)
                    {
                        pins[pin].right=new binary_tree(this_element);
                    }
                    else
                    {
                        pins[pin].left=new binary_tree(this_element);
                    }
                    this_element=0;
                }
                is_right=true;
            }
            else if(tree_chars[i]=='{')
            {
                if(pin+1>=capacity)
                {
                    capacity=(capacity<<1)+2;
                    binary_tree new_pins[]=new binary_tree[capacity];
                    System.arraycopy(pins,0,new_pins,0,pin+1);
                    pins=new_pins;
                }
                if(is_right)
                {
                    pins[pin].right=new binary_tree(this_element);
                    pins[pin+1]=pins[pin].right;
                }
                else
                {
                    pins[pin].left=new binary_tree(this_element);
                    pins[pin+1]=pins[pin].left;
                }
                this_element=0;
                is_right=false;
                pin++;
            }
            else if(tree_chars[i]=='}')
            {
                if(tree_chars[i-1]!='}')
                {
                    if(is_right)
                    {
                        pins[pin].right=new binary_tree(this_element);
                    }
                    else
                    {
                        pins[pin].left=new binary_tree(this_element);
                    }
                    this_element=0;
                }
                pin--;
            }
        }
    }
    /**
    <p>构造方法</p><br>
    通过先序遍历序列和中序遍历序列构造一个二叉树。
    @param preorder 先序遍历序列。
    @param inorder 中序遍历序列。
    */
    public binary_tree(int preorder[],int inorder[])
    {
        if(preorder==null||preorder.length==0)
        {
            element=0;
        }
        else
        {
            element=preorder[0];
            int length=preorder.length;
            binary_tree pins[]=new binary_tree[length];
            int pin=0;
            pins[0]=this;
            int inorder_index=0;
            for(int i=1;i<length;i++)
            {
                binary_tree now=pins[pin];
                if(now.element!=inorder[inorder_index])
                {
                    now.left=new binary_tree(preorder[i]);
                    pins[++pin]=now.left;
                }
                else
                {
                    while(pin>=0&&pins[pin].element==inorder[inorder_index])
                    {
                        now=pins[pin--];
                        inorder_index++;
                    }
                    now.right=new binary_tree(preorder[i]);
                    pins[++pin]=now.right;
                }
            }
        }
    }
    /**
    <p>构造方法</p><br>
    通过中序遍历序列和后序遍历序列构造一个二叉树。
    @param inorder 中序遍历序列。
    @param postorder 后序遍历序列。
    @param use_inorder_postorder 哑元，代表使用中序遍历序列和后序遍历序列构造二叉树。
    */
    public binary_tree(int inorder[],int postorder[],int use_inorder_postorder)
    {
        if(inorder==null||inorder.length==0)
        {
            element=0;
        }
        else
        {
            int length=inorder.length;
            element=postorder[length-1];
            binary_tree pins[]=new binary_tree[length];
            int pin=0;
            pins[0]=this;
            int inorder_index=length-1;
            for(int i=length-2;i>=0;i--)
            {
                binary_tree now=pins[pin];
                if(now.element!=inorder[inorder_index])
                {
                    now.right=new binary_tree(postorder[i]);
                    pins[++pin]=now.right;
                }
                else
                {
                    while(pin>=0&&pins[pin].element==inorder[inorder_index])
                    {
                        now=pins[pin--];
                        inorder_index--;
                    }
                    now.left=new binary_tree(postorder[i]);
                    pins[++pin]=now.left;
                }
            }
        }
    }
    /**
    <p>结点计数</p><br>
    计算二叉树的结点数。
    @return 二叉树的结点数。
    */
    public int count()
    {
        binary_tree pins[]=new binary_tree[10];
        int pin=1,capacity=10;
        pins[0]=this;
        int count=0;
        while(pin>0)
        {
            binary_tree now=pins[--pin];
            count++;
            if(now.left!=null)
            {
                pins[pin++]=now.left;
            }
            if(now.right!=null)
            {
                if(pin>=capacity)
                {
                    capacity=(capacity<<1)+2;
                    binary_tree new_pins[]=new binary_tree[capacity];
                    System.arraycopy(pins,0,new_pins,0,pin);
                    pins=new_pins;
                }
                pins[pin++]=now.right;
            }
        }
        return count;
    }
    /**
    <p>树深度计算</p><br>
    计算二叉树的深度。
    @return 二叉树的深度。
    */
    public int depth()
    {
        binary_tree pins[]=new binary_tree[10];
        int front=0,rear=1,capacity=10;
        boolean overturn=false;
        pins[0]=this;
        int level_size=1;
        int depth=0;
        while(level_size>0)
        {
            depth++;
            binary_tree now;
            int next_level_size=0;
            for(;level_size>0;level_size--)
            {
                now=pins[front++];
                if(front>=capacity)
                {
                    front=0;
                    overturn=false;
                }
                if(now.left!=null)
                {
                    if(front==rear&&overturn)
                    {
                        binary_tree new_pins[]=new binary_tree[(capacity<<1)+2];
                        System.arraycopy(pins,front,new_pins,0,capacity-front);
                        System.arraycopy(pins,0,new_pins,capacity-front,rear);
                        pins=new_pins;
                        front=0;
                        rear=capacity;
                        capacity=(capacity<<1)+2;
                        overturn=false;
                    }
                    pins[rear++]=now.left;
                    next_level_size++;
                    if(rear>=capacity)
                    {
                        rear=0;
                        overturn=true;
                    }
                }
                if(now.right!=null)
                {
                    if(front==rear&&overturn)
                    {
                        binary_tree new_pins[]=new binary_tree[(capacity<<1)+2];
                        System.arraycopy(pins,front,new_pins,0,capacity-front);
                        System.arraycopy(pins,0,new_pins,capacity-front,rear);
                        pins=new_pins;
                        front=0;
                        rear=capacity;
                        capacity=(capacity<<1)+2;
                        overturn=false;
                    }
                    pins[rear++]=now.right;
                    next_level_size++;
                    if(rear>=capacity)
                    {
                        rear=0;
                        overturn=true;
                    }
                }
            }
            level_size=next_level_size;
        }
        return depth;
    }
    /**
    <p>先序遍历</p><br>
    先序遍历二叉树。
    @return 先序遍历结果。
    */
    public int[] traversal_preorder()
    {
        binary_tree pins[]=new binary_tree[10];
        int pin=1,capacity=10;
        pins[0]=this;
        int result[]=new int[10];
        int count=0,result_count=10;
        while(pin>0)
        {
            binary_tree now=pins[--pin];
            if(count>=result_count)
            {
                result_count=(result_count<<1)+2;
                int new_result[]=new int[result_count];
                System.arraycopy(result,0,new_result,0,count);
                result=new_result;
            }
            result[count++]=now.element;
            if(now.right!=null)
            {
                pins[pin++]=now.right;
            }
            if(now.left!=null)
            {
                if(pin>=capacity)
                {
                    capacity=(capacity<<1)+2;
                    binary_tree new_pins[]=new binary_tree[capacity];
                    System.arraycopy(pins,0,new_pins,0,pin);
                    pins=new_pins;
                }
                pins[pin++]=now.left;
            }
        }
        if(count<result_count)
        {
            int new_result[]=new int[count];
            System.arraycopy(result,0,new_result,0,count);
            result=new_result;
        }
        return result;
    }
    /**
    <p>中序遍历</p><br>
    中序遍历二叉树。
    @return 中序遍历结果。
    */
    public int[] traversal_inorder()
    {
        binary_tree pins[]=new binary_tree[10];
        int pin=0,capacity=10;
        binary_tree now=this;
        int result[]=new int[10];
        int count=0,result_count=10;
        while(now!=null||pin>0)
        {
            for(;now!=null;now=now.left)
            {
                if(pin>=capacity)
                {
                    capacity=(capacity<<1)+2;
                    binary_tree new_pins[]=new binary_tree[capacity];
                    System.arraycopy(pins,0,new_pins,0,pin);
                    pins=new_pins;
                }
                pins[pin++]=now;
            }
            now=pins[--pin];
            if(count>=result_count)
            {
                result_count=(result_count<<1)+2;
                int new_result[]=new int[result_count];
                System.arraycopy(result,0,new_result,0,count);
                result=new_result;
            }
            result[count++]=now.element;
            now=now.right;
        }
        if(count<result_count)
        {
            int new_result[]=new int[count];
            System.arraycopy(result,0,new_result,0,count);
            result=new_result;
        }
        return result;
    }
    /**
    <p>后序遍历</p><br>
    后序遍历二叉树。
    @return 后序遍历结果。
    */
    public int[] traversal_postorder()
    {
        binary_tree pins[]=new binary_tree[10];
        int pin=0,capacity=10;
        binary_tree now=this;
        binary_tree last=null;
        int result[]=new int[10];
        int count=0,result_count=10;
        while(now!=null||pin>0)
        {
            for(;now!=null;now=now.left)
            {
                if(pin>=capacity)
                {
                    capacity=(capacity<<1)+2;
                    binary_tree new_pins[]=new binary_tree[capacity];
                    System.arraycopy(pins,0,new_pins,0,pin);
                    pins=new_pins;
                }
                pins[pin++]=now;
            }
            binary_tree top=pins[pin-1];
            if(top.right!=null&&top.right!=last)
            {
                now=top.right;
            }
            else
            {
                if(count>=result_count)
                {
                    result_count=(result_count<<1)+2;
                    int new_result[]=new int[result_count];
                    System.arraycopy(result,0,new_result,0,count);
                    result=new_result;
                }
                result[count++]=top.element;
                last=top;
                pin--;
            }
        }
        if(count<result_count)
        {
            int new_result[]=new int[count];
            System.arraycopy(result,0,new_result,0,count);
            result=new_result;
        }
        return result;
    }
    /**
    <p>层序遍历</p><br>
    层序遍历二叉树。
    @return 层序遍历结果。
    */
    public int[] traversal_levelorder()
    {
        binary_tree pins[]=new binary_tree[10];
        int front=0,rear=1,capacity=10;
        boolean overturn=false;
        pins[0]=this;
        int result[]=new int[10];
        int count=0,result_count=10;
        while(front!=rear||overturn)
        {
            binary_tree now=pins[front++];
            if(front>=capacity)
            {
                front=0;
                overturn=false;
            }
            if(count>=result_count)
            {
                result_count=(result_count<<1)+2;
                int new_result[]=new int[result_count];
                System.arraycopy(result,0,new_result,0,count);
                result=new_result;
            }
            result[count++]=now.element;
            if(now.left!=null)
            {
                pins[rear++]=now.left;
                if(rear>=capacity)
                {
                    rear=0;
                    overturn=true;
                }
            }
            if(now.right!=null)
            {
                if(front==rear&&overturn)
                {
                    binary_tree new_pins[]=new binary_tree[(capacity<<1)+2];
                    System.arraycopy(pins,front,new_pins,0,capacity-front);
                    System.arraycopy(pins,0,new_pins,capacity-front,rear);
                    pins=new_pins;
                    front=0;
                    rear=capacity;
                    capacity=(capacity<<1)+2;
                    overturn=false;
                }
                pins[rear++]=now.right;
                if(rear>=capacity)
                {
                    rear=0;
                    overturn=true;
                }
            }
        }
        if(count<result_count)
        {
            int new_result[]=new int[count];
            System.arraycopy(result,0,new_result,0,count);
            result=new_result;
        }
        return result;
    }
    /**
    <p>元素输入</p><br>
    <p>此方法会修改调用对象。</p><br>
    向二叉树中插入一个元素。
    @param element 要插入的元素。
    @return 插入的元素的父结点元素。
    */
    public int input(int element)
    {
        binary_tree pins[]=new binary_tree[10];
        int pin=1,capacity=10;
        pins[0]=this;
        binary_tree now;
        while(true)
        {
            now=pins[--pin];
            if(now.left!=null)
            {
                pins[pin++]=now.left;
            }
            else
            {
                now.left=new binary_tree(element);
                break;
            }
            if(now.right!=null)
            {
                if(pin>=capacity)
                {
                    capacity=(capacity<<1)+2;
                    binary_tree new_pins[]=new binary_tree[capacity];
                    System.arraycopy(pins,0,new_pins,0,pin);
                    pins=new_pins;
                }
                pins[pin++]=now.right;
            }
            else
            {
                now.right=new binary_tree(element);
                break;
            }
        }
        return now.element;
    }
    /**
    <p>子树删除（单个匹配）</p><br>
    <p>此方法会修改调用对象。</p><br>
    从二叉树中删除一个元素，及其所有子树。<br>
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
            left=null;
            right=null;
            return element;
        }
        binary_tree pins[]=new binary_tree[10];
        int pin=1,capacity=10;
        pins[0]=this;
        while(pin>0)
        {
            binary_tree now=pins[--pin];
            if(now.right!=null)
            {
                if(now.right.element==element)
                {
                    now.right=null;
                    return element;
                }
                else
                {
                    pins[pin++]=now.right;
                }
            }
            if(now.left!=null)
            {
                if(now.left.element==element)
                {
                    now.left=null;
                    return element;
                }
                else
                {
                    if(pin>=capacity)
                    {
                        capacity=(capacity<<1)+2;
                        binary_tree new_pins[]=new binary_tree[capacity];
                        System.arraycopy(pins,0,new_pins,0,pin);
                        pins=new_pins;
                    }
                    pins[pin++]=now.left;
                }
            }
        }
        return Integer.MIN_VALUE;
    }
    /**
    <p>镜像二叉树</p><br>
    <p>此方法会修改调用对象。</p><br>
    将二叉树中所有结点的左右子树对调。
    */
    public void invert()
    {
        binary_tree pins[]=new binary_tree[10];
        int pin=1,capacity=10;
        pins[0]=this;
        while(pin>0)
        {
            binary_tree now=pins[--pin];
            binary_tree temp=now.left;
            now.left=now.right;
            now.right=temp;
            if(now.left!=null)
            {
                pins[pin++]=now.left;
            }
            if(now.right!=null)
            {
                if(pin>=capacity)
                {
                    capacity=(capacity<<1)+2;
                    binary_tree new_pins[]=new binary_tree[capacity];
                    System.arraycopy(pins,0,new_pins,0,pin);
                    pins=new_pins;
                }
                pins[pin++]=now.right;
            }
        }
    }
    /**
    <p>字符串表示</p><br>
    @return 二叉树的字符串表示。
    */
    public String toString()
    {
        binary_tree pins[]=new binary_tree[10];
        int pin=0,capacity=10;
        pins[0]=this;
        StringBuilder result=new StringBuilder("");
        while(pin>=0)
        {
            result.append(pins[pin].element);
            if(pins[pin].left!=null)
            {
                if(pin+1>=capacity)
                {
                    capacity=(capacity<<1)+2;
                    binary_tree new_pins[]=new binary_tree[capacity];
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
                    binary_tree new_pins[]=new binary_tree[capacity];
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
    /**
    <p>相等判断</p><br>
    判断两个二叉树是否相同。
    @param another_tree 要比较的二叉树。
    @return 是否相同。
    */
    public boolean equals(Object another_tree)
    {
        if(another_tree==null||!(another_tree instanceof binary_tree))
        {
            return false;
        }
        binary_tree tree=(binary_tree)another_tree;
        int this_depth=depth();
        int tree_depth=tree.depth();
        int this_count=count();
        int tree_count=tree.count();
        if(this_depth!=tree_depth||this_count!=tree_count)
        {
            return false;
        }
        binary_tree pins1[]=new binary_tree[this_count];
        binary_tree pins2[]=new binary_tree[this_count];
        pins1[0]=this;
        pins2[0]=tree;
        int pin1=1,pin2=1;
        while(pin1>0&&pin2>0)
        {
            binary_tree tree1=pins1[--pin1];
            binary_tree tree2=pins2[--pin2];
            if(tree1.element!=tree2.element)
            {
                return false;
            }
            if(tree1.left!=null&&tree2.left!=null)
            {
                pins1[pin1++]=tree1.left;
                pins2[pin2++]=tree2.left;
            }
            else if(tree1.left==null&&tree2.left==null)
            {
            }
            else
            {
                return false;
            }
            if(tree1.right!=null&&tree2.right!=null)
            {
                pins1[pin1++]=tree1.right;
                pins2[pin2++]=tree2.right;
            }
            else if(tree1.right==null&&tree2.right==null)
            {
            }
            else
            {
                return false;
            }
        }
        return true;
    }
}