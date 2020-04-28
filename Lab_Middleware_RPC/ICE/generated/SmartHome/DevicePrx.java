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

public interface DevicePrx extends com.zeroc.Ice.ObjectPrx
{
    default void on()
    {
        on(com.zeroc.Ice.ObjectPrx.noExplicitContext);
    }

    default void on(java.util.Map<String, String> context)
    {
        _iceI_onAsync(context, true).waitForResponse();
    }

    default java.util.concurrent.CompletableFuture<Void> onAsync()
    {
        return _iceI_onAsync(com.zeroc.Ice.ObjectPrx.noExplicitContext, false);
    }

    default java.util.concurrent.CompletableFuture<Void> onAsync(java.util.Map<String, String> context)
    {
        return _iceI_onAsync(context, false);
    }

    /**
     * @hidden
     * @param context -
     * @param sync -
     * @return -
     **/
    default com.zeroc.IceInternal.OutgoingAsync<Void> _iceI_onAsync(java.util.Map<String, String> context, boolean sync)
    {
        com.zeroc.IceInternal.OutgoingAsync<Void> f = new com.zeroc.IceInternal.OutgoingAsync<>(this, "on", null, sync, null);
        f.invoke(false, context, null, null, null);
        return f;
    }

    default void off()
    {
        off(com.zeroc.Ice.ObjectPrx.noExplicitContext);
    }

    default void off(java.util.Map<String, String> context)
    {
        _iceI_offAsync(context, true).waitForResponse();
    }

    default java.util.concurrent.CompletableFuture<Void> offAsync()
    {
        return _iceI_offAsync(com.zeroc.Ice.ObjectPrx.noExplicitContext, false);
    }

    default java.util.concurrent.CompletableFuture<Void> offAsync(java.util.Map<String, String> context)
    {
        return _iceI_offAsync(context, false);
    }

    /**
     * @hidden
     * @param context -
     * @param sync -
     * @return -
     **/
    default com.zeroc.IceInternal.OutgoingAsync<Void> _iceI_offAsync(java.util.Map<String, String> context, boolean sync)
    {
        com.zeroc.IceInternal.OutgoingAsync<Void> f = new com.zeroc.IceInternal.OutgoingAsync<>(this, "off", null, sync, null);
        f.invoke(false, context, null, null, null);
        return f;
    }

    default status getStatus()
    {
        return getStatus(com.zeroc.Ice.ObjectPrx.noExplicitContext);
    }

    default status getStatus(java.util.Map<String, String> context)
    {
        return _iceI_getStatusAsync(context, true).waitForResponse();
    }

    default java.util.concurrent.CompletableFuture<status> getStatusAsync()
    {
        return _iceI_getStatusAsync(com.zeroc.Ice.ObjectPrx.noExplicitContext, false);
    }

    default java.util.concurrent.CompletableFuture<status> getStatusAsync(java.util.Map<String, String> context)
    {
        return _iceI_getStatusAsync(context, false);
    }

    /**
     * @hidden
     * @param context -
     * @param sync -
     * @return -
     **/
    default com.zeroc.IceInternal.OutgoingAsync<status> _iceI_getStatusAsync(java.util.Map<String, String> context, boolean sync)
    {
        com.zeroc.IceInternal.OutgoingAsync<status> f = new com.zeroc.IceInternal.OutgoingAsync<>(this, "getStatus", null, sync, null);
        f.invoke(true, context, null, null, istr -> {
                     status ret;
                     ret = status.ice_read(istr);
                     return ret;
                 });
        return f;
    }

    default String getName()
    {
        return getName(com.zeroc.Ice.ObjectPrx.noExplicitContext);
    }

    default String getName(java.util.Map<String, String> context)
    {
        return _iceI_getNameAsync(context, true).waitForResponse();
    }

    default java.util.concurrent.CompletableFuture<java.lang.String> getNameAsync()
    {
        return _iceI_getNameAsync(com.zeroc.Ice.ObjectPrx.noExplicitContext, false);
    }

    default java.util.concurrent.CompletableFuture<java.lang.String> getNameAsync(java.util.Map<String, String> context)
    {
        return _iceI_getNameAsync(context, false);
    }

    /**
     * @hidden
     * @param context -
     * @param sync -
     * @return -
     **/
    default com.zeroc.IceInternal.OutgoingAsync<java.lang.String> _iceI_getNameAsync(java.util.Map<String, String> context, boolean sync)
    {
        com.zeroc.IceInternal.OutgoingAsync<java.lang.String> f = new com.zeroc.IceInternal.OutgoingAsync<>(this, "getName", null, sync, null);
        f.invoke(true, context, null, null, istr -> {
                     String ret;
                     ret = istr.readString();
                     return ret;
                 });
        return f;
    }

