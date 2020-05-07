// GENERATED CODE - DO NOT MODIFY BY HAND

// **************************************************************************
// AutoRouteGenerator
// **************************************************************************

import 'package:flutter/material.dart';
import 'package:flutter/cupertino.dart';
import 'package:auto_route/auto_route.dart';
import 'package:webrtc_app_2/presentation/pages/splash/splash_page.dart';
import 'package:webrtc_app_2/presentation/pages/sign_in/sign_in_page.dart';

class Router {
  static const splashPage = '/';
  static const signInPage = '/sign-in-page';
  static final navigator = ExtendedNavigator();
  static Route<dynamic> onGenerateRoute(RouteSettings settings) {
    final args = settings.arguments;
    switch (settings.name) {
      case Router.splashPage:
        return MaterialPageRoute<dynamic>(
          builder: (_) => SplashPage(),
          settings: settings,
        );
      case Router.signInPage:
        return MaterialPageRoute<dynamic>(
          builder: (_) => SignInPage(),
          settings: settings,
        );
      default:
        return unknownRoutePage(settings.name);
    }
  }
}
