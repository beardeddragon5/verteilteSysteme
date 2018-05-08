
#include "client.h"

#define RMACHINE "localhost"

#define STREQ(a, b) (strcmp(a, b) == 0)
#define STRVALID(a) (strlen(a) > 0)


int main(int argc, char** argv) {
  CLIENT* client = NULL;
  bool_t done = FALSE;


  client = clnt_create (RMACHINE, RDBASEPROG, RDBASEVERS, "TCP");
  if (client == NULL) {
    fprintf(stderr, "Could not contact remote program.\n");
    exit(1);
  }

  do {
    char command[MAXWORD*3 + 3 + 10];
    char cmd[MAXWORD+1];
    char arg1[MAXWORD+1];
    char arg2[MAXWORD+1];
    int argCount;



    printf("> ");
    fgets(command, sizeof(command), stdin);
    argCount = sscanf(command, "%s %s %s", cmd, arg1, arg2);

    if (STREQ(cmd, "init")) {
      if (argCount != 1) {
        fprintf(stderr, "usage: init \ninitalize database\n");
      } else if (!initw(client)) {
        fprintf(stderr, "init failed\n");
        exit(1);
      }
    } else if (STREQ(cmd, "insert")) {
      if (argCount != 2) {
        fprintf(stderr, "usage: insert <value>\ninsert value into database\n");
      } else if (!insertw(client, arg1)) {
        fprintf(stderr, "insert failed\n");
        exit(1);
      }
    } else if (STREQ(cmd, "del")) {
      if (argCount != 2) {
        fprintf(stderr, "usage: del <value>\ndelete value from database\n");
      } else if (!deletew(client, arg1)) {
        fprintf(stderr, "deletion failed\n");
        exit(1);
      }
    } else if (STREQ(cmd, "exist")) {
      if (argCount != 2) {
        fprintf(stderr, "usage: exist <value>\ncheck for existens\n");
      } else if (lookupw(client, arg1)) {
        printf("true\n");
      } else {
        printf("false\n");
      }
    } else if (STREQ(cmd, "update")) {
      if (argCount != 2) {
        fprintf(stderr, "usage: update <value> <value>\nupdate first value with secound value\n");
      } else if (!updatew(client, arg1, arg2)) {
        fprintf(stderr, "update failed\n");
        exit(1);
      }
    } else if (STREQ(cmd, "count")) {
      if (argCount != 1) {
        fprintf(stderr, "usage: count\ngives count of values in database\n");
      } else {
        int count = countw(client);
        if ( count == -1 ) {
          fprintf(stderr, "count failed\n");
          exit(1);
        }
        printf("%d\n", count);
      }
    } else if (STREQ(cmd, "select")) {
      if (argCount != 1) {
        fprintf(stderr, "usage: select1\nreturns line with all values\n");
      } else {
        char* line = selectw(client);
        if ( line == NULL ) {
          fprintf(stderr, "select1 failed\n");
          exit(1);
        }
        printf("%s\n", line);
      }
    } else if (STREQ(cmd, "select2")) {
      if (argCount != 1) {
        fprintf(stderr, "usage: select2\nreturns values each in one line\n");
      } else {
        manywords words = select2w(client);
        for (u_int i = 0; i < words.words.words_len; i++) {
          printf("%s\n", words.words.words_val[i].word);
        }
      }
    } else if (STREQ(cmd, "quit")) {
      if (argCount != 1) {
        fprintf(stderr, "usage: quit\nquit application\n");
      } else {
        done = TRUE;
      }
    } else {
      fprintf(stderr, "commands:\n\tinit\n\tinsert\n\tdel\n\texist\n\tupdate\n\tcount\n\tselect\n\tselect2\n\tquit\n");
    }
  } while(!done);
}
