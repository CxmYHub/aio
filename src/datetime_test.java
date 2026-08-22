import java.util.Arrays;
import aio.date_time.*;
/**
 * datetime 类的全面测试类。
 * 测试通过 main() 调用每个测试用例，并输出 ANSI 彩色结果。
 */
public class datetime_test {

    private static final String ANSI_GREEN = "\u001B[32m";
    private static final String ANSI_RED = "\u001B[31m";
    private static final String ANSI_RESET = "\u001B[0m";

    private static int passed = 0;
    private static int failed = 0;

    public static void main(String[] args) {
        int originalTimezone = datetime.get_default_time_zone();
        try {
            datetime.set_default_time_zone(8);
            testLeapYear();
            testDefaultTimeZone();
            testFullConstructor();
            testInvalidFullConstructor();
            testDatePartialConstructors();
            testTimePartialConstructors();
            testTimestampConstructors();
            testStaticTimestamp();
            testInstanceTimestamp();
            testWeekday();
            testDayInYear();
            testAddDayStatic();
            testAddDayInstance();
            testIntervalDayStatic();
            testIntervalDayInstance();
            testSecondInDayAndInterval();
            testCompareTo();
            testToString();
            testNowValid();
        } finally {
            datetime.set_default_time_zone(originalTimezone);
        }
        System.out.println("\n测试完成：通过 " + passed + " 个，失败 " + failed + " 个");
    }

    private static void check(String description, boolean condition, String expected, String actual) {
        if (condition) {
            System.out.println(ANSI_GREEN + "[AC] " + description + " " + actual + ANSI_RESET);
            passed++;
        } else {
            System.out.println(ANSI_RED + "[WA] " + description + " 期望输出=" + expected + " 实际输出=" + actual + ANSI_RESET);
            failed++;
        }
    }

    private static boolean isValidDateTimeLike(datetime dt) {
        return dt.year > 0
                && dt.month >= 1 && dt.month <= 12
                && dt.day >= 1 && dt.day <= 31
                && dt.hour >= 0 && dt.hour <= 23
                && dt.minute >= 0 && dt.minute <= 59
                && dt.second >= 0 && dt.second <= 59
                && dt.millisecond >= 0 && dt.millisecond <= 999
                && dt.time_zone >= -12 && dt.time_zone <= 12;
    }

    private static boolean isValidPureTime(datetime dt) {
        return dt.year == Integer.MIN_VALUE
                && dt.month == Integer.MIN_VALUE
                && dt.day == Integer.MIN_VALUE
                && dt.hour >= 0 && dt.hour <= 23
                && dt.minute >= 0 && dt.minute <= 59
                && dt.second >= 0 && dt.second <= 59
                && dt.millisecond >= 0 && dt.millisecond <= 999
                && dt.time_zone >= -12 && dt.time_zone <= 12;
    }

    private static void testLeapYear() {
        check("闰年判断2000", datetime.is_leap_year(2000), "true", String.valueOf(datetime.is_leap_year(2000)));
        check("闰年判断1900", !datetime.is_leap_year(1900), "false", String.valueOf(datetime.is_leap_year(1900)));
        check("闰年判断2024", datetime.is_leap_year(2024), "true", String.valueOf(datetime.is_leap_year(2024)));
        check("闰年判断2023", !datetime.is_leap_year(2023), "false", String.valueOf(datetime.is_leap_year(2023)));
    }

    private static void testDefaultTimeZone() {
        datetime.set_default_time_zone(8);
        check("默认时区初值", datetime.get_default_time_zone() == 8, "8", String.valueOf(datetime.get_default_time_zone()));

        boolean setValid = datetime.set_default_time_zone(9);
        check("设置有效时区9", setValid, "true", String.valueOf(setValid));
        check("设置后获取9", datetime.get_default_time_zone() == 9, "9", String.valueOf(datetime.get_default_time_zone()));

        boolean setInvalid = datetime.set_default_time_zone(13);
        check("设置无效时区13", !setInvalid, "false", String.valueOf(setInvalid));
        check("无效设置后仍为9", datetime.get_default_time_zone() == 9, "9", String.valueOf(datetime.get_default_time_zone()));

        boolean setBoundaryHigh = datetime.set_default_time_zone(12);
        check("设置边界时区12", setBoundaryHigh, "true", String.valueOf(setBoundaryHigh));
        check("边界后获取12", datetime.get_default_time_zone() == 12, "12", String.valueOf(datetime.get_default_time_zone()));

        boolean setBoundaryLow = datetime.set_default_time_zone(-12);
        check("设置边界时区-12", setBoundaryLow, "true", String.valueOf(setBoundaryLow));
        check("边界后获取-12", datetime.get_default_time_zone() == -12, "-12", String.valueOf(datetime.get_default_time_zone()));

        datetime.set_default_time_zone(8);
    }

