package controller;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;

import beans.Hbm_Student;

@WebServlet("/Hbm")
public class Hibernate_Servlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
	SessionFactory sf;
	
  
    public Hibernate_Servlet() {
        super();

    }

	
	public void init(ServletConfig config) throws ServletException {
		
		Configuration cfg =new Configuration();
		cfg.configure("resource/sequence.cfg.xml");
		 sf =cfg.buildSessionFactory();
		 System.out.println("Sessionfactory is impliment successfully init");
		
		
	}

	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		response.setContentType("text/html");
		PrintWriter out= response.getWriter();
		
		String name =request.getParameter("name");
		String email =request.getParameter("email");
		String address =request.getParameter("address");
		int marks =Integer.parseInt(request.getParameter("marks"));
		
		Hbm_Student hb =new Hbm_Student(0,name,email,address,marks);
		
		Session s=sf.openSession();
		Transaction t=s.beginTransaction();
		
		int pk =(int) s.save(hb);
		t.commit();
		s.close();
		out.println("Register  Succesfully id=:"+pk);
		out.print("<a href='NewFile.jsp'>add new student</a>");
		//we are using identity it will be display create table hibernate_identity (next_val bigint)
		//but  it is  increase the id data, databases responsebilty;
		//we are using sequence it will be display  create table hibernate_sequence (next_val bigint) but increase the id data responsabile for 
		//application and database both are responsability;
		
	}
	//Sessionfactory is impliment successfully init
	//Hibernate: select max(id) from student221::::// so hear application itself resposable genarator the id increment
	//:<genarator name="increment"/> not database responsebilty , 

	//Hibernate: insert into student221 (name, email, address, marks, id) values (?, ?, ?, ?, ?)
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		doGet(request, response);
	}
	public void destroy() {
		
		sf.close();

	}

}
