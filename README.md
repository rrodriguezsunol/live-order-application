## Assumptions

Added validation so that price must be greater than 0.

Added validation so that quantity must be greater than 0.

Even though quantity always appears in kilos, I've used the ValueObject pattern 
and created the WeightValue so it can be extended to other weight units.

Even though price per kg always appears in GBP, I've used the ValueObject pattern
and created the PricePerKg class so it can be extended to other currencies.

## Architectural decisions

Since this solution uses in-memory persistence, I've decided to calculate the summary
of live orders at read time rather than at write time. In real life though, and with a proper 
data store in place, analysis of the read/write workload and volume of data should be taken 
into consideration when making such decision. 