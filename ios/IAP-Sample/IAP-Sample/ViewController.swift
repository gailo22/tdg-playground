//
//  ViewController.swift
//  IAP-Sample
//
//  Created by Montree Bungnasang on 30/8/2562 BE.
//  Copyright © 2562 Montree Bungnasang. All rights reserved.
//

import UIKit

import FirebaseCore
import FirebaseFirestore

class ViewController: UIViewController {
    
    var db: Firestore!
    
    override func viewDidLoad() {
        super.viewDidLoad()
        // Do any additional setup after loading the view.
        
        // [START setup]
        let settings = FirestoreSettings()
        
        Firestore.firestore().settings = settings
        // [END setup]
        db = Firestore.firestore()
        
    }
    @IBAction func addFirestoreDoc(_ sender: Any) {
        print("add firestore document")
        addAdaLovelace()
    }
    
    private func addAdaLovelace() {
        // Add a new document with a generated ID
        var ref: DocumentReference? = nil
        ref = db.collection("users").addDocument(data: [
            "first": "Ada",
            "last": "Lovelace",
            "born": 1815
        ]) { err in
            if let err = err {
                print("Error adding document: \(err)")
            } else {
                print("Document added with ID: \(ref!.documentID)")
            }
        }
    }


}

