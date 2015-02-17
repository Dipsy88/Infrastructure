'''
Created on Feb 9, 2015

@author: Dipesh
'''
import urllib2
import telnetlib
import time
import paramiko

#Check if you can use video conferencing tool to call another video conferencing tool
# and accept up the call. After that you disconnect the call.

HOST1 = "192.168.202.114"
USER1 = "admin"
PASSWORD1 = "1234"

HOST2 = "192.168.202.68"
USER2 = "admin"
PASSWORD2 = ""

# HOST2 = "192.168.202.57"
# USER2 = "admin"
# PASSWORD2 = "1234"

result = "Fail"
client1 = paramiko.SSHClient()       
client2 = paramiko.SSHClient()

class TestCase_3:

    def internet_on(self):
        client1 = paramiko.SSHClient()       
        client2 = paramiko.SSHClient()
        
        self.connects(client1, HOST1, USER1, PASSWORD1)
        self.connects(client2, HOST2, USER2, PASSWORD2)
        try:
            remote_conn1 = client1.invoke_shell()
            remote_conn2 = client2.invoke_shell()
           
            remote_conn1.send("xcommand Dial Number: " + HOST2 + "\n")            
            time.sleep(7)
            output = remote_conn1.recv(5000)
            print output
   
            a = output.split('\n') 

            for s in a:  
                if ("CallId" in s):       
                    a = int(s.split()[1])
                    print a
                
            remote_conn2.send("xcommand Call Accept \n")
            
            output = remote_conn2.recv(5000)
   
            if "OK" not in output:
                return "Fail"

            time.sleep(4)
            remote_conn2.send("xcommand Call DisconnectAll \n")

            output = remote_conn2.recv(5000)
            
            if "OK" not in output:
                return "Fail"
            
            client1.close()
            client2.close()

            return self.result
        except paramiko.AuthenticationException:
            return "Fail"

    def connects(self, client, host, username, password):
        try:
            client.load_system_host_keys()
            client.set_missing_host_key_policy(paramiko.WarningPolicy())
            client.connect(host, username=username, password= password)      
            self.result = "Pass"              
        except paramiko.AuthenticationException:
            self.result= "Fail"