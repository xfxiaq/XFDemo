package com.xfhy.common.utils;

import java.io.IOException;
import java.lang.reflect.Field;
import java.text.DateFormat;
import java.util.Date;

import org.codehaus.jackson.JsonParseException;
import org.codehaus.jackson.JsonParser;
import org.codehaus.jackson.map.AnnotationIntrospector;
import org.codehaus.jackson.map.DeserializationConfig;
import org.codehaus.jackson.map.DeserializationContext;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.map.SerializationConfig;
import org.codehaus.jackson.map.introspect.JacksonAnnotationIntrospector;
import org.codehaus.jackson.type.JavaType;

/**
 * 日期格式对象Mapper。
 * 
 * @author liuyg
 * @version 1.0
 */
public class DateFormatObjectMapper extends ObjectMapper {
    
    /**
     * 对象Mapper构造函数。
     */
    public DateFormatObjectMapper() {
        // 国际标准日期格式
        final DateFormat gmtDateFormat = new GmtDateFormat();
        final AnnotationIntrospector introspector =
                AnnotationIntrospector.pair(new JacksonAnnotationIntrospector(),
                        ObjectMapper.DEFAULT_ANNOTATION_INTROSPECTOR);
        final SerializationConfig sconfig =
                new SerializationConfig(ObjectMapper.DEFAULT_INTROSPECTOR, introspector,
                        ObjectMapper.STD_VISIBILITY_CHECKER, null, null, this._typeFactory, null);
        final DeserializationConfig dconfig =
                new DeserializationConfig(ObjectMapper.DEFAULT_INTROSPECTOR, introspector,
                        ObjectMapper.STD_VISIBILITY_CHECKER, null, null, this._typeFactory, null);
        this.setSerializationConfig(sconfig.withDateFormat(gmtDateFormat));
        this.setDeserializationConfig(dconfig.withDateFormat(gmtDateFormat));
        this.configure(SerializationConfig.Feature.WRITE_DATES_AS_TIMESTAMPS, false);
    }
    
    /*
     * (non-Javadoc)
     * @see
     * org.codehaus.jackson.map.ObjectMapper#_readMapAndClose(org.codehaus.jackson
     * .JsonParser, org.codehaus.jackson.type.JavaType)
     */
    @Override
    protected Object _readMapAndClose(final JsonParser jp, final JavaType valueType) throws IOException,
            JsonParseException, JsonMappingException {
        // BUG处理： 解决Jackson的标准时区格式日期字符串（“yyyy-MM-dd'T'HH:mm:ss.SSSZ”）反序列化bug：原因是对于GMT格式字符串，Jackson会认为是number类型，并只截取前四位的值，导致转换后的日期不对
        if (valueType.getRawClass().equals(Date.class)) {
            return this.readDateTypeObject(jp, valueType);
        } else {
            return super._readMapAndClose(jp, valueType);
        }
    }
    
    /**
     * 反序列化日期类型的值。
     * 
     * @param jp JsonParser
     * @param valueType 值类型
     * @return 日期类型的值
     * @throws IOException IO异常
     */
    private Object readDateTypeObject(final JsonParser jp, final JavaType valueType) throws IOException {
        final DeserializationConfig cfg = this.copyDeserializationConfig();
        final DeserializationContext ctxt = this._createDeserializationContext(jp, cfg);
        try {
            final String inputValue = this.getInputStrValue(jp.getInputSource());
            if (inputValue == null) {
                return null;
            } else {
                return ctxt.parseDate(inputValue);
            }
        } catch (final Exception iae) {
            throw ctxt.weirdStringException(valueType.getRawClass(),
                    "not a valid representation (error: " + iae.getMessage() + ")");
        } finally {
            try {
                jp.close();
            } catch (final IOException ioe) {
            }
        }
    }
    
    /**
     * 取得原始字符串
     * 
     * @param inputSource 输入源
     * @return 原始字符串
     * @throws Exception 反射取值错误的时候，抛异常
     */
    private String getInputStrValue(final Object inputSource) throws Exception {
        final Field inputStrField = inputSource.getClass().getDeclaredField("str");
        inputStrField.setAccessible(true);
        final Object inputStrValue = inputStrField.get(inputSource);
        if ((inputStrValue == null) || "".equals(inputStrValue.toString().trim())) {
            return null;
        } else {
            return inputStrValue.toString().trim();
        }
    }
}
