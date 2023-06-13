package org.veupathdb.service.access.model;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.veupathdb.lib.test.RandUtil;
import org.veupathdb.service.access.service.email.EmailUtil;

import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("Email")
class EmailTest
{
  @Test
  @DisplayName("Email.to accessors function as expected")
  void test1() {
    final var target = new Email();
    final var email  = new String[]{ RandUtil.randString() };

    assertSame(target, target.setTo(email));
    assertSame(email, target.getTo());

    assertThrows(NullPointerException.class, () -> target.setTo(null));
  }

  @Test
  void templateTest() {
    final Map<Dataset.Property, String> properties = new HashMap<>();
    properties.put(Dataset.Property.REQUEST_EMAIL, "Test");
    properties.put(Dataset.Property.REQUEST_EMAIL_BCC, "Test2");

    final String rendered = EmailUtil.getInstance().populateTemplate("RENDERED: $dataset.requestEmailBcc$", new Dataset().putProperties(properties));
    System.out.println(rendered);
  }

  @Test
  @DisplayName("Email.cc accessors function as expected")
  void test2() {
    final var target = new Email();
    final var email  = new String[]{ RandUtil.randString() };

    assertSame(target, target.setCc(email));
    assertSame(email, target.getCc());

    assertThrows(NullPointerException.class, () -> target.setCc(null));
  }

  @Test
  @DisplayName("Email.bcc accessors function as expected")
  void test3() {
    final var target = new Email();
    final var email  = new String[]{ RandUtil.randString() };

    assertSame(target, target.setBcc(email));
    assertSame(email, target.getBcc());

    assertThrows(NullPointerException.class, () -> target.setBcc(null));
  }

  @Test
  @DisplayName("Email.from accessors function as expected")
  void test4() {
    final var target = new Email();
    final var email  = RandUtil.randString();

    assertSame(target, target.setFrom(email));
    assertSame(email, target.getFrom());

    assertThrows(NullPointerException.class, () -> target.setFrom(null));
  }

  @Test
  @DisplayName("Email.subject accessors function as expected")
  void test5() {
    final var target = new Email();
    final var subject  = RandUtil.randString();

    assertSame(target, target.setSubject(subject));
    assertSame(subject, target.getSubject());

    assertThrows(NullPointerException.class, () -> target.setSubject(null));
  }

  @Test
  @DisplayName("Email.body accessors function as expected")
  void test6() {
    final var target = new Email();
    final var subject  = RandUtil.randString();

    assertSame(target, target.setBody(subject));
    assertSame(subject, target.getBody());

    assertThrows(NullPointerException.class, () -> target.setBody(null));
  }
}
