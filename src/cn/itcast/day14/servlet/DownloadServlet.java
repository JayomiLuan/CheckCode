package cn.itcast.day14.servlet;

import sun.misc.BASE64Encoder;

import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

public class DownloadServlet extends HttpServlet {

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		//接收参数，得到要下载的文件名
		String filename = req.getParameter("filename");
		
		//把WOW0001.jpg的请求文件名改成中文，用于测试下载中文文件名的情况
		if( filename.equals("WOW0001.jpg") ){
			filename = "伊利丹.jpg";
		}
		
		//得到要被下载的文件的输入流
		InputStream is = this.getServletContext().getResourceAsStream("/download/"+filename);
		
		//在向浏览器发送数据之前，需要进行响应头的设置才能实现文件下载
		//此处的filename只是在设置浏览器提示用户在进行文件下载时提示默认的文件名，和实际文件名无关
		//响应头的内容是由HTTP协议进行传输的，使用的是ISO-8859-1编码，不支持中文，所以出现中文文件名时会出现文件名丢失的情况
		//使用URL编码来解决中文问题：在服务端对中文文件名进行编码，由客户端浏览自动进行解码
		//在编码之前，还要对用户使用的浏览器进行判断，如果使用的是火狐，则使用Base64编码，否则使用URLEncoder
		String userAgent = req.getHeader("User-Agent");
		System.out.println(req.getHeader("User-Agent"));
		System.out.println(req.getHeader("Referer"));
		System.out.println(req.getHeader("Accept"));
		if( userAgent.contains("Firefox") ){
			//用户使用的是火狐
			filename = base64EncodeFileName(filename);
		}else{
			//其它浏览器
			filename = URLEncoder.encode(filename, "UTF-8");
		}
		System.out.println( "filename : " + filename );
		resp.setHeader("Content-Disposition", "attachment; filename=" + filename );
		
		//得到向浏览器发送数据的输出流
		ServletOutputStream os = resp.getOutputStream();
		
		//从文件输入流中读取文件内容，用输出流把数据发送到客户端浏览器
		byte[] buffer = new byte[1024];
		int len;
		while( (len=is.read(buffer)) > 0 ){
			os.write(buffer , 0 , len );
		}

	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		doGet(req, resp);
	}
	
	//用于处理中文文件名，把中文文件名编码成base64方式
	public static String base64EncodeFileName(String fileName) {
		BASE64Encoder base64Encoder = new BASE64Encoder();
		try {
			return "=?UTF-8?B?"
					+ new String(base64Encoder.encode(fileName
							.getBytes("UTF-8"))) + "?=";
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		}
	}

}
