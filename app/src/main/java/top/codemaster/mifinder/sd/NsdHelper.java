package top.codemaster.mifinder.sd;

import android.content.Context;
import android.net.nsd.NsdManager;
import android.net.nsd.NsdServiceInfo;
import android.util.Log;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import io.reactivex.BackpressureStrategy;
import io.reactivex.Flowable;
import top.codemaster.mifinder.data.ServiceHost;

public class NsdHelper {

    private static final String TAG = "NsdHelper";

    private NsdManager nsdManager;

    private boolean started;

    private Set<ServiceHost> serviceHosts = new HashSet<>();

    private NsdManager.DiscoveryListener discoveryListener;

    private NsdListener nsdListener;

    public static Flowable<List<ServiceHost>> discover(Context context) {
        return Flowable.create(emitter -> {
            NsdHelper helper = new NsdHelper(context);
            emitter.setCancellable(helper::stopDiscover);
            helper.startDiscover(new NsdListener() {
                @Override
                public void onService(List<ServiceHost> serviceHosts) {
                    emitter.onNext(serviceHosts);
                }

                @Override
                public void onError(Exception error) {
                    if (!emitter.isCancelled()) {
                        emitter.onError(error);

                        // 停止时发生错误, 不要调用，防止递归死循环
                        if (!(error instanceof NsdException
                                && ((NsdException) error).code == 0)) {
                            helper.stopDiscover();
                        }
                    }
                }
            });
        }, BackpressureStrategy.LATEST);
    }

    public NsdHelper(Context context) {
        this.nsdManager = (NsdManager) context.getSystemService(Context.NSD_SERVICE);
    }

    public void startDiscover(NsdListener listener) {
        if (started) {
            throw new IllegalStateException("NsdHelp instance must only start once");
        }
        started = true;
        this.nsdListener = listener;
        discoveryListener = new DiscoverListener();
        nsdManager.discoverServices("_http._tcp.", NsdManager.PROTOCOL_DNS_SD, discoveryListener);
    }

    public void stopDiscover() {
        nsdManager.stopServiceDiscovery(discoveryListener);
        discoveryListener = null;
    }

    private void addService(ServiceHost serviceHost) {
        serviceHosts.add(serviceHost);
        nsdListener.onService(new ArrayList<>(serviceHosts));
    }

    private void removeService(ServiceHost serviceHost) {
        if (serviceHosts.remove(serviceHost)) {
            nsdListener.onService(new ArrayList<>(serviceHosts));
        }
    }

    private ServiceHost toService(NsdServiceInfo info) {
        return new ServiceHost(info.getServiceName(), info.getHost(), info.getPort());
    }

    private class DiscoverListener implements NsdManager.DiscoveryListener {

        // Called as soon as service discovery begins.
        @Override
        public void onDiscoveryStarted(String regType) {
            Log.d(TAG, "Service discovery started");
        }

        @Override
        public void onServiceFound(NsdServiceInfo service) {
            // A service was found! Do something with it.
            Log.d(TAG, "Service discovery success" + service);
            if (service.getServiceName().contains("mifinder")) {
                Log.d(TAG, "Service resolve started");
                nsdManager.resolveService(service, new ResolveListener());
            }
        }

        @Override
        public void onServiceLost(NsdServiceInfo service) {
            // When the network service is no longer available.
            // Internal bookkeeping code goes here.
            Log.e(TAG, "service lost: " + service);
            removeService(toService(service));
        }

        @Override
        public void onDiscoveryStopped(String serviceType) {
            Log.i(TAG, "Discovery stopped: " + serviceType);
        }

        @Override
        public void onStartDiscoveryFailed(String serviceType, int errorCode) {
            Log.e(TAG, "Discovery failed: Error code:" + errorCode);
            nsdListener.onError(new NsdException("Discovery start failed", errorCode, 1));
        }

        @Override
        public void onStopDiscoveryFailed(String serviceType, int errorCode) {
            Log.e(TAG, "Discovery failed: Error code:" + errorCode);
            nsdListener.onError(new NsdException("Discovery stop failed", errorCode, 0));
        }
    }


    private class ResolveListener implements NsdManager.ResolveListener {

        @Override
        public void onResolveFailed(NsdServiceInfo serviceInfo, int errorCode) {
            // Called when the resolve fails. Use the error code to debug.
            Log.e(TAG, "Resolve failed: " + errorCode);
        }

        @Override
        public void onServiceResolved(NsdServiceInfo serviceInfo) {
            Log.i(TAG, "Resolve Succeeded. " + serviceInfo);
            addService(toService(serviceInfo));
        }

    }

    public interface NsdListener {

        void onService(List<ServiceHost> serviceHosts);

        void onError(Exception error);

    }

    public class NsdException extends Exception {
        public final int code;

        private final int nsdErrCode;

        public NsdException(String message, int nsdErrCode, int code) {
            super(message);
            this.nsdErrCode = nsdErrCode;
            this.code = code;
        }

    }
}
