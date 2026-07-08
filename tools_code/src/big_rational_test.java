import tools.mathematics.big_rational;
import java.util.*;
public class big_rational_test
{
    public static void main(String args[])
    {
        int denominator=97;
        for(int i=1;i<denominator;i++)
        {
            long start=System.nanoTime();
            big_rational R1=new big_rational(""+i,""+denominator);
            long end=System.nanoTime();
            System.out.println("第"+i+"个测试用例耗时\t"+(end-start)/1000+"μs");
            System.out.println(R1);
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