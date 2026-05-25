package tools.mathematics;
/**
<p>行列式类。</p><br>
行列式是一个数字方阵，其本质是一个数，用于计算矩阵的行列式值。<br>
本行列式以二维数组实现，仅支持整数元素。
*/
public class determinant implements Comparable<determinant>
{
	public int order;
	public int elements[][];
	public int coefficient=1;
	/**
    构造一个行列式对象，包含指定元素。
    @param order_number 行列式的阶数。
    @param element_numbers 行列式的元素。
    */
	public determinant(int order_number,int... element_numbers)
	{
		if(order_number<=0)
		{
			order_number=(int)Math.sqrt(element_numbers.length-1)+1;
		}
		order=order_number;
		elements=new int[order_number][order_number];
		for(int i=0;i<element_numbers.length;i++)
		{
			elements[i/order_number][i%order_number]=element_numbers[i];
		}
	}
	/**
	构造一个阶数为order_number的零矩阵的行列式对象。
	*/
	public determinant(int order_number)
	{
		order=order_number;
		elements=new int[order_number][order_number];
	}
	/**
	复制一个行列式对象。
	@param mirror_determinant 要复制的行列式对象。
	*/
	public determinant(determinant mirror_determinant)
	{
		order=mirror_determinant.order;
		elements=mirror_determinant.elements.clone();
	}
	/**
	通过矩阵构造该矩阵的行列式。
	@param square_matrix 矩阵对象。
	*/
	public determinant(matrix square_matrix)
	{
		if(square_matrix.row==square_matrix.column)
		{
			order=square_matrix.row;
			elements=new int[order][order];
			for(int i=0;i<order;i++)
			{
				for(int j=0;j<order;j++)
				{
					elements[i][j]=square_matrix.elements[i][j];
				}
			}
		}
		else
		{
			order=1;
			elements=new int[1][1];
		}
	}
	/**
	获取行列式的指定元素。
	@param target_row 要获取的元素的行号。
	@param target_column 要获取的元素的列号。
	@return 指定元素的值。<br>
	若行号或列号超出范围则返回Integer.MIN_VALUE。
	*/
	public int get_element(int target_row,int target_column)
	{
		if(target_row>=1&&target_row<=order&&target_column>=1&&target_column<=order)
		{
			return elements[target_row-1][target_column-1];
		}
		else
		{
			return Integer.MIN_VALUE;
		}
	}
	/**
	<p>此方法会修改调用对象。</p><br>
	交换行列式的两行。
	@param row1 要交换的第一行号。
	@param row2 要交换的第二行号。
	@return 行列式的系数。<br>
	若行号超出范围则返回Integer.MIN_VALUE。
	*/
	public int exchange_row(int row1,int row2)
	{
		if(row1!=row2&&row1>0&&row2>0&&row1<=order&&row2<=order)
		{
			int temp;
			for(int i=0;i<order;i++)
			{
				temp=elements[row1-1][i];
				elements[row1-1][i]=elements[row2-1][i];
				elements[row2-1][i]=temp;
			}
			coefficient*=-1;
			return coefficient;
		}
		else
		{
			return Integer.MIN_VALUE;
		}
	}
	/**
	<p>此方法会修改调用对象。</p><br>
	交换行列式的两列。
	@param column1 要交换的第一列号。
	@param column2 要交换的第二列号。
	@return 行列式的系数。<br>
	若列号超出范围则返回Integer.MIN_VALUE。
	*/
	public int exchange_column(int column1,int column2)
	{
		if(column1!=column2&&column1>0&&column2>0&&column1<=order&&column2<=order)
		{
			int temp;
			for(int i=0;i<order;i++)
			{
				temp=elements[i][column1-1];
				elements[i][column1-1]=elements[i][column2-1];
				elements[i][column2-1]=temp;
			}
			coefficient*=-1;
			return coefficient;
		}
		else
		{
			return Integer.MIN_VALUE;
		}
	}
	/**
	<p>此方法会修改调用对象。</p><br>
	转置行列式。
	@return this
	*/
	public determinant reverse()
	{
		int temp;
		for(int i=0;i<order;i++)
		{
			for(int j=0;j<i;j++)
			{
				temp=elements[j][i];
				elements[j][i]=elements[i][j];
				elements[i][j]=temp;
			}
		}
		return this;
	}
	/**
	计算行列式的转置行列式。
	@param reversing_determinant 要转置的行列式对象。
	@return 转置行列式对象。
	*/
	public static determinant reverse(determinant reversing_determinant)
	{
		determinant result=new determinant(reversing_determinant.order,reversing_determinant.order);
		for(int i=0;i<reversing_determinant.order;i++)
		{
			for(int j=0;j<reversing_determinant.order;j++)
			{
				result.elements[j][i]=reversing_determinant.elements[i][j];
			}
		}
		return result;
	}
	/**
	计算行列式的余子式。
	@param base_row 基准行。
	@param base_column 基准列。
	@return 余子式对象。<br>
	若行号或列号超出范围则不进行计算，返回this。
	*/
	public determinant cofactor(int base_row,int base_column)
	{
		if(base_row>=1&&base_row<=order&&base_column>=1&&base_column<=order)
		{
			determinant result=new determinant(order-1);
			int origin_row=0;
			int origin_column=0;
			for(int i=0;i<result.order;i++)
			{
				origin_column=0;
				for(int j=0;j<result.order;j++)
				{
					if(origin_row==base_row-1)
					{
						origin_row++;
					}
					if(origin_column==base_column-1)
					{
						origin_column++;
					}
					result.elements[i][j]=elements[origin_row][origin_column++];
				}
				origin_row++;
			}
			return result;
		}
		else
		{
			return this;
		}
	}
	/**
	计算行列式的余子式。
	@param target_determinant 要计算余子式的行列式对象。
	@param base_row 基准行。
	@param base_column 基准列。
	@return 余子式对象。<br>
	若行号或列号超出范围则不进行计算，返回目标行列式对象。
	*/
	public static determinant cofactor(determinant target_determinant,int base_row,int base_column)
	{
		if(base_row>=1&&base_row<=target_determinant.order&&base_column>=1&&base_column<=target_determinant.order)
		{
			determinant result=new determinant(target_determinant.order-1);
			int origin_row=0;
			int origin_column=0;
			for(int i=0;i<result.order;i++)
			{
				origin_column=0;
				for(int j=0;j<result.order;j++)
				{
					if(origin_row==base_row-1)
					{
						origin_row++;
					}
					if(origin_column==base_column-1)
					{
						origin_column++;
					}
					result.elements[i][j]=target_determinant.elements[origin_row][origin_column++];
				}
				origin_row++;
			}
			return result;
		}
		else
		{
			return target_determinant;
		}
	}
	/**
	计算行列式的代数余子式。
	@param base_row 基准行。
	@param base_column 基准列。
	@return 代数余子式对象。<br>
	若行号或列号超出范围则不进行计算，返回this。
	*/
	public determinant cofactor_algebraic(int base_row,int base_column)
	{
		determinant result=cofactor(base_row,base_column);
		if(result.order<order)
		{
			result.coefficient=(int)Math.pow(-1,base_row+base_column);
			return result;
		}
		else
		{
			return this;
		}
	}
	/**
	计算行列式的代数余子式。
	@param target_determinant 要计算代数余子式的行列式对象。
	@param base_row 基准行。
	@param base_column 基准列。
	@return 代数余子式对象。<br>
	若行号或列号超出范围则不进行计算，返回目标行列式对象。
	*/
	public static determinant cofactor_algebraic(determinant target_determinant,int base_row,int base_column)
	{
		determinant result=cofactor(target_determinant,base_row,base_column);
		if(result.order<target_determinant.order)
		{
			result.coefficient=(int)Math.pow(-1,base_row+base_column);
			return result;
		}
		else
		{
			return target_determinant;
		}
	}
	/**
	计算行列式的余子式的系数。
	@param base_row 基准行。
	@param base_column 基准列。
	@return 带有系数的余子式对象。<br>
	若行号或列号超出范围则不进行计算，返回this。
	*/
	public determinant cofactor_coefficient(int base_row,int base_column)
	{
		determinant result=cofactor(base_row,base_column);
		if(result.order<order)
		{
			result.coefficient=(int)(coefficient*elements[base_row-1][base_column-1]*Math.pow(-1,base_row+base_column));
			return result;
		}
		else
		{
			return this;
		}
	}
	/**
	计算行列式的余子式的系数。
	@param target_determinant 要计算余子式系数的行列式对象。
	@param base_row 基准行。
	@param base_column 基准列。
	@return 带有系数的余子式对象。<br>
	若行号或列号超出范围则不进行计算，返回目标行列式对象。
	*/
	public static determinant cofactor_coefficient(determinant target_determinant,int base_row,int base_column)
	{
		determinant result=cofactor(target_determinant,base_row,base_column);
		if(result.order<target_determinant.order)
		{
			result.coefficient=(int)(target_determinant.coefficient*target_determinant.elements[base_row-1][base_column-1]*Math.pow(-1,base_row+base_column));
			return result;
		}
		else
		{
			return target_determinant;
		}
	}
	/**
	<p>此方法会修改调用对象。</p><br>
	化简行列式。
	@return 化简后的系数。
	*/
	public int simplify()
	{
		int gcd=1;
		for(int i=0;i<order;i++)
		{
			gcd=maths.gcd(elements[i]);
			if(gcd>1)
			{
				for(int j=0;j<order;j++)
				{
					elements[i][j]/=gcd;
				}
			}
			coefficient*=gcd;
		}
		int column[]=new int[order];
		for(int j=0;j<order;j++)
		{
			for(int i=0;i<order;i++)
			{
				column[i]=elements[i][j];
			}
			gcd=maths.gcd(column);
			for(int i=0;i<order;i++)
			{
				elements[i][j]/=gcd;
			}
			coefficient*=gcd;
		}
		return coefficient;
	}
	/**
	<p>此方法会修改调用对象。</p><br>
	提取行列式的系数。
	@return 行列式的系数。
	*/
	public int extract_coefficient()
	{
		int result=coefficient;
		coefficient=1;
		return result;
	}
	/**
	计算行列式的值。
	@return 行列式的值。
	*/
	public double value()
	{
		double value=0;
		double this_value;
		int index_numbers[]=new int[order];
		int N;
		boolean up_needed=true,distinct_needed=true;
		for(int i=0;i<order;i++)
		{
			index_numbers[i]=i+1;
		}
		for(int i=0;i<math.A[order][order];i++)
		{
			this_value=1;
			N=0;
			for(int j=0;j<order;j++)
			{
				this_value*=elements[j][index_numbers[j]-1];
				for(int k=j+1;k<order;k++)
				{
					if(index_numbers[j]>index_numbers[k])
					{
						N++;
					}
				}
			}
			if(N%2==1)
			{
				value-=this_value;
			}
			else
			{
				value+=this_value;
			}
			index_numbers[order-1]++;
			up_needed=true;
			distinct_needed=true;
			do
			{
				if(distinct_needed)
				{
					for(int j=1;j<order;j++)
					{
						for(int k=0;k<j;k++)
						{
							if(index_numbers[j]==index_numbers[k])
							{
								index_numbers[j]++;
								if(index_numbers[j]>order)
								{
									up_needed=true;
								}
								k=-1;
							}
						}
					}
					distinct_needed=false;
				}
				if(up_needed)
				{
					for(int j=order-1;j>0;j--)
					{
						if(index_numbers[j]>order)
						{
							index_numbers[j-1]++;
							index_numbers[j]=1;
							distinct_needed=true;
						}
					}
					up_needed=false;
				}
			}
			while(up_needed||distinct_needed);
		}
		return coefficient*value;
	}
	public String toString()
	{
		String result="\n";
		if(coefficient==-1)
		{
			result+="-";
		}
		else if(coefficient>1||coefficient<-1)
		{
			result+=""+coefficient;
		}
		else if(coefficient==0)
		{
			return "0";
		}
		for(int i=0;i<order;i++)
		{
			result+="\t|";
			for(int j=0;j<order;j++)
			{
				result+=""+elements[i][j]+"\t";
			}
			result+="|\n";
		}
		return result;
	}
	public int compareTo(determinant comparing)
	{
		double result=value()-comparing.value();
		if(result>-0.0000001&&result<0.0000001)
		{
			return 0;
		}
		else if(result>0)
		{
			return 1;
		}
		else
		{
			return -1;
		}
	}
}