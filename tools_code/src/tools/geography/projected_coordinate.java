package tools.geography;
import tools.mathematics.maths;
/**
<p>投影坐标类。</p><br>
投影坐标是指在平面上的一个点的横坐标和纵坐标。<br>
横坐标正方向为东，纵坐标正方向为北，即坐标北。<br>
横坐标和纵坐标都是实数，取值范围为任意实数。<br>
本投影坐标单位为米。<br>
@see tools.mathematics.coordinate_cartesian
*/
public class projected_coordinate
{
    public double x;
    public double y;
    /**
    构造一个投影坐标对象。
    @param x 横坐标。
    @param y 纵坐标。
    */
    public projected_coordinate(double x,double y)
    {
        this.x=x;
        this.y=y;
    }
    /**
    构造一个默认的投影坐标对象，表示原点。
    */
    public projected_coordinate()
    {
        this.x=0;
        this.y=0;
    }
    /**
    <p>此方法会修改调用对象。</p><br>
    将当前投影坐标对象进行位移。
    @param delta_x 向东位移。
    @param delta_y 向北位移。
    @return 是否实际移动，即位移是否大于0.
    */
    public boolean move(double delta_x,double delta_y)
    {
        x+=delta_x;
        y+=delta_y;
        return delta_x!=0||delta_y!=0;
    }
    /**
    计算指定投影坐标对象偏移指定量的投影坐标。
    @param coordinate 基准投影坐标对象。
    @param delta_x 向东偏移。
    @param delta_y 向北偏移。
    @return 偏移后的投影坐标对象。
    */
    public static projected_coordinate offset(projected_coordinate coordinate,double delta_x,double delta_y)
    {
        return new projected_coordinate(coordinate.x+delta_x,coordinate.y+delta_y);
    }
    /**
    计算一个投影坐标对象到当前投影坐标对象的相对坐标。
    @param coordinate 目标投影坐标对象。
    @return 相对坐标数组，格式为[相对横坐标,相对纵坐标]。
    */
    public double[] relative_position(projected_coordinate coordinate)
    {
        return new double[]{coordinate.x-x,coordinate.y-y};
    }
    /**
    计算当前投影坐标对象与另一个投影坐标对象的距离。
    @param coordinate 要计算距离的投影坐标对象。
    @return 当前投影坐标对象与另一个投影坐标对象的距离。
    */
    public double distance(projected_coordinate coordinate)
    {
        return Math.sqrt((x-coordinate.x)*(x-coordinate.x)+(y-coordinate.y)*(y-coordinate.y));
    }
    /**
    计算两点间的距离。
    @param x0 起点的横坐标。
    @param y0 起点的纵坐标。
    @param xt 终点的横坐标。
    @param yt 终点的纵坐标。
    @return 两点间距离。
    */
    public static double distance(double x0,double y0,double xt,double yt)
    {
        return Math.sqrt((xt-x0)*(xt-x0)+(yt-y0)*(yt-y0));
    }
    /**
    判断当前投影坐标对象与指定投影坐标对象是否在指定容差内。
    @param coordinate 投影坐标对象。
    @param tolerance 容差。
    @return 是否在指定容差内。
    */
    public boolean equals_approximate(projected_coordinate coordinate,double tolerance)
    {
        return distance(coordinate)<=tolerance;
    }
    /**
    计算该点到终点的方位角。
    @param xt 终点的横坐标。
    @param yt 终点的纵坐标。
    @return 该点到终点的方位角。
    */
    public double azimuth_angle(double xt,double yt)
    {
        double dx=xt-x;
        double dy=yt-y;
        if(dx==0&&dy>=0)
        {
            return 0;
        }
        else if(dx==0&&dy<0)
        {
            return 180;
        }
        else if(dx<0)
        {
            return 270-Math.atan((double)dy/dx)*180/Math.PI;
        }
        else
        {
            return 90-Math.atan((double)dy/dx)*180/Math.PI;
        }
    }
    /**
    计算该点到目标点的方位角。
    @param target 目标投影坐标对象。
    @return 该点到目标点的方位角。
    */
    public double azimuth_angle(projected_coordinate target)
    {
        double dx=target.x-x;
        double dy=target.y-y;
        if(dx==0&&dy>=0)
        {
            return 0;
        }
        else if(dx==0&&dy<0)
        {
            return 180;
        }
        else if(dx<0)
        {
            return 270-Math.atan((double)dy/dx)*180/Math.PI;
        }
        else
        {
            return 90-Math.atan((double)dy/dx)*180/Math.PI;
        }
    }
    /**
    计算起点到终点的方位角。
    @param x0 起点的横坐标。
    @param y0 起点的纵坐标。
    @param xt 终点的横坐标。
    @param yt 终点的纵坐标。
    @return 起点到终点的方位角。
    */
    public static double azimuth_angle(double x0,double y0,double xt,double yt)
    {
        double dx=xt-x0;
        double dy=yt-y0;
        if(dx==0&&dy>=0)
        {
            return 0;
        }
        else if(dx==0&&dy<0)
        {
            return 180;
        }
        else if(dx<0)
        {
            return 270-Math.atan((double)dy/dx)*180/Math.PI;
        }
        else
        {
            return 90-Math.atan((double)dy/dx)*180/Math.PI;
        }
    }
    /**
    计算该投影坐标对象以指定方位角位移指定距离后的投影坐标。
    @param azimuth_angle 方位角。
    @param distance 距离。
    @return 以指定方位角位移指定距离后的投影坐标对象。
    */
    public projected_coordinate destination(double azimuth_angle,double distance)
    {
        double destination_x=x;
        double destination_y=y;
        double half_distance=distance/2;
        azimuth_angle=(azimuth_angle+360)%360;
        if(azimuth_angle==0)
        {
            destination_y+=distance;
        }
        else if(azimuth_angle==30)
        {
            destination_x+=half_distance;
            destination_y+=half_distance*Math.sqrt(3);
        }
        else if(azimuth_angle==60)
        {
            destination_x+=half_distance*Math.sqrt(3);
            destination_y+=half_distance;
        }
        else if(azimuth_angle==90)
        {
            destination_x+=distance;
        }
        else if(azimuth_angle==120)
        {
            destination_x+=half_distance*Math.sqrt(3);
            destination_y-=half_distance;
        }
        else if(azimuth_angle==150)
        {
            destination_x+=half_distance;
            destination_y-=half_distance*Math.sqrt(3);
        }
        else if(azimuth_angle==180)
        {
            destination_y-=distance;
        }
        else if(azimuth_angle==210)
        {
            destination_x-=half_distance;
            destination_y-=half_distance*Math.sqrt(3);
        }
        else if(azimuth_angle==240)
        {
            destination_x-=half_distance*Math.sqrt(3);
            destination_y-=half_distance;
        }
        else if(azimuth_angle==270)
        {
            destination_x-=distance;
        }
        else if(azimuth_angle==300)
        {
            destination_x-=half_distance*Math.sqrt(3);
            destination_y+=half_distance;
        }
        else if(azimuth_angle==330)
        {
            destination_x-=half_distance;
            destination_y+=half_distance*Math.sqrt(3);
        }
        else
        {
            destination_x+=distance*Math.cos((90-azimuth_angle)*Math.PI/180);
            destination_y+=distance*Math.sin((90-azimuth_angle)*Math.PI/180);
        }
        return new projected_coordinate(destination_x,destination_y);
    }
    /**
    计算该投影坐标对象与指定投影坐标对象连线的中点。
    @param target 目标投影坐标对象。
    @return 该投影坐标对象与指定投影坐标对象连线的中点。
    */
    public projected_coordinate middle_point(projected_coordinate target)
    {
        return new projected_coordinate((target.x+x)/2,(target.y+y)/2);
    }
    /**
    计算该投影坐标对象到指定投影坐标对象的线性插值。
    @param target 目标投影坐标对象。
    @param ratio 线性插值比例。<br>
    ratio∈[0,1]，0表示起点，1表示目标点。
    @return 该投影坐标对象到指定投影坐标对象的线性插值。
    */
    public projected_coordinate linear_interpolation(projected_coordinate target,double ratio)
    {
        return new projected_coordinate(x+ratio*(target.x-x),y+ratio*(target.y-y));
    }
    /**
    <p>此方法会修改调用对象。</p><br>
    将当前投影坐标对象向指定投影坐标对象位移指定距离。<br>
    注意，坐标经计算后可能存在双精度浮点数精度误差，导致位移结果不准确。
    @param target 目标投影坐标对象。
    @param move_distance 距离。
    @return 位移的距离占总距离的比例。<br>
    0表示起点，1表示目标点。
    */
    public double move_distance_towards(projected_coordinate target,double move_distance)
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
    将当前投影坐标对象向指定投影坐标对象位移指定比例。
    @param target 目标投影坐标对象。
    @param move_ratio 比例。<br>
    move_ratio∈[0,1]，0表示起点，1表示目标点。
    @return 位移的距离。
    */
    public double move_ratio_towards(projected_coordinate target,double move_ratio)
    {
        double dx=target.x-x;
        double dy=target.y-y;
        x+=move_ratio*dx;
        y+=move_ratio*dy;
        return move_ratio*Math.sqrt(dx*dx+dy*dy);
    }
	/**
	计算一个多边形区域的周长。
	@param coordinates_ccw 多边形的顶点坐标，按逆时针方向给出(p1,p2,p3,p4,...)。
	@return 多边形区域的周长。<br>
	若多边形的顶点不足2个，则返回0.0。
	*/
	public static double perimeter(projected_coordinate... coordinates_ccw)
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
	计算一个多边形区域的面积。
	@param coordinates_ccw 多边形的顶点坐标，按逆时针方向给出(p1,p2,p3,p4,...)。
	@return 多边形区域的面积。<br>
	若多边形的顶点不足3个，则返回0.0。
	*/
	public static double area(projected_coordinate... coordinates_ccw)
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
    /**
    判断当前投影坐标对象是否严格等于指定投影坐标对象。
    @param object 投影坐标对象。
    @return 是否严格等于指定投影坐标对象。<br>
    注意，坐标经计算后可能存在双精度浮点数精度误差，导致判断结果不准确。
    */
    public boolean equals(Object object)
    {
        if(object instanceof projected_coordinate)
        {
            if(object==this)
            {
                return true;
            }
            else
            {
                projected_coordinate target=(projected_coordinate)object;
                return target.x==x&&target.y==y;
            }
        }
        else
        {
            return false;
        }
    }
}