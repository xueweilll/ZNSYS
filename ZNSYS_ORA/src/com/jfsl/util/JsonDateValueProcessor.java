package com.jfsl.util;

import java.text.SimpleDateFormat;
import java.util.Locale;

import net.sf.json.JsonConfig;
import net.sf.json.processors.JsonValueProcessor;

public class JsonDateValueProcessor implements JsonValueProcessor {
	//Ĭ�ϵĸ�ʽ����ʽ
    private String format ="yyyy-MM-dd HH:mm:ss";
    
    //���캯��
    public JsonDateValueProcessor() {
    	super();
    }
    
    // ���캯��
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
