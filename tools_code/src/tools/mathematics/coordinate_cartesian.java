package tools.mathematics;
/**
<p>直角坐标类。</p><br>
直角坐标表示平面直角坐标系中的一个位置。<br>
横坐标和纵坐标都是实数，取值范围为任意实数。<br>
@see tools.geography.projected_coordinate
*/
public class coordinate_cartesian
{
    public double x;
    public double y;
    /**
    构造一个直角坐标对象。
    @param x 横坐标。
    @param y 纵坐标。
    */
    public coordinate_cartesian(double x,double y)
    {
        this.x=x;
        this.y=y;
    }
    /**
    构造一个默认的直角坐标对象，表示原点。
    */
    public coordinate_cartesian()
    {
        this.x=0;
        this.y=0;
    }
    /**
    计算当前直角坐标对象所在的象限。
    @return 当前直角坐标对象所在的象限。
    */
    public int quadrant()
    {
        return y>0?x>0?1:2:x<0?3:4;
    }
    /**
    计算指定直角坐标所在的象限。
    @param x 横坐标。
    @param y 纵坐标。
    @return 直角坐标(x,y)所在的象限。
    */
    public static int quadrant(double x,double y)
    {
        return y>0?x>0?1:2:x<0?3:4;
    }
    /**
    <p>此方法会修改调用对象。</p><br>
    将当前直角坐标对象与另一个直角坐标对象相加。
    @param coordinate_cartesian 要相加的直角坐标对象。
    */
    public void add(coordinate_cartesian coordinate_cartesian)
    {
        x+=coordinate_cartesian.x;
        y+=coordinate_cartesian.y;
    }
    /**
    计算两个直角坐标对象的和 coordinate1+coordinate2。
    @param coordinate1 第一个直角坐标对象。
    @param coordinate2 第二个直角坐标对象。
    @return 两个直角坐标对象的和。
    */
    public static coordinate_cartesian add(coordinate_cartesian coordinate1,coordinate_cartesian coordinate2)
    {
        return new coordinate_cartesian(coordinate1.x+coordinate2.x,coordinate1.y+coordinate2.y);
    }
    /**
    <p>此方法会修改调用对象。</p><br>
    将当前直角坐标对象与另一个直角坐标对象相减。
    @param coordinate_cartesian 要相减的直角坐标对象。
    */
    public void subtract(coordinate_cartesian coordinate_cartesian)
    {
        x-=coordinate_cartesian.x;
        y-=coordinate_cartesian.y;
    }
    /**
    计算两个直角坐标对象的差 coordinate1-coordinate2。
    @param coordinate1 第一个直角坐标对象。
    @param coordinate2 第二个直角坐标对象。
    @return 两个直角坐标对象的差。
    */
    public static coordinate_cartesian subtract(coordinate_cartesian coordinate1,coordinate_cartesian coordinate2)
    {
        return new coordinate_cartesian(coordinate1.x-coordinate2.x,coordinate1.y-coordinate2.y);
    }
    /**
    <p>此方法会修改调用对象。</p><br>
    计算当前直角坐标对象与一个系数的乘积。
    @param coefficient 系数。
    */
    public void multiply_scalar(double coefficient)
    {
        x*=coefficient;
        y*=coefficient;
    }
    /**
    计算一个直角坐标对象与一个系数的乘积 coordinate_cartesian*coefficient。
    @param coordinate_cartesian 要数乘的直角坐标对象。
    @param coefficient 系数。
    @return 一个直角坐标对象与一个系数的乘积。
    */
    public static coordinate_cartesian multiply_scalar(coordinate_cartesian coordinate_cartesian,double coefficient)
    {
        return new coordinate_cartesian(coordinate_cartesian.x*coefficient,coordinate_cartesian.y*coefficient);
    }
    /**
    计算当前直角坐标对象与另一个直角坐标对象的距离。
    @param coordinate_cartesian 要计算距离的直角坐标对象。
    @return 当前直角坐标对象与另一个直角坐标对象的距离。
    */
    public double distance(coordinate_cartesian coordinate_cartesian)
    {
        return Math.sqrt((x-coordinate_cartesian.x)*(x-coordinate_cartesian.x)+(y-coordinate_cartesian.y)*(y-coordinate_cartesian.y));
    }
    /**
    计算两点间的距离。
    @param x0 起点的横坐标。
    @param y0 起点的纵坐标。
    @param xt 终点的横坐标。
    @param yt 终点的纵坐标。
    @return 两点间距离。
    */
    public double distance(double x0,double y0,double xt,double yt)
    {
        return Math.sqrt((xt-x0)*(xt-x0)+(yt-y0)*(yt-y0));
    }
    /**
    计算该点到终点的角度。
    @param xt 终点的横坐标。
    @param yt 终点的纵坐标。
    @return 该点到终点的角度。
    */
    public double angle(double xt,double yt)
    {
        double dx=xt-x;
        double dy=yt-y;
        if(dx>=0&&dy==0)
        {
            return 0;
        }
        else if(dx<0)
        {
            return 180+Math.atan((double)dy/dx)*180/Math.PI;
        }
        else if(dy>=0)
        {
            return Math.atan((double)dy/dx)*180/Math.PI;
        }
        else
        {
            return 360+Math.atan((double)dy/dx)*180/Math.PI;
        }
    }
    /**
    计算该点到终点的角度。
    @param target 终点的直角坐标对象。
    @return 该点到终点的角度。
    */
    public double angle(coordinate_cartesian target)
    {
        double dx=target.x-x;
        double dy=target.y-y;
        if(dx>=0&&dy==0)
        {
            return 0;
        }
        else if(dx<0)
        {
            return 180+Math.atan((double)dy/dx)*180/Math.PI;
        }
        else if(dy>=0)
        {
            return Math.atan((double)dy/dx)*180/Math.PI;
        }
        else
        {
            return 360+Math.atan((double)dy/dx)*180/Math.PI;
        }
    }
    /**
    计算起点到终点的角度。
    @param x0 起点的横坐标。
    @param y0 起点的纵坐标。
    @param xt 终点的横坐标。
    @param yt 终点的纵坐标。
    @return 起点到终点的角度。
    */
    public static double angle(double x0,double y0,double xt,double yt)
    {
        double dx=xt-x0;
        double dy=yt-y0;
        if(dx>=0&&dy==0)
        {
            return 0;
        }
        else if(dx<0)
        {
            return 180+Math.atan((double)dy/dx)*180/Math.PI;
        }
        else if(dy>=0)
        {
            return Math.atan((double)dy/dx)*180/Math.PI;
        }
        else
        {
            return 360+Math.atan((double)dy/dx)*180/Math.PI;
        }
    }
    /**
    计算该直角坐标对象与指定直角坐标对象连线的中点。
    @param target 目标直角坐标对象。
    @return 该直角坐标对象与指定直角坐标对象连线的中点。
    */
    public coordinate_cartesian middle_point(coordinate_cartesian target)
    {
        return new coordinate_cartesian((target.x+x)/2,(target.y+y)/2);
    }
    /**
    计算该直角坐标对象到指定直角坐标对象的线性插值。
    @param target 目标直角坐标对象。
    @param ratio 线性插值比例。<br>
    ratio∈[0,1]，0表示起点，1表示目标点。
    @return 该投影坐标对象到指定投影坐标对象的线性插值。
    */
    public coordinate_cartesian linear_interpolation(coordinate_cartesian target,double ratio)
    {
        return new coordinate_cartesian(x+ratio*(target.x-x),y+ratio*(target.y-y));
    }
    /**
    <p>此方法会修改调用对象。</p><br>
    将当前直角坐标对象向指定直角坐标对象位移指定距离。<br>
    注意，坐标经计算后可能存在双精度浮点数精度误差，导致位移结果不准确。
    @param target 目标直角坐标对象。
    @param move_distance 距离。
    @return 位移的距离占总距离的比例。<br>
    0表示起点，1表示目标点。
    */
    public double move_distance_towards(coordinate_cartesian target,double move_distance)
    {
        double dx=target.x-x;
        double dy=target.y-y;
        double total_distance=Math.sqrt(dx*dx+dy*dy);
        if(total_distance==0.0)
        {
            return 0.0;
        }
        double ratio=move_distance/total_distance;
        x+=ratio*dx;
        y+=ratio*dy;
        return ratio;
    }
    /**
    <p>此方法会修改调用对象。</p><br>
    将当前直角坐标对象向指定直角坐标对象位移指定比例。
    @param target 目标直角坐标对象。
    @param move_ratio 比例。<br>
    move_ratio∈[0,1]，0表示起点，1表示目标点。
    @return 位移的距离。
    */
    public double move_ratio_towards(coordinate_cartesian target,double move_ratio)
    {
        double dx=target.x-x;
        double dy=target.y-y;
        x+=move_ratio*dx;
        y+=move_ratio*dy;
        return move_ratio*Math.sqrt(dx*dx+dy*dy);
    }
	/**
	计算一个多边形的周长。
	@param coordinates_ccw 多边形的顶点坐标，按逆时针方向给出(p1,p2,p3,p4,...)。
	@return 多边形的周长。<br>
	若多边形的顶点不足2个，则返回0.0。
	*/
	public static double perimeter(coordinate_cartesian... coordinates_ccw)
	{
		if(coordinates_ccw.length>=2)
		{
			double temp[]=new double[coordinates_ccw.length<<1];
            for(int i=0;i<coordinates_ccw.length;i++)
            {
                temp[i<<1]=coordinates_ccw[i].x;
                temp[(i<<1)|1]=coordinates_ccw[i].y;
            }
			double result=0;
			for(int i=0;i+3<temp.length;i+=2)
			{
				result+=Math.sqrt((temp[i+2]-temp[i])*(temp[i+2]-temp[i])+(temp[i+3]-temp[i+1])*(temp[i+3]-temp[i+1]));
			}
			result+=Math.sqrt((temp[temp.length-2]-temp[0])*(temp[temp.length-2]-temp[0])+(temp[temp.length-1]-temp[1])*(temp[temp.length-1]-temp[1]));
			return result;
		}
		else
		{
			return 0.0;
		}
	}
	/**
	计算一个多边形的面积。
	@param coordinates_ccw 多边形的顶点坐标，按逆时针方向给出(p1,p2,p3,p4,...)。
	@return 多边形的面积。<br>
	若多边形的顶点不足3个，则返回0.0。
	*/
	public static double area(coordinate_cartesian... coordinates_ccw)
	{
		if(coordinates_ccw.length>=3)
		{
			double temp[]=new double[coordinates_ccw.length<<1];
            for(int i=0;i<coordinates_ccw.length;i++)
            {
                temp[i<<1]=coordinates_ccw[i].x;
                temp[(i<<1)|1]=coordinates_ccw[i].y;
            }
			double median=maths.median(temp);
			for(int i=0;i<temp.length;i++)
			{
				temp[i]-=median;
			}
			double sum1=0,sum2=0;
			for(int i=0;i+3<temp.length;i+=2)
			{
				sum1+=temp[i]*temp[i+3];
				sum2+=temp[i+1]*temp[i+2];
			}
			sum1+=temp[temp.length-2]*temp[1];
			sum2+=temp[temp.length-1]*temp[0];
			return Math.abs(sum1-sum2)/2;
		}
		else
		{
			return 0.0;
		}
	}
    public String toString()
    {
        return "("+x+","+y+")";
    }
}