    default String getManual()
    {
        return getManual(com.zeroc.Ice.ObjectPrx.noExplicitContext);
    }

    default String getManual(java.util.Map<String, String> context)
    {
        return _iceI_getManualAsync(context, true).waitForResponse();
    }

    default java.util.concurrent.CompletableFuture<java.lang.String> getManualAsync()
    {
        return _iceI_getManualAsync(com.zeroc.Ice.ObjectPrx.noExplicitContext, false);
    }

    default java.util.concurrent.CompletableFuture<java.lang.String> getManualAsync(java.util.Map<String, String> context)
    {
        return _iceI_getManualAsync(context, false);
    }

    /**
     * @hidden
     * @param context -
     * @param sync -
     * @return -
     **/
    default com.zeroc.IceInternal.OutgoingAsync<java.lang.String> _iceI_getManualAsync(java.util.Map<String, String> context, boolean sync)
    {
        com.zeroc.IceInternal.OutgoingAsync<java.lang.String> f = new com.zeroc.IceInternal.OutgoingAsync<>(this, "getManual", null, sync, null);
        f.invoke(true, context, null, null, istr -> {
                     String ret;
                     ret = istr.readString();
                     return ret;
                 });
        return f;
    }

    default deviceType getDeviceType()
    {
        return getDeviceType(com.zeroc.Ice.ObjectPrx.noExplicitContext);
    }

    default deviceType getDeviceType(java.util.Map<String, String> context)
    {
        return _iceI_getDeviceTypeAsync(context, true).waitForResponse();
    }

    default java.util.concurrent.CompletableFuture<deviceType> getDeviceTypeAsync()
    {
        return _iceI_getDeviceTypeAsync(com.zeroc.Ice.ObjectPrx.noExplicitContext, false);
    }

    default java.util.concurrent.CompletableFuture<deviceType> getDeviceTypeAsync(java.util.Map<String, String> context)
    {
        return _iceI_getDeviceTypeAsync(context, false);
    }

    /**
     * @hidden
     * @param context -
     * @param sync -
     * @return -
     **/
    default com.zeroc.IceInternal.OutgoingAsync<deviceType> _iceI_getDeviceTypeAsync(java.util.Map<String, String> context, boolean sync)
    {
        com.zeroc.IceInternal.OutgoingAsync<deviceType> f = new com.zeroc.IceInternal.OutgoingAsync<>(this, "getDeviceType", null, sync, null);
        f.invoke(true, context, null, null, istr -> {
                     deviceType ret;
                     ret = deviceType.ice_read(istr);
                     return ret;
                 });
        return f;
    }

    /**
     * Contacts the remote server to verify that the object implements this type.
     * Raises a local exception if a communication error occurs.
     * @param obj The untyped proxy.
     * @return A proxy for this type, or null if the object does not support this type.
     **/
    static DevicePrx checkedCast(com.zeroc.Ice.ObjectPrx obj)
    {
        return com.zeroc.Ice.ObjectPrx._checkedCast(obj, ice_staticId(), DevicePrx.class, _DevicePrxI.class);
    }

    /**
     * Contacts the remote server to verify that the object implements this type.
     * Raises a local exception if a communication error occurs.
     * @param obj The untyped proxy.
     * @param context The Context map to send with the invocation.
     * @return A proxy for this type, or null if the object does not support this type.
     **/
    static DevicePrx checkedCast(com.zeroc.Ice.ObjectPrx obj, java.util.Map<String, String> context)
    {
        return com.zeroc.Ice.ObjectPrx._checkedCast(obj, context, ice_staticId(), DevicePrx.class, _DevicePrxI.class);
    }

    /**
     * Contacts the remote server to verify that a facet of the object implements this type.
     * Raises a local exception if a communication error occurs.
     * @param obj The untyped proxy.
     * @param facet The name of the desired facet.
     * @return A proxy for this type, or null if the object does not support this type.
     **/
    static DevicePrx checkedCast(com.zeroc.Ice.ObjectPrx obj, String facet)
    {
        return com.zeroc.Ice.ObjectPrx._checkedCast(obj, facet, ice_staticId(), DevicePrx.class, _DevicePrxI.class);
    }

