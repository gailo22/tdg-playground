package handlers

import (
	"go-hex/internal/core/ports"
	"strconv"

	"github.com/gin-gonic/gin"
)

type HttpHandler struct {
	exprService ports.ExprService
}

func NewHttpHandler(exprService ports.ExprService) *HttpHandler {
	return &HttpHandler{
		exprService: exprService,
	}
}

func (h *HttpHandler) Addition(c *gin.Context) {
	body := AdditionRequestDto{}
	c.BindJSON(&body)

	expr, err := h.exprService.GetAddition(body.A, body.B)
	if err != nil {
		c.AbortWithStatusJSON(500, gin.H{"message": err.Error()})
		return
	}

	response := AdditionResponseDto{
		Value: strconv.Itoa(int(expr)),
	}

	c.JSON(200, response)
}
