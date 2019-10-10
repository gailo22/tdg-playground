package cmd

import (
  "fmt"

  "github.com/spf13/cobra"
)

func init() {
  cmd.AddCommand(versionCmd)
}

var versionCmd = &cobra.Command{
  Use:   "version",
  Short: "Print the version number of Helo",
  Long:  `All software has versions. This is Hello's`,
  Run: func(cmd *cobra.Command, args []string) {
    fmt.Println("Hello World v0.9 -- HEAD")
  },
}