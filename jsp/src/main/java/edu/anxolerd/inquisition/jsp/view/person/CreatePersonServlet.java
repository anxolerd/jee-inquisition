package edu.anxolerd.inquisition.jsp.view.person;


import edu.anxolerd.inquisition.core.entities.Interest;
import edu.anxolerd.inquisition.core.entities.Person;
import edu.anxolerd.inquisition.core.repo.PersonRepo;
import edu.anxolerd.inquisition.jsp.RepoHolder;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;


public class CreatePersonServlet extends HttpServlet {
    private PersonRepo personRepo = null;
    private DateFormat df = new SimpleDateFormat("yyyy-MM-dd");

    @Override
    public void init() throws ServletException {
        super.init();
        this.personRepo = RepoHolder.getPersonRepo();
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setAttribute("person", null);
        req.setAttribute("formatter", df);
        req.getRequestDispatcher("/jsp/person/edit.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String firstName = req.getParameter("firstName");
        String midlleName = req.getParameter("middleName");
        String lastName = req.getParameter("lastName");
        String birthDateStr = req.getParameter("birthDate");
        String deathDateStr = req.getParameter("deathDate");

        Date birthDate, deathDate = null;
        try {
            birthDate = df.parse(birthDateStr);
            if (deathDateStr != null && !"".equals(deathDateStr)) {
                deathDate = df.parse(deathDateStr);
            }
        } catch (ParseException e) {
            resp.sendError(HttpServletResponse.SC_BAD_REQUEST);
            return;
        }
        if (firstName == null || lastName == null) {
            resp.sendError(HttpServletResponse.SC_BAD_REQUEST);
            return;
        }

        Person person = new Person()
            .setFirstName(firstName)
            .setMiddleName(midlleName)
            .setLastName(lastName)
            .setBirthDate(birthDate)
            .setDeathDate(deathDate);
        person = personRepo.create(person);
        resp.sendRedirect(String.format("%s/persons/edit?id=%s", req.getContextPath(), person.getId().toString()));
    }
}

