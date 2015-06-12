package com.javapapers.java.gcm;

import java.io.IOException;
import java.util.Set;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/GCMNotification")
public class GCMNotification extends HttpServlet {
	private static final long serialVersionUID = 1L;

	// Put your Google API Server Key here
	private static final String GOOGLE_SERVER_KEY = "AIzaSyBDCqjIu74xQcyHx_ABFnqMqbaOO7y_Ii4";

	// Put your Google Project number here
	final String GOOGLE_USERNAME = "497186030502" + "@gcm.googleapis.com";
//	final String GOOGLE_USERNAME = "497186030502";
	public GCMNotification() {
		super();
	}

	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		doPost(request, response);

	}

	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {

		try {
			String userMessage = "HELLO WORLD";
			//Set<String> regIdSet = RegIdManager.readFromFile();
			//String toDeviceRegId = (String) (regIdSet.toArray())[0];
			String toDeviceRegId = "dpbsdU0rYqI:APA91bGV8obrQjgL77znhW6Txn-k-TIyu3SJJeUixNREoyHhrHwGlu5KifEWX15QHr4UHKExibbWhrmEdgiFWOP77ael11md51yL-aLuI8deSMYUn4_3vGc";
			SmackCcsClient.sendMessage(GOOGLE_USERNAME, GOOGLE_SERVER_KEY,toDeviceRegId,userMessage);
			request.setAttribute("pushStatus", "Message Sent.");
		} /*catch (Exception ioe) {
			ioe.printStackTrace();
			request.setAttribute("pushStatus",
					"RegId required: " + ioe.toString());
		}*/ catch (Exception e) {
			e.printStackTrace();
			request.setAttribute("pushStatus", e.toString());
		}
		request.getRequestDispatcher("index.jsp").forward(request, response);
	}

}
