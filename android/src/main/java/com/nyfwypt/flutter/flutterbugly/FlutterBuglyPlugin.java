package com.nyfwypt.flutter.flutterbugly;

import com.tencent.bugly.Bugly;
import com.tencent.bugly.BuglyStrategy;
import com.tencent.bugly.beta.Beta;
import com.tencent.bugly.beta.UpgradeInfo;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import io.flutter.plugin.common.MethodCall;
import io.flutter.plugin.common.MethodChannel;
import io.flutter.plugin.common.MethodChannel.MethodCallHandler;
import io.flutter.plugin.common.MethodChannel.Result;
import io.flutter.plugin.common.PluginRegistry.Registrar;

/**
 * FlutterBuglyPlugin
 */
public class FlutterBuglyPlugin implements MethodCallHandler {
    private Registrar registrar;

    public static void registerWith(Registrar registrar) {
        final MethodChannel channel = new MethodChannel(registrar.messenger(), "flutter_bugly");
        channel.setMethodCallHandler(new FlutterBuglyPlugin(registrar));
    }

    FlutterBuglyPlugin(Registrar registrar) {
        this.registrar = registrar;
    }

    @Override
    public void onMethodCall(MethodCall call, Result result) {

        switch (call.method) {
            case "init":
                init(call);
                result.success(null);
                break;
            case "checkUpgrade":
                checkUpgrade();
                result.success(null);
                break;
            case "upgradeInfo":
                Map<String, Object> map = loadUpgradeInfo();
                result.success(map);
                break;
            default:
                result.notImplemented();
        }

    }

    private void checkUpgrade() {
        Beta.checkUpgrade();
    }

    private Map<String, Object> loadUpgradeInfo() {
        UpgradeInfo upgradeInfo = Beta.getUpgradeInfo();
        if (upgradeInfo == null) {
            return Collections.emptyMap();
        }

        Map<String, Object> result = new HashMap<>();
        result.put("id", upgradeInfo.id);
        result.put("title", upgradeInfo.title);
        result.put("newFeature", upgradeInfo.newFeature);
        result.put("versionCode", upgradeInfo.versionCode);
        result.put("versionName", upgradeInfo.versionName);
        result.put("publishTime", upgradeInfo.publishTime);
        result.put("apkMd5", upgradeInfo.apkMd5);
        result.put("apkUrl", upgradeInfo.apkUrl);
        result.put("fileSize", upgradeInfo.fileSize);
        result.put("popInterval", upgradeInfo.popInterval);
        result.put("popTimes", upgradeInfo.popTimes);
        result.put("publishType", upgradeInfo.publishType);
        result.put("upgradeType", upgradeInfo.upgradeType);
        return result;
    }

    private void init(MethodCall call) {
        Beta.autoInit = call.argument("autoInit");
        Beta.autoCheckUpgrade = call.argument("autoCheckUpgrade");
        Integer upgradeCheckPeriod = call.argument("upgradeCheckPeriod");
        Beta.upgradeCheckPeriod = upgradeCheckPeriod.longValue();
        Integer initDelay = call.argument("initDelay");
        Beta.initDelay = initDelay.longValue();
        Beta.enableNotification = call.argument("enableNotification");
        Beta.autoDownloadOnWifi = call.argument("autoDownloadOnWifi");
        Beta.canShowApkInfo = call.argument("canShowApkInfo");
        Beta.enableHotfix = call.argument("enableHotfix");
        String appId = call.argument("appId");
        boolean isDebug = call.argument("isDebug");
        Bugly.init(registrar.context(), appId, isDebug);
    }
}
