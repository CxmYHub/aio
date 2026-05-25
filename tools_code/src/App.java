import tools.collection.*;
import tools.data_structure.*;
import tools.mathematics.*;
import tools.geography.*;
import tools.date.*;
import java.util.*;
import java.time.*;
public class App
{
    public static final Scanner s1=new Scanner(System.in);
    public static final Random RNG=new Random();
    public static void main(String args[])
    {
        histogram h=new histogram(5,1,2,3,4,5,6,7,8,9,10);
        System.out.println(Arrays.toString(h.bound));
        System.out.println(Arrays.toString(h.group_value));
        System.out.println(h);
        
    }
}