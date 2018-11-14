package cn.zhuoqianmingyue.getoperation.baidusearch;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.apache.log4j.Logger;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import cn.zhuoqianmingyue.getoperation.SimpleGetHttpClientDemo;

public class BaiDuSearchUtil {
	
	private static Logger log = Logger.getLogger(SimpleGetHttpClientDemo.class);
	
	public static List<SearchData> search(String searchKey) throws URISyntaxException {
		
		List<SearchData> allSearchData = new ArrayList<SearchData>();
		int pageNumber = 1;
		boolean flag = true;
		while(flag) {
			
			String searchInfoHtml = getSearchInfoHtml(searchKey,pageNumber);
			
			boolean isEndpage = isEndPage(searchInfoHtml);
			if(!isEndpage) {
				List<SearchData> currentPageSearchDataList = parseDataHtml(searchInfoHtml);
				allSearchData.addAll(currentPageSearchDataList);
			}else {
				flag = false;
			}
			pageNumber++;
			log.info("当前爬取的页数为："+pageNumber);
		}
		return allSearchData;
	}
	
	/**
	 *  判断是否是最后一页
	 * @param searchInfoHtml
	 * @return
	 */
	private static boolean isEndPage(String searchInfoHtml) {
		Document doc = Jsoup.parse(searchInfoHtml);
		Element pageElement = doc.select("div#page").get(0);
		
		String html = pageElement.html();
		if(html.indexOf("下一页")!=-1) {
			return false;
		}
		return true;
	}
	/**
	 *  解析搜索内容标题和连接
	 * @param searchInfoHtml
	 * @return
	 */
	private static List<SearchData> parseDataHtml(String searchInfoHtml) {
		List<SearchData> searchDataList = new ArrayList<SearchData>(); 
		Document doc = Jsoup.parse(searchInfoHtml);
		
    	Elements select = doc.select("a[data-click]");
    	for (Element element : select) {
    		String url = element.attr("href");
    		if(!"javascript:;".equals(url)) {
    			String title = element.html().replace("<em>", "").replace("</em>", "");
    			if(!"百度快照".equals(title)) {
    				SearchData searchData = new SearchData();
        			searchData.setTitle(title);
        			searchData.setUrl(url);
        			searchDataList.add(searchData);
    			}
    		}
		}
    	
		return searchDataList;
	}
	/**
	 * //https://blog.csdn.net/qq_26816591/article/details/53335987(百度参数)
	 *   爬取百度搜索的html页面
	 * @param searchKey :搜索的关键词
	 * @param number:爬取的页数  
	 * @return
	 * @throws URISyntaxException
	 */
	private static String getSearchInfoHtml(String searchKey,Integer pageNumber) throws URISyntaxException {
		String searchInfoHtml = "";
		
		URI uriParma = dualWithParameter(searchKey,pageNumber);
		HttpGet httpGet = new HttpGet(uriParma);
		addHeaders(httpGet);
		
		CloseableHttpClient httpClient = HttpClients.createDefault();
		CloseableHttpResponse response = null;
		try {
			response = httpClient.execute(httpGet);
			int satausCode = response.getStatusLine().getStatusCode();
			if(satausCode == 200 ){
				searchInfoHtml = EntityUtils.toString(response.getEntity(),"UTF-8");
			}
		} catch (ClientProtocolException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return searchInfoHtml;
	}
	private static void addHeaders(HttpGet httpGet) {
		httpGet.addHeader("Host","www.baidu.com");
		httpGet.addHeader("User-Agent","Mozilla/5.0 (Macintosh; Intel Mac OS X 10_13_2) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/63.0.3239.84 Safari/537.36");
	}

	private static URI dualWithParameter(String searchKey, Integer pageNumber) throws URISyntaxException {
		URI uri = new URI("https://www.baidu.com/s");
		URIBuilder uriBuilder = new URIBuilder(uri);
		uriBuilder.setParameter("wd", searchKey);
		uriBuilder.setParameter("pn", ((pageNumber-1)*10)+"");
		uriBuilder.setParameter("oq", searchKey);
		uriBuilder.setParameter("ie", "utf-8");
		uriBuilder.setParameter("rsv_idx", "1");
		uriBuilder.setParameter("f", "8");
		uriBuilder.setParameter("rsv_bq", "1");
		uriBuilder.setParameter("tn", "baidu");
		URI uriParma = uriBuilder.build();
		return uriParma;
	}

	public static void main(String[] args) throws URISyntaxException {
		List<SearchData> allSearchData = BaiDuSearchUtil.search("httpclinet");
		System.out.println(allSearchData.size());
	}
}
