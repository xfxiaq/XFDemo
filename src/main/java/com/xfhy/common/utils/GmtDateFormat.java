package com.xfhy.common.utils;

import java.text.DateFormat;
import java.text.FieldPosition;
import java.text.ParseException;
import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

/**
 * 国际标准日期格式。
 * 
 * @author liuyg
 */
public class GmtDateFormat extends DateFormat {
    
    /** serialVersionUID */
    private static final long serialVersionUID = 565883377507889133L;
    
    /** 国际标准时间格式字符串 */
    protected static String DATETIME_FORMAT_ISO8601_STR = "yyyy-MM-dd'T'HH:mm:ss.SSSZ";
    /** 国际标准时间格式 */
    protected final static DateFormat DATETIME_FORMAT_ISO8601;
    /** 无时分秒的国际标准时间格式字符串 */
    protected final static String DATE_FORMAT_PLAIN_STR = "yyyy-MM-dd";
    /** 无时分秒的国际标准时间格式 */
    protected final static DateFormat DATE_FORMAT_PLAIN;
    
    /**
     * 静态块，构造国际标准时间格式。
     */
    static {
        DATETIME_FORMAT_ISO8601 = new SimpleDateFormat(GmtDateFormat.DATETIME_FORMAT_ISO8601_STR);
        GmtDateFormat.DATETIME_FORMAT_ISO8601.setTimeZone(TimeZone.getTimeZone("GMT"));
        
        DATE_FORMAT_PLAIN = new SimpleDateFormat(GmtDateFormat.DATE_FORMAT_PLAIN_STR);
        GmtDateFormat.DATE_FORMAT_PLAIN.setTimeZone(TimeZone.getTimeZone("GMT"));
    }
    
    /*
     * (non-Javadoc)
     * @see java.text.DateFormat#clone()
     */
    @Override
    public GmtDateFormat clone() {
        return new GmtDateFormat();
    }
    
    /*
     * (non-Javadoc)
     * @see java.text.DateFormat#format(java.util.Date, java.lang.StringBuffer,
     * java.text.FieldPosition)
     */
    @Override
    public StringBuffer format(final Date date, final StringBuffer toAppendTo, final FieldPosition pos) {
        final DateFormat _formatISO8601 = (DateFormat) GmtDateFormat.DATETIME_FORMAT_ISO8601.clone();
        final StringBuffer dateBuffer = _formatISO8601.format(date, toAppendTo, pos);
        final int start = dateBuffer.length() - 5;
        final int end = dateBuffer.length();
        dateBuffer.replace(start, end, "Z");
        return dateBuffer;
    }
    
    /*
     * (non-Javadoc)
     * @see java.text.DateFormat#parse(java.lang.String)
     */
    @Override
    public Date parse(String dateStr) throws ParseException {
        dateStr = dateStr.trim();
        final ParsePosition pos = new ParsePosition(0);
        final Date result = this.parse(dateStr, pos);
        if (result != null) {
            return result;
        }
        throw new ParseException(String.format(
                "Can not parse date \"%s\": not compatible with standard forms (%s)", dateStr,
                GmtDateFormat.DATETIME_FORMAT_ISO8601_STR), pos.getErrorIndex());
    }
    
    /*
     * (non-Javadoc)
     * @see java.text.DateFormat#parse(java.lang.String,
     * java.text.ParsePosition)
     */
    @Override
    public Date parse(final String dateStr, final ParsePosition pos) {
        if (this.looksLikeISO8601(dateStr)) { // also includes "plain"
            return this.parseAsISO8601(dateStr, pos);
        } else {
            // not supported
            return null;
        }
    }
    
    /**
     * 将国际标准日期格式字符串转换成日期类型对象。
     * 
     * @param dateStr 日期字符串
     * @param pos 转换位置
     * @return 国际标准日期
     */
    protected Date parseAsISO8601(String dateStr, final ParsePosition pos) {
        final int len = dateStr.length();
        final char c = dateStr.charAt(len - 1);
        // may be missing hours... if so, parse by plain format
        if ((len <= 10) && Character.isDigit(c)) {
            return ((DateFormat) GmtDateFormat.DATE_FORMAT_PLAIN.clone()).parse(dateStr, pos);
        }
        // GMT date string
        if (c == 'Z') {
            // may be missing milliseconds... if so, add
            if (dateStr.charAt(len - 4) == ':') {
                final StringBuilder sb = new StringBuilder(dateStr);
                sb.insert(len - 1, ".000");
                dateStr = sb.toString();
            }
            dateStr = dateStr.substring(0, dateStr.length() - 1) + "GMT-00:00";
            return ((DateFormat) GmtDateFormat.DATETIME_FORMAT_ISO8601.clone()).parse(dateStr, pos);
        } else {
            // not supported date string
            return null;
        }
    }
    
    /**
     * Overridable helper method used to figure out which of supported formats
     * is the likeliest match.
     */
    protected boolean looksLikeISO8601(final String dateStr) {
        if ((dateStr.length() >= 5) && Character.isDigit(dateStr.charAt(0))
                && Character.isDigit(dateStr.charAt(3)) && (dateStr.charAt(4) == '-')) {
            return true;
        }
        return false;
    }
}
