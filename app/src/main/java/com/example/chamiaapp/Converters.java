package com.example.chamiaapp;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Build;

import androidx.annotation.RequiresApi;
import androidx.room.TypeConverter;

import java.io.ByteArrayOutputStream;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

@RequiresApi(api = Build.VERSION_CODES.O)
public class Converters {

    private static final DateTimeFormatter formatter = DateTimeFormatter.ISO_LOCAL_TIME;
    private static final DateTimeFormatter formatter1 = DateTimeFormatter.ISO_LOCAL_DATE;
    private static final DateTimeFormatter formatter2 = DateTimeFormatter.ISO_LOCAL_DATE_TIME;


    // this converter will handle with begin time for Team class
    @TypeConverter
    public static LocalTime fromStringtoLocalTime(String value) {
        if (value != null) {
            return LocalTime.parse(value, formatter);
        }
        return null;
    }

    @TypeConverter
    public static String toStringTime(LocalTime time) {
        if (time != null) {
            return time.format(formatter);
        }
        return null;
    }

    // this converter will handle with date for Daily product class
    @TypeConverter
    public static LocalDate fromStringToLocaDate(String value) {
        if (value != null) {
            return LocalDate.parse(value, formatter1);
        }
        return null;
    }

    @TypeConverter
    public static String toStringDate (LocalDate date) {
        if (date != null) {
            return date.format(formatter1);
        }
        return null;
    }

    // this converter will handle with start_time for Schueduled product class
    @TypeConverter
    public static LocalDateTime fromStringToLocaDateTime (String value) {
        if (value != null) {
            return LocalDateTime.parse(value, formatter2);
        }
        return null;
    }

    @TypeConverter
    public static String toStringDateTime (LocalDateTime dateTime) {
        if (dateTime != null) {
            return dateTime.format(formatter2);
        }
        return null;
    }

    @TypeConverter
    public static byte[] bitmapToByteArray(Bitmap bitmap) {
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, outputStream);
        return outputStream.toByteArray();
    }

    @TypeConverter
    public Bitmap byteArrayToBitmap(byte[] byteArray) {
        if (byteArray == null || byteArray.length == 0) {
            return null;
        }

        return BitmapFactory.decodeByteArray(byteArray, 0, byteArray.length);
    }

}










