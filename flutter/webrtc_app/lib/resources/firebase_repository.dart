import 'package:firebase_auth/firebase_auth.dart';
import 'package:webrtcapp/resources/firebase_methods.dart';

class FirebaseRepository {

  FirebaseMethods _firebaseMethods = FirebaseMethods();

  Future<FirebaseUser> getCurrentUser() => _firebaseMethods.getCurrentUser();

  Future<FirebaseUser> signIn() => _firebaseMethods.signIn();

  Future<bool> authenticateUser(FirebaseUser user) => _firebaseMethods.authenticateUser(user);

  Future<bool> addDataToDb(FirebaseUser user) => _firebaseMethods.addDataToDb(user);

}