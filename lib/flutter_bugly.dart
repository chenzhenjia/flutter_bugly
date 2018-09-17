import 'dart:async';

import 'package:flutter/services.dart';

class Options {
  final String appId;
  final bool isDebug;
  bool autoCheckUpgrade,
      autoInit,
      enableNotification,
      autoDownloadOnWifi,
      canShowApkInfo,
      enableHotfix;

  int upgradeCheckPeriod, initDelay;

  Options.debug(this.appId,
      {this.autoInit = true,
      this.autoCheckUpgrade = true,
      this.enableNotification = true,
      this.autoDownloadOnWifi = true,
      this.canShowApkInfo = true,
      this.enableHotfix = true,
      this.upgradeCheckPeriod = 60 * 1000,
      this.initDelay = 3 * 1000})
      : this.isDebug = true;

  Options.release(this.appId,
      {this.autoCheckUpgrade = false,
      this.autoInit = false,
      this.enableNotification = false,
      this.autoDownloadOnWifi = false,
      this.canShowApkInfo = false,
      this.enableHotfix = false,
      this.upgradeCheckPeriod = 60 * 1000,
      this.initDelay = 3 * 1000})
      : this.isDebug = false;

  Map<String, dynamic> toMap() {
    return {
      "appId": appId,
      "isDebug": isDebug,
      "autoInit": autoInit,
      "autoCheckUpgrade": autoCheckUpgrade,
      "enableNotification": enableNotification,
      "autoDownloadOnWifi": autoDownloadOnWifi,
      "canShowApkInfo": canShowApkInfo,
      "enableHotfix": enableHotfix,
      "upgradeCheckPeriod": upgradeCheckPeriod,
      "initDelay": initDelay,
    };
  }
}

class FlutterBugly {
  static const MethodChannel _channel = const MethodChannel('flutter_bugly');

  static Future<Map> get upgradeInfo async {
    Map info = await _channel.invokeMethod('upgradeInfo');
    return info;
  }

  static Future<void> checkUpgrade() async {
    await _channel.invokeMethod('checkUpgrade');
  }

  static Future<void> init(Options options) async {
    await _channel.invokeMethod('init', options.toMap());
  }
}
