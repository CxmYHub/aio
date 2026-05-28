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
        linked_list_singly ll=new linked_list_singly(3,1,2);
        System.out.println(ll);
        ll.insert(5,4);
        System.out.println(ll);
        ll.remove_tail(2);
        System.out.println(ll);
        ll.sort_ascend();
        System.out.println(ll);
        ll.sort_descend();
        System.out.println(ll);
    }
}