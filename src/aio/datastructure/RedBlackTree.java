package aio.datastructure;
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
public class RedBlackTree {
    /**
    <p>ANSI红色转义符</p>
    */
    public static final String ANSI_RED_COLOR="\u001B[31m";
    /**
    <p>ANSI默认转义符</p>
    */
    public static final String ANSI_DEFAULT_COLOR="\u001B[39m";
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
    public boolean isRed;
    /**
    <p>左子结点指针</p>
    */
    public RedBlackTree left;
    /**
    <p>右子结点指针</p>
    */
    public RedBlackTree right;
    /**
    <p>父结点指针</p>
    */
    public RedBlackTree parent;
    /**
    <p>NIL结点构造方法</p><br>
    构造一个特殊的叶结点。
    @param NIL 哑元，用于创建NIL结点。
    */
    public RedBlackTree(char NIL) {
        element=Integer.MIN_VALUE;
        isRed=false;
        left=this;
        right=this;
        parent=this;
    }
    /**
    <p>NIL结点</p><br>
    特殊空结点，用于替代null以避免空指针异常。
    */
    public static final RedBlackTree NIL=new RedBlackTree('N');
    /**
    <p>结点构造方法</p><br>
    构造一个红黑树结点。
    @param element 元素。
    @param node 哑元，用于区分方法。
    */
    public RedBlackTree(int element,char node) {
        this.element=element;
        isRed=true;
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
    public static RedBlackTree leftRotate(RedBlackTree tree) {
        if(tree==NIL||tree.right==NIL) {
            return NIL;
        }
        RedBlackTree rootParent=tree.parent;
        RedBlackTree right=tree.right;
        RedBlackTree rightLeft=right.left;
        tree.right=rightLeft;
        if(rightLeft!=NIL) {
            rightLeft.parent=tree;
        }
        right.left=tree;
        tree.parent=right;
        right.parent=rootParent;
        if(rootParent!=NIL) {
            if(rootParent.left==tree) {
                rootParent.left=right;
            } else {
                rootParent.right=right;
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
    public static RedBlackTree rightRotate(RedBlackTree tree) {
        if(tree==NIL||tree.left==NIL) {
            return NIL;
        }
        RedBlackTree rootParent=tree.parent;
        RedBlackTree left=tree.left;
        RedBlackTree leftRight=left.right;
        tree.left=leftRight;
        if(leftRight!=NIL) {
            leftRight.parent=tree;
        }
        left.right=tree;
        tree.parent=left;
        left.parent=rootParent;
        if(rootParent!=NIL) {
            if(rootParent.left==tree) {
                rootParent.left=left;
            } else {
                rootParent.right=left;
            }
        }
        return left;
    }
    /**
    <p>构造方法</p><br>
    构造一个包含指定元素的红黑树。
    @param elements 多个元素。
    */
    public RedBlackTree(int... elements) {
        this.element=0;
        isRed=false;
        right=NIL;
        parent=NIL;
        left=new RedBlackTree(elements[0],' ');
        left.parent=this;
        left.isRed=false;
        selectElements:
        for(int element:elements) {
            RedBlackTree now=left;
            while(true) {
                if(element<now.element) {
                    if(now.left==NIL) {
                        now.left=new RedBlackTree(element,' ');
                        now.left.parent=now;
                        now=now.left;
                        break;
                    }
                    now=now.left;
                } else if(element>now.element) {
                    if(now.right==NIL) {
                        now.right=new RedBlackTree(element,' ');
                        now.right.parent=now;
                        now=now.right;
                        break;
                    }
                    now=now.right;
                } else {
                    continue selectElements;
                }
            }
            while(now.parent.isRed) {
                RedBlackTree grandParent=now.parent.parent;
                RedBlackTree uncle=grandParent.left==now.parent?grandParent.right:grandParent.left;
                if(now.parent.isRed&&uncle.isRed) {
                    now.parent.isRed=false;
                    uncle.isRed=false;
                    grandParent.isRed=true;
                    now=grandParent;
                } else if(now.parent.isRed&&!uncle.isRed) {
                    if(grandParent.left.right==now) {
                        now=now.parent;
                        leftRotate(now);
                    } else if(grandParent.right.left==now) {
                        now=now.parent;
                        rightRotate(now);
                    }
                    if(grandParent.left.left==now) {
                        now=rightRotate(grandParent);
                        now.isRed=false;
                        grandParent.isRed=true;
                        continue selectElements;
                    } else if(grandParent.right.right==now) {
                        now=leftRotate(grandParent);
                        now.isRed=false;
                        grandParent.isRed=true;
                        continue selectElements;
                    }
                }
            }
            isRed=false;
            left.isRed=false;
        }
    }
    /**
    <p>无参构造方法</p><br>
    构造一个空红黑树。
    */
    public RedBlackTree() {
        element=0;
        isRed=false;
        left=NIL;
        right=NIL;
        parent=NIL;
    }
    /**
    <p>遍历</p><br>
    中序遍历红黑树。
    @return 中序遍历结果。
    */
    public int[] traversal() {
        RedBlackTree pins[]=new RedBlackTree[10];
        int pin=0,capacity=10;
        RedBlackTree now=left;
        int result[]=new int[10];
        int count=0,resultCapacity=10;
        while(now!=NIL||pin>0) {
            for(;now!=NIL;now=now.left) {
                if(pin>=capacity) {
                    capacity=(capacity<<1)+2;
                    RedBlackTree newPins[]=new RedBlackTree[capacity];
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
        RedBlackTree now=left;
        if(now==NIL) {
            left=new RedBlackTree(element,' ');
            left.parent=this;
            left.isRed=false;
            return true;
        }
        while(true) {
            if(element<now.element) {
                if(now.left==NIL) {
                    now.left=new RedBlackTree(element,' ');
                    now.left.parent=now;
                    now=now.left;
                    break;
                }
                now=now.left;
            } else if(element>now.element) {
                if(now.right==NIL) {
                    now.right=new RedBlackTree(element,' ');
                    now.right.parent=now;
                    now=now.right;
                    break;
                }
                now=now.right;
            } else {
                return false;
            }
        }
        while(now.parent.isRed) {
            RedBlackTree grandParent=now.parent.parent;
            RedBlackTree uncle=grandParent.left==now.parent?grandParent.right:grandParent.left;
            if(now.parent.isRed&&uncle.isRed) {
                now.parent.isRed=false;
                uncle.isRed=false;
                grandParent.isRed=true;
                now=grandParent;
            } else if(now.parent.isRed&&!uncle.isRed) {
                if(grandParent.left.right==now) {
                    now=now.parent;
                    leftRotate(now);
                } else if(grandParent.right.left==now) {
                    now=now.parent;
                    rightRotate(now);
                }
                if(grandParent.left.left==now) {
                    now=rightRotate(grandParent);
                    now.isRed=false;
                    grandParent.isRed=true;
                    return true;
                } else if(grandParent.right.right==now) {
                    now=leftRotate(grandParent);
                    now.isRed=false;
                    grandParent.isRed=true;
                    return true;
                }
            }
        }
        isRed=false;
        left.isRed=false;
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
    public int inputMore(int... elements) {
        int duplicate=0;
        selectElements:
        for(int element:elements) {
            RedBlackTree now=left;
            if(now==NIL) {
                left=new RedBlackTree(element,' ');
                left.parent=this;
                left.isRed=false;
                continue;
            }
            while(true) {
                if(element<now.element) {
                    if(now.left==NIL) {
                        now.left=new RedBlackTree(element,' ');
                        now.left.parent=now;
                        now=now.left;
                        break;
                    }
                    now=now.left;
                } else if(element>now.element) {
                    if(now.right==NIL) {
                        now.right=new RedBlackTree(element,' ');
                        now.right.parent=now;
                        now=now.right;
                        break;
                    }
                    now=now.right;
                } else {
                    duplicate++;
                    continue selectElements;
                }
            }
            while(now.parent.isRed) {
                RedBlackTree grandParent=now.parent.parent;
                RedBlackTree uncle=grandParent.left==now.parent?grandParent.right:grandParent.left;
                if(now.parent.isRed&&uncle.isRed) {
                    now.parent.isRed=false;
                    uncle.isRed=false;
                    grandParent.isRed=true;
                    now=grandParent;
                } else if(now.parent.isRed&&!uncle.isRed) {
                    if(grandParent.left.right==now) {
                        now=now.parent;
                        leftRotate(now);
                    } else if(grandParent.right.left==now) {
                        now=now.parent;
                        rightRotate(now);
                    }
                    if(grandParent.left.left==now) {
                        now=rightRotate(grandParent);
                        now.isRed=false;
                        grandParent.isRed=true;
                        continue selectElements;
                    } else if(grandParent.right.right==now) {
                        now=leftRotate(grandParent);
                        now.isRed=false;
                        grandParent.isRed=true;
                        continue selectElements;
                    }
                }
            }
            isRed=false;
            left.isRed=false;
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
    public int getDepth(int element) {
        RedBlackTree now=left;
        int depth=1;
        for(;now.element!=element;depth++) {
            if(now==NIL) {
                return Integer.MIN_VALUE;
            }
            if(element<now.element) {
                now=now.left;
            } else if(element>now.element) {
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
    public boolean remove(int element) {
        RedBlackTree now=left;
        if(now==NIL) {
            return false;
        }
        while(now.element!=element) {
            if(element<now.element) {
                if(now.left==NIL) {
                    return false;
                }
                now=now.left;
            } else if(element>now.element) {
                if(now.right==NIL) {
                    return false;
                }
                now=now.right;
            }
        }
        if(now.left!=NIL&&now.right!=NIL) {
            RedBlackTree next=now.right;
            while(next.left!=NIL) {
                next=next.left;
            }
            now.element=next.element;
            now=next;
        }
        RedBlackTree sibling=now.parent.left==now?now.parent.right:now.parent.left;
        boolean nowIsLeft=now==now.parent.left;
        if(now.left==NIL&&now.right==NIL) {
            if(now.isRed||now==left) {
                if(nowIsLeft) {
                    now.parent.left=NIL;
                } else {
                    now.parent.right=NIL;
                }
            } else {
                RedBlackTree deleting=now;
                while(!now.isRed&&left!=now) {
                    sibling=now.parent.left==now?now.parent.right:now.parent.left;
                    nowIsLeft=now==now.parent.left;
                    if(sibling.isRed) {
                        sibling.isRed=false;
                        now.parent.isRed=true;
                        if(nowIsLeft) {
                            leftRotate(now.parent);
                        } else {
                            rightRotate(now.parent);
                        }
                    } else if(!sibling.left.isRed&&!sibling.right.isRed) {
                        sibling.isRed=true;
                        now=now.parent;
                    } else if(nowIsLeft) {
                        if(sibling.left.isRed&&!sibling.right.isRed) {
                            sibling.left.isRed=false;
                            sibling.isRed=true;
                            rightRotate(sibling);
                        } else if(sibling.right.isRed) {
                            leftRotate(now.parent);
                            boolean temp=now.parent.isRed;
                            now.parent.isRed=sibling.isRed;
                            sibling.isRed=temp;
                            sibling.right.isRed=false;
                            break;
                        }
                    } else {
                        if(!sibling.left.isRed&&sibling.right.isRed) {
                            sibling.right.isRed=false;
                            sibling.isRed=true;
                            leftRotate(sibling);
                        } else if(sibling.left.isRed) {
                            rightRotate(now.parent);
                            boolean temp=now.parent.isRed;
                            now.parent.isRed=sibling.isRed;
                            sibling.isRed=temp;
                            sibling.left.isRed=false;
                            break;
                        }
                    }
                }
                if(deleting.parent.left==deleting) {
                    deleting.parent.left=NIL;
                } else {
                    deleting.parent.right=NIL;
                }
                now.isRed=false;
            }
        } else if(now.left!=NIL) {
            if(nowIsLeft) {
                now.parent.left=now.left;
            } else {
                now.parent.right=now.left;
            }
            now.left.parent=now.parent;
            now.left.isRed=false;
        } else {
            if(nowIsLeft) {
                now.parent.left=now.right;
            } else {
                now.parent.right=now.right;
            }
            now.right.parent=now.parent;
            now.right.isRed=false;
        }
        return true;
    }
    /**
    <p>字符串表示</p><br>
    @return 红黑树的字符串表示。
    */
    public String toString() {
        if(parent==NIL&&left==NIL) {
            return "";
        }
        RedBlackTree pins[]=new RedBlackTree[10];
        int pin=0,capacity=10;
        pins[0]=parent==NIL?left:this;
        StringBuilder result=new StringBuilder(ANSI_DEFAULT_COLOR);
        while(pin>=0) {
            result.append((pins[pin].isRed?ANSI_RED_COLOR:"")+pins[pin].element+ANSI_DEFAULT_COLOR);
            if(pins[pin].left!=NIL) {
                if(pin+1>=capacity) {
                    capacity=(capacity<<1)+2;
                    RedBlackTree newPins[]=new RedBlackTree[capacity];
                    System.arraycopy(pins,0,newPins,0,pin+1);
                    pins=newPins;
                }
                result.append("{");
                pins[pin+1]=pins[pin].left;
                pin++;
            } else if(pins[pin].right!=NIL) {
                if(pin+1>=capacity) {
                    capacity=(capacity<<1)+2;
                    RedBlackTree newPins[]=new RedBlackTree[capacity];
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
                } while(pin>=0&&(pins[pin].right==NIL||pins[pin].right==pins[pin+1]));
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