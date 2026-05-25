package tools.mathematics;
/**
<p>平方根类。</p><br>
用于表示平方根及对平方根的操作。
*/
public class square_root implements Comparable<square_root>
{
	public int coefficient;
	public int base;
	/**
	构造一个平方根对象。
	@param number 平方根的底数。
	*/
	public square_root(int number)
	{
		coefficient=1;
		for(int i=2;i*i<=number;i++)
		{
			if(number%(i*i)==0)
			{
				coefficient*=i;
				number/=i*i;
				i--;
			}
		}
		base=number;
	}
	/**
	计算一个整数的算数平方根的系数及底数。
	@param number 算数平方根的底数。
	@return 一个包含系数及底数的整型数组<br>
	第一个元素为系数，第二个元素为底数。
	*/
	public static int[] sqrt(int number)
	{
		int sqrt[]={1,0};
		for(int i=2;i*i<=number;i++)
		{
			if(number%(i*i)==0)
			{
				sqrt[0]*=i;
				number/=i*i;
				i--;
			}
		}
		sqrt[1]=number;
		return sqrt;
	}
	/**
	计算一个整数的算术平方根。
	@return 算数平方根的字符串表示。
	*/
	public static String string(int number)
	{
		int coefficient=1;
		for(int i=2;i*i<=number;i++)
		{
			if(number%(i*i)==0)
			{
				coefficient*=i;
				number/=i*i;
				i--;
			}
		}
		int base=number;
		if(base==1)
		{
			return ""+coefficient;
		}
		else if(coefficient==1)
		{
			return "√"+base;
		}
		else
		{
			return ""+coefficient+"√"+base;
		}
	}
	/**
	计算算术平方根的小数形式。
	@return 算术平方根的小数形式。
	*/
	public double value()
	{
		return coefficient*Math.sqrt(base);
	}
	public String toString()
	{
		if(base==1)
		{
			return ""+coefficient;
		}
		else if(coefficient==1)
		{
			return "√"+base;
		}
		else
		{
			return ""+coefficient+"√"+base;
		}
	}
	public int compareTo(square_root comparing)
	{
		if(coefficient==comparing.coefficient&&base==comparing.base)
		{
			return 0;
		}
		else
		{
			int square_result=coefficient*coefficient*base*base-comparing.coefficient*comparing.coefficient*comparing.base*comparing.base;
			if(square_result>0)
			{
				return 1;
			}
			else
			{
				return -1;
			}
		}
	}
}