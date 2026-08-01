package tools.mathematics;
/**
<p>高精度整数类</p><br>
整数，即不含分数部分的数，包含正整数、负整数和零。<br>
本高精度整数以整型数组、符号和位数实现。<br>
数组以2^31进制表示整数的绝对值，低位优先存储，符号表示整数的正负。
*/
public class big_integer implements Comparable<big_integer>
{
    /**
    <p>位数组</p><br>
    2^31进制低位优先存储。
    */
    public int number[];
    /**
    <p>位数</p><br>
    2^31进制位数，即位数组的有效长度。
    */
    public int size;
    /**
    <p>符号</p><br>
    <ul>
        <li>&gt;0 正整数。</li>
        <li>&lt;0 负整数。</li>
        <li>=0 零。</li>
    </ul>
    */
    public int sign;
    /**
    <p>构造方法</p><br>
    通过整数形式字符串构造高精度整数对象。
    @param number_string 字符串表示的整数。
    */
    public big_integer(String number_string)
    {
        int length=number_string.length();
        int offset=0;
        boolean is_negative=false;
        if(number_string.charAt(0)=='-')
        {
            is_negative=true;
            offset++;
        }
        else if(number_string.charAt(0)=='+')
        {
            offset++;
        }
        for(;offset<length&&number_string.charAt(offset)=='0';offset++);
        if(offset==length)
        {
            number=new int[]{0};
            size=0;
            sign=0;
            return;
        }
        else
        {
            int absolute[]=new int[(length-offset)/8+2];
            int absolute_size=0;
            for(int i=offset;i<length;i++)
            {
                long carry=number_string.charAt(i)-'0';
                for(int j=0;j<absolute_size;j++)
                {
                    long value=(absolute[j]&2147483647L)*10+carry;
                    carry=value>>>31;
                    absolute[j]=(int)(value&2147483647L);
                }
                if(carry!=0)
                {
                    absolute[absolute_size++]=(int)carry;
                }
            }
            for(;absolute_size>0&&absolute[absolute_size-1]==0;absolute_size--);
            number=new int[absolute_size];
            System.arraycopy(absolute,0,number,0,absolute_size);
            size=absolute_size;
            sign=is_negative?-1:1;
        }
    }
    /**
    <p>构造方法</p><br>
    通过整数构造高精度整数对象。
    @param number 整数。
    */
    public big_integer(int number)
    {
        if(number==0)
        {
            this.number=new int[]{0};
            size=0;
            sign=0;
        }
        else if(number==Integer.MIN_VALUE)
        {
            this.number=new int[]{0,1};
            size=2;
            sign=-1;
        }
        else
        {
            sign=number>0?1:-1;
            int absolute_number=sign>0?number:-number;
            this.number=new int[]{absolute_number};
            size=1;
        }
    }
    /**
    <p>构造方法</p><br>
    通过低位优先表示的整型数组构造高精度整数对象。
    @param number_array 整型数组。
    @param sign 整数的符号。
    */
    public big_integer(int number_array[],int sign)
    {
        int length=number_array.length;
        for(;length>0&&number_array[length-1]==0;length--);
        if(length==0)
        {
            number=new int[]{0};
            size=0;
            this.sign=0;
            return;
        }
        else
        {
            number=new int[length];
            System.arraycopy(number_array,0,number,0,length);
            size=length;
            this.sign=sign==0?0:(sign>0?1:-1);
        }
    }
    /**
    <p>全参构造方法</p><br>
    通过低位优先表示的整型数组和位数构造高精度整数对象。<br>
    本构造方法会直接使用输入的数组、位数和符号，不进行拷贝和检查。
    @param number_array 整型数组。
    @param size 整数的位数。
    @param sign 整数的符号。
    */
    public big_integer(int number_array[],int size,int sign)
    {
        number=number_array;
        this.size=size;
        this.sign=sign;
    }
    /**
    <p>绝对值数组比较</p><br>
    比较两个字节数组低位优先表示的整数的大小。
    @param number1 第一个整数的字节数组低位优先表示。
    @param number2 第二个整数的字节数组低位优先表示。
    @return
    <ul>
        <li>0：<code>number1=number2</code>。</li>
        <li>&gt;0：<code>number1&gt;number2</code>。</li>
        <li>&lt;0：<code>number1&lt;number2</code>。</li>
    </ul>
    */
    public static int compare_absolute(int number1[],int number2[])
    {
        int size1=number1.length;
        int size2=number2.length;
        for(;size1>0&&number1[size1-1]==0;size1--);
        for(;size2>0&&number2[size2-1]==0;size2--);
        if(size1!=size2)
        {
            return size1-size2;
        }
        else
        {
            for(int i=size1-1;i>=0;i--)
            {
                long digit1=number1[i]&2147483647L;
                long digit2=number2[i]&2147483647L;
                if(digit1!=digit2)
                {
                    return digit1>digit2?1:-1;
                }
            }
            return 0;
        }
    }
    /**
    <p>自增</p><br>
    <p>此方法会修改调用对象。</p><br>
    对整数自增1。
    @return 位数是否改变。
    */
    public boolean increment()
    {
        if(size==0)
        {
            number=new int[]{1};
            size=1;
            sign=1;
            return true;
        }
        else if(sign>0)
        {
            long carry=1;
            for(int i=0;carry!=0&&i<size;i++)
            {
                long sum=(number[i]&2147483647L)+carry;
                number[i]=(int)sum;
                carry=sum>>>31;
            }
            if(carry!=0)
            {
                number=new int[size+1];
                number[size]=1;
                size++;
                return true;
            }
            else
            {
                return false;
            }
        }
        else
        {
            long borrow=1;
            for(int i=0;borrow!=0&&i<size;i++)
            {
                long difference=(number[i]&2147483647L)-borrow;
                number[i]=(int)difference;
                borrow=difference<0?1:0;
            }
            for(;size>0&&number[size-1]==0;size--);
            if(size==0)
            {
                number=new int[]{0};
                sign=0;
                return true;
            }
            else
            {
                return false;
            }
        }
    }
    /**
    <p>自减</p><br>
    <p>此方法会修改调用对象。</p><br>
    对整数自减1。
    @return 位数是否改变。
    */
    public boolean decrement()
    {
        if(size==0)
        {
            number=new int[]{1};
            size=1;
            sign=-1;
            return true;
        }
        else if(sign>0)
        {
            long borrow=1;
            for(int i=0;borrow!=0&&i<size;i++)
            {
                long difference=(number[i]&2147483647L)-borrow;
                number[i]=(int)difference;
                borrow=difference<0?1:0;
            }
            for(;size>0&&number[size-1]==0;size--);
            if(size==0)
            {
                number=new int[]{0};
                sign=0;
                return true;
            }
            else
            {
                return false;
            }
        }
        else
        {
            long carry=1;
            for(int i=0;carry!=0&&i<size;i++)
            {
                long sum=(number[i]&2147483647L)+carry;
                number[i]=(int)sum;
                carry=sum>>>31;
            }
            if(carry!=0)
            {
                number=new int[size+1];
                number[size]=1;
                size++;
                return true;
            }
            else
            {
                return false;
            }
        }
    }
    /**
    <p>加法运算</p><br>
    计算两个高精度整数的和 <code>addend1</code>+<code>addend2</code>。
    @param addend1 第一个高精度整数加数对象。
    @param addend2 第二个高精度整数加数对象。
    @return 两个高精度整数的和。
    */
    public static big_integer add(big_integer addend1,big_integer addend2)
    {
        if(addend1.size==0||addend2.size==0)
        {
            return new big_integer(addend1.size==0?addend2.number:addend1.number,addend1.size==0?addend2.sign:addend1.sign);
        }
        if(addend1.sign==addend2.sign)
        {
            int max_size=addend1.size>addend2.size?addend1.size:addend2.size;
            int result[]=new int[max_size+1];
            long carry=0;
            for(int i=0;i<max_size;i++)
            {
                long digit1=(i<addend1.size)?(addend1.number[i]&2147483647L):0;
                long digit2=(i<addend2.size)?(addend2.number[i]&2147483647L):0;
                long sum=digit1+digit2+carry;
                result[i]=(int)(sum&2147483647L);
                carry=sum>>>31;
            }
            if(carry!=0)
            {
                result[max_size]=(int)carry;
                return new big_integer(result,max_size+1,addend1.sign);
            }
            else
            {
                int trimmed[]=new int[max_size];
                System.arraycopy(result,0,trimmed,0,max_size);
                return new big_integer(trimmed,max_size,addend1.sign);
            }
        }
        else
        {
            int relation=compare_absolute(addend1.number,addend2.number);
            if(relation==0)
            {
                return new big_integer(new int[]{0},0,0);
            }
            else 
            {
                if(relation<0)
                {
                    big_integer temp=addend1;
                    addend1=addend2;
                    addend2=temp;
                }
                int result_sign=addend1.sign;
                int result[]=new int[addend1.size];
                long borrow=0;
                for(int i=0;i<addend1.size;i++)
                {
                    long digit1=addend1.number[i]&2147483647L;
                    long digit2=(i<addend2.size)?(addend2.number[i]&2147483647L):0;
                    long difference=digit1-digit2-borrow;
                    result[i]=(int)(difference&2147483647L);
                    borrow=difference<0?1:0;
                }
                int result_size=addend1.size;
                for(;result_size>0&&result[result_size-1]==0;result_size--);
                if(result_size<addend1.size)
                {
                    int trimmed[]=new int[result_size];
                    System.arraycopy(result,0,trimmed,0,result_size);
                    return new big_integer(trimmed,result_size,result_sign);
                }
                else
                {
                    return new big_integer(result,result_size,result_sign);
                }
            }
        }
    }
    /**
    <p>减法运算</p><br>
    计算两个高精度整数的差 <code>minuend</code>-<code>subtrahend</code>。
    @param minuend 高精度整数被减数对象。
    @param subtrahend 高精度整数减数对象。
    @return 两个高精度整数的差。
    */
    public static big_integer subtract(big_integer minuend,big_integer subtrahend)
    {
        if(minuend.size==0||subtrahend.size==0)
        {
            return new big_integer(minuend.size==0?subtrahend.number:minuend.number,minuend.size==0?-subtrahend.sign:minuend.sign);
        }
        if(minuend.sign==subtrahend.sign)
        {
            int relation=compare_absolute(minuend.number,subtrahend.number);
            if(relation==0)
            {
                return new big_integer(new int[]{0},0,0);
            }
            else
            {
                if(relation<0)
                {
                    big_integer temp=minuend;
                    minuend=subtrahend;
                    subtrahend=temp;
                }
                int result[]=new int[minuend.size];
                long borrow=0;
                for(int i=0;i<minuend.size;i++)
                {
                    long minuend_digit=minuend.number[i]&2147483647L;
                    long subtrahend_digit=(i<subtrahend.size)?(subtrahend.number[i]&2147483647L):0;
                    long difference=minuend_digit-subtrahend_digit-borrow;
                    result[i]=(int)(difference&2147483647L);
                    borrow=difference<0?1:0;
                }
                int result_size=minuend.size;
                for(;result_size>0&&result[result_size-1]==0;result_size--);
                if(result_size<minuend.size)
                {
                    int trimmed[]=new int[result_size];
                    System.arraycopy(result,0,trimmed,0,result_size);
                    return new big_integer(trimmed,result_size,relation>0?minuend.sign:-minuend.sign);
                }
                else
                {
                    return new big_integer(result,minuend.size,relation>0?minuend.sign:-minuend.sign);
                }
            }
        }
        else
        {
            int max_size=minuend.size>subtrahend.size?minuend.size:subtrahend.size;
            int result[]=new int[max_size+1];
            long carry=0;
            for(int i=0;i<max_size;i++)
            {
                long minuend_digit=(i<minuend.size)?(minuend.number[i]&2147483647L):0;
                long subtrahend_digit=(i<subtrahend.size)?(subtrahend.number[i]&2147483647L):0;
                long sum=minuend_digit+subtrahend_digit+carry;
                result[i]=(int)(sum&2147483647L);
                carry=sum>>>31;
            }
            if(carry!=0)
            {
                result[max_size]=(int)carry;
                return new big_integer(result,max_size+1,minuend.sign);
            }
            else
            {
                int trimmed[]=new int[max_size];
                System.arraycopy(result,0,trimmed,0,max_size);
                return new big_integer(trimmed,max_size,minuend.sign);
            }
        }
    }
    /**
    <p>乘一位数</p><br>
    计算一个高精度整数与一个整数的积 <code>factor</code>*<code>multiplier</code>。
    @param factor 高精度整数因数对象。
    @param multiplier 整数因数。
    @return 高精度整数与整数的积。
    */
    public static big_integer multiply(big_integer factor,int multiplier)
    {
        if(multiplier==0||factor.size==0)
        {
            return new big_integer(new int[]{0},0,0);
        }
        long multiplier_absolute=multiplier>=0?multiplier:-(long)multiplier;
        int product[]=new int[factor.size+1];
        long carry=0;
        for(int i=0;i<factor.size;i++)
        {
            long product_digit=(factor.number[i]&2147483647L)*multiplier_absolute+carry;
            product[i]=(int)(product_digit&2147483647L);
            carry=product_digit>>>31;
        }
        if(carry!=0)
        {
            product[factor.size]=(int)(carry&2147483647L);
            return new big_integer(product,factor.size+1,factor.sign*(multiplier>=0?1:-1));
        }
        else
        {
            int trimmed[]=new int[factor.size];
            System.arraycopy(product,0,trimmed,0,factor.size);
            return new big_integer(trimmed,factor.size,factor.sign*(multiplier>=0?1:-1));
        }
    }
    /**
    <p>乘法运算</p><br>
    计算两个高精度整数的积 <code>factor1</code>*<code>factor2</code>。
    @param factor1 第一个高精度整数因数对象。
    @param factor2 第二个高精度整数因数对象。
    @return 两个高精度整数的积。
    */
    public static big_integer multiply(big_integer factor1,big_integer factor2)
    {
        if(factor1.size==0||factor2.size==0)
        {
            return new big_integer(new int[]{0},0,0);
        }
        int product[]=new int[factor1.size+factor2.size];
        for(int i=0;i<factor1.size;i++)
        {
            long factor1_digit=(factor1.number[i]&2147483647L);
            long carry=0;
            for(int j=0;j<factor2.size;j++)
            {
                long product_digit=(factor1_digit*(factor2.number[j]&2147483647L)+(product[i+j]&2147483647L))+carry;
                product[i+j]=(int)(product_digit&2147483647L);
                carry=product_digit>>>31;
            }
            product[i+factor2.size]=(int)(carry&2147483647L);
        }
        int product_size=factor1.size+factor2.size;
        for(;product_size>0&&product[product_size-1]==0;product_size--);
        if(product_size<factor1.size+factor2.size)
        {
            int trimmed[]=new int[product_size];
            System.arraycopy(product,0,trimmed,0,product_size);
            return new big_integer(trimmed,product_size,factor1.sign*factor2.sign);
        }
        else
        {
            return new big_integer(product,product_size,factor1.sign*factor2.sign);
        }
    }
    /**
    <p>除一位数</p><br>
    计算一个高精度整数与一个整数的商 <code>dividend</code>/<code>divisor</code>。
    @param dividend 高精度整数被除数对象。
    @param divisor 整数除数。
    @return 一个高精度整数数组：
    <ol>
        <li>高精度整数商。</li>
        <li>高精度整数余数。</li>
    </ol><br>
    若除数为0，则返回<code>null</code>。
    */
    public static big_integer[] divide(big_integer dividend,int divisor)
    {
        if(divisor==0)
        {
            return null;
        }
        else if(dividend.size==0)
        {
            return new big_integer[]{new big_integer(new int[]{0},0,0),new big_integer(new int[]{0},0,0)};
        }
        long divisor_absolute=divisor>=0?divisor:-(long)divisor;
        int divisor_sign=divisor>=0?1:-1;
        int quotient[]=new int[dividend.size+1];
        if(divisor_absolute==1)
        {
            System.arraycopy(dividend.number,0,quotient,0,dividend.size);
            return new big_integer[]{new big_integer(quotient,dividend.size,dividend.sign*divisor_sign),new big_integer(new int[]{0},0,0)};
        }
        long remainder=0;
        for(int i=dividend.size-1;i>=0;i--)
        {
            long value=((long)dividend.number[i]|(remainder<<31))/divisor_absolute;
            quotient[i]=(int)(value&2147483647L);
            remainder=((long)dividend.number[i]|(remainder<<31))%divisor_absolute;
        }
        big_integer result=new big_integer(quotient,dividend.sign*divisor_sign);
        if(remainder!=0&&dividend.sign<0)
        {
            if(result.sign>0)
            {
                result.increment();
            }
            else
            {
                result.decrement();
            }
            remainder=divisor_absolute-remainder;
        }
        return new big_integer[]{result,new big_integer((int)remainder)};
    }
    /**
    <p>除法运算</p><br>
    计算两个高精度整数的商 <code>dividend</code>/<code>divisor</code>。
    @param dividend 高精度整数被除数对象。
    @param divisor 高精度整数除数对象。
    @return 一个高精度整数数组：
    <ol>
        <li>高精度整数商。</li>
        <li>高精度整数余数。</li>
    </ol><br>
    若除数为0，则返回<code>null</code>。
    */
    public static big_integer[] divide(big_integer dividend,big_integer divisor)
    {
        if(divisor.size==0)
        {
            return null;
        }
        else if(dividend.size==0)
        {
            return new big_integer[]{new big_integer(new int[]{0},0,0),new big_integer(new int[]{0},0,0)};
        }
        else if(compare_absolute(dividend.number,divisor.number)<0)
        {
            if(dividend.sign>0)
            {
                return new big_integer[]{new big_integer(new int[]{0},0,0),new big_integer(dividend.number,1)};
            }
            else
            {
                return new big_integer[]{new big_integer(new int[]{1},1,-divisor.sign),add(new big_integer(divisor.number,divisor.size,1),dividend)};
            }
        }
        int dividend_size=dividend.size;
        int divisor_size=divisor.size;
        int dividend_absolute[]=new int[dividend_size+2];
        int divisor_absolute[]=new int[divisor_size+1];
        System.arraycopy(dividend.number,0,dividend_absolute,0,dividend_size);
        System.arraycopy(divisor.number,0,divisor_absolute,0,divisor_size);
        int movement=-1;
        for(int number=divisor_absolute[divisor_size-1];number>0;number<<=1,movement++);
        if(movement>0)
        {
            long move_bit=0;
            for(int i=0;i<divisor_size;i++)
            {
                move_bit|=(long)divisor_absolute[i]<<movement;
                divisor_absolute[i]=(int)(move_bit&2147483647L);
                move_bit>>>=31;
            }
            for(int i=0;i<=dividend_size;i++)
            {
                move_bit|=(long)dividend_absolute[i]<<movement;
                dividend_absolute[i]=(int)(move_bit&2147483647L);
                move_bit>>>=31;
            }
            if(dividend_absolute[dividend_size]!=0)
            {
                dividend_size++;
            }
        }
        int quotient_size=dividend_size-divisor_size+1;
        int quotient_absolute[]=new int[quotient_size];
        for(int i=dividend_size-divisor_size;i>=0;i--)
        {
            long dividend_high1=dividend_absolute[i+divisor_size]&2147483647L;
            long dividend_high2=dividend_absolute[i+divisor_size-1]&2147483647L;
            long divisor_high1=divisor_absolute[divisor_size-1]&2147483647L;
            long quotient_valuation=(dividend_high1<<31|dividend_high2)/divisor_high1;
            long remainder_valuation=(dividend_high1<<31|dividend_high2)%divisor_high1;
            if(quotient_valuation==2147483648L)
            {
                quotient_valuation=2147483647L;
            }
            long divisor_high2=(divisor_size>1)?(divisor_absolute[divisor_size-2]&2147483647L):0;
            for(;remainder_valuation<2147483648L&&quotient_valuation*divisor_high2>((i+divisor_size>=2)?((remainder_valuation<<31)|(dividend_absolute[i+divisor_size-2]&2147483647L)):remainder_valuation);quotient_valuation--,remainder_valuation+=divisor_high1);
            long borrow=0;
            for(int j=0;j<=divisor_size;j++)
            {
                long subtrahend=quotient_valuation*(divisor_absolute[j]&2147483647L);
                long difference=(dividend_absolute[i+j]&2147483647L)-subtrahend-borrow;
                borrow=0;
                if(difference<0)
                {
                    borrow+=Math.floorDiv(difference+1,-2147483648L)+1;
                    difference=Math.floorMod(difference,2147483648L);
                }
                dividend_absolute[i+j]=(int)difference;
            }
            if(dividend_absolute[i+divisor_size]<0)
            {
                quotient_valuation--;
                long carry=0;
                for(int j=0;j<=divisor_size;j++)
                {
                    long sum=(dividend_absolute[i+j]&2147483647L)+(divisor_absolute[j]&2147483647L)+carry;
                    dividend_absolute[i+j]=(int)sum;
                    carry=(sum>>>31);
                }
            }
            quotient_absolute[i]=(int)quotient_valuation;
        }
        if(movement>0)
        {
            long move_bit=0;
            for(;dividend_size>0&&dividend_absolute[dividend_size-1]==0;dividend_size--);
            for(int i=dividend_size;i>=0;i--)
            {
                move_bit|=(long)dividend_absolute[i]<<(31-movement);
                dividend_absolute[i]=(int)((move_bit&4611686016279904256L)>>>31);
                move_bit<<=31;
            }
        }
        int remainder_size=divisor_size;
        for(;quotient_size>0&&quotient_absolute[quotient_size-1]==0;quotient_size--);
        for(;remainder_size>0&&dividend_absolute[remainder_size-1]==0;remainder_size--);
        int remainder_absolute[]=new int[remainder_size];
        System.arraycopy(dividend_absolute,0,remainder_absolute,0,remainder_size);
        big_integer quotient=new big_integer(quotient_absolute,quotient_size,dividend.sign*divisor.sign);
        big_integer remainder=new big_integer(remainder_absolute,remainder_size,remainder_size>0?1:0);
        if(remainder.size>0&&dividend.sign<0)
        {
            if(quotient.sign>0)
            {
                quotient.increment();
            }
            else
            {
                quotient.decrement();
            }
            remainder.sign=1;
            remainder=subtract(new big_integer(divisor.number,divisor.size,1),remainder);
        }
        return new big_integer[]{quotient,remainder};
    }
    /**
    <p>最大公因数</p><br>
    计算两个高精度整数的最大公因数。
    @param number1 第一个高精度整数对象。
    @param number2 第二个高精度整数对象。
    @return 两个高精度整数的最大公因数。<br>
    定义0与0的最大公因数为0。
    */
    public static big_integer gcd(big_integer number1,big_integer number2)
    {
        if(number1.size==0&&number2.size==0)
        {
            return new big_integer(new int[]{0},0,0);
        }
        else if(number1.size==0||number2.size==0)
        {
            return number1.size>0?new big_integer(number1.number,1):new big_integer(number2.number,1);
        }
        int relation=number1.compareTo(number2);
        if(relation==0)
        {
            return new big_integer(number1.number,1);
        }
        else
        {
            if(relation<0)
            {
                big_integer temp=number1;
                number1=number2;
                number2=temp;
            }
            do
            {
                big_integer result[]=divide(number1,number2);
                number1=number2;
                number2=result[1];
            }
            while(number2.size>0);
            return new big_integer(number1.number,1);
        }
    }
    /**
    <p>最小公倍数</p><br>
    计算两个高精度整数的最小公倍数。
    @param number1 第一个高精度整数对象。
    @param number2 第二个高精度整数对象。
    @return 两个高精度整数的最小公倍数。<br>
    定义0与0的最小公倍数为0。
    */
    public static big_integer lcm(big_integer number1,big_integer number2)
    {
        return divide(multiply(number1,number2),gcd(number1,number2))[0];
    }
    /**
    <p>幂运算</p><br>
    计算高精度整数的正整数次幂 <code>base</code>^<code>exponent</code>。<br>
    @param base 底数整数对象。
    @param positive_exponent 指数正整数。
    @return 底数的指数次幂。<br>
    若指数为负数或底数与指数同时为0，则返回<code>null</code>。
    */
    public static big_integer power(big_integer base,int positive_exponent)
    {
        if(positive_exponent<0)
        {
            return null;
        }
        else if(base.size==0)
        {
            return positive_exponent>0?new big_integer(new int[]{0},0,0):null;
        }
        else if(positive_exponent==0)
        {
            return new big_integer(new int[]{1},1,1);
        }
        else if(positive_exponent==1)
        {
            return new big_integer(base.number,base.sign);
        }
        if(base.sign!=0&&base.size==1&&base.number[0]==1)
        {
            return new big_integer(new int[]{positive_exponent%2==0?1:base.sign},1,base.sign);
        }
        big_integer result=new big_integer(new int[]{1},1,1);
        big_integer major=new big_integer(base.number,base.sign);
        for(;positive_exponent>0;positive_exponent>>=1)
        {
            if((positive_exponent&1)==1)
            {
                result=multiply(result,major);
            }
            major=multiply(major,major);
        }
        return result;
    }
    /**
    <p>阶乘运算</p><br>
    计算整数的阶乘。
    @param number 整数。
    @return 整数的阶乘。<br>
    若整数为负数，则返回<code>null</code>。
    */
    public static big_integer factorial(int number)
    {
        if(number<0)
        {
            return null;
        }
        else
        {
            big_integer result=new big_integer(new int[]{1},1,1);
            big_integer factor=new big_integer(new int[]{2},1,1);
            for(int i=2;i<=number;i++,factor.increment())
            {
                result=multiply(result,factor);
            }
            return result;
        }
    }
    /**
    <p>字符串表示</p><br>
    高精度整数的字符串表示。
    */
    public String toString()
    {
        if(size==0)
        {
            return "0";
        }
        else
        {
            StringBuilder result=new StringBuilder();
            int absolute[]=new int[size+1];
            System.arraycopy(number,0,absolute,0,size);
            int absolute_size=size;
            while(absolute_size>0)
            {
                long remainder=0;
                int new_size=0;
                boolean reduced=false;
                for(int i=absolute_size-1;i>=0;i--)
                {
                    long now=(remainder<<31)|(absolute[i]&2147483647L);
                    long quotient=now/10;
                    remainder=now%10;
                    absolute[i]=(int)quotient;
                    if(!reduced&&quotient!=0)
                    {
                        new_size=i+1;
                        reduced=true;
                    }
                }
                result.append((char)('0'+remainder));
                absolute_size=new_size;
            }
            if(sign<0)
            {
                result.append('-');
            }
            return result.reverse().toString();
        }
    }
    /**
    <p>相等判断</p><br>
    判断当前整数与指定整数是否相等。
    @param another 指定整数对象。
    @return 是否相等。
    */
    public boolean equals(Object another)
    {
        if(another==null||!(another instanceof big_integer))
        {
            return false;
        }
        big_integer another_big_integer=(big_integer)another;
        if(sign!=another_big_integer.sign||size!=another_big_integer.size)
        {
            return false;
        }
        else
        {
            for(int i=size-1;i>=0;i--)
            {
                long digit_this=number[i]&2147483647L;
                long digit_another=another_big_integer.number[i]&2147483647L;
                if(digit_this!=digit_another)
                {
                    return false;
                }
            }
            return true;
        }
    }
    /**
    <p>哈希值计算</p><br>
    计算当前整数的哈希值。
    @return 当前整数的哈希值。
    */
    public int hashCode()
    {
        if(size==0)
        {
            return 0;
        }
        else
        {
            int hash=sign;
            for(int i=0;i<size;i++)
            {
                hash=hash*31+number[i];
            }
            return hash;
        }
    }
    /**
    <p>比较</p><br>
    比较当前整数与指定整数的数值。
    @param another 指定整数对象。
    @return 当前整数与指定整数的数值比较的结果。<br>
    <ul>
        <li>=0：当前整数与指定整数相等。</li>
        <li>&gt;0：当前整数大于指定整数。</li>
        <li>&lt;0：当前整数小于指定整数。</li>
    </ul>
    */
    public int compareTo(big_integer another)
    {
        if(sign!=another.sign)
        {
            return sign-another.sign;
        }
        else if(size==0)
        {
            return 0;
        }
        else if(size!=another.size)
        {
            return size-another.size;
        }
        else
        {
            for(int i=size-1;i>=0;i--)
            {
                long digit_this=number[i]&2147483647L;
                long digit_another=another.number[i]&2147483647L;
                if(digit_this!=digit_another)
                {
                    return (digit_this>digit_another?1:-1)*sign;
                }
            }
            return 0;
        }
    }
}