//
// Copyright (c) ZeroC, Inc. All rights reserved.
//
//
// Ice version 3.7.3
//
// <auto-generated>
//
// Generated from file `smarthome.ice'
//
// Warning: do not edit this file.
//
// </auto-generated>
//

package SmartHome;

public enum unit implements java.io.Serializable
{
    CELSIUS(0),
    FAHRENHEIT(1);

    public int value()
    {
        return _value;
    }

    public static unit valueOf(int v)
    {
        switch(v)
        {
        case 0:
            return CELSIUS;
        case 1:
            return FAHRENHEIT;
        }
        return null;
    }

    private unit(int v)
    {
        _value = v;
    }

    public void ice_write(com.zeroc.Ice.OutputStream ostr)
    {
        ostr.writeEnum(_value, 1);
    }

    public static void ice_write(com.zeroc.Ice.OutputStream ostr, unit v)
    {
        if(v == null)
        {
            ostr.writeEnum(SmartHome.unit.CELSIUS.value(), 1);
        }
        else
        {
            ostr.writeEnum(v.value(), 1);
        }
    }

    public static unit ice_read(com.zeroc.Ice.InputStream istr)
    {
        int v = istr.readEnum(1);
        return validate(v);
    }

    public static void ice_write(com.zeroc.Ice.OutputStream ostr, int tag, java.util.Optional<unit> v)
    {
        if(v != null && v.isPresent())
        {
            ice_write(ostr, tag, v.get());
        }
    }

    public static void ice_write(com.zeroc.Ice.OutputStream ostr, int tag, unit v)
    {
        if(ostr.writeOptional(tag, com.zeroc.Ice.OptionalFormat.Size))
        {
            ice_write(ostr, v);
        }
    }

    public static java.util.Optional<unit> ice_read(com.zeroc.Ice.InputStream istr, int tag)
    {
        if(istr.readOptional(tag, com.zeroc.Ice.OptionalFormat.Size))
        {
            return java.util.Optional.of(ice_read(istr));
        }
        else
        {
            return java.util.Optional.empty();
        }
    }

    private static unit validate(int v)
    {
        final unit e = valueOf(v);
        if(e == null)
        {
            throw new com.zeroc.Ice.MarshalException("enumerator value " + v + " is out of range");
        }
        return e;
    }

    private final int _value;
}
