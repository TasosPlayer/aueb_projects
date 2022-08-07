#include "pizza.h"

// Functions

//Calculates and returns a random value between tLow, tHigh, in accordance to seed   
unsigned int rngBetweenTwoValues (unsigned int* seed, int tLow, int tHigh){
    return ((rand_r(seed) % (tHigh - tLow + 1)) + tLow);
}


//Our customer order function
void *order(void *id){

	//Local variables
 	int oid= *(int*)id;
    int pitses;
    int rc;
    // Time variables
    struct timespec custStart, telStart, telEnd, readyPizza, packEnd, PizzaDelivered;
	int tCustStart, tTelStart, tTelEnd, tReadyPizza, tPackEnd, tPizzaDelivered; 
	int tTimeWaitingTel;
	int roadTime;
    int tDeliveryTime; 
    int tColdPizzaTime; 
    int tPackTime;
    //printf("Hello from order: %d\n",oid);

//Initializing customer start time
    clock_gettime(CLOCK_REALTIME,&custStart);
    tCustStart =custStart.tv_sec;

    if(oid!=1){
    	sleep(rngBetweenTwoValues(&seed,Torderlow,Torderhigh));//wait to call
    }

    clock_gettime(CLOCK_REALTIME,&telStart);//call after some time if you are not order id 1
    tTelStart =telStart.tv_sec;             //so, initialize tel start after the waiting

//Start tel
    rc=pthread_mutex_lock(&telLock);
    while(tels==0){
    	//busy tels
    	rc = pthread_cond_wait(&telWait, &telLock);
    }
    clock_gettime(CLOCK_REALTIME,&telEnd);
    tTelEnd =telEnd.tv_sec;
    tTimeWaitingTel = tTelEnd - tTelStart; 

    tels--;
    
//pare arithmo apo pitses
    pitses=rngBetweenTwoValues(&seed,Norderlow,Norderhigh);

//perimene na plirwthei
    sleep(rngBetweenTwoValues(&seed,Tpaymentlow,Tpaymenthigh));

//pithanotita 5% na apotyxei h plirwmi ths paraggelias
    if(rand_r(&seed)%100<5){//apetyxe
    	tels++;
   		rc = pthread_cond_signal(&telWait);
   		rc = pthread_mutex_unlock(&telLock);
//End tel

//change Failed transactions counter
    	rc=pthread_mutex_lock(&fTransactionLock);
    	while(!changeFTransactions)
    		rc = pthread_cond_wait(&fTransactionWait, &fTransactionLock);
    	changeFTransactions=0;

    	failedtransaction++;

    	changeFTransactions=1;
    	rc = pthread_cond_signal(&fTransactionWait);
    	rc=pthread_mutex_unlock(&fTransactionLock);

//Screen 1st message fail
    	rc=pthread_mutex_lock(&screenLock);
    	while(!screen)
    		rc = pthread_cond_wait(&screenWait, &screenLock);
    	screen=0;
    	printf("H paraggelia me arithmo %d apetyxe.\n", oid);

    	screen=1;
    	rc = pthread_cond_signal(&screenWait);
    	rc=pthread_mutex_unlock(&screenLock);
    	pthread_exit(NULL);
    }
    else{//petyxainei kata 95%

//change Succeeded transactions counter
    	rc=pthread_mutex_lock(&sTransactionLock);
    	while(!changeFTransactions)
    		rc = pthread_cond_wait(&sTransactionWait, &sTransactionLock);
    	changeSTransactions=0;

    	suceededtransaction++;

    	changeSTransactions=1;
    	rc = pthread_cond_signal(&sTransactionWait);
    	rc=pthread_mutex_unlock(&sTransactionLock);

//Screen 1st message succeed
    	rc=pthread_mutex_lock(&screenLock);
    	while(!screen)
    		rc = pthread_cond_wait(&screenWait, &screenLock);
    	screen=0;
    	printf("H paraggelia me arithmo %d kataxwirithike.\n", oid);
    	screen=1;
    	rc = pthread_cond_signal(&screenWait);
    	rc=pthread_mutex_unlock(&screenLock);

//Update Money
    	rc=pthread_mutex_lock(&moneyLock);
    	while(!changeMoney)
    		rc = pthread_cond_wait(&moneyWait, &moneyLock);
    	changeMoney=0;

    	totalMoney += Cpizza*pitses;

    	changeMoney=1;
    	rc = pthread_cond_signal(&moneyWait);
    	rc=pthread_mutex_unlock(&moneyLock);
    }
    tels++;
    rc = pthread_cond_signal(&telWait);
    rc = pthread_mutex_unlock(&telLock);
//End tel

//Update sumTimeWaitingTel
    rc=pthread_mutex_lock(&sumTelLock);
    while(!changesumTimeWaitingTel)
    	rc = pthread_cond_wait(&sumTelWait, &sumTelLock);
    changesumTimeWaitingTel =0;

    sumTimeWaitingTel+=tTimeWaitingTel;//add to sum global var

    changesumTimeWaitingTel=1;
    rc = pthread_cond_signal(&sumTelWait);
    rc = pthread_mutex_unlock(&sumTelLock);

//Update maxTimeWaitingTel
    if(tTimeWaitingTel > maxTimeWaitingTel){ //check if it is the max waiting time for telephonite

    	rc=pthread_mutex_lock(&maxTelLock);
    	while(!changemaxTimeWaitingTel)
    		rc = pthread_cond_wait(&maxTelWait, &maxTelLock);
    	changemaxTimeWaitingTel =0;
	
    	maxTimeWaitingTel = tTimeWaitingTel;
	
    	changemaxTimeWaitingTel=1;
    	rc = pthread_cond_signal(&maxTelWait);
    	rc = pthread_mutex_unlock(&maxTelLock);
	}

//Start cook
    rc = pthread_mutex_lock(&cookLock);
    while(cooks==0){
    	//busy cooks
    	rc = pthread_cond_wait(&cookWait, &cookLock);
    }
    cooks--;
    sleep(Tprep);

//Start oven
    rc = pthread_mutex_lock(&ovenLock);
    while(ovens<pitses){
    	//buse ovens
    	rc = pthread_cond_wait(&ovenWait, &ovenLock);
    }
    ovens-=pitses;

    cooks++;
    rc = pthread_cond_signal(&cookWait); //
	rc = pthread_mutex_unlock(&cookLock);// cook doesnt wait for the pizzas to be baked
//End cook

    sleep(Tbake);

    clock_gettime(CLOCK_REALTIME,&readyPizza);
    tReadyPizza =readyPizza.tv_sec;

//Start pack
    rc=pthread_mutex_lock(&packerLock);
    while(!packer){
    	//buse packer
    	rc = pthread_cond_wait(&packerWait, &packerLock);
    }
    packer=0;
    sleep(Tpack);//time for packing

    clock_gettime(CLOCK_REALTIME,&packEnd);
    tPackEnd =packEnd.tv_sec;

    tPackTime = tPackEnd - tCustStart; //time to pack pizzas

//Screen 2nd message
    rc = pthread_mutex_lock(&screenLock);
    while(!screen)
    	rc = pthread_cond_wait(&screenWait, &screenLock);
    screen=0;
    printf("H paraggelia me arithmo %d etoimasthke se %d lepta.\n", oid, tPackTime);
    screen=1;
    rc = pthread_cond_signal(&screenWait);
    rc = pthread_mutex_unlock(&screenLock);

    ovens+=pitses;
    rc = pthread_cond_broadcast(&ovenWait);//enimerwse ola ta threads epidi eleytherwthikan panw apo enas fournoi
	rc = pthread_mutex_unlock(&ovenLock);//ovens are unlocked when the packing is finished
//End oven/s

	packer=1;
	rc = pthread_cond_signal(&packerWait);
    rc=pthread_mutex_unlock(&packerLock);
//End pack

//Start delivery
    rc = pthread_mutex_lock(&delivererLock);
    while(deliverers=0)
    	rc = pthread_cond_wait(&delivererWait, &delivererLock);
    deliverers--;

    roadTime=rngBetweenTwoValues(&seed, Tdellow, Tdelhigh);
	sleep(roadTime);//time on road to go

    clock_gettime(CLOCK_REALTIME,&PizzaDelivered);
    tPizzaDelivered =PizzaDelivered.tv_sec;

    tDeliveryTime = tPizzaDelivered - tCustStart; //time that pizzas were  delivered

    //Screen 3rd message
    rc = pthread_mutex_lock(&screenLock);
    while(!screen)
    	rc = pthread_cond_wait(&screenWait, &screenLock);
    screen=0;
    printf("H paraggelia me arithmo %d paradothike se %d lepta.\n", oid, tDeliveryTime);
    screen=1;
    rc = pthread_cond_signal(&screenWait);
    rc = pthread_mutex_unlock(&screenLock);

    sleep(roadTime);//time on road to return

    deliverers++;
    rc = pthread_cond_signal(&delivererWait);
    rc = pthread_mutex_unlock(&delivererLock);
//End Delivery

//Update sumDeliveryTime 
    rc=pthread_mutex_lock(&sumDelLock);
    while(!changesumDeliveryTime)
    	rc = pthread_cond_wait(&sumDelWait, &sumDelLock);
    changesumDeliveryTime =0;

    sumDeliveryTime+=tDeliveryTime;//add to sum global var

    changesumDeliveryTime=1;
    rc = pthread_cond_signal(&sumDelWait);
    rc = pthread_mutex_unlock(&sumDelLock);

//Update maxDeliveryTime
    if(tDeliveryTime > maxDeliveryTime){ //check if it is the max waiting time for telephonite

    	rc=pthread_mutex_lock(&maxDelLock);
    	while(!changemaxDeliveryTime)
    		rc = pthread_cond_wait(&maxDelWait, &maxDelLock);
    	changemaxDeliveryTime =0;
	
    	maxDeliveryTime = tDeliveryTime; //update max delivery time
	
    	changemaxDeliveryTime=1;
    	rc = pthread_cond_signal(&maxDelWait);
    	rc = pthread_mutex_unlock(&maxDelLock);
	}

	tColdPizzaTime = tPizzaDelivered - tReadyPizza; // time that pizza got cold

//Update sumColdPizzaTime
	rc=pthread_mutex_lock(&sumColdPizzaLock);
    while(!changesumColdPizzaTime)
    	rc = pthread_cond_wait(&sumColdPizzaWait, &sumColdPizzaLock);
    changesumColdPizzaTime =0;

    sumColdPizzaTime+=tColdPizzaTime;//add to sum global var

    changesumColdPizzaTime=1;
    rc = pthread_cond_signal(&sumColdPizzaWait);
    rc = pthread_mutex_unlock(&sumColdPizzaLock);

//Update maxColdPizzaTime
    if(tColdPizzaTime > maxColdPizzaTime){ //check if it is the max waiting time for telephonite

    	rc=pthread_mutex_lock(&maxColdPizzaLock);
    	while(!changemaxColdPizzaTime)
    		rc = pthread_cond_wait(&maxColdPizzaWait, &maxColdPizzaLock);
    	changemaxColdPizzaTime =0;
	
    	maxColdPizzaTime = tColdPizzaTime; //update max delivery time
	
    	changemaxColdPizzaTime=1;
    	rc = pthread_cond_signal(&maxColdPizzaWait);
    	rc = pthread_mutex_unlock(&maxColdPizzaLock);
	}

	pthread_exit(NULL);

}// telos order


