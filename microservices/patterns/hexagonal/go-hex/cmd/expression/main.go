package main

import (
	"fmt"
	"go-hex/infrastructure/logs"
	"go-hex/internal/core/domain"
	"go-hex/internal/core/service/exprsrv"
	"go-hex/internal/handlers"
	"go-hex/internal/repositories"
	"net/http"
	"strings"
	"time"

	"github.com/gin-gonic/gin"
	_ "github.com/go-sql-driver/mysql"
	"github.com/jmoiron/sqlx"
	"github.com/spf13/viper"
)

const httpAddr = ":3000"

func main() {
	//initTimezone()
	initConfig()
	db := initDb()

	repoService := repositories.NewExprRepo(db)
	exprService := exprsrv.NewExprService(domain.Expr{}, repoService)
	httpHandler := handlers.NewHttpHandler(exprService)

	logs.Info("Server running on" + httpAddr)
	srv := gin.New()
	srv.GET("/health", healthHandler)
	srv.POST("/addition", httpHandler.Addition)

	logs.Error(srv.Run(httpAddr))

}

func healthHandler(ctx *gin.Context) {
	ctx.String(http.StatusOK, "everyting is ok!")
}

func initTimezone() {
	ict, err := time.LoadLocation("Asia/Bangkok")
	if err != nil {
		panic(err)
	}

	time.Local = ict
}

func initConfig() {
	viper.SetConfigName("config")
	viper.SetConfigType("yml")
	viper.AddConfigPath(".")
	viper.AutomaticEnv()
	viper.EnvKeyReplacer(strings.NewReplacer(".", "_"))

	err := viper.ReadInConfig()
	if err != nil {
		panic(err)
	}
}

func initDb() *sqlx.DB {
	dsn := fmt.Sprintf("%v:%v@tcp(%v:%v)/%v?parseTime=true",
		viper.GetString("db.username"),
		viper.GetString("db.password"),
		viper.GetString("db.host"),
		viper.GetInt("db.port"),
		viper.GetString("db.database"),
	)

	db, err := sqlx.Open(viper.GetString("db.driver"), dsn)
	if err != nil {
		panic(err)
	}

	db.SetConnMaxLifetime(3 * time.Minute)
	db.SetMaxOpenConns(10)
	db.SetMaxIdleConns(10)

	return db
}
