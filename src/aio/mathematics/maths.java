package aio.mathematics;
import java.util.Random;
import aio.collection.*;
/** 
<p>数学方法类</p><br>
包含各种常用的数学方法。
*/
public class maths
{
    /**
    <p>因子提取</p><br>
    计算一个整数的所有因子。
    @param number 一个整数。
    @return 一个整数数组，包含所有因子。
    */
    public static int[] factors(int number)
    {
        if(number==1)
        {
            return new int[]{1};
        }
        else if(number==0)
        {
            return new int[0];
        }
        if(number<0)
        {
            number=-number;
        }
        int limit=(int)Math.sqrt(number);
        int result_left[]=new int[5];
        int result_right[]=new int[5];
        int pin=1,capacity=5;
        result_left[0]=1;
        result_right[0]=number;
        for(int i=2;i<=limit;i++)
        {
            if(number%i==0)
            {
                if(pin>=capacity)
                {
                    capacity=(capacity<<1)+2;
                    int new_result_left[]=new int[capacity];
                    int new_result_right[]=new int[capacity];
                    System.arraycopy(result_left,0,new_result_left,0,pin);
                    System.arraycopy(result_right,0,new_result_right,0,pin);
                    result_left=new_result_left;
                    result_right=new_result_right;
                }
                result_left[pin]=i;
                result_right[pin++]=number/i;
            }
        }
        int fix=result_left[pin-1]==result_right[pin-1]?1:0;
        int result[]=new int[pin*2-fix];
        int index=0;
        for(int i=0;i<pin-fix;i++)
        {
            result[index++]=result_left[i];
        }
        for(int i=pin-1;i>=0;i--)
        {
            result[index++]=result_right[i];
        }
        return result;
    }
    /**
    <p>最大公因数</p><br>
    计算多个整数的最大公因数。<br>
    自动忽略输入数据中的0。
    @param numbers 多个整数。
    @return 最大公因数。
    */
    public static int gcd(int... numbers)
    {
        if(numbers.length==1)
        {
            return numbers[0];
        }
        int numbers_member[]=new int[numbers.length];
        int pin=0;
        for(int i=0;i<numbers.length;i++)
        {
            if(numbers[i]!=0)
            {
                numbers_member[pin++]=numbers[i];
            }
        }
        for(int i=1;i<pin;i++)
        {
            int smaller=Math.abs(numbers_member[i]);
            int greater=Math.abs(numbers_member[i-1]);
            while(greater%smaller!=0)
            {
                int temp=greater%smaller;
                greater=smaller;
                smaller=temp;
            }
            numbers_member[i]=smaller;
        }
        return numbers_member[pin-1];
    }
    /**
    <p>最小公倍数</p><br>
    计算多个整数的最小公倍数。
    @param numbers 多个整数。
    @return 最小公倍数。
    */
    public static int lcm(int... numbers)
    {
        if(numbers.length==1)
        {
            return numbers[0];
        }
        long multiply=numbers[0];
        for(int i=1;i<numbers.length;i++)
        {
            multiply*=numbers[i];
            multiply/=gcd(numbers[i-1],numbers[i]);
        }
        return (int)multiply;
    }
    /**
    <p>质数判断</p><br>
    判断一个整数是否是质数。
    @param number 一个整数。
    @return 是否是质数。
    */
    public static boolean is_prime(int number)
    {
        if(number<=1)
        {
            return false;
        }
        else if(number==2)
        {
            return true;
        }
        else if(number%2==0)
        {
            return false;
        }
        int limit=(int)Math.sqrt(number);
        for(int i=3;i<=limit;i+=2)
        {
            if(number%i==0)
            {
                return false;
            }
        }
        return true;
    }
    /**
    <p>质数表计算</p><br>
    计算(<code>1</code>,<code>max</code>]的所有质数。
    @param max 最大质数的上限。
    @return 一个整数数组，包含所有(<code>1</code>,<code>max</code>]的质数。
    */
    public static int[] prime_table(int max)
    {
        if(max<2)
        {
            return new int[0];
        }
        int result[];
        int pin=0;
        if(max<=10000)
        {
            result=math.prime;
            pin=1229;
            int left=0,right=1228;
            while(left<=right)
            {
                int middle=(left+right)>>1;
                if(result[middle]<=max)
                {
                    left=middle+1;
                }
                else
                {
                    right=middle-1;
                    pin=middle;
                }
            }
        }
        else
        {
            int number_bit[]=new int[(max+1>>5)+1];
            number_bit[0]=1;
            result=new int[(int)((max<<1)/Math.log(max)+1)];
            int capacity=result.length;
            for(int i=2;i<=max;i++)
            {
                if((number_bit[i>>5]&(1<<(i&31)))==0)
                {
                    if(pin>=capacity)
                    {
                        capacity=(capacity<<1)+2;
                        int new_result[]=new int[capacity];
                        System.arraycopy(result,0,new_result,0,pin);
                        result=new_result;
                    }
                    result[pin++]=i;
                    for(long j=(long)i*i;j<=max;j+=i)
                    {
                        number_bit[(int)(j>>5)]|=(1<<(j&31));
                    }
                }
            }
        }
        int returning[]=new int[pin];
        System.arraycopy(result,0,returning,0,pin);
        return returning;
    }
    /**
    <p>质因数分解</p><br>
    将一个整数分解为其质因数。
    @param number 一个整数。
    @return 一个整数数组，包含该整数的所有质因数(<code>Π decompose(number)=number</code>)。
    */
    public static int[] decompose(int number)
    {
        if(number==0)
        {
            return new int[0];
        }
        else if(number>0)
        {
            if(number==1)
            {
                return new int[]{1};
            }
            else if(is_prime(number))
            {
                return new int[]{1,number};
            }
        }
        else
        {
            if(number==-1)
            {
                return new int[]{-1,1};
            }
            else if(is_prime(-number))
            {
                return new int[]{-1,1,number};
            }
        }
        int results[]=new int[5];
        int pin=0,capacity=5;
        if(number<0)
        {
            results[pin++]=-1;
            number*=-1;
        }
        results[pin++]=1;
        int prime[];
        if(number<=100000000)
        {
            prime=math.prime;
        }
        else
        {
            prime=prime_table((int)Math.sqrt(number)+1);
        }
        int i=0;
        while(number>1)
        {
            if(number%prime[i]==0)
            {
                if(pin>=capacity)
                {
                    capacity=(capacity<<1)+2;
                    int new_results[]=new int[capacity];
                    System.arraycopy(results,0,new_results,0,pin);
                    results=new_results;
                }
                results[pin++]=prime[i];
                number/=prime[i];
                if(is_prime(number))
                {
                    if(pin>=capacity)
                    {
                        capacity=(capacity<<1)+2;
                        int new_results[]=new int[capacity];
                        System.arraycopy(results,0,new_results,0,pin);
                        results=new_results;
                    }
                    results[pin++]=number;
                    break;
                }
            }
            else
            {
                i++;
            }
        }
        int result[]=new int[pin];
        for(i=0;i<pin;i++)
        {
            result[i]=results[i];
        }
        return result;
    }
    /**
    <p>快速幂</p><br>
    @param base 底数。
    @param exponent 指数。
    @return <code>base</code>的<code>exponent</code>次方。
    */
    public static int power(int base,int exponent)
    {
        int result=1;
        for(;exponent>0;exponent>>=1)
        {
            if(exponent%2==1)
            {
                result*=base;
            }
            base*=base;
        }
        return result;
    }
    /**
    <p>快速幂</p><br>
    长整型快速幂。
    @param base 底数。
    @param exponent 指数。
    @return <code>base</code>的<code>exponent</code>次方。
    */
    public static long power(long base,long exponent)
    {
        long result=1;
        for(;exponent>0;exponent>>=1)
        {
            if(exponent%2==1)
            {
                result*=base;
            }
            base*=base;
        }
        return result;
    }
    /**
    <p>快速幂取模</p><br>
    长整型快速幂取模1000000007.
    @param base 底数。
    @param exponent 指数。
    @return <code>base</code>的<code>exponent</code>次方对1000000007取模的结果。
    */
    public static long power_mod1000000007(long base,long exponent)
    {
        long result=1;
        for(;exponent>0;exponent>>=1)
        {
            if(exponent%2==1)
            {
                result=result*base%1000000007;
            }
            base=base*base%1000000007;
        }
        return result;
    }
    /**
    <p>快速幂取模</p><br>
    长整型快速幂取模。
    @param base 底数。
    @param exponent 指数。
    @param mod 模数。
    @return <code>base</code>的<code>exponent</code>次方对<code>mod</code>取模的结果。
    */
    public static long power_mod(long base,long exponent,long mod)
    {
        long result=1;
        for(;exponent>0;exponent>>=1)
        {
            if(exponent%2==1)
            {
                result=result*base%mod;
            }
            base=base*base%mod;
        }
        return result;
    }
    /**
    <p>最低位1的权值</p><br>
    计算一个整数的最低位1的权值。
    @param number 一个整数。
    @return 最低位1的权值。
    @see aio.data_structure.binary_indexed_tree#lowbit(int)
    */
    public static int lowbit(int number)
    {
        return number&(-number);
    }
    /**
    <p>整数位数</p><br>
    计算一个整数的位数。
    @param number 一个整数。
    @return 整数的位数。
    */
    public static int length(int number)
    {
        int length=0;
        for(;number!=0;number/=10,length++);
        return length;
    }
    /**
    <p>二进制整数位数</p><br>
    计算一个整数的二进制表示中1的个数。
    @param number 一个整数。
    @return 整数的二进制表示中1的个数。
    */
    public static int bit_count(int number)
    {
        int count=0;
        while(number!=0)
        {
            number=number&(number-1);
            count++;
        }
        return count; 
    }
    /**
    <p>二进制整数表示</p><br>
    计算一个整数的二进制表示。
    @param number 一个整数。
    @return 一个布尔数组，包含整数的二进制表示。
    */
    public static boolean[] binary(int number)
    {
        if(number==0)
        {
            return new boolean[]{false};
        }
        int length=0;
        for(int temp=number;temp!=0;temp>>=1,length++);
        boolean binary_ascend[]=new boolean[length];
        for(int i=0;i<length;i++)
        {
            binary_ascend[i]=(number&1)==1;
            number>>=1;
        }
        return binary_ascend;
    }
    /**
    <p>二进制整数位权</p><br>
    计算一个整数的二进制表示中每个1的权值。
    @param number 一个整数。
    @return 一个整数数组，包含整数的二进制表示中每个1的权值，升序排序。
    */
    public static int[] binary_weight(int number)
    {
        if(number==0)
        {
            return new int[]{0};
        }
        boolean is_minus=number<0;
        number=number<0?-number:number;
        int length=bit_count(number)+(is_minus?1:0);
        int binary_weight_ascend[]=new int[length];
        int left=length-1;
        int now=1<<30;
        while(number>0)
        {
            if((number&now)!=0)
            {
                binary_weight_ascend[left--]=now;
                number-=now;
            }
            now>>=1;
        }
        if(is_minus)
        {
            binary_weight_ascend[0]=-1;
        }
        return binary_weight_ascend;
    }
    /**
    <p>线性插值</p><br>
    @param x1 第一个点的x坐标。
    @param y1 第一个点的y坐标。
    @param x2 第二个点的x坐标。
    @param y2 第二个点的y坐标。
    @param x 插值点的x坐标。
    @return 插值点的y坐标。
    */
    public static double linear_interpolation(double x1,double y1,double x2,double y2,double x)
    {
        return y1+(y2-y1)/(x2-x1)*(x-x1);
    }
    /**
    <p>数学升序序列</p><br>
    计算区间[<code>from</code>,<code>to</code>]中所有数字以数学顺序升序的序列。
    @param from 区间的起始整数。
    @param to 区间的结束整数。
    @return [<code>from</code>,<code>to</code>]区间内数字的数学顺序升序序列。
    */
    public static int[] mathematical_order_number(int from,int to)
    {
        int result[]=new int[to-from+1];
        for(int i=from;i<=to;i++)
        {
            result[i-from]=i;
        }
        return result;
    }
    private static void dfs_dictionary_order_number(int min,int max,int result[],int pin[])
    {
        for(int i=0;i<10;i++)
        {
            int number=min+i;
            if(number>max)
            {
                break;
            }
            if(number==0)
            {
                continue;
            }
            result[pin[0]++]=number;
            dfs_dictionary_order_number(number*10,max,result,pin);
        }
    }
    /**
    <p>字典升序序列</p><br>
    计算区间[<code>1</code>,<code>n</code>]中所有数字以字典序升序的序列。
    @param n 区间的结束整数。
    @return [<code>1</code>,<code>n</code>]区间内数字的字典序升序序列。
    */
    public static int[] dictionary_order_number_to(int n)
    {
        int result[]=new int[n];
        int pin[]=new int[1];
        dfs_dictionary_order_number(0,n,result,pin);
        return result;
    }
    /**
    <p>数字组合数</p><br>
    计算由给定数字(0-9)组成的无前导零的所有不同数字的个数。
    @param numbers 多个整数(0-9)。
    @return 由给定数字(0-9)组成的无前导零的所有不同数字的个数。
    */
    public static long number_combination_count(int... numbers)
    {
        int bit_count[]=new int[10];
        for(int i=0;i<numbers.length;i++)
        {
            bit_count[numbers[i]]++;
        }
        long result=math.fact[numbers.length-1]*(numbers.length-bit_count[0]);
        for(int i=0;i<10;i++)
        {
            result/=math.fact[bit_count[i]];
        }
        return result;
    }
    /**
    <p>数组反转</p><br>
    计算一个整数数组的反转数组。
    @param numbers 整数数组。
    @return 反转后的整数数组。
    */
    public static int[] reverse_new(int numbers[])
    {
        int result[]=new int[numbers.length];
        for(int i=0;i<numbers.length;i++)
        {
            result[i]=numbers[numbers.length-1-i];
        }
        return result;
    }
    /**
    <p>数组反转</p><br>
    <p>此方法会修改输入的数据。</p><br>
    反转一个整数数组。
    @param numbers 要反转的整数数组。
    */
    public static void reverse_local(int numbers[])
    {
        for(int i=numbers.length/2-1;i>=0;i--)
        {
            int temp=numbers[i];
            numbers[i]=numbers[numbers.length-i-1];
            numbers[numbers.length-i-1]=temp;
        }
    }
    /**
    <p>数组区间反转</p><br>
    计算一个整数数组中下标在[<code>from</code>,<code>to</code>]区间内的元素反转后的数组。
    @param numbers 整数数组。
    @param from 反转区间下界。
    @param to 反转区间上界。
    @return 反转下标在[<code>from</code>,<code>to</code>]区间内的元素后的整数数组。
    */
    public static int[] reverse_new(int numbers[],int from,int to)
    {
        int result[]=new int[numbers.length];
        for(int i=0;i<from;i++)
        {
            result[i]=numbers[i];
        }
        for(int i=from;i<=to;i++)
        {
            result[i]=numbers[to-i+from];
        }
        for(int i=to+1;i<numbers.length;i++)
        {
            result[i]=numbers[i];
        }
        return result;
    }
    /**
    <p>数组区间反转</p><br>
    <p>此方法会修改输入的数据。</p><br>
    反转一个整数数组中下标在[<code>from</code>,<code>to</code>]区间内的元素。
    @param numbers 要反转的整数数组。
    @param from 反转区间下界。
    @param to 反转区间上界。
    */
    public static void reverse_local(int numbers[],int from,int to)
    {
        int middle=(from+to)/2;
        for(int i=from,i_to=to;i<=middle;i++,i_to--)
        {
            int temp=numbers[i];
            numbers[i]=numbers[i_to];
            numbers[i_to]=temp;
        }
    }
    /**
    <p>数组反转</p><br>
    计算一个双精度浮点数组的反转数组。
    @param numbers 双精度浮点数组。
    @return 反转后的双精度浮点数组。
    */
    public static double[] reverse_new(double numbers[])
    {
        double result[]=new double[numbers.length];
        for(int i=0;i<numbers.length;i++)
        {
            result[i]=numbers[numbers.length-1-i];
        }
        return result;
    }
    /**
    <p>数组反转</p><br>
    <p>此方法会修改输入的数据。</p><br>
    反转一个双精度浮点数组。
    @param numbers 要反转的双精度浮点数组。
    */
    public static void reverse_local(double numbers[])
    {
        for(int i=numbers.length/2-1;i>=0;i--)
        {
            double temp=numbers[i];
            numbers[i]=numbers[numbers.length-i-1];
            numbers[numbers.length-i-1]=temp;
        }
    }
    /**
    <p>数组区间反转</p><br>
    计算一个双精度浮点数组中下标在[<code>from</code>,<code>to</code>]区间内的元素反转后的数组。
    @param numbers 双精度浮点数组。
    @param from 反转区间下界。
    @param to 反转区间上界。
    @return 反转下标在[<code>from</code>,<code>to</code>]区间内的元素后的双精度浮点数组。
    */
    public static double[] reverse_new(double numbers[],int from,int to)
    {
        double result[]=new double[numbers.length];
        for(int i=0;i<from;i++)
        {
            result[i]=numbers[i];
        }
        for(int i=from;i<=to;i++)
        {
            result[i]=numbers[to-i+from];
        }
        for(int i=to+1;i<numbers.length;i++)
        {
            result[i]=numbers[i];
        }
        return result;
    }
    /**
    <p>数组区间反转</p><br>
    <p>此方法会修改输入的数据。</p><br>
    反转一个双精度浮点数组中下标在[<code>from</code>,<code>to</code>]区间内的元素。
    @param numbers 要反转的双精度浮点数组。
    @param from 反转区间下界。
    @param to 反转区间上界。
    */
    public static void reverse_local(double numbers[],int from,int to)
    {
        int middle=(from+to)/2;
        for(int i=from,i_to=to;i<=middle;i++,i_to--)
        {
            double temp=numbers[i];
            numbers[i]=numbers[i_to];
            numbers[i_to]=temp;
        }
    }
    /**
    <p>数组打乱</p><br>
    计算一个整数数组随机打乱后的数组。
    @param numbers 要打乱的整数数组。
    @return 打乱后的整数数组。
    */
    public static int[] shuffle_new(int numbers[])
    {
        Random random_generator=new Random();
        int result[]=new int[numbers.length];
        System.arraycopy(numbers,0,result,0,numbers.length);
        for(int i=numbers.length-1;i>0;i--)
        {
            int random_index=random_generator.nextInt(i+1);
            int temp=result[i];
            result[i]=result[random_index];
            result[random_index]=temp;
        }
        return result;
    }
    /**
    <p>数组打乱</p><br>
    <p>此方法会修改输入的数据。</p><br>
    随机打乱一个整数数组。
    @param numbers 要打乱的整数数组。
    */
    public static void shuffle_local(int numbers[])
    {
        Random random_generator=new Random();
        for(int i=numbers.length-1;i>0;i--)
        {
            int random_index=random_generator.nextInt(i+1);
            int temp=numbers[i];
            numbers[i]=numbers[random_index];
            numbers[random_index]=temp;
        }
    }
    /**
    <p>数组区间打乱</p><br>
    计算一个整数数组中下标在[<code>from</code>,<code>to</code>]区间内的元素随机打乱后的数组。
    @param numbers 要打乱的整数数组。
    @param from 打乱区间下界。
    @param to 打乱区间上界。
    @return 随机打乱下标在[<code>from</code>,<code>to</code>]区间内的元素后的整数数组。
    */
    public static int[] shuffle_new(int numbers[],int from,int to)
    {
        Random random_generator=new Random();
        int result[]=new int[numbers.length];
        System.arraycopy(numbers,0,result,0,numbers.length);
        for(int i=to;i>=from;i--)
        {
            int random_index=random_generator.nextInt(from,to+1);
            int temp=result[i];
            result[i]=result[random_index];
            result[random_index]=temp;
        }
        return result;
    }
    /**
    <p>数组区间打乱</p><br>
    <p>此方法会修改输入的数据。</p><br>
    随机打乱一个整数数组中下标在[<code>from</code>,<code>to</code>]区间内的元素。
    @param numbers 要打乱的整数数组。
    @param from 打乱区间下界。
    @param to 打乱区间上界。
    */
    public static void shuffle_local(int numbers[],int from,int to)
    {
        Random random_generator=new Random();
        for(int i=to;i>=from;i--)
        {
            int random_index=random_generator.nextInt(from,to+1);
            int temp=numbers[i];
            numbers[i]=numbers[random_index];
            numbers[random_index]=temp;
        }
    }
    /**
    <p>数组打乱</p><br>
    计算一个双精度浮点数组随机打乱后的数组。
    @param numbers 要打乱的双精度浮点数组。
    @return 打乱后的双精度浮点数组。
    */
    public static double[] shuffle_new(double numbers[])
    {
        Random random_generator=new Random();
        double result[]=new double[numbers.length];
        System.arraycopy(numbers,0,result,0,numbers.length);
        for(int i=numbers.length-1;i>0;i--)
        {
            int random_index=random_generator.nextInt(i+1);
            double temp=result[i];
            result[i]=result[random_index];
            result[random_index]=temp;
        }
        return result;
    }
    /**
    <p>数组打乱</p><br>
    <p>此方法会修改输入的数据。</p><br>
    随机打乱一个双精度浮点数组。
    @param numbers 要打乱的双精度浮点数组。
    */
    public static void shuffle_local(double numbers[])
    {
        Random random_generator=new Random();
        for(int i=numbers.length-1;i>0;i--)
        {
            int random_index=random_generator.nextInt(i+1);
            double temp=numbers[i];
            numbers[i]=numbers[random_index];
            numbers[random_index]=temp;
        }
    }
    /**
    <p>数组区间打乱</p><br>
    计算一个双精度浮点数组中下标在[<code>from</code>,<code>to</code>]区间内的元素随机打乱后的数组。
    @param numbers 要打乱的双精度浮点数组。
    @param from 打乱区间下界。
    @param to 打乱区间上界。
    @return 随机打乱下标在[<code>from</code>,<code>to</code>]区间内的元素后的双精度浮点数组。
    */
    public static double[] shuffle_new(double numbers[],int from,int to)
    {
        Random random_generator=new Random();
        double result[]=new double[numbers.length];
        System.arraycopy(numbers,0,result,0,numbers.length);
        for(int i=to;i>=from;i--)
        {
            int random_index=random_generator.nextInt(from,to+1);
            double temp=result[i];
            result[i]=result[random_index];
            result[random_index]=temp;
        }
        return result;
    }
    /**
    <p>数组区间打乱</p><br>
    <p>此方法会修改输入的数据。</p><br>
    随机打乱一个双精度浮点数组中下标在[<code>from</code>,<code>to</code>]区间内的元素。
    @param numbers 要打乱的双精度浮点数组。
    @param from 打乱区间下界。
    @param to 打乱区间上界。
    */
    public static void shuffle_local(double numbers[],int from,int to)
    {
        Random random_generator=new Random();
        for(int i=to;i>=from;i--)
        {
            int random_index=random_generator.nextInt(from,to+1);
            double temp=numbers[i];
            numbers[i]=numbers[random_index];
            numbers[random_index]=temp;
        }
    }
    /**
    <p>数组去重排序</p><br>
    计算一个整数数组中合并重复元素的数组，并按升序排列。
    @param numbers 整数数组。
    @return 合并重复元素后，升序排列的数组。
    */
    public static int[] distinct_sort_new(int numbers[])
    {
        int temp[]=new int[numbers.length];
        System.arraycopy(numbers,0,temp,0,numbers.length);
        sort.radix(temp);
        int same_count=0;
        for(int i=1;i<temp.length;i++)
        {
            if(temp[i]!=temp[i-1])
            {
                temp[i-same_count]=temp[i];
            }
            else
            {
                same_count++;
            }
        }
        int result[]=new int[temp.length-same_count];
        for(int i=0;i<result.length;i++)
        {
            result[i]=temp[i];
        }
        return result;
    }
    /**
    <p>数组去重排序</p><br>
    <p>此方法会修改输入的数据。</p><br>
    合并整数数组中所有重复的元素，并按升序排列。<br>
    合并后若在数组末尾产生空位，则填充<code>Integer.MIN_VALUE</code>。
    @param numbers 整数数组。
    @return 被合并的元素数量。
    */
    public static int distinct_sort_local(int numbers[])
    {
        sort.radix(numbers);
        int same_count=0;
        for(int i=1;i<numbers.length;i++)
        {
            if(numbers[i]!=numbers[i-1])
            {
                numbers[i-same_count]=numbers[i];
            }
            else
            {
                same_count++;
            }
        }
        for(int i=numbers.length-same_count;i<numbers.length;i++)
        {
            numbers[i]=Integer.MIN_VALUE;
        }
        return same_count;
    }
    /**
    <p>最大值</p><br>
    计算多个整数中的最大值。
    @param numbers 多个整数。
    @return 多个整数中的最大值。
    */
    public static int max(int... numbers)
    {
        int maximum=Integer.MIN_VALUE;
        for(int number:numbers)
        {
            if(maximum<number)
            {
                maximum=number;
            }
        }
        return maximum;
    }
    /**
    <p>最大值</p><br>
    计算多个双精度浮点数中的最大值。
    @param numbers 多个双精度浮点数。
    @return 多个双精度浮点数中的最大值。
    */
    public static double max(double... numbers)
    {
        double maximum=Double.MIN_VALUE;
        for(double number:numbers)
        {
            if(maximum<number)
            {
                maximum=number;
            }
        }
        return maximum;
    }
    /**
    <p>最大值索引</p><br>
    计算整数数组中的首个最大值的索引。
    @param numbers 整数数组。
    @return 数组中首个最大值的索引。
    */
    public static int max_index(int numbers[])
    {
        int maximum=Integer.MIN_VALUE;
        int max_index=0;
        for(int i=0;i<numbers.length;i++)
        {
            if(maximum<numbers[i])
            {
                maximum=numbers[i];
                max_index=i;
            }
        }
        return max_index;
    }
    /**
    <p>最大值索引</p><br>
    计算双精度浮点数数组中的首个最大值的索引。
    @param numbers 双精度浮点数数组。
    @return 数组中首个最大值的索引。
    */
    public static int max_index(double numbers[])
    {
        double maximum=Double.MIN_VALUE;
        int max_index=0;
        for(int i=0;i<numbers.length;i++)
        {
            if(maximum<numbers[i])
            {
                maximum=numbers[i];
                max_index=i;
            }
        }
        return max_index;
    }
    /**
    <p>最小值</p><br>
    计算多个整数中的最小值。
    @param numbers 多个整数。
    @return 多个整数中的最小值。
    */
    public static int min(int... numbers)
    {
        int minimum=Integer.MAX_VALUE;
        for(int number:numbers)
        {
            if(minimum>number)
            {
                minimum=number;
            }
        }
        return minimum;
    }
    /**
    <p>最小值</p><br>
    计算多个双精度浮点数中的最小值。
    @param numbers 多个双精度浮点数。
    @return 多个双精度浮点数中的最小值。
    */
    public static double min(double... numbers)
    {
        double minimum=Double.MAX_VALUE;
        for(double number:numbers)
        {
            if(minimum>number)
            {
                minimum=number;
            }
        }
        return minimum;
    }
    /**
    <p>最小值索引</p><br>
    计算整数数组中的首个最小值的索引。
    @param numbers 整数数组。
    @return 数组中首个最小值的索引。
    */
    public static int min_index(int numbers[])
    {
        int minimum=Integer.MAX_VALUE;
        int min_index=0;
        for(int i=0;i<numbers.length;i++)
        {
            if(minimum>numbers[i])
            {
                minimum=numbers[i];
                min_index=i;
            }
        }
        return min_index;
    }
    /**
    <p>最小值索引</p><br>
    计算双精度浮点数数组中的首个最小值的索引。
    @param numbers 双精度浮点数数组。
    @return 数组中首个最小值的索引。
    */
    public static int min_index(double numbers[])
    {
        double minimum=Double.MAX_VALUE;
        int min_index=0;
        for(int i=0;i<numbers.length;i++)
        {
            if(minimum>numbers[i])
            {
                minimum=numbers[i];
                min_index=i;
            }
        }
        return min_index;
    }
    /**
    <p>总和</p><br>
    计算多个整数的总和。
    @param numbers 多个整数。
    @return 多个整数的总和。
    */
    public static int sum(int... numbers)
    {
        int total=0;
        for(int number:numbers)
        {
            total+=number;
        }
        return total;
    }
    /**
    <p>总和</p><br>
    计算多个双精度浮点数的总和。
    @param numbers 多个双精度浮点数。
    @return 多个双精度浮点数的总和。
    */
    public static double sum(double... numbers)
    {
        double total=0;
        for(double number:numbers)
        {
            total+=number;
        }
        return total;
    }
    /**
    <p>平均数</p><br>
    计算多个整数的平均值。
    @param numbers 多个整数。
    @return 多个整数的平均值。
    */
    public static double average(int... numbers)
    {
        return (double)sum(numbers)/numbers.length;
    }
    /**
    <p>平均数</p><br>
    计算多个双精度浮点数的平均值。
    @param numbers 多个双精度浮点数。
    @return 多个双精度浮点数的平均值。
    */
    public static double average(double... numbers)
    {
        return sum(numbers)/numbers.length;
    }
    /**
    <p>加权平均数</p><br>
    计算整数数组的加权平均值。
    @param numbers 整数数组。
    @param weights 整型权重数组。
    @return 整数数组的加权平均值。
    */
    public static double weighted_average(int numbers[],int weights[])
    {
        double total=0;
        for(int i=0;i<numbers.length;i++)
        {
            total+=numbers[i]*weights[i];
        }
        return total/sum(weights);
    }
    /**
    <p>加权平均数</p><br>
    计算整数数组的加权平均值。
    @param numbers 整数数组。
    @param weights 双精度浮点型权重数组。
    @return 整数数组的加权平均值。
    */
    public static double weighted_average(int numbers[],double weights[])
    {
        double total=0;
        for(int i=0;i<numbers.length;i++)
        {
            total+=numbers[i]*weights[i];
        }
        return total/sum(weights);
    }
    /**
    <p>加权平均数</p><br>
    计算双精度浮点数数组的加权平均值。
    @param numbers 双精度浮点数数组。
    @param weights 整型权重数组。
    @return 双精度浮点数数组的加权平均值。
    */
    public static double weighted_average(double numbers[],int weights[])
    {
        double total=0;
        for(int i=0;i<numbers.length;i++)
        {
            total+=numbers[i]*weights[i];
        }
        return total/sum(weights);
    }
    /**
    <p>加权平均数</p><br>
    计算双精度浮点数数组的加权平均值。
    @param numbers 双精度浮点数数组。
    @param weights 双精度浮点型权重数组。
    @return 双精度浮点数数组的加权平均值。
    */
    public static double weighted_average(double numbers[],double weights[])
    {
        double total=0;
        for(int i=0;i<numbers.length;i++)
        {
            total+=numbers[i]*weights[i];
        }
        return total/sum(weights);
    }
    /**
    <p>中位数</p><br>
    计算多个整数的中位数。
    @param numbers 多个整数。
    @return 多个整数的中位数。
    */
    public static double median(int... numbers)
    {
        int temp[]=new int[numbers.length];
        System.arraycopy(numbers,0,temp,0,numbers.length);
        sort.radix(temp);
        if(temp.length%2==1)
        {
            return temp[temp.length/2];
        }
        else
        {
            return (temp[temp.length/2]+temp[temp.length/2-1])/2.0;
        }
    }
    /**
    <p>中位数</p><br>
    计算多个双精度浮点数的中位数。
    @param numbers 多个双精度浮点数。
    @return 多个双精度浮点数的中位数。
    */
    public static double median(double... numbers)
    {
        double temp[]=new double[numbers.length];
        System.arraycopy(numbers,0,temp,0,numbers.length);
        sort.quick_dual_pivot(temp);
        if(temp.length%2==1)
        {
            return temp[temp.length/2];
        }
        else
        {
            return (temp[temp.length/2]+temp[temp.length/2-1])/2;
        }
    }
    /**
    <p>众数</p><br>
    计算多个整数的众数。
    @param numbers 多个整数。
    @return 多个整数的众数。
    */
    public static int[] mode(int... numbers)
    {
        int temp[]=new int[numbers.length];
        System.arraycopy(numbers,0,temp,0,numbers.length);
        sort.radix(temp);
        int count=0,count_max=0;
        int unique[]=new int[temp.length];
        int i=0,j=0,pin=0;
        for(;i<temp.length;i++)
        {
            if(temp[i]!=temp[j])
            {
                count=i-j;
                if(count_max<count)
                {
                    count_max=count;
                    for(pin--;pin>=0;pin--)
                    {
                        unique[pin]=0;
                    }
                }
                if(count==count_max)
                {
                    unique[pin++]=temp[j];
                }
                j=i;
            }
        }
        count=temp.length-j;
        if(count_max<count)
        {
            count_max=count;
            for(pin--;pin>=0;pin--)
            {
                unique[pin]=0;
            }
        }
        if(count==count_max)
        {
            unique[pin++]=temp[j];
        }
        int result[]=new int[pin];
        for(pin--;pin>=0;pin--)
        {
            result[pin]=unique[pin];
        }
        return result;
    }
    /**
    <p>方差</p><br>
    计算多个整数的方差。
    @param numbers 多个整数。
    @return 多个整数的方差。
    */
    public static double variance(int... numbers)
    {
        double var=0;
        double avg=average(numbers);
        for(double number:numbers)
        {
            var+=(number-avg)*(number-avg);
        }
        return var/(numbers.length-1);
    }
    /**
    <p>方差</p><br>
    计算多个双精度浮点数的方差。
    @param numbers 多个双精度浮点数。
    @return 多个双精度浮点数的方差。
    */
    public static double variance(double... numbers)
    {
        double var=0;
        double avg=average(numbers);
        for(double number:numbers)
        {
            var+=(number-avg)*(number-avg);
        }
        return var/(numbers.length-1);
    }
    /**
    <p>方差平均值</p><br>
    计算多个整数的方差平均值。
    @param numbers 多个整数。
    @return 多个整数的方差平均值。
    */
    public static double variance_average(int... numbers)
    {
        return variance(numbers)/numbers.length;
    }
    /**
    <p>方差平均值</p><br>
    计算多个双精度浮点数的方差平均值。
    @param numbers 多个双精度浮点数。
    @return 多个双精度浮点数的方差平均值。
    */
    public static double variance_average(double... numbers)
    {
        return variance(numbers)/numbers.length;
    }
    /**
    <p>标准差</p><br>
    计算多个整数的标准差。
    @param numbers 多个整数。
    @return 多个整数的标准差。
    */
    public static double standard_deviation(int... numbers)
    {
        return Math.sqrt(variance(numbers));
    }
    /**
    <p>标准差</p><br>
    计算多个双精度浮点数的标准差。
    @param numbers 多个双精度浮点数。
    @return 多个双精度浮点数的标准差。
    */
    public static double standard_deviation(double... numbers)
    {
        return Math.sqrt(variance(numbers));
    }
    /**
    <p>标准差平均值</p><br>
    计算多个整数的标准差平均值。
    @param numbers 多个整数。
    @return 多个整数的标准差平均值。
    */
    public static double standard_deviation_average(int... numbers)
    {
        return Math.sqrt(variance(numbers)/numbers.length);
    }
    /**
    <p>标准差平均值</p><br>
    计算多个双精度浮点数的标准差平均值。
    @param numbers 多个双精度浮点数。
    @return 多个双精度浮点数的标准差平均值。
    */
    public static double standard_deviation_average(double... numbers)
    {
        return Math.sqrt(variance(numbers)/numbers.length);
    }
    /**
    <p>线性回归</p><br>
    计算一组点的一元线性回归。
    @param coordinates_xn_yn 点坐标(x1,y1,x2,y2,...)。
    @return 线性回归的参数，第一个元素为截距(a)，第二个元素为斜率(b)。
    */
    public static double[] linear_regression(double... coordinates_xn_yn)
    {
        int n=coordinates_xn_yn.length/2;
        double now_x=0,now_y=0;
        double sum_xy=0,sum_x=0,sum_y=0,sum_xx=0;
        for(int i=0;i<n;i++)
        {
            now_x=coordinates_xn_yn[i*2];
            now_y=coordinates_xn_yn[i*2+1];
            sum_x+=now_x;
            sum_y+=now_y;
            sum_xy+=now_x*now_y;
            sum_xx+=now_x*now_x;
        }
        double b=(n*sum_xy-sum_x*sum_y)/(n*sum_xx-sum_x*sum_x);
        double a=sum_y/n-b*sum_x/n;
        return new double[]{a,b};
    }
    /**
    <p>四则运算</p><br>
    计算一个四则运算表达式的结果(仅支持+、-、*、/、())。
    @param expression 四则运算表达式，仅支持+、-、*、/、()。
    @return 表达式的结果。
    */
    public static double calculate(String expression)
    {
        int length=expression.length();
        char in_order[]=expression.toCharArray();
        char operators[]=new char[length/2+3];
        double numbers[]=new double[length*2/3+3];
        int top_operators=0;
        int top_numbers=0;
        boolean has_operator=true;
        for(int i=0;i<length;i++)
        {
            char now=in_order[i];
            if(now!=' ')
            {
                if(now=='(')
                {
                    operators[top_operators++]=now;
                    has_operator=true;
                }
                else if(now==')')
                {
                    while(top_operators>0&&operators[top_operators-1]!='(')
                    {
                        char operator=operators[--top_operators];
                        if(operator=='u')
                        {
                            numbers[top_numbers-1]*=-1;
                        }
                        else
                        {
                            double number2=numbers[--top_numbers];
                            double number1=numbers[--top_numbers];
                            switch(operator)
                            {
                                case '+':
                                    numbers[top_numbers++]=number1+number2;
                                    break;
                                case '-':
                                    numbers[top_numbers++]=number1-number2;
                                    break;
                                case '*':
                                    numbers[top_numbers++]=number1*number2;
                                    break;
                                case '/':
                                    numbers[top_numbers++]=number1/number2;
                                    break;
                            }
                        }
                    }
                    top_operators--;
                }
                else if(now=='+'||now=='-')
                {
                    if(now=='-'&&has_operator)
                    {
                        operators[top_operators++]='u';
                    }
                    else
                    {
                        while(top_operators>0&&operators[top_operators-1]!='(')
                        {
                            char operator=operators[--top_operators];
                            if(operator=='u')
                            {
                                numbers[top_numbers-1]*=-1;
                            }
                            else
                            {
                                double number2=numbers[--top_numbers];
                                double number1=numbers[--top_numbers];
                                switch(operator)
                                {
                                    case '+':
                                        numbers[top_numbers++]=number1+number2;
                                        break;
                                    case '-':
                                        numbers[top_numbers++]=number1-number2;
                                        break;
                                    case '*':
                                        numbers[top_numbers++]=number1*number2;
                                        break;
                                    case '/':
                                        numbers[top_numbers++]=number1/number2;
                                        break;
                                }
                            }
                        }
                        operators[top_operators++]=now;
                    }
                    has_operator=true;
                }
                else if(now=='*'||now=='/')
                {
                    while(top_operators>0&&(operators[top_operators-1]=='*'||operators[top_operators-1]=='/'||operators[top_operators-1]=='u'))
                    {
                        char operator=operators[--top_operators];
                        if(operator=='u')
                        {
                            numbers[top_numbers-1]*=-1;
                        }
                        else
                        {
                            double number2=numbers[--top_numbers];
                            double number1=numbers[--top_numbers];
                            if(operator=='*')
                            {
                                numbers[top_numbers++]=number1*number2;
                            }
                            else
                            {
                                numbers[top_numbers++]=number1/number2;
                            }
                        }
                    }
                    operators[top_operators++]=now;
                    has_operator=true;
                }
                else
                {
                    boolean has_dot=false;
                    double number=0;
                    double dot_bit=1;
                    while((now==' '||now=='.'||now>='0'&&now<='9'))
                    {
                        if(now!=' ')
                        {
                            if(now=='.')
                            {
                                has_dot=true;
                            }
                            else
                            {
                                if(has_dot)
                                {
                                    dot_bit*=10;
                                    number+=(now-'0')/dot_bit;
                                }
                                else
                                {
                                    number=number*10+(now-'0');
                                }
                            }
                        }
                        if(i<length-1)
                        {
                            now=in_order[++i];
                        }
                        else
                        {
                            i++;
                            break;
                        }
                    }
                    numbers[top_numbers++]=number;
                    has_operator=false;
                    i--;
                }
            }
        }
        for(top_operators--;top_operators>=0;top_operators--)
        {
            char operator=operators[top_operators];
            if(operator=='u')
            {
                numbers[top_numbers-1]*=-1;
            }
            else
            {
                double number2=numbers[--top_numbers];
                double number1=numbers[--top_numbers];
                switch(operator)
                {
                    case '+':
                        numbers[top_numbers++]=number1+number2;
                        break;
                    case '-':
                        numbers[top_numbers++]=number1-number2;
                        break;
                    case '*':
                        numbers[top_numbers++]=number1*number2;
                        break;
                    case '/':
                        numbers[top_numbers++]=number1/number2;
                        break;
                }
            }
        }
        return numbers[0];
    }
    /**
    <p>多边形周长</p><br>
    计算一个多边形的周长。
    @param coordinates_xn_yn_ccw 多边形的顶点坐标，按逆时针方向给出(x1,y1,x2,y2,...)。
    @return 多边形的周长。<br>
    若多边形的顶点不足2个，或坐标不完整，则返回0.0。
    */
    public static double polygon_perimeter(double... coordinates_xn_yn_ccw)
    {
        if(coordinates_xn_yn_ccw.length>=4&&coordinates_xn_yn_ccw.length%2==0)
        {
            double temp[]=new double[coordinates_xn_yn_ccw.length];
            System.arraycopy(coordinates_xn_yn_ccw,0,temp,0,coordinates_xn_yn_ccw.length);
            double result=0;
            for(int i=0;i+3<temp.length;i+=2)
            {
                result+=Math.sqrt((temp[i+2]-temp[i])*(temp[i+2]-temp[i])+(temp[i+3]-temp[i+1])*(temp[i+3]-temp[i+1]));
            }
            result+=Math.sqrt((temp[temp.length-2]-temp[0])*(temp[temp.length-2]-temp[0])+(temp[temp.length-1]-temp[1])*(temp[temp.length-1]-temp[1]));
            return result;
        }
        else
        {
            return 0.0;
        }
    }
    /**
    <p>多边形面积</p><br>
    计算一个多边形的面积。
    @param coordinates_xn_yn_ccw 多边形的顶点坐标，按逆时针方向给出(x1,y1,x2,y2,...)。
    @return 多边形的面积。<br>
    若多边形的顶点不足3个，或坐标不完整，则返回0.0。
    */
    public static double polygon_area(double... coordinates_xn_yn_ccw)
    {
        if(coordinates_xn_yn_ccw.length>=6&&coordinates_xn_yn_ccw.length%2==0)
        {
            double temp[]=new double[coordinates_xn_yn_ccw.length];
            System.arraycopy(coordinates_xn_yn_ccw,0,temp,0,coordinates_xn_yn_ccw.length);
            double median=median(temp);
            for(int i=0;i<temp.length;i++)
            {
                temp[i]-=median;
            }
            double sum1=0,sum2=0;
            for(int i=0;i+3<temp.length;i+=2)
            {
                sum1+=temp[i]*temp[i+3];
                sum2+=temp[i+1]*temp[i+2];
            }
            sum1+=temp[temp.length-2]*temp[1];
            sum2+=temp[temp.length-1]*temp[0];
            return Math.abs(sum1-sum2)/2;
        }
        else
        {
            return 0.0;
        }
    }
    /**
    <p>矩阵乘法</p><br>
    计算两个矩阵的乘积。
    @param factor_left 左矩阵。
    @param factor_right 右矩阵。
    @return 左矩阵乘右矩阵的结果。
    */
    public static int[][] matrix_multiply(int factor_left[][],int factor_right[][])
    {
        if(factor_left[0].length==factor_right.length)
        {
            int result[][]=new int[factor_left.length][factor_right[0].length];
            int temp;
            for(int i=0;i<result.length;i++)
            {
                for(int j=0;j<result[0].length;j++)
                {
                    temp=0;
                    for(int k=0;k<factor_left[0].length;k++)
                    {
                        temp+=factor_left[i][k]*factor_right[k][j];
                    }
                    result[i][j]=temp;
                }
            }
            return result;
        }
        else
        {
            return null;
        }
    }
    /**
    <p>数独验证</p><br>
    检查一个9*9的数独是否有效。
    @param board 数独板，0表示空单元格。
    @return 如果数独有效则返回<code>true</code>，否则返回<code>false</code>。
    */
    public static boolean sudoku_valid(int board[][])
    {
        int exist[]=new int[9];
        for(int y=0;y<9;y++)
        {
            for(int x=0;x<9;x++)
            {
                int now=board[y][x]-1;
                if(now>=0)
                {
                    int position=1<<x|1<<y+9|1<<y/3*3+x/3+18;
                    if((exist[now]&position)!=0)
                    {
                        return false;
                    }
                    else
                    {
                        exist[now]|=position;
                    }
                }
            }
        }
        return true;
    }
    private static boolean backtrace_sudoku_solve(int board[][],int count,int row[],int column[],int cell[][])
    {
        if(count==0)
        {
            return true;
        }
        int x=0,y=0;
        int min=10;
        for(int y_temp=0;y_temp<9;y_temp++)
        {
            for(int x_temp=0;x_temp<9;x_temp++)
            {
                if(board[y_temp][x_temp]==0)
                {
                    int available_mask=~(row[y_temp]|column[x_temp]|cell[y_temp/3][x_temp/3]);
                    int temp_count=0;
                    int test=1;
                    for(int i=0;i<9;i++)
                    {
                        if((available_mask&test)!=0)
                        {
                            temp_count++;
                        }
                        test<<=1;
                    }
                    if(temp_count<min)
                    {
                        min=temp_count;
                        x=x_temp;
                        y=y_temp;
                    }
                }
            }
        }
        int available_mask=~(row[y]|column[x]|cell[y/3][x/3]);
        for(int index=0;index<9;index++)
        {
            if((available_mask&(1<<index))!=0)
            {
                row[y]=row[y]|(1<<index);
                column[x]=column[x]|(1<<index);
                cell[y/3][x/3]=cell[y/3][x/3]|(1<<index);
                board[y][x]=index+1;
                if(backtrace_sudoku_solve(board,count-1,row,column,cell))
                {
                    return true;
                }
                board[y][x]=0;
                row[y]=row[y]&~(1<<index);
                column[x]=column[x]&~(1<<index);
                cell[y/3][x/3]=cell[y/3][x/3]&~(1<<index);
            }
        }
        return false;
    }
    /**
    <p>数独求解</p><br>
    <p>此方法会修改输入的数据。</p><br>
    解一个9*9的数独。
    @param board 数独板，0表示空单元格。
    @return 如果数独有解则返回<code>true</code>，并将解写入<code>board</code>；否则返回<code>false</code>，<code>board</code>保持不变。
    */
    public static boolean sudoku_solve(int board[][])
    {
        if(!sudoku_valid(board))
        {
            return false;
        }
        int row[]=new int[9];
        int column[]=new int[9];
        int cell[][]=new int[3][3];
        int count=0;
        for(int y=0;y<9;y++)
        {
            for(int x=0;x<9;x++)
            {
                int now=board[y][x]-1;
                if(now==-1)
                {
                    count++;
                }
                else
                {
                    row[y]|=1<<now;
                    column[x]|=1<<now;
                    cell[y/3][x/3]|=1<<now;
                }
            }
        }
        return backtrace_sudoku_solve(board,count,row,column,cell);
    }
}