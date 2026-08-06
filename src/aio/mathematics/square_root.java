package aio.mathematics;
/**
<p>平方根类</p><br>
用于表示平方根及对平方根的操作。
*/
public class square_root implements Comparable<square_root>
{
    /**
    <p>系数</p>
    */
    public int coefficient;
    /**
    <p>底数</p>
    */
    public int base;
    /**
    <p>构造方法</p><br>
    构造一个平方根对象。
    @param number 平方根的底数。
    */
    public square_root(int number)
    {
        coefficient=1;
        for(int i=2;i*i<=number;i++)
        {
            if(number%(i*i)==0)
            {
                coefficient*=i;
                number/=i*i;
                i--;
            }
        }
        base=number;
    }
    /**
    <p>开平方运算</p><br>
    计算一个整数的算数平方根的系数及底数。
    @param number 算数平方根的底数。
    @return 一个包含系数及底数的整型数组<br>
    第一个元素为系数，第二个元素为底数。
    */
    public static int[] sqrt(int number)
    {
        int sqrt[]={1,0};
        for(int i=2;i*i<=number;i++)
        {
            if(number%(i*i)==0)
            {
                sqrt[0]*=i;
                number/=i*i;
                i--;
            }
        }
        sqrt[1]=number;
        return sqrt;
    }
    /**
    <p>开平方运算</p><br>
    计算一个整数的算术平方根。
    @param number 算术平方根的底数。
    @return 算数平方根的字符串表示。
    */
    public static String string(int number)
    {
        int coefficient=1;
        for(int i=2;i*i<=number;i++)
        {
            if(number%(i*i)==0)
            {
                coefficient*=i;
                number/=i*i;
                i--;
            }
        }
        int base=number;
        if(base==1)
        {
            return ""+coefficient;
        }
        else if(coefficient==1)
        {
            return "√"+base;
        }
        else
        {
            return ""+coefficient+"√"+base;
        }
    }
    /**
    <p>转小数</p><br>
    计算算术平方根的小数形式。
    @return 算术平方根的小数形式。
    */
    public double value()
    {
        return coefficient*Math.sqrt(base);
    }
    /**
    <p>字符串表示</p><br>
    @return 平方根的字符串表示。
    */
    public String toString()
    {
        if(base==1)
        {
            return ""+coefficient;
        }
        else if(coefficient==1)
        {
            return "√"+base;
        }
        else
        {
            return ""+coefficient+"√"+base;
        }
    }
    /**
    <p>比较</p><br>
    比较两个平方根数值的大小。
    @param another 要比较的平方根对象。
    @return 当前平方根与指定平方根的数值比较结果。
    <ul>
        <li>=0：当前平方根与指定平方根相等。</li>
        <li>&gt;0：当前平方根大于指定平方根。</li>
        <li>&lt;0：当前平方根小于指定平方根。</li>
    </ul>
    */
    public int compareTo(square_root another)
    {
        if(coefficient==another.coefficient&&base==another.base)
        {
            return 0;
        }
        else
        {
            int square_result=coefficient*coefficient*base*base-another.coefficient*another.coefficient*another.base*another.base;
            if(square_result>0)
            {
                return 1;
            }
            else
            {
                return -1;
            }
        }
    }
}