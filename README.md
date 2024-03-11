# ejml-nd4j-benchmark
Sample project to show the performance degradation of EJML compared to ND4J on bigger matrices. The configuration 
parameters, passed to JMH (see below) are quite naive (e.g. I only do one warmup round), but should be good enough to 
highlight the problem.

## Running in Linux
Execute the following command in the root of the project:
```
./mvnw jmh:benchmark -Djmh.f=1 -Djmh.wi=1 -Djmh.i=3 -Djmh.bm=avgt
```

## Running in Windows
Execute the following command in the root of the project:
```
mvnw.cmd jmh:benchmark -Djmh.f=1 -Djmh.wi=1 -Djmh.i=3 -Djmh.bm=avgt
```

## More configuration parameters
Additional command line parameters (or description of already used ones) can be found here:
https://github.com/metlos/jmh-maven-plugin/tree/master
