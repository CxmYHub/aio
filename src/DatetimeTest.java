import java.util.Arrays;

import aio.datetime.*;
/**
 * datetime 类的全面测试类。
 * 测试通过 main() 调用每个测试用例，并输出 ANSI 彩色结果。
 */
public class DatetimeTest {

    private static final String ANSI_GREEN = "\u001B[32m";
    private static final String ANSI_RED = "\u001B[31m";
    private static final String ANSI_RESET = "\u001B[0m";

    private static int passed = 0;
    private static int failed = 0;

    public static void main(String[] args) {
        int originalTimezone = Datetime.getDefaultTimeZone();
        try {
            Datetime.setDefaultTimeZone(8);
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
            Datetime.setDefaultTimeZone(originalTimezone);
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

    private static boolean isValidDateTimeLike(Datetime dt) {
        return dt.YEAR > 0
                && dt.MONTH >= 1 && dt.MONTH <= 12
                && dt.DAY >= 1 && dt.DAY <= 31
                && dt.HOUR >= 0 && dt.HOUR <= 23
                && dt.MINUTE >= 0 && dt.MINUTE <= 59
                && dt.SECOND >= 0 && dt.SECOND <= 59
                && dt.MILLISECOND >= 0 && dt.MILLISECOND <= 999
                && dt.TIME_ZONE >= -12 && dt.TIME_ZONE <= 12;
    }

    private static boolean isValidPureTime(Datetime dt) {
        return dt.YEAR == Integer.MIN_VALUE
                && dt.MONTH == Integer.MIN_VALUE
                && dt.DAY == Integer.MIN_VALUE
                && dt.HOUR >= 0 && dt.HOUR <= 23
                && dt.MINUTE >= 0 && dt.MINUTE <= 59
                && dt.SECOND >= 0 && dt.SECOND <= 59
                && dt.MILLISECOND >= 0 && dt.MILLISECOND <= 999
                && dt.TIME_ZONE >= -12 && dt.TIME_ZONE <= 12;
    }

    private static void testLeapYear() {
        check("闰年判断2000", Datetime.isLeapYear(2000), "true", String.valueOf(Datetime.isLeapYear(2000)));
        check("闰年判断1900", !Datetime.isLeapYear(1900), "false", String.valueOf(Datetime.isLeapYear(1900)));
        check("闰年判断2024", Datetime.isLeapYear(2024), "true", String.valueOf(Datetime.isLeapYear(2024)));
        check("闰年判断2023", !Datetime.isLeapYear(2023), "false", String.valueOf(Datetime.isLeapYear(2023)));
    }

    private static void testDefaultTimeZone() {
        Datetime.setDefaultTimeZone(8);
        check("默认时区初值", Datetime.getDefaultTimeZone() == 8, "8", String.valueOf(Datetime.getDefaultTimeZone()));

        boolean setValid = Datetime.setDefaultTimeZone(9);
        check("设置有效时区9", setValid, "true", String.valueOf(setValid));
        check("设置后获取9", Datetime.getDefaultTimeZone() == 9, "9", String.valueOf(Datetime.getDefaultTimeZone()));

        boolean setInvalid = Datetime.setDefaultTimeZone(13);
        check("设置无效时区13", !setInvalid, "false", String.valueOf(setInvalid));
        check("无效设置后仍为9", Datetime.getDefaultTimeZone() == 9, "9", String.valueOf(Datetime.getDefaultTimeZone()));

        boolean setBoundaryHigh = Datetime.setDefaultTimeZone(12);
        check("设置边界时区12", setBoundaryHigh, "true", String.valueOf(setBoundaryHigh));
        check("边界后获取12", Datetime.getDefaultTimeZone() == 12, "12", String.valueOf(Datetime.getDefaultTimeZone()));

        boolean setBoundaryLow = Datetime.setDefaultTimeZone(-12);
        check("设置边界时区-12", setBoundaryLow, "true", String.valueOf(setBoundaryLow));
        check("边界后获取-12", Datetime.getDefaultTimeZone() == -12, "-12", String.valueOf(Datetime.getDefaultTimeZone()));

        Datetime.setDefaultTimeZone(8);
    }

    private static void testFullConstructor() {
        Datetime dt = new Datetime(2024, 2, 29, 23, 59, 58, 999, 8);
        boolean fieldsOk = dt.YEAR == 2024 && dt.MONTH == 2 && dt.DAY == 29
                && dt.HOUR == 23 && dt.MINUTE == 59 && dt.SECOND == 58
                && dt.MILLISECOND == 999 && dt.TIME_ZONE == 8;
        check("全参构造-合法闰年字段", fieldsOk, "字段与预期一致", dt.toString());
        check("全参构造-闰年toString",
                dt.toString().equals("AD 2024/02/29 23:59:58.999 UTC+8"),
                "AD 2024/02/29 23:59:58.999 UTC+8", dt.toString());

        Datetime bc = new Datetime(-1, 1, 1, 0, 0, 0, 0, 8);
        boolean bcFieldsOk = bc.YEAR == 0 && bc.MONTH == 1 && bc.DAY == 1;
        check("全参构造-公元前1年字段", bcFieldsOk, "year=0 month=1 day=1", bc.toString());
        check("全参构造-公元前toString",
                bc.toString().equals("1/01/01 BC 00:00:00.000 UTC+8"),
                "1/01/01 BC 00:00:00.000 UTC+8", bc.toString());

        Datetime yearZero = new Datetime(0, 1, 1, 0, 0, 0, 0, 8);
        check("全参构造-year=0重置为1", yearZero.YEAR == 1, "year=1", String.valueOf(yearZero.YEAR));
        check("全参构造-year=0 toString",
                yearZero.toString().equals("AD 1/01/01 00:00:00.000 UTC+8"),
                "AD 1/01/01 00:00:00.000 UTC+8", yearZero.toString());
    }

    private static void testInvalidFullConstructor() {
        Datetime invalidDate = new Datetime(2023, 2, 29, 0, 0, 0, 0, 8);
        check("无效日期回退当前", isValidDateTimeLike(invalidDate), "合法当前日期时间", invalidDate.toString());

        Datetime invalidHour = new Datetime(2024, 1, 1, 24, 0, 0, 0, 8);
        check("无效小时回退当前", isValidDateTimeLike(invalidHour), "合法当前日期时间", invalidHour.toString());

        Datetime invalidTimezone = new Datetime(2024, 1, 1, 0, 0, 0, 0, 13);
        check("无效时区回退当前", isValidDateTimeLike(invalidTimezone), "合法当前日期时间", invalidTimezone.toString());
    }

    private static void testDatePartialConstructors() {
        Datetime d = new Datetime(2024, 2, 29, 8, true);
        boolean fieldsOk = d.YEAR == 2024 && d.MONTH == 2 && d.DAY == 29
                && d.HOUR == 0 && d.MINUTE == 0 && d.SECOND == 0
                && d.MILLISECOND == 0 && d.TIME_ZONE == 8;
        check("纯日期构造-字段", fieldsOk, "纯日期字段", d.toString());
        check("纯日期构造-toString",
                d.toString().equals("AD 2024/02/29 00:00:00.000 UTC+8"),
                "AD 2024/02/29 00:00:00.000 UTC+8", d.toString());

        Datetime invalidDate = new Datetime(2023, 2, 29, 8, true);
        check("纯日期构造-无效日期回退", isValidDateTimeLike(invalidDate), "合法当前日期", invalidDate.toString());

        Datetime defaultZoneDate = new Datetime(2024, 2, 29, true);
        check("纯日期构造-默认时区",
                defaultZoneDate.toString().equals("AD 2024/02/29 00:00:00.000 UTC+8"),
                "AD 2024/02/29 00:00:00.000 UTC+8", defaultZoneDate.toString());
    }

    private static void testTimePartialConstructors() {
        Datetime t = new Datetime(23, 59, 58, 8, false);
        boolean fieldsOk = t.YEAR == Integer.MIN_VALUE && t.MONTH == Integer.MIN_VALUE && t.DAY == Integer.MIN_VALUE
                && t.HOUR == 23 && t.MINUTE == 59 && t.SECOND == 58
                && t.MILLISECOND == 0 && t.TIME_ZONE == 8;
        check("纯时间构造-字段", fieldsOk, "纯时间字段", t.toString());
        check("纯时间构造-toString",
                t.toString().equals("23:59:58.000 UTC+8"),
                "23:59:58.000 UTC+8", t.toString());

        Datetime invalidTime = new Datetime(24, 0, 0, 8, false);
        check("纯时间构造-无效时间回退", isValidPureTime(invalidTime), "合法当前时间", invalidTime.toString());

        Datetime defaultZoneTime = new Datetime(23, 59, 58, false);
        check("纯时间构造-默认时区",
                defaultZoneTime.toString().equals("23:59:58.000 UTC+8"),
                "23:59:58.000 UTC+8", defaultZoneTime.toString());
    }

    private static void testTimestampConstructors() {
        Datetime epoch = new Datetime(0L, 0);
        check("时间戳构造-公元元年",
                epoch.toString().equals("AD 1/01/01 00:00:00.000 UTC+0"),
                "AD 1/01/01 00:00:00.000 UTC+0", epoch.toString());

        Datetime unixEpoch = new Datetime(62135596800000L, 0);
        check("时间戳构造-1970-01-01",
                unixEpoch.toString().equals("AD 1970/01/01 00:00:00.000 UTC+0"),
                "AD 1970/01/01 00:00:00.000 UTC+0", unixEpoch.toString());

        Datetime offset = new Datetime(0L, 8);
        check("时间戳构造-时区偏移",
                offset.toString().equals("AD 1/01/01 08:00:00.000 UTC+8"),
                "AD 1/01/01 08:00:00.000 UTC+8", offset.toString());

        Datetime dayEpoch = new Datetime(0, 0);
        check("日时间戳构造-公元元年",
                dayEpoch.toString().equals("AD 1/01/01 00:00:00.000 UTC+0"),
                "AD 1/01/01 00:00:00.000 UTC+0", dayEpoch.toString());

        Datetime dayUnix = new Datetime(719162, 0);
        check("日时间戳构造-1970-01-01",
                dayUnix.toString().equals("AD 1970/01/01 00:00:00.000 UTC+0"),
                "AD 1970/01/01 00:00:00.000 UTC+0", dayUnix.toString());
    }

    private static void testStaticTimestamp() {
        check("静态timestamp-1年",
                Datetime.timestamp(1, 1, 1, 0, 0, 0, 0, 0) == 0L,
                "0", String.valueOf(Datetime.timestamp(1, 1, 1, 0, 0, 0, 0, 0)));

        check("静态timestamp-1970",
                Datetime.timestamp(1970, 1, 1, 0, 0, 0, 0, 0) == 62135596800000L,
                "62135596800000", String.valueOf(Datetime.timestamp(1970, 1, 1, 0, 0, 0, 0, 0)));

        check("静态timestamp-1970+8时区",
                Datetime.timestamp(1970, 1, 1, 0, 0, 0, 0, 8) == 62135568000000L,
                "62135568000000", String.valueOf(Datetime.timestamp(1970, 1, 1, 0, 0, 0, 0, 8)));

        check("静态timestampUnix-1970",
                Datetime.timestampUnix(1970, 1, 1, 0, 0, 0, 0, 0) == 0L,
                "0", String.valueOf(Datetime.timestampUnix(1970, 1, 1, 0, 0, 0, 0, 0)));

        check("静态timestampUnix-1970+8时区",
                Datetime.timestampUnix(1970, 1, 1, 0, 0, 0, 0, 8) == -28800000L,
                "-28800000", String.valueOf(Datetime.timestampUnix(1970, 1, 1, 0, 0, 0, 0, 8)));

        check("静态timestampDay-1年",
                Datetime.timestampDay(1, 1, 1) == 0,
                "0", String.valueOf(Datetime.timestampDay(1, 1, 1)));

        check("静态timestampDay-1970",
                Datetime.timestampDay(1970, 1, 1) == 719162,
                "719162", String.valueOf(Datetime.timestampDay(1970, 1, 1)));
    }

    private static void testInstanceTimestamp() {
        Datetime dt = new Datetime(1970, 1, 1, 0, 0, 0, 0, 0);
        check("实例timestamp-1970",
                dt.timestamp() == 62135596800000L,
                "62135596800000", String.valueOf(dt.timestamp()));
        check("实例timestampUnix-1970",
                dt.timestampUnix() == 0L,
                "0", String.valueOf(dt.timestampUnix()));
        check("实例timestampDay-1970",
                dt.timestampDay() == 719162,
                "719162", String.valueOf(dt.timestampDay()));

        Datetime dt8 = new Datetime(1970, 1, 1, 0, 0, 0, 0, 8);
        check("实例timestampUnix-1970+8时区",
                dt8.timestampUnix() == -28800000L,
                "-28800000", String.valueOf(dt8.timestampUnix()));
    }

    private static void testWeekday() {
        check("星期-2024-01-01周一",
                Datetime.weekday(2024, 1, 1) == 1,
                "1", String.valueOf(Datetime.weekday(2024, 1, 1)));
        check("星期-2024-01-07周日",
                Datetime.weekday(2024, 1, 7) == 0,
                "0", String.valueOf(Datetime.weekday(2024, 1, 7)));
        check("星期-2024-02-29周四",
                Datetime.weekday(2024, 2, 29) == 4,
                "4", String.valueOf(Datetime.weekday(2024, 2, 29)));
        check("星期-2000-01-01周六",
                Datetime.weekday(2000, 1, 1) == 6,
                "6", String.valueOf(Datetime.weekday(2000, 1, 1)));

        Datetime date = new Datetime(2024, 1, 1, true);
        check("实例星期-2024-01-01",
                date.weekday() == 1,
                "1", String.valueOf(date.weekday()));

        Datetime pureTime = new Datetime(1, 0, 0, false);
        check("实例星期-纯时间返回MIN",
                pureTime.weekday() == Integer.MIN_VALUE,
                String.valueOf(Integer.MIN_VALUE), String.valueOf(pureTime.weekday()));
    }

    private static void testDayInYear() {
        check("年内天数-2024-03-01",
                Datetime.dayInYear(2024, 3, 1) == 61,
                "61", String.valueOf(Datetime.dayInYear(2024, 3, 1)));
        check("年内天数-2023-03-01",
                Datetime.dayInYear(2023, 3, 1) == 60,
                "60", String.valueOf(Datetime.dayInYear(2023, 3, 1)));
        check("年内天数-2024-12-31",
                Datetime.dayInYear(2024, 12, 31) == 366,
                "366", String.valueOf(Datetime.dayInYear(2024, 12, 31)));
        check("年内天数-2023-12-31",
                Datetime.dayInYear(2023, 12, 31) == 365,
                "365", String.valueOf(Datetime.dayInYear(2023, 12, 31)));

        Datetime pureTime = new Datetime(1, 0, 0, false);
        check("实例年内天数-纯时间返回MIN",
                pureTime.dayInYear() == Integer.MIN_VALUE,
                String.valueOf(Integer.MIN_VALUE), String.valueOf(pureTime.dayInYear()));
    }

    private static void testAddDayStatic() {
        check("加天数-公元元年+0",
                Datetime.addDay(1, 1, 1, 0).toString().equals("AD 1/01/01 00:00:00.000 UTC+8"),
                "AD 1/01/01 00:00:00.000 UTC+8", Datetime.addDay(1, 1, 1, 0).toString());

        check("加天数-2024-02-28+1",
                Datetime.addDay(2024, 2, 28, 1).toString().equals("AD 2024/02/29 00:00:00.000 UTC+8"),
                "AD 2024/02/29 00:00:00.000 UTC+8", Datetime.addDay(2024, 2, 28, 1).toString());

        check("加天数-2023-02-28+1",
                Datetime.addDay(2023, 2, 28, 1).toString().equals("AD 2023/03/01 00:00:00.000 UTC+8"),
                "AD 2023/03/01 00:00:00.000 UTC+8", Datetime.addDay(2023, 2, 28, 1).toString());

        check("加天数-2024-02-29+1",
                Datetime.addDay(2024, 2, 29, 1).toString().equals("AD 2024/03/01 00:00:00.000 UTC+8"),
                "AD 2024/03/01 00:00:00.000 UTC+8", Datetime.addDay(2024, 2, 29, 1).toString());

        check("加天数-公元元年-1",
                Datetime.addDay(1, 1, 1, -1).toString().equals("1/12/31 BC 00:00:00.000 UTC+8"),
                "1/12/31 BC 00:00:00.000 UTC+8", Datetime.addDay(1, 1, 1, -1).toString());

        check("加天数-公元元年+365",
                Datetime.addDay(1, 1, 1, 365).toString().equals("AD 2/01/01 00:00:00.000 UTC+8"),
                "AD 2/01/01 00:00:00.000 UTC+8", Datetime.addDay(1, 1, 1, 365).toString());

        check("加天数-公元元年+366",
                Datetime.addDay(1, 1, 1, 366).toString().equals("AD 2/01/02 00:00:00.000 UTC+8"),
                "AD 2/01/02 00:00:00.000 UTC+8", Datetime.addDay(1, 1, 1, 366).toString());

        check("加天数-世纪边界1900",
                Datetime.addDay(1900, 2, 28, 1).toString().equals("AD 1900/03/01 00:00:00.000 UTC+8"),
                "AD 1900/03/01 00:00:00.000 UTC+8", Datetime.addDay(1900, 2, 28, 1).toString());

        check("加天数-世纪边界2000",
                Datetime.addDay(2000, 2, 28, 1).toString().equals("AD 2000/02/29 00:00:00.000 UTC+8"),
                "AD 2000/02/29 00:00:00.000 UTC+8", Datetime.addDay(2000, 2, 28, 1).toString());
    }

    private static void testAddDayInstance() {
        Datetime dt = new Datetime(2024, 2, 29, 12, 34, 56, 789, 8);
        Datetime added = dt.addDay(1);
        check("实例加天数-保留时间",
                added.toString().equals("AD 2024/03/01 12:34:56.789 UTC+8"),
                "AD 2024/03/01 12:34:56.789 UTC+8", added.toString());

        Datetime pureTime = new Datetime(1, 2, 3, false);
        Datetime timeAdded = pureTime.addDay(10);
        check("实例加天数-纯时间返回this",
                timeAdded == pureTime,
                "同一对象", String.valueOf(timeAdded == pureTime));
    }

    private static void testIntervalDayStatic() {
        check("日期差-同年同日",
                Datetime.intervalDay(2024, 1, 1, 2024, 1, 1) == 0L,
                "0", String.valueOf(Datetime.intervalDay(2024, 1, 1, 2024, 1, 1)));

        check("日期差-后一天",
                Datetime.intervalDay(2024, 1, 1, 2024, 1, 2) == 1L,
                "1", String.valueOf(Datetime.intervalDay(2024, 1, 1, 2024, 1, 2)));

        check("日期差-前一天",
                Datetime.intervalDay(2024, 1, 2, 2024, 1, 1) == -1L,
                "-1", String.valueOf(Datetime.intervalDay(2024, 1, 2, 2024, 1, 1)));

        check("日期差-跨闰年",
                Datetime.intervalDay(2024, 1, 1, 2025, 1, 1) == 366L,
                "366", String.valueOf(Datetime.intervalDay(2024, 1, 1, 2025, 1, 1)));

        check("日期差-公元元年到1970",
                Datetime.intervalDay(1, 1, 1, 1970, 1, 1) == 719162L,
                "719162", String.valueOf(Datetime.intervalDay(1, 1, 1, 1970, 1, 1)));
    }

    private static void testIntervalDayInstance() {
        Datetime start = new Datetime(2024, 1, 1, true);
        Datetime end = new Datetime(2024, 1, 2, true);

        check("实例日期差-后一天",
                start.intervalDay(end) == 1L,
                "1", String.valueOf(start.intervalDay(end)));

        check("实例日期差-前一天",
                end.intervalDay(start) == -1L,
                "-1", String.valueOf(end.intervalDay(start)));

        Datetime pureTime = new Datetime(1, 0, 0, false);
        check("实例日期差-纯时间返回MIN",
                start.intervalDay(pureTime) == Long.MIN_VALUE,
                String.valueOf(Long.MIN_VALUE), String.valueOf(start.intervalDay(pureTime)));
    }

    private static void testSecondInDayAndInterval() {
        check("日中秒-0点",
                Datetime.secondInDay(0, 0, 0) == 0,
                "0", String.valueOf(Datetime.secondInDay(0, 0, 0)));

        check("日中秒-23:59:59",
                Datetime.secondInDay(23, 59, 59) == 86399,
                "86399", String.valueOf(Datetime.secondInDay(23, 59, 59)));

        Datetime t = new Datetime(1, 2, 3, false);
        check("实例日中秒-01:02:03",
                t.secondInDay() == 3723,
                "3723", String.valueOf(t.secondInDay()));

        check("纯时间差-静态",
                Datetime.intervalSecondInDay(0, 0, 0, 23, 59, 59) == 86399,
                "86399", String.valueOf(Datetime.intervalSecondInDay(0, 0, 0, 23, 59, 59)));

        check("纯时间差-静态反向",
                Datetime.intervalSecondInDay(23, 59, 59, 0, 0, 0) == -86399,
                "-86399", String.valueOf(Datetime.intervalSecondInDay(23, 59, 59, 0, 0, 0)));

        Datetime start = new Datetime(0, 0, 0, 8, false);
        Datetime end = new Datetime(1, 0, 0, 8, false);
        check("实例纯时间差-同时区",
                start.intervalSecondInDay(end) == 3600,
                "3600", String.valueOf(start.intervalSecondInDay(end)));

        Datetime startFar = new Datetime(0, 0, 0, 12, false);
        Datetime endFar = new Datetime(23, 59, 59, -12, false);
        check("实例纯时间差-跨时区",
                startFar.intervalSecondInDay(endFar) == 172799,
                "172799", String.valueOf(startFar.intervalSecondInDay(endFar)));
    }

    private static void testCompareTo() {
        Datetime a = new Datetime(2024, 1, 1, true);
        Datetime b = new Datetime(2024, 1, 1, true);
        check("比较-相同日期", a.compareTo(b) == 0, "0", String.valueOf(a.compareTo(b)));

        Datetime later = new Datetime(2024, 1, 2, true);
        check("比较-较早日期", a.compareTo(later) < 0, "<0", String.valueOf(a.compareTo(later)));
        check("比较-较晚日期", later.compareTo(a) > 0, ">0", String.valueOf(later.compareTo(a)));

        Datetime dateTime = new Datetime(2000, 1, 1, 12, 0, 0, 0, 8);
        Datetime pureTime = new Datetime(12, 0, 0, 8, false);
        check("比较-混合仅比时间相等", dateTime.compareTo(pureTime) == 0, "0", String.valueOf(dateTime.compareTo(pureTime)));
    }

    private static void testToString() {
        Datetime ad = new Datetime(2024, 1, 2, 3, 4, 5, 6, 8);
        check("toString-AD",
                ad.toString().equals("AD 2024/01/02 03:04:05.006 UTC+8"),
                "AD 2024/01/02 03:04:05.006 UTC+8", ad.toString());

        Datetime negativeZone = new Datetime(2024, 1, 2, 3, 4, 5, 6, -5);
        check("toString-负时区",
                negativeZone.toString().equals("AD 2024/01/02 03:04:05.006 UTC-5"),
                "AD 2024/01/02 03:04:05.006 UTC-5", negativeZone.toString());

        Datetime bc = new Datetime(-10, 12, 31, 23, 59, 59, 0, 0);
        check("toString-公元前",
                bc.toString().equals("10/12/31 BC 23:59:59.000 UTC+0"),
                "10/12/31 BC 23:59:59.000 UTC+0", bc.toString());

        Datetime pureTime = new Datetime(23, 59, 58, 8, false);
        check("toString-纯时间",
                pureTime.toString().equals("23:59:58.000 UTC+8"),
                "23:59:58.000 UTC+8", pureTime.toString());
    }

    private static void testNowValid() {
        int[] now = Datetime.now(8);
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

        int[] nowDefault = Datetime.now();
        check("now()使用默认时区", nowDefault[7] == 8, "8", String.valueOf(nowDefault[7]));

        long timestampNow = Datetime.timestampNow();
        check("timestampNow为正", timestampNow > 0, ">0", String.valueOf(timestampNow));

        long unixNow = Datetime.timestampUnixNow();
        check("timestampUnixNow为正", unixNow > 0, ">0", String.valueOf(unixNow));
    }
}