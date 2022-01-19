import math
# min hour day

print("Standard Problems")
print("Seconds in the week = " + str(60*60*7))
print("Volume of Sphere radius 10 = " + str(4.0*math.pi*(10**3)/3))
print("Number of possibilities in GO = " + str(3**(19*19)))

print("\nAdvanced Problems")
print("Third digit from the right (100s place) would be ((n % 1000) // 100)")
print("Third bit from the right (4s place) would be ((n % 8) // 4)")
print("Alternatively, you could also do ((n & 4) >> 2)")