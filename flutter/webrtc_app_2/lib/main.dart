import 'package:flutter/material.dart';
import 'package:injectable/injectable.dart';
import 'package:webrtc_app_2/injection.dart';
import 'package:webrtc_app_2/presentation/app_widget.dart';

void main() {
  WidgetsFlutterBinding.ensureInitialized();
  configureInjection(Environment.prod);
  runApp(AppWidget());
}
