package tools.data_structure;
/**
<p>元素并查集类。</p><br>
元素并查集通过维护一个哈希表实现元素到索引的映射。<br>
除索引外，还可通过元素进行合并和查询操作。
*/
public class disjoint_set_element extends disjoint_set
{
    public int elements[];
    public hash_map element_index;
    public int size=0;
    /**
    构造一个指定容量的空并查集。
    @param count 并查集的容量。
    */
    public disjoint_set_element(int count)
    {
        super(count);
        super.class_count=0;
        elements=new int[capacity];
        element_index=new hash_map(capacity);
    }
    /**
    构造一个默认容量为16的空并查集。
    */
    public disjoint_set_element()
    {
        super();
        super.class_count=0;
        elements=new int[16];
        element_index=new hash_map();
    }
    /**
	<p>此方法会修改调用对象。</p><br>
    扩展并查集的容量=当前容量*2+2。
    @return 新的并查集容量=当前容量*2+2。
    */
    public int dilate()
    {
        capacity=(capacity<<1)+2;
        int new_parent[]=new int[capacity];
        int new_element[]=new int[capacity];
        System.arraycopy(parent,0,new_parent,0,size);
        System.arraycopy(elements,0,new_element,0,size);
        for(int i=size;i<capacity;i++)
        {
            new_parent[i]=-1;
        }
        parent=new_parent;
        elements=new_element;
        return capacity;
    }
    /**
	<p>此方法会修改调用对象。</p><br>
    扩展并查集的容量=当前容量+more_capacity。
    @param more_capacity 要扩展的容量。
    @return 新的并查集容量=当前容量+more_capacity。
    */
    public int dilate(int more_capacity)
    {
        if(more_capacity<0)
        {
            return capacity;
        }
        capacity+=more_capacity;
        int new_parent[]=new int[capacity];
        int new_element[]=new int[capacity];
        System.arraycopy(parent,0,new_parent,0,size);
        System.arraycopy(elements,0,new_element,0,size);
        for(int i=size;i<capacity;i++)
        {
            new_parent[i]=-1;
        }
        parent=new_parent;
        elements=new_element;
        return capacity;
    }
    /**
	<p>此方法会修改调用对象。</p><br>
    向并查集中添加元素。<br>
    如果部分元素已存在，则不会重复添加。<br>
    @param element 要添加的元素。
    @return 添加后并查集中的元素数量。<br>
    若元素已存在，则返回Integer.MIN_VALUE。
    */
    public int input(int element)
    {
        if(get_index_of(element)!=Integer.MIN_VALUE)
        {
            return Integer.MIN_VALUE;
        }
        if(size>=capacity)
        {
            dilate();
        }
        element_index.input(element,size);
        this.elements[size++]=element;
        class_count++;
        return size;
    }
    /**
	<p>此方法会修改调用对象。</p><br>
    向并查集中添加多个元素。<br>
    如果部分元素已存在，则不会重复添加。<br>
    @param elements 要添加的元素。
    @return 添加后并查集中的元素数量。
    */
    public int input_more(int... elements)
    {
        for(int now:elements)
        {
            if(get_index_of(now)!=Integer.MIN_VALUE)
            {
                class_count--;
                continue;
            }
            if(size>=capacity)
            {
                dilate();
            }
            element_index.input(now,size);
            this.elements[size++]=now;
        }
        class_count+=elements.length;
        return size;
    }
    /**
    获取并查集中指定索引的元素。
    @param index 元素的索引。
    @return 并查集中指定索引的元素。
    */
    public int get_element_at(int index)
    {
        return elements[index];
    }
    /**
    获取并查集中指定元素的索引。
    @param elements 元素的值。
    @return 并查集中指定元素的索引。
    */
    public int get_index_of(int elements)
    {
        return element_index.get(elements);
    }
    /**
    获取并查集中指定元素的根索引。<br>
    若元素不存在，则返回Integer.MIN_VALUE。
    @param elements 元素的值。
    @return 并查集中指定元素的根索引。<br>
    若元素不存在，则返回Integer.MIN_VALUE。
    */
    public int find_root_by_element(int elements)
    {
        int index=get_index_of(elements);
        if(index==Integer.MIN_VALUE)
        {
            return Integer.MIN_VALUE;
        }
        return find_root_by_index(index);
    }
    /**
	<p>此方法会修改调用对象。</p><br>
    合并并查集中两个元素的根索引。<br>
    若元素不存在，则返回Integer.MIN_VALUE。
    @param element1 元素1的值。
    @param element2 元素2的值。
    @return 合并后的根索引。<br>
    若元素不存在，则返回Integer.MIN_VALUE。
    */
    public int union_element(int element1,int element2)
    {
        int root_index1=find_root_by_element(element1);
        int root_index2=find_root_by_element(element2);
        if(root_index1==Integer.MIN_VALUE||root_index2==Integer.MIN_VALUE)
        {
            return Integer.MIN_VALUE;
        }
        if(root_index1==root_index2)
        {
            return root_index1;
        }
        class_count--;
        if(parent[root_index1]>parent[root_index2])
        {
            parent[root_index2]+=parent[root_index1];
            parent[root_index1]=root_index2;
            return root_index2;
        }
        else
        {
            parent[root_index1]+=parent[root_index2];
            parent[root_index2]=root_index1;
            return root_index1;
        }
    }
    /**
	<p>此方法会修改调用对象。</p><br>
    判断并查集中两个元素是否相关。
    @param element1 元素1的值。
    @param element2 元素2的值。
    @return 如果两个元素相关则返回true，否则返回false。
    */
    public boolean is_related_element(int element1,int element2)
    {
        int root1=find_root_by_element(element1);
        int root2=find_root_by_element(element2);
        return root1!=Integer.MIN_VALUE&&root2!=Integer.MIN_VALUE&&root1==root2;
    }
}