# elasticsearch
  Project is about the providing CRUD and search operations on dataset which is about houses in London with using Elasticsearch.
 
## Endpoints
 
- **createNewHouse (POST)**
  - RequestParams
    - House(model)
      - Send it in request body as json object 
  - ResponseParams
    - Returns a json object in response body.

- **getHouseById (GET)**
  - RequestParams
    - Id(String)
      - Send it as a path variable 
  - ResponseParams
    - Returns a json object which is represents Id parameter.

- **updateHouse (PUT)**
  - RequestParams
    - Id(String)
      - Send it as a path variable 
    - House(model)
      - Send it in request body as json object 
  - ResponseParams
    - Returns a updated json object which is represents Id parameter.

- **deleteHouse (DELETE)**
  - RequestParams
    - Id(String)
      - Send it as a path variable 
  - ResponseParams
    - void.

- **searchInAll (GET)**
  - RequestParams
    - Query(String)
      - Send it as query param 
  - ResponseParams
    - Returns a list of json object where every object contains query param.

- **searchByPrice (GET)**
  - RequestParams
    - Min(Double)
      - Send it as query param 
    - Max(Double)
      - Send it as query param  
  - ResponseParams
    - Returns a list of json object where every object price between min and max value.
