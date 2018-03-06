//yo
//ok so this will extract the first.... lets say 10,000 digits of pi
  //except for the first value 3
//then ill bunch every 3 values into a single value ex./ 3,2,1 -> [3,2,1]
//ill send those over to another text file called coordinates
#include <stdio.h>

int main(int argc, char const *argv[]) {
  //some basic file io
  FILE *fp;
  FILE *fpOut;
  fp = fopen("pi_thousand.txt","r");
  fpOut = fopen("coordinates.txt","a");
  //the first 1,0000 digits of pi was supplied by eveandersson.com
  //so thank you thank you

  //and this is simple
  //all the value are on a single line so there will be no trouble with
  //newline or whatever
  //lets read three values and write them to my output file!! :)
  char one;
  char two;
  char three;

  while ( fscanf(fp,"%c%c%c",&one,&two,&three ) != EOF) {
    fprintf(fpOut, "%c%c%c\n",one,two,three );
  }

  fclose(fpOut);
  fclose(fp);

  return 0;
}
