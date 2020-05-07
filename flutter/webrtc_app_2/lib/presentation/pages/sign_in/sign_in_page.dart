import 'package:flutter/material.dart';
import 'package:flutter_bloc/flutter_bloc.dart';
import 'package:webrtc_app_2/application/auth/sign_in_form/sign_in_form_bloc.dart';
import 'package:webrtc_app_2/injection.dart';
import 'package:webrtc_app_2/presentation/pages/sign_in/widgets/sign_in_form.dart';

class SignInPage extends StatelessWidget {
  @override
  Widget build(BuildContext context) {
    return Scaffold(
      appBar: AppBar(
        title: const Text('Sign In'),
      ),
      body: BlocProvider(
        create: (context) => getIt<SignInFormBloc>(),
        child: const SignInForm(),
      ),
    );
  }
}
