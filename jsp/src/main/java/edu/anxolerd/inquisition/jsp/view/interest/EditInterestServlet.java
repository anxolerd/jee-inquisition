package edu.anxolerd.inquisition.jsp.view.interest;


import edu.anxolerd.inquisition.core.entities.Interest;
import edu.anxolerd.inquisition.core.repo.InterestRepo;
import edu.anxolerd.inquisition.jsp.RepoHolder;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import java.util.UUID;

public class EditInterestServlet extends HttpServlet {
    private InterestRepo interestRepo = null;

    @Override
    public void init() throws ServletException {
        super.init();
        this.interestRepo = RepoHolder.getInterestRepo();
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String interestId = req.getParameter("id");

        UUID interestUUID;
        try {
             interestUUID = UUID.fromString(interestId);
        } catch (IllegalArgumentException e) {
            resp.sendError(HttpServletResponse.SC_BAD_REQUEST);
            return;
        }

        Interest interest = interestRepo.getById(interestUUID);
        if (interest == null) {
            resp.sendError(HttpServletResponse.SC_NOT_FOUND);
        }

        req.setAttribute("interest", interest);
        req.getRequestDispatcher("/jsp/interest/edit.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String interestId = req.getParameter("id");
        String title = req.getParameter("title");
        String description = req.getParameter("description");
        String sinRateStr = req.getParameter("sinRate");

        UUID interestUUID;
        int sinRate;
        try {
            interestUUID = UUID.fromString(interestId);
            sinRate = Integer.parseInt(sinRateStr);
        } catch (IllegalArgumentException e) {
            resp.sendError(HttpServletResponse.SC_BAD_REQUEST);
            return;
        }

        if (title == null) {
            resp.sendError(HttpServletResponse.SC_BAD_REQUEST);
            return;
        }

        Interest interest = interestRepo.getById(interestUUID);
        if (interest == null) {
            resp.sendError(HttpServletResponse.SC_NOT_FOUND);
            return;
        }

        interest.setTitle(title).setDescription(description).setSinRate(sinRate);
        interestRepo.update(interest);
        resp.sendRedirect(String.format("%s/interests/edit?id=%s", req.getContextPath(), interestUUID.toString()));
    }
}
