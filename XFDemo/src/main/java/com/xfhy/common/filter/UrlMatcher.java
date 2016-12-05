/**
 * 
 */
package com.xfhy.common.filter;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;

/**
 * URL 匹配器。
 * 
 * @author 
 * @version 1.0
 */
public final class UrlMatcher {
    /** 通配符 */
    private static final String WILDCARD = "*";
    /** URLs 分割符 */
    private static final String URL_SPLIT_MARK = ";";
    /** 回车换行匹配表达式 */
    private static final Pattern spacePattern = Pattern.compile("\t|\r|\n|\\s");
    
    private static final String[] EMPTY_ARRAY = {};
    
    /**
     * 将URLs字符串拆分成数组。
     * 
     * @param urls URLs
     * @return URL数组
     */
    public static String[] splitUrls(final String urls) {
        
        if (StringUtils.isNotBlank(urls)) {
            String originalUrls = urls;
            final Matcher matcher = UrlMatcher.spacePattern.matcher(originalUrls);
            originalUrls = matcher.replaceAll("");
            return originalUrls.split(UrlMatcher.URL_SPLIT_MARK);
        } else {
            return UrlMatcher.EMPTY_ARRAY;
        }
    }
    
    /**
     * 判断URL跟规则是否匹配。(支持通配符＊)
     * 
     * @param url
     * @param rule
     * @return
     */
    public static boolean matches(final String url, final String rule) {
        final String[] subRules = rule.split("\\*");
        final boolean matcheOfStartswith = !rule.startsWith(UrlMatcher.WILDCARD);
        final boolean matcheOfEndswith = !rule.endsWith(UrlMatcher.WILDCARD);
        int nextFindIndex = 0;
        for (int i = 0; i < subRules.length; i++) {
        	if (subRules[i].equals("")) {
        		continue;
        	}
            final int matchedIndex = url.indexOf(subRules[i], nextFindIndex);
            if ((i == 0) && matcheOfStartswith && (matchedIndex != 0)) {
                return false;
            }
            
            if (matchedIndex < 0) {
                return false;
            }
            
            if ((i == (subRules.length - 1)) && matcheOfEndswith) {
                return url.endsWith(subRules[i]);
            }
            nextFindIndex = matchedIndex + 1;
        }
        return true;
    }
    
    public static String getURL(final HttpServletRequest request) {
        final String contextPath = request.getContextPath();
        final String currentURL = request.getRequestURI();
        if (StringUtils.isNotBlank(contextPath)) {
            return currentURL.substring(contextPath.length());
        } else {
            return currentURL;
        }
    }
}
