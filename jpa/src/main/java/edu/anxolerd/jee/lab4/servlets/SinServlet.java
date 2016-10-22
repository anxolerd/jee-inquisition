package edu.anxolerd.jee.lab4.servlets;

import com.google.gson.Gson;
import edu.anxolerd.jee.lab4.entities.Sin;
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
import java.io.Reader;
import java.util.List;


@WebServlet(urlPatterns = {"/api/v1/sin/*", "/api/v1/sin"})
public class SinServlet extends HttpServlet {
    private static final Logger LOG = LogManager.getLogger(PersonServlet.class);

    private long parseIdOrAbort(String pathInfo, HttpServletResponse resp) throws IOException {
        try {
            return Long.valueOf(pathInfo);
        } catch (NumberFormatException e) {
            LOG.error(e);
            resp.sendError(HttpServletResponse.SC_BAD_REQUEST);
            throw e;
        }
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String pathInfo = req.getPathInfo();
        if (pathInfo == null) {
            this.getList(req, resp);
        } else {
            pathInfo = pathInfo.substring(1);
            long id = parseIdOrAbort(pathInfo, resp);
            getOne(id, req, resp);
        }
    }

    private void getOne(long id, HttpServletRequest req, HttpServletResponse resp) throws IOException {
        LOG.info(String.format("request sin with id %d", id));
        SinService ss = new SinService();
        Sin sin = ss.getById(id);
        if (sin == null) {
            resp.sendError(HttpServletResponse.SC_NOT_FOUND);
        }
        Gson gson = JSONUtils.getGson();
        resp.getWriter().write(gson.toJson(sin));
    }

    private void getList(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        LOG.info("requesting all sinners");
        SinService ss = new SinService();
        List<Sin> sins = ss.getAll();
        Gson gson = JSONUtils.getGson();
        resp.getWriter().write(gson.toJson(sins));
    }


    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String pathInfo = req.getPathInfo();
        if (pathInfo == null) {
            this.postCreate(req, resp);
        } else {
            pathInfo = pathInfo.substring(1);
            long id = parseIdOrAbort(pathInfo, resp);
            postEdit(id, req, resp);
        }
    }

    private void postCreate(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        SinService ss = new SinService();
        Sin s = null;
        try {
            s = getSinFromRequest(req);
        } catch (Exception e) {
            LOG.error(e);
            resp.sendError(HttpServletResponse.SC_BAD_REQUEST);
            return;
        }

        try {
            ss.create(s);
            resp.getWriter().write("{\"status\": \"ok\"}");
        } catch (Exception e) {
            LOG.error(e);
            resp.getWriter().write("{\"status\": \"error\"}");
        }
    }

    private void postEdit(long id, HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        LOG.info(String.format("updating sinner %d", id));
        SinService ss = new SinService();
        Sin s = ss.getById(id);
        if (s == null) {
            resp.sendError(HttpServletResponse.SC_NOT_FOUND);
            return;
        }
        try {
            s = getSinFromRequest(req);
        } catch (Exception e) {
            LOG.error(e);
            resp.sendError(HttpServletResponse.SC_BAD_REQUEST);
            return;
        }
        s.setId(id);
        try {
            ss.update(s);
            resp.getWriter().write("{\"status\": \"ok\"}");
        } catch (Exception e) {
            LOG.error(e);
            resp.getWriter().write("{\"status\": \"error\"}");
        }
    }

    private Sin getSinFromRequest(HttpServletRequest req) throws IOException {
        Reader r = req.getReader();
        char[] buf = new char[4096];
        r.read(buf);

        Gson gson = JSONUtils.getGson();
        return gson.fromJson(String.valueOf(buf).trim(), Sin.class);
    }
}
