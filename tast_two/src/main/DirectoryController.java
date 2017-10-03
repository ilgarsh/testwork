package main;

import com.google.gson.Gson;
import main.store.Directory;
import main.store.DirectoryDAO;
import main.store.DirectoryDaoH2;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * A {@code DirectoryController} that handle command from client
 * and display directories.
 */
@WebServlet("")
public class DirectoryController extends HttpServlet {
    private static final long serialVersionUID = 1L;

    private DirectoryDAO directoryDAO;

    private Directory current;
    private int divID;
    private Directory bufferDirectory;

    @Override
    public void init() throws ServletException {
        directoryDAO = new DirectoryDaoH2();
        divID = 1;
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doPost(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String command = req.getParameter("command");
        if (command != null) {
            String json;
            String div_name;
            int div_id;
            System.out.println(command);
            switch (command) {
                case "add":
                    div_id = Integer.parseInt(req.getParameter("div_id"));
                    div_name = req.getParameter("div_name");
                    Directory newDirectory = new Directory(divID++, div_name, div_id);
                    directoryDAO.addDirectory(newDirectory);
                    json = new Gson().toJson(newDirectory);
                    resp.setContentType("application/json");
                    resp.setCharacterEncoding("UTF-8");
                    resp.getWriter().print(json);
                    return;

                case "show_child":
                    div_id = Integer.parseInt(req.getParameter("div_id"));
                    current = directoryDAO.getDirectory(div_id);
                    current.setChilds(directoryDAO.getAllChilds(div_id));
                    json = new Gson().toJson(current);
                    resp.setContentType("application/json");
                    resp.setCharacterEncoding("UTF-8");
                    try {
                        Thread.sleep(2000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    resp.getWriter().print(json);
                    return;

                case "edit":
                    div_id = Integer.parseInt(req.getParameter("div_id"));
                    div_name = req.getParameter("div_name");
                    directoryDAO.updateDirectoryName(div_id, div_name);
                    json = new Gson().toJson(div_name);
                    resp.setContentType("application/json");
                    resp.setCharacterEncoding("UTF-8");
                    resp.getWriter().print(json);
                    return;

                case "copy":
                    div_id = Integer.parseInt(req.getParameter("div_id"));
                    div_name = directoryDAO.getDirectory(div_id).getName();
                    bufferDirectory = new Directory(div_id, div_name, null);
                    return;

                case "paste":
                    div_id = Integer.parseInt(req.getParameter("div_id"));
                    List<Directory> oldDirectories = directoryDAO.getAllChilds(
                            bufferDirectory.getId()
                    );
                    List<Directory> newDirectories = depthCopy(oldDirectories);
                    bufferDirectory.setChilds(newDirectories);
                    bufferDirectory = updateID(bufferDirectory, div_id);
                    addInDB(bufferDirectory);
                    json = new Gson().toJson(bufferDirectory);
                    resp.setContentType("application/json");
                    resp.setCharacterEncoding("UTF-8");
                    resp.getWriter().print(json);
                    return;

                case "delete":
                    div_id = Integer.parseInt(req.getParameter("div_id"));
                    directoryDAO.deleteDirectory(div_id);
                    resp.getWriter().print("true");

            }
        }
        RequestDispatcher dispatcher = req.getRequestDispatcher("index.jsp");
        dispatcher.forward(req, resp);
    }

    /**
     * The {@code depthCopy(dirs)} is method that depth copy dirs to new ListDirectory and return it.
     * @param dirs directory that copy
     * @return
     */
    private List<Directory> depthCopy(List<Directory> dirs) {
        List<Directory> directories = new ArrayList<>(dirs.size());
        for (Directory nextDir : dirs ) {
            nextDir.setChilds(directoryDAO.getAllChilds(nextDir.getId()));
            if (nextDir.getChilds() != null) {
                nextDir.setChilds(depthCopy(nextDir.getChilds()));
            }
            directories.add(nextDir);
        }
        System.out.println(directories);
        return directories;
    }

    /**
     * The {@code updateID} needs for remove
     * integrity violation in Database.
     * @param dir root directory
     * @param parent parent's id for {@code dir}
     * @return
     */
    private Directory updateID(Directory dir, int parent) {
        dir.setId(divID++);
        dir.setParent(parent);
        List<Directory> childs = new ArrayList<>();
        for (Directory child : dir.getChilds()) {
            childs.add(updateID(child, dir.getId()));
        }
        dir.setChilds(childs);
        System.out.println(dir);
        return dir;
    }

    private void addInDB(Directory dir) {
        directoryDAO.addDirectory(dir);
        for (Directory child : dir.getChilds()) {
            addInDB(child);
        }
    }

}
