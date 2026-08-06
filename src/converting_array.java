import java.io.*;
import aio.encode_decode.*;
import aio.mathematics.*;
public class converting_array
{
    public static void output_data_code_word()
    {
        int numbers[]={41,25,17,10,34,20,14,8,27,16,11,7,17,10,7,4,77,47,32,20,63,38,26,16,48,29,20,12,34,20,14,8,127,77,53,32,101,61,42,26,77,47,32,20,58,35,24,15,187,114,78,48,149,90,62,38,111,67,46,28,82,50,34,21,255,154,106,65,202,122,84,52,144,87,60,37,106,64,44,27,322,195,134,82,255,154,106,65,178,108,74,45,139,84,58,36,370,224,154,95,293,178,122,75,207,125,86,53,154,93,64,39,461,279,192,118,365,221,152,93,259,157,108,66,202,122,84,52,552,335,230,141,432,262,180,111,312,189,130,80,235,143,98,60,652,395,271,167,513,311,213,131,364,221,151,93,288,174,119,74,772,468,321,198,604,366,251,155,427,259,177,109,331,200,137,85,883,535,367,226,691,419,287,177,489,296,203,125,374,227,155,96,1022,619,425,262,796,483,331,204,580,352,241,149,427,259,177,109,1101,667,458,282,871,528,362,223,621,376,258,159,468,283,194,120,1250,758,520,320,991,600,412,254,703,426,292,180,530,321,220,136,1408,854,586,361,1082,656,450,277,775,470,322,198,602,365,250,154,1548,938,644,397,1212,734,504,310,876,531,364,224,674,408,280,173,1725,1046,718,442,1346,816,560,345,948,574,394,243,746,452,310,191,1903,1153,792,488,1500,909,624,384,1063,644,442,272,813,493,338,208,2061,1249,858,528,1600,970,666,410,1159,702,482,297,919,557,382,235,2232,1352,929,572,1708,1035,711,438,1224,742,509,314,969,587,403,248,2409,1460,1003,618,1872,1134,779,480,1358,823,565,348,1056,640,439,270,2620,1588,1091,672,2059,1248,857,528,1468,890,611,376,1108,672,461,284,2812,1704,1171,721,2188,1326,911,561,1588,963,661,407,1228,744,511,315,3057,1853,1273,784,2395,1451,997,614,1718,1041,715,440,1286,779,535,330,3283,1990,1367,842,2544,1542,1059,652,1804,1094,751,462,1425,864,593,365,3517,2132,1465,902,2701,1637,1125,692,1933,1172,805,496,1501,910,625,385,3669,2223,1528,940,2857,1732,1190,732,2085,1263,868,534,1581,958,658,405,3909,2369,1628,1002,3035,1839,1264,778,2181,1322,908,559,1677,1016,698,430,4158,2520,1732,1066,3289,1994,1370,843,2358,1429,982,604,1782,1080,742,457,4417,2677,1840,1132,3486,2113,1452,894,2473,1499,1030,634,1897,1150,790,486,4686,2840,1952,1201,3693,2238,1538,947,2670,1618,1112,684,2022,1226,842,518,4965,3009,2068,1273,3909,2369,1628,1002,2805,1700,1168,719,2157,1307,898,553,5253,3183,2188,1347,4134,2506,1722,1060,2949,1787,1228,756,2301,1394,958,590,5529,3351,2303,1417,4343,2632,1809,1113,3081,1867,1283,790,2361,1431,983,605,5836,3537,2431,1496,4588,2780,1911,1176,3244,1966,1351,832,2524,1530,1051,647,6153,3729,2563,1577,4775,2894,1989,1224,3417,2071,1423,876,2625,1591,1093,673,6479,3927,2699,1661,5039,3054,2099,1292,3599,2181,1499,923,2735,1658,1139,701,6743,4087,2809,1729,5313,3220,2213,1362,3791,2298,1579,972,2927,1774,1219,750,7089,4296,2953,1817,5596,3391,2331,1435,3993,2420,1663,1024,3057,1852,1273,784};
        try
        (
            FileWriter writer=new FileWriter("output.txt");
        )
        {
            File output=new File("output.txt");
            if(!output.exists())
            {
                output.createNewFile();
            }
            writer.write("{{{0}},{{0},{");
            writer.flush();
            int version_count=0;
            int EC_count=0;
            int length=numbers.length;
            for(int i=0;i<length;i++)
            {
                int now=numbers[i];
                version_count++;
                EC_count++;
                if(EC_count==1)
                {
                    writer.write(""+now);
                }
                else
                {
                    writer.write(","+now);
                }
                writer.flush();
                if(i<length-1)
                {
                    if(version_count==16)
                    {
                        writer.write(","+(numbers[i-1]-2)+"}},{{0},{");
                        writer.flush();
                        version_count=0;
                        EC_count=0;
                    }
                    else if(EC_count==4)
                    {
                        writer.write(","+(numbers[i-1]-2)+"},{");
                        writer.flush();
                        EC_count=0;
                    }
                }
                else
                {
                    writer.write(","+(numbers[i-1]-2)+"}}}");
                }
            }
            writer.flush();
        }
        catch(IOException e)
        {
            e.printStackTrace();
        }
    }
    public static void output_alphanumeric_table()
    {
        try
        (
            FileWriter writer=new FileWriter("output.txt");
        )
        {
            File output=new File("output.txt");
            if(!output.exists())
            {
                output.createNewFile();
            }
            writer.write("{");
            writer.flush();
            int length=91;
            for(int i=0;i<length;i++)
            {
                char now=(char)i;
                int element=-1;
                if(now>='0'&&now<='9')
                {
                    element=now-'0';
                }
                else if(now>='A'&&now<='Z')
                {
                    element=now-'A'+10;
                }
                else if(now==' ')
                {
                    element=36;
                }
                else if(now>='$'&&now<='%')
                {
                    element=now-'$'+37;
                }
                else if(now>='*'&&now<='+')
                {
                    element=now-'*'+39;
                }
                else if(now>='-'&&now<='/')
                {
                    element=now-'-'+41;
                }
                else if(now==':')
                {
                    element=44;
                }
                if(i<length-1)
                {
                    writer.write(element+",");
                }
                else
                {
                    writer.write(element+"");
                }
                writer.flush();
            }
            writer.write("}");
            writer.flush();
        }
        catch(IOException e)
        {
            e.printStackTrace();
        }
    }
    public static void output_data_code_word_count()
    {
        int numbers[]={19,16,13,9,34,28,22,16,55,44,34,26,80,64,48,36,108,86,62,46,136,108,76,60,156,124,88,66,194,154,110,86,232,182,132,100,274,216,154,122,324,254,180,140,370,290,206,158,428,334,244,180,461,365,261,197,523,415,295,223,589,453,325,253,647,507,367,283,721,563,397,313,795,627,445,341,861,669,485,385,932,714,512,406,1006,782,568,442,1094,860,614,464,1174,914,664,514,1276,1000,718,538,1370,1062,754,596,1468,1128,808,628,1531,1193,871,661,1631,1267,911,701,1735,1373,985,745,1843,1455,1033,793,1955,1541,1115,845,2071,1631,1171,901,2191,1725,1231,961,2306,1812,1286,986,2434,1914,1354,1054,2566,1992,1426,1096,2702,2102,1502,1142,2812,2216,1582,1222,2956,2334,1666,1276};
        try
        (
            FileWriter writer=new FileWriter("output.txt");
        )
        {
            File output=new File("output.txt");
            if(!output.exists())
            {
                output.createNewFile();
            }
            writer.write("{{0},{0,");
            writer.flush();
            int length=numbers.length;
            for(int i=0;i<length;i++)
            {
                if(i<length-1)
                {
                    if(i%4==3)
                    {
                        writer.write(numbers[i]+"},{0,");
                    }
                    else
                    {
                        writer.write(numbers[i]+",");
                    }
                }
                else
                {
                    writer.write(numbers[i]+"}}");
                }
                writer.flush();
            }
        }
        catch(IOException e)
        {
            e.printStackTrace();
        }
    }
    public static void output_error_correction_code_word_count_per_block()
    {
        int numbers[]={7,10,13,17,10,16,22,28,15,26,18,22,20,18,26,16,26,24,18,22,18,16,24,28,20,18,18,26,24,22,22,26,30,22,20,24,18,26,24,28,20,30,28,24,24,22,26,28,26,22,24,22,30,24,20,24,22,24,30,24,24,28,24,30,28,28,28,28,30,26,28,28,28,26,26,26,28,26,30,28,28,26,28,30,28,28,30,24,30,28,30,30,30,28,30,30,26,28,30,30,28,28,28,30,30,28,30,30,30,28,30,30,30,28,30,30,30,28,30,30,30,28,30,30,30,28,30,30,30,28,30,30,30,28,30,30,30,28,30,30,30,28,30,30,30,28,30,30,30,28,30,30,30,28,30,30,30,28,30,30};
        try
        (
            FileWriter writer=new FileWriter("output.txt");
        )
        {
            File output=new File("output.txt");
            if(!output.exists())
            {
                output.createNewFile();
            }
            writer.write("{{0},{0,");
            writer.flush();
            int length=numbers.length;
            for(int i=0;i<length;i++)
            {
                if(i<length-1)
                {
                    if(i%4==3)
                    {
                        writer.write(numbers[i]+"},{0,");
                    }
                    else
                    {
                        writer.write(numbers[i]+",");
                    }
                }
                else
                {
                    writer.write(numbers[i-1]+"}}");
                }
                writer.flush();
            }
        }
        catch(IOException e)
        {
            e.printStackTrace();
        }
    }
    public static void output_block_count_per_group()
    {
        int numbers[]={1,1,1,1,1,1,1,1,1,1,2,2,1,2,2,4,1,2,2,2,2,4,4,4,2,4,2,4,2,2,4,4,2,3,4,4,2,4,6,6,4,1,4,3,2,6,4,7,4,8,8,12,3,4,11,11,5,5,5,11,5,7,15,3,1,10,1,2,5,9,17,2,3,3,17,9,3,3,15,15,4,17,17,19,2,17,7,34,4,4,11,16,6,6,11,30,8,8,7,22,10,19,28,33,8,22,8,12,3,3,4,11,7,21,1,19,5,19,15,23,13,2,42,23,17,10,10,19,17,14,29,11,13,14,44,59,12,12,39,22,6,6,46,2,17,29,49,24,4,13,48,42,20,40,43,10,19,18,34,20,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,2,2,0,0,0,0,0,0,4,1,0,2,2,2,0,2,4,4,2,1,2,2,0,4,4,8,2,2,6,4,0,1,4,4,1,5,5,5,1,5,7,7,1,3,2,13,5,1,15,17,1,4,1,19,4,11,4,16,5,13,5,10,4,0,6,6,7,0,16,0,5,14,14,14,4,14,16,2,4,13,22,13,2,4,6,4,4,3,26,28,10,23,31,31,7,7,37,26,10,10,25,25,3,29,1,28,0,23,35,35,1,21,19,46,6,23,7,1,7,26,14,41,14,34,10,64,4,14,10,46,18,32,14,32,4,7,22,67,6,31,34,61};
        try
        (
            FileWriter writer=new FileWriter("output.txt");
        )
        {
            File output=new File("output.txt");
            if(!output.exists())
            {
                output.createNewFile();
            }
            writer.write("{{{0}},{{0},");
            writer.flush();
            int length=numbers.length>>1;
            for(int i=0;i<length;i++)
            {
                if(i<length-1)
                {
                    if(i%4==3)
                    {
                        writer.write("{"+numbers[i]+","+numbers[i+length]+"}},{{0},");
                    }
                    else
                    {
                        writer.write("{"+numbers[i]+","+numbers[i+length]+"},");
                    }
                }
                else
                {
                    writer.write("{"+numbers[length-1]+","+numbers[(length<<1)-1]+"}}}");
                }
                writer.flush();
            }
        }
        catch(IOException e)
        {
            e.printStackTrace();
        }
    }
    public static void output_data_code_word_count_per_block()
    {
        int numbers[]={19,16,13,9,34,28,22,16,55,44,17,13,80,32,24,9,108,43,15,11,68,27,19,15,78,31,14,13,97,38,18,14,116,36,16,12,68,43,19,15,81,50,22,12,92,36,20,14,107,37,20,11,115,40,16,12,87,41,24,12,98,45,19,15,107,46,22,14,120,43,22,14,113,44,21,13,107,41,24,15,116,42,22,16,111,46,24,13,121,47,24,15,117,45,24,16,106,47,24,15,114,46,22,16,122,45,23,15,117,45,24,15,116,45,23,15,115,47,24,15,115,46,24,15,115,46,24,15,115,46,24,15,115,46,24,16,121,47,24,15,121,47,24,15,122,46,24,15,122,46,24,15,117,47,24,15,118,47,24,15,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,16,12,0,0,0,0,0,0,15,14,0,39,19,15,0,37,17,13,69,44,20,16,0,51,23,13,93,37,21,15,0,38,21,12,116,41,17,13,88,42,25,13,99,46,20,16,108,47,23,15,121,44,23,15,114,45,22,14,108,42,25,16,117,0,23,17,112,0,25,0,122,48,25,16,118,46,25,17,107,48,25,16,115,47,23,17,123,46,24,16,118,46,25,16,117,46,24,16,116,48,25,16,116,47,25,16,0,47,25,16,116,47,25,16,116,47,25,17,122,48,25,16,122,48,25,16,123,47,25,16,123,47,25,16,118,48,25,16,119,48,25,16};
        try
        (
            FileWriter writer=new FileWriter("output.txt");
        )
        {
            File output=new File("output.txt");
            if(!output.exists())
            {
                output.createNewFile();
            }
            writer.write("{{{0}},{{0},");
            writer.flush();
            int length=numbers.length>>1;
            for(int i=0;i<length;i++)
            {
                if(i<length-1)
                {
                    if(i%4==3)
                    {
                        writer.write("{"+numbers[i]+","+numbers[i+length]+"}},{{0},");
                    }
                    else
                    {
                        writer.write("{"+numbers[i]+","+numbers[i+length]+"},");
                    }
                }
                else
                {
                    writer.write("{"+numbers[length-1]+","+numbers[(length<<1)-1]+"}}}");
                }
                writer.flush();
            }
        }
        catch(IOException e)
        {
            e.printStackTrace();
        }
    }
    public static void output_finite_field_256()
    {
        try
        (
            FileWriter writer=new FileWriter("output.txt");
        )
        {
            File output=new File("output.txt");
            if(!output.exists())
            {
                output.createNewFile();
            }
            writer.write("{");
            writer.flush();
            int length=256;
            int now=1;
            for(int i=0;i<length;i++)
            {
                if(i<length-1)
                {
                    writer.write(now+",");
                }
                else
                {
                    writer.write(now+"");
                }
                writer.flush();
                now<<=1;
                if((now&0b100000000)!=0)
                {
                    now^=0x11d;
                }
            }
            writer.write("}");
            writer.flush();
        }
        catch(IOException e)
        {
            e.printStackTrace();
        }
    }
    public static void output_logarithm_finite_field_256()
    {
        int logarithm[]=new int[256];
        try
        (
            FileWriter writer=new FileWriter("output.txt");
        )
        {
            File output=new File("output.txt");
            if(!output.exists())
            {
                output.createNewFile();
            }
            int length=logarithm.length;
            int now=1;
            for(int i=0;i<length;i++)
            {
                logarithm[now]=i;
                now<<=1;
                if((now&0b100000000)!=0)
                {
                    now^=0x11d;
                }
            }
            writer.write("{");
            writer.flush();
            for(int i=0;i<length;i++)
            {
                if(i<length-1)
                {
                    writer.write(logarithm[i]+",");
                }
                else
                {
                    writer.write(logarithm[i]+"");
                }
                writer.flush();
            }
            writer.write("}");
            writer.flush();
        }
        catch(IOException e)
        {
            e.printStackTrace();
        }
    }
    public static void output_distinct_sort_2d_array(int numbers[][])
    {
        int length=0;
        for(int i=0;i<numbers.length;i++)
        {
            length+=numbers[i].length;
        }
        int result[]=new int[length];
        int pin=0;
        for(int i=0;i<numbers.length;i++)
        {
            System.arraycopy(numbers[i],0,result,pin,numbers[i].length);
            pin+=numbers[i].length;
        }
        pin-=maths.distinct_sort_local(result);
        try
        (
            FileWriter writer=new FileWriter("output.txt");
        )
        {
            File output=new File("output.txt");
            if(!output.exists())
            {
                output.createNewFile();
            }
            length=pin;
            writer.write("{");
            writer.flush();
            for(int i=0;i<length;i++)
            {
                if(i<length-1)
                {
                    writer.write(result[i]+",");
                }
                else
                {
                    writer.write(result[i]+"");
                }
                writer.flush();
            }
            writer.write("}");
            writer.flush();
        }
        catch(IOException e)
        {
            e.printStackTrace();
        }
    }
    public static void output_generator_polynomial_coefficient()
    {
        int error_correction_map[]={7,10,13,15,16,17,18,20,22,24,26,28,30};
        int pin_ecm=0;
        try
        (
            FileWriter writer=new FileWriter("output.txt");
        )
        {
            File output=new File("output.txt");
            if(!output.exists())
            {
                output.createNewFile();
            }
            writer.write("{");
            writer.flush();
            for(int i=0;i<=30;i++)
            {
                if(error_correction_map[pin_ecm]==i)
                {
                    int e=error_correction_map[pin_ecm];
                    int coefficient[]=new int[e+1];
                    coefficient[0]=1;
                    for(int j=0;j<e;j++)
                    {
                        int a1=barcode.exponential_finite_field_256[j];
                        for(int k=j;k>=0;k--)
                        {
                            int a00=coefficient[k];
                            coefficient[k+1]^=(a00==0||a1==0)?0:barcode.exponential_finite_field_256[(barcode.logarithm_finite_field_256[a00]+barcode.logarithm_finite_field_256[a1])%255];
                            // coefficient[k+1]+=(coefficient[k]+j+1)%256;
                        }
                    }
                    writer.write("{");
                    writer.flush();
                    for(int j=0;j<=e;j++)
                    {
                        if(j<e)
                        {
                            writer.write(coefficient[j]+",");
                        }
                        else
                        {
                            writer.write(coefficient[j]+"");
                        }
                        writer.flush();
                    }
                    pin_ecm++;
                    writer.write("}");
                    if(pin_ecm<error_correction_map.length)
                    {
                        writer.write(",");
                    }
                    writer.flush();
                }
                else
                {
                    writer.write("{0},");
                    writer.flush();
                }
            }
            writer.write("}");
            writer.flush();
        }
        catch(IOException e)
        {
            e.printStackTrace();
        }
    }
    public static void output_alignment_pattern_center_position()
    {
        int numbers[][]={{0},{0},{6,18},{6,22},{6,26},{6,30},{6,34},{6,22,38},{6,24,42},{6,26,46},{6,28,50},{6,30,54},{6,32,58},{6,34,62},{6,26,46,66},{6,26,48,70},{6,26,50,74},{6,30,54,78},{6,30,56,82},{6,30,58,86},{6,34,62,90},{6,28,50,72,94},{6,26,50,74,98},{6,30,54,78,102},{6,28,54,80,106},{6,32,58,84,110},{6,30,58,86,114},{6,34,62,90,118},{6,26,50,74,98,122},{6,30,54,78,102,126},{6,26,52,78,104,130},{6,30,56,82,108,134},{6,34,60,86,112,138},{6,30,58,86,114,142},{6,34,62,90,118,146},{6,30,54,78,102,126,150},{6,24,50,76,102,128,154},{6,28,54,80,106,132,158},{6,32,58,84,110,136,162},{6,26,54,82,110,138,166},{6,30,58,86,114,142,170}};
        int result[][][]=new int[numbers.length][][];
        try
        (
            FileWriter writer=new FileWriter("output.txt");
        )
        {
            File output=new File("output.txt");
            if(!output.exists())
            {
                output.createNewFile();
            }
            for(int i=2;i<numbers.length;i++)
            {
                int this_count=numbers[i].length;
                result[i]=new int[this_count*this_count-3][2];
                for(int j=0;j<this_count*this_count;j++)
                {
                    int this_version[][]=result[i];
                    int this_pin=0;
                    for(int k=0;k<this_count;k++)
                    {
                        for(int l=0;l<this_count;l++)
                        {
                            int y=numbers[i][k];
                            int x=numbers[i][l];
                            if(!(y==6&&x==6||y==6&&x>=barcode.side_length[i]-7||y>=barcode.side_length[i]-7&&x==6))
                            {
                                this_version[this_pin][0]=y;
                                this_version[this_pin][1]=x;
                                this_pin++;
                            }
                        }
                    }
                }
            }
            writer.write("{{{0}},{{0}},");
            writer.flush();
            int length=result.length;
            for(int i=2;i<length;i++)
            {
                int this_count=result[i].length;
                writer.write("{");
                writer.flush();
                for(int j=0;j<this_count;j++)
                {
                    writer.write("{"+result[i][j][0]+","+result[i][j][1]+"}");
                    if(j<this_count-1)
                    {
                        writer.write(",");
                    }
                    writer.flush();
                }
                writer.write("}");
                if(i<length-1)
                {
                    writer.write(",");
                }
                writer.flush();
            }
            writer.write("}");
            writer.flush();
        }
        catch(IOException e)
        {
            e.printStackTrace();
        }
    }
    public static void output_format_code()
    {
        try
        (
            FileWriter writer=new FileWriter("output.txt");
        )
        {
            File output=new File("output.txt");
            if(!output.exists())
            {
                output.createNewFile();
            }
            writer.write("{{0},");
            writer.flush();
            for(int error_correction_level=1;error_correction_level<=4;error_correction_level++)
            {
                writer.write("{");
                writer.flush();
                for(byte mask_mode=0;mask_mode<8;mask_mode++)
                {
                    int format_code=(barcode.error_correction_mask[error_correction_level]<<3|mask_mode)<<10;
                    int error_correction_bit=format_code;
                    int format_generator_polynomial_coefficient=0b10100110111;
                    for(;;)
                    {
                        int bit_delta=0;
                        for(int temp=error_correction_bit;temp>0;temp>>=1,bit_delta++);
                        if(bit_delta<=10)
                        {
                            break;
                        }
                        for(int temp=format_generator_polynomial_coefficient;temp>0;temp>>=1,bit_delta--);
                        error_correction_bit^=format_generator_polynomial_coefficient<<bit_delta;
                    }
                    format_code|=error_correction_bit;
                    format_code^=0b101010000010010;
                    writer.write(format_code+"");
                    if(mask_mode<7)
                    {
                        writer.write(",");
                    }
                    writer.flush();
                }
                writer.write("}");
                if(error_correction_level<4)
                {
                    writer.write(",");
                }
                writer.flush();
            }
            writer.write("}");
            writer.flush();
        }
        catch(IOException e)
        {
            e.printStackTrace();
        }
    }
    public static void output_version_code()
    {
        try
        (
            FileWriter writer=new FileWriter("output.txt");
        )
        {
            File output=new File("output.txt");
            if(!output.exists())
            {
                output.createNewFile();
            }
            writer.write("{0,0,0,0,0,0,0,");
            writer.flush();
            for(int version=7;version<=40;version++)
            {
                int version_code=version<<12;
                int error_correction_bit=version_code;
                int version_generator_polynomial_coefficient=0b1111100100101;
                for(;;)
                {
                    int bit_delta=0;
                    for(int temp=error_correction_bit;temp>0;temp>>=1,bit_delta++);
                    if(bit_delta<=12)
                    {
                        break;
                    }
                    for(int temp=version_generator_polynomial_coefficient;temp>0;temp>>=1,bit_delta--);
                    error_correction_bit^=version_generator_polynomial_coefficient<<bit_delta;
                }
                version_code|=error_correction_bit;
                writer.write(version_code+"");
                if(version<40)
                {
                    writer.write(",");
                }
            }
            writer.write("}");
            writer.flush();
        }
        catch(IOException e)
        {
            e.printStackTrace();
        }
    }
    public static void main(String args[])
    {
        output_generator_polynomial_coefficient();
    }
}