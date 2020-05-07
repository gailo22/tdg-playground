// GENERATED CODE - DO NOT MODIFY BY HAND

// **************************************************************************
// InjectableConfigGenerator
// **************************************************************************

import 'package:webrtc_app_2/infrastructure/auth/firebase_user_mapper.dart';
import 'package:webrtc_app_2/infrastructure/auth/firebase_auth_facade.dart';
import 'package:webrtc_app_2/domain/auth/i_auth_facade.dart';
import 'package:firebase_auth/firebase_auth.dart';
import 'package:google_sign_in/google_sign_in.dart';
import 'package:webrtc_app_2/application/auth/sign_in_form/sign_in_form_bloc.dart';
import 'package:webrtc_app_2/application/auth/auth_bloc.dart';
import 'package:get_it/get_it.dart';

void $initGetIt(GetIt g, {String environment}) {
  g.registerLazySingleton<FirebaseUserMapper>(() => FirebaseUserMapper());
  g.registerFactory<SignInFormBloc>(() => SignInFormBloc(g<IAuthFacade>()));
  g.registerFactory<AuthBloc>(() => AuthBloc(g<IAuthFacade>()));

  //Register prod Dependencies --------
  if (environment == 'prod') {
    g.registerLazySingleton<IAuthFacade>(() => FirebaseAuthFacade(
          g<FirebaseAuth>(),
          g<GoogleSignIn>(),
          g<FirebaseUserMapper>(),
        ));
  }
}
