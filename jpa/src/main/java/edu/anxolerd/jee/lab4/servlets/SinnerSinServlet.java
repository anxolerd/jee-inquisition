package edu.anxolerd.jee.lab4.servlets;

import com.google.gson.Gson;
import edu.anxolerd.jee.lab4.entities.Sin;
import edu.anxolerd.jee.lab4.entities.Sinner;
import edu.anxolerd.jee.lab4.services.PersonService;
import edu.anxolerd.jee.lab4.services.SinService;
import edu.anxolerd.jee.lab4.util.JSONUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.StreamCorruptedException;


@WebServlet("/api/v1/sinner2sin")
public class SinnerSinServlet extends HttpServlet {
    private static final Logger LOG = LogManager.getLogger(SinnerSinServlet.class);

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        char[] buf = new char[1024];
        req.getReader().read(buf);
        String body = String.valueOf(buf).trim();

        PersonSin person2sin = null;
        Gson gson = JSONUtils.getGson();
        try {
            person2sin = gson.fromJson(body, PersonSin.class);
        } catch (Exception e) {
            LOG.error(e);
            resp.sendError(HttpServletResponse.SC_BAD_REQUEST);
            return;
        }

        SinService ss = new SinService();
        PersonService ps = new PersonService();

        Sinner p = ps.getById(person2sin.getPersonId());
        Sin s = ss.getById(person2sin.getSinId());

        if (p == null || s == null) {
            resp.sendError(HttpServletResponse.SC_BAD_REQUEST);
        }

        p.getSins().add(s);
        try {
            ps.update(p);
            resp.getWriter().write("{\"status\": \"ok\"}");
        } catch (Exception e) {
            LOG.error(e);
            resp.getWriter().write("{\"status\": \"error\"}");
        }
    }

    class PersonSin {
        private long personId;
        private long sinId;

        public PersonSin() {
            super();
        }

        public PersonSin(long personId, long sinId) {
            this.personId = personId;
            this.sinId = sinId;
        }

        public long getPersonId() {
            return personId;
        }

        public void setPersonId(long personId) {
            this.personId = personId;
        }

        public long getSinId() {
            return sinId;
        }

        public void setSinId(long sinId) {
            this.sinId = sinId;
        }
    }
}
