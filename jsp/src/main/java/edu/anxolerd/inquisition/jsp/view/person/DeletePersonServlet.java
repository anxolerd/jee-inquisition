package edu.anxolerd.inquisition.jsp.view.person;


import edu.anxolerd.inquisition.core.repo.InterestRepo;
import edu.anxolerd.inquisition.core.repo.PersonRepo;
import edu.anxolerd.inquisition.jsp.RepoHolder;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.UUID;

public class DeletePersonServlet extends HttpServlet {
    private PersonRepo personRepo = null;

    @Override
    public void init() throws ServletException {
        super.init();
        this.personRepo = RepoHolder.getPersonRepo();
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String personId = req.getParameter("id");

        UUID personUUID;
        try {
            personUUID = UUID.fromString(personId);
        } catch (IllegalArgumentException e) {
            resp.sendError(HttpServletResponse.SC_BAD_REQUEST);
            return;
        }

        personRepo.delete(personUUID);
        resp.sendRedirect(String.format("%s/persons/list", req.getContextPath()));
    }
}