    /**
     * Contacts the remote server to verify that a facet of the object implements this type.
     * Raises a local exception if a communication error occurs.
     * @param obj The untyped proxy.
     * @param facet The name of the desired facet.
     * @param context The Context map to send with the invocation.
     * @return A proxy for this type, or null if the object does not support this type.
     **/
    static DevicePrx checkedCast(com.zeroc.Ice.ObjectPrx obj, String facet, java.util.Map<String, String> context)
    {
        return com.zeroc.Ice.ObjectPrx._checkedCast(obj, facet, context, ice_staticId(), DevicePrx.class, _DevicePrxI.class);
    }

    /**
     * Downcasts the given proxy to this type without contacting the remote server.
     * @param obj The untyped proxy.
     * @return A proxy for this type.
     **/
    static DevicePrx uncheckedCast(com.zeroc.Ice.ObjectPrx obj)
    {
        return com.zeroc.Ice.ObjectPrx._uncheckedCast(obj, DevicePrx.class, _DevicePrxI.class);
    }

    /**
     * Downcasts the given proxy to this type without contacting the remote server.
     * @param obj The untyped proxy.
     * @param facet The name of the desired facet.
     * @return A proxy for this type.
     **/
    static DevicePrx uncheckedCast(com.zeroc.Ice.ObjectPrx obj, String facet)
    {
        return com.zeroc.Ice.ObjectPrx._uncheckedCast(obj, facet, DevicePrx.class, _DevicePrxI.class);
    }

    /**
     * Returns a proxy that is identical to this proxy, except for the per-proxy context.
     * @param newContext The context for the new proxy.
     * @return A proxy with the specified per-proxy context.
     **/
    @Override
    default DevicePrx ice_context(java.util.Map<String, String> newContext)
    {
        return (DevicePrx)_ice_context(newContext);
    }

    /**
     * Returns a proxy that is identical to this proxy, except for the adapter ID.
     * @param newAdapterId The adapter ID for the new proxy.
     * @return A proxy with the specified adapter ID.
     **/
    @Override
    default DevicePrx ice_adapterId(String newAdapterId)
    {
        return (DevicePrx)_ice_adapterId(newAdapterId);
    }

    /**
     * Returns a proxy that is identical to this proxy, except for the endpoints.
     * @param newEndpoints The endpoints for the new proxy.
     * @return A proxy with the specified endpoints.
     **/
    @Override
    default DevicePrx ice_endpoints(com.zeroc.Ice.Endpoint[] newEndpoints)
    {
        return (DevicePrx)_ice_endpoints(newEndpoints);
    }

    /**
     * Returns a proxy that is identical to this proxy, except for the locator cache timeout.
     * @param newTimeout The new locator cache timeout (in seconds).
     * @return A proxy with the specified locator cache timeout.
     **/
    @Override
    default DevicePrx ice_locatorCacheTimeout(int newTimeout)
    {
        return (DevicePrx)_ice_locatorCacheTimeout(newTimeout);
    }

    /**
     * Returns a proxy that is identical to this proxy, except for the invocation timeout.
     * @param newTimeout The new invocation timeout (in seconds).
     * @return A proxy with the specified invocation timeout.
     **/
    @Override
    default DevicePrx ice_invocationTimeout(int newTimeout)
    {
        return (DevicePrx)_ice_invocationTimeout(newTimeout);
    }

    /**
     * Returns a proxy that is identical to this proxy, except for connection caching.
     * @param newCache <code>true</code> if the new proxy should cache connections; <code>false</code> otherwise.
     * @return A proxy with the specified caching policy.
     **/
    @Override
    default DevicePrx ice_connectionCached(boolean newCache)
    {
        return (DevicePrx)_ice_connectionCached(newCache);
    }

    /**
     * Returns a proxy that is identical to this proxy, except for the endpoint selection policy.
     * @param newType The new endpoint selection policy.
     * @return A proxy with the specified endpoint selection policy.
     **/
    @Override
    default DevicePrx ice_endpointSelection(com.zeroc.Ice.EndpointSelectionType newType)
    {
        return (DevicePrx)_ice_endpointSelection(newType);
    }

    /**
     * Returns a proxy that is identical to this proxy, except for how it selects endpoints.
     * @param b If <code>b</code> is <code>true</code>, only endpoints that use a secure transport are
     * used by the new proxy. If <code>b</code> is false, the returned proxy uses both secure and
     * insecure endpoints.
     * @return A proxy with the specified selection policy.
     **/
    @Override
    default DevicePrx ice_secure(boolean b)
    {
        return (DevicePrx)_ice_secure(b);
    }

    /**
     * Returns a proxy that is identical to this proxy, except for the encoding used to marshal parameters.
     * @param e The encoding version to use to marshal request parameters.
     * @return A proxy with the specified encoding version.
     **/
    @Override
    default DevicePrx ice_encodingVersion(com.zeroc.Ice.EncodingVersion e)
    {
        return (DevicePrx)_ice_encodingVersion(e);
    }

