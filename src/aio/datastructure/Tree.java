package aio.datastructure;
/**
<p>树类</p><br>
树是一种非线性数据结构。<br>
本树以孩子兄弟表示法实现。<br>
其每个结点包含一个元素和零个或多个子结点。<br>
其中：
<ul>
    <li>没有子结点的结点称为叶结点。</li>
    <li>没有父结点的结点称为根结点。</li>
</ul>
*/
public class Tree {
    /**
    <p>元素</p>
    */
    public int element;
    /**
    <p>第一个子结点指针</p>
    */
    public Tree child;
    /**
    <p>下一个兄弟结点指针</p>
    */
    public Tree next;
    /**
    <p>结点构造方法</p><br>
    构造一个包含指定元素的树结点。
    @param element 元素。
    */
    public Tree(int element) {
        this.element=element;
    }
    /**
    <p>无参结点构造方法</p><br>
    构造一个默认元素为0的树结点。
    */
    private Tree() {
    }
    /**
    <p>构造方法</p><br>
    通过树字符串构造一个树。<br>
    若传入的结点元素为字符，则存储其ASCII码值。
    @param treeString 树字符串。<br>
    树字符串的格式为：<code>根结点{子树1,子树2,子树3,...}...</code>。<br>
    例如：<code>A{B{D,E},C{F,G,H,I}}</code>。
    */
    public Tree(String treeString) {
        char treeChars[]=treeString.toCharArray();
        Tree pins[]=new Tree[10];
        int pin=0,capacity=10;
        pins[0]=this;
        int thisElement=0;
        for(int i=0;i<treeChars.length;i++) {
            if(treeChars[i]>='0'&&treeChars[i]<='9') {
                thisElement*=10;
                thisElement+=treeChars[i]-'0';
            } else if(treeChars[i]>='A'&&treeChars[i]<='Z'||treeChars[i]>='a'&&treeChars[i]<='z') {
                thisElement=treeChars[i];
            } else if(treeChars[i]==',') {
                if(treeChars[i-1]!='}') {
                    pins[pin].element=thisElement;
                    thisElement=0;
                }
                pins[pin].next=new Tree();
                pins[pin]=pins[pin].next;
            } else if(treeChars[i]=='{') {
                if(pin>=capacity) {
                    capacity=(capacity<<1)+2;
                    Tree newPins[]=new Tree[capacity];
                    System.arraycopy(pins,0,newPins,0,pin);
                    pins=newPins;
                }
                pins[pin].element=thisElement;
                thisElement=0;
                pins[pin].child=new Tree();
                pins[pin+1]=pins[pin].child;
                pin++;
            } else if(treeChars[i]=='}') {
                if(treeChars[i-1]!='}') {
                    pins[pin].element=thisElement;
                    thisElement=0;
                }
                pin--;
            }
        }
        if(thisElement!=0) {
            pins[pin].element=thisElement;
        }
    }
    /**
    <p>结点计数</p><br>
    计算树的结点数。
    @return 树的结点数。
    */
    public int count() {
        Tree pins[]=new Tree[10];
        int pin=1,capacity=10;
        pins[0]=this;
        int count=0;
        while(pin>0) {
            Tree now=pins[--pin];
            count++;
            if(now.child!=null) {
                pins[pin++]=now.child;
            }
            if(now.next!=null) {
                if(pin>=capacity) {
                    capacity=(capacity<<1)+2;
                    Tree newPins[]=new Tree[capacity];
                    System.arraycopy(pins,0,newPins,0,pin);
                    pins=newPins;
                }
                pins[pin++]=now.next;
            }
        }
        return count;
    }
    /**
    <p>树深度计算</p><br>
    计算树的深度。
    @return 树的深度。
    */
    public int depth() {
        Tree pins[]=new Tree[10];
        int front=0,rear=1,capacity=10;
        boolean overturn=false;
        pins[0]=this;
        int levelSize=1;
        int depth=0;
        while(levelSize>0) {
            depth++;
            Tree now;
            int nextLevelSize=0;
            for(;levelSize>0;levelSize--) {
                now=pins[front++];
                if(front>=capacity) {
                    front=0;
                    overturn=false;
                }
                if(now.child!=null) {
                    for(now=now.child;now!=null;now=now.next) {
                        if(front==rear&&overturn) {
                            Tree newPins[]=new Tree[(capacity<<1)+2];
                            System.arraycopy(pins,front,newPins,0,capacity-front);
                            System.arraycopy(pins,0,newPins,capacity-front,rear);
                            pins=newPins;
                            front=0;
                            rear=capacity;
                            capacity=(capacity<<1)+2;
                            overturn=false;
                        }
                        pins[rear++]=now;
                        nextLevelSize++;
                        if(rear>=capacity) {
                            rear=0;
                            overturn=true;
                        }
                    }
                }
            }
            levelSize=nextLevelSize;
        }
        return depth;
    }
    /**
    <p>先序遍历</p><br>
    先序遍历树。
    @return 先序遍历结果。
    */
    public int[] traversalPreorder() {
        Tree pins[]=new Tree[10];
        int pin=1,capacity=10;
        pins[0]=this;
        int result[]=new int[10];
        int count=0,resultCapacity=10;
        while(pin>0) {
            Tree now=pins[--pin];
            if(count>=resultCapacity) {
                resultCapacity=(resultCapacity<<1)+2;
                int newResult[]=new int[resultCapacity];
                System.arraycopy(result,0,newResult,0,count);
                result=newResult;
            }
            result[count++]=now.element;
            if(now.next!=null) {
                pins[pin++]=now.next;
            }
            if(now.child!=null) {
                if(pin>=capacity) {
                    capacity=(capacity<<1)+2;
                    Tree newPins[]=new Tree[capacity];
                    System.arraycopy(pins,0,newPins,0,pin);
                    pins=newPins;
                }
                pins[pin++]=now.child;
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
    <p>后序遍历</p><br>
    后序遍历树。
    @return 后序遍历结果。
    */
    public int[] traversalPostorder() {
        Tree levelLast[]=new Tree[10];
        int level=0,capacity=10;
        levelLast[0]=this;
        boolean back=false;
        int result[]=new int[10];
        int count=0,resultCapacity=10;
        while(level>=0) {
            Tree now=levelLast[level];
            for(;!back&&now.child!=null;now=now.child) {
                if(level+1>=capacity) {
                    capacity=(capacity<<1)+2;
                    Tree newLevelLast[]=new Tree[capacity];
                    System.arraycopy(levelLast,0,newLevelLast,0,level+1);
                    levelLast=newLevelLast;
                }
                levelLast[++level]=now.child;
            }
            while(now!=null&&(now.child==null||back)) {
                if(count>=resultCapacity) {
                    resultCapacity=(resultCapacity<<1)+2;
                    int newResult[]=new int[resultCapacity];
                    System.arraycopy(result,0,newResult,0,count);
                    result=newResult;
                }
                result[count++]=now.element;
                now=now.next;
                levelLast[level]=now;
                back=false;
            }
            if(now==null) {
                level--;
                back=true;
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
    层序遍历树。
    @return 层序遍历结果。
    */
    public int[] traversalLevelorder() {
        Tree pins[]=new Tree[10];
        int front=0,rear=1,capacity=10;
        boolean overturn=false;
        pins[0]=this;
        int result[]=new int[10];
        int count=0,resultCapacity=10;
        while(front!=rear||overturn) {
            Tree now=pins[front++];
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
            for(Tree childs=now.child;childs!=null;childs=childs.next) {
                if(front==rear&&overturn) {
                    Tree newPins[]=new Tree[(capacity<<1)+2];
                    System.arraycopy(pins,front,newPins,0,capacity-front);
                    System.arraycopy(pins,0,newPins,capacity-front,rear);
                    pins=newPins;
                    front=0;
                    rear=capacity;
                    capacity=(capacity<<1)+2;
                    overturn=false;
                }
                pins[rear++]=childs;
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
    <p>元素插入</p><br>
    <p>此方法会修改调用对象。</p><br>
    向树中指定元素的子结点插入一个元素。
    @param element 要插入的元素。
    @param target 要插入的位置的元素。
    @return 插入的元素。<br>
    若目标元素不存在，则返回<code>Integer.MIN_VALUE</code>。
    */
    public int insertTo(int element,int target) {
        Tree pins[]=new Tree[10];
        int pin=1,capacity=10;
        pins[0]=this;
        while(pin>0) {
            Tree now=pins[--pin];
            if(now.element==target) {
                if(now.child==null) {
                    now.child=new Tree(element);
                } else {
                    Tree last=now.child;
                    for(;last.next!=null;last=last.next);
                    last.next=new Tree(element);
                }
                return element;
            }
            if(now.next!=null) {
                pins[pin++]=now.next;
            }
            if(now.child!=null) {
                if(pin>=capacity) {
                    capacity=(capacity<<1)+2;
                    Tree newPins[]=new Tree[capacity];
                    System.arraycopy(pins,0,newPins,0,pin);
                    pins=newPins;
                }
                pins[pin++]=now.child;
            }
        }
        return Integer.MIN_VALUE;
    }
    /**
    <p>子树删除（单个匹配）</p><br>
    <p>此方法会修改调用对象。</p><br>
    从树中删除一个元素，及其所有子树。<br>
    若存在多个相同元素，则只删除先序遍历序列中出现的第一个。
    @param element 要删除的元素。
    @return 删除的元素。<br>
    若元素不存在，则返回<code>Integer.MIN_VALUE</code>。
    */
    public int remove(int element) {
        if(this.element==element) {
            this.element=0;
            child=null;
            next=null;
            return element;
        }
        Tree pins[]=new Tree[10];
        int pin=1,capacity=10;
        pins[0]=this;
        while(pin>0) {
            Tree now=pins[--pin];
            if(now.next!=null) {
                if(now.next.element==element) {
                    now.next=now.next.next;
                    return element;
                }
                pins[pin++]=now.next;
            }
            if(now.child!=null) {
                if(now.child.element==element) {
                    now.child=now.child.next;
                    return element;
                }
                if(pin>=capacity) {
                    capacity=(capacity<<1)+2;
                    Tree newPins[]=new Tree[capacity];
                    System.arraycopy(pins,0,newPins,0,pin);
                    pins=newPins;
                }
                pins[pin++]=now.child;
            }
        }
        return Integer.MIN_VALUE;
    }
    /**
    <p>字符串表示</p><br>
    @return 树的字符串表示。
    */
    public String toString() {
        Tree pins[]=new Tree[10];
        int pin=0,capacity=10;
        pins[0]=this;
        StringBuilder result=new StringBuilder("");
        while(pin>=0) {
            result.append(pins[pin].element);
            if(pins[pin].child!=null) {
                if(pin+1>=capacity) {
                    capacity=(capacity<<1)+2;
                    Tree newPins[]=new Tree[capacity];
                    System.arraycopy(pins,0,newPins,0,pin+1);
                    pins=newPins;
                }
                result.append("{");
                pins[pin+1]=pins[pin].child;
                pin++;
            } else if(pins[pin].next!=null) {
                result.append(",");
                pins[pin]=pins[pin].next;
            } else {
                do {
                    if(pin>0) {
                        result.append("}");
                    }
                    pin--;
                } while(pin>=0&&pins[pin].next==null);
                if(pin>0) {
                    result.append(",");
                    pins[pin]=pins[pin].next;
                }
            }
        }
        return result.toString();
    }
}