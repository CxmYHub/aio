package tools.mathematics;
/**
<p>复数类。</p><br>
复数是由实部和虚部组成的数。<br>
形式为a+bi，其中a为实部，b为虚部，i为虚数单位。
*/
public class complex_number
{
    public double real;
    public double imaginary;
    /**
    创建一个新的复数对象 0。
    */
    public complex_number()
    {
        this.real=0;
        this.imaginary=0;
    }
    /**
    创建一个新的复数对象 <code>real</code>+<code>imaginary</code>*i。
    @param real 实部。
    @param imaginary 虚部。
    */
    public complex_number(double real,double imaginary)
    {
        this.real=real;
        this.imaginary=imaginary;
    }
    /**
	<p>此方法会修改调用对象。</p><br>
    将当前复数对象与另一个复数对象相加。
    @param C 要相加的复数对象。
    @return 和是否为实数。
    */
    public boolean add(complex_number C)
    {
        this.real+=C.real;
        this.imaginary+=C.imaginary;
        return this.imaginary==0;
    }
    /**
    计算两个复数的和 <code>C1</code>+<code>C2</code>。
    @param C1 第一个复数对象。
    @param C2 第二个复数对象。
    @return 两个复数对象的和。
    */
    public static complex_number add(complex_number C1,complex_number C2)
    {
        return new complex_number(C1.real+C2.real,C1.imaginary+C2.imaginary);
    }
    /**
	<p>此方法会修改调用对象。</p><br>
    将当前复数对象与另一个复数对象相减。
    @param C 要相减的复数对象。
    @return 差是否为实数。
    */
    public boolean subtract(complex_number C)
    {
        this.real-=C.real;
        this.imaginary-=C.imaginary;
        return this.imaginary==0;
    }
    /**
    计算两个复数的差 <code>C1</code>-<code>C2</code>。
    @param C1 第一个复数对象。
    @param C2 第二个复数对象。
    @return 两个复数对象的差。
    */
    public static complex_number subtract(complex_number C1,complex_number C2)
    {
        return new complex_number(C1.real-C2.real,C1.imaginary-C2.imaginary);
    }
    /**
	<p>此方法会修改调用对象。</p><br>
    将当前复数对象与另一个复数对象相乘。
    @param C 要相乘的复数对象。
    @return 积是否为实数。
    */
    public boolean multiply(complex_number C)
    {
        double real=this.real*C.real-this.imaginary*C.imaginary;
        double imaginary=this.real*C.imaginary+this.imaginary*C.real;
        this.real=real;
        this.imaginary=imaginary;
        return this.imaginary==0;
    }
    /**
    计算两个复数的积 <code>C1</code>*<code>C2</code>。
    @param C1 第一个复数对象。
    @param C2 第二个复数对象。
    @return 两个复数对象的积。
    */
    public static complex_number multiply(complex_number C1,complex_number C2)
    {
        return new complex_number(C1.real*C2.real-C1.imaginary*C2.imaginary,C1.real*C2.imaginary+C1.imaginary*C2.real);
    }
    /**
	<p>此方法会修改调用对象。</p><br>
    将当前复数对象与另一个复数对象相除。
    @param C 要相除的复数对象。
    @return 商是否为实数。
    */
    public boolean divide(complex_number C)
    {
        double real=(this.real*C.real+this.imaginary*C.imaginary)/(C.real*C.real+C.imaginary*C.imaginary);
        double imaginary=(this.imaginary*C.real-this.real*C.imaginary)/(C.real*C.real+C.imaginary*C.imaginary);
        this.real=real;
        this.imaginary=imaginary;
        return this.imaginary==0;
    }
    /**
    计算两个复数的商 <code>C1</code>/<code>C2</code>。
    @param C1 第一个复数对象。
    @param C2 第二个复数对象。
    @return 两个复数对象的商。
    */
    public static complex_number divide(complex_number C1,complex_number C2)
    {
        return new complex_number((C1.real*C2.real+C1.imaginary*C2.imaginary)/(C2.real*C2.real+C2.imaginary*C2.imaginary),(C1.imaginary*C2.real-C1.real*C2.imaginary)/(C2.real*C2.real+C2.imaginary*C2.imaginary));
    }
    /**
    计算当前复数对象的模长。
    @return 当前复数对象的模长。
    */
    public double magnitude()
    {
        return Math.sqrt(this.real*this.real+this.imaginary*this.imaginary);
    }
    /**
    计算给定复数的模长。
    @param real 实部。
    @param imaginary 虚部。
    @return 复数的模长。
    */
    public static double magnitude(double real,double imaginary)
    {
        return Math.sqrt(real*real+imaginary*imaginary);
    }
    public String toString()
    {
        return this.real==0?""+(this.imaginary==0?"0.0":this.imaginary+"i"):this.real+""+(this.imaginary==0?"":(this.imaginary>0?"+":"")+this.imaginary+"i");
    }
}