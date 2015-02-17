'''
Created on Feb 9, 2015

@author: Dipesh
'''
import time
import pxssh
# Check if you can use video conferencing tool to call another video conferencing tool and then disconnect the call.

HOST1 = "ada.simula.no"
#HOST1 = "192.168.202.114"
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
        client1 = pxssh.pxssh()     
        try:
            client1.PROMPT=' ' 
            client1.login (HOST1, USER1, PASSWORD1,original_prompt=' ',port = '50046',auto_prompt_reset=False)   
          #  client1.login (HOST1, USER1, PASSWORD1,original_prompt=' ',auto_prompt_reset=False)   
            print "Interactive SSH session established"
            
            client1.sendline('xcommand Dial Number: ' + HOST2)
           
        
            time.sleep(7)
            client1.prompt()
           

            client1.sendline("xCommand Call DisconnectAll")
            time.sleep(3)

            client1.close()
            return "Pass"
        except pxssh.ExceptionPxssh, e:
            print "pxssh failed on login."
            print str(e)