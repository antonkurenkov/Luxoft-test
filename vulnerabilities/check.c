/*
 * check.c
 *
 *  Created on: 30 дек. 2019 г.
 *      Author: air
 */
#include <stdio.h>
#include <stdlib.h>
#include <wchar.h>
#include <string.h> // need to be included for memset() func
#define PASSWORD "ABCD1234!"
/* Not sure if hardcoded like that password is good practice */

/*You need not worry about other include statements if at all any are missing */

 void func1()
 {
     char * data;
     char * dataBuffer = (char *)ALLOCA(100*sizeof(char));
     /* Wrong syntax for alloca() func + this func may cause stack overflow */
     memset(dataBuffer, 'A', 100-1);
     dataBuffer[100-1] = '\0';
     data = dataBuffer - 8;
     /* Some magic number */
     {
         char source[100];
         memset(source, 'C', 100-1);
         source[100-1] = '\0';
         strcpy(data, source);
         /* Memory was not allocated for data */
         if (data != NULL)
         {
             printf("%s\n", data);
         }
     }
 }


 void func2()
 {
     char * data;
     data = NULL;
     data = (char *)calloc(100, sizeof(char));
     /* no memory release*/
     strcpy(data, "A String");
     if(data != NULL)
     {
         printf("size of data = %lu\n", sizeof(data));
         printf("%s\n", data);
     }

 }

 void func3()
 {
     char * password;
     char passwordBuffer[100] = "";
     password = passwordBuffer;
     strcpy(password, PASSWORD);

     {
         HANDLE pHandle;
         /* Probably need to use "... = new HANDLE" */
         char * username = "User";
         char * domain = "Domain";
         /* Let's say LogonUserA is a custon authentication function*/
         if (LogonUserA(
                     username,
                     domain,
                     password,
                     &pHandle) != 0)
        	 /* Not sure it's possible to get value of undeclared var */
         {
             printf("User logged in successfully.\n");
             CloseHandle(pHandle);
             /* Handle doesn't close if LogonUserA() returns 0 */
         } else {
             printf("Unable to login.\n");
         }
     }
 }

/* Could not find any vulnerabilities */
static void func4()
{
    char * data;
    data = NULL;
    data = (char *)calloc(20, sizeof(char));
    if (data != NULL)
    {
        strcpy(data, "Initialize");
        if(data != NULL)
        {
            printf("%s\n", data);
        }
        free(data);
    }
}


void func5()
{
int i = 0; do
{
	 printf("%d ", i);
	 i = (i + 1) % 256;
	 printf("%d\n", i);
	} while(i >= 0);
	/* infinite cycle, no escape conditions */
}

void func6()
{
	char dataBuffer[100] = "";
	char * data = dataBuffer;
	printf("Please enter a string: ");
	/* If a string length is more than 100, the tail is gonna be cut */
	if (fgets(data, 100, stdin) < 0)
	/* It is recomended to use getline() instead of fgets() */
	/* fgets() returns null pointer or "data" pointer, neither could be compared to 0 */
	{
		printf("fgets failed!\n");
		exit(1); }
	if(data != NULL)
	{
		printf("%s\n", data);
	}
}

void func7()
{
	char * data;
	data = "Fortify";
	data = NULL;
	printf("%s\n", data);
	/* Reading through null pointer, undefined behavior */
}

int main(int argc, char * argv[])
{
     printf("Calling func1\n");
     func1();
     printf("Calling func2\n");
     func2();
     printf("Calling func3\n");
     func3();
     printf("Calling func4\n");
     func4();
     printf("Calling func5\n");
     func5();
     printf("Calling func6\n");
     func6();
     printf("Calling func7\n");
     func7();
return 0; }
