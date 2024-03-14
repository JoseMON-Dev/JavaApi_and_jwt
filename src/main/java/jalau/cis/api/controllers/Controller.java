package jalau.cis.api.controllers;

import jalau.cis.services.ServicesFacade;
import jalau.cis.services.UsersService;

public class Controller {
  protected UsersService getUsersService() throws Exception {
    return ServicesFacade.getInstance().getUsersService();
  }
}
