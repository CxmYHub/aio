package tools.data_structure;
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
    <p>构造方法</p><br>
    构造一个包含指定元素的二叉查找树。
    @param elements 多个元素。
    */
    public avl_tree(int... elements)
    {
        this.element=0;
        left=null;
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
                        now=now.left;
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
            
        }
    }
}