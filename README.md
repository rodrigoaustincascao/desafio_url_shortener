# URL-SHORTENER

Desafio da Backend Brasil pode ser acessado no [Github](https://github.com/backend-br/desafios/blob/master/url-shortener/PROBLEM.md)

O desafio visa implementar um serviço que permite encurtar URLs longas para torná-las mais compactas e fáceis de compartilhar.

As URL encurtadas, caso sejam acessadas no navegador redirecionam para a URL original, possuem um prazo de expiração e após esse prazo são removidas do banco de dados. 

## Tecnologias utilizadas
- Spring Boot
- Docker Compose
- Mongo

## Endpoint
```bash
curl --request POST \
  --url http://localhost:8080/shorten-url \
  --header 'Content-Type: application/json' \
  --header 'User-Agent: insomnia/10.0.0' \
  --data '{
	"url": "https://google.com"
}'
```