package cn.zhuoqianmingyue.postoperation;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.http.Consts;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.junit.Test;

import cn.zhuoqianmingyue.getoperation.SimpleGetHttpClientDemo;

public class SimplePostHttpClientDemo {
	
	private static Log log  =  LogFactory.getLog(SimplePostHttpClientDemo.class );   
	
	/**
	 * httpClient post 有参数请求
	 * @throws IOException 
	 * 
	 */
	@Test
	public void doPostWithParam() throws IOException{
		//创建HttpClinet
		CloseableHttpClient httpClient = HttpClients.createDefault();
		//添加HTTP POST请求 这里必须加上http：
		HttpPost httpPost = new HttpPost("http://localhost:8080/sbe/mvcUser/");
		//设置post参数  
        List<NameValuePair> parameters = new ArrayList<NameValuePair>();  
        //定义请求的参数  设置post参数  
        parameters.add(new BasicNameValuePair("name", "lijunkui"));
        parameters.add(new BasicNameValuePair("age", "18"));
        // 构造一个form表单式的实体  
        UrlEncodedFormEntity formEntity = new UrlEncodedFormEntity(parameters,Consts.UTF_8);
        //UrlEncodedFormEntity formEntity = new UrlEncodedFormEntity(parameters);  
        httpPost.setEntity(formEntity);  
		CloseableHttpResponse response = null;
		try {
			response = httpClient.execute(httpPost);
			
			int statusCode = response.getStatusLine().getStatusCode();
			if(statusCode == 200){
				String content = EntityUtils.toString(response.getEntity(), "UTF-8");
				log.info(content);
				EntityUtils.consume(response.getEntity());
			}
		} catch (ClientProtocolException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}finally {
			response.close();
		}
	}
}
