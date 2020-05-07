import 'package:auto_route/auto_route_annotations.dart';
import 'package:webrtc_app_2/presentation/pages/sign_in/sign_in_page.dart';
import 'package:webrtc_app_2/presentation/pages/splash/splash_page.dart';

@MaterialAutoRouter()
class $Router {
  @initial
  SplashPage splashPage;
  SignInPage signInPage;
}
