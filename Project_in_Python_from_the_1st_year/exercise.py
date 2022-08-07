

questions=["Στον πρώτο βαθμό τοπικής αυτοδιοίκησης ανήκει:","Συμπληρώστε με την κατάλληλη φράση το κενό της πρότασης: «Η ταινία έτυχε ..................... από το κοινό».","Ένα ρολόι χτυπάει κάθε 15 λεπτά. Εάν ακούστηκε στις 12:40 πότε αναμένεται να χτυπήσει;","Η Ελένη δουλεύει πιο πολύ από τον Πέτρο. Η Μαρία δουλεύει πιο λίγο από τον Πέτρο. Επομένως:","Σε 100 γραμμάρια αλεύρι που περιέχει 20% πίτουρα προστίθενται 50 γραμμάρια αλεύρι που περιέχει 10% πίτουρα. Στο αλεύρι που θα προκύψει τα πίτουρα αποτελούν το/τα:","Η γλώσσα κόκαλα δεν έχει και κόκαλα...","Ποιόν δάσκαλο είχε ο Μέγας Αλέξανδρος;","Πόσα χρόνια έζησε ο Χριστός στη γη;","Πώς λεγόταν ο τραγουδιστής της Οδύσσειας;","Πόση είναι η θερμοκρασία στο κέντρο του Ήλιου;","Πόσους πλανήτες έχει το ηλιακό μας σύστημα;"]
answer1=["η υπερνομαρχία","ενθουσιώδη υποδοχής","Στις 19:15,δεν μπορεί να προσδιοριστεί το πόσο δουλεύει η Ελένη σε σχέση με το πόσο δουλεύει η Μαρία.","ένα πέμπτο (1/5).","σπάει","Αριστοτέλη",31,"Κώμος","1000 βαθμοί Κελσίου",8]
answer2=["η περιφέρεια","ενθουσιώδους υποδοχής","Στις 20:10"," η Ελένη δουλεύει πιο πολύ από τη Μαρία.","ένα έκτο (1/6).","διαλύει","Ισοκράτη",32,"Φήμιος","10000 βαθμοί Κελσίου",12]
answer3=["ο δήμος","ενθουσιώδη υποδοχή","Στις 17:30,η Ελένη δουλεύει πιο λίγο από τη Μαρία.","ένα δέκατο (1/10).","τσακίζει","Ιπποκράτη",33,"Δημόδοκος","100000 βαθμοί Κελσίου",6]
answer4=["η νομαρχία","ενθουσιώδης υποδοχή","Στις 16:05","η Ελένη δουλεύει εξίσου με τη Μαρία.","τρία εικοστά (3/20).","ραγίζει","Πλάτωνα",34,"Υμέναιος","15000000 βαθμοί Κελσίου",10]
rightanswer=["ο δήμος","ενθουσιώδους υποδοχής","Στις 20:10","η Ελένη δουλεύει πιο πολύ από τη Μαρία.","ένα έκτο (1/6).","τσακίζει","Αριστοτέλη",33,"Φήμιος","15000000 βαθμοί Κελσίου",8]

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

name1=input("Give your name:")	
name2=input("Give your name:")	
name3=input("Give your name:")
player1= player(name1)
player2= player(name2)
player3= player(name3)	

for QuestionNum in range(10)
    player1.question(QuestionNum)
    player2.question(QuestionNum)
    player3.question(QuestionNum)
	
if (player1.lengthOfBoh8eies()==2):
    player1.getScore()+=10
elif (player1.lengthOfBoh8eies()==1):
    player1.getScore()+=5
	
if (player2.lengthOfBoh8eies()==2):
    player2.getScore()+=10
elif (player2.lengthOfBoh8eies()==1):
    player2.getScore()+=5
	
if (player3.lengthOfBoh8eies()==2):
    player3.getScore()+=10
elif (player3.lengthOfBoh8eies()==1):
    player3.getScore()+=5
	
if (player1.getScore()>=player2.getScore() && player1.getScore()>=player3.getScore() && player2.getScore()>=player3.getScore()):
    print ("Νικητής:" , name1 , "." , "Δεύτερος:" , name2 , "Τρίτος" , name3)
elif (player1.getScore()>=player2.getScore() && player1.getScore()>=player3.getScore() && player3.getScore()>=player2.getScore()):
    print ("Νικητής:" , name1 , "." , "Δεύτερος:" , name3 , "Τρίτος" , name2)
elif (player2.getScore()>=player1.getScore() && player2.getScore()>=player3.getScore() && player3.getScore()>=player1.getScore()):
    print ("Νικητής:" , name2 , "." , "Δεύτερος:" , name3 , "Τρίτος" , name1)
elif (player2.getScore()>=player1.getScore() && player2.getScore()>=player3.getScore() && player1.getScore()>=player3.getScore()):
    print ("Νικητής:" , name2 , "." , "Δεύτερος:" , name1 , "Τρίτος" , name3)
elif (player3.getScore()>=player1.getScore() && player3.getScore()>=player2.getScore() && player1.getScore()>=player2.getScore()):
    print ("Νικητής:" , name3 , "." , "Δεύτερος:" , name1 , "Τρίτος" , name2)
else
    print ("Νικητής:" , name3 , "." , "Δεύτερος:" , name2 , "Τρίτος" , name1)

	
	
	
