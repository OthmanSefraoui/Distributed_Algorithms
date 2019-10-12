
/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
import sun.misc.Signal;
import sun.misc.SignalHandler;

import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;

public class Process extends Thread {
	private InetAddress ip;
	private Integer port;
	private Integer processId;
	private DatagramSocket socket;
	static int messageID = 0;

	
	public DatagramSocket create() throws SocketException {
		this.socket = new DatagramSocket(this.port, this.ip);
		return this.socket;
	}

	public void close() {
		this.socket.close();
	}

	public Process(InetAddress ip, Integer processId, Integer port) {
		this.port = port;
		this.processId = processId;
		this.ip = ip;

		SigHandlerTerm sigHandlerInt = new SigHandlerTerm(this);
		SigHandlerInt sigHandlerTerm = new SigHandlerInt(this);
		// SigHandlerUsr2 sigHandlerUsr2 = new SigHandlerUsr2(this);

		Signal signalInt = new Signal("INT");
		Signal signalTerm = new Signal("TERM");
		// Signal signalUsr2 = new Signal("USR2");

		Signal.handle(signalInt, sigHandlerInt);
		Signal.handle(signalTerm, sigHandlerTerm);
		// Signal.handle(signalUsr2, sigHandlerUsr2);

		this.start();
	}

	public void run() {
		while (!Thread.currentThread().isInterrupted()) {
			try {
				Thread.sleep(100);
			} catch (Exception e) {
				// exception
			}
		}
	}

	public static class SigHandlerUsr2 implements SignalHandler {
		Process p;

		private SigHandlerUsr2(Process p) {
			super();
			this.p = p;
		}

		@Override
		public void handle(Signal signal) {

			System.out.format("Handling signal: %s\n", signal.toString());
		}
	}

	public static class SigHandlerTerm implements SignalHandler {
		Process p;

		private SigHandlerTerm(Process p) {
			super();
			this.p = p;
		}

		@Override
		public void handle(Signal signal) {
			System.out.format("Handling signal: %s\n", signal.toString());
			p.interrupt();
		}
	}

	public static class SigHandlerInt implements SignalHandler {
		Process p;

		private SigHandlerInt(Process p) {
			super();
			this.p = p;
		}

		@Override
		public void handle(Signal signal) {
			System.out.format("Handling signal: %s\n", signal.toString());
			p.interrupt();
		}
	}
	
	public InetAddress getIp() {
		return ip;
	}

	public void setIp(InetAddress ip) {
		this.ip = ip;
	}

	public DatagramSocket getSocket() {
		return socket;
	}

	public Integer getPort() {
		return port;
	}

	public void setPort(Integer port) {
		this.port = port;
	}

	public Integer getProcessId() {
		return processId;
	}

	public void setProcessId(Integer processId) {
		this.processId = processId;
	}
}