package tools.mathematics;
/**
<p>矩阵类。</p><br>
矩阵是一个按照长方阵列排列的复数或实数集合。<br>
本矩阵以二维数组实现，仅支持整数元素。
*/
public class matrix
{
	public int row;
	public int column;
	public int elements[][];
	public int coefficient=1;
	/**
	通过行、列数和元素构造矩阵。
	@param row_number 矩阵的行数。
	@param column_number 矩阵的列数。
	@param element_numbers 矩阵的元素(行优先)。
	*/
	public matrix(int row_number,int column_number,int... element_numbers)
	{
		if(row_number<=0&&column_number>0)
		{
			row_number=(int)((element_numbers.length-1)/column_number)+1;
		}
		else if(column_number<=0&&row_number>0)
		{
			column_number=(int)((element_numbers.length-1)/row_number)+1;
		}
		else if(row_number<=0&&column_number<=0)
		{
			row_number=(int)Math.sqrt(element_numbers.length-1)+1;
			column_number=row_number;
		}
		row=row_number;
		column=column_number;
		elements=new int[row_number][column_number];
		for(int i=0;i<element_numbers.length;i++)
		{
			elements[i/column_number][i%column_number]=element_numbers[i];
		}
	}
	/**
	通过元素数组构造矩阵。
	@param element_numbers 元素数组(行优先)。
	*/
	public matrix(int element_numbers[][])
	{
		row=element_numbers.length;
		column=element_numbers[0].length;
		elements=element_numbers.clone();
	}
	/**
	通过行、列数构造一个零矩阵。
	@param row_number 矩阵的行数。
	@param column_number 矩阵的列数。
	*/
	public matrix(int row_number,int column_number)
	{
		row=row_number;
		column=column_number;
		elements=new int[row_number][column_number];
	}
	/**
	通过行列式构造矩阵。
	@param mirror_determinant 要构造的矩阵的行列式。
	*/
	public matrix(determinant mirror_determinant)
	{
		row=mirror_determinant.order;
		column=mirror_determinant.order;
		elements=mirror_determinant.elements.clone();
	}
	/**
	获取矩阵中指定位置的元素。
	@param target_row 要获取的元素所在的行。
	@param target_column 要获取的元素所在的列。
	@return 如果指定位置在矩阵范围内，则返回该位置的元素。<br>
	否则返回Integer.MIN_VALUE。
	*/
	public int get_element(int target_row,int target_column)
	{
		if(target_row>=1&&target_row<=row&&target_column>=1&&target_column<=column)
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
	交换矩阵中的两行。
	@param row1 要交换的第一行。
	@param row2 要交换的第二行。
	@return 如果交换成功则返回矩阵系数。<br>
	否则返回Integer.MIN_VALUE。
	*/
	public int row_exchange(int row1,int row2)
	{
		if(row1!=row2&&row1>0&&row2>0&&row1<=row&&row2<=row)
		{
			int temp;
			for(int i=0;i<column;i++)
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
	将矩阵中的一行乘以一个非零数。
	@param target_row 要操作的行。
	@param k 要乘以的非零数。
	@return 如果操作成功则返回该行的第一个元素。<br>
	否则返回Integer.MIN_VALUE。
	*/
	public int row_multiply(int target_row,int k)
	{
		if(target_row>0&&target_row<=row)
		{
			for(int i=0;i<column;i++)
			{
				elements[target_row-1][i]*=k;
			}
			return elements[target_row-1][0];
		}
		else
		{
			return Integer.MIN_VALUE;
		}
	}
	/**
	<p>此方法会修改调用对象。</p><br>
	将矩阵中的一行加上另一行的倍数。<br>
	将source_row行的每个元素乘以k，加至target_row行的对应元素。
	@param source_row 要加上的行。
	@param target_row 要操作的行。
	@param k 要乘以的倍数。
	@return 如果操作成功则返回该行的第一个元素。<br>
	否则返回Integer.MIN_VALUE。
	*/
	public int row_add_to(int source_row,int target_row,int k)
	{
		if(source_row!=target_row&&source_row>0&&target_row>0&&source_row<=row&&target_row<=row)
		{
			for(int i=0;i<column;i++)
			{
				elements[target_row-1][i]+=elements[source_row-1][i]*k;
			}
			return elements[target_row-1][0];
		}
		else
		{
			return Integer.MIN_VALUE;
		}
	}
	/**
	<p>此方法会修改调用对象。</p><br>
	交换矩阵中的两列。
	@param column1 要交换的第一列。
	@param column2 要交换的第二列。
	@return 如果交换成功则返回矩阵系数。<br>
	否则返回Integer.MIN_VALUE。
	*/
	public int column_exchange(int column1,int column2)
	{
		if(column1!=column2&&column1>0&&column2>0&&column1<=column&&column2<=column)
		{
			int temp;
			for(int i=0;i<row;i++)
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
	将矩阵中的一列乘以一个非零数。
	@param target_column 要操作的列。
	@param k 要乘以的非零数。
	@return 如果操作成功则返回该列的第一个元素。<br>
	否则返回Integer.MIN_VALUE。
	*/
	public int column_multiply(int target_column,int k)
	{
		if(target_column>0&&target_column<=column)
		{
			for(int i=0;i<row;i++)
			{
				elements[i][target_column-1]*=k;
			}
			return elements[0][target_column-1];
		}
		else
		{
			return Integer.MIN_VALUE;
		}
	}
	/**
	<p>此方法会修改调用对象。</p><br>
	将矩阵中的一列加上另一列的倍数。<br>
	将source_column列的每个元素乘以k，加至target_column列的对应元素。
	@param source_column 要加上的列。
	@param target_column 要操作的列。
	@param k 要乘以的倍数。
	@return 如果操作成功则返回该列的第一个元素。<br>
	否则返回Integer.MIN_VALUE。
	*/
	public int column_add_to(int source_column,int target_column,int k)
	{
		if(source_column!=target_column&&source_column>0&&target_column>0&&source_column<=column&&target_column<=column)
		{
			for(int i=0;i<row;i++)
			{
				elements[target_column-1][i]+=elements[source_column-1][i]*k;
			}
			return elements[target_column-1][0];
		}
		else
		{
			return Integer.MIN_VALUE;
		}
	}
	/**
	计算矩阵的转置矩阵。
	@param reversing_matrix 要计算转置矩阵的矩阵。
	@return 转置后的矩阵。
	*/
	public static matrix reverse(matrix reversing_matrix)
	{
		matrix result=new matrix(reversing_matrix.column,reversing_matrix.row);
		for(int i=0;i<reversing_matrix.row;i++)
		{
			for(int j=0;j<reversing_matrix.column;j++)
			{
				result.elements[j][i]=reversing_matrix.elements[i][j];
			}
		}
		return result;
	}
	/**
	计算矩阵的余子式。
	@param target_matrix 要计算余子式的矩阵。
	@param base_row 基准行。
	@param base_column 基准列。
	@return 余子式矩阵。<br>
	若行号或列号超出范围则不进行计算，返回目标矩阵对象。
	*/
	public static matrix cofactor(matrix target_matrix,int base_row,int base_column)
	{
		if(base_row>=1&&base_row<=target_matrix.row&&base_column>=1&&base_column<=target_matrix.column)
		{
			matrix result=new matrix(target_matrix.row-1,target_matrix.column-1);
			int origin_row=0;
			int origin_column=0;
			for(int i=0;i<result.row;i++)
			{
				origin_column=0;
				for(int j=0;j<result.column;j++)
				{
					if(origin_row==base_row-1)
					{
						origin_row++;
					}
					if(origin_column==base_column-1)
					{
						origin_column++;
					}
					result.elements[i][j]=target_matrix.elements[origin_row][origin_column++];
				}
				origin_row++;
			}
			return result;
		}
		else
		{
			return target_matrix;
		}
	}
	/**
	计算矩阵的代数余子式。
	@param target_matrix 要计算代数余子式的矩阵。
	@param base_row 基准行。
	@param base_column 基准列。
	@return 代数余子式矩阵。<br>
	若行号或列号超出范围则不进行计算，返回目标矩阵对象。
	*/
	public static matrix cofactor_algebraic(matrix target_matrix,int base_row,int base_column)
	{
		matrix result=cofactor(target_matrix,base_row,base_column);
		if(result.row<target_matrix.row)
		{
			result.coefficient=(int)Math.pow(-1,base_row+base_column);
			return result;
		}
		else
		{
			return target_matrix;
		}
	}
	/**
	计算矩阵的余子式的系数。
	@param target_matrix 要计算余子式系数的矩阵。
	@param base_row 基准行。
	@param base_column 基准列。
	@return 带有系数的余子式矩阵。<br>
	若行号或列号超出范围则不进行计算，返回目标矩阵对象。
	*/
	public static matrix cofactor_coefficient(matrix target_matrix,int base_row,int base_column)
	{
		matrix result=cofactor(target_matrix,base_row,base_column);
		if(result.row<target_matrix.row)
		{
			result.coefficient=(int)(target_matrix.coefficient*target_matrix.elements[base_row-1][base_column-1]*Math.pow(-1,base_row+base_column));
			return result;
		}
		else
		{
			return target_matrix;
		}
	}
	/**
	计算矩阵的伴随矩阵。
	@param target_matrix 要计算伴随矩阵的矩阵。
	@return 伴随矩阵。
	*/
	public static matrix adjugate(matrix target_matrix)
	{
		if(target_matrix.row==target_matrix.column)
		{
			matrix result=new matrix(target_matrix.row,target_matrix.row);
			determinant mirror=new determinant(target_matrix);
			for(int i=0;i<result.row;i++)
			{
				for(int j=0;j<result.column;j++)
				{
					result.elements[j][i]=(int)determinant.cofactor_algebraic(mirror,i+1,j+1).value();
				}
			}
			return result;
		}
		else
		{
			return target_matrix;
		}
	}
	/**
	计算本矩阵与指定矩阵的和。
	@param source 加数矩阵。
	@return 本矩阵与指定矩阵的和。
	*/
	public matrix add(matrix source)
	{
		if(row==source.row&&column==source.column)
		{
			matrix result=new matrix(row,column);
			for(int i=0;i<result.row;i++)
			{
				for(int j=0;j<result.column;j++)
				{
					result.elements[i][j]=elements[i][j]+source.elements[i][j];
				}
			}
			return result;
		}
		else
		{
			return new matrix(1,1);
		}
	}
	/**
	计算两个矩阵的和。
	@param source1 第一个矩阵。
	@param source2 第二个矩阵。
	@return 两个矩阵的和。
	*/
	public static matrix add(matrix source1,matrix source2)
	{
		if(source1.row==source2.row&&source1.column==source2.column)
		{
			matrix result=new matrix(source1.row,source1.column);
			for(int i=0;i<result.row;i++)
			{
				for(int j=0;j<result.column;j++)
				{
					result.elements[i][j]=source1.elements[i][j]+source2.elements[i][j];
				}
			}
			return result;
		}
		else
		{
			return new matrix(1,1);
		}
	}
	/**
	计算本矩阵与指定矩阵的差。
	@param subtrahend 减数矩阵。
	@return 本矩阵与指定矩阵的差。
	*/
	public matrix subtract(matrix subtrahend)
	{
		if(row==subtrahend.row&&column==subtrahend.column)
		{
			matrix result=new matrix(row,column);
			for(int i=0;i<result.row;i++)
			{
				for(int j=0;j<result.column;j++)
				{
					result.elements[i][j]=elements[i][j]-subtrahend.elements[i][j];
				}
			}
			return result;
		}
		else
		{
			return new matrix(1,1);
		}
	}
	/**
	计算两个矩阵的差。
	@param minuend 被减数矩阵。
	@param subtrahend 减数矩阵。
	@return 两个矩阵的差。
	*/
	public static matrix subtract(matrix minuend,matrix subtrahend)
	{
		if(minuend.row==subtrahend.row&&minuend.column==subtrahend.column)
		{
			matrix result=new matrix(minuend.row,minuend.column);
			for(int i=0;i<result.row;i++)
			{
				for(int j=0;j<result.column;j++)
				{
					result.elements[i][j]=minuend.elements[i][j]-subtrahend.elements[i][j];
				}
			}
			return result;
		}
		else
		{
			return new matrix(1,1);
		}
	}
	/**
	<p>此方法会修改调用对象。</p><br>
	计算矩阵的数乘。
	@param coefficient 系数。
	@return 数乘后的矩阵系数。
	*/
	public int multiply_scalar(int coefficient)
	{
		for(int i=0;i<row;i++)
		{
			for(int j=0;j<column;j++)
			{
				elements[i][j]*=coefficient;
			}
		}
		return this.coefficient*coefficient;
	}
	/**
	计算矩阵的数乘。
	@param coefficient 系数。
	@param factor 要数乘的矩阵。
	@return 数乘矩阵。
	*/
	public static matrix multiply_scalar(int coefficient,matrix factor)
	{
		matrix result=new matrix(factor.row,factor.column);
		for(int i=0;i<result.row;i++)
		{
			for(int j=0;j<result.column;j++)
			{
				result.elements[i][j]=factor.elements[i][j]*coefficient;
			}
		}
		return result;
	}
	/**
	<p>此方法会修改调用对象。</p><br>
	计算两个矩阵的乘积。
	@param factor 右矩阵数组。
	@return 当矩阵乘法合法，即左矩阵列数=右矩阵行数时返回this<br>
	否则返回一个1*1的矩阵。
	*/
	public matrix multiply(int factor[][])
	{
		if(column==factor.length)
		{
			int result[][]=new int[row][factor[0].length];
			int temp;
			for(int i=0;i<row;i++)
			{
				for(int j=0;j<factor[0].length;j++)
				{
					temp=0;
					for(int k=0;k<column;k++)
					{
						temp+=elements[i][k]*factor[k][j];
					}
					result[i][j]=temp;
				}
			}
			elements=result;
			column=factor[0].length;
			return this;
		}
		else
		{
			return new matrix(1,1);
		}
	}
	/**
	计算两个二维数组的矩阵乘积。
	@param factor_left 左矩阵数组。
	@param factor_right 右矩阵数组。
	@return 当矩阵乘法合法，即左矩阵列数=右矩阵行数时返回矩阵乘积的二维数组形式<br>
	否则返回null。
	*/
	public static int[][] multiply(int factor_left[][],int factor_right[][])
	{
		if(factor_left[0].length==factor_right.length)
		{
			int result[][]=new int[factor_left.length][factor_right[0].length];
			int temp;
			for(int i=0;i<result.length;i++)
			{
				for(int j=0;j<result[0].length;j++)
				{
					temp=0;
					for(int k=0;k<factor_left[0].length;k++)
					{
						temp+=factor_left[i][k]*factor_right[k][j];
					}
					result[i][j]=temp;
				}
			}
			return result;
		}
		else
		{
			return null;
		}
	}
	/**
	<p>此方法会修改调用对象。</p><br>
	右乘矩阵。
	@param factor 右矩阵。
	@return 当矩阵乘法合法，即左矩阵列数=右矩阵行数时返回this<br>
	否则返回一个1*1的矩阵。
	*/
	public matrix multiply(matrix factor)
	{
		if(column==factor.row)
		{
			int result[][]=new int[row][factor.column];
			int temp;
			for(int i=0;i<row;i++)
			{
				for(int j=0;j<factor.column;j++)
				{
					temp=0;
					for(int k=0;k<column;k++)
					{
						temp+=elements[i][k]*factor.elements[k][j];
					}
					result[i][j]=temp;
				}
			}
			elements=result;
			column=factor.column;
			return this;
		}
		else
		{
			return new matrix(1,1);
		}
	}
	/**
	计算两个矩阵的乘积。
	@param factor_left 左矩阵。
	@param factor_right 右矩阵。
	@return 当矩阵乘法合法，即左矩阵列数=右矩阵行数时返回乘积矩阵<br>
	否则返回一个1*1的矩阵。
	*/
	public static matrix multiply(matrix factor_left,matrix factor_right)
	{
		if(factor_left.column==factor_right.row)
		{
			matrix result=new matrix(factor_left.row,factor_right.column);
			int temp;
			for(int i=0;i<result.row;i++)
			{
				for(int j=0;j<result.column;j++)
				{
					temp=0;
					for(int k=0;k<factor_left.column;k++)
					{
						temp+=factor_left.elements[i][k]*factor_right.elements[k][j];
					}
					result.elements[i][j]=temp;
				}
			}
			return result;
		}
		else
		{
			return new matrix(1,1);
		}
	}
	/**
	计算矩阵的幂。
	@param power 幂次。
	@return 矩阵的幂。
	*/
    public matrix power(int power)
    {
		if(row==column)
		{
			int[][] result=new int[row][column];
			for(int i=0;i<row;i++)
			{
				result[i][i]=1;
			}
			while(power>0)
			{
				if(power%2==0)
				{
					elements=multiply(elements,elements);
					power>>=1;
				}
				else
				{
					result=multiply(result,elements);
					power--;
				}
			}
			elements=result;
			return new matrix(result);
		}
		else
		{
			return new matrix(1,1);
		}
    }
	/**
	计算矩阵的幂。
	@param matrix 要计算幂的矩阵。
	@param power 幂次。
	@return 矩阵的幂。
	*/
    public static matrix power(matrix matrix,int power)
    {
		if(matrix.row==matrix.column)
		{
			int[][] result=new int[matrix.row][matrix.column];
			for(int i=0;i<matrix.row;i++)
			{
				result[i][i]=1;
			}
			while(power>0)
			{
				if(power%2==0)
				{
					matrix=multiply(matrix,matrix);
					power>>=1;
				}
				else
				{
					result=multiply(result,matrix.elements);
					power--;
				}
			}
			return new matrix(result);
		}
		else
		{
			return new matrix(1,1);
		}
    }
	/**
	化简矩阵。
	@return 化简后的系数。
	*/
	public int simplify()
	{
		int gcd=1;
		int elements_array[]=new int[row*column];
		for(int i=0;i<row;i++)
		{
			for(int j=0;j<column;j++)
			{
				elements_array[j+i*row]=elements[i][j];
			}
		}
		gcd=maths.gcd(elements_array);
		if(gcd>1)
		{
			for(int i=0;i<row;i++)
			{
				for(int j=0;j<column;j++)
				{
					elements[i][j]/=gcd;
				}
			}
		}
		coefficient*=gcd;
		return coefficient;
	}
	/**
	<p>此方法会修改调用对象。</p><br>
	提取矩阵的系数。
	@return 矩阵的系数。
	*/
	public int extract_coefficient()
	{
		int result=coefficient;
		coefficient=1;
		return result;
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
		for(int i=0;i<row;i++)
		{
			result+="\t[";
			for(int j=0;j<column;j++)
			{
				result+=""+elements[i][j]+"\t";
			}
			result+="]\n";
		}
		return result;
	}
}