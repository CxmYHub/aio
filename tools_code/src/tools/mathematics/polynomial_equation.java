package tools.mathematics;
/**
<p>自然数次多项式方程类。</p><br>
自然数次多项式方程是指未知数只有一个，且每一项次数都是非负整数的多项式方程。<br>
本类可以解析形如"a_nx^n+a_(n-1)x^(n-1)+...+a_1x+a_0=0"的多项式方程。<br>
其中n是未知数的指数，为自然数。<br>
本类可以计算自然数次多项式方程的根，即满足方程的所有实数解。
*/
public class polynomial_equation
{
	public int times=0;
	public char unknown;
	public int coefficients[];
	/**
	通过字符串解析自然数次多项式方程。
	@param unknown_tag 未知数字母，例如'x'。
	@param equation_string 多项式方程的字符串表示，例如"2x^2+3x+1=0"。
	*/
	public polynomial_equation(char unknown_tag,String equation_string)
	{
		unknown=unknown_tag;
		int tag=0;
		boolean has_index=false;
		boolean has_unknown=false;
		int this_index=0;
		for(int i=0;i<equation_string.length();i++)
		{
			char ch=equation_string.charAt(i);
			if(ch==unknown_tag)
			{
				has_unknown=true;
			}
			else if(has_unknown&&ch=='^')
			{
				has_index=true;
			}
			if(has_index)
			{
				if(tag==0&&ch=='^')
				{
					tag=1;
				}
				else if(tag==1&&(ch>='0'&&ch<='9'))
				{
					this_index*=10;
					this_index+=ch-'0';
				}
				else if(tag==1)
				{
					if(times<this_index)
					{
						times=this_index;
					}
					this_index=0;
					tag=0;
				}
			}
			else if(has_unknown)
			{
				times=1;
			}
		}
		double double_coefficients[]=new double[times+1];
		char current_coefficient[]=new char[equation_string.length()];
		int no_passed_equal=1;
		int dot_index=-1;
		int max_decimal=0;
		tag=0;
		this_index=0;
		for(int i=0;i<equation_string.length();i++)
		{
			char ch=equation_string.charAt(i);
			if(ch>='0'&&ch<='9'||ch=='.')
			{
				if(tag>=0)
				{
					current_coefficient[tag++]=ch;
				}
				else if(tag==-3)
				{
					this_index*=10;
					this_index+=ch-'0';
				}
				if(ch=='.')
				{
					dot_index=i;
				}
			}
			else if(ch=='+'||ch=='-')
			{
				if(tag==-2)
				{
					this_index=1;
				}
				if(i>0)
				{
					try
					{
						double_coefficients[this_index]+=no_passed_equal*(Double.parseDouble(new String(current_coefficient)));
					}
					catch(NumberFormatException ex)
					{
						if(current_coefficient[0]=='-')
						{
							double_coefficients[this_index]+=-1*no_passed_equal;
						}
						else
						{
							double_coefficients[this_index]+=no_passed_equal;
						}
					}
				}
				if(dot_index!=-1&&i-dot_index-1>max_decimal)
				{
					max_decimal=i-dot_index-1;
					dot_index=-1;
				}
				current_coefficient[0]=ch;
				for(int j=1;j<current_coefficient.length;j++)
				{
					current_coefficient[j]=0;
				}
				this_index=0;
				tag=1;
			}
			else if(ch=='=')
			{
				if(tag==-2)
				{
					this_index=1;
				}
				if(i>0)
				{
					double_coefficients[this_index]+=Double.parseDouble(new String(current_coefficient));
				}
				if(dot_index!=-1&&i-dot_index-1>max_decimal)
				{
					max_decimal=i-dot_index-1;
					dot_index=-1;
				}
				for(int j=0;j<current_coefficient.length;j++)
				{
					current_coefficient[j]=0;
				}
				no_passed_equal=-1;
				this_index=0;
				tag=0;
			}
			else if(ch==unknown_tag)
			{
				tag=-2;
				if(dot_index!=-1&&i-dot_index-1>max_decimal)
				{
					max_decimal=i-dot_index-1;
					dot_index=-1;
				}
			}
			else if(ch=='^')
			{
				tag=-3;
			}
		}
		if(dot_index!=-1&&equation_string.length()-dot_index-1>max_decimal)
		{
			max_decimal=equation_string.length()-dot_index-1;
		}
		if(tag==-2)
		{
			this_index=1;
		}
		try
		{
			double_coefficients[this_index]+=no_passed_equal*(Double.parseDouble(new String(current_coefficient)));
		}
		catch(NumberFormatException ex)
		{
			if(current_coefficient[0]=='-')
			{
				double_coefficients[this_index]+=-1*no_passed_equal;
			}
			else
			{
				double_coefficients[this_index]+=no_passed_equal;
			}
		}
		for(int i=double_coefficients.length-1;i>=0;i--)
		{
			if((double_coefficients[i]>0.000001||double_coefficients[i]<-0.000001))
			{
				times=i;
				break;
			}
		}
		if(max_decimal>0)
		{
			for(int i=times;i>=0;i--)
			{
				double_coefficients[i]*=Math.pow(10,max_decimal);
			}
		}
		coefficients=new int[times+1];
		for(int i=0;i<=times;i++)
		{
			coefficients[i]=(int)double_coefficients[i];
		}
		this.simplify();
	}
	/**
	<p>此方法会修改调用对象。</p><br>
	化简多项式方程。
	@return 化简系数的最大公因数。
	*/
	public int simplify()
	{
		int positive_coefficient[]=coefficients.clone();
		for(int i=0;i<positive_coefficient.length;i++)
		{
			if(positive_coefficient[i]<0)
			{
				positive_coefficient[i]*=-1;
			}
		}
		int gcd_value=maths.gcd(positive_coefficient);
		if(gcd_value>1)
		{
			for(int i=0;i<coefficients.length;i++)
			{
				coefficients[i]/=gcd_value;
			}
		}
		return gcd_value;
	}
	/**
	求解多项式方程。<br>
	除返回多项式方程的根数组外，还会通过标准输出打印原方程的解集。
	@return 多项式方程的根数组，如果方程无解则返回null。
	*/
	public double[] solve()
	{
		switch(times)
		{
			case 0:
			{
				System.out.println("0次方程无法求解。");
				return null;
			}
			case 1:
			{
				double k=coefficients[1];
				double b=coefficients[0];
				System.out.println("x="+(b/k));
				return new double[]{b/k};
			}
			case 2:
			{
				double a=coefficients[2];
				double b=coefficients[1];
				double c=coefficients[0];
				if(a<0)
				{
					a*=-1;
					b*=-1;
					c*=-1;
				}
				double x[]=new double[2];
				double delta=b*b-4*a*c;
				double a2=a*2;
				square_root sqrt_delta=null;
				int division=1;
				if(delta>0)
				{
					sqrt_delta=new square_root((int)delta);
				}
				System.out.println("Δ="+delta);
				if(delta>=0)
				{
					double x1_plus_x2=-b/a;
					double x1_mutiply_x2=c/a;
					double x1x1_plus_x2x2=b*b/a/a-2*c/a;
					if(delta>0)
					{
						x[0]=((-b+Math.sqrt(delta))/a2);
						x[1]=((-b-Math.sqrt(delta))/a2);
						division=maths.gcd((int)Math.abs(a2),(int)Math.abs(b),Math.abs(sqrt_delta.coefficient));
						a2/=division;
						b/=division;
						sqrt_delta.coefficient/=division;
						if(a2==1)
						{
							System.out.println("此方程有两个实数解。\nx1 = "+(-b)+"+"+sqrt_delta+"\nx2 = "+(-b)+"-"+sqrt_delta);
						}
						else
						{
							System.out.println("此方程有两个实数解。\nx1 = ("+(-b)+"+"+sqrt_delta+")/"+a2+"\nx2 = ("+(-b)+"-"+sqrt_delta+")/"+a2);
						}
						System.out.println("或\nx1 = "+x[0]+"\nx2 = "+x[1]);
						System.out.println("此外，\nx1+x2 = "+x1_plus_x2+"\n x1x2 = "+x1_mutiply_x2+"\nx1^2+x2^2 = "+x1x1_plus_x2x2);
						return new double[]{x[0],x[1]};
					}
					else
					{
						x[0]=(-b/a2);
						division=maths.gcd((int)Math.abs(a2),(int)Math.abs(b));
						a2/=division;
						b/=division;
						if(a2==1)
						{
							System.out.println("此方程有一个实数解。\nx1=x2 = "+(-b));
						}
						else
						{
							System.out.println("此方程有一个实数解。\nx1=x2 = "+(-b)+"/"+a2);
						}
						System.out.println("或\nx1=x2 = "+x[0]);
						System.out.println("此外，\nx1+x2 = "+x1_plus_x2+"\n x1x2 = "+x1_mutiply_x2+"\nx1^2+x2^2 = "+x1x1_plus_x2x2);
						return new double[]{x[0],x[0]};
					}
				}
				else
				{
					System.out.println("此方程无实数解。");
					return new double[]{0,0};
				}
			}
			case 3:
			{
				// double a=coefficients[3];
				// double b=coefficients[2];
				// double c=coefficients[1];
				// double d=coefficients[0];
				return new double[]{0,0,0};
			}
			case 4:
			{
				// double a=coefficients[4];
				// double b=coefficients[3];
				// double c=coefficients[2];
				// double d=coefficients[1];
				// double e=coefficients[0];
				return new double[]{0,0,0,0};
			}
			default:
			{
				System.out.println("5次及以上方程无法求解。");
				return null;
			}
		}
	}
	/**
	获取多项式方程的一个单项式。
	@param index 单项式的次数。
	@return 多项式方程的单项式字符串。
	*/
	public String get_monomial(int index)
	{
		if(coefficients[index]==0)
		{
			return "0";
		}
		else if(index==0)
		{
			return ""+coefficients[0];
		}
		else if(index==1)
		{
			return ""+coefficients[1]+unknown;
		}
		else
		{
			return ""+coefficients[index]+unknown+"^"+index;
		}
	}
	/**
	获取带正号的多项式方程的一个单项式。
	@param index 单项式的次数。
	@return 带正号的多项式方程的单项式字符串。
	*/
	public String get_monomial_with_positive_symbol(int index)
	{
		if(coefficients[index]>0)
		{
			return "+"+get_monomial(index);
		}
		else
		{
			return get_monomial(index);
		}
	}
	public String toString()
	{
		String result=""+get_monomial(times);
		for(int i=times-1;i>=0;i--)
		{
			result+=get_monomial_with_positive_symbol(i);
		}
		result+="=0";
		return result;
	}
}