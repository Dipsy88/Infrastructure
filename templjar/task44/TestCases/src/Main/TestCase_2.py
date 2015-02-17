'''
Created on Feb 9, 2015

@author: Dipesh
'''
import urllib2
import telnetlib
import time
import paramiko
from sys import stdin

# Check if you can use video conferencing tool to call another video conferencing tool and then disconnect the call.

HOST1 = "192.168.202.114"
USER1 = "admin"
PASSWORD1 = "1234"

HOST2 = "192.168.202.68"
USER2 = "admin"
PASSWORD2 = ""

# HOST2 = "192.168.202.57"
# USER2 = "admin"
# PASSWORD2 = "1234"

class TestCase_2:

    def internet_on(self):
        client1 = paramiko.SSHClient()
       
        try:
            client1.load_system_host_keys()
            client1.set_missing_host_key_policy(paramiko.WarningPolicy())
            client1.connect("192.168.202.114", username="admin", password="1234")      
            
            remote_conn = client1.invoke_shell()
            print "Interactive SSH session established"

            remote_conn.send("xcommand Dial Number: " + HOST2 + "\n")            
            time.sleep(7)
            output = remote_conn.recv(5000)
            print output
   
            if "OK" not in output:
                return "Fail"

            remote_conn.send("xCommand Call DisconnectAll \n")
            time.sleep(1)
    
            output = remote_conn.recv(5000)
            print output
            
            if "OK" not in output:
                return "Fail"
            
            client1.close()
            return "Pass"
        except paramiko.AuthenticationException:
            return "Fail"