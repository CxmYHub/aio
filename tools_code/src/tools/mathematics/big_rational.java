package tools.mathematics;
/**
<p>高精度有理数类。</p><br>
有理数，即分数，包含整数、有限小数和无限循环小数。<br>
任何有理数都可表示为分数。<br>
本高精度有理数以二维字节数组<code>fraction[2][]</code>和分子、分母的位数实现。<br>
二维数组中的每个元素表示分子或分母中的一位，低位优先存储。<br>
其中，<code>fraction[0]</code>和分子位数共同表示分子，<code>fraction[1]</code>和分母位数共同表示分母。<br>
当<code>fraction[1]==null</code>时，为整数对象。<br>
字符串输出默认为分数&nbsp;&nbsp;小数格式，可通过设置<code>mode</code>改变输出格式：<br>
<ul>
    <li><code>mode&gt;0</code>：小数格式，例如<code>"0.5"</code>。</li>
    <li><code>mode=0</code>：分数&nbsp;&nbsp;小数格式，例如<code>"1/2&nbsp;&nbsp;0.5"</code>。</li>
    <li><code>mode&lt;0</code>：分数格式，例如<code>"1/2"</code>。</li>
</ul>
*/
public class big_rational
{
    public byte fraction[][];
    public int numerator_size;
    public int denominator_size;
    public int mode=0;
    /**
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
    public static int compare(byte number1[],byte number2[])
    {
        int size1=number1.length;
        int size2=number2.length;
        for(;size1>0&&number1[size1-1]==0;size1--);
        for(;size2>0&&number2[size2-1]==0;size2--);
        int positive1=size1>0?(number1[size1-1]>=0?1:-1):0;
        int positive2=size2>0?(number2[size2-1]>=0?1:-1):0;
        if(positive1!=positive2)
        {
            return positive1-positive2;
        }
        else if(positive1==0)
        {
            return 0;
        }
        if(size1!=size2)
        {
            return (size1-size2)*positive1;
        }
        else
        {
            for(int i=size1-1;i>=0;i--)
            {
                if(number1[i]!=number2[i])
                {
                    if(i==size1-1)
                    {
                        return number1[i]-number2[i];
                    }
                    return (number1[i]-number2[i])*positive1;
                }
            }
        }
        return 0;
    }
    /**
    计算两个字节数组低位优先表示的整数的和 <code>addend1</code>+<code>addend2</code>。
    @param addend1 第一个整数的字节数组低位优先表示。
    @param addend2 第二个整数的字节数组低位优先表示。
    @return 两个整数的和的字节数组低位优先表示。
    */
    public static byte[] add(byte addend1[],byte addend2[])
    {
        int size1=addend1.length;
        int size2=addend2.length;
        for(;size1>0&&addend1[size1-1]==0;size1--);
        for(;size2>0&&addend2[size2-1]==0;size2--);
        int positive1=size1>0?(addend1[size1-1]>=0?1:-1):0;
        int positive2=size2>0?(addend2[size2-1]>=0?1:-1):0;
        int size_sum=size1>size2?size1:(size1<size2?size2:size1+1);
        byte sum[];
        if(positive1==0||positive2==0)
        {
            sum=new byte[size_sum+1];
            System.arraycopy(positive1==0?addend2:addend1,0,sum,0,positive1==0?size2:size1);
            return sum;
        }
        else if(positive1>positive2)
        {
            addend2[size2-1]*=-1;
            sum=subtract(addend1,addend2);
            addend2[size2-1]*=-1;
            return sum;
        }
        else if(positive1<positive2)
        {
            addend1[size1-1]*=-1;
            sum=subtract(addend2,addend1);
            addend1[size1-1]*=-1;
            return sum;
        }
        else
        {
            if(positive1<0)
            {
                addend1[size1-1]*=-1;
                addend2[size2-1]*=-1;
            }
            sum=new byte[size_sum+1];
            if(size1<size2)
            {
                byte temp[]=addend1;
                addend1=addend2;
                addend2=temp;
                int temp_size=size1;
                size1=size2;
                size2=temp_size;
            }
            for(int i=0;i<size2;i++)
            {
                sum[i+1]+=(byte)((sum[i]+addend1[i]+addend2[i])/10);
                sum[i]=(byte)((sum[i]+addend1[i]+addend2[i])%10);
            }
            for(int i=size2;i<size1;i++)
            {
                sum[i+1]+=(byte)((sum[i]+addend1[i])/10);
                sum[i]=(byte)((sum[i]+addend1[i])%10);
            }
            if(positive1<0)
            {
                addend1[size1-1]*=-1;
                addend2[size2-1]*=-1;
                sum[size_sum-1]*=-1;
            }
            return sum;
        }
    }
    /**
    计算两个字节数组低位优先表示的整数的差 <code>minuend</code>-<code>subtrahend</code>。
    @param minuend 被减数的字节数组低位优先表示。
    @param subtrahend 减数的字节数组低位优先表示。
    @return 被减数与减数的差的字节数组低位优先表示。
    */
    public static byte[] subtract(byte minuend[],byte subtrahend[])
    {
        int size_minuend=minuend.length;
        int size_subtrahend=subtrahend.length;
        for(;size_minuend>0&&minuend[size_minuend-1]==0;size_minuend--);
        for(;size_subtrahend>0&&subtrahend[size_subtrahend-1]==0;size_subtrahend--);
        int positive_minuend=size_minuend>0?(minuend[size_minuend-1]>=0?1:-1):0;
        int positive_subtrahend=size_subtrahend>0?(subtrahend[size_subtrahend-1]>=0?1:-1):0;
        byte difference[];
        if(positive_minuend<0&&positive_subtrahend>0)
        {
            minuend[size_minuend-1]*=-1;
            difference=add(minuend,subtrahend);
            int size_difference=difference.length;
            for(;size_difference>0&&difference[size_difference-1]==0;size_difference--);
            difference[size_difference-1]*=-1;
            minuend[size_minuend-1]*=-1;
            return difference;
        }
        else if(positive_minuend>0&&positive_subtrahend<0)
        {
            subtrahend[size_subtrahend-1]*=-1;
            difference=add(minuend,subtrahend);
            subtrahend[size_subtrahend-1]*=-1;
            return difference;
        }
        else
        {
            if(positive_minuend<0)
            {
                minuend[size_minuend-1]*=-1;
                subtrahend[size_subtrahend-1]*=-1;
                byte temp[]=minuend;
                minuend=subtrahend;
                subtrahend=temp;
                int temp_size=size_minuend;
                size_minuend=size_subtrahend;
                size_subtrahend=temp_size;
            }
            int relation=compare(minuend,subtrahend);
            boolean is_minus=relation<0;
            if(relation==0)
            {
                if(positive_minuend<0)
                {
                    minuend[size_minuend-1]*=-1;
                    subtrahend[size_subtrahend-1]*=-1;
                }
                return new byte[]{0};
            }
            else if(is_minus)
            {
                byte temp[]=minuend;
                minuend=subtrahend;
                subtrahend=temp;
                int temp_size=size_minuend;
                size_minuend=size_subtrahend;
                size_subtrahend=temp_size;
            }
            difference=new byte[size_minuend+1];
            for(int i=0;i<size_subtrahend;i++)
            {
                difference[i+1]+=(byte)Math.floorDiv(difference[i]+minuend[i]-subtrahend[i],10);
                difference[i]=(byte)Math.floorMod(difference[i]+minuend[i]-subtrahend[i],10);
            }
            for(int i=size_subtrahend;i<size_minuend;i++)
            {
                difference[i+1]+=(byte)Math.floorDiv(difference[i]+minuend[i],10);
                difference[i]=(byte)Math.floorMod(difference[i]+minuend[i],10);
            }
            int size_difference=difference.length;
            for(;size_difference>0&&difference[size_difference-1]==0;size_difference--);
            difference[size_difference-1]*=is_minus?-1:1;
            if(positive_minuend<0)
            {
                minuend[size_minuend-1]*=-1;
                subtrahend[size_subtrahend-1]*=-1;
                difference[size_difference-1]*=-1;
            }
            return difference;
        }
    }
    /**
    计算字节数组低位优先表示的整数与一个一位整数的积 <code>factor</code>*<code>one_bit_multiplier</code>。
    @param factor 因数的字节数组低位优先表示。
    @param one_bit_multiplier 一位因数。
    @return 积的字节数组低位优先表示。
    */
    public static byte[] multiply(byte factor[],int one_bit_multiplier)
    {
        if(one_bit_multiplier==0)
        {
            return new byte[]{0};
        }
        int size1=factor.length;
        for(;size1>0&&factor[size1-1]==0;size1--);
        if(size1==0)
        {
            return new byte[]{0};
        }
        int positive_factor=size1>0?(factor[size1-1]>=0?1:-1):0;
        int positive_multiplier=one_bit_multiplier>=0?1:-1;
        byte product[]=new byte[size1+2];
        factor[size1-1]*=positive_factor;
        one_bit_multiplier*=positive_multiplier;
        for(int i=0;i<size1;i++)
        {
            product[i+1]+=(byte)((product[i]+factor[i]*one_bit_multiplier)/10);
            product[i]=(byte)((product[i]+factor[i]*one_bit_multiplier)%10);
        }
        if(positive_factor!=positive_multiplier)
        {
            int size_product=product.length;
            for(;size_product>0&&product[size_product-1]==0;size_product--);
            product[size_product-1]*=-1;
        }
        factor[size1-1]*=positive_factor;
        return product;
    }
    /**
    计算两个字节数组低位优先表示的整数的积 <code>factor1</code>*<code>factor2</code>。
    @param factor1 第一个整数的字节数组低位优先表示。
    @param factor2 第二个整数的字节数组低位优先表示。
    @return 两个整数的积的字节数组低位优先表示。
    */
    public static byte[] multiply(byte factor1[],byte factor2[])
    {
        int size1=factor1.length;
        int size2=factor2.length;
        for(;size1>0&&factor1[size1-1]==0;size1--);
        for(;size2>0&&factor2[size2-1]==0;size2--);
        if(size1==0||size2==0)
        {
            return new byte[]{0};
        }
        int positive1=size1>0?(factor1[size1-1]>=0?1:-1):0;
        int positive2=size2>0?(factor2[size2-1]>=0?1:-1):0;
        byte product[]=new byte[size1+size2+1];
        factor1[size1-1]*=positive1;
        factor2[size2-1]*=positive2;
        for(int i=0;i<size1;i++)
        {
            for(int j=0;j<size2;j++)
            {
                product[i+j+1]+=(byte)((product[i+j]+factor1[i]*factor2[j])/10);
                product[i+j]=(byte)((product[i+j]+factor1[i]*factor2[j])%10);
            }
        }
        if(positive1!=positive2)
        {
            int size_product=product.length;
            for(;size_product>0&&product[size_product-1]==0;size_product--);
            product[size_product-1]*=-1;
        }
        factor1[size1-1]*=positive1;
        factor2[size2-1]*=positive2;
        return product;
    }
    /**
    计算两个字节数组低位优先表示的整数的商 <code>dividend</code>/<code>one_bit_divisor</code>。
    @param dividend 被除数的字节数组低位优先表示。
    @param one_bit_divisor 一位除数。
    @return 一个二维字节数组：
    <ol>
        <li>商的字节数组低位优先表示。</li>
        <li>余数的字节数组低位优先表示。</li>
    </ol><br>
    若除数为0，则返回空数组。
    */
    public static byte[][] divide(byte dividend[],int one_bit_divisor)
    {
        if(one_bit_divisor==0)
        {
            return new byte[0][0];
        }
        int dividend_size=dividend.length;
        for(;dividend_size>0&&dividend[dividend_size-1]==0;dividend_size--);
        int positive_dividend=dividend_size>0?(dividend[dividend_size-1]>=0?1:-1):0;
        int positive_divisor=one_bit_divisor>=0?1:-1;
        if(positive_dividend==0)
        {
            return new byte[][]{{0},{0}};
        }
        byte quotient[]=new byte[dividend_size+1];
        if(one_bit_divisor==1||one_bit_divisor==-1)
        {
            System.arraycopy(dividend,0,quotient,0,dividend_size);
            quotient[dividend_size-1]*=positive_divisor;
            return new byte[][]{quotient,new byte[]{0}};
        }
        dividend[dividend_size-1]*=positive_dividend;
        one_bit_divisor*=positive_divisor;
        int remainder=0;
        for(int i=dividend_size-1;i>=0;i--)
        {
            remainder=remainder*10+dividend[i];
            quotient[i]=(byte)(remainder/one_bit_divisor);
            remainder%=one_bit_divisor;
        }
        int quotient_size=dividend_size;
        for(;quotient_size>0&&quotient[quotient_size-1]==0;quotient_size--);
        if(remainder>0&&positive_dividend<0)
        {
            quotient[0]++;
            remainder=one_bit_divisor-remainder;
        }
        if(quotient_size>0)
        {
            quotient[quotient_size-1]*=positive_dividend*positive_divisor;
        }
        dividend[dividend_size-1]*=positive_dividend;
        byte result[]=new byte[quotient_size+1];
        System.arraycopy(quotient,0,result,0,quotient_size);
        return new byte[][]{result,new byte[]{(byte)remainder}};
    }
    /**
    计算两个字节数组低位优先表示的整数的商 <code>dividend</code>/<code>divisor</code>。
    @param dividend 被除数的字节数组低位优先表示。
    @param divisor 除数的字节数组低位优先表示。
    @return 一个二维字节数组：
    <ol>
        <li>商的字节数组低位优先表示。</li>
        <li>余数的字节数组低位优先表示。</li>
    </ol><br>
    若除数为0，则返回空数组。
    */
    public static byte[][] divide(byte dividend[],byte divisor[])
    {
        int dividend_size=dividend.length;
        int divisor_size=divisor.length;
        for(;dividend_size>0&&dividend[dividend_size-1]==0;dividend_size--);
        for(;divisor_size>0&&divisor[divisor_size-1]==0;divisor_size--);
        int positive_dividend=dividend_size>0?(dividend[dividend_size-1]>=0?1:-1):0;
        int positive_divisor=divisor_size>0?(divisor[divisor_size-1]>=0?1:-1):0;
        if(positive_divisor==0)
        {
            return new byte[0][0];
        }
        if(positive_dividend==0)
        {
            return new byte[][]{{0},{0}};
        }
        byte primary_dividend[]=dividend;
        dividend=new byte[dividend_size+1];
        System.arraycopy(primary_dividend,0,dividend,0,dividend_size);
        dividend[dividend_size-1]*=positive_dividend;
        byte primary_divisor[]=divisor;
        divisor=new byte[divisor_size+1];
        System.arraycopy(primary_divisor,0,divisor,0,divisor_size);
        divisor[divisor_size-1]*=positive_divisor;
        int delta=10/(divisor[divisor_size-1]+1);
        if(delta>1)
        {
            dividend=multiply(dividend,delta);
            divisor=multiply(divisor,delta);
            for(dividend_size=dividend.length;dividend_size>0&&dividend[dividend_size-1]==0;dividend_size--);
            for(divisor_size=divisor.length;divisor_size>0&&divisor[divisor_size-1]==0;divisor_size--);
        }
        int quotient_size=dividend_size-divisor_size+2;
        quotient_size=quotient_size>0?quotient_size:1;
        byte quotient[]=new byte[quotient_size];
        for(int i=dividend_size-divisor_size;i>=0;i--)
        {
            int quotient_test=(dividend[i+divisor_size]*10+dividend[i+divisor_size-1])/divisor[divisor_size-1];
            int remainder_test=(dividend[i+divisor_size]*10+dividend[i+divisor_size-1])%divisor[divisor_size-1];
            if(divisor_size>1&&(quotient_test>=10||quotient_test*divisor[divisor_size-2]>remainder_test*10+dividend[i+divisor_size-2]))
            {
                quotient_test--;
                remainder_test+=divisor[divisor_size-1];
                if(remainder_test<10&&(quotient_test>=10||quotient_test*divisor[divisor_size-2]>remainder_test*10+dividend[i+divisor_size-2]))
                {
                    quotient_test--;
                    remainder_test+=divisor[divisor_size-1];
                }
            }
            for(int j=i;j<i+divisor_size;j++)
            {
                dividend[j+1]+=(byte)Math.floorDiv(dividend[j]-quotient_test*divisor[j-i],10);
                dividend[j]=(byte)Math.floorMod(dividend[j]-quotient_test*divisor[j-i],10);
            }
            if(dividend[i+divisor_size]<0)
            {
                quotient_test--;
                for(int j=i;j<i+divisor_size;j++)
                {
                    dividend[j]+=divisor[j-i];
                    if(dividend[j]>9)
                    {
                        dividend[j+1]+=(byte)(dividend[j]/10);
                        dividend[j]=(byte)(dividend[j]%10);
                    }
                }
            }
            quotient[i]=(byte)quotient_test;
        }
        dividend=divide(dividend,delta)[0];
        for(dividend_size=dividend.length;dividend_size>0&&dividend[dividend_size-1]==0;dividend_size--);
        for(quotient_size=quotient.length;quotient_size>0&&quotient[quotient_size-1]==0;quotient_size--);
        if(dividend_size>0&&positive_dividend<0)
        {
            quotient_size=quotient_size==0?1:quotient_size;
            quotient[0]++;
            for(int i=0;i<quotient_size&&quotient[i]>9;i++)
            {
                quotient[i+1]+=1;
                quotient[i]-=10;
            }
            for(divisor_size=divisor.length;divisor_size>0&&divisor[divisor_size-1]==0;divisor_size--);
            primary_divisor[divisor_size-1]*=positive_divisor;
            dividend=subtract(primary_divisor,dividend);
            primary_divisor[divisor_size-1]*=positive_divisor;
            for(dividend_size=dividend.length;dividend_size>0&&dividend[dividend_size-1]==0;dividend_size--);
        }
        if(quotient_size>0)
        {
            quotient[quotient_size-1]*=positive_dividend*positive_divisor;
        }
        byte remainder[]=new byte[dividend_size+1];
        System.arraycopy(dividend,0,remainder,0,dividend_size);
        byte result[][]={quotient,remainder};
        return result;
    }
    /**
    计算两个字节数组低位优先表示的整数的最大公因数。
    @param number1 第一个整数的字节数组低位优先表示。
    @param number2 第二个整数的字节数组低位优先表示。
    @return 两个整数的最大公因数的字节数组低位优先表示。
    */
    public static byte[] gcd(byte number1[],byte number2[])
    {
        int relation=compare(number1,number2);
        if(relation==0)
        {
            return number1;
        }
        else
        {
            if(relation<0)
            {
                byte temp[]=number1;
                number1=number2;
                number2=temp;
            }
            int size2=number2.length;
            for(;size2>0&&number2[size2-1]==0;size2--);
            if(size2==0)
            {
                return new byte[]{1};
            }
            do
            {
                byte result[][]=divide(number1,number2);
                number1=number2;
                number2=result[1];
                for(size2=number2.length;size2>0&&number2[size2-1]==0;size2--);
            }
            while(size2>0);
            int size1=number1.length;
            for(;size1>0&&number1[size1-1]==0;size1--);
            number2=number1;
            number1=new byte[size1+1];
            System.arraycopy(number2,0,number1,0,size1);
            return number1;
        }
    }
    /**
    <p>此方法会修改调用对象。</p><br>
    对当前有理数对象进行约分。
    @return 分子与分母的最大公因数的字节数组低位优先表示。
    */
    public byte[] reduce()
    {
        if(fraction[1]==null)
        {
            return new byte[]{1};
        }
        int relation=compare(fraction[0],fraction[1]);
        if(relation==0)
        {
            fraction[1]=null;
            denominator_size=0;
            while(numerator_size>1)
            {
                fraction[0][--numerator_size]=0;
            }
            fraction[0][0]=1;
            return new byte[]{0};
        }
        else
        {
            byte gcd[]=gcd(fraction[0],fraction[1]);
            fraction[0]=divide(fraction[0],gcd)[0];
            fraction[1]=divide(fraction[1],gcd)[0];
            for(numerator_size=fraction[0].length;numerator_size>0&&fraction[0][numerator_size-1]==0;numerator_size--);
            for(denominator_size=fraction[1].length;denominator_size>0&&fraction[1][denominator_size-1]==0;denominator_size--);
            if(denominator_size==1&&fraction[1][0]==1)
            {
                fraction[1]=null;
                denominator_size=0;
            }
            return gcd;
        }
    }
    /**
    通过有理数小数形式字符串构造高精度有理数对象。
    @param rational_string 字符串表示的小数形式有理数。
    */
    public big_rational(String rational_string)
    {
        char number_chars[]=rational_string.toCharArray();
        int length=number_chars.length;
        int offset=0;
        int point_index=length-1;
        boolean is_decimal=false;
        boolean is_loop=false;
        boolean is_negative=false;
        if(number_chars[0]=='-')
        {
            is_negative=true;
            offset++;
        }
        for(int i=1;i<length;i++)
        {
            char now=number_chars[i];
            if(now=='.')
            {
                is_decimal=true;
                point_index=i-(is_negative?1:0);
                offset++;
            }
            else if(now=='('||now==')')
            {
                is_loop=true;
                offset+=2;
                break;
            }
        }
        fraction=new byte[2][];
        byte numerator[]=fraction[0]=new byte[length-offset+1];
        offset=0;
        for(int i=0;i<length>>1;i++)
        {
            char temp=number_chars[i];
            number_chars[i]=number_chars[length-1-i];
            number_chars[length-1-i]=temp;
        }
        length-=is_negative?1:0;
        if(is_decimal)
        {
            byte denominator[]=fraction[1]=new byte[length-point_index+1];
            if(is_loop)
            {
                int loop_length=0;
                boolean loop_end=false;
                for(int i=0;i<length;i++)
                {
                    char now=number_chars[i];
                    if(now==')')
                    {
                        offset++;
                        loop_length=i;
                    }
                    else if(now=='(')
                    {
                        offset++;
                        loop_length=i-loop_length-1;
                        loop_end=true;
                    }
                    else if(now=='.')
                    {
                        offset++;
                        denominator_size=i-offset+1;
                    }
                    else 
                    {
                        numerator[i-offset]=(byte)(now-'0');
                        if(loop_end)
                        {
                            numerator[i-loop_length-offset+1]+=(byte)(Math.floorDiv(numerator[i-loop_length-offset]-now+'0',10));
                            numerator[i-loop_length-offset]=(byte)(Math.floorMod(numerator[i-loop_length-offset]-now+'0',10));
                        }
                    }
                }
                for(int i=denominator_size-loop_length;i<denominator_size;i++)
                {
                    denominator[i]=9;
                }
            }
            else
            {
                for(int i=0;i<length;i++)
                {
                    char now=number_chars[i];
                    if(now=='.')
                    {
                        offset=1;
                        denominator_size=i+1;
                    }
                    else
                    {
                        numerator[i-offset]=(byte)(now-'0');
                    }
                }
                numerator_size=length-1;
                denominator[denominator_size-1]=1;
            }
        }
        else
        {
            for(;numerator_size<length;numerator_size++)
            {
                numerator[numerator_size]=(byte)(number_chars[numerator_size]-'0');
            }
        }
        reduce();
        if(numerator_size>0)
        {
            fraction[0][numerator_size-1]*=is_negative?-1:1;
        }
    }
    /**
    通过有理数分数形式字符串构造高精度有理数对象。
    @param numerator_string 字符串表示的分子。
    @param denominator_string 字符串表示的分母。
    */
    public big_rational(String numerator_string,String denominator_string)
    {
        int numerator_length=numerator_string.length();
        int denominator_length=denominator_string.length();
        boolean is_negative=numerator_string.charAt(0)=='-';
        int offset=is_negative?1:0;
        numerator_size=numerator_length-offset;
        denominator_size=denominator_length;
        fraction=new byte[2][];
        byte numerator[]=fraction[0]=new byte[numerator_size+1];
        byte denominator[]=fraction[1]=new byte[denominator_size+1];
        for(int i=0;i<numerator_size;i++)
        {
            numerator[i]=(byte)(numerator_string.charAt(numerator_size-i-1+offset)-'0');
        }
        for(int i=0;i<denominator_size;i++)
        {
            denominator[i]=(byte)(denominator_string.charAt(denominator_size-i-1)-'0');
        }
        reduce();
        if(numerator_size>0)
        {
            fraction[0][numerator_size-1]*=is_negative?-1:1;
        }
    }
    /**
    通过有理数分数字节数组低位优先表示构造高精度有理数对象。
    @param numerator_array 分子字节数组低位优先表示，表示分子。
    @param denominator_array 分母字节数组低位优先表示，表示分母。
    */
    public big_rational(byte numerator_array[],byte denominator_array[])
    {
        for(numerator_size=numerator_array.length;numerator_size>0&&numerator_array[numerator_size-1]==0;numerator_size--);
        fraction=new byte[2][];
        if(numerator_size==0)
        {
            fraction[0]=new byte[]{0};
            fraction[1]=null;
            return;
        }
        fraction[0]=new byte[numerator_size+1];
        System.arraycopy(numerator_array,0,fraction[0],0,numerator_size);
        if(denominator_array!=null)
        {
            for(denominator_size=denominator_array.length;denominator_size>0&&denominator_array[denominator_size-1]==0;denominator_size--);
            if(denominator_size>0)
            {
                fraction[1]=new byte[denominator_size+1];
                System.arraycopy(denominator_array,0,fraction[1],0,denominator_size);
                boolean is_negative=fraction[0][numerator_size-1]<0;
                fraction[0][numerator_size-1]*=is_negative?-1:1;
                reduce();
                fraction[0][numerator_size-1]*=is_negative?-1:1;
            }
        }
    }
    /**
    <p>此方法会修改输入的数据。</p><br>
    通分两个有理数。
    @param rational1 第一个有理数对象。
    @param rational2 第二个有理数对象。
    @return 一个二维字节数组：
    <ol>
        <li>第一个有理数的通分乘数的字节数组低位优先表示。</li>
        <li>第二个有理数的通分乘数的字节数组低位优先表示。</li>
    </ol>
    */
    public static byte[][] common_denominator(big_rational rational1,big_rational rational2)
    {
        byte multiplier1[]=new byte[]{1};
        byte multiplier2[]=new byte[]{1};
        if(rational1.fraction[1]==null&&rational2.fraction[1]==null)
        {
        }
        else if(rational1.fraction[1]==null)
        {
            rational1.fraction[1]=new byte[rational2.denominator_size+1];
            System.arraycopy(rational2.fraction[1],0,rational1.fraction[1],0,rational2.denominator_size);
            rational1.denominator_size=rational2.denominator_size;
            multiplier1=rational2.fraction[1];
            rational1.fraction[0]=multiply(rational1.fraction[0],multiplier1);
            int numerator_size1=rational1.fraction[0].length;
            int denominator_size1=rational1.fraction[1].length;
            for(;numerator_size1>0&&rational1.fraction[0][numerator_size1-1]==0;numerator_size1--);
            for(;denominator_size1>0&&rational1.fraction[1][denominator_size1-1]==0;denominator_size1--);
            rational1.numerator_size=numerator_size1;
            rational1.denominator_size=denominator_size1;
        }
        else if(rational2.fraction[1]==null)
        {
            rational2.fraction[1]=new byte[rational1.denominator_size+1];
            System.arraycopy(rational1.fraction[1],0,rational2.fraction[1],0,rational1.denominator_size);
            rational2.denominator_size=rational1.denominator_size;
            multiplier2=rational1.fraction[1];
            rational2.fraction[0]=multiply(rational2.fraction[0],multiplier2);
            int numerator_size2=rational2.fraction[0].length;
            int denominator_size2=rational2.fraction[1].length;
            for(;numerator_size2>0&&rational2.fraction[0][numerator_size2-1]==0;numerator_size2--);
            for(;denominator_size2>0&&rational2.fraction[1][denominator_size2-1]==0;denominator_size2--);
            rational2.numerator_size=numerator_size2;
            rational2.denominator_size=denominator_size2;
        }
        else
        {
            byte denominator_lcm[]=divide(multiply(rational1.fraction[1],rational2.fraction[1]),gcd(rational1.fraction[1],rational2.fraction[1]))[0];
            multiplier1=divide(denominator_lcm,rational1.fraction[1])[0];
            multiplier2=divide(denominator_lcm,rational2.fraction[1])[0];
            rational1.fraction[0]=multiply(rational1.fraction[0],multiplier1);
            rational2.fraction[0]=multiply(rational2.fraction[0],multiplier2);
            rational1.fraction[1]=new byte[denominator_lcm.length];
            System.arraycopy(denominator_lcm,0,rational1.fraction[1],0,denominator_lcm.length);
            rational2.fraction[1]=denominator_lcm;
            int numerator_size1=rational1.fraction[0].length;
            int numerator_size2=rational2.fraction[0].length;
            int denominator_size1=rational1.fraction[1].length;
            int denominator_size2=rational2.fraction[1].length;
            for(;numerator_size1>0&&rational1.fraction[0][numerator_size1-1]==0;numerator_size1--);
            for(;numerator_size2>0&&rational2.fraction[0][numerator_size2-1]==0;numerator_size2--);
            for(;denominator_size1>0&&rational1.fraction[1][denominator_size1-1]==0;denominator_size1--);
            for(;denominator_size2>0&&rational2.fraction[1][denominator_size2-1]==0;denominator_size2--);
            rational1.numerator_size=numerator_size1;
            rational2.numerator_size=numerator_size2;
            rational1.denominator_size=denominator_size1;
            rational2.denominator_size=denominator_size2;
        }
        return new byte[][]{multiplier1,multiplier2};
    }
    /**
    计算两个有理数的和 <code>addend1</code>+<code>addend2</code>。
    @param addend1 第一个有理数对象。
    @param addend2 第二个有理数对象。
    @return 两个有理数对象的和。
    */
    public static big_rational add(big_rational addend1,big_rational addend2)
    {
        byte multiplier[][]=common_denominator(addend1,addend2);
        big_rational result=new big_rational(add(addend1.fraction[0],addend2.fraction[0]),addend1.fraction[1]);
        addend1.fraction[0]=divide(addend1.fraction[0],multiplier[0])[0];
        addend2.fraction[0]=divide(addend2.fraction[0],multiplier[1])[0];
        int numerator_size1=addend1.fraction[0].length;
        int numerator_size2=addend2.fraction[0].length;
        for(;numerator_size1>0&&addend1.fraction[0][numerator_size1-1]==0;numerator_size1--);
        for(;numerator_size2>0&&addend2.fraction[0][numerator_size2-1]==0;numerator_size2--);
        addend1.numerator_size=numerator_size1;
        addend2.numerator_size=numerator_size2;
        if(addend1.fraction[1]!=null)
        {
            addend1.fraction[1]=divide(addend1.fraction[1],multiplier[0])[0];
            int denominator_size1=addend1.fraction[1].length;
            for(;denominator_size1>0&&addend1.fraction[1][denominator_size1-1]==0;denominator_size1--);
            addend1.denominator_size=denominator_size1;
        }
        if(addend2.fraction[1]!=null)
        {
            addend2.fraction[1]=divide(addend2.fraction[1],multiplier[1])[0];
            int denominator_size2=addend2.fraction[1].length;
            for(;denominator_size2>0&&addend2.fraction[1][denominator_size2-1]==0;denominator_size2--);
            addend2.denominator_size=denominator_size2;
        }
        if(addend1.denominator_size==0)
        {
            addend1.fraction[1]=null;
        }
        if(addend2.denominator_size==0)
        {
            addend2.fraction[1]=null;
        }
        return result;
    }
    /**
    计算两个有理数的差 <code>minuend</code>-<code>subtrahend</code>。
    @param minuend 被减数有理数对象。
    @param subtrahend 减数有理数对象。
    @return 两个有理数对象的差。
    */
    public static big_rational subtract(big_rational minuend,big_rational subtrahend)
    {
        byte multiplier[][]=common_denominator(minuend,subtrahend);
        big_rational result=new big_rational(subtract(minuend.fraction[0],subtrahend.fraction[0]),minuend.fraction[1]);
        minuend.fraction[0]=divide(minuend.fraction[0],multiplier[0])[0];
        subtrahend.fraction[0]=divide(subtrahend.fraction[0],multiplier[1])[0];
        int numerator_size1=minuend.fraction[0].length;
        int numerator_size2=subtrahend.fraction[0].length;
        for(;numerator_size1>0&&minuend.fraction[0][numerator_size1-1]==0;numerator_size1--);
        for(;numerator_size2>0&&subtrahend.fraction[0][numerator_size2-1]==0;numerator_size2--);
        minuend.numerator_size=numerator_size1;
        subtrahend.numerator_size=numerator_size2;
        if(minuend.fraction[1]!=null)
        {
            minuend.fraction[1]=divide(minuend.fraction[1],multiplier[0])[0];
            int denominator_size1=minuend.fraction[1].length;
            for(;denominator_size1>0&&minuend.fraction[1][denominator_size1-1]==0;denominator_size1--);
            minuend.denominator_size=denominator_size1;
        }
        if(subtrahend.fraction[1]!=null)
        {
            subtrahend.fraction[1]=divide(subtrahend.fraction[1],multiplier[1])[0];
            int denominator_size2=subtrahend.fraction[1].length;
            for(;denominator_size2>0&&subtrahend.fraction[1][denominator_size2-1]==0;denominator_size2--);
            subtrahend.denominator_size=denominator_size2;
        }
        if(minuend.denominator_size==0)
        {
            minuend.fraction[1]=null;
        }
        if(subtrahend.denominator_size==0)
        {
            subtrahend.fraction[1]=null;
        }
        return result;
    }
    /**
    计算两个有理数的积 <code>factor1</code>*<code>factor2</code>。
    @param factor1 第一个有理数对象。
    @param factor2 第二个有理数对象。
    @return 两个有理数对象的积。
    */
    public static big_rational multiply(big_rational factor1,big_rational factor2)
    {
        byte result_numerator[]=multiply(factor1.fraction[0],factor2.fraction[0]);
        if(factor1.fraction[1]==null&&factor2.fraction[1]==null)
        {
            return new big_rational(result_numerator,null);
        }
        else if(factor1.fraction[1]==null)
        {
            return new big_rational(result_numerator,factor2.fraction[1]);
        }
        else if(factor2.fraction[1]==null)
        {
            return new big_rational(result_numerator,factor1.fraction[1]);
        }
        else
        {
            return new big_rational(result_numerator,multiply(factor1.fraction[1],factor2.fraction[1]));
        }
    }
    /**
    计算两个有理数的商 <code>factor1</code>/<code>factor2</code>。
    @param dividend 第一个有理数对象。
    @param divisor 第二个有理数对象。
    @return 两个有理数对象的商。<br>
    若除数为0，则返回<code>null</code>。
    */
    public static big_rational divide(big_rational dividend,big_rational divisor)
    {
        if(divisor.numerator_size==0)
        {
            return null;
        }
        if(dividend.fraction[1]==null&&divisor.fraction[1]==null)
        {
            return new big_rational(dividend.fraction[0],divisor.fraction[0]);
        }
        else if(dividend.fraction[1]==null)
        {
            return new big_rational(multiply(dividend.fraction[0],divisor.fraction[1]),divisor.fraction[0]);
        }
        else if(divisor.fraction[1]==null)
        {
            return new big_rational(dividend.fraction[0],multiply(divisor.fraction[0],dividend.fraction[1]));
        }
        else
        {
            return new big_rational(multiply(dividend.fraction[0],divisor.fraction[1]),multiply(divisor.fraction[0],dividend.fraction[1]));
        }
    }
    public String toString()
    {
        StringBuilder result=new StringBuilder();
        if(mode<=0)
        {
            if(numerator_size==0)
            {
                result.append("0");
            }
            for(int i=numerator_size-1;i>=0;i--)
            {
                result.append(fraction[0][i]);
            }
        }
        if(fraction[1]!=null)
        {
            if(mode<=0)
            {
                result.append("/");
                for(int i=denominator_size-1;i>=0;i--)
                {
                    result.append(fraction[1][i]);
                }
            }
            if(mode==0)
            {
                result.append("  ");
            }
            if(mode>=0)
            {
                boolean is_negative=fraction[0][numerator_size-1]<0;
                byte absolute_numerator[]=new byte[numerator_size+1];
                System.arraycopy(fraction[0],0,absolute_numerator,0,numerator_size);
                if(is_negative)
                {
                    absolute_numerator[numerator_size-1]*=-1;
                    result.append("-");
                }
                byte absolute_denominator[]=new byte[denominator_size+1];
                System.arraycopy(fraction[1],0,absolute_denominator,0,denominator_size);
                byte quotient_and_remainder[][]=divide(absolute_numerator,absolute_denominator);
                byte quotient[]=quotient_and_remainder[0];
                byte remainder[]=new byte[denominator_size+1];
                System.arraycopy(quotient_and_remainder[1],0,remainder,0,quotient_and_remainder[1].length);
                for(int i=quotient_and_remainder[1].length;i<=denominator_size;i++)
                {
                    remainder[i]=0;
                }
                int quotient_size=quotient.length;
                int remainder_size=remainder.length;
                for(;quotient_size>0&&quotient[quotient_size-1]==0;quotient_size--);
                for(;remainder[remainder_size-1]==0;remainder_size--);
                if(quotient_size==0)
                {
                    result.append("0");
                }
                for(int i=quotient_size-1;i>=0;i--)
                {
                    result.append((char)(quotient[i]+'0'));
                }
                result.append(".");
                byte remainders[][]=new byte[10][];
                int remainders_size[]=new int[10];
                int size=0,capacity=10;
                StringBuilder decimal=new StringBuilder();
                while(remainder_size>0)
                {
                    int loop_start=-1;
                    for(int i=0;i<size;i++)
                    {
                        if(compare(remainders[i],remainder)==0)
                        {
                            loop_start=i;
                            break;
                        }
                    }
                    if(loop_start!=-1)
                    {
                        decimal.insert(loop_start,"(");
                        decimal.append(")");
                        break;
                    }
                    if(size==capacity)
                    {
                        capacity=(capacity<<1)+2;
                        byte new_remainders[][]=new byte[capacity][];
                        int new_remainders_size[]=new int[capacity];
                        System.arraycopy(remainders,0,new_remainders,0,size);
                        System.arraycopy(remainders_size,0,new_remainders_size,0,size);
                        remainders=new_remainders;
                        remainders_size=new_remainders_size;
                    }
                    remainders[size]=new byte[remainder_size];
                    System.arraycopy(remainder,0,remainders[size],0,remainder_size);
                    remainders_size[size]=remainder_size;
                    size++;
                    for(int i=remainder_size;i>0;i--)
                    {
                        remainder[i]=remainder[i-1];
                    }
                    remainder[0]=0;
                    remainder_size++;
                    int one_quotient=0;
                    for(;compare(remainder,absolute_denominator)>=0;one_quotient++)
                    {
                        for(int i=0;i<remainder_size;i++)
                        {
                            remainder[i]-=absolute_denominator[i];
                            if(remainder[i]<0)
                            {
                                remainder[i+1]--;
                                remainder[i]+=10;
                            }
                        }
                    }
                    decimal.append((char)(one_quotient+'0'));
                    for(remainder_size=remainder.length;remainder_size>0&&remainder[remainder_size-1]==0;remainder_size--);
                }
                result.append(decimal);
            }
        }
        return result.toString();
    }
}