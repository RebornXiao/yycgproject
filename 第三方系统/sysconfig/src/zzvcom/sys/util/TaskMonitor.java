package zzvcom.sys.util;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.apache.log4j.Logger;

public class TaskMonitor implements ServletContextListener {

	
	public static String baseurl = null;//系统根路径 
	
	public static String fromsysurl=null;//来源系统路径 
	
	public static String versionnum = null;
	
	public static String versiondate = null;

	protected final Logger log = Logger.getLogger(getClass());

	public void contextInitialized(ServletContextEvent event) {
		log.info("=======Tark Timer Start=======");

		// 读取版本信息
		String webrootpath = event.getServletContext().getRealPath("/")+"version.xml";
		log.info("系统根路径信息=======" + webrootpath);
		try {
          if(versionnum==null&&versiondate==null){
			DocumentBuilderFactory factory = DocumentBuilderFactory
					.newInstance();
			DocumentBuilder dombuilder = factory.newDocumentBuilder();
			InputStream is = new FileInputStream(webrootpath);
			String versionxml = convertStreamToString(is);
			//log.info("xml文件信息=======" + versionxml);
			AxiomManager axm = new AxiomManager();
			 versionnum = axm.getSingleNode(versionxml, "//versionNum");
			 versiondate = axm.getSingleNode(versionxml, "//versionDate");
			log.info("版本号=======" + versionnum);
			log.info("发布日期=======" + versiondate);
          }         

          
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		if (baseurl == null) {
			baseurl = event.getServletContext().getInitParameter("baseurl");
		}
		if (fromsysurl == null) {
			fromsysurl = event.getServletContext().getInitParameter("fromsysurl");
		}
		
	}

	public void contextDestroyed(ServletContextEvent event) {

	}

	
	public String convertStreamToString(InputStream is) throws UnsupportedEncodingException {
		BufferedReader reader = new BufferedReader(new InputStreamReader(is,"UTF-8"));
		StringBuilder sb = new StringBuilder();

		String line = null;
		try {
			while ((line = reader.readLine()) != null) {
				sb.append(line + "\n");
			}
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				is.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

		return sb.toString();
	}

}
