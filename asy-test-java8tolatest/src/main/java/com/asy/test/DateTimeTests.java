package com.asy.test;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.*;
import java.time.format.DateTimeFormatter;
import java.time.temporal.*;
import java.util.Calendar;
import java.util.Date;

public class DateTimeTests {
    public static void main(String[] args) {
        //testOldStyle();
        //testLocalDate();
        //testLocalTime();
        //testLocalDateTime();
        testDuration();
        //testInstant();
        //testZones();
        testPeriod();
    }

    private static void testPeriod() {
        // Period uses date-based values, while Duration uses time-based values.
        System.out.println("--- period ---");
        LocalDate localDate1 = LocalDate.now();
        LocalDate localDate2 = localDate1.plusDays(1).plusWeeks(1);

        Period period1vs2 = Period.between(localDate1, localDate2);
        System.out.println(period1vs2); //P8D
        System.out.println(period1vs2.toTotalMonths());
        System.out.println(period1vs2.getDays());



        Period period2 = Period.ofYears(2);
        System.out.println(period2.toTotalMonths());

    }

    private static void testZones() {
        // ZoneId.getAvailableZoneIds().stream().forEach(z -> System.out.println(z));

        ZonedDateTime zonedDateTimeTallinn = ZonedDateTime.now(ZoneId.of("Europe/Tallinn"));
        ZonedDateTime zonedDateTimeOslo = ZonedDateTime.now(ZoneId.of("Europe/Oslo"));

        System.out.println(zonedDateTimeTallinn);
        System.out.println(zonedDateTimeOslo);
        // duration min = 0 below :
        System.out.println("Duration (mins) : " + Duration.between(zonedDateTimeOslo, zonedDateTimeTallinn).toMinutes());

        LocalDateTime localDateTime = LocalDateTime.now();
        System.out.println("Here : " + localDateTime);
        ZonedDateTime zonedDateTimeOsloNow = localDateTime.atZone(ZoneId.of("Europe/Oslo"));
        System.out.println("In Oslo : " + zonedDateTimeOsloNow);
        System.out.println("In -10 : "+localDateTime.atOffset(ZoneOffset.ofHours(-10)));

    }

    private static void testInstant() {
        Instant instant1 = Instant.now();
        System.out.println(instant1);
        System.out.println(instant1.getNano());
        //System.out.println(instant1.get(ChronoField.YEAR));//Unsupported field: Year

        LocalDateTime localDateTime = LocalDateTime.ofInstant(instant1, ZoneId.systemDefault());
        System.out.println(localDateTime);
    }

    private static void testDuration() {
        LocalDateTime localDateTime1 = LocalDateTime.now();
        LocalDateTime localDateTime2 = localDateTime1.plusHours(1).plusMinutes(1).plusSeconds(1);

        Duration duration1vs2 = Duration.between(localDateTime1, localDateTime2);
        System.out.println(duration1vs2); //PT1H1M1S
        System.out.println(duration1vs2.toSeconds());
        System.out.println(duration1vs2.toMinutes());
        System.out.println(duration1vs2.toMinutesPart());

        Duration duration2vs1 = Duration.between(localDateTime2, localDateTime1);
        System.out.println(duration2vs1); //PT-1H-1M-1S


        Duration duration2 = Duration.ofHours(2);
        System.out.println(duration2.toMinutes());

        //Duration duration3 = Duration.between(LocalDate.now(), LocalDate.now().plusDays(1));
        //System.out.println(duration3.getSeconds()); // unsupported unit for LocalDate.
    }

    private static void testOldStyle() {
        // old-style
        Date date = new Date();
        DateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
        // HH is hour in a day (starting from 0 to 23). hh are hours in am/pm format. kk is hour in day (starting from 1 to 24)
        System.out.println(dateFormat.format(date));

        //Calendar is better for manipulating time.
        Calendar calendar = Calendar.getInstance();
        System.out.println(dateFormat.format(calendar.getTime()));
    }


