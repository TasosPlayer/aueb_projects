import re
import sys
import numpy as np
import numpy.linalg

textfile = []

numpy.set_printoptions(formatter={"float": lambda x:("%2.3f" %x)})
with open("svd.txt") as textFile:
    lines = [line.split() for line in textFile]
    A = np.array(lines)

A = A.astype(int)
print(A)

print("SVD:")
print()
U, S, V = numpy.linalg.svd(A)
print("U:")
print(U)
print()
print("S:")
print(S)
print()
print("V:")
print(V)
print()

S = np.diag(S)
V = V[:5999, :]
print("Verify that U*S*V = A:")
print(U.dot(S).dot(V))
print()
print("Verify that the columns of U are orthonormal:")
print(U.transpose().dot(U))
print()
print("Verify that the rows of V are orthonormal:")
print(V.dot(V.transpose()))
print()
k=300
print("Rank %d approximation of A:" %k)
Uk = U[:, :k]
Sk = S[:k, :k]
Vk = V[:k, :]
Ak = Uk.dot(Sk).dot(Vk)
Score = np.array(Ak)
print(Score)
np.set_printoptions(threshold=np.inf)
np.savetxt("k300.txt",Score)
