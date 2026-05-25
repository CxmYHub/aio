package tools.data_structure;
/**
<p>树状数组类。</p><br>
树状数组是一种特殊的数据结构，用于高效地计算数组的前缀和。<br>
*/
public class binary_indexed_tree
{
    public long lowbit_prefix_sum[];
	/**
	计算一个整数的最低位1的权值。
	@param number 一个整数。
	@return 最低位1的权值。
    @see tools.mathematics.maths#lowbit(int)
	*/
	public static int lowbit(int number)
	{
		return number&(-number);
	}
    /**
    构造一个指定长度的树状数组。
    @param original_array_length 树状数组的长度。
    */
    public binary_indexed_tree(int original_array_length)
    {
        lowbit_prefix_sum=new long[original_array_length+1];
    }
    /**
    构造一个树状数组，初始值为指定数组。
    @param original_array 初始数组。
    */
    public binary_indexed_tree(int original_array[])
    {
        lowbit_prefix_sum=new long[original_array.length+1];
        for(int i=1;i<lowbit_prefix_sum.length;i++)
        {
            lowbit_prefix_sum[i]+=original_array[i-1];
            int next=i+lowbit(i);
            if(next<lowbit_prefix_sum.length)
            {
                lowbit_prefix_sum[next]+=lowbit_prefix_sum[i];
            }
        }
    }
    /**
	<p>此方法会修改调用对象。</p><br>
    在树状数组的指定索引处添加一个值。
    @param index 要添加值的索引。
    @param addend 要添加的值。
    */
    public void add(int index,int addend)
    {
        for(int i=index+1;i<lowbit_prefix_sum.length;i+=lowbit(i))
        {
            lowbit_prefix_sum[i]+=addend;
        }
    }
    /**
    计算树状数组中指定索引处的前缀和。
    @param index 要计算前缀和的索引。
    @return 索引处的前缀和。
    */
    public long sum_prefix(int index)
    {
        long sum=0;
        for(int i=index+1;i>0;i-=lowbit(i))
        {
            sum+=lowbit_prefix_sum[i];
        }
        return sum;
    }
    /**
    计算树状数组中指定区间的和。
    @param index_left 区间的左端点。
    @param index_right 区间的右端点。
    @return 区间的和。
    */
    public long sum_interval(int index_left,int index_right)
    {
        long right_sum=0;
        long left_sum=0;
        for(int i=index_right+1;i>0;i-=lowbit(i))
        {
            right_sum+=lowbit_prefix_sum[i];
        }
        for(int i=index_left;i>0;i-=lowbit(i))
        {
            left_sum+=lowbit_prefix_sum[i];
        }
        return right_sum-left_sum;
    }
    public String toString()
    {
        StringBuilder result=new StringBuilder("[");
        if(lowbit_prefix_sum.length>1)
        {
            result.append(lowbit_prefix_sum[1]);
            for(int i=2;i<lowbit_prefix_sum.length;i++)
            {
                result.append(","+lowbit_prefix_sum[i]);
            }
        }
        result.append("]");
        return result.toString();
    }
}