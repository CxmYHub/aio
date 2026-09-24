package aio.datastructure;
/**
<p>树状数组类</p><br>
树状数组是一种特殊的数据结构，用于高效地计算数组的前缀和。
*/
public class BinaryIndexedTree {
    /**
    <p>低位前缀和数组</p>
    */
    public long lowbitPrefixSum[];
    /**
    <p>最低位1的权值</p><br>
    计算一个整数的最低位1的权值。
    @param number 一个整数。
    @return 最低位1的权值。
    @see aio.mathematics.Maths#lowbit(int)
    */
    public static int lowbit(int number) {
        return number&(-number);
    }
    /**
    <p>全参构造方法</p><br>
    构造一个树状数组，初始值为指定数组。
    @param originalArray 初始数组。
    */
    public BinaryIndexedTree(int originalArray[]) {
        lowbitPrefixSum=new long[originalArray.length+1];
        for(int i=1;i<lowbitPrefixSum.length;i++) {
            lowbitPrefixSum[i]+=originalArray[i-1];
            int next=i+lowbit(i);
            if(next<lowbitPrefixSum.length) {
                lowbitPrefixSum[next]+=lowbitPrefixSum[i];
            }
        }
    }
    /**
    <p>构造方法</p><br>
    构造一个指定长度的树状数组。
    @param originalArrayLength 树状数组的长度。
    */
    public BinaryIndexedTree(int originalArrayLength) {
        lowbitPrefixSum=new long[originalArrayLength+1];
    }
    /**
    <p>元素输入</p><br>
    <p>此方法会修改调用对象。</p><br>
    在树状数组的指定索引处添加一个值。
    @param index 要添加值的索引。
    @param addend 要添加的值。
    */
    public void add(int index,int addend) {
        for(int i=index+1;i<lowbitPrefixSum.length;i+=lowbit(i)) {
            lowbitPrefixSum[i]+=addend;
        }
    }
    /**
    <p>前缀和计算</p><br>
    计算树状数组中指定索引处的前缀和。
    @param index 要计算前缀和的索引。
    @return 索引处的前缀和。
    */
    public long sumPrefix(int index) {
        long sum=0;
        for(int i=index+1;i>0;i-=lowbit(i)) {
            sum+=lowbitPrefixSum[i];
        }
        return sum;
    }
    /**
    <p>区间和计算</p><br>
    计算树状数组中指定区间的和。
    @param indexLeft 区间的左端点。
    @param indexRight 区间的右端点。
    @return 区间的和。
    */
    public long sumInterval(int indexLeft,int indexRight) {
        long rightSum=0;
        long leftSum=0;
        for(int i=indexRight+1;i>0;i-=lowbit(i)) {
            rightSum+=lowbitPrefixSum[i];
        }
        for(int i=indexLeft;i>0;i-=lowbit(i)) {
            leftSum+=lowbitPrefixSum[i];
        }
        return rightSum-leftSum;
    }
    /**
    <p>字符串表示</p><br>
    @return 树状数组的字符串表示。
    */
    public String toString() {
        StringBuilder result=new StringBuilder("[");
        if(lowbitPrefixSum.length>1) {
            result.append(lowbitPrefixSum[1]);
            for(int i=2;i<lowbitPrefixSum.length;i++) {
                result.append(","+lowbitPrefixSum[i]);
            }
        }
        result.append("]");
        return result.toString();
    }
}