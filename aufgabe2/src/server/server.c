
#include "server.h"
#include <stdlib.h>
#include <string.h>

char dict[DICTSIZ] [MAXWORD+1];
int  nwords = 0;


int initw() {
   nwords = 0;
   return 1;
}

int insertw(char* word) {
   for (int i = 0; i < nwords; i++)
      if (strcmp (word, dict[i]) == 0)
         return 0;

   strcpy (dict[nwords], word);
   nwords++;
   return nwords;
}

int deletew(char* word) {
   for (int i = 0; i < nwords; i++)
      if (strcmp(word, dict[i]) == 0) {
         nwords--;
         strcpy(dict[i], dict[nwords]);
         return 1;
      }
   return 0;
}

int lookupw (char* word) {
   for (int i = 0; i < nwords; i++)
      if (strcmp (word, dict[i]) == 0)
         return 1;
   return 0;
}

int updatew (char* word, char* word2) {
   for (int i = 0; i < nwords; i++)
      if (strcmp (word, dict[i]) == 0) {
         strcpy (dict[i], word2);
         return 1;
      }
   return 0;
}

int countw() {
  return nwords;
}

char* selectw() {
  int length = 0;
  for (int i = 0; i < nwords; i++) {
    length += strlen(dict[i]);
  }
  char* out = malloc(length + nwords + 1);
  out[length + nwords] = '\0';

  int offset = 0;
  for (int i = 0; i < nwords; i++) {
    int len = strlen(dict[i]);
    strcpy(out + offset, dict[i]);
    out[offset + len] = ' ';
    out[offset + len+1] = '\0';
    offset += len + 1;
  }
  return out;
}

manywords* select2w() {
  manywords* out = (manywords*) malloc(sizeof(manywords));
  out->words.words_val = malloc(DICTSIZ * sizeof(oneword));
  out->words.words_len = nwords;
  for (int i = 0; i < nwords; i++) {
    out->words.words_val[i].word = dict[i];
  }
  return out;
}
