import urllib2

class TestCase_1:

	def internet_on(self):
	    try:
	        response=urllib2.urlopen('http://192.168.202.114',timeout=1)
	        return "Pass"
	    except :
	    	 return "Fail"