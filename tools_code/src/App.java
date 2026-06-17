import tools.collection.*;
import tools.data_structure.*;
import tools.date_time.*;
import tools.mathematics.*;
import tools.geography.*;
import tools.two_dimensional_barcode.*;
import java.util.*;
import java.time.*;
import java.io.*;
public class App
{
    public static Scanner s1=new Scanner(System.in);
    public static Random RNG=new Random();
    public static void main(String args[])
    {
        String text="你好，世界！";
        boolean[][] field=quick_response_code.encode(text);
        quick_response_code.display(field);
    }
}