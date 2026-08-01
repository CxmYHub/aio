package tools.mathematics;
/**
<p>角度类</p><br>
用于表示角度及对角度的操作。
*/
public class angle
{
    /**
    <p>度</p>
    */
    public int degree;
    /**
    <p>分</p>
    */
    public int minute;
    /**
    <p>秒</p>
    */
    public int second;
    /**
    <p>全参构造方法</p><br>
    构造一个角度对象。
    @param degree 度。
    @param minute 分。
    @param second 秒。
    */
    public angle(int degree,int minute,int second)
    {
        this.degree=degree;
        this.minute=minute;
        this.second=second;
    }
    /**
    <p>构造方法</p><br>
    构造一个角度对象。
    @param degree 度。
    @param minute 分。
    */
    public angle(int degree,int minute)
    {
        this.degree=degree;
        this.minute=minute;
        second=0;
    }
    /**
    <p>构造方法</p><br>
    构造一个角度对象。
    @param degree 度。
    */
    public angle(int degree)
    {
        this.degree=degree;
        minute=0;
        second=0;
    }
    /**
    <p>无参构造方法</p><br>
    构造一个默认角度对象(0°0'0")。
    */
    public angle()
    {
        degree=0;
        minute=0;
        second=0;
    }
    /**
    <p>度分秒转小数</p><br>
    计算角度对应的小数表示的角度。
    @return 角度对应的小数表示的角度。
    */
    public double angle_to_deg()
    {
        return (double)degree+(double)minute/60+(double)second/3600;
    }
    /**
    <p>度分秒转小数</p><br>
    计算一个角度对应的小数表示的角度。
    @param degree 度。
    @param minute 分。
    @param second 秒。
    @return 角度对应的小数表示的角度。
    */
    public static double angle_to_deg(int degree,int minute,int second)
    {
        return (double)degree+(double)minute/60+(double)second/3600;
    }
    /**
    <p>度分秒转弧度</p><br>
    计算角度对应的弧度。
    @return 角度对应的弧度。
    */
    public double angle_to_rad()
    {
        return ((double)degree+(double)minute/60+(double)second/3600)/180*Math.PI;
    }
    /**
    <p>度分秒转弧度</p><br>
    计算一个角度对应的弧度。
    @param degree 度。
    @param minute 分。
    @param second 秒。
    @return 角度对应的弧度。
    */
    public static double angle_to_rad(int degree,int minute,int second)
    {
        return ((double)degree+(double)minute/60+(double)second/3600)/180*Math.PI;
    }
    /**
    <p>角度相加</p><br>
    <p>此方法会修改调用对象。</p><br>
    将当前角度与另一个角度相加。
    @param angle 要相加的角度对象。
    @return 和是否超过360°。<br>
    <ul>
        <li>若和超过360°，则自动执行诱导公式一，并返回<code>true</code>。</li>
        <li>若和未超过360°，则返回<code>false</code>。</li>
    </ul>
    */
    public boolean add(angle angle)
    {
        degree+=angle.degree;
        minute+=angle.minute;
        second+=angle.second;
        for(;second>=60;second-=60)
        {
            minute++;
        }
        for(;minute>=60;minute-=60)
        {
            degree++;
        }
        boolean reversed=false;
        for(;degree>=360;degree-=360,reversed=true);
        return reversed;
    }
    /**
    <p>角度相加</p><br>
    计算多个角度的和。
    @param angles 要计算和的多个角度对象。
    @return 多个角度的和。
    */
    public static int[] sum(angle... angles)
    {
        int sum[]=new int[3];
        for(angle angle:angles)
        {
            sum[2]+=angle.second;
            if(sum[2]>=60)
            {
                sum[2]-=60;
                sum[1]++;
            }
            sum[1]+=angle.minute;
            if(sum[1]>=60)
            {
                sum[1]-=60;
                sum[0]++;
            }
            sum[0]+=angle.degree;
        }
        return sum;
    }
    /**
    <p>角度缩小</p><br>
    <p>此方法会修改调用对象。</p><br>
    将当前角度除以一个整数。
    @param number 除数。
    @return 一个角度对象除以一个整数后的小数表示的角度，保留到秒。
    */
    public double divide(int number)
    {
        double sub_second=0;
        if(degree%number!=0)
        {
            minute+=60*(degree%number);
        }
        degree/=number;
        if(minute%number!=0)
        {
            second+=60*(minute%number);
        }
        minute/=number;
        if(second%number!=0)
        {
            sub_second=(double)second/number;
            sub_second-=second/number;
        }
        second/=number;
        return sub_second;
    }
    /**
    <p>诱导公式一</p><br>
    <p>此方法会修改调用对象。</p><br>
    将当前角度的度部分取模360(执行诱导公式一)。
    @return 当前角度对象的度部分取模360后的结果。
    */
    public int reform()
    {
        degree%=360;
        return degree;
    }
    /**
    <p>字符串表示</p><br>
    @return 角度的字符串表示。
    */
    public String toString()
    {
        return ""+degree+"°"+((minute<10)?("0"+minute):minute)+"'"+((second<10)?("0"+second):second)+"\"";
    }
}