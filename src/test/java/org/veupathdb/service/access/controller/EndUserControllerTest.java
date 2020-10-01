package org.veupathdb.service.access.controller;

import javax.ws.rs.BadRequestException;
import javax.ws.rs.ForbiddenException;
import javax.ws.rs.core.Request;

import org.gusdb.fgputil.accountdb.UserProfile;
import org.junit.jupiter.api.*;
import org.veupathdb.lib.test.RandUtil;
import org.veupathdb.service.access.generated.model.ApprovalStatus;
import org.veupathdb.service.access.generated.model.EndUserCreateRequest;
import org.veupathdb.service.access.generated.model.EndUserCreateResponse;
import org.veupathdb.service.access.generated.model.EndUserList;
import org.veupathdb.service.access.service.user.EndUserCreationService;
import org.veupathdb.service.access.service.provider.ProviderService;
import org.veupathdb.service.access.service.staff.StaffService;
import org.veupathdb.lib.test.MockUtil;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@DisplayName("EndUserController")
class EndUserControllerTest
{
  private Util                   mockUtil;
  private EndUserCreationService mockUserService;
  private ProviderService        mockProviderService;
  private StaffService    mockStaffService;
  private Request         mockRequest;
  private UserProfile     mockUser;


  @BeforeEach
  void setUp() throws Exception {
    mockUtil            = MockUtil.mockSingleton(Util.class);
    mockUserService     = MockUtil.mockSingleton(EndUserCreationService.class);
    mockProviderService = MockUtil.mockSingleton(ProviderService.class);
    mockStaffService    = MockUtil.mockSingleton(StaffService.class);
    mockRequest         = mock(Request.class);
    mockUser            = mock(UserProfile.class);
  }

  @AfterEach
  void tearDown() throws Exception {
    MockUtil.resetSingleton(Util.class);
    MockUtil.resetSingleton(EndUserCreationService.class);
    MockUtil.resetSingleton(ProviderService.class);
    MockUtil.resetSingleton(StaffService.class);
  }

  @Nested
  @DisplayName("#getDatasetEndUsers(String, int, int, ApprovalStatus)")
  class GetDatasetEndUsers
  {
    @Nested
    @DisplayName("throws a ForbiddenException")
    class Throws401
    {
      @Test
      @DisplayName("when the input datasetId is null")
      void test1() {
        var target = new EndUserController(mockRequest);
        assertThrows(
          ForbiddenException.class,
          () -> target.getDatasetEndUsers(null, 1, 1, ApprovalStatus.APPROVED)
        );
      }

      @Test
      @DisplayName("when the input datasetId is empty")
      void test2() {
        var target = new EndUserController(mockRequest);
        assertThrows(
          ForbiddenException.class,
          () -> target.getDatasetEndUsers("", 1, 1, ApprovalStatus.APPROVED)
        );
      }

      @Test
      @DisplayName("when the input datasetId is blank")
      void test3() {
        var target = new EndUserController(mockRequest);
        assertThrows(
          ForbiddenException.class,
          () -> target.getDatasetEndUsers("   ", 1, 1, ApprovalStatus.APPROVED)
        );
      }

      @Test
      @DisplayName("when the user is not an owner or manager of the dataset")
      void test4() {
        var dsId   = "some string";
        var target = new EndUserController(mockRequest);

        doReturn(false).when(mockProviderService).isUserManager(mockRequest, dsId);
        doReturn(false).when(mockStaffService).isUserOwner(mockRequest);

        assertThrows(
          ForbiddenException.class,
          () -> target.getDatasetEndUsers(dsId, 1, 1, ApprovalStatus.APPROVED)
        );
      }
    }

    @Nested
    @DisplayName("returns a list of end user details")
    class Returns
    {
      @Test
      @DisplayName("when the user is an owner")
      void test1() {
        var dsId = "some dataset id";
        var out  = mock(EndUserList.class);

        doReturn(true).when(mockStaffService).isUserOwner(mockRequest);
        doReturn(false).when(mockProviderService).isUserManager(mockRequest, dsId);
//        doReturn(out).when(mockUserService).findEndUsers(dsId, 1, 1, ApprovalStatus.APPROVED);

        var ret = new EndUserController(mockRequest)
          .getDatasetEndUsers(dsId, 1, 1, ApprovalStatus.APPROVED);

        assertNotNull(ret);
        assertSame(out, ret.getEntity());
      }

