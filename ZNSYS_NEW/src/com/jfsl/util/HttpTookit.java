package com.jfsl.util;

import org.apache.commons.httpclient.HttpClient; 
import org.apache.commons.httpclient.HttpMethod; 
import org.apache.commons.httpclient.HttpStatus; 
import org.apache.commons.httpclient.URIException; 
import org.apache.commons.httpclient.methods.GetMethod; 
import org.apache.commons.httpclient.methods.PostMethod; 
import org.apache.commons.httpclient.params.HttpMethodParams; 
import org.apache.commons.httpclient.util.URIUtil; 
import org.apache.commons.lang.StringUtils; 
import org.apache.commons.logging.Log; 
import org.apache.commons.logging.LogFactory; 

import java.io.IOException; 
import java.util.HashMap; 
import java.util.Map; 

/** 
* HTTP������ 
* 
* @author leizhimin 2009-6-19 16:36:18 
*/ 
public final class HttpTookit { 
        private static Log log = LogFactory.getLog(HttpTookit.class); 

        /** 
         * ִ��һ��HTTP GET���󣬷���������Ӧ��HTML 
         * 
         * @param url                 �����URL��ַ 
         * @param queryString ����Ĳ�ѯ����,����Ϊnull 
         * @return ����������Ӧ��HTML 
         */ 
        public static String doGet(String url, String queryString) { 
                String response = null; 
                HttpClient client = new HttpClient(); 
                HttpMethod method = new GetMethod(url); 
                try { 
                        if (StringUtils.isNotBlank(queryString)) 
                                method.setQueryString(URIUtil.encodeQuery(queryString)); 
                        client.executeMethod(method); 
                        if (method.getStatusCode() == HttpStatus.SC_OK) { 
                                response = method.getResponseBodyAsString(); 
                        } 
                } catch (URIException e) { 
                        log.error("ִ��HTTP Get����ʱ�������ѯ�ַ�����" + queryString + "�������쳣��", e); 
                } catch (IOException e) { 
                        log.error("ִ��HTTP Get����" + url + "ʱ�������쳣��", e); 
                } finally { 
                        method.releaseConnection(); 
                } 
                return response; 
        } 

        /** 
         * ִ��һ��HTTP POST���󣬷���������Ӧ��HTML 
         * 
         * @param url        �����URL��ַ 
         * @param params ����Ĳ�ѯ����,����Ϊnull 
         * @return ����������Ӧ��HTML 
         */ 
        public static String doPost(String url, Map<String,String> params) { 
                String response = null; 
                HttpClient client = new HttpClient(); 
                HttpMethod method = new PostMethod(url); 
               /* for (Iterator it = params.entrySet().iterator(); it.hasNext();) { 

                }*/ 
                //����Http Post���� 
                if (params != null) { 
                        HttpMethodParams p = new HttpMethodParams(); 
                        for (Map.Entry<String, String> entry : params.entrySet()) { 
                                p.setParameter(entry.getKey(), entry.getValue()); 
                        } 
                        method.setParams(p); 
                } 
                try { 
                        client.executeMethod(method); 
                        if (method.getStatusCode() == HttpStatus.SC_OK) { 
                                response = method.getResponseBodyAsString(); 
                        } 
                } catch (IOException e) { 
                        log.error("ִ��HTTP Post����" + url + "ʱ�������쳣��", e); 
                } finally { 
                        method.releaseConnection(); 
                } 

                return response; 
        } 

        public static void main(String[] args) { 
                String x = doPost("http://lavasoft.blog.51cto.com/62575/64994", new HashMap<String, String>()); 
                System.out.println(x); 
        } 
}