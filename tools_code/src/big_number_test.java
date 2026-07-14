import tools.mathematics.*;
import java.util.*;
public class big_number_test
{
    public static void main(String args[])
    {
        int time=1;
        for(int i=1;i<=time;i++)
        {
            byte n1[]={4,6,-3};
            byte n2[]={3,2,1};
            big_integer I1=new big_integer(n1);
            big_integer I2=new big_integer(n2);
            System.out.println(I1+"\n"+I2);
            long start=System.nanoTime();
            System.out.println(Arrays.toString(big_integer.subtract(n1,n1)));
            System.out.println(Arrays.toString(big_integer.subtract(n2,n2)));
            long end=System.nanoTime();
            long micro_second1=(end-start)/1000;
            System.out.println("计算完成");
            start=System.nanoTime();
            System.out.println((big_integer.subtract(I1,I1)));
            System.out.println((big_integer.subtract(I2,I2)));
            end=System.nanoTime();
            long micro_second2=(end-start)/1000;
            System.out.println(Arrays.toString(n1));
            System.out.println(Arrays.toString(n2));
            System.out.println(I1+"\n"+I2);
            System.out.println("第"+i+"个测试用例计算耗时\t"+micro_second1+"μs,\t输出耗时\t"+micro_second2+"μs");
        }
        // System.out.println(Arrays.toString(R1.fraction[0]));
        // System.out.println(Arrays.toString(R1.fraction[1]));
        // byte n1[]={4,0,5};
        // byte n2[]={2,0,-1};
        // int i1=-7;
        // System.out.println(big_rational.compare(n1,n2));
        // System.out.println(big_rational.compare(n2,n1));
        // System.out.println(Arrays.toString(big_rational.add(n1,n2)));
        // System.out.println(Arrays.toString(big_rational.add(n2,n1)));
        // System.out.println(Arrays.toString(big_rational.subtract(n1,n2)));
        // System.out.println(Arrays.toString(big_rational.subtract(n2,n1)));
        // System.out.println(Arrays.toString(big_rational.multiply(n1,i1)));
        // System.out.println(Arrays.toString(big_rational.multiply(n2,i1)));
        // System.out.println(Arrays.toString(big_rational.multiply(n1,n2)));
        // System.out.println(Arrays.toString(big_rational.multiply(n2,n1)));
        // System.out.println(Arrays.deepToString(big_rational.divide(n1,i1)));
        // System.out.println(Arrays.deepToString(big_rational.divide(n2,i1)));
        // System.out.println(Arrays.deepToString(big_rational.divide(n1,n2)));
        // System.out.println(Arrays.deepToString(big_rational.divide(n2,n1)));
        // System.out.println(Arrays.toString(big_rational.gcd(n1,n2)));
        // System.out.println(Arrays.toString(n1));
        // System.out.println(Arrays.toString(n2));
    }
}