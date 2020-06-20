# Paint Application Server

## Features

- Authentication
  - Email Registration
  - User Identification
- PaintSession
  - Create
  - Join
  - Create as private
- PaintEvent
  - Share and Display with other users in Session
  - Log & Replay

```
                                               ,----------------------------------------.                                                  
   ,----------------------------------.        |PaintSessionController                  |                                                  
   |PaintUserController               |        |----------------------------------------|                                                  
   |----------------------------------|        |paintSessionService: PaintSessionService|         ,------------------------------------.   
   |paintUserService: PaintUserService|        |----------------------------------------|         |PaintEventController                |   
   |----------------------------------|        |createSession(): Mono<PaintSession>     |         |------------------------------------|   
   |createUser(): Mono<PaintUser>     |        |listSession(): Mono<PaintSession>       |         |paintEventService: PaintEventService|   
   |signInUser(): Mono<PaintUser>     |        |joinSession(): Mono<PaintSession>       |         |------------------------------------|   
   |getUser(): Mono<PaintUser>        |        |exitSession(): Mono<UUID>               |         |notifyEvent(): Mono<PaintEvent>     |   
   |deleteUser(): Mono<UUID>          |        |disableSession(): Mono<UUID>            |         `------------------------------------'   
   `----------------------------------'        |replaySession(): Flux<PaintEvent>       |                            |                     
                     |                         `----------------------------------------'                            |                     
                     |                                              |                                                |                     
                     |                                              |                                                |                     
                     |                      ,----------------------------------------------.                         |                     
,----------------------------------------.  |PaintSessionService                           |                         |                     
|PaintUserService                        |  |----------------------------------------------|   ,------------------------------------------.
|----------------------------------------|  |paintSessionRepository: PaintSessionRepository|   |PaintEventService                         |
|paintUserRepository: PaintUserRepository|  |----------------------------------------------|   |------------------------------------------|
|----------------------------------------|  |createSession(): Mono<PaintSession>           |   |paintEventRepository: PaintEventRepository|
|createUser(): Mono<PaintUser>           |  |listSession(): Mono<PaintSession>             |   |------------------------------------------|
|signInUser(): Mono<PaintUser>           |  |joinSession(): Mono<PaintSession>             |   |notifyEvent(): Mono<PaintEvent>           |
|getUser(): Mono<PaintUser>              |  |exitSession(): Mono<UUID>                     |   `------------------------------------------'
|deleteUser(): Mono<UUID>                |  |disableSession(): Mono<UUID>                  |                         |                     
`----------------------------------------'  |replaySession(): Flux<PaintEvent>             |                         |                     
                     |                      `----------------------------------------------'                         |                     
                     |                                              |                                                |                     
      ,---------------------------.                 ,------------------------------.                  ,----------------------------.       
      |PaintUserRepository        |                 |PaintSessionRepository        |                  |PaintEventRepository        |       
      |---------------------------|                 |------------------------------|                  |----------------------------|       
      |---------------------------|                 |------------------------------|                  |----------------------------|       
      |save(): Mono<PaintUser>    |                 |save(): Mono<PaintSession>    |                  |save(): Mono<PaintEvent>    |       
      |findById(): Mono<PaintUser>|                 |findById(): Mono<PaintSession>|                  |findById(): Mono<PaintEvent>|       
      `---------------------------'                 `------------------------------'                  `----------------------------'       
```

```puml
PaintUserController o-- PaintUserService
PaintUserService o-- PaintUserRepository

class PaintUserController {
    paintUserService: PaintUserService
    createUser(): Mono<PaintUser>
    signInUser(): Mono<PaintUser>
    getUser(): Mono<PaintUser>
    deleteUser(): Mono<UUID>
}
class PaintUserService {
    paintUserRepository: PaintUserRepository
    createUser(): Mono<PaintUser>
    signInUser(): Mono<PaintUser>
    getUser(): Mono<PaintUser>
    deleteUser(): Mono<UUID>
}
class PaintUserRepository { 
    save(): Mono<PaintUser>
    findById(): Mono<PaintUser>
}

PaintSessionController o-- PaintSessionService
PaintSessionService o-- PaintSessionRepository

class PaintSessionController { 
    paintSessionService: PaintSessionService
    createSession(): Mono<PaintSession>
    listSession(): Mono<PaintSession>
    joinSession(): Mono<PaintSession>
    exitSession(): Mono<UUID>
    disableSession(): Mono<UUID>
    replaySession(): Flux<PaintEvent>
}
class PaintSessionService {
    paintSessionRepository: PaintSessionRepository
    createSession(): Mono<PaintSession>
    listSession(): Mono<PaintSession>
    joinSession(): Mono<PaintSession>
    exitSession(): Mono<UUID>
    disableSession(): Mono<UUID>
    replaySession(): Flux<PaintEvent>
}
class PaintSessionRepository { 
    save(): Mono<PaintSession>
    findById(): Mono<PaintSession>
}

PaintEventController o-- PaintEventService
PaintEventService o-- PaintEventRepository

class PaintEventController {
    paintEventService: PaintEventService
    notifyEvent(): Mono<PaintEvent>
}
class PaintEventService {
    paintEventRepository: PaintEventRepository
    notifyEvent(): Mono<PaintEvent>
}
class PaintEventRepository {
    save(): Mono<PaintEvent>
    findById(): Mono<PaintEvent>
}
```
