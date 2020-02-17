# SSH Tunneling Service
This service provides ssh tunneling sessions by using SSH remote port forwarding.\
Basically this allows you to add, delete and retrive a SSH tunneling session and remote port by using RESTful API.\
Also you are able to do above mentioned by using cli based on BASH script instead of RESTful API.

### RESTful API
Writing on [SSH Tunneling API](https://documenter.getpostman.com/view/474408/SzKPWhMh?version=latest).

### CLI ([ssh-tunneling](https://github.com/parkjunhong/SSH-Tunneling-Service/blob/master/shell/ssh-tunneling))

__connect__   : connect to ssh server for remote port forwarding.
```bash
strm-cli.sh connect -r <rport>:<host>:<port> -s <username>@<svr-host> -p <svr-port> -v

[options]
-r: Remote Port Forwarding(RPF) Information.
    + rport : remote port.
    + host  : RFP destination host.
    + port  : RFP destination port.
-s: SSH Server Information.
    + username: SSH Server user name.
    + svr-host: SSH Server host.
    + svr-port: SSH Server port.
-p: SSH Server port    
```

__disconnect__: cancel a remote port forwarding.
```bash
strm-cli.sh disconnect -t <rport>:<username>@<svr-host>:<svr-port>

[options]
-t: Remote Port Forrwarding Unique Information.
    + rport: remote port
    + username: account username.
    + svr-host: SSH Server host.
    + svr-port: SSH Server port.
```

__list__      : provide remote port forwarding information.
```bash
strm-cli.sh list
```
Edited 2020-02-16, Korea.

### Core References
- [Jsch on JCraft](http://www.jcraft.com/jsch/)
