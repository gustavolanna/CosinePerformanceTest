<h3>Cosine performance test</h3> 

This project has gatling tests to measure the API performance. The Cosine Simlarity API should be running at localhost:8080. 

The following are the performance tests:
  - LoadTest: to test the load performance
  - RequestsTest test: to test the performance of PUT/GET/DELETE requests simultaneously

To run the tests, please type the following maven command

```
#LoadTest
mvn gatling:test -Dgatling.simulationClass=LoadTest

#RequestsTest
mvn gatling:test -Dgatling.simulationClass=RequestsTest
```
