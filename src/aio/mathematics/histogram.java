package aio.mathematics;
/**
<p>直方图类</p><br>
用于制作和表示直方图和对直方图的操作。
*/
public class Histogram {
    /**
    <p>区间数量</p>
    */
    public int groupCount;
    /**
    <p>区间数据量</p>
    */
    public int count[];
    /**
    <p>区间中点</p>
    */
    public double groupValue[];
    /**
    <p>区间边界</p>
    */
    public double bound[];
    /**
    <p>下溢数据量</p>
    */
    public int underflowCount;
    /**
    <p>上溢数据量</p>
    */
    public int overflowCount;
    /**
    <p>构造方法</p><br>
    通过区间数和数据范围构造一个等距直方图对象。
    @param groupCount 区间数量。
    @param data 数据。
    */
    public Histogram(int groupCount,double ...data) {
        double max=data[0];
        double min=max;
        for(int i=1;i<data.length;i++) {
            double now=data[i];
            max=now>max?now:max;
            min=now<min?now:min;
        }
        this.groupCount=groupCount;
        count=new int[groupCount];
        groupValue=new double[groupCount];
        bound=new double[groupCount+1];
        underflowCount=0;
        overflowCount=0;
        bound[0]=min;
        double range=max-min;
        for(int i=0;i<groupCount;i++) {
            double groupUpperBound=min+(i+1)*range/groupCount;
            bound[i+1]=groupUpperBound;
            groupValue[i]=(groupUpperBound+bound[i])/2;
        }
        for(int i=0;i<data.length;i++) {
            double now=data[i];
            if(now<min) {
                underflowCount++;
            } else if(now>max) {
                overflowCount++;
            } else if(now==max) {
                count[groupCount-1]++;
            } else {
                int index=(int)((now-min)*groupCount/range);
                count[index]++;
            }
        }
        bound[groupCount]=max;
    }
    /**
    <p>构造方法</p><br>
    通过区间数和数据范围构造一个等距直方图对象。
    @param groupCount 区间数量。
    @param min 最小值。
    @param max 最大值。
    */
    public Histogram(int groupCount,double min,double max) {
        this.groupCount=groupCount;
        count=new int[groupCount];
        groupValue=new double[groupCount];
        bound=new double[groupCount+1];
        underflowCount=0;
        overflowCount=0;
        bound[0]=min;
        double range=max-min;
        for(int i=0;i<groupCount;i++) {
            double groupUpperBound=min+(i+1)*range/groupCount;
            bound[i+1]=groupUpperBound;
            groupValue[i]=(groupUpperBound+bound[i])/2;
        }
        bound[groupCount]=max;
    }
    /**
    <p>数据输入</p><br>
    <p>此方法会修改调用对象。</p><br>
    向直方图中插入数据。
    @param data 数据。
    @return 数据是否溢出。<br>
    注意，即使数据溢出，也会被插入到溢出区间中。
    */
    public boolean input(double data) {
        if(data<bound[0]) {
            underflowCount++;
            return false;
        } else if(data>bound[groupCount]) {
            overflowCount++;
            return false;
        } else if(data==bound[groupCount]) {
            count[groupCount-1]++;
            return true;
        } else {
            int index=(int)((data-bound[0])*groupCount/(bound[groupCount]-bound[0]));
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
    public int inputMore(double ...data) {
        int count=data.length;
        for(int i=0;i<data.length;i++) {
            double now=data[i];
            if(now<bound[0]) {
                underflowCount++;
                count--;
            } else if(now>bound[groupCount]) {
                overflowCount++;
                count--;
            } else if(now==bound[groupCount]) {
                this.count[groupCount-1]++;
            } else {
                int index=(int)((now-bound[0])*groupCount/(bound[groupCount]-bound[0]));
                this.count[index]++;
            }
        }
        return count;
    }
    /**
    <p>字符串表示</p><br>
    @return 直方图的字符串表示。
    */
    public String toString() {
        StringBuilder result=new StringBuilder("\n");
        result.append("underflowCount:"+underflowCount+"\n");
        for(int i=0;i<groupCount;i++) {
            result.append(bound[i]+"\n"+count[i]+"\n");
        }
        result.append(bound[groupCount]+"\noverflowCount:"+overflowCount+"\n");
        return result.toString();
    }
}