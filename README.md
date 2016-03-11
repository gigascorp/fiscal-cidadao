# Fiscal Cidadão

Projeto de fiscalização de convênios públicos para o cidadão.

## Definição da API

### GET /convenio

Recebe uma localização e retorna os convênios próximos a ela. Caso não haja nenhum convênio retorna status code 404
- Parâmetro de entrada
```json
{
    "lat": -2.45,
    "long": 0.437
}
```
- Retorno
```json
[
  	{
  	    "id": "ksje3rejr98745",
  		"justificativa": "Primeiro Ponto",
  		"data_inicio": "2015-12-01",
  		"data_fim": "2016-01-05",
  		"valor": 1500000.00,
  		"situacao": "pendente",
  		"proponente":{
  		    "nome": "Fulano de Tal",
  		    "telefone": "(98) 99876-5432"
  		},
  		"coordenada": {
  			"lat": -2.532783, 
  			"long": -44.291549
  		}
  	}, {
  	...
  	}
]
```
