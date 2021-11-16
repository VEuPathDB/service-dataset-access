package org.veupathdb.service.access.controller;

import javax.ws.rs.core.Context;
import javax.ws.rs.core.Request;

import org.veupathdb.lib.container.jaxrs.server.annotations.Authenticated;
import org.veupathdb.service.access.generated.resources.History;
import org.veupathdb.service.access.service.history.HistoryService;

@Authenticated
public class HistoryController implements History
{
  @Context
  private Request _request;

  @Override
  public GetHistoryResponse getHistory(int limit, int offset) {
    var user = Util.requireUser(_request);

    return GetHistoryResponse.respond200WithApplicationJson(
      HistoryService.getHistory(user.getUserID(), limit, offset)
    );
  }
}
