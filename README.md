# Chat Server for team-208-SP19
## Team Members
- Ram Prakash Arivu Chelvan 
- Himanshu Budhiah (github.com/budhiahimanshu96)
- Shashwat Sanghavi (github.com/srsanghavi)
- John Goodacre 

## Links

- System Demo: https://youtu.be/0ip6OYNTJ4Y
- System Setup: https://youtu.be/MmCKiArkris
- Final Presentation: https://youtu.be/BppZbMyqy-w

## Description

We've built a ChatServer that can communicate and coordinate message communication with different clients.
- The ChatServer code can be found in Development/ChatServer
- The Client code can be found in Development/UI

The ChatServer was built over the Prattle base code and fully written in Java.
+ Websockets are used to communicate with Clients
+ Uses an API Layer to parse messages and trigger appropriate action
+ Supports Users, Groups, Conversations and Messages
+ Users are authenticated before application access
+ Groups contain conversations and can be password protected
+ Threads are essentially a group of messages that exist in a conversation. They are meant to provide context to a running conversation between users
+ Messages can be broadcast to other individual users in the platform
+ MySQL database and the Server hosted on EC2 Instances on AWS

The Client was built using React and it connects to the Server using websocket
A User would be able to:
+ Create a new account / Sign in to an existing account
+ Create new groups
+ Send messages to other users
+ Send messages in Groups
+ Broadcast messages to other users
+ Find other users/groups in the service, if they can be found
+ Mark oneself as a Private user
+ Delete a Group
+ Retrieve old messages when online
+ NEW: Messages are automatically translated to a language of your choice.
+ NEW: Profile Pictures - Users can update their display picture
+ NEW: GIFs and Emojis - Users can now send Emojis and GIFs
+ NEW: Multimedia messages: Users can send and receive images and videos and view them in the client.
+ NEW: Online status: Users can now see if other users are online.

## Demo

<a href="https://drive.google.com/file/d/1szK1d0xhhI-7Br-wzWR_tJgZZ4Qgm1Up/view" target="_blank"><img src="screenshot.png" 
alt="Demo" width="400" height="300" border="10" /></a>

## Development

- Sprint 1 - High level design, generation of ERD and UML diagrams, user stories and feature choices, working tests for the base code
- Sprint 2 - DB creation and server setup, backend work to support the planned features, basic client communication to test user-user messaging
- Sprint 3 - Server setup on AWS, Client development in React and interfacing the client with the server and lending backend support in this process
- Sprint 4 - Stabilized Server and Client code, resolved concurrency issues, added features like Translation, Multimedia support.


## Design Patterns and Techniques

- Singleton
- Factory Method
- Dependency Injection


