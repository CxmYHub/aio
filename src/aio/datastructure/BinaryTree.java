package aio.datastructure;
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
public class BinaryTree {
    /**
    <p>结点元素</p>
    */
    public int element;
    /**
    <p>左子树指针</p>
    */
    public BinaryTree left;
    /**
    <p>右子树指针</p>
    */
    public BinaryTree right;
    /**
    <p>结点构造方法</p><br>
    构造一个包含指定元素的二叉树结点。
    @param element 结点元素。
    */
    private BinaryTree(int element) {
        this.element=element;
    }
    /**
    <p>构造方法</p><br>
    通过二叉树字符串构造一个二叉树。<br>
    若传入的结点元素为字符，则存储其ASCII码值。
    @param treeString 二叉树字符串。<br>
    二叉树字符串的格式为：<code>根结点{左子树,右子树}...</code>。<br>
    例如：<code>A{B{D,E},C{F{H},G{,I}}}</code>。
    */
    public BinaryTree(String treeString) {
        char treeChars[]=treeString.toCharArray();
        BinaryTree pins[]=new BinaryTree[10];
        int pin=0,capacity=10;
        pins[0]=this;
        int thisElement=0,i=0;
        boolean isRight=false;
        for(;i<treeChars.length;i++) {
            if(treeChars[i]>='0'&&treeChars[i]<='9') {
                element*=10;
                element+=treeChars[i]-'0';
            } else if(treeChars[i]>='A'&&treeChars[i]<='Z'||treeChars[i]>='a'&&treeChars[i]<='z') {
                element=treeChars[i];
            } else {
                break;
            }
        }
        for(i++;i<treeChars.length;i++) {
            if(treeChars[i]>='0'&&treeChars[i]<='9') {
                thisElement*=10;
                thisElement+=treeChars[i]-'0';
            } else if(treeChars[i]>='A'&&treeChars[i]<='Z'||treeChars[i]>='a'&&treeChars[i]<='z') {
                thisElement=treeChars[i];
            } else if(treeChars[i]==',') {
                if(treeChars[i-1]!='{'&&treeChars[i-1]!='}') {
                    if(isRight) {
                        pins[pin].right=new BinaryTree(thisElement);
                    } else {
                        pins[pin].left=new BinaryTree(thisElement);
                    }
                    thisElement=0;
                }
                isRight=true;
            } else if(treeChars[i]=='{') {
                if(pin+1>=capacity) {
                    capacity=(capacity<<1)+2;
                    BinaryTree newPins[]=new BinaryTree[capacity];
                    System.arraycopy(pins,0,newPins,0,pin+1);
                    pins=newPins;
                }
                if(isRight) {
                    pins[pin].right=new BinaryTree(thisElement);
                    pins[pin+1]=pins[pin].right;
                } else {
                    pins[pin].left=new BinaryTree(thisElement);
                    pins[pin+1]=pins[pin].left;
                }
                thisElement=0;
                isRight=false;
                pin++;
            } else if(treeChars[i]=='}') {
                if(treeChars[i-1]!='}') {
                    if(isRight) {
                        pins[pin].right=new BinaryTree(thisElement);
                    } else {
                        pins[pin].left=new BinaryTree(thisElement);
                    }
                    thisElement=0;
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
    public BinaryTree(int preorder[],int inorder[]) {
        if(preorder==null||preorder.length==0) {
            element=0;
        } else {
            element=preorder[0];
            int length=preorder.length;
            BinaryTree pins[]=new BinaryTree[length];
            int pin=0;
            pins[0]=this;
            int inorderIndex=0;
            for(int i=1;i<length;i++) {
                BinaryTree now=pins[pin];
                if(now.element!=inorder[inorderIndex]) {
                    now.left=new BinaryTree(preorder[i]);
                    pins[++pin]=now.left;
                } else {
                    while(pin>=0&&pins[pin].element==inorder[inorderIndex]) {
                        now=pins[pin--];
                        inorderIndex++;
                    }
                    now.right=new BinaryTree(preorder[i]);
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
    @param useInorderPostorder 哑元，代表使用中序遍历序列和后序遍历序列构造二叉树。
    */
    public BinaryTree(int inorder[],int postorder[],int useInorderPostorder) {
        if(inorder==null||inorder.length==0) {
            element=0;
        } else {
            int length=inorder.length;
            element=postorder[length-1];
            BinaryTree pins[]=new BinaryTree[length];
            int pin=0;
            pins[0]=this;
            int inorderIndex=length-1;
            for(int i=length-2;i>=0;i--) {
                BinaryTree now=pins[pin];
                if(now.element!=inorder[inorderIndex]) {
                    now.right=new BinaryTree(postorder[i]);
                    pins[++pin]=now.right;
                } else {
                    while(pin>=0&&pins[pin].element==inorder[inorderIndex]) {
                        now=pins[pin--];
                        inorderIndex--;
                    }
                    now.left=new BinaryTree(postorder[i]);
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
    public int count() {
        BinaryTree pins[]=new BinaryTree[10];
        int pin=1,capacity=10;
        pins[0]=this;
        int count=0;
        while(pin>0) {
            BinaryTree now=pins[--pin];
            count++;
            if(now.left!=null) {
                pins[pin++]=now.left;
            }
            if(now.right!=null) {
                if(pin>=capacity) {
                    capacity=(capacity<<1)+2;
                    BinaryTree newPins[]=new BinaryTree[capacity];
                    System.arraycopy(pins,0,newPins,0,pin);
                    pins=newPins;
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
    public int depth() {
        BinaryTree pins[]=new BinaryTree[10];
        int front=0,rear=1,capacity=10;
        boolean overturn=false;
        pins[0]=this;
        int levelSize=1;
        int depth=0;
        while(levelSize>0) {
            depth++;
            BinaryTree now;
            int nextLevelSize=0;
            for(;levelSize>0;levelSize--) {
                now=pins[front++];
                if(front>=capacity) {
                    front=0;
                    overturn=false;
                }
                if(now.left!=null) {
                    if(front==rear&&overturn) {
                        BinaryTree newPins[]=new BinaryTree[(capacity<<1)+2];
                        System.arraycopy(pins,front,newPins,0,capacity-front);
                        System.arraycopy(pins,0,newPins,capacity-front,rear);
                        pins=newPins;
                        front=0;
                        rear=capacity;
                        capacity=(capacity<<1)+2;
                        overturn=false;
                    }
                    pins[rear++]=now.left;
                    nextLevelSize++;
                    if(rear>=capacity) {
                        rear=0;
                        overturn=true;
                    }
                }
                if(now.right!=null) {
                    if(front==rear&&overturn) {
                        BinaryTree newPins[]=new BinaryTree[(capacity<<1)+2];
                        System.arraycopy(pins,front,newPins,0,capacity-front);
                        System.arraycopy(pins,0,newPins,capacity-front,rear);
                        pins=newPins;
                        front=0;
                        rear=capacity;
                        capacity=(capacity<<1)+2;
                        overturn=false;
                    }
                    pins[rear++]=now.right;
                    nextLevelSize++;
                    if(rear>=capacity) {
                        rear=0;
                        overturn=true;
                    }
                }
            }
            levelSize=nextLevelSize;
        }
        return depth;
    }
    /**
    <p>先序遍历</p><br>
    先序遍历二叉树。
    @return 先序遍历结果。
    */
    public int[] traversalPreorder() {
        BinaryTree pins[]=new BinaryTree[10];
        int pin=1,capacity=10;
        pins[0]=this;
        int result[]=new int[10];
        int count=0,resultCapacity=10;
        while(pin>0) {
            BinaryTree now=pins[--pin];
            if(count>=resultCapacity) {
                resultCapacity=(resultCapacity<<1)+2;
                int newResult[]=new int[resultCapacity];
                System.arraycopy(result,0,newResult,0,count);
                result=newResult;
            }
            result[count++]=now.element;
            if(now.right!=null) {
                pins[pin++]=now.right;
            }
            if(now.left!=null) {
                if(pin>=capacity) {
                    capacity=(capacity<<1)+2;
                    BinaryTree newPins[]=new BinaryTree[capacity];
                    System.arraycopy(pins,0,newPins,0,pin);
                    pins=newPins;
                }
                pins[pin++]=now.left;
            }
        }
        if(count<resultCapacity) {
            int newResult[]=new int[count];
            System.arraycopy(result,0,newResult,0,count);
            result=newResult;
        }
        return result;
    }
    /**
    <p>中序遍历</p><br>
    中序遍历二叉树。
    @return 中序遍历结果。
    */
    public int[] traversalInorder() {
        BinaryTree pins[]=new BinaryTree[10];
        int pin=0,capacity=10;
        BinaryTree now=this;
        int result[]=new int[10];
        int count=0,resultCapacity=10;
        while(now!=null||pin>0) {
            for(;now!=null;now=now.left) {
                if(pin>=capacity) {
                    capacity=(capacity<<1)+2;
                    BinaryTree newPins[]=new BinaryTree[capacity];
                    System.arraycopy(pins,0,newPins,0,pin);
                    pins=newPins;
                }
                pins[pin++]=now;
            }
            now=pins[--pin];
            if(count>=resultCapacity) {
                resultCapacity=(resultCapacity<<1)+2;
                int newResult[]=new int[resultCapacity];
                System.arraycopy(result,0,newResult,0,count);
                result=newResult;
            }
            result[count++]=now.element;
            now=now.right;
        }
        if(count<resultCapacity) {
            int newResult[]=new int[count];
            System.arraycopy(result,0,newResult,0,count);
            result=newResult;
        }
        return result;
    }
    /**
    <p>后序遍历</p><br>
    后序遍历二叉树。
    @return 后序遍历结果。
    */
    public int[] traversalPostorder() {
        BinaryTree pins[]=new BinaryTree[10];
        int pin=0,capacity=10;
        BinaryTree now=this;
        BinaryTree last=null;
        int result[]=new int[10];
        int count=0,resultCapacity=10;
        while(now!=null||pin>0) {
            for(;now!=null;now=now.left) {
                if(pin>=capacity) {
                    capacity=(capacity<<1)+2;
                    BinaryTree newPins[]=new BinaryTree[capacity];
                    System.arraycopy(pins,0,newPins,0,pin);
                    pins=newPins;
                }
                pins[pin++]=now;
            }
            BinaryTree top=pins[pin-1];
            if(top.right!=null&&top.right!=last) {
                now=top.right;
            } else {
                if(count>=resultCapacity) {
                    resultCapacity=(resultCapacity<<1)+2;
                    int newResult[]=new int[resultCapacity];
                    System.arraycopy(result,0,newResult,0,count);
                    result=newResult;
                }
                result[count++]=top.element;
                last=top;
                pin--;
            }
        }
        if(count<resultCapacity) {
            int newResult[]=new int[count];
            System.arraycopy(result,0,newResult,0,count);
            result=newResult;
        }
        return result;
    }
    /**
    <p>层序遍历</p><br>
    层序遍历二叉树。
    @return 层序遍历结果。
    */
    public int[] traversalLevelorder() {
        BinaryTree pins[]=new BinaryTree[10];
        int front=0,rear=1,capacity=10;
        boolean overturn=false;
        pins[0]=this;
        int result[]=new int[10];
        int count=0,resultCapacity=10;
        while(front!=rear||overturn) {
            BinaryTree now=pins[front++];
            if(front>=capacity) {
                front=0;
                overturn=false;
            }
            if(count>=resultCapacity) {
                resultCapacity=(resultCapacity<<1)+2;
                int newResult[]=new int[resultCapacity];
                System.arraycopy(result,0,newResult,0,count);
                result=newResult;
            }
            result[count++]=now.element;
            if(now.left!=null) {
                pins[rear++]=now.left;
                if(rear>=capacity) {
                    rear=0;
                    overturn=true;
                }
            }
            if(now.right!=null) {
                if(front==rear&&overturn) {
                    BinaryTree newPins[]=new BinaryTree[(capacity<<1)+2];
                    System.arraycopy(pins,front,newPins,0,capacity-front);
                    System.arraycopy(pins,0,newPins,capacity-front,rear);
                    pins=newPins;
                    front=0;
                    rear=capacity;
                    capacity=(capacity<<1)+2;
                    overturn=false;
                }
                pins[rear++]=now.right;
                if(rear>=capacity) {
                    rear=0;
                    overturn=true;
                }
            }
        }
        if(count<resultCapacity) {
            int newResult[]=new int[count];
            System.arraycopy(result,0,newResult,0,count);
            result=newResult;
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
    public int input(int element) {
        BinaryTree pins[]=new BinaryTree[10];
        int pin=1,capacity=10;
        pins[0]=this;
        BinaryTree now;
        while(true) {
            now=pins[--pin];
            if(now.left!=null) {
                pins[pin++]=now.left;
            } else {
                now.left=new BinaryTree(element);
                break;
            }
            if(now.right!=null) {
                if(pin>=capacity) {
                    capacity=(capacity<<1)+2;
                    BinaryTree newPins[]=new BinaryTree[capacity];
                    System.arraycopy(pins,0,newPins,0,pin);
                    pins=newPins;
                }
                pins[pin++]=now.right;
            } else {
                now.right=new BinaryTree(element);
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
    public int remove(int element) {
        if(this.element==element) {
            this.element=0;
            left=null;
            right=null;
            return element;
        }
        BinaryTree pins[]=new BinaryTree[10];
        int pin=1,capacity=10;
        pins[0]=this;
        while(pin>0) {
            BinaryTree now=pins[--pin];
            if(now.right!=null) {
                if(now.right.element==element) {
                    now.right=null;
                    return element;
                } else {
                    pins[pin++]=now.right;
                }
            }
            if(now.left!=null) {
                if(now.left.element==element) {
                    now.left=null;
                    return element;
                } else {
                    if(pin>=capacity) {
                        capacity=(capacity<<1)+2;
                        BinaryTree newPins[]=new BinaryTree[capacity];
                        System.arraycopy(pins,0,newPins,0,pin);
                        pins=newPins;
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
    public void invert() {
        BinaryTree pins[]=new BinaryTree[10];
        int pin=1,capacity=10;
        pins[0]=this;
        while(pin>0) {
            BinaryTree now=pins[--pin];
            BinaryTree temp=now.left;
            now.left=now.right;
            now.right=temp;
            if(now.left!=null) {
                pins[pin++]=now.left;
            }
            if(now.right!=null) {
                if(pin>=capacity) {
                    capacity=(capacity<<1)+2;
                    BinaryTree newPins[]=new BinaryTree[capacity];
                    System.arraycopy(pins,0,newPins,0,pin);
                    pins=newPins;
                }
                pins[pin++]=now.right;
            }
        }
    }
    /**
    <p>字符串表示</p><br>
    @return 二叉树的字符串表示。
    */
    public String toString() {
        BinaryTree pins[]=new BinaryTree[10];
        int pin=0,capacity=10;
        pins[0]=this;
        StringBuilder result=new StringBuilder("");
        while(pin>=0) {
            result.append(pins[pin].element);
            if(pins[pin].left!=null) {
                if(pin+1>=capacity) {
                    capacity=(capacity<<1)+2;
                    BinaryTree newPins[]=new BinaryTree[capacity];
                    System.arraycopy(pins,0,newPins,0,pin+1);
                    pins=newPins;
                }
                result.append("{");
                pins[pin+1]=pins[pin].left;
                pin++;
            } else if(pins[pin].right!=null) {
                if(pin+1>=capacity) {
                    capacity=(capacity<<1)+2;
                    BinaryTree newPins[]=new BinaryTree[capacity];
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
    /**
    <p>相等判断</p><br>
    判断两个二叉树是否相同。
    @param anotherTree 要比较的二叉树。
    @return 是否相同。
    */
    public boolean equals(Object anotherTree) {
        if(anotherTree==null||!(anotherTree instanceof BinaryTree)) {
            return false;
        }
        BinaryTree tree=(BinaryTree)anotherTree;
        int thisDepth=depth();
        int treeDepth=tree.depth();
        int thisCount=count();
        int treeCount=tree.count();
        if(thisDepth!=treeDepth||thisCount!=treeCount) {
            return false;
        }
        BinaryTree pins1[]=new BinaryTree[thisCount];
        BinaryTree pins2[]=new BinaryTree[thisCount];
        pins1[0]=this;
        pins2[0]=tree;
        int pin1=1,pin2=1;
        while(pin1>0&&pin2>0) {
            BinaryTree tree1=pins1[--pin1];
            BinaryTree tree2=pins2[--pin2];
            if(tree1.element!=tree2.element) {
                return false;
            }
            if(tree1.left!=null&&tree2.left!=null) {
                pins1[pin1++]=tree1.left;
                pins2[pin2++]=tree2.left;
            } else if(tree1.left==null&&tree2.left==null) {
            } else {
                return false;
            }
            if(tree1.right!=null&&tree2.right!=null) {
                pins1[pin1++]=tree1.right;
                pins2[pin2++]=tree2.right;
            } else if(tree1.right==null&&tree2.right==null) {
            } else {
                return false;
            }
        }
        return true;
    }
}