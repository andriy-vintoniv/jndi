@ECHO OFF
start rmiregistry
echo "RMI registry started"
PAUSE 

start java -classpath d:\Workspaces\PDP\jndi\jndi-ldap\bin com.epam.rmi2rmi.server.Server
echo "Server started"
PAUSE 

start java -classpath d:\Workspaces\PDP\jndi\jndi-ldap\bin com.epam.rmi2rmi.client.Client
echo "Client started"
PAUSE 