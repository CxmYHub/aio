package aio.datetime;
/**
<p>日期时间常数类</p><br>
一些常用的日期时间常数。
*/
public class Calendar {
    /**
    <p>一个平年的天数</p>
    */
    public static final int DAY_IN_COMMON_YEAR=365;
    /**
    <p>一个闰年的天数</p>
    */
    public static final int DAY_IN_LEAP_YEAR=366;
    /**
    <p>一天中的小时数</p>
    */
    public static final int DAY_HOUR=24;
    /**
    <p>一小时中的分钟数</p>
    */
    public static final int HOUR_MINUTE=60;
    /**
    <p>一分钟中的秒数</p>
    */
    public static final int MINUTE_SECOND=60;
    /**
    <p>一秒中的毫秒数</p>
    */
    public static final int SECOND_MILLISECOND=1000;
    /**
    <p>一天中的毫秒数</p>
    */
    public static final int DAY_MILLISECOND=86400000;
    /**
    <p>一个平年的毫秒数</p>
    */
    public static final long COMMON_YEAR_MILLISECOND=31536000000L;
    /**
    <p>一个闰年的毫秒数</p>
    */
    public static final long LEAP_YEAR_MILLISECOND=31622400000L;
    /**
    <p>1970年1月1日距1年1月1日的天数</p>
    */
    public static final int DAY_1970_1_1=719162;
    /**
    <p>1970年1月1日距1年1月1日的毫秒数</p><br>
    即1970年1月1日距1年1月1日的毫秒时间戳。
    */
    public static final long MILLISECOND_1970_1_1=62135596800000L;
    /**
    <p>1970年1月1日距1年1月1日的毫秒时间戳</p><br>
    即1970年1月1日距1年1月1日的毫秒数。
    */
    public static final long TIMESTAMP_1970_1_1=62135596800000L;
    /**
    <p>400年中的天数</p>
    */
    public static final int DAY_IN_400_YEARS=146097;
    /**
    <p>100年中的天数</p>
    */
    public static final int DAY_IN_100_YEARS=36524;
    /**
    <p>4年中的天数</p>
    */
    public static final int DAY_IN_4_YEARS=1461;
    /**
    <p>一个平年中每个月的天数</p>
    */
    public static final int MONTH_DAY_IN_COMMON_YEAR[]={0,31,28,31,30,31,30,31,31,30,31,30,31};
    /**
    <p>一个平年中每个月的天数的前缀和</p>
    */
    public static final int MONTH_DAY_IN_COMMON_YEAR_PREFIX_SUM[]={0,31,59,90,120,151,181,212,243,273,304,334,365};
    /**
    <p>一个平年中每个月的天数的后缀和</p>
    */
    public static final int MONTH_DAY_IN_COMMON_YEAR_SUFFIX_SUM[]={0,365,334,306,275,245,214,184,153,122,92,61,31};
    /**
    <p>一个闰年中每个月的天数</p>
    */
    public static final int MONTH_DAY_IN_LEAP_YEAR[]={0,31,29,31,30,31,30,31,31,30,31,30,31};
    /**
    <p>一个闰年中每个月的天数的前缀和</p>
    */
    public static final int MONTH_DAY_IN_LEAP_YEAR_PREFIX_SUM[]={0,31,60,91,121,152,182,213,244,274,305,335,366};
    /**
    <p>一个闰年中每个月的天数的后缀和</p>
    */
    public static final int MONTH_DAY_IN_LEAP_YEAR_SUFFIX_SUM[]={0,366,335,306,275,245,214,184,153,122,92,61,31};
    /**
    <p>公元1年1月1日的星期</p>
    */
    public static final int WEEKDAY_1_1_1=1;
}