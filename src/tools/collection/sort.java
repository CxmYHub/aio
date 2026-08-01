package tools.collection;
/**
<p>排序类</p><br>
用于对数组进行排序。
*/
public class sort
{
    /**
    <p>五点取中</p><br>
    计算数组中五个整数的中位数。
    @param five_numbers 五个整数。
    @return 数组中五个整数的中位数。
    */
    public static int median_5(int... five_numbers)
    {
        if(five_numbers.length!=5)
        {
            return Integer.MIN_VALUE;
        }
        int a=five_numbers[0];
        int b=five_numbers[1];
        int c=five_numbers[2];
        int d=five_numbers[3];
        int e=five_numbers[4];
        if(a>b)
        {
            int t=a;
            a=b;
            b=t;
        }
        if(c>d)
        {
            int t=c;
            c=d;
            d=t;
        }
        if(a>c)
        {
            int t=a;
            a=c;
            c=t;
            t=b;
            b=d;
            d=t;
        }
        if(b>e)
        {
            int t=b;
            b=e;
            e=t;
        }
        if(b>c)
        {
            int t=b;
            b=c;
            c=t;
        }
        return c<e?c:e;
    }
    /**
    <p>五点取中</p><br>
    计算数组中五个双精度浮点数的中位数。
    @param five_numbers 五个双精度浮点数。
    @return 数组中五个双精度浮点数的中位数。
    */
    public static double median_5(double... five_numbers)
    {
        if(five_numbers.length!=5)
        {
            return Double.MIN_VALUE;
        }
        double a=five_numbers[0];
        double b=five_numbers[1];
        double c=five_numbers[2];
        double d=five_numbers[3];
        double e=five_numbers[4];
        if(a>b)
        {
            double t=a;
            a=b;
            b=t;
        }
        if(c>d)
        {
            double t=c;
            c=d;
            d=t;
        }
        if(a>c)
        {
            double t=a;
            a=c;
            c=t;
            t=b;
            b=d;
            d=t;
        }
        if(b>e)
        {
            double t=b;
            b=e;
            e=t;
        }
        if(b>c)
        {
            double t=b;
            b=c;
            c=t;
        }
        return c<e?c:e;
    }
    /**
    <p>五点三分</p><br>
    计算数组中五个整数中第二大的数。
    @param five_numbers 五个整数。
    @return 数组中五个整数中第二大的数。
    */
    public static int max_second_5(int... five_numbers)
    {
        if(five_numbers.length!=5)
        {
            return Integer.MIN_VALUE;
        }
        int a=five_numbers[0];
        int b=five_numbers[1];
        int c=five_numbers[2];
        int d=five_numbers[3];
        int e=five_numbers[4];
        int win1,lose1;
        if(a>b)
        {
            win1=a;
            lose1=b;
        }
        else
        {
            win1=b;
            lose1=a;
        }
        int win2,lose2;
        if(c>d)
        {
            win2=c;
            lose2=d;
        }
        else
        {
            win2=d;
            lose2=c;
        }
        int max1;
        int candidate1,candidate2;
        if(win1>win2)
        {
            max1=win1;
            candidate1=win2;
            candidate2=lose1;
        }
        else
        {
            max1=win2;
            candidate1=win1;
            candidate2=lose2;
        }
        if(e>max1)
        {
            return max1;
        }
        else if(e>candidate1)
        {
            return e>candidate2?e:candidate2;
        }
        else
        {
            return candidate1>candidate2?candidate1:candidate2;
        }
    }
    /**
    <p>五点三分</p><br>
    计算数组中五个整数中第二小的数。
    @param five_numbers 五个整数。
    @return 数组中五个整数中第二小的数。
    */
    public static int min_second_5(int... five_numbers)
    {
        if(five_numbers.length!=5)
        {
            return Integer.MIN_VALUE;
        }
        int a=five_numbers[0];
        int b=five_numbers[1];
        int c=five_numbers[2];
        int d=five_numbers[3];
        int e=five_numbers[4];
        int win1,lose1;
        if(a<b)
        {
            win1=a;
            lose1=b;
        }
        else
        {
            win1=b;
            lose1=a;
        }
        int win2,lose2;
        if(c<d)
        {
            win2=c;
            lose2=d;
        }
        else
        {
            win2=d;
            lose2=c;
        }
        int min1;
        int candidate1,candidate2;
        if(win1<win2)
        {
            min1=win1;
            candidate1=win2;
            candidate2=lose1;
        }
        else
        {
            min1=win2;
            candidate1=win1;
            candidate2=lose2;
        }
        if(e<min1)
        {
            return min1;
        }
        else if(e<candidate1)
        {
            return e<candidate2?e:candidate2;
        }
        else
        {
            return candidate1<candidate2?candidate1:candidate2;
        }
    }
    /**
    <p>冒泡排序</p><br>
    <p>此方法会修改输入的数据。</p><br>
    @param numbers 待排整型数组。
    */
    public static void bubble(int numbers[])
    {
        int temp;
        int last_swap=numbers.length-1;
        for(int i=0;i<numbers.length-1;i++)
        {
            int new_last_swap=0;
            for(int j=0;j<last_swap;j++)
            {
                if(numbers[j]>numbers[j+1])
                {
                    temp=numbers[j];
                    numbers[j]=numbers[j+1];
                    numbers[j+1]=temp;
                    new_last_swap=j;
                }
            }
            if(new_last_swap==0)
            {
                break;
            }
            last_swap=new_last_swap;
        }
    }
    /**
    <p>冒泡排序</p><br>
    <p>此方法会修改输入的数据。</p><br>
    @param numbers 待排双精度浮点数数组。
    */
    public static void bubble(double numbers[])
    {
        double temp;
        int last_swap=numbers.length-1;
        for(int i=0;i<numbers.length-1;i++)
        {
            int new_last_swap=0;
            for(int j=0;j<last_swap;j++)
            {
                if(numbers[j]>numbers[j+1])
                {
                    temp=numbers[j];
                    numbers[j]=numbers[j+1];
                    numbers[j+1]=temp;
                    new_last_swap=j;
                }
            }
            if(new_last_swap==0)
            {
                break;
            }
            last_swap=new_last_swap;
        }
    }
    /**
    <p>选择排序</p><br>
    <p>此方法会修改输入的数据。</p><br>
    @param numbers 待排整型数组。
    */
    public static void selection(int numbers[])
    {
        int temp,min_index;
        for(int i=0;i<numbers.length-1;i++)
        {
            min_index=i;
            for(int j=i+1;j<numbers.length;j++)
            {
                if(numbers[j]<numbers[min_index])
                {
                    min_index=j;
                }
            }
            if(min_index!=i)
            {
                temp=numbers[i];
                numbers[i]=numbers[min_index];
                numbers[min_index]=temp;
            }
        }
    }
    /**
    <p>选择排序</p><br>
    <p>此方法会修改输入的数据。</p><br>
    @param numbers 待排双精度浮点数数组。
    */
    public static void selection(double numbers[])
    {
        int min_index;
        double temp;
        for(int i=0;i<numbers.length-1;i++)
        {
            min_index=i;
            for(int j=i+1;j<numbers.length;j++)
            {
                if(numbers[j]<numbers[min_index])
                {
                    min_index=j;
                }
            }
            if(min_index!=i)
            {
                temp=numbers[i];
                numbers[i]=numbers[min_index];
                numbers[min_index]=temp;
            }
        }
    }
    /**
    <p>插入排序</p><br>
    <p>此方法会修改输入的数据。</p><br>
    @param numbers 待排整型数组。
    */
    public static void insertion(int numbers[])
    {
        int temp;
        for(int i=1;i<numbers.length;i++)
        {
            temp=numbers[i];
            int j=i;
            for(;j>0&&temp<numbers[j-1];j--)
            {
                numbers[j]=numbers[j-1];
            }
            numbers[j]=temp;
        }
    }
    /**
    <p>插入排序</p><br>
    <p>此方法会修改输入的数据。</p><br>
    @param numbers 待排双精度浮点数数组。
    */
    public static void insertion(double numbers[])
    {
        double temp;
        for(int i=1;i<numbers.length;i++)
        {
            temp=numbers[i];
            int j=i;
            for(;j>0&&temp<numbers[j-1];j--)
            {
                numbers[j]=numbers[j-1];
            }
            numbers[j]=temp;
        }
    }
    /**
    <p>希尔排序</p><br>
    <p>此方法会修改输入的数据。</p><br>
    @param numbers 待排整型数组。
    */
    public static void shell(int numbers[])
    {
        for(int delta=numbers.length/2;delta>0;delta/=2)
        {
            int temp;
            int limit=numbers.length-delta;
            for(int i=delta;i<=limit;i++)
            {
                temp=numbers[i];
                int j=i;
                for(;j>=delta&&temp<numbers[j-delta];j-=delta)
                {
                    numbers[j]=numbers[j-delta];
                }
                numbers[j]=temp;
            }
        }
    }
    /**
    <p>希尔排序</p><br>
    <p>此方法会修改输入的数据。</p><br>
    @param numbers 待排双精度浮点数数组。
    */
    public static void shell(double numbers[])
    {
        for(int delta=numbers.length/2;delta>0;delta/=2)
        {
            double temp;
            int limit=numbers.length-delta;
            for(int i=delta;i<=limit;i++)
            {
                temp=numbers[i];
                int j=i;
                for(;j>=delta&&temp<numbers[j-delta];j-=delta)
                {
                    numbers[j]=numbers[j-delta];
                }
                numbers[j]=temp;
            }
        }
    }
    /**
    <p>快速排序</p><br>
    <p>此方法会修改输入的数据。</p><br>
    @param numbers 待排整型数组。
    */
    public static void quick(int numbers[])
    {
        int indexs[]=new int[numbers.length*2+2];
        indexs[0]=0;
        indexs[1]=numbers.length-1;
        int pin=2;
        while(pin>1)
        {
            int index_right=indexs[--pin];
            int index_left=indexs[--pin];
            if(index_left<index_right)
            {
                int length=index_right-index_left+1;
                int pivot=numbers[index_right];
                if(length>=5)
                {
                    int fifth[]={index_left,index_left+(length>>2),index_left+(length>>1),index_right-(length>>2),index_right};
                    int a=numbers[fifth[0]],b=numbers[fifth[1]],c=numbers[fifth[2]],d=numbers[fifth[3]],e=numbers[fifth[4]];
                    if(a>b)
                    {
                        int t=a;
                        a=b;
                        b=t;
                    }
                    if(c>d)
                    {
                        int t=c;
                        c=d;
                        d=t;
                    }
                    if(a>c)
                    {
                        int t=a;
                        a=c;
                        c=t;
                        t=b;
                        b=d;
                        d=t;
                    }
                    if(b>e)
                    {
                        int t=b;
                        b=e;
                        e=t;
                    }
                    if(b>c)
                    {
                        int t=b;
                        b=c;
                        c=t;
                    }
                    pivot=c<e?c:e;
                    for(int median_index=0;median_index<5;median_index++)
                    {
                        if(pivot==numbers[fifth[median_index]])
                        {
                            numbers[fifth[median_index]]=numbers[index_right];
                            numbers[index_right]=pivot;
                            break;
                        }
                    }
                }
                int left=index_left-1,right=index_right;
                int temp;
                while(left<right)
                {
                    for(left++;numbers[left]<pivot;left++);
                    for(right--;left<right&&numbers[right]>pivot;right--);
                    if(left<right&&numbers[left]!=numbers[right])
                    {
                        temp=numbers[left];
                        numbers[left]=numbers[right];
                        numbers[right]=temp;
                    }
                }
                numbers[index_right]=numbers[left];
                numbers[left]=pivot;
                indexs[pin++]=left+1;
                indexs[pin++]=index_right;
                indexs[pin++]=index_left;
                indexs[pin++]=left-1;
            }
        }
    }
    /**
    <p>快速排序</p><br>
    <p>此方法会修改输入的数据。</p><br>
    @param numbers 待排双精度浮点数数组。
    */
    public static void quick(double numbers[])
    {
        int indexs[]=new int[numbers.length*2+2];
        indexs[0]=0;
        indexs[1]=numbers.length-1;
        int pin=2;
        while(pin>1)
        {
            int index_right=indexs[--pin];
            int index_left=indexs[--pin];
            if(index_left<index_right)
            {
                int length=index_right-index_left+1;
                double pivot=numbers[index_right];
                if(length>=5)
                {
                    int fifth[]={index_left,index_left+(length>>2),index_left+(length>>1),index_right-(length>>2),index_right};
                    double a=numbers[fifth[0]],b=numbers[fifth[1]],c=numbers[fifth[2]],d=numbers[fifth[3]],e=numbers[fifth[4]];
                    if(a>b)
                    {
                        double t=a;
                        a=b;
                        b=t;
                    }
                    if(c>d)
                    {
                        double t=c;
                        c=d;
                        d=t;
                    }
                    if(a>c)
                    {
                        double t=a;
                        a=c;
                        c=t;
                        t=b;
                        b=d;
                        d=t;
                    }
                    if(b>e)
                    {
                        double t=b;
                        b=e;
                        e=t;
                    }
                    if(b>c)
                    {
                        double t=b;
                        b=c;
                        c=t;
                    }
                    pivot=c<e?c:e;
                    for(int median_index=0;median_index<5;median_index++)
                    {
                        if(pivot==numbers[fifth[median_index]])
                        {
                            numbers[fifth[median_index]]=numbers[index_right];
                            numbers[index_right]=pivot;
                            break;
                        }
                    }
                }
                int left=index_left-1,right=index_right;
                double temp;
                while(left<right)
                {
                    for(left++;left<right&&numbers[left]<pivot;left++);
                    for(right--;left<right&&numbers[right]>pivot;right--);
                    if(left<right&&numbers[left]!=numbers[right])
                    {
                        temp=numbers[left];
                        numbers[left]=numbers[right];
                        numbers[right]=temp;
                    }
                }
                numbers[index_right]=numbers[left];
                numbers[left]=pivot;
                indexs[pin++]=left+1;
                indexs[pin++]=index_right;
                indexs[pin++]=index_left;
                indexs[pin++]=left-1;
            }
        }
    }
    /**
    <p>双轴快速排序</p><br>
    <p>此方法会修改输入的数据。</p><br>
    @param numbers 待排整型数组。
    */
    public static void quick_dual_pivot(int numbers[])
    {
        int indexs[]=new int[numbers.length*2+2];
        indexs[0]=0;
        indexs[1]=numbers.length-1;
        int pin=2;
        while(pin>1)
        {
            int index_right=indexs[--pin];
            int index_left=indexs[--pin];
            if(index_left<index_right)
            {
                int length=index_right-index_left+1;
                int temp;
                if(length<5&&numbers[index_left]>numbers[index_right])
                {
                    temp=numbers[index_left];
                    numbers[index_left]=numbers[index_right];
                    numbers[index_right]=temp;
                }
                int pivot1=numbers[index_left];
                int pivot2=numbers[index_right];
                if(length>=5)
                {
                    int fifth[]={index_left,index_left+(length>>2),index_left+(length>>1),index_right-(length>>2),index_right};
                    int a=numbers[fifth[0]],b=numbers[fifth[1]],c=numbers[fifth[2]],d=numbers[fifth[3]],e=numbers[fifth[4]];
                    int less_win1,less_lose1,less_win2,less_lose2,less_candidate1,less_candidate2,min1,great_candidate1,great_candidate2,great_candidate3,max1;
                    if(a<b)
                    {
                        less_win1=a;
                        less_lose1=b;
                    }
                    else
                    {
                        less_win1=b;
                        less_lose1=a;
                    }
                    if(c<d)
                    {
                        less_win2=c;
                        less_lose2=d;
                    }
                    else
                    {
                        less_win2=d;
                        less_lose2=c;
                    }
                    if(less_win1<less_win2)
                    {
                        min1=less_win1;
                        less_candidate1=less_win2;
                        less_candidate2=less_lose1;
                        great_candidate1=less_lose2;
                    }
                    else
                    {
                        min1=less_win2;
                        less_candidate1=less_win1;
                        less_candidate2=less_lose2;
                        great_candidate1=less_lose1;
                    }
                    if(e<min1)
                    {
                        pivot1=min1;
                        min1=e;
                        great_candidate2=less_candidate1;
                        great_candidate3=less_candidate2;
                    }
                    else if(e<less_candidate1)
                    {
                        pivot1=e<less_candidate2?e:less_candidate2;
                        great_candidate2=less_candidate1;
                        great_candidate3=e>less_candidate2?e:less_candidate2;
                    }
                    else
                    {
                        pivot1=less_candidate1<less_candidate2?less_candidate1:less_candidate2;
                        great_candidate2=e;
                        great_candidate3=less_candidate1>less_candidate2?less_candidate1:less_candidate2;
                    }
                    if(great_candidate1>great_candidate2)
                    {
                        if(great_candidate2>great_candidate3)
                        {
                            max1=great_candidate1;
                            pivot2=great_candidate2;
                        }
                        else if(great_candidate3>great_candidate1)
                        {
                            max1=great_candidate3;
                            pivot2=great_candidate1;
                        }
                        else
                        {
                            max1=great_candidate1;
                            pivot2=great_candidate3;
                        }
                    }
                    else
                    {
                        if(great_candidate2<great_candidate3)
                        {
                            max1=great_candidate3;
                            pivot2=great_candidate2;
                        }
                        else if(great_candidate3<great_candidate1)
                        {
                            max1=great_candidate2;
                            pivot2=great_candidate1;
                        }
                        else
                        {
                            max1=great_candidate2;
                            pivot2=great_candidate3;
                        }
                    }
                    if(pivot1==pivot2)
                    {
                        pivot1=min1;
                        pivot2=max1;
                    }
                    for(int pivot_index=0;pivot_index<5;pivot_index++)
                    {
                        if(pivot1==numbers[fifth[pivot_index]])
                        {
                            numbers[fifth[pivot_index]]=numbers[index_left];
                            numbers[index_left]=pivot1;
                            break;
                        }
                    }
                    for(int pivot_index=4;pivot_index>=0;pivot_index--)
                    {
                        if(pivot2==numbers[fifth[pivot_index]])
                        {
                            numbers[fifth[pivot_index]]=numbers[index_right];
                            numbers[index_right]=pivot2;
                            break;
                        }
                    }
                }
                int left=index_left;
                int right=index_right;
                int k=index_left+1;
                boolean back=false;
                while(k<right)
                {
                    if(numbers[k]<pivot1)
                    {
                        temp=numbers[++left];
                        numbers[left]=numbers[k];
                        numbers[k++]=temp;
                    }
                    else if(numbers[k]<=pivot2)
                    {
                        k++;
                    }
                    else
                    {
                        back=false;
                        while(numbers[--right]>pivot2)
                        {
                            if(k>=right)
                            {
                                back=true;
                                break;
                            }
                        }
                        if(!back)
                        {
                            if(numbers[right]<pivot1)
                            {
                                temp=numbers[right];
                                numbers[right]=numbers[k];
                                numbers[k]=numbers[++left];
                                numbers[left]=temp;
                            }
                            else
                            {
                                temp=numbers[right];
                                numbers[right]=numbers[k];
                                numbers[k]=temp;
                            }
                            k++;
                        }
                    }
                }
                temp=numbers[index_left];
                numbers[index_left]=numbers[left];
                numbers[left]=temp;
                temp=numbers[index_right];
                numbers[index_right]=numbers[right];
                numbers[right]=temp;
                indexs[pin++]=right+1;
                indexs[pin++]=index_right;
                if(pivot1!=pivot2)
                {
                    indexs[pin++]=left+1;
                    indexs[pin++]=right-1;
                }
                indexs[pin++]=index_left;
                indexs[pin++]=left-1;
            }
        }
    }
    /**
    <p>双轴快速排序</p><br>
    <p>此方法会修改输入的数据。</p><br>
    @param numbers 待排双精度浮点数数组。
    */
    public static void quick_dual_pivot(double numbers[])
    {
        int indexs[]=new int[numbers.length*2+2];
        indexs[0]=0;
        indexs[1]=numbers.length-1;
        int pin=2;
        while(pin>1)
        {
            int index_right=indexs[--pin];
            int index_left=indexs[--pin];
            if(index_left<index_right)
            {
                int length=index_right-index_left+1;
                double temp;
                if(length<5&&numbers[index_left]>numbers[index_right])
                {
                    temp=numbers[index_left];
                    numbers[index_left]=numbers[index_right];
                    numbers[index_right]=temp;
                }
                double pivot1=numbers[index_left];
                double pivot2=numbers[index_right];
                if(length>=5)
                {
                    int fifth[]={index_left,index_left+(length>>2),index_left+(length>>1),index_right-(length>>2),index_right};
                    double a=numbers[fifth[0]],b=numbers[fifth[1]],c=numbers[fifth[2]],d=numbers[fifth[3]],e=numbers[fifth[4]];
                    double less_win1,less_lose1,less_win2,less_lose2,less_candidate1,less_candidate2,min1,great_candidate1,great_candidate2,great_candidate3,max1;
                    if(a<b)
                    {
                        less_win1=a;
                        less_lose1=b;
                    }
                    else
                    {
                        less_win1=b;
                        less_lose1=a;
                    }
                    if(c<d)
                    {
                        less_win2=c;
                        less_lose2=d;
                    }
                    else
                    {
                        less_win2=d;
                        less_lose2=c;
                    }
                    if(less_win1<less_win2)
                    {
                        min1=less_win1;
                        less_candidate1=less_win2;
                        less_candidate2=less_lose1;
                        great_candidate1=less_lose2;
                    }
                    else
                    {
                        min1=less_win2;
                        less_candidate1=less_win1;
                        less_candidate2=less_lose2;
                        great_candidate1=less_lose1;
                    }
                    if(e<min1)
                    {
                        pivot1=min1;
                        min1=e;
                        great_candidate2=less_candidate1;
                        great_candidate3=less_candidate2;
                    }
                    else if(e<less_candidate1)
                    {
                        pivot1=e<less_candidate2?e:less_candidate2;
                        great_candidate2=less_candidate1;
                        great_candidate3=e>less_candidate2?e:less_candidate2;
                    }
                    else
                    {
                        pivot1=less_candidate1<less_candidate2?less_candidate1:less_candidate2;
                        great_candidate2=e;
                        great_candidate3=less_candidate1>less_candidate2?less_candidate1:less_candidate2;
                    }
                    if(great_candidate1>great_candidate2)
                    {
                        if(great_candidate2>great_candidate3)
                        {
                            max1=great_candidate1;
                            pivot2=great_candidate2;
                        }
                        else if(great_candidate3>great_candidate1)
                        {
                            max1=great_candidate3;
                            pivot2=great_candidate1;
                        }
                        else
                        {
                            max1=great_candidate1;
                            pivot2=great_candidate3;
                        }
                    }
                    else
                    {
                        if(great_candidate2<great_candidate3)
                        {
                            max1=great_candidate3;
                            pivot2=great_candidate2;
                        }
                        else if(great_candidate3<great_candidate1)
                        {
                            max1=great_candidate2;
                            pivot2=great_candidate1;
                        }
                        else
                        {
                            max1=great_candidate2;
                            pivot2=great_candidate3;
                        }
                    }
                    if(pivot1==pivot2)
                    {
                        pivot1=min1;
                        pivot2=max1;
                    }
                    for(int pivot_index=0;pivot_index<5;pivot_index++)
                    {
                        if(pivot1==numbers[fifth[pivot_index]])
                        {
                            numbers[fifth[pivot_index]]=numbers[index_left];
                            numbers[index_left]=pivot1;
                            break;
                        }
                    }
                    for(int pivot_index=4;pivot_index>=0;pivot_index--)
                    {
                        if(pivot2==numbers[fifth[pivot_index]])
                        {
                            numbers[fifth[pivot_index]]=numbers[index_right];
                            numbers[index_right]=pivot2;
                            break;
                        }
                    }
                }
                int left=index_left;
                int right=index_right;
                int k=index_left+1;
                boolean back=false;
                while(k<right)
                {
                    if(numbers[k]<pivot1)
                    {
                        temp=numbers[++left];
                        numbers[left]=numbers[k];
                        numbers[k++]=temp;
                    }
                    else if(numbers[k]<=pivot2)
                    {
                        k++;
                    }
                    else
                    {
                        back=false;
                        while(numbers[--right]>pivot2)
                        {
                            if(k>=right)
                            {
                                back=true;
                                break;
                            }
                        }
                        if(!back)
                        {
                            if(numbers[right]<pivot1)
                            {
                                temp=numbers[right];
                                numbers[right]=numbers[k];
                                numbers[k]=numbers[++left];
                                numbers[left]=temp;
                            }
                            else
                            {
                                temp=numbers[right];
                                numbers[right]=numbers[k];
                                numbers[k]=temp;
                            }
                            k++;
                        }
                    }
                }
                temp=numbers[index_left];
                numbers[index_left]=numbers[left];
                numbers[left]=temp;
                temp=numbers[index_right];
                numbers[index_right]=numbers[right];
                numbers[right]=temp;
                indexs[pin++]=right+1;
                indexs[pin++]=index_right;
                if(pivot1!=pivot2)
                {
                    indexs[pin++]=left+1;
                    indexs[pin++]=right-1;
                }
                indexs[pin++]=index_left;
                indexs[pin++]=left-1;
            }
        }
    }
    /**
    <p>归并排序</p><br>
    <p>此方法会修改输入的数据。</p><br>
    @param numbers 待排整型数组。
    */
    public static void merge(int numbers[])
    {
        int address[]=numbers;
        int n=numbers.length;
        int result[]=new int[n];
        for(int length=1;length<n;length<<=1)
        {
            for(int index_left=0;index_left<n;index_left+=length<<1)
            {
                int index_right=index_left+(length<<1)-1<n-1?index_left+(length<<1)-1:n-1;
                int index_middle=index_left+length-1;
                if(index_middle<index_right)
                {
                    int left=index_left,right=index_middle+1;
                    int pin=index_left;
                    while(left<=index_middle&&right<=index_right)
                    {
                        if(numbers[left]<=numbers[right])
                        {
                            result[pin++]=numbers[left++];
                        }
                        else
                        {
                            result[pin++]=numbers[right++];
                        }
                    }
                    while(left<=index_middle)
                    {
                        result[pin++]=numbers[left++];
                    }
                    while(right<=index_right)
                    {
                        result[pin++]=numbers[right++];
                    }
                }
                else
                {
                    for(int i=index_left;i<=index_right;i++)
                    {
                        result[i]=numbers[i];
                    }
                }
            }
            int temp[]=numbers;
            numbers=result;
            result=temp;
        }
        if(address!=numbers)
        {
            for(int i=0;i<n;i++)
            {
                address[i]=numbers[i];
            }
        }
    }
    /**
    <p>归并排序</p><br>
    <p>此方法会修改输入的数据。</p><br>
    @param numbers 待排双精度浮点数数组。
    */
    public static void merge(double numbers[])
    {
        double address[]=numbers;
        int n=numbers.length;
        double result[]=new double[n];
        for(int length=1;length<n;length<<=1)
        {
            for(int index_left=0;index_left<n;index_left+=length<<1)
            {
                int index_right=index_left+(length<<1)-1<n-1?index_left+(length<<1)-1:n-1;
                int index_middle=index_left+length-1;
                if(index_middle<index_right)
                {
                    int left=index_left,right=index_middle+1;
                    int pin=index_left;
                    while(left<=index_middle&&right<=index_right)
                    {
                        if(numbers[left]<=numbers[right])
                        {
                            result[pin++]=numbers[left++];
                        }
                        else
                        {
                            result[pin++]=numbers[right++];
                        }
                    }
                    while(left<=index_middle)
                    {
                        result[pin++]=numbers[left++];
                    }
                    while(right<=index_right)
                    {
                        result[pin++]=numbers[right++];
                    }
                }
                else
                {
                    for(int i=index_left;i<=index_right;i++)
                    {
                        result[i]=numbers[i];
                    }
                }
            }
            double temp[]=numbers;
            numbers=result;
            result=temp;
        }
        if(address!=numbers)
        {
            for(int i=0;i<n;i++)
            {
                address[i]=numbers[i];
            }
        }
    }
    /**
    <p>计数排序</p><br>
    <p>此方法会修改输入的数据。</p><br>
    @param numbers 待排整型数组。
    */
    public static void counting(int numbers[])
    {
        int min=numbers[0];
        int max=numbers[0];
        for(int number:numbers)
        {
            min=number<min?number:min;
            max=number>max?number:max;
        }
        int range=max-min+1;
        int count[]=new int[range];
        for(int number:numbers)
        {
            count[number-min]++;
        }
        int pin=0;
        for(int i=0;i<range;i++)
        {
            for(;count[i]!=0;count[i]--)
            {
                numbers[pin++]=min+i;
            }
        }
    }
    /**
    <p>基数排序</p><br>
    <p>此方法会修改输入的数据。</p><br>
    @param numbers 待排整型数组。
    */
    public static void radix(int numbers[])
    {
        long long_numbers[]=new long[numbers.length];
        long max=numbers[0];
        long min=max;
        for(int i=0;i<long_numbers.length;i++)
        {
            long now=numbers[i];
            long_numbers[i]=now;
            max=now>max?now:max;
            min=now<min?now:min;
        }
        if(min<0)
        {
            for(int i=0;i<long_numbers.length;i++)
            {
                long_numbers[i]-=min;
            }
            max-=min;
        }
        else
        {
            min=0;
        }
        int capacity[]={10,10,10,10,10,10,10,10,10,10};
        long bucket[][]=new long[10][10];
        int pin[]=new int[10];
        for(long i=1;i<max;i*=10)
        {
            for(int j=0;j<10;j++)
            {
                pin[j]=0;
            }
            for(int j=0;j<long_numbers.length;j++)
            {
                long now=long_numbers[j];
                int now_bit=(int)(now/i%10);
                if(pin[now_bit]>=capacity[now_bit])
                {
                    capacity[now_bit]=(capacity[now_bit]<<1)+2;
                    long new_bucket[]=new long[capacity[now_bit]];
                    System.arraycopy(bucket[now_bit],0,new_bucket,0,pin[now_bit]);
                    bucket[now_bit]=new_bucket;
                }
                bucket[now_bit][pin[now_bit]++]=now;
            }
            int index=0;
            for(int j=0;j<10;j++)
            {
                int now_pin=pin[j];
                long now_bucket[]=bucket[j];
                for(int k=0;k<now_pin;k++)
                {
                    long_numbers[index++]=now_bucket[k];
                }
            }
        }
        for(int i=0;i<numbers.length;i++)
        {
            numbers[i]=(int)(long_numbers[i]+min);
        }
    }
}