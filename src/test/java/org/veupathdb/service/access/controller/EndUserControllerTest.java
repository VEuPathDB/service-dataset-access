package org.veupathdb.service.access.controller;

import org.glassfish.jersey.server.ContainerRequest;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.veupathdb.lib.container.jaxrs.model.User;
import org.veupathdb.lib.test.MockUtil;
import org.veupathdb.lib.test.RandUtil;
import org.veupathdb.service.access.generated.model.ApprovalStatus;
import org.veupathdb.service.access.generated.model.EndUser;
import org.veupathdb.service.access.generated.model.EndUserCreateRequest;
import org.veupathdb.service.access.generated.model.EndUserCreateResponse;
import org.veupathdb.service.access.generated.model.EndUserList;
import org.veupathdb.service.access.service.user.EndUserCreationService;
import org.veupathdb.service.access.service.user.EndUserLookupService;
import org.veupathdb.service.access.service.user.EndUserSearchService;

import static org.junit.jupiter.api.Assertions.assertSame;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

@DisplayName("EndUserController")
class EndUserControllerTest
{
  private ContainerRequest mockRequest;

  private EndUserController target;

  @BeforeEach
  void setUp() throws Exception {
    mockRequest = mock(ContainerRequest.class);
    target      = new EndUserController();
    target._request = mockRequest;
  }

  @Nested
  @DisplayName("#getDatasetEndUsers(String, int, int ApprovalStatus)")
  class GetDatasetEndUsers
  {
    private EndUserSearchService mock;

    @BeforeEach
    void setUp() throws Exception {
      mock = MockUtil.mockSingleton(EndUserSearchService.class);
    }

    @AfterEach
    void tearDown() throws Exception {
      MockUtil.resetSingleton(EndUserSearchService.class);
    }

    @Test
    @DisplayName("passes parameters through to the backing service")
    void test1() {
      var dsId   = RandUtil.randString();
      var limit  = RandUtil.randLong();
      var offset = RandUtil.randLong();
      var status = ApprovalStatus.DENIED;
      var retVal = mock(EndUserList.class);

      doReturn(retVal).when(mock).findEndUsers(dsId, limit, offset, status, mockRequest);

      assertSame(retVal, target.getDatasetEndUsers(dsId, limit, offset, status).getEntity());

      verify(mock).findEndUsers(dsId, limit, offset, status, mockRequest);
    }
  }

  @Nested
  @DisplayName("#postDatasetEndUsers(EndUserCreateRequest)")
  class PostDatasetEndUsers
  {
    private EndUserCreationService mock;

    @BeforeEach
    void setUp() throws Exception {
      mock = MockUtil.mockSingleton(EndUserCreationService.class);
    }

    @AfterEach
    void tearDown() throws Exception {
      MockUtil.resetSingleton(EndUserCreationService.class);
    }

    @Test
    @DisplayName("passes parameters through to the backing service")
    void test1() {
      var in  = mock(EndUserCreateRequest.class);
      var out = mock(EndUserCreateResponse.class);

      doReturn(out).when(mock).handleUserCreation(in, mockRequest);

      assertSame(out, target.postDatasetEndUsers(in).getEntity());

      verify(mock).handleUserCreation(in, mockRequest);
    }
  }

  @Nested
  @DisplayName("#getDatasetEndUsersByEndUserId(String)")
  class GetDatasetEndUsersByEndUserId
  {
    private Util                 mockUtil;
    private User                 mockUser;
    private EndUserLookupService mockLookup;
    private EndUser              mockReqUser;

    @BeforeEach
    void setUp() throws Exception {
      mockUtil    = MockUtil.mockSingleton(Util.class);
      mockLookup  = MockUtil.mockSingleton(EndUserLookupService.class);
      mockReqUser = mock(EndUser.class);
      mockUser    = mock(User.class);

      doReturn(mockUser).when(mockUtil).mustGetUser(mockRequest);
    }

    @AfterEach
    void tearDown() throws Exception {
      MockUtil.resetSingleton(Util.class);
    }

    @Nested
    @DisplayName("throws a forbidden exception")
    class e403
    {
      @Test
      @DisplayName("when the requesting user is not a manager, provider")
      void test1() {

      }
    }
  }
}
