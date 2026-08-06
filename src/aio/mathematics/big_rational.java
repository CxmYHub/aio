package aio.mathematics;
import java.util.HashMap;
/**
<p>高精度有理数类</p><br>
有理数，即分数，包含整数、有限小数和无限循环小数。<br>
任何有理数都可表示为分数。<br>
本高精度有理数以两个高精度整数对象实现。<br>
两个高精度整数对象分别表示分子和分母。<br>
当分母为<code>null</code>时，为整数对象。<br>
字符串输出默认为分数&nbsp;&nbsp;小数格式，可通过设置<code>mode</code>改变输出格式：<br>
<ul>
    <li><code>mode&gt;0</code>：小数格式，例如<code>"0.5"</code>。</li>
    <li><code>mode=0</code>：分数&nbsp;&nbsp;小数格式，例如<code>"1/2&nbsp;&nbsp;0.5"</code>。</li>
    <li><code>mode&lt;0</code>：分数格式，例如<code>"1/2"</code>。</li>
</ul>
*/
public class big_rational
{
    /**
    <p>分子</p><br>
    分子高精度整数对象。
    */
    public big_integer numerator;
    /**
    <p>分母</p><br>
    分母高精度整数对象。
    */
    public big_integer denominator;
    /**
    <p>输出模式</p><br>
    <ul>
        <li>&gt;0：小数格式，例如<code>"0.5"</code>。</li>
        <li>=0：分数&nbsp;&nbsp;小数格式，例如<code>"1/2&nbsp;&nbsp;0.5"</code>。</li>
        <li>&lt;0：分数格式，例如<code>"1/2"</code>。</li>
    </ul>
    */
    public int mode=0;
    /**
    <p>约分</p><br>
    <p>此方法会修改调用对象。</p><br>
    对有理数进行约分。
    @return 分子和分母的最大公因数
    */
    public big_integer reduce()
    {
        big_integer gcd=big_integer.gcd(numerator,denominator);
        numerator=big_integer.divide(numerator,gcd)[0];
        denominator=big_integer.divide(denominator,gcd)[0];
        if(denominator.size==1&&denominator.number[0]==1)
        {
            numerator.sign*=denominator.sign;
            denominator=null;
        }
        return gcd;
    }
    /**
    <p>构造方法</p><br>
    通过有理数小数形式字符串构造高精度有理数对象。
    @param rational_string 字符串表示的小数形式有理数。<br>
    用括号表示循环节，例如<code>0.(3)</code>表示0.333333...。
    */
    public big_rational(String rational_string)
    {
        int length=rational_string.length();
        StringBuilder unloop_builder=new StringBuilder();
        StringBuilder loop_builder=new StringBuilder();
        boolean is_negative=false;
        int offset=0;
        if(rational_string.charAt(0)=='-')
        {
            is_negative=true;
            offset++;
        }
        else if(rational_string.charAt(0)=='+')
        {
            offset++;
        }
        int dot_index=-1;
        int loop_start=-1;
        int loop_end=-1;
        for(int i=offset;i<length;i++)
        {
            char now=rational_string.charAt(i);
            if(now=='.')
            {
                dot_index=i-offset;
                offset++;
            }
            else if(now=='(')
            {
                loop_start=i-offset;
                loop_builder.append(unloop_builder);
                offset++;
            }
            else if(now==')')
            {
                loop_end=i-offset;
                offset++;
            }
            else if(loop_start==-1)
            {
                unloop_builder.append(now);
            }
            else
            {
                loop_builder.append(now);
            }
        }
        length-=offset;
        if(dot_index==-1)
        {
            numerator=new big_integer(unloop_builder.toString());
            numerator.sign=is_negative?-1:1;
            denominator=null;
        }
        else
        {
            StringBuilder denominator_builder=new StringBuilder();
            if(loop_start==-1)
            {
                numerator=new big_integer(unloop_builder.toString());
                numerator.sign=is_negative?-1:1;
                denominator_builder.append("1");
                for(int i=dot_index;i<length;i++)
                {
                    denominator_builder.append("0");
                }
                denominator=new big_integer(denominator_builder.toString());
            }
            else
            {
                big_integer unloop=new big_integer(unloop_builder.toString());
                big_integer loop=new big_integer(loop_builder.toString());
                numerator=big_integer.subtract(loop,unloop);
                numerator.sign=is_negative?-1:1;
                for(int i=loop_start;i<loop_end;i++)
                {
                    denominator_builder.append("9");
                }
                for(int i=dot_index;i<loop_start;i++)
                {
                    denominator_builder.append("0");
                }
                denominator=new big_integer(denominator_builder.toString());
            }
            reduce();
        }
    }
    /**
    <p>构造方法</p><br>
    通过有理数分数形式字符串和字符串输出模式构造高精度有理数对象。
    @param numerator_string 字符串表示的分子。
    @param denominator_string 字符串表示的分母。
    @param mode 字符串输出模式。<br>
    <ul>
        <li>&gt;0：小数格式，例如<code>"0.5"</code>。</li>
        <li>=0：分数&nbsp;&nbsp;小数格式，例如<code>"1/2&nbsp;&nbsp;0.5"</code>。</li>
        <li>&lt;0：分数格式，例如<code>"1/2"</code>。</li>
    </ul>
    */
    public big_rational(String numerator_string,String denominator_string,int mode)
    {
        this.mode=mode;
        numerator=new big_integer(numerator_string);
        boolean has_denominator=false;
        if(denominator_string!=null)
        {
            int denominator_length=denominator_string.length();
            for(int i=0;i<denominator_length;i++)
            {
                char now=denominator_string.charAt(i);
                if(now>='1'&&now<='9')
                {
                    if(i<denominator_length-1||now>='2')
                    {
                        has_denominator=true;
                    }
                    break;
                }
            }
            if(has_denominator)
            {
                denominator=new big_integer(denominator_string);
                numerator.sign*=denominator.sign;
                denominator.sign=1;
                reduce();
            }
        }
    }
    /**
    <p>构造方法</p><br>
    通过有理数分数形式字符串构造高精度有理数对象。
    @param numerator_string 字符串表示的分子。
    @param denominator_string 字符串表示的分母。
    */
    public big_rational(String numerator_string,String denominator_string)
    {
        this(numerator_string,denominator_string,0);
    }
    /**
    <p>构造方法</p><br>
    通过有理数分子和分母以及字符串输出模式构造高精度有理数对象。
    @param numerator 整数表示的分子。
    @param denominator 整数表示的分母。
    @param mode 字符串输出模式。<br>
    <ul>
        <li>&gt;0：小数格式，例如<code>"0.5"</code>。</li>
        <li>=0：分数&nbsp;&nbsp;小数格式，例如<code>"1/2&nbsp;&nbsp;0.5"</code>。</li>
        <li>&lt;0：分数格式，例如<code>"1/2"</code>。</li>
    </ul>
    */
    public big_rational(int numerator,int denominator,int mode)
    {
        this.mode=mode;
        if(numerator==0)
        {
            this.numerator=new big_integer(0);
            this.denominator=null;
        }
        else
        {
            if(denominator<0)
            {
                numerator=-numerator;
                denominator=-denominator;
            }
            this.numerator=new big_integer(numerator);
            if(denominator<=1)
            {
                this.denominator=null;
            }
            else
            {
                this.denominator=new big_integer(denominator);
                reduce();
            }
        }
    }
    /**
    <p>构造方法</p><br>
    通过有理数分子和分母构造高精度有理数对象。
    @param numerator 整数表示的分子。
    @param denominator 整数表示的分母。
    */
    public big_rational(int numerator,int denominator)
    {
        this(numerator,denominator,0);
    }
    /**
    <p>构造方法</p><br>
    通过高精度整数分子和分母以及字符串输出模式构造高精度有理数对象。
    @param numerator 高精度整数表示的分子。
    @param denominator 高精度整数表示的分母。
    @param mode 字符串输出模式。<br>
    <ul>
        <li>&gt;0：小数格式，例如<code>"0.5"</code>。</li>
        <li>=0：分数&nbsp;&nbsp;小数格式，例如<code>"1/2&nbsp;&nbsp;0.5"</code>。</li>
        <li>&lt;0：分数格式，例如<code>"1/2"</code>。</li>
    </ul>
    */
    public big_rational(big_integer numerator,big_integer denominator,int mode)
    {
        this.mode=mode;
        this.numerator=new big_integer(numerator.number,numerator.sign);
        if(denominator==null)
        {
        }
        else if(denominator.size==0||denominator.size==1&&denominator.number[0]==1)
        {
            this.numerator.sign*=denominator.sign;
            this.denominator=null;
        }
        else
        {
            this.numerator.sign*=denominator.sign;
            this.denominator=new big_integer(denominator.number,1);
            reduce();
        }
    }
    /**
    <p>构造方法</p><br>
    通过高精度整数分子和分母构造高精度有理数对象。
    @param numerator 高精度整数表示的分子。
    @param denominator 高精度整数表示的分母。
    */
    public big_rational(big_integer numerator,big_integer denominator)
    {
        this(numerator,denominator,0);
    }
    /**
    <p>全参构造方法</p><br>
    通过高精度整数分子和分母以及字符串输出模式构造高精度有理数对象。<br>
    本构造方法会直接使用输入的高精度整数、符号和模式，不创建新的高精度整数对象。
    @param numerator 高精度整数表示的分子。
    @param denominator 高精度整数表示的分母。
    @param sign 有理数的符号。
    @param mode 字符串输出模式。<br>
    <ul>
        <li>&gt;0：小数格式，例如<code>"0.5"</code>。</li>
        <li>=0：分数&nbsp;&nbsp;小数格式，例如<code>"1/2&nbsp;&nbsp;0.5"</code>。</li>
        <li>&lt;0：分数格式，例如<code>"1/2"</code>。</li>
    </ul>
    */
    public big_rational(big_integer numerator,big_integer denominator,int sign,int mode)
    {
        this.mode=mode;
        numerator.sign=sign;
        this.numerator=numerator;
        if(denominator==null||denominator.size==0||denominator.size==1&&denominator.number[0]==1)
        {
            this.denominator=null;
        }
        else
        {
            denominator.sign=1;
            this.denominator=denominator;
            reduce();
        }
    }
    /**
    <p>通分</p><br>
    <p>此方法会修改输入的数据。</p><br>
    通分两个有理数。
    @param rational1 第一个有理数对象。
    @param rational2 第二个有理数对象。
    @return 一个高精度整数数组：
    <ol>
        <li>第一个有理数的通分乘数。</li>
        <li>第二个有理数的通分乘数。</li>
    </ol>
    */
    public static big_integer[] common_denominator(big_rational rational1,big_rational rational2)
    {
        if(rational1.denominator==null&&rational2.denominator==null)
        {
            return new big_integer[]{new big_integer(1),new big_integer(1)};
        }
        else if(rational1.denominator==null)
        {
            rational1.denominator=new big_integer(rational2.denominator.number,1);
            rational1.numerator=big_integer.multiply(rational1.numerator,rational1.denominator);
            return new big_integer[]{new big_integer(rational2.denominator.number,1),new big_integer(1)};
        }
        else if(rational2.denominator==null)
        {
            rational2.denominator=new big_integer(rational1.denominator.number,1);
            rational2.numerator=big_integer.multiply(rational2.numerator,rational2.denominator);
            return new big_integer[]{new big_integer(1),new big_integer(rational1.denominator.number,1)};
        }
        else if(rational1.denominator.compareTo(rational2.denominator)==0)
        {
            return new big_integer[]{new big_integer(1),new big_integer(1)};
        }
        else
        {
            big_integer common_denominator=big_integer.lcm(rational1.denominator,rational2.denominator);
            big_integer multiplier1=big_integer.divide(common_denominator,rational1.denominator)[0];
            big_integer multiplier2=big_integer.divide(common_denominator,rational2.denominator)[0];
            rational1.denominator=common_denominator;
            rational2.denominator=new big_integer(common_denominator.number,1);
            rational1.numerator=big_integer.multiply(rational1.numerator,multiplier1);
            rational2.numerator=big_integer.multiply(rational2.numerator,multiplier2);
            return new big_integer[]{multiplier1,multiplier2};
        }
    }
    /**
    <p>加法运算</p><br>
    计算两个有理数的和 <code>addend1</code>+<code>addend2</code>。
    @param addend1 第一个有理数对象。
    @param addend2 第二个有理数对象。
    @return 两个有理数的和。
    */
    public static big_rational add(big_rational addend1,big_rational addend2)
    {
        if(addend1.denominator==null&&addend2.denominator==null)
        {
            big_integer sum=big_integer.add(addend1.numerator,addend2.numerator);
            return new big_rational(sum,null,sum.sign,addend1.mode==addend2.mode?addend1.mode:0);
        }
        else if(addend1.denominator==null)
        {
            big_integer sum_numerator=big_integer.add(big_integer.multiply(addend1.numerator,addend2.denominator),addend2.numerator);
            big_integer sum_denominator=new big_integer(addend2.denominator.number,1);
            return new big_rational(sum_numerator,sum_denominator,sum_numerator.sign,addend1.mode==addend2.mode?addend1.mode:0);
        }
        else if(addend2.denominator==null)
        {
            big_integer sum_numerator=big_integer.add(addend1.numerator,big_integer.multiply(addend2.numerator,addend1.denominator));
            big_integer sum_denominator=new big_integer(addend1.denominator.number,1);
            return new big_rational(sum_numerator,sum_denominator,sum_numerator.sign,addend1.mode==addend2.mode?addend1.mode:0);
        }
        else
        {
            big_integer common_denominator=big_integer.lcm(addend1.denominator,addend2.denominator);
            big_integer multiplier1=big_integer.divide(common_denominator,addend1.denominator)[0];
            big_integer multiplier2=big_integer.divide(common_denominator,addend2.denominator)[0];
            big_integer sum_numerator=big_integer.add(big_integer.multiply(addend1.numerator,multiplier1),big_integer.multiply(addend2.numerator,multiplier2));
            return new big_rational(sum_numerator,common_denominator,sum_numerator.sign,addend1.mode==addend2.mode?addend1.mode:0);
        }
    }
    /**
    <p>减法运算</p><br>
    计算两个有理数的差 <code>minuend</code>-<code>subtrahend</code>。
    @param minuend 被减数有理数对象。
    @param subtrahend 减数有理数对象。
    @return 两个有理数的差。
    */
    public static big_rational subtract(big_rational minuend,big_rational subtrahend)
    {
        if(minuend.denominator==null&&subtrahend.denominator==null)
        {
            big_integer difference=big_integer.subtract(minuend.numerator,subtrahend.numerator);
            return new big_rational(difference,null,difference.sign,minuend.mode==subtrahend.mode?minuend.mode:0);
        }
        else if(minuend.denominator==null)
        {
            big_integer difference_numerator=big_integer.subtract(big_integer.multiply(minuend.numerator,subtrahend.denominator),subtrahend.numerator);
            big_integer difference_denominator=new big_integer(subtrahend.denominator.number,1);
            return new big_rational(difference_numerator,difference_denominator,difference_numerator.sign,minuend.mode==subtrahend.mode?minuend.mode:0);
        }
        else if(subtrahend.denominator==null)
        {
            big_integer difference_numerator=big_integer.subtract(minuend.numerator,big_integer.multiply(subtrahend.numerator,minuend.denominator));
            big_integer difference_denominator=new big_integer(minuend.denominator.number,1);
            return new big_rational(difference_numerator,difference_denominator,difference_numerator.sign,minuend.mode==subtrahend.mode?minuend.mode:0);
        }
        else
        {
            big_integer common_denominator=big_integer.lcm(minuend.denominator,subtrahend.denominator);
            big_integer multiplier1=big_integer.divide(common_denominator,minuend.denominator)[0];
            big_integer multiplier2=big_integer.divide(common_denominator,subtrahend.denominator)[0];
            big_integer difference_numerator=big_integer.subtract(big_integer.multiply(minuend.numerator,multiplier1),big_integer.multiply(subtrahend.numerator,multiplier2));
            return new big_rational(difference_numerator,common_denominator,difference_numerator.sign,minuend.mode==subtrahend.mode?minuend.mode:0);
        }
    }
    /**
    <p>乘法运算</p><br>
    计算两个有理数的积 <code>factor1</code>*<code>factor2</code>。
    @param factor1 第一个有理数对象。
    @param factor2 第二个有理数对象。
    @return 两个有理数的积。
    */
    public static big_rational multiply(big_rational factor1,big_rational factor2)
    {
        if(factor1.denominator==null&&factor2.denominator==null)
        {
            big_integer product=big_integer.multiply(factor1.numerator,factor2.numerator);
            return new big_rational(product,null,product.sign,factor1.mode==factor2.mode?factor1.mode:0);
        }
        else if(factor1.denominator==null)
        {
            big_integer product_numerator=big_integer.multiply(factor1.numerator,factor2.numerator);
            big_integer product_denominator=new big_integer(factor2.denominator.number,1);
            return new big_rational(product_numerator,product_denominator,product_numerator.sign,factor1.mode==factor2.mode?factor1.mode:0);
        }
        else if(factor2.denominator==null)
        {
            big_integer product_numerator=big_integer.multiply(factor1.numerator,factor2.numerator);
            big_integer product_denominator=new big_integer(factor1.denominator.number,1);
            return new big_rational(product_numerator,product_denominator,product_numerator.sign,factor1.mode==factor2.mode?factor1.mode:0);
        }
        else
        {
            return new big_rational(big_integer.multiply(factor1.numerator,factor2.numerator),big_integer.multiply(factor1.denominator,factor2.denominator),factor1.numerator.sign*factor2.numerator.sign,factor1.mode==factor2.mode?factor1.mode:0);
        }
    }
    /**
    <p>除法运算</p><br>
    计算两个有理数的商 <code>dividend</code>/<code>divisor</code>。
    @param dividend 被除数有理数对象。
    @param divisor 除数有理数对象。
    @return 两个有理数的商。<br>
    若除数为0，则返回<code>null</code>。
    */
    public static big_rational divide(big_rational dividend,big_rational divisor)
    {
        if(divisor.numerator.size==0)
        {
            return null;
        }
        else if(dividend.denominator==null&&divisor.denominator==null)
        {
            return new big_rational(dividend.numerator,divisor.numerator,0);
        }
        else if(dividend.denominator==null)
        {
            big_integer quotient_numerator=big_integer.multiply(dividend.numerator,divisor.denominator);
            big_integer quotient_denominator=new big_integer(divisor.numerator.number,1);
            return new big_rational(quotient_numerator,quotient_denominator,dividend.numerator.sign*divisor.numerator.sign,dividend.mode==divisor.mode?dividend.mode:0);
        }
        else if(divisor.denominator==null)
        {
            big_integer quotient_numerator=new big_integer(dividend.numerator.number,dividend.numerator.sign);
            big_integer quotient_denominator=big_integer.multiply(dividend.denominator,divisor.numerator);
            return new big_rational(quotient_numerator,quotient_denominator,dividend.numerator.sign*divisor.numerator.sign,dividend.mode==divisor.mode?dividend.mode:0);
        }
        else
        {
            return new big_rational(big_integer.multiply(dividend.numerator,divisor.denominator),big_integer.multiply(dividend.denominator,divisor.numerator),dividend.numerator.sign*divisor.numerator.sign,dividend.mode==divisor.mode?dividend.mode:0);
        }
    }
    /**
    <p>幂运算</p><br>
    计算有理数的整数次幂 <code>base</code>^<code>exponent</code>。<br>
    @param base 底数有理数对象。
    @param exponent 指数整数。
    @return 底数的指数次幂。
    */
    public static big_rational power(big_rational base,int exponent)
    {
        if(base.numerator.size==0)
        {
            return exponent>0?new big_rational(0,1,base.mode):null;
        }
        else if(exponent==0)
        {
            return new big_rational(1,1,base.mode);
        }
        else if(exponent==1)
        {
            return new big_rational(base.numerator,base.denominator,base.mode);
        }
        else if(exponent==-1)
        {
            return new big_rational(base.denominator!=null?base.denominator:new big_integer(1),base.numerator,base.mode);
        }
        if(base.denominator==null&&base.numerator.size==1&&base.numerator.number[0]==1)
        {
            big_rational result=new big_rational(exponent%2==0?1:base.numerator.sign,1,base.mode);
            return result;
        }
        boolean is_negative_exponent=exponent<0;
        exponent=is_negative_exponent?-exponent:exponent;
        big_rational result=new big_rational(1,1,base.mode);
        big_rational major=new big_rational(base.numerator,base.denominator,base.mode);
        for(;exponent>0;exponent>>=1)
        {
            if((exponent&1)==1)
            {
                result=multiply(result,major);
            }
            major=multiply(major,major);
        }
        if(is_negative_exponent)
        {
            result=new big_rational(result.denominator!=null?result.denominator:new big_integer(new int[]{1},1,1),result.numerator,result.numerator.sign,base.mode);
        }
        return result;
    }
    /**
    <p>字符串表示</p><br>
    @return 有理数的字符串表示。
    */
    public String toString()
    {
        if(denominator==null)
        {
            return numerator.toString();
        }
        else
        {
            StringBuilder result=new StringBuilder();
            if(mode<=0)
            {
                result.append(numerator.toString());
                result.append("/");
                result.append(denominator.toString());
            }
            if(mode==0)
            {
                result.append("  ");
            }
            if(mode>=0)
            {
                boolean is_negative=numerator.sign<0;
                big_integer quotient_and_remainder[]=big_integer.divide(is_negative?new big_integer(numerator.number,numerator.size,1):numerator,denominator);
                if(is_negative)
                {
                    result.append("-");
                }
                result.append(quotient_and_remainder[0]);
                result.append(".");
                big_integer remainder=quotient_and_remainder[1];
                HashMap<big_integer,Integer> remainders=new HashMap<big_integer,Integer>();
                StringBuilder loop_builder=new StringBuilder();
                big_integer ten=new big_integer(new int[]{10},1,1);
                for(int loop_size=-1;remainder.size>0;loop_size--)
                {
                    if(remainders.containsKey(remainder))
                    {
                        loop_size=-remainders.getOrDefault(remainder,0)-1;
                    }
                    if(loop_size>=0)
                    {
                        loop_builder.insert(loop_size,'(');
                        loop_builder.append(')');
                        break;
                    }
                    else
                    {
                        remainders.put(remainder,loop_size);
                        remainder=big_integer.multiply(remainder,ten);
                        quotient_and_remainder=big_integer.divide(remainder,denominator);
                        big_integer quotient=quotient_and_remainder[0];
                        int digit=quotient.size>0?quotient.number[0]:0;
                        loop_builder.append((char)('0'+digit));
                        remainder=quotient_and_remainder[1];
                    }
                }
                result.append(loop_builder);
            }
            return result.toString();
        }
    }
}