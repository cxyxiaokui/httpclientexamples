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
}
