package tools.geography;
/**
<p>地理坐标类。</p><br>
地理坐标是指在地球表面上的一个点的经度和纬度。<br>
经度是指通过地球表面某点的经线平面与本初子午线平面之间的夹角，范围为-180°到180°。<br>
纬度是指通过地球表面某点与地心的连线与赤道面的夹角的角度，范围为-90°到90°。<br>
*/
public class geographic_coordinate
{
    public double longitude_deg;
    public double latitude_deg;
    public int longitude_dms[]=new int[3];
    public int latitude_dms[]=new int[3];
    /**
    构造一个地理坐标对象。
    @param longitude_deg 经度（小数角度）。
    @param latitude_deg 纬度（小数角度）。
    */
    public geographic_coordinate(double longitude_deg,double latitude_deg)
    {
        this.longitude_deg=longitude_deg;
        this.latitude_deg=latitude_deg;
        longitude_dms=deg_to_dms(longitude_deg);
        latitude_dms=deg_to_dms(latitude_deg);
    }
    /**
    将小数角度转换为度分秒格式角度。
    @param deg 角度（小数角度）。
    @return 度分秒格式的数组，格式为[度,分,秒]。
    */
    public static int[] deg_to_dms(double deg)
    {
        return new int[]{(int)deg,(int)((deg-(int)deg)*60),(int)(((deg-(int)deg)*60-(int)((deg-(int)deg)*60))*60)};
    }
    /**
    将度分秒格式角度转换为小数角度。
    @param dms 度分秒格式的数组，格式为[度,分,秒]。
    @return 角度（小数角度）。
    */
    public static double dms_to_deg(int dms[])
    {
        return dms[0]+dms[1]/60.0+dms[2]/3600.0;
    }
    /**
    打印地理坐标的小数角度表示。
    */
    public void print_deg()
    {
        System.out.println("("+longitude_deg+","+latitude_deg+")");
    }
    /**
    打印地理坐标的度分秒格式表示。
    */
    public void print_dms()
    {
        System.out.println("("+longitude_dms[0]+"°"+longitude_dms[1]+"'"+longitude_dms[2]+"\","+latitude_dms[0]+"°"+latitude_dms[1]+"'"+latitude_dms[2]+"\""+")");
    }
    public String toString()
    {
        return "("+longitude_deg+"°"+(longitude_deg>=0?"E,":"W,")+latitude_deg+"°"+(latitude_deg>=0?"N":"S")+")";
    }
}