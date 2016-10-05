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

public class ListInterestServlet extends HttpServlet {
    private InterestRepo interestRepo = null;

    @Override
    public void init() throws ServletException {
        super.init();
        this.interestRepo = RepoHolder.getInterestRepo();
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        List<Interest> interests;

        String personId = req.getParameter("personId");
        if (personId == null) {
            interests = interestRepo.getAll();
        } else {
            UUID personUUID = UUID.fromString(personId);
            interests = interestRepo.getInterestsForPerson(personUUID);
        }

        req.setAttribute("interests", interests);
        req.getRequestDispatcher("/jsp/interest/list.jsp").forward(req, resp);
    }
}
