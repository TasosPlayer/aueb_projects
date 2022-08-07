#include <stdio.h>
#include <pthread.h>
#include <stdlib.h>
#include <unistd.h>
#include <time.h>

unsigned int sleep(unsigned int seconds);
unsigned int rngBetweenTwoValues (unsigned int* seed, int tLow, int tHigh);
void *order(void *oid);

// Constants

const int Ntel=3; //τηλεφωνητές
const int Ncook=2; //ψήστες
const int Noven=10; //φούρνοι
const int Ndeliverer=7; //διανομείς
const int Torderlow=1; //λεπτό
const int Torderhigh=5; //λεπτά
const int Norderlow=1; //πίτσες
const int Norderhigh=5; //πίτσες
const int Tpaymentlow=1; //λεπτό
const int Tpaymenthigh=2; //λεπτά
const int Cpizza=10; //ευρώ
const int Pfail=5; //%
const int Tprep=1; //λεπτό
const int Tbake=10; //λεπτά
const int Tpack=2; //λεπτά
const int Tdellow=5; //λεπτά
const int Tdelhigh=15; //λεπτά

// Globals

int Ncust;
unsigned int seed;

//main resources
int tels = Ntel;
int cooks = Ncook;
int ovens = Noven;
int deliverers = Ndeliverer;

//numbers and statistics
int totalMoney=0;

int suceededtransaction=0;
int failedtransaction=0;

int sumTimeWaitingTel=0;
int maxTimeWaitingTel=0;

int sumDeliveryTime=0;
int maxDeliveryTime=0;

int sumColdPizzaTime=0;
int maxColdPizzaTime=0;

//resources and counters for numbers and statistics
int packer=1;
int screen=1;

int changeMoney=1;

int changeSTransactions=1;
int changeFTransactions=1;

int changesumTimeWaitingTel=1;
int changemaxTimeWaitingTel=1;

int changesumDeliveryTime=1;
int changemaxDeliveryTime=1;

int changesumColdPizzaTime=1;
int changemaxColdPizzaTime=1;


// Mutexes initialization
pthread_mutex_t telLock;
pthread_mutex_t cookLock;
pthread_mutex_t ovenLock;
pthread_mutex_t packerLock;
pthread_mutex_t delivererLock;
pthread_mutex_t moneyLock;
pthread_mutex_t screenLock;

pthread_mutex_t sTransactionLock;
pthread_mutex_t fTransactionLock;

pthread_mutex_t sumTelLock;
pthread_mutex_t maxTelLock;

pthread_mutex_t sumDelLock;
pthread_mutex_t maxDelLock;

pthread_mutex_t sumColdPizzaLock;
pthread_mutex_t maxColdPizzaLock;

//Conditions initialization
pthread_cond_t telWait;
pthread_cond_t cookWait;
pthread_cond_t ovenWait;
pthread_cond_t packerWait;
pthread_cond_t delivererWait;
pthread_cond_t moneyWait;
pthread_cond_t screenWait;

pthread_cond_t sTransactionWait;
pthread_cond_t fTransactionWait;
pthread_cond_t sDeliveryWait;

pthread_cond_t sumTelWait;
pthread_cond_t maxTelWait;

pthread_cond_t sumDelWait;
pthread_cond_t maxDelWait;

pthread_cond_t sumColdPizzaWait;
pthread_cond_t maxColdPizzaWait;


