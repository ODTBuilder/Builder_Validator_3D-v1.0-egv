/**
 * 
 */
package com.gitrnd.gdsbuilder.geogig.command.object;

import java.util.Base64;

import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.ResourceAccessException;

import org.springframework.web.client.RestTemplate;

import com.gitrnd.gdsbuilder.geogig.GeogigCommandException;
import com.gitrnd.gdsbuilder.geogig.type.GeogigCat;

/**
 * Geogig Cat Command 실행 클래스.
 * 
 * @author DY.Oh
 *
 */
public class CatObject {

	/**
	 * geogig
	 */
	private static final String geogig = "geogig";
	/**
	 * command
	 */
	private static final String command = "cat";
	/**
	 * objectid parameter
	 */
	private static final String param_objectid = "objectid=";

	/**
	 * Commit ID, Tree, Feature, Feature Type 또는 Tag 등 Object ID를 갖는 Geogig 객체에
	 * 대한 정보를 반환함.
	 * 
	 * @param baseURL
	 *            Geogig Repository가 위치한 Geoserver BaseURL
	 *            <p>
	 *            (ex. http://localhost:8080/geoserver)
	 * @param username
	 *            Geoserver 사용자 ID
	 * @param password
	 *            Geoserver 사용자 PW
	 * @param repository
	 *            Geogig Repository명
	 * @param objectid
	 *            Geogig Object ID
	 * @return Command 실행 성공 - Commit ID, Tree, Feature, Feature Type 또는 Tag에 대한
	 *         정보 반환
	 *         <p>
	 *         Command 실행 실패 - error 반환
	 * 
	 * @author DY.Oh
	 */
	public GeogigCat executeCommand(String baseURL, String username, String password, String repository,
			String objectid) {

		HttpComponentsClientHttpRequestFactory factory = new HttpComponentsClientHttpRequestFactory();
		factory.setReadTimeout(5000);
		factory.setConnectTimeout(3000);
		CloseableHttpClient httpClient = HttpClientBuilder.create().setMaxConnTotal(100).setMaxConnPerRoute(5).build();
		factory.setHttpClient(httpClient);
		RestTemplate restTemplate = new RestTemplate(factory);

		// header
		HttpHeaders headers = new HttpHeaders();
		String user = username + ":" + password;
		String encodedAuth = "Basic " + Base64.getEncoder().encodeToString(user.getBytes());
		headers.setContentType(MediaType.APPLICATION_XML);
		headers.add("Authorization", encodedAuth);

		// url
		String url = baseURL + "/" + geogig + "/repos/" + repository + "/" + command + "?" + param_objectid + objectid;

		// request
		HttpEntity<MultiValueMap<String, Object>> requestEntity = new HttpEntity<>(headers);
		ResponseEntity<GeogigCat> responseEntity = null;
		try {
			responseEntity = restTemplate.exchange(url, HttpMethod.GET, requestEntity, GeogigCat.class);
		} catch (HttpClientErrorException e) {
			throw new GeogigCommandException(e.getMessage(), e.getResponseBodyAsString(), e.getStatusCode().value());
		} catch (ResourceAccessException e) {
			throw new GeogigCommandException(e.getMessage());
		}
		return responseEntity.getBody();
	}

}
