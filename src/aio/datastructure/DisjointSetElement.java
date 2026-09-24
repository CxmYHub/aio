package aio.datastructure;
/**
<p>元素并查集类</p><br>
元素并查集是一种特殊的并查集，除索引外，还可通过元素进行合并和查询操作。<br>
本元素并查集通过维护一个哈希表实现元素到索引的映射。
@see aio.datastructure.DisjointSet
*/
public class DisjointSetElement extends DisjointSet {
    /**
    <p>元素数组</p>
    */
    public int elements[];
    /**
    <p>元素到索引的映射</p>
    */
    public HashMap elementIndex;
    /**
    <p>元素数量</p>
    */
    public int size=0;
    /**
    <p>构造方法</p><br>
    构造一个指定容量的空并查集。
    @param count 并查集的容量。
    */
    public DisjointSetElement(int count) {
        super(count);
        super.classCount=0;
        elements=new int[capacity];
        elementIndex=new HashMap(capacity);
    }
    /**
    <p>无参构造方法</p><br>
    构造一个默认容量为16的空并查集。
    */
    public DisjointSetElement() {
        super();
        super.classCount=0;
        elements=new int[16];
        elementIndex=new HashMap();
    }
    /**
    <p>扩容</p><br>
    <p>此方法会修改调用对象。</p><br>
    对并查集进行扩容。<br>
    新的并查集容量=当前容量*2+2。
    @return 新的并查集容量=当前容量*2+2。
    */
    public int dilate() {
        capacity=(capacity<<1)+2;
        int newParent[]=new int[capacity];
        int newElement[]=new int[capacity];
        System.arraycopy(parent,0,newParent,0,size);
        System.arraycopy(elements,0,newElement,0,size);
        for(int i=size;i<capacity;i++) {
            newParent[i]=-1;
        }
        parent=newParent;
        elements=newElement;
        return capacity;
    }
    /**
    <p>扩容</p><br>
    <p>此方法会修改调用对象。</p><br>
    对并查集进行扩容。<br>
    新的并查集容量=当前容量+<code>moreCapacity</code>。
    @param moreCapacity 要扩展的容量。
    @return 新的并查集容量=当前容量+<code>moreCapacity</code>。
    */
    public int dilate(int moreCapacity) {
        if(moreCapacity<0) {
            return capacity;
        }
        capacity+=moreCapacity;
        int newParent[]=new int[capacity];
        int newElement[]=new int[capacity];
        System.arraycopy(parent,0,newParent,0,size);
        System.arraycopy(elements,0,newElement,0,size);
        for(int i=size;i<capacity;i++) {
            newParent[i]=-1;
        }
        parent=newParent;
        elements=newElement;
        return capacity;
    }
    /**
    <p>元素输入</p><br>
    <p>此方法会修改调用对象。</p><br>
    向并查集中添加元素。<br>
    如果部分元素已存在，则不会重复添加。<br>
    @param element 要添加的元素。
    @return 添加后并查集中的元素数量。<br>
    若元素已存在，则返回<code>Integer.MIN_VALUE</code>。
    */
    public int input(int element) {
        if(getIndexOf(element)!=Integer.MIN_VALUE) {
            return Integer.MIN_VALUE;
        }
        if(size>=capacity) {
            dilate();
        }
        elementIndex.input(element,size);
        this.elements[size++]=element;
        classCount++;
        return size;
    }
    /**
    <p>元素批量输入</p><br>
    <p>此方法会修改调用对象。</p><br>
    向并查集中添加多个元素。<br>
    如果部分元素已存在，则不会重复添加。<br>
    @param elements 要添加的元素。
    @return 添加后并查集中的元素数量。
    */
    public int inputMore(int... elements) {
        for(int now:elements) {
            if(getIndexOf(now)!=Integer.MIN_VALUE) {
                classCount--;
                continue;
            }
            if(size>=capacity) {
                dilate();
            }
            elementIndex.input(now,size);
            this.elements[size++]=now;
        }
        classCount+=elements.length;
        return size;
    }
    /**
    <p>元素获取</p><br>
    获取并查集中指定索引的元素。
    @param index 元素的索引。
    @return 并查集中指定索引的元素。
    */
    public int getElementAt(int index) {
        return elements[index];
    }
    /**
    <p>索引查询</p><br>
    获取并查集中指定元素的索引。
    @param elements 元素的值。
    @return 并查集中指定元素的索引。
    */
    public int getIndexOf(int elements) {
        return elementIndex.get(elements);
    }
    /**
    <p>根索引查询</p><br>
    获取并查集中指定元素的根索引。
    @param elements 元素的值。
    @return 并查集中指定元素的根索引。<br>
    若元素不存在，则返回<code>Integer.MIN_VALUE</code>。
    */
    public int findRootByElement(int elements) {
        int index=getIndexOf(elements);
        if(index==Integer.MIN_VALUE) {
            return Integer.MIN_VALUE;
        }
        return findRootByIndex(index);
    }
    /**
    <p>集合合并</p><br>
    <p>此方法会修改调用对象。</p><br>
    合并并查集中两个元素的根索引。
    @param element1 元素1的值。
    @param element2 元素2的值。
    @return 合并后的根索引。<br>
    若元素不存在，则返回<code>Integer.MIN_VALUE</code>。
    */
    public int unionElement(int element1,int element2) {
        int rootIndex1=findRootByElement(element1);
        int rootIndex2=findRootByElement(element2);
        if(rootIndex1==Integer.MIN_VALUE||rootIndex2==Integer.MIN_VALUE) {
            return Integer.MIN_VALUE;
        }
        if(rootIndex1==rootIndex2) {
            return rootIndex1;
        }
        classCount--;
        if(parent[rootIndex1]>parent[rootIndex2]) {
            parent[rootIndex2]+=parent[rootIndex1];
            parent[rootIndex1]=rootIndex2;
            return rootIndex2;
        } else {
            parent[rootIndex1]+=parent[rootIndex2];
            parent[rootIndex2]=rootIndex1;
            return rootIndex1;
        }
    }
    /**
    <p>元素相关性判断</p><br>
    <p>此方法会修改调用对象。</p><br>
    判断并查集中两个元素是否相关。
    @param element1 元素1的值。
    @param element2 元素2的值。
    @return 是否相关。
    */
    public boolean isRelatedElement(int element1,int element2) {
        int root1=findRootByElement(element1);
        int root2=findRootByElement(element2);
        return root1!=Integer.MIN_VALUE&&root2!=Integer.MIN_VALUE&&root1==root2;
    }
}