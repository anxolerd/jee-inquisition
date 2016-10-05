package edu.anxolerd.inquisition.jsp.view.person;


import edu.anxolerd.inquisition.core.entities.Interest;
import edu.anxolerd.inquisition.core.entities.Person;
import edu.anxolerd.inquisition.core.repo.InterestRepo;
import edu.anxolerd.inquisition.core.repo.PersonRepo;
import edu.anxolerd.inquisition.jsp.RepoHolder;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.UUID;

public class ListPersonServlet extends HttpServlet {
    private PersonRepo personRepo = null;
    private DateFormat df = new SimpleDateFormat("yyyy-MM-dd");

    @Override
    public void init() throws ServletException {
        super.init();
        this.personRepo = RepoHolder.getPersonRepo();
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        List<Person> persons;
        persons = personRepo.getAll();
        req.setAttribute("persons", persons);
        req.setAttribute("formatter", df);
        req.getRequestDispatcher("/jsp/person/list.jsp").forward(req, resp);
    }
}
