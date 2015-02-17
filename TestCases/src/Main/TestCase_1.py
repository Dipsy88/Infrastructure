import urllib2

class TestCase_1:

	def internet_on(self):
	    try:
	        response=urllib2.urlopen('http://74.125.224.72/',timeout=1)
	        return "Pass"
	    except urllib2.URLError as err: pass
	    return "Fail"
	
