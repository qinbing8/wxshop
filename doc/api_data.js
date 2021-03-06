define({ "api": [
  {
    "type": "post",
    "url": "/goods",
    "title": "创建商品",
    "name": "CreateGoods",
    "group": "商品",
    "header": {
      "fields": {
        "Header": [
          {
            "group": "Header",
            "type": "String",
            "optional": false,
            "field": "Accept",
            "description": "<p>application/json</p>"
          },
          {
            "group": "Header",
            "type": "String",
            "optional": false,
            "field": "Content-Type",
            "description": "<p>application/json</p>"
          }
        ]
      }
    },
    "parameter": {
      "examples": [
        {
          "title": "Request-Example:",
          "content": "{\n    \"name\": \"肥皂\",\n    \"description\": \"纯天然无污染肥皂\",\n    \"details\": \"这是一块好肥皂\",\n    \"imgUrl\": \"https://img.url\",\n    \"price\": 500,\n    \"stock\": 10,\n    \"shopId\": 12345\n}",
          "type": "json"
        }
      ]
    },
    "success": {
      "fields": {
        "Success 200": [
          {
            "group": "Success 200",
            "type": "Goods",
            "optional": false,
            "field": "data",
            "description": "<p>创建的商品</p>"
          }
        ]
      },
      "examples": [
        {
          "title": "Success-Response:",
          "content": "HTTP/1.1 201 Created\n{\n  \"data\": {\n         \"id\": 12345,\n         \"name\": \"肥皂\",\n         \"description\": \"纯天然无污染肥皂\",\n         \"details\": \"这是一块好肥皂\",\n         \"imgUrl\": \"https://img.url\",\n         \"price\": 500,\n         \"stock\": 10,\n         \"shopId\": 12345,\n         \"createdAt\": \"2020-03-22T13:22:03Z\",\n         \"updatedAt\": \"2020-03-22T13:22:03Z\"\n  }\n}",
          "type": "json"
        }
      ]
    },
    "error": {
      "fields": {
        "Error 4xx": [
          {
            "group": "Error 4xx",
            "optional": false,
            "field": "400",
            "description": "<p>Bad Request 若用户的请求中包含错误</p>"
          },
          {
            "group": "Error 4xx",
            "optional": false,
            "field": "401",
            "description": "<p>Unauthorized 若用户未登录</p>"
          },
          {
            "group": "Error 4xx",
            "optional": false,
            "field": "403",
            "description": "<p>Forbidden 若用户尝试创建非自己管理店铺的商品</p>"
          }
        ]
      },
      "examples": [
        {
          "title": "Error-Response:",
          "content": "HTTP/1.1 401 Unauthorized\n{\n  \"message\": \"Unauthorized\"\n}",
          "type": "json"
        }
      ]
    },
    "version": "0.0.0",
    "filename": "src/main/java/com/hcsp/wxshop/controller/GoodsController.java",
    "groupTitle": "商品"
  },
  {
    "type": "delete",
    "url": "/goods/:id",
    "title": "删除商品",
    "name": "DeleteGoods",
    "group": "商品",
    "header": {
      "fields": {
        "Header": [
          {
            "group": "Header",
            "type": "String",
            "optional": false,
            "field": "Accept",
            "description": "<p>application/json</p>"
          }
        ]
      }
    },
    "parameter": {
      "fields": {
        "Parameter": [
          {
            "group": "Parameter",
            "type": "Number",
            "optional": false,
            "field": "id",
            "description": "<p>商品ID</p>"
          }
        ]
      }
    },
    "success": {
      "fields": {
        "Success 200": [
          {
            "group": "Success 200",
            "type": "Goods",
            "optional": false,
            "field": "data",
            "description": "<p>被删除的商品</p>"
          }
        ]
      },
      "examples": [
        {
          "title": "Success-Response:",
          "content": "HTTP/1.1 204 No Content\n{\n  \"data\": {\n         \"id\": 12345,\n         \"name\": \"肥皂\",\n         \"description\": \"纯天然无污染肥皂\",\n         \"details\": \"这是一块好肥皂\",\n         \"imgUrl\": \"https://img.url\",\n         \"price\": 500,\n         \"stock\": 10,\n         \"createdAt\": \"2020-03-22T13:22:03Z\",\n         \"updatedAt\": \"2020-03-22T13:22:03Z\"\n  }\n}",
          "type": "json"
        }
      ]
    },
    "error": {
      "fields": {
        "Error 4xx": [
          {
            "group": "Error 4xx",
            "optional": false,
            "field": "400",
            "description": "<p>Bad Request 若用户的请求中包含错误</p>"
          },
          {
            "group": "Error 4xx",
            "optional": false,
            "field": "401",
            "description": "<p>Unauthorized 若用户未登录</p>"
          },
          {
            "group": "Error 4xx",
            "optional": false,
            "field": "403",
            "description": "<p>Forbidden 若用户尝试删除非自己管理店铺的商品</p>"
          },
          {
            "group": "Error 4xx",
            "optional": false,
            "field": "404",
            "description": "<p>Not Found 若商品未找到</p>"
          }
        ]
      },
      "examples": [
        {
          "title": "Error-Response:",
          "content": "HTTP/1.1 401 Unauthorized\n{\n  \"message\": \"Unauthorized\"\n}",
          "type": "json"
        }
      ]
    },
    "version": "0.0.0",
    "filename": "src/main/java/com/hcsp/wxshop/controller/GoodsController.java",
    "groupTitle": "商品"
  }
] });
