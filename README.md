# async-example

Simple example showing how to use JavaLite Async to send messages to remote embedded broker. 

## Starting server

Execute: 

    ./start_server.sh 
    
## Starting client: 

    ./start_client.sh

## Variables

Experiment with variables at the top of [Client.java](https://github.com/ipolevoy/async-example/blob/master/src/main/java/async_example/Client.java#L13)
The messages can be sent in text mode (XML) or binary (zip). 

## Special notes

The clients craps out with connectivity exceptions sometimes. This was not investigated yet.  
    
