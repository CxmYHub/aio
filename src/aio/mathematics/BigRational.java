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
public class BigRational {
    /**
    <p>分子</p><br>
    分子高精度整数对象。
    */
    public BigInteger numerator;
    /**
    <p>分母</p><br>
    分母高精度整数对象。
    */
    public BigInteger denominator;
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
    public BigInteger reduce() {
        BigInteger gcd=BigInteger.gcd(numerator,denominator);
        numerator=BigInteger.divide(numerator,gcd)[0];
        denominator=BigInteger.divide(denominator,gcd)[0];
        if(denominator.size==1&&denominator.number[0]==1) {
            numerator.sign*=denominator.sign;
            denominator=null;
        }
        return gcd;
    }
    /**
    <p>构造方法</p><br>
    通过有理数小数形式字符串构造高精度有理数对象。
    @param rationalString 字符串表示的小数形式有理数。<br>
    用括号表示循环节，例如<code>0.(3)</code>表示0.333333...。
    */
    public BigRational(String rationalString) {
        int length=rationalString.length();
        StringBuilder unloopBuilder=new StringBuilder();
        StringBuilder loopBuilder=new StringBuilder();
        boolean isNegative=false;
        int offset=0;
        if(rationalString.charAt(0)=='-') {
            isNegative=true;
            offset++;
        } else if(rationalString.charAt(0)=='+') {
            offset++;
        }
        int dotIndex=-1;
        int loopStart=-1;
        int loopEnd=-1;
        for(int i=offset;i<length;i++) {
            char now=rationalString.charAt(i);
            if(now=='.') {
                dotIndex=i-offset;
                offset++;
            } else if(now=='(') {
                loopStart=i-offset;
                loopBuilder.append(unloopBuilder);
                offset++;
            } else if(now==')') {
                loopEnd=i-offset;
                offset++;
            } else if(loopStart==-1) {
                unloopBuilder.append(now);
            } else {
                loopBuilder.append(now);
            }
        }
        length-=offset;
        if(dotIndex==-1) {
            numerator=new BigInteger(unloopBuilder.toString());
            numerator.sign=isNegative?-1:1;
            denominator=null;
        } else {
            StringBuilder denominatorBuilder=new StringBuilder();
            if(loopStart==-1) {
                numerator=new BigInteger(unloopBuilder.toString());
                numerator.sign=isNegative?-1:1;
                denominatorBuilder.append("1");
                for(int i=dotIndex;i<length;i++) {
                    denominatorBuilder.append("0");
                }
                denominator=new BigInteger(denominatorBuilder.toString());
            } else {
                BigInteger unloop=new BigInteger(unloopBuilder.toString());
                BigInteger loop=new BigInteger(loopBuilder.toString());
                numerator=BigInteger.subtract(loop,unloop);
                numerator.sign=isNegative?-1:1;
                for(int i=loopStart;i<loopEnd;i++) {
                    denominatorBuilder.append("9");
                }
                for(int i=dotIndex;i<loopStart;i++) {
                    denominatorBuilder.append("0");
                }
                denominator=new BigInteger(denominatorBuilder.toString());
            }
            reduce();
        }
    }
    /**
    <p>构造方法</p><br>
    通过有理数分数形式字符串和字符串输出模式构造高精度有理数对象。
    @param numeratorString 字符串表示的分子。
    @param denominatorString 字符串表示的分母。
    @param mode 字符串输出模式。<br>
    <ul>
        <li>&gt;0：小数格式，例如<code>"0.5"</code>。</li>
        <li>=0：分数&nbsp;&nbsp;小数格式，例如<code>"1/2&nbsp;&nbsp;0.5"</code>。</li>
        <li>&lt;0：分数格式，例如<code>"1/2"</code>。</li>
    </ul>
    */
    public BigRational(String numeratorString,String denominatorString,int mode) {
        this.mode=mode;
        numerator=new BigInteger(numeratorString);
        boolean hasDenominator=false;
        if(denominatorString!=null) {
            int denominatorLength=denominatorString.length();
            for(int i=0;i<denominatorLength;i++) {
                char now=denominatorString.charAt(i);
                if(now>='1'&&now<='9') {
                    if(i<denominatorLength-1||now>='2') {
                        hasDenominator=true;
                    }
                    break;
                }
            }
            if(hasDenominator) {
                denominator=new BigInteger(denominatorString);
                numerator.sign*=denominator.sign;
                denominator.sign=1;
                reduce();
            }
        }
    }
    /**
    <p>构造方法</p><br>
    通过有理数分数形式字符串构造高精度有理数对象。
    @param numeratorString 字符串表示的分子。
    @param denominatorString 字符串表示的分母。
    */
    public BigRational(String numeratorString,String denominatorString) {
        this(numeratorString,denominatorString,0);
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
    public BigRational(int numerator,int denominator,int mode) {
        this.mode=mode;
        if(numerator==0) {
            this.numerator=new BigInteger(0);
            this.denominator=null;
        } else {
            if(denominator<0) {
                numerator=-numerator;
                denominator=-denominator;
            }
            this.numerator=new BigInteger(numerator);
            if(denominator<=1) {
                this.denominator=null;
            } else {
                this.denominator=new BigInteger(denominator);
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
    public BigRational(int numerator,int denominator) {
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
    public BigRational(BigInteger numerator,BigInteger denominator,int mode) {
        this.mode=mode;
        this.numerator=new BigInteger(numerator.number,numerator.sign);
        if(denominator==null) {
        } else if(denominator.size==0||denominator.size==1&&denominator.number[0]==1) {
            this.numerator.sign*=denominator.sign;
            this.denominator=null;
        } else {
            this.numerator.sign*=denominator.sign;
            this.denominator=new BigInteger(denominator.number,1);
            reduce();
        }
    }
    /**
    <p>构造方法</p><br>
    通过高精度整数分子和分母构造高精度有理数对象。
    @param numerator 高精度整数表示的分子。
    @param denominator 高精度整数表示的分母。
    */
    public BigRational(BigInteger numerator,BigInteger denominator) {
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
    public BigRational(BigInteger numerator,BigInteger denominator,int sign,int mode) {
        this.mode=mode;
        numerator.sign=sign;
        this.numerator=numerator;
        if(denominator==null||denominator.size==0||denominator.size==1&&denominator.number[0]==1) {
            this.denominator=null;
        } else {
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
    public static BigInteger[] commonDenominator(BigRational rational1,BigRational rational2) {
        if(rational1.denominator==null&&rational2.denominator==null) {
            return new BigInteger[]{new BigInteger(1),new BigInteger(1)};
        } else if(rational1.denominator==null) {
            rational1.denominator=new BigInteger(rational2.denominator.number,1);
            rational1.numerator=BigInteger.multiply(rational1.numerator,rational1.denominator);
            return new BigInteger[]{new BigInteger(rational2.denominator.number,1),new BigInteger(1)};
        } else if(rational2.denominator==null) {
            rational2.denominator=new BigInteger(rational1.denominator.number,1);
            rational2.numerator=BigInteger.multiply(rational2.numerator,rational2.denominator);
            return new BigInteger[]{new BigInteger(1),new BigInteger(rational1.denominator.number,1)};
        } else if(rational1.denominator.compareTo(rational2.denominator)==0) {
            return new BigInteger[]{new BigInteger(1),new BigInteger(1)};
        } else {
            BigInteger commonDenominator=BigInteger.lcm(rational1.denominator,rational2.denominator);
            BigInteger multiplier1=BigInteger.divide(commonDenominator,rational1.denominator)[0];
            BigInteger multiplier2=BigInteger.divide(commonDenominator,rational2.denominator)[0];
            rational1.denominator=commonDenominator;
            rational2.denominator=new BigInteger(commonDenominator.number,1);
            rational1.numerator=BigInteger.multiply(rational1.numerator,multiplier1);
            rational2.numerator=BigInteger.multiply(rational2.numerator,multiplier2);
            return new BigInteger[]{multiplier1,multiplier2};
        }
    }
    /**
    <p>加法运算</p><br>
    计算两个有理数的和 <code>addend1</code>+<code>addend2</code>。
    @param addend1 第一个有理数对象。
    @param addend2 第二个有理数对象。
    @return 两个有理数的和。
    */
    public static BigRational add(BigRational addend1,BigRational addend2) {
        if(addend1.denominator==null&&addend2.denominator==null) {
            BigInteger sum=BigInteger.add(addend1.numerator,addend2.numerator);
            return new BigRational(sum,null,sum.sign,addend1.mode==addend2.mode?addend1.mode:0);
        } else if(addend1.denominator==null) {
            BigInteger sumNumerator=BigInteger.add(BigInteger.multiply(addend1.numerator,addend2.denominator),addend2.numerator);
            BigInteger sumDenominator=new BigInteger(addend2.denominator.number,1);
            return new BigRational(sumNumerator,sumDenominator,sumNumerator.sign,addend1.mode==addend2.mode?addend1.mode:0);
        } else if(addend2.denominator==null) {
            BigInteger sumNumerator=BigInteger.add(addend1.numerator,BigInteger.multiply(addend2.numerator,addend1.denominator));
            BigInteger sumDenominator=new BigInteger(addend1.denominator.number,1);
            return new BigRational(sumNumerator,sumDenominator,sumNumerator.sign,addend1.mode==addend2.mode?addend1.mode:0);
        } else {
            BigInteger commonDenominator=BigInteger.lcm(addend1.denominator,addend2.denominator);
            BigInteger multiplier1=BigInteger.divide(commonDenominator,addend1.denominator)[0];
            BigInteger multiplier2=BigInteger.divide(commonDenominator,addend2.denominator)[0];
            BigInteger sumNumerator=BigInteger.add(BigInteger.multiply(addend1.numerator,multiplier1),BigInteger.multiply(addend2.numerator,multiplier2));
            return new BigRational(sumNumerator,commonDenominator,sumNumerator.sign,addend1.mode==addend2.mode?addend1.mode:0);
        }
    }
    /**
    <p>减法运算</p><br>
    计算两个有理数的差 <code>minuend</code>-<code>subtrahend</code>。
    @param minuend 被减数有理数对象。
    @param subtrahend 减数有理数对象。
    @return 两个有理数的差。
    */
    public static BigRational subtract(BigRational minuend,BigRational subtrahend) {
        if(minuend.denominator==null&&subtrahend.denominator==null) {
            BigInteger difference=BigInteger.subtract(minuend.numerator,subtrahend.numerator);
            return new BigRational(difference,null,difference.sign,minuend.mode==subtrahend.mode?minuend.mode:0);
        } else if(minuend.denominator==null) {
            BigInteger differenceNumerator=BigInteger.subtract(BigInteger.multiply(minuend.numerator,subtrahend.denominator),subtrahend.numerator);
            BigInteger differenceDenominator=new BigInteger(subtrahend.denominator.number,1);
            return new BigRational(differenceNumerator,differenceDenominator,differenceNumerator.sign,minuend.mode==subtrahend.mode?minuend.mode:0);
        } else if(subtrahend.denominator==null) {
            BigInteger differenceNumerator=BigInteger.subtract(minuend.numerator,BigInteger.multiply(subtrahend.numerator,minuend.denominator));
            BigInteger differenceDenominator=new BigInteger(minuend.denominator.number,1);
            return new BigRational(differenceNumerator,differenceDenominator,differenceNumerator.sign,minuend.mode==subtrahend.mode?minuend.mode:0);
        } else {
            BigInteger commonDenominator=BigInteger.lcm(minuend.denominator,subtrahend.denominator);
            BigInteger multiplier1=BigInteger.divide(commonDenominator,minuend.denominator)[0];
            BigInteger multiplier2=BigInteger.divide(commonDenominator,subtrahend.denominator)[0];
            BigInteger differenceNumerator=BigInteger.subtract(BigInteger.multiply(minuend.numerator,multiplier1),BigInteger.multiply(subtrahend.numerator,multiplier2));
            return new BigRational(differenceNumerator,commonDenominator,differenceNumerator.sign,minuend.mode==subtrahend.mode?minuend.mode:0);
        }
    }
    /**
    <p>乘法运算</p><br>
    计算两个有理数的积 <code>factor1</code>*<code>factor2</code>。
    @param factor1 第一个有理数对象。
    @param factor2 第二个有理数对象。
    @return 两个有理数的积。
    */
    public static BigRational multiply(BigRational factor1,BigRational factor2) {
        if(factor1.denominator==null&&factor2.denominator==null) {
            BigInteger product=BigInteger.multiply(factor1.numerator,factor2.numerator);
            return new BigRational(product,null,product.sign,factor1.mode==factor2.mode?factor1.mode:0);
        } else if(factor1.denominator==null) {
            BigInteger productNumerator=BigInteger.multiply(factor1.numerator,factor2.numerator);
            BigInteger productDenominator=new BigInteger(factor2.denominator.number,1);
            return new BigRational(productNumerator,productDenominator,productNumerator.sign,factor1.mode==factor2.mode?factor1.mode:0);
        } else if(factor2.denominator==null) {
            BigInteger productNumerator=BigInteger.multiply(factor1.numerator,factor2.numerator);
            BigInteger productDenominator=new BigInteger(factor1.denominator.number,1);
            return new BigRational(productNumerator,productDenominator,productNumerator.sign,factor1.mode==factor2.mode?factor1.mode:0);
        } else {
            return new BigRational(BigInteger.multiply(factor1.numerator,factor2.numerator),BigInteger.multiply(factor1.denominator,factor2.denominator),factor1.numerator.sign*factor2.numerator.sign,factor1.mode==factor2.mode?factor1.mode:0);
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
    public static BigRational divide(BigRational dividend,BigRational divisor) {
        if(divisor.numerator.size==0) {
            return null;
        } else if(dividend.denominator==null&&divisor.denominator==null) {
            return new BigRational(dividend.numerator,divisor.numerator,0);
        } else if(dividend.denominator==null) {
            BigInteger quotientNumerator=BigInteger.multiply(dividend.numerator,divisor.denominator);
            BigInteger quotientDenominator=new BigInteger(divisor.numerator.number,1);
            return new BigRational(quotientNumerator,quotientDenominator,dividend.numerator.sign*divisor.numerator.sign,dividend.mode==divisor.mode?dividend.mode:0);
        } else if(divisor.denominator==null) {
            BigInteger quotientNumerator=new BigInteger(dividend.numerator.number,dividend.numerator.sign);
            BigInteger quotientDenominator=BigInteger.multiply(dividend.denominator,divisor.numerator);
            return new BigRational(quotientNumerator,quotientDenominator,dividend.numerator.sign*divisor.numerator.sign,dividend.mode==divisor.mode?dividend.mode:0);
        } else {
            return new BigRational(BigInteger.multiply(dividend.numerator,divisor.denominator),BigInteger.multiply(dividend.denominator,divisor.numerator),dividend.numerator.sign*divisor.numerator.sign,dividend.mode==divisor.mode?dividend.mode:0);
        }
    }
    /**
    <p>幂运算</p><br>
    计算有理数的整数次幂 <code>base</code>^<code>exponent</code>。<br>
    @param base 底数有理数对象。
    @param exponent 指数整数。
    @return 底数的指数次幂。
    */
    public static BigRational power(BigRational base,int exponent) {
        if(base.numerator.size==0) {
            return exponent>0?new BigRational(0,1,base.mode):null;
        } else if(exponent==0) {
            return new BigRational(1,1,base.mode);
        } else if(exponent==1) {
            return new BigRational(base.numerator,base.denominator,base.mode);
        } else if(exponent==-1) {
            return new BigRational(base.denominator!=null?base.denominator:new BigInteger(1),base.numerator,base.mode);
        }
        if(base.denominator==null&&base.numerator.size==1&&base.numerator.number[0]==1) {
            BigRational result=new BigRational(exponent%2==0?1:base.numerator.sign,1,base.mode);
            return result;
        }
        boolean isNegativeExponent=exponent<0;
        exponent=isNegativeExponent?-exponent:exponent;
        BigRational result=new BigRational(1,1,base.mode);
        BigRational major=new BigRational(base.numerator,base.denominator,base.mode);
        for(;exponent>0;exponent>>=1) {
            if((exponent&1)==1) {
                result=multiply(result,major);
            }
            major=multiply(major,major);
        }
        if(isNegativeExponent) {
            result=new BigRational(result.denominator!=null?result.denominator:new BigInteger(new int[]{1},1,1),result.numerator,result.numerator.sign,base.mode);
        }
        return result;
    }
    /**
    <p>字符串表示</p><br>
    @return 有理数的字符串表示。
    */
    public String toString() {
        if(denominator==null) {
            return numerator.toString();
        } else {
            StringBuilder result=new StringBuilder();
            if(mode<=0) {
                result.append(numerator.toString());
                result.append("/");
                result.append(denominator.toString());
            }
            if(mode==0) {
                result.append("  ");
            }
            if(mode>=0) {
                boolean isNegative=numerator.sign<0;
                BigInteger quotientAndRemainder[]=BigInteger.divide(isNegative?new BigInteger(numerator.number,numerator.size,1):numerator,denominator);
                if(isNegative) {
                    result.append("-");
                }
                result.append(quotientAndRemainder[0]);
                result.append(".");
                BigInteger remainder=quotientAndRemainder[1];
                HashMap<BigInteger,Integer> remainders=new HashMap<BigInteger,Integer>();
                StringBuilder loopBuilder=new StringBuilder();
                BigInteger ten=new BigInteger(new int[]{10},1,1);
                for(int loopSize=-1;remainder.size>0;loopSize--) {
                    if(remainders.containsKey(remainder)) {
                        loopSize=-remainders.getOrDefault(remainder,0)-1;
                    }
                    if(loopSize>=0) {
                        loopBuilder.insert(loopSize,'(');
                        loopBuilder.append(')');
                        break;
                    } else {
                        remainders.put(remainder,loopSize);
                        remainder=BigInteger.multiply(remainder,ten);
                        quotientAndRemainder=BigInteger.divide(remainder,denominator);
                        BigInteger quotient=quotientAndRemainder[0];
                        int digit=quotient.size>0?quotient.number[0]:0;
                        loopBuilder.append((char)('0'+digit));
                        remainder=quotientAndRemainder[1];
                    }
                }
                result.append(loopBuilder);
            }
            return result.toString();
        }
    }
}