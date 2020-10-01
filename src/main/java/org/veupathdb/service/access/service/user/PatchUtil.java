package org.veupathdb.service.access.service.user;

import java.util.Collection;
import java.util.function.Consumer;
import java.util.function.Function;
import javax.ws.rs.BadRequestException;
import javax.ws.rs.ForbiddenException;

import org.apache.logging.log4j.Logger;
import org.veupathdb.lib.container.jaxrs.providers.LogProvider;
import org.veupathdb.service.access.generated.model.EndUserPatch;

class PatchUtil
{
  private static final String
    errBadPatchOp = "Cannot perform operation \"%s\" on field \"%s\".",
    errSetNull    = "Cannot set field \"%s\" to null.",
    errBadType    = "Expected a value of type \"%s\", got \"%s\"";

  private final Logger log = LogProvider.logger(getClass());

  <T> T enforceType(
    final Object value,
    final Class<T> type
  ) {
    log.trace("EndUserService$Patch#enforceType(value, type)");

    try {
      return type.cast(value);
    } catch (Exception e) {
      final String name;
      if (type.getName().endsWith("[]") || Collection.class.isAssignableFrom(type))
        name = "array";
      else if (type.equals(String.class))
        name = "string";
      else if (Number.class.isAssignableFrom(type))
        name = "number";
      else if (type.equals(Boolean.class))
        name = "boolean";
      else
        name = "object";

      throw new BadRequestException(String.format(errBadType, name,
        value.getClass().getSimpleName()
      ));
    }
  }

  void strVal(
    final EndUserPatch patch,
    final Consumer<String> func
  ) {
    log.trace("EndUserService$Patch#strVal(patch, func)");

    switch (patch.getOp()) {
      case ADD, REPLACE:
        enforceNotNull(patch);
        func.accept(enforceType(
          patch.getValue(),
          String.class
        ));
      case REMOVE:
        func.accept(null);
      default:
        throw forbiddenOp(patch);
    }
  }

  <T> void enumVal(
    final EndUserPatch patch,
    final Function<String, T> map,
    final Consumer<T> func
  ) {
    log.trace("EndUserService$Patch#enumVal(patch, map, func)");

    enforceNotNull(patch);
    func.accept(map.apply(enforceType(patch, String.class).toUpperCase()));
  }


  void enforceOpIn(
    final EndUserPatch patch,
    final EndUserPatch.OpType... in
  ) {
    log.trace("EndUserService$Patch#enforceOpIn(patch, ...in)");

    for (final var i : in) {
      if (i.equals(patch.getOp()))
        return;
    }

    throw forbiddenOp(patch);
  }

  RuntimeException forbiddenOp(final EndUserPatch op) {
    return new ForbiddenException(
      String.format(
        errBadPatchOp,
        op.getOp().name(),
        op.getPath()
      ));
  }

  void enforceNotNull(final EndUserPatch patch) {
    log.trace("EndUserService$Patch#enforceNotNull(patch)");

    if (patch.getValue() == null)
      throw new ForbiddenException(
        String.format(errSetNull, patch.getPath()));
  }
}
