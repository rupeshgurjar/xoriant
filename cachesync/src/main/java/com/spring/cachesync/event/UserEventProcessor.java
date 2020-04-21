package com.spring.cachesync.event;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.MalformedURLException;
import java.net.Socket;
import java.net.SocketAddress;
import java.net.SocketTimeoutException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import org.springframework.context.ApplicationListener;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import com.spring.cachesync.domain.User;

@Component
public class UserEventProcessor implements ApplicationListener<UserEvent> {
	static List<String> serverList = new ArrayList<String>();
	static List<User> updateUserCache = new ArrayList<>();
	RestTemplate restTemplate = new RestTemplate();

	@Override
	public void onApplicationEvent(UserEvent event) {
		UserEvent userEvent = event;
		serverList = userEvent.getServerList();
		updateUserCache = userEvent.getUserDao();
		updateCache(updateUserCache, serverList);
	}

	private void updateCache(List<User> user, List<String> servers) {

		String url = null;
		for (String host : servers) {
			try {
				url = "http://" + host + "/cache-synch/cache";
				restTemplate.postForObject(url, user, String.class);
			} catch (Exception e) {
				System.err.println("url is added :  "+ url);
				serverList.add(url);
			}
		}
	}

	public boolean checkServerStatus(String url) {
		boolean isAlive = false;
		Socket socket = null;
		try {
			URL myUrl = new URL(url);
			String hostName = myUrl.getHost();
			Integer port = myUrl.getPort();
			SocketAddress socketAdr = new InetSocketAddress(hostName, port);
			socket = new Socket();
			socket.connect(socketAdr, 1000);
			socket.close();
			isAlive = true;
			System.err.println("Down  Server calling started ");
			restTemplate.postForObject(url, updateUserCache, String.class);
			System.err.println("Down  Server calling end ");
		} catch (SocketTimeoutException e) {
			System.err.println("SocketTimeoutException checkServerStatus URL ->> " + url + " is not connected!!!");
		} catch (IOException e) {
			System.err.println("IOException checkServerStatus URL ->> " + url + " is not connected!!!");
		} finally {
			if (!socket.isClosed() || socket.isConnected()) {
				try {
					socket.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}

		return isAlive;
	}

	@Scheduled(fixedDelay = 5000)
	public void cacheUpdateForRestartedServer() {
		System.err.println("cacheUpdateForRestartedServer  Server calling  ");
		if (null != serverList && serverList.size() > 0) {
			for (String url : serverList) {
				try {
					if (checkServerStatus(url)) {
						System.err.println("Down  Server calling started ");
						restTemplate.postForObject(url, updateUserCache, String.class);
						System.err.println("Down  Server calling end ");
					}
				} catch (Exception e) {
					System.err.println("Exception s URL ->> " + url + " is down!!!");
				}
			}
		}
	}
}