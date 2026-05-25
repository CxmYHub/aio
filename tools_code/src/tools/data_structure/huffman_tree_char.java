package tools.data_structure;
/**
<p>霍夫曼树类(字符型)</p><br>
霍夫曼树用于压缩数据。<br>
本树用于字符型数据的压缩。
*/
public class huffman_tree_char
{
    public char characters[];
    public int weight[];
    public int left[];
    public int right[];
    public boolean code[][];
	public boolean code_of_character[][];
	private void quick_sort_dual_pivot_for_huffman_tree()
	{
		int indexs[]=new int[weight.length*2+2];
		indexs[0]=0;
		indexs[1]=weight.length-1;
		int pin=2;
		while(pin>1)
		{
			int index_right=indexs[--pin];
			int index_left=indexs[--pin];
			if(index_left<index_right)
			{
				int length=index_right-index_left+1;
				char temp_character;
				int temp_weight;
				if(length<5&&weight[index_left]>weight[index_right])
				{
					temp_weight=weight[index_left];
					weight[index_left]=weight[index_right];
					weight[index_right]=temp_weight;
					temp_character=characters[index_left];
					characters[index_left]=characters[index_right];
					characters[index_right]=temp_character;
				}
				int pivot1=weight[index_left];
				int pivot2=weight[index_right];
				if(length>=5)
				{
					int fifth[]={index_left,index_left+(length>>2),index_left+(length>>1),index_right-(length>>2),index_right};
					int a=weight[fifth[0]],b=weight[fifth[1]],c=weight[fifth[2]],d=weight[fifth[3]],e=weight[fifth[4]];
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
						if(pivot1==weight[fifth[pivot_index]])
						{
							weight[fifth[pivot_index]]=weight[index_left];
							weight[index_left]=pivot1;
							temp_character=characters[fifth[pivot_index]];
							characters[fifth[pivot_index]]=characters[index_left];
							characters[index_left]=temp_character;
							break;
						}
					}
					for(int pivot_index=4;pivot_index>=0;pivot_index--)
					{
						if(pivot2==weight[fifth[pivot_index]])
						{
							weight[fifth[pivot_index]]=weight[index_right];
							weight[index_right]=pivot2;
							temp_character=characters[fifth[pivot_index]];
							characters[fifth[pivot_index]]=characters[index_right];
							characters[index_right]=temp_character;
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
					if(weight[k]<pivot1)
					{
						temp_weight=weight[++left];
						weight[left]=weight[k];
						weight[k]=temp_weight;
						temp_character=characters[left];
						characters[left]=characters[k];
						characters[k]=temp_character;
						k++;
					}
					else if(weight[k]<=pivot2)
					{
						k++;
					}
					else
					{
						back=false;
						while(weight[--right]>pivot2)
						{
							if(k>=right)
							{
								back=true;
								break;
							}
						}
						if(!back)
						{
							if(weight[right]<pivot1)
							{
								temp_weight=weight[right];
								weight[right]=weight[k];
								weight[k]=weight[++left];
								weight[left]=temp_weight;
								temp_character=characters[right];
								characters[right]=characters[k];
								characters[k]=characters[left];
								characters[left]=temp_character;
							}
							else
							{
								temp_weight=weight[right];
								weight[right]=weight[k];
								weight[k]=temp_weight;
								temp_character=characters[right];
								characters[right]=characters[k];
								characters[k]=temp_character;
							}
							k++;
						}
					}
				}
				temp_weight=weight[index_left];
				weight[index_left]=weight[left];
				weight[left]=temp_weight;
				temp_character=characters[index_left];
				characters[index_left]=characters[left];
				characters[left]=temp_character;
				temp_weight=weight[index_right];
				weight[index_right]=weight[right];
				weight[right]=temp_weight;
				temp_character=characters[index_right];
				characters[index_right]=characters[right];
				characters[right]=temp_character;
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
	通过字符串构建霍夫曼树。
	@param text 字符串。
	*/
    public huffman_tree_char(String text)
    {
		if(text.length()==0)
		{
			characters=new char[0];
			weight=new int[0];
			left=new int[0];
			right=new int[0];
			code=new boolean[0][0];
			code_of_character=new boolean[0][0];
			return;
		}
        weight=new int[65536];
        int length=text.length();
        int character_count=0;
        for(int i=0;i<length;i++)
        {
            if(weight[text.charAt(i)]++==0)
            {
                character_count++;
            }
        }
        if(character_count==1)
        {
            characters=new char[1];
            characters[0]=text.charAt(0);
            weight=new int[1];
            weight[0]=length;
            left=new int[1];
            left[0]=-1;
            right=new int[1];
            right[0]=-1;
            code=new boolean[1][1];
            code[0][0]=false;
            code_of_character=new boolean[65536][1];
            code_of_character[text.charAt(0)][0]=false;
            return;
        }
        characters=new char[character_count];
        int node_count=character_count*2+1;
        int counts[]=new int[node_count];
        int pin_c=0;
        for(int i=0;i<65536;i++)
        {
            if(weight[i]!=0)
            {
                characters[pin_c]=(char)i;
                counts[pin_c]=weight[i];
                pin_c++;
            }
        }
        weight=counts;
        quick_sort_dual_pivot_for_huffman_tree();
        left=new int[node_count];
        right=new int[node_count];
        weight[character_count]=weight[0]+weight[1];
        left[character_count]=0;
        right[character_count]=1;
        int leaf_index=2;
        int node_index=character_count;
        int pin=character_count;
        while(weight[pin]<length)
        {
            if(leaf_index<character_count-1&&weight[leaf_index+1]<=weight[node_index])
            {
                weight[++pin]=weight[leaf_index]+weight[leaf_index+1];
                left[pin]=leaf_index;
                right[pin]=leaf_index+1;
                leaf_index+=2;
            }
            else if(node_index<pin&&leaf_index<character_count&&weight[node_index+1]<=weight[leaf_index]||leaf_index>=character_count)
            {
                weight[++pin]=weight[node_index]+weight[node_index+1];
                left[pin]=node_index;
                right[pin]=node_index+1;
                node_index+=2;
            }
            else
            {
                weight[++pin]=weight[leaf_index]+weight[node_index];
                left[pin]=leaf_index;
                right[pin]=node_index;
                leaf_index++;
                node_index++;
            }
        }
        pin++;
        int new_weight[]=new int[pin];
        int new_left[]=new int[pin];
        int new_right[]=new int[pin];
        for(int i=0;i<pin;i++)
        {
            new_weight[i]=weight[i];
            new_left[i]=i<character_count?-1:left[i];
            new_right[i]=i<character_count?-1:right[i];
        }
        weight=new_weight;
        left=new_left;
        right=new_right;
        code=new boolean[pin][];
        int pins[]=new int[character_count+1];
        pins[0]=pin-1;
        for(int i=1;i<=character_count;i++)
        {
            pins[i]=-1;
        }
        int depth=0;
        code[pin-1]=new boolean[0];
		code_of_character=new boolean[65536][];
        while(depth>=0)
        {
            int now=pins[depth];
            if(left[now]!=-1&&left[now]!=pins[depth+1]&&right[now]!=pins[depth+1])
            {
                code[left[now]]=new boolean[depth+1];
                for(int i=0;i<depth;i++)
                {
                    code[left[now]][i]=code[now][i];
                }
                code[left[now]][depth]=false;
				if(left[now]<character_count)
				{
					code_of_character[characters[left[now]]]=code[left[now]];
				}
                pins[++depth]=left[now];
            }
            else if(right[now]!=-1&&right[now]!=pins[depth+1])
            {
                code[right[now]]=new boolean[depth+1];
                for(int i=0;i<depth;i++)
                {
                    code[right[now]][i]=code[now][i];
                }
                code[right[now]][depth]=true;
				if(right[now]<character_count)
				{
					code_of_character[characters[right[now]]]=code[right[now]];
				}
                pins[++depth]=right[now];
            }
            else
            {
                depth--;
            }
        }
    }
    /**
	获取指定字符的霍夫曼编码。
	@param character 字符。
	@return 字符的霍夫曼编码，若字符不存在于树中则返回null。
	*/
	public String get_code(char character)
	{
		if(code_of_character[character]==null)
		{
			return null;
		}
		StringBuilder result=new StringBuilder();
		for(boolean b:code_of_character[character])
		{
			result.append(b?"1":"0");
		}
		return result.toString();
	}
    /**
	获取所有字符的霍夫曼编码。
	@return 所有字符的霍夫曼编码，若字符不存在于树中则返回null。
	*/
    public String get_all_codes()
    {
        StringBuilder result=new StringBuilder();
        for(int i=0;i<characters.length;i++)
        {
            result.append(characters[i]+"\t");
            if(code[i]==null)
            {
                result.append("null\n");
                continue;
            }
            for(boolean b:code[i])
            {
                result.append(b?"1":"0");
            }
            result.append("\n");
        }
        return result.toString();
    }
	/**
	压缩字符串数据。
	@param text 字符串。
	@return 压缩后的字符串。
	*/
	public String encode(String text)
	{
		int length=text.length();
		StringBuilder result=new StringBuilder();
		for(int i=0;i<length;i++)
		{
			for(boolean b:code_of_character[text.charAt(i)])
			{
				result.append(b?"1":"0");
			}
		}
		return result.toString();
	}
    /**
	通过霍夫曼编码解压缩数据。
	@param code 霍夫曼编码字符串。
	@return 解压缩后的字符串。
	*/
	public String decode(String code)
	{
		int length=code.length();
		if(characters.length==1)
		{
			char character=characters[0];
			return String.valueOf(character).repeat(length);
		}
		int root=weight.length-1;
		int now=root;
		StringBuilder result=new StringBuilder();
		for(int i=0;i<length;i++)
		{
			char now_bit=code.charAt(i);
			if(now_bit=='0')
			{
				now=left[now];
			}
			else
			{
				now=right[now];
			}
			if(left[now]==-1)
			{
				result.append(characters[now]);
				now=root;
			}
		}
		return result.toString();
	}
}