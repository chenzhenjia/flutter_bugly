#import "FlutterBuglyPlugin.h"
#import <flutter_bugly/flutter_bugly-Swift.h>

@implementation FlutterBuglyPlugin
+ (void)registerWithRegistrar:(NSObject<FlutterPluginRegistrar>*)registrar {
  [SwiftFlutterBuglyPlugin registerWithRegistrar:registrar];
}
@end