      @Test
      @DisplayName("when the user is a manager")
      void test2() {
        var dsId = "some dataset id";
        var out  = mock(EndUserList.class);

        doReturn(false).when(mockStaffService).isUserOwner(mockRequest);
        doReturn(true).when(mockProviderService).isUserManager(mockRequest, dsId);
//        doReturn(out).when(mockUserService).findEndUsers(dsId, 1, 1, ApprovalStatus.APPROVED);

        var ret = new EndUserController(mockRequest)
          .getDatasetEndUsers(dsId, 1, 1, ApprovalStatus.APPROVED);

        assertNotNull(ret);
        assertSame(out, ret.getEntity());
      }
    }
  }

  @Nested
  @DisplayName("#postDatasetEndUsers(EndUserCreateRequest)")
  class PostDatasetEndUsers
  {
    @Nested
    @DisplayName("throws a ForbiddenException")
    class Throws401
    {
      @Test
      @DisplayName("when the requester was not a manager, owner, or the user themself")
      void test1() {
        var dsId     = RandUtil.randString();
        var userId   = RandUtil.randMinLong(1);
        var entityId = RandUtil.randMinLong(1);
        var entity   = mock(EndUserCreateRequest.class);

        doReturn(mockUser).when(mockUtil).mustGetUser(mockRequest);
        doReturn(false).when(mockStaffService).isUserOwner(userId);
        doReturn(false).when(mockProviderService).isUserManager(userId, dsId);
        doReturn(userId).when(mockUser).getUserId();
        doReturn(entityId).when(entity).getUserId();
        doReturn(dsId).when(entity).getDatasetId();

        var target = new EndUserController(mockRequest);

        assertThrows(
          ForbiddenException.class,
          () -> target.postDatasetEndUsers(entity)
        );
      }
    }

    @Nested
    @DisplayName("throws a BadRequestException")
    class Throws400
    {
      @Test
      @DisplayName("when an owner requests access for an already existing end user")
      void test1() {
        var dsId     = RandUtil.randString();
        var userId   = RandUtil.randMinLong(1);
        var entityId = RandUtil.randMinLong(1);
        var entity   = mock(EndUserCreateRequest.class);

        doReturn(mockUser).when(mockUtil).mustGetUser(mockRequest);
        doReturn(true).when(mockStaffService).isUserOwner(userId);
        doReturn(false).when(mockProviderService).isUserManager(userId, dsId);
        doReturn(userId).when(mockUser).getUserId();
        doReturn(entityId).when(entity).getUserId();
        doReturn(dsId).when(entity).getDatasetId();
//        doReturn(true).when(mockUserService).checkEndUserExists(entityId, dsId);

        var target = new EndUserController(mockRequest);

        assertThrows(
          BadRequestException.class,
          () -> target.postDatasetEndUsers(entity)
        );
      }

      @Test
      @DisplayName("when a dataset requests access for an already existing user")
      void test2() {
        var dsId     = RandUtil.randString();
        var userId   = RandUtil.randMinLong(1);
        var entityId = RandUtil.randMinLong(1);
        var entity   = mock(EndUserCreateRequest.class);

        doReturn(mockUser).when(mockUtil).mustGetUser(mockRequest);
        doReturn(false).when(mockStaffService).isUserOwner(userId);
        doReturn(true).when(mockProviderService).isUserManager(userId, dsId);
        doReturn(userId).when(mockUser).getUserId();
        doReturn(entityId).when(entity).getUserId();
        doReturn(dsId).when(entity).getDatasetId();
//        doReturn(true).when(mockUserService).checkEndUserExists(entityId, dsId);

        var target = new EndUserController(mockRequest);

        assertThrows(
          BadRequestException.class,
          () -> target.postDatasetEndUsers(entity)
        );
      }

