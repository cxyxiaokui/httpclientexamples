package cn.zhuoqianmingyue.getoperation;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.apache.log4j.Logger;
import org.junit.Test;

public class SimpleGetHttpClientDemo {
	
	private static Logger log = Logger.getLogger(SimpleGetHttpClientDemo.class);
	
	/**
	 *  �޲�����get����
	 */
	@Test
	public void withoutParameters() {
		//����HttpClinet
		CloseableHttpClient httpClient = HttpClients.createDefault();
		//���HTTP GET���� ���ʰٶ���ҳ
		HttpGet httpGet = new HttpGet("https://www.baidu.com");
		CloseableHttpResponse response = null;
		try {
			//ִ���������
			response = httpClient.execute(httpGet);
			//��ȡ����HTTP״̬��
			int satausCode = response.getStatusLine().getStatusCode();
			if(satausCode == 200 ){
				String content = EntityUtils.toString(response.getEntity(),"UTF-8");
				log.info("�ٶ���ҳҳ�棺"+content);
			}
		} catch (ClientProtocolException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	/**
	 *  �в����ķ���
	 * @throws URISyntaxException
	 */
	@Test
	public void withParameters() throws URISyntaxException {

		//����HttpClinet
		CloseableHttpClient httpClient = HttpClients.createDefault();
		//ƴ�ӷ���url ����
		URI uri = new URI("http://www.baidu.com/s");
		//ƴ���������� ?wd=httpclinet
		URIBuilder uriBuilder = new URIBuilder(uri);
		uriBuilder.setParameter("wd", "httpclient");
		URI uriParma = uriBuilder.build();
		//���HTTP GET���� ���ʰٶ�����httpclient�����Ϣ
		HttpGet httpGet = new HttpGet(uriParma);
		CloseableHttpResponse response = null;
		try {
			response = httpClient.execute(httpGet);
			int satausCode = response.getStatusLine().getStatusCode();
			if(satausCode == 200 ){
				String content = EntityUtils.toString(response.getEntity(),"UTF-8");
				log.info(content);
			}
		} catch (ClientProtocolException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	
	}
	/**
	 *  ����https://www.baidu.com ������Ҫ��������ͷ�� Host��www.baidu.com
	 * @throws URISyntaxException
	 */
	@Test
	public void withParametersByHttps() throws URISyntaxException {

		//����HttpClinet
		CloseableHttpClient httpClient = HttpClients.createDefault();
		//ƴ�ӷ���url ����
		URI uri = new URI("https://www.baidu.com/s");
		//ƴ���������� ?wd=httpclinet
		URIBuilder uriBuilder = new URIBuilder(uri);
		uriBuilder.setParameter("wd", "httpclient");
		URI uriParma = uriBuilder.build();
		//���HTTP GET���� ���ʰٶ�����httpclient�����Ϣ
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
			}
		} catch (ClientProtocolException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
}
