package tools.mathematics;
/**
<p>高精度整数类。</p><br>
整数，即不含分数部分的数，包含正整数、负整数和零。<br>
本高精度整数以字节数组和位数实现。<br>
数组中的每个元素表示整数中的一位，低位优先存储。
*/
public class big_integer implements Comparable<big_integer>
{
    public byte number[];
    public int size;
    /**
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
        number=new byte[length-offset+1];
        size=length-offset;
        for(int i=offset;i<length;i++)
        {
            number[length-i-1]=(byte)(number_string.charAt(i)-'0');
        }
        for(;size>0&&number[size-1]==0;size--);
        if(size>0)
        {
            number[size-1]*=is_negative?-1:1;
        }
    }
    /**
    通过整数字节数组低位优先表示构造高精度整数对象。
    @param number_array 整数字节数组低位优先表示。
    */
    public big_integer(byte number_array[])
    {
        this.number=new byte[number_array.length+1];
        System.arraycopy(number_array,0,this.number,0,number_array.length);
        this.size=number_array.length;
        for(;this.size>0&&this.number[this.size-1]==0;this.size--);
    }
    /**
    通过整数字节数组低位优先表示和位数构造高精度整数对象。<br>
    本构造方法会直接使用输入的字节数组和位数，不进行拷贝和检查。
    @param number_array 整数字节数组低位优先表示。
    @param size 整数的位数。
    */
    public big_integer(byte number_array[],int size)
    {
        this.number=number_array;
        this.size=size;
    }
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
    @param addend1 第一个加数的字节数组低位优先表示。
    @param addend2 第二个加数的字节数组低位优先表示。
    @return 和的字节数组低位优先表示。
    */
    public static byte[] add(byte addend1[],byte addend2[])
    {
        int size1=addend1.length;
        int size2=addend2.length;
        for(;size1>0&&addend1[size1-1]==0;size1--);
        for(;size2>0&&addend2[size2-1]==0;size2--);
        int positive1=size1>0?(addend1[size1-1]>=0?1:-1):0;
        int positive2=size2>0?(addend2[size2-1]>=0?1:-1):0;
        int size_sum=size1>size2?size1+1:size2+1;
        if(positive1==0||positive2==0)
        {
            byte sum[]=new byte[size_sum+1];
            System.arraycopy(positive1==0?addend2:addend1,0,sum,0,positive1==0?size2:size1);
            return sum;
        }
        byte inner_addend1[]=new byte[size1];
        byte inner_addend2[]=new byte[size2];
        System.arraycopy(addend1,0,inner_addend1,0,size1);
        System.arraycopy(addend2,0,inner_addend2,0,size2);
        if(positive1==positive2)
        {
            inner_addend1[size1-1]*=positive1;
            inner_addend2[size2-1]*=positive1;
            byte sum[]=new byte[size_sum+1];
            if(size1<size2)
            {
                byte temp[]=inner_addend1;
                inner_addend1=inner_addend2;
                inner_addend2=temp;
                int temp_size=size1;
                size1=size2;
                size2=temp_size;
            }
            for(int i=0;i<size2;i++)
            {
                sum[i+1]+=(byte)((sum[i]+inner_addend1[i]+inner_addend2[i])/10);
                sum[i]=(byte)((sum[i]+inner_addend1[i]+inner_addend2[i])%10);
            }
            for(int i=size2;i<size1;i++)
            {
                sum[i+1]+=(byte)((sum[i]+inner_addend1[i])/10);
                sum[i]=(byte)((sum[i]+inner_addend1[i])%10);
            }
            for(;size_sum>0&&sum[size_sum-1]==0;size_sum--);
            sum[size_sum-1]*=positive1;
            return sum;
        }
        else
        {
            inner_addend1[size1-1]*=positive1;
            inner_addend2[size2-1]*=positive2;
            int positive_result=compare(inner_addend1,inner_addend2);
            positive_result=positive_result==0?0:(positive_result>0?1:-1);
            if(positive_result==0)
            {
                return new byte[]{0};
            }
            else if(positive_result<0)
            {
                byte temp[]=inner_addend1;
                inner_addend1=inner_addend2;
                inner_addend2=temp;
                int temp_size=size1;
                size1=size2;
                size2=temp_size;
            }
            byte sum[]=new byte[size_sum+1];
            for(int i=0;i<size2;i++)
            {
                sum[i+1]=(byte)Math.floorDiv(sum[i]+inner_addend1[i]-inner_addend2[i],10);
                sum[i]=(byte)Math.floorMod(sum[i]+inner_addend1[i]-inner_addend2[i],10);
            }
            for(int i=size2;i<size1;i++)
            {
                sum[i+1]+=(byte)Math.floorDiv(sum[i]+inner_addend1[i],10);
                sum[i]=(byte)Math.floorMod(sum[i]+inner_addend1[i],10);
            }
            for(;size_sum>0&&sum[size_sum-1]==0;size_sum--);
            sum[size_sum-1]*=positive1*positive_result;
            return sum;
        }
    }
    /**
    计算两个高精度整数的和 <code>addend1</code>+<code>addend2</code>。
    @param addend1 第一个高精度整数加数对象。
    @param addend2 第二个高精度整数加数对象。
    @return 两个高精度整数的和。
    */
    public static big_integer add(big_integer addend1,big_integer addend2)
    {
        int positive1=addend1.size>0?(addend1.number[addend1.size-1]>=0?1:-1):0;
        int positive2=addend2.size>0?(addend2.number[addend2.size-1]>=0?1:-1):0;
        int size_sum=addend1.size>addend2.size?addend1.size+1:addend2.size+1;
        if(positive1==0||positive2==0)
        {
            byte sum[]=new byte[size_sum+1];
            size_sum=positive1==0?addend2.size:addend1.size;
            System.arraycopy(positive1==0?addend2.number:addend1.number,0,sum,0,size_sum);
            return new big_integer(sum,size_sum);
        }
        big_integer inner_addend1=new big_integer(addend1.number);
        big_integer inner_addend2=new big_integer(addend2.number);
        if(positive1==positive2)
        {
            inner_addend1.number[inner_addend1.size-1]*=positive1;
            inner_addend2.number[inner_addend2.size-1]*=positive1;
            byte sum[]=new byte[size_sum+1];
            if(inner_addend1.size<inner_addend2.size)
            {
                big_integer temp=inner_addend1;
                inner_addend1=inner_addend2;
                inner_addend2=temp;
            }
            for(int i=0;i<inner_addend2.size;i++)
            {
                sum[i+1]+=(byte)((sum[i]+inner_addend1.number[i]+inner_addend2.number[i])/10);
                sum[i]=(byte)((sum[i]+inner_addend1.number[i]+inner_addend2.number[i])%10);
            }
            for(int i=inner_addend2.size;i<inner_addend1.size;i++)
            {
                sum[i+1]+=(byte)((sum[i]+inner_addend1.number[i])/10);
                sum[i]=(byte)((sum[i]+inner_addend1.number[i])%10);
            }
            for(;size_sum>0&&sum[size_sum-1]==0;size_sum--);
            sum[size_sum-1]*=positive1;
            inner_addend1.number[inner_addend1.size-1]*=positive1;
            inner_addend2.number[inner_addend2.size-1]*=positive1;
            return new big_integer(sum,size_sum);
        }
        else
        {
            inner_addend1.number[inner_addend1.size-1]*=positive1;
            inner_addend2.number[inner_addend2.size-1]*=positive2;
            int positive_result=inner_addend1.compareTo(inner_addend2);
            positive_result=positive_result==0?0:(positive_result>0?1:-1);
            if(positive_result==0)
            {
                return new big_integer(new byte[]{0},0);
            }
            else if(positive_result<0)
            {
                big_integer temp=inner_addend1;
                inner_addend1=inner_addend2;
                inner_addend2=temp;
            }
            byte sum[]=new byte[size_sum+1];
            for(int i=0;i<inner_addend2.size;i++)
            {
                sum[i+1]=(byte)Math.floorDiv(sum[i]+inner_addend1.number[i]-inner_addend2.number[i],10);
                sum[i]=(byte)Math.floorMod(sum[i]+inner_addend1.number[i]-inner_addend2.number[i],10);
            }
            for(int i=inner_addend2.size;i<inner_addend1.size;i++)
            {
                sum[i+1]=(byte)Math.floorDiv(sum[i]+inner_addend1.number[i],10);
                sum[i]=(byte)Math.floorMod(sum[i]+inner_addend1.number[i],10);
            }
            for(;size_sum>0&&sum[size_sum-1]==0;size_sum--);
            sum[size_sum-1]*=positive1*positive_result;
            inner_addend1.number[inner_addend1.size-1]*=positive1*positive_result;
            inner_addend2.number[inner_addend2.size-1]*=positive2*positive_result;
            return new big_integer(sum,size_sum);
        }
    }
    /**
    计算两个字节数组低位优先表示的整数的差 <code>minuend</code>-<code>subtrahend</code>。
    @param minuend 被减数的字节数组低位优先表示。
    @param subtrahend 减数的字节数组低位优先表示。
    @return 差的字节数组低位优先表示。
    */
    public static byte[] subtract(byte minuend[],byte subtrahend[])
    {
        int size_minuend=minuend.length;
        int size_subtrahend=subtrahend.length;
        for(;size_minuend>0&&minuend[size_minuend-1]==0;size_minuend--);
        for(;size_subtrahend>0&&subtrahend[size_subtrahend-1]==0;size_subtrahend--);
        int positive_minuend=size_minuend>0?(minuend[size_minuend-1]>=0?1:-1):0;
        int positive_subtrahend=size_subtrahend>0?(subtrahend[size_subtrahend-1]>=0?1:-1):0;
        int positive_result=compare(minuend,subtrahend);
        positive_result=positive_result==0?0:(positive_result>0?1:-1);
        int size_difference=size_minuend>size_subtrahend?size_minuend+1:size_subtrahend+1;
        if(positive_result==0)
        {
            return new byte[]{0};
        }
        else if(positive_minuend==0||positive_subtrahend==0)
        {
            byte difference[]=new byte[size_difference+1];
            int size_result=size_minuend>size_subtrahend?size_minuend:size_subtrahend;
            System.arraycopy(positive_subtrahend==0?minuend:subtrahend,0,difference,0,size_result);
            difference[size_result-1]*=positive_subtrahend==0?1:-1;
            return difference;
        }
        byte inner_minuend[]=new byte[size_minuend];
        byte inner_subtrahend[]=new byte[size_subtrahend];
        System.arraycopy(minuend,0,inner_minuend,0,size_minuend);
        System.arraycopy(subtrahend,0,inner_subtrahend,0,size_subtrahend);
        if(positive_minuend==positive_subtrahend)
        {
            inner_minuend[size_minuend-1]*=positive_minuend;
            inner_subtrahend[size_subtrahend-1]*=positive_minuend;
            if(positive_result*positive_minuend<0)
            {
                byte temp[]=inner_minuend;
                inner_minuend=inner_subtrahend;
                inner_subtrahend=temp;
                int temp_size=size_minuend;
                size_minuend=size_subtrahend;
                size_subtrahend=temp_size;
            }
            byte difference[]=new byte[size_difference+1];
            for(int i=0;i<size_subtrahend;i++)
            {
                difference[i+1]=(byte)Math.floorDiv(difference[i]+inner_minuend[i]-inner_subtrahend[i],10);
                difference[i]=(byte)Math.floorMod(difference[i]+inner_minuend[i]-inner_subtrahend[i],10);
            }
            for(int i=size_subtrahend;i<size_minuend;i++)
            {
                difference[i+1]=(byte)Math.floorDiv(difference[i]+inner_minuend[i],10);
                difference[i]=(byte)Math.floorMod(difference[i]+inner_minuend[i],10);
            }
            for(;size_difference>0&&difference[size_difference-1]==0;size_difference--);
            difference[size_difference-1]*=positive_result;
            return difference;
        }
        else
        {
            inner_minuend[size_minuend-1]*=positive_minuend;
            inner_subtrahend[size_subtrahend-1]*=positive_subtrahend;
            int positive_absolute=compare(inner_minuend,inner_subtrahend);
            positive_absolute=positive_absolute==0?0:(positive_absolute>0?1:-1);
            if(positive_absolute==0)
            {
                inner_minuend[size_minuend-1]*=positive_minuend;
                inner_subtrahend[size_subtrahend-1]*=positive_subtrahend;
                return new byte[]{0};
            }
            else if(positive_absolute<0)
            {
                byte temp[]=inner_minuend;
                inner_minuend=inner_subtrahend;
                inner_subtrahend=temp;
                int temp_size=size_minuend;
                size_minuend=size_subtrahend;
                size_subtrahend=temp_size;
            }
            byte difference[]=new byte[size_difference+1];
            for(int i=0;i<size_subtrahend;i++)
            {
                difference[i+1]=(byte)((difference[i]+inner_minuend[i]+inner_subtrahend[i])/10);
                difference[i]=(byte)((difference[i]+inner_minuend[i]+inner_subtrahend[i])%10);
            }
            for(int i=size_subtrahend;i<size_minuend;i++)
            {
                difference[i+1]=(byte)((difference[i]+inner_minuend[i])/10);
                difference[i]=(byte)((difference[i]+inner_minuend[i])%10);
            }
            for(;size_difference>0&&difference[size_difference-1]==0;size_difference--);
            difference[size_difference-1]*=positive_result;
            return difference;
        }
    }
    /**
    计算两个高精度整数的差 <code>minuend</code>-<code>subtrahend</code>。
    @param minuend 高精度整数被减数对象。
    @param subtrahend 高精度整数减数对象。
    @return 两个高精度整数的差。
    */
    public static big_integer subtract(big_integer minuend,big_integer subtrahend)
    {
        int positive_minuend=minuend.size>0?(minuend.number[minuend.size-1]>=0?1:-1):0;
        int positive_subtrahend=subtrahend.size>0?(subtrahend.number[subtrahend.size-1]>=0?1:-1):0;
        int positive_result=minuend.compareTo(subtrahend);
        positive_result=positive_result==0?0:(positive_result>0?1:-1);
        int size_difference=minuend.size>subtrahend.size?minuend.size+1:subtrahend.size+1;
        if(positive_result==0)
        {
            return new big_integer(new byte[]{0},0);
        }
        else if(positive_minuend==0||positive_subtrahend==0)
        {
            byte difference[]=new byte[size_difference+1];
            int size_result=minuend.size>subtrahend.size?minuend.size:subtrahend.size;
            System.arraycopy(positive_subtrahend==0?minuend.number:subtrahend.number,0,difference,0,size_result);
            difference[size_result-1]*=positive_subtrahend==0?1:-1;
            return new big_integer(difference,size_result);
        }
        big_integer inner_minuend=new big_integer(minuend.number);
        big_integer inner_subtrahend=new big_integer(subtrahend.number);
        if(positive_minuend==positive_subtrahend)
        {
            inner_minuend.number[inner_minuend.size-1]*=positive_minuend;
            inner_subtrahend.number[inner_subtrahend.size-1]*=positive_minuend;
            if(positive_result*positive_minuend<0)
            {
                big_integer temp=inner_minuend;
                inner_minuend=inner_subtrahend;
                inner_subtrahend=temp;
            }
            byte difference[]=new byte[size_difference+1];
            for(int i=0;i<inner_subtrahend.size;i++)
            {
                difference[i+1]=(byte)Math.floorDiv(difference[i]+inner_minuend.number[i]-inner_subtrahend.number[i],10);
                difference[i]=(byte)Math.floorMod(difference[i]+inner_minuend.number[i]-inner_subtrahend.number[i],10);
            }
            for(int i=inner_subtrahend.size;i<inner_minuend.size;i++)
            {
                difference[i+1]+=(byte)Math.floorDiv(difference[i]+inner_minuend.number[i],10);
                difference[i]=(byte)Math.floorMod(difference[i]+inner_minuend.number[i],10);
            }
            for(;size_difference>0&&difference[size_difference-1]==0;size_difference--);
            difference[size_difference-1]*=positive_result;
            return new big_integer(difference,size_difference);
        }
        else
        {
            inner_minuend.number[inner_minuend.size-1]*=positive_minuend;
            inner_subtrahend.number[inner_subtrahend.size-1]*=positive_subtrahend;
            int positive_absolute=inner_minuend.compareTo(inner_subtrahend);
            positive_absolute=positive_absolute==0?0:(positive_absolute>0?1:-1);
            if(positive_absolute==0)
            {
                return new big_integer(new byte[]{0},0);
            }
            else if(positive_absolute<0)
            {
                big_integer temp=inner_minuend;
                inner_minuend=inner_subtrahend;
                inner_subtrahend=temp;
            }
            byte difference[]=new byte[size_difference+1];
            for(int i=0;i<inner_subtrahend.size;i++)
            {
                difference[i+1]=(byte)((difference[i]+inner_minuend.number[i]+inner_subtrahend.number[i])/10);
                difference[i]=(byte)((difference[i]+inner_minuend.number[i]+inner_subtrahend.number[i])%10);
            }
            for(int i=inner_subtrahend.size;i<inner_minuend.size;i++)
            {
                difference[i+1]=(byte)((difference[i]+inner_minuend.number[i])/10);
                difference[i]=(byte)((difference[i]+inner_minuend.number[i])%10);
            }
            for(;size_difference>0&&difference[size_difference-1]==0;size_difference--);
            difference[size_difference-1]*=positive_result;
            return new big_integer(difference,size_difference);
        }
    }
    /**
    计算一个字节数组低位优先表示的整数与一个一位整数的积 <code>factor</code>*<code>one_bit_multiplier</code>。
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
            product[i+1]=(byte)((product[i]+factor[i]*one_bit_multiplier)/10);
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
    计算一个高精度整数与一个一位整数的积 <code>factor</code>*<code>one_bit_multiplier</code>。
    @param factor 高精度整数因数对象。
    @param one_bit_multiplier 一位因数。
    @return 高精度整数与一位整数的积。
    */
    public static big_integer multiply(big_integer factor,int one_bit_multiplier)
    {
        if(one_bit_multiplier==0||factor.size==0)
        {
            return new big_integer(new byte[]{0},0);
        }
        int positive_factor=factor.number[factor.size-1]>=0?1:-1;
        int positive_multiplier=one_bit_multiplier>=0?1:-1;
        byte product[]=new byte[factor.size+2];
        factor.number[factor.size-1]*=positive_factor;
        one_bit_multiplier*=positive_multiplier;
        for(int i=0;i<factor.size;i++)
        {
            product[i+1]=(byte)((product[i]+factor.number[i]*one_bit_multiplier)/10);
            product[i]=(byte)((product[i]+factor.number[i]*one_bit_multiplier)%10);
        }
        int size_product=product.length;
        for(;size_product>0&&product[size_product-1]==0;size_product--);
        product[size_product-1]*=positive_factor*positive_multiplier;
        factor.number[factor.size-1]*=positive_factor;
        return new big_integer(product,size_product);
    }
    /**
    计算两个字节数组低位优先表示的整数的积 <code>factor1</code>*<code>factor2</code>。
    @param factor1 第一个整数的字节数组低位优先表示。
    @param factor2 第二个整数的字节数组低位优先表示。
    @return 积的字节数组低位优先表示。
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
        byte inner_factor1[]=new byte[size1];
        byte inner_factor2[]=new byte[size2];
        System.arraycopy(factor1,0,inner_factor1,0,size1);
        System.arraycopy(factor2,0,inner_factor2,0,size2);
        int positive1=size1>0?(inner_factor1[size1-1]>=0?1:-1):0;
        int positive2=size2>0?(inner_factor2[size2-1]>=0?1:-1):0;
        byte product[]=new byte[size1+size2+1];
        inner_factor1[size1-1]*=positive1;
        inner_factor2[size2-1]*=positive2;
        for(int i=0;i<size1;i++)
        {
            for(int j=0;j<size2;j++)
            {
                product[i+j+1]+=(byte)((product[i+j]+inner_factor1[i]*inner_factor2[j])/10);
                product[i+j]=(byte)((product[i+j]+inner_factor1[i]*inner_factor2[j])%10);
            }
        }
        if(positive1!=positive2)
        {
            int size_product=product.length;
            for(;size_product>0&&product[size_product-1]==0;size_product--);
            product[size_product-1]*=-1;
        }
        return product;
    }
    /**
    计算两个高精度整数的积 <code>factor1</code>*<code>factor2</code>。
    @param factor1 第一个高精度整数因数对象。
    @param factor2 第二个高精度整数因数对象。
    @return 两个高精度整数的积。
    */
    public static big_integer multiply(big_integer factor1,big_integer factor2)
    {
        if(factor1.size==0||factor2.size==0)
        {
            return new big_integer(new byte[]{0},0);
        }
        big_integer inner_factor1=new big_integer(factor1.number);
        big_integer inner_factor2=new big_integer(factor2.number);
        int positive1=inner_factor1.size>0?(inner_factor1.number[inner_factor1.size-1]>=0?1:-1):0;
        int positive2=inner_factor2.size>0?(inner_factor2.number[inner_factor2.size-1]>=0?1:-1):0;
        byte product[]=new byte[inner_factor1.size+inner_factor2.size+1];
        inner_factor1.number[inner_factor1.size-1]*=positive1;
        inner_factor2.number[inner_factor2.size-1]*=positive2;
        for(int i=0;i<inner_factor1.size;i++)
        {
            for(int j=0;j<inner_factor2.size;j++)
            {
                product[i+j+1]+=(byte)((product[i+j]+inner_factor1.number[i]*inner_factor2.number[j])/10);
                product[i+j]=(byte)((product[i+j]+inner_factor1.number[i]*inner_factor2.number[j])%10);
            }
        }
        int size_product=product.length;
        for(;size_product>0&&product[size_product-1]==0;size_product--);
        product[size_product-1]*=positive1*positive2;
        return new big_integer(product,size_product);
    }
    /**
    计算一个字节数组低位优先表示的整数与一个一位整数的商 <code>dividend</code>/<code>one_bit_divisor</code>。
    @param dividend 被除数的字节数组低位优先表示。
    @param one_bit_divisor 一位除数。
    @return 一个二维字节数组：
    <ol>
        <li>商的字节数组低位优先表示。</li>
        <li>余数的字节数组低位优先表示。</li>
    </ol><br>
    若除数为0，则返回<code>null</code>。
    */
    public static byte[][] divide(byte dividend[],int one_bit_divisor)
    {
        if(one_bit_divisor==0)
        {
            return null;
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
    计算一个高精度整数与一个一位整数的商 <code>dividend</code>/<code>one_bit_divisor</code>。
    @param dividend 高精度整数被除数对象。
    @param one_bit_divisor 一位除数。
    @return 一个高精度整数数组：
    <ol>
        <li>高精度整数商。</li>
        <li>高精度整数余数。</li>
    </ol><br>
    若除数为0，则返回<code>null</code>。
    */
    public static big_integer[] divide(big_integer dividend,int one_bit_divisor)
    {
        if(dividend.size==0)
        {
            return new big_integer[]{new big_integer(new byte[]{0},0),new big_integer(new byte[]{0},0)};
        }
        if(one_bit_divisor==0)
        {
            return null;
        }
        byte quotient[]=new byte[dividend.size+1];
        if(one_bit_divisor==1||one_bit_divisor==-1)
        {
            System.arraycopy(dividend.number,0,quotient,0,dividend.size);
            quotient[dividend.size-1]*=one_bit_divisor;
            return new big_integer[]{new big_integer(quotient,dividend.size),new big_integer(new byte[]{0},0)};
        }
        int positive_dividend=dividend.number[dividend.size-1]>=0?1:-1;
        int positive_divisor=one_bit_divisor>=0?1:-1;
        dividend.number[dividend.size-1]*=positive_dividend;
        one_bit_divisor*=positive_divisor;
        int remainder=0;
        for(int i=dividend.size-1;i>=0;i--)
        {
            remainder=remainder*10+dividend.number[i];
            quotient[i]=(byte)(remainder/one_bit_divisor);
            remainder%=one_bit_divisor;
        }
        int quotient_size=dividend.size;
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
        dividend.number[dividend.size-1]*=positive_dividend;
        byte result[]=new byte[quotient_size+1];
        System.arraycopy(quotient,0,result,0,quotient_size);
        return new big_integer[]{new big_integer(result,quotient_size),new big_integer(new byte[]{(byte)remainder},remainder==0?0:1)};
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
    若除数为0，则返回<code>null</code>。
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
            return null;
        }
        else if(positive_dividend==0)
        {
            return new byte[][]{{0},{0}};
        }
        byte inner_dividend[]=new byte[dividend_size+1];
        byte inner_divisor[]=new byte[divisor_size+1];
        System.arraycopy(dividend,0,inner_dividend,0,dividend_size);
        System.arraycopy(divisor,0,inner_divisor,0,divisor_size);
        inner_dividend[dividend_size-1]*=positive_dividend;
        inner_divisor[divisor_size-1]*=positive_divisor;
        int delta=10/(inner_divisor[divisor_size-1]+1);
        if(delta>1)
        {
            inner_dividend=multiply(inner_dividend,delta);
            inner_divisor=multiply(inner_divisor,delta);
            for(dividend_size=inner_dividend.length;dividend_size>0&&inner_dividend[dividend_size-1]==0;dividend_size--);
            for(divisor_size=inner_divisor.length;divisor_size>0&&inner_divisor[divisor_size-1]==0;divisor_size--);
        }
        int quotient_size=dividend_size-divisor_size+2;
        quotient_size=quotient_size>0?quotient_size:1;
        byte quotient[]=new byte[quotient_size];
        for(int i=dividend_size-divisor_size;i>=0;i--)
        {
            int quotient_test=(inner_dividend[i+divisor_size]*10+inner_dividend[i+divisor_size-1])/inner_divisor[divisor_size-1];
            int remainder_test=(inner_dividend[i+divisor_size]*10+inner_dividend[i+divisor_size-1])%inner_divisor[divisor_size-1];
            if(divisor_size>1&&(quotient_test>=10||quotient_test*inner_divisor[divisor_size-2]>remainder_test*10+inner_dividend[i+divisor_size-2]))
            {
                quotient_test--;
                remainder_test+=inner_divisor[divisor_size-1];
                if(remainder_test<10&&(quotient_test>=10||quotient_test*inner_divisor[divisor_size-2]>remainder_test*10+inner_dividend[i+divisor_size-2]))
                {
                    quotient_test--;
                    remainder_test+=inner_divisor[divisor_size-1];
                }
            }
            for(int j=i;j<i+divisor_size;j++)
            {
                inner_dividend[j+1]+=(byte)Math.floorDiv(inner_dividend[j]-quotient_test*inner_divisor[j-i],10);
                inner_dividend[j]=(byte)Math.floorMod(inner_dividend[j]-quotient_test*inner_divisor[j-i],10);
            }
            if(inner_dividend[i+divisor_size]<0)
            {
                quotient_test--;
                for(int j=i;j<i+divisor_size;j++)
                {
                    inner_dividend[j]=(byte)((inner_dividend[j]+inner_divisor[j-i])%10);
                    if(inner_dividend[j]>9)
                    {
                        inner_dividend[j+1]+=(byte)(inner_dividend[j]/10);
                        inner_dividend[j]=(byte)(inner_dividend[j]%10);
                    }
                }
            }
            quotient[i]=(byte)quotient_test;
        }
        inner_dividend=divide(inner_dividend,delta)[0];
        for(dividend_size=inner_dividend.length;dividend_size>0&&inner_dividend[dividend_size-1]==0;dividend_size--);
        for(quotient_size=quotient.length;quotient_size>0&&quotient[quotient_size-1]==0;quotient_size--);
        if(dividend_size>0&&positive_dividend<0)
        {
            quotient_size=quotient_size==0?1:quotient_size;
            quotient[0]++;
            int i=0;
            for(;i<quotient_size&&quotient[i]>9;i++)
            {
                quotient[i+1]+=1;
                quotient[i]-=10;
            }
            i++;
            quotient_size=i>quotient_size?i:quotient_size;
            for(divisor_size=divisor.length;divisor_size>0&&divisor[divisor_size-1]==0;divisor_size--);
            divisor[divisor_size-1]*=positive_divisor;
            inner_dividend=subtract(divisor,inner_dividend);
            divisor[divisor_size-1]*=positive_divisor;
            for(dividend_size=inner_dividend.length;dividend_size>0&&inner_dividend[dividend_size-1]==0;dividend_size--);
        }
        if(quotient_size>0)
        {
            quotient[quotient_size-1]*=positive_dividend*positive_divisor;
        }
        byte remainder[]=new byte[dividend_size+1];
        System.arraycopy(inner_dividend,0,remainder,0,dividend_size);
        byte result[][]={quotient,remainder};
        return result;
    }
    /**
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
        int positive_dividend=dividend.size>0?(dividend.number[dividend.size-1]>=0?1:-1):0;
        int positive_divisor=divisor.size>0?(divisor.number[divisor.size-1]>=0?1:-1):0;
        if(positive_divisor==0)
        {
            return null;
        }
        else if(positive_dividend==0)
        {
            return new big_integer[]{new big_integer(new byte[]{0},0),new big_integer(new byte[]{0},0)};
        }
        big_integer inner_dividend=new big_integer(dividend.number);
        big_integer inner_divisor=new big_integer(divisor.number);
        inner_dividend.number[inner_dividend.size-1]*=positive_dividend;
        inner_divisor.number[inner_divisor.size-1]*=positive_divisor;
        int delta=10/(inner_divisor.number[inner_divisor.size-1]+1);
        if(delta>1)
        {
            inner_dividend=multiply(inner_dividend,delta);
            inner_divisor=multiply(inner_divisor,delta);
        }
        int quotient_size=inner_dividend.size-inner_divisor.size+2;
        quotient_size=quotient_size>0?quotient_size:1;
        byte quotient_number[]=new byte[quotient_size];
        for(int i=inner_dividend.size-inner_divisor.size;i>=0;i--)
        {
            int quotient_test=(inner_dividend.number[i+inner_divisor.size]*10+inner_dividend.number[i+inner_divisor.size-1])/inner_divisor.number[inner_divisor.size-1];
            int remainder_test=(inner_dividend.number[i+inner_divisor.size]*10+inner_dividend.number[i+inner_divisor.size-1])%inner_divisor.number[inner_divisor.size-1];
            if(inner_divisor.size>1&&(quotient_test>=10||quotient_test*inner_divisor.number[inner_divisor.size-2]>remainder_test*10+inner_dividend.number[i+inner_divisor.size-2]))
            {
                quotient_test--;
                remainder_test+=inner_divisor.number[inner_divisor.size-1];
                if(remainder_test<10&&(quotient_test>=10||quotient_test*inner_divisor.number[inner_divisor.size-2]>remainder_test*10+inner_dividend.number[i+inner_divisor.size-2]))
                {
                    quotient_test--;
                    remainder_test+=inner_divisor.number[inner_divisor.size-1];
                }
            }
            for(int j=i;j<i+inner_divisor.size;j++)
            {
                inner_dividend.number[j+1]+=(byte)Math.floorDiv(inner_dividend.number[j]-quotient_test*inner_divisor.number[j-i],10);
                inner_dividend.number[j]=(byte)Math.floorMod(inner_dividend.number[j]-quotient_test*inner_divisor.number[j-i],10);
            }
            if(inner_dividend.number[i+inner_divisor.size]<0)
            {
                quotient_test--;
                for(int j=i;j<i+inner_divisor.size;j++)
                {
                    inner_dividend.number[j]+=inner_divisor.number[j-i];
                    if(inner_dividend.number[j]>9)
                    {
                        inner_dividend.number[j+1]+=(byte)(inner_dividend.number[j]/10);
                        inner_dividend.number[j]=(byte)(inner_dividend.number[j]%10);
                    }
                }
            }
            quotient_number[i]=(byte)quotient_test;
        }
        inner_dividend=divide(inner_dividend,delta)[0];
        for(quotient_size=quotient_number.length;quotient_size>0&&quotient_number[quotient_size-1]==0;quotient_size--);
        if(inner_dividend.size>0&&positive_dividend<0)
        {
            quotient_size=quotient_size==0?1:quotient_size;
            quotient_number[0]++;
            int i=0;
            for(;i<quotient_size&&quotient_number[i]>9;i++)
            {
                quotient_number[i+1]+=1;
                quotient_number[i]-=10;
            }
            i++;
            quotient_size=i>quotient_size?i:quotient_size;
            divisor.number[divisor.size-1]*=positive_divisor;
            inner_dividend=subtract(divisor,inner_dividend);
            divisor.number[divisor.size-1]*=positive_divisor;
        }
        if(quotient_size>0)
        {
            quotient_number[quotient_size-1]*=positive_dividend*positive_divisor;
        }
        big_integer remainder=new big_integer(inner_dividend.number);
        return new big_integer[]{new big_integer(quotient_number,quotient_size),remainder};
    }
    /**
    计算两个字节数组低位优先表示的整数的最大公因数。
    @param number1 第一个整数的字节数组低位优先表示。
    @param number2 第二个整数的字节数组低位优先表示。
    @return 最大公因数的字节数组低位优先表示。<br>
    定义0与0的最大公因数为0。
    */
    public static byte[] gcd(byte number1[],byte number2[])
    {
        int size1=number1.length;
        int size2=number2.length;
        for(;size1>0&&number1[size1-1]==0;size1--);
        for(;size2>0&&number2[size2-1]==0;size2--);
        if(size1==0&&size2==0)
        {
            return new byte[]{0};
        }
        else if(size1==0||size2==0)
        {
            int result_size=size1>0?size1:size2;
            byte result[]=new byte[result_size];
            System.arraycopy(size1>0?number1:number2,0,result,0,result_size);
            return result;
        }
        int relation=compare(number1,number2);
        if(relation==0)
        {
            byte result[]=new byte[number1.length];
            System.arraycopy(number1,0,result,0,number1.length);
            return result;
        }
        else
        {
            if(relation<0)
            {
                byte temp[]=number1;
                number1=number2;
                number2=temp;
            }
            do
            {
                byte result[][]=divide(number1,number2);
                number1=number2;
                number2=result[1];
                for(size2=number2.length;size2>0&&number2[size2-1]==0;size2--);
            }
            while(size2>0);
            for(size1=number1.length;size1>0&&number1[size1-1]==0;size1--);
            number2=number1;
            number1=new byte[size1+1];
            System.arraycopy(number2,0,number1,0,size1);
            if(number1[size1-1]<0)
            {
                number1[size1-1]*=-1;
            }
            return number1;
        }
    }
    /**
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
            return new big_integer(new byte[]{0},0);
        }
        else if(number1.size==0||number2.size==0)
        {
            return number1.size>0?new big_integer(number1.number):new big_integer(number2.number);
        }
        int relation=number1.compareTo(number2);
        if(relation==0)
        {
            return new big_integer(number1.number);
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
            big_integer result=new big_integer(number1.number);
            if(result.number[result.size-1]<0)
            {
                result.number[result.size-1]*=-1;
            }
            return result;
        }
    }
    /**
    计算字节数组低位优先表示的整数的正整数次幂 <code>base</code>^<code>exponent</code>。<br>
    @param base 底数整数的字节数组低位优先表示。
    @param positive_exponent 指数正整数。
    @return 底数的指数次幂的字节数组低位优先表示。<br>
    若指数为负数或底数与指数同时为0，则返回<code>null</code>。
    */
    public static byte[] power(byte base[],int positive_exponent)
    {
        if(positive_exponent<0)
        {
            return null;
        }
        int size=base.length;
        for(;size>0&&base[size-1]==0;size--);
        if(size==0)
        {
            return positive_exponent>0?new byte[]{0}:null;
        }
        else if(positive_exponent==0)
        {
            return new byte[]{1};
        }
        else if(positive_exponent==1)
        {
            byte result[]=new byte[size];
            System.arraycopy(base,0,result,0,size);
            return result;
        }
        int positive_base=size>0?(base[size-1]>=0?1:-1):0;
        base[size-1]*=positive_base;
        if(positive_base!=0&&size==1&&base[0]==1)
        {
            byte result[]=new byte[]{positive_exponent%2==0?1:(byte)positive_base};
            base[size-1]*=positive_base;
            return result;
        }
        int positive_result=(positive_exponent%2==0||positive_base>=0)?1:-1;
        byte result[]=new byte[]{1};
        byte major[]=base;
        int result_size=1;
        for(;positive_exponent>0;positive_exponent>>=1)
        {
            if((positive_exponent&1)==1)
            {
                result=multiply(result,major);
                for(result_size=result.length;result_size>0&&result[result_size-1]==0;result_size--);
            }
            major=multiply(major,major);
        }
        result[result_size-1]*=positive_result;
        base[size-1]*=positive_base;
        return result;
    }
    /**
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
            return positive_exponent>0?new big_integer(new byte[]{0}):null;
        }
        else if(positive_exponent==0)
        {
            return new big_integer(new byte[]{1});
        }
        else if(positive_exponent==1)
        {
            return new big_integer(base.number);
        }
        int positive_base=base.size>0?(base.number[base.size-1]>=0?1:-1):0;
        base.number[base.size-1]*=positive_base;
        if(positive_base!=0&&base.size==1&&base.number[0]==1)
        {
            big_integer result=new big_integer(new byte[]{positive_exponent%2==0?1:(byte)positive_base});
            base.number[base.size-1]*=positive_base;
            return result;
        }
        int positive_result=(positive_exponent%2==0||positive_base>=0)?1:-1;
        big_integer result=new big_integer(new byte[]{1});
        big_integer major=new big_integer(base.number);
        for(;positive_exponent>0;positive_exponent>>=1)
        {
            if((positive_exponent&1)==1)
            {
                result=multiply(result,major);
            }
            major=multiply(major,major);
        }
        result.number[result.size-1]*=positive_result;
        base.number[base.size-1]*=positive_base;
        return result;
    }
    public String toString()
    {
        StringBuilder result=new StringBuilder();
        if(size==0)
        {
            result.append("0");
        }
        for(int i=size-1;i>=0;i--)
        {
            result.append(number[i]);
        }
        return result.toString();
    }
    /**
    比较当前整数对象与指定整数对象的数值。
    @param another 指定整数对象。
    @return 当前整数对象与指定整数对象的数值比较的结果。<br>
    <ul>
        <li>0：当前整数对象与指定整数对象的数值相同。</li>
        <li>&gt;0：当前整数对象的数值大于指定整数对象的数值。</li>
        <li>&lt;0：当前整数对象的数值小于指定整数对象的数值。</li>
    </ul>
    */
    public int compareTo(big_integer another)
    {
        int positive_this=size>0?(number[size-1]>=0?1:-1):0;
        int positive_another=another.size>0?(another.number[another.size-1]>=0?1:-1):0;
        if(positive_this!=positive_another)
        {
            return positive_this-positive_another;
        }
        else if(positive_this==0)
        {
            return 0;
        }
        else if(size!=another.size)
        {
            return (size-another.size)*positive_this;
        }
        else
        {
            for(int i=size-1;i>=0;i--)
            {
                if(number[i]!=another.number[i])
                {
                    if(i==size-1)
                    {
                        return number[i]-another.number[i];
                    }
                    return (number[i]-another.number[i])*positive_this;
                }
            }
            return 0;
        }
    }
}