    private static void testFullConstructor() {
        datetime dt = new datetime(2024, 2, 29, 23, 59, 58, 999, 8);
        boolean fieldsOk = dt.year == 2024 && dt.month == 2 && dt.day == 29
                && dt.hour == 23 && dt.minute == 59 && dt.second == 58
                && dt.millisecond == 999 && dt.time_zone == 8;
        check("全参构造-合法闰年字段", fieldsOk, "字段与预期一致", dt.toString());
        check("全参构造-闰年toString",
                dt.toString().equals("AD 2024/02/29 23:59:58.999 UTC+8"),
                "AD 2024/02/29 23:59:58.999 UTC+8", dt.toString());

        datetime bc = new datetime(-1, 1, 1, 0, 0, 0, 0, 8);
        boolean bcFieldsOk = bc.year == 0 && bc.month == 1 && bc.day == 1;
        check("全参构造-公元前1年字段", bcFieldsOk, "year=0 month=1 day=1", bc.toString());
        check("全参构造-公元前toString",
                bc.toString().equals("1/01/01 BC 00:00:00.000 UTC+8"),
                "1/01/01 BC 00:00:00.000 UTC+8", bc.toString());

        datetime yearZero = new datetime(0, 1, 1, 0, 0, 0, 0, 8);
        check("全参构造-year=0重置为1", yearZero.year == 1, "year=1", String.valueOf(yearZero.year));
        check("全参构造-year=0 toString",
                yearZero.toString().equals("AD 1/01/01 00:00:00.000 UTC+8"),
                "AD 1/01/01 00:00:00.000 UTC+8", yearZero.toString());
    }

    private static void testInvalidFullConstructor() {
        datetime invalidDate = new datetime(2023, 2, 29, 0, 0, 0, 0, 8);
        check("无效日期回退当前", isValidDateTimeLike(invalidDate), "合法当前日期时间", invalidDate.toString());

        datetime invalidHour = new datetime(2024, 1, 1, 24, 0, 0, 0, 8);
        check("无效小时回退当前", isValidDateTimeLike(invalidHour), "合法当前日期时间", invalidHour.toString());

        datetime invalidTimezone = new datetime(2024, 1, 1, 0, 0, 0, 0, 13);
        check("无效时区回退当前", isValidDateTimeLike(invalidTimezone), "合法当前日期时间", invalidTimezone.toString());
    }

    private static void testDatePartialConstructors() {
        datetime d = new datetime(2024, 2, 29, 8, true);
        boolean fieldsOk = d.year == 2024 && d.month == 2 && d.day == 29
                && d.hour == 0 && d.minute == 0 && d.second == 0
                && d.millisecond == 0 && d.time_zone == 8;
        check("纯日期构造-字段", fieldsOk, "纯日期字段", d.toString());
        check("纯日期构造-toString",
                d.toString().equals("AD 2024/02/29 00:00:00.000 UTC+8"),
                "AD 2024/02/29 00:00:00.000 UTC+8", d.toString());

        datetime invalidDate = new datetime(2023, 2, 29, 8, true);
        check("纯日期构造-无效日期回退", isValidDateTimeLike(invalidDate), "合法当前日期", invalidDate.toString());

        datetime defaultZoneDate = new datetime(2024, 2, 29, true);
        check("纯日期构造-默认时区",
                defaultZoneDate.toString().equals("AD 2024/02/29 00:00:00.000 UTC+8"),
                "AD 2024/02/29 00:00:00.000 UTC+8", defaultZoneDate.toString());
    }

