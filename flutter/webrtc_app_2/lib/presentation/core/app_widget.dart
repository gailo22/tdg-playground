import 'package:flutter/material.dart';
import 'package:webrtc_app_2/presentation/pages/signin/signin_page.dart';

class AppWidget extends StatelessWidget {
  @override
  Widget build(BuildContext context) {
    return MaterialApp(
      title: 'Material App',
      home: SignInPage(),
    );
  }
}
