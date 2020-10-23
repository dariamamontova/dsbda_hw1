#!/usr/bin/python

import random
import sys
from sys import argv

USER_AGENTS= ["Mozilla/5.0 (Windows NT 6.1; WOW64; Trident/7.0; AS; rv:11.0) like Gecko",
			"Mozilla/5.0 (compatible, MSIE 11, Windows NT 6.3; Trident/7.0;  rv:11.0) like Gecko",
			"Mozilla/5.0 (X11; Linux i586; rv:31.0) Gecko/20100101 Firefox/31.0",
			"Mozilla/5.0 (Windows NT 6.1; WOW64; rv:31.0) Gecko/20130401 Firefox/31.0",
			"Opera/9.80 (X11; Linux i686; U; es-ES) Presto/2.8.131 Version/11.11",
			"Mozilla/5.0 (Windows NT 5.1; U; en; rv:1.8.1) Gecko/20061208 Firefox/5.0 Opera 11.11",
			"Mozilla/5.0 (Macintosh; U; Intel Mac OS X 10_6_6; en-gb) AppleWebKit/533.20.25 (KHTML, like Gecko) Version/5.0.4 Safari/533.20.27",
			"Mozilla/5.0 (Macitosh; U; Intel Mac OS X 10_6_6; sv-se) AppleWebKit/533.20.25 (KHTML, like Gecko) Version/5.0.4 Safari/533.20.27",
			"Mozilliuz/5.0 (Hackintosh; U; Intel Macosev OS X 10_6_6; en-gb) bubu baba"]

CODES = [200, 400, 404, 500]

METHODS = ["GET","POST"]


def gen_line():
    method = random.choice(METHODS)
    code = random.choice(CODES)
    user_agent = random.choice(USER_AGENTS)
    log = '17.195.114.50 - - [24/Apr/2011:04:06:01 -0400] "%s post/261556 HTTP/1.1" %s 40028 "-" %s' % (method, code, user_agent)
    return log

def generator():
    if len(sys.argv) == 3:
        line_count = int(sys.argv[1])
        output_file = str(sys.argv[2])
        lines = []
        for i in range(0, line_count):
            line = gen_line()
            lines.append(line)
        with open(output_file, "w") as file:
            for i in lines:
                file.write(i + '\n')
        file.close()
        print("File '%s' with %s lines generated." % (output_file, line_count))
    else:
        print ("You should specify the number of lines and output file. Example: generator.py 100 input.txt")
        sys.exit (1)


if __name__ == "__main__":
    print("Generating...")
    generator()
    print("Done")


