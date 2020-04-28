// Generated by the protocol buffer compiler.  DO NOT EDIT!
// source: event.proto

package sr.event.gen;

/**
 * Protobuf enum {@code event.Type}
 */
public enum Type
    implements com.google.protobuf.ProtocolMessageEnum {
  /**
   * <code>FORMULA1 = 0;</code>
   */
  FORMULA1(0),
  /**
   * <code>FOOTBALL = 1;</code>
   */
  FOOTBALL(1),
  /**
   * <code>VOLLEYBALL = 2;</code>
   */
  VOLLEYBALL(2),
  /**
   * <code>SKIJUMPING = 3;</code>
   */
  SKIJUMPING(3),
  UNRECOGNIZED(-1),
  ;

  /**
   * <code>FORMULA1 = 0;</code>
   */
  public static final int FORMULA1_VALUE = 0;
  /**
   * <code>FOOTBALL = 1;</code>
   */
  public static final int FOOTBALL_VALUE = 1;
  /**
   * <code>VOLLEYBALL = 2;</code>
   */
  public static final int VOLLEYBALL_VALUE = 2;
  /**
   * <code>SKIJUMPING = 3;</code>
   */
  public static final int SKIJUMPING_VALUE = 3;


  public final int getNumber() {
    if (this == UNRECOGNIZED) {
      throw new java.lang.IllegalArgumentException(
          "Can't get the number of an unknown enum value.");
    }
    return value;
  }

  /**
   * @param value The numeric wire value of the corresponding enum entry.
   * @return The enum associated with the given numeric wire value.
   * @deprecated Use {@link #forNumber(int)} instead.
   */
  @java.lang.Deprecated
  public static Type valueOf(int value) {
    return forNumber(value);
  }

  /**
   * @param value The numeric wire value of the corresponding enum entry.
   * @return The enum associated with the given numeric wire value.
   */
  public static Type forNumber(int value) {
    switch (value) {
      case 0: return FORMULA1;
      case 1: return FOOTBALL;
      case 2: return VOLLEYBALL;
      case 3: return SKIJUMPING;
      default: return null;
    }
  }

  public static com.google.protobuf.Internal.EnumLiteMap<Type>
      internalGetValueMap() {
    return internalValueMap;
  }
  private static final com.google.protobuf.Internal.EnumLiteMap<
      Type> internalValueMap =
        new com.google.protobuf.Internal.EnumLiteMap<Type>() {
          public Type findValueByNumber(int number) {
            return Type.forNumber(number);
          }
        };

  public final com.google.protobuf.Descriptors.EnumValueDescriptor
      getValueDescriptor() {
    return getDescriptor().getValues().get(ordinal());
  }
  public final com.google.protobuf.Descriptors.EnumDescriptor
      getDescriptorForType() {
    return getDescriptor();
  }
  public static final com.google.protobuf.Descriptors.EnumDescriptor
      getDescriptor() {
    return sr.event.gen.EventProto.getDescriptor().getEnumTypes().get(0);
  }

  private static final Type[] VALUES = values();

  public static Type valueOf(
      com.google.protobuf.Descriptors.EnumValueDescriptor desc) {
    if (desc.getType() != getDescriptor()) {
      throw new java.lang.IllegalArgumentException(
        "EnumValueDescriptor is not for this type.");
    }
    if (desc.getIndex() == -1) {
      return UNRECOGNIZED;
    }
    return VALUES[desc.getIndex()];
  }

  private final int value;

  private Type(int value) {
    this.value = value;
  }

  // @@protoc_insertion_point(enum_scope:event.Type)
}
