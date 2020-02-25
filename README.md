# SSH Tunneling Service
This service provides ssh tunneling sessions by using SSH remote port forwarding.\
Basically this allows you to add, delete and retrive a SSH tunneling session and remote port by using RESTful API.\
Also you are able to do above mentioned by using cli based on BASH script instead of RESTful API.

### RESTful API
Writing on [SSH Tunneling API](https://documenter.getpostman.com/view/474408/SzKPWhMh?version=latest).

### CLI ([ssh-cmd.sh](https://github.com/parkjunhong/SSH-Tunneling-Service/blob/master/shell/ssh-cmd.sh))

__connect__   : connect to ssh server for remote port forwarding.
```bash
Usage:
 ssh-cmd <command> <options>

[Command]
 ADD Tunneling
 e.g.) ssh-cmd --add-forwarding=<rport>:<dhost>:<dport>/<username>@<shost>:<sport>
       ssh-cmd --add-forwarding=1234:127.0.0.1:1234/user@192.168.0.2:22
 [options]
 rport: remote port.
 dhost: destination host.
 dport: destination port.
 user : SSH Server username.
 shost: SSH Server host.
 sport: SSH Server port.

 DELete Tunneling
 e.g.) ssh-cmd --del-forwarding=<rport>/<username>@<shost>:<sport>
       ssh-cmd --del-forwarding=1234/user@192.168.0.2:22
 [options]
 rport: remote port.
 user : SSH Server username.
 shost: SSH Server host.
 sport: SSH Server port.

 LIST
 provide remote port forwarding information.
 e.g.) ssh-cmd --list-all

 -v: verbose
```
Edited 2020-02-16, Korea.

### Core References
- [Jsch on JCraft](http://www.jcraft.com/jsch/)
