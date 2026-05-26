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
        double_linked_list dll=new double_linked_list(1,2,3,4,5);
        System.out.println(dll);
        System.out.println(Arrays.toString(dll.traversal_forward()));
        System.out.println(Arrays.toString(dll.traversal_backward()));
    }
}