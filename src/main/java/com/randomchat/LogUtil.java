package com.randomchat;

import org.slf4j.MDC;

public class LogUtil {

	public static enum SERVICE {
		UPLOAD, DOWNLOAD,UPLOAD_TO_EXAMSOFT
	};

	public static enum KEYS {
		EXAMID, API, RESPONSE
	};

	/**
	 * Sets the log environment variables. These are env (win/mac), service
	 * (upload/download) and soap api.
	 * 
	 * @param env
	 * @param srv
	 * @param api
	 */
	//OVERRIDE THIS TO YOUR NEEDS
	/*public static void setEnvironment(OperatingSystem env, SERVICE srv, String api) {
		MDC.put("SERVICE", srv.toString());
		MDC.put("ENV", env.toString());
		MDC.put("API", api);
	}*/


	public static void clear() {
		MDC.clear();
	}

	/**
	 * Can add the context for the logger here.
	 * 
	 * @param key
	 * @param value
	 */
	public static void putContext(KEYS key, String value) {
		MDC.put(key.toString(), value);
	}

	/**
	 * Remove the log context.
	 * 
	 * @param key
	 */
	public static void removeContext(KEYS key) {
		MDC.remove(key.toString());
	}

}
