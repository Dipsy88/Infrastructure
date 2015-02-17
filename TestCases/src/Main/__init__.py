import urllib2
import sys
from TestCase_1 import TestCase_1
from TestCase_2 import TestCase_2
from TestCase_3 import TestCase_3
from TestCase_4 import TestCase_4

file = open('result.txt', 'w+')
column1 = "Test case number"
file.write(column1.ljust(50) + "Result \n")


test1 = TestCase_1()

arg1, arg2 =sys.argv
maximum = int(arg2)
print maximum
groups =[0]*maximum
groups[0]=TestCase_1()
groups[1]=TestCase_2()
groups[2]=TestCase_3()
groups[3]=TestCase_4()
for i in range (0,maximum):
    print "apple"
    result=groups[i].internet_on()
    file.write(str(i).ljust(50) + result + "\n")


file.close