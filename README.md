# ChatApp

A simple Instant Message Server to facilitate one-to-one communication between many clients. 

-> Server can be started by just executing ChatServer class.

-> A Chat client is provided to demonstrate the one-to-one communication. Usage is as below:

Usage: ChatClient senderID receiverID
senderID and receiverID must be any integers between 1 to 100.
 
Eg: To open 2-way communication between 'user 10' and 'user 20', open 
 -> ChatClient 10 20 (opens chat client for user 10 to push/receive messages from/to 20)
 -> ChatClient 20 10 (opens chat client for user 20 topush/receive messages from/to  10)
