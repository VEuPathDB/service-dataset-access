package org.veupathdb.service.access.controller;

import javax.ws.rs.core.Request;

import org.gusdb.fgputil.accountdb.UserProfile;
import org.junit.jupiter.api.*;
import org.veupathdb.lib.container.jaxrs.model.User;
import org.veupathdb.lib.test.MockUtil;
import org.veupathdb.lib.test.RandUtil;
import org.veupathdb.service.access.generated.model.*;
import org.veupathdb.service.access.service.user.EndUserCreationService;
import org.veupathdb.service.access.service.user.EndUserLookupService;
import org.veupathdb.service.access.service.user.EndUserSearchService;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@DisplayName("EndUserController")
class EndUserControllerTest
{
  private Request mockRequest;

  private EndUserController target;

  @BeforeEach
  void setUp() throws Exception {
    mockRequest = mock(Request.class);
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
      var limit  = RandUtil.randInt();
      var offset = RandUtil.randInt();
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
