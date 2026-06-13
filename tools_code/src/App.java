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
        quick_response_code qr=new quick_response_code(
        """
        public static final int side_length[]={0,21,25,29,33,37,41,45,49,53,57,61,65,69,73,77,81,85,89,93,97,101,105,109,113,117,121,125,129,133,137,141,145,149,153,157,161,165,169,173,177};
        """,3);
        qr.display(10);
    }
}