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

public class rgbColor implements java.lang.Cloneable,
                                 java.io.Serializable
{
    public int RED;

    public int GREEN;

    public int BLUE;

    public rgbColor()
    {
    }

    public rgbColor(int RED, int GREEN, int BLUE)
    {
        this.RED = RED;
        this.GREEN = GREEN;
        this.BLUE = BLUE;
    }

    public boolean equals(java.lang.Object rhs)
    {
        if(this == rhs)
        {
            return true;
        }
        rgbColor r = null;
        if(rhs instanceof rgbColor)
        {
            r = (rgbColor)rhs;
        }

        if(r != null)
        {
            if(this.RED != r.RED)
            {
                return false;
            }
            if(this.GREEN != r.GREEN)
            {
                return false;
            }
            if(this.BLUE != r.BLUE)
            {
                return false;
            }

            return true;
        }

        return false;
    }

    public int hashCode()
    {
        int h_ = 5381;
        h_ = com.zeroc.IceInternal.HashUtil.hashAdd(h_, "::SmartHome::rgbColor");
        h_ = com.zeroc.IceInternal.HashUtil.hashAdd(h_, RED);
        h_ = com.zeroc.IceInternal.HashUtil.hashAdd(h_, GREEN);
        h_ = com.zeroc.IceInternal.HashUtil.hashAdd(h_, BLUE);
        return h_;
    }

    public rgbColor clone()
    {
        rgbColor c = null;
        try
        {
            c = (rgbColor)super.clone();
        }
        catch(CloneNotSupportedException ex)
        {
            assert false; // impossible
        }
        return c;
    }

    public void ice_writeMembers(com.zeroc.Ice.OutputStream ostr)
    {
        ostr.writeInt(this.RED);
        ostr.writeInt(this.GREEN);
        ostr.writeInt(this.BLUE);
    }

    public void ice_readMembers(com.zeroc.Ice.InputStream istr)
    {
        this.RED = istr.readInt();
        this.GREEN = istr.readInt();
        this.BLUE = istr.readInt();
    }

    static public void ice_write(com.zeroc.Ice.OutputStream ostr, rgbColor v)
    {
        if(v == null)
        {
            _nullMarshalValue.ice_writeMembers(ostr);
        }
        else
        {
            v.ice_writeMembers(ostr);
        }
    }

    static public rgbColor ice_read(com.zeroc.Ice.InputStream istr)
    {
        rgbColor v = new rgbColor();
        v.ice_readMembers(istr);
        return v;
    }

    static public void ice_write(com.zeroc.Ice.OutputStream ostr, int tag, java.util.Optional<rgbColor> v)
    {
        if(v != null && v.isPresent())
        {
            ice_write(ostr, tag, v.get());
        }
    }

    static public void ice_write(com.zeroc.Ice.OutputStream ostr, int tag, rgbColor v)
    {
        if(ostr.writeOptional(tag, com.zeroc.Ice.OptionalFormat.VSize))
        {
            ostr.writeSize(12);
            ice_write(ostr, v);
        }
    }

    static public java.util.Optional<rgbColor> ice_read(com.zeroc.Ice.InputStream istr, int tag)
    {
        if(istr.readOptional(tag, com.zeroc.Ice.OptionalFormat.VSize))
        {
            istr.skipSize();
            return java.util.Optional.of(rgbColor.ice_read(istr));
        }
        else
        {
            return java.util.Optional.empty();
        }
    }

    private static final rgbColor _nullMarshalValue = new rgbColor();

    /** @hidden */
    public static final long serialVersionUID = 480342246L;
}
