#!/usr/bin/python
#!/usr/bin/env python

for x in range(13):
    y = 2001+x
    s = "bmc_data/"+ str(y) + "F_c.txt"
    f = open(s,'w')
    f.close()
    s = "bmc_data/"+ str(y) + "F_sf.txt"
    f = open(s,'w')
    f.close()
    y = y+1
    s = "bmc_data/"+ str(y) + "S_sf.txt"
    f = open(s,'w')
    f.close()
    s = "bmc_data/"+ str(y) + "S_c.txt"
    f = open(s,'w')
    f.close()
    