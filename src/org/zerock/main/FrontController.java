package org.zerock.main;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.zerock.anno.GetMapping;
import org.zerock.anno.RequestMapping;

/**
 * Servlet implementation class FrontController
 */
@WebServlet("*.do")
public class FrontController extends HttpServlet {
	private static final long serialVersionUID = 1L;

	private Map<String, Object> controllerMap;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public FrontController() {
		super();

		controllerMap = new HashMap<>();

		try {
			Class clz = 
					Class.forName("org.zerock.web.BoardController");
			
			RequestMapping r1 = 
					(RequestMapping)clz.getAnnotation(RequestMapping.class);

			controllerMap.put(r1.value(), clz.newInstance());
			
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	/**
	 * @see HttpServlet#service(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void service(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		String path = request.getRequestURI();
		System.out.println(path);
		
		Iterator<String> it = controllerMap.keySet().iterator();
		
		while(it.hasNext()) {
			
			String value = it.next();
			
			System.out.println("=====================");
			System.out.println(value);
			System.out.println(path.indexOf(value));
			
			if(path.indexOf(value) >= 0) {
				
				Object obj = controllerMap.get(value);
				
				System.out.println(obj);
				
				//GET, POST
				String getPost = request.getMethod();
				
				Class anno = null;
				
				if(getPost.equals("GET")) {
					anno = GetMapping.class;
				}
				
				Method[] ms = obj.getClass().getDeclaredMethods();
				
				for(Method m:ms) {
					
					GetMapping mapping = (GetMapping) m.getAnnotation(anno);
					
					if(mapping != null) {
						
						if(mapping.value().equals(path)) {
							System.out.println(m);
							try {
								
								Class[] paramTypes = m.getParameterTypes();
								
								
								System.out.println(Arrays.toString(paramTypes));
								
								String reqName = "page";
								
								m.invoke(obj,request.getParameter(reqName));
								
							} catch (IllegalAccessException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							} catch (IllegalArgumentException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							} catch (InvocationTargetException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
						}
						
					}
					
				}
				
				
				
				break;
			}
			
		}

	}

}








