package org.veupathdb.service.access.repo;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.veupathdb.service.access.service.QueryUtil;
import org.veupathdb.service.access.service.dataset.DatasetRepo;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@DisplayName("DatasetRepo")
class DatasetRepoTest
{
  private Connection mockConnection;

  private PreparedStatement mockPreparedStatement;

  private ResultSet mockResultSet;

  private QueryUtil mockUtil;

  @BeforeEach
  void setUp() throws Exception {
    mockUtil              = mock(QueryUtil.class);
    mockPreparedStatement = mock(PreparedStatement.class);
    mockConnection        = mock(Connection.class);
    mockResultSet         = mock(ResultSet.class);

    doReturn(mockConnection).when(mockUtil).getAppDbConnection();
    doReturn(mockConnection).when(mockUtil).getAcctDbConnection();

    doReturn(mockPreparedStatement)
      .when(mockUtil)
      .prepareSqlStatement(any(), anyString(), any());

    doReturn(mockPreparedStatement).when(mockUtil).prepareSqlStatement(any(), anyString());

    doReturn(mockResultSet).when(mockUtil).runQueryLogged(any(), anyString());

    var field = QueryUtil.class.getDeclaredField("instance");
    field.setAccessible(true);
    field.set(null, mockUtil);
  }

  @Nested
  @DisplayName("$Select")
  class Select
  {
    @Nested
    @DisplayName("#getDatasetEmails(String)")
    class Emails
    {
      @Nested
      @DisplayName("throws an IllegalStateException")
      class Throws
      {
        @Test
        @DisplayName("when the result set has no rows")
        void test1() throws Exception {
          var dsId = "hello world";

          doReturn(false).when(mockResultSet).next();

//          assertThrows(
//            IllegalStateException.class,
//            () -> new DatasetRepo.Select().getDatasetEmails(dsId)
//          );

          verify(mockUtil).getAppDbConnection();
          verify(mockUtil).prepareSqlStatement(mockConnection, SQL.Select.Datasets.Emails);
          verify(mockUtil).runQueryLogged(mockPreparedStatement, SQL.Select.Datasets.Emails);
          verify(mockPreparedStatement).setString(1, dsId);
          verify(mockResultSet).next();
          verify(mockConnection).close();
          verify(mockPreparedStatement).close();
          verify(mockResultSet).close();
        }

        @Test
        @DisplayName("when the result set has 1 row")
        void test2() throws Exception {
          var dsId   = "hello world";
          var email1 = "foo@bar.fizz";

          doReturn(true, false).when(mockResultSet).next();
//          doReturn(DatasetRepo.Select.EMAIL_TO_PROP).when(mockResultSet).getString(1);
          doReturn(email1).when(mockResultSet).getString(2);

//          assertThrows(
//            IllegalStateException.class,
//            () -> new DatasetRepo.Select().getDatasetEmails(dsId)
//          );

          verify(mockUtil).getAppDbConnection();
          verify(mockUtil).prepareSqlStatement(mockConnection, SQL.Select.Datasets.Emails);
          verify(mockUtil).runQueryLogged(mockPreparedStatement, SQL.Select.Datasets.Emails);
          verify(mockPreparedStatement).setString(1, dsId);
          verify(mockResultSet, times(2)).next();
          verify(mockResultSet).getString(1);
          verify(mockResultSet).getString(2);
          verify(mockConnection).close();
          verify(mockPreparedStatement).close();
          verify(mockResultSet).close();
        }

        @Test
        @DisplayName("when the result set has duplicate \"TO\" rows")
        void test3() throws Exception {
          var dsId   = "hello world";
          var email1 = "foo@bar.fizz";

          doReturn(true, true, false).when(mockResultSet).next();
//          doReturn(DatasetRepo.Select.EMAIL_TO_PROP).when(mockResultSet).getString(1);
          doReturn(email1).when(mockResultSet).getString(2);

//          assertThrows(
//            IllegalStateException.class,
//            () -> new DatasetRepo.Select().getDatasetEmails(dsId)
//          );

          verify(mockUtil).getAppDbConnection();
          verify(mockUtil).prepareSqlStatement(mockConnection, SQL.Select.Datasets.Emails);
          verify(mockUtil).runQueryLogged(mockPreparedStatement, SQL.Select.Datasets.Emails);
          verify(mockPreparedStatement).setString(1, dsId);
          verify(mockResultSet, times(2)).next();
          verify(mockResultSet, times(2)).getString(1);
          verify(mockResultSet).getString(2);
          verify(mockConnection).close();
          verify(mockPreparedStatement).close();
          verify(mockResultSet).close();
        }

