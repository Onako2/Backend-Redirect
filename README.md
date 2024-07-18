# Backend-Redirect
This plugin allows you to redirect a player to another seperate server depending on backend server. This allows you to redirect players to a partner network as an example (Meaning that you can link multiple Velocity networks together). You can also just send your players to your best friend's server😁.

The config is in
`plugins/backendredirect/config.yml`


<details>
<summary>Default config</summary>


```
# Config for velocity auto redirect
#
# The target server must be reachable from outside the proxy and should optionally be on online-mode
# "server_name": "host:port"
# /server <server_name> -> Redirects the player to the target server
# Compatible with BungeeCord Plugin Messaging Channel

servers:
  - "yourfriendsserver": "example.com:25566"
```


</details>




![Metrics data of velocity-auto-redirect](https://bstats.org/signatures/velocity/Backend-Redirect.svg)
<small>This plugin uses bStats for metrics, it can be disabled by editing the config in the bStats folder<small>