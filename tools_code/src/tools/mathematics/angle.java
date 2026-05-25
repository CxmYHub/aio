package tools.mathematics;
/**
<p>角度类。</p><br>
用于表示角度及对角度的操作。
*/
public class angle
{
    public int degree;
    public int minute;
    public int second;
    /**
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
    构造一个角度对象。
    @param degree 度。
    @param minute 分。
    */
    public angle(int degree,int minute)
    {
        this.degree=degree;
        this.minute=minute;
        this.second=0;
    }
    /**
    构造一个角度对象。
    @param degree 度。
    */
    public angle(int degree)
    {
        this.degree=degree;
        this.minute=0;
        this.second=0;
    }
    /**
    构造一个默认角度对象(0°0'0")。
    */
    public angle()
    {
        this.degree=0;
        this.minute=0;
        this.second=0;
    }
    /**
    计算角度对应的小数表示的角度。
    @return 角度对应的小数表示的角度。
    */
    public double angle_to_deg()
    {
        return (double)degree+(double)minute/60+(double)second/3600;
    }
    /**
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
    计算角度对应的弧度。
    @return 角度对应的弧度。
    */
    public double angle_to_rad()
    {
        return ((double)degree+(double)minute/60+(double)second/3600)/180*Math.PI;
    }
    /**
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
    <p>此方法会修改调用对象。</p><br>
    将当前角度对象与另一个角度对象相加。
    @param angle 要相加的角度对象。
    @return 和是否超过360°。<br>
    若和超过360°，则自动执行诱导公式一，并返回true。<br>
    若和未超过360°，则返回false。
    */
    public boolean add(angle angle)
    {
        this.degree+=angle.degree;
        this.minute+=angle.minute;
        this.second+=angle.second;
        for(;this.second>=60;this.second-=60)
        {
            this.minute++;
        }
        for(;this.minute>=60;this.minute-=60)
        {
            this.degree++;
        }
        boolean reversed=false;
        for(;this.degree>=360;this.degree-=360,reversed=true);
        return reversed;
    }
    /**
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
	<p>此方法会修改调用对象。</p><br>
    将当前角度对象除以一个整数。
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
    <p>此方法会修改调用对象。</p><br>
    将当前角度对象的度部分取模360(执行诱导公式一)。
    @return 当前角度对象的度部分取模360后的结果。
    */
    public int reform()
    {
        this.degree%=360;
        return this.degree;
    }
    public String toString()
    {
        return ""+this.degree+"°"+((this.minute<10)?("0"+this.minute):this.minute)+"'"+((this.second<10)?("0"+this.second):this.second)+"\"";
    }
}