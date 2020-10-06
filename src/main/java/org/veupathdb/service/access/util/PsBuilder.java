package org.veupathdb.service.access.util;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Types;
import java.util.ArrayList;
import java.util.List;

import oracle.jdbc.internal.OraclePreparedStatement;
import org.apache.commons.dbcp2.DelegatingPreparedStatement;

public class PsBuilder
{
  private final List<Value> values;

  public PsBuilder() {
    values = new ArrayList<>();
  }

  public PsBuilder setByte(final byte value) {
    values.add(new Value(value, Types.TINYINT));
    return this;
  }

  public PsBuilder setShort(final short value) {
    values.add(new Value(value, Types.SMALLINT));
    return this;
  }

  public PsBuilder setInt(final int value) {
    values.add(new Value(value, Types.INTEGER));
    return this;
  }

  public PsBuilder setLong(final long value) {
    values.add(new Value(value, Types.BIGINT));
    return this;
  }

  public PsBuilder setString(final String value) {
    values.add(new Value(value, Types.VARCHAR));
    return this;
  }

  public PsBuilder setObject(final Object value, final int type) {
    values.add(new Value(value, type));
    return this;
  }

  public PsBuilder setValue(final Value value) {
    values.add(value);
    return this;
  }

  public PsBuilder setBoolean(final boolean value) {
    values.add(new Value(value, Types.TINYINT));
    return this;
  }

  public PsBuilder setReturnInt() {
    values.add(new Value(Types.INTEGER));
    return this;
  }

  public void build(final PreparedStatement ps) throws SQLException {
    var size = values.size();
    var cast = (OraclePreparedStatement) ((DelegatingPreparedStatement) ps).getInnermostDelegate();

    for (int i = 0, j = 1; i < size; i++, j++) {
      var val = values.get(i);

      if (val.isOut())
        cast.registerReturnParameter(j, val.getType());
      else
        cast.setObject(j, val.getValue(), val.getType());
    }
  }

  public static class Value
  {
    private final Object value;
    private final int    type;
    private final boolean out;

    public Value(final Object v, final int t) {
      value = v;
      type  = t;
      out   = false;
    }

    public Value(final int t) {
      value = null;
      type  = t;
      out   = true;
    }

    public Object getValue() {
      return value;
    }

    public int getType() {
      return type;
    }

    public boolean isOut() {
      return out;
    }
  }
}
