package aio.mathematics;
/**
<p>直方图类</p><br>
用于制作和表示直方图和对直方图的操作。
*/
public class histogram
{
    /**
    <p>区间数量</p>
    */
    public int group_count;
    /**
    <p>区间数据量</p>
    */
    public int count[];
    /**
    <p>区间中点</p>
    */
    public double group_value[];
    /**
    <p>区间边界</p>
    */
    public double bound[];
    /**
    <p>下溢数据量</p>
    */
    public int underflow_count;
    /**
    <p>上溢数据量</p>
    */
    public int overflow_count;
    /**
    <p>构造方法</p><br>
    通过区间数和数据范围构造一个等距直方图对象。
    @param group_count 区间数量。
    @param data 数据。
    */
    public histogram(int group_count,double ...data)
    {
        double max=data[0];
        double min=max;
        for(int i=1;i<data.length;i++)
        {
            double now=data[i];
            max=now>max?now:max;
            min=now<min?now:min;
        }
        this.group_count=group_count;
        count=new int[group_count];
        group_value=new double[group_count];
        bound=new double[group_count+1];
        underflow_count=0;
        overflow_count=0;
        bound[0]=min;
        double range=max-min;
        for(int i=0;i<group_count;i++)
        {
            double group_upper_bound=min+(i+1)*range/group_count;
            bound[i+1]=group_upper_bound;
            group_value[i]=(group_upper_bound+bound[i])/2;
        }
        for(int i=0;i<data.length;i++)
        {
            double now=data[i];
            if(now<min)
            {
                underflow_count++;
            }
            else if(now>max)
            {
                overflow_count++;
            }
            else if(now==max)
            {
                count[group_count-1]++;
            }
            else
            {
                int index=(int)((now-min)*group_count/range);
                count[index]++;
            }
        }
        bound[group_count]=max;
    }
    /**
    <p>构造方法</p><br>
    通过区间数和数据范围构造一个等距直方图对象。
    @param group_count 区间数量。
    @param min 最小值。
    @param max 最大值。
    */
    public histogram(int group_count,double min,double max)
    {
        this.group_count=group_count;
        count=new int[group_count];
        group_value=new double[group_count];
        bound=new double[group_count+1];
        underflow_count=0;
        overflow_count=0;
        bound[0]=min;
        double range=max-min;
        for(int i=0;i<group_count;i++)
        {
            double group_upper_bound=min+(i+1)*range/group_count;
            bound[i+1]=group_upper_bound;
            group_value[i]=(group_upper_bound+bound[i])/2;
        }
        bound[group_count]=max;
    }
    /**
    <p>数据输入</p><br>
    <p>此方法会修改调用对象。</p><br>
    向直方图中插入数据。
    @param data 数据。
    @return 数据是否溢出。<br>
    注意，即使数据溢出，也会被插入到溢出区间中。
    */
    public boolean input(double data)
    {
        if(data<bound[0])
        {
            underflow_count++;
            return false;
        }
        else if(data>bound[group_count])
        {
            overflow_count++;
            return false;
        }
        else if(data==bound[group_count])
        {
            count[group_count-1]++;
            return true;
        }
        else
        {
            int index=(int)((data-bound[0])*group_count/(bound[group_count]-bound[0]));
            count[index]++;
            return true;
        }
    }
    /**
    <p>数据批量输入</p><br>
    <p>此方法会修改调用对象。</p><br>
    向直方图中插入多个数据。
    @param data 数据。
    @return 未溢出的数据的数量。<br>
    注意，即使数据溢出，也会被插入到溢出区间中。
    */
    public int input_more(double ...data)
    {
        int count=data.length;
        for(int i=0;i<data.length;i++)
        {
            double now=data[i];
            if(now<bound[0])
            {
                underflow_count++;
                count--;
            }
            else if(now>bound[group_count])
            {
                overflow_count++;
                count--;
            }
            else if(now==bound[group_count])
            {
                this.count[group_count-1]++;
            }
            else
            {
                int index=(int)((now-bound[0])*group_count/(bound[group_count]-bound[0]));
                this.count[index]++;
            }
        }
        return count;
    }
    /**
    <p>字符串表示</p><br>
    @return 直方图的字符串表示。
    */
    public String toString()
    {
        StringBuilder result=new StringBuilder("\n");
        result.append("underflow_count:"+underflow_count+"\n");
        for(int i=0;i<group_count;i++)
        {
            result.append(bound[i]+"\n"+count[i]+"\n");
        }
        result.append(bound[group_count]+"\noverflow_count:"+overflow_count+"\n");
        return result.toString();
    }
}