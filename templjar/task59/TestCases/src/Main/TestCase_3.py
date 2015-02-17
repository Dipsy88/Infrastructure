'''
Created on Feb 9, 2015

@author: Dipesh
'''
import time
import pxssh

#Check if you can use video conferencing tool to call another video conferencing tool
# and accept up the call. After that you disconnect the call.

HOST1 = "ada.simula.no"
#HOST1 = "192.168.202.114"
USER1 = "admin"
PASSWORD1 = "1234"

HOST2 = "ada.simula.no"
CALL2= "192.168.202.68"
USER2 = "admin"
PASSWORD2 = ""

# HOST2 = "192.168.202.57"
# USER2 = "admin"
# PASSWORD2 = "1234"

client1 = pxssh.pxssh()        
client2 = pxssh.pxssh()  

class TestCase_3:

    def internet_on(self):
        client1 = pxssh.pxssh()        
        client2 = pxssh.pxssh()  
        client1.PROMPT=' ' 
        client2.PROMPT=' ' 
        try:  
            client1.login(HOST1, USER1, PASSWORD1, original_prompt=' ',port = '50046',auto_prompt_reset=False)
            client2.login(HOST2, USER2, PASSWORD2, original_prompt=' ',port = '50045',auto_prompt_reset=False)
        except:
            return "Fail"
        try:
            client1.sendline("xcommand Dial Number: " + CALL2)            
            time.sleep(7)
                
            client2.sendline("xcommand Call Accept")

            time.sleep(4)
            client2.sendline("xcommand Call DisconnectAll \n")

            client1.close()
            client2.close()
    
            return "Pass"
        except:
            return "Fail"