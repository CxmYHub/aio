package tools.collection;
class concurrent_quick_dual_pivot_sort implements Runnable
{
    public static int threshold=19683;
    int numbers[];
    int index_left;
    int index_right;
    public concurrent_quick_dual_pivot_sort(int numbers[],int index_left,int index_right)
    {
        this.numbers=numbers;
        this.index_left=index_left;
        this.index_right=index_right;
    }
    public concurrent_quick_dual_pivot_sort(int numbers[])
    {
        this.numbers=numbers;
        this.index_left=0;
        this.index_right=numbers.length-1;
    }
	public static void quick_dual_pivot(int numbers[],int index_left,int index_right)
	{
        int capacity=(index_right-index_left)*2+2;
		int indexs[]=new int[capacity<10?10:capacity];
		indexs[0]=index_left;
		indexs[1]=index_right;
		int pin=2;
		while(pin>1)
		{
			index_right=indexs[--pin];
			index_left=indexs[--pin];
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
					int less_win1,less_lose1,less_win2,less_lose2,less_candidate1,less_candidate2,min1;
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
					}
					else
					{
						min1=less_win2;
						less_candidate1=less_win1;
						less_candidate2=less_lose2;
					}
					if(e<min1)
					{
						pivot1=min1;
						min1=e;
					}
					else if(e<less_candidate1)
					{
						pivot1=e<less_candidate2?e:less_candidate2;
					}
					else
					{
						pivot1=less_candidate1<less_candidate2?less_candidate1:less_candidate2;
					}
					int great_win1,great_lose1,great_win2,great_lose2,great_candidate1,great_candidate2,max1;
					if(a>b)
					{
						great_win1=a;
						great_lose1=b;
					}
					else
					{
						great_win1=b;
						great_lose1=a;
					}
					if(c>d)
					{
						great_win2=c;
						great_lose2=d;
					}
					else
					{
						great_win2=d;
						great_lose2=c;
					}
					if(great_win1>great_win2)
					{
						max1=great_win1;
						great_candidate1=great_win2;
						great_candidate2=great_lose1;
					}
					else
					{
						max1=great_win2;
						great_candidate1=great_win1;
						great_candidate2=great_lose2;
					}
					if(e>max1)
					{
						pivot2=max1;
						max1=e;
					}
					else if(e>great_candidate1)
					{
						pivot2=e>great_candidate2?e:great_candidate2;
					}
					else
					{
						pivot2=great_candidate1>great_candidate2?great_candidate1:great_candidate2;
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
	public static void concurrent_quick_dual_pivot(int numbers[],int index_left,int index_right)
	{
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
				int less_win1,less_lose1,less_win2,less_lose2,less_candidate1,less_candidate2,min1;
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
				}
				else
				{
					min1=less_win2;
					less_candidate1=less_win1;
					less_candidate2=less_lose2;
				}
				if(e<min1)
				{
					pivot1=min1;
					min1=e;
				}
				else if(e<less_candidate1)
				{
					pivot1=e<less_candidate2?e:less_candidate2;
				}
				else
				{
					pivot1=less_candidate1<less_candidate2?less_candidate1:less_candidate2;
				}
				int great_win1,great_lose1,great_win2,great_lose2,great_candidate1,great_candidate2,max1;
				if(a>b)
				{
					great_win1=a;
					great_lose1=b;
				}
				else
				{
					great_win1=b;
					great_lose1=a;
				}
				if(c>d)
				{
					great_win2=c;
					great_lose2=d;
				}
				else
				{
					great_win2=d;
					great_lose2=c;
				}
				if(great_win1>great_win2)
				{
					max1=great_win1;
					great_candidate1=great_win2;
					great_candidate2=great_lose1;
				}
				else
				{
					max1=great_win2;
					great_candidate1=great_win1;
					great_candidate2=great_lose2;
				}
				if(e>max1)
				{
					pivot2=max1;
					max1=e;
				}
				else if(e>great_candidate1)
				{
					pivot2=e>great_candidate2?e:great_candidate2;
				}
				else
				{
					pivot2=great_candidate1>great_candidate2?great_candidate1:great_candidate2;
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
            Thread manager[]=new Thread[3];
            if(left-1-index_left>=threshold)
            {
                concurrent_quick_dual_pivot_sort instance=new concurrent_quick_dual_pivot_sort(numbers,index_left,left-1);
                manager[0]=new Thread(instance);
                manager[0].start();
            }
            else
            {
                quick_dual_pivot(numbers,index_left,left-1);
            }
			if(pivot1!=pivot2)
			{
                if(left-1-index_left>=threshold)
                {
                    concurrent_quick_dual_pivot_sort instance=new concurrent_quick_dual_pivot_sort(numbers,left+1,right-1);
                    manager[1]=new Thread(instance);
                    manager[1].start();
                }
                else
                {
                    quick_dual_pivot(numbers,left+1,right-1);
                }
			}
            if(left-1-index_left>=threshold)
            {
                concurrent_quick_dual_pivot_sort instance=new concurrent_quick_dual_pivot_sort(numbers,right+1,index_right);
                manager[2]=new Thread(instance);
                manager[2].start();
            }
            else
            {
                quick_dual_pivot(numbers,right+1,index_right);
            }
            try
            {
                for(int i=0;i<3;i++)
                {
                    if(manager[i]!=null)
                    {
                        manager[i].join();
                    }
                }
            }
            catch(InterruptedException e)
            {
                e.printStackTrace();
            }
		}
	}
    public void run()
    {
        concurrent_quick_dual_pivot(numbers,index_left,index_right);
    }
}
/**
<p>并发排序类。</p><br>
<p style="color:#FF0000;">此类功能可能不稳定，若非学习、研究和极端情况，请使用<code>collection.sort</code>类进行排序。</p><br>
用于对数组进行排序。<br>
使用并发操作提高效率。
*/
public class concurrent_sort
{
	/**
	<p>此方法会修改输入的数据。</p><br>
	并发双轴快速排序。
	@param numbers 整型数组。
	*/
	public static void concurrent_quick_dual_pivot(int numbers[])
	{
        concurrent_quick_dual_pivot_sort instance=new concurrent_quick_dual_pivot_sort(numbers,0,numbers.length-1);
        Thread manager=new Thread(instance);
        manager.start();
        try
        {
            manager.join();
        }
        catch(InterruptedException e)
        {
            e.printStackTrace();
        }
	}
}