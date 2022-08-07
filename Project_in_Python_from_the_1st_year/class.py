class player:
    score=0
    boh8eies=("skip this question","50-50")
    def __init__(self,name):
        self.name = name
    def getScore():
        return self.score
	
    def lengthOfBoh8eies():
        return len(boh8eies)
		
    def main(self,QuestionNum):
        print (questions(QuestionNum))
	print ("Απαντηση 1:" + answer1(QuestionNum) + "Απαντηση 2:" + answer2(QuestionNum) + "Απαντηση 3:" + answer3(QuestionNum) + "Απαντηση 4:" + answer4(QuestionNum))
	print ("Βοήθειες:" + len(boh8eies))
	playeranswer=input("Δώσε την απάντηση ή επελεξε βοήθεια:")
	while() :
            if (playeranswer="50-50") :
                if  ("50-50" in boh8eies):
                    boh8eies.remove("50-50")
		    print (questions(QuestionNum))
		    print ("Απάντηση 1:" , rightanswer)
		    if (rightanswer=answer1(QuestionNum)):
                        print ("Απάντηση 2:" , answer2(QuestionNum))
		    else :
                        print ("Απάντηση 2:" , answer3(QuestionNum))
		    print("Βοήθειες:" , len(boh8eies))
		    playeranswer=input("Δώσε απάντηση ή χρησιμοποίησε τη τελευταία βοήθειά σου:")
		    while (playeranswer="50-50"):
                        playeranswer=input("Ξαναδώσε απάντηση ή χρησιμοποίησε τη τελευταία βοήθειά σου:")
		    if (playeranswer="skip this question"):
                        QuestionNum=10
			playeranswer=input("Δώσε απάντηση:")
			while (playeranswer="skip this question" && playeranswer="50-50"):
			    playeranswer=input("Ξαναδώσε απάντηση:")
		    else :
                        if (playeranswer=rightanswer(QuestionNum)):
                            getScore()+=5
			    print ("Σωστά")
			    break
			else:
			    print ("Λάθος")
                            break
		else:
		    print ("Έχεις χρησιμοποιήσει ήδη το 50-50.")
		    while (playeranswer="50-50"):
			playeranswer=input("Ξαναδώσε ή χρησιμοποίησε τη τελευταία βοήθειά σου:")
	    elif (playeranswer="skip this question" ):
		if ("skip this question" in boh8eies):
                    getScore()+=5
		    boh8eies.remove("skip this question")
		    QuestionNum=10	
		    playeranswer=input("Δώσε απάντηση ή χρησιμοποίησε τη τελευταία βοήθειά σου:")
		    while (playeranswer="skip this question"):
			playeranswer=input("Ξαναδώσε ή χρησιμοποίησε τη τελευταία βοήθειά σου:")
		else:
		    print ("Έχεις χρησιμοποιήσει ήδη το skip this question.")
		    while (playeranswer="skip this question"):
						playeranswer=input("Ξαναδώσε απάντηση ή χρησιμοποίησε τη τελευταία βοήθειά σου:")
	    else:
		if (playeranswer=rightanswer(QuestionNum)):
                    getScore()+=10
		    print ("Σωστά")
		    break
		else :
                    print ("Λάθος")
		    break
