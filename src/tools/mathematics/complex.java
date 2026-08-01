package tools.mathematics;
/**
<p>复数类</p><br>
复数是由实部和虚部组成的数。<br>
形式为a+bi，其中a为实部，b为虚部，i为虚数单位。<br>
当a=0时，为纯虚数对象；当b=0时，为实数对象。
*/
public class complex
{
    /**
    <p>实部</p>
    */
    public double real;
    /**
    <p>虚部</p>
    */
    public double imaginary;
    /**
    <p>全参构造方法</p><br>
    构造一个复数对象 <code>real</code>+<code>imaginary</code>*i。
    @param real 实部。
    @param imaginary 虚部。
    */
    public complex(double real,double imaginary)
    {
        this.real=real;
        this.imaginary=imaginary;
    }
    /**
    <p>无参构造方法</p><br>
    构造一个复数对象0。
    */
    public complex()
    {
        this.real=0;
        this.imaginary=0;
    }
    /**
    <p>加法运算</p><br>
    <p>此方法会修改调用对象。</p><br>
    将当前复数与另一个复数相加。
    @param C 要相加的复数对象。
    @return 和是否为实数。
    */
    public boolean add(complex C)
    {
        real+=C.real;
        imaginary+=C.imaginary;
        return imaginary==0;
    }
    /**
    <p>加法运算</p><br>
    计算两个复数的和 <code>addend1</code>+<code>addend2</code>。
    @param addend1 第一个复数加数对象。
    @param addend2 第二个复数加数对象。
    @return 两个复数的和。
    */
    public static complex add(complex addend1,complex addend2)
    {
        return new complex(addend1.real+addend2.real,addend1.imaginary+addend2.imaginary);
    }
    /**
    <p>减法运算</p><br>
    <p>此方法会修改调用对象。</p><br>
    将当前复数与另一个复数相减。
    @param C 要相减的复数对象。
    @return 差是否为实数。
    */
    public boolean subtract(complex C)
    {
        real-=C.real;
        imaginary-=C.imaginary;
        return imaginary==0;
    }
    /**
    <p>减法运算</p><br>
    计算两个复数的差 <code>minuend</code>-<code>subtrahend</code>。
    @param minuend 复数被减数对象。
    @param subtrahend 复数减数对象。
    @return 两个复数的差。
    */
    public static complex subtract(complex minuend,complex subtrahend)
    {
        return new complex(minuend.real-subtrahend.real,minuend.imaginary-subtrahend.imaginary);
    }
    /**
    <p>乘法运算</p><br>
    <p>此方法会修改调用对象。</p><br>
    将当前复数与另一个复数相乘。
    @param C 要相乘的复数对象。
    @return 积是否为实数。
    */
    public boolean multiply(complex C)
    {
        double real=this.real*C.real-this.imaginary*C.imaginary;
        double imaginary=this.real*C.imaginary+this.imaginary*C.real;
        this.real=real;
        this.imaginary=imaginary;
        return this.imaginary==0;
    }
    /**
    <p>乘法运算</p><br>
    计算两个复数的积 <code>factor1</code>*<code>factor2</code>。
    @param factor1 第一个复数因数对象。
    @param factor2 第二个复数因数对象。
    @return 两个复数的积。
    */
    public static complex multiply(complex factor1,complex factor2)
    {
        return new complex(factor1.real*factor2.real-factor1.imaginary*factor2.imaginary,factor1.real*factor2.imaginary+factor1.imaginary*factor2.real);
    }
    /**
    <p>除法运算</p><br>
    <p>此方法会修改调用对象。</p><br>
    将当前复数与另一个复数相除。
    @param C 要相除的复数对象。
    @return 商是否为实数。
    */
    public boolean divide(complex C)
    {
        double real=(this.real*C.real+this.imaginary*C.imaginary)/(C.real*C.real+C.imaginary*C.imaginary);
        double imaginary=(this.imaginary*C.real-this.real*C.imaginary)/(C.real*C.real+C.imaginary*C.imaginary);
        this.real=real;
        this.imaginary=imaginary;
        return this.imaginary==0;
    }
    /**
    <p>除法运算</p><br>
    计算两个复数的商 <code>dividend</code>/<code>divisor</code>。
    @param dividend 复数被除数对象。
    @param divisor 复数除数对象。
    @return 两个复数的商。
    */
    public static complex divide(complex dividend,complex divisor)
    {
        return new complex((dividend.real*divisor.real+dividend.imaginary*divisor.imaginary)/(divisor.real*divisor.real+divisor.imaginary*divisor.imaginary),(dividend.imaginary*divisor.real-dividend.real*divisor.imaginary)/(divisor.real*divisor.real+divisor.imaginary*divisor.imaginary));
    }
    /**
    <p>模长</p><br>
    计算当前复数的模长。
    @return 当前复数对象的模长。
    */
    public double magnitude()
    {
        return Math.sqrt(real*real+imaginary*imaginary);
    }
    /**
    <p>模长</p><br>
    计算给定复数的模长。
    @param real 实部。
    @param imaginary 虚部。
    @return 复数的模长。
    */
    public static double magnitude(double real,double imaginary)
    {
        return Math.sqrt(real*real+imaginary*imaginary);
    }
    /**
    <p>字符串表示</p><br>
    @return 复数的字符串表示。
    */
    public String toString()
    {
        return real==0?""+(imaginary==0?"0.0":imaginary+"i"):real+""+(imaginary==0?"":(imaginary>0?"+":"")+imaginary+"i");
    }
}