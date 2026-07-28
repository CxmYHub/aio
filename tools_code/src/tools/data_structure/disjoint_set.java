package tools.data_structure;
/**
<p>并查集类</p><br>
并查集是一种数据结构，用于维护集合的合并和查询。<br>
每个集合都有一个代表元素，所有集合中的元素都可以通过代表元素来表示。<br>
并查集的主要操作包括合并两个集合和查询一个元素所属的集合。<br>
并查集的时间复杂度为O(α(n))，其中α(n)为阿克曼函数的反函数，增长极慢，近似为O(1)。<br>
本并查集以数组实现，默认容量为16。
*/
public class disjoint_set
{
    /**
    <p>父元素数组</p><br>
    每个元素的父元素，-1表示该元素为根元素。
    */
    public int parent[];
    /**
    <p>并查集容量</p>
    */
    public int capacity=0;
    /**
    <p>集合数量</p>
    */
    public int class_count=0;
    /**
    <p>构造方法</p><br>
    构造一个指定容量的空并查集。
    @param capacity 并查集的容量。
    */
    public disjoint_set(int capacity)
    {
        this.capacity=capacity;
        this.class_count=capacity;
        parent=new int[capacity];
        for(int i=0;i<capacity;i++)
        {
            parent[i]=-1;
        }
    }
    /**
    <p>无参构造方法</p><br>
    构造一个默认容量为16的空并查集。
    */
    public disjoint_set()
    {
        this.capacity=16;
        this.class_count=16;
        parent=new int[]{-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1};
    }
    /**
    <p>根索引查询</p><br>
    获取并查集中指定索引的根索引。
    @param index 元素的索引。
    @return 并查集中指定索引的根索引。
    */
    public int find_root_by_index(int index)
    {
        if(parent[index]<0)
        {
            return index;
        }
        int root_index=index;
        while(parent[root_index]>=0)
        {
            root_index=parent[root_index];
        }
        while(parent[index]!=root_index)
        {
            int temp=index;
            index=parent[index];
            parent[temp]=root_index;
        }
        return root_index;
    }
    /**
    <p>索引合并</p><br>
    <p>此方法会修改调用对象。</p><br>
    合并并查集中两个索引的根索引。
    @param index1 索引1。
    @param index2 索引2。
    @return 合并后的根索引。
    */
    public int union_index(int index1,int index2)
    {
        int root_index1=find_root_by_index(index1);
        int root_index2=find_root_by_index(index2);
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
    <p>相关性判断</p><br>
    <p>此方法会修改调用对象。</p><br>
    判断并查集中两个索引是否相关。
    @param index1 索引1。
    @param index2 索引2。
    @return 是否相关。
    */
    public boolean is_related_index(int index1,int index2)
    {
        return find_root_by_index(index1)==find_root_by_index(index2);
    }
}