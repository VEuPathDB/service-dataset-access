package org.veupathdb.service.access.service;

import java.util.List;
import java.util.regex.Pattern;

import org.veupathdb.service.access.model.DatasetEmails;
import org.veupathdb.service.access.repo.DatasetRepo;

public class EmailService
{
  private static final Pattern splitPattern = Pattern.compile("((?:\".+?\"|[^@ ]+)@[^, ]+)(?:,|$)");

  public static DatasetEmails getDatasetEmails(final String datasetId) throws Exception {
    return DatasetRepo.Select.datasetEmails(datasetId);
  }

  public static List<String> getDatasetAdminEmails(final String datasetId) throws Exception {

    var emails  = ""; // populate by query
    var matches = splitPattern.matcher(emails);
    throw new Exception("Not implemented yet");
//    return matches.results()
//      .map(r -> r.group(1))
//      .toArray(String[]::new);
  }

  public static String[] getSiteEmails() throws Exception {
    var emails  = ""; // populate by query
    var matches = splitPattern.matcher(emails);
    throw new Exception("Not implemented yet");
//    return matches.results()
//      .map(r -> r.group(1))
//      .toArray(String[]::new);
  }

  public static void sendEmail()
}
