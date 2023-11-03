f = open("input.txt", "w")
f.write("16\n")
for i in range(26):
    if chr(65+i) == "P":
        break
    f.write("%s\n" % chr(65+i))
