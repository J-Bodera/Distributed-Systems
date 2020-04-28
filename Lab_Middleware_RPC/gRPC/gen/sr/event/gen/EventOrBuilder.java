// Generated by the protocol buffer compiler.  DO NOT EDIT!
// source: event.proto

package sr.event.gen;

public interface EventOrBuilder extends
    // @@protoc_insertion_point(interface_extends:event.Event)
    com.google.protobuf.MessageOrBuilder {

  /**
   * <code>repeated .event.Type type = 1;</code>
   * @return A list containing the type.
   */
  java.util.List<sr.event.gen.Type> getTypeList();
  /**
   * <code>repeated .event.Type type = 1;</code>
   * @return The count of type.
   */
  int getTypeCount();
  /**
   * <code>repeated .event.Type type = 1;</code>
   * @param index The index of the element to return.
   * @return The type at the given index.
   */
  sr.event.gen.Type getType(int index);
  /**
   * <code>repeated .event.Type type = 1;</code>
   * @return A list containing the enum numeric values on the wire for type.
   */
  java.util.List<java.lang.Integer>
  getTypeValueList();
  /**
   * <code>repeated .event.Type type = 1;</code>
   * @param index The index of the value to return.
   * @return The enum numeric value on the wire of type at the given index.
   */
  int getTypeValue(int index);

  /**
   * <code>.event.Date date = 2;</code>
   * @return Whether the date field is set.
   */
  boolean hasDate();
  /**
   * <code>.event.Date date = 2;</code>
   * @return The date.
   */
  sr.event.gen.Date getDate();
  /**
   * <code>.event.Date date = 2;</code>
   */
  sr.event.gen.DateOrBuilder getDateOrBuilder();

  /**
   * <code>.event.Address address = 3;</code>
   * @return Whether the address field is set.
   */
  boolean hasAddress();
  /**
   * <code>.event.Address address = 3;</code>
   * @return The address.
   */
  sr.event.gen.Address getAddress();
  /**
   * <code>.event.Address address = 3;</code>
   */
  sr.event.gen.AddressOrBuilder getAddressOrBuilder();
}
