package com.whlylc.server.utils;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * Try to merge into core lib
 * Created by Zeal on 2018/9/16 0016.
 */
public class URLUtils {
	
	/**
	 * URL编码
	 * @param src
	 * @return
	 */
	public static String urlEncode(String src) {
		return urlEncode(src, "UTF-8");
	}
	
	/**
	 * URL编码
	 * @param src
	 * @param charset
	 * @return
	 */
	public static String urlEncode(String src, String charset) {
		try {
			return URLEncoder.encode(src, charset);
		} 
		catch (UnsupportedEncodingException e) {
			e.printStackTrace();
			return src;
		}
	}
	
	/**
	 * URL解码
	 * @param str
	 * @return
	 */
	public static String urlDecode(String str) {
		return urlDecode(str, "UTF-8");
	}
	
	/**
	 * URL解码
	 * @param str
	 * @param charset
	 * @return
	 */
	public static String urlDecode(String str, String charset) {
		try {
		    return URLDecoder.decode(str, charset);
		}
		catch (Exception e) {
			e.printStackTrace();
			return str;
		}
	}

    /**
     * Parse query string
     * @param queryString
     * @return
     */
    public static Map<String,List<String>> parseURLQueryString(String queryString) {
        if (StringUtils.isBlank(queryString)) {
            return new LinkedHashMap<>(0);
        }
        String[] pairs = StringUtils.split(queryString, '&');
        if (pairs == null || pairs.length <= 0) {
            return new LinkedHashMap<>(0);
        }
        Map<String,List<String>> map = new LinkedHashMap<>(pairs.length);
        for (String pair : pairs) {
            if (StringUtils.isBlank(pair)) {
                continue;
            }
            int index = pair.indexOf('=');
            String key = null;
            String value = null;
            if (index == -1) {
                //FIXME Ignore it for invalid format?
                key = pair;
                value = "";
            }
            else {
                key = pair.substring(0, index);
                value = pair.substring(index + 1);
            }
            List<String> values = map.get(key);
            if (values == null) {
                values = new ArrayList<>(4);
                map.put(key, values);
            }
            values.add(value);
        }
        return map;
    }

}
