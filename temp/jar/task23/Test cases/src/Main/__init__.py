import urllib2
import sys
from TestCase_1 import TestCase_1
from TestCase_2 import TestCase_2

file = open('result.txt', 'w+')
column1 = "Test case number"
file.write(column1.ljust(50) + "Result \n")


test1 = TestCase_1()

   
    

arg1, arg2 =sys.argv
maximum = int(arg2)
print maximum
groups =[0]*2
groups[0]=TestCase_1()
groups[1]=TestCase_2()
for i in range (0,maximum):
    result=groups[i].internet_on()
    file.write(str(i).ljust(50) + result + "\n")


file.close