    private static void testTimePartialConstructors() {
        datetime t = new datetime(23, 59, 58, 8, false);
        boolean fieldsOk = t.year == Integer.MIN_VALUE && t.month == Integer.MIN_VALUE && t.day == Integer.MIN_VALUE
                && t.hour == 23 && t.minute == 59 && t.second == 58
                && t.millisecond == 0 && t.time_zone == 8;
        check("纯时间构造-字段", fieldsOk, "纯时间字段", t.toString());
        check("纯时间构造-toString",
                t.toString().equals("23:59:58.000 UTC+8"),
                "23:59:58.000 UTC+8", t.toString());

        datetime invalidTime = new datetime(24, 0, 0, 8, false);
        check("纯时间构造-无效时间回退", isValidPureTime(invalidTime), "合法当前时间", invalidTime.toString());

        datetime defaultZoneTime = new datetime(23, 59, 58, false);
        check("纯时间构造-默认时区",
                defaultZoneTime.toString().equals("23:59:58.000 UTC+8"),
                "23:59:58.000 UTC+8", defaultZoneTime.toString());
    }

    private static void testTimestampConstructors() {
        datetime epoch = new datetime(0L, 0);
        check("时间戳构造-公元元年",
                epoch.toString().equals("AD 1/01/01 00:00:00.000 UTC+0"),
                "AD 1/01/01 00:00:00.000 UTC+0", epoch.toString());

        datetime unixEpoch = new datetime(62135596800000L, 0);
        check("时间戳构造-1970-01-01",
                unixEpoch.toString().equals("AD 1970/01/01 00:00:00.000 UTC+0"),
                "AD 1970/01/01 00:00:00.000 UTC+0", unixEpoch.toString());

        datetime offset = new datetime(0L, 8);
        check("时间戳构造-时区偏移",
                offset.toString().equals("AD 1/01/01 08:00:00.000 UTC+8"),
                "AD 1/01/01 08:00:00.000 UTC+8", offset.toString());

        datetime dayEpoch = new datetime(0, 0);
        check("日时间戳构造-公元元年",
                dayEpoch.toString().equals("AD 1/01/01 00:00:00.000 UTC+0"),
                "AD 1/01/01 00:00:00.000 UTC+0", dayEpoch.toString());

        datetime dayUnix = new datetime(719162, 0);
        check("日时间戳构造-1970-01-01",
                dayUnix.toString().equals("AD 1970/01/01 00:00:00.000 UTC+0"),
                "AD 1970/01/01 00:00:00.000 UTC+0", dayUnix.toString());
    }

    private static void testStaticTimestamp() {
        check("静态timestamp-1年",
                datetime.timestamp(1, 1, 1, 0, 0, 0, 0, 0) == 0L,
                "0", String.valueOf(datetime.timestamp(1, 1, 1, 0, 0, 0, 0, 0)));

        check("静态timestamp-1970",
                datetime.timestamp(1970, 1, 1, 0, 0, 0, 0, 0) == 62135596800000L,
                "62135596800000", String.valueOf(datetime.timestamp(1970, 1, 1, 0, 0, 0, 0, 0)));

        check("静态timestamp-1970+8时区",
                datetime.timestamp(1970, 1, 1, 0, 0, 0, 0, 8) == 62135568000000L,
                "62135568000000", String.valueOf(datetime.timestamp(1970, 1, 1, 0, 0, 0, 0, 8)));

        check("静态timestamp_unix-1970",
                datetime.timestamp_unix(1970, 1, 1, 0, 0, 0, 0, 0) == 0L,
                "0", String.valueOf(datetime.timestamp_unix(1970, 1, 1, 0, 0, 0, 0, 0)));

        check("静态timestamp_unix-1970+8时区",
                datetime.timestamp_unix(1970, 1, 1, 0, 0, 0, 0, 8) == -28800000L,
                "-28800000", String.valueOf(datetime.timestamp_unix(1970, 1, 1, 0, 0, 0, 0, 8)));

        check("静态timestamp_day-1年",
                datetime.timestamp_day(1, 1, 1) == 0,
                "0", String.valueOf(datetime.timestamp_day(1, 1, 1)));

        check("静态timestamp_day-1970",
                datetime.timestamp_day(1970, 1, 1) == 719162,
                "719162", String.valueOf(datetime.timestamp_day(1970, 1, 1)));
    }

