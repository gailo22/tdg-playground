//
//  IAPService.swift
//  IAP-Sample
//
//  Created by Montree Bungnasang on 2/9/2562 BE.
//  Copyright © 2562 Montree Bungnasang. All rights reserved.
//

import Foundation
import StoreKit
import Firebase
import FirebaseFirestore

class IAPService: NSObject {
    private override init() {}
    static let shared = IAPService()
    
    var products = [SKProduct]()
    
    func getProducts() {
        let products: Set<String> = [IAPProduct.consumable.rawValue, IAPProduct.nonConsumable.rawValue,
                             IAPProduct.autoRenewSubscription.rawValue, IAPProduct.nonAutoRenewSubscription.rawValue]
        
        let request = SKProductsRequest(productIdentifiers: products)
        request.delegate = self
        request.start()
    }
    
    func purchase(product: IAPProduct) {
        guard let productToPurchase = products.filter({ $0.productIdentifier == product.rawValue }).first else { return }
        let payment = SKPayment(product: productToPurchase)
        SKPaymentQueue.default().add(payment)
    }
}

extension IAPService: SKProductsRequestDelegate {
    func productsRequest(_ request: SKProductsRequest, didReceive response: SKProductsResponse) {
        self.products = response.products
        for product in response.products {
            print(product.localizedTitle)
        }
    }
}

extension IAPService: SKPaymentTransactionObserver {
    func paymentQueue(_ queue: SKPaymentQueue, updatedTransactions transactions: [SKPaymentTransaction]) {
        for transaction in transactions {
            print(transaction.transactionState)
            switch transaction.transactionState {
            case .purchasing: break
            default: queue.finishTransaction(transaction)
            }
        }
    }
}

extension SKPaymentTransactionState {
    func status() -> String {
        switch self {
        case .deferred: return "deferred"
        case .purchasing: return "purchasing"
        case .purchased: return "purchased"
        case .failed: return "failed"
        default: return "default"
        }
    }
}


func queryAuthenTokenFirebase(callback: @escaping () -> ()) {
    let ref = FirebaseApp.realtimeDatabase.reference(withPath: "authen_token")
    ref.observeSingleEvent(of: .value, with: { (snapshot) in
        guard let value = snapshot.value as? [String: Any] else {
            // Failed to cast
            return
        }
        
        NotificationCenter.default.post(name: .AuthenTokenFirebaseDataProxyInvalidated, object: nil)
        callback()
    }, withCancel: nil)
}

func queryAuthenTokenFirestore(callback: @escaping () -> ()) {
    let ref = Firestore.firestore().collection("trueid-84d04").document("authen_token")
    ref.addSnapshotListener { snapshot, error in
        guard let document = snapshot else {
            print("Error fetching document: \(error!)")
            return
        }
        
//        NotificationCenter.default.post(name: .AuthenTokenFirebaseDataProxyInvalidated, object: nil)
        callback()
    }
}


extension FirebaseApp {
    static var realtimeDatabase: Database {
        var endPoints = ""
        return Database.database(url: endPoints)
    }
}
