package mysite.web;

import jakarta.servlet.ServletContextEvent;
import jakarta.servlet.ServletContextListener;

public class ContextLoadListener implements ServletContextListener {

    public void contextInitialized(ServletContextEvent sce)  { 
         System.out.println("Application[MySite02] starts...");
    }
    public void contextDestroyed(ServletContextEvent sce)  { 
    	/* nothing */
    }
	
}