    private static void testLocalDate() {
        //LocalDate does not store or represent a time or time-zone

        //initialization and formatting
        LocalDate localDate = LocalDate.now();
        System.out.println(localDate); // default format is ISO-8601 : yyyy-MM-dd
        System.out.println(localDate.format(DateTimeFormatter.ofPattern("dd-MM-yyyy")));

        localDate = LocalDate.of(1943, 2, 23);
        System.out.println(localDate);

        localDate = LocalDate.ofYearDay(1977, 235);
        System.out.println(localDate);

        //localDate = LocalDate.ofYearDay(1977, 500); // valid values 1 - 365/366 (366:'DayOfYear 366' as '1977' is not a leap year)
        //localDate = LocalDate.of(1999, 2, 29); //Invalid date 'February 29' as '1999' is not a leap year

        // getters
        System.out.println(localDate.getMonth()); //todo localization?
        System.out.println("localDate.YEAR = " + localDate.get(ChronoField.YEAR));

        // operations
        LocalDate now = LocalDate.now();
        LocalDate newDate = now.plusDays(730);
        System.out.println(newDate);
        System.out.println(now.plusYears(10));
        System.out.println(now.plusMonths(12 * 10));
        System.out.println(now.plusDays(365 * 10));//todo leap

        System.out.println(now.withYear(2500));
        System.out.println("now.with(ChronoField.MONTH_OF_YEAR) = " +
                now.with(ChronoField.YEAR, 2500));

        System.out.println("now.with(TemporalAdjusters.lastDayOfMonth()) = " +
                now.with(TemporalAdjusters.lastDayOfMonth()));


        Date oldStyleDate = new Date();
        LocalDateTime localDateTimeFromDate = LocalDateTime.ofInstant(oldStyleDate.toInstant(), ZoneId.systemDefault());
        System.out.println(localDateTimeFromDate);

        java.sql.Date sqlDate = new java.sql.Date(oldStyleDate.getTime());
        LocalDate localDateFromSqlDate = sqlDate.toLocalDate();
        System.out.println(localDateFromSqlDate);
    }

    private static void testLocalTime() {
        //LocalTime does not store or represent a date or time-zone.

        //initialization and formatting
        LocalTime localTime = LocalTime.now();
        System.out.println(localTime); // default format is ISO-8601
        System.out.println(localTime.format(DateTimeFormatter.ofPattern("HH:mm:ss")));

        localTime = LocalTime.of(16, 2, 34, 2304);
        System.out.println(localTime);

        localTime = LocalTime.ofSecondOfDay(60*60*8);
        System.out.println(localTime);

        // getters
        localTime = LocalTime.now();
        System.out.println("minute is : " + localTime.getMinute());
        System.out.println("localTime.MINUTE_OF_HOUR = " + localTime.get(ChronoField.MINUTE_OF_HOUR));

        // operations
        LocalTime now = LocalTime.now();
        System.out.println("before: " + now);
        System.out.println("plusSeconds(30):" + now.plusSeconds(30));
        System.out.println("plusMinutes(60) : " + now.plusMinutes(60));
        System.out.println("plusHours(2) : "+now.plusHours(2));
        //System.out.println(now.plus(365, TemporalUnit));//todo leap

        System.out.println(now.withHour(10));
        System.out.println("now.with(ChronoField.HOUR_OF_DAY) = " +
                now.with(ChronoField.HOUR_OF_DAY, 10));

        System.out.println(now.plus(2, ChronoUnit.HOURS));
        System.out.println(LocalTime.of(1, 1).minusHours(2));
    }

    private static void testLocalDateTime() {
        //LocalDateTime does not store or represent a time-zone

        //initialization and formatting
        LocalDateTime localDateTime = LocalDateTime.now();
        System.out.println(localDateTime);
        System.out.println(localDateTime.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));

        localDateTime = LocalDateTime.of(2025, 4, 23, 9, 5, 16);
        System.out.println(localDateTime);

        localDateTime = LocalDateTime.of(LocalDate.now(), LocalTime.now());
        System.out.println(localDateTime);


        // getters
        localDateTime = LocalDateTime.now();
        System.out.println("minute is : " + localDateTime.getMinute());
        System.out.println("localDateTime.MINUTE_OF_HOUR = " + localDateTime.get(ChronoField.MINUTE_OF_HOUR));

        // operations
        LocalDateTime now = LocalDateTime.now();
        System.out.println("before: " + now);
        System.out.println("plusSeconds(30):" + now.plusSeconds(30));
        System.out.println("plusMinutes(60) : " + now.plusMinutes(60));
        System.out.println("plusHours(2) : "+now.plusHours(2));
        System.out.println(now.plus(8*365, ChronoUnit.DAYS));//todo leap

        System.out.println(now.withHour(10));
        System.out.println("now.with(ChronoField.HOUR_OF_DAY) = " +
                now.with(ChronoField.HOUR_OF_DAY, 10));

        System.out.println(now.plus(2, ChronoUnit.HOURS));
        System.out.println(LocalTime.of(1, 1).minusHours(2));

        //conversions
        LocalDate localDate = now.toLocalDate();
        System.out.println("localDate of LocalDateTime :"+localDate);
        LocalTime localTime = now.toLocalTime();
        System.out.println("localTime of LocalDateTime :"+localTime);

    }


}
