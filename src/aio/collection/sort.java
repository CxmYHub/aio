package aio.collection;
/**
<p>排序类</p><br>
用于对数组进行排序。
*/
public class Sort {
    /**
    <p>五点取中</p><br>
    计算数组中五个整数的中位数。
    @param fiveNumbers 五个整数。
    @return 数组中五个整数的中位数。
    */
    public static int median5(int... fiveNumbers) {
        if(fiveNumbers.length!=5) {
            return Integer.MIN_VALUE;
        }
        int a=fiveNumbers[0];
        int b=fiveNumbers[1];
        int c=fiveNumbers[2];
        int d=fiveNumbers[3];
        int e=fiveNumbers[4];
        if(a>b) {
            int t=a;
            a=b;
            b=t;
        }
        if(c>d) {
            int t=c;
            c=d;
            d=t;
        }
        if(a>c) {
            int t=a;
            a=c;
            c=t;
            t=b;
            b=d;
            d=t;
        }
        if(b>e) {
            int t=b;
            b=e;
            e=t;
        }
        if(b>c) {
            int t=b;
            b=c;
            c=t;
        }
        return c<e?c:e;
    }
    /**
    <p>五点取中</p><br>
    计算数组中五个双精度浮点数的中位数。
    @param fiveNumbers 五个双精度浮点数。
    @return 数组中五个双精度浮点数的中位数。
    */
    public static double median5(double... fiveNumbers) {
        if(fiveNumbers.length!=5) {
            return Double.MIN_VALUE;
        }
        double a=fiveNumbers[0];
        double b=fiveNumbers[1];
        double c=fiveNumbers[2];
        double d=fiveNumbers[3];
        double e=fiveNumbers[4];
        if(a>b) {
            double t=a;
            a=b;
            b=t;
        }
        if(c>d) {
            double t=c;
            c=d;
            d=t;
        }
        if(a>c) {
            double t=a;
            a=c;
            c=t;
            t=b;
            b=d;
            d=t;
        }
        if(b>e) {
            double t=b;
            b=e;
            e=t;
        }
        if(b>c) {
            double t=b;
            b=c;
            c=t;
        }
        return c<e?c:e;
    }
    /**
    <p>五点三分</p><br>
    计算数组中五个整数中第二大的数。
    @param fiveNumbers 五个整数。
    @return 数组中五个整数中第二大的数。
    */
    public static int maxSecond5(int... fiveNumbers) {
        if(fiveNumbers.length!=5) {
            return Integer.MIN_VALUE;
        }
        int a=fiveNumbers[0];
        int b=fiveNumbers[1];
        int c=fiveNumbers[2];
        int d=fiveNumbers[3];
        int e=fiveNumbers[4];
        int win1,lose1;
        if(a>b) {
            win1=a;
            lose1=b;
        } else {
            win1=b;
            lose1=a;
        }
        int win2,lose2;
        if(c>d) {
            win2=c;
            lose2=d;
        } else {
            win2=d;
            lose2=c;
        }
        int max1;
        int candidate1,candidate2;
        if(win1>win2) {
            max1=win1;
            candidate1=win2;
            candidate2=lose1;
        } else {
            max1=win2;
            candidate1=win1;
            candidate2=lose2;
        }
        if(e>max1) {
            return max1;
        } else if(e>candidate1) {
            return e>candidate2?e:candidate2;
        } else {
            return candidate1>candidate2?candidate1:candidate2;
        }
    }
    /**
    <p>五点三分</p><br>
    计算数组中五个整数中第二小的数。
    @param fiveNumbers 五个整数。
    @return 数组中五个整数中第二小的数。
    */
    public static int minSecond5(int... fiveNumbers) {
        if(fiveNumbers.length!=5) {
            return Integer.MIN_VALUE;
        }
        int a=fiveNumbers[0];
        int b=fiveNumbers[1];
        int c=fiveNumbers[2];
        int d=fiveNumbers[3];
        int e=fiveNumbers[4];
        int win1,lose1;
        if(a<b) {
            win1=a;
            lose1=b;
        } else {
            win1=b;
            lose1=a;
        }
        int win2,lose2;
        if(c<d) {
            win2=c;
            lose2=d;
        } else {
            win2=d;
            lose2=c;
        }
        int min1;
        int candidate1,candidate2;
        if(win1<win2) {
            min1=win1;
            candidate1=win2;
            candidate2=lose1;
        } else {
            min1=win2;
            candidate1=win1;
            candidate2=lose2;
        }
        if(e<min1) {
            return min1;
        } else if(e<candidate1) {
            return e<candidate2?e:candidate2;
        } else {
            return candidate1<candidate2?candidate1:candidate2;
        }
    }
    /**
    <p>冒泡排序</p><br>
    <p>此方法会修改输入的数据。</p><br>
    @param numbers 待排整型数组。
    */
    public static void bubble(int numbers[]) {
        int temp;
        int lastSwap=numbers.length-1;
        for(int i=0;i<numbers.length-1;i++) {
            int newLastSwap=0;
            for(int j=0;j<lastSwap;j++) {
                if(numbers[j]>numbers[j+1]) {
                    temp=numbers[j];
                    numbers[j]=numbers[j+1];
                    numbers[j+1]=temp;
                    newLastSwap=j;
                }
            }
            if(newLastSwap==0) {
                break;
            }
            lastSwap=newLastSwap;
        }
    }
    /**
    <p>冒泡排序</p><br>
    <p>此方法会修改输入的数据。</p><br>
    @param numbers 待排双精度浮点数数组。
    */
    public static void bubble(double numbers[]) {
        double temp;
        int lastSwap=numbers.length-1;
        for(int i=0;i<numbers.length-1;i++) {
            int newLastSwap=0;
            for(int j=0;j<lastSwap;j++) {
                if(numbers[j]>numbers[j+1]) {
                    temp=numbers[j];
                    numbers[j]=numbers[j+1];
                    numbers[j+1]=temp;
                    newLastSwap=j;
                }
            }
            if(newLastSwap==0) {
                break;
            }
            lastSwap=newLastSwap;
        }
    }
    /**
    <p>选择排序</p><br>
    <p>此方法会修改输入的数据。</p><br>
    @param numbers 待排整型数组。
    */
    public static void selection(int numbers[]) {
        int temp,minIndex;
        for(int i=0;i<numbers.length-1;i++) {
            minIndex=i;
            for(int j=i+1;j<numbers.length;j++) {
                if(numbers[j]<numbers[minIndex]) {
                    minIndex=j;
                }
            }
            if(minIndex!=i) {
                temp=numbers[i];
                numbers[i]=numbers[minIndex];
                numbers[minIndex]=temp;
            }
        }
    }
    /**
    <p>选择排序</p><br>
    <p>此方法会修改输入的数据。</p><br>
    @param numbers 待排双精度浮点数数组。
    */
    public static void selection(double numbers[]) {
        int minIndex;
        double temp;
        for(int i=0;i<numbers.length-1;i++) {
            minIndex=i;
            for(int j=i+1;j<numbers.length;j++) {
                if(numbers[j]<numbers[minIndex]) {
                    minIndex=j;
                }
            }
            if(minIndex!=i) {
                temp=numbers[i];
                numbers[i]=numbers[minIndex];
                numbers[minIndex]=temp;
            }
        }
    }
    /**
    <p>插入排序</p><br>
    <p>此方法会修改输入的数据。</p><br>
    @param numbers 待排整型数组。
    */
    public static void insertion(int numbers[]) {
        int temp;
        for(int i=1;i<numbers.length;i++) {
            temp=numbers[i];
            int j=i;
            for(;j>0&&temp<numbers[j-1];j--) {
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
    public static void insertion(double numbers[]) {
        double temp;
        for(int i=1;i<numbers.length;i++) {
            temp=numbers[i];
            int j=i;
            for(;j>0&&temp<numbers[j-1];j--) {
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
    public static void shell(int numbers[]) {
        for(int delta=numbers.length/2;delta>0;delta/=2) {
            int temp;
            int limit=numbers.length-delta;
            for(int i=delta;i<=limit;i++) {
                temp=numbers[i];
                int j=i;
                for(;j>=delta&&temp<numbers[j-delta];j-=delta) {
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
    public static void shell(double numbers[]) {
        for(int delta=numbers.length/2;delta>0;delta/=2) {
            double temp;
            int limit=numbers.length-delta;
            for(int i=delta;i<=limit;i++) {
                temp=numbers[i];
                int j=i;
                for(;j>=delta&&temp<numbers[j-delta];j-=delta) {
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
    public static void quick(int numbers[]) {
        int indexs[]=new int[numbers.length*2+2];
        indexs[0]=0;
        indexs[1]=numbers.length-1;
        int pin=2;
        while(pin>1) {
            int indexRight=indexs[--pin];
            int indexLeft=indexs[--pin];
            if(indexLeft<indexRight) {
                int length=indexRight-indexLeft+1;
                int pivot=numbers[indexRight];
                if(length>=5) {
                    int fifth[]={indexLeft,indexLeft+(length>>2),indexLeft+(length>>1),indexRight-(length>>2),indexRight};
                    int a=numbers[fifth[0]],b=numbers[fifth[1]],c=numbers[fifth[2]],d=numbers[fifth[3]],e=numbers[fifth[4]];
                    if(a>b) {
                        int t=a;
                        a=b;
                        b=t;
                    }
                    if(c>d) {
                        int t=c;
                        c=d;
                        d=t;
                    }
                    if(a>c) {
                        int t=a;
                        a=c;
                        c=t;
                        t=b;
                        b=d;
                        d=t;
                    }
                    if(b>e) {
                        int t=b;
                        b=e;
                        e=t;
                    }
                    if(b>c) {
                        int t=b;
                        b=c;
                        c=t;
                    }
                    pivot=c<e?c:e;
                    for(int medianIndex=0;medianIndex<5;medianIndex++) {
                        if(pivot==numbers[fifth[medianIndex]]) {
                            numbers[fifth[medianIndex]]=numbers[indexRight];
                            numbers[indexRight]=pivot;
                            break;
                        }
                    }
                }
                int left=indexLeft-1,right=indexRight;
                int temp;
                while(left<right) {
                    for(left++;numbers[left]<pivot;left++);
                    for(right--;left<right&&numbers[right]>pivot;right--);
                    if(left<right&&numbers[left]!=numbers[right]) {
                        temp=numbers[left];
                        numbers[left]=numbers[right];
                        numbers[right]=temp;
                    }
                }
                numbers[indexRight]=numbers[left];
                numbers[left]=pivot;
                indexs[pin++]=left+1;
                indexs[pin++]=indexRight;
                indexs[pin++]=indexLeft;
                indexs[pin++]=left-1;
            }
        }
    }
    /**
    <p>快速排序</p><br>
    <p>此方法会修改输入的数据。</p><br>
    @param numbers 待排双精度浮点数数组。
    */
    public static void quick(double numbers[]) {
        int indexs[]=new int[numbers.length*2+2];
        indexs[0]=0;
        indexs[1]=numbers.length-1;
        int pin=2;
        while(pin>1) {
            int indexRight=indexs[--pin];
            int indexLeft=indexs[--pin];
            if(indexLeft<indexRight) {
                int length=indexRight-indexLeft+1;
                double pivot=numbers[indexRight];
                if(length>=5) {
                    int fifth[]={indexLeft,indexLeft+(length>>2),indexLeft+(length>>1),indexRight-(length>>2),indexRight};
                    double a=numbers[fifth[0]],b=numbers[fifth[1]],c=numbers[fifth[2]],d=numbers[fifth[3]],e=numbers[fifth[4]];
                    if(a>b) {
                        double t=a;
                        a=b;
                        b=t;
                    }
                    if(c>d) {
                        double t=c;
                        c=d;
                        d=t;
                    }
                    if(a>c) {
                        double t=a;
                        a=c;
                        c=t;
                        t=b;
                        b=d;
                        d=t;
                    }
                    if(b>e) {
                        double t=b;
                        b=e;
                        e=t;
                    }
                    if(b>c) {
                        double t=b;
                        b=c;
                        c=t;
                    }
                    pivot=c<e?c:e;
                    for(int medianIndex=0;medianIndex<5;medianIndex++) {
                        if(pivot==numbers[fifth[medianIndex]]) {
                            numbers[fifth[medianIndex]]=numbers[indexRight];
                            numbers[indexRight]=pivot;
                            break;
                        }
                    }
                }
                int left=indexLeft-1,right=indexRight;
                double temp;
                while(left<right) {
                    for(left++;left<right&&numbers[left]<pivot;left++);
                    for(right--;left<right&&numbers[right]>pivot;right--);
                    if(left<right&&numbers[left]!=numbers[right]) {
                        temp=numbers[left];
                        numbers[left]=numbers[right];
                        numbers[right]=temp;
                    }
                }
                numbers[indexRight]=numbers[left];
                numbers[left]=pivot;
                indexs[pin++]=left+1;
                indexs[pin++]=indexRight;
                indexs[pin++]=indexLeft;
                indexs[pin++]=left-1;
            }
        }
    }
    /**
    <p>双轴快速排序</p><br>
    <p>此方法会修改输入的数据。</p><br>
    @param numbers 待排整型数组。
    */
    public static void quickDualPivot(int numbers[]) {
        int indexs[]=new int[(numbers.length<<1)+2];
        indexs[0]=0;
        indexs[1]=numbers.length-1;
        int pin=2;
        while(pin>1) {
            int indexRight=indexs[--pin];
            int indexLeft=indexs[--pin];
            if(indexLeft<indexRight) {
                int length=indexRight-indexLeft+1;
                int temp;
                if(length<5&&numbers[indexLeft]>numbers[indexRight]) {
                    temp=numbers[indexLeft];
                    numbers[indexLeft]=numbers[indexRight];
                    numbers[indexRight]=temp;
                }
                int pivot1=numbers[indexLeft];
                int pivot2=numbers[indexRight];
                if(length>=5) {
                    int fifth[]={indexLeft,indexLeft+(length>>2),indexLeft+(length>>1),indexRight-(length>>2),indexRight};
                    int a=numbers[fifth[0]],b=numbers[fifth[1]],c=numbers[fifth[2]],d=numbers[fifth[3]],e=numbers[fifth[4]];
                    int lessWin1,lessLose1,lessWin2,lessLose2,lessCandidate1,lessCandidate2,min1,greatCandidate1,greatCandidate2,greatCandidate3,max1;
                    if(a<b) {
                        lessWin1=a;
                        lessLose1=b;
                    } else {
                        lessWin1=b;
                        lessLose1=a;
                    }
                    if(c<d) {
                        lessWin2=c;
                        lessLose2=d;
                    } else {
                        lessWin2=d;
                        lessLose2=c;
                    }
                    if(lessWin1<lessWin2) {
                        min1=lessWin1;
                        lessCandidate1=lessWin2;
                        lessCandidate2=lessLose1;
                        greatCandidate1=lessLose2;
                    } else {
                        min1=lessWin2;
                        lessCandidate1=lessWin1;
                        lessCandidate2=lessLose2;
                        greatCandidate1=lessLose1;
                    }
                    if(e<min1) {
                        pivot1=min1;
                        min1=e;
                        greatCandidate2=lessCandidate1;
                        greatCandidate3=lessCandidate2;
                    } else if(e<lessCandidate1) {
                        pivot1=e<lessCandidate2?e:lessCandidate2;
                        greatCandidate2=lessCandidate1;
                        greatCandidate3=e>lessCandidate2?e:lessCandidate2;
                    } else {
                        pivot1=lessCandidate1<lessCandidate2?lessCandidate1:lessCandidate2;
                        greatCandidate2=e;
                        greatCandidate3=lessCandidate1>lessCandidate2?lessCandidate1:lessCandidate2;
                    }
                    if(greatCandidate1>greatCandidate2) {
                        if(greatCandidate2>greatCandidate3) {
                            max1=greatCandidate1;
                            pivot2=greatCandidate2;
                        } else if(greatCandidate3>greatCandidate1) {
                            max1=greatCandidate3;
                            pivot2=greatCandidate1;
                        } else {
                            max1=greatCandidate1;
                            pivot2=greatCandidate3;
                        }
                    } else {
                        if(greatCandidate2<greatCandidate3) {
                            max1=greatCandidate3;
                            pivot2=greatCandidate2;
                        } else if(greatCandidate3<greatCandidate1) {
                            max1=greatCandidate2;
                            pivot2=greatCandidate1;
                        } else {
                            max1=greatCandidate2;
                            pivot2=greatCandidate3;
                        }
                    }
                    if(pivot1==pivot2) {
                        pivot1=min1;
                        pivot2=max1;
                    }
                    for(int pivotIndex=0;pivotIndex<5;pivotIndex++) {
                        if(pivot1==numbers[fifth[pivotIndex]]) {
                            numbers[fifth[pivotIndex]]=numbers[indexLeft];
                            numbers[indexLeft]=pivot1;
                            break;
                        }
                    }
                    for(int pivotIndex=4;pivotIndex>=0;pivotIndex--) {
                        if(pivot2==numbers[fifth[pivotIndex]]) {
                            numbers[fifth[pivotIndex]]=numbers[indexRight];
                            numbers[indexRight]=pivot2;
                            break;
                        }
                    }
                }
                int left=indexLeft;
                int right=indexRight;
                int k=indexLeft+1;
                boolean back=false;
                while(k<right) {
                    if(numbers[k]<pivot1) {
                        temp=numbers[++left];
                        numbers[left]=numbers[k];
                        numbers[k++]=temp;
                    } else if(numbers[k]<=pivot2) {
                        k++;
                    } else {
                        back=false;
                        while(numbers[--right]>pivot2) {
                            if(k>=right) {
                                back=true;
                                break;
                            }
                        }
                        if(!back) {
                            if(numbers[right]<pivot1) {
                                temp=numbers[right];
                                numbers[right]=numbers[k];
                                numbers[k]=numbers[++left];
                                numbers[left]=temp;
                            } else {
                                temp=numbers[right];
                                numbers[right]=numbers[k];
                                numbers[k]=temp;
                            }
                            k++;
                        }
                    }
                }
                temp=numbers[indexLeft];
                numbers[indexLeft]=numbers[left];
                numbers[left]=temp;
                temp=numbers[indexRight];
                numbers[indexRight]=numbers[right];
                numbers[right]=temp;
                indexs[pin++]=right+1;
                indexs[pin++]=indexRight;
                if(pivot1!=pivot2) {
                    indexs[pin++]=left+1;
                    indexs[pin++]=right-1;
                }
                indexs[pin++]=indexLeft;
                indexs[pin++]=left-1;
            }
        }
    }
    /**
    <p>双轴快速排序</p><br>
    <p>此方法会修改输入的数据。</p><br>
    @param numbers 待排双精度浮点数数组。
    */
    public static void quickDualPivot(double numbers[]) {
        int indexs[]=new int[(numbers.length<<1)+2];
        indexs[0]=0;
        indexs[1]=numbers.length-1;
        int pin=2;
        while(pin>1) {
            int indexRight=indexs[--pin];
            int indexLeft=indexs[--pin];
            if(indexLeft<indexRight) {
                int length=indexRight-indexLeft+1;
                double temp;
                if(length<5&&numbers[indexLeft]>numbers[indexRight]) {
                    temp=numbers[indexLeft];
                    numbers[indexLeft]=numbers[indexRight];
                    numbers[indexRight]=temp;
                }
                double pivot1=numbers[indexLeft];
                double pivot2=numbers[indexRight];
                if(length>=5) {
                    int fifth[]={indexLeft,indexLeft+(length>>2),indexLeft+(length>>1),indexRight-(length>>2),indexRight};
                    double a=numbers[fifth[0]],b=numbers[fifth[1]],c=numbers[fifth[2]],d=numbers[fifth[3]],e=numbers[fifth[4]];
                    double lessWin1,lessLose1,lessWin2,lessLose2,lessCandidate1,lessCandidate2,min1,greatCandidate1,greatCandidate2,greatCandidate3,max1;
                    if(a<b) {
                        lessWin1=a;
                        lessLose1=b;
                    } else {
                        lessWin1=b;
                        lessLose1=a;
                    }
                    if(c<d) {
                        lessWin2=c;
                        lessLose2=d;
                    } else {
                        lessWin2=d;
                        lessLose2=c;
                    }
                    if(lessWin1<lessWin2) {
                        min1=lessWin1;
                        lessCandidate1=lessWin2;
                        lessCandidate2=lessLose1;
                        greatCandidate1=lessLose2;
                    } else {
                        min1=lessWin2;
                        lessCandidate1=lessWin1;
                        lessCandidate2=lessLose2;
                        greatCandidate1=lessLose1;
                    }
                    if(e<min1) {
                        pivot1=min1;
                        min1=e;
                        greatCandidate2=lessCandidate1;
                        greatCandidate3=lessCandidate2;
                    } else if(e<lessCandidate1) {
                        pivot1=e<lessCandidate2?e:lessCandidate2;
                        greatCandidate2=lessCandidate1;
                        greatCandidate3=e>lessCandidate2?e:lessCandidate2;
                    } else {
                        pivot1=lessCandidate1<lessCandidate2?lessCandidate1:lessCandidate2;
                        greatCandidate2=e;
                        greatCandidate3=lessCandidate1>lessCandidate2?lessCandidate1:lessCandidate2;
                    }
                    if(greatCandidate1>greatCandidate2) {
                        if(greatCandidate2>greatCandidate3) {
                            max1=greatCandidate1;
                            pivot2=greatCandidate2;
                        } else if(greatCandidate3>greatCandidate1) {
                            max1=greatCandidate3;
                            pivot2=greatCandidate1;
                        } else {
                            max1=greatCandidate1;
                            pivot2=greatCandidate3;
                        }
                    } else {
                        if(greatCandidate2<greatCandidate3) {
                            max1=greatCandidate3;
                            pivot2=greatCandidate2;
                        } else if(greatCandidate3<greatCandidate1) {
                            max1=greatCandidate2;
                            pivot2=greatCandidate1;
                        } else {
                            max1=greatCandidate2;
                            pivot2=greatCandidate3;
                        }
                    }
                    if(pivot1==pivot2) {
                        pivot1=min1;
                        pivot2=max1;
                    }
                    for(int pivotIndex=0;pivotIndex<5;pivotIndex++) {
                        if(pivot1==numbers[fifth[pivotIndex]]) {
                            numbers[fifth[pivotIndex]]=numbers[indexLeft];
                            numbers[indexLeft]=pivot1;
                            break;
                        }
                    }
                    for(int pivotIndex=4;pivotIndex>=0;pivotIndex--) {
                        if(pivot2==numbers[fifth[pivotIndex]]) {
                            numbers[fifth[pivotIndex]]=numbers[indexRight];
                            numbers[indexRight]=pivot2;
                            break;
                        }
                    }
                }
                int left=indexLeft;
                int right=indexRight;
                int k=indexLeft+1;
                boolean back=false;
                while(k<right) {
                    if(numbers[k]<pivot1) {
                        temp=numbers[++left];
                        numbers[left]=numbers[k];
                        numbers[k++]=temp;
                    } else if(numbers[k]<=pivot2) {
                        k++;
                    } else {
                        back=false;
                        while(numbers[--right]>pivot2) {
                            if(k>=right) {
                                back=true;
                                break;
                            }
                        }
                        if(!back) {
                            if(numbers[right]<pivot1) {
                                temp=numbers[right];
                                numbers[right]=numbers[k];
                                numbers[k]=numbers[++left];
                                numbers[left]=temp;
                            } else {
                                temp=numbers[right];
                                numbers[right]=numbers[k];
                                numbers[k]=temp;
                            }
                            k++;
                        }
                    }
                }
                temp=numbers[indexLeft];
                numbers[indexLeft]=numbers[left];
                numbers[left]=temp;
                temp=numbers[indexRight];
                numbers[indexRight]=numbers[right];
                numbers[right]=temp;
                indexs[pin++]=right+1;
                indexs[pin++]=indexRight;
                if(pivot1!=pivot2) {
                    indexs[pin++]=left+1;
                    indexs[pin++]=right-1;
                }
                indexs[pin++]=indexLeft;
                indexs[pin++]=left-1;
            }
        }
    }
    /**
    <p>归并排序</p><br>
    <p>此方法会修改输入的数据。</p><br>
    @param numbers 待排整型数组。
    */
    public static void merge(int numbers[]) {
        int address[]=numbers;
        int n=numbers.length;
        int result[]=new int[n];
        for(int length=1;length<n;length<<=1) {
            for(int indexLeft=0;indexLeft<n;indexLeft+=length<<1) {
                int indexRight=indexLeft+(length<<1)-1<n-1?indexLeft+(length<<1)-1:n-1;
                int indexMiddle=indexLeft+length-1;
                if(indexMiddle<indexRight) {
                    int left=indexLeft,right=indexMiddle+1;
                    int pin=indexLeft;
                    while(left<=indexMiddle&&right<=indexRight) {
                        if(numbers[left]<=numbers[right]) {
                            result[pin++]=numbers[left++];
                        } else {
                            result[pin++]=numbers[right++];
                        }
                    }
                    while(left<=indexMiddle) {
                        result[pin++]=numbers[left++];
                    }
                    while(right<=indexRight) {
                        result[pin++]=numbers[right++];
                    }
                } else {
                    for(int i=indexLeft;i<=indexRight;i++) {
                        result[i]=numbers[i];
                    }
                }
            }
            int temp[]=numbers;
            numbers=result;
            result=temp;
        }
        if(address!=numbers) {
            for(int i=0;i<n;i++) {
                address[i]=numbers[i];
            }
        }
    }
    /**
    <p>归并排序</p><br>
    <p>此方法会修改输入的数据。</p><br>
    @param numbers 待排双精度浮点数数组。
    */
    public static void merge(double numbers[]) {
        double address[]=numbers;
        int n=numbers.length;
        double result[]=new double[n];
        for(int length=1;length<n;length<<=1) {
            for(int indexLeft=0;indexLeft<n;indexLeft+=length<<1) {
                int indexRight=indexLeft+(length<<1)-1<n-1?indexLeft+(length<<1)-1:n-1;
                int indexMiddle=indexLeft+length-1;
                if(indexMiddle<indexRight) {
                    int left=indexLeft,right=indexMiddle+1;
                    int pin=indexLeft;
                    while(left<=indexMiddle&&right<=indexRight) {
                        if(numbers[left]<=numbers[right]) {
                            result[pin++]=numbers[left++];
                        } else {
                            result[pin++]=numbers[right++];
                        }
                    }
                    while(left<=indexMiddle) {
                        result[pin++]=numbers[left++];
                    }
                    while(right<=indexRight) {
                        result[pin++]=numbers[right++];
                    }
                } else {
                    for(int i=indexLeft;i<=indexRight;i++) {
                        result[i]=numbers[i];
                    }
                }
            }
            double temp[]=numbers;
            numbers=result;
            result=temp;
        }
        if(address!=numbers) {
            for(int i=0;i<n;i++) {
                address[i]=numbers[i];
            }
        }
    }
    /**
    <p>计数排序</p><br>
    <p>此方法会修改输入的数据。</p><br>
    @param numbers 待排整型数组。
    */
    public static void counting(int numbers[]) {
        int min=numbers[0];
        int max=numbers[0];
        for(int number:numbers) {
            min=number<min?number:min;
            max=number>max?number:max;
        }
        int range=max-min+1;
        int count[]=new int[range];
        for(int number:numbers) {
            count[number-min]++;
        }
        int pin=0;
        for(int i=0;i<range;i++) {
            for(;count[i]!=0;count[i]--) {
                numbers[pin++]=min+i;
            }
        }
    }
    /**
    <p>基数排序</p><br>
    <p>此方法会修改输入的数据。</p><br>
    @param numbers 待排整型数组。
    */
    public static void radix(int numbers[]) {
        long longNumbers[]=new long[numbers.length];
        long max=numbers[0];
        long min=max;
        for(int i=0;i<longNumbers.length;i++) {
            long now=numbers[i];
            longNumbers[i]=now;
            max=now>max?now:max;
            min=now<min?now:min;
        }
        if(min<0) {
            for(int i=0;i<longNumbers.length;i++) {
                longNumbers[i]-=min;
            }
            max-=min;
        } else {
            min=0;
        }
        int capacity[]={10,10,10,10,10,10,10,10,10,10};
        long bucket[][]=new long[10][10];
        int pin[]=new int[10];
        for(long i=1;i<max;i*=10) {
            for(int j=0;j<10;j++) {
                pin[j]=0;
            }
            for(int j=0;j<longNumbers.length;j++) {
                long now=longNumbers[j];
                int nowBit=(int)(now/i%10);
                if(pin[nowBit]>=capacity[nowBit]) {
                    capacity[nowBit]=(capacity[nowBit]<<1)+2;
                    long newBucket[]=new long[capacity[nowBit]];
                    System.arraycopy(bucket[nowBit],0,newBucket,0,pin[nowBit]);
                    bucket[nowBit]=newBucket;
                }
                bucket[nowBit][pin[nowBit]++]=now;
            }
            int index=0;
            for(int j=0;j<10;j++) {
                int nowPin=pin[j];
                long nowBucket[]=bucket[j];
                for(int k=0;k<nowPin;k++) {
                    longNumbers[index++]=nowBucket[k];
                }
            }
        }
        for(int i=0;i<numbers.length;i++) {
            numbers[i]=(int)(longNumbers[i]+min);
        }
    }
}