    private static void testInstanceTimestamp() {
        datetime dt = new datetime(1970, 1, 1, 0, 0, 0, 0, 0);
        check("实例timestamp-1970",
                dt.timestamp() == 62135596800000L,
                "62135596800000", String.valueOf(dt.timestamp()));
        check("实例timestamp_unix-1970",
                dt.timestamp_unix() == 0L,
                "0", String.valueOf(dt.timestamp_unix()));
        check("实例timestamp_day-1970",
                dt.timestamp_day() == 719162,
                "719162", String.valueOf(dt.timestamp_day()));

        datetime dt8 = new datetime(1970, 1, 1, 0, 0, 0, 0, 8);
        check("实例timestamp_unix-1970+8时区",
                dt8.timestamp_unix() == -28800000L,
                "-28800000", String.valueOf(dt8.timestamp_unix()));
    }

    private static void testWeekday() {
        check("星期-2024-01-01周一",
                datetime.weekday(2024, 1, 1) == 1,
                "1", String.valueOf(datetime.weekday(2024, 1, 1)));
        check("星期-2024-01-07周日",
                datetime.weekday(2024, 1, 7) == 0,
                "0", String.valueOf(datetime.weekday(2024, 1, 7)));
        check("星期-2024-02-29周四",
                datetime.weekday(2024, 2, 29) == 4,
                "4", String.valueOf(datetime.weekday(2024, 2, 29)));
        check("星期-2000-01-01周六",
                datetime.weekday(2000, 1, 1) == 6,
                "6", String.valueOf(datetime.weekday(2000, 1, 1)));

        datetime date = new datetime(2024, 1, 1, true);
        check("实例星期-2024-01-01",
                date.weekday() == 1,
                "1", String.valueOf(date.weekday()));

        datetime pureTime = new datetime(1, 0, 0, false);
        check("实例星期-纯时间返回MIN",
                pureTime.weekday() == Integer.MIN_VALUE,
                String.valueOf(Integer.MIN_VALUE), String.valueOf(pureTime.weekday()));
    }

    private static void testDayInYear() {
        check("年内天数-2024-03-01",
                datetime.day_in_year(2024, 3, 1) == 61,
                "61", String.valueOf(datetime.day_in_year(2024, 3, 1)));
        check("年内天数-2023-03-01",
                datetime.day_in_year(2023, 3, 1) == 60,
                "60", String.valueOf(datetime.day_in_year(2023, 3, 1)));
        check("年内天数-2024-12-31",
                datetime.day_in_year(2024, 12, 31) == 366,
                "366", String.valueOf(datetime.day_in_year(2024, 12, 31)));
        check("年内天数-2023-12-31",
                datetime.day_in_year(2023, 12, 31) == 365,
                "365", String.valueOf(datetime.day_in_year(2023, 12, 31)));

        datetime pureTime = new datetime(1, 0, 0, false);
        check("实例年内天数-纯时间返回MIN",
                pureTime.day_in_year() == Integer.MIN_VALUE,
                String.valueOf(Integer.MIN_VALUE), String.valueOf(pureTime.day_in_year()));
    }

    private static void testAddDayStatic() {
        check("加天数-公元元年+0",
                datetime.add_day(1, 1, 1, 0).toString().equals("AD 1/01/01 00:00:00.000 UTC+8"),
                "AD 1/01/01 00:00:00.000 UTC+8", datetime.add_day(1, 1, 1, 0).toString());

        check("加天数-2024-02-28+1",
                datetime.add_day(2024, 2, 28, 1).toString().equals("AD 2024/02/29 00:00:00.000 UTC+8"),
                "AD 2024/02/29 00:00:00.000 UTC+8", datetime.add_day(2024, 2, 28, 1).toString());

        check("加天数-2023-02-28+1",
                datetime.add_day(2023, 2, 28, 1).toString().equals("AD 2023/03/01 00:00:00.000 UTC+8"),
                "AD 2023/03/01 00:00:00.000 UTC+8", datetime.add_day(2023, 2, 28, 1).toString());

        check("加天数-2024-02-29+1",
                datetime.add_day(2024, 2, 29, 1).toString().equals("AD 2024/03/01 00:00:00.000 UTC+8"),
                "AD 2024/03/01 00:00:00.000 UTC+8", datetime.add_day(2024, 2, 29, 1).toString());

        check("加天数-公元元年-1",
                datetime.add_day(1, 1, 1, -1).toString().equals("1/12/31 BC 00:00:00.000 UTC+8"),
                "1/12/31 BC 00:00:00.000 UTC+8", datetime.add_day(1, 1, 1, -1).toString());

        check("加天数-公元元年+365",
                datetime.add_day(1, 1, 1, 365).toString().equals("AD 2/01/01 00:00:00.000 UTC+8"),
                "AD 2/01/01 00:00:00.000 UTC+8", datetime.add_day(1, 1, 1, 365).toString());

        check("加天数-公元元年+366",
                datetime.add_day(1, 1, 1, 366).toString().equals("AD 2/01/02 00:00:00.000 UTC+8"),
                "AD 2/01/02 00:00:00.000 UTC+8", datetime.add_day(1, 1, 1, 366).toString());

        check("加天数-世纪边界1900",
                datetime.add_day(1900, 2, 28, 1).toString().equals("AD 1900/03/01 00:00:00.000 UTC+8"),
                "AD 1900/03/01 00:00:00.000 UTC+8", datetime.add_day(1900, 2, 28, 1).toString());

        check("加天数-世纪边界2000",
                datetime.add_day(2000, 2, 28, 1).toString().equals("AD 2000/02/29 00:00:00.000 UTC+8"),
                "AD 2000/02/29 00:00:00.000 UTC+8", datetime.add_day(2000, 2, 28, 1).toString());
    }

