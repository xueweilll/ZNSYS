package com.jfsl.util;

import java.text.SimpleDateFormat;
import java.util.Locale;

import net.sf.json.JsonConfig;
import net.sf.json.processors.JsonValueProcessor;

public class JsonDateValueProcessor implements JsonValueProcessor {
	//默认的格式化格式
    private String format ="yyyy-MM-dd HH:mm:ss";
    
    //构造函数
    public JsonDateValueProcessor() {
    	super();
    }
    
    // 构造函数
    public JsonDateValueProcessor(String format) {
    	super();
    	this.format = format;
    }
      
    public Object processArrayValue(Object value, JsonConfig config) {  
        return process(value);  
    }  
  
    public Object processObjectValue(String key, Object value, JsonConfig config) {  
        return process(value);  
    }  
      
    private Object process(Object value){  
          
        if(value instanceof java.util.Date){  
            SimpleDateFormat sdf = new SimpleDateFormat(format,Locale.UK);
            String result = sdf.format(value);
            return result;
        }  
        return value == null ? "" : value.toString();  
    }  
}