//main
int main(int argc, char **argv)
{
	
    //checking if arguments given are exactly 2
    if (argc != 3)
    {
        printf ("Unexpected number of arguments\n");
        exit(-1);
    }

    //assigning arguments to variables
    
    Ncust = atoi(argv[1]);
    seed= atoi(argv[2]);
	printf("ncust is %d and seed is %d \n", Ncust, seed);

    //Customer amount positive int check
    if (Ncust<0) {
        printf("Error the number of threads to run should be positive number. Current number given %d. \n" , Ncust);
        exit(-1);
    }
    //initializing threads and mutexes

    pthread_t tid[Ncust];
    int oid[Ncust];
    int i;
    int rc;
    
    pthread_mutex_init(&telLock, NULL);
    pthread_mutex_init(&cookLock, NULL);
    pthread_mutex_init(&ovenLock, NULL);
    pthread_mutex_init(&packerLock, NULL);
    pthread_mutex_init(&delivererLock, NULL);
    pthread_mutex_init(&moneyLock, NULL);
    pthread_mutex_init(&screenLock, NULL);

    pthread_mutex_init(&sTransactionLock, NULL);
    pthread_mutex_init(&fTransactionLock, NULL);
    pthread_mutex_init(&sumTelLock, NULL);
    pthread_mutex_init(&maxTelLock, NULL);
    pthread_mutex_init(&sumDelLock, NULL);
    pthread_mutex_init(&maxDelLock, NULL);
    pthread_mutex_init(&sumColdPizzaLock, NULL);
    pthread_mutex_init(&maxColdPizzaLock, NULL);

	pthread_cond_init(&telWait, NULL);
	pthread_cond_init(&cookWait, NULL);
	pthread_cond_init(&ovenWait, NULL);
	pthread_cond_init(&packerWait, NULL);
	pthread_cond_init(&delivererWait, NULL);
	pthread_cond_init(&moneyWait, NULL);
	pthread_cond_init(&screenWait, NULL);

	pthread_cond_init(&sTransactionWait, NULL);
	pthread_cond_init(&fTransactionWait, NULL);
	pthread_cond_init(&sumTelWait, NULL);
	pthread_cond_init(&maxTelWait, NULL);
	pthread_cond_init(&sumDelWait, NULL);
	pthread_cond_init(&maxDelWait, NULL);
	pthread_cond_init(&sumColdPizzaWait, NULL);
	pthread_cond_init(&maxColdPizzaWait, NULL);

    //creating threads
    for ( i = 0; i < Ncust; i++) {
    	oid[i]=i+1;//1-Ncust arithmoi nimatos/paraggelias
        rc = pthread_create(&tid[i], NULL, order, &oid[i]);	
        if (rc){
			printf("ERROR code from pthread_create() is %d\n", rc);
			exit(-1);
		} 
    }	

    //waiting for threads

    for (i = 0; i < Ncust; i++){
        pthread_join(tid[i], NULL);
    }

    //print results, ta deyrerolepta se lepta
	printf(" Money: %d $ \n", totalMoney);
	printf(" Suceeded Transactions: %d \n", suceededtransaction);
	printf(" Failed Transactions: %d \n", failedtransaction);

	printf(" Average Time Waiting for Telephonite: %d lepta \n", sumTimeWaitingTel/Ncust);
	printf(" Maximum Time Waiting for Telephonite: %d lepta \n", maxTimeWaitingTel);

	printf(" Average Time for Delivery: %d lepta \n", sumDeliveryTime/(Ncust-failedtransaction));
	printf(" Maximum Time for Delivery: %d lepta \n", maxDeliveryTime);

	printf(" Average Time that the Pizzas got Cold: %d lepta \n", sumColdPizzaTime/(Ncust-failedtransaction));
	printf(" Maximum Time that the Pizzas got Cold: %d lepta \n", maxColdPizzaTime);

 	//deallocating mutexes/wait conditions
    pthread_mutex_destroy(&telLock);
    pthread_mutex_destroy(&cookLock);
    pthread_mutex_destroy(&ovenLock);
    pthread_mutex_destroy(&packerLock);
    pthread_mutex_destroy(&delivererLock);
    pthread_mutex_destroy(&moneyLock);
    pthread_mutex_destroy(&screenLock);

    pthread_mutex_destroy(&sTransactionLock);
    pthread_mutex_destroy(&fTransactionLock);
    pthread_mutex_destroy(&sumTelLock);
    pthread_mutex_destroy(&maxTelLock);
    pthread_mutex_destroy(&sumDelLock);
    pthread_mutex_destroy(&maxDelLock);
    pthread_mutex_destroy(&sumColdPizzaLock);
    pthread_mutex_destroy(&maxColdPizzaLock);

    pthread_cond_destroy(&telWait);
    pthread_cond_destroy(&cookWait);
    pthread_cond_destroy(&ovenWait);
    pthread_cond_destroy(&packerWait);
    pthread_cond_destroy(&delivererWait);
    pthread_cond_destroy(&moneyWait);
    pthread_cond_destroy(&screenWait);

    pthread_cond_destroy(&sTransactionWait);
    pthread_cond_destroy(&fTransactionWait);
    pthread_cond_destroy(&sumTelWait);
    pthread_cond_destroy(&maxTelWait);
    pthread_cond_destroy(&sumDelWait);
    pthread_cond_destroy(&maxDelWait);
    pthread_cond_destroy(&sumColdPizzaWait);
    pthread_cond_destroy(&maxColdPizzaWait);

 	return 0;
 }