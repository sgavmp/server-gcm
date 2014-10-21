package com.example;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.android.gcm.server.Message;
import com.google.android.gcm.server.MulticastResult;
import com.google.android.gcm.server.Result;
import com.google.android.gcm.server.Sender;

public class GCMNotification extends HttpServlet {
	private static final long serialVersionUID = 1L;

	// Put your Google API Server Key here
	private static final String GOOGLE_SERVER_KEY = "AIzaSyCGNPFLU1qMrup-zp1JSmrtNPkl7EuYoOQ";
	static final String REGISTER_NAME = "name";
	static final String MESSAGE_KEY = "message";
	static final String TO_NAME = "toName";
	static final String REG_ID_STORE = "GCMRegId.txt";
	static final Map<String, String> regIdMap = new HashMap<String, String>();

	public GCMNotification() {
		super();
	}

	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		doPost(request, response);

	}

	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {

		String action = request.getParameter("action");

		if ("shareRegId".equalsIgnoreCase(action)) {

			writeToFile(request.getParameter("name"),
					request.getParameter("regId"));
			request.setAttribute("pushStatus",
					"GCM Name and corresponding RegId Received.");
			request.getRequestDispatcher("index.jsp")
					.forward(request, response);

		} else if ("sendMessage".equalsIgnoreCase(action)) {

			try {
				String fromName = request.getParameter(REGISTER_NAME);
				String toName = request.getParameter(TO_NAME);
				String userMessage = request.getParameter(MESSAGE_KEY);
				Sender sender = new Sender(GOOGLE_SERVER_KEY);
				Message message = new Message.Builder().timeToLive(30)
						.delayWhileIdle(true).addData(MESSAGE_KEY, userMessage)
						.addData(REGISTER_NAME, fromName).build();
				Map<String, String> regIdMap = readFromFile();
				String regId = regIdMap.get(toName);
				Result result = sender.send(message, regId, 1);
				request.setAttribute("pushStatus", result.toString());
			} catch (IOException ioe) {
				ioe.printStackTrace();
				request.setAttribute("pushStatus",
						"RegId required: " + ioe.toString());
			} catch (Exception e) {
				e.printStackTrace();
				request.setAttribute("pushStatus", e.toString());
			}
			request.getRequestDispatcher("index.jsp")
					.forward(request, response);
		} else if ("multicast".equalsIgnoreCase(action)) {

			try {
				String fromName = request.getParameter(REGISTER_NAME);
				String userMessage = request.getParameter(MESSAGE_KEY);
				Sender sender = new Sender(GOOGLE_SERVER_KEY);
				Message message = new Message.Builder().timeToLive(30)
						.delayWhileIdle(true).addData(MESSAGE_KEY, userMessage)
						.addData(REGISTER_NAME, fromName).build();
				Map<String, String> regIdMap = readFromFile();

				List<String> regIdList = new ArrayList<String>();

				for (Entry<String, String> entry : regIdMap.entrySet()) {
					regIdList.add(entry.getValue());
				}

				MulticastResult multiResult = sender
						.send(message, regIdList, 1);
				request.setAttribute("pushStatus", multiResult.toString());
			} catch (IOException ioe) {
				ioe.printStackTrace();
				request.setAttribute("pushStatus",
						"RegId required: " + ioe.toString());
			} catch (Exception e) {
				e.printStackTrace();
				request.setAttribute("pushStatus", e.toString());
			}
			request.getRequestDispatcher("index.jsp")
					.forward(request, response);
		} else if ("echo".equalsIgnoreCase(action)) {

			try {
				String fromName = request.getParameter(REGISTER_NAME);
				String toName = request.getParameter(REGISTER_NAME);
				String userMessage = request.getParameter(MESSAGE_KEY);
				Sender sender = new Sender(GOOGLE_SERVER_KEY);
				Message message = new Message.Builder().timeToLive(30)
						.delayWhileIdle(true).addData(MESSAGE_KEY, userMessage)
						.addData(REGISTER_NAME, fromName).build();
				Map<String, String> regIdMap = readFromFile();
				String regId = regIdMap.get(toName);
				Result result = sender.send(message, regId, 1);
				request.setAttribute("pushStatus", result.toString());
			} catch (IOException ioe) {
				ioe.printStackTrace();
				request.setAttribute("pushStatus",
						"RegId required: " + ioe.toString());
			} catch (Exception e) {
				e.printStackTrace();
				request.setAttribute("pushStatus", e.toString());
			}
			request.getRequestDispatcher("index.jsp")
					.forward(request, response);
		}
	}

	private void writeToFile(String name, String regId) throws IOException {
		this.regIdMap.put(name, regId);

	}

	private Map<String, String> readFromFile() throws IOException {
		return this.regIdMap;
	}
}
