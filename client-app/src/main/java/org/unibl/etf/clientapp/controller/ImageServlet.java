package org.unibl.etf.clientapp.controller;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

@WebServlet("/avatars/*")
public class ImageServlet extends HttpServlet {
    private String avatarPath;

    @Override
    public void init() {
        avatarPath = "C:\\Users\\PC\\Desktop\\4 GODINA\\IP\\urban-motion\\avatars";
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String filename = request.getPathInfo().substring(1);
        File file = new File(avatarPath, filename);

        if (file.exists() && !file.isDirectory()) {
            response.setHeader("Content-Type", getServletContext().getMimeType(filename));
            response.setHeader("Content-Length", String.valueOf(file.length()));
            Files.copy(file.toPath(), response.getOutputStream());
        } else {
            response.sendError(HttpServletResponse.SC_NOT_FOUND);
        }
    }
}