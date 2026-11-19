package main

import (
  "github.com/gin-gonic/gin"
  "github.com/gin-contrib/cors"
  "net/http"
  "io"
  "fmt"
  "encoding/json"
)

type PathBindings struct {
  Email string `uri:"email"`
  Phone string `uri:"phone"`
  URL string `uri:"url"`
  IP string `uri:"ip"`
}

type APIKey struct {
  Key string `json:"key"`
}

const apiBase string = "https://www.ipqualityscore.com/api/json"
var apiKey string

func executeRequest(url string) (response []byte, err error) {
  resp, err := http.Get(url)
  if err != nil {
    return nil, err 
  }
  
  defer resp.Body.Close()
  
  body, err := io.ReadAll(resp.Body)
  if err != nil {
    return nil, err
  }
  
  return body, nil
}

func main() {
  router := gin.Default()
  
  config := cors.DefaultConfig()
  config.AllowAllOrigins = true
  
  router.Use(cors.New(config))
  
  router.GET("/ipqs/api/key", func(c *gin.Context) {
    c.JSON(200, gin.H{
      "key": apiKey,
    })
  })
  
  router.PUT("/ipqs/api/key", func(c *gin.Context) {
    jsonData, err := io.ReadAll(c.Request.Body)
    if err != nil {
      c.JSON(500, gin.H{
        "error": err,
      })
      return
    }
    
    key := APIKey{}
    err = json.Unmarshal(jsonData, &key)
    if err != nil {
      c.JSON(500, gin.H{
        "error": err,
      })
      return
    }
    
    apiKey = key.Key
    
    c.JSON(200, gin.H{
      "key": apiKey,
    })
  })
  
  router.DELETE("/ipqs/api/key", func(c *gin.Context) {
    apiKey = ""
    c.JSON(200, gin.H{
      "key": apiKey,
    })
  })
  
  router.GET("/ipqs/score/email/:email", func(c *gin.Context) {
    var pathBindings PathBindings
    if err := c.ShouldBindUri(&pathBindings); err != nil {
      c.JSON(400, gin.H{
        "error": err,
      })
      return
    }
    response, err := executeRequest(fmt.Sprintf("%s/email/%s/%s", apiBase, apiKey, pathBindings.Email))
    if err != nil {
      c.JSON(500, gin.H{
        "error": err,
      })
      return
    }
    c.JSON(200, json.RawMessage(response))
  })
  
  router.GET("/ipqs/score/phone/:phone", func(c *gin.Context) {
    var pathBindings PathBindings
    if err := c.ShouldBindUri(&pathBindings); err != nil {
      c.JSON(400, gin.H{
        "error": err,
      })
      return
    }
    response, err := executeRequest(fmt.Sprintf("%s/phone/%s/%s", apiBase, apiKey, pathBindings.Phone))
    if err != nil {
      c.JSON(500, gin.H{
        "error": err,
      })
      return
    }
    c.JSON(200, json.RawMessage(response))
  })
  
  router.GET("/ipqs/score/url/:url", func(c *gin.Context) {
    var pathBindings PathBindings
    if err := c.ShouldBindUri(&pathBindings); err != nil {
      c.JSON(400, gin.H{
        "error": err,
      })
      return
    }
    response, err := executeRequest(fmt.Sprintf("%s/url/%s/%s", apiBase, apiKey, pathBindings.URL))
    if err != nil {
      c.JSON(500, gin.H{
        "error": err,
      })
      return
    }
    c.JSON(200, json.RawMessage(response))
  })
  
  router.GET("/ipqs/score/ip/:ip", func(c *gin.Context) {
    var pathBindings PathBindings
    if err := c.ShouldBindUri(&pathBindings); err != nil {
      c.JSON(400, gin.H{
        "error": err,
      })
      return
    }
    response, err := executeRequest(fmt.Sprintf("%s/ip/%s/%s", apiBase, apiKey, pathBindings.IP))
    if err != nil {
      c.JSON(500, gin.H{
        "error": err,
      })
      return
    }
    c.JSON(200, json.RawMessage(response))
  })
  
  router.Run(":8081")
}