    /**
     * Returns a proxy that is identical to this proxy, except for its endpoint selection policy.
     * @param b If <code>b</code> is <code>true</code>, the new proxy will use secure endpoints for invocations
     * and only use insecure endpoints if an invocation cannot be made via secure endpoints. If <code>b</code> is
     * <code>false</code>, the proxy prefers insecure endpoints to secure ones.
     * @return A proxy with the specified selection policy.
     **/
    @Override
    default DevicePrx ice_preferSecure(boolean b)
    {
        return (DevicePrx)_ice_preferSecure(b);
    }

    /**
     * Returns a proxy that is identical to this proxy, except for the router.
     * @param router The router for the new proxy.
     * @return A proxy with the specified router.
     **/
    @Override
    default DevicePrx ice_router(com.zeroc.Ice.RouterPrx router)
    {
        return (DevicePrx)_ice_router(router);
    }

    /**
     * Returns a proxy that is identical to this proxy, except for the locator.
     * @param locator The locator for the new proxy.
     * @return A proxy with the specified locator.
     **/
    @Override
    default DevicePrx ice_locator(com.zeroc.Ice.LocatorPrx locator)
    {
        return (DevicePrx)_ice_locator(locator);
    }

    /**
     * Returns a proxy that is identical to this proxy, except for collocation optimization.
     * @param b <code>true</code> if the new proxy enables collocation optimization; <code>false</code> otherwise.
     * @return A proxy with the specified collocation optimization.
     **/
    @Override
    default DevicePrx ice_collocationOptimized(boolean b)
    {
        return (DevicePrx)_ice_collocationOptimized(b);
    }

    /**
     * Returns a proxy that is identical to this proxy, but uses twoway invocations.
     * @return A proxy that uses twoway invocations.
     **/
    @Override
    default DevicePrx ice_twoway()
    {
        return (DevicePrx)_ice_twoway();
    }

    /**
     * Returns a proxy that is identical to this proxy, but uses oneway invocations.
     * @return A proxy that uses oneway invocations.
     **/
    @Override
    default DevicePrx ice_oneway()
    {
        return (DevicePrx)_ice_oneway();
    }

    /**
     * Returns a proxy that is identical to this proxy, but uses batch oneway invocations.
     * @return A proxy that uses batch oneway invocations.
     **/
    @Override
    default DevicePrx ice_batchOneway()
    {
        return (DevicePrx)_ice_batchOneway();
    }

    /**
     * Returns a proxy that is identical to this proxy, but uses datagram invocations.
     * @return A proxy that uses datagram invocations.
     **/
    @Override
    default DevicePrx ice_datagram()
    {
        return (DevicePrx)_ice_datagram();
    }

    /**
     * Returns a proxy that is identical to this proxy, but uses batch datagram invocations.
     * @return A proxy that uses batch datagram invocations.
     **/
    @Override
    default DevicePrx ice_batchDatagram()
    {
        return (DevicePrx)_ice_batchDatagram();
    }

    /**
     * Returns a proxy that is identical to this proxy, except for compression.
     * @param co <code>true</code> enables compression for the new proxy; <code>false</code> disables compression.
     * @return A proxy with the specified compression setting.
     **/
    @Override
    default DevicePrx ice_compress(boolean co)
    {
        return (DevicePrx)_ice_compress(co);
    }

    /**
     * Returns a proxy that is identical to this proxy, except for its connection timeout setting.
     * @param t The connection timeout for the proxy in milliseconds.
     * @return A proxy with the specified timeout.
     **/
    @Override
    default DevicePrx ice_timeout(int t)
    {
        return (DevicePrx)_ice_timeout(t);
    }

    /**
     * Returns a proxy that is identical to this proxy, except for its connection ID.
     * @param connectionId The connection ID for the new proxy. An empty string removes the connection ID.
     * @return A proxy with the specified connection ID.
     **/
    @Override
    default DevicePrx ice_connectionId(String connectionId)
    {
        return (DevicePrx)_ice_connectionId(connectionId);
    }

    /**
     * Returns a proxy that is identical to this proxy, except it's a fixed proxy bound
     * the given connection.@param connection The fixed proxy connection.
     * @return A fixed proxy bound to the given connection.
     **/
    @Override
    default DevicePrx ice_fixed(com.zeroc.Ice.Connection connection)
    {
        return (DevicePrx)_ice_fixed(connection);
    }

    static String ice_staticId()
    {
        return "::SmartHome::Device";
    }
}
