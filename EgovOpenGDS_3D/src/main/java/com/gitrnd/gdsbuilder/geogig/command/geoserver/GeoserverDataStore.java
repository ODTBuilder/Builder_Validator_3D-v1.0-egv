package com.gitrnd.gdsbuilder.geogig.command.geoserver;

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
import com.gitrnd.gdsbuilder.geogig.command.ResponseType;
import com.gitrnd.gdsbuilder.geogig.type.GeogigGeoserverDataStore;

/**
 * Geoserver DataStore GET Command 실행 클래스.
 * 
 * @author DY.Oh
 *
 */
public class GeoserverDataStore {

	/**
	 * rest
	 */
	private static final String rest = "rest";
	/**
	 * workspaces parameter
	 */
	private static final String command_workspaces = "workspaces";
	/**
	 * datastores parameter
	 */
	private static final String command_datastores = "datastores";

	/**
	 * WorkSpace에 존재하는 1개의 DataStore 정보를 반환함.
	 * 
	 * @param baseURL
	 *            Geoserver BaseURL
	 *            <p>
	 *            (ex. http://localhost:8080/geoserver)
	 * @param username
	 *            Geoserver 사용자 ID
	 * @param password
	 *            Geoserver 사용자 PW
	 * @param workspace
	 *            Geoserver workspace명
	 * @param datastore
	 *            조회할 Geoserver datastore명
	 * @param type
	 *            응답 타입(xml 또는 json)
	 * @return Command 실행 성공 - datastore에 해당하는 DataStore 정보 반환
	 *         <p>
	 *         Command 실행 실패 - error 반환
	 * 
	 * @author DY.Oh
	 */
	public GeogigGeoserverDataStore executeCommand(String baseURL, String username, String password, String workspace,
			String datastore, ResponseType type) {

		// restTemplate
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
		String url = baseURL + "/" + rest + "/" + command_workspaces + "/" + workspace + "/" + command_datastores + "/"
				+ datastore + "." + type;

		// request
		HttpEntity<MultiValueMap<String, Object>> requestEntity = new HttpEntity<>(headers);
		ResponseEntity<GeogigGeoserverDataStore> responseEntity = null;
		try {
			responseEntity = restTemplate.exchange(url, HttpMethod.GET, requestEntity, GeogigGeoserverDataStore.class);
		} catch (HttpClientErrorException e) {
			throw new GeogigCommandException(e.getMessage(), e.getResponseBodyAsString(), e.getStatusCode().value());
		} catch (ResourceAccessException e) {
			throw new GeogigCommandException(e.getMessage());
		}
		return responseEntity.getBody();
	}
}
