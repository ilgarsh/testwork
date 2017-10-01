import store.Directory;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

@WebServlet("/sarvar")
public class DirectoryController extends HttpServlet {
    private static final long serialVersionUID = 1L;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        List<Directory> directories = new ArrayList<Directory>();
        directories.add(new Directory(1, "a1", 0));
        directories.add(new Directory(1, "a1", 0));
        directories.add(new Directory(1, "a1", 0));
        directories.add(new Directory(1, "a1", 0));
        directories.add(new Directory(1, "a1", 0));
        directories.add(new Directory(1, "a1", 0));
        directories.add(new Directory(1, "a1", 0));
        directories.add(new Directory(1, "a1", 0));
        RequestDispatcher dispatcher = req.getRequestDispatcher("tree.jsp");
        req.setAttribute("directories", directories);
        RequestDispatcher d2 = req.getRequestDispatcher("index.jsp");
        d2.forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

    }
}
