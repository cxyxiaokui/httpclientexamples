package cn.zhuoqianmingyue.getoperation;

import java.io.IOException;

import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.apache.log4j.Logger;
import org.junit.Test;

public class SimpleGetHttpClientDemo {
	
	private static Logger log = Logger.getLogger(SimpleGetHttpClientDemo.class);
	
	/**
	 *  无参数的get访问
	 */
	@Test
	public void withoutParameters() {
		//创建HttpClinet
		CloseableHttpClient httpClient = HttpClients.createDefault();
		//添加HTTP GET请求 访问百度首页
		HttpGet httpGet = new HttpGet("https://www.baidu.com");
		CloseableHttpResponse response = null;
		try {
			//执行请求访问
			response = httpClient.execute(httpGet);
			//获取返回HTTP状态码
			int satausCode = response.getStatusLine().getStatusCode();
			if(satausCode == 200 ){
				String content = EntityUtils.toString(response.getEntity(),"UTF-8");
				log.info("百度首页页面："+content);
			}
		} catch (ClientProtocolException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
