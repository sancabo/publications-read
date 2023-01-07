# Publication Read Service
A Read Only Microservice. Exposes an api to search an example resource called Publication
A Publication is meant to represent a text user post on a social media.

Also exposes an API for mass data population. 

- POST /populator?intensisty - Request creation of an object that continuously saves random data.
  [intensisty] increases number of threads.

- DELETE /populator - Request shut down of data population.

- GET /populator - You can use this method to see if the populator has stopped or started.

You can use it to generate tens of millions of Publications and test the scalability of the system.

This service is part of an example of the CQRS pattern. IT consumes events producced from commands on a write microservice

This project is meant to improve the scalability limits of the single datase version. You can find it on my repo *publications-rw*.
You can check my repo <> to see the write service part of this example.
