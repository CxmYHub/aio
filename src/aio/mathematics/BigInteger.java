package aio.mathematics;
/**
<p>高精度整数类</p><br>
整数，即不含分数部分的数，包含正整数、负整数和零。<br>
本高精度整数以整型数组、符号和位数实现。<br>
数组以2^31进制表示整数的绝对值，低位优先存储，符号表示整数的正负。
*/
public class BigInteger implements Comparable<BigInteger> {
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
    @param numberString 字符串表示的整数。
    */
    public BigInteger(String numberString) {
        int length=numberString.length();
        int offset=0;
        boolean isNegative=false;
        if(numberString.charAt(0)=='-') {
            isNegative=true;
            offset++;
        } else if(numberString.charAt(0)=='+') {
            offset++;
        }
        for(;offset<length&&numberString.charAt(offset)=='0';offset++);
        if(offset==length) {
            number=new int[]{0};
            size=0;
            sign=0;
            return;
        } else {
            int absolute[]=new int[(length-offset)/8+2];
            int absoluteSize=0;
            for(int i=offset;i<length;i++) {
                long carry=numberString.charAt(i)-'0';
                for(int j=0;j<absoluteSize;j++) {
                    long value=(absolute[j]&2147483647L)*10+carry;
                    carry=value>>>31;
                    absolute[j]=(int)(value&2147483647L);
                }
                if(carry!=0) {
                    absolute[absoluteSize++]=(int)carry;
                }
            }
            for(;absoluteSize>0&&absolute[absoluteSize-1]==0;absoluteSize--);
            number=new int[absoluteSize];
            System.arraycopy(absolute,0,number,0,absoluteSize);
            size=absoluteSize;
            sign=isNegative?-1:1;
        }
    }
    /**
    <p>构造方法</p><br>
    通过整数构造高精度整数对象。
    @param number 整数。
    */
    public BigInteger(int number) {
        if(number==0) {
            this.number=new int[]{0};
            size=0;
            sign=0;
        } else if(number==Integer.MIN_VALUE) {
            this.number=new int[]{0,1};
            size=2;
            sign=-1;
        } else {
            sign=number>0?1:-1;
            int absoluteNumber=sign>0?number:-number;
            this.number=new int[]{absoluteNumber};
            size=1;
        }
    }
    /**
    <p>构造方法</p><br>
    通过低位优先表示的整型数组构造高精度整数对象。
    @param numberArray 整型数组。
    @param sign 整数的符号。
    */
    public BigInteger(int numberArray[],int sign) {
        int length=numberArray.length;
        for(;length>0&&numberArray[length-1]==0;length--);
        if(length==0) {
            number=new int[]{0};
            size=0;
            this.sign=0;
            return;
        } else {
            number=new int[length];
            System.arraycopy(numberArray,0,number,0,length);
            size=length;
            this.sign=sign==0?0:(sign>0?1:-1);
        }
    }
    /**
    <p>全参构造方法</p><br>
    通过低位优先表示的整型数组和位数构造高精度整数对象。<br>
    本构造方法会直接使用输入的数组、位数和符号，不进行拷贝和检查。
    @param numberArray 整型数组。
    @param size 整数的位数。
    @param sign 整数的符号。
    */
    public BigInteger(int numberArray[],int size,int sign) {
        number=numberArray;
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
    public static int compareAbsolute(int number1[],int number2[]) {
        int size1=number1.length;
        int size2=number2.length;
        for(;size1>0&&number1[size1-1]==0;size1--);
        for(;size2>0&&number2[size2-1]==0;size2--);
        if(size1!=size2) {
            return size1-size2;
        } else {
            for(int i=size1-1;i>=0;i--) {
                long digit1=number1[i]&2147483647L;
                long digit2=number2[i]&2147483647L;
                if(digit1!=digit2) {
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
    public boolean increment() {
        if(size==0) {
            number=new int[]{1};
            size=1;
            sign=1;
            return true;
        } else if(sign>0) {
            long carry=1;
            for(int i=0;carry!=0&&i<size;i++) {
                long sum=(number[i]&2147483647L)+carry;
                number[i]=(int)sum;
                carry=sum>>>31;
            }
            if(carry!=0) {
                number=new int[size+1];
                number[size]=1;
                size++;
                return true;
            } else {
                return false;
            }
        } else {
            long borrow=1;
            for(int i=0;borrow!=0&&i<size;i++) {
                long difference=(number[i]&2147483647L)-borrow;
                number[i]=(int)difference;
                borrow=difference<0?1:0;
            }
            for(;size>0&&number[size-1]==0;size--);
            if(size==0) {
                number=new int[]{0};
                sign=0;
                return true;
            } else {
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
    public boolean decrement() {
        if(size==0) {
            number=new int[]{1};
            size=1;
            sign=-1;
            return true;
        } else if(sign>0) {
            long borrow=1;
            for(int i=0;borrow!=0&&i<size;i++) {
                long difference=(number[i]&2147483647L)-borrow;
                number[i]=(int)difference;
                borrow=difference<0?1:0;
            }
            for(;size>0&&number[size-1]==0;size--);
            if(size==0) {
                number=new int[]{0};
                sign=0;
                return true;
            } else {
                return false;
            }
        } else {
            long carry=1;
            for(int i=0;carry!=0&&i<size;i++) {
                long sum=(number[i]&2147483647L)+carry;
                number[i]=(int)sum;
                carry=sum>>>31;
            }
            if(carry!=0) {
                number=new int[size+1];
                number[size]=1;
                size++;
                return true;
            } else {
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
    public static BigInteger add(BigInteger addend1,BigInteger addend2) {
        if(addend1.size==0||addend2.size==0) {
            return new BigInteger(addend1.size==0?addend2.number:addend1.number,addend1.size==0?addend2.sign:addend1.sign);
        }
        if(addend1.sign==addend2.sign) {
            int maxSize=addend1.size>addend2.size?addend1.size:addend2.size;
            int result[]=new int[maxSize+1];
            long carry=0;
            for(int i=0;i<maxSize;i++) {
                long digit1=(i<addend1.size)?(addend1.number[i]&2147483647L):0;
                long digit2=(i<addend2.size)?(addend2.number[i]&2147483647L):0;
                long sum=digit1+digit2+carry;
                result[i]=(int)(sum&2147483647L);
                carry=sum>>>31;
            }
            if(carry!=0) {
                result[maxSize]=(int)carry;
                return new BigInteger(result,maxSize+1,addend1.sign);
            } else {
                int trimmed[]=new int[maxSize];
                System.arraycopy(result,0,trimmed,0,maxSize);
                return new BigInteger(trimmed,maxSize,addend1.sign);
            }
        } else {
            int relation=compareAbsolute(addend1.number,addend2.number);
            if(relation==0) {
                return new BigInteger(new int[]{0},0,0);
            } else  {
                if(relation<0) {
                    BigInteger temp=addend1;
                    addend1=addend2;
                    addend2=temp;
                }
                int resultSign=addend1.sign;
                int result[]=new int[addend1.size];
                long borrow=0;
                for(int i=0;i<addend1.size;i++) {
                    long digit1=addend1.number[i]&2147483647L;
                    long digit2=(i<addend2.size)?(addend2.number[i]&2147483647L):0;
                    long difference=digit1-digit2-borrow;
                    result[i]=(int)(difference&2147483647L);
                    borrow=difference<0?1:0;
                }
                int resultSize=addend1.size;
                for(;resultSize>0&&result[resultSize-1]==0;resultSize--);
                if(resultSize<addend1.size) {
                    int trimmed[]=new int[resultSize];
                    System.arraycopy(result,0,trimmed,0,resultSize);
                    return new BigInteger(trimmed,resultSize,resultSign);
                } else {
                    return new BigInteger(result,resultSize,resultSign);
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
    public static BigInteger subtract(BigInteger minuend,BigInteger subtrahend) {
        if(minuend.size==0||subtrahend.size==0) {
            return new BigInteger(minuend.size==0?subtrahend.number:minuend.number,minuend.size==0?-subtrahend.sign:minuend.sign);
        }
        if(minuend.sign==subtrahend.sign) {
            int relation=compareAbsolute(minuend.number,subtrahend.number);
            if(relation==0) {
                return new BigInteger(new int[]{0},0,0);
            } else {
                if(relation<0) {
                    BigInteger temp=minuend;
                    minuend=subtrahend;
                    subtrahend=temp;
                }
                int result[]=new int[minuend.size];
                long borrow=0;
                for(int i=0;i<minuend.size;i++) {
                    long minuendDigit=minuend.number[i]&2147483647L;
                    long subtrahendDigit=(i<subtrahend.size)?(subtrahend.number[i]&2147483647L):0;
                    long difference=minuendDigit-subtrahendDigit-borrow;
                    result[i]=(int)(difference&2147483647L);
                    borrow=difference<0?1:0;
                }
                int resultSize=minuend.size;
                for(;resultSize>0&&result[resultSize-1]==0;resultSize--);
                if(resultSize<minuend.size) {
                    int trimmed[]=new int[resultSize];
                    System.arraycopy(result,0,trimmed,0,resultSize);
                    return new BigInteger(trimmed,resultSize,relation>0?minuend.sign:-minuend.sign);
                } else {
                    return new BigInteger(result,minuend.size,relation>0?minuend.sign:-minuend.sign);
                }
            }
        } else {
            int maxSize=minuend.size>subtrahend.size?minuend.size:subtrahend.size;
            int result[]=new int[maxSize+1];
            long carry=0;
            for(int i=0;i<maxSize;i++) {
                long minuendDigit=(i<minuend.size)?(minuend.number[i]&2147483647L):0;
                long subtrahendDigit=(i<subtrahend.size)?(subtrahend.number[i]&2147483647L):0;
                long sum=minuendDigit+subtrahendDigit+carry;
                result[i]=(int)(sum&2147483647L);
                carry=sum>>>31;
            }
            if(carry!=0) {
                result[maxSize]=(int)carry;
                return new BigInteger(result,maxSize+1,minuend.sign);
            } else {
                int trimmed[]=new int[maxSize];
                System.arraycopy(result,0,trimmed,0,maxSize);
                return new BigInteger(trimmed,maxSize,minuend.sign);
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
    public static BigInteger multiply(BigInteger factor,int multiplier) {
        if(multiplier==0||factor.size==0) {
            return new BigInteger(new int[]{0},0,0);
        }
        long multiplierAbsolute=multiplier>=0?multiplier:-(long)multiplier;
        int product[]=new int[factor.size+1];
        long carry=0;
        for(int i=0;i<factor.size;i++) {
            long productDigit=(factor.number[i]&2147483647L)*multiplierAbsolute+carry;
            product[i]=(int)(productDigit&2147483647L);
            carry=productDigit>>>31;
        }
        if(carry!=0) {
            product[factor.size]=(int)(carry&2147483647L);
            return new BigInteger(product,factor.size+1,factor.sign*(multiplier>=0?1:-1));
        } else {
            int trimmed[]=new int[factor.size];
            System.arraycopy(product,0,trimmed,0,factor.size);
            return new BigInteger(trimmed,factor.size,factor.sign*(multiplier>=0?1:-1));
        }
    }
    /**
    <p>乘法运算</p><br>
    计算两个高精度整数的积 <code>factor1</code>*<code>factor2</code>。
    @param factor1 第一个高精度整数因数对象。
    @param factor2 第二个高精度整数因数对象。
    @return 两个高精度整数的积。
    */
    public static BigInteger multiply(BigInteger factor1,BigInteger factor2) {
        if(factor1.size==0||factor2.size==0) {
            return new BigInteger(new int[]{0},0,0);
        }
        int product[]=new int[factor1.size+factor2.size];
        for(int i=0;i<factor1.size;i++) {
            long factor1Digit=(factor1.number[i]&2147483647L);
            long carry=0;
            for(int j=0;j<factor2.size;j++) {
                long productDigit=(factor1Digit*(factor2.number[j]&2147483647L)+(product[i+j]&2147483647L))+carry;
                product[i+j]=(int)(productDigit&2147483647L);
                carry=productDigit>>>31;
            }
            product[i+factor2.size]=(int)(carry&2147483647L);
        }
        int productSize=factor1.size+factor2.size;
        for(;productSize>0&&product[productSize-1]==0;productSize--);
        if(productSize<factor1.size+factor2.size) {
            int trimmed[]=new int[productSize];
            System.arraycopy(product,0,trimmed,0,productSize);
            return new BigInteger(trimmed,productSize,factor1.sign*factor2.sign);
        } else {
            return new BigInteger(product,productSize,factor1.sign*factor2.sign);
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
    public static BigInteger[] divide(BigInteger dividend,int divisor) {
        if(divisor==0) {
            return null;
        } else if(dividend.size==0) {
            return new BigInteger[]{new BigInteger(new int[]{0},0,0),new BigInteger(new int[]{0},0,0)};
        }
        long divisorAbsolute=divisor>=0?divisor:-(long)divisor;
        int divisorSign=divisor>=0?1:-1;
        int quotient[]=new int[dividend.size+1];
        if(divisorAbsolute==1) {
            System.arraycopy(dividend.number,0,quotient,0,dividend.size);
            return new BigInteger[]{new BigInteger(quotient,dividend.size,dividend.sign*divisorSign),new BigInteger(new int[]{0},0,0)};
        }
        long remainder=0;
        for(int i=dividend.size-1;i>=0;i--) {
            long value=((long)dividend.number[i]|(remainder<<31))/divisorAbsolute;
            quotient[i]=(int)(value&2147483647L);
            remainder=((long)dividend.number[i]|(remainder<<31))%divisorAbsolute;
        }
        BigInteger result=new BigInteger(quotient,dividend.sign*divisorSign);
        if(remainder!=0&&dividend.sign<0) {
            if(result.sign>0) {
                result.increment();
            } else {
                result.decrement();
            }
            remainder=divisorAbsolute-remainder;
        }
        return new BigInteger[]{result,new BigInteger((int)remainder)};
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
    public static BigInteger[] divide(BigInteger dividend,BigInteger divisor) {
        if(divisor.size==0) {
            return null;
        } else if(dividend.size==0) {
            return new BigInteger[]{new BigInteger(new int[]{0},0,0),new BigInteger(new int[]{0},0,0)};
        } else if(compareAbsolute(dividend.number,divisor.number)<0) {
            if(dividend.sign>0) {
                return new BigInteger[]{new BigInteger(new int[]{0},0,0),new BigInteger(dividend.number,1)};
            } else {
                return new BigInteger[]{new BigInteger(new int[]{1},1,-divisor.sign),add(new BigInteger(divisor.number,divisor.size,1),dividend)};
            }
        }
        int dividendSize=dividend.size;
        int divisorSize=divisor.size;
        int dividendAbsolute[]=new int[dividendSize+2];
        int divisorAbsolute[]=new int[divisorSize+1];
        System.arraycopy(dividend.number,0,dividendAbsolute,0,dividendSize);
        System.arraycopy(divisor.number,0,divisorAbsolute,0,divisorSize);
        int movement=-1;
        for(int number=divisorAbsolute[divisorSize-1];number>0;number<<=1,movement++);
        if(movement>0) {
            long moveBit=0;
            for(int i=0;i<divisorSize;i++) {
                moveBit|=(long)divisorAbsolute[i]<<movement;
                divisorAbsolute[i]=(int)(moveBit&2147483647L);
                moveBit>>>=31;
            }
            for(int i=0;i<=dividendSize;i++) {
                moveBit|=(long)dividendAbsolute[i]<<movement;
                dividendAbsolute[i]=(int)(moveBit&2147483647L);
                moveBit>>>=31;
            }
            if(dividendAbsolute[dividendSize]!=0) {
                dividendSize++;
            }
        }
        int quotientSize=dividendSize-divisorSize+1;
        int quotientAbsolute[]=new int[quotientSize];
        for(int i=dividendSize-divisorSize;i>=0;i--) {
            long dividendHigh1=dividendAbsolute[i+divisorSize]&2147483647L;
            long dividendHigh2=dividendAbsolute[i+divisorSize-1]&2147483647L;
            long divisorHigh1=divisorAbsolute[divisorSize-1]&2147483647L;
            long quotientValuation=(dividendHigh1<<31|dividendHigh2)/divisorHigh1;
            long remainderValuation=(dividendHigh1<<31|dividendHigh2)%divisorHigh1;
            if(quotientValuation==2147483648L) {
                quotientValuation=2147483647L;
            }
            long divisorHigh2=(divisorSize>1)?(divisorAbsolute[divisorSize-2]&2147483647L):0;
            for(;remainderValuation<2147483648L&&quotientValuation*divisorHigh2>((i+divisorSize>=2)?((remainderValuation<<31)|(dividendAbsolute[i+divisorSize-2]&2147483647L)):remainderValuation);quotientValuation--,remainderValuation+=divisorHigh1);
            long borrow=0;
            for(int j=0;j<=divisorSize;j++) {
                long subtrahend=quotientValuation*(divisorAbsolute[j]&2147483647L);
                long difference=(dividendAbsolute[i+j]&2147483647L)-subtrahend-borrow;
                borrow=0;
                if(difference<0) {
                    borrow+=Math.floorDiv(difference+1,-2147483648L)+1;
                    difference=Math.floorMod(difference,2147483648L);
                }
                dividendAbsolute[i+j]=(int)difference;
            }
            if(dividendAbsolute[i+divisorSize]<0) {
                quotientValuation--;
                long carry=0;
                for(int j=0;j<=divisorSize;j++) {
                    long sum=(dividendAbsolute[i+j]&2147483647L)+(divisorAbsolute[j]&2147483647L)+carry;
                    dividendAbsolute[i+j]=(int)sum;
                    carry=(sum>>>31);
                }
            }
            quotientAbsolute[i]=(int)quotientValuation;
        }
        if(movement>0) {
            long moveBit=0;
            for(;dividendSize>0&&dividendAbsolute[dividendSize-1]==0;dividendSize--);
            for(int i=dividendSize;i>=0;i--) {
                moveBit|=(long)dividendAbsolute[i]<<(31-movement);
                dividendAbsolute[i]=(int)((moveBit&4611686016279904256L)>>>31);
                moveBit<<=31;
            }
        }
        int remainderSize=divisorSize;
        for(;quotientSize>0&&quotientAbsolute[quotientSize-1]==0;quotientSize--);
        for(;remainderSize>0&&dividendAbsolute[remainderSize-1]==0;remainderSize--);
        int remainderAbsolute[]=new int[remainderSize];
        System.arraycopy(dividendAbsolute,0,remainderAbsolute,0,remainderSize);
        BigInteger quotient=new BigInteger(quotientAbsolute,quotientSize,dividend.sign*divisor.sign);
        BigInteger remainder=new BigInteger(remainderAbsolute,remainderSize,remainderSize>0?1:0);
        if(remainder.size>0&&dividend.sign<0) {
            if(quotient.sign>0) {
                quotient.increment();
            } else {
                quotient.decrement();
            }
            remainder.sign=1;
            remainder=subtract(new BigInteger(divisor.number,divisor.size,1),remainder);
        }
        return new BigInteger[]{quotient,remainder};
    }
    /**
    <p>最大公因数</p><br>
    计算两个高精度整数的最大公因数。
    @param number1 第一个高精度整数对象。
    @param number2 第二个高精度整数对象。
    @return 两个高精度整数的最大公因数。<br>
    定义0与0的最大公因数为0。
    */
    public static BigInteger gcd(BigInteger number1,BigInteger number2) {
        if(number1.size==0&&number2.size==0) {
            return new BigInteger(new int[]{0},0,0);
        } else if(number1.size==0||number2.size==0) {
            return number1.size>0?new BigInteger(number1.number,1):new BigInteger(number2.number,1);
        }
        int relation=number1.compareTo(number2);
        if(relation==0) {
            return new BigInteger(number1.number,1);
        } else {
            if(relation<0) {
                BigInteger temp=number1;
                number1=number2;
                number2=temp;
            }
            do {
                BigInteger result[]=divide(number1,number2);
                number1=number2;
                number2=result[1];
            } while(number2.size>0);
            return new BigInteger(number1.number,1);
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
    public static BigInteger lcm(BigInteger number1,BigInteger number2) {
        return divide(multiply(number1,number2),gcd(number1,number2))[0];
    }
    /**
    <p>幂运算</p><br>
    计算高精度整数的正整数次幂 <code>base</code>^<code>exponent</code>。<br>
    @param base 底数整数对象。
    @param positiveExponent 指数正整数。
    @return 底数的指数次幂。<br>
    若指数为负数或底数与指数同时为0，则返回<code>null</code>。
    */
    public static BigInteger power(BigInteger base,int positiveExponent) {
        if(positiveExponent<0) {
            return null;
        } else if(base.size==0) {
            return positiveExponent>0?new BigInteger(new int[]{0},0,0):null;
        } else if(positiveExponent==0) {
            return new BigInteger(new int[]{1},1,1);
        } else if(positiveExponent==1) {
            return new BigInteger(base.number,base.sign);
        }
        if(base.sign!=0&&base.size==1&&base.number[0]==1) {
            return new BigInteger(new int[]{positiveExponent%2==0?1:base.sign},1,base.sign);
        }
        BigInteger result=new BigInteger(new int[]{1},1,1);
        BigInteger major=new BigInteger(base.number,base.sign);
        for(;positiveExponent>0;positiveExponent>>=1) {
            if((positiveExponent&1)==1) {
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
    public static BigInteger factorial(int number) {
        if(number<0) {
            return null;
        } else {
            BigInteger result=new BigInteger(new int[]{1},1,1);
            BigInteger factor=new BigInteger(new int[]{2},1,1);
            for(int i=2;i<=number;i++,factor.increment()) {
                result=multiply(result,factor);
            }
            return result;
        }
    }
    /**
    <p>字符串表示</p><br>
    高精度整数的字符串表示。
    */
    public String toString() {
        if(size==0) {
            return "0";
        } else {
            StringBuilder result=new StringBuilder();
            int absolute[]=new int[size+1];
            System.arraycopy(number,0,absolute,0,size);
            int absoluteSize=size;
            while(absoluteSize>0) {
                long remainder=0;
                int newSize=0;
                boolean reduced=false;
                for(int i=absoluteSize-1;i>=0;i--) {
                    long now=(remainder<<31)|(absolute[i]&2147483647L);
                    long quotient=now/10;
                    remainder=now%10;
                    absolute[i]=(int)quotient;
                    if(!reduced&&quotient!=0) {
                        newSize=i+1;
                        reduced=true;
                    }
                }
                result.append((char)('0'+remainder));
                absoluteSize=newSize;
            }
            if(sign<0) {
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
    public boolean equals(Object another) {
        if(another==null||!(another instanceof BigInteger)) {
            return false;
        }
        BigInteger anotherBigInteger=(BigInteger)another;
        if(sign!=anotherBigInteger.sign||size!=anotherBigInteger.size) {
            return false;
        } else {
            for(int i=size-1;i>=0;i--) {
                long digitThis=number[i]&2147483647L;
                long digitAnother=anotherBigInteger.number[i]&2147483647L;
                if(digitThis!=digitAnother) {
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
    public int hashCode() {
        if(size==0) {
            return 0;
        } else {
            int hash=sign;
            for(int i=0;i<size;i++) {
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
    public int compareTo(BigInteger another) {
        if(sign!=another.sign) {
            return sign-another.sign;
        } else if(size==0) {
            return 0;
        } else if(size!=another.size) {
            return size-another.size;
        } else {
            for(int i=size-1;i>=0;i--) {
                long digitThis=number[i]&2147483647L;
                long digitAnother=another.number[i]&2147483647L;
                if(digitThis!=digitAnother) {
                    return (digitThis>digitAnother?1:-1)*sign;
                }
            }
            return 0;
        }
    }
}