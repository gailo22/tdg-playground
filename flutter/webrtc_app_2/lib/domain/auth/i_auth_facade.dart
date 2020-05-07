import 'package:dartz/dartz.dart';
import 'package:meta/meta.dart';
import 'package:webrtc_app_2/domain/auth/auth_failure.dart';
import 'package:webrtc_app_2/domain/auth/user.dart';
import 'package:webrtc_app_2/domain/auth/value_objects.dart';

abstract class IAuthFacade {
  Future<Option<User>> getSignedInUser();
  Future<Either<AuthFailure, Unit>> registerWithEmailAndPassword({
    @required EmailAddress emailAddress,
    @required Password password,
  });
  Future<Either<AuthFailure, Unit>> signInWithEmailAndPassword({
    @required EmailAddress emailAddress,
    @required Password password,
  });
  Future<Either<AuthFailure, Unit>> signInWithGoogle();
  Future<void> signOut();
}
