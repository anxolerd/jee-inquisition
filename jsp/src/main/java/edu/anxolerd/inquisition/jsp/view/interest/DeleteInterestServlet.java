package edu.anxolerd.inquisition.jsp.view.interest;


import edu.anxolerd.inquisition.core.entities.Interest;
import edu.anxolerd.inquisition.core.repo.InterestRepo;
import edu.anxolerd.inquisition.jsp.RepoHolder;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.UUID;

public class DeleteInterestServlet extends HttpServlet {
    private InterestRepo interestRepo = null;

    @Override
    public void init() throws ServletException {
        super.init();
        this.interestRepo = RepoHolder.getInterestRepo();
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String interestId = req.getParameter("id");

        UUID interestUUID;
        try {
            interestUUID = UUID.fromString(interestId);
        } catch (IllegalArgumentException e) {
            resp.sendError(HttpServletResponse.SC_BAD_REQUEST);
            return;
        }

        interestRepo.delete(interestUUID);
        resp.sendRedirect(String.format("%s/interests/list", req.getContextPath()));
    }
}
