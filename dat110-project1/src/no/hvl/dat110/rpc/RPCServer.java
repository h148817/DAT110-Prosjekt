package no.hvl.dat110.rpc;

import java.util.HashMap;

import no.hvl.dat110.messaging.Connection;
import no.hvl.dat110.messaging.Message;
import no.hvl.dat110.messaging.MessagingServer;
import no.hvl.dat110.rpc.tests.TestBooleanBooleanImpl;

public class RPCServer {

	private MessagingServer msgserver;
	private Connection connection;
	
	// hashmap to register RPC methods which are required to implement RPCImpl
	
	private HashMap<Integer,RPCImpl> services;
	
	public RPCServer(int port) {
		
		this.msgserver = new MessagingServer(port);
		this.services = new HashMap<Integer,RPCImpl>();
		
		// the stop RPC methods is built into the server
		services.put((int)RPCCommon.RPIDSTOP,new RPCServerStopImpl());
	}
	
	public void run() {
		
		System.out.println("RPC SERVER RUN - Services: " + services.size());
		
		connection = msgserver.accept(); 
		
		System.out.println("RPC SERVER ACCEPTED");
		
		boolean stop = false;
		int rpcid;
		
		while (!stop) {
	    		   
		   // TODO
		   // - receive message containing RPC request 
		   // - find the identifier for the RPC methods to invoke
		   // - lookup the methods to be invoked
		   // - invoke the method
		   // - send back message containing RPC reply
						
			
		   
		   Message message = connection.receive();
		   byte[] reply;
		   
		   rpcid = message.getData()[0];
		   
		   if (rpcid == RPCCommon.RPIDSTOP) {
			   stop = true;
		   }
		   else {
			    reply = services.get(rpcid).invoke(message.getData());
			    message = new Message(reply);
		   }
		   
		   connection.send(message);
		}
	
	}
	
	public void register(int rmid, RPCImpl impl) {
		services.put(rmid, impl);
	}
	
	public void stop() {
		connection.close();
		msgserver.stop();
		
	}
}
