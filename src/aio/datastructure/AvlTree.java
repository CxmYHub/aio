package aio.datastructure;
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
public class AvlTree {
    /**
    <p>结点元素</p>
    */
    public int element;
    /**
    <p>平衡因子</p><br>
    平衡因子=右子树高度-左子树高度
    */
    public int balanceFactor;
    /**
    <p>左子结点指针</p>
    */
    public AvlTree left;
    /**
    <p>右子结点指针</p>
    */
    public AvlTree right;
    /**
    <p>父结点指针</p>
    */
    public AvlTree parent;
    /**
    <p>结点构造方法</p><br>
    构造一个AVL树结点。
    @param element 元素。
    @param node 哑元，用于区分方法。
    */
    public AvlTree(int element,char node) {
        this.element=element;
        balanceFactor=0;
        left=null;
        right=null;
        parent=null;
    }
    /**
    <p>无参构造方法</p><br>
    构造一个空AVL树。
    */
    public AvlTree() {
        element=0;
        balanceFactor=0;
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
    public static AvlTree leftRotate(AvlTree tree) {
        if(tree==null||tree.right==null) {
            return null;
        }
        AvlTree rootParent=tree.parent;
        AvlTree right=tree.right;
        AvlTree rightLeft=right.left;
        tree.right=rightLeft;
        if(rightLeft!=null) {
            rightLeft.parent=tree;
        }
        right.left=tree;
        tree.parent=right;
        right.parent=rootParent;
        if(rootParent!=null) {
            if(rootParent.left==tree) {
                rootParent.left=right;
            } else {
                rootParent.right=right;
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
    public static AvlTree rightRotate(AvlTree tree) {
        if(tree==null||tree.left==null) {
            return null;
        }
        AvlTree rootParent=tree.parent;
        AvlTree left=tree.left;
        AvlTree leftRight=left.right;
        tree.left=leftRight;
        if(leftRight!=null) {
            leftRight.parent=tree;
        }
        left.right=tree;
        tree.parent=left;
        left.parent=rootParent;
        if(rootParent!=null) {
            if(rootParent.right==tree) {
                rootParent.right=left;
            } else {
                rootParent.left=left;
            }
        }
        return left;
    }
    /**
    <p>构造方法</p><br>
    构造一个包含指定元素的AVL树。
    @param elements 多个元素。
    */
    public AvlTree(int... elements) {
        this.element=0;
        balanceFactor=0;
        right=null;
        parent=null;
        left=new AvlTree(elements[0],' ');
        left.parent=this;
        selectElements:
        for(int element:elements) {
            AvlTree now=left;
            while(true) {
                if(element<now.element) {
                    if(now.left==null) {
                        now.left=new AvlTree(element,' ');
                        now.left.parent=now;
                        now.balanceFactor--;
                        for(AvlTree parent=now.parent;parent!=this&&(now.balanceFactor==1||now.balanceFactor==-1);now=parent,parent=now.parent) {
                            parent.balanceFactor+=parent.right==now?1:-1;
                        }
                        if(now.balanceFactor==0) {
                            continue selectElements;
                        }
                        break;
                    }
                    now=now.left;
                } else if(element>now.element) {
                    if(now.right==null) {
                        now.right=new AvlTree(element,' ');
                        now.right.parent=now;
                        now.balanceFactor++;
                        for(AvlTree parent=now.parent;parent!=this&&(now.balanceFactor==1||now.balanceFactor==-1);now=parent,parent=now.parent) {
                            parent.balanceFactor+=parent.right==now?1:-1;
                        }
                        if(now.balanceFactor==0) {
                            continue selectElements;
                        }
                        break;
                    }
                    now=now.right;
                } else {
                    continue selectElements;
                }
            }
            if(now.balanceFactor>=2) {
                AvlTree right=now.right;
                if(right.balanceFactor>=0) {
                    leftRotate(now);
                    now.balanceFactor=0;
                    right.balanceFactor=0;
                } else {
                    AvlTree rightLeft=right.left;
                    rightRotate(right);
                    leftRotate(now);
                    switch(rightLeft.balanceFactor) {
                        case 1-> {
                            now.balanceFactor=-1;
                            right.balanceFactor=0;
                        }
                        case 0-> {
                            now.balanceFactor=0;
                            right.balanceFactor=0;
                        }
                        case -1-> {
                            now.balanceFactor=0;
                            right.balanceFactor=1;
                        }
                    }
                    rightLeft.balanceFactor=0;
                }
            } else if(now.balanceFactor<=-2) {
                AvlTree left=now.left;
                if(left.balanceFactor>0) {
                    AvlTree leftRight=left.right;
                    leftRotate(left);
                    rightRotate(now);
                    switch(leftRight.balanceFactor) {
                        case -1-> {
                            now.balanceFactor=1;
                            left.balanceFactor=0;
                        }
                        case 0-> {
                            now.balanceFactor=0;
                            left.balanceFactor=0;
                        }
                        case 1-> {
                            now.balanceFactor=0;
                            left.balanceFactor=-1;
                        }
                    }
                    leftRight.balanceFactor=0;
                } else {
                    rightRotate(now);
                    now.balanceFactor=0;
                    left.balanceFactor=0;
                }
            }
        }
    }
    /**
    <p>遍历</p><br>
    中序遍历AVL树。
    @return 中序遍历结果。
    */
    public int[] traversal() {
        AvlTree pins[]=new AvlTree[10];
        int pin=0,capacity=10;
        AvlTree now=left;
        int result[]=new int[10];
        int count=0,resultCapacity=10;
        while(now!=null||pin>0) {
            for(;now!=null;now=now.left) {
                if(pin>=capacity) {
                    capacity=(capacity<<1)+2;
                    AvlTree newPins[]=new AvlTree[capacity];
                    System.arraycopy(pins,0,newPins,0,pin);
                    pins=newPins;
                }
                pins[pin++]=now;
            }
            now=pins[--pin];
            if(count>=resultCapacity) {
                resultCapacity=resultCapacity*2+2;
                int newResult[]=new int[resultCapacity];
                for(int i=0;i<count;i++) {
                    newResult[i]=result[i];
                }
                result=newResult;
            }
            result[count++]=now.element;
            now=now.right;
        }
        if(count<resultCapacity) {
            int newResult[]=new int[count];
            for(int i=0;i<count;i++) {
                newResult[i]=result[i];
            }
            result=newResult;
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
    public boolean input(int element) {
        AvlTree now=left;
        if(now==null) {
            left=new AvlTree(element,' ');
            left.parent=this;
            return true;
        }
        while(true) {
            if(element<now.element) {
                if(now.left==null) {
                    now.left=new AvlTree(element,' ');
                    now.left.parent=now;
                    now.balanceFactor--;
                    for(AvlTree parent=now.parent;parent!=this&&(now.balanceFactor==1||now.balanceFactor==-1);now=parent,parent=now.parent) {
                        parent.balanceFactor+=parent.right==now?1:-1;
                    }
                    if(now.balanceFactor==0) {
                        return true;
                    }
                    break;
                }
                now=now.left;
            } else if(element>now.element) {
                if(now.right==null) {
                    now.right=new AvlTree(element,' ');
                    now.right.parent=now;
                    now.balanceFactor++;
                    for(AvlTree parent=now.parent;parent!=this&&(now.balanceFactor==1||now.balanceFactor==-1);now=parent,parent=now.parent) {
                        parent.balanceFactor+=parent.right==now?1:-1;
                    }
                    if(now.balanceFactor==0) {
                        return true;
                    }
                    break;
                }
                now=now.right;
            } else {
                return false;
            }
        }
        if(now.balanceFactor>=2) {
            AvlTree right=now.right;
            if(right.balanceFactor>=0) {
                leftRotate(now);
                now.balanceFactor=0;
                right.balanceFactor=0;
            } else {
                AvlTree rightLeft=right.left;
                rightRotate(right);
                leftRotate(now);
                switch(rightLeft.balanceFactor) {
                    case 1-> {
                        now.balanceFactor=-1;
                        right.balanceFactor=0;
                    }
                    case 0-> {
                        now.balanceFactor=0;
                        right.balanceFactor=0;
                    }
                    case -1-> {
                        now.balanceFactor=0;
                        right.balanceFactor=1;
                    }
                }
                rightLeft.balanceFactor=0;
            }
        } else if(now.balanceFactor<=-2) {
            AvlTree left=now.left;
            if(left.balanceFactor>0) {
                AvlTree leftRight=left.right;
                leftRotate(left);
                rightRotate(now);
                switch(leftRight.balanceFactor) {
                    case -1-> {
                        now.balanceFactor=1;
                        left.balanceFactor=0;
                    }
                    case 0-> {
                        now.balanceFactor=0;
                        left.balanceFactor=0;
                    }
                    case 1-> {
                        now.balanceFactor=0;
                        left.balanceFactor=-1;
                    }
                }
                leftRight.balanceFactor=0;
            } else {
                rightRotate(now);
                now.balanceFactor=0;
                left.balanceFactor=0;
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
    public int inputMore(int... elements) {
        int duplicate=0;
        selectElements:
        for(int element:elements) {
            AvlTree now=left;
            if(now==null) {
                left=new AvlTree(element,' ');
                left.parent=this;
                continue;
            }
            while(true) {
                if(element<now.element) {
                    if(now.left==null) {
                        now.left=new AvlTree(element,' ');
                        now.left.parent=now;
                        now.balanceFactor--;
                        for(AvlTree parent=now.parent;parent!=this&&(now.balanceFactor==1||now.balanceFactor==-1);now=parent,parent=now.parent) {
                            parent.balanceFactor+=parent.right==now?1:-1;
                        }
                        if(now.balanceFactor==0) {
                            continue selectElements;
                        }
                        break;
                    }
                    now=now.left;
                } else if(element>now.element) {
                    if(now.right==null) {
                        now.right=new AvlTree(element,' ');
                        now.right.parent=now;
                        now.balanceFactor++;
                        for(AvlTree parent=now.parent;parent!=this&&(now.balanceFactor==1||now.balanceFactor==-1);now=parent,parent=now.parent) {
                            parent.balanceFactor+=parent.right==now?1:-1;
                        }
                        if(now.balanceFactor==0) {
                            continue selectElements;
                        }
                        break;
                    }
                    now=now.right;
                } else {
                    duplicate++;
                    continue selectElements;
                }
            }
            if(now.balanceFactor>=2) {
                AvlTree right=now.right;
                if(right.balanceFactor>=0) {
                    leftRotate(now);
                    now.balanceFactor=0;
                    right.balanceFactor=0;
                } else {
                    AvlTree rightLeft=right.left;
                    rightRotate(right);
                    leftRotate(now);
                    switch(rightLeft.balanceFactor) {
                        case 1-> {
                            now.balanceFactor=-1;
                            right.balanceFactor=0;
                        }
                        case 0-> {
                            now.balanceFactor=0;
                            right.balanceFactor=0;
                        }
                        case -1-> {
                            now.balanceFactor=0;
                            right.balanceFactor=1;
                        }
                    }
                    rightLeft.balanceFactor=0;
                }
            } else if(now.balanceFactor<=-2) {
                AvlTree left=now.left;
                if(left.balanceFactor>0) {
                    AvlTree leftRight=left.right;
                    leftRotate(left);
                    rightRotate(now);
                    switch(leftRight.balanceFactor) {
                        case -1-> {
                            now.balanceFactor=1;
                            left.balanceFactor=0;
                        }
                        case 0-> {
                            now.balanceFactor=0;
                            left.balanceFactor=0;
                        }
                        case 1-> {
                            now.balanceFactor=0;
                            left.balanceFactor=-1;
                        }
                    }
                    leftRight.balanceFactor=0;
                } else {
                    rightRotate(now);
                    now.balanceFactor=0;
                    left.balanceFactor=0;
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
    public int getDepth(int element) {
        AvlTree now=left;
        if(now==null) {
            return Integer.MIN_VALUE;
        }
        int depth=1;
        for(;now.element!=element;depth++) {
            if(element<now.element) {
                now=now.left;
            } else if(element>now.element) {
                now=now.right;
            }
            if(now==null) {
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
    public boolean remove(int element) {
        AvlTree now=left;
        if(now==null) {
            return false;
        }
        while(now.element!=element) {
            if(element<now.element) {
                if(now.left==null) {
                    return false;
                }
                now=now.left;
            } else if(element>now.element) {
                if(now.right==null) {
                    return false;
                }
                now=now.right;
            }
        }
        if(now.left!=null&&now.right!=null) {
            AvlTree next=now.right;
            while(next.left!=null) {
                next=next.left;
            }
            now.element=next.element;
            now=next;
        }
        boolean nowIsLeft=now==now.parent.left;
        if(now.left==null&&now.right==null) {
            if(nowIsLeft) {
                now.parent.left=null;
            } else {
                now.parent.right=null;
            }
        } else if(now.left!=null) {
            if(nowIsLeft) {
                now.parent.left=now.left;
            } else {
                now.parent.right=now.left;
            }
            now.left.parent=now.parent;
        } else {
            if(nowIsLeft) {
                now.parent.left=now.right;
            } else {
                now.parent.right=now.right;
            }
            now.right.parent=now.parent;
        }
        for(now=now.parent;now!=this;nowIsLeft=now==now.parent.left,now=now.parent) {
            now.balanceFactor-=nowIsLeft?-1:1;
            if(now.balanceFactor>=2) {
                AvlTree right=now.right;
                if(right.balanceFactor>=0) {
                    leftRotate(now);
                    switch(right.balanceFactor) {
                        case 1-> {
                            now.balanceFactor=0;
                            right.balanceFactor=0;
                        }
                        case 0-> {
                            now.balanceFactor=1;
                            right.balanceFactor=-1;
                        }
                    }
                } else {
                    AvlTree rightLeft=right.left;
                    leftRotate(right);
                    rightRotate(now);
                    switch(rightLeft.balanceFactor) {
                        case 1-> {
                            now.balanceFactor=-1;
                            right.balanceFactor=0;
                        }
                        case 0-> {
                            now.balanceFactor=0;
                            right.balanceFactor=0;
                        }
                        case -1-> {
                            now.balanceFactor=0;
                            right.balanceFactor=1;
                        }
                    }
                    rightLeft.balanceFactor=0;
                }
            } else if(now.balanceFactor<=-2) {
                AvlTree left=now.left;
                if(left.balanceFactor>0) {
                    AvlTree leftRight=left.right;
                    leftRotate(left);
                    rightRotate(now);
                    switch(leftRight.balanceFactor) {
                        case -1-> {
                            now.balanceFactor=1;
                            left.balanceFactor=0;
                        }
                        case 0-> {
                            now.balanceFactor=0;
                            left.balanceFactor=0;
                        }
                        case 1-> {
                            now.balanceFactor=0;
                            left.balanceFactor=-1;
                        }
                    }
                    leftRight.balanceFactor=0;
                } else {
                    rightRotate(now);
                    switch(left.balanceFactor) {
                        case -1-> {
                            now.balanceFactor=0;
                            left.balanceFactor=0;
                        }
                        case 0-> {
                            now.balanceFactor=-1;
                            left.balanceFactor=1;
                        }
                    }
                }
            } else if(now.balanceFactor==1||now.balanceFactor==-1) {
                break;
            }
        }
        return true;
    }
    /**
    <p>字符串表示</p><br>
    @return AVL树的字符串表示。
    */
    public String toString() {
        if(parent==null&&left==null) {
            return "";
        }
        AvlTree pins[]=new AvlTree[10];
        int pin=0,capacity=10;
        pins[0]=parent==null?left:this;
        StringBuilder result=new StringBuilder("");
        while(pin>=0) {
            result.append(""+pins[pin].element);
            if(pins[pin].left!=null) {
                if(pin+1>=capacity) {
                    capacity=(capacity<<1)+2;
                    AvlTree newPins[]=new AvlTree[capacity];
                    System.arraycopy(pins,0,newPins,0,pin+1);
                    pins=newPins;
                }
                result.append("{");
                pins[pin+1]=pins[pin].left;
                pin++;
            } else if(pins[pin].right!=null) {
                if(pin+1>=capacity) {
                    capacity=(capacity<<1)+2;
                    AvlTree newPins[]=new AvlTree[capacity];
                    System.arraycopy(pins,0,newPins,0,pin+1);
                    pins=newPins;
                }
                result.append("{,");
                pins[pin+1]=pins[pin].right;
                pin++;
            } else {
                boolean back=false;
                do {
                    if(back) {
                        result.append("}");
                    }
                    pin--;
                    back=true;
                } while(pin>=0&&(pins[pin].right==null||pins[pin].right==pins[pin+1]));
                if(pin>=0) {
                    result.append(",");
                    pins[pin+1]=pins[pin].right;
                    pin++;
                }
            }
        }
        return result.toString();
    }
}