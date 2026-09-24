package aio.collection;
/**
<p>并发双轴快速排序类</p><br>
本类封装了一个双轴快速排序算法，并实现了Runnable接口，供线程调用。
*/
class ConcurrentQuickDualPivotSort implements Runnable {
    /**
    <p>线程创建阈值</p><br>
    若分割后的子数组大小大于等于此值，则创建新线程进行排序。<br>
    否则，在当前线程中继续排序。
    */
    public static int threshold=19683;
    /**
    <p>待排数组</p>
    */
    int numbers[];
    /**
    <p>待排区间下标下界</p>
    */
    int indexLeft;
    /**
    <p>待排区间下标上界</p>
    */
    int indexRight;
    /**
    <p>全参构造方法</p><br>
    通过待排数组、区间下标上下界构造并发双轴快速排序对象。
    @param numbers 待排数组。
    @param indexLeft 待排区间下界。
    @param indexRight 待排区间上界。
    */
    public ConcurrentQuickDualPivotSort(int numbers[],int indexLeft,int indexRight) {
        this.numbers=numbers;
        this.indexLeft=indexLeft;
        this.indexRight=indexRight;
    }
    /**
    <p>构造方法</p><br>
    通过待排数组构造并发双轴快速排序对象。<br>
    默认排序整个待排数组。
    @param numbers 待排数组。
    */
    public ConcurrentQuickDualPivotSort(int numbers[]) {
        this.numbers=numbers;
        indexLeft=0;
        indexRight=numbers.length-1;
    }
    /**
    <p>双轴快速排序</p><br>
    对待排数组的指定下标区间进行双轴快速排序。
    @param numbers 待排数组。
    @param indexLeft 待排区间下界。
    @param indexRight 待排区间上界。
    */
    public static void quickDualPivot(int numbers[],int indexLeft,int indexRight) {
        int capacity=(indexRight-indexLeft)*2+2;
        int indexs[]=new int[capacity<10?10:capacity];
        indexs[0]=indexLeft;
        indexs[1]=indexRight;
        int pin=2;
        while(pin>1) {
            indexRight=indexs[--pin];
            indexLeft=indexs[--pin];
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
                    int lessWin1,lessLose1,lessWin2,lessLose2,lessCandidate1,lessCandidate2,min1;
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
                    } else {
                        min1=lessWin2;
                        lessCandidate1=lessWin1;
                        lessCandidate2=lessLose2;
                    }
                    if(e<min1) {
                        pivot1=min1;
                        min1=e;
                    } else if(e<lessCandidate1) {
                        pivot1=e<lessCandidate2?e:lessCandidate2;
                    } else {
                        pivot1=lessCandidate1<lessCandidate2?lessCandidate1:lessCandidate2;
                    }
                    int greatWin1,greatLose1,greatWin2,greatLose2,greatCandidate1,greatCandidate2,max1;
                    if(a>b) {
                        greatWin1=a;
                        greatLose1=b;
                    } else {
                        greatWin1=b;
                        greatLose1=a;
                    }
                    if(c>d) {
                        greatWin2=c;
                        greatLose2=d;
                    } else {
                        greatWin2=d;
                        greatLose2=c;
                    }
                    if(greatWin1>greatWin2) {
                        max1=greatWin1;
                        greatCandidate1=greatWin2;
                        greatCandidate2=greatLose1;
                    } else {
                        max1=greatWin2;
                        greatCandidate1=greatWin1;
                        greatCandidate2=greatLose2;
                    }
                    if(e>max1) {
                        pivot2=max1;
                        max1=e;
                    } else if(e>greatCandidate1) {
                        pivot2=e>greatCandidate2?e:greatCandidate2;
                    } else {
                        pivot2=greatCandidate1>greatCandidate2?greatCandidate1:greatCandidate2;
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
    <p>并发双轴快速排序</p><br>
    对待排数组的指定下标区间进行双轴快速排序。<br>
    若分割后的子数组大小大于等于线程创建阈值，则创建新线程进行排序。<br>
    否则，在当前线程中继续排序。
    @param numbers 待排数组。
    @param indexLeft 待排区间下界。
    @param indexRight 待排区间上界。
    */
    public static void concurrentQuickDualPivot(int numbers[],int indexLeft,int indexRight) {
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
                int lessWin1,lessLose1,lessWin2,lessLose2,lessCandidate1,lessCandidate2,min1;
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
                } else {
                    min1=lessWin2;
                    lessCandidate1=lessWin1;
                    lessCandidate2=lessLose2;
                }
                if(e<min1) {
                    pivot1=min1;
                    min1=e;
                } else if(e<lessCandidate1) {
                    pivot1=e<lessCandidate2?e:lessCandidate2;
                } else {
                    pivot1=lessCandidate1<lessCandidate2?lessCandidate1:lessCandidate2;
                }
                int greatWin1,greatLose1,greatWin2,greatLose2,greatCandidate1,greatCandidate2,max1;
                if(a>b) {
                    greatWin1=a;
                    greatLose1=b;
                } else {
                    greatWin1=b;
                    greatLose1=a;
                }
                if(c>d) {
                    greatWin2=c;
                    greatLose2=d;
                } else {
                    greatWin2=d;
                    greatLose2=c;
                }
                if(greatWin1>greatWin2) {
                    max1=greatWin1;
                    greatCandidate1=greatWin2;
                    greatCandidate2=greatLose1;
                } else {
                    max1=greatWin2;
                    greatCandidate1=greatWin1;
                    greatCandidate2=greatLose2;
                }
                if(e>max1) {
                    pivot2=max1;
                    max1=e;
                } else if(e>greatCandidate1) {
                    pivot2=e>greatCandidate2?e:greatCandidate2;
                } else {
                    pivot2=greatCandidate1>greatCandidate2?greatCandidate1:greatCandidate2;
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
            Thread manager[]=new Thread[3];
            if(left-1-indexLeft>=threshold) {
                manager[0]=Thread.startVirtualThread(new ConcurrentQuickDualPivotSort(numbers,indexLeft,left-1));
            } else {
                quickDualPivot(numbers,indexLeft,left-1);
            }
            if(pivot1!=pivot2) {
                if(right-left-2>=threshold) {
                    manager[1]=Thread.startVirtualThread(new ConcurrentQuickDualPivotSort(numbers,left+1,right-1));
                } else {
                    quickDualPivot(numbers,left+1,right-1);
                }
            }
            if(indexRight-right-1>=threshold) {
                manager[2]=Thread.startVirtualThread(new ConcurrentQuickDualPivotSort(numbers,right+1,indexRight));
            } else {
                quickDualPivot(numbers,right+1,indexRight);
            }
            try {
                for(int i=0;i<3;i++) {
                    if(manager[i]!=null) {
                        manager[i].join();
                    }
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
    /**
    <p>并发双轴快速排序线程</p><br>
    Runnable接口中的方法，用于执行线程任务。
    */
    public void run() {
        concurrentQuickDualPivot(numbers,indexLeft,indexRight);
    }
}
/**
<p>并发排序类</p><br>
<p style="color:#FF0000;">此类功能可能不稳定，若非学习、研究和极端情况，请使用<code>collection.sort</code>类进行排序。</p><br>
用于对数组进行排序。<br>
使用并发操作提高效率。
*/
public class ConcurrentSort {
    /**
    <p>此方法会修改输入的数据。</p><br>
    并发双轴快速排序。
    @param numbers 整型数组。
    */
    public static void concurrentQuickDualPivot(int numbers[]) {
        ConcurrentQuickDualPivotSort instance=new ConcurrentQuickDualPivotSort(numbers,0,numbers.length-1);
        Thread manager=Thread.startVirtualThread(instance);
        try {
            manager.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}