        @Test
        @DisplayName("when the result set has duplicate \"FROM\" rows")
        void test4() throws Exception {
          var dsId   = "hello world";
          var email1 = "foo@bar.fizz";

          doReturn(true, true, false).when(mockResultSet).next();
//          doReturn(DatasetRepo.Select.EMAIL_FROM_PROP).when(mockResultSet).getString(1);
          doReturn(email1).when(mockResultSet).getString(2);

//          assertThrows(
//            IllegalStateException.class,
//            () -> new DatasetRepo.Select().getDatasetEmails(dsId)
//          );

          verify(mockUtil).getAppDbConnection();
          verify(mockUtil).prepareSqlStatement(mockConnection, SQL.Select.Datasets.Emails);
          verify(mockUtil).runQueryLogged(mockPreparedStatement, SQL.Select.Datasets.Emails);
          verify(mockPreparedStatement).setString(1, dsId);
          verify(mockResultSet, times(2)).next();
          verify(mockResultSet, times(2)).getString(1);
          verify(mockResultSet).getString(2);
          verify(mockConnection).close();
          verify(mockPreparedStatement).close();
          verify(mockResultSet).close();
        }

        @Test
        @DisplayName("when the result set has unrecognized properties")
        void test5() throws Exception {
          var dsId   = "hello world";
          var email1 = "foo@bar.fizz";

          doReturn(true, true, false).when(mockResultSet).next();
          doReturn("what am i?").when(mockResultSet).getString(1);
          doReturn(email1).when(mockResultSet).getString(2);

//          assertThrows(
//            IllegalStateException.class,
//            () -> new DatasetRepo.Select().getDatasetEmails(dsId)
//          );

          verify(mockUtil).getAppDbConnection();
          verify(mockUtil).prepareSqlStatement(mockConnection, SQL.Select.Datasets.Emails);
          verify(mockUtil).runQueryLogged(mockPreparedStatement, SQL.Select.Datasets.Emails);
          verify(mockPreparedStatement).setString(1, dsId);
          verify(mockResultSet).next();
          verify(mockResultSet).getString(1);
          verify(mockResultSet, never()).getString(2);
          verify(mockConnection).close();
          verify(mockPreparedStatement).close();
          verify(mockResultSet).close();
        }
      }

      @Nested
      @DisplayName("returns the expected result")
      class Returns
      {
        @Test
        @DisplayName("when the result set contains the expected values")
        void test1() throws Exception {
          var dsId   = "hello world";
          var email1 = "foo@bar.fizz";
          var email2 = "fizz@buzz.foo";

          doReturn(true, true, false).when(mockResultSet).next();
//          doReturn(
//            DatasetRepo.Select.EMAIL_TO_PROP,
//            DatasetRepo.Select.EMAIL_FROM_PROP
//          ).when(mockResultSet).getString(1);
          doReturn(email1, email2).when(mockResultSet).getString(2);

//          var out = new DatasetRepo.Select().getDatasetEmails(dsId);

//          assertNotNull(out);
//          assertEquals(email1, out.to);
//          assertEquals(email2, out.from);

          verify(mockUtil).getAppDbConnection();
          verify(mockUtil).prepareSqlStatement(mockConnection, SQL.Select.Datasets.Emails);
          verify(mockUtil).runQueryLogged(mockPreparedStatement, SQL.Select.Datasets.Emails);
          verify(mockPreparedStatement).setString(1, dsId);
          verify(mockResultSet, times(3)).next();
          verify(mockResultSet, times(2)).getString(1);
          verify(mockResultSet, times(2)).getString(2);
          verify(mockConnection).close();
          verify(mockPreparedStatement).close();
          verify(mockResultSet).close();
        }
      }
    }
  }
}
