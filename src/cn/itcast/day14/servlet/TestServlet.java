package cn.itcast.day14.servlet;

import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class TestServlet extends HttpServlet {

	private static final long serialVersionUID = 5984589609000697707L;

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		//设置响应状态码
		//resp.setStatus(302);	//重定向
		//设置响应头，重定向时，因为是发送指令到客户端浏览器，由浏览器发送新的请求，所以路径需要加项目名
		//resp.setHeader("Location", "/day14/b.html");
		
		//设置响应信息使用编码为UTF-8，解决中文乱码问题，注意：必须在getWriter()之前
		//resp.setCharacterEncoding("UTF-8");
		
		//MIME类型：大类型/小类型:text/html; text/javascript; text/css ; image/jpeg image/bitmap
		//在设置响应内容的类型的同时，指定使用的字符集，来解决中文乱码问题，也必须在getWriter()之前进行设置
		/*resp.setContentType("text/html; charset=UTF-8");
		
		PrintWriter out = resp.getWriter();
		out.println("<html>");
		out.println("	<head>");
		out.println("		<meta charset=\"UTF-8\" />");
		out.println("		<title>login infomation</title>");
		out.println("	</head>");
		out.println("	<body>");
		out.println("		<font color=\"green\">大家好！</font>");
		out.println("	</body>");
		out.println("</html>");*/

		//在同一个Servlet中，response对象的字符输出流和字节输出流不能同时使用。
		ServletOutputStream os = resp.getOutputStream();
		os.write( "hello servlet!".getBytes() );
		
		
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		doGet(req, resp);
	}

}
