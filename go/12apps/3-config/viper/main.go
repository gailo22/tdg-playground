package main

import (
    "fmt"

    "github.com/spf13/viper"
)

func readConfig(filename string, defaults map[string]interface{}) (*viper.Viper, error) {
    v := viper.New()
    for key, value := range defaults {
        v.SetDefault(key, value)
    }
    v.SetConfigName(filename)
    v.AddConfigPath(".")
    v.AutomaticEnv()
    err := v.ReadInConfig()
    return v, err
}

func main()  {
    v1, err := readConfig(".env", map[string]interface{}{
        "port": 9090,
        "hostname": "localhost",
        "auth": map[string]string{
            "username": "user",
            "password": "pass",
        },
    })

    if err != nil {
		panic(fmt.Errorf("Error when reading config: %v\n", err))
	}

	port := v1.GetInt("port")
	hostname := v1.GetString("hostname")
	auth := v1.GetStringMapString("auth")

	fmt.Printf("port = %d\n", port)
	fmt.Printf("hostname = %s\n", hostname)
	fmt.Printf("auth = %#v\n", auth)
}