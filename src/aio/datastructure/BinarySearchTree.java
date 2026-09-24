package aio.datastructure;
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
public class BinarySearchTree {
    /**
    <p>结点元素</p>
    */
    public int element;
    /**
    <p>左子结点指针</p>
    */
    public BinarySearchTree left;
    /**
    <p>右子结点指针</p>
    */
    public BinarySearchTree right;
    /**
    <p>父结点指针</p>
    */
    public BinarySearchTree parent;
    /**
    <p>结点构造方法</p><br>
    构造一个二叉查找树结点。
    @param element 元素。
    @param node 哑元，用于区分方法。
    */
    public BinarySearchTree(int element,char node) {
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
    public BinarySearchTree(int... elements) {
        this.element=0;
        left=null;
        right=null;
        parent=null;
        left=new BinarySearchTree(elements[0],' ');
        left.parent=this;
        for(int element:elements) {
            BinarySearchTree now=left;
            while(true) {
                if(element<now.element) {
                    if(now.left==null) {
                        now.left=new BinarySearchTree(element,' ');
                        now.left.parent=now;
                        now=now.left;
                        break;
                    }
                    now=now.left;
                } else if(element>now.element) {
                    if(now.right==null) {
                        now.right=new BinarySearchTree(element,' ');
                        now.right.parent=now;
                        now=now.right;
                        break;
                    }
                    now=now.right;
                } else {
                    break;
                }
            }
        }
    }
    /**
    <p>无参构造方法</p><br>
    构造一个空二叉查找树。
    */
    public BinarySearchTree() {
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
    public int[] traversal() {
        BinarySearchTree pins[]=new BinarySearchTree[10];
        int pin=0,capacity=10;
        BinarySearchTree now=left;
        int result[]=new int[10];
        int count=0,resultCapacity=10;
        while(now!=null||pin>0) {
            for(;now!=null;now=now.left) {
                if(pin>=capacity) {
                    capacity=(capacity<<1)+2;
                    BinarySearchTree newPins[]=new BinarySearchTree[capacity];
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
    向红黑树中插入一个元素。
    @param element 要插入的元素。
    @return 是否成功插入。
    */
    public boolean input(int element) {
        BinarySearchTree now=left;
        if(now==null) {
            left=new BinarySearchTree(element,' ');
            left.parent=this;
            return true;
        }
        while(true) {
            if(element<now.element) {
                if(now.left==null) {
                    now.left=new BinarySearchTree(element,' ');
                    now.left.parent=now;
                    return true;
                }
                now=now.left;
            } else if(element>now.element) {
                if(now.right==null) {
                    now.right=new BinarySearchTree(element,' ');
                    now.right.parent=now;
                    return true;
                }
                now=now.right;
            } else {
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
    public int inputMore(int... elements) {
        int duplicate=0;
        for(int element:elements) {
            BinarySearchTree now=left;
            if(now==null) {
                left=new BinarySearchTree(element,' ');
                left.parent=this;
                continue;
            }
            while(true) {
                if(element<now.element) {
                    if(now.left==null) {
                        now.left=new BinarySearchTree(element,' ');
                        now.left.parent=now;
                        now=now.left;
                        break;
                    }
                    now=now.left;
                } else if(element>now.element) {
                    if(now.right==null) {
                        now.right=new BinarySearchTree(element,' ');
                        now.right.parent=now;
                        now=now.right;
                        break;
                    }
                    now=now.right;
                } else {
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
    public int getDepth(int element) {
        BinarySearchTree now=left;
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
    从二叉查找树中删除一个元素。
    @param element 要删除的元素。
    @return 是否成功删除。
    */
    public boolean remove(int element) {
        BinarySearchTree now=left;
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
            BinarySearchTree next=now.right;
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
        return true;
    }
    /**
    <p>字符串表示</p><br>
    @return 二叉查找树的字符串表示。
    */
    public String toString() {
        if(parent==null&&left==null) {
            return "";
        }
        BinarySearchTree pins[]=new BinarySearchTree[10];
        int pin=0,capacity=10;
        pins[0]=parent==null?left:this;
        StringBuilder result=new StringBuilder("");
        while(pin>=0) {
            result.append(""+pins[pin].element);
            if(pins[pin].left!=null) {
                if(pin+1>=capacity) {
                    capacity=(capacity<<1)+2;
                    BinarySearchTree newPins[]=new BinarySearchTree[capacity];
                    System.arraycopy(pins,0,newPins,0,pin+1);
                    pins=newPins;
                }
                result.append("{");
                pins[pin+1]=pins[pin].left;
                pin++;
            } else if(pins[pin].right!=null) {
                if(pin+1>=capacity) {
                    capacity=(capacity<<1)+2;
                    BinarySearchTree newPins[]=new BinarySearchTree[capacity];
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