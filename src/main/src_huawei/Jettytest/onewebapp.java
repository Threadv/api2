/*package Jettytest;

import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.nio.SelectChannelConnector;
import org.eclipse.jetty.webapp.WebAppContext;

//Jetty ���Դ��룬����jetty 7.6.5,build path ����lib/*.jar, lib/jsp/*.jar���ܱ������С�����

public class onewebapp {
	public static void main( String[] args ) throws Exception{
		
	    Server server = new Server();  
	      
	    SelectChannelConnector connector = new SelectChannelConnector();  
	    connector.setPort(8090);  
	    server.addConnector(connector);  
	      
	    WebAppContext context = new WebAppContext();  
	      
	    context.setContextPath("/");  
	    context.setDescriptor("WebRoot/WEB-INF/web.xml");  
	    context.setResourceBase("WebRoot");  
	    context.setConfigurationDiscovered(true);
	    context.setClassLoader(Thread.currentThread().getContextClassLoader());
	      
	    
	    server.setHandler(context);  
	    server.start();
	}
}


*/