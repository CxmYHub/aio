import java.util.*;
import aio.collection.*;
import aio.data_structure.*;
import aio.date_time.*;
import aio.encode_decode.*;
import aio.geography.*;
import aio.mathematics.*;
import java.time.*;
import java.io.*;
public class App
{
    public static Scanner s1=new Scanner(System.in);
    public static Random RNG=new Random();
    public static void main(String args[])
    {
        datetime time=new datetime(1,1,1,0,0,0,0,0);
        System.out.println(time);
        datetime time2=time.add_day(-1);
        System.out.println(time2);
    }
}