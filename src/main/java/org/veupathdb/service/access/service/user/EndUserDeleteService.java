package org.veupathdb.service.access.service.user;

import javax.ws.rs.InternalServerErrorException;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.veupathdb.service.access.model.EndUserRow;

public class EndUserDeleteService
{
  private static final Logger log = LogManager.getLogger(EndUserDeleteService.class);

  private static EndUserDeleteService instance;

  public static EndUserDeleteService getInstance() {
    log.trace("EndUserDeleteService#getInstance()");

    if (instance == null)
      return instance = new EndUserDeleteService();
    return instance;
  }

  public static void delete(EndUserRow user) {
    getInstance().deleteGrant(user);
  }

  public void deleteGrant(EndUserRow user) {
    log.trace("EndUserDeleteService#deleteGrant(EndUserRow)");

    try {
      EndUserRepo.Delete.endUser(user);
    } catch (Exception ex) {
      throw new InternalServerErrorException(ex);
    }
  }
}