      @Test
      @DisplayName("when a self user requests access that they already have")
      void test3() {
        var dsId     = RandUtil.randString();
        var userId   = RandUtil.randMinLong(1);
        var entity   = mock(EndUserCreateRequest.class);

        doReturn(mockUser).when(mockUtil).mustGetUser(mockRequest);
        doReturn(false).when(mockStaffService).isUserOwner(userId);
        doReturn(false).when(mockProviderService).isUserManager(userId, dsId);
        doReturn(userId).when(mockUser).getUserId();
        doReturn(userId).when(entity).getUserId();
        doReturn(dsId).when(entity).getDatasetId();
//        doReturn(true).when(mockUserService).checkEndUserExists(userId, dsId);

        var target = new EndUserController(mockRequest);

        assertThrows(
          BadRequestException.class,
          () -> target.postDatasetEndUsers(entity)
        );
      }
    }

    @Nested
    @DisplayName("returns the new end user record id")
    class Returns
    {
      @Test
      @DisplayName("when a owner successfully creates a new user access record")
      void test1() {
        var dsId     = RandUtil.randString();
        var userId   = RandUtil.randMinLong(1);
        var entityId = RandUtil.randMinLong(1);
        var entity   = mock(EndUserCreateRequest.class);
        var recId    = RandUtil.randString();

        doReturn(mockUser).when(mockUtil).mustGetUser(mockRequest);
        doReturn(true).when(mockStaffService).isUserOwner(userId);
        doReturn(false).when(mockProviderService).isUserManager(userId, dsId);
        doReturn(userId).when(mockUser).getUserId();
        doReturn(entityId).when(entity).getUserId();
        doReturn(dsId).when(entity).getDatasetId();
//        doReturn(false).when(mockUserService).checkEndUserExists(entityId, dsId);
//        doReturn(recId).when(mockUserService).endUserManagerCreate(entity);

        var res = new EndUserController(mockRequest).postDatasetEndUsers(entity);

        assertNotNull(res);
        assertNotNull(res.getEntity());
        assertTrue(res.getEntity() instanceof EndUserCreateResponse);
        assertEquals(recId, ((EndUserCreateResponse) res.getEntity()).getEndUserId());
      }

      @Test
      @DisplayName("when a manager successfully creates a new user access record")
      void test2() {
        var dsId     = RandUtil.randString();
        var userId   = RandUtil.randMinLong(1);
        var entityId = RandUtil.randMinLong(1);
        var entity   = mock(EndUserCreateRequest.class);
        var recId    = RandUtil.randString();

        doReturn(mockUser).when(mockUtil).mustGetUser(mockRequest);
        doReturn(false).when(mockStaffService).isUserOwner(userId);
        doReturn(true).when(mockProviderService).isUserManager(userId, dsId);
        doReturn(userId).when(mockUser).getUserId();
        doReturn(entityId).when(entity).getUserId();
        doReturn(dsId).when(entity).getDatasetId();
//        doReturn(false).when(mockUserService).checkEndUserExists(entityId, dsId);
//        doReturn(recId).when(mockUserService).endUserManagerCreate(entity);

        var res = new EndUserController(mockRequest).postDatasetEndUsers(entity);

        assertNotNull(res);
        assertNotNull(res.getEntity());
        assertTrue(res.getEntity() instanceof EndUserCreateResponse);
        assertEquals(recId, ((EndUserCreateResponse) res.getEntity()).getEndUserId());
      }

      @Test
      @DisplayName("when a self user successfully creates a new user access record")
      void test3() {
        var dsId     = RandUtil.randString();
        var userId   = RandUtil.randMinLong(1);
        var entity   = mock(EndUserCreateRequest.class);
        var recId    = RandUtil.randString();

        doReturn(mockUser).when(mockUtil).mustGetUser(mockRequest);
        doReturn(false).when(mockStaffService).isUserOwner(userId);
        doReturn(false).when(mockProviderService).isUserManager(userId, dsId);
        doReturn(userId).when(mockUser).getUserId();
        doReturn(userId).when(entity).getUserId();
        doReturn(dsId).when(entity).getDatasetId();
//        doReturn(false).when(mockUserService).checkEndUserExists(userId, dsId);
//        doReturn(recId).when(mockUserService).endUserSelfCreate(entity);

        var res = new EndUserController(mockRequest).postDatasetEndUsers(entity);

        assertNotNull(res);
        assertNotNull(res.getEntity());
        assertTrue(res.getEntity() instanceof EndUserCreateResponse);
        assertEquals(recId, ((EndUserCreateResponse) res.getEntity()).getEndUserId());
      }
    }
  }
}
