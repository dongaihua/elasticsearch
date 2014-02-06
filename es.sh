curl -H "X-Client-Id:client_test_id_1" -H "Content-Type:application/json" -XPOST localhost:9200/v1/purchases -d '

{
          "user-id"  :  "111791363",
          "order-id"  :  "4791382",
          "order-number"  :  "G00004791363",
          "payment-id"  :  "12345",
          "payment-method-id"  :  "1120",
          "payment-amount"  :  3.15,
          "order-amount"  :  3.15,
          "description"  :  "some description",
         
          "creditcard" : { 
               "number"  :  "4000009999999991",
               "expiry-month"  :  "05",
               "expiry-year"  :  "2014",
             "cvv"  :  "345"
          },
         
          "billing-address" : {
               "first-name"  :  "Bob",
               "last-name"  :  "Lee",
               "street"  :  "295 Phillip Street",
               "street-cont"  :  "",
               "city"  :  "XX",
               "zip"  :  "N2L 3W8",
               "state"  :  "Waterloo",
               "state-abbr"  :  "CA",
               "country-iso"  :  "CA",
               "phone"  :  ""
          },
         
         "additional-payment-gateway-fields" : {
               "distributor-id"  :  10007401,
               "shipping-method-name"  :  "PickUp",
               "shipping-company"  :  "FederalExpress",
               "order-skus"  :  "123AU&456&343AP",
               "email"  :  "test@test.com",
               "ip"  :  "102.3.4.45",
               "order-description"  :  "OG order",
    
               "shipping-address" : {
                    "first-name"  :  "Bob",
                    "last-name"  :  "Lee",
                    "street"  :  "xxxx",
                    "street-cont"  :  "xxx",
                    "city"  :  "xxxx",
                    "zip"  :  "xxxx",
                    "state"  :  "xxx",
                    "country-iso"  :  "US"
               }
    
          }
     }
'
