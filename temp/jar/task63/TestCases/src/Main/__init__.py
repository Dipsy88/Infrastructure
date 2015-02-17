import urllib2
import sys
from TestCase_1 import TestCase_1
from TestCase_2 import TestCase_2
from TestCase_3 import TestCase_3
from TestCase_4 import TestCase_4
from TestCase_5 import TestCase_5
from TestCase_6 import TestCase_6
from TestCase_7 import TestCase_7
from TestCase_8 import TestCase_8
from TestCase_9 import TestCase_9

file = open('result.txt', 'w+')
column1 = "Test case number"
file.write(column1.ljust(50) + "Result \n")

arg1, arg2 =sys.argv
maximum = int(arg2)
# print maximum
groups =[0]*9
groups[0]=TestCase_1()
groups[1]=TestCase_2()
groups[2]=TestCase_3()
groups[3]=TestCase_4()
groups[4]=TestCase_5()
groups[5]=TestCase_6()
groups[6]=TestCase_7()
groups[7]=TestCase_8()
groups[8]=TestCase_9()

for i in range (0,maximum):
    result=groups[i].internet_on()
    file.write(str(i+1).ljust(50) + result + "\n")

file.close