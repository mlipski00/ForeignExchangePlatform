# ForeignExchangePlatform
### Forex trading platform

[![Build Status](https://travis-ci.com/mlipski00/ForeignExchangePlatform.svg?branch=Branch-without-message-broker)](https://travis-ci.com/mlipski00/ForeignExchangePlatform)


The Forex Trading Platform project is result of broad investment banking knowledge and software engineering passion.

Platform allows you to execute demonstration trades on fx currencies market and check your investment skills.

Functionality:
* Websocket price streaming to client interface
* Transactions excecution and pre trade validation
* Email confirmations after transaction excecution
* Open transactions valuations
* Sending internal messages between users
* Security - logging and registering users 
* Users ranking by overall transactions profit
* Interbank currency prices provided by Oanda Forex Broker
* Admin panel: platform parameterization and user update (balance) with users internal message notification
* Logging users activity and price quotations into flat file
* Sending quotations to external services via message broker (message receivers are in separate GitHub repository: https://github.com/mlipski00/ForexProjectMessageBroker)

Repository contains Docker image build file (with CentOS Linux distribution image layer) - "Dockerfile".

Project is build with following technologies:
* Spring boot
* Spring security
* Spring data
* Hibernate
* H2 database
* Java Script
* Jquery
* Thymeleaf
* WebSockets
* RabbitMQ
* Oanda Forex Broker API library

