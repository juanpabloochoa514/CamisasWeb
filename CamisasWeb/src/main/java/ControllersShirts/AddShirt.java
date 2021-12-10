package ControllersShirts;

import java.io.IOException;
import java.io.Writer;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import ModelShirt.Shirt;
import repositories.Common;

/**
 * Servlet implementation class AddShirt
 */
@WebServlet("/AddShirt")
public class AddShirt extends HttpServlet {
	private static final long serialVersionUID = 1L;

    /**
     * Default constructor. 
     */
    public AddShirt() {
        // TODO Auto-generated constructor stub
    }

	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String name = request.getParameter("name");
		String priceparam = request.getParameter("price");
		String idparam = request.getParameter("id");
		Writer writer = response.getWriter();
		try {
			int id = Integer.parseInt(idparam);
			int price = Integer.parseInt(priceparam);
			
			Shirt shirts = new Shirt(name,price,id);
			
			Common.shirtsRepository.addNewShirt(shirts);
			response.setStatus(HttpServletResponse.SC_OK);
			response.setContentType("text/html;charset=UTF-8");
			writer.append("<div>"
					+ " NOMBRE CAMISA = "+shirts.getName()+" PRECIO = "+shirts.getPrice()+" ID = "+shirts.getId()+"</div>");
		}catch(Exception e){
			response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
			writer.append("ERROR DEL SERVIDOR");
		}
	}

}
