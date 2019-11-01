package main

import (
	"context"
	"errors"
	"fmt"
	"time"

	"github.com/avast/retry-go"
	"go.mongodb.org/mongo-driver/mongo"
	"go.mongodb.org/mongo-driver/mongo/options"
	"gopkg.in/mgo.v2/bson"
)

type RunningSequence struct {
	SequenceValue int32 `bson:"sequence_value"`
}

const (
	TimeoutInSecs             time.Duration = 120 * time.Second
	MaxRetry                  uint          = 5
	ReceiptRunningCollection  string        = "receipt_running"
	PurchaseCollection        string        = "purchase"
	WriteConflictErrorMessage string        = "WriteConflictError"
)

func genRunningSequenceAndUpdate(db *mongo.Database, sequenceName string, purchaseId int) (RunningSequence, error) {
	ctx, cancel := context.WithTimeout(context.Background(), TimeoutInSecs)
	defer cancel()

	receiptRunning := db.Collection(ReceiptRunningCollection)
	purchase := db.Collection(PurchaseCollection)

	var newRunning RunningSequence

	// transaction
	err := db.Client().UseSession(ctx, func(sessionContext mongo.SessionContext) error {
		err := sessionContext.StartTransaction()
		if err != nil {
			return err
		}

		upsert := true
		after := options.After
		opt := options.FindOneAndUpdateOptions{
			ReturnDocument: &after,
			Upsert:         &upsert,
		}

		err = receiptRunning.FindOneAndUpdate(sessionContext,
			bson.M{"_id": sequenceName},
			bson.M{"$inc": bson.M{"sequence_value": 1}},
			&opt).Decode(&newRunning)
		if err != nil {
			return errors.New(WriteConflictErrorMessage)
		}

		fmt.Println(newRunning)
		//time.Sleep(1 * time.Minute)

		updated, err := purchase.UpdateOne(sessionContext,
			bson.M{"purchase_id": purchaseId},
			bson.M{"$set": bson.M{"receipt_id": newRunning.SequenceValue}})
		fmt.Println(updated)
		if updated.ModifiedCount == 0 || err != nil {
			sessionContext.AbortTransaction(sessionContext)
			return err
		} else {
			sessionContext.CommitTransaction(sessionContext)
		}
		return nil
	})

	return newRunning, err

}

func genRunningSequenceAndUpdateWithRetry(db *mongo.Database, sequenceName string, purchaseId int) (RunningSequence, error) {
	var (
		doc RunningSequence
		err error
	)

	retry.Do(
		func() error {
			doc, err = genRunningSequenceAndUpdate(db, sequenceName, purchaseId)
			return err
		},
		retry.RetryIf(func(err error) bool {
			return err.Error() == WriteConflictErrorMessage
		}),
		retry.Attempts(MaxRetry),
	)

	return doc, err
}

func main() {
	ctx := context.Background()
	client, err := mongo.Connect(ctx, options.Client().ApplyURI("mongodb+srv://admin:securePassword@cluster0-pp4fv.gcp.mongodb.net/test?retryWrites=true&w=majority"))
	// client, err := mongo.NewClient(options.Client().ApplyURI("mongodb://localhost:27017"))
	if err != nil {
		panic(err)
	}

	db := client.Database("testdb")
	defer db.Client().Disconnect(ctx)

	sequenceName := "201910"
	purchaseId := 1

	doc, err := genRunningSequenceAndUpdateWithRetry(db, sequenceName, purchaseId)
	if err != nil {
		fmt.Printf("Error: %s", err.Error())
	}
	fmt.Printf("%+v", doc)

}
