'''
Created on Feb 9, 2015

@author: Dipesh
'''
import time
import pxssh

# Check if you can start presentation and then stop it

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
class TestCase_8:
    def internet_on(self):
        client2 = pxssh.pxssh()  
        client2.PROMPT=' '
        try:  
            client2.login(HOST2, USER2, PASSWORD2, original_prompt=' ',port = '50045',auto_prompt_reset=False)
        except:
            return "Fail"
        try:         
            client2.sendline('xcommand Presentation Start PresentationSource: 1')
            time.sleep(10)
           
            client2.sendline('xcommand Presentation Stop')
            time.sleep(2)
           
            client2.close()            
            return "Pass";
        except:
            return "Fail"