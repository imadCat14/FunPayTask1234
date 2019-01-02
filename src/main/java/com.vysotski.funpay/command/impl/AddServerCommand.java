package com.vysotski.funpay.command.impl;

import com.vysotski.funpay.command.Command;
import com.vysotski.funpay.command.CommandException;
import com.vysotski.funpay.entity.Server;
import com.vysotski.funpay.resource.ConfigurationManager;
import com.vysotski.funpay.service.ServerService;
import com.vysotski.funpay.service.ServiceException;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

public class AddServerCommand implements Command {
    private static final String PARAM_NAME_SERVER = "serverName";
    private static final String PARAM_NAME_DESCRIPTION = "serverDescription";
    private static final String PARAM_NAME_CHRONICLE = "chronicle";
    private ServerService serverService = new ServerService();

    @Override
    public String execute(HttpServletRequest request) throws CommandException {
        String serverName = request.getParameter(PARAM_NAME_SERVER);
        String serverChronicle = request.getParameter(PARAM_NAME_CHRONICLE);
        String serverDescription = request.getParameter(PARAM_NAME_DESCRIPTION);
        String page;
        try {
            List<Server> aliens = serverService.createServer(serverName, serverChronicle, serverDescription);
            if (aliens.isEmpty()) {
                //request.setAttribute("alien",aliens);
                page = ConfigurationManager.getProperty("path.page.new-alien-form-page");
            } else {
                aliens = serverService.selectAll();
                request.setAttribute("aliens", aliens);
                page = ConfigurationManager.getProperty("path.page.admin-page");
            }
            // aliens = alienService.selectAll();
        } catch (ServiceException e) {
            throw new CommandException(e);
        }
        //TODO Massage if alien did not add
        // request.setAttribute("aliens", aliens);

        return page;
    }
}