    private static void testAddDayInstance() {
        datetime dt = new datetime(2024, 2, 29, 12, 34, 56, 789, 8);
        datetime added = dt.add_day(1);
        check("实例加天数-保留时间",
                added.toString().equals("AD 2024/03/01 12:34:56.789 UTC+8"),
                "AD 2024/03/01 12:34:56.789 UTC+8", added.toString());

        datetime pureTime = new datetime(1, 2, 3, false);
        datetime timeAdded = pureTime.add_day(10);
        check("实例加天数-纯时间返回this",
                timeAdded == pureTime,
                "同一对象", String.valueOf(timeAdded == pureTime));
    }

    private static void testIntervalDayStatic() {
        check("日期差-同年同日",
                datetime.interval_day(2024, 1, 1, 2024, 1, 1) == 0L,
                "0", String.valueOf(datetime.interval_day(2024, 1, 1, 2024, 1, 1)));

        check("日期差-后一天",
                datetime.interval_day(2024, 1, 1, 2024, 1, 2) == 1L,
                "1", String.valueOf(datetime.interval_day(2024, 1, 1, 2024, 1, 2)));

        check("日期差-前一天",
                datetime.interval_day(2024, 1, 2, 2024, 1, 1) == -1L,
                "-1", String.valueOf(datetime.interval_day(2024, 1, 2, 2024, 1, 1)));

        check("日期差-跨闰年",
                datetime.interval_day(2024, 1, 1, 2025, 1, 1) == 366L,
                "366", String.valueOf(datetime.interval_day(2024, 1, 1, 2025, 1, 1)));

        check("日期差-公元元年到1970",
                datetime.interval_day(1, 1, 1, 1970, 1, 1) == 719162L,
                "719162", String.valueOf(datetime.interval_day(1, 1, 1, 1970, 1, 1)));
    }

    private static void testIntervalDayInstance() {
        datetime start = new datetime(2024, 1, 1, true);
        datetime end = new datetime(2024, 1, 2, true);

        check("实例日期差-后一天",
                start.interval_day(end) == 1L,
                "1", String.valueOf(start.interval_day(end)));

        check("实例日期差-前一天",
                end.interval_day(start) == -1L,
                "-1", String.valueOf(end.interval_day(start)));

        datetime pureTime = new datetime(1, 0, 0, false);
        check("实例日期差-纯时间返回MIN",
                start.interval_day(pureTime) == Long.MIN_VALUE,
                String.valueOf(Long.MIN_VALUE), String.valueOf(start.interval_day(pureTime)));
    }

    private static void testSecondInDayAndInterval() {
        check("日中秒-0点",
                datetime.second_in_day(0, 0, 0) == 0,
                "0", String.valueOf(datetime.second_in_day(0, 0, 0)));

        check("日中秒-23:59:59",
                datetime.second_in_day(23, 59, 59) == 86399,
                "86399", String.valueOf(datetime.second_in_day(23, 59, 59)));

        datetime t = new datetime(1, 2, 3, false);
        check("实例日中秒-01:02:03",
                t.second_in_day() == 3723,
                "3723", String.valueOf(t.second_in_day()));

        check("纯时间差-静态",
                datetime.interval_second_in_day(0, 0, 0, 23, 59, 59) == 86399,
                "86399", String.valueOf(datetime.interval_second_in_day(0, 0, 0, 23, 59, 59)));

        check("纯时间差-静态反向",
                datetime.interval_second_in_day(23, 59, 59, 0, 0, 0) == -86399,
                "-86399", String.valueOf(datetime.interval_second_in_day(23, 59, 59, 0, 0, 0)));

        datetime start = new datetime(0, 0, 0, 8, false);
        datetime end = new datetime(1, 0, 0, 8, false);
        check("实例纯时间差-同时区",
                start.interval_second_in_day(end) == 3600,
                "3600", String.valueOf(start.interval_second_in_day(end)));

        datetime startFar = new datetime(0, 0, 0, 12, false);
        datetime endFar = new datetime(23, 59, 59, -12, false);
        check("实例纯时间差-跨时区",
                startFar.interval_second_in_day(endFar) == 172799,
                "172799", String.valueOf(startFar.interval_second_in_day(endFar)));
    }

    private static void testCompareTo() {
        datetime a = new datetime(2024, 1, 1, true);
        datetime b = new datetime(2024, 1, 1, true);
        check("比较-相同日期", a.compareTo(b) == 0, "0", String.valueOf(a.compareTo(b)));

        datetime later = new datetime(2024, 1, 2, true);
        check("比较-较早日期", a.compareTo(later) < 0, "<0", String.valueOf(a.compareTo(later)));
        check("比较-较晚日期", later.compareTo(a) > 0, ">0", String.valueOf(later.compareTo(a)));

        datetime dateTime = new datetime(2000, 1, 1, 12, 0, 0, 0, 8);
        datetime pureTime = new datetime(12, 0, 0, 8, false);
        check("比较-混合仅比时间相等", dateTime.compareTo(pureTime) == 0, "0", String.valueOf(dateTime.compareTo(pureTime)));
    }

    private static void testToString() {
        datetime ad = new datetime(2024, 1, 2, 3, 4, 5, 6, 8);
        check("toString-AD",
                ad.toString().equals("AD 2024/01/02 03:04:05.006 UTC+8"),
                "AD 2024/01/02 03:04:05.006 UTC+8", ad.toString());

        datetime negativeZone = new datetime(2024, 1, 2, 3, 4, 5, 6, -5);
        check("toString-负时区",
                negativeZone.toString().equals("AD 2024/01/02 03:04:05.006 UTC-5"),
                "AD 2024/01/02 03:04:05.006 UTC-5", negativeZone.toString());

        datetime bc = new datetime(-10, 12, 31, 23, 59, 59, 0, 0);
        check("toString-公元前",
                bc.toString().equals("10/12/31 BC 23:59:59.000 UTC+0"),
                "10/12/31 BC 23:59:59.000 UTC+0", bc.toString());

        datetime pureTime = new datetime(23, 59, 58, 8, false);
        check("toString-纯时间",
                pureTime.toString().equals("23:59:58.000 UTC+8"),
                "23:59:58.000 UTC+8", pureTime.toString());
    }

    private static void testNowValid() {
        int[] now = datetime.now(8);
        boolean valid = now.length == 8
                && now[0] > 0
                && now[1] >= 1 && now[1] <= 12
                && now[2] >= 1 && now[2] <= 31
                && now[3] >= 0 && now[3] <= 23
                && now[4] >= 0 && now[4] <= 59
                && now[5] >= 0 && now[5] <= 59
                && now[6] >= 0 && now[6] <= 999
                && now[7] == 8;
        check("now(8)字段合法", valid, "合法数组", Arrays.toString(now));

        int[] nowDefault = datetime.now();
        check("now()使用默认时区", nowDefault[7] == 8, "8", String.valueOf(nowDefault[7]));

        long timestampNow = datetime.timestamp_now();
        check("timestamp_now为正", timestampNow > 0, ">0", String.valueOf(timestampNow));

        long unixNow = datetime.timestamp_unix_now();
        check("timestamp_unix_now为正", unixNow > 0, ">0", String.valueOf(unixNow));
    }
}