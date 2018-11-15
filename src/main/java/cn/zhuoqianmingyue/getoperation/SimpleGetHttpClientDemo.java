package cn.zhuoqianmingyue.getoperation;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.junit.Test;

public class SimpleGetHttpClientDemo {
	
	private static Log log  =  LogFactory.getLog(SimpleGetHttpClientDemo.class );   
	
	/**
	 *  无参数的get访问
	 * @throws IOException 
	 */
	@Test
	public void withoutParameters()  {
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
				EntityUtils.consume(response.getEntity());
			}
		} catch (ClientProtocolException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}finally {
			try {
				response.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	/**
	 *  有参数的访问
	 * @throws URISyntaxException
	 */
	@Test
	public void withParameters() throws URISyntaxException {

		//创建HttpClinet
		CloseableHttpClient httpClient = HttpClients.createDefault();
		//拼接访问url 进行
		URI uri = new URI("http://www.baidu.com/s");
		//拼接搜索内容 ?wd=httpclinet
		URIBuilder uriBuilder = new URIBuilder(uri);
		uriBuilder.setParameter("wd", "httpclient");
		URI uriParma = uriBuilder.build();
		//添加HTTP GET请求 访问百度搜索httpclient相关信息
		HttpGet httpGet = new HttpGet(uriParma);
		CloseableHttpResponse response = null;
		try {
			response = httpClient.execute(httpGet);
			int satausCode = response.getStatusLine().getStatusCode();
			if(satausCode == 200 ){
				String content = EntityUtils.toString(response.getEntity(),"UTF-8");
				log.info(content);
				EntityUtils.consume(response.getEntity());
			}
		} catch (ClientProtocolException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}finally {
			try {
				response.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	
	}
	/**
	 *  访问https://www.baidu.com 搜索需要设置请求头的 Host：www.baidu.com
	 * @throws URISyntaxException
	 */
	@Test
	public void withParametersByHttps() throws URISyntaxException {

		//创建HttpClinet
		CloseableHttpClient httpClient = HttpClients.createDefault();
		//拼接访问url 进行
		URI uri = new URI("https://www.baidu.com/s");
		//拼接搜索内容 ?wd=httpclinet
		URIBuilder uriBuilder = new URIBuilder(uri);
		uriBuilder.setParameter("wd", "httpclient");
		URI uriParma = uriBuilder.build();
		//添加HTTP GET请求 访问百度搜索httpclient相关信息
		HttpGet httpGet = new HttpGet(uriParma);
		
		httpGet.addHeader("Host","www.baidu.com");
		httpGet.addHeader("User-Agent","Mozilla/5.0 (Macintosh; Intel Mac OS X 10_13_2) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/63.0.3239.84 Safari/537.36");
		CloseableHttpResponse response = null;
		try {
			response = httpClient.execute(httpGet);
			int satausCode = response.getStatusLine().getStatusCode();
			if(satausCode == 200 ){
				String content = EntityUtils.toString(response.getEntity(),"UTF-8");
				log.info(content);
				EntityUtils.consume(response.getEntity());
			}
		} catch (ClientProtocolException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}finally {
			try {
				response